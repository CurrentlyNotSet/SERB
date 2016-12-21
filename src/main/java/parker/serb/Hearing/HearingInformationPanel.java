/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.Hearing;

import com.alee.extended.date.WebDateField;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import parker.serb.CMDS.CMDSGroupNumberDialog;
import parker.serb.Global;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CMDSResult;
import parker.serb.sql.CMDSStatusType;
import parker.serb.sql.HearingCase;
import parker.serb.sql.HearingHearing;
import parker.serb.sql.HearingsMediation;
import parker.serb.sql.ReClassCode;
import parker.serb.sql.User;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class HearingInformationPanel extends javax.swing.JPanel {

    HearingCase orginalInformation;
    /**
     * Creates new form ORGInformationPanel
     */
    public HearingInformationPanel() {
        initComponents();
        setTableColumnWidth();
        jButton1.setVisible(false);
    }
    
    private void setTableColumnWidth() {
        mediationTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        mediationTable.getColumnModel().getColumn(0).setMinWidth(0);
        mediationTable.getColumnModel().getColumn(0).setMaxWidth(0);
    }
    
    public void clearAll() {
        DefaultTableModel model = (DefaultTableModel) mediationTable.getModel();
        model.setRowCount(0);
        
        caseStatusNotesTextBox.setText("");
        
        statusComboBox.setSelectedItem("Open");
        caseTypeTextBox.setText("");
        boardActionPCDateTextBox.setText("");
        boardActionPreDDateTextBox.setText("");
        directiveIssuedDateTextBox.setText("");
        expeditedComboBox.setSelectedItem("No");
        complaintDueDateTextBox.setText("");
        draftComplaintToHearingTextBox.setText("");
        preHearingDateTextBox.setText("");
        proposedRecDueDateTextBox.setText("");
        exceptionsDateTextBox.setText("");
    }
    
//    public void loadALJComboBox() {
//        aljComboBox.removeAllItems();
//        
//        aljComboBox.addItem("");
//        
//        List userList = User.loadULPComboBox();
//        
//        for (Object user : userList) {
//            aljComboBox.addItem((String) user);
//        }
//        
//        aljComboBox.setSelectedItem("");
//    }
    
//    public void loadMediatorComboBox() {
//        mediatorComboBox.removeAllItems();
//        
//        mediatorComboBox.addItem("");
//        
//        List userList = User.loadULPComboBox();
//        
//        for (Object user : userList) {
//            mediatorComboBox.addItem((String) user);
//        }
//        
//        mediatorComboBox.setSelectedItem("");
//    }
    
    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);
        
        openClosedComboBox.setEnabled(true);
        boardActionPCDateTextBox.setEnabled(true);
        boardActionPCDateTextBox.setBackground(Color.white);
        boardActionPreDDateTextBox.setEnabled(true);
        boardActionPreDDateTextBox.setBackground(Color.white);
        directiveIssuedDateTextBox.setEnabled(true);
        directiveIssuedDateTextBox.setBackground(Color.white);
        expeditedComboBox.setEnabled(true);
        complaintDueDateTextBox.setEnabled(true);
        complaintDueDateTextBox.setBackground(Color.white);
        draftComplaintToHearingTextBox.setEnabled(true);
        draftComplaintToHearingTextBox.setBackground(Color.white);
        preHearingDateTextBox.setEnabled(true);
        preHearingDateTextBox.setBackground(Color.white);
        proposedRecDueDateTextBox.setEnabled(true);
        proposedRecDueDateTextBox.setBackground(Color.white);
        exceptionsDateTextBox.setEnabled(true);
        exceptionsDateTextBox.setBackground(Color.white);
        
        caseStatusNotesTextBox.setEnabled(true);
        caseStatusNotesTextBox.setBackground(Color.white);
        
        jButton1.setVisible(true);
        
