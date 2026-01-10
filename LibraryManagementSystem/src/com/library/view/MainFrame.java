package com.library.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.library.dao.*;
import com.library.model.Staff;

import java.sql.SQLException;

/**
 * Main Application Frame
 * Contains sidebar menu and main content panel
 * 
 * @author Library Management System
 * @version 1.0
 */
public class MainFrame extends JFrame {
    
    private Staff currentStaff;
    private JPanel contentPanel;
    private JLabel lblWelcome;
    
    // Menu buttons
    private JButton btnDashboard;
    private JButton btnBooks;
    private JButton btnReaders;
    private JButton btnBorrow;
    private JButton btnFines;
    private JButton btnStatistics;
    private JButton btnStaff;
    private JButton btnLogout;
    
    public MainFrame(Staff staff) {
        this.currentStaff = staff;
        
        setTitle("Library Management System - " + staff.getHoTen());
        setSize(1200, 700);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        
        initComponents();
        setupLayout();
        attachListeners();
        
        // Show dashboard by default
        showDashboard();
    }
    
    private void initComponents() {
        // Sidebar menu buttons
        btnDashboard = createMenuButton("📊 Trang Chủ", new Color(33, 150, 243));
        btnBooks = createMenuButton("📚 Quản Lý Sách", new Color(76, 175, 80));
        btnReaders = createMenuButton("👥 Quản Lý Độc Giả", new Color(255, 152, 0));
        btnBorrow = createMenuButton("📖 Mượn/Trả Sách", new Color(156, 39, 176));
        btnFines = createMenuButton("💰 Quản Lý Phạt", new Color(220, 53, 69));
        btnStatistics = createMenuButton("📈 Thống Kê", new Color(0, 150, 136));
        btnStaff = createMenuButton("👨‍💼 Quản Lý Nhân Viên", new Color(96, 125, 139));
        btnLogout = createMenuButton("🚪 Đăng Xuất", new Color(244, 67, 54));
        
        // Check permissions
        if (!currentStaff.isAdmin()) {
            btnBooks.setEnabled(false);
            btnStaff.setEnabled(false);
        }
        
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
    }
    
    private JButton createMenuButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setPreferredSize(new Dimension(200, 45));
        button.setMaximumSize(new Dimension(200, 45));
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        
        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(color.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(color);
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout());
        
