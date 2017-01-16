/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ULP;

import java.util.Date;
import java.util.List;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.report.GenerateReport;
import parker.serb.sql.Activity;
import parker.serb.sql.Audit;
import parker.serb.sql.SMDSDocuments;
import parker.serb.sql.ULPCase;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parker.johnston
 */
public class ULPBulkCloseCasesDialog extends javax.swing.JFrame {

    DefaultTableModel model;
    String startDate = "";
    
    /**
     * Creates new form MEDsettleCases
     * @param parent
     * @param modal
     */
    public ULPBulkCloseCasesDialog(java.awt.Frame parent, boolean modal) {
        initComponents();
        setActive();
        
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void setActive(){
        setTableSize();
        addListeners();
    }
  
    private void addListeners() {        
        startDateField.addDateSelectionListener((Date date) -> {
            if (!startDateField.getText().equals(startDate)){
                startDate = startDateField.getText();
                checkIfTableIsLoadable();
            }
        });
    }

    
    private void setTableSize(){
        //CheckBox
        caseTable.getColumnModel().getColumn(0).setMinWidth(35);
        caseTable.getColumnModel().getColumn(0).setPreferredWidth(35);
        caseTable.getColumnModel().getColumn(0).setMaxWidth(35);
        
        //ID
        caseTable.getColumnModel().getColumn(1).setMinWidth(0);
        caseTable.getColumnModel().getColumn(1).setPreferredWidth(0);
        caseTable.getColumnModel().getColumn(1).setMaxWidth(0);
        
//        //Case Number
//        caseTable.getColumnModel().getColumn(2).setMinWidth(125);
//        caseTable.getColumnModel().getColumn(2).setPreferredWidth(125);
//        caseTable.getColumnModel().getColumn(2).setMaxWidth(125);
//
//        //Filed Date
//        caseTable.getColumnModel().getColumn(4).setMinWidth(80);
//        caseTable.getColumnModel().getColumn(4).setPreferredWidth(80);
//        caseTable.getColumnModel().getColumn(4).setMaxWidth(80);
        
        //Status
        caseTable.getColumnModel().getColumn(5).setMinWidth(0);
        caseTable.getColumnModel().getColumn(5).setPreferredWidth(0);
        caseTable.getColumnModel().getColumn(5).setMaxWidth(0);
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
    
    private void checkIfTableIsLoadable(){
        if(!"".equals(startDate)){
            loadTableThread();
        }else{
            clearTable();
        }
    }
    
    private void loadTable(){
        Date start = new Date(NumberFormatService.convertMMDDYYYY(startDateField.getText()));

        List<ULPCase> caseList = ULPCase.loadULPCasesToClose(start);

        for (ULPCase item : caseList) {
            String caseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(
                    item.caseYear, item.caseType, item.caseMonth, item.caseNumber);
            
            model.addRow(new Object[]{
                false,
                item.id,
                caseNumber,
                item.barginingUnitNo.trim().equals("") ? item.employerIDNumber : item.barginingUnitNo,
                Global.mmddyyyy.format(item.fileDate),
                item.currentStatus
            });
        }
        jLayeredPane1.moveToBack(jPanel1);
        countLabel.setText("Entries: " + caseTable.getRowCount());
    }
        
    private void updateList(){
        for (int i = 0; i < caseTable.getRowCount(); i++) {
            if (caseTable.getValueAt(i, 0).equals(true)) {
                int caseNumberID = Integer.valueOf(caseTable.getValueAt(i, 1).toString());
                String[] caseNumber = caseTable.getValueAt(i, 2).toString().split("-");
                                
                ULPCase.updateClosedCases(caseNumberID);
                Activity.addActivtyFromDocket("Case Closed", "", caseNumber, "", "", "", "", false, false);
                Audit.addAuditEntry("Closed Case: " + caseNumber + " from ULP Bulk Case Close");
            }
        }
    }
    
    private void printList(){
        jLayeredPane1.moveToFront(jPanel1);
        
        Thread temp = new Thread(() -> {
            SMDSDocuments report = SMDSDocuments.findDocumentByFileName("ULP Cases Closed.jasper");
            GenerateReport.runReport(report);
            jLayeredPane1.moveToBack(jPanel1);
        });
        temp.start();
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
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        countLabel = new javax.swing.JLabel();
        startDateField = new com.alee.extended.date.WebDateField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        caseTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

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

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bulk Close ULP Cases");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Start Date:");

        countLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        countLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        startDateField.setEditable(false);
        startDateField.setCaretColor(new java.awt.Color(0, 0, 0));
        startDateField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        startDateField.setDateFormat(Global.mmddyyyy);

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        caseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "ID", "Case Number", "Employer", "File Date", "Status"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(caseTable);

        jLayeredPane1.add(jScrollPane1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 678, Short.MAX_VALUE)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 658, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 434, Short.MAX_VALUE)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel6, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 412, Short.MAX_VALUE)
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
                        .add(0, 0, 0)
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(startDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 0, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, countLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 678, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(printButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(closeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .add(updateButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
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
                    .add(jLabel3)
                    .add(startDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(countLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 464, Short.MAX_VALUE)
                .add(printButton)
                .add(18, 18, 18)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(updateButton)
                    .add(closeButton))
                .addContainerGap())
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                    .add(100, 100, 100)
                    .add(jLayeredPane1)
                    .add(100, 100, 100)))
        );

        layout.linkSize(new java.awt.Component[] {jLabel3, startDateField}, org.jdesktop.layout.GroupLayout.VERTICAL);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void printButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_printButtonActionPerformed
        Audit.addAuditEntry("Printed ULP Bulk Close Processed Records");
        printList();
    }//GEN-LAST:event_printButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        Audit.addAuditEntry("Closed ULP Bulk Case Close");
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        updateList();
        loadTableThread();
    }//GEN-LAST:event_updateButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable caseTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel countLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton printButton;
    private com.alee.extended.date.WebDateField startDateField;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
