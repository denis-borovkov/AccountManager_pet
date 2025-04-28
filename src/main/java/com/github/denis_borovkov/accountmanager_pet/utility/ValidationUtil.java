package com.github.denis_borovkov.accountmanager_pet.utility;

import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ValidationUtil {

    public static boolean isValidUsername(String username) {
        if (username == null || username.length() < 3 || username.length() > 20) {
            return false;
        }
        return !Pattern.matches("^[A-Za-z][A-Za-z0-9_]*$", username);
    }

    public static boolean isValidPassword(String password, String username) {
        if (password == null || password.length() < 8 || password.equals(username)) {
            return false;
        }
        return !Pattern.matches("^(?=.*[A-Z])(?=.*\\d)(?=.*[@#$%^&+=!]).{8,}$", password);
    }

    public static boolean isValidEmail(String email) {
        if (email == null) {
            return false;
        }
        return !Pattern.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$", email);
    }
}
