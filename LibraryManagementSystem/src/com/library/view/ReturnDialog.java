package com.library.view;

import com.library.dao.BookDAO;
import com.library.dao.BorrowDAO;
import com.library.model.Book;
import com.library.model.BorrowDetail;
import com.library.model.BorrowTicket;
import com.library.util.ErrorMessages;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Dialog for handling book returns with detailed status
 */
public class ReturnDialog extends JDialog {
    
    private BorrowTicket borrowTicket;
    private List<BorrowDetail> borrowDetails;
    private boolean confirmed = false;
    
    private BookDAO bookDAO;
    private BorrowDAO borrowDAO;
    
    // Components
    private JTable tblBooks;
    private DefaultTableModel tableModel;
    private JLabel lblTotalFine;
    private JTextArea txtNotes;
    
    // Return data
    private Map<String, ReturnBookInfo> returnInfoMap;
    
    private static final double FINE_PER_DAY = 5000; // 5,000 VND per day
    private static final double FINE_DAMAGED_LIGHT = 20000; // 20,000 VND
    private static final double FINE_DAMAGED_HEAVY = 100000; // 100,000 VND
    private static final double FINE_LOST = 500000; // 500,000 VND
    
    public ReturnDialog(Window owner, BorrowTicket ticket, List<BorrowDetail> details) {
        super(owner, "Trả Sách - Phiếu " + ticket.getMaPM(), ModalityType.APPLICATION_MODAL);
        this.borrowTicket = ticket;
        this.borrowDetails = details;
        this.bookDAO = new BookDAO();
        this.borrowDAO = new BorrowDAO();
        this.returnInfoMap = new HashMap<>();
        
        setSize(900, 700);
        setLocationRelativeTo(owner);
        
        initComponents();
        setupLayout();
        loadBorrowDetails();
        calculateTotalFine();
    }
    
    private void initComponents() {
        String[] columns = {"Mã Sách", "Tên Sách", "SL Mượn", "Tình Trạng", "Ghi Chú"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 3 || column == 4; // Status and notes are editable
            }
        };
        
        tblBooks = new JTable(tableModel);
        tblBooks.setRowHeight(30);
        
        // Set up status combo box for column 3
        JComboBox<String> statusCombo = new JComboBox<>(new String[]{
            "Bình thường", 
            "Hư hỏng nhẹ", 
            "Hư hỏng nặng", 
            "Mất"
        });
        tblBooks.getColumnModel().getColumn(3).setCellEditor(new DefaultCellEditor(statusCombo));
        
        lblTotalFine = new JLabel("Tổng tiền phạt: 0 VNĐ");
        lblTotalFine.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTotalFine.setForeground(new Color(244, 67, 54));
        
