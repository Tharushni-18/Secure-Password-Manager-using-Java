
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.security.GeneralSecurityException;
import java.util.Arrays;

public class EncryptionUtil {

    private static final int ITERATIONS = 310000;
    private static final int KEY_LENGTH = 256;

    public static byte[] generateRandomBytes(int length) {
        byte[] bytes = new byte[length];
        new SecureRandom().nextBytes(bytes);
        return bytes;
    }

    public static SecretKey deriveKey(String password, byte[] salt)
            throws GeneralSecurityException {

        PBEKeySpec spec = new PBEKeySpec(
                password.toCharArray(), salt, ITERATIONS, KEY_LENGTH);

        try {
            SecretKeyFactory factory =
                    SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");

            byte[] keyBytes = factory.generateSecret(spec).getEncoded();

            try {
                return new SecretKeySpec(keyBytes, "AES");
            } finally {
                Arrays.fill(keyBytes, (byte) 0);
            }

        } finally {
            spec.clearPassword();
        }
    }

    public static byte[] encrypt(byte[] data, SecretKey key, byte[] nonce)
            throws GeneralSecurityException {

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec spec = new GCMParameterSpec(128, nonce);

        cipher.init(Cipher.ENCRYPT_MODE, key, spec);

        return cipher.doFinal(data);
    }

    public static byte[] decrypt(byte[] data, SecretKey key, byte[] nonce)
            throws GeneralSecurityException {

        Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");

        GCMParameterSpec spec = new GCMParameterSpec(128, nonce);

        cipher.init(Cipher.DECRYPT_MODE, key, spec);

        return cipher.doFinal(data);
    }
}
