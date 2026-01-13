package com.library.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.ReaderDAO;
import com.toedter.calendar.JDateChooser;

/**
 * Statistics Panel with Charts and Summary Cards
 */
public class StatisticsPanel extends JPanel {
    
    private BookDAO bookDAO;
    private ReaderDAO readerDAO;
    private BorrowDAO borrowDAO;
    
    private JDateChooser dateFrom;
    private JDateChooser dateTo;
    private JButton btnFilter;
    private JPanel cardsPanel;
    private JPanel chartsPanel;
    
    public StatisticsPanel() {
        this.bookDAO = new BookDAO();
        this.readerDAO = new ReaderDAO();
        this.borrowDAO = new BorrowDAO();
        
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        initComponents();
    }
    
    private void initComponents() {
        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel lblTitle = new JLabel("📈 THỐNG KÊ HỆ THỐNG");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(0, 150, 136));
        titlePanel.add(lblTitle, BorderLayout.WEST);
        
        // Date Filter Panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        filterPanel.setBackground(Color.WHITE);
        
        filterPanel.add(new JLabel("Từ ngày:"));
        dateFrom = new JDateChooser();
        dateFrom.setDateFormatString("dd/MM/yyyy");
        dateFrom.setDate(java.sql.Date.valueOf(LocalDate.now().minusMonths(1)));
        filterPanel.add(dateFrom);
        
        filterPanel.add(new JLabel("Đến ngày:"));
        dateTo = new JDateChooser();
        dateTo.setDateFormatString("dd/MM/yyyy");
        dateTo.setDate(java.sql.Date.valueOf(LocalDate.now()));
        filterPanel.add(dateTo);
        
        btnFilter = new JButton("🔍 Lọc");
        btnFilter.setBackground(new Color(33, 150, 243));
        btnFilter.setForeground(Color.WHITE);
        btnFilter.setFocusPainted(false);
        btnFilter.addActionListener(e -> loadStatistics());
        filterPanel.add(btnFilter);
        
        titlePanel.add(filterPanel, BorderLayout.EAST);
        
        // Statistics Cards
        cardsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Charts Panel
        chartsPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        chartsPanel.setBackground(Color.WHITE);
        chartsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        // Load initial statistics
        loadStatistics();
        
