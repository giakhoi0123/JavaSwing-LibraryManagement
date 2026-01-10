package com.library.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.library.model.Staff;

/**
 * Dialog for Adding/Editing Staff
 */
public class StaffDialog extends JDialog {
    
    private Staff staff;
    private boolean confirmed = false;
    
    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtSoDT;
    private JTextField txtEmail;
    private JTextField txtChucVu;
    private JTextField txtUsername;
    private JPasswordField txtPassword;
    private JComboBox<String> cmbRole;
    private JComboBox<String> cmbTrangThai;
    
    private JButton btnSave;
    private JButton btnCancel;
    
    public StaffDialog(Window owner, Staff staff) {
        super(owner, staff == null ? "Thêm Nhân Viên Mới" : "Sửa Thông Tin Nhân Viên", ModalityType.APPLICATION_MODAL);
        this.staff = staff;
        
        setSize(450, 550);
        setLocationRelativeTo(owner);
        setResizable(false);
        
        initComponents();
        setupLayout();
        attachListeners();
        
        if (staff != null) {
            populateFields();
        }
    }
    
    private void initComponents() {
        txtMaNV = new JTextField(20);
        txtHoTen = new JTextField(20);
        txtSoDT = new JTextField(20);
        txtEmail = new JTextField(20);
        txtChucVu = new JTextField(20);
        txtUsername = new JTextField(20);
        txtPassword = new JPasswordField(20);
        
        cmbRole = new JComboBox<>(new String[]{"Librarian", "Admin"});
        cmbTrangThai = new JComboBox<>(new String[]{"Active", "Inactive"});
        
        btnSave = new JButton("💾 Lưu");
        btnSave.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSave.setBackground(new Color(76, 175, 80));
        btnSave.setForeground(Color.WHITE);
        btnSave.setFocusPainted(false);
        
        btnCancel = new JButton("❌ Hủy");
        btnCancel.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnCancel.setBackground(new Color(244, 67, 54));
        btnCancel.setForeground(Color.WHITE);
        btnCancel.setFocusPainted(false);
        
        if (staff != null) {
            txtMaNV.setEditable(false);
            txtMaNV.setBackground(new Color(240, 240, 240));
            txtUsername.setEditable(false);
            txtUsername.setBackground(new Color(240, 240, 240));
        }
    }
    
    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(6, 8, 6, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        int row = 0;
        
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Mã NV:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtMaNV, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Họ Tên:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtHoTen, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("SĐT:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtSoDT, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Chức Vụ:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtChucVu, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Username:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtUsername, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Password:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtPassword, gbc);
        
        if (staff != null) {
            JLabel lblNote = new JLabel("(Để trống nếu không đổi mật khẩu)");
            lblNote.setFont(new Font("Segoe UI", Font.ITALIC, 10));
            lblNote.setForeground(Color.GRAY);
            row++;
            gbc.gridx = 1; gbc.gridy = row;
            formPanel.add(lblNote, gbc);
        }
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Role:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbRole, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Trạng Thái:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbTrangThai, gbc);
        
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void attachListeners() {
        btnSave.addActionListener(e -> saveStaff());
        btnCancel.addActionListener(e -> dispose());
    }
    
    private void populateFields() {
        txtMaNV.setText(staff.getMaNV());
        txtHoTen.setText(staff.getHoTen());
        txtSoDT.setText(staff.getSoDT());
        txtEmail.setText(staff.getEmail() != null ? staff.getEmail() : "");
        txtChucVu.setText(staff.getChucVu());
        txtUsername.setText(staff.getUsername());
        cmbRole.setSelectedItem(staff.getRole());
        cmbTrangThai.setSelectedItem(staff.getTrangThai());
    }
    
    private void saveStaff() {
        // Validation
        if (txtMaNV.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã nhân viên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (txtSoDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        if (txtUsername.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập username!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        String password = new String(txtPassword.getPassword());
        if (staff == null && password.trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mật khẩu!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return;
        }
        
        // Create/Update staff
        if (staff == null) {
            staff = new Staff();
        }
        
        staff.setMaNV(txtMaNV.getText().trim());
        staff.setHoTen(txtHoTen.getText().trim());
        staff.setSoDT(txtSoDT.getText().trim());
        staff.setEmail(txtEmail.getText().trim().isEmpty() ? null : txtEmail.getText().trim());
        staff.setChucVu(txtChucVu.getText().trim());
        staff.setUsername(txtUsername.getText().trim());
        
        if (!password.trim().isEmpty()) {
            staff.setPassword(password);
        }
        
        staff.setRole((String) cmbRole.getSelectedItem());
        staff.setTrangThai((String) cmbTrangThai.getSelectedItem());
        
        confirmed = true;
        dispose();
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Staff getStaff() {
        return staff;
    }
}