//        aljComboBox.setEnabled(true);
//        openDateTextBox.setEnabled(true);
//        openDateTextBox.setBackground(Color.white);
//        closeDateTextBox.setEnabled(true);
//        closeDateTextBox.setBackground(Color.white);
//        groupNumberTextBox.setEnabled(true);
//        groupNumberTextBox.setBackground(Color.white);
//        mediatorComboBox.setEnabled(true);
//        pbrBoxTextBox.setEnabled(true);
//        pbrBoxTextBox.setBackground(Color.white);
//        groupTypeComboBox.setEnabled(true);
//        statusComboBox.setEnabled(true);
//        reclassCodeComboBox.setEnabled(true);
//        resultComboBox.setEnabled(true);
    }
    
    public void disableUpdate(boolean save) {
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);
        
        openClosedComboBox.setEnabled(false);
        boardActionPCDateTextBox.setEnabled(false);
        boardActionPCDateTextBox.setBackground(new Color(238,238,238));
        boardActionPreDDateTextBox.setEnabled(false);
        boardActionPreDDateTextBox.setBackground(new Color(238,238,238));
        directiveIssuedDateTextBox.setEnabled(false);
        directiveIssuedDateTextBox.setBackground(new Color(238,238,238));
        expeditedComboBox.setEnabled(false);
        complaintDueDateTextBox.setEnabled(false);
        complaintDueDateTextBox.setBackground(new Color(238,238,238));
        draftComplaintToHearingTextBox.setEnabled(false);
        draftComplaintToHearingTextBox.setBackground(new Color(238,238,238));
        preHearingDateTextBox.setEnabled(false);
        preHearingDateTextBox.setBackground(new Color(238,238,238));
        proposedRecDueDateTextBox.setEnabled(false);
        proposedRecDueDateTextBox.setBackground(new Color(238,238,238));
        exceptionsDateTextBox.setEnabled(false);
        exceptionsDateTextBox.setBackground(new Color(238,238,238));
        
        caseStatusNotesTextBox.setEnabled(false);
        caseStatusNotesTextBox.setBackground(new Color(238,238,238));
        
        jButton1.setVisible(false);
        
        
//        aljComboBox.setEnabled(false);
//        openDateTextBox.setEnabled(false);
//        openDateTextBox.setBackground(new Color(238,238,238));
//        closeDateTextBox.setEnabled(false);
//        closeDateTextBox.setBackground(new Color(238,238,238));
//        groupNumberTextBox.setEnabled(false);
//        groupNumberTextBox.setBackground(new Color(238,238,238));
//        mediatorComboBox.setEnabled(false);
//        pbrBoxTextBox.setEnabled(false);
//        pbrBoxTextBox.setBackground(new Color(238,238,238));
//        groupTypeComboBox.setEnabled(false);
//        statusComboBox.setEnabled(false);
//        reclassCodeComboBox.setEnabled(false);
//        resultComboBox.setEnabled(false);

        if(save) {
            saveInformation();
        }
        
        loadInformation();
    }
    
    private void saveInformation() {
        HearingCase newInformation = new HearingCase();
        
        newInformation.openClose = openClosedComboBox.getSelectedItem().toString();
        newInformation.boardActionPCDate = boardActionPCDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boardActionPCDateTextBox.getText()));
        newInformation.boardActionPreDDate = boardActionPreDDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boardActionPreDDateTextBox.getText()));
        newInformation.directiveIssuedDate = directiveIssuedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(directiveIssuedDateTextBox.getText()));
        newInformation.expedited = expeditedComboBox.getSelectedItem().toString().equals("Yes");
        newInformation.complaintDueDate = complaintDueDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(complaintDueDateTextBox.getText()));
        newInformation.draftComplaintToHearingDate = draftComplaintToHearingTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(draftComplaintToHearingTextBox.getText()));
        newInformation.preHearingDate = preHearingDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(preHearingDateTextBox.getText()));
        newInformation.proposedRecDueDate = proposedRecDueDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(proposedRecDueDateTextBox.getText()));
        newInformation.exceptionFilingDate = exceptionsDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(exceptionsDateTextBox.getText()));
                        
        newInformation.caseStatusNotes = caseStatusNotesTextBox.getText().equals("") ? null : caseStatusNotesTextBox.getText();
        
//        newInformation.aljID = (aljComboBox.getSelectedItem() == null || aljComboBox.getSelectedItem().equals("")) ? 0 : (aljComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(aljComboBox.getSelectedItem().toString().trim()));
//        newInformation.openDate = openDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(openDateTextBox.getText()));
//        newInformation.closeDate = closeDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(closeDateTextBox.getText()));
//        newInformation.groupNumber = groupNumberTextBox.getText().trim().equals("") ? null : groupNumberTextBox.getText().trim();
//        newInformation.mediatorID = (mediatorComboBox.getSelectedItem() == null || mediatorComboBox.getSelectedItem().equals("")) ? 0 : (mediatorComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(mediatorComboBox.getSelectedItem().toString().trim()));
//        
//        newInformation.PBRBox = pbrBoxTextBox.getText().trim().equals("") ? null : pbrBoxTextBox.getText().trim();
//        newInformation.groupType = groupTypeComboBox.getSelectedItem().toString().trim().equals("") ? null : groupTypeComboBox.getSelectedItem().toString();
//        newInformation.caseStatus = statusComboBox.getSelectedItem().toString().trim().equals("") ? null : statusComboBox.getSelectedItem().toString();
//        newInformation.reclassCode = reclassCodeComboBox.getSelectedItem().toString().trim().equals("") ? null : reclassCodeComboBox.getSelectedItem().toString();
//        newInformation.result = resultComboBox.getSelectedItem().toString().trim().equals("") ? null : resultComboBox.getSelectedItem().toString();
        
