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
import parker.serb.CMDS.CMDSGroupNumberDialog;
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
public class HearingInformationPanel extends javax.swing.JPanel {

    CMDSCase orginalInformation;
    /**
     * Creates new form ORGInformationPanel
     */
    public HearingInformationPanel() {
        initComponents();
        jButton1.setVisible(false);
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
        
        List userList = User.loadULPComboBox();
        
        for (Object user : userList) {
            aljComboBox.addItem((String) user);
        }
        
        aljComboBox.setSelectedItem("");
    }
    
    public void loadMediatorComboBox() {
        mediatorComboBox.removeAllItems();
        
        mediatorComboBox.addItem("");
        
        List userList = User.loadULPComboBox();
        
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
        openDateTextBox.setBackground(new Color(238,238,238));
        closeDateTextBox.setEnabled(false);
        closeDateTextBox.setBackground(new Color(238,238,238));
        groupNumberTextBox.setEnabled(false);
        groupNumberTextBox.setBackground(new Color(238,238,238));
        mediatorComboBox.setEnabled(false);
        pbrBoxTextBox.setEnabled(false);
        pbrBoxTextBox.setBackground(new Color(238,238,238));
        groupTypeComboBox.setEnabled(false);
        statusComboBox.setEnabled(false);
        reclassCodeComboBox.setEnabled(false);
        resultComboBox.setEnabled(false);

        if(save) {
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
        openDateTextBox = new com.alee.extended.date.WebDateField();
        closeDateTextBox = new com.alee.extended.date.WebDateField();
        aljComboBox = new javax.swing.JComboBox<>();
        caseNumberTextBox = new javax.swing.JTextField();
        groupNumberTextBox = new javax.swing.JTextField();
        mediatorComboBox = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        groupNumberTextBox1 = new javax.swing.JTextField();
        groupNumberTextBox2 = new javax.swing.JTextField();
        groupNumberTextBox3 = new javax.swing.JTextField();
        groupNumberTextBox4 = new javax.swing.JTextField();
        groupNumberTextBox5 = new javax.swing.JTextField();
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
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel4 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        relatedCaseTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Open/Closed:");

        jLabel2.setText("Case Type:");

        jLabel3.setText("Board Action PC Date:");

        jLabel4.setText("Board Action Pre-D Date:");

        jLabel5.setText("Directive Issued Date:");

        jLabel6.setText("Expedited:");

        openDateTextBox.setEditable(false);
        openDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        openDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        openDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        openDateTextBox.setEnabled(false);
        openDateTextBox.setDateFormat(Global.mmddyyyy);
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

        mediatorComboBox.setEnabled(false);

        jLabel13.setText("Complaint Due Date:");

        jLabel26.setText("Draft Complaint to Hearing:");

        jLabel27.setText("Proposed Rec Due:");

        jLabel28.setText("Prehearing Date:");

        jLabel29.setText("Exceptions Date:");

        groupNumberTextBox1.setBackground(new java.awt.Color(238, 238, 238));
        groupNumberTextBox1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        groupNumberTextBox1.setEnabled(false);
        groupNumberTextBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupNumberTextBox1MouseClicked(evt);
            }
        });

        groupNumberTextBox2.setBackground(new java.awt.Color(238, 238, 238));
        groupNumberTextBox2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        groupNumberTextBox2.setEnabled(false);
        groupNumberTextBox2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupNumberTextBox2MouseClicked(evt);
            }
        });

        groupNumberTextBox3.setBackground(new java.awt.Color(238, 238, 238));
        groupNumberTextBox3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        groupNumberTextBox3.setEnabled(false);
        groupNumberTextBox3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupNumberTextBox3MouseClicked(evt);
            }
        });

        groupNumberTextBox4.setBackground(new java.awt.Color(238, 238, 238));
        groupNumberTextBox4.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        groupNumberTextBox4.setEnabled(false);
        groupNumberTextBox4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupNumberTextBox4MouseClicked(evt);
            }
        });

        groupNumberTextBox5.setBackground(new java.awt.Color(238, 238, 238));
        groupNumberTextBox5.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        groupNumberTextBox5.setEnabled(false);
        groupNumberTextBox5.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                groupNumberTextBox5MouseClicked(evt);
            }
        });

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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(openDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(closeDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(aljComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(caseNumberTextBox)
                    .addComponent(groupNumberTextBox)
                    .addComponent(mediatorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(groupNumberTextBox1)
                    .addComponent(groupNumberTextBox2)
                    .addComponent(groupNumberTextBox3)
                    .addComponent(groupNumberTextBox4)
                    .addComponent(groupNumberTextBox5)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(groupNumberTextBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(groupNumberTextBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(groupNumberTextBox3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(groupNumberTextBox4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel29)
                    .addComponent(groupNumberTextBox5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setText("Board Action Date:");

        jLabel8.setText("Other:");

        jLabel9.setText("ALJ Assigned:");

        pbrBoxTextBox.setBackground(new java.awt.Color(238, 238, 238));
        pbrBoxTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pbrBoxTextBox.setEnabled(false);

        groupTypeComboBox.setEnabled(false);

        statusComboBox.setEnabled(false);

        reclassCodeComboBox.setEnabled(false);

        resultComboBox.setEnabled(false);
        resultComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                resultComboBoxActionPerformed(evt);
            }
        });

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
                    .addComponent(pbrBoxTextBox)
                    .addComponent(groupTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(statusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(reclassCodeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(resultComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pbrBoxTextBox1)
                    .addComponent(pbrBoxTextBox2, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbrBoxTextBox3, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbrBoxTextBox4, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbrBoxTextBox5, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(pbrBoxTextBox6, javax.swing.GroupLayout.Alignment.TRAILING))
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

        jTextArea1.setBackground(new java.awt.Color(238, 238, 238));
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea1.setEnabled(false);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel14)
                        .addContainerGap(44, Short.MAX_VALUE))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                        .addContainerGap())))
        );

        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("Mediation Information");

        relatedCaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "PC / Pre-D", "Mediator", "Date Assigned", "Mediation Date", "Outcome"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        relatedCaseTable.setRequestFocusEnabled(false);
        jScrollPane2.setViewportView(relatedCaseTable);

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
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
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

    private void openDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_openDateTextBoxMouseClicked
        clearDate(openDateTextBox, evt);
    }//GEN-LAST:event_openDateTextBoxMouseClicked

    private void resultComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_resultComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_resultComboBoxActionPerformed

    private void closeDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_closeDateTextBoxMouseClicked
        clearDate(closeDateTextBox, evt);
    }//GEN-LAST:event_closeDateTextBoxMouseClicked

    private void groupNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupNumberTextBoxMouseClicked
        if(groupNumberTextBox.isEnabled() && evt.getClickCount() == 2) {
            new CMDSGroupNumberDialog(Global.root, true, groupNumberTextBox.getText().trim());
            loadInformation();
        }
    }//GEN-LAST:event_groupNumberTextBoxMouseClicked

    private void groupNumberTextBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupNumberTextBox1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_groupNumberTextBox1MouseClicked

    private void groupNumberTextBox2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupNumberTextBox2MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_groupNumberTextBox2MouseClicked

    private void groupNumberTextBox3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupNumberTextBox3MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_groupNumberTextBox3MouseClicked

    private void groupNumberTextBox4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupNumberTextBox4MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_groupNumberTextBox4MouseClicked

    private void groupNumberTextBox5MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_groupNumberTextBox5MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_groupNumberTextBox5MouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> aljComboBox;
    private javax.swing.JTextField caseNumberTextBox;
    private com.alee.extended.date.WebDateField closeDateTextBox;
    private javax.swing.JTextField groupNumberTextBox;
    private javax.swing.JTextField groupNumberTextBox1;
    private javax.swing.JTextField groupNumberTextBox2;
    private javax.swing.JTextField groupNumberTextBox3;
    private javax.swing.JTextField groupNumberTextBox4;
    private javax.swing.JTextField groupNumberTextBox5;
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
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> mediatorComboBox;
    private com.alee.extended.date.WebDateField openDateTextBox;
    private javax.swing.JTextField pbrBoxTextBox;
    private javax.swing.JTextField pbrBoxTextBox1;
    private javax.swing.JTextField pbrBoxTextBox2;
    private javax.swing.JTextField pbrBoxTextBox3;
    private javax.swing.JTextField pbrBoxTextBox4;
    private javax.swing.JTextField pbrBoxTextBox5;
    private javax.swing.JTextField pbrBoxTextBox6;
    private javax.swing.JComboBox<String> reclassCodeComboBox;
    private javax.swing.JTable relatedCaseTable;
    private javax.swing.JComboBox<String> resultComboBox;
    private javax.swing.JComboBox<String> statusComboBox;
    // End of variables declaration//GEN-END:variables
}
