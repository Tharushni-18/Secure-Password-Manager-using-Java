
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.security.GeneralSecurityException;
import java.io.IOException;

public class PasswordManager {

    private final List<Credential> credentials;
    private final VaultStorage storage;
    private final String masterPassword;

    public PasswordManager(VaultStorage storage,
                           String masterPassword,
                           List<Credential> credentials) {

        this.storage = storage;
        this.masterPassword = masterPassword;
        this.credentials = new ArrayList<>(credentials);
    }

    private void saveData() {
        try {
            storage.save(masterPassword, credentials);
            System.out.println("Vault saved successfully.");
        } catch (IOException | GeneralSecurityException e) {
            System.out.println("Error saving vault: " + e.getMessage());
        }
    }

    public void addCredential(Scanner sc) {

        System.out.print("Enter website: ");
        String website = sc.nextLine().trim();

        System.out.print("Enter username: ");
        String username = sc.nextLine().trim();

        System.out.print("Enter password: ");
        String password = sc.nextLine();

        if (website.isEmpty() || username.isEmpty() || password.isEmpty()) {
            System.out.println("All fields are required.");
            return;
        }

        credentials.add(new Credential(website, username, password));

        saveData();
        System.out.println("Credential added successfully!");
    }

    public void viewCredentials() {

        if (credentials.isEmpty()) {
            System.out.println("No credentials found.");
            return;
        }

        System.out.println("\n------ SAVED CREDENTIALS ------");

        for (Credential c : credentials) {
            System.out.println("Website  : " + c.getWebsite());
            System.out.println("Username : " + c.getUsername());
            System.out.println("Password : " + c.getPassword());
            System.out.println("-------------------------------");
        }
    }

    public void searchCredential(Scanner sc) {

        System.out.print("Enter website to search: ");
        String search = sc.nextLine().trim();

        boolean found = false;

        for (Credential c : credentials) {

            if (c.getWebsite().equalsIgnoreCase(search)) {

                System.out.println("Website  : " + c.getWebsite());
                System.out.println("Username : " + c.getUsername());
                System.out.println("Password : " + c.getPassword());

                found = true;
            }
        }

        if (!found) {
            System.out.println("Credential not found.");
        }
    }

    public void updateCredential(Scanner sc) {

        System.out.print("Enter website to update: ");
        String website = sc.nextLine().trim();

        for (Credential c : credentials) {

            if (c.getWebsite().equalsIgnoreCase(website)) {

                System.out.print("Enter new username: ");
                String username = sc.nextLine().trim();

                System.out.print("Enter new password: ");
                String password = sc.nextLine();

                if (username.isEmpty() || password.isEmpty()) {
                    System.out.println("Fields cannot be empty.");
                    return;
                }

                c.setUsername(username);
                c.setPassword(password);

                saveData();

                System.out.println("Credential updated successfully!");
                return;
            }
        }

        System.out.println("Credential not found.");
    }

    public void deleteCredential(Scanner sc) {

        System.out.print("Enter website to delete: ");
        String website = sc.nextLine().trim();

        boolean removed = credentials.removeIf(
                c -> c.getWebsite().equalsIgnoreCase(website));

        if (removed) {
            saveData();
            System.out.println("Credential deleted successfully!");
        } else {
            System.out.println("Credential not found.");
        }
    }
}
