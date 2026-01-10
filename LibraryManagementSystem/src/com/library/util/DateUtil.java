package com.library.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Date;

/**
 * Date Utility Class for date formatting and calculations
 * 
 * @author Library Management System
 * @version 1.0
 */
public class DateUtil {
    
    private static final String DEFAULT_DATE_FORMAT = "dd/MM/yyyy";
    private static final String SQL_DATE_FORMAT = "yyyy-MM-dd";
    private static final SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_DATE_FORMAT);
    private static final SimpleDateFormat sqlSdf = new SimpleDateFormat(SQL_DATE_FORMAT);
    
    /**
     * Format Date to String (dd/MM/yyyy)
     * @param date Date object
     * @return Formatted string
     */
    public static String formatDate(Date date) {
        if (date == null) return "";
        return sdf.format(date);
    }
    
    /**
     * Format LocalDate to String (dd/MM/yyyy)
     * @param date LocalDate object
     * @return Formatted string
     */
    public static String formatDate(LocalDate date) {
        if (date == null) return "";
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return date.format(formatter);
    }
    
    /**
     * Parse String to Date (dd/MM/yyyy)
     * @param dateStr Date string
     * @return Date object
     * @throws ParseException
     */
    public static Date parseDate(String dateStr) throws ParseException {
        if (dateStr == null || dateStr.isEmpty()) return null;
        return sdf.parse(dateStr);
    }
    
    /**
     * Parse String to LocalDate (dd/MM/yyyy)
     * @param dateStr Date string
     * @return LocalDate object
     */
    public static LocalDate parseLocalDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return null;
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(DEFAULT_DATE_FORMAT);
        return LocalDate.parse(dateStr, formatter);
    }
    
    /**
     * Convert Date to SQL Date format (yyyy-MM-dd)
     * @param date Date object
     * @return SQL formatted string
     */
    public static String toSqlDate(Date date) {
        if (date == null) return null;
        return sqlSdf.format(date);
    }
    
    /**
     * Convert LocalDate to java.sql.Date
     * @param localDate LocalDate object
     * @return java.sql.Date
     */
    public static java.sql.Date toSqlDate(LocalDate localDate) {
        if (localDate == null) return null;
        return java.sql.Date.valueOf(localDate);
    }
    
    /**
     * Convert java.sql.Date to LocalDate
     * @param sqlDate java.sql.Date object
     * @return LocalDate
     */
    public static LocalDate toLocalDate(java.sql.Date sqlDate) {
        if (sqlDate == null) return null;
        return sqlDate.toLocalDate();
    }
    
    /**
     * Convert Date to LocalDate
     * @param date Date object
     * @return LocalDate
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) return null;
        return date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
    }
    
    /**
     * Get current date as LocalDate
     * @return Current LocalDate
     */
    public static LocalDate getCurrentDate() {
        return LocalDate.now();
    }
    
    /**
     * Get current date as java.sql.Date
     * @return Current java.sql.Date
     */
    public static java.sql.Date getCurrentSqlDate() {
        return java.sql.Date.valueOf(LocalDate.now());
    }
    
    /**
     * Calculate days between two dates
     * @param fromDate Start date
     * @param toDate End date
     * @return Number of days
     */
    public static long daysBetween(LocalDate fromDate, LocalDate toDate) {
        if (fromDate == null || toDate == null) return 0;
        return ChronoUnit.DAYS.between(fromDate, toDate);
    }
    
    /**
     * Calculate days between two dates
     * @param fromDate Start date
     * @param toDate End date
     * @return Number of days
     */
    public static long daysBetween(Date fromDate, Date toDate) {
        if (fromDate == null || toDate == null) return 0;
        LocalDate from = toLocalDate(fromDate);
        LocalDate to = toLocalDate(toDate);
        return ChronoUnit.DAYS.between(from, to);
    }
    
    /**
     * Add days to a date
     * @param date Base date
     * @param days Number of days to add
     * @return New LocalDate
     */
    public static LocalDate addDays(LocalDate date, int days) {
        if (date == null) return null;
        return date.plusDays(days);
    }
    
    /**
     * Check if date is after another date
     * @param date1 First date
     * @param date2 Second date
     * @return true if date1 is after date2
     */
    public static boolean isAfter(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) return false;
        return date1.isAfter(date2);
    }
    
    /**
     * Check if date is before another date
     * @param date1 First date
     * @param date2 Second date
     * @return true if date1 is before date2
     */
    public static boolean isBefore(LocalDate date1, LocalDate date2) {
        if (date1 == null || date2 == null) return false;
        return date1.isBefore(date2);
    }
    
    /**
     * Validate date string format (dd/MM/yyyy)
     * @param dateStr Date string
     * @return true if valid format
     */
    public static boolean isValidDateFormat(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) return false;
        try {
            sdf.parse(dateStr);
            return true;
        } catch (ParseException e) {
            return false;
        }
    }
    
    /**
     * Calculate fine amount for late return (5000 VND per day)
     * @param dueDate Due date
     * @param returnDate Actual return date
     * @return Fine amount
     */
    public static double calculateLateFine(LocalDate dueDate, LocalDate returnDate) {
        if (dueDate == null || returnDate == null) return 0.0;
        
        long daysLate = daysBetween(dueDate, returnDate);
        if (daysLate <= 0) return 0.0;
        
        return daysLate * 5000.0; // 5000 VND per day
    }
}
