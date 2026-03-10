package com.library.view;

import com.library.dao.ReaderDAO;
import com.library.model.Reader;
import com.library.model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Panel for comprehensive Reader Management
 * Features: View, Search, Add, Edit, Delete, Renew Membership
 */
public class ReaderManagementPanel extends JPanel {
    
    private Staff currentStaff;
    private ReaderDAO readerDAO;
    
    private JTable tblReaders;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    
    private JTextField txtSearch;
    private JCheckBox chkActiveOnly;
    
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRenew;
    private JButton btnRefresh;
    private JButton btnViewDetails;
    private JButton btnImportExcel;
    private JButton btnExportExcel;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public ReaderManagementPanel(Staff staff) {
        this.currentStaff = staff;
        this.readerDAO = new ReaderDAO();
        
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        initComponents();
        setupLayout();
        attachListeners();
        loadReadersData();
    }
    
    private void initComponents() {
        txtSearch = new JTextField(25);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSearch.setToolTipText("Tìm theo mã, tên, SĐT, email...");
        
        chkActiveOnly = new JCheckBox("Chỉ hiển thị độc giả đang hoạt động");
        chkActiveOnly.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chkActiveOnly.setBackground(Color.WHITE);
        chkActiveOnly.setSelected(true);
        
        String[] columns = {"Mã ĐG", "Họ Tên", "Ngày Sinh", "Giới Tính", "SĐT", "Email", "Địa Chỉ", "Ngày Lập Thẻ", "Ngày Hết Hạn", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblReaders = new JTable(tableModel);
        tblReaders.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblReaders.setRowHeight(25);
        tblReaders.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblReaders.getTableHeader().setBackground(new Color(255, 152, 0));
        tblReaders.getTableHeader().setForeground(Color.WHITE);
        tblReaders.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        tblReaders.getColumnModel().getColumn(0).setPreferredWidth(80);
        tblReaders.getColumnModel().getColumn(1).setPreferredWidth(180);
        tblReaders.getColumnModel().getColumn(2).setPreferredWidth(100);
        tblReaders.getColumnModel().getColumn(3).setPreferredWidth(80);
        tblReaders.getColumnModel().getColumn(4).setPreferredWidth(110);
        tblReaders.getColumnModel().getColumn(5).setPreferredWidth(180);
        tblReaders.getColumnModel().getColumn(6).setPreferredWidth(200);
        tblReaders.getColumnModel().getColumn(7).setPreferredWidth(100);
        tblReaders.getColumnModel().getColumn(8).setPreferredWidth(100);
        tblReaders.getColumnModel().getColumn(9).setPreferredWidth(100);
        
        rowSorter = new TableRowSorter<>(tableModel);
        tblReaders.setRowSorter(rowSorter);
        
        btnAdd = createStyledButton("➕ Thêm ĐG", new Color(76, 175, 80));
        btnEdit = createStyledButton("✏️ Sửa ĐG", new Color(255, 152, 0));
        btnDelete = createStyledButton("🗑️ Xóa ĐG", new Color(244, 67, 54));
        btnRenew = createStyledButton("🔄 Gia Hạn", new Color(33, 150, 243));
        btnViewDetails = createStyledButton("👁️ Chi Tiết", new Color(156, 39, 176));
        btnRefresh = createStyledButton("🔄 Làm Mới", new Color(96, 125, 139));
        btnImportExcel = createStyledButton("📥 Nhập Excel", new Color(0, 150, 136));
        btnExportExcel = createStyledButton("📤 Xuất Excel", new Color(103, 58, 183));
        
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnRenew.setEnabled(false);
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
        button.setPreferredSize(new Dimension(130, 35));
        
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
        JPanel titlePanel = new JPanel(new BorderLayout());
        titlePanel.setBackground(Color.WHITE);
        titlePanel.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));
        