//        newInformation.mailedRR = rrMailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(rrMailedTextBox.getText()));
//        newInformation.mailedBO = boMailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boMailedTextBox.getText()));
//        newInformation.mailedPO1 = po1MailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po1MailedTextBox.getText()));
//        newInformation.mailedPO2 = po2MailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po2MailedTextBox.getText()));
//        newInformation.mailedPO3 = po3MailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po3MailedTextBox.getText()));
//        newInformation.mailedPO4 = po4MailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po4MailedTextBox.getText()));
//         
//        newInformation.remailedRR = rrRemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(rrRemailedTextBox.getText()));
//        newInformation.remailedBO = boRemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boRemailedTextBox.getText()));
//        newInformation.remailedPO1 = po1RemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po1RemailedTextBox.getText()));
//        newInformation.remailedPO2 = po2RemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po2RemailedTextBox.getText()));
//        newInformation.remailedPO3 = po3RemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po3RemailedTextBox.getText()));
//        newInformation.remailedPO4 = po4RemailedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po4RemailedTextBox.getText()));
//        
//        newInformation.returnReceiptRR = rrReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(rrReturnReceiptTextBox.getText()));
//        newInformation.returnReceiptBO = boReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boReturnReceiptTextBox.getText()));
//        newInformation.returnReceiptPO1 = po1ReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po1ReturnReceiptTextBox.getText()));
//        newInformation.returnReceiptPO2 = po2ReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po2ReturnReceiptTextBox.getText()));
//        newInformation.returnReceiptPO3 = po3ReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po3ReturnReceiptTextBox.getText()));
//        newInformation.returnReceiptPO4 = po4ReturnReceiptTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po4ReturnReceiptTextBox.getText()));
//        
//        newInformation.pullDateRR = rrPullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(rrPullDateTextBox.getText()));
//        newInformation.pullDateBO = boPullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boPullDateTextBox.getText()));
//        newInformation.pullDatePO1 = po1PullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po1PullDateTextBox.getText()));
//        newInformation.pullDatePO2 = po2PullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po2PullDateTextBox.getText()));
//        newInformation.pullDatePO3 = po3PullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po3PullDateTextBox.getText()));
//        newInformation.pullDatePO4 = po4PullDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(po4PullDateTextBox.getText())); 
        
//        newInformation.hearingCompletedDate = hearingCompletedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(hearingCompletedDateTextBox.getText()));
//        newInformation.postHearingBriefsDue = postHearingBriefsDueTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(postHearingBriefsDueTextBox.getText()));
        
        HearingCase.updateHearingCaseInformation(newInformation, orginalInformation);
    }
    
    public void loadInformation() {
//        loadALJComboBox();
//        loadMediatorComboBox();
//        loadGroupTypeComboBox();
//        loadCaseStatusComboBox();
//        loadReclassComboBox();
//        loadResultComboBox();

        loadMediationTable();
        
        orginalInformation = HearingCase.loadHearingCaseInformation();
        
        openClosedComboBox.setSelectedItem(orginalInformation.openClose);
        caseTypeTextBox.setText(Global.caseType);
        boardActionPCDateTextBox.setText(orginalInformation.boardActionPCDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.boardActionPCDate.getTime())) : "");
        boardActionPreDDateTextBox.setText(orginalInformation.boardActionPreDDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.boardActionPreDDate.getTime())) : "");
        directiveIssuedDateTextBox.setText(orginalInformation.directiveIssuedDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.directiveIssuedDate.getTime())) : "");
        expeditedComboBox.setSelectedItem(orginalInformation.expedited == true ? "Yes" : "No");
        complaintDueDateTextBox.setText(orginalInformation.complaintDueDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.complaintDueDate.getTime())) : "");
        draftComplaintToHearingTextBox.setText(orginalInformation.draftComplaintToHearingDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.draftComplaintToHearingDate.getTime())) : "");
        preHearingDateTextBox.setText(orginalInformation.preHearingDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.preHearingDate.getTime())) : "");
        proposedRecDueDateTextBox.setText(orginalInformation.proposedRecDueDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.proposedRecDueDate.getTime())) : "");
        exceptionsDateTextBox.setText(orginalInformation.exceptionFilingDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.exceptionFilingDate.getTime())) : "");
        
        caseStatusNotesTextBox.setText(orginalInformation.caseStatusNotes == null ? "" : orginalInformation.caseStatusNotes);

        
        
