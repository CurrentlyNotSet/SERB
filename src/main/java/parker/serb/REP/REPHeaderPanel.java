/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import parker.serb.util.CaseNotFoundDialog;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import parker.serb.Global;
import parker.serb.ULP.ULPCaseSearch;
import parker.serb.sql.Audit;
import parker.serb.sql.CaseParty;
import parker.serb.sql.Party;
import parker.serb.sql.REPCase;
import parker.serb.util.NumberFormatService;

//

/**
 *
 * @author parker
 */
public class REPHeaderPanel extends javax.swing.JPanel {

    REPCaseSearch search = null;
    /**
     * Creates new form REPHeaderPanel
     */
    public REPHeaderPanel() {
        initComponents();
        addListeners();
    }
    
    private void addListeners() {
        caseNumberComboBox.addActionListener((ActionEvent e) -> {
            if(caseNumberComboBox.getSelectedItem() != null) {
                Global.root.getrEPRootPanel1().getjTabbedPane1().setSelectedIndex(0);
                if(caseNumberComboBox.getSelectedItem().toString().trim().equals("")) {
                    if(Global.root != null) {
//                        Global.root.getjButton2().setText("Update");
                        Global.root.getjButton2().setEnabled(false);
//                        Global.caseYear = null;
//                        Global.caseType = null;
//                        Global.caseMonth = null;
//                        Global.caseNumber = null;
                        Global.root.getrEPRootPanel1().clearAll();
                    }
                } else {
                    loadInformation();
                    if(Global.root.getrEPRootPanel1().getjTabbedPane1().getSelectedIndex() == 0)
                        Global.root.getrEPRootPanel1().getActivityPanel1().loadAllActivity();
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
        }
    }
    
    public void loadHeaderInformation() {
        String employer = "";
        String employeeOrg = "";
        String incumbentEEO = "";
        String rivalEEO = "";
        
        
        if(Global.caseNumber != null) {
            REPCase rep = REPCase.loadHeaderInformation();
            if(rep == null) {
                new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
                caseNumberComboBox.setSelectedItem(Global.caseNumber);
            } else {
                filedDateTextBox.setText(rep.fileDate != null ? Global.mmddyyyy.format(new Date(rep.fileDate.getTime())) : "");
                closedDateTextBox.setText(rep.courtClosedDate != null ? Global.mmddyyyy.format(new Date(rep.courtClosedDate.getTime())) : "");
                currentStatusTextBox.setText(rep.status1 != null ? rep.status1 : "");
                caseTypeTextBox.setText(rep.type != null ? rep.type : "");
                bargainingUnitTextBox.setText(rep.bargainingUnitNumber != null ? rep.bargainingUnitNumber : "");

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
                        case "Employer":
                            if(employer.equals("")) {
                                employer += name;
                            } else {
                                employer += ", " + name;
                            }
                            break;
                        case "Employee Organization":
                            if(employeeOrg.equals("")) {
                                employeeOrg += name;
                            } else {
                                employeeOrg += ", " + name;
                            }
                            break;
                        case "Incumbent Employee Organization":
                            if(incumbentEEO.equals("")) {
                                incumbentEEO += name;
                            } else {
                                incumbentEEO += ", " + name;
                            }
                            break;
                        case "Rival Employee Organization":
                            if(rivalEEO.equals("")) {
                                rivalEEO += name;
                            } else {
                                rivalEEO += ", " + name;
                            }
                            break;
                    }
                }
                employerTextBox.setText(employer);
                employerTextBox.setCaretPosition(0);
                employeeOrgTextBox.setText(employeeOrg);
                employeeOrgTextBox.setCaretPosition(0);
                incumbentEEOTextBox.setText(incumbentEEO);
                incumbentEEOTextBox.setCaretPosition(0);
                rivalEEOTextBox.setText(rivalEEO);
                rivalEEOTextBox.setCaretPosition(0);
            }
        }
    }
    
    public void loadCases() {
        caseNumberComboBox.removeAllItems();
        caseNumberComboBox.addItem("");

        List caseNumberList = REPCase.loadREPCaseNumbers();
        
        caseNumberList.stream().forEach((caseNumber) -> {
            caseNumberComboBox.addItem(caseNumber.toString());
        });
    }
    
    /**
     * 
     */
    void clearAll() {
        employerTextBox.setText("");
        employeeOrgTextBox.setText("");
        incumbentEEOTextBox.setText("");
        rivalEEOTextBox.setText("");
        closedDateTextBox.setText("");
        currentStatusTextBox.setText("");
        caseTypeTextBox.setText("");
        bargainingUnitTextBox.setText("");
        filedDateTextBox.setText("");
    }

    public JComboBox getjComboBox2() {
        return caseNumberComboBox;
    }

    public JTextField getEmployerTextBox() {
        return employerTextBox;
    }

    public JTextField getEmployeeOrgTextBox() {
        return employeeOrgTextBox;
    }

    public JTextField getIncumbentEEOTextBox() {
        return incumbentEEOTextBox;
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
        employerTextBox = new javax.swing.JTextField();
        employeeOrgTextBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        incumbentEEOTextBox = new javax.swing.JTextField();
        rivalEEOTextBox = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        filedDateTextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        closedDateTextBox = new javax.swing.JTextField();
        currentStatusTextBox = new javax.swing.JTextField();
        caseTypeTextBox = new javax.swing.JTextField();
        bargainingUnitTextBox = new javax.swing.JTextField();

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

        jLabel1.setText("Employer:");

        jLabel2.setText("Employee Org:");

        employerTextBox.setEditable(false);
        employerTextBox.setBackground(new java.awt.Color(238, 238, 238));
        employerTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                employerTextBoxActionPerformed(evt);
            }
        });

        employeeOrgTextBox.setEditable(false);
        employeeOrgTextBox.setBackground(new java.awt.Color(238, 238, 238));

        jLabel3.setText("Incumbent EEO:");

        jLabel4.setText("Rival EEO:");

        incumbentEEOTextBox.setEditable(false);
        incumbentEEOTextBox.setBackground(new java.awt.Color(238, 238, 238));

        rivalEEOTextBox.setEditable(false);
        rivalEEOTextBox.setBackground(new java.awt.Color(238, 238, 238));

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
                    .addComponent(caseNumberComboBox, 0, 218, Short.MAX_VALUE)
                    .addComponent(employerTextBox)
                    .addComponent(employeeOrgTextBox)
                    .addComponent(incumbentEEOTextBox)
                    .addComponent(rivalEEOTextBox))
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
                    .addComponent(employerTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeOrgTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(incumbentEEOTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rivalEEOTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        add(jPanel1);

        jLabel12.setText("Filed Date:");

        filedDateTextBox.setEditable(false);
        filedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));

        jLabel5.setText("Closed Date:");

        jLabel6.setText("Current Status:");

        jLabel7.setText("Case Type:");

        jLabel8.setText("Bargaining Unit:");

        closedDateTextBox.setEditable(false);
        closedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));

        currentStatusTextBox.setEditable(false);
        currentStatusTextBox.setBackground(new java.awt.Color(238, 238, 238));

        caseTypeTextBox.setEditable(false);
        caseTypeTextBox.setBackground(new java.awt.Color(238, 238, 238));

        bargainingUnitTextBox.setEditable(false);
        bargainingUnitTextBox.setBackground(new java.awt.Color(238, 238, 238));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel12)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel8)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filedDateTextBox)
                    .addComponent(closedDateTextBox)
                    .addComponent(currentStatusTextBox)
                    .addComponent(caseTypeTextBox)
                    .addComponent(bargainingUnitTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 217, Short.MAX_VALUE))
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
                    .addComponent(currentStatusTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(caseTypeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(bargainingUnitTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(9, Short.MAX_VALUE))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void employerTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_employerTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_employerTextBoxActionPerformed

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
            if(search == null) {
                search = new REPCaseSearch((JFrame) getRootPane().getParent(), true);
            } else {
                search.setVisible(true);
            }
        }
    }//GEN-LAST:event_jLabel11MouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField bargainingUnitTextBox;
    private javax.swing.JComboBox caseNumberComboBox;
    private javax.swing.JTextField caseTypeTextBox;
    private javax.swing.JTextField closedDateTextBox;
    private javax.swing.JTextField currentStatusTextBox;
    private javax.swing.JTextField employeeOrgTextBox;
    private javax.swing.JTextField employerTextBox;
    private javax.swing.JTextField filedDateTextBox;
    private javax.swing.JTextField incumbentEEOTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JTextField rivalEEOTextBox;
    // End of variables declaration//GEN-END:variables
}
