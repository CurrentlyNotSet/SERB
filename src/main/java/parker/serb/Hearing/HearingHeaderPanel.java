/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.Hearing;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.Date;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import parker.serb.Global;
import parker.serb.sql.Audit;
import parker.serb.sql.CaseParty;
import parker.serb.sql.CaseType;
import parker.serb.sql.HearingCase;
import parker.serb.sql.HearingsMediation;
import parker.serb.sql.User;
import parker.serb.util.CaseInEditModeDialog;
import parker.serb.util.CaseNotFoundDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parker
 */
public class HearingHeaderPanel extends javax.swing.JPanel {

    HearingCaseSearch search = null;
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
//                Global.root.getHearingRootPanel1().getjTabbedPane1().setSelectedIndex(0);
                if(caseNumberComboBox.getSelectedItem().toString().trim().equals("")) {
                    Global.root.getHearingRootPanel1().clearAll();
                    if(Global.root != null) {
//                        if(Global.caseNumber == null) {
                            Global.root.getjButton2().setText("Add Entry");
                            Global.root.getjButton2().setEnabled(false);
                            Global.root.getjButton4().setEnabled(false);
                            Global.root.getjButton9().setVisible(false);
//                        } else {
//                            Global.root.getjButton2().setText("Add Entry");
//                            Global.root.getjButton2().setEnabled(true);
//                        }

//                        Global.caseNumber = null;
//                        Global.caseMonth = null;
//                        Global.caseType = null;
//                        Global.caseYear = null;
                        Global.root.getHearingRootPanel1().clearAll();
                    }
                } else {
                    Global.root.getHearingRootPanel1().clearAll();
                    caseNumberComboBox.setSelectedItem(caseNumberComboBox.getSelectedItem().toString().toUpperCase());
                    loadInformation();
//                    if(Global.root.getHearingRootPanel1().getjTabbedPane1().getSelectedIndex() == 0) {
//                        Global.root.getHearingRootPanel1().getActivityPanel1().loadAllHearingActivity();
//                        Global.root.getjButton2().setText("Add Entry");
//                        Global.root.getjButton2().setEnabled(true);
//                        Global.root.getjButton4().setText("Documents");
//                        Global.root.getjButton4().setEnabled(true);
//                        Global.root.getjButton9().setVisible(true);
//                    }
                    Audit.addAuditEntry("Loaded Case: " + caseNumberComboBox.getSelectedItem().toString().trim());
                }
            }
        });
    }

    private void loadInformation() {
        if (caseNumberComboBox.getSelectedItem().toString().trim().length() == 16) {
            NumberFormatService.parseFullCaseNumber(caseNumberComboBox.getSelectedItem().toString().trim());
            String selectedSection = CaseType.getSectionFromCaseType(Global.caseType);
            if ("MED".equalsIgnoreCase(selectedSection)
                    || "REP".equalsIgnoreCase(selectedSection)
                    || "ULP".equalsIgnoreCase(selectedSection)) {
                User.updateLastCaseNumber();
                loadHeaderInformation();
                Global.root.getHearingRootPanel1().loadInformation();
                Global.root.getHearingRootPanel1().setButtons();
            } else {
                caseNumberComboBox.setSelectedItem("");
                Global.root.getHearingRootPanel1().clearAll();
                WebOptionPane.showMessageDialog(Global.root,
                        "<html><center>Unable to load case, invalid case section<br><br>Please use the " + selectedSection + " tab</center></html>",
                        "Error", WebOptionPane.ERROR_MESSAGE);
            }
        } else {
            new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
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
                new CaseNotFoundDialog((JFrame) getRootPane().getParent(), true, caseNumberComboBox.getSelectedItem().toString());
                caseNumberComboBox.setSelectedItem("");
            } else {
                aljTextBox.setText(User.getNameByID(hearings.aljID));
                pcDateTextBox.setText(hearings.boardActionPCDate != null ? Global.mmddyyyy.format(new Date(hearings.boardActionPCDate.getTime())) : "");
                statusTextBox.setText(hearings.openClose);
                finalResultTextBox.setText(hearings.FinalResult);
                mediatorTextBox.setText(HearingsMediation.getLastestMediatorByCase());

                List caseParties = CaseParty.loadPartiesByCase();

                for(Object caseParty: caseParties) {
                    CaseParty partyInformation = (CaseParty) caseParty;

                    String name = "";

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
                            if(party1.equals("")) {
                                party1 += name;
                            } else {
                                party1 += ", " + name;
                            }
                            break;
                        case "Employer REP":
                            if(party2.equals("")) {
                                party2 += name;
                            } else {
                                party2 += ", " + name;
                            }
                            break;
                        case "Employee Organization":
                            if(party3.equals("")) {
                                party3 += name;
                            } else {
                                party3 += ", " + name;
                            }
                            break;
                        case "Employee Organization REP":
                            if(party4.equals("")) {
                                party4 += name;
                            } else {
                                party4 += ", " + name;
                            }
                            break;
                        case "Charging Party":
                            if(party1.equals("")) {
                                party1 += name;
                            } else {
                                party1 += ", " + name;
                            }
                            break;
                        case "Charging Party REP":
                            if(party2.equals("")) {
                                party2 += name;
                            } else {
                                party2 += ", " + name;
                            }
                            break;
                        case "Charged Party":
                            if(party3.equals("")) {
                                party3 += name;
                            } else {
                                party3 += ", " + name;
                            }
                            break;
                        case "Charged Party REP":
                            if(party4.equals("")) {
                                party4 += name;
                            } else {
                                party4 += ", " + name;
                            }
                            break;
                    }
                }
                party1TextBox.setText(party1);
                party2TextBox.setText(party2);
                party3TextBox.setText(party3);
                party4TextBox.setText(party4);
            }
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
        Global.caseYear = null;
        Global.caseType = null;
        Global.caseMonth = null;
        Global.caseNumber = null;
        aljTextBox.setText("");
        pcDateTextBox.setText("");
        statusTextBox.setText("");
        finalResultTextBox.setText("");
        mediatorTextBox.setText("");
        party1TextBox.setText("");
        party2TextBox.setText("");
        party3TextBox.setText("");
        party4TextBox.setText("");
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
        mediatorTextBox = new javax.swing.JTextField();
        party1Label = new javax.swing.JLabel();
        party2Label = new javax.swing.JLabel();
        party1TextBox = new javax.swing.JTextField();
        party2TextBox = new javax.swing.JTextField();
        party3Label = new javax.swing.JLabel();
        party3TextBox = new javax.swing.JTextField();
        party4Label = new javax.swing.JLabel();
        party4TextBox = new javax.swing.JTextField();

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

        mediatorTextBox.setEditable(false);
        mediatorTextBox.setBackground(new java.awt.Color(238, 238, 238));

        party1Label.setText("Employer:");

        party2Label.setText("Employer Rep:");

        party1TextBox.setEditable(false);
        party1TextBox.setBackground(new java.awt.Color(238, 238, 238));

        party2TextBox.setEditable(false);
        party2TextBox.setBackground(new java.awt.Color(238, 238, 238));

        party3Label.setText("Employee Org:");

        party3TextBox.setEditable(false);
        party3TextBox.setBackground(new java.awt.Color(238, 238, 238));

        party4Label.setText("Employee Org Rep:");

        party4TextBox.setEditable(false);
        party4TextBox.setBackground(new java.awt.Color(238, 238, 238));

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
                    .addComponent(mediatorTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE)
                    .addComponent(party1TextBox)
                    .addComponent(party2TextBox)
                    .addComponent(party3TextBox)
                    .addComponent(party4TextBox))
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
                    .addComponent(party1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(party1Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(party2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(party2Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(party3TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(party3Label))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(party4Label)
                    .addComponent(party4TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(22, Short.MAX_VALUE))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void jLabel11MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel11MouseClicked
        if(caseNumberComboBox.isEnabled()) {
            if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
                if(search == null) {
                    search = new HearingCaseSearch(Global.root, true);
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
    private javax.swing.JComboBox caseNumberComboBox;
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
    private javax.swing.JTextField mediatorTextBox;
    private javax.swing.JLabel party1Label;
    private javax.swing.JTextField party1TextBox;
    private javax.swing.JLabel party2Label;
    private javax.swing.JTextField party2TextBox;
    private javax.swing.JLabel party3Label;
    private javax.swing.JTextField party3TextBox;
    private javax.swing.JLabel party4Label;
    private javax.swing.JTextField party4TextBox;
    private javax.swing.JTextField pcDateTextBox;
    private javax.swing.JTextField rivalEEOTextBox1;
    private javax.swing.JTextField statusTextBox;
    // End of variables declaration//GEN-END:variables
}
