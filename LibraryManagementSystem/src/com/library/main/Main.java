package com.library.main;

import com.formdev.flatlaf.FlatLightLaf;
import com.library.view.LoginDialog;
import com.library.util.DBConnection;

import javax.swing.*;

/**
 * Main Application Entry Point
 * Initializes FlatLaf Look and Feel and starts the application
 * 
 * @author Library Management System
 * @version 1.0
 */
public class Main {
    
    public static void main(String[] args) {
        // Set FlatLaf Look and Feel
        try {
            // Use FlatLightLaf for light theme (can change to FlatDarkLaf for dark theme)
            UIManager.setLookAndFeel(new FlatLightLaf());
            
            // Optional: Customize FlatLaf properties
            UIManager.put("Button.arc", 10); // Rounded buttons
            UIManager.put("Component.arc", 10); // Rounded components
            UIManager.put("TextComponent.arc", 10); // Rounded text fields
            
            System.out.println("✓ FlatLaf Look and Feel loaded successfully");
            
        } catch (Exception e) {
            System.err.println("✗ Failed to initialize FlatLaf Look and Feel");
            e.printStackTrace();
            
            // Fallback to system look and feel
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        
        // Test database connection
        DBConnection dbConnection = DBConnection.getInstance();
        if (!dbConnection.testConnection()) {
            JOptionPane.showMessageDialog(null, 
                "Không thể kết nối đến cơ sở dữ liệu!\n" +
                "Vui lòng kiểm tra:\n" +
                "1. MySQL server đang chạy\n" +
                "2. Thông tin kết nối trong DBConnection.java\n" +
                "3. Database 'library_management' đã được tạo",
                "Lỗi Kết Nối",
                JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        
        // Start application on Event Dispatch Thread
        SwingUtilities.invokeLater(() -> {
            // Show login dialog
            LoginDialog loginDialog = new LoginDialog(null);
            loginDialog.setVisible(true);
        });
    }
}