//        caseNumberTextBox.setText(NumberFormatService.generateFullCaseNumber());
//        aljComboBox.setSelectedItem(orginalInformation.aljID != 0 ? User.getNameByID(orginalInformation.aljID) : "");
//        openDateTextBox.setText(orginalInformation.openDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.openDate.getTime())) : "");
//        closeDateTextBox.setText(orginalInformation.closeDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.closeDate.getTime())) : "");
//        groupNumberTextBox.setText(orginalInformation.groupNumber != null ? orginalInformation.groupNumber : "");
//        mediatorComboBox.setSelectedItem(orginalInformation.mediatorID != 0 ? User.getNameByID(orginalInformation.mediatorID) : "");
//
//        pbrBoxTextBox.setText(orginalInformation.PBRBox != null ? orginalInformation.PBRBox : "");
//        groupTypeComboBox.setSelectedItem(orginalInformation.groupType != null ? orginalInformation.groupType : "");
//        statusComboBox.setSelectedItem(orginalInformation.caseStatus != null ? orginalInformation.caseStatus : "");
//        reclassCodeComboBox.setSelectedItem(orginalInformation.reclassCode != null ? orginalInformation.reclassCode : "");
//        resultComboBox.setSelectedItem(orginalInformation.result != null ? orginalInformation.result : "");
        
//        rrMailedTextBox.setText(orginalInformation.mailedRR != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedRR.getTime())) : "");
//        rrRemailedTextBox.setText(orginalInformation.remailedRR != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedRR.getTime())) : "");
//        rrReturnReceiptTextBox.setText(orginalInformation.returnReceiptRR != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptRR.getTime())) : "");
//        rrPullDateTextBox.setText(orginalInformation.pullDateRR != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDateRR.getTime())) : "");
//        
//        boMailedTextBox.setText(orginalInformation.mailedBO != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedBO.getTime())) : "");
//        boRemailedTextBox.setText(orginalInformation.remailedBO != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedBO.getTime())) : "");
//        boReturnReceiptTextBox.setText(orginalInformation.returnReceiptBO != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptBO.getTime())) : "");
//        boPullDateTextBox.setText(orginalInformation.pullDateBO != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDateBO.getTime())) : "");
//
//        po1MailedTextBox.setText(orginalInformation.mailedPO1 != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedPO1.getTime())) : "");
//        po1RemailedTextBox.setText(orginalInformation.remailedPO1 != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedPO1.getTime())) : "");
//        po1ReturnReceiptTextBox.setText(orginalInformation.returnReceiptPO1 != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptPO1.getTime())) : "");
//        po1PullDateTextBox.setText(orginalInformation.pullDatePO1 != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDatePO1.getTime())) : "");
//
//        po2MailedTextBox.setText(orginalInformation.mailedPO2 != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedPO2.getTime())) : "");
//        po2RemailedTextBox.setText(orginalInformation.remailedPO2 != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedPO2.getTime())) : "");
//        po2ReturnReceiptTextBox.setText(orginalInformation.returnReceiptPO2 != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptPO2.getTime())) : "");
//        po2PullDateTextBox.setText(orginalInformation.pullDatePO2 != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDatePO2.getTime())) : "");
//
//        po3MailedTextBox.setText(orginalInformation.mailedPO3 != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedPO3.getTime())) : "");
//        po3RemailedTextBox.setText(orginalInformation.remailedPO3 != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedPO3.getTime())) : "");
//        po3ReturnReceiptTextBox.setText(orginalInformation.returnReceiptPO3 != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptPO3.getTime())) : "");
//        po3PullDateTextBox.setText(orginalInformation.pullDatePO3 != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDatePO3.getTime())) : "");
//        
//        po4MailedTextBox.setText(orginalInformation.mailedPO4 != null ? Global.mmddyyyy.format(new Date(orginalInformation.mailedPO4.getTime())) : "");
//        po4RemailedTextBox.setText(orginalInformation.remailedPO4 != null ? Global.mmddyyyy.format(new Date(orginalInformation.remailedPO4.getTime())) : "");
//        po4ReturnReceiptTextBox.setText(orginalInformation.returnReceiptPO4 != null ? Global.mmddyyyy.format(new Date(orginalInformation.returnReceiptPO4.getTime())) : "");
//        po4PullDateTextBox.setText(orginalInformation.pullDatePO4 != null ? Global.mmddyyyy.format(new Date(orginalInformation.pullDatePO4.getTime())) : "");

