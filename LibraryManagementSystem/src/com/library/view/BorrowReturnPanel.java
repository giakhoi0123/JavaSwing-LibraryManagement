package com.library.view;

import com.library.model.*;
import com.library.dao.*;
import com.library.util.DateUtil;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Borrow and Return Panel
 * Handles book borrowing and returning operations
 * 
 * @author Library Management System
 * @version 1.0
 */
public class BorrowReturnPanel extends JPanel {
    
    private Staff currentStaff;
    
    // DAOs
    private BookDAO bookDAO;
    private ReaderDAO readerDAO;
    private BorrowDAO borrowDAO;
    
    // Components
    private JTabbedPane tabbedPane;
    
    // Borrow Tab Components
    private JTextField txtSearchReader;
    private JTextField txtReaderName;
    private JTextField txtReaderPhone;
    private JTextField txtSearchBook;
    private JComboBox<Integer> cboBorrowDays;
    private JTable tblSelectedBooks;
    private DefaultTableModel selectedBooksModel;
    private JButton btnAddBook;
    private JButton btnRemoveBook;
    private JButton btnCreateBorrow;
    
    // Return Tab Components
    private JTextField txtSearchBorrow;
    private JTable tblActiveBorrows;
    private DefaultTableModel activeBorrowsModel;
    private JTable tblBorrowDetails;
    private DefaultTableModel borrowDetailsModel;
    private JButton btnReturnBooks;
    private JLabel lblLateFine;
    
    // Current data
    private Reader currentReader;
    private List<Book> selectedBooks;
    private BorrowTicket selectedBorrowTicket;
    
    public BorrowReturnPanel(Staff staff) {
        this.currentStaff = staff;
        this.bookDAO = new BookDAO();
        this.readerDAO = new ReaderDAO();
        this.borrowDAO = new BorrowDAO();
        this.selectedBooks = new ArrayList<>();
        
        setLayout(new BorderLayout());
        initComponents();
        setupLayout();
        attachListeners();
        loadActiveBorrows();
    }
    
    private void initComponents() {
        tabbedPane = new JTabbedPane();
        
        // Borrow Tab
        JPanel borrowTab = createBorrowTab();
        tabbedPane.addTab("📖 Lập Phiếu Mượn", borrowTab);
        
        // Return Tab
        JPanel returnTab = createReturnTab();
        tabbedPane.addTab("📥 Trả Sách", returnTab);
    }
    
    private JPanel createBorrowTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Top Panel - Reader Selection
        JPanel readerPanel = new JPanel(new BorderLayout(10, 10));
        readerPanel.setBorder(BorderFactory.createTitledBorder("Thông Tin Độc Giả"));
        
        JPanel readerSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        readerSearchPanel.add(new JLabel("Tìm độc giả (Mã/SĐT):"));
        txtSearchReader = new JTextField(15);
        readerSearchPanel.add(txtSearchReader);
        
        JButton btnSearchReader = new JButton("🔍 Tìm");
        readerSearchPanel.add(btnSearchReader);
        
        JPanel readerInfoPanel = new JPanel(new GridLayout(2, 2, 10, 5));
        readerInfoPanel.add(new JLabel("Họ tên:"));
        txtReaderName = new JTextField();
        txtReaderName.setEditable(false);
        readerInfoPanel.add(txtReaderName);
        
        readerInfoPanel.add(new JLabel("Số điện thoại:"));
        txtReaderPhone = new JTextField();
        txtReaderPhone.setEditable(false);
        readerInfoPanel.add(txtReaderPhone);
        
        readerPanel.add(readerSearchPanel, BorderLayout.NORTH);
        readerPanel.add(readerInfoPanel, BorderLayout.CENTER);
        
        // Center Panel - Book Selection
        JPanel bookPanel = new JPanel(new BorderLayout(10, 10));
        bookPanel.setBorder(BorderFactory.createTitledBorder("Chọn Sách Mượn"));
        
        JPanel bookSearchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        bookSearchPanel.add(new JLabel("Tìm sách:"));
        txtSearchBook = new JTextField(20);
        bookSearchPanel.add(txtSearchBook);
        
        JButton btnSearchBook = new JButton("🔍 Tìm");
        bookSearchPanel.add(btnSearchBook);
        
        btnAddBook = new JButton("➕ Thêm Sách");
        btnAddBook.setEnabled(false);
        bookSearchPanel.add(btnAddBook);
        