        // Top Panel (Header)
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(new Color(33, 150, 243));
        topPanel.setPreferredSize(new Dimension(1200, 60));
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20));
        
        JLabel lblTitle = new JLabel("LIBRARY MANAGEMENT SYSTEM");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        
        lblWelcome = new JLabel("Xin chào, " + currentStaff.getHoTen() + " (" + currentStaff.getRole() + ")");
        lblWelcome.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        lblWelcome.setForeground(Color.WHITE);
        
        topPanel.add(lblTitle, BorderLayout.WEST);
        topPanel.add(lblWelcome, BorderLayout.EAST);
        
        // Sidebar Panel (Menu)
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(37, 37, 38));
        sidebarPanel.setPreferredSize(new Dimension(220, 640));
        sidebarPanel.setBorder(BorderFactory.createEmptyBorder(20, 10, 20, 10));
        
        sidebarPanel.add(btnDashboard);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnBooks);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnReaders);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnBorrow);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnFines);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        sidebarPanel.add(btnStatistics);
        sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        
        if (currentStaff.isAdmin()) {
            sidebarPanel.add(btnStaff);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }
        
        sidebarPanel.add(Box.createVerticalGlue());
        sidebarPanel.add(btnLogout);
        
        // Add panels to frame
        add(topPanel, BorderLayout.NORTH);
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
    
    private void attachListeners() {
        btnDashboard.addActionListener(e -> showDashboard());
        btnBooks.addActionListener(e -> showBookManagement());
        btnReaders.addActionListener(e -> showReaderManagement());
        btnBorrow.addActionListener(e -> showBorrowReturn());
        btnFines.addActionListener(e -> showFineManagement());
        btnStatistics.addActionListener(e -> showStatistics());
        btnStaff.addActionListener(e -> showStaffManagement());
        
        btnLogout.addActionListener(e -> {
            int confirm = JOptionPane.showConfirmDialog(this,
                "Bạn có chắc chắn muốn đăng xuất?",
                "Xác Nhận Đăng Xuất",
                JOptionPane.YES_NO_OPTION);
            
            if (confirm == JOptionPane.YES_OPTION) {
                dispose();
                LoginDialog loginDialog = new LoginDialog(null);
                loginDialog.setVisible(true);
            }
        });
    }
    
    private void showDashboard() {
        contentPanel.removeAll();
        
        JPanel dashboard = new JPanel(new BorderLayout());
        dashboard.setBackground(Color.WHITE);
        
        JLabel lblTitle = new JLabel("📊 THỐNG KÊ HỆ THỐNG", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitle.setForeground(new Color(0, 150, 136));
        lblTitle.setBorder(BorderFactory.createEmptyBorder(20, 0, 20, 0));
        
        // Statistics cards panel with real data
        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 20, 20));
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        try {
            BookDAO bookDAO = new BookDAO();
            ReaderDAO readerDAO = new ReaderDAO();
            BorrowDAO borrowDAO = new BorrowDAO();
            TicketFineDAO fineDAO = new TicketFineDAO();
            
            int totalBooks = bookDAO.getAllBooks().size();
            int availableBooks = bookDAO.getAvailableBooks().size();
            int activeReaders = readerDAO.getActiveReaders().size();
            int allReaders = readerDAO.getAllReaders().size();
            int currentBorrows = borrowDAO.getAllBorrowTickets().size();
            int overdueTickets = borrowDAO.getOverdueBorrowTickets().size();
            double totalFines = fineDAO.getTotalFinesAmount();
            
            cardsPanel.add(createStatCard("📚 Tổng Số Sách", String.valueOf(totalBooks), new Color(33, 150, 243)));
            cardsPanel.add(createStatCard("✅ Sách Còn", String.valueOf(availableBooks), new Color(76, 175, 80)));
            cardsPanel.add(createStatCard("👥 ĐG Hoạt Động", String.valueOf(activeReaders), new Color(255, 152, 0)));
            cardsPanel.add(createStatCard("⚠️ Phiếu Quá Hạn", String.valueOf(overdueTickets), new Color(244, 67, 54)));
            cardsPanel.add(createStatCard("📖 Tổng Độc Giả", String.valueOf(allReaders), new Color(156, 39, 176)));
            cardsPanel.add(createStatCard("💰 Tổng Phạt", String.format("%,.0f VNĐ", totalFines), new Color(0, 150, 136)));
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("Lỗi khi load thống kê: " + ex.getMessage());
            cardsPanel.add(createStatCard("📚 Tổng Số Sách", "Lỗi", new Color(33, 150, 243)));
            cardsPanel.add(createStatCard("✅ Sách Còn", "Lỗi", new Color(76, 175, 80)));
            cardsPanel.add(createStatCard("👥 ĐG Hoạt Động", "Lỗi", new Color(255, 152, 0)));
            cardsPanel.add(createStatCard("⚠️ Phiếu Quá Hạn", "Lỗi", new Color(244, 67, 54)));
            cardsPanel.add(createStatCard("📖 Tổng Độc Giả", "Lỗi", new Color(156, 39, 176)));
            cardsPanel.add(createStatCard("💰 Tổng Phạt", "Lỗi", new Color(0, 150, 136)));
            cardsPanel.add(createStatCard("💰 Tổng Phạt", "Lỗi", new Color(0, 150, 136)));
        }
        
        dashboard.add(lblTitle, BorderLayout.NORTH);
        dashboard.add(cardsPanel, BorderLayout.CENTER);
        
        contentPanel.add(dashboard);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private JPanel createStatCard(String title, String value, Color color) {
        JPanel card = new JPanel(new BorderLayout());
        card.setBackground(color);
        card.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color.darker(), 2),
            BorderFactory.createEmptyBorder(20, 20, 20, 20)
        ));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTitle.setForeground(Color.WHITE);
        
        JLabel lblValue = new JLabel(value);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 36));
        lblValue.setForeground(Color.WHITE);
        lblValue.setHorizontalAlignment(SwingConstants.CENTER);
        
        card.add(lblTitle, BorderLayout.NORTH);
        card.add(lblValue, BorderLayout.CENTER);
        
        return card;
    }
    
    private void showBookManagement() {
        contentPanel.removeAll();
        BookManagementPanel bookPanel = new BookManagementPanel(currentStaff);
        contentPanel.add(bookPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showReaderManagement() {
        contentPanel.removeAll();
        ReaderManagementPanel readerPanel = new ReaderManagementPanel(currentStaff);
        contentPanel.add(readerPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showBorrowReturn() {
        contentPanel.removeAll();
        BorrowReturnPanel borrowPanel = new BorrowReturnPanel(currentStaff);
        contentPanel.add(borrowPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showFineManagement() {
        contentPanel.removeAll();
        FineManagementPanel finePanel = new FineManagementPanel(currentStaff);
        contentPanel.add(finePanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showStatistics() {
        contentPanel.removeAll();
        StatisticsPanel statsPanel = new StatisticsPanel();
        contentPanel.add(statsPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showStaffManagement() {
        contentPanel.removeAll();
        StaffManagementPanel staffPanel = new StaffManagementPanel(currentStaff);
        contentPanel.add(staffPanel);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
