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
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;

import com.library.dao.AuthorDAO;
import com.library.dao.CategoryDAO;
import com.library.dao.PublisherDAO;
import com.library.model.Book;

/**
 * Dialog for Adding/Editing Book Information
 */
public class BookDialog extends JDialog {
    
    private Book book;
    private boolean confirmed = false;
    
    // DAOs
    private AuthorDAO authorDAO;
    private CategoryDAO categoryDAO;
    private PublisherDAO publisherDAO;
    
    // Input fields
    private JTextField txtMaSach;
    private JTextField txtTenSach;
    private JComboBox<String> cboMaTG;
    private JComboBox<String> cboMaTheLoai;
    private JComboBox<String> cboMaNXB;
    private JSpinner spnNamXB;
    private JSpinner spnSoLuong;
    private JTextField txtDonGia;
    private JTextField txtViTri;
    
    // Buttons
    private JButton btnSave;
    private JButton btnCancel;
    
    public BookDialog(Window owner, Book book) {
        super(owner, book == null ? "Thêm Sách Mới" : "Sửa Thông Tin Sách", ModalityType.APPLICATION_MODAL);
        this.book = book;
        this.authorDAO = new AuthorDAO();
        this.categoryDAO = new CategoryDAO();
        this.publisherDAO = new PublisherDAO();
        
        setSize(500, 600);
        setLocationRelativeTo(owner);
        setResizable(false);
        
        initComponents();
        setupLayout();
        attachListeners();
        
        if (book != null) {
            populateFields();
        }
    }
    
    private void initComponents() {
        txtMaSach = new JTextField(20);
        txtTenSach = new JTextField(20);
        
        // Load combo boxes with data
        cboMaTG = new JComboBox<>();
        cboMaTheLoai = new JComboBox<>();
        cboMaNXB = new JComboBox<>();
        
        loadComboBoxData();
        
        spnNamXB = new JSpinner(new SpinnerNumberModel(2024, 1900, 2100, 1));
        spnSoLuong = new JSpinner(new SpinnerNumberModel(1, 0, 9999, 1));
        
        txtDonGia = new JTextField(20);
        txtViTri = new JTextField(20);
        
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
        
        if (book != null) {
            txtMaSach.setEditable(false);
            txtMaSach.setBackground(new Color(240, 240, 240));
        }
    }
    
    private void loadComboBoxData() {
        try {
            // Load authors
            Map<String, String> authors = authorDAO.getAllAuthors();
            DefaultComboBoxModel<String> authorModel = new DefaultComboBoxModel<>();
            authorModel.addElement("-- Chọn tác giả --");
            for (Map.Entry<String, String> entry : authors.entrySet()) {
                authorModel.addElement(entry.getKey() + " - " + entry.getValue());
            }
            cboMaTG.setModel(authorModel);
            
            // Load categories
            Map<String, String> categories = categoryDAO.getAllCategories();
            DefaultComboBoxModel<String> categoryModel = new DefaultComboBoxModel<>();
            categoryModel.addElement("-- Chọn thể loại --");
            for (Map.Entry<String, String> entry : categories.entrySet()) {
                categoryModel.addElement(entry.getKey() + " - " + entry.getValue());
            }
            cboMaTheLoai.setModel(categoryModel);
            
            // Load publishers
            Map<String, String> publishers = publisherDAO.getAllPublishers();
            DefaultComboBoxModel<String> publisherModel = new DefaultComboBoxModel<>();
            publisherModel.addElement("-- Chọn nhà xuất bản --");
            for (Map.Entry<String, String> entry : publishers.entrySet()) {
                publisherModel.addElement(entry.getKey() + " - " + entry.getValue());
            }
            cboMaNXB.setModel(publisherModel);
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this,
                "Không thể tải dữ liệu: " + e.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void setupLayout() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Form Panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        
        // Row 0: Mã Sách
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Mã Sách:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtMaSach, gbc);
        
        // Row 1: Tên Sách
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Tên Sách:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtTenSach, gbc);
        