        txtNotes = new JTextArea(4, 30);
        txtNotes.setLineWrap(true);
        txtNotes.setWrapStyleWord(true);
    }
    
    private void setupLayout() {
        setLayout(new BorderLayout(10, 10));
        
        // Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 10, 15));
        headerPanel.setBackground(new Color(33, 150, 243));
        
        JLabel lblTitle = new JLabel("📥 TRẢ SÁCH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitle.setForeground(Color.WHITE);
        
        JPanel infoPanel = new JPanel(new GridLayout(3, 1));
        infoPanel.setBackground(new Color(33, 150, 243));
        JLabel lblTicket = new JLabel("Mã phiếu: " + borrowTicket.getMaPM());
        JLabel lblReader = new JLabel("Độc giả: " + borrowTicket.getMaDG());
        JLabel lblBorrowDate = new JLabel("Ngày mượn: " + borrowTicket.getNgayMuon());
        
        lblTicket.setForeground(Color.WHITE);
        lblReader.setForeground(Color.WHITE);
        lblBorrowDate.setForeground(Color.WHITE);
        
        infoPanel.add(lblTicket);
        infoPanel.add(lblReader);
        infoPanel.add(lblBorrowDate);
        
        headerPanel.add(lblTitle, BorderLayout.NORTH);
        headerPanel.add(infoPanel, BorderLayout.CENTER);
        
        // Center Panel - Books Table
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        
        JLabel lblInstruction = new JLabel("Vui lòng chọn tình trạng cho từng sách khi trả:");
        lblInstruction.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(tblBooks);
        
        centerPanel.add(lblInstruction, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        // Fine calculation panel
        JPanel finePanel = new JPanel(new BorderLayout(10, 10));
        finePanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Phạt"));
        finePanel.setBackground(Color.WHITE);
        
        JPanel fineDetailsPanel = new JPanel(new GridLayout(3, 1, 5, 5));
        fineDetailsPanel.setBackground(Color.WHITE);
        
        long overdueDays = ChronoUnit.DAYS.between(borrowTicket.getNgayHenTra(), LocalDate.now());
        if (overdueDays > 0) {
            JLabel lblOverdue = new JLabel("Số ngày quá hạn: " + overdueDays + " ngày");
            lblOverdue.setForeground(new Color(244, 67, 54));
            fineDetailsPanel.add(lblOverdue);
            
            JLabel lblOverdueFine = new JLabel("Phạt quá hạn: " + String.format("%,d VNĐ", (long)(overdueDays * FINE_PER_DAY)));
            lblOverdueFine.setForeground(new Color(244, 67, 54));
            fineDetailsPanel.add(lblOverdueFine);
        } else {
            JLabel lblOnTime = new JLabel("✅ Trả đúng hạn");
            lblOnTime.setForeground(new Color(76, 175, 80));
            fineDetailsPanel.add(lblOnTime);
        }
        
        fineDetailsPanel.add(lblTotalFine);
        
        finePanel.add(fineDetailsPanel, BorderLayout.CENTER);
        
        // Notes Panel
        JPanel notesPanel = new JPanel(new BorderLayout(5, 5));
        notesPanel.setBorder(BorderFactory.createTitledBorder("Ghi Chú Chung"));
        notesPanel.add(new JScrollPane(txtNotes), BorderLayout.CENTER);
        
        JPanel bottomLeftPanel = new JPanel(new GridLayout(2, 1, 10, 10));
        bottomLeftPanel.add(finePanel);
        bottomLeftPanel.add(notesPanel);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        JButton btnCalculate = new JButton("🔄 Tính Lại Tiền Phạt");
        btnCalculate.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCalculate.setBackground(new Color(255, 152, 0));
        btnCalculate.setForeground(Color.WHITE);
        btnCalculate.setFocusPainted(false);
        btnCalculate.addActionListener(e -> calculateTotalFine());
        
        JButton btnConfirm = new JButton("✅ Xác Nhận Trả Sách");
        btnConfirm.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnConfirm.setBackground(new Color(76, 175, 80));
        btnConfirm.setForeground(Color.WHITE);
        btnConfirm.setFocusPainted(false);
        btnConfirm.addActionListener(e -> handleConfirmReturn());
        
        JButton btnCancel = new JButton("❌ Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(244, 67, 54));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        btnCancel.addActionListener(e -> dispose());
        
        buttonPanel.add(btnCalculate);
        buttonPanel.add(btnConfirm);
        buttonPanel.add(btnCancel);
        
        // Bottom Panel
        JPanel bottomPanel = new JPanel(new BorderLayout(10, 10));
        bottomPanel.setBorder(BorderFactory.createEmptyBorder(10, 15, 15, 15));
        bottomPanel.add(bottomLeftPanel, BorderLayout.CENTER);
        bottomPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Add to dialog
        add(headerPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);
        add(bottomPanel, BorderLayout.SOUTH);
    }
    
    private void loadBorrowDetails() {
        tableModel.setRowCount(0);
        
        for (BorrowDetail detail : borrowDetails) {
            String bookName = "";
            try {
                Book book = bookDAO.getBookById(detail.getMaSach());
                if (book != null) {
                    bookName = book.getTenSach();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            
            tableModel.addRow(new Object[]{
                detail.getMaSach(),
                bookName,
                detail.getSoLuongMuon(),
                "Bình thường", // Default status
                "" // Empty notes
            });
            
            // Initialize return info
            ReturnBookInfo info = new ReturnBookInfo();
            info.bookId = detail.getMaSach();
            info.quantity = detail.getSoLuongMuon();
            info.status = "Bình thường";
            info.notes = "";
            returnInfoMap.put(detail.getMaSach(), info);
        }
    }
    
    private void calculateTotalFine() {
        // Update return info from table
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            String bookId = (String) tableModel.getValueAt(i, 0);
            String status = (String) tableModel.getValueAt(i, 3);
            String notes = (String) tableModel.getValueAt(i, 4);
            
            ReturnBookInfo info = returnInfoMap.get(bookId);
            if (info != null) {
                info.status = status;
                info.notes = notes != null ? notes.toString() : "";
            }
        }
        
        // Calculate total fine
        double totalFine = 0;
        
        // Overdue fine
        long overdueDays = ChronoUnit.DAYS.between(borrowTicket.getNgayHenTra(), LocalDate.now());
        if (overdueDays > 0) {
            totalFine += overdueDays * FINE_PER_DAY;
        }
        
        // Damage/lost fines
        for (ReturnBookInfo info : returnInfoMap.values()) {
            switch (info.status) {
                case "Hư hỏng nhẹ":
                    totalFine += FINE_DAMAGED_LIGHT * info.quantity;
                    break;
                case "Hư hỏng nặng":
                    totalFine += FINE_DAMAGED_HEAVY * info.quantity;
                    break;
                case "Mất":
                    totalFine += FINE_LOST * info.quantity;
                    break;
            }
        }
        
        lblTotalFine.setText("Tổng tiền phạt: " + String.format("%,.0f VNĐ", totalFine));
    }
    
    private void handleConfirmReturn() {
        calculateTotalFine();
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Xác nhận trả sách với tổng tiền phạt: " + lblTotalFine.getText() + "?",
            "Xác Nhận Trả Sách",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            confirmed = true;
            dispose();
        }
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Map<String, ReturnBookInfo> getReturnInfo() {
        return returnInfoMap;
    }
    
    public double getTotalFine() {
        String fineText = lblTotalFine.getText();
        String fineAmount = fineText.replaceAll("[^0-9]", "");
        return Double.parseDouble(fineAmount);
    }
    
    public String getNotes() {
        return txtNotes.getText();
    }
    
    /**
     * Inner class to hold return information for each book
     */
    public static class ReturnBookInfo {
        public String bookId;
        public int quantity;
        public String status;
        public String notes;
    }
}
