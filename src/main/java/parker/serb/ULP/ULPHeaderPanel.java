/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ULP;

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
import parker.serb.sql.CaseParty;
import parker.serb.sql.ULPCase;
import parker.serb.sql.User;
import parker.serb.util.CaseNotFoundDialog;
import parker.serb.util.NumberFormatService;
/**
 *
 * @author parker
 */
public class ULPHeaderPanel extends javax.swing.JPanel {

    ULPCaseSearch search = null;
    
    public ULPHeaderPanel() {
        initComponents();
        addListeners();
    }
    
    private void addListeners() {
        caseNumberComboBox.addActionListener((ActionEvent e) -> {
            if(caseNumberComboBox.getSelectedItem() != null) {
                Global.root.getuLPRootPanel1().getjTabbedPane1().setSelectedIndex(0);
                if(caseNumberComboBox.getSelectedItem().toString().trim().equals("")) {
                    if(Global.root != null) {
                        Global.root.getjButton2().setText("Update");
                        Global.root.getjButton2().setEnabled(false);
                        Global.root.getjButton3().setEnabled(false);
                        Global.root.getjButton6().setEnabled(false);
                        Global.caseYear = null;
                        Global.caseType = null;
                        Global.caseMonth = null;
                        Global.caseNumber = null;
                        Global.root.getuLPRootPanel1().clearAll();
                    }
                } else {
                    Global.root.getjButton6().setEnabled(true);
                    Global.root.getjButton3().setEnabled(true);
                    loadInformation();
                    if(Global.root.getuLPRootPanel1().getjTabbedPane1().getSelectedIndex() == 0)
                        Global.root.getuLPRootPanel1().getActivityPanel1().loadAllActivity();
                    Audit.addAuditEntry("Loaded Case: " + caseNumberComboBox.getSelectedItem().toString().trim());
                }
            }
        });
    }
    
