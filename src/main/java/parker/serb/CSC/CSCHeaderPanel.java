/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CSC;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import parker.serb.CMDS.CMDSCaseSearch;
import parker.serb.Global;
import parker.serb.MED.MEDCaseSearch;
import parker.serb.sql.Audit;
import parker.serb.sql.CSCCase;
import parker.serb.sql.User;
import parker.serb.util.CaseInEditModeDialog;

//

/**
 *
 * @author parker
 */
public class CSCHeaderPanel extends javax.swing.JPanel {

    CSCCaseSearch search = null;
    
    /**
     * Creates new form REPHeaderPanel
     */
    public CSCHeaderPanel() {
        initComponents();
        addListeners();
    }
    
    private void addListeners() {
        caseNumberComboBox.addActionListener((ActionEvent e) -> {
            if(caseNumberComboBox.getSelectedItem() != null) {
//                Global.root.getcSCRootPanel1().getjTabbedPane1().setSelectedIndex(0);
                if(caseNumberComboBox.getSelectedItem().toString().trim().equals("")) {
                    if(Global.root != null) {
                        Global.root.getjButton2().setText("Update");
                        Global.root.getjButton2().setEnabled(false);
                        Global.root.getjButton4().setEnabled(false);
                        Global.root.getjButton9().setVisible(false);
                        if(Global.caseNumber == null) {
                            Global.caseNumber = null;
                            Global.caseMonth = null;
                            Global.caseType = null;
                            Global.caseYear = null;
                        }
                        Global.root.getcSCRootPanel1().clearAll();
                    }
                } else {
                    caseNumberComboBox.setSelectedItem(caseNumberComboBox.getSelectedItem().toString().toUpperCase());
                    loadInformation();
//                    if(Global.root.getcSCRootPanel1().getjTabbedPane1().getSelectedIndex() == 0) {
//                        Global.root.getjButton2().setText("Add Entry");
//                        Global.root.getjButton2().setEnabled(true);
//                        Global.root.getjButton4().setText("Single Letter");
//                        Global.root.getjButton4().setEnabled(true);
//                        Global.root.getjButton9().setVisible(true);
//                        Global.root.getcSCRootPanel1().getActivityPanel1().loadAllActivity();
//                    } 
                    Audit.addAuditEntry("Loaded Case: " + caseNumberComboBox.getSelectedItem().toString().trim());
                }
            }
        });
    }
    
    private void loadInformation() {
        Global.caseYear = null;
        Global.caseType = "CSC";
        Global.caseMonth = null;
        Global.caseNumber = caseNumberComboBox.getSelectedItem().toString().trim();
        loadHeaderInformation();
    }
    
    public void loadHeaderInformation() {
        
        if(Global.caseNumber != null) {
            CSCCase csc = CSCCase.loadHeaderInformation();
            if(csc == null) {
//                new ORG((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
                caseNumberComboBox.setSelectedItem(Global.caseNumber);
            } else {
                Global.caseNumber = csc.cscNumber != null ? csc.cscNumber : "";
                Global.caseType = "CSC";
                User.updateLastCaseNumber();
                CSCNumberTextBox.setText(csc.cscNumber != null ? csc.cscNumber : "");
                Global.root.getcSCRootPanel1().loadInformation();
                Global.root.getcSCRootPanel1().setButtons();
            }
        }
    }
    
    public void loadUpdatedHeaderInformation() {
        
        if(Global.caseNumber != null) {
            Global.caseNumber = CSCCase.getCSCName();
            CSCCase org = CSCCase.loadHeaderInformation();
            if(org == null) {
//                new ORG((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
                caseNumberComboBox.setSelectedItem(Global.caseNumber);
            } else {
                Global.caseNumber = org.cscNumber != null ? org.cscNumber : "";
                Global.caseType = "CSC";
                User.updateLastCaseNumber();
                CSCNumberTextBox.setText(org.cscNumber != null ? org.cscNumber : "");
            }
        }
    }
    
    public void loadCases() {
        caseNumberComboBox.removeAllItems();
        caseNumberComboBox.addItem("");

        List caseNumberList = CSCCase.loadCSCNames();
        
        caseNumberList.stream().forEach((caseNumber) -> {
            caseNumberComboBox.addItem(caseNumber);
        });
    }
    
    void clearAll() {
        Global.caseYear = null;
        Global.caseType = null;
        Global.caseMonth = null;
        Global.caseNumber = null;
        CSCNumberTextBox.setText("");
    }

    public JComboBox getjComboBox2() {
        return caseNumberComboBox;
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jMenuItem1 = new javax.swing.JMenuItem();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        caseNumberComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        CSCNumberTextBox = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();

        jMenuItem1.setText("jMenuItem1");

        setLayout(new java.awt.GridLayout(1, 0));

        jLabel11.setText("CSC Name:");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        caseNumberComboBox.setEditable(true);
        caseNumberComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("CSC Number:");

        CSCNumberTextBox.setEditable(false);
        CSCNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        CSCNumberTextBox.setFocusable(false);
        CSCNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                CSCNumberTextBoxMouseClicked(evt);
            }
        });
        CSCNumberTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CSCNumberTextBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(caseNumberComboBox, 0, 234, Short.MAX_VALUE)
                    .addComponent(CSCNumberTextBox))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(caseNumberComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CSCNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addContainerGap(115, Short.MAX_VALUE))
        );

        add(jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 335, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 179, Short.MAX_VALUE)
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void CSCNumberTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CSCNumberTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CSCNumberTextBoxActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        if(caseNumberComboBox.isEnabled()) {
            if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
                Audit.addAuditEntry("Opened CSC Case Search Dialog");
                if(search == null) {
                    search = new CSCCaseSearch(Global.root, true);
                } else {
                    search.setVisible(true);
                }
            }
        } else {
            new CaseInEditModeDialog(Global.root, true);
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void CSCNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_CSCNumberTextBoxMouseClicked
        if(caseNumberComboBox.isEnabled()) {
            if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
                Audit.addAuditEntry("Opened CSC Case Search Dialog");
                new CSCNumberSearchDialog(Global.root, true);
            }
        } else {
            new CaseInEditModeDialog(Global.root, true);
        }
    }//GEN-LAST:event_CSCNumberTextBoxMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField CSCNumberTextBox;
    private javax.swing.JComboBox caseNumberComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
