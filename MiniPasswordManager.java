import java.security.MessageDigest;
import java.security.SecureRandom;
import java.util.Base64;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class MiniPasswordManager {

    // =========================
    // Data Structures
    // =========================

    private static final Map<String, User> registeredUsers = new HashMap<>();

    private static final Scanner scanner = new Scanner(System.in);

    private static final SecureRandom secureRandom = new SecureRandom();

    private static final int MAX_LOGIN_ATTEMPTS = 3;

    // =========================
    // Main Method
    // =========================

    public static void main(String[] args) {

        showWelcomeBanner();

        while (true) {

            System.out.println("""
                    
                    ====================================
                    1. Register User
                    2. Login User
                    3. Generate Strong Password
                    4. Exit Application
                    ====================================
                    """);

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1" -> registerUser();

                case "2" -> loginUser();

                case "3" -> generateRandomPassword();

                case "4" -> {
                    System.out.println("\nThank you for using Secure Password Manager!");
                    return;
                }

                default -> System.out.println("Invalid option!");
            }
        }
    }

    // =========================
    // Welcome Banner
    // =========================

    private static void showWelcomeBanner() {

        System.out.println("""
                
                ============================================
                     SECURE MINI PASSWORD MANAGER
                ============================================
                Securely Store & Manage Your Passwords
                ============================================
                """);
    }

    // =========================
    // User Registration
    // =========================

    private static void registerUser() {

        System.out.print("Enter username: ");

        String username = scanner.nextLine().trim();

        if (username.isEmpty()) {
            System.out.println("Username cannot be empty!");
            return;
        }

        if (registeredUsers.containsKey(username)) {
            System.out.println("Username already exists!");
            return;
        }

        System.out.print("Enter password: ");

        String password = scanner.nextLine();

        showPasswordStrength(password);

        if (!isStrongPassword(password)) {

            System.out.println("""
                    
                    Password must contain:
                    • Uppercase letter
                    • Lowercase letter
                    • Digit
                    • Minimum 8 characters
                    """);

            return;
        }

        String salt = generateSalt();

        String hashedPassword = hashPassword(password, salt);

        User user = new User(hashedPassword, salt);

        registeredUsers.put(username, user);

        System.out.println("\n[SUCCESS] User registered successfully!");
    }

    // =========================
    // User Login
    // =========================

    private static void loginUser() {

        System.out.print("Enter username: ");

        String username = scanner.nextLine().trim();

        User user = registeredUsers.get(username);

        if (user == null) {
            System.out.println("User not found!");
            return;
        }

        int attempts = 0;

        while (attempts < MAX_LOGIN_ATTEMPTS) {

            System.out.print("Enter password: ");

            String password = scanner.nextLine();

            String hashedInputPassword =
                    hashPassword(password, user.getSalt());

            if (hashedInputPassword.equals(user.getHashedPassword())) {

                System.out.println("\nPassword correct!");

                if (verifyTwoFactorAuthentication()) {

                    System.out.println("""
                            
                            ====================================
                            LOGIN SUCCESSFUL
                            ====================================
                            """);

                    credentialMenu(user);

                } else {
                    System.out.println("2FA verification failed!");
                }

                return;
            }

            attempts++;

            System.out.println(
                    "Incorrect password! Attempts left: "
                            + (MAX_LOGIN_ATTEMPTS - attempts)
            );
        }

        System.out.println("Maximum login attempts reached.");
    }

    // =========================
    // Credential Menu
    // =========================

    private static void credentialMenu(User user) {

        while (true) {

            System.out.println("""
                    
                    ====================================
                    1. Add Credential
                    2. View Credentials
                    3. Search Credential
                    4. Delete Credential
                    5. Generate Strong Password
                    6. Credential Statistics
                    7. Logout
                    ====================================
                    """);

            System.out.print("Choose an option: ");

            String choice = scanner.nextLine();

            switch (choice) {

                case "1" -> addCredential(user);

                case "2" -> viewCredentials(user);

                case "3" -> searchCredential(user);

                case "4" -> deleteCredential(user);

                case "5" -> generateRandomPassword();

                case "6" -> showCredentialStatistics(user);

                case "7" -> {
                    System.out.println("\nLogging out...");
                    return;
                }

                default -> System.out.println("Invalid option!");
            }
        }
    }

    // =========================
    // Add Credential
    // =========================

    private static void addCredential(User user) {

        System.out.print("Enter account name: ");

        String accountName = scanner.nextLine().trim();

        if (accountName.isEmpty()) {
            System.out.println("Account name cannot be empty!");
            return;
        }

        if (user.getCredentials().containsKey(accountName)) {
            System.out.println("Credential already exists!");
            return;
        }

        System.out.print("Enter account password: ");

        String accountPassword = scanner.nextLine();

        String salt = generateSalt();

        String hashedPassword =
                hashPassword(accountPassword, salt);

        Credential credential =
                new Credential(hashedPassword, salt);

        user.getCredentials().put(accountName, credential);

        System.out.println("[SUCCESS] Credential added successfully!");
    }

    // =========================
    // View Credentials
    // =========================

    private static void viewCredentials(User user) {

        Map<String, Credential> credentials =
                user.getCredentials();

        if (credentials.isEmpty()) {
            System.out.println("No credentials stored.");
            return;
        }

        System.out.println("""
                
                ====================================
                     STORED CREDENTIALS
                ====================================
                """);

        for (Map.Entry<String, Credential> entry
                : credentials.entrySet()) {

            String hiddenPassword =
                    "*".repeat(10);

            System.out.println(
                    "Account: " + entry.getKey()
                            + " | Password: "
                            + hiddenPassword
            );
        }
    }

    // =========================
    // Search Credential
    // =========================

    private static void searchCredential(User user) {

        System.out.print("Enter account name to search: ");

        String accountName = scanner.nextLine().trim();

        Credential credential =
                user.getCredentials().get(accountName);

        if (credential == null) {
            System.out.println("Credential not found!");
            return;
        }

        System.out.println("""
                
                ====================================
                     CREDENTIAL FOUND
                ====================================
                """);

        System.out.println("Account Exists Successfully!");
    }

    // =========================
    // Delete Credential
    // =========================

    private static void deleteCredential(User user) {

        System.out.print("Enter account name to delete: ");

        String accountName = scanner.nextLine().trim();

        Credential removedCredential =
                user.getCredentials().remove(accountName);

        if (removedCredential == null) {
            System.out.println("Credential not found!");
            return;
        }

        System.out.println("[SUCCESS] Credential deleted successfully!");
    }

    // =========================
    // Credential Statistics
    // =========================

    private static void showCredentialStatistics(User user) {

        int totalCredentials =
                user.getCredentials().size();

        System.out.println("""
                
                ====================================
                     CREDENTIAL STATISTICS
                ====================================
                """);

        System.out.println(
                "Total Credentials Stored: "
                        + totalCredentials
        );
    }

    // =========================
    // Password Generator
    // =========================

    private static void generateRandomPassword() {

        String characters =
                "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                        + "abcdefghijklmnopqrstuvwxyz"
                        + "0123456789"
                        + "@#$%&*!";

        StringBuilder generatedPassword =
                new StringBuilder();

        for (int index = 0; index < 12; index++) {

            int randomIndex =
                    secureRandom.nextInt(characters.length());

            generatedPassword.append(
                    characters.charAt(randomIndex)
            );
        }

        System.out.println("""
                
                ====================================
                     GENERATED PASSWORD
                ====================================
                """);

        System.out.println(generatedPassword);
    }

    // =========================
    // Password Strength Checker
    // =========================

    private static void showPasswordStrength(String password) {

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;

        for (char character : password.toCharArray()) {

            if (Character.isUpperCase(character)) {
                hasUppercase = true;
            }

            else if (Character.isLowerCase(character)) {
                hasLowercase = true;
            }

            else if (Character.isDigit(character)) {
                hasDigit = true;
            }
        }

        int score = 0;

        if (hasUppercase) score++;
        if (hasLowercase) score++;
        if (hasDigit) score++;
        if (password.length() >= 8) score++;

        if (score <= 2) {
            System.out.println("Password Strength: WEAK");
        }

        else if (score == 3) {
            System.out.println("Password Strength: MEDIUM");
        }

        else {
            System.out.println("Password Strength: STRONG");
        }
    }

    private static boolean isStrongPassword(String password) {

        boolean hasUppercase = false;
        boolean hasLowercase = false;
        boolean hasDigit = false;

        for (char character : password.toCharArray()) {

            if (Character.isUpperCase(character)) {
                hasUppercase = true;
            }

            else if (Character.isLowerCase(character)) {
                hasLowercase = true;
            }

            else if (Character.isDigit(character)) {
                hasDigit = true;
            }
        }

        return password.length() >= 8
                && hasUppercase
                && hasLowercase
                && hasDigit;
    }

    // =========================
    // Salt Generation
    // =========================

    private static String generateSalt() {

        byte[] salt = new byte[16];

        secureRandom.nextBytes(salt);

        return Base64
                .getEncoder()
                .encodeToString(salt);
    }

    // =========================
    // Password Hashing
    // =========================

    private static String hashPassword(
            String password,
            String salt
    ) {

        try {

            MessageDigest messageDigest =
                    MessageDigest.getInstance("SHA-256");

            messageDigest.update(salt.getBytes());

            byte[] hashedPassword =
                    messageDigest.digest(password.getBytes());

            return Base64
                    .getEncoder()
                    .encodeToString(hashedPassword);

        } catch (Exception exception) {

            throw new RuntimeException(
                    "Error hashing password",
                    exception
            );
        }
    }

    // =========================
    // 2FA Verification
    // =========================

    private static boolean verifyTwoFactorAuthentication() {

        int generatedCode =
                100000 + secureRandom.nextInt(900000);

        System.out.println(
                "\nYour 2FA Code: "
                        + generatedCode
        );

        System.out.print("Enter 2FA code: ");

        String userInput = scanner.nextLine();

        return userInput.equals(
                String.valueOf(generatedCode)
        );
    }

    // =========================
    // User Class
    // =========================

    static class User {

        private final String hashedPassword;

        private final String salt;

        private final Map<String, Credential> credentials =
                new HashMap<>();

        public User(String hashedPassword, String salt) {

            this.hashedPassword = hashedPassword;
            this.salt = salt;
        }

        public String getHashedPassword() {
            return hashedPassword;
        }

        public String getSalt() {
            return salt;
        }

        public Map<String, Credential> getCredentials() {
            return credentials;
        }
    }

    // =========================
    // Credential Class
    // =========================

    static class Credential {

        private final String hashedPassword;

        private final String salt;

        public Credential(
                String hashedPassword,
                String salt
        ) {

            this.hashedPassword = hashedPassword;
            this.salt = salt;
        }

        public String getHashedPassword() {
            return hashedPassword;
        }

        public String getSalt() {
            return salt;
        }
    }
}