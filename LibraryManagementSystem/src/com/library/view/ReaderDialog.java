package com.library.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Window;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerDateModel;

import com.library.dao.ReaderDAO;
import com.library.model.Reader;
import com.library.util.ValidationUtil;

/**
 * Dialog for Adding/Editing Reader Information
 */
public class ReaderDialog extends JDialog {
    
    private Reader reader;
    private boolean confirmed = false;
    private ReaderDAO readerDAO;
    
    private JTextField txtMaDG;
    private JTextField txtHoTen;
    private JSpinner spnNgaySinh;
    private JComboBox<String> cmbGioiTinh;
    private JTextField txtSoDT;
    private JTextField txtEmail;
    private JTextField txtDiaChi;
    private JSpinner spnNgayLapThe;
    private JSpinner spnNgayHetHan;
    private JComboBox<String> cmbTrangThai;
    
    private JButton btnSave;
    private JButton btnCancel;
    
    public ReaderDialog(Window owner, Reader reader) {
        super(owner, reader == null ? "Thêm Độc Giả Mới" : "Sửa Thông Tin Độc Giả", ModalityType.APPLICATION_MODAL);
        this.reader = reader;
        this.readerDAO = new ReaderDAO();
        
        setSize(500, 650);
        setLocationRelativeTo(owner);
        setResizable(false);
        
        initComponents();
        setupLayout();
        attachListeners();
        
        if (reader != null) {
            populateFields();
            txtMaDG.setEditable(false); // Không cho sửa mã khi edit
        }
    }
    
