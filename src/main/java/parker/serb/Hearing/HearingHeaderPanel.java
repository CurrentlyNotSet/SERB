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
//                    if(Global.root.getHearingRootPanel1().getjTabbedPane1().getSelectedIndex() == 0)
//                        Global.root.getHearingRootPanel1().getActivityPanel1().loadAllActivity();
                    Audit.addAuditEntry("Loaded Case: " + caseNumberComboBox.getSelectedItem().toString().trim());
                }
            }
        });
    }
    
    private void loadInformation() {
        if(caseNumberComboBox.getSelectedItem().toString().trim().length() == 16) {
            NumberFormatService.parseFullCaseNumber(caseNumberComboBox.getSelectedItem().toString().trim());
//            loadHeaderInformation();
        } else {
//            new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());  
        }
    }
    
    public void loadHeaderInformation() {
//        String employer = "";
//        String employeeOrg = "";
//        String incumbentEEO = "";
//        String rivalEEO = "";
//        
//        
//        if(Global.caseNumber != null) {
//            REPCase rep = REPCase.loadHeaderInformation();
//            if(rep == null) {
//                new REPCaseNotFound((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
//                caseNumberComboBox.setSelectedItem(Global.caseNumber);
//            } else {
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
//        }
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
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        filedDateTextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        closedDateTextBox = new javax.swing.JTextField();
        caseTypeTextBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        rivalEEOTextBox2 = new javax.swing.JTextField();

        jMenuItem1.setText("jMenuItem1");

        rivalEEOTextBox1.setEditable(false);
        rivalEEOTextBox1.setBackground(new java.awt.Color(238, 238, 238));

        setLayout(new java.awt.GridLayout(1, 0));

        jLabel11.setText("Case Number:");

        caseNumberComboBox.setEditable(true);
        caseNumberComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel11)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caseNumberComboBox, 0, 229, Short.MAX_VALUE)
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
                .addContainerGap(148, Short.MAX_VALUE))
        );

        add(jPanel1);

        jLabel12.setText("Employer:");

        filedDateTextBox.setEditable(false);
        filedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));

        jLabel5.setText("Employer Indiv:");

        jLabel7.setText("Union:");

        closedDateTextBox.setEditable(false);
        closedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));

        caseTypeTextBox.setEditable(false);
        caseTypeTextBox.setBackground(new java.awt.Color(238, 238, 238));

        jLabel8.setText("Union Indiv:");

        rivalEEOTextBox2.setEditable(false);
        rivalEEOTextBox2.setBackground(new java.awt.Color(238, 238, 238));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel5)
                    .addComponent(jLabel12)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filedDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 204, Short.MAX_VALUE)
                    .addComponent(closedDateTextBox)
                    .addComponent(caseTypeTextBox)
                    .addComponent(rivalEEOTextBox2))
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
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caseTypeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rivalEEOTextBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(46, Short.MAX_VALUE))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox caseNumberComboBox;
    private javax.swing.JTextField caseTypeTextBox;
    private javax.swing.JTextField closedDateTextBox;
    private javax.swing.JTextField filedDateTextBox;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField rivalEEOTextBox1;
    private javax.swing.JTextField rivalEEOTextBox2;
    // End of variables declaration//GEN-END:variables
}
