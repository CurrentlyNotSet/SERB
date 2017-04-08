/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import parker.serb.Global;
import parker.serb.sql.Audit;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CaseParty;
import parker.serb.sql.User;
import parker.serb.util.CaseInEditModeDialog;
import parker.serb.util.CaseNotFoundDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parker
 */
public class CMDSHeaderPanel extends javax.swing.JPanel {

    CMDSCaseSearch search = null;

    /**
     * Creates new form REPHeaderPanel
     */
    public CMDSHeaderPanel() {
        initComponents();
        addListeners();
    }

    private void addListeners() {
        caseNumberComboBox.addActionListener((ActionEvent e) -> {
            if(caseNumberComboBox.getSelectedItem() != null) {
//                Global.root.getcMDSRootPanel1().getjTabbedPane1().setSelectedIndex(0);
                if(caseNumberComboBox.getSelectedItem().toString().trim().equals("")) {
                    if(Global.root != null) {
                        Global.root.getjButton2().setText("Update");
                        Global.root.getjButton2().setEnabled(false);
                        Global.root.getjButton4().setEnabled(false);
                        if(Global.caseNumber == null) {
                            Global.root.getjButton9().setVisible(false);
                            Global.caseNumber = null;
                            Global.caseMonth = null;
                            Global.caseType = null;
                            Global.caseYear = null;
                        }
                        Global.root.getcMDSRootPanel1().clearAll();
                    }
                } else {
                    caseNumberComboBox.setSelectedItem(caseNumberComboBox.getSelectedItem().toString().toUpperCase());
                    loadInformation();
//                    if(Global.root.getcMDSRootPanel1().getjTabbedPane1().getSelectedIndex() == 0) {
////                        Global.root.getcMDSRootPanel1().getActivityPanel1().loadAllActivity();
////                        Global.root.getjButton2().setText("Add Entry");
////                        Global.root.getjButton2().setEnabled(true);
                        Global.root.getjButton4().setText("Documents");
                        Global.root.getjButton4().setEnabled(true);
                        
//                    }
                    Audit.addAuditEntry("Loaded Case: " + caseNumberComboBox.getSelectedItem().toString().trim());
                }
            }
        });
    }

    private void loadInformation() {
        if(caseNumberComboBox.getSelectedItem().toString().trim().length() == 16) {
            NumberFormatService.parseFullCaseNumber(caseNumberComboBox.getSelectedItem().toString().trim());
            User.updateLastCaseNumber();
            loadHeaderInformation();
            Global.root.getcMDSRootPanel1().loadInformation();
            Global.root.getcMDSRootPanel1().setButtons();
        } else {
            new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
        }
    }

    public void loadHeaderInformation() {

        String appellee = "";
        String appellant = "";

        if(Global.caseNumber != null) {
            CMDSCase cmds = CMDSCase.loadHeaderInformation();
            if(cmds == null) {
                //TODO: Case Not Found
            } else {
                groupNumberTextBox.setText(cmds.groupNumber != null ? cmds.groupNumber : "");
                aljTextBox.setText(cmds.aljID != 0 ? User.getNameByID(cmds.aljID).replace("null", "").trim() : ""); //TODO: Convert ID to Name
                openDateTExtBox.setText(cmds.openDate != null ? Global.mmddyyyy.format(new Date(cmds.openDate.getTime())) : "");
                closeDateTextBox.setText(cmds.closeDate != null ? Global.mmddyyyy.format(new Date(cmds.closeDate.getTime())) : "");
                inventoryStatusDateTextBox.setText(cmds.inventoryStatusDate != null ? Global.mmddyyyy.format(new Date(cmds.inventoryStatusDate.getTime())) : "");
                inventoryStatusLineTextBox.setText(cmds.inventroyStatusLine != null ? cmds.inventroyStatusLine : "");
                statusTextBox.setText(cmds.caseStatus != null ? cmds.caseStatus : "");
                resultTextBox.setText(cmds.result != null ? cmds.result : "");

                List caseParties = CaseParty.loadPartiesByCase();

                for(Object caseParty: caseParties) {
                    CaseParty partyInformation = (CaseParty) caseParty;

                    String name;

                    if(partyInformation.firstName.equals("") && partyInformation.lastName.equals("")) {
                        name = partyInformation.companyName;
                    } else {
                        name = (partyInformation.prefix.equals("") ? "" : (partyInformation.prefix + " "))
                        + (partyInformation.firstName.equals("") ? "" : (partyInformation.firstName + " "))
                        + (partyInformation.middleInitial.equals("") ? "" : (partyInformation.middleInitial + ". "))
                        + (partyInformation.lastName.equals("") ? "" : (partyInformation.lastName))
                        + (partyInformation.suffix.equals("") ? "" : (" " + partyInformation.suffix))
                        + (partyInformation.nameTitle.equals("") ? "" : (", " + partyInformation.nameTitle));
                    }

                    switch (partyInformation.caseRelation) {
                        case "Appellee":
                            if(appellee.equals("")) {
                                appellee += name;
                            } else {
                                appellee += ", " + name;
                            }
                            break;
                        case "Appellant":
                            if(appellant.equals("")) {
                                appellant += name;
                            } else {
                                appellant += ", " + name;
                            }
                            break;
                    }
                    appelleeTextBox.setText(appellee);
                    appellantTextBox.setText(appellant);
                }
            }
            appellantTextBox.setCaretPosition(0);
            appelleeTextBox.setCaretPosition(0);
            inventoryStatusLineTextBox.setCaretPosition(0);
        }
    }

