package com.library.view;

import com.library.dao.BookDAO;
import com.library.model.Book;
import com.library.model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Panel for comprehensive Book Management
 * Features: View all books, Search, Add, Edit, Delete
 * 
 * @author Library Management System
 * @version 1.0
 */
public class BookManagementPanel extends JPanel {
    
    private Staff currentStaff;
    private BookDAO bookDAO;
    
    // Table components
    private JTable tblBooks;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    
    // Search components
    private JTextField txtSearch;
    private JComboBox<String> cmbSearchType;
    
    // Filter components
    private JComboBox<String> cmbCategoryFilter;
    private JCheckBox chkAvailableOnly;
    
    // Action buttons
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnViewDetails;
    private JButton btnImportExcel;
    private JButton btnExportExcel;
    
    public BookManagementPanel(Staff staff) {
        this.currentStaff = staff;
        this.bookDAO = new BookDAO();
        
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        initComponents();
        loadCategories(); // Load categories once during initialization
        setupLayout();
        attachListeners();
        loadBooksData();
    }
    
    private void initComponents() {
        // Search components
        txtSearch = new JTextField(20);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setToolTipText("Nhập từ khóa tìm kiếm...");
        
        cmbSearchType = new JComboBox<>(new String[]{
            "Tất cả", "Tên sách", "Tác giả", "Nhà xuất bản"
        });
        cmbSearchType.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        // Filter components
        cmbCategoryFilter = new JComboBox<>();
        cmbCategoryFilter.addItem("Tất cả thể loại");
        cmbCategoryFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        chkAvailableOnly = new JCheckBox("Chỉ hiển thị sách còn");
        chkAvailableOnly.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkAvailableOnly.setBackground(Color.WHITE);
        
        // Table setup
        String[] columns = {"Mã Sách", "Tên Sách", "Tác Giả", "Thể Loại", "NXB", "Năm XB", "Số Lượng", "Đơn Giá", "Vị Trí"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        
        tblBooks = new JTable(tableModel);
        tblBooks.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblBooks.setRowHeight(25);
        tblBooks.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblBooks.getTableHeader().setBackground(new Color(33, 150, 243));
        tblBooks.getTableHeader().setForeground(Color.WHITE);
        tblBooks.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblBooks.setGridColor(new Color(224, 224, 224));
        
        // Column widths
        tblBooks.getColumnModel().getColumn(0).setPreferredWidth(80);  // Mã Sách
        tblBooks.getColumnModel().getColumn(1).setPreferredWidth(250); // Tên Sách
        tblBooks.getColumnModel().getColumn(2).setPreferredWidth(150); // Tác Giả
        tblBooks.getColumnModel().getColumn(3).setPreferredWidth(120); // Thể Loại
        tblBooks.getColumnModel().getColumn(4).setPreferredWidth(150); // NXB
        tblBooks.getColumnModel().getColumn(5).setPreferredWidth(80);  // Năm XB
        tblBooks.getColumnModel().getColumn(6).setPreferredWidth(80);  // Số Lượng
        tblBooks.getColumnModel().getColumn(7).setPreferredWidth(100); // Đơn Giá
        tblBooks.getColumnModel().getColumn(8).setPreferredWidth(120); // Vị Trí
        
        rowSorter = new TableRowSorter<>(tableModel);
        tblBooks.setRowSorter(rowSorter);
        
        // Action buttons
        btnAdd = createStyledButton("➕ Thêm Sách", new Color(76, 175, 80));
        btnEdit = createStyledButton("✏️ Sửa Sách", new Color(255, 152, 0));
        btnDelete = createStyledButton("🗑️ Xóa Sách", new Color(244, 67, 54));
        btnViewDetails = createStyledButton("👁️ Chi Tiết", new Color(33, 150, 243));
        btnRefresh = createStyledButton("🔄 Làm Mới", new Color(96, 125, 139));
        btnImportExcel = createStyledButton("📥 Nhập Excel", new Color(0, 150, 136));
        btnExportExcel = createStyledButton("📤 Xuất Excel", new Color(103, 58, 183));
        
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnViewDetails.setEnabled(false);
    }
    
    private JButton createStyledButton(String text, Color bgColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI", Font.BOLD, 13));
        button.setBackground(bgColor);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 35));
        
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor.darker());
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(bgColor);
            }
        });
        
        return button;
    }
    
    private void setupLayout() {
        // Title Panel
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel lblTitle = new JLabel("📚 QUẢN LÝ SÁCH");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(33, 150, 243));
        titlePanel.add(lblTitle, BorderLayout.WEST);
        
        // Search Panel
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(33, 150, 243), 2),
                "Tìm Kiếm & Lọc",
                0,
                0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(33, 150, 243)
            ),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        searchPanel.add(new JLabel("Loại:"));
        searchPanel.add(cmbSearchType);
        searchPanel.add(new JLabel("Từ khóa:"));
        searchPanel.add(txtSearch);
        searchPanel.add(new JLabel("Thể loại:"));
        searchPanel.add(cmbCategoryFilter);
        searchPanel.add(chkAvailableOnly);
        
        // Button Panel - split into 2 rows to avoid overflow
        JPanel buttonRow1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonRow1.setBackground(Color.WHITE);
        buttonRow1.add(btnAdd);
        buttonRow1.add(btnEdit);
        buttonRow1.add(btnDelete);
        buttonRow1.add(btnViewDetails);
        buttonRow1.add(btnRefresh);
        
        JPanel buttonRow2 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonRow2.setBackground(Color.WHITE);
        buttonRow2.add(btnImportExcel);
        buttonRow2.add(btnExportExcel);
        
        JPanel buttonPanel = new JPanel(new BorderLayout());
        buttonPanel.setBackground(Color.WHITE);
        buttonPanel.add(buttonRow1, BorderLayout.NORTH);
        buttonPanel.add(buttonRow2, BorderLayout.SOUTH);
        
        // Top Panel (Search + Buttons)
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        // Table Panel
        JScrollPane scrollPane = new JScrollPane(tblBooks);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224)));
        
        // Status Panel
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(new Color(245, 245, 245));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel lblStatus = new JLabel("Tổng số sách: 0");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusPanel.add(lblStatus);
        
        // Add to main panel
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(statusPanel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void attachListeners() {
        // Search on text change
        txtSearch.addCaretListener(e -> performSearch());
        
        // Filter listeners
        cmbSearchType.addActionListener(e -> performSearch());
        cmbCategoryFilter.addActionListener(e -> performSearch());
        chkAvailableOnly.addActionListener(e -> performSearch());
        
        // Table selection
        tblBooks.getSelectionModel().addListSelectionListener(e -> {
            boolean hasSelection = tblBooks.getSelectedRow() != -1;
            btnEdit.setEnabled(hasSelection);
            btnDelete.setEnabled(hasSelection);
            btnViewDetails.setEnabled(hasSelection);
        });
        
        // Button actions
        btnAdd.addActionListener(e -> showAddBookDialog());
        btnEdit.addActionListener(e -> showEditBookDialog());
        btnDelete.addActionListener(e -> deleteSelectedBook());
        btnViewDetails.addActionListener(e -> showBookDetails());
        btnRefresh.addActionListener(e -> loadBooksData());
        btnImportExcel.addActionListener(e -> importBooksFromExcel());
        btnExportExcel.addActionListener(e -> exportBooksToExcel());
    }
    
    private void loadBooksData() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            tableModel.setRowCount(0); // Clear table
            
            for (Book book : books) {
                Object[] row = {
                    book.getMaSach(),
                    book.getTenSach(),
                    book.getMaTG() != null ? book.getMaTG() : "N/A",
                    book.getTenTheLoai() != null ? book.getTenTheLoai() : "N/A",
                    book.getMaNXB() != null ? book.getMaNXB() : "N/A",
                    book.getNamXB(),
                    book.getSoLuong(),
                    String.format("%,.0f VNĐ", book.getDonGia()),
                    book.getViTri() != null ? book.getViTri() : "N/A"
                };
                tableModel.addRow(row);
            }
            
            // Clear filters and refresh display
            txtSearch.setText("");
            cmbCategoryFilter.setSelectedIndex(0);
            chkAvailableOnly.setSelected(false);
            rowSorter.setRowFilter(null);
            
            updateStatus();
            tableModel.fireTableDataChanged();
            tblBooks.revalidate();
            tblBooks.repaint();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu sách: " + ex.getMessage(),
                "Lỗi Database",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void loadCategories() {
        try {
            List<String> categories = bookDAO.getAllCategories();
            for (String category : categories) {
                cmbCategoryFilter.addItem(category);
            }
        } catch (SQLException ex) {
            System.err.println("Error loading categories: " + ex.getMessage());
        }
    }
    
    private void performSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        String searchType = (String) cmbSearchType.getSelectedItem();
        String categoryFilter = (String) cmbCategoryFilter.getSelectedItem();
        boolean availableOnly = chkAvailableOnly.isSelected();
        
        System.out.println("🔍 Performing search:");
        System.out.println("  - Keyword: '" + keyword + "'");
        System.out.println("  - Search Type: '" + searchType + "'");
        System.out.println("  - Category Filter: '" + categoryFilter + "'");
        System.out.println("  - Available Only: " + availableOnly);
        
        RowFilter<DefaultTableModel, Object> rf = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                // Search filter
                if (!keyword.isEmpty()) {
                    boolean matchFound = false;
                    
                    switch (searchType) {
                        case "Tên sách":
                            matchFound = entry.getStringValue(1).toLowerCase().contains(keyword);
                            break;
                        case "Tác giả":
                            matchFound = entry.getStringValue(2).toLowerCase().contains(keyword);
                            break;
                        case "Nhà xuất bản":
                            matchFound = entry.getStringValue(4).toLowerCase().contains(keyword);
                            break;
                        default: // Tất cả
                            for (int i = 0; i < entry.getValueCount(); i++) {
                                if (entry.getStringValue(i).toLowerCase().contains(keyword)) {
                                    matchFound = true;
                                    break;
                                }
                            }
                    }
                    
                    if (!matchFound) return false;
                }
                
                // Category filter
                if (!categoryFilter.equals("Tất cả thể loại")) {
                    String bookCategory = entry.getStringValue(3);
                    boolean match = bookCategory.contains(categoryFilter);
                    System.out.println("  Category check: '" + categoryFilter + "' vs '" + bookCategory + "' = " + match);
                    if (!match) {
                        return false;
                    }
                }
                
                // Available only filter
                if (availableOnly) {
                    String soLuong = entry.getStringValue(6);
                    try {
                        int quantity = Integer.parseInt(soLuong);
                        if (quantity <= 0) return false;
                    } catch (NumberFormatException e) {
                        return false;
                    }
                }
                
                return true;
            }
        };
        
        rowSorter.setRowFilter(rf);
        updateStatus();
    }
    
    private void updateStatus() {
        int totalRows = tableModel.getRowCount();
        int visibleRows = tblBooks.getRowCount();
        
        Component[] components = ((JPanel)((JPanel)getComponent(1)).getComponent(2)).getComponents();
        if (components.length > 0 && components[0] instanceof JLabel) {
            JLabel lblStatus = (JLabel) components[0];
            lblStatus.setText(String.format("Tổng số sách: %d | Hiển thị: %d", totalRows, visibleRows));
        }
    }
    
    private void showAddBookDialog() {
        BookDialog dialog = new BookDialog(SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadBooksData();
        }
    }
    
    private void showEditBookDialog() {
        int selectedRow = tblBooks.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblBooks.convertRowIndexToModel(selectedRow);
        String maSach = (String) tableModel.getValueAt(modelRow, 0);
        
        try {
            Book book = bookDAO.getBookById(maSach);
            if (book != null) {
                BookDialog dialog = new BookDialog(SwingUtilities.getWindowAncestor(this), book);
                dialog.setVisible(true);
                
                if (dialog.isConfirmed()) {
                    loadBooksData();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi sửa sách: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteSelectedBook() {
        int selectedRow = tblBooks.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblBooks.convertRowIndexToModel(selectedRow);
        String maSach = (String) tableModel.getValueAt(modelRow, 0);
        String tenSach = (String) tableModel.getValueAt(modelRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa sách:\n" + tenSach + " (" + maSach + ")?",
            "Xác Nhận Xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                bookDAO.deleteBook(maSach);
                JOptionPane.showMessageDialog(this,
                    "Xóa sách thành công!",
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
                loadBooksData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi xóa sách: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showBookDetails() {
        int selectedRow = tblBooks.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblBooks.convertRowIndexToModel(selectedRow);
        String maSach = (String) tableModel.getValueAt(modelRow, 0);
        
        try {
            Book book = bookDAO.getBookById(maSach);
            if (book != null) {
                String details = String.format(
                    "━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "📚 THÔNG TIN CHI TIẾT SÁCH\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                    "Mã sách: %s\n" +
                    "Tên sách: %s\n" +
                    "Tác giả: %s\n" +
                    "Thể loại: %s\n" +
                    "Nhà xuất bản: %s\n" +
                    "Năm xuất bản: %d\n" +
                    "Số lượng: %d\n" +
                    "Đơn giá: %,.0f VNĐ\n" +
                    "Vị trí: %s\n" +
                    "Trạng thái: %s",
                    book.getMaSach(),
                    book.getTenSach(),
                    book.getMaTG() != null ? book.getMaTG() : "N/A",
                    book.getMaTheLoai() != null ? book.getMaTheLoai() : "N/A",
                    book.getMaNXB() != null ? book.getMaNXB() : "N/A",
                    book.getNamXB(),
                    book.getSoLuong(),
                    book.getDonGia(),
                    book.getViTri() != null ? book.getViTri() : "N/A",
                    book.isAvailable() ? "✅ Còn sách" : "❌ Hết sách"
                );
                
                JOptionPane.showMessageDialog(this,
                    details,
                    "Chi Tiết Sách",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi xem chi tiết: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    /**
     * Import books from Excel file
     */
    private void importBooksFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn File Excel/CSV để Nhập Sách");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Excel & CSV Files (*.xlsx, *.xls, *.csv)", "xlsx", "xls", "csv"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                
                // Import books from Excel
                List<Book> importedBooks = com.library.util.ExcelUtil.importBooksFromExcel(filePath);
                
                if (importedBooks.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Không có dữ liệu để nhập!",
                        "Thông Báo",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Save books to database
                int successCount = 0;
                int errorCount = 0;
                StringBuilder errors = new StringBuilder();
                
                for (Book book : importedBooks) {
                    try {
                        bookDAO.upsertBook(book);
                        successCount++;
                    } catch (SQLException ex) {
                        errorCount++;
                        errors.append("- ").append(book.getTenSach()).append(": ").append(ex.getMessage()).append("\n");
                    }
                }
                
                // Reload data
                loadBooksData();
                
                // Show result
                String message = String.format(
                    "Nhập thành công: %d sách\nLỗi: %d sách\n\n%s",
                    successCount, errorCount,
                    errorCount > 0 ? "Chi tiết lỗi:\n" + errors.toString() : ""
                );
                
                JOptionPane.showMessageDialog(this,
                    message,
                    "Kết Quả Nhập Excel",
                    errorCount > 0 ? JOptionPane.WARNING_MESSAGE : JOptionPane.INFORMATION_MESSAGE);
                    
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi nhập file Excel: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }
    
    /**
     * Export books to Excel file
     */
    private void exportBooksToExcel() {
        try {
            List<Book> books = bookDAO.getAllBooks();
            
            if (books.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Không có dữ liệu để xuất!",
                    "Thông Báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu File Excel");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
            fileChooser.setSelectedFile(new java.io.File("DanhSachSach.xlsx"));
            
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                
                com.library.util.ExcelUtil.exportBooksToExcel(books, filePath);
                
                JOptionPane.showMessageDialog(this,
                    "Xuất file Excel thành công!\nĐường dẫn: " + filePath,
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi xuất file Excel: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            ex.printStackTrace();
        }
    }
}
