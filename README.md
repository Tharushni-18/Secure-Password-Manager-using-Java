 Secure Password Manager Using Core Java

1. Project Overview

The **Secure Password Manager** is a Java-based application designed to securely store and manage website login credentials. It allows users to add, view, search, update, and delete passwords using a simple menu-driven interface.

The application uses **AES-256-GCM encryption** to protect stored credentials and **PBKDF2WithHmacSHA256** to derive an encryption key from the user's master password.

2. Objectives

- Securely store website login credentials.
- Protect sensitive information using encryption.
- Generate strong random passwords.
- Provide easy credential management.
- Maintain encrypted data between application sessions.

3. Technologies Used

- Programming Language: Java
- Concepts: Object-Oriented Programming, Collections, Exception Handling, File Handling
- Encryption: AES-256-GCM
- Key Derivation: PBKDF2WithHmacSHA256
- IDE:Visual Studio Code
- Storage: Local encrypted file (`vault.dat`)

4. Features

1. Master Password Authentication – Create a master password to access the vault.
2. Add Credentials – Save website names, usernames, and passwords.
3. View Credentials – Display saved login details.
4. Search Credentials – Find credentials using website names.
5. Update Credentials – Modify saved usernames and passwords.
6. Delete Credentials – Remove stored credentials.
7. Password Generator – Generate random passwords between 8 and 128 characters.
8. Encrypted Storage – Store credentials in an encrypted local vault.
9. Data Persistence – Retain saved credentials after closing and reopening the application.

5. Project Structure

SecurePasswordManager/
│
├── Main.java

├── Credential.java

├── PasswordGenerator.java

├── EncryptionUtil.java

├── VaultStorage.java

├── PasswordManager.java


└── vault.dat

6.  Application Menu

================================
     SECURE PASSWORD MANAGER
================================

========== MAIN MENU ==========
1. Add Credential
   
2. View Credentials
   
3. Search Credential
 
4. Update Credential
  
5. Delete Credential
 
6. Generate Password
 
7. Exit
===============================

7. Security Implementation

- AES-256-GCM: Encrypts credentials and provides authenticated encryption.
- PBKDF2WithHmacSHA256: Derives a 256-bit encryption key from the master password.
- Random Salt: Uses a unique salt for key derivation.
- Random Nonce: Uses a fresh nonce for each encryption operation.
- Encrypted File Storage: Saves encrypted information in `vault.dat`.

8. Java Concepts Demonstrated

- Classes and Objects
- Encapsulation
- Constructors
- ArrayList and Collections
- File Handling
- Exception Handling
- Java Cryptography Architecture (JCA)
- Loops and Conditional Statements


9.Advantages

- Data Security: Protects stored credentials using AES-256-GCM encryption.
-  Master Password Protection: Uses a master password to access the encrypted vault.
-  Easy Management: Allows users to add, view, search, update, and delete credentials.
-  Password Generation: Generates random passwords for better account security.
   - Secure Random Number Generation