//        hearingCompletedDateTextBox.setText(orginalInformation.hearingCompletedDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.hearingCompletedDate.getTime())) : "");
//        postHearingBriefsDueTextBox.setText(orginalInformation.postHearingBriefsDue != null ? Global.mmddyyyy.format(new Date(orginalInformation.postHearingBriefsDue.getTime())) : "");

    }
    
    private void loadMediationTable() {
        DefaultTableModel model = (DefaultTableModel) mediationTable.getModel();
        model.setRowCount(0);
        
        List<HearingsMediation> items = HearingsMediation.loadAllMediationsByCaseNumber();
        
        for (HearingsMediation item : items) {
            model.addRow(new Object[] {
                item.id,
                item.pcPreD == null ? "" : item.pcPreD, 
                User.getNameByID(item.mediatorID),
                item.dateAssigned == null ? "" : Global.mmddyyyy.format(new Date(item.dateAssigned.getTime())),
                item.mediationDate == null ? "" : Global.mmddyyyy.format(new Date(item.mediationDate.getTime())),
                item.outcome == null ? "" : item.outcome
            });
        }
    }
    
    
    private void clearDate(WebDateField dateField, MouseEvent evt) {
        if(evt.getButton() == MouseEvent.BUTTON3 && dateField.isEnabled()) {
            ClearDateDialog dialog = new ClearDateDialog((JFrame) Global.root, true);
            if(dialog.isReset()) {
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
        boardActionPCDateTextBox = new com.alee.extended.date.WebDateField();
        boardActionPreDDateTextBox = new com.alee.extended.date.WebDateField();
        jLabel13 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        directiveIssuedDateTextBox = new com.alee.extended.date.WebDateField();
        complaintDueDateTextBox = new com.alee.extended.date.WebDateField();
        draftComplaintToHearingTextBox = new com.alee.extended.date.WebDateField();
        preHearingDateTextBox = new com.alee.extended.date.WebDateField();
        proposedRecDueDateTextBox = new com.alee.extended.date.WebDateField();
        exceptionsDateTextBox = new com.alee.extended.date.WebDateField();
        expeditedComboBox = new javax.swing.JComboBox<>();
        openClosedComboBox = new javax.swing.JComboBox<>();
        caseTypeTextBox = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        groupTypeComboBox = new javax.swing.JComboBox<>();
        statusComboBox = new javax.swing.JComboBox<>();
        jLabel11 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        pbrBoxTextBox1 = new javax.swing.JTextField();
        pbrBoxTextBox2 = new javax.swing.JTextField();
        pbrBoxTextBox3 = new javax.swing.JTextField();
        pbrBoxTextBox4 = new javax.swing.JTextField();
        pbrBoxTextBox5 = new javax.swing.JTextField();
        pbrBoxTextBox6 = new javax.swing.JTextField();
        closeDateTextBox7 = new com.alee.extended.date.WebDateField();
        closeDateTextBox8 = new com.alee.extended.date.WebDateField();
        closeDateTextBox9 = new com.alee.extended.date.WebDateField();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        caseStatusNotesTextBox = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        mediationTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Open/Closed:");

        jLabel2.setText("Case Type:");

        jLabel3.setText("Board Action PC Date:");

        jLabel4.setText("Board Action Pre-D Date:");

        jLabel5.setText("Directive Issued Date:");

        jLabel6.setText("Expedited:");

        boardActionPCDateTextBox.setEditable(false);
        boardActionPCDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        boardActionPCDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        boardActionPCDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        boardActionPCDateTextBox.setEnabled(false);
        boardActionPCDateTextBox.setDateFormat(Global.mmddyyyy);
        boardActionPCDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boardActionPCDateTextBoxMouseClicked(evt);
            }
        });

        boardActionPreDDateTextBox.setEditable(false);
        boardActionPreDDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        boardActionPreDDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        boardActionPreDDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        boardActionPreDDateTextBox.setEnabled(false);
        boardActionPreDDateTextBox.setDateFormat(Global.mmddyyyy);
        boardActionPreDDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                boardActionPreDDateTextBoxMouseClicked(evt);
            }
        });

        jLabel13.setText("Complaint Due Date:");

        jLabel26.setText("Draft Complaint to Hearing:");

        jLabel27.setText("Proposed Rec Due:");

        jLabel28.setText("Prehearing Date:");

        jLabel29.setText("Exceptions Date:");

        directiveIssuedDateTextBox.setEditable(false);
        directiveIssuedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        directiveIssuedDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        directiveIssuedDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        directiveIssuedDateTextBox.setEnabled(false);
        directiveIssuedDateTextBox.setDateFormat(Global.mmddyyyy);
        directiveIssuedDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                directiveIssuedDateTextBoxMouseClicked(evt);
            }
        });

        complaintDueDateTextBox.setEditable(false);
        complaintDueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        complaintDueDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        complaintDueDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        complaintDueDateTextBox.setEnabled(false);
        complaintDueDateTextBox.setDateFormat(Global.mmddyyyy);
        complaintDueDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                complaintDueDateTextBoxMouseClicked(evt);
            }
        });

        draftComplaintToHearingTextBox.setEditable(false);
        draftComplaintToHearingTextBox.setBackground(new java.awt.Color(238, 238, 238));
        draftComplaintToHearingTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        draftComplaintToHearingTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        draftComplaintToHearingTextBox.setEnabled(false);
        draftComplaintToHearingTextBox.setDateFormat(Global.mmddyyyy);
        draftComplaintToHearingTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                draftComplaintToHearingTextBoxMouseClicked(evt);
            }
        });

        preHearingDateTextBox.setEditable(false);
        preHearingDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        preHearingDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        preHearingDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        preHearingDateTextBox.setEnabled(false);
        preHearingDateTextBox.setDateFormat(Global.mmddyyyy);
        preHearingDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                preHearingDateTextBoxMouseClicked(evt);
            }
        });

        proposedRecDueDateTextBox.setEditable(false);
        proposedRecDueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        proposedRecDueDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        proposedRecDueDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        proposedRecDueDateTextBox.setEnabled(false);
        proposedRecDueDateTextBox.setDateFormat(Global.mmddyyyy);
        proposedRecDueDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                proposedRecDueDateTextBoxMouseClicked(evt);
            }
        });

        exceptionsDateTextBox.setEditable(false);
        exceptionsDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        exceptionsDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        exceptionsDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        exceptionsDateTextBox.setEnabled(false);
        exceptionsDateTextBox.setDateFormat(Global.mmddyyyy);
        exceptionsDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                exceptionsDateTextBoxMouseClicked(evt);
            }
        });

        expeditedComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "No", "Yes" }));
        expeditedComboBox.setEnabled(false);

        openClosedComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Open", "Closed" }));
        openClosedComboBox.setEnabled(false);

        caseTypeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        caseTypeTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        caseTypeTextBox.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel29)
                    .addComponent(jLabel27)
                    .addComponent(jLabel28)
                    .addComponent(jLabel13)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6)
                    .addComponent(jLabel1)
                    .addComponent(jLabel26, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(directiveIssuedDateTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boardActionPreDDateTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(boardActionPCDateTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(complaintDueDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(draftComplaintToHearingTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(preHearingDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(proposedRecDueDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(exceptionsDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(expeditedComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 291, Short.MAX_VALUE)
                    .addComponent(caseTypeTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(openClosedComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(openClosedComboBox)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(caseTypeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(boardActionPCDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(boardActionPreDDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(directiveIssuedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(expeditedComboBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(complaintDueDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(draftComplaintToHearingTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(preHearingDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(proposedRecDueDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(exceptionsDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        jLabel7.setText("Board Action Date:");

        jLabel8.setText("Other:");

        jLabel9.setText("ALJ Assigned:");

        groupTypeComboBox.setEnabled(false);

        statusComboBox.setEnabled(false);

        jLabel11.setText("Complaint Issued Date:");

        jLabel10.setText("Hearing Date:");

        jLabel12.setText("Proposed Rec Issued:");

        jLabel16.setText("Response Filing Date:");

        jLabel17.setText("Issuance of OP / Dir. Date:");

        jLabel18.setText("Final Result:");

        jLabel19.setText("Opinion:");

        jLabel20.setText("Companion Cases:");

        pbrBoxTextBox1.setBackground(new java.awt.Color(238, 238, 238));
        pbrBoxTextBox1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pbrBoxTextBox1.setEnabled(false);

        pbrBoxTextBox2.setBackground(new java.awt.Color(238, 238, 238));
        pbrBoxTextBox2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pbrBoxTextBox2.setEnabled(false);

        pbrBoxTextBox3.setBackground(new java.awt.Color(238, 238, 238));
        pbrBoxTextBox3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pbrBoxTextBox3.setEnabled(false);

        pbrBoxTextBox4.setBackground(new java.awt.Color(238, 238, 238));
        pbrBoxTextBox4.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pbrBoxTextBox4.setEnabled(false);

        pbrBoxTextBox5.setBackground(new java.awt.Color(238, 238, 238));
        pbrBoxTextBox5.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pbrBoxTextBox5.setEnabled(false);

        pbrBoxTextBox6.setBackground(new java.awt.Color(238, 238, 238));
        pbrBoxTextBox6.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pbrBoxTextBox6.setEnabled(false);

        closeDateTextBox7.setEditable(false);
        closeDateTextBox7.setBackground(new java.awt.Color(238, 238, 238));
        closeDateTextBox7.setCaretColor(new java.awt.Color(0, 0, 0));
        closeDateTextBox7.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        closeDateTextBox7.setEnabled(false);
        boardActionPreDDateTextBox.setDateFormat(Global.mmddyyyy);
        closeDateTextBox7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeDateTextBox7MouseClicked(evt);
            }
        });

        closeDateTextBox8.setEditable(false);
        closeDateTextBox8.setBackground(new java.awt.Color(238, 238, 238));
        closeDateTextBox8.setCaretColor(new java.awt.Color(0, 0, 0));
        closeDateTextBox8.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        closeDateTextBox8.setEnabled(false);
        boardActionPreDDateTextBox.setDateFormat(Global.mmddyyyy);
        closeDateTextBox8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeDateTextBox8MouseClicked(evt);
            }
        });

        closeDateTextBox9.setEditable(false);
        closeDateTextBox9.setBackground(new java.awt.Color(238, 238, 238));
        closeDateTextBox9.setCaretColor(new java.awt.Color(0, 0, 0));
        closeDateTextBox9.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        closeDateTextBox9.setEnabled(false);
        boardActionPreDDateTextBox.setDateFormat(Global.mmddyyyy);
        closeDateTextBox9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                closeDateTextBox9MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel12)
                    .addComponent(jLabel9)
                    .addComponent(jLabel7)
                    .addComponent(jLabel8)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pbrBoxTextBox1)
                    .addComponent(pbrBoxTextBox2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbrBoxTextBox3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbrBoxTextBox4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbrBoxTextBox5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbrBoxTextBox6, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(closeDateTextBox7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(groupTypeComboBox, 0, 292, Short.MAX_VALUE)
                    .addComponent(statusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closeDateTextBox8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closeDateTextBox9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(closeDateTextBox7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeDateTextBox8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(closeDateTextBox9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(pbrBoxTextBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(pbrBoxTextBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(pbrBoxTextBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(pbrBoxTextBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(pbrBoxTextBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(pbrBoxTextBox6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel14.setText("Case Status Notes:");

        caseStatusNotesTextBox.setBackground(new java.awt.Color(238, 238, 238));
        caseStatusNotesTextBox.setColumns(20);
        caseStatusNotesTextBox.setLineWrap(true);
        caseStatusNotesTextBox.setRows(5);
        caseStatusNotesTextBox.setWrapStyleWord(true);
        caseStatusNotesTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        caseStatusNotesTextBox.setEnabled(false);
        jScrollPane1.setViewportView(caseStatusNotesTextBox);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(jLabel14)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1)
                        .addContainerGap())
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 64, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(jLabel14)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Mediation Information");

        mediationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "PC / Pre-D", "Mediator", "Date Assigned", "Mediation Date", "Outcome"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        mediationTable.setRequestFocusEnabled(false);
        mediationTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mediationTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(mediationTable);
        if (mediationTable.getColumnModel().getColumnCount() > 0) {
            mediationTable.getColumnModel().getColumn(0).setResizable(false);
        }

        jButton1.setText("+");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 915, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 127, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void boardActionPCDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boardActionPCDateTextBoxMouseClicked
        clearDate(boardActionPCDateTextBox, evt);
    }//GEN-LAST:event_boardActionPCDateTextBoxMouseClicked

    private void boardActionPreDDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boardActionPreDDateTextBoxMouseClicked
        clearDate(boardActionPreDDateTextBox, evt);
    }//GEN-LAST:event_boardActionPreDDateTextBoxMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new HearingAddMediationDialog(Global.root, true);
        mediationTable.clearSelection();
        loadMediationTable();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void directiveIssuedDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_directiveIssuedDateTextBoxMouseClicked
        clearDate(boardActionPreDDateTextBox, evt);
    }//GEN-LAST:event_directiveIssuedDateTextBoxMouseClicked

    private void complaintDueDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_complaintDueDateTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_complaintDueDateTextBoxMouseClicked

    private void draftComplaintToHearingTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_draftComplaintToHearingTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_draftComplaintToHearingTextBoxMouseClicked

    private void preHearingDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_preHearingDateTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_preHearingDateTextBoxMouseClicked

    private void proposedRecDueDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_proposedRecDueDateTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_proposedRecDueDateTextBoxMouseClicked

    private void exceptionsDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_exceptionsDateTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_exceptionsDateTextBoxMouseClicked

    private void closeDateTextBox7MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeDateTextBox7MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_closeDateTextBox7MouseClicked

    private void closeDateTextBox8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeDateTextBox8MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_closeDateTextBox8MouseClicked

    private void closeDateTextBox9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeDateTextBox9MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_closeDateTextBox9MouseClicked

    private void mediationTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mediationTableMouseClicked
        if(evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            new HearingUpdateMediationDialog(Global.root,
                true,
                mediationTable.getValueAt(mediationTable.getSelectedRow(), 0).toString(),
                mediationTable.getValueAt(mediationTable.getSelectedRow(), 1).toString(),
                mediationTable.getValueAt(mediationTable.getSelectedRow(), 2).toString(),
                mediationTable.getValueAt(mediationTable.getSelectedRow(), 3).toString(),
                mediationTable.getValueAt(mediationTable.getSelectedRow(), 4).toString(),
                mediationTable.getValueAt(mediationTable.getSelectedRow(), 5).toString()
            );
            mediationTable.clearSelection();
            loadMediationTable();
        } else if(evt.getButton() == MouseEvent.BUTTON3) {
            new HearingRemoveMediationDialog(Global.root,
                    true,
                    mediationTable.getValueAt(mediationTable.getSelectedRow(), 0).toString(),
                    mediationTable.getValueAt(mediationTable.getSelectedRow(), 4).toString());
            mediationTable.clearSelection();
            loadMediationTable();
        } 
    }//GEN-LAST:event_mediationTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.extended.date.WebDateField boardActionPCDateTextBox;
    private com.alee.extended.date.WebDateField boardActionPreDDateTextBox;
    private javax.swing.JTextArea caseStatusNotesTextBox;
    private javax.swing.JTextField caseTypeTextBox;
    private com.alee.extended.date.WebDateField closeDateTextBox7;
    private com.alee.extended.date.WebDateField closeDateTextBox8;
    private com.alee.extended.date.WebDateField closeDateTextBox9;
    private com.alee.extended.date.WebDateField complaintDueDateTextBox;
    private com.alee.extended.date.WebDateField directiveIssuedDateTextBox;
    private com.alee.extended.date.WebDateField draftComplaintToHearingTextBox;
    private com.alee.extended.date.WebDateField exceptionsDateTextBox;
    private javax.swing.JComboBox<String> expeditedComboBox;
    private javax.swing.JComboBox<String> groupTypeComboBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable mediationTable;
    private javax.swing.JComboBox<String> openClosedComboBox;
    private javax.swing.JTextField pbrBoxTextBox1;
    private javax.swing.JTextField pbrBoxTextBox2;
    private javax.swing.JTextField pbrBoxTextBox3;
    private javax.swing.JTextField pbrBoxTextBox4;
    private javax.swing.JTextField pbrBoxTextBox5;
    private javax.swing.JTextField pbrBoxTextBox6;
    private com.alee.extended.date.WebDateField preHearingDateTextBox;
    private com.alee.extended.date.WebDateField proposedRecDueDateTextBox;
    private javax.swing.JComboBox<String> statusComboBox;
    // End of variables declaration//GEN-END:variables
}
