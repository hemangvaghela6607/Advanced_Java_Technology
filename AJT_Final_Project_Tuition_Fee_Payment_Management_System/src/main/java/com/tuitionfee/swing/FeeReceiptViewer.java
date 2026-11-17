package com.tuitionfee.swing;

import com.tuitionfee.util.DBConnection;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * Standalone Java Swing Desktop Utility: "Fee Receipt Viewer".
 * Connects directly to MySQL using JDBC to query, search, and view
 * payment records and fee receipts by Receipt Number or Student Roll Number.
 * 
 * Can be run directly from Apache NetBeans via:
 * Right Click -> Run File (Shift+F6).
 */
public class FeeReceiptViewer extends JFrame {

    private JTextField searchField;
    private JComboBox<String> searchTypeCombo;
    private JTable paymentTable;
    private DefaultTableModel tableModel;
    private JLabel statusLabel;

    public FeeReceiptViewer() {
        setTitle("Tuition Fee Management - Desktop Receipt Viewer");
        setSize(950, 550);
        setMinimumSize(new Dimension(800, 450));
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        initUI();
        loadAllPayments(); // Initial load
    }

    private void initUI() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(new EmptyBorder(15, 15, 15, 15));
        mainPanel.setBackground(new Color(245, 247, 250));

        // 1. Header Panel
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(30, 41, 59));
        headerPanel.setBorder(new EmptyBorder(12, 15, 12, 15));

        JLabel titleLabel = new JLabel("Fee Receipt & Payment Viewer (Admin Utility)");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        titleLabel.setForeground(Color.WHITE);

        JLabel subtitleLabel = new JLabel("AJT Project - Standalone Swing Desktop Client (JDBC)");
        subtitleLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        subtitleLabel.setForeground(new Color(148, 163, 184));

        headerPanel.add(titleLabel, BorderLayout.NORTH);
        headerPanel.add(subtitleLabel, BorderLayout.SOUTH);
        mainPanel.add(headerPanel, BorderLayout.NORTH);

        // 2. Search & Controls Bar
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 8));
        controlsPanel.setBackground(Color.WHITE);
        controlsPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                new EmptyBorder(5, 5, 5, 5)
        ));

        controlsPanel.add(new JLabel("Search By:"));
        searchTypeCombo = new JComboBox<>(new String[]{"Receipt Number", "Student Roll Number", "Student Name"});
        searchTypeCombo.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        controlsPanel.add(searchTypeCombo);

        searchField = new JTextField(18);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        controlsPanel.add(searchField);

        JButton searchBtn = new JButton("Search");
        searchBtn.setBackground(new Color(37, 99, 235));
        searchBtn.setForeground(Color.BLACK);
        searchBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        searchBtn.setFocusPainted(false);
        controlsPanel.add(searchBtn);

        JButton viewAllBtn = new JButton("View All");
        viewAllBtn.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        controlsPanel.add(viewAllBtn);

        JButton detailsBtn = new JButton("View Selected Receipt");
        detailsBtn.setFont(new Font("Segoe UI", Font.BOLD, 12));
        controlsPanel.add(detailsBtn);

        // 3. Table Panel
        String[] columnNames = {
                "Receipt #", "Roll Number", "Student Name", "Course", 
                "Amount Paid (₹)", "Payment Date", "Method", "Status", "Transaction Ref"
        };
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make read-only
            }
        };

        paymentTable = new JTable(tableModel);
        paymentTable.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        paymentTable.setRowHeight(26);
        paymentTable.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        paymentTable.getTableHeader().setBackground(new Color(241, 245, 249));

        // Center align specific columns
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        paymentTable.getColumnModel().getColumn(0).setCellRenderer(centerRenderer);
        paymentTable.getColumnModel().getColumn(1).setCellRenderer(centerRenderer);
        paymentTable.getColumnModel().getColumn(4).setCellRenderer(centerRenderer);
        paymentTable.getColumnModel().getColumn(7).setCellRenderer(centerRenderer);

        JScrollPane scrollPane = new JScrollPane(paymentTable);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240)));

        JPanel centerPanel = new JPanel(new BorderLayout(0, 8));
        centerPanel.setOpaque(false);
        centerPanel.add(controlsPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(centerPanel, BorderLayout.CENTER);

        // 4. Status Bar
        statusLabel = new JLabel(" Ready. Connected to MySQL database.");
        statusLabel.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        statusLabel.setBorder(new EmptyBorder(5, 0, 0, 0));
        statusLabel.setForeground(new Color(100, 116, 139));
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);

        // Action Listeners
        searchBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        searchField.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                performSearch();
            }
        });

        viewAllBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchField.setText("");
                loadAllPayments();
            }
        });

        detailsBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                showSelectedReceiptDetails();
            }
        });
    }

    private void loadAllPayments() {
        String sql = "SELECT p.receipt_number, s.roll_number, u.name, c.course_name, " +
                     "p.amount_paid, p.payment_date, p.payment_method, p.payment_status, p.transaction_reference " +
                     "FROM payments p " +
                     "JOIN fee_assignments fa ON p.assignment_id = fa.assignment_id " +
                     "JOIN students s ON fa.student_id = s.student_id " +
                     "JOIN users u ON s.user_id = u.user_id " +
                     "LEFT JOIN courses c ON s.course_id = c.course_id " +
                     "ORDER BY p.payment_date DESC";
        executeQueryAndPopulateTable(sql, null);
    }

    private void performSearch() {
        String queryText = searchField.getText().trim();
        if (queryText.isEmpty()) {
            loadAllPayments();
            return;
        }

        String selectedType = (String) searchTypeCombo.getSelectedItem();
        String sql = "SELECT p.receipt_number, s.roll_number, u.name, c.course_name, " +
                     "p.amount_paid, p.payment_date, p.payment_method, p.payment_status, p.transaction_reference " +
                     "FROM payments p " +
                     "JOIN fee_assignments fa ON p.assignment_id = fa.assignment_id " +
                     "JOIN students s ON fa.student_id = s.student_id " +
                     "JOIN users u ON s.user_id = u.user_id " +
                     "LEFT JOIN courses c ON s.course_id = c.course_id " +
                     "WHERE ";

        if ("Receipt Number".equals(selectedType)) {
            sql += "p.receipt_number LIKE ? ";
        } else if ("Student Roll Number".equals(selectedType)) {
            sql += "s.roll_number LIKE ? ";
        } else {
            sql += "LOWER(u.name) LIKE ? ";
        }
        sql += "ORDER BY p.payment_date DESC";

        executeQueryAndPopulateTable(sql, "%" + queryText + "%");
    }

    private void executeQueryAndPopulateTable(String sql, String parameter) {
        tableModel.setRowCount(0); // clear existing rows
        int count = 0;

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            if (parameter != null) {
                ps.setString(1, parameter);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Object[] row = {
                            rs.getString("receipt_number"),
                            rs.getString("roll_number"),
                            rs.getString("name"),
                            rs.getString("course_name"),
                            String.format("%.2f", rs.getDouble("amount_paid")),
                            rs.getString("payment_date"),
                            rs.getString("payment_method"),
                            rs.getString("payment_status"),
                            rs.getString("transaction_reference")
                    };
                    tableModel.addRow(row);
                    count++;
                }
            }
            statusLabel.setText(" Successfully loaded " + count + " payment record(s).");
            statusLabel.setForeground(new Color(22, 101, 52));
        } catch (SQLException e) {
            statusLabel.setText(" Database connection error: " + e.getMessage());
            statusLabel.setForeground(Color.RED);
            JOptionPane.showMessageDialog(this,
                    "Failed to connect to MySQL database.\nPlease ensure XAMPP MySQL is running.\nError: " + e.getMessage(),
                    "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void showSelectedReceiptDetails() {
        int selectedRow = paymentTable.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a payment record from the table.",
                    "No Selection", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String receiptNo = (String) tableModel.getValueAt(selectedRow, 0);
        String rollNo = (String) tableModel.getValueAt(selectedRow, 1);
        String name = (String) tableModel.getValueAt(selectedRow, 2);
        String course = (String) tableModel.getValueAt(selectedRow, 3);
        String amount = (String) tableModel.getValueAt(selectedRow, 4);
        String date = (String) tableModel.getValueAt(selectedRow, 5);
        String method = (String) tableModel.getValueAt(selectedRow, 6);
        String status = (String) tableModel.getValueAt(selectedRow, 7);
        String txn = (String) tableModel.getValueAt(selectedRow, 8);

        StringBuilder sb = new StringBuilder();
        sb.append("=========================================\n");
        sb.append("      TUITION FEE PAYMENT RECEIPT        \n");
        sb.append("=========================================\n\n");
        sb.append(" Receipt Number   : ").append(receiptNo).append("\n");
        sb.append(" Transaction Ref  : ").append(txn).append("\n");
        sb.append(" Date & Time      : ").append(date).append("\n\n");
        sb.append(" Student Name     : ").append(name).append("\n");
        sb.append(" Roll Number      : ").append(rollNo).append("\n");
        sb.append(" Course Enrolled  : ").append(course).append("\n\n");
        sb.append(" Amount Paid      : ₹").append(amount).append("\n");
        sb.append(" Payment Mode     : ").append(method).append("\n");
        sb.append(" Payment Status   : ").append(status).append("\n\n");
        sb.append("=========================================\n");
        sb.append(" Status: Verified & Recorded in System   \n");
        sb.append("=========================================\n");

        JTextArea textArea = new JTextArea(sb.toString(), 16, 32);
        textArea.setFont(new Font("Monospaced", Font.PLAIN, 12));
        textArea.setEditable(false);

        JOptionPane.showMessageDialog(this, new JScrollPane(textArea),
                "Receipt Details - " + receiptNo, JOptionPane.INFORMATION_MESSAGE);
    }

    /**
     * Main entry point to run the Swing Desktop application independently.
     */
    public static void main(String[] args) {
        try {
            // Set System Look and Feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {}

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new FeeReceiptViewer().setVisible(true);
            }
        });
    }
}
