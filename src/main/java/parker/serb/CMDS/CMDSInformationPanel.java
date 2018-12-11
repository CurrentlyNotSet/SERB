/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CMDSResult;
import parker.serb.sql.CMDSStatusType;
import parker.serb.sql.ReClassCode;
import parker.serb.sql.User;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class CMDSInformationPanel extends javax.swing.JPanel {

    CMDSCase orginalInformation;

    /**
     * Creates new form ORGInformationPanel
     */
    public CMDSInformationPanel() {
        initComponents();
    }

    public void clearAll() {
        caseNumberTextBox.setText("");
        aljComboBox.setSelectedItem("");
        openDateTextBox.setText("");
        closeDateTextBox.setText("");
        groupNumberTextBox.setText("");
        mediatorComboBox.setSelectedItem("");
        pbrBoxTextBox.setText("");
        groupTypeComboBox.setSelectedItem("");
        statusComboBox.setSelectedItem("");
        reclassCodeComboBox.setSelectedItem("");
        resultComboBox.setSelectedItem("");
    }

    public void loadALJComboBox() {
        aljComboBox.removeAllItems();

        aljComboBox.addItem("");

        List userList = User.loadSectionDropDowns("ALJ");

        for (Object user : userList) {
            aljComboBox.addItem((String) user);
        }

        aljComboBox.setSelectedItem("");
    }

    public void loadMediatorComboBox() {
        mediatorComboBox.removeAllItems();

        mediatorComboBox.addItem("");

        List userList = User.loadSectionDropDowns("CMDS");

        for (Object user : userList) {
            mediatorComboBox.addItem((String) user);
        }

        mediatorComboBox.setSelectedItem("");
    }

    public void loadCaseStatusComboBox() {
        statusComboBox.removeAllItems();

        statusComboBox.addItem("");

        List userList = CMDSStatusType.loadAll();

        for (Object user : userList) {
            statusComboBox.addItem((String) user);
        }

        statusComboBox.setSelectedItem("");
    }

    public void loadReclassComboBox() {
        reclassCodeComboBox.removeAllItems();

        reclassCodeComboBox.addItem("");

        List userList = ReClassCode.loadAll();

        for (Object user : userList) {
            reclassCodeComboBox.addItem((String) user);
        }

        reclassCodeComboBox.setSelectedItem("");
    }

    public void loadResultComboBox() {
        resultComboBox.removeAllItems();

        resultComboBox.addItem("");

        List userList = CMDSResult.loadAll();

        for (Object user : userList) {
            resultComboBox.addItem((String) user);
        }

        resultComboBox.setSelectedItem("");
    }

    public void loadGroupTypeComboBox() {
        groupTypeComboBox.removeAllItems();

        groupTypeComboBox.addItem("");
        groupTypeComboBox.addItem("S");
        groupTypeComboBox.addItem("M");

        groupTypeComboBox.setSelectedItem("");
    }

    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);

        aljComboBox.setEnabled(true);
        openDateTextBox.setEnabled(true);
        openDateTextBox.setBackground(Color.white);
        closeDateTextBox.setEnabled(true);
        closeDateTextBox.setBackground(Color.white);
        groupNumberTextBox.setEnabled(true);
        groupNumberTextBox.setBackground(Color.white);
        mediatorComboBox.setEnabled(true);
        pbrBoxTextBox.setEnabled(true);
        pbrBoxTextBox.setBackground(Color.white);
        groupTypeComboBox.setEnabled(true);
        statusComboBox.setEnabled(true);
        reclassCodeComboBox.setEnabled(true);
        resultComboBox.setEnabled(true);
    }

    public void disableUpdate(boolean save) {
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);

        aljComboBox.setEnabled(false);
        openDateTextBox.setEnabled(false);
        openDateTextBox.setBackground(new Color(238, 238, 238));
        closeDateTextBox.setEnabled(false);
        closeDateTextBox.setBackground(new Color(238, 238, 238));
        groupNumberTextBox.setEnabled(false);
        groupNumberTextBox.setBackground(new Color(238, 238, 238));
        mediatorComboBox.setEnabled(false);
        pbrBoxTextBox.setEnabled(false);
        pbrBoxTextBox.setBackground(new Color(238, 238, 238));
        groupTypeComboBox.setEnabled(false);
        statusComboBox.setEnabled(false);
        reclassCodeComboBox.setEnabled(false);
        resultComboBox.setEnabled(false);

        if (save) {
            saveInformation();
        }

        loadInformation();
    }

    private void saveInformation() {
        CMDSCase newInformation = new CMDSCase();

        newInformation.aljID = (aljComboBox.getSelectedItem() == null || aljComboBox.getSelectedItem().equals("")) ? 0 : (aljComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(aljComboBox.getSelectedItem().toString().trim()));
        newInformation.openDate = openDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(openDateTextBox.getText()));
        newInformation.closeDate = closeDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(closeDateTextBox.getText()));
        newInformation.groupNumber = groupNumberTextBox.getText().trim().equals("") ? null : groupNumberTextBox.getText().trim();
        newInformation.mediatorID = (mediatorComboBox.getSelectedItem() == null || mediatorComboBox.getSelectedItem().equals("")) ? 0 : (mediatorComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(mediatorComboBox.getSelectedItem().toString().trim()));

        newInformation.PBRBox = pbrBoxTextBox.getText().trim().equals("") ? null : pbrBoxTextBox.getText().trim();
        newInformation.groupType = groupTypeComboBox.getSelectedItem().toString().trim().equals("") ? null : groupTypeComboBox.getSelectedItem().toString();
        newInformation.caseStatus = statusComboBox.getSelectedItem().toString().trim().equals("") ? null : statusComboBox.getSelectedItem().toString();
        newInformation.reclassCode = reclassCodeComboBox.getSelectedItem().toString().trim().equals("") ? null : reclassCodeComboBox.getSelectedItem().toString();
        newInformation.result = resultComboBox.getSelectedItem().toString().trim().equals("") ? null : resultComboBox.getSelectedItem().toString();

        newInformation.mailedRR = rrMailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(rrMailedTextBox.getText()));
        newInformation.mailedBO = boMailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boMailedTextBox.getText()));
        newInformation.mailedPO1 = po1MailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po1MailedTextBox.getText()));
        newInformation.mailedPO2 = po2MailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po2MailedTextBox.getText()));
        newInformation.mailedPO3 = po3MailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po3MailedTextBox.getText()));
        newInformation.mailedPO4 = po4MailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po4MailedTextBox.getText()));

        newInformation.remailedRR = rrRemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(rrRemailedTextBox.getText()));
        newInformation.remailedBO = boRemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boRemailedTextBox.getText()));
        newInformation.remailedPO1 = po1RemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po1RemailedTextBox.getText()));
        newInformation.remailedPO2 = po2RemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po2RemailedTextBox.getText()));
        newInformation.remailedPO3 = po3RemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po3RemailedTextBox.getText()));
        newInformation.remailedPO4 = po4RemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po4RemailedTextBox.getText()));

        newInformation.returnReceiptRR = rrReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(rrReturnReceiptTextBox.getText()));
        newInformation.returnReceiptBO = boReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boReturnReceiptTextBox.getText()));
        newInformation.returnReceiptPO1 = po1ReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po1ReturnReceiptTextBox.getText()));
        newInformation.returnReceiptPO2 = po2ReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po2ReturnReceiptTextBox.getText()));
        newInformation.returnReceiptPO3 = po3ReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po3ReturnReceiptTextBox.getText()));
        newInformation.returnReceiptPO4 = po4ReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po4ReturnReceiptTextBox.getText()));

        newInformation.pullDateRR = rrPullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(rrPullDateTextBox.getText()));
        newInformation.pullDateBO = boPullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boPullDateTextBox.getText()));
        newInformation.pullDatePO1 = po1PullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po1PullDateTextBox.getText()));
        newInformation.pullDatePO2 = po2PullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po2PullDateTextBox.getText()));
        newInformation.pullDatePO3 = po3PullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po3PullDateTextBox.getText()));
        newInformation.pullDatePO4 = po4PullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po4PullDateTextBox.getText()));

        newInformation.hearingCompletedDate = hearingCompletedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(hearingCompletedDateTextBox.getText()));
        newInformation.postHearingBriefsDue = postHearingBriefsDueTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(postHearingBriefsDueTextBox.getText()));

        CMDSCase.updateCMDSInformation(newInformation, orginalInformation);
    }

    public void loadInformation() {
        loadALJComboBox();
        loadMediatorComboBox();
        loadGroupTypeComboBox();
        loadCaseStatusComboBox();
        loadReclassComboBox();
        loadResultComboBox();

        orginalInformation = CMDSCase.loadCMDSCaseInformation();

        caseNumberTextBox.setText(NumberFormatService.generateFullCaseNumber());
        aljComboBox.setSelectedItem(orginalInformation.aljID != 0 ? User.getNameByID(orginalInformation.aljID) : "");
        openDateTextBox.setText(orginalInformation.openDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.openDate.getTime())) : "");
        closeDateTextBox.setText(orginalInformation.closeDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.closeDate.getTime())) : "");
        groupNumberTextBox.setText(orginalInformation.groupNumber != null ? orginalInformation.groupNumber : "");
        mediatorComboBox.setSelectedItem(orginalInformation.mediatorID != 0 ? User.getNameByID(orginalInformation.mediatorID) : "");

        pbrBoxTextBox.setText(orginalInformation.PBRBox != null ? orginalInformation.PBRBox : "");
        groupTypeComboBox.setSelectedItem(orginalInformation.groupType != null ? orginalInformation.groupType : "");
        statusComboBox.setSelectedItem(orginalInformation.caseStatus != null ? orginalInformation.caseStatus : "");
        reclassCodeComboBox.setSelectedItem(orginalInformation.reclassCode != null ? orginalInformation.reclassCode : "");
        resultComboBox.setSelectedItem(orginalInformation.result != null ? orginalInformation.result : "");

        rrMailedTextBox.setText(orginalInformation.mailedRR != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedRR.getTime())) : "");
        rrRemailedTextBox.setText(orginalInformation.remailedRR != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedRR.getTime())) : "");
        rrReturnReceiptTextBox.setText(orginalInformation.returnReceiptRR != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptRR.getTime())) : "");
        rrPullDateTextBox.setText(orginalInformation.pullDateRR != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDateRR.getTime())) : "");

        boMailedTextBox.setText(orginalInformation.mailedBO != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedBO.getTime())) : "");
        boRemailedTextBox.setText(orginalInformation.remailedBO != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedBO.getTime())) : "");
        boReturnReceiptTextBox.setText(orginalInformation.returnReceiptBO != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptBO.getTime())) : "");
        boPullDateTextBox.setText(orginalInformation.pullDateBO != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDateBO.getTime())) : "");

        po1MailedTextBox.setText(orginalInformation.mailedPO1 != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedPO1.getTime())) : "");
        po1RemailedTextBox.setText(orginalInformation.remailedPO1 != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedPO1.getTime())) : "");
        po1ReturnReceiptTextBox.setText(orginalInformation.returnReceiptPO1 != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptPO1.getTime())) : "");
        po1PullDateTextBox.setText(orginalInformation.pullDatePO1 != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDatePO1.getTime())) : "");

        po2MailedTextBox.setText(orginalInformation.mailedPO2 != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedPO2.getTime())) : "");
        po2RemailedTextBox.setText(orginalInformation.remailedPO2 != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedPO2.getTime())) : "");
        po2ReturnReceiptTextBox.setText(orginalInformation.returnReceiptPO2 != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptPO2.getTime())) : "");
        po2PullDateTextBox.setText(orginalInformation.pullDatePO2 != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDatePO2.getTime())) : "");

        po3MailedTextBox.setText(orginalInformation.mailedPO3 != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedPO3.getTime())) : "");
        po3RemailedTextBox.setText(orginalInformation.remailedPO3 != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedPO3.getTime())) : "");
        po3ReturnReceiptTextBox.setText(orginalInformation.returnReceiptPO3 != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptPO3.getTime())) : "");
        po3PullDateTextBox.setText(orginalInformation.pullDatePO3 != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDatePO3.getTime())) : "");

        po4MailedTextBox.setText(orginalInformation.mailedPO4 != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedPO4.getTime())) : "");
        po4RemailedTextBox.setText(orginalInformation.remailedPO4 != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedPO4.getTime())) : "");
        po4ReturnReceiptTextBox.setText(orginalInformation.returnReceiptPO4 != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptPO4.getTime())) : "");
        po4PullDateTextBox.setText(orginalInformation.pullDatePO4 != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDatePO4.getTime())) : "");

        hearingCompletedDateTextBox.setText(orginalInformation.hearingCompletedDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.hearingCompletedDate.getTime())) : "");
        postHearingBriefsDueTextBox.setText(orginalInformation.postHearingBriefsDue != null ? Global.mmddyyyy.format(new Date(orginalInformation.postHearingBriefsDue.getTime())) : "");

    }

    private void clearDate(WebDateField dateField, MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON3 && dateField.isEnabled()) {
            ClearDateDialog dialog = new ClearDateDialog((JFrame) Global.root, true);
            if (dialog.isReset()) {
                dateField.setText("");
            }
            dialog.dispose();
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        openDateTextBox = new com.alee.extended.date.WebDateField();
        closeDateTextBox = new com.alee.extended.date.WebDateField();
        aljComboBox = new javax.swing.JComboBox<>();
        caseNumberTextBox = new javax.swing.JTextField();
        groupNumberTextBox = new javax.swing.JTextField();
        mediatorComboBox = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        pbrBoxTextBox = new javax.swing.JTextField();
        groupTypeComboBox = new javax.swing.JComboBox<>();
        statusComboBox = new javax.swing.JComboBox<>();
        reclassCodeComboBox = new javax.swing.JComboBox<>();
        resultComboBox = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        rrMailedTextBox = new javax.swing.JTextField();
        rrRemailedTextBox = new javax.swing.JTextField();
        rrReturnReceiptTextBox = new javax.swing.JTextField();
        rrPullDateTextBox = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        boMailedTextBox = new javax.swing.JTextField();
        boRemailedTextBox = new javax.swing.JTextField();
        boReturnReceiptTextBox = new javax.swing.JTextField();
        boPullDateTextBox = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        po1MailedTextBox = new javax.swing.JTextField();
        po1RemailedTextBox = new javax.swing.JTextField();
        po1ReturnReceiptTextBox = new javax.swing.JTextField();
        po1PullDateTextBox = new javax.swing.JTextField();
        jPanel12 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        po2MailedTextBox = new javax.swing.JTextField();
        po2RemailedTextBox = new javax.swing.JTextField();
        po2ReturnReceiptTextBox = new javax.swing.JTextField();
        po2PullDateTextBox = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        po3MailedTextBox = new javax.swing.JTextField();
        po3RemailedTextBox = new javax.swing.JTextField();
        po3ReturnReceiptTextBox = new javax.swing.JTextField();
        po3PullDateTextBox = new javax.swing.JTextField();
        jPanel14 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        po4MailedTextBox = new javax.swing.JTextField();
        po4RemailedTextBox = new javax.swing.JTextField();
        po4ReturnReceiptTextBox = new javax.swing.JTextField();
        po4PullDateTextBox = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        hearingCompletedDateTextBox = new javax.swing.JTextField();
        postHearingBriefsDueTextBox = new javax.swing.JTextField();

        jLabel1.setText("Case Number:");

        jLabel2.setText("ALJ:");

        jLabel3.setText("Open Date:");

        jLabel4.setText("Close Date:");

        jLabel5.setText("Group Number:");

        jLabel6.setText("Mediator:");

        openDateTextBox.setEditable(false);
        openDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        openDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        openDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        openDateTextBox.setEnabled(false);
        openDateTextBox.setDateFormat(Global.mmddyyyy);

        openDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            openDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    openDateTextBoxMouseClicked(evt);
                }
            });

            closeDateTextBox.setEditable(false);
            closeDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
            closeDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
            closeDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            closeDateTextBox.setEnabled(false);
            closeDateTextBox.setDateFormat(Global.mmddyyyy);

            closeDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );
                closeDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        closeDateTextBoxMouseClicked(evt);
                    }
                });

                aljComboBox.setEnabled(false);

                caseNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
                caseNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                caseNumberTextBox.setEnabled(false);

                groupNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
                groupNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                groupNumberTextBox.setEnabled(false);
                groupNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        groupNumberTextBoxMouseClicked(evt);
                    }
                });
                groupNumberTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
                    public void keyTyped(java.awt.event.KeyEvent evt) {
                        groupNumberTextBoxKeyTyped(evt);
                    }
                });

                mediatorComboBox.setEnabled(false);

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(openDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(closeDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(aljComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(caseNumberTextBox)
                            .addComponent(groupNumberTextBox)
                            .addComponent(mediatorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(0, 0, 0))
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(aljComboBox)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(openDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(closeDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(groupNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mediatorComboBox)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap(14, Short.MAX_VALUE))
                );

                jLabel7.setText("PBR Box:");

                jLabel8.setText("Group Type:");

                jLabel9.setText("Status:");

                pbrBoxTextBox.setBackground(new java.awt.Color(238, 238, 238));
                pbrBoxTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                pbrBoxTextBox.setEnabled(false);

                groupTypeComboBox.setEnabled(false);

                statusComboBox.setEnabled(false);

                reclassCodeComboBox.setEnabled(false);

                resultComboBox.setEnabled(false);

                jLabel11.setText("Reclass Code:");

                jLabel10.setText("Result:");

                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                jPanel2.setLayout(jPanel2Layout);
                jPanel2Layout.setHorizontalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, 0)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(pbrBoxTextBox)
                            .addComponent(groupTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(statusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(reclassCodeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(resultComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                );
                jPanel2Layout.setVerticalGroup(
                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(pbrBoxTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(groupTypeComboBox)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(statusComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addGap(4, 4, 4)
                                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(reclassCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(resultComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(42, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                jPanel3.setLayout(jPanel3Layout);
                jPanel3Layout.setHorizontalGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(10, 10, 10)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
                jPanel3Layout.setVerticalGroup(
                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );

                jLabel12.setText("Mailed:");

                jLabel17.setText(" ");

                jLabel18.setText("Remailed:");

                jLabel19.setText("Return Receipt:");

                jLabel20.setText("Pull Date:");

                javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
                jPanel8.setLayout(jPanel8Layout);
                jPanel8Layout.setHorizontalGroup(
                    jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel8Layout.createSequentialGroup()
                                            .addGap(56, 56, 56)
                                            .addComponent(jLabel12))
                                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(jLabel17)))
                                    .addComponent(jLabel18, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addContainerGap())
                );
                jPanel8Layout.setVerticalGroup(
                    jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel16.setText("R and R");

                rrMailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                rrMailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                rrMailedTextBox.setEnabled(false);

                rrRemailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                rrRemailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                rrRemailedTextBox.setEnabled(false);

                rrReturnReceiptTextBox.setBackground(new java.awt.Color(238, 238, 238));
                rrReturnReceiptTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                rrReturnReceiptTextBox.setEnabled(false);

                rrPullDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                rrPullDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                rrPullDateTextBox.setEnabled(false);

                javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
                jPanel9.setLayout(jPanel9Layout);
                jPanel9Layout.setHorizontalGroup(
                    jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addContainerGap()
                                .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, 131, Short.MAX_VALUE))
                            .addComponent(rrRemailedTextBox)
                            .addComponent(rrReturnReceiptTextBox)
                            .addComponent(rrPullDateTextBox)
                            .addComponent(rrMailedTextBox))
                        .addGap(0, 0, 0))
                );
                jPanel9Layout.setVerticalGroup(
                    jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel16)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rrMailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rrRemailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rrReturnReceiptTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(rrPullDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                );

                jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel21.setText("B. O.");

                boMailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                boMailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                boMailedTextBox.setEnabled(false);

                boRemailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                boRemailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                boRemailedTextBox.setEnabled(false);

                boReturnReceiptTextBox.setBackground(new java.awt.Color(238, 238, 238));
                boReturnReceiptTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                boReturnReceiptTextBox.setEnabled(false);

                boPullDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                boPullDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                boPullDateTextBox.setEnabled(false);

                javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
                jPanel10.setLayout(jPanel10Layout);
                jPanel10Layout.setHorizontalGroup(
                    jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(boMailedTextBox)
                    .addComponent(boRemailedTextBox)
                    .addComponent(boReturnReceiptTextBox)
                    .addComponent(boPullDateTextBox)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                        .addContainerGap())
                );
                jPanel10Layout.setVerticalGroup(
                    jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boMailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boRemailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boReturnReceiptTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(boPullDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel22.setText("P. O. #1");

                po1MailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po1MailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po1MailedTextBox.setEnabled(false);

                po1RemailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po1RemailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po1RemailedTextBox.setEnabled(false);

                po1ReturnReceiptTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po1ReturnReceiptTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po1ReturnReceiptTextBox.setEnabled(false);

                po1PullDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po1PullDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po1PullDateTextBox.setEnabled(false);

                javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
                jPanel11.setLayout(jPanel11Layout);
                jPanel11Layout.setHorizontalGroup(
                    jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(po1MailedTextBox)
                    .addComponent(po1RemailedTextBox)
                    .addComponent(po1ReturnReceiptTextBox)
                    .addComponent(po1PullDateTextBox)
                );
                jPanel11Layout.setVerticalGroup(
                    jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel11Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po1MailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po1RemailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po1ReturnReceiptTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po1PullDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel23.setText("P. O. #2");

                po2MailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po2MailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po2MailedTextBox.setEnabled(false);

                po2RemailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po2RemailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po2RemailedTextBox.setEnabled(false);

                po2ReturnReceiptTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po2ReturnReceiptTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po2ReturnReceiptTextBox.setEnabled(false);

                po2PullDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po2PullDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po2PullDateTextBox.setEnabled(false);

                javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
                jPanel12.setLayout(jPanel12Layout);
                jPanel12Layout.setHorizontalGroup(
                    jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, 125, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(po2MailedTextBox)
                    .addComponent(po2RemailedTextBox)
                    .addComponent(po2ReturnReceiptTextBox)
                    .addComponent(po2PullDateTextBox)
                );
                jPanel12Layout.setVerticalGroup(
                    jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po2MailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po2RemailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po2ReturnReceiptTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po2PullDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                );

                jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel24.setText("P. O. #3");

                po3MailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po3MailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po3MailedTextBox.setEnabled(false);

                po3RemailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po3RemailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po3RemailedTextBox.setEnabled(false);

                po3ReturnReceiptTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po3ReturnReceiptTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po3ReturnReceiptTextBox.setEnabled(false);

                po3PullDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po3PullDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po3PullDateTextBox.setEnabled(false);

                javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
                jPanel13.setLayout(jPanel13Layout);
                jPanel13Layout.setHorizontalGroup(
                    jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel24, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(po3MailedTextBox)
                    .addComponent(po3RemailedTextBox)
                    .addComponent(po3ReturnReceiptTextBox)
                    .addComponent(po3PullDateTextBox)
                );
                jPanel13Layout.setVerticalGroup(
                    jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po3MailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po3RemailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po3ReturnReceiptTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po3PullDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel25.setText("P. O. #4");

                po4MailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po4MailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po4MailedTextBox.setEnabled(false);

                po4RemailedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po4RemailedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po4RemailedTextBox.setEnabled(false);

                po4ReturnReceiptTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po4ReturnReceiptTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po4ReturnReceiptTextBox.setEnabled(false);

                po4PullDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                po4PullDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                po4PullDateTextBox.setEnabled(false);

                javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
                jPanel14.setLayout(jPanel14Layout);
                jPanel14Layout.setHorizontalGroup(
                    jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25, javax.swing.GroupLayout.DEFAULT_SIZE, 126, Short.MAX_VALUE)
                        .addContainerGap())
                    .addComponent(po4MailedTextBox)
                    .addComponent(po4RemailedTextBox)
                    .addComponent(po4ReturnReceiptTextBox)
                    .addComponent(po4PullDateTextBox)
                );
                jPanel14Layout.setVerticalGroup(
                    jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel14Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po4MailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po4RemailedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po4ReturnReceiptTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(po4PullDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                jLabel14.setText("Hearing Completed Date:");

                jLabel15.setText("Post Hearing Briefs Due:");

                hearingCompletedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                hearingCompletedDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                hearingCompletedDateTextBox.setEnabled(false);

                postHearingBriefsDueTextBox.setBackground(new java.awt.Color(238, 238, 238));
                postHearingBriefsDueTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                postHearingBriefsDueTextBox.setEnabled(false);

                javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                jPanel4.setLayout(jPanel4Layout);
                jPanel4Layout.setHorizontalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(0, 0, 0)
                                .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel15)
                                    .addComponent(jLabel14))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(hearingCompletedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(postHearingBriefsDueTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(0, 612, Short.MAX_VALUE)))
                        .addContainerGap())
                );
                jPanel4Layout.setVerticalGroup(
                    jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel13, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel9, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel10, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel14)
                            .addComponent(hearingCompletedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel15)
                            .addComponent(postHearingBriefsDueTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                this.setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );
            }// </editor-fold>//GEN-END:initComponents

    private void openDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openDateTextBoxMouseClicked
        clearDate(openDateTextBox, evt);
    }//GEN-LAST:event_openDateTextBoxMouseClicked

    private void closeDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeDateTextBoxMouseClicked
        clearDate(closeDateTextBox, evt);
    }//GEN-LAST:event_closeDateTextBoxMouseClicked

    private void groupNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupNumberTextBoxMouseClicked
        if (groupNumberTextBox.isEnabled() && evt.getClickCount() == 2) {
            new CMDSGroupNumberDialog(Global.root, true, groupNumberTextBox.getText().trim());
            loadInformation();
        }
    }//GEN-LAST:event_groupNumberTextBoxMouseClicked

    private void groupNumberTextBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_groupNumberTextBoxKeyTyped
        if (groupNumberTextBox.getText().length() >= 8) {
            evt.consume();
            groupNumberTextBox.setText(groupNumberTextBox.getText().substring(0, 8));
            WebOptionPane.showMessageDialog(Global.root, "Group Number is limited to 8 characters max.", "Warning", WebOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_groupNumberTextBoxKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> aljComboBox;
    private javax.swing.JTextField boMailedTextBox;
    private javax.swing.JTextField boPullDateTextBox;
    private javax.swing.JTextField boRemailedTextBox;
    private javax.swing.JTextField boReturnReceiptTextBox;
    private javax.swing.JTextField caseNumberTextBox;
    private com.alee.extended.date.WebDateField closeDateTextBox;
    private javax.swing.JTextField groupNumberTextBox;
    private javax.swing.JComboBox<String> groupTypeComboBox;
    private javax.swing.JTextField hearingCompletedDateTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JComboBox<String> mediatorComboBox;
    private com.alee.extended.date.WebDateField openDateTextBox;
    private javax.swing.JTextField pbrBoxTextBox;
    private javax.swing.JTextField po1MailedTextBox;
    private javax.swing.JTextField po1PullDateTextBox;
    private javax.swing.JTextField po1RemailedTextBox;
    private javax.swing.JTextField po1ReturnReceiptTextBox;
    private javax.swing.JTextField po2MailedTextBox;
    private javax.swing.JTextField po2PullDateTextBox;
    private javax.swing.JTextField po2RemailedTextBox;
    private javax.swing.JTextField po2ReturnReceiptTextBox;
    private javax.swing.JTextField po3MailedTextBox;
    private javax.swing.JTextField po3PullDateTextBox;
    private javax.swing.JTextField po3RemailedTextBox;
    private javax.swing.JTextField po3ReturnReceiptTextBox;
    private javax.swing.JTextField po4MailedTextBox;
    private javax.swing.JTextField po4PullDateTextBox;
    private javax.swing.JTextField po4RemailedTextBox;
    private javax.swing.JTextField po4ReturnReceiptTextBox;
    private javax.swing.JTextField postHearingBriefsDueTextBox;
    private javax.swing.JComboBox<String> reclassCodeComboBox;
    private javax.swing.JComboBox<String> resultComboBox;
    private javax.swing.JTextField rrMailedTextBox;
    private javax.swing.JTextField rrPullDateTextBox;
    private javax.swing.JTextField rrRemailedTextBox;
    private javax.swing.JTextField rrReturnReceiptTextBox;
    private javax.swing.JComboBox<String> statusComboBox;
    // End of variables declaration//GEN-END:variables
}
