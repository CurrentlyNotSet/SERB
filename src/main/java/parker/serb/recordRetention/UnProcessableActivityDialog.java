/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.recordRetention;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JTable;
import javax.swing.SwingUtilities;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.DBConnectionInfo;
import parker.serb.Global;
import parker.serb.sql.PurgedActivity;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author User
 */
public class UnProcessableActivityDialog extends javax.swing.JDialog {

    /**
     * Creates new form UnProcessableActivityDialog
     * @param parent
     * @param modal
     * @param failedPurgeListPassed
     * @param sectionSelectedPassed
     */
    public UnProcessableActivityDialog(java.awt.Frame parent, boolean modal, List<PurgedActivity> failedPurgeListPassed, String sectionSelectedPassed) {
        super(parent, modal);
        initComponents();
        addRenderer();
        setActive(failedPurgeListPassed, sectionSelectedPassed);
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void addRenderer() {
        jTable1.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private final DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
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
    
    private void setActive(List<PurgedActivity> failedPurgeListPassed, String sectionSelectedPassed) {
        if (DBConnectionInfo.getDatabase().contains("SERB-Development") || DBConnectionInfo.getDatabase().contains("SERB-TEST")){
            this.setIconImage( new ImageIcon(getClass().getResource("/SERBSeal-INVERT.png")).getImage() );
        } else {
            this.setIconImage( new ImageIcon(getClass().getResource("/SERBSeal.png")).getImage() );
        }
        setTableSize();
        loadTableThread(failedPurgeListPassed, sectionSelectedPassed);
    }
    
    private void setTableSize() {
        //ID / Object
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        
        //Case Number
        jTable1.getColumnModel().getColumn(1).setMinWidth(175);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(175);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(175);
        
        //Date
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(140);
        jTable1.getColumnModel().getColumn(2).setMinWidth(140);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(140);
    }
    
    private void loadTableThread(List<PurgedActivity> failedPurgeListPassed, String sectionSelectedPassed) {

        Thread temp = new Thread(() -> {
            SwingUtilities.invokeLater(() -> {
                loadTable(failedPurgeListPassed, sectionSelectedPassed);
            });
        });
        temp.start();
    }
    
    private void loadTable(List<PurgedActivity> failedPurgeListPassed, String section) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        if (failedPurgeListPassed != null){
            for (PurgedActivity item : failedPurgeListPassed) {
                String caseNumber = "";
                
                if (section.equals("ORG") || section.equals("CSC")
                        || section.equals("Civil Service Commission")){
                    caseNumber = item.orgName;
                } else {
                    caseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(
                        item.caseYear, item.caseType, item.caseMonth, item.caseNumber);
                }   
                model.addRow(new Object[]{
                    item,
                    caseNumber,
                    item.date == null ? "" : Global.mmddyyyyhhmma.format(item.date),
                    item.action == null ? "" : item.action
                });
            }
        }
        validate();
        repaint();
    }
    
    
    
    
    
    
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Unable to Purge due to File Lock");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Object", "Case Number", "Date", "Activity"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
        }

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 676, Short.MAX_VALUE))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 265, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    // End of variables declaration//GEN-END:variables
}