    private void loadInformation() {
        if(caseNumberComboBox.getSelectedItem().toString().trim().length() == 16) {
            NumberFormatService.parseFullCaseNumber(caseNumberComboBox.getSelectedItem().toString().trim());
            loadHeaderInformation();
        } else {
            new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());  
            Global.root.getuLPRootPanel1().clearAll();
        }
    }
    
    public void loadHeaderInformation() {
        String chargingParty = "";
        String chargingPartyREP = "";
        String chargedParty = "";
        String chargedPartyREP = "";
        
        ULPCase ulp = ULPCase.loadHeaderInformation();
        
        if(ulp == null) {
            new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());          
        } else {
            filedDateTextBox.setText(ulp.fileDate != null ? Global.mmddyyyy.format(new Date(ulp.fileDate.getTime())) : "");
            currentStatusTextBox.setText(ulp.currentStatus != null ? ulp.currentStatus : "");
            investigatorTextBox.setText(ulp.investigatorID != 0 ? User.getNameByID(ulp.investigatorID) : "");
            ALJTextBox.setText(ulp.aljID != 0 ? User.getNameByID(ulp.aljID) : "");

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
                    case "Charging Party":
                        if(chargingParty.equals("")) {
                            chargingParty += name;
                        } else {
                            chargingParty += ", " + name;
                        }
                        break;
                    case "Charging Party REP":
                        if(chargingPartyREP.equals("")) {
                            chargingPartyREP += name;
                        } else {
                            chargingPartyREP += ", " + name;
                        }
                        break;
                    case "Charged Party":
                        if(chargedParty.equals("")) {
                            chargedParty += name;
                        } else {
                            chargedParty += ", " + name;
                        }
                        break;
                    case "Charged Party REP":
                        if(chargedPartyREP.equals("")) {
                            chargedPartyREP += name;
                        } else {
                            chargedPartyREP += ", " + name;
                        }
                        break;
                }
            }
            chargingPartyTextBox.setText(chargingParty);
            chargingPartyREPTextBox.setText(chargingPartyREP);
            chargedPartyTextBox.setText(chargedParty);
            chargedPartyREPTextBox.setText(chargedPartyREP);
        }
    }
    
    public void loadCases() {
        caseNumberComboBox.removeAllItems();
        caseNumberComboBox.addItem("");

        List caseNumberList = ULPCase.loadULPCaseNumbers();
        
        caseNumberList.stream().forEach((caseNumber) -> {
            caseNumberComboBox.addItem(caseNumber.toString());
        });
    }
    
    void clearAll() {
        chargingPartyTextBox.setText("");
        chargingPartyREPTextBox.setText("");
        chargedPartyTextBox.setText("");
        chargedPartyREPTextBox.setText("");
        currentStatusTextBox.setText("");
        investigatorTextBox.setText("");
        ALJTextBox.setText("");
        filedDateTextBox.setText("");
    }

    public JComboBox getjComboBox2() {
        return caseNumberComboBox;
    }

    public JTextField getChargedPartyTextBox() {
        return chargedPartyTextBox;
    }

    public JTextField getChargingPartyTextBox() {
        return chargingPartyTextBox;
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
        jLabel2 = new javax.swing.JLabel();
        chargingPartyTextBox = new javax.swing.JTextField();
        chargingPartyREPTextBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        chargedPartyTextBox = new javax.swing.JTextField();
        chargedPartyREPTextBox = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        filedDateTextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        currentStatusTextBox = new javax.swing.JTextField();
        investigatorTextBox = new javax.swing.JTextField();
        ALJTextBox = new javax.swing.JTextField();

        jMenuItem1.setText("jMenuItem1");

        setLayout(new java.awt.GridLayout(1, 0));

        jLabel11.setText("Case Number:");
        jLabel11.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel11MouseClicked(evt);
            }
        });

        caseNumberComboBox.setEditable(true);
        caseNumberComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("Charging Party:");

        jLabel2.setText("Charging Rep:");

        chargingPartyTextBox.setEditable(false);
        chargingPartyTextBox.setBackground(new java.awt.Color(238, 238, 238));
        chargingPartyTextBox.setFocusable(false);

        chargingPartyREPTextBox.setEditable(false);
        chargingPartyREPTextBox.setBackground(new java.awt.Color(238, 238, 238));
        chargingPartyREPTextBox.setFocusable(false);

        jLabel3.setText("Charged Party:");

        jLabel4.setText("Charged Rep:");

        chargedPartyTextBox.setEditable(false);
        chargedPartyTextBox.setBackground(new java.awt.Color(238, 238, 238));
        chargedPartyTextBox.setFocusable(false);

        chargedPartyREPTextBox.setEditable(false);
        chargedPartyREPTextBox.setBackground(new java.awt.Color(238, 238, 238));
        chargedPartyREPTextBox.setFocusable(false);

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
                    .addComponent(chargingPartyTextBox)
                    .addComponent(chargingPartyREPTextBox)
                    .addComponent(chargedPartyTextBox)
                    .addComponent(chargedPartyREPTextBox))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(caseNumberComboBox)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chargingPartyTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chargingPartyREPTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chargedPartyTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(chargedPartyREPTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        add(jPanel1);

        jLabel12.setText("Filed Date:");

        filedDateTextBox.setEditable(false);
        filedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        filedDateTextBox.setFocusable(false);

        jLabel5.setText("Current Status:");

        jLabel6.setText("Investigator:");

        jLabel7.setText("ALJ:");

        currentStatusTextBox.setEditable(false);
        currentStatusTextBox.setBackground(new java.awt.Color(238, 238, 238));
        currentStatusTextBox.setFocusable(false);

        investigatorTextBox.setEditable(false);
        investigatorTextBox.setBackground(new java.awt.Color(238, 238, 238));
        investigatorTextBox.setFocusable(false);

        ALJTextBox.setEditable(false);
        ALJTextBox.setBackground(new java.awt.Color(238, 238, 238));
        ALJTextBox.setFocusable(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filedDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE)
                    .addComponent(currentStatusTextBox)
                    .addComponent(investigatorTextBox)
                    .addComponent(ALJTextBox))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(filedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(currentStatusTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(investigatorTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ALJTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addContainerGap(51, Short.MAX_VALUE))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
            if(search == null) {
                search = new ULPCaseSearch((JFrame) getRootPane().getParent(), true);
            } else {
                search.setVisible(true);
            }
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField ALJTextBox;
    private javax.swing.JComboBox caseNumberComboBox;
    private javax.swing.JTextField chargedPartyREPTextBox;
    private javax.swing.JTextField chargedPartyTextBox;
    private javax.swing.JTextField chargingPartyREPTextBox;
    private javax.swing.JTextField chargingPartyTextBox;
    private javax.swing.JTextField currentStatusTextBox;
    private javax.swing.JTextField filedDateTextBox;
    private javax.swing.JTextField investigatorTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    // End of variables declaration//GEN-END:variables
}
