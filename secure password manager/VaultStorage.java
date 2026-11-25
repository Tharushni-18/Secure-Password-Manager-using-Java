
import javax.crypto.SecretKey;
import java.io.*;
import java.nio.file.*;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;

public class VaultStorage {

    private static final String FILE_NAME = "vault.dat";
    private static final byte[] MAGIC = {'S', 'P', 'M', '1'};

    public boolean exists() {
        return Files.exists(Paths.get(FILE_NAME));
    }

    public List<Credential> load(String masterPassword)
            throws IOException, GeneralSecurityException {

        byte[] fileData = Files.readAllBytes(Paths.get(FILE_NAME));

        try (DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(fileData))) {

            byte[] magic = new byte[4];
            in.readFully(magic);

            if (!Arrays.equals(magic, MAGIC)) {
                throw new IOException("Invalid vault file.");
            }

            byte[] salt = new byte[16];
            byte[] nonce = new byte[12];

            in.readFully(salt);
            in.readFully(nonce);

            int length = in.readInt();

            if (length < 16 || length > 10000000 ||
                    length != in.available()) {
                throw new IOException("Invalid vault data.");
            }

            byte[] encrypted = new byte[length];
            in.readFully(encrypted);

            SecretKey key = EncryptionUtil.deriveKey(
                    masterPassword, salt);

            byte[] decrypted = EncryptionUtil.decrypt(
                    encrypted, key, nonce);

            try {
                return deserialize(decrypted);
            } finally {
                Arrays.fill(decrypted, (byte) 0);
            }
        }
    }

    public void save(String masterPassword, List<Credential> credentials)
            throws IOException, GeneralSecurityException {

        byte[] salt = EncryptionUtil.generateRandomBytes(16);
        byte[] nonce = EncryptionUtil.generateRandomBytes(12);

        SecretKey key = EncryptionUtil.deriveKey(masterPassword, salt);

        byte[] plainData = serialize(credentials);

        byte[] encrypted;

        try {
            encrypted = EncryptionUtil.encrypt(plainData, key, nonce);
        } finally {
            Arrays.fill(plainData, (byte) 0);
        }

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (DataOutputStream out = new DataOutputStream(buffer)) {
            out.write(MAGIC);
            out.write(salt);
            out.write(nonce);
            out.writeInt(encrypted.length);
            out.write(encrypted);
        }

        Path target = Paths.get(FILE_NAME);
        Path temp = Paths.get(FILE_NAME + ".tmp");

        try {
            Files.write(temp, buffer.toByteArray());

            try {
                Files.move(temp, target,
                        StandardCopyOption.REPLACE_EXISTING,
                        StandardCopyOption.ATOMIC_MOVE);
            } catch (AtomicMoveNotSupportedException e) {
                Files.move(temp, target,
                        StandardCopyOption.REPLACE_EXISTING);
            }

        } finally {
            Files.deleteIfExists(temp);
        }
    }

    private byte[] serialize(List<Credential> credentials)
            throws IOException {

        ByteArrayOutputStream buffer = new ByteArrayOutputStream();

        try (DataOutputStream out = new DataOutputStream(buffer)) {

            out.writeInt(credentials.size());

            for (Credential c : credentials) {
                out.writeUTF(c.getWebsite());
                out.writeUTF(c.getUsername());
                out.writeUTF(c.getPassword());
            }
        }

        return buffer.toByteArray();
    }

    private List<Credential> deserialize(byte[] data)
            throws IOException {

        List<Credential> credentials = new ArrayList<>();

        try (DataInputStream in = new DataInputStream(
                new ByteArrayInputStream(data))) {

            int count = in.readInt();

            if (count < 0 || count > 10000) {
                throw new IOException("Invalid credential count.");
            }

            for (int i = 0; i < count; i++) {

                String website = in.readUTF();
                String username = in.readUTF();
                String password = in.readUTF();

                credentials.add(
                        new Credential(website, username, password));
            }

            if (in.available() != 0) {
                throw new IOException("Invalid vault contents.");
            }
        }

        return credentials;
    }
}
