
import java.util.Scanner;
import java.util.ArrayList;
import java.util.List;
import java.security.GeneralSecurityException;
import java.io.IOException;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        VaultStorage storage = new VaultStorage();

        System.out.println("================================");
        System.out.println("     SECURE PASSWORD MANAGER");
        System.out.println("================================");

        try {

            String masterPassword;

            // First-time setup
            if (!storage.exists()) {

                System.out.println("\nCreate Master Password");

                System.out.print("Enter Master Password: ");
                masterPassword = sc.nextLine();

                System.out.print("Confirm Master Password: ");
                String confirm = sc.nextLine();

                if (!masterPassword.equals(confirm)) {
                    System.out.println("Passwords do not match!");
                    return;
                }

                if (masterPassword.length() < 8) {
                    System.out.println("Password must contain at least 8 characters!");
                    return;
                }

                storage.save(masterPassword, new ArrayList<>());

                System.out.println("Vault created successfully!");

            } else {

                // Existing user login
                System.out.print("Enter Master Password: ");
                masterPassword = sc.nextLine();
            }

            // Load saved credentials
            List<Credential> credentials;

            try {
                credentials = storage.load(masterPassword);
            } catch (GeneralSecurityException e) {
                System.out.println("Incorrect Master Password!");
                return;
            }

            // Create PasswordManager object
            PasswordManager manager = new PasswordManager(
                    storage,
                    masterPassword,
                    credentials
            );

            int choice = 0;

            do {

                System.out.println("\n========== MAIN MENU ==========");
                System.out.println("1. Add Credential");
                System.out.println("2. View Credentials");
                System.out.println("3. Search Credential");
                System.out.println("4. Update Credential");
                System.out.println("5. Delete Credential");
                System.out.println("6. Generate Password");
                System.out.println("7. Exit");
                System.out.println("===============================");

                System.out.print("Enter your choice: ");

                try {

                    choice = Integer.parseInt(sc.nextLine());

                    switch (choice) {

                        case 1:
                            manager.addCredential(sc);
                            break;

                        case 2:
                            manager.viewCredentials();
                            break;

                        case 3:
                            manager.searchCredential(sc);
                            break;

                        case 4:
                            manager.updateCredential(sc);
                            break;

                        case 5:
                            manager.deleteCredential(sc);
                            break;

                        case 6:

                            System.out.print("Enter password length (8-128): ");

                            try {
                                int length = Integer.parseInt(sc.nextLine());

                                String password =
                                        PasswordGenerator.generatePassword(length);

                                System.out.println("Generated Password: " + password);

                            } catch (NumberFormatException e) {
                                System.out.println("Enter a valid number!");
                            } catch (IllegalArgumentException e) {
                                System.out.println(e.getMessage());
                            }

                            break;

                        case 7:
                            System.out.println("Thank you for using Secure Password Manager!");
                            break;

                        default:
                            System.out.println("Invalid choice! Enter 1 to 7.");
                    }

                } catch (NumberFormatException e) {
                    System.out.println("Please enter a valid number!");
                }

            } while (choice != 7);

        } catch (IOException | GeneralSecurityException e) {

            System.out.println("Application Error: " + e.getMessage());

        } finally {

            sc.close();
        }
    }
}
