/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package test;

import org.mindrot.jbcrypt.BCrypt;
import java.sql.*;

public class PasswordVerification {

    public static boolean checkPassword(String plainPassword, String storedHash) {
        return BCrypt.checkpw(plainPassword, storedHash);
    }

    public static void main(String[] args) {
        // Example password and stored hash
        String plainPassword = "myPassword123";

        // Simulate retrieval of hashed password from the database
        String storedHash = "$2a$10$8xzn8rJqrNowwL9DsJEWu.qqHCGHsUNLFmzYNZlCeBKCK46mQVXPG";

        boolean isPasswordCorrect = checkPassword(plainPassword, storedHash);

        if (isPasswordCorrect) {
            System.out.println("Password is correct!");
        } else {
            System.out.println("Password is incorrect!");
        }
    }

    private static String retrieveHashedPasswordFromDatabase() {
        String hashedPassword = "9gQHR5/n7I1Vh8pt3xT/b61R48usCKt6nDh3OVQ/xRM=";



        return hashedPassword;
    }
}
