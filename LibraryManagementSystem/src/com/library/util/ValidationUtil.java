package com.library.util;

import java.time.LocalDate;
import java.util.regex.Pattern;

/**
 * Validation Utility Class for input validation
 * 
 * @author Library Management System
 * @version 1.0
 */
public class ValidationUtil {
    
    // Regex patterns
    private static final Pattern PHONE_PATTERN = Pattern.compile("^(0[3|5|7|8|9])[0-9]{8}$");
    private static final Pattern EMAIL_PATTERN = Pattern.compile("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$");
    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-zA-Z0-9_]{5,20}$");
    
    /**
     * Validate phone number (Vietnamese format)
     * @param phone Phone number
     * @return true if valid
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null || phone.isEmpty()) return false;
        return PHONE_PATTERN.matcher(phone).matches();
    }
    
    /**
     * Alias for isValidPhone for compatibility
     */
    public static boolean isValidPhoneNumber(String phone) {
        return isValidPhone(phone);
    }
    
    /**
     * Validate email address
     * @param email Email address
     * @return true if valid
     */
    public static boolean isValidEmail(String email) {
        if (email == null || email.isEmpty()) return false;
        return EMAIL_PATTERN.matcher(email).matches();
    }
    
    /**
     * Validate username
     * @param username Username (5-20 characters, alphanumeric and underscore)
     * @return true if valid
     */
    public static boolean isValidUsername(String username) {
        if (username == null || username.isEmpty()) return false;
        return USERNAME_PATTERN.matcher(username).matches();
    }
    
    /**
     * Validate password strength
     * @param password Password (minimum 6 characters)
     * @return true if valid
     */
    public static boolean isValidPassword(String password) {
        if (password == null || password.isEmpty()) return false;
        return password.length() >= 6;
    }
    
    /**
     * Validate if string is not empty
     * @param str String to check
     * @return true if not empty
     */
    public static boolean isNotEmpty(String str) {
        return str != null && !str.trim().isEmpty();
    }
    
    /**
     * Validate if number is positive
     * @param number Number to check
     * @return true if positive
     */
    public static boolean isPositive(int number) {
        return number > 0;
    }
    
    /**
     * Validate if number is positive
     * @param number Number to check
     * @return true if positive
     */
    public static boolean isPositive(double number) {
        return number > 0;
    }
    
    /**
     * Validate if return date is after borrow date
     * @param borrowDate Borrow date
     * @param returnDate Return date
     * @return true if valid
     */
    public static boolean isValidReturnDate(LocalDate borrowDate, LocalDate returnDate) {
        if (borrowDate == null || returnDate == null) return false;
        return returnDate.isAfter(borrowDate);
    }
    
    /**
     * Validate if expiry date is after issue date
     * @param issueDate Issue date
     * @param expiryDate Expiry date
     * @return true if valid
     */
    public static boolean isValidExpiryDate(LocalDate issueDate, LocalDate expiryDate) {
        if (issueDate == null || expiryDate == null) return false;
        return expiryDate.isAfter(issueDate);
    }
    
    /**
     * Sanitize string input (remove leading/trailing spaces)
     * @param input Input string
     * @return Sanitized string
     */
    public static String sanitize(String input) {
        if (input == null) return "";
        return input.trim();
    }
    
    /**
     * Validate numeric string
     * @param str String to check
     * @return true if numeric
     */
    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    /**
     * Validate integer string
     * @param str String to check
     * @return true if integer
     */
    public static boolean isInteger(String str) {
        if (str == null || str.isEmpty()) return false;
        try {
            Integer.parseInt(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
