/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import java.awt.Color;
import java.awt.Component;
import java.sql.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.report.GenerateReport;
import parker.serb.sql.Activity;
import parker.serb.sql.Audit;
import parker.serb.sql.MEDCase;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SingleDateDialog;

/**
 *
 * @author parker.johnston
 */
public class MEDBulkClosedCasesDialog extends javax.swing.JFrame {

    DefaultTableModel model;

    /**
     * Creates new form MEDsettleCases
     *
     * @param parent
     * @param modal
     */
    public MEDBulkClosedCasesDialog(java.awt.Frame parent, boolean modal) {
        initComponents();
        addRenderer();
        setActive();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void addRenderer() {
        caseTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Global.ALTERNATE_ROW_COLOR);
                }
                return c;
            }
        });
    }

    private void setActive() {
        setTableSize();
        loadTableThread();
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

        //Object List
        caseTable.getColumnModel().getColumn(4).setMinWidth(0);
        caseTable.getColumnModel().getColumn(4).setPreferredWidth(0);
        caseTable.getColumnModel().getColumn(4).setMaxWidth(0);

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
        List<MEDCase> caseList = MEDCase.getCloseList();

        for (MEDCase item : caseList) {
            String caseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(
                    item.caseYear, item.caseType, item.caseMonth, item.caseNumber);

            model.addRow(new Object[]{
                false,
                caseNumber,
                item.employerIDNumber,
                Global.mmddyyyy.format(item.fileDate),
                item
            });
        }
        jLayeredPane1.moveToBack(jPanel1);
        countLabel.setText("Entries: " + caseTable.getRowCount());
    }

    private void updateList() {
        SingleDateDialog selectedDate = new SingleDateDialog(this, true, "What was the Board Meeting Date?");

        if (selectedDate.valid) {
            for (int i = 0; i < caseTable.getRowCount(); i++) {
                if (caseTable.getValueAt(i, 0).equals(true)) {
                    processRow((MEDCase) caseTable.getValueAt(i, 4), selectedDate.selectedDate);
                }
            }
        }
    }


    private void processRow(MEDCase item, Date selectedDate){
        String caseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(
                    item.caseYear, item.caseType, item.caseMonth, item.caseNumber);

        MEDCase.updateClosedMedCases(item, selectedDate);
        Activity.addNewCaseActivty(caseNumber, "Case Closed");
        Audit.addAuditEntry("Closed Case: " + caseNumber + " from ULP Bulk Case Close");
    }

    private void printList() {
        jLayeredPane1.moveToFront(jPanel1);

        Thread temp = new Thread(() -> {
            SMDSDocuments report = SMDSDocuments.findDocumentByFileName("BulkCloseCasesList.jasper");
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
        countLabel = new javax.swing.JLabel();
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
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
            }
        });

        printButton.setText("Print Cases to Be Closed List");
        printButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                printButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Close MED cases");

        countLabel.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        caseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "", "Case Number", "Employer", "File Date", "Object"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                true, false, false, false, false
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
            caseTable.getColumnModel().getColumn(4).setResizable(false);
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
            .add(0, 638, Short.MAX_VALUE)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 618, Short.MAX_VALUE)
                    .addContainerGap()))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(0, 455, Short.MAX_VALUE)
            .add(jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 433, Short.MAX_VALUE)
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
                    .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .add(0, 0, Short.MAX_VALUE)
                        .add(printButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 191, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, countLabel, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(layout.createSequentialGroup()
                        .add(closeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 438, Short.MAX_VALUE)
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
                .add(countLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 14, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 510, Short.MAX_VALUE)
                .add(printButton)
                .add(45, 45, 45)
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(closeButton)
                    .add(updateButton))
                .addContainerGap())
            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(layout.createSequentialGroup()
                    .add(67, 67, 67)
                    .add(jLayeredPane1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(153, Short.MAX_VALUE)))
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
        loadTableThread();
    }//GEN-LAST:event_updateButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable caseTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel countLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton printButton;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
