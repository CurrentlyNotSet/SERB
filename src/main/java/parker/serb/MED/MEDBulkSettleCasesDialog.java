/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.MEDCase;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parker.johnston
 */
public class MEDBulkSettleCasesDialog extends javax.swing.JFrame {

    DefaultTableModel model;

    /**
     * Creates new form MEDsettleCases
     *
     * @param parent
     * @param modal
     */
    public MEDBulkSettleCasesDialog(java.awt.Frame parent, boolean modal) {
        initComponents();
        setActive();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void setActive() {
        loadYearsComboBox();
        setTableSize();
        addListeners();
    }

    private void addListeners() {
        settleDateField.addDateSelectionListener((Date date) -> {
            checkProcessable();
        });
        
    }
    
    private void loadYearsComboBox() {
        List<String> yearList = MEDCase.getSettleCaseYears();
        for (String year : yearList) {
            yearComboBox.addItem(year);
        }
        loadMonthsComboBox();
    }

    private void loadMonthsComboBox() {
        monthComboBox.removeAllItems();
        monthComboBox.addItem("");
        List<String> monthList = MEDCase.getSettleCaseMonths(yearComboBox.getSelectedItem().toString());
        for (String month : monthList) {
            switch (month) {
                case "01":
                    monthComboBox.addItem("01 - January");
                    break;
                case "02":
                    monthComboBox.addItem("02 - February");
                    break;
                case "03":
                    monthComboBox.addItem("03 - March");
                    break;
                case "04":
                    monthComboBox.addItem("04 - April");
                    break;
                case "05":
                    monthComboBox.addItem("05 - May");
                    break;
                case "06":
                    monthComboBox.addItem("06 - June");
                    break;
                case "07":
                    monthComboBox.addItem("07 - July");
                    break;
                case "08":
                    monthComboBox.addItem("08 - August");
                    break;
                case "09":
                    monthComboBox.addItem("09 - September");
                    break;
                case "10":
                    monthComboBox.addItem("10 - October");
                    break;
                case "11":
                    monthComboBox.addItem("11 - November");
                    break;
                case "12":
                    monthComboBox.addItem("12 - December");
                    break;
                default:
                    break;
            }
        }
    }

    private void setTableSize() {
        //CheckBox
        caseTable.getColumnModel().getColumn(0).setMinWidth(35);
        caseTable.getColumnModel().getColumn(0).setPreferredWidth(35);
        caseTable.getColumnModel().getColumn(0).setMaxWidth(35);

        //Case Number
        caseTable.getColumnModel().getColumn(1).setMinWidth(125);
        caseTable.getColumnModel().getColumn(1).setPreferredWidth(125);
        caseTable.getColumnModel().getColumn(1).setMaxWidth(125);

        //Filed Date
        caseTable.getColumnModel().getColumn(3).setMinWidth(100);
        caseTable.getColumnModel().getColumn(3).setPreferredWidth(100);
        caseTable.getColumnModel().getColumn(3).setMaxWidth(100);

        //get Table
        model = (DefaultTableModel) caseTable.getModel();
    }

    private void clearTable() {
        model = (DefaultTableModel) caseTable.getModel();
        model.setRowCount(0);
        countLabel.setText(" ");
    }
    
    private void loadTableThread() {
        clearTable();
        jLayeredPane1.moveToFront(jPanel1);
        Thread temp = new Thread(() -> {
            loadTable();
        });
        temp.start();
    }

    private void loadTable() {
        String caseYear = yearComboBox.getSelectedItem().toString().trim();
        String caseMonth = monthComboBox.getSelectedItem().toString().trim().substring(0, 2);

        List<MEDCase> caseList = MEDCase.getSettleList(caseYear, caseMonth);

        for (MEDCase item : caseList) {
            String caseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(
                    item.caseYear, item.caseType, item.caseMonth, item.caseNumber);

            model.addRow(new Object[]{
                false,
                caseNumber,
                item.employerIDNumber,
                Global.mmddyyyy.format(item.fileDate)
            });
        }
        jLayeredPane1.moveToBack(jPanel1);
        countLabel.setText("Entries: " + caseTable.getRowCount());
    }

    private void updateList() {
        for (int i = 0; i < caseTable.getRowCount(); i++) {
            if (caseTable.getValueAt(i, 0).equals(true)) {
                String caseNumber = caseTable.getValueAt(i, 1).toString();
                Timestamp settleDate = new Timestamp(NumberFormatService.convertMMDDYYYY(settleDateField.getText()));

                MEDCase.updateSettledCases(caseNumber, settleDate);
            }
        }
    }
    
    private void checkProcessable(){
        if (yearComboBox.getSelectedItem() != null && monthComboBox.getSelectedItem() != null){
            if (!yearComboBox.getSelectedItem().toString().equals("") 
                    && !monthComboBox.getSelectedItem().toString().equals("")
                    && !settleDateField.getText().equals("")){
                updateButton.setEnabled(true);
            } else {
                updateButton.setEnabled(false);
            }
        } else {
            updateButton.setEnabled(false);
        }
    }
    
    private void printList() {
//        try {
//            HashMap para = new HashMap();
//            String startDate = JOptionPane.showInputDialog("Please enter a start date (MM/DD/YYYY)");
//            String endDate = JOptionPane.showInputDialog("Please enter an end date (MM/DD/YYYY)");
//            Connection con = global.getDba().getObjConn();
//            para.put("startDate", startDate);
//            para.put("endDate", endDate);
//            String reportPath = "G:\\XLNCMS\\SERBTemplates\\MED\\ReportTemplates\\MEDCasestobeSettled.jrxml";
//            JasperReport jr = JasperCompileManager.compileReport(reportPath);
//            JasperPrint jp = JasperFillManager.fillReport(jr, para, con);
//            JasperViewer.viewReport(jp, false);
//        } catch (JRException ex) {
//            SystemErrorNotificationEMail SENE = new SystemErrorNotificationEMail(global.getMainFrame(), true, global);
//            StringWriter errors = new StringWriter();
//            ex.printStackTrace(new PrintWriter(errors));
//            SENE.loadInformation("MEDReportsPanel", "246", errors.toString());
//        }
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
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        monthComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        countLabel = new javax.swing.JLabel();
        settleDateField = new com.alee.extended.date.WebDateField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        caseTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update");
        updateButton.setEnabled(false);
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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Settle MED cases");

        jLabel2.setText("Enter Settle Date:");

        monthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "01 - January", "02 - February", "03 - March", "04 - April", "05 - May", "06 - June", "07 - July", "08 - August", "09 - September", "10 - October", "11 - November", "12 - December" }));
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

        settleDateField.setEditable(false);
        settleDateField.setCaretColor(new java.awt.Color(0, 0, 0));
        settleDateField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        settleDateField.setDateFormat(Global.mmddyyyy);

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

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

        jLayeredPane1.add(jScrollPane1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 690, Short.MAX_VALUE)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 670, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 422, Short.MAX_VALUE)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                    .addContainerGap()))
        );

        jLayeredPane1.add(jPanel1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                                .add(jLabel2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(settleDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 157, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(printButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(org.jdesktop.layout.GroupLayout.TRAILING, countLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(closeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(updateButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .add(layout.createSequentialGroup()
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 42, Short.MAX_VALUE)
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(yearComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 161, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(113, 113, 113)
                        .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 86, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(monthComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 161, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(65, Short.MAX_VALUE))))
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLayeredPane1)
                    .addContainerGap()))
        );

        layout.linkSize(new java.awt.Component[] {closeButton, updateButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(yearComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel3)
                    .add(monthComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .add(jLabel4))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(countLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 440, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(printButton)
                    .add(jLabel2)
                    .add(settleDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .add(44, 44, 44)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(closeButton)
                    .add(updateButton))
                .addContainerGap())
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(100, 100, 100)
                    .add(jLayeredPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 422, Short.MAX_VALUE)
                    .add(116, 116, 116)))
        );

        layout.linkSize(new java.awt.Component[] {jLabel3, jLabel4, monthComboBox, yearComboBox}, org.jdesktop.layout.GroupLayout.VERTICAL);

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
        loadTableThread();
    }//GEN-LAST:event_updateButtonActionPerformed

    private void yearComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_yearComboBoxActionPerformed
        if (!"".equals(yearComboBox.getSelectedItem().toString().trim())) {
            loadMonthsComboBox();
            clearTable();
        }
        checkProcessable();
    }//GEN-LAST:event_yearComboBoxActionPerformed

    private void monthComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_monthComboBoxActionPerformed
        if (monthComboBox.getSelectedItem() != null){
                if (!"".equals(yearComboBox.getSelectedItem().toString().trim())
                    && !"".equals(monthComboBox.getSelectedItem().toString().trim())) {
                loadTableThread();
            } else {
                clearTable();
            }
        }
        checkProcessable();
    }//GEN-LAST:event_monthComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable caseTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel countLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox monthComboBox;
    private javax.swing.JButton printButton;
    private com.alee.extended.date.WebDateField settleDateField;
    private javax.swing.JButton updateButton;
    private javax.swing.JComboBox yearComboBox;
    // End of variables declaration//GEN-END:variables
}
