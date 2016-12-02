/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.Hearing;

import parker.serb.MED.*;
import parker.serb.ULP.*;
import parker.serb.REP.*;
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.sql.Audit;
import parker.serb.sql.CaseParty;
import parker.serb.sql.HearingCase;
import parker.serb.sql.Party;
import parker.serb.sql.REPCase;
import parker.serb.util.CaseNotFoundDialog;
import parker.serb.util.NumberFormatService;

//

/**
 *
 * @author parker
 */
public class HearingHeaderPanel extends javax.swing.JPanel {

    /**
     * Creates new form REPHeaderPanel
     */
    public HearingHeaderPanel() {
        initComponents();
        addListeners();
    }
    
    private void addListeners() {
        caseNumberComboBox.addActionListener((ActionEvent e) -> {
            if(caseNumberComboBox.getSelectedItem() != null) {
                Global.root.getHearingRootPanel1().getjTabbedPane1().setSelectedIndex(0);
                if(caseNumberComboBox.getSelectedItem().toString().trim().equals("")) {
                    if(Global.root != null) {
                        Global.root.getjButton2().setText("Update");
                        Global.root.getjButton2().setEnabled(false);
                        Global.caseNumber = null;
                        Global.caseMonth = null;
                        Global.caseType = null;
                        Global.caseYear = null;
                        Global.root.getHearingRootPanel1().clearAll();
                    }
                } else {
                    loadInformation();
                    if(Global.root.getHearingRootPanel1().getjTabbedPane1().getSelectedIndex() == 0)
                        Global.root.getHearingRootPanel1().getActivityPanel1().loadAllHearingActivity();
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
//            new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());  
        }
    }
    
    public void loadHeaderInformation() {
        setHeaderPartyText();
        
        String party1 = "";
        String party2 = "";
        String party3 = "";
        String party4 = "";
        
        
        if(Global.caseNumber != null) {
            HearingCase hearings = HearingCase.loadHeaderInformation();
            if(hearings == null) {
//                new REPCaseNotFound((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
//                caseNumberComboBox.setSelectedItem(Global.caseNumber);
            } else {
//                filedDateTextBox.setText(rep.fileDate != null ? Global.mmddyyyy.format(new Date(rep.fileDate.getTime())) : "");
//                closedDateTextBox.setText(rep.courtClosedDate != null ? Global.mmddyyyy.format(new Date(rep.courtClosedDate.getTime())) : "");
//                currentStatusTextBox.setText(rep.status1 != null ? rep.status1 : "");
//                caseTypeTextBox.setText(rep.caseType != null ? rep.caseType : "");
//                bargainingUnitTextBox.setText(rep.bargainingUnitNumber != null ? rep.bargainingUnitNumber : "");
//
//                List caseParties = CaseParty.loadPartiesByCase();
//
//                for(Object caseParty: caseParties) {
//                    CaseParty partyInformation = (CaseParty) caseParty;
//
//                    switch (partyInformation.type) {
//                        case "Employer":
//                            if(employer.equals("")) {
//                                employer += partyInformation.name;
//                            } else {
//                                employer += ", " + partyInformation.name;
//                            }
//                            break;
//                        case "Employee Organization":
//                            if(employeeOrg.equals("")) {
//                                employeeOrg += partyInformation.name;
//                            } else {
//                                employeeOrg += ", " + partyInformation.name;
//                            }
//                            break;
//                        case "Incumbent Employee Organization":
//                            if(incumbentEEO.equals("")) {
//                                incumbentEEO += partyInformation.name;
//                            } else {
//                                incumbentEEO += ", " + partyInformation.name;
//                            }
//                            break;
//                        case "Rival Employee Organization":
//                            if(rivalEEO.equals("")) {
//                                rivalEEO += partyInformation.name;
//                            } else {
//                                rivalEEO += ", " + partyInformation.name;
//                            }
//                            break;
//                    }
//                }
//                employerTextBox.setText(employer);
//                employeeOrgTextBox.setText(employeeOrg);
//                incumbentEEOTextBox.setText(incumbentEEO);
//                rivalEEOTextBox.setText(rivalEEO);
//            }
        }
    }
    
    private void setHeaderPartyText() {
        switch(Global.caseType) {
            case "ULP":
            case "ERC":
            case "JWD":
                //ulp
                party1Label.setText("Intervenor Party:");
                party2Label.setText("Intervenor Rep:");
                party3Label.setText("Respondent Party:");
                party4Label.setText("Respondent Rep:");
                break;
            case "REP":
            case "RBT":
                party1Label.setText("Employer:");
                party2Label.setText("Employer Rep:");
                party3Label.setText("Employee Org:");
                party4Label.setText("Employee Org Rep:");
                break;
            case "MED":
            case "STK":
            case "NCN":
            case "CON":
                party1Label.setText("Employer:");
                party2Label.setText("Employer Rep:");
                party3Label.setText("Employee Org:");
                party4Label.setText("Employee Org Rep:");
                break;
        }
    }
    
    public void loadCases() {
        caseNumberComboBox.removeAllItems();
        caseNumberComboBox.addItem("");

        List caseNumberList = HearingCase.loadHearingCaseNumbers();
        
        caseNumberList.stream().forEach((caseNumber) -> {
            caseNumberComboBox.addItem(caseNumber.toString());
        });
    }
    
    /**
     * 
     */
    void clearAll() {
//        employerTextBox.setText("");
//        employeeOrgTextBox.setText("");
//        incumbentEEOTextBox.setText("");
//        rivalEEOTextBox.setText("");
//        closedDateTextBox.setText("");
//        currentStatusTextBox.setText("");
//        caseTypeTextBox.setText("");
//        bargainingUnitTextBox.setText("");
//        filedDateTextBox.setText("");
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
        rivalEEOTextBox1 = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        caseNumberComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        aljTextBox = new javax.swing.JTextField();
        pcDateTextBox = new javax.swing.JTextField();
        statusTextBox = new javax.swing.JTextField();
        finalResultTextBox = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        filedDateTextBox = new javax.swing.JTextField();
        party1Label = new javax.swing.JLabel();
        party2Label = new javax.swing.JLabel();
        closedDateTextBox = new javax.swing.JTextField();
        caseTypeTextBox = new javax.swing.JTextField();
        party3Label = new javax.swing.JLabel();
        rivalEEOTextBox2 = new javax.swing.JTextField();
        party4Label = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();

        jMenuItem1.setText("jMenuItem1");

        rivalEEOTextBox1.setEditable(false);
        rivalEEOTextBox1.setBackground(new java.awt.Color(238, 238, 238));

        setLayout(new java.awt.GridLayout(1, 0));

        jLabel11.setText("Case Number:");

        caseNumberComboBox.setEditable(true);
        caseNumberComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText("ALJ:");

        jLabel2.setText("PC Date:");

        jLabel3.setText("Status:");

        jLabel4.setText("Final Result:");

        aljTextBox.setEditable(false);
        aljTextBox.setBackground(new java.awt.Color(238, 238, 238));

        pcDateTextBox.setEditable(false);
        pcDateTextBox.setBackground(new java.awt.Color(238, 238, 238));

        statusTextBox.setEditable(false);
        statusTextBox.setBackground(new java.awt.Color(238, 238, 238));

        finalResultTextBox.setEditable(false);
        finalResultTextBox.setBackground(new java.awt.Color(238, 238, 238));

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
                    .addComponent(caseNumberComboBox, 0, 229, Short.MAX_VALUE)
                    .addComponent(aljTextBox)
                    .addComponent(pcDateTextBox)
                    .addComponent(statusTextBox)
                    .addComponent(finalResultTextBox))
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
                    .addComponent(jLabel1)
                    .addComponent(aljTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(pcDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(statusTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(finalResultTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        add(jPanel1);

        jLabel12.setText("Mediator:");

        filedDateTextBox.setEditable(false);
        filedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));

        party1Label.setText("Employer:");

        party2Label.setText("Employer Rep:");

        closedDateTextBox.setEditable(false);
        closedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));

        caseTypeTextBox.setEditable(false);
        caseTypeTextBox.setBackground(new java.awt.Color(238, 238, 238));

        party3Label.setText("Employee Org:");

        rivalEEOTextBox2.setEditable(false);
        rivalEEOTextBox2.setBackground(new java.awt.Color(238, 238, 238));

        party4Label.setText("Employee Org Rep:");

        jTextField1.setEditable(false);
        jTextField1.setBackground(new java.awt.Color(238, 238, 238));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(party4Label)
                    .addComponent(party2Label)
                    .addComponent(party1Label)
                    .addComponent(jLabel12)
                    .addComponent(party3Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filedDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(closedDateTextBox)
                    .addComponent(caseTypeTextBox)
                    .addComponent(rivalEEOTextBox2)
                    .addComponent(jTextField1))
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
                    .addComponent(closedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(party1Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caseTypeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(party2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rivalEEOTextBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(party3Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(party4Label)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField aljTextBox;
    private javax.swing.JComboBox caseNumberComboBox;
    private javax.swing.JTextField caseTypeTextBox;
    private javax.swing.JTextField closedDateTextBox;
    private javax.swing.JTextField filedDateTextBox;
    private javax.swing.JTextField finalResultTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel party1Label;
    private javax.swing.JLabel party2Label;
    private javax.swing.JLabel party3Label;
    private javax.swing.JLabel party4Label;
    private javax.swing.JTextField pcDateTextBox;
    private javax.swing.JTextField rivalEEOTextBox1;
    private javax.swing.JTextField rivalEEOTextBox2;
    private javax.swing.JTextField statusTextBox;
    // End of variables declaration//GEN-END:variables
}
