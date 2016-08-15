/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import java.sql.Timestamp;
import java.util.Calendar;
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
public class MEDBulkSendToBoardDialog extends javax.swing.JFrame {

    String dateForm;
    DefaultTableModel model;
    
    /**
     * Creates new form MEDsettleCases
     * @param parent
     * @param modal
     */
    public MEDBulkSendToBoardDialog(java.awt.Frame parent, boolean modal) {
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
            checkIfTableIsLoadable();
        });
        
        endDateField.addDateSelectionListener((Date date) -> {
            checkIfTableIsLoadable();
        });
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

        //Filed Date
        caseTable.getColumnModel().getColumn(3).setMinWidth(80);
        caseTable.getColumnModel().getColumn(3).setPreferredWidth(80);
        caseTable.getColumnModel().getColumn(3).setMaxWidth(80);
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
    
    private void checkIfTableIsLoadable(){
        if(!"".equals(startDateField.getText().trim()) && !"".equals(endDateField.getText().trim())){
            loadTableThread();
        }else{
            clearTable();
        }
    }
    
    private void loadTable(){
        Date startDate = new Timestamp(NumberFormatService.convertMMDDYYYY(startDateField.getText()));
        Date endDate = new Timestamp(NumberFormatService.convertMMDDYYYY(endDateField.getText()));

        List<MEDCase> caseList = MEDCase.getCloseList(startDate, endDate);

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
        
    private void updateList(){
        Timestamp settleDate = new Timestamp(Calendar.getInstance().getTime().getTime());
        
        for (int i = 0; i < caseTable.getRowCount(); i++) {
            if (caseTable.getValueAt(i, 0).equals(true)) {
                String caseNumber = caseTable.getValueAt(i, 1).toString();
                
                MEDCase.updateClosedCases(caseNumber, settleDate);
            }
        }
    }
    
    private void printList(){
//        try {
//                HashMap para = new HashMap();
//                String dateParam = JOptionPane.showInputDialog("Please enter a start date (MM/DD/YYYY)");
//                Connection con = global.getDba().getObjConn();
//                para.put("Date", dateParam);
//                String reportPath = "G:\\XLNCMS\\SERBTemplates\\MED\\ReportTemplates\\MEDCasestobeClosedbyBoard.jrxml";
//                JasperReport jr = JasperCompileManager.compileReport(reportPath);
//                JasperPrint jp = JasperFillManager.fillReport(jr, para, con);
//                JasperViewer.viewReport(jp, false);
//            } catch (JRException ex) {
//                SystemErrorNotificationEMail SENE = new SystemErrorNotificationEMail(global.getMainFrame(), true, global);
//                StringWriter errors = new StringWriter();
//                ex.printStackTrace(new PrintWriter(errors));
//                SENE.loadInformation("MEDReportsPanel", "246", errors.toString());
//            }
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
        jLabel5 = new javax.swing.JLabel();
        startDateField = new com.alee.extended.date.WebDateField();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        caseTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        endDateField = new com.alee.extended.date.WebDateField();

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
        jLabel1.setText("Send MED Cases to Board to Close");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Start Date:");

        countLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        countLabel.setVerticalAlignment(javax.swing.SwingConstants.TOP);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("End Date:");

        startDateField.setEditable(false);
        startDateField.setCaretColor(new java.awt.Color(0, 0, 0));
        startDateField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        startDateField.setDateFormat(Global.mmddyyyy);

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

        endDateField.setEditable(false);
        endDateField.setCaretColor(new java.awt.Color(0, 0, 0));
        endDateField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        endDateField.setDateFormat(Global.mmddyyyy);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .add(0, 57, Short.MAX_VALUE)
                        .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(startDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(63, 63, 63)
                        .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(endDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .add(0, 116, Short.MAX_VALUE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, countLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
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
                .add(11, 11, 11)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel3)
                        .add(startDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                        .add(jLabel5)
                        .add(endDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(countLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 471, Short.MAX_VALUE)
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

        layout.linkSize(new java.awt.Component[] {jLabel3, jLabel5, startDateField}, org.jdesktop.layout.GroupLayout.VERTICAL);

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable caseTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel countLabel;
    private com.alee.extended.date.WebDateField endDateField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton printButton;
    private com.alee.extended.date.WebDateField startDateField;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}