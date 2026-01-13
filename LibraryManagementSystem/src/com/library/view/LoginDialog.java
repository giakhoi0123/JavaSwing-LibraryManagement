package com.library.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Frame;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;

import com.library.dao.StaffDAO;
import com.library.model.Staff;

/**
 * Login Dialog
 * Authenticates staff members before accessing the system
 * 
 * @author Library Management System
 * @version 1.0
 */
public class LoginDialog extends JDialog {
    
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JButton btnLogin;
    private JButton btnCancel;
    private JLabel lblStatus;
    
    private StaffDAO staffDAO;
    private Staff currentStaff;
    
    public LoginDialog(Frame parent) {
        super(parent, "Đăng Nhập - Library Management System", true);
        staffDAO = new StaffDAO();
        
        // Set favicon
        try {
            ImageIcon icon = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
            setIconImage(icon.getImage());
        } catch (Exception e) {
            // Icon not found
        }
        
        initComponents();
        setupLayout();
        attachListeners();
        
        setSize(450, 350);
        setLocationRelativeTo(parent);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
    }
    
    private void initComponents() {
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        btnLogin = new JButton("Đăng Nhập");
        btnCancel = new JButton("Thoát");
        lblStatus = new JLabel(" ");
        
        // Set button colors
        btnLogin.setBackground(new Color(46, 125, 50));
        btnLogin.setForeground(Color.WHITE);
        btnLogin.setFocusPainted(false);
        
        btnCancel.setBackground(new Color(211, 47, 47));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        
        lblStatus.setForeground(Color.RED);
        lblStatus.setHorizontalAlignment(SwingConstants.CENTER);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Header Panel
        JPanel headerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        headerPanel.setBackground(new Color(33, 150, 243));
        headerPanel.setPreferredSize(new Dimension(450, 100));
        
        // Logo
        try {
            ImageIcon originalIcon = new ImageIcon(getClass().getClassLoader().getResource("logo.png"));
            Image scaledImage = originalIcon.getImage().getScaledInstance(60, 60, Image.SCALE_SMOOTH);
            JLabel logoLabel = new JLabel(new ImageIcon(scaledImage));
            headerPanel.add(logoLabel);
        } catch (Exception e) {
            // Fallback to emoji if logo not found
        }
        
        JLabel lblTitle = new JLabel("LIBRARY MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblTitle.setForeground(Color.WHITE);
        headerPanel.add(lblTitle);
        
        // Center Panel (Login Form)
        JPanel centerPanel = new JPanel(new GridBagLayout());
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Username
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.3;
        centerPanel.add(new JLabel("Tên đăng nhập:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        centerPanel.add(txtUsername, gbc);
        
        // Password
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.3;
        centerPanel.add(new JLabel("Mật khẩu:"), gbc);
        
        gbc.gridx = 1;
        gbc.weightx = 0.7;
        centerPanel.add(txtPassword, gbc);
        
        // Status Label
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2;
        centerPanel.add(lblStatus, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        buttonPanel.add(btnLogin);
        buttonPanel.add(btnCancel);
        
        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 2;
        centerPanel.add(buttonPanel, gbc);
        
        // Footer Panel
        JPanel footerPanel = new JPanel();
        footerPanel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        JLabel lblFooter = new JLabel("© 2025 Library Management System");
        lblFooter.setFont(new Font("Segoe UI", Font.ITALIC, 10));
        lblFooter.setForeground(Color.GRAY);
        footerPanel.add(lblFooter);
        
        // Add panels to dialog
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(footerPanel, BorderLayout.SOUTH);
    }
    
    private void attachListeners() {
        // Login button action
        btnLogin.addActionListener(e -> handleLogin());
        
        // Cancel button action
        btnCancel.addActionListener(e -> {
            dispose();
            System.exit(0);
        });
        
        // Enter key press
        txtPassword.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    handleLogin();
                }
            }
        });
        
        txtUsername.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    txtPassword.requestFocus();
                }
            }
        });
    }
    
    private void handleLogin() {
        String username = txtUsername.getText().trim();
        String password = new String(txtPassword.getPassword());
        
        // Validate input
        if (username.isEmpty()) {
            lblStatus.setText("Vui lòng nhập tên đăng nhập!");
            txtUsername.requestFocus();
            return;
        }
        
        if (password.isEmpty()) {
            lblStatus.setText("Vui lòng nhập mật khẩu!");
            txtPassword.requestFocus();
            return;
        }
        
        // Disable button during authentication
        btnLogin.setEnabled(false);
        lblStatus.setText("Đang xác thực...");
        lblStatus.setForeground(Color.BLUE);
        
        // Authenticate in background thread
        SwingWorker<Staff, Void> worker = new SwingWorker<>() {
            @Override
            protected Staff doInBackground() throws Exception {
                return staffDAO.authenticate(username, password);
            }
            
            @Override
            protected void done() {
                try {
                    currentStaff = get();
                    
                    if (currentStaff != null) {
                        // Login successful
                        lblStatus.setText("Đăng nhập thành công!");
                        lblStatus.setForeground(new Color(46, 125, 50));
                        
                        // Open main frame
                        SwingUtilities.invokeLater(() -> {
                            MainFrame mainFrame = new MainFrame(currentStaff);
                            mainFrame.setVisible(true);
                            dispose();
                        });
                        
                    } else {
                        // Login failed
                        lblStatus.setText("Sai tên đăng nhập hoặc mật khẩu!");
                        lblStatus.setForeground(Color.RED);
                        txtPassword.setText("");
                        txtPassword.requestFocus();
                        btnLogin.setEnabled(true);
                    }
                    
                } catch (Exception e) {
                    lblStatus.setText("Lỗi kết nối: " + e.getMessage());
                    lblStatus.setForeground(Color.RED);
                    btnLogin.setEnabled(true);
                    e.printStackTrace();
                }
            }
        };
        
        worker.execute();
    }
    
    public Staff getCurrentStaff() {
        return currentStaff;
    }
}