        JLabel lblTitle = new JLabel("👥 QUẢN LÝ ĐỘC GIẢ");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(255, 152, 0));
        titlePanel.add(lblTitle, BorderLayout.WEST);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(255, 152, 0), 2),
                "Tìm Kiếm & Lọc",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(255, 152, 0)
            ),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        searchPanel.add(new JLabel("🔍 Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(chkActiveOnly);
        
        JPanel buttonRow1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonRow1.setBackground(Color.WHITE);
        buttonRow1.add(btnAdd);
        buttonRow1.add(btnEdit);
        buttonRow1.add(btnDelete);
        buttonRow1.add(btnRenew);
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
        
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(tblReaders);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224)));
        
        JPanel statusPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        statusPanel.setBackground(new Color(245, 245, 245));
        statusPanel.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));
        
        JLabel lblStatus = new JLabel("Tổng số độc giả: 0");
        lblStatus.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        statusPanel.add(lblStatus);
        
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        centerPanel.add(statusPanel, BorderLayout.SOUTH);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void attachListeners() {
        txtSearch.addCaretListener(e -> performSearch());
        chkActiveOnly.addActionListener(e -> performSearch());
        
        tblReaders.getSelectionModel().addListSelectionListener(e -> {
            boolean hasSelection = tblReaders.getSelectedRow() != -1;
            btnEdit.setEnabled(hasSelection);
            btnDelete.setEnabled(hasSelection);
            btnRenew.setEnabled(hasSelection);
            btnViewDetails.setEnabled(hasSelection);
        });
        
        btnAdd.addActionListener(e -> showAddReaderDialog());
        btnEdit.addActionListener(e -> showEditReaderDialog());
        btnDelete.addActionListener(e -> deleteSelectedReader());
        btnRenew.addActionListener(e -> renewMembership());
        btnViewDetails.addActionListener(e -> showReaderDetails());
        btnRefresh.addActionListener(e -> loadReadersData());
        btnImportExcel.addActionListener(e -> importReadersFromExcel());
        btnExportExcel.addActionListener(e -> exportReadersToExcel());
    }
    
    private void loadReadersData() {
        try {
            System.out.println("🔄 Loading readers data...");
            List<Reader> readers;
            if (chkActiveOnly.isSelected()) {
                readers = readerDAO.getActiveReaders();
                System.out.println("✓ Loaded " + readers.size() + " active readers");
            } else {
                readers = readerDAO.getAllReaders();
                System.out.println("✓ Loaded " + readers.size() + " total readers");
            }
            
            tableModel.setRowCount(0);
            
            for (Reader reader : readers) {
                Object[] row = {
                    reader.getMaDG(),
                    reader.getHoTen(),
                    reader.getNgaySinh() != null ? reader.getNgaySinh().format(DATE_FORMATTER) : "",
                    reader.getGioiTinh(),
                    reader.getSoDT(),
                    reader.getEmail() != null ? reader.getEmail() : "",
                    reader.getDiaChi() != null ? reader.getDiaChi() : "",
                    reader.getNgayLapThe() != null ? reader.getNgayLapThe().format(DATE_FORMATTER) : "",
                    reader.getNgayHetHan() != null ? reader.getNgayHetHan().format(DATE_FORMATTER) : "",
                    reader.getTrangThai()
                };
                tableModel.addRow(row);
            }
            
            System.out.println("✓ Added " + tableModel.getRowCount() + " rows to table");
            
            // Clear filters and refresh display
            txtSearch.setText("");
            chkActiveOnly.setSelected(false);
            rowSorter.setRowFilter(null);
            
            updateStatus();
            tableModel.fireTableDataChanged();
            tblReaders.revalidate();
            tblReaders.repaint();
            
        } catch (SQLException ex) {
            ex.printStackTrace();
            System.err.println("❌ Error loading readers: " + ex.getMessage());
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu độc giả: " + ex.getMessage(),
                "Lỗi Database",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        
        if (keyword.isEmpty()) {
            rowSorter.setRowFilter(null);
        } else {
            RowFilter<DefaultTableModel, Object> rf = new RowFilter<DefaultTableModel, Object>() {
                @Override
                public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                    // Search in columns: MaDG, HoTen, SoDT, Email
                    for (int i : new int[]{0, 1, 4, 5}) {
                        String value = entry.getStringValue(i).toLowerCase();
                        if (value.contains(keyword)) {
                            return true;
                        }
                    }
                    return false;
                }
            };
            rowSorter.setRowFilter(rf);
        }
        
        updateStatus();
    }
    
    private void updateStatus() {
        int totalRows = tableModel.getRowCount();
        int visibleRows = tblReaders.getRowCount();
        
        Component[] components = ((JPanel)((JPanel)getComponent(1)).getComponent(2)).getComponents();
        if (components.length > 0 && components[0] instanceof JLabel) {
            JLabel lblStatus = (JLabel) components[0];
            lblStatus.setText(String.format("Tổng số độc giả: %d | Hiển thị: %d", totalRows, visibleRows));
        }
    }
    
    private void showAddReaderDialog() {
        ReaderDialog dialog = new ReaderDialog(SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            loadReadersData();
        }
    }
    
    private void showEditReaderDialog() {
        int selectedRow = tblReaders.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblReaders.convertRowIndexToModel(selectedRow);
        String maDG = (String) tableModel.getValueAt(modelRow, 0);
        
        try {
            Reader reader = readerDAO.getReaderById(maDG);
            if (reader != null) {
                ReaderDialog dialog = new ReaderDialog(SwingUtilities.getWindowAncestor(this), reader);
                dialog.setVisible(true);
                
                if (dialog.isConfirmed()) {
                    loadReadersData();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi sửa độc giả: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteSelectedReader() {
        int selectedRow = tblReaders.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblReaders.convertRowIndexToModel(selectedRow);
        String maDG = (String) tableModel.getValueAt(modelRow, 0);
        String hoTen = (String) tableModel.getValueAt(modelRow, 1);
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa độc giả:\n" + hoTen + " (" + maDG + ")?",
            "Xác Nhận Xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                readerDAO.deleteReader(maDG);
                JOptionPane.showMessageDialog(this,
                    "Xóa độc giả thành công!",
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
                loadReadersData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi xóa độc giả: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void renewMembership() {
        int selectedRow = tblReaders.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblReaders.convertRowIndexToModel(selectedRow);
        String maDG = (String) tableModel.getValueAt(modelRow, 0);
        String hoTen = (String) tableModel.getValueAt(modelRow, 1);
        
        String[] options = {"6 tháng", "12 tháng", "24 tháng"};
        int choice = JOptionPane.showOptionDialog(this,
            "Chọn thời gian gia hạn cho: " + hoTen,
            "Gia Hạn Thẻ Độc Giả",
            JOptionPane.DEFAULT_OPTION,
            JOptionPane.QUESTION_MESSAGE,
            null,
            options,
            options[1]);
        
        if (choice != -1) {
            int months = choice == 0 ? 6 : (choice == 1 ? 12 : 24);
            
            try {
                readerDAO.renewMembership(maDG, months);
                JOptionPane.showMessageDialog(this,
                    "Gia hạn thẻ thành công!",
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
                loadReadersData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi gia hạn thẻ: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showReaderDetails() {
        int selectedRow = tblReaders.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblReaders.convertRowIndexToModel(selectedRow);
        String maDG = (String) tableModel.getValueAt(modelRow, 0);
        
        try {
            Reader reader = readerDAO.getReaderById(maDG);
            if (reader != null) {
                String details = String.format(
                    "━━━━━━━━━━━━━━━━━━━━━━\n" +
                    "👤 THÔNG TIN CHI TIẾT ĐỘC GIẢ\n" +
                    "━━━━━━━━━━━━━━━━━━━━━━\n\n" +
                    "Mã độc giả: %s\n" +
                    "Họ tên: %s\n" +
                    "Ngày sinh: %s\n" +
                    "Giới tính: %s\n" +
                    "Số điện thoại: %s\n" +
                    "Email: %s\n" +
                    "Địa chỉ: %s\n" +
                    "Ngày lập thẻ: %s\n" +
                    "Ngày hết hạn: %s\n" +
                    "Trạng thái: %s\n" +
                    "Có thể mượn sách: %s",
                    reader.getMaDG(),
                    reader.getHoTen(),
                    reader.getNgaySinh() != null ? reader.getNgaySinh().format(DATE_FORMATTER) : "N/A",
                    reader.getGioiTinh(),
                    reader.getSoDT(),
                    reader.getEmail() != null ? reader.getEmail() : "N/A",
                    reader.getDiaChi() != null ? reader.getDiaChi() : "N/A",
                    reader.getNgayLapThe() != null ? reader.getNgayLapThe().format(DATE_FORMATTER) : "N/A",
                    reader.getNgayHetHan() != null ? reader.getNgayHetHan().format(DATE_FORMATTER) : "N/A",
                    reader.getTrangThai(),
                    reader.canBorrow() ? "✅ Có" : "❌ Không"
                );
                
                JOptionPane.showMessageDialog(this,
                    details,
                    "Chi Tiết Độc Giả",
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
     * Import readers from Excel file
     */
    private void importReadersFromExcel() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Chọn File Excel/CSV để Nhập Độc Giả");
        fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter(
            "Excel & CSV Files (*.xlsx, *.xls, *.csv)", "xlsx", "xls", "csv"));
        
        int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                
                // Import readers from Excel
                List<Reader> importedReaders = com.library.util.ExcelUtil.importReadersFromExcel(filePath);
                
                if (importedReaders.isEmpty()) {
                    JOptionPane.showMessageDialog(this,
                        "Không có dữ liệu để nhập!",
                        "Thông Báo",
                        JOptionPane.WARNING_MESSAGE);
                    return;
                }
                
                // Save readers to database
                int successCount = 0;
                int errorCount = 0;
                StringBuilder errors = new StringBuilder();
                
                for (Reader reader : importedReaders) {
                    try {
                        readerDAO.upsertReader(reader);
                        successCount++;
                    } catch (SQLException ex) {
                        errorCount++;
                        errors.append("- ").append(reader.getHoTen()).append(": ").append(ex.getMessage()).append("\n");
                    }
                }
                
                // Reload data
                loadReadersData();
                
                // Show result
                String message = String.format(
                    "Nhập thành công: %d độc giả\nLỗi: %d độc giả\n\n%s",
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
     * Export readers to Excel file
     */
    private void exportReadersToExcel() {
        try {
            List<Reader> readers = readerDAO.getAllReaders();
            
            if (readers.isEmpty()) {
                JOptionPane.showMessageDialog(this,
                    "Không có dữ liệu để xuất!",
                    "Thông Báo",
                    JOptionPane.WARNING_MESSAGE);
                return;
            }
            
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle("Lưu File Excel");
            fileChooser.setFileFilter(new javax.swing.filechooser.FileNameExtensionFilter("Excel Files", "xlsx"));
            fileChooser.setSelectedFile(new java.io.File("DanhSachDocGia.xlsx"));
            
            int result = fileChooser.showSaveDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                String filePath = fileChooser.getSelectedFile().getAbsolutePath();
                if (!filePath.endsWith(".xlsx")) {
                    filePath += ".xlsx";
                }
                
                com.library.util.ExcelUtil.exportReadersToExcel(readers, filePath);
                
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
