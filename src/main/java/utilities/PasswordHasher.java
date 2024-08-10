package utilities;

import org.mindrot.jbcrypt.BCrypt;

/**
 * The PasswordHasher class provides utility methods for hashing passwords
 * and verifying hashed passwords using the BCrypt hashing algorithm.
 */
public class PasswordHasher {

    /**
     * Hashes a plain text password using the BCrypt hashing algorithm.
     *
     * @param plainTextPassword The plain text password to hash.
     * @return The hashed password as a String.
     */
    public static String hashPassword(String plainTextPassword) {
        return BCrypt.hashpw(plainTextPassword, BCrypt.gensalt());
    }

    /**
     * Verifies that a plain text password matches a hashed password.
     *
     * @param plainTextPassword The plain text password to verify.
     * @param hashedPassword The hashed password to compare against.
     * @return true if the plain text password matches the hashed password; false otherwise.
     */
    public static boolean checkPassword(String plainTextPassword, String hashedPassword) {
        return BCrypt.checkpw(plainTextPassword, hashedPassword);
    }
}