    private void initComponents() {
        txtMaDG = new JTextField(20);
        txtHoTen = new JTextField(20);
        
        SpinnerDateModel birthModel = new SpinnerDateModel();
        spnNgaySinh = new JSpinner(birthModel);
        JSpinner.DateEditor birthEditor = new JSpinner.DateEditor(spnNgaySinh, "dd/MM/yyyy");
        spnNgaySinh.setEditor(birthEditor);
        
        cmbGioiTinh = new JComboBox<>(new String[]{"Nam", "Nữ", "Khác"});
        
        txtSoDT = new JTextField(20);
        txtEmail = new JTextField(20);
        txtDiaChi = new JTextField(20);
        
        SpinnerDateModel lapTheModel = new SpinnerDateModel();
        spnNgayLapThe = new JSpinner(lapTheModel);
        JSpinner.DateEditor lapTheEditor = new JSpinner.DateEditor(spnNgayLapThe, "dd/MM/yyyy");
        spnNgayLapThe.setEditor(lapTheEditor);
        
        SpinnerDateModel hetHanModel = new SpinnerDateModel();
        spnNgayHetHan = new JSpinner(hetHanModel);
        JSpinner.DateEditor hetHanEditor = new JSpinner.DateEditor(spnNgayHetHan, "dd/MM/yyyy");
        spnNgayHetHan.setEditor(hetHanEditor);
        
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
        
        if (reader != null) {
            txtMaDG.setEditable(false);
            txtMaDG.setBackground(new Color(240, 240, 240));
        } else {
            // Set default dates for new reader
            spnNgayLapThe.setValue(new Date());
            LocalDate expiry = LocalDate.now().plusYears(1);
            spnNgayHetHan.setValue(Date.from(expiry.atStartOfDay(ZoneId.systemDefault()).toInstant()));
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
        formPanel.add(new JLabel("Mã Độc Giả:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtMaDG, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Họ Tên:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtHoTen, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Ngày Sinh:"), gbc);
        gbc.gridx = 1;
        formPanel.add(spnNgaySinh, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Giới Tính:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cmbGioiTinh, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Số Điện Thoại:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtSoDT, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Email:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtEmail, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Địa Chỉ:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDiaChi, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Ngày Lập Thẻ:"), gbc);
        gbc.gridx = 1;
        formPanel.add(spnNgayLapThe, gbc);
        
        row++;
        gbc.gridx = 0; gbc.gridy = row;
        formPanel.add(new JLabel("Ngày Hết Hạn:"), gbc);
        gbc.gridx = 1;
        formPanel.add(spnNgayHetHan, gbc);
        
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
        btnSave.addActionListener(e -> saveReader());
        btnCancel.addActionListener(e -> dispose());
    }
    
    private void populateFields() {
        txtMaDG.setText(reader.getMaDG());
        txtHoTen.setText(reader.getHoTen());
        
        if (reader.getNgaySinh() != null) {
            Date birthDate = Date.from(reader.getNgaySinh().atStartOfDay(ZoneId.systemDefault()).toInstant());
            spnNgaySinh.setValue(birthDate);
        }
        
        cmbGioiTinh.setSelectedItem(reader.getGioiTinh());
        txtSoDT.setText(reader.getSoDT());
        txtEmail.setText(reader.getEmail() != null ? reader.getEmail() : "");
        txtDiaChi.setText(reader.getDiaChi() != null ? reader.getDiaChi() : "");
        
        if (reader.getNgayLapThe() != null) {
            Date lapTheDate = Date.from(reader.getNgayLapThe().atStartOfDay(ZoneId.systemDefault()).toInstant());
            spnNgayLapThe.setValue(lapTheDate);
        }
        
        if (reader.getNgayHetHan() != null) {
            Date hetHanDate = Date.from(reader.getNgayHetHan().atStartOfDay(ZoneId.systemDefault()).toInstant());
            spnNgayHetHan.setValue(hetHanDate);
        }
        
        cmbTrangThai.setSelectedItem(reader.getTrangThai());
    }
    
    private void saveReader() {
        // Validation
        if (txtMaDG.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã độc giả!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtMaDG.requestFocus();
            return;
        }
        
        if (txtHoTen.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập họ tên!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtHoTen.requestFocus();
            return;
        }
        
        if (txtSoDT.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập số điện thoại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            txtSoDT.requestFocus();
            return;
        }
        
        if (!ValidationUtil.isValidPhoneNumber(txtSoDT.getText().trim())) {
            JOptionPane.showMessageDialog(this, 
                "Số điện thoại không hợp lệ! Phải có 10 số và bắt đầu bằng 0.", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            txtSoDT.requestFocus();
            return;
        }
        
        if (!txtEmail.getText().trim().isEmpty() && !ValidationUtil.isValidEmail(txtEmail.getText().trim())) {
            JOptionPane.showMessageDialog(this, 
                "Email không hợp lệ! Phải theo định dạng: example@domain.com", 
                "Lỗi", 
                JOptionPane.ERROR_MESSAGE);
            txtEmail.requestFocus();
            return;
        }
        
        // Create/Update reader object
        boolean isNewReader = (reader == null);
        if (isNewReader) {
            reader = new Reader();
        }
        
        reader.setMaDG(txtMaDG.getText().trim());
        reader.setHoTen(txtHoTen.getText().trim());
        
        Date birthDate = (Date) spnNgaySinh.getValue();
        reader.setNgaySinh(birthDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        
        reader.setGioiTinh((String) cmbGioiTinh.getSelectedItem());
        reader.setSoDT(txtSoDT.getText().trim());
        reader.setEmail(txtEmail.getText().trim().isEmpty() ? null : txtEmail.getText().trim());
        reader.setDiaChi(txtDiaChi.getText().trim().isEmpty() ? null : txtDiaChi.getText().trim());
        
        Date lapTheDate = (Date) spnNgayLapThe.getValue();
        reader.setNgayLapThe(lapTheDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        
        Date hetHanDate = (Date) spnNgayHetHan.getValue();
        reader.setNgayHetHan(hetHanDate.toInstant().atZone(ZoneId.systemDefault()).toLocalDate());
        
        reader.setTrangThai((String) cmbTrangThai.getSelectedItem());
        
        // Save to database
        try {
            if (isNewReader) {
                readerDAO.insertReader(reader);
                JOptionPane.showMessageDialog(this,
                    "Thêm độc giả thành công!",
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
            } else {
                readerDAO.updateReader(reader);
                JOptionPane.showMessageDialog(this,
                    "Cập nhật độc giả thành công!",
                    "Thành Công",
                    JOptionPane.INFORMATION_MESSAGE);
            }
            
            confirmed = true;
            dispose();
            
        } catch (SQLException ex) {
            String errorMsg = ex.getMessage();
            
            // Xử lý các lỗi phổ biến
            if (errorMsg.contains("Duplicate entry")) {
                if (errorMsg.contains("PRIMARY")) {
                    JOptionPane.showMessageDialog(this,
                        "Mã độc giả '" + reader.getMaDG() + "' đã tồn tại!\n" +
                        "Vui lòng nhập mã khác.",
                        "Lỗi Trùng Mã",
                        JOptionPane.ERROR_MESSAGE);
                    txtMaDG.requestFocus();
                    txtMaDG.selectAll();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Dữ liệu bị trùng: " + errorMsg,
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi lưu độc giả:\n" + errorMsg,
                    "Lỗi Database",
                    JOptionPane.ERROR_MESSAGE);
            }
            
            // KHÔNG dispose() - giữ dialog mở để user có thể sửa
        }
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Reader getReader() {
        return reader;
    }
}
