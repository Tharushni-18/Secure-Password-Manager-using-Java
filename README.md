 Secure Password Manager Using Core Java

★ Project Overview

The **Secure Password Manager** is a Java-based application designed to securely store and manage website login credentials. It allows users to add, view, search, update, and delete passwords using a simple menu-driven interface.

The application uses **AES-256-GCM encryption** to protect stored credentials and **PBKDF2WithHmacSHA256** to derive an encryption key from the user's master password.

★ Objectives

1. Securely store website login credentials.
2. Protect sensitive information using encryption.
3. Generate strong random passwords.
4. Provide easy credential management.
5. Maintain encrypted data between application sessions.
  
★ Technologies Used

1. Programming Language: Java
2. Concepts: Object-Oriented Programming, Collections, Exception Handling, File Handling
3. Encryption: AES-256-GCM
4. Key Derivation: PBKDF2WithHmacSHA256
5. IDE:Visual Studio Code
6. Storage: Local encrypted file (`vault.dat`)

★ Features

1. Master Password Authentication – Create a master password to access the vault.
2. Add Credentials – Save website names, usernames, and passwords.
3. View Credentials – Display saved login details.
4. Search Credentials – Find credentials using website names.
5. Update Credentials – Modify saved usernames and passwords.
6. Delete Credentials – Remove stored credentials.
7. Password Generator – Generate random passwords between 8 and 128 characters.
8. Encrypted Storage – Store credentials in an encrypted local vault.
9. Data Persistence – Retain saved credentials after closing and reopening the application.

★ Project Structure

SecurePasswordManager/
│
├── Main.java

├── Credential.java

├── PasswordGenerator.java

├── EncryptionUtil.java

├── VaultStorage.java

├── PasswordManager.java


└── vault.dat

 ★ Application Menu


1. Add Credential
   
2. View Credentials
   
3. Search Credential
 
4. Update Credential
  
5. Delete Credential
 
6. Generate Password
 
7. Exit


★ Security Implementation

1. AES-256-GCM: Encrypts credentials and provides authenticated encryption.
2. PBKDF2WithHmacSHA256: Derives a 256-bit encryption key from the master password.
3. Random Salt: Uses a unique salt for key derivation.
4. Random Nonce: Uses a fresh nonce for each encryption operation.
5. Encrypted File Storage: Saves encrypted information in `vault.dat`.

★ Java Concepts Demonstrated

1. Classes and Objects
2. Encapsulation
3. Constructors
4. ArrayList and Collections
5. File Handling
6. Exception Handling
7. Java Cryptography Architecture (JCA)
8. Loops and Conditional Statements


★ Advantages
  
1. Data Security: Protects stored credentials using AES-256-GCM encryption.
2. Master Password Protection: Uses a master password to access the encrypted vault.
3. Easy Management: Allows users to add, view, search, update, and delete credentials.
4. Password Generation: Generates random passwords for better account security.
5. Data Persistence: Saves encrypted credentials locally, so data remains available after restarting.

