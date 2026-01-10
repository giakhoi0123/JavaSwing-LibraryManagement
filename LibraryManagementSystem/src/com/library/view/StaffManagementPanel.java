package com.library.view;

import com.library.dao.StaffDAO;
import com.library.model.Staff;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * Staff Management Panel (Admin Only)
 */
public class StaffManagementPanel extends JPanel {
    
    private Staff currentStaff;
    private StaffDAO staffDAO;
    
    private JTable tblStaff;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> rowSorter;
    
    private JTextField txtSearch;
    private JComboBox<String> cmbRoleFilter;
    
    private JButton btnAdd;
    private JButton btnEdit;
    private JButton btnDelete;
    private JButton btnRefresh;
    private JButton btnResetPassword;
    
    public StaffManagementPanel(Staff staff) {
        this.currentStaff = staff;
        this.staffDAO = new StaffDAO();
        
        setLayout(new BorderLayout(10, 10));
        setBackground(Color.WHITE);
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        
        if (!staff.isAdmin()) {
            showAccessDenied();
            return;
        }
        
        initComponents();
        setupLayout();
        attachListeners();
        loadStaffData();
    }
    
    private void showAccessDenied() {
        JLabel lblDenied = new JLabel("⛔ QUYỀN TRUY CẬP BỊ TỪ CHỐI", SwingConstants.CENTER);
        lblDenied.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblDenied.setForeground(new Color(244, 67, 54));
        
        JLabel lblMessage = new JLabel("Chỉ Admin mới có quyền quản lý nhân viên", SwingConstants.CENTER);
        lblMessage.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        lblMessage.setForeground(Color.GRAY);
        
        setLayout(new GridLayout(2, 1, 10, 10));
        add(lblDenied);
        add(lblMessage);
    }
    
