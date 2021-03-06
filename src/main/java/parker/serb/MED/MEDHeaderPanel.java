/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import com.alee.laf.optionpane.WebOptionPane;
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
import parker.serb.sql.CaseType;
import parker.serb.sql.MEDCase;
import parker.serb.sql.Mediator;
import parker.serb.sql.User;
import parker.serb.util.CaseInEditModeDialog;
import parker.serb.util.CaseNotFoundDialog;
import parker.serb.util.NumberFormatService;

//

/**
 *
 * @author parker
 */
public class MEDHeaderPanel extends javax.swing.JPanel {

    MEDCaseSearch search = null;

    /**
     * Creates new form REPHeaderPanel
     */
    public MEDHeaderPanel() {
        initComponents();
        addListeners();
    }

    private void addListeners() {
        caseNumberComboBox.addActionListener((ActionEvent e) -> {
            if(caseNumberComboBox.getSelectedItem() != null) {
                if(caseNumberComboBox.getSelectedItem().toString().trim().equals("")) {
                    if(Global.root != null) {
                        Global.root.getjButton2().setText("Update");
                        Global.root.getjButton2().setEnabled(false);
                        Global.root.getjButton3().setEnabled(false);
                        Global.root.getjButton9().setVisible(false);
                        Global.root.getmEDRootPanel1().clearAll();
                    }
                } else {
                    Global.root.getjButton2().setEnabled(true);
                    Global.root.getjButton3().setEnabled(true);
                    caseNumberComboBox.setSelectedItem(caseNumberComboBox.getSelectedItem().toString().toUpperCase());
                    loadInformation();
                    Audit.addAuditEntry("Loaded Case: " + caseNumberComboBox.getSelectedItem().toString().trim());
                }
            }
        });
    }

    private void loadInformation() {
        if (caseNumberComboBox.getSelectedItem().toString().trim().length() == 16) {
            if(MEDCase.validateCaseNumber(caseNumberComboBox.getSelectedItem().toString().trim())) {
                NumberFormatService.parseFullCaseNumber(caseNumberComboBox.getSelectedItem().toString().trim());
                User.updateLastCaseNumber();
                loadHeaderInformation();
                Global.root.getmEDRootPanel1().loadInformation();
                Global.root.getmEDRootPanel1().setButtons();
            } else {
                new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
                Global.root.getmEDRootPanel1().clearAll();
            }
        } else {
            new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
            Global.root.getmEDRootPanel1().clearAll();
        }
    }