        // Selected Books Table
        String[] columns = {"Mã Sách", "Tên Sách", "Tác Giả", "Vị Trí", "Tình Trạng"};
        selectedBooksModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 4; // Only condition column is editable
            }
        };
        tblSelectedBooks = new JTable(selectedBooksModel);
        JScrollPane scrollPane = new JScrollPane(tblSelectedBooks);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnRemoveBook = new JButton("➖ Xóa Sách");
        buttonPanel.add(btnRemoveBook);
        
        bookPanel.add(bookSearchPanel, BorderLayout.NORTH);
        bookPanel.add(scrollPane, BorderLayout.CENTER);
        bookPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Bottom Panel - Borrow Settings
        JPanel settingsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        settingsPanel.setBorder(BorderFactory.createTitledBorder("Cài Đặt Mượn Sách"));
        
        settingsPanel.add(new JLabel("Số ngày mượn:"));
        cboBorrowDays = new JComboBox<>(new Integer[]{7, 14, 21, 30});
        cboBorrowDays.setSelectedItem(14);
        settingsPanel.add(cboBorrowDays);
        
        btnCreateBorrow = new JButton("✅ Lập Phiếu Mượn");
        btnCreateBorrow.setBackground(new Color(76, 175, 80));
        btnCreateBorrow.setForeground(Color.WHITE);
        btnCreateBorrow.setEnabled(false);
        settingsPanel.add(btnCreateBorrow);
        
        // Add all to main panel
        JPanel topContainer = new JPanel(new BorderLayout());
        topContainer.add(readerPanel, BorderLayout.NORTH);
        topContainer.add(bookPanel, BorderLayout.CENTER);
        
        panel.add(topContainer, BorderLayout.CENTER);
        panel.add(settingsPanel, BorderLayout.SOUTH);
        
        // Attach listeners for Borrow Tab
        btnSearchReader.addActionListener(e -> handleSearchReader());
        btnSearchBook.addActionListener(e -> handleSearchBook());
        btnAddBook.addActionListener(e -> handleAddBook());
        btnRemoveBook.addActionListener(e -> handleRemoveBook());
        btnCreateBorrow.addActionListener(e -> handleCreateBorrow());
        
        return panel;
    }
    
    private JPanel createReturnTab() {
        JPanel panel = new JPanel(new BorderLayout(10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        // Top Panel - Active Borrows
        JPanel borrowPanel = new JPanel(new BorderLayout());
        borrowPanel.setBorder(BorderFactory.createTitledBorder("Danh Sách Phiếu Mượn Đang Hoạt Động"));
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.add(new JLabel("Tìm kiếm:"));
        txtSearchBorrow = new JTextField(20);
        searchPanel.add(txtSearchBorrow);
        
        JButton btnRefresh = new JButton("🔄 Làm Mới");
        searchPanel.add(btnRefresh);
        
        String[] borrowColumns = {"Mã Phiếu", "Độc Giả", "Ngày Mượn", "Hạn Trả", "Trạng Thái"};
        activeBorrowsModel = new DefaultTableModel(borrowColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblActiveBorrows = new JTable(activeBorrowsModel);
        tblActiveBorrows.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                handleSelectBorrow();
            }
        });
        JScrollPane borrowScrollPane = new JScrollPane(tblActiveBorrows);
        
        borrowPanel.add(searchPanel, BorderLayout.NORTH);
        borrowPanel.add(borrowScrollPane, BorderLayout.CENTER);
        
        // Bottom Panel - Borrow Details and Return
        JPanel detailsPanel = new JPanel(new BorderLayout());
        detailsPanel.setBorder(BorderFactory.createTitledBorder("Chi Tiết Sách Mượn"));
        
        String[] detailColumns = {"Mã Sách", "Tên Sách", "Tác Giả", "Tình Trạng Lúc Mượn"};
        borrowDetailsModel = new DefaultTableModel(detailColumns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        tblBorrowDetails = new JTable(borrowDetailsModel);
        JScrollPane detailsScrollPane = new JScrollPane(tblBorrowDetails);
        
        JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        lblLateFine = new JLabel("Tiền phạt: 0 VND");
        lblLateFine.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblLateFine.setForeground(Color.RED);
        returnPanel.add(lblLateFine);
        
        btnReturnBooks = new JButton("✅ Xác Nhận Trả Sách");
        btnReturnBooks.setBackground(new Color(33, 150, 243));
        btnReturnBooks.setForeground(Color.WHITE);
        btnReturnBooks.setEnabled(false);
        returnPanel.add(btnReturnBooks);
        
        detailsPanel.add(detailsScrollPane, BorderLayout.CENTER);
        detailsPanel.add(returnPanel, BorderLayout.SOUTH);
        
        // Split pane
        JSplitPane splitPane = new JSplitPane(JSplitPane.VERTICAL_SPLIT, borrowPanel, detailsPanel);
        splitPane.setDividerLocation(300);
        
        panel.add(splitPane, BorderLayout.CENTER);
        
        // Attach listeners for Return Tab
        btnRefresh.addActionListener(e -> loadActiveBorrows());
        btnReturnBooks.addActionListener(e -> handleReturnBooks());
        
        return panel;
    }
    
    private void setupLayout() {
        add(tabbedPane, BorderLayout.CENTER);
    }
    
    private void attachListeners() {
        // Already attached in createBorrowTab and createReturnTab
    }
    
    // Borrow Tab Handlers
    private void handleSearchReader() {
        String keyword = txtSearchReader.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã hoặc số điện thoại độc giả!");
            return;
        }
        
        try {
            Reader reader = readerDAO.getReaderById(keyword);
            if (reader == null) {
                // Try search by phone
                List<Reader> readers = readerDAO.searchReaders(keyword);
                if (!readers.isEmpty()) {
                    reader = readers.get(0);
                }
            }
            
            if (reader != null) {
                if (reader.canBorrow()) {
                    currentReader = reader;
                    txtReaderName.setText(reader.getHoTen());
                    txtReaderPhone.setText(reader.getSoDT());
                    btnAddBook.setEnabled(true);
                    JOptionPane.showMessageDialog(this, "Tìm thấy độc giả: " + reader.getHoTen());
                } else {
                    JOptionPane.showMessageDialog(this, 
                        "Độc giả không thể mượn sách!\nLý do: " + 
                        (reader.isExpired() ? "Thẻ đã hết hạn" : "Tài khoản không hoạt động"),
                        "Cảnh Báo", 
                        JOptionPane.WARNING_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy độc giả!", 
                    "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void handleSearchBook() {
        String keyword = txtSearchBook.getText().trim();
        if (keyword.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập từ khóa tìm kiếm!");
            return;
        }
        
        try {
            List<Book> books = bookDAO.searchBooks(keyword);
            if (!books.isEmpty()) {
                // Show book selection dialog
                BookSelectionDialog dialog = new BookSelectionDialog(books);
                dialog.setVisible(true);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy sách!", 
                    "Thông Báo", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void handleAddBook() {
        if (currentReader == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả trước!");
            return;
        }
        
        String maSach = txtSearchBook.getText().trim();
        if (maSach.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sách!");
            return;
        }
        
        try {
            Book book = bookDAO.getBookById(maSach);
            if (book != null && book.isAvailable()) {
                // Check if already added
                boolean exists = selectedBooks.stream()
                    .anyMatch(b -> b.getMaSach().equals(book.getMaSach()));
                
                if (!exists) {
                    selectedBooks.add(book);
                    selectedBooksModel.addRow(new Object[]{
                        book.getMaSach(),
                        book.getTenSach(),
                        book.getTenTG(),
                        book.getViTri(),
                        "Tốt"
                    });
                    btnCreateBorrow.setEnabled(!selectedBooks.isEmpty());
                    txtSearchBook.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Sách đã được thêm!");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Sách không có sẵn hoặc đã hết!", 
                    "Cảnh Báo", JOptionPane.WARNING_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void handleRemoveBook() {
        int selectedRow = tblSelectedBooks.getSelectedRow();
        if (selectedRow >= 0) {
            selectedBooks.remove(selectedRow);
            selectedBooksModel.removeRow(selectedRow);
            btnCreateBorrow.setEnabled(!selectedBooks.isEmpty());
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn sách để xóa!");
        }
    }
    
    private void handleCreateBorrow() {
        if (currentReader == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn độc giả!");
            return;
        }
        
        if (selectedBooks.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng thêm ít nhất một cuốn sách!");
            return;
        }
        
        try {
            // Generate ticket ID
            String maPM = borrowDAO.generateNextBorrowTicketId();
            
            // Create borrow ticket
            BorrowTicket ticket = new BorrowTicket();
            ticket.setMaPM(maPM);
            ticket.setMaDG(currentReader.getMaDG());
            ticket.setMaNV(currentStaff.getMaNV());
            ticket.setNgayMuon(LocalDate.now());
            
            int borrowDays = (Integer) cboBorrowDays.getSelectedItem();
            ticket.setHanTra(LocalDate.now().plusDays(borrowDays));
            ticket.setTrangThai("Đang mượn");
            
            // Add borrow details
            for (int i = 0; i < selectedBooksModel.getRowCount(); i++) {
                BorrowDetail detail = new BorrowDetail();
                detail.setMaPM(maPM);
                detail.setMaSach((String) selectedBooksModel.getValueAt(i, 0));
                detail.setTinhTrangLucMuon((String) selectedBooksModel.getValueAt(i, 4));
                ticket.addBorrowDetail(detail);
            }
            
            // Save to database
            boolean success = borrowDAO.createBorrowTicket(ticket);
            
            if (success) {
                JOptionPane.showMessageDialog(this, 
                    "Lập phiếu mượn thành công!\n" +
                    "Mã phiếu: " + maPM + "\n" +
                    "Hạn trả: " + DateUtil.formatDate(ticket.getHanTra()),
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
                
                // Reset form
                currentReader = null;
                selectedBooks.clear();
                txtSearchReader.setText("");
                txtReaderName.setText("");
                txtReaderPhone.setText("");
                txtSearchBook.setText("");
                selectedBooksModel.setRowCount(0);
                btnCreateBorrow.setEnabled(false);
                btnAddBook.setEnabled(false);
                
                // Refresh return tab
                loadActiveBorrows();
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, 
                "Lỗi khi lập phiếu mượn: " + ex.getMessage(), 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    // Return Tab Handlers
    private void loadActiveBorrows() {
        try {
            List<BorrowTicket> tickets = borrowDAO.getActiveBorrowTickets();
            activeBorrowsModel.setRowCount(0);
            
            for (BorrowTicket ticket : tickets) {
                String status = ticket.isOverdue() ? "⚠️ Quá hạn" : "✅ Đang mượn";
                activeBorrowsModel.addRow(new Object[]{
                    ticket.getMaPM(),
                    ticket.getTenDocGia(),
                    DateUtil.formatDate(ticket.getNgayMuon()),
                    DateUtil.formatDate(ticket.getHanTra()),
                    status
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), 
                "Lỗi", JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
    
    private void handleSelectBorrow() {
        int selectedRow = tblActiveBorrows.getSelectedRow();
        if (selectedRow >= 0) {
            String maPM = (String) activeBorrowsModel.getValueAt(selectedRow, 0);
            
            try {
                selectedBorrowTicket = borrowDAO.getBorrowTicketById(maPM);
                if (selectedBorrowTicket != null) {
                    // Load borrow details
                    borrowDetailsModel.setRowCount(0);
                    for (BorrowDetail detail : selectedBorrowTicket.getBorrowDetails()) {
                        borrowDetailsModel.addRow(new Object[]{
                            detail.getMaSach(),
                            detail.getTenSach(),
                            detail.getTenTacGia(),
                            detail.getTinhTrangLucMuon()
                        });
                    }
                    
                    // Calculate late fine
                    double fine = borrowDAO.calculateLateFine(maPM, LocalDate.now());
                    lblLateFine.setText(String.format("Tiền phạt: %.0f VND", fine));
                    
                    btnReturnBooks.setEnabled(true);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Lỗi: " + ex.getMessage(), 
                    "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    private void handleReturnBooks() {
        if (selectedBorrowTicket == null) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn phiếu mượn!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Xác nhận trả sách cho phiếu mượn: " + selectedBorrowTicket.getMaPM() + "?",
            "Xác Nhận",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            JOptionPane.showMessageDialog(this, 
                "Chức năng trả sách đang được phát triển!\n" +
                "Sẽ bao gồm:\n" +
                "- Cập nhật trạng thái phiếu mượn\n" +
                "- Cộng lại số lượng sách\n" +
                "- Tính tiền phạt tự động\n" +
                "- Lưu vào bảng PHIEU_TRA",
                "Thông Báo",
                JOptionPane.INFORMATION_MESSAGE);
        }
    }
    
    // Inner class for Book Selection Dialog
    private class BookSelectionDialog extends JDialog {
        public BookSelectionDialog(List<Book> books) {
            setTitle("Chọn Sách");
            setModal(true);
            setSize(600, 400);
            setLocationRelativeTo(BorrowReturnPanel.this);
            
            String[] columns = {"Mã Sách", "Tên Sách", "Tác Giả", "Số Lượng"};
            DefaultTableModel model = new DefaultTableModel(columns, 0);
            
            for (Book book : books) {
                model.addRow(new Object[]{
                    book.getMaSach(),
                    book.getTenSach(),
                    book.getTenTG(),
                    book.getSoLuong()
                });
            }
            
            JTable table = new JTable(model);
            JScrollPane scrollPane = new JScrollPane(table);
            
            JButton btnSelect = new JButton("Chọn");
            btnSelect.addActionListener(e -> {
                int selectedRow = table.getSelectedRow();
                if (selectedRow >= 0) {
                    txtSearchBook.setText((String) model.getValueAt(selectedRow, 0));
                    dispose();
                    handleAddBook();
                }
            });
            
            JPanel buttonPanel = new JPanel();
            buttonPanel.add(btnSelect);
            
            setLayout(new BorderLayout());
            add(scrollPane, BorderLayout.CENTER);
            add(buttonPanel, BorderLayout.SOUTH);
        }
    }
}