    private void initComponents() {
        txtSearch = new JTextField(25);
        txtSearch.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        cmbRoleFilter = new JComboBox<>(new String[]{"Tất cả", "Admin", "Librarian"});
        cmbRoleFilter.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        String[] columns = {"Mã NV", "Họ Tên", "SĐT", "Email", "Chức Vụ", "Username", "Role", "Trạng Thái"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblStaff = new JTable(tableModel);
        tblStaff.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblStaff.setRowHeight(25);
        tblStaff.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        tblStaff.getTableHeader().setBackground(new Color(96, 125, 139));
        tblStaff.getTableHeader().setForeground(Color.WHITE);
        tblStaff.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        rowSorter = new TableRowSorter<>(tableModel);
        tblStaff.setRowSorter(rowSorter);
        
        btnAdd = createStyledButton("➕ Thêm NV", new Color(76, 175, 80));
        btnEdit = createStyledButton("✏️ Sửa NV", new Color(255, 152, 0));
        btnDelete = createStyledButton("🗑️ Xóa NV", new Color(244, 67, 54));
        btnResetPassword = createStyledButton("🔑 Reset MK", new Color(33, 150, 243));
        btnRefresh = createStyledButton("🔄 Làm Mới", new Color(96, 125, 139));
        
        btnEdit.setEnabled(false);
        btnDelete.setEnabled(false);
        btnResetPassword.setEnabled(false);
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
        
        JLabel lblTitle = new JLabel("👨‍💼 QUẢN LÝ NHÂN VIÊN");
        lblTitle.setFont(new Font("Segoe UI", Font.BOLD, 26));
        lblTitle.setForeground(new Color(96, 125, 139));
        titlePanel.add(lblTitle, BorderLayout.WEST);
        
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        searchPanel.setBackground(Color.WHITE);
        searchPanel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(96, 125, 139), 2),
                "Tìm Kiếm & Lọc",
                0, 0,
                new Font("Segoe UI", Font.BOLD, 14),
                new Color(96, 125, 139)
            ),
            BorderFactory.createEmptyBorder(5, 10, 5, 10)
        ));
        
        searchPanel.add(new JLabel("🔍 Tìm kiếm:"));
        searchPanel.add(txtSearch);
        searchPanel.add(new JLabel("Vai trò:"));
        searchPanel.add(cmbRoleFilter);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT, 10, 5));
        buttonPanel.setBackground(Color.WHITE);
        
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnEdit);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnResetPassword);
        buttonPanel.add(btnRefresh);
        
        JPanel topPanel = new JPanel(new BorderLayout(10, 10));
        topPanel.setBackground(Color.WHITE);
        topPanel.add(searchPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        JScrollPane scrollPane = new JScrollPane(tblStaff);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(224, 224, 224)));
        
        add(titlePanel, BorderLayout.NORTH);
        
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(topPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
    }
    
    private void attachListeners() {
        txtSearch.addCaretListener(e -> performSearch());
        cmbRoleFilter.addActionListener(e -> performSearch());
        
        tblStaff.getSelectionModel().addListSelectionListener(e -> {
            boolean hasSelection = tblStaff.getSelectedRow() != -1;
            btnEdit.setEnabled(hasSelection);
            btnDelete.setEnabled(hasSelection);
            btnResetPassword.setEnabled(hasSelection);
        });
        
        btnAdd.addActionListener(e -> showAddStaffDialog());
        btnEdit.addActionListener(e -> showEditStaffDialog());
        btnDelete.addActionListener(e -> deleteSelectedStaff());
        btnResetPassword.addActionListener(e -> resetPassword());
        btnRefresh.addActionListener(e -> loadStaffData());
    }
    
    private void loadStaffData() {
        try {
            List<Staff> staffList = staffDAO.getAllStaff();
            tableModel.setRowCount(0);
            
            for (Staff staff : staffList) {
                Object[] row = {
                    staff.getMaNV(),
                    staff.getHoTen(),
                    staff.getSoDT(),
                    staff.getEmail() != null ? staff.getEmail() : "",
                    staff.getChucVu(),
                    staff.getUsername(),
                    staff.getRole(),
                    staff.getTrangThai()
                };
                tableModel.addRow(row);
            }
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải dữ liệu: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        String roleFilter = (String) cmbRoleFilter.getSelectedItem();
        
        RowFilter<DefaultTableModel, Object> rf = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                // Keyword search
                if (!keyword.isEmpty()) {
                    boolean found = false;
                    for (int i = 0; i < entry.getValueCount(); i++) {
                        if (entry.getStringValue(i).toLowerCase().contains(keyword)) {
                            found = true;
                            break;
                        }
                    }
                    if (!found) return false;
                }
                
                // Role filter
                if (!roleFilter.equals("Tất cả")) {
                    if (!entry.getStringValue(6).equals(roleFilter)) {
                        return false;
                    }
                }
                
                return true;
            }
        };
        
        rowSorter.setRowFilter(rf);
    }
    
    private void showAddStaffDialog() {
        StaffDialog dialog = new StaffDialog(SwingUtilities.getWindowAncestor(this), null);
        dialog.setVisible(true);
        
        if (dialog.isConfirmed()) {
            Staff newStaff = dialog.getStaff();
            try {
                staffDAO.insertStaff(newStaff);
                JOptionPane.showMessageDialog(this,
                    "Thêm nhân viên thành công!",
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
                loadStaffData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi thêm nhân viên: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void showEditStaffDialog() {
        int selectedRow = tblStaff.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblStaff.convertRowIndexToModel(selectedRow);
        String maNV = (String) tableModel.getValueAt(modelRow, 0);
        
        try {
            Staff staff = staffDAO.getStaffById(maNV);
            if (staff != null) {
                StaffDialog dialog = new StaffDialog(SwingUtilities.getWindowAncestor(this), staff);
                dialog.setVisible(true);
                
                if (dialog.isConfirmed()) {
                    Staff updatedStaff = dialog.getStaff();
                    staffDAO.updateStaff(updatedStaff);
                    JOptionPane.showMessageDialog(this,
                        "Cập nhật nhân viên thành công!",
                        "Thành Công",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadStaffData();
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi sửa nhân viên: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void deleteSelectedStaff() {
        int selectedRow = tblStaff.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblStaff.convertRowIndexToModel(selectedRow);
        String maNV = (String) tableModel.getValueAt(modelRow, 0);
        String hoTen = (String) tableModel.getValueAt(modelRow, 1);
        
        if (maNV.equals(currentStaff.getMaNV())) {
            JOptionPane.showMessageDialog(this,
                "Không thể xóa tài khoản của chính mình!",
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc chắn muốn xóa nhân viên:\n" + hoTen + " (" + maNV + ")?",
            "Xác Nhận Xóa",
            JOptionPane.YES_NO_OPTION,
            JOptionPane.WARNING_MESSAGE);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                staffDAO.deleteStaff(maNV);
                JOptionPane.showMessageDialog(this,
                    "Xóa nhân viên thành công!",
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
                loadStaffData();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi xóa: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void resetPassword() {
        int selectedRow = tblStaff.getSelectedRow();
        if (selectedRow == -1) return;
        
        int modelRow = tblStaff.convertRowIndexToModel(selectedRow);
        String maNV = (String) tableModel.getValueAt(modelRow, 0);
        String hoTen = (String) tableModel.getValueAt(modelRow, 1);
        
        String newPassword = "123456";
        
        int confirm = JOptionPane.showConfirmDialog(this,
            "Reset mật khẩu cho " + hoTen + " về: " + newPassword + "?",
            "Xác Nhận Reset Mật Khẩu",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                staffDAO.resetPassword(maNV, newPassword);
                JOptionPane.showMessageDialog(this,
                    "Reset mật khẩu thành công!\nMật khẩu mới: " + newPassword,
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi reset mật khẩu: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
