package com.library.view;

import com.library.dao.TicketFineDAO;
import com.library.model.Staff;
import com.library.model.TicketFine;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Fine Management Panel
 * Allows staff to view and manage fines (payment collection)
 */
public class FineManagementPanel extends JPanel {
    
    private Staff currentStaff;
    private TicketFineDAO fineDAO;
    
    // UI Components
    private JTable tblFines;
    private DefaultTableModel tableModel;
    private TableRowSorter<DefaultTableModel> sorter;
    
    private JTextField txtSearch;
    private JComboBox<String> cmbStatus;
    private JButton btnSearch;
    private JButton btnRefresh;
    private JButton btnCollectPayment;
    private JButton btnViewDetails;
    
    private JLabel lblTotalUnpaid;
    private JLabel lblTotalPaid;
    private JLabel lblTotalAll;
    
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
    public FineManagementPanel(Staff staff) {
        this.currentStaff = staff;
        this.fineDAO = new TicketFineDAO();
        
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        initComponents();
        loadFines();
        updateStatistics();
    }
    
    private void initComponents() {
        // === TOP PANEL - Statistics ===
        JPanel statsPanel = createStatisticsPanel();
        add(statsPanel, BorderLayout.NORTH);
        
        // === CENTER PANEL - Table ===
        JPanel centerPanel = new JPanel(new BorderLayout(0, 10));
        
        // Search panel
        JPanel searchPanel = createSearchPanel();
        centerPanel.add(searchPanel, BorderLayout.NORTH);
        
        // Table
        String[] columns = {"Mã PP", "Mã ĐG", "Tên Độc Giả", "Mã PM", "Ngày Lập", 
                           "Tiền Phạt Trễ", "Tiền Phạt Hư Hỏng", "Tổng Tiền", 
                           "Trạng Thái", "Ngày Thanh Toán"};
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        
        tblFines = new JTable(tableModel);
        tblFines.setRowHeight(30);
        tblFines.getTableHeader().setFont(new Font("Arial", Font.BOLD, 12));
        tblFines.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        
        sorter = new TableRowSorter<>(tableModel);
        tblFines.setRowSorter(sorter);
        
        JScrollPane scrollPane = new JScrollPane(tblFines);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);
        