    public void loadHeaderInformation() {
        String employer = "";
        String employerREP = "";
        String employeeOrg = "";
        String employeeOrgREP = "";

        if(Global.caseNumber != null) {
            MEDCase med = MEDCase.loadHeaderInformation();
            if(med == null) {
                new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
            } else {
                fileDateTextBox.setText(med.fileDate != null ? Global.mmddyyyy.format(new Date(med.fileDate.getTime())) : "");
                if(med.FMCSMediatorAppointedID == null &&
                    med.stateMediatorAppointedID != null) {
                    mediatorTextBox.setText(Mediator.getMediatorNameByID(med.stateMediatorAppointedID));
                    mediatorPhoneNumber.setText(NumberFormatService.convertStringToPhoneNumber(Mediator.getMediatorPhoneByID(med.stateMediatorAppointedID)));
                } else if(med.FMCSMediatorAppointedID != null &&
                    med.stateMediatorAppointedID == null) {
                    mediatorTextBox.setText(Mediator.getMediatorNameByID(med.FMCSMediatorAppointedID));
                    mediatorPhoneNumber.setText(NumberFormatService.convertStringToPhoneNumber(Mediator.getMediatorPhoneByID(med.FMCSMediatorAppointedID)));
                } else {
                    mediatorTextBox.setText("");
                    mediatorPhoneNumber.setText("");
                }
                statusTextBox.setText(med.caseStatus != null ? med.caseStatus : "");

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
                        case "Employer REP":
                            if(employerREP.equals("")) {
                                employerREP += name;
                            } else {
                                employerREP += ", " + name;
                            }
                            break;
                        case "Employee Organization":
                            if(employeeOrg.equals("")) {
                                employeeOrg += name;
                            } else {
                                employeeOrg += ", " + name;
                            }
                            break;
                        case "Employee Organization REP":
                            if(employeeOrgREP.equals("")) {
                                employeeOrgREP += name;
                            } else {
                                employeeOrgREP += ", " + name;
                            }
                            break;
                    }
                }
                employerTextBox.setText(employer);
                employerTextBox.setCaretPosition(0);
                employerRepTextBox.setText(employerREP);
                employerRepTextBox.setCaretPosition(0);
                employeeOrgTextBox.setText(employeeOrg);
                employeeOrgTextBox.setCaretPosition(0);
                employeeOrgRepTextBox.setText(employeeOrgREP);
                employeeOrgRepTextBox.setCaretPosition(0);
            }
        }
    }

    public void loadCases() {
        caseNumberComboBox.removeAllItems();
        caseNumberComboBox.addItem("");

        List caseNumberList = MEDCase.loadMEDCaseNumbers();

        caseNumberList.stream().forEach((caseNumber) -> {
            caseNumberComboBox.addItem(caseNumber.toString());
        });
    }

    void clearAll() {
        Global.caseYear = null;
        Global.caseType = null;
        Global.caseMonth = null;
        Global.caseNumber = null;
        employerTextBox.setText("");
        employerRepTextBox.setText("");
        employeeOrgTextBox.setText("");
        employeeOrgRepTextBox.setText("");
        mediatorTextBox.setText("");
        mediatorPhoneNumber.setText("");
        fileDateTextBox.setText("");
        statusTextBox.setText("");
        mediatorTextBox.setText("");
    }

    public JComboBox getjComboBox2() {
        return caseNumberComboBox;
    }

    public JTextField getEmployeeOrgTextBox() {
        return employeeOrgTextBox;
    }

    public JTextField getEmployerTextBox() {
        return employerTextBox;
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
        employerTextBox = new javax.swing.JTextField();
        employerRepTextBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        employeeOrgTextBox = new javax.swing.JTextField();
        employeeOrgRepTextBox = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        mediatorTextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        mediatorPhoneNumber = new javax.swing.JTextField();
        fileDateTextBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        statusTextBox = new javax.swing.JTextField();

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

        jLabel1.setText("Employer:");

        jLabel2.setText("Employer REP:");

        employerTextBox.setEditable(false);
        employerTextBox.setBackground(new java.awt.Color(238, 238, 238));

        employerRepTextBox.setEditable(false);
        employerRepTextBox.setBackground(new java.awt.Color(238, 238, 238));

        jLabel3.setText("Employee Org:");

        jLabel4.setText("Employee Org REP:");

        employeeOrgTextBox.setEditable(false);
        employeeOrgTextBox.setBackground(new java.awt.Color(238, 238, 238));

        employeeOrgRepTextBox.setEditable(false);
        employeeOrgRepTextBox.setBackground(new java.awt.Color(238, 238, 238));

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
                    .addComponent(caseNumberComboBox, 0, 200, Short.MAX_VALUE)
                    .addComponent(employerTextBox)
                    .addComponent(employerRepTextBox)
                    .addComponent(employeeOrgTextBox)
                    .addComponent(employeeOrgRepTextBox))
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
                    .addComponent(employerRepTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeOrgTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(employeeOrgRepTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        add(jPanel1);

        jLabel12.setText("Mediator:");

        mediatorTextBox.setEditable(false);
        mediatorTextBox.setBackground(new java.awt.Color(238, 238, 238));

        jLabel5.setText("Mediator Phone:");

        jLabel7.setText("File Date:");

        mediatorPhoneNumber.setEditable(false);
        mediatorPhoneNumber.setBackground(new java.awt.Color(238, 238, 238));

        fileDateTextBox.setEditable(false);
        fileDateTextBox.setBackground(new java.awt.Color(238, 238, 238));

        jLabel8.setText("Status:");

        statusTextBox.setEditable(false);
        statusTextBox.setBackground(new java.awt.Color(238, 238, 238));

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
                    .addComponent(mediatorTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
                    .addComponent(mediatorPhoneNumber)
                    .addComponent(fileDateTextBox)
                    .addComponent(statusTextBox))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(mediatorTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(mediatorPhoneNumber, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addGap(40, 40, 40)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(statusTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addContainerGap(12, Short.MAX_VALUE))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        if(caseNumberComboBox.isEnabled()) {
            if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
                if(search == null) {
                    search = new MEDCaseSearch((JFrame) getRootPane().getParent(), true);
                } else {
                    search.setVisible(true);
                }
            }
        } else {
            new CaseInEditModeDialog(Global.root, true);
        }
    }//GEN-LAST:event_jLabel11MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox caseNumberComboBox;
    private javax.swing.JTextField employeeOrgRepTextBox;
    private javax.swing.JTextField employeeOrgTextBox;
    private javax.swing.JTextField employerRepTextBox;
    private javax.swing.JTextField employerTextBox;
    private javax.swing.JTextField fileDateTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField mediatorPhoneNumber;
    private javax.swing.JTextField mediatorTextBox;
    private javax.swing.JTextField rivalEEOTextBox1;
    private javax.swing.JTextField statusTextBox;
    // End of variables declaration//GEN-END:variables
}
