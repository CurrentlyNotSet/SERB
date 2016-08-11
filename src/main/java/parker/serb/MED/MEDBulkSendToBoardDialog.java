/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import java.sql.Timestamp;
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
    }
    
    private void checkIfTableIsLoadable(){
        if(!"".equals(startDateField.getText().trim()) && !"".equals(endDateField.getText().trim())){
            loadTable();
        }else{
            clearTable();
        }
    }
    
    private void clearTable(){
        DefaultTableModel model = (DefaultTableModel) caseTable.getModel();
        model.setRowCount(0);
        countLabel.setText(" ");
    }
    
    private void loadTable(){
//        String settleDateStart = startDateField.getText().trim();
//        String settleDateEnd = endDateField.getText().trim();
//        clearTable();
//        try {
//            String sql = "SELECT medcase.CaseNumber, medcase.EmployerName, "
//                    + "medcase.CaseFileDate, medcase.Status "
//                    + "FROM medcase "
//                    + "WHERE medcase.CBAReceivedDate BETWEEN CAST(? as datetime) AND CAST(? as datetime) "
//                    + "AND medcase.Active = 1 AND medcase.tempholder4 != 'Send to Brd to Close' ORDER BY medcase.CaseNumber";
//            PreparedStatement preparedStatement = global.getDba().getObjConn().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
//            preparedStatement.setString(1, settleDateStart);
//            preparedStatement.setString(2, settleDateEnd);
//            ResultSet action = preparedStatement.executeQuery();
//            
//            while(action.next())
//            {                
//                ((DefaultTableModel) caseTable.getModel()).addRow(new Object[]{
//                    true,
//                    action.getString("CaseNumber"),
//                    action.getString("EmployerName"),
//                    action.getString("CaseFileDate")
//                });
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(MEDBulkSendToBoardDialog.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        countLabel.setText("Entries: " + caseTable.getRowCount());
    }
        
    private void updateList(){
//        for(int i = 0; i < caseTable.getRowCount(); i++){
//            if (caseTable.getValueAt(i, 0).equals(true)) {
//                try {
//                    String sql = "UPDATE medcase SET "
//                            + "tempholder4 =  ? "   //1
//                            + "WHERE CaseNumber = ?";    //2
//                    PreparedStatement preparedStatement = global.getDba().getObjConn().prepareStatement(sql);
//                    preparedStatement.setString(1,  "Send to Brd to Close");
//                    preparedStatement.setString(2,  caseTable.getValueAt(i, 1).toString());
//                    preparedStatement.executeUpdate();
//                } catch (SQLException ex) {
//                    Logger.getLogger(AdminSQLQueries.class.getName()).log(Level.SEVERE, null, ex);
//                }
//            }
//        }
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
        jScrollPane1 = new javax.swing.JScrollPane();
        caseTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        countLabel = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        startDateField = new com.alee.extended.date.WebDateField();
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
        startDateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                startDateFieldActionPerformed(evt);
            }
        });

        endDateField.setEditable(false);
        endDateField.setCaretColor(new java.awt.Color(0, 0, 0));
        endDateField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        startDateField.setDateFormat(Global.mmddyyyy);
        endDateField.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                endDateFieldActionPerformed(evt);
            }
        });

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, countLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jScrollPane1)
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(printButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .add(layout.createSequentialGroup()
                .add(50, 50, 50)
                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(startDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 68, Short.MAX_VALUE)
                .add(jLabel5, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(endDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 111, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .add(70, 70, 70))
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(closeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(updateButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 95, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {closeButton, updateButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

        layout.linkSize(new java.awt.Component[] {endDateField, startDateField}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

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
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jScrollPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 423, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                .add(printButton)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 49, Short.MAX_VALUE)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(updateButton)
                    .add(closeButton))
                .addContainerGap())
        );

        layout.linkSize(new java.awt.Component[] {endDateField, jLabel3, jLabel5, startDateField}, org.jdesktop.layout.GroupLayout.VERTICAL);

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

    private void startDateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startDateFieldActionPerformed
        checkIfTableIsLoadable();
    }//GEN-LAST:event_startDateFieldActionPerformed

    private void endDateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_endDateFieldActionPerformed
        checkIfTableIsLoadable();
    }//GEN-LAST:event_endDateFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable caseTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel countLabel;
    private com.alee.extended.date.WebDateField endDateField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton printButton;
    private com.alee.extended.date.WebDateField startDateField;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
