
import java.security.SecureRandom;

public class PasswordGenerator {

    private static final String CHARACTERS =
            "ABCDEFGHIJKLMNOPQRSTUVWXYZ" +
            "abcdefghijklmnopqrstuvwxyz" +
            "0123456789!@#$%^&*()_+-=";

    private static final SecureRandom random = new SecureRandom();

    public static String generatePassword(int length) {

        if (length < 8 || length > 128) {
            throw new IllegalArgumentException(
                    "Password length must be between 8 and 128.");
        }

        StringBuilder password = new StringBuilder();

        for (int i = 0; i < length; i++) {
            int index = random.nextInt(CHARACTERS.length());
            password.append(CHARACTERS.charAt(index));
        }

        return password.toString();
    }
}