/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import parker.serb.sql.MEDCase;

/**
 *
 * @author parker.johnston
 */
public class MEDBulkSettleCasesDialog extends javax.swing.JFrame {

    Calendar cal;
    String caseYear;
    String caseMonth;
    String dateForm;
    String tableLimit;
    /**
     * Creates new form MEDsettleCases
     * @param parent
     * @param modal
     */
    public MEDBulkSettleCasesDialog(java.awt.Frame parent, boolean modal) {
        initComponents();
        setActive();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void setActive(){
        loadYearsComboBox();
        setTableSize();
    }
    
    private void loadYearsComboBox(){
        List<String> yearList = MEDCase.getSettleCaseYears();
        for (String year : yearList){
            yearComboBox.addItem(year);
        }
    }
    
    private void setTableSize(){
        //CheckBox
        caseTable.getColumnModel().getColumn(0).setMinWidth(35);
        caseTable.getColumnModel().getColumn(0).setPreferredWidth(35);
        caseTable.getColumnModel().getColumn(0).setMaxWidth(35);
        
        //Case Number
        caseTable.getColumnModel().getColumn(1).setMinWidth(125);
        caseTable.getColumnModel().getColumn(1).setPreferredWidth(125);
        caseTable.getColumnModel().getColumn(1).setMaxWidth(125);
        
        //Employer Name
        //NONE
        
        //Filed Date
        caseTable.getColumnModel().getColumn(3).setMinWidth(80);
        caseTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        caseTable.getColumnModel().getColumn(3).setMaxWidth(80);
    }
    
    private void clearTable(){
        DefaultTableModel model = (DefaultTableModel) caseTable.getModel();
        model.setRowCount(0);
        countLabel.setText(" ");
    }
    
    private void loadTable(){
        caseYear = yearComboBox.getSelectedItem().toString().trim();
        caseMonth = monthComboBox.getSelectedItem().toString().trim().substring(0, 2);
        tableLimit = caseYear + "-MED-" + caseMonth;
        clearTable();
        try {
            String sql = "SELECT medcase.CaseNumber, medcase.EmployerName, "
                    + "medcase.CaseFileDate, medcase.Status "
                    + "FROM medcase WHERE LEFT(medcase.CaseNumber, 11) = ? "
                    + "AND medcase.Active = 1 AND CBAReceivedDate = '' AND medcase.Status = 'open'";
            PreparedStatement preparedStatement = global.getDba().getObjConn().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, tableLimit);
            ResultSet action = preparedStatement.executeQuery();
            
            while(action.next())
            {                
                ((DefaultTableModel) caseTable.getModel()).addRow(new Object[]{
                    false,
                    action.getString("CaseNumber"),
                    action.getString("EmployerName"),
                    action.getString("CaseFileDate")
                });
            }
        } catch (SQLException ex) {
            Logger.getLogger(MEDBulkSettleCasesDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
        countLabel.setText("Entries: " + caseTable.getRowCount());
    }
    
    private void updateList(){
        for(int i = 0; i < caseTable.getRowCount(); i++){
            if (caseTable.getValueAt(i, 0).equals(true)) {
                try {
                    String sql = "UPDATE medcase SET "
                            + "CBAReceivedDate = ? "   //1
                            + "WHERE CaseNumber = ?";    //2
                    PreparedStatement preparedStatement = global.getDba().getObjConn().prepareStatement(sql);
                    preparedStatement.setString(1,  settleDateTextBox.getText().trim());
                    preparedStatement.setString(2,  caseTable.getValueAt(i, 1).toString());
                    preparedStatement.executeUpdate();
                } catch (SQLException ex) {
                    Logger.getLogger(AdminSQLQueries.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    private void printList(){
        try {
                HashMap para = new HashMap();
                String startDate = JOptionPane.showInputDialog("Please enter a start date (MM/DD/YYYY)");
                String endDate = JOptionPane.showInputDialog("Please enter an end date (MM/DD/YYYY)");
                Connection con = global.getDba().getObjConn();
                para.put("startDate", startDate);
                para.put("endDate", endDate);
                String reportPath = "G:\\XLNCMS\\SERBTemplates\\MED\\ReportTemplates\\MEDCasestobeSettled.jrxml";
                JasperReport jr = JasperCompileManager.compileReport(reportPath);
                JasperPrint jp = JasperFillManager.fillReport(jr, para, con);
                JasperViewer.viewReport(jp, false);
            } catch (JRException ex) {
                SystemErrorNotificationEMail SENE = new SystemErrorNotificationEMail(global.getMainFrame(), true, global);
                StringWriter errors = new StringWriter();
                ex.printStackTrace(new PrintWriter(errors));
                SENE.loadInformation("MEDReportsPanel", "246", errors.toString());
            }
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        printButton = new javax.swing.JButton();
        yearComboBox = new javax.swing.JComboBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        caseTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        settleDateTextBox = new javax.swing.JTextField();
        monthComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        countLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        printButton.setText("Print Processed Records");
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        yearComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                yearComboBoxActionPerformed(evt);
            }
        });

        caseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Case Number", "Employer", "File Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(caseTable);
        if (caseTable.getColumnModel().getColumnCount() > 0) {
            caseTable.getColumnModel().getColumn(0).setResizable(false);
            caseTable.getColumnModel().getColumn(1).setResizable(false);
            caseTable.getColumnModel().getColumn(2).setResizable(false);
            caseTable.getColumnModel().getColumn(3).setResizable(false);
        }

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Settle MED cases");

        jLabel2.setText("Enter Settle Date:");

        settleDateTextBox.setEditable(false);
        settleDateTextBox.setBackground(new java.awt.Color(255, 255, 255));
        settleDateTextBox.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        settleDateTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        settleDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        settleDateTextBox.setEnabled(false);

        monthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "01 - January", "02 - February", "03 - March", "04 - April", "05 - May", "06 - June", "07 - July", "08 - August", "09 - September", "10 - October", "11 - November", "12 - December" }));
        monthComboBox.setEnabled(false);
        monthComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                monthComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Case Year:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Case Month:");

        countLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jScrollPane1)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(jLabel2)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(settleDateTextBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(printButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, countLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createSequentialGroup()
                                .add(updateButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(416, 416, 416)
                                .add(closeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(42, 42, 42)
                                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(yearComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 161, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(monthComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 161, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .add(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {closeButton, updateButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(monthComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel4))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(yearComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(jLabel3)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(countLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 423, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(printButton)
                    .add(jLabel2)
                    .add(settleDateTextBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(45, 45, 45)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(closeButton)
                    .add(updateButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        printList();
    }//GEN-LAST:event_printButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        updateList();
        loadTable();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void yearComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboBoxActionPerformed
        if (!"".equals(yearComboBox.getSelectedItem().toString().trim()))
        {
            if (monthComboBox.isEnabled() && !"".equals(monthComboBox.getSelectedItem().toString().trim())){
                loadTable();
            } else {
                monthComboBox.setEnabled(true);
                clearTable();
            }
        } else {
            monthComboBox.setEnabled(false);
        }
    }//GEN-LAST:event_yearComboBoxActionPerformed

    private void monthComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboBoxActionPerformed
        if (!"".equals(yearComboBox.getSelectedItem().toString().trim()) 
                && !"".equals(monthComboBox.getSelectedItem().toString().trim()))
        {
            loadTable();
        } else {
            clearTable();
        }
    }//GEN-LAST:event_monthComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable caseTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel countLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox monthComboBox;
    private javax.swing.JButton printButton;
    private javax.swing.JTextField settleDateTextBox;
    private javax.swing.JButton updateButton;
    private javax.swing.JComboBox yearComboBox;
    // End of variables declaration//GEN-END:variables
}