    public void loadCases() {
        caseNumberComboBox.removeAllItems();
        caseNumberComboBox.addItem("");

        List caseNumberList = CMDSCase.loadCMDSCaseNumbers();

        caseNumberList.stream().forEach((caseNumber) -> {
            caseNumberComboBox.addItem(caseNumber.toString());
        });
    }

    void clearAll() {
        Global.caseYear = null;
        Global.caseType = null;
        Global.caseMonth = null;
        Global.caseNumber = null;
        groupNumberTextBox.setText("");
        aljTextBox.setText("");
        openDateTExtBox.setText("");
        closeDateTextBox.setText("");
        appellantTextBox.setText("");
        appelleeTextBox.setText("");
        inventoryStatusDateTextBox.setText("");
        inventoryStatusLineTextBox.setText("");
        statusTextBox.setText("");
        resultTextBox.setText("");
    }

    public JComboBox getjComboBox2() {
        return caseNumberComboBox;
    }

    public JTextField getEmployeeOrgTextBox() {
        return openDateTExtBox;
    }

    public JTextField getEmployerTextBox() {
        return groupNumberTextBox;
    }

    public JTextField getAppellantTextBox() {
        return appellantTextBox;
    }

    public JTextField getAppelleeTextBox() {
        return appelleeTextBox;
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
        rivalEEOTextBox1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        caseNumberComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        groupNumberTextBox = new javax.swing.JTextField();
        aljTextBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        openDateTExtBox = new javax.swing.JTextField();
        closeDateTextBox = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        appellantTextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        appelleeTextBox = new javax.swing.JTextField();
        inventoryStatusDateTextBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        inventoryStatusLineTextBox = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        statusTextBox = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        resultTextBox = new javax.swing.JTextField();

        jMenuItem1.setText("jMenuItem1");

        rivalEEOTextBox1.setEditable(false);
        rivalEEOTextBox1.setBackground(new java.awt.Color(238, 238, 238));

        setLayout(new java.awt.GridLayout(1, 0));

        jLabel11.setText("Case Number:");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        caseNumberComboBox.setEditable(true);
        caseNumberComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Group Number:");

        jLabel2.setText("ALJ:");

        groupNumberTextBox.setEditable(false);
        groupNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        groupNumberTextBox.setFocusable(false);

        aljTextBox.setEditable(false);
        aljTextBox.setBackground(new java.awt.Color(238, 238, 238));
        aljTextBox.setFocusable(false);

        jLabel3.setText("Open Date:");

        jLabel4.setText("Close Date:");

        openDateTExtBox.setEditable(false);
        openDateTExtBox.setBackground(new java.awt.Color(238, 238, 238));
        openDateTExtBox.setFocusable(false);

        closeDateTextBox.setEditable(false);
        closeDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        closeDateTextBox.setFocusable(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(caseNumberComboBox, 0, 221, Short.MAX_VALUE)
                    .addComponent(groupNumberTextBox)
                    .addComponent(aljTextBox)
                    .addComponent(openDateTExtBox)
                    .addComponent(closeDateTextBox))
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
                    .addComponent(groupNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(aljTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(openDateTExtBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        add(jPanel1);

        jLabel12.setText("Appellant:");

        appellantTextBox.setEditable(false);
        appellantTextBox.setBackground(new java.awt.Color(238, 238, 238));
        appellantTextBox.setFocusable(false);

        jLabel5.setText("Appellee:");

        jLabel7.setText("Inv Status Date:");

        appelleeTextBox.setEditable(false);
        appelleeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        appelleeTextBox.setFocusable(false);

        inventoryStatusDateTextBox.setEditable(false);
        inventoryStatusDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        inventoryStatusDateTextBox.setFocusable(false);

        jLabel8.setText("Inv Status Line:");

        inventoryStatusLineTextBox.setEditable(false);
        inventoryStatusLineTextBox.setBackground(new java.awt.Color(238, 238, 238));
        inventoryStatusLineTextBox.setFocusable(false);

        jLabel9.setText("Status:");

        statusTextBox.setEditable(false);
        statusTextBox.setBackground(new java.awt.Color(238, 238, 238));
        statusTextBox.setFocusable(false);

        jLabel10.setText("Result:");

        resultTextBox.setEditable(false);
        resultTextBox.setBackground(new java.awt.Color(238, 238, 238));
        resultTextBox.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel12)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(appellantTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 202, Short.MAX_VALUE)
                    .addComponent(appelleeTextBox)
                    .addComponent(inventoryStatusDateTextBox)
                    .addComponent(inventoryStatusLineTextBox)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(statusTextBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel10)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(resultTextBox)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(appellantTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(appelleeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(inventoryStatusDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(inventoryStatusLineTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(statusTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(resultTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        if(caseNumberComboBox.isEnabled()) {
            if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
                if(search == null) {
                    search = new CMDSCaseSearch((JFrame) getRootPane().getParent(), true);
                } else {
                    search.setVisible(true);
                }
            }
        } else {
            new CaseInEditModeDialog(Global.root, true);
        }
    }//GEN-LAST:event_jLabel11MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aljTextBox;
    private javax.swing.JTextField appellantTextBox;
    private javax.swing.JTextField appelleeTextBox;
    private javax.swing.JComboBox caseNumberComboBox;
    private javax.swing.JTextField closeDateTextBox;
    private javax.swing.JTextField groupNumberTextBox;
    private javax.swing.JTextField inventoryStatusDateTextBox;
    private javax.swing.JTextField inventoryStatusLineTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField openDateTExtBox;
    private javax.swing.JTextField resultTextBox;
    private javax.swing.JTextField rivalEEOTextBox1;
    private javax.swing.JTextField statusTextBox;
    // End of variables declaration//GEN-END:variables
}