        // Row 2: Mã Tác Giả
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel("Tác Giả:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cboMaTG, gbc);
        
        // Row 3: Mã Thể Loại
        gbc.gridx = 0; gbc.gridy = 3;
        formPanel.add(new JLabel("Thể Loại:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cboMaTheLoai, gbc);
        
        // Row 4: Mã NXB
        gbc.gridx = 0; gbc.gridy = 4;
        formPanel.add(new JLabel("Nhà Xuất Bản:"), gbc);
        gbc.gridx = 1;
        formPanel.add(cboMaNXB, gbc);
        
        // Row 5: Năm XB
        gbc.gridx = 0; gbc.gridy = 5;
        formPanel.add(new JLabel("Năm Xuất Bản:"), gbc);
        gbc.gridx = 1;
        formPanel.add(spnNamXB, gbc);
        
        // Row 6: Số Lượng
        gbc.gridx = 0; gbc.gridy = 6;
        formPanel.add(new JLabel("Số Lượng:"), gbc);
        gbc.gridx = 1;
        formPanel.add(spnSoLuong, gbc);
        
        // Row 7: Đơn Giá
        gbc.gridx = 0; gbc.gridy = 7;
        formPanel.add(new JLabel("Đơn Giá (VNĐ):"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtDonGia, gbc);
        
        // Row 8: Vị Trí
        gbc.gridx = 0; gbc.gridy = 8;
        formPanel.add(new JLabel("Vị Trí:"), gbc);
        gbc.gridx = 1;
        formPanel.add(txtViTri, gbc);
        
        // Button Panel
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        buttonPanel.add(btnSave);
        buttonPanel.add(btnCancel);
        
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        setContentPane(mainPanel);
    }
    
    private void attachListeners() {
        btnSave.addActionListener(e -> saveBook());
        btnCancel.addActionListener(e -> dispose());
    }
    
    private void populateFields() {
        txtMaSach.setText(book.getMaSach());
        txtTenSach.setText(book.getTenSach());
        
        // Set combo box selections
        if (book.getMaTG() != null && !book.getMaTG().isEmpty()) {
            for (int i = 0; i < cboMaTG.getItemCount(); i++) {
                String item = cboMaTG.getItemAt(i);
                if (item.startsWith(book.getMaTG() + " -")) {
                    cboMaTG.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        if (book.getMaTheLoai() != null && !book.getMaTheLoai().isEmpty()) {
            for (int i = 0; i < cboMaTheLoai.getItemCount(); i++) {
                String item = cboMaTheLoai.getItemAt(i);
                if (item.startsWith(book.getMaTheLoai() + " -")) {
                    cboMaTheLoai.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        if (book.getMaNXB() != null && !book.getMaNXB().isEmpty()) {
            for (int i = 0; i < cboMaNXB.getItemCount(); i++) {
                String item = cboMaNXB.getItemAt(i);
                if (item.startsWith(book.getMaNXB() + " -")) {
                    cboMaNXB.setSelectedIndex(i);
                    break;
                }
            }
        }
        
        spnNamXB.setValue(book.getNamXB());
        spnSoLuong.setValue(book.getSoLuong());
        txtDonGia.setText(String.valueOf(book.getDonGia()));
        txtViTri.setText(book.getViTri() != null ? book.getViTri() : "");
    }
    
    private String extractIdFromComboBox(JComboBox<String> comboBox) {
        String selected = (String) comboBox.getSelectedItem();
        if (selected == null || selected.startsWith("--")) {
            return null;
        }
        
        int dashIndex = selected.indexOf(" -");
        if (dashIndex > 0) {
            return selected.substring(0, dashIndex).trim();
        }
        
        return null;
    }
    
    private void saveBook() {
        // Validation
        if (txtMaSach.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã sách!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            txtMaSach.requestFocus();
            return;
        }
        
        if (txtTenSach.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập tên sách!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            txtTenSach.requestFocus();
            return;
        }
        
        try {
            double donGia = Double.parseDouble(txtDonGia.getText().trim());
            if (donGia < 0) {
                JOptionPane.showMessageDialog(this, "Đơn giá phải lớn hơn hoặc bằng 0!", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
                return;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Đơn giá không hợp lệ! Vui lòng nhập số.", "Lỗi nhập liệu", JOptionPane.ERROR_MESSAGE);
            txtDonGia.requestFocus();
            return;
        }
        
        // Create/Update book object
        if (book == null) {
            book = new Book();
        }
        
        book.setMaSach(txtMaSach.getText().trim());
        book.setTenSach(txtTenSach.getText().trim());
        book.setMaTG(extractIdFromComboBox(cboMaTG));
        book.setMaTheLoai(extractIdFromComboBox(cboMaTheLoai));
        book.setMaNXB(extractIdFromComboBox(cboMaNXB));
        book.setNamXB((Integer) spnNamXB.getValue());
        book.setSoLuong((Integer) spnSoLuong.getValue());
        book.setDonGia(Double.parseDouble(txtDonGia.getText().trim()));
        book.setViTri(txtViTri.getText().trim().isEmpty() ? null : txtViTri.getText().trim());
        
        confirmed = true;
        dispose();
    }
    
    public boolean isConfirmed() {
        return confirmed;
    }
    
    public Book getBook() {
        return book;
    }
}
