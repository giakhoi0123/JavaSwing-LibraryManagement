package com.library.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.sql.SQLException;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.dao.ReaderDAO;

/**
 * Statistics Panel with Charts and Summary Cards
 */
public class StatisticsPanel extends JPanel {
    
    private BookDAO bookDAO;
    private ReaderDAO readerDAO;
    private BorrowDAO borrowDAO;
    
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
        
        // Statistics Cards
        JPanel cardsPanel = new JPanel(new GridLayout(2, 3, 15, 15));
        cardsPanel.setBackground(Color.WHITE);
        cardsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
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
        
        // Charts Panel (placeholder for now)
        JPanel chartsPanel = new JPanel(new GridLayout(1, 2, 15, 15));
        chartsPanel.setBackground(Color.WHITE);
        chartsPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        
        chartsPanel.add(createChartPlaceholder("Thống Kê Mượn Sách Theo Tháng", new Color(33, 150, 243)));
        chartsPanel.add(createChartPlaceholder("Top 10 Sách Được Mượn Nhiều Nhất", new Color(76, 175, 80)));
        
        // Main layout
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(cardsPanel, BorderLayout.NORTH);
        centerPanel.add(chartsPanel, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
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