        // Main layout
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(cardsPanel, BorderLayout.NORTH);
        centerPanel.add(chartsPanel, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void loadStatistics() {
        cardsPanel.removeAll();
        chartsPanel.removeAll();
        
        try {
            int totalBooks = bookDAO.getAllBooks().size();
            int availableBooks = bookDAO.getAvailableBooks().size();
            int activeReaders = readerDAO.getActiveReaders().size();
            int overdueTickets = borrowDAO.getOverdueBorrowTickets().size();
            int allReaders = readerDAO.getAllReaders().size();
            
            cardsPanel.add(createStatCard("📚 Tổng Số Sách", String.valueOf(totalBooks), new Color(33, 150, 243)));
            cardsPanel.add(createStatCard("✅ Sách Còn", String.valueOf(availableBooks), new Color(76, 175, 80)));
            cardsPanel.add(createStatCard("👥 ĐG Hoạt Động", String.valueOf(activeReaders), new Color(255, 152, 0)));
            cardsPanel.add(createStatCard("⚠️ Phiếu Quá Hạn", String.valueOf(overdueTickets), new Color(244, 67, 54)));
            cardsPanel.add(createStatCard("📖 Tổng Độc Giả", String.valueOf(allReaders), new Color(156, 39, 176)));
            cardsPanel.add(createStatCard("💰 Tổng Phạt", "0 VNĐ", new Color(0, 150, 136)));
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải thống kê: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
        
        // Add chart panels
        chartsPanel.add(createBorrowStatisticsChart());
        chartsPanel.add(createTopBooksChart());
        
        cardsPanel.revalidate();
        cardsPanel.repaint();
        chartsPanel.revalidate();
        chartsPanel.repaint();
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
    
    private JPanel createBorrowStatisticsChart() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitle = new JLabel("📊 Thống Kê Mượn Sách Theo Thời Gian");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(new Color(33, 150, 243));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Simple bar chart representation
        JPanel chartArea = new JPanel(new GridLayout(1, 5, 10, 10));
        chartArea.setBackground(Color.WHITE);
        
        String[] months = {"Tháng 1", "Tháng 2", "Tháng 3", "Tháng 4", "Tháng 5"};
        int[] values = {25, 40, 35, 50, 45}; // Sample data
        
        for (int i = 0; i < months.length; i++) {
            chartArea.add(createBarChart(months[i], values[i]));
        }
        
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(chartArea, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createTopBooksChart() {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(76, 175, 80), 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitle = new JLabel("📚 Top 5 Sách Được Mượn Nhiều Nhất");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(new Color(76, 175, 80));
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        // Create a simple list view for top books
        JPanel listPanel = new JPanel(new GridLayout(5, 1, 5, 5));
        listPanel.setBackground(Color.WHITE);
        
        String[] books = {"Lập trình Java", "Cơ sở dữ liệu", "Mạng máy tính", "Thuật toán", "Kỹ thuật phần mềm"};
        int[] counts = {45, 38, 35, 30, 28};
        
        for (int i = 0; i < books.length; i++) {
            listPanel.add(createBookStatItem(i + 1, books[i], counts[i]));
        }
        
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(listPanel, BorderLayout.CENTER);
        
        return panel;
    }
    
    private JPanel createBarChart(String label, int value) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        
        JPanel barPanel = new JPanel();
        barPanel.setLayout(new BoxLayout(barPanel, BoxLayout.Y_AXIS));
        barPanel.setBackground(Color.WHITE);
        
        // Create bar with height proportional to value
        JPanel bar = new JPanel();
        bar.setBackground(new Color(33, 150, 243));
        bar.setPreferredSize(new java.awt.Dimension(40, value * 2));
        bar.setBorder(BorderFactory.createLineBorder(new Color(33, 150, 243).darker(), 1));
        
        JLabel lblValue = new JLabel(String.valueOf(value), SwingConstants.CENTER);
        lblValue.setFont(new Font("Segoe UI", Font.BOLD, 12));
        
        JLabel lblLabel = new JLabel(label, SwingConstants.CENTER);
        lblLabel.setFont(new Font("Segoe UI", Font.PLAIN, 10));
        
        barPanel.add(javax.swing.Box.createVerticalGlue());
        barPanel.add(bar);
        
        panel.add(lblValue, BorderLayout.NORTH);
        panel.add(barPanel, BorderLayout.CENTER);
        panel.add(lblLabel, BorderLayout.SOUTH);
        
        return panel;
    }
    
    private JPanel createBookStatItem(int rank, String bookName, int count) {
        JPanel panel = new JPanel(new BorderLayout(10, 5));
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(Color.LIGHT_GRAY, 1),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        JLabel lblRank = new JLabel("#" + rank);
        lblRank.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblRank.setForeground(new Color(76, 175, 80));
        
        JLabel lblBook = new JLabel(bookName);
        lblBook.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JLabel lblCount = new JLabel(count + " lượt");
        lblCount.setFont(new Font("Segoe UI", Font.BOLD, 12));
        lblCount.setForeground(new Color(33, 150, 243));
        
        panel.add(lblRank, BorderLayout.WEST);
        panel.add(lblBook, BorderLayout.CENTER);
        panel.add(lblCount, BorderLayout.EAST);
        
        return panel;
    }
    
    private JPanel createChartPlaceholder(String title, Color color) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(color, 2),
            BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));
        
        JLabel lblTitle = new JLabel(title);
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTitle.setForeground(color);
        lblTitle.setHorizontalAlignment(SwingConstants.CENTER);
        
        JLabel lblChart = new JLabel("📊 Biểu đồ sẽ được hiển thị ở đây");
        lblChart.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblChart.setHorizontalAlignment(SwingConstants.CENTER);
        lblChart.setForeground(Color.GRAY);
        
        panel.add(lblTitle, BorderLayout.NORTH);
        panel.add(lblChart, BorderLayout.CENTER);
        
        return panel;
    }
}
