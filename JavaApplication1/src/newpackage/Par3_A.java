/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package newpackage;
public class Par3_A extends javax.swing.JFrame {

    private static final java.util.logging.Logger logger =
            java.util.logging.Logger.getLogger(Par3_A.class.getName());

    // Store selected date
    private java.util.Date selectedDob;

    public Par3_A() {
        initComponents();

        // Fix JCalendar size
        jCalendar2.setPreferredSize(
                new java.awt.Dimension(300, 220)
        );

        // Hide week number column
        jCalendar2.getDayChooser()
                .setWeekOfYearVisible(false);

        // Set today's date
        selectedDob = new java.util.Date();
        jCalendar2.setDate(selectedDob);

        // Detect whenever user selects a different date
        jCalendar2.addPropertyChangeListener("calendar", evt -> {

            selectedDob = jCalendar2.getDate();

            if (selectedDob != null) {
                java.text.SimpleDateFormat sdf =
                        new java.text.SimpleDateFormat("dd-MM-yyyy");

                System.out.println(
                        "Selected Date: " + sdf.format(selectedDob)
                );
            }
        });
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jTextField9 = new javax.swing.JTextField();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jTextField12 = new javax.swing.JTextField();
        jComboBox1 = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();   // RESET BUTTON
        jCalendar2 = new com.toedter.calendar.JCalendar();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jTextField1.setText("Erno:");
        jTextField1.addActionListener(this::jTextField1ActionPerformed);

        jTextField2.setText("Name:");
        jTextField2.addActionListener(this::jTextField2ActionPerformed);

        jTextField5.setText("Email:");
        jTextField5.addActionListener(this::jTextField5ActionPerformed);

        jTextField7.setText("Dob:");
        jTextField7.addActionListener(this::jTextField7ActionPerformed);

        jTextField8.setText("Gender");
        jTextField8.addActionListener(this::jTextField8ActionPerformed);

        buttonGroup1.add(jRadioButton1);
        jRadioButton1.setText("Male");
        jRadioButton1.setCursor(
                new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR)
        );

        buttonGroup1.add(jRadioButton2);
        jRadioButton2.setText("Female");
        jRadioButton2.addActionListener(this::jRadioButton2ActionPerformed);

        jTextField9.setText("Password:");
        jTextField9.addActionListener(this::jTextField9ActionPerformed);

        jTextField11.setText("Address:");
        jTextField11.addActionListener(this::jTextField11ActionPerformed);

        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTextField12.setText("Class:");
        jTextField12.addActionListener(this::jTextField12ActionPerformed);

        jComboBox1.setModel(
                new javax.swing.DefaultComboBoxModel<>(
                        new String[]{"EV1", "EV2", "EV3", "EV4"}
                )
        );

        // SUBMIT BUTTON
        jButton1.setText("Submit");
        jButton1.addActionListener(this::jButton1ActionPerformed);

        // RESET BUTTON
        jButton2.setText("Reset");
        jButton2.addActionListener(this::jButton2ActionPerformed);

        javax.swing.GroupLayout layout =
                new javax.swing.GroupLayout(getContentPane());

        getContentPane().setLayout(layout);