        // === BOTTOM PANEL - Actions ===
        JPanel actionsPanel = createActionsPanel();
        add(actionsPanel, BorderLayout.SOUTH);
    }
    
    private JPanel createStatisticsPanel() {
        JPanel panel = new JPanel(new GridLayout(1, 3, 15, 0));
        panel.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder("📊 Thống Kê"),
            BorderFactory.createEmptyBorder(10, 10, 10, 10)
        ));
        
        lblTotalUnpaid = new JLabel("Chưa thanh toán: 0 VNĐ", SwingConstants.CENTER);
        lblTotalUnpaid.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalUnpaid.setForeground(new Color(220, 53, 69));
        
        lblTotalPaid = new JLabel("Đã thanh toán: 0 VNĐ", SwingConstants.CENTER);
        lblTotalPaid.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalPaid.setForeground(new Color(40, 167, 69));
        
        lblTotalAll = new JLabel("Tổng cộng: 0 VNĐ", SwingConstants.CENTER);
        lblTotalAll.setFont(new Font("Arial", Font.BOLD, 14));
        lblTotalAll.setForeground(new Color(0, 123, 255));
        
        panel.add(lblTotalUnpaid);
        panel.add(lblTotalPaid);
        panel.add(lblTotalAll);
        
        return panel;
    }
    
    private JPanel createSearchPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        
        panel.add(new JLabel("Tìm kiếm:"));
        txtSearch = new JTextField(20);
        txtSearch.setToolTipText("Nhập mã phiếu phạt, mã độc giả hoặc tên độc giả");
        panel.add(txtSearch);
        
        panel.add(new JLabel("Trạng thái:"));
        cmbStatus = new JComboBox<>(new String[]{"Tất cả", "Chưa thanh toán", "Đã thanh toán"});
        panel.add(cmbStatus);
        
        btnSearch = new JButton("🔍 Tìm");
        btnSearch.addActionListener(e -> performSearch());
        panel.add(btnSearch);
        
        btnRefresh = new JButton("🔄 Làm mới");
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            cmbStatus.setSelectedIndex(0);
            loadFines();
            updateStatistics();
        });
        panel.add(btnRefresh);
        
        return panel;
    }
    
    private JPanel createActionsPanel() {
        JPanel panel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 10));
        
        btnCollectPayment = new JButton("💰 Thu Tiền");
        btnCollectPayment.setFont(new Font("Arial", Font.BOLD, 13));
        btnCollectPayment.setBackground(new Color(40, 167, 69));
        btnCollectPayment.setForeground(Color.WHITE);
        btnCollectPayment.setFocusPainted(false);
        btnCollectPayment.addActionListener(e -> collectPayment());
        panel.add(btnCollectPayment);
        
        btnViewDetails = new JButton("📄 Xem Chi Tiết");
        btnViewDetails.addActionListener(e -> viewDetails());
        panel.add(btnViewDetails);
        
        return panel;
    }
    
    private void loadFines() {
        try {
            List<TicketFine> fines = fineDAO.getAllFines();
            tableModel.setRowCount(0);
            
            for (TicketFine fine : fines) {
                Object[] row = {
                    fine.getMaPP(),
                    fine.getMaDG(),
                    fine.getTenDocGia(),
                    fine.getMaPM() != null ? fine.getMaPM() : "N/A",
                    fine.getNgayLap() != null ? fine.getNgayLap().format(DATE_FORMATTER) : "",
                    String.format("%,.0f", fine.getTienPhatTreHan()),
                    String.format("%,.0f", fine.getTienPhatHuHong()),
                    String.format("%,.0f", fine.getTongTien()),
                    fine.getTrangThaiThanhToan(),
                    fine.getNgayThanhToan() != null ? fine.getNgayThanhToan().format(DATE_FORMATTER) : "Chưa thanh toán"
                };
                tableModel.addRow(row);
            }
            
            // Clear filters and refresh display
            txtSearch.setText("");
            cmbStatus.setSelectedIndex(0);
            sorter.setRowFilter(null);
            
            tableModel.fireTableDataChanged();
            tblFines.revalidate();
            tblFines.repaint();
            
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                "Lỗi khi tải danh sách phạt: " + ex.getMessage(),
                "Lỗi",
                JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void performSearch() {
        String keyword = txtSearch.getText().trim().toLowerCase();
        String status = (String) cmbStatus.getSelectedItem();
        
        if (keyword.isEmpty() && "Tất cả".equals(status)) {
            sorter.setRowFilter(null);
            return;
        }
        
        RowFilter<DefaultTableModel, Object> rf = new RowFilter<DefaultTableModel, Object>() {
            @Override
            public boolean include(Entry<? extends DefaultTableModel, ? extends Object> entry) {
                boolean matchKeyword = keyword.isEmpty();
                boolean matchStatus = "Tất cả".equals(status);
                
                // Check keyword match (columns 0, 1, 2)
                if (!keyword.isEmpty()) {
                    for (int i = 0; i < 3; i++) {
                        String value = entry.getStringValue(i).toLowerCase();
                        if (value.contains(keyword)) {
                            matchKeyword = true;
                            break;
                        }
                    }
                }
                
                // Check status match (column 8)
                if (!"Tất cả".equals(status)) {
                    String fineStatus = entry.getStringValue(8);
                    matchStatus = status.equals(fineStatus);
                }
                
                return matchKeyword && matchStatus;
            }
        };
        
        sorter.setRowFilter(rf);
    }
    
    private void collectPayment() {
        int selectedRow = tblFines.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một phiếu phạt để thu tiền!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int modelRow = tblFines.convertRowIndexToModel(selectedRow);
        String maPP = (String) tableModel.getValueAt(modelRow, 0);
        String trangThai = (String) tableModel.getValueAt(modelRow, 8);
        String tongTienStr = (String) tableModel.getValueAt(modelRow, 7);
        double tongTien = Double.parseDouble(tongTienStr.replace(",", ""));
        
        if ("Đã thanh toán".equals(trangThai)) {
            JOptionPane.showMessageDialog(this,
                "Phiếu phạt này đã được thanh toán!",
                "Thông báo",
                JOptionPane.INFORMATION_MESSAGE);
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this,
            String.format("Xác nhận thu tiền phạt?\n\nMã phiếu: %s\nSố tiền: %,.0f VNĐ", maPP, tongTien),
            "Xác nhận thanh toán",
            JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                boolean success = fineDAO.markAsPaid(maPP);
                if (success) {
                    JOptionPane.showMessageDialog(this,
                        "Thu tiền phạt thành công!",
                        "Thành công",
                        JOptionPane.INFORMATION_MESSAGE);
                    loadFines();
                    updateStatistics();
                } else {
                    JOptionPane.showMessageDialog(this,
                        "Không thể cập nhật trạng thái thanh toán!",
                        "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                    "Lỗi khi thu tiền: " + ex.getMessage(),
                    "Lỗi",
                    JOptionPane.ERROR_MESSAGE);
            }
        }
    }
    
    private void viewDetails() {
        int selectedRow = tblFines.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn một phiếu phạt để xem chi tiết!",
                "Thông báo",
                JOptionPane.WARNING_MESSAGE);
            return;
        }
        
        int modelRow = tblFines.convertRowIndexToModel(selectedRow);
        
        StringBuilder details = new StringBuilder();
        details.append("=== CHI TIẾT PHIẾU PHẠT ===\n\n");
        details.append(String.format("Mã phiếu phạt: %s\n", tableModel.getValueAt(modelRow, 0)));
        details.append(String.format("Mã độc giả: %s\n", tableModel.getValueAt(modelRow, 1)));
        details.append(String.format("Tên độc giả: %s\n", tableModel.getValueAt(modelRow, 2)));
        details.append(String.format("Mã phiếu mượn: %s\n", tableModel.getValueAt(modelRow, 3)));
        details.append(String.format("Ngày lập: %s\n\n", tableModel.getValueAt(modelRow, 4)));
        details.append(String.format("Tiền phạt trễ hạn: %s VNĐ\n", tableModel.getValueAt(modelRow, 5)));
        details.append(String.format("Tiền phạt hư hỏng: %s VNĐ\n", tableModel.getValueAt(modelRow, 6)));
        details.append(String.format("TỔNG TIỀN: %s VNĐ\n\n", tableModel.getValueAt(modelRow, 7)));
        details.append(String.format("Trạng thái: %s\n", tableModel.getValueAt(modelRow, 8)));
        details.append(String.format("Ngày thanh toán: %s", tableModel.getValueAt(modelRow, 9)));
        
        JTextArea textArea = new JTextArea(details.toString());
        textArea.setEditable(false);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        
        JScrollPane scrollPane = new JScrollPane(textArea);
        scrollPane.setPreferredSize(new Dimension(400, 300));
        
        JOptionPane.showMessageDialog(this,
            scrollPane,
            "Chi Tiết Phiếu Phạt",
            JOptionPane.INFORMATION_MESSAGE);
    }
    
    private void updateStatistics() {
        try {
            double totalAll = 0;
            double totalPaid = 0;
            double totalUnpaid = 0;
            
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String tongTienStr = (String) tableModel.getValueAt(i, 7);
                String trangThai = (String) tableModel.getValueAt(i, 8);
                
                double amount = Double.parseDouble(tongTienStr.replace(",", ""));
                totalAll += amount;
                
                if ("Đã thanh toán".equals(trangThai)) {
                    totalPaid += amount;
                } else {
                    totalUnpaid += amount;
                }
            }
            
            lblTotalUnpaid.setText(String.format("Chưa thanh toán: %,.0f VNĐ", totalUnpaid));
            lblTotalPaid.setText(String.format("Đã thanh toán: %,.0f VNĐ", totalPaid));
            lblTotalAll.setText(String.format("Tổng cộng: %,.0f VNĐ", totalAll));
            
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
