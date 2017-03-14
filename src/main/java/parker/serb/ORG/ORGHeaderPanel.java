/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ORG;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.SwingUtilities;
import parker.serb.Global;
import parker.serb.sql.Audit;
import parker.serb.sql.ORGCase;
import parker.serb.sql.User;

//

/**
 *
 * @author parker
 */
public class ORGHeaderPanel extends javax.swing.JPanel {

    ORGCaseSearch search = null;

    /**
     * Creates new form REPHeaderPanel
     */
    public ORGHeaderPanel() {
        initComponents();
        addListeners();
    }

    private void addListeners() {
        caseNumberComboBox.addActionListener((ActionEvent e) -> {
            if(caseNumberComboBox.getSelectedItem() != null) {
                Global.root.getoRGRootPanel1().getjTabbedPane1().setSelectedIndex(0);
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
                        Global.root.getoRGRootPanel1().clearAll();
                    }
                } else {
                    Global.root.getjButton2().setEnabled(true);
                    caseNumberComboBox.setSelectedItem(caseNumberComboBox.getSelectedItem().toString().toUpperCase());
                    loadInformation();
                    if(Global.root.getoRGRootPanel1().getjTabbedPane1().getSelectedIndex() == 0) {
                        Global.root.getjButton2().setText("Add Entry");
                        Global.root.getjButton2().setEnabled(true);
                        Global.root.getjButton4().setText("Single Letter");
                        Global.root.getjButton4().setEnabled(true);
                        Global.root.getjButton9().setVisible(true);
                        Global.root.getoRGRootPanel1().getActivityPanel1().loadAllActivity();
                    }

                    Audit.addAuditEntry("Loaded Case: " + caseNumberComboBox.getSelectedItem().toString().trim());
                }
            }
        });
    }

    private void loadInformation() {
        Global.caseYear = null;
        Global.caseType = "ORG";
        Global.caseMonth = null;
        Global.caseNumber = caseNumberComboBox.getSelectedItem().toString().trim();

        loadHeaderInformation();
    }

    public void loadHeaderInformation() {

        if(Global.caseNumber != null) {
            ORGCase org = ORGCase.loadHeaderInformation();
            if(org == null) {
//                new ORG((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
                caseNumberComboBox.setSelectedItem(Global.caseNumber);
            } else {
                Global.caseNumber = org.orgNumber != null ? org.orgNumber : "";
                Global.caseType = "ORG";
                User.updateLastCaseNumber();
                orgNumberTextBox.setText(org.orgNumber != null ? org.orgNumber : "");
                fiscalYearEndingTextBox.setText(org.fiscalYearEnding != null ? org.fiscalYearEnding : "");
                filingDueDateTextBox.setText(org.filingDueDate != null ? org.filingDueDate : "");
                annualReportTextBox.setText(org.annualReport != null ? Global.mmddyyyy.format(new Date(org.annualReport.getTime())) : "");
                financialReportTextBox.setText(org.financialReport != null ? Global.mmddyyyy.format(new Date(org.financialReport.getTime())) : "");
                registrationReportTextBox.setText(org.registrationReport != null ? Global.mmddyyyy.format(new Date(org.registrationReport.getTime())) : "");
                constructionAndByLawsTextBox.setText(org.constructionAndByLaws != null ? Global.mmddyyyy.format(new Date(org.constructionAndByLaws.getTime())) : "");
                filedByParentTextBox.setText(org.filedByParent == true ? "Yes" : "No");
            }
        }
    }

    public void loadUpdatedHeaderInformation() {

        if(Global.caseNumber != null) {
            Global.caseNumber = ORGCase.getORGName();
            ORGCase org = ORGCase.loadHeaderInformation();
            if(org == null) {
//                new ORG((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
                caseNumberComboBox.setSelectedItem(Global.caseNumber);
            } else {
                Global.caseNumber = org.orgNumber != null ? org.orgNumber : "";
                Global.caseType = "ORG";
                orgNumberTextBox.setText(org.orgNumber != null ? org.orgNumber : "");
                fiscalYearEndingTextBox.setText(org.fiscalYearEnding != null ? org.fiscalYearEnding : "");
                filingDueDateTextBox.setText(org.filingDueDate != null ? org.filingDueDate : "");
                annualReportTextBox.setText(org.annualReport != null ? Global.mmddyyyy.format(new Date(org.annualReport.getTime())) : "");
                financialReportTextBox.setText(org.financialReport != null ? Global.mmddyyyy.format(new Date(org.financialReport.getTime())) : "");
                registrationReportTextBox.setText(org.registrationReport != null ? Global.mmddyyyy.format(new Date(org.registrationReport.getTime())) : "");
                constructionAndByLawsTextBox.setText(org.constructionAndByLaws != null ? Global.mmddyyyy.format(new Date(org.constructionAndByLaws.getTime())) : "");
                filedByParentTextBox.setText(org.filedByParent == true ? "Yes" : "No");
            }
        }
    }

    public void loadCases() {
        caseNumberComboBox.removeAllItems();
        caseNumberComboBox.addItem("");

        List caseNumberList = ORGCase.loadORGNames();

        caseNumberList.stream().forEach((caseNumber) -> {
            caseNumberComboBox.addItem(caseNumber);
        });
    }

    /**
     *
     */
    void clearAll() {
        Global.caseYear = null;
        Global.caseType = null;
        Global.caseMonth = null;
        Global.caseNumber = null;
        orgNumberTextBox.setText("");
        fiscalYearEndingTextBox.setText("");
        filingDueDateTextBox.setText("");
        annualReportTextBox.setText("");
        financialReportTextBox.setText("");
        registrationReportTextBox.setText("");
        constructionAndByLawsTextBox.setText("");
        filedByParentTextBox.setText("");
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
        orgNumberTextBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        fiscalYearEndingTextBox = new javax.swing.JTextField();
        filingDueDateTextBox = new javax.swing.JTextField();
        filedByParentTextBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        financialReportTextBox = new javax.swing.JTextField();
        registrationReportTextBox = new javax.swing.JTextField();
        constructionAndByLawsTextBox = new javax.swing.JTextField();
        annualReportTextBox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();

        jMenuItem1.setText("jMenuItem1");

        setLayout(new java.awt.GridLayout(1, 0));

        jLabel11.setText("ORG Name:");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        caseNumberComboBox.setEditable(true);
        caseNumberComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("ORG Number:");

        orgNumberTextBox.setEditable(false);
        orgNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                orgNumberTextBoxMouseClicked(evt);
            }
        });
        orgNumberTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orgNumberTextBoxActionPerformed(evt);
            }
        });

        jLabel2.setText("Fiscal Year Ending:");

        jLabel3.setText("Filing Due Date:");

        fiscalYearEndingTextBox.setEditable(false);
        fiscalYearEndingTextBox.setBackground(new java.awt.Color(238, 238, 238));
        fiscalYearEndingTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        filingDueDateTextBox.setEditable(false);
        filingDueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        filingDueDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        filedByParentTextBox.setEditable(false);
        filedByParentTextBox.setBackground(new java.awt.Color(238, 238, 238));
        filedByParentTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel8.setText("Filed By Parent:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel11)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filedByParentTextBox)
                    .addComponent(caseNumberComboBox, 0, 193, Short.MAX_VALUE)
                    .addComponent(orgNumberTextBox)
                    .addComponent(fiscalYearEndingTextBox)
                    .addComponent(filingDueDateTextBox))
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
                    .addComponent(orgNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fiscalYearEndingTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(filingDueDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(filedByParentTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(19, Short.MAX_VALUE))
        );

        add(jPanel1);

        jLabel5.setText("Financial Report:");

        jLabel6.setText("Registration Report:");

        jLabel7.setText("Constitution and By Laws:");

        financialReportTextBox.setEditable(false);
        financialReportTextBox.setBackground(new java.awt.Color(238, 238, 238));
        financialReportTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        registrationReportTextBox.setEditable(false);
        registrationReportTextBox.setBackground(new java.awt.Color(238, 238, 238));
        registrationReportTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        constructionAndByLawsTextBox.setEditable(false);
        constructionAndByLawsTextBox.setBackground(new java.awt.Color(238, 238, 238));
        constructionAndByLawsTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        annualReportTextBox.setEditable(false);
        annualReportTextBox.setBackground(new java.awt.Color(238, 238, 238));
        annualReportTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel4.setText("Annual Report:");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7)
                        .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(financialReportTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 154, Short.MAX_VALUE)
                    .addComponent(registrationReportTextBox)
                    .addComponent(constructionAndByLawsTextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(annualReportTextBox, javax.swing.GroupLayout.Alignment.TRAILING))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(annualReportTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(financialReportTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(registrationReportTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(constructionAndByLawsTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void orgNumberTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orgNumberTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orgNumberTextBoxActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
            if(search == null) {
                search = new ORGCaseSearch(Global.root, true);
            } else {
                search.setVisible(true);
            }
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    private void orgNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_orgNumberTextBoxMouseClicked
        if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
            Audit.addAuditEntry("Opened ORG Case Search Dialog");
            new ORGNumberSearchDialog(Global.root, true);
        }
    }//GEN-LAST:event_orgNumberTextBoxMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField annualReportTextBox;
    private javax.swing.JComboBox caseNumberComboBox;
    private javax.swing.JTextField constructionAndByLawsTextBox;
    private javax.swing.JTextField filedByParentTextBox;
    private javax.swing.JTextField filingDueDateTextBox;
    private javax.swing.JTextField financialReportTextBox;
    private javax.swing.JTextField fiscalYearEndingTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField orgNumberTextBox;
    private javax.swing.JTextField registrationReportTextBox;
    // End of variables declaration//GEN-END:variables
}