        layout.setHorizontalGroup(
            layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.LEADING
            )
            .addGroup(
                layout.createSequentialGroup()
                .addGroup(
                    layout.createParallelGroup(
                            javax.swing.GroupLayout.Alignment.LEADING
                    )
                    .addGroup(
                        layout.createSequentialGroup()
                        .addGap(115, 115, 115)
                        .addGroup(
                            layout.createParallelGroup(
                                    javax.swing.GroupLayout.Alignment.LEADING
                            )
                            .addGroup(
                                layout.createSequentialGroup()
                                .addComponent(
                                    jTextField12,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    71,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                )
                                .addComponent(
                                    jComboBox1,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    javax.swing.GroupLayout.DEFAULT_SIZE,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                            )
                            .addGroup(
                                layout.createSequentialGroup()
                                .addComponent(
                                    jTextField8,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    71,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addGap(18, 18, 18)
                                .addComponent(
                                    jRadioButton1,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    98,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                )
                                .addComponent(
                                    jRadioButton2,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    73,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                            )
                            .addGroup(
                                layout.createSequentialGroup()
                                .addComponent(
                                    jTextField5,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    71,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                )
                                .addComponent(
                                    jTextField6,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    132,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                            )
                            .addGroup(
                                layout.createParallelGroup(
                                    javax.swing.GroupLayout.Alignment.TRAILING
                                )
                                .addComponent(
                                    jTextField3,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    132,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addGroup(
                                    layout.createSequentialGroup()
                                    .addComponent(
                                        jTextField1,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        71,
                                        javax.swing.GroupLayout.PREFERRED_SIZE
                                    )
                                    .addPreferredGap(
                                        javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                    )
                                    .addComponent(
                                        jTextField4,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        132,
                                        javax.swing.GroupLayout.PREFERRED_SIZE
                                    )
                                )
                            )
                            .addGroup(
                                layout.createSequentialGroup()
                                .addComponent(
                                    jTextField9,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    71,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                                .addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                )
                                .addComponent(
                                    jTextField10,
                                    javax.swing.GroupLayout.PREFERRED_SIZE,
                                    132,
                                    javax.swing.GroupLayout.PREFERRED_SIZE
                                )
                            )
                            .addGroup(
                                layout.createSequentialGroup()
                                .addGroup(
                                    layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING
                                    )
                                    .addComponent(
                                        jTextField7,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        71,
                                        javax.swing.GroupLayout.PREFERRED_SIZE
                                    )
                                    .addComponent(
                                        jTextField11,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        71,
                                        javax.swing.GroupLayout.PREFERRED_SIZE
                                    )
                                )
                                .addPreferredGap(
                                    javax.swing.LayoutStyle.ComponentPlacement.RELATED
                                )
                                .addGroup(
                                    layout.createParallelGroup(
                                        javax.swing.GroupLayout.Alignment.LEADING
                                    )
                                    .addComponent(
                                        jScrollPane1,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        javax.swing.GroupLayout.DEFAULT_SIZE,
                                        javax.swing.GroupLayout.PREFERRED_SIZE
                                    )
                                    .addComponent(
                                        jCalendar2,
                                        javax.swing.GroupLayout.PREFERRED_SIZE,
                                        299,
                                        javax.swing.GroupLayout.PREFERRED_SIZE
                                    )
                                )
                            )
                        )
                    )

                    // SUBMIT + RESET BUTTONS
                    .addGroup(
                        layout.createSequentialGroup()
                        .addGap(245, 245, 245)
                        .addComponent(jButton1)
                        .addGap(15, 15, 15)
                        .addComponent(jButton2)
                    )
                )
                .addContainerGap(163, Short.MAX_VALUE)
            )
            .addGroup(
                layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.LEADING
                )
                .addGroup(
                    layout.createSequentialGroup()
                    .addGap(113, 113, 113)
                    .addComponent(
                        jTextField2,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        71,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addContainerGap(470, Short.MAX_VALUE)
                )
            )
        );

        layout.setVerticalGroup(
            layout.createParallelGroup(
                javax.swing.GroupLayout.Alignment.LEADING
            )
            .addGroup(
                layout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(
                    jTextField3,
                    javax.swing.GroupLayout.PREFERRED_SIZE,
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    javax.swing.GroupLayout.PREFERRED_SIZE
                )
                .addGap(18, 18, 18)
                .addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE
                    )
                    .addComponent(
                        jTextField4,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addComponent(
                        jTextField1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                )
                .addGap(18, 18, 18)
                .addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE
                    )
                    .addComponent(
                        jTextField5,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addComponent(
                        jTextField6,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                )
                .addGap(18, 18, 18)
                .addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE
                    )
                    .addComponent(
                        jTextField9,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addComponent(
                        jTextField10,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                )
                .addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.UNRELATED
                )
                .addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE
                    )
                    .addComponent(
                        jTextField12,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addComponent(
                        jComboBox1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                )
                .addGap(9, 9, 9)
                .addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE
                    )
                    .addComponent(
                        jTextField8,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2)
                )
                .addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING
                    )
                    .addGroup(
                        layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(
                            jTextField7,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            javax.swing.GroupLayout.DEFAULT_SIZE,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                    )
                    .addGroup(
                        layout.createSequentialGroup()
                        .addPreferredGap(
                            javax.swing.LayoutStyle.ComponentPlacement.UNRELATED
                        )
                        .addComponent(
                            jCalendar2,
                            javax.swing.GroupLayout.PREFERRED_SIZE,
                            252,
                            javax.swing.GroupLayout.PREFERRED_SIZE
                        )
                    )
                )
                .addGap(31, 31, 31)
                .addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.LEADING
                    )
                    .addComponent(
                        jScrollPane1,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addComponent(
                        jTextField11,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                )
                .addPreferredGap(
                    javax.swing.LayoutStyle.ComponentPlacement.RELATED
                )

                // SUBMIT + RESET
                .addGroup(
                    layout.createParallelGroup(
                        javax.swing.GroupLayout.Alignment.BASELINE
                    )
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                )

                .addContainerGap(
                    javax.swing.GroupLayout.DEFAULT_SIZE,
                    Short.MAX_VALUE
                )
            )
            .addGroup(
                layout.createParallelGroup(
                    javax.swing.GroupLayout.Alignment.LEADING
                )
                .addGroup(
                    layout.createSequentialGroup()
                    .addGap(37, 37, 37)
                    .addComponent(
                        jTextField2,
                        javax.swing.GroupLayout.PREFERRED_SIZE,
                        javax.swing.GroupLayout.DEFAULT_SIZE,
                        javax.swing.GroupLayout.PREFERRED_SIZE
                    )
                    .addContainerGap(600, Short.MAX_VALUE)
                )
            )
        );

        pack();
    }// </editor-fold>

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField5ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField7ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField8ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jRadioButton2ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField9ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField11ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void jTextField12ActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    // SUBMIT BUTTON
    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {

        // Get values
        String erno = jTextField4.getText().trim();
        String name = jTextField3.getText().trim();
        String email = jTextField6.getText().trim();
        String password = jTextField10.getText().trim();
        String address = jTextArea1.getText().trim();

        // 1. Enrollment Number Validation
        if (erno.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter Enrollment Number",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            jTextField4.requestFocus();
            return;
        }

        if (!erno.matches("[0-9]+")) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Enrollment Number should contain only numbers",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            jTextField4.requestFocus();
            return;
        }

        // 2. Name Validation
        if (name.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter Name",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            jTextField3.requestFocus();
            return;
        }

        if (!name.matches("[a-zA-Z ]+")) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Name should contain only alphabets",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            jTextField3.requestFocus();
            return;
        }

        // 3. Email Validation
        if (email.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter Email",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            jTextField6.requestFocus();
            return;
        }

        if (!email.matches(
                "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter a valid Email",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            jTextField6.requestFocus();
            return;
        }

        // 4. Password Validation
        if (password.isEmpty()) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter Password",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            jTextField10.requestFocus();
            return;
        }

        if (password.length() < 6) {
            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Password must be at least 6 characters",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );
            jTextField10.requestFocus();
            return;
        }

        // 5. Class Validation
        if (jComboBox1.getSelectedItem() == null) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select Class",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            jComboBox1.requestFocus();
            return;
        }

        String selectedClass =
                jComboBox1.getSelectedItem().toString();

        // 6. Gender Validation
        String gender = "";

        if (jRadioButton1.isSelected()) {
            gender = "Male";
        } else if (jRadioButton2.isSelected()) {
            gender = "Female";
        }

        if (gender.isEmpty()) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select Gender",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // 7. Date of Birth Validation
        java.util.Date dob = selectedDob;

        if (dob == null) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please select Date of Birth",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        java.util.Date today = new java.util.Date();

        if (dob.after(today)) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Date of Birth cannot be in the future",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Format DOB
        java.text.SimpleDateFormat sdf =
                new java.text.SimpleDateFormat("dd-MM-yyyy");

        String dobText = sdf.format(dob);

        // 8. Address Validation
        if (address.isEmpty()) {

            javax.swing.JOptionPane.showMessageDialog(
                    this,
                    "Please enter Address",
                    "Validation Error",
                    javax.swing.JOptionPane.ERROR_MESSAGE
            );

            jTextArea1.requestFocus();
            return;
        }

        // Registration Successful
        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Registration Successful!\n\n"
                + "Enrollment No: " + erno + "\n"
                + "Name: " + name + "\n"
                + "Email: " + email + "\n"
                + "Class: " + selectedClass + "\n"
                + "Gender: " + gender + "\n"
                + "Date of Birth: " + dobText,
                "Success",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );

        // Clear form after successful registration
        clearForm();
    }

    // RESET BUTTON
    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {

        clearForm();

        javax.swing.JOptionPane.showMessageDialog(
                this,
                "Form Cleared Successfully!",
                "Reset",
                javax.swing.JOptionPane.INFORMATION_MESSAGE
        );
    }

    // CLEAR FORM METHOD
    private void clearForm() {

        // Clear text fields
        jTextField4.setText("");     // Enrollment No
        jTextField3.setText("");     // Name
        jTextField6.setText("");     // Email
        jTextField10.setText("");    // Password

        // Clear address
        jTextArea1.setText("");

        // Clear gender
        buttonGroup1.clearSelection();

        // Reset class
        jComboBox1.setSelectedIndex(0);

        // Reset calendar to today's date
        selectedDob = new java.util.Date();
        jCalendar2.setDate(selectedDob);

        // Focus on enrollment number
        jTextField4.requestFocus();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        /* Set the Nimbus look and feel */

        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code ">
        try {

            for (
                javax.swing.UIManager.LookAndFeelInfo info :
                javax.swing.UIManager.getInstalledLookAndFeels()
            ) {

                if ("Nimbus".equals(info.getName())) {

                    javax.swing.UIManager.setLookAndFeel(
                            info.getClassName()
                    );

                    break;
                }
            }

        } catch (
                ReflectiveOperationException |
                javax.swing.UnsupportedLookAndFeelException ex
        ) {

            logger.log(
                    java.util.logging.Level.SEVERE,
                    null,
                    ex
            );
        }

        //</editor-fold>

        /* Create and display the form */

        java.awt.EventQueue.invokeLater(
                () -> new Par3_A().setVisible(true)
        );
    }

    // Variables declaration - do not modify
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;       // RESET
    private com.toedter.calendar.JCalendar jCalendar2;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    // End of variables declaration
}