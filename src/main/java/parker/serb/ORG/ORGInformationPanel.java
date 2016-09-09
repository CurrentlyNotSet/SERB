/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ORG;

import com.alee.extended.date.WebDateField;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.employer.employerDetail;
import parker.serb.employer.employerSearch;
import parker.serb.sql.County;
import parker.serb.sql.Employer;
import parker.serb.sql.MEDCase;
import parker.serb.sql.ORGCase;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class ORGInformationPanel extends javax.swing.JPanel {

    ORGCase orginalInformation;
    /**
     * Creates new form ORGInformationPanel
     */
    public ORGInformationPanel() {
        initComponents();
        loadOrgTypeComboBox();
        loadStateComboBox();
        loadFiscalYearEndingComboBox();
//        loadDueDateComboBox();
    }
    
    private void loadOrgTypeComboBox() {
        orgTypeComboBox.removeAllItems();
        orgTypeComboBox.addItem("");
        orgTypeComboBox.addItem("International");
        orgTypeComboBox.addItem("National");
        orgTypeComboBox.addItem("State");
        orgTypeComboBox.addItem("Local");
    }
    
    private void loadStateComboBox() {
        orgStateComboBox.removeAllItems();
        orgStateComboBox.addItem("");
        
        for (String state : Global.states) {
            orgStateComboBox.addItem(state);
        }
    }
    
    private void loadFiscalYearEndingComboBox() {
        fiscalYearEndingComboBox.removeAllItems();
        fiscalYearEndingComboBox.addItem("");
        fiscalYearEndingComboBox.addItem("January");
        fiscalYearEndingComboBox.addItem("Febuary");
        fiscalYearEndingComboBox.addItem("March");
        fiscalYearEndingComboBox.addItem("April");
        fiscalYearEndingComboBox.addItem("May");
        fiscalYearEndingComboBox.addItem("June");
        fiscalYearEndingComboBox.addItem("July");
        fiscalYearEndingComboBox.addItem("August");
        fiscalYearEndingComboBox.addItem("September");
        fiscalYearEndingComboBox.addItem("October");
        fiscalYearEndingComboBox.addItem("November");
        fiscalYearEndingComboBox.addItem("December");
    }
//    
//    private void loadDueDateComboBox() {
//        fiscalYearEndingComboBox.removeAllItems();
//        fiscalYearEndingComboBox.addItem("");
//        fiscalYearEndingComboBox.addItem("January 15th");
//        fiscalYearEndingComboBox.addItem("February 15th");
//        fiscalYearEndingComboBox.addItem("March 15th");
//        fiscalYearEndingComboBox.addItem("April 15th");
//        fiscalYearEndingComboBox.addItem("May 15th");
//        fiscalYearEndingComboBox.addItem("June 15th");
//        fiscalYearEndingComboBox.addItem("July 15th");
//        fiscalYearEndingComboBox.addItem("August 15th");
//        fiscalYearEndingComboBox.addItem("September 15th");
//        fiscalYearEndingComboBox.addItem("October 15th");
//        fiscalYearEndingComboBox.addItem("November 15th");
//        fiscalYearEndingComboBox.addItem("December 15th");
//    }
    
    public void clearAll() {
        orgNameTextBox.setText("");
        alsoKnownAsTextBox.setText("");
        orgNumberTextBox.setText("");
        orgTypeComboBox.setSelectedItem("");
        orgPhone1TextBox.setText("");
        orgPhone2TextBox.setText("");
        orgFaxTextBox.setText("");
        employerIDTextBox.setText("");
        orgAddress1TextBox.setText("");
        orgAddress2TextBox.setText("");
        orgCityTextBox.setText("");
        orgStateComboBox.setSelectedItem("");
        orgZipTextBox.setText("");
        orgCountyComboBox.setSelectedItem("");
        orgEmailTextBox.setText("");
        
        fiscalYearEndingComboBox.setSelectedItem("");
        annualReportLastFiledTextBox.setText("");
        financialStatementLastFiledTextBox.setText("");
        regiestionReportLastFiledTextBox.setText("");
        constrctionAndByLawsFiledTextBox.setText("");
        lastNotificationTextBox.setText("");
        deemedCertifiedCheckBox.setSelected(false);
        boardCertifiedCheckBox.setSelected(false);
        filedByParentCheckBox.setSelected(false);
        validCheckBox.setSelected(false);
        parent1TextBox.setText("");
        parent2TextBox.setText("");
        caseTextBox.setText("");
        dueDateTextBox.setText("");
        dateFiledTextBox.setText("");
//        certifiedDateTextBox.setText("");
        registrationLetterSentTextBox.setText("");
        extensionDateTextBox.setText("");
    }
    
    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);
        
        orgNameTextBox.setEnabled(true);
        orgNameTextBox.setBackground(Color.white);
        alsoKnownAsTextBox.setEnabled(true);
        alsoKnownAsTextBox.setBackground(Color.white);
        orgNumberTextBox.setEnabled(true);
        orgNumberTextBox.setBackground(Color.white);
        orgTypeComboBox.setEnabled(true);
        orgPhone1TextBox.setEnabled(true);
        orgPhone1TextBox.setBackground(Color.white);
        orgPhone2TextBox.setEnabled(true);
        orgPhone2TextBox.setBackground(Color.white);
        orgFaxTextBox.setEnabled(true);
        orgFaxTextBox.setBackground(Color.white);
        employerIDTextBox.setEnabled(true);
        employerIDTextBox.setBackground(Color.white);
        orgAddress1TextBox.setEnabled(true);
        orgAddress1TextBox.setBackground(Color.white);
        orgAddress2TextBox.setEnabled(true);
        orgAddress2TextBox.setBackground(Color.white);
        orgCityTextBox.setEnabled(true);
        orgCityTextBox.setBackground(Color.white);
        orgStateComboBox.setEnabled(true);
        orgZipTextBox.setEnabled(true);
        orgZipTextBox.setBackground(Color.white);
        orgCountyComboBox.setEnabled(true);
        orgEmailTextBox.setEnabled(true);
        orgEmailTextBox.setBackground(Color.white);
        
        fiscalYearEndingComboBox.setEnabled(true);
        annualReportLastFiledTextBox.setEnabled(true);
        annualReportLastFiledTextBox.setBackground(Color.white);
        financialStatementLastFiledTextBox.setEnabled(true);
        financialStatementLastFiledTextBox.setBackground(Color.white);
        regiestionReportLastFiledTextBox.setEnabled(true);
        regiestionReportLastFiledTextBox.setBackground(Color.white);
        constrctionAndByLawsFiledTextBox.setEnabled(true);
        constrctionAndByLawsFiledTextBox.setBackground(Color.white);
        lastNotificationTextBox.setEnabled(true);
        lastNotificationTextBox.setBackground(Color.white);
        deemedCertifiedCheckBox.setEnabled(true);
        boardCertifiedCheckBox.setEnabled(true);
        filedByParentCheckBox.setEnabled(true);
        validCheckBox.setEnabled(true);
        parent1TextBox.setEnabled(true);
        parent1TextBox.setBackground(Color.white);
        parent2TextBox.setEnabled(true);
        parent2TextBox.setBackground(Color.white);
        caseTextBox.setEnabled(true);
        caseTextBox.setBackground(Color.white);
        dateFiledTextBox.setEnabled(true);
        dateFiledTextBox.setBackground(Color.white);
//        certifiedDateTextBox.setEnabled(true);
//        certifiedDateTextBox.setBackground(Color.white);
        registrationLetterSentTextBox.setEnabled(true);
        registrationLetterSentTextBox.setBackground(Color.white);
        extensionDateTextBox.setEnabled(true);
        extensionDateTextBox.setBackground(Color.white);
    }
    
    public void disableUpdate(boolean save) {
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);
        
        orgNameTextBox.setEnabled(false);
        orgNameTextBox.setBackground(new Color(238,238,238));
        alsoKnownAsTextBox.setEnabled(false);
        alsoKnownAsTextBox.setBackground(new Color(238,238,238));
        orgNumberTextBox.setEnabled(false);
        orgNumberTextBox.setBackground(new Color(238,238,238));
        orgTypeComboBox.setEnabled(false);
        orgPhone1TextBox.setEnabled(false);
        orgPhone1TextBox.setBackground(new Color(238,238,238));
        orgPhone2TextBox.setEnabled(false);
        orgPhone2TextBox.setBackground(new Color(238,238,238));
        orgFaxTextBox.setEnabled(false);
        orgFaxTextBox.setBackground(new Color(238,238,238));
        employerIDTextBox.setEnabled(false);
        employerIDTextBox.setBackground(new Color(238,238,238));
        orgAddress1TextBox.setEnabled(false);
        orgAddress1TextBox.setBackground(new Color(238,238,238));
        orgAddress2TextBox.setEnabled(false);
        orgAddress2TextBox.setBackground(new Color(238,238,238));
        orgCityTextBox.setEnabled(false);
        orgCityTextBox.setBackground(new Color(238,238,238));
        orgStateComboBox.setEnabled(false);
        orgZipTextBox.setEnabled(false);
        orgZipTextBox.setBackground(new Color(238,238,238));
        orgCountyComboBox.setEnabled(false);
        orgEmailTextBox.setEnabled(false);
        orgEmailTextBox.setBackground(new Color(238,238,238));
        
        fiscalYearEndingComboBox.setEnabled(false);
        annualReportLastFiledTextBox.setEnabled(false);
        annualReportLastFiledTextBox.setBackground(new Color(238,238,238));
        financialStatementLastFiledTextBox.setEnabled(false);
        financialStatementLastFiledTextBox.setBackground(new Color(238,238,238));
        regiestionReportLastFiledTextBox.setEnabled(false);
        regiestionReportLastFiledTextBox.setBackground(new Color(238,238,238));
        constrctionAndByLawsFiledTextBox.setEnabled(false);
        constrctionAndByLawsFiledTextBox.setBackground(new Color(238,238,238));
        lastNotificationTextBox.setEnabled(false);
        lastNotificationTextBox.setBackground(new Color(238,238,238));
        deemedCertifiedCheckBox.setEnabled(false);
        boardCertifiedCheckBox.setEnabled(false);
        filedByParentCheckBox.setEnabled(false);
        validCheckBox.setEnabled(false);
        parent1TextBox.setEnabled(false);
        parent1TextBox.setBackground(new Color(238,238,238));
        parent2TextBox.setEnabled(false);
        parent2TextBox.setBackground(new Color(238,238,238));
        caseTextBox.setEnabled(false);
        caseTextBox.setBackground(new Color(238,238,238));
        dateFiledTextBox.setEnabled(false);
        dateFiledTextBox.setBackground(new Color(238,238,238));
//        certifiedDateTextBox.setEnabled(false);
//        certifiedDateTextBox.setBackground(new Color(238,238,238));
        registrationLetterSentTextBox.setEnabled(false);
        registrationLetterSentTextBox.setBackground(new Color(238,238,238));
        extensionDateTextBox.setEnabled(false);
        extensionDateTextBox.setBackground(new Color(238,238,238));
        
        if(save) {
            saveInformation();
        }
        
        loadInformation();
    }
    
    private void saveInformation() {
        ORGCase newInformation = new ORGCase();
        
        newInformation.orgName = orgNameTextBox.getText().trim().equals("") ? null : orgNameTextBox.getText().trim();
        newInformation.alsoKnownAs = alsoKnownAsTextBox.getText().trim().equals("") ? null : alsoKnownAsTextBox.getText().trim();
        newInformation.orgNumber = orgNumberTextBox.getText().trim().equals("") ? null : orgNumberTextBox.getText().trim();
        newInformation.orgType = orgTypeComboBox.getSelectedItem().toString().trim().equals("") ? null : orgTypeComboBox.getSelectedItem().toString();
        newInformation.orgPhone1 = orgPhone1TextBox.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(orgPhone1TextBox.getText());
        newInformation.orgPhone2 = orgPhone2TextBox.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(orgPhone2TextBox.getText());
        newInformation.orgFax = orgFaxTextBox.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(orgFaxTextBox.getText());
        newInformation.employerID = employerIDTextBox.getText().trim().equals("") ? null : employerIDTextBox.getText().split("-")[0].trim();
        newInformation.orgAddress1 = orgAddress1TextBox.getText().trim().equals("") ? null : orgAddress1TextBox.getText().trim();
        newInformation.orgAddress2 = orgAddress2TextBox.getText().trim().equals("") ? null : orgAddress2TextBox.getText().trim();
        newInformation.orgCity = orgCityTextBox.getText().trim().equals("") ? null : orgCityTextBox.getText().trim();
        newInformation.orgState = orgStateComboBox.getSelectedItem().toString().trim().equals("") ? null : orgStateComboBox.getSelectedItem().toString().trim();
        newInformation.orgZip = orgZipTextBox.getText().trim().equals("") ? null : orgZipTextBox.getText().trim();
        newInformation.orgCounty = orgCountyComboBox.getSelectedItem().toString().trim().equals("") ? null : orgCountyComboBox.getSelectedItem().toString().trim();
        newInformation.orgEmail = orgEmailTextBox.getText().trim().equals("") ? null : orgEmailTextBox.getText().trim();
        newInformation.fiscalYearEnding = fiscalYearEndingComboBox.getSelectedItem().toString().trim().equals("") ? null : fiscalYearEndingComboBox.getSelectedItem().toString().trim();
        newInformation.annualReport = annualReportLastFiledTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(annualReportLastFiledTextBox.getText()));
        newInformation.financialReport = financialStatementLastFiledTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(financialStatementLastFiledTextBox.getText()));
        newInformation.registrationReport = regiestionReportLastFiledTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(regiestionReportLastFiledTextBox.getText()));
        newInformation.constructionAndByLaws = constrctionAndByLawsFiledTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(constrctionAndByLawsFiledTextBox.getText()));
        newInformation.lastNotification = lastNotificationTextBox.getText().trim().equals("") ? null : lastNotificationTextBox.getText().trim();
        newInformation.deemedCertified = deemedCertifiedCheckBox.isSelected();
        newInformation.boardCertified = boardCertifiedCheckBox.isSelected();
        newInformation.filedByParent = filedByParentCheckBox.isSelected();
        newInformation.valid = validCheckBox.isSelected();
        newInformation.parent1 = parent1TextBox.getText().trim().equals("") ? null : parent1TextBox.getText().trim();
        newInformation.parent2 = parent2TextBox.getText().trim().equals("") ? null : parent2TextBox.getText().trim();
        newInformation.outsideCase = caseTextBox.getText().trim().equals("") ? null : caseTextBox.getText().trim();
        newInformation.filingDueDate = dueDateTextBox.getText().trim().equals("") ? null : dueDateTextBox.getText().trim();
        newInformation.dateFiled = dateFiledTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(dateFiledTextBox.getText()));
//        newInformation.certifiedDate = certifiedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(certifiedDateTextBox.getText()));
        newInformation.registrationLetterSent = registrationLetterSentTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(registrationLetterSentTextBox.getText()));
        newInformation.extensionDate = extensionDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(extensionDateTextBox.getText()));

        ORGCase.updateORGInformation(newInformation, orginalInformation);
        
        if(!newInformation.orgName.equals(orginalInformation.orgName)) {
            Global.root.getoRGHeaderPanel2().loadCases();
            Global.root.getoRGHeaderPanel2().getjComboBox2().setSelectedItem(newInformation.orgName);
        }
    }
    
    public void loadInformation() {
        orginalInformation = ORGCase.loadORGInformation();
        
        orgNameTextBox.setText(orginalInformation.orgName != null ? orginalInformation.orgName : "");
        alsoKnownAsTextBox.setText(orginalInformation.alsoKnownAs != null ? orginalInformation.alsoKnownAs : "");
        orgNumberTextBox.setText(orginalInformation.orgNumber != null ? orginalInformation.orgNumber : "");
        orgTypeComboBox.setSelectedItem(orginalInformation.orgType != null ? orginalInformation.orgType : "");
        orgPhone1TextBox.setText(orginalInformation.orgPhone1 != null ? NumberFormatService.convertStringToPhoneNumber(orginalInformation.orgPhone1) : "");
        orgPhone2TextBox.setText(orginalInformation.orgPhone2 != null ? NumberFormatService.convertStringToPhoneNumber(orginalInformation.orgPhone2) : "");
        orgFaxTextBox.setText(orginalInformation.orgFax != null ? NumberFormatService.convertStringToPhoneNumber(orginalInformation.orgFax) : "");
        employerIDTextBox.setText(orginalInformation.employerID != null ? orginalInformation.employerID + " - " + Employer.getEmployerNameByID(orginalInformation.employerID) : "");
        orgAddress1TextBox.setText(orginalInformation.orgAddress1 != null ? orginalInformation.orgAddress1 : "");
        orgAddress2TextBox.setText(orginalInformation.orgAddress2 != null ? orginalInformation.orgAddress2 : "");
        orgCityTextBox.setText(orginalInformation.orgCity != null ? orginalInformation.orgCity : "");
        orgStateComboBox.setSelectedItem(orginalInformation.orgState != null ? orginalInformation.orgState : "");
        orgZipTextBox.setText(orginalInformation.orgZip != null ? orginalInformation.orgZip : "");
        orgCountyComboBox.setSelectedItem(orginalInformation.orgCounty != null ? orginalInformation.orgCounty : "");
        orgEmailTextBox.setText(orginalInformation.orgEmail != null ? orginalInformation.orgEmail : "");
        
        //right side
        fiscalYearEndingComboBox.setSelectedItem(orginalInformation.fiscalYearEnding != null ? orginalInformation.fiscalYearEnding : "");
        annualReportLastFiledTextBox.setText(orginalInformation.annualReport != null ? Global.mmddyyyy.format(new Date(orginalInformation.annualReport.getTime())) : "");
        financialStatementLastFiledTextBox.setText(orginalInformation.financialReport != null ? Global.mmddyyyy.format(new Date(orginalInformation.financialReport.getTime())) : "");
        regiestionReportLastFiledTextBox.setText(orginalInformation.registrationReport != null ? Global.mmddyyyy.format(new Date(orginalInformation.registrationReport.getTime())) : "");
        constrctionAndByLawsFiledTextBox.setText(orginalInformation.constructionAndByLaws != null ? Global.mmddyyyy.format(new Date(orginalInformation.constructionAndByLaws.getTime())) : "");
        lastNotificationTextBox.setText(orginalInformation.lastNotification != null ? orginalInformation.lastNotification : "");
        deemedCertifiedCheckBox.setSelected(orginalInformation.deemedCertified == true);
        boardCertifiedCheckBox.setSelected(orginalInformation.boardCertified == true);
        filedByParentCheckBox.setSelected(orginalInformation.filedByParent == true);
        validCheckBox.setSelected(orginalInformation.valid == true);
        parent1TextBox.setText(orginalInformation.parent1 != null ? orginalInformation.parent1 : "");
        parent2TextBox.setText(orginalInformation.parent2 != null ? orginalInformation.parent2 : "");
        caseTextBox.setText(orginalInformation.outsideCase != null ? orginalInformation.outsideCase : "");      
        dueDateTextBox.setText(orginalInformation.filingDueDate != null ? orginalInformation.filingDueDate : "");
        dateFiledTextBox.setText(orginalInformation.dateFiled != null ? Global.mmddyyyy.format(new Date(orginalInformation.dateFiled.getTime())) : "");
//        certifiedDateTextBox.setText(orginalInformation.certifiedDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.certifiedDate.getTime())) : "");
        registrationLetterSentTextBox.setText(orginalInformation.registrationLetterSent != null ? Global.mmddyyyy.format(new Date(orginalInformation.registrationLetterSent.getTime())) : "");
        extensionDateTextBox.setText(orginalInformation.extensionDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.extensionDate.getTime())) : "");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        orgNameTextBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        orgNumberTextBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        orgTypeComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        orgPhone1TextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        orgPhone2TextBox = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        orgFaxTextBox = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        employerIDTextBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        orgAddress1TextBox = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        orgAddress2TextBox = new javax.swing.JTextField();
        orgCityTextBox = new javax.swing.JTextField();
        orgStateComboBox = new javax.swing.JComboBox<>();
        orgZipTextBox = new javax.swing.JTextField();
        orgEmailTextBox = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        alsoKnownAsTextBox = new javax.swing.JTextField();
        orgCountyComboBox = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        fiscalYearEndingComboBox = new javax.swing.JComboBox<>();
        annualReportLastFiledTextBox = new com.alee.extended.date.WebDateField();
        financialStatementLastFiledTextBox = new com.alee.extended.date.WebDateField();
        regiestionReportLastFiledTextBox = new com.alee.extended.date.WebDateField();
        constrctionAndByLawsFiledTextBox = new com.alee.extended.date.WebDateField();
        parent1TextBox = new javax.swing.JTextField();
        parent2TextBox = new javax.swing.JTextField();
        caseTextBox = new javax.swing.JTextField();
        dateFiledTextBox = new com.alee.extended.date.WebDateField();
        registrationLetterSentTextBox = new com.alee.extended.date.WebDateField();
        extensionDateTextBox = new com.alee.extended.date.WebDateField();
        deemedCertifiedCheckBox = new javax.swing.JCheckBox();
        boardCertifiedCheckBox = new javax.swing.JCheckBox();
        filedByParentCheckBox = new javax.swing.JCheckBox();
        validCheckBox = new javax.swing.JCheckBox();
        lastNotificationTextBox = new javax.swing.JTextField();
        dueDateTextBox = new javax.swing.JTextField();

        jLabel1.setText("Org Name:");

        orgNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgNameTextBox.setEnabled(false);
        orgNameTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                orgNameTextBoxKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                orgNameTextBoxKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                orgNameTextBoxKeyReleased(evt);
            }
        });

        jLabel2.setText("Org Number:");

        orgNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgNumberTextBox.setEnabled(false);

        jLabel3.setText("Org Type:");

        orgTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        orgTypeComboBox.setEnabled(false);

        jLabel4.setText("Org Phone 1:");

        orgPhone1TextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgPhone1TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgPhone1TextBox.setEnabled(false);

        jLabel5.setText("Org Phone 2:");

        orgPhone2TextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgPhone2TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgPhone2TextBox.setEnabled(false);

        jLabel6.setText("Org Fax:");

        orgFaxTextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgFaxTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgFaxTextBox.setEnabled(false);

        jLabel7.setText("Employer ID:");

        employerIDTextBox.setBackground(new java.awt.Color(238, 238, 238));
        employerIDTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        employerIDTextBox.setEnabled(false);
        employerIDTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employerIDTextBoxMouseClicked(evt);
            }
        });

        jLabel8.setText("Org Address 1:");

        orgAddress1TextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgAddress1TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgAddress1TextBox.setEnabled(false);

        jLabel9.setText("Org Address 2:");

        jLabel10.setText("Org City:");

        jLabel11.setText("Org State:");

        jLabel12.setText("Org Zip:");

        jLabel13.setText("Org County:");

        jLabel14.setText("Org Email:");

        orgAddress2TextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgAddress2TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgAddress2TextBox.setEnabled(false);

        orgCityTextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgCityTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgCityTextBox.setEnabled(false);

        orgStateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        orgStateComboBox.setEnabled(false);
        orgStateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orgStateComboBoxActionPerformed(evt);
            }
        });

        orgZipTextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgZipTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgZipTextBox.setEnabled(false);

        orgEmailTextBox.setBackground(new java.awt.Color(238, 238, 238));
        orgEmailTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        orgEmailTextBox.setEnabled(false);

        jLabel15.setText("Also Known As:");

        alsoKnownAsTextBox.setBackground(new java.awt.Color(238, 238, 238));
        alsoKnownAsTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        alsoKnownAsTextBox.setEnabled(false);

        orgCountyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        orgCountyComboBox.setEnabled(false);
        orgCountyComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                orgCountyComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(orgNameTextBox)
                    .addComponent(orgNumberTextBox)
                    .addComponent(orgTypeComboBox, 0, 396, Short.MAX_VALUE)
                    .addComponent(orgPhone1TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(orgPhone2TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(orgFaxTextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(employerIDTextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(orgAddress1TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(orgAddress2TextBox)
                    .addComponent(orgCityTextBox)
                    .addComponent(orgStateComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(orgZipTextBox)
                    .addComponent(orgEmailTextBox)
                    .addComponent(alsoKnownAsTextBox)
                    .addComponent(orgCountyComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(orgNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(alsoKnownAsTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(orgNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(orgTypeComboBox)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(orgPhone1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(orgPhone2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(orgFaxTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(employerIDTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(orgAddress1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(orgAddress2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(orgCityTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(orgStateComboBox)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(orgZipTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(orgCountyComboBox)
                    .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(orgEmailTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );

        jLabel16.setText("Fiscal Year Ending:");

        jLabel17.setText("Annual Report LF:");

        jLabel18.setText("Financial Statement LF:");

        jLabel19.setText("Registration Report LF:");

        jLabel20.setText("Constitution and By Laws Filed:");

        jLabel21.setText("Last Notification:");

        jLabel24.setText("Parent 1:");

        jLabel25.setText("Parent 2:");

        jLabel26.setText("Case:");

        jLabel27.setText("Due Date:");

        jLabel28.setText("Date Filed:");

        jLabel30.setText("Registration Letter Sent:");

        jLabel31.setText("Extension Date:");

        fiscalYearEndingComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        fiscalYearEndingComboBox.setEnabled(false);
        fiscalYearEndingComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fiscalYearEndingComboBoxActionPerformed(evt);
            }
        });

        annualReportLastFiledTextBox.setEditable(false);
        annualReportLastFiledTextBox.setBackground(new java.awt.Color(238, 238, 238));
        annualReportLastFiledTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        annualReportLastFiledTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        annualReportLastFiledTextBox.setEnabled(false);
        annualReportLastFiledTextBox.setDateFormat(Global.mmddyyyy);
        annualReportLastFiledTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                annualReportLastFiledTextBoxMouseClicked(evt);
            }
        });
        annualReportLastFiledTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                annualReportLastFiledTextBoxActionPerformed(evt);
            }
        });

        financialStatementLastFiledTextBox.setEditable(false);
        financialStatementLastFiledTextBox.setBackground(new java.awt.Color(238, 238, 238));
        financialStatementLastFiledTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        financialStatementLastFiledTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        financialStatementLastFiledTextBox.setEnabled(false);
        financialStatementLastFiledTextBox.setDateFormat(Global.mmddyyyy);
        financialStatementLastFiledTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                financialStatementLastFiledTextBoxMouseClicked(evt);
            }
        });

        regiestionReportLastFiledTextBox.setEditable(false);
        regiestionReportLastFiledTextBox.setBackground(new java.awt.Color(238, 238, 238));
        regiestionReportLastFiledTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        regiestionReportLastFiledTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        regiestionReportLastFiledTextBox.setEnabled(false);
        regiestionReportLastFiledTextBox.setDateFormat(Global.mmddyyyy);
        regiestionReportLastFiledTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                regiestionReportLastFiledTextBoxMouseClicked(evt);
            }
        });

        constrctionAndByLawsFiledTextBox.setEditable(false);
        constrctionAndByLawsFiledTextBox.setBackground(new java.awt.Color(238, 238, 238));
        constrctionAndByLawsFiledTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        constrctionAndByLawsFiledTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        constrctionAndByLawsFiledTextBox.setEnabled(false);
        constrctionAndByLawsFiledTextBox.setDateFormat(Global.mmddyyyy);
        constrctionAndByLawsFiledTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                constrctionAndByLawsFiledTextBoxMouseClicked(evt);
            }
        });

        parent1TextBox.setBackground(new java.awt.Color(238, 238, 238));
        parent1TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        parent1TextBox.setEnabled(false);

        parent2TextBox.setBackground(new java.awt.Color(238, 238, 238));
        parent2TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        parent2TextBox.setEnabled(false);

        caseTextBox.setBackground(new java.awt.Color(238, 238, 238));
        caseTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        caseTextBox.setEnabled(false);

        dateFiledTextBox.setEditable(false);
        dateFiledTextBox.setBackground(new java.awt.Color(238, 238, 238));
        dateFiledTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        dateFiledTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dateFiledTextBox.setEnabled(false);
        dateFiledTextBox.setDateFormat(Global.mmddyyyy);
        dateFiledTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                dateFiledTextBoxMouseClicked(evt);
            }
        });

        registrationLetterSentTextBox.setEditable(false);
        registrationLetterSentTextBox.setBackground(new java.awt.Color(238, 238, 238));
        registrationLetterSentTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        registrationLetterSentTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        registrationLetterSentTextBox.setEnabled(false);
        registrationLetterSentTextBox.setDateFormat(Global.mmddyyyy);
        registrationLetterSentTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                registrationLetterSentTextBoxMouseClicked(evt);
            }
        });

        extensionDateTextBox.setEditable(false);
        extensionDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        extensionDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        extensionDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        extensionDateTextBox.setEnabled(false);
        extensionDateTextBox.setDateFormat(Global.mmddyyyy);
        extensionDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                extensionDateTextBoxMouseClicked(evt);
            }
        });

        deemedCertifiedCheckBox.setText("Deemed Certified");
        deemedCertifiedCheckBox.setEnabled(false);
        deemedCertifiedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deemedCertifiedCheckBoxActionPerformed(evt);
            }
        });

        boardCertifiedCheckBox.setText("Board Certified");
        boardCertifiedCheckBox.setEnabled(false);
        boardCertifiedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardCertifiedCheckBoxActionPerformed(evt);
            }
        });

        filedByParentCheckBox.setText("Filed By Parent");
        filedByParentCheckBox.setEnabled(false);
        filedByParentCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                filedByParentCheckBoxActionPerformed(evt);
            }
        });

        validCheckBox.setText("Valid");
        validCheckBox.setEnabled(false);
        validCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                validCheckBoxActionPerformed(evt);
            }
        });

        lastNotificationTextBox.setBackground(new java.awt.Color(238, 238, 238));
        lastNotificationTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lastNotificationTextBox.setEnabled(false);

        dueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        dueDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dueDateTextBox.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30)
                    .addComponent(jLabel28)
                    .addComponent(jLabel27)
                    .addComponent(jLabel26)
                    .addComponent(jLabel25)
                    .addComponent(jLabel24)
                    .addComponent(jLabel21)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(annualReportLastFiledTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(financialStatementLastFiledTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(regiestionReportLastFiledTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(constrctionAndByLawsFiledTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(parent1TextBox)
                    .addComponent(parent2TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(caseTextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(registrationLetterSentTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(extensionDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lastNotificationTextBox)
                    .addComponent(fiscalYearEndingComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(dueDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(dateFiledTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(deemedCertifiedCheckBox)
                            .addComponent(filedByParentCheckBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(validCheckBox)
                            .addComponent(boardCertifiedCheckBox))
                        .addGap(0, 23, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(fiscalYearEndingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(dueDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel28)
                            .addComponent(dateFiledTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(annualReportLastFiledTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel17))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(financialStatementLastFiledTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel18))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(regiestionReportLastFiledTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel19))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel20)
                            .addComponent(constrctionAndByLawsFiledTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel31)
                            .addComponent(extensionDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel21)
                            .addComponent(lastNotificationTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(deemedCertifiedCheckBox)
                        .addComponent(boardCertifiedCheckBox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(validCheckBox)
                        .addComponent(filedByParentCheckBox))
                    .addComponent(jLabel23, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(parent1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(parent2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel25))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(caseTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel30)
                    .addComponent(registrationLetterSentTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void annualReportLastFiledTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_annualReportLastFiledTextBoxMouseClicked
        clearDate(annualReportLastFiledTextBox, evt);
    }//GEN-LAST:event_annualReportLastFiledTextBoxMouseClicked

    private void financialStatementLastFiledTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_financialStatementLastFiledTextBoxMouseClicked
        clearDate(financialStatementLastFiledTextBox, evt);
    }//GEN-LAST:event_financialStatementLastFiledTextBoxMouseClicked

    private void regiestionReportLastFiledTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_regiestionReportLastFiledTextBoxMouseClicked
        clearDate(regiestionReportLastFiledTextBox, evt);
    }//GEN-LAST:event_regiestionReportLastFiledTextBoxMouseClicked

    private void constrctionAndByLawsFiledTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_constrctionAndByLawsFiledTextBoxMouseClicked
        clearDate(constrctionAndByLawsFiledTextBox, evt);
    }//GEN-LAST:event_constrctionAndByLawsFiledTextBoxMouseClicked

    private void dateFiledTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateFiledTextBoxMouseClicked
        clearDate(dateFiledTextBox, evt);
    }//GEN-LAST:event_dateFiledTextBoxMouseClicked

    private void registrationLetterSentTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registrationLetterSentTextBoxMouseClicked
        clearDate(registrationLetterSentTextBox, evt);
    }//GEN-LAST:event_registrationLetterSentTextBoxMouseClicked

    private void extensionDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_extensionDateTextBoxMouseClicked
        clearDate(extensionDateTextBox, evt);
    }//GEN-LAST:event_extensionDateTextBoxMouseClicked

    private void deemedCertifiedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deemedCertifiedCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deemedCertifiedCheckBoxActionPerformed

    private void boardCertifiedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardCertifiedCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boardCertifiedCheckBoxActionPerformed

    private void filedByParentCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_filedByParentCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_filedByParentCheckBoxActionPerformed

    private void validCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_validCheckBoxActionPerformed

    private void orgStateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orgStateComboBoxActionPerformed
        if(orgStateComboBox.getSelectedItem() != null) {
            if(orgStateComboBox.getSelectedItem().toString().equals("")) {
                orgCountyComboBox.removeAllItems();
                orgCountyComboBox.addItem("");
                orgCountyComboBox.setSelectedItem("");
            } else {
                orgCountyComboBox.removeAllItems();
                orgCountyComboBox.addItem("");
                List counties = County.loadCountyListByState(orgStateComboBox.getSelectedItem().toString());
                for(int i = 0; i < counties.size(); i++) {
                    County county = (County) counties.get(i);
                    orgCountyComboBox.addItem(county.countyName);
                }
            }
        }
    }//GEN-LAST:event_orgStateComboBoxActionPerformed

    private void orgCountyComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_orgCountyComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_orgCountyComboBoxActionPerformed

    private void employerIDTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employerIDTextBoxMouseClicked
        if(evt.getClickCount() == 2) {
            employerSearch search = new employerSearch((JFrame) Global.root.getRootPane().getParent(), true, employerIDTextBox.getText().trim());
            employerIDTextBox.setText(search.getEmployerNumber() + " - " + Employer.getEmployerNameByID(search.getEmployerNumber()));
            search.dispose();
            if(employerIDTextBox.getText().equals("")) {
                new employerDetail((JFrame) Global.root.getRootPane().getParent(), true, employerIDTextBox.getText().split("-")[0].trim());
            }
        }
    }//GEN-LAST:event_employerIDTextBoxMouseClicked

    private void orgNameTextBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orgNameTextBoxKeyPressed
        if(orgNameTextBox.getText().trim().equals("")) {
            Global.root.getjButton2().setEnabled(false);
        } else {
            Global.root.getjButton2().setEnabled(true);
        }
    }//GEN-LAST:event_orgNameTextBoxKeyPressed

    private void orgNameTextBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orgNameTextBoxKeyTyped
        if(orgNameTextBox.getText().trim().equals("")) {
            Global.root.getjButton2().setEnabled(false);
        } else {
            Global.root.getjButton2().setEnabled(true);
        }
    }//GEN-LAST:event_orgNameTextBoxKeyTyped

    private void orgNameTextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_orgNameTextBoxKeyReleased
        if(orgNameTextBox.getText().trim().equals("")) {
            Global.root.getjButton2().setEnabled(false);
        } else {
            Global.root.getjButton2().setEnabled(true);
        }
    }//GEN-LAST:event_orgNameTextBoxKeyReleased

    private void fiscalYearEndingComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fiscalYearEndingComboBoxActionPerformed
        if(fiscalYearEndingComboBox.getSelectedItem() != null) {
            switch(fiscalYearEndingComboBox.getSelectedItem().toString()) {
                case "January":
                    dueDateTextBox.setText("June 15th");
                    break;
                case "Febrary":
                    dueDateTextBox.setText("July 15th");
                    break;
                case "March":
                    dueDateTextBox.setText("August 15th");
                    break; 
                case "April":
                    dueDateTextBox.setText("September 15th");
                    break;
                case "May":
                    dueDateTextBox.setText("October 15th");
                    break;
                case "June":
                    dueDateTextBox.setText("November 15th");
                    break;
                case "July":
                    dueDateTextBox.setText("December 15th");
                    break;
                case "August":
                    dueDateTextBox.setText("January 15th");
                    break;
                case "September":
                    dueDateTextBox.setText("Febuary 15th");
                    break;
                case "October":
                    dueDateTextBox.setText("March 15th");
                    break; 
                case "November":
                    dueDateTextBox.setText("April 15th");
                    break;
                case "December":
                    dueDateTextBox.setText("May 15th");
                    break;
                default:
                    dueDateTextBox.setText("");
                    break;
            }
        } else {
            dueDateTextBox.setText("");
        }
    }//GEN-LAST:event_fiscalYearEndingComboBoxActionPerformed

    private void annualReportLastFiledTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_annualReportLastFiledTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_annualReportLastFiledTextBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField alsoKnownAsTextBox;
    private com.alee.extended.date.WebDateField annualReportLastFiledTextBox;
    private javax.swing.JCheckBox boardCertifiedCheckBox;
    private javax.swing.JTextField caseTextBox;
    private com.alee.extended.date.WebDateField constrctionAndByLawsFiledTextBox;
    private com.alee.extended.date.WebDateField dateFiledTextBox;
    private javax.swing.JCheckBox deemedCertifiedCheckBox;
    private javax.swing.JTextField dueDateTextBox;
    private javax.swing.JTextField employerIDTextBox;
    private com.alee.extended.date.WebDateField extensionDateTextBox;
    private javax.swing.JCheckBox filedByParentCheckBox;
    private com.alee.extended.date.WebDateField financialStatementLastFiledTextBox;
    private javax.swing.JComboBox<String> fiscalYearEndingComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
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
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField lastNotificationTextBox;
    private javax.swing.JTextField orgAddress1TextBox;
    private javax.swing.JTextField orgAddress2TextBox;
    private javax.swing.JTextField orgCityTextBox;
    private javax.swing.JComboBox<String> orgCountyComboBox;
    private javax.swing.JTextField orgEmailTextBox;
    private javax.swing.JTextField orgFaxTextBox;
    private javax.swing.JTextField orgNameTextBox;
    private javax.swing.JTextField orgNumberTextBox;
    private javax.swing.JTextField orgPhone1TextBox;
    private javax.swing.JTextField orgPhone2TextBox;
    private javax.swing.JComboBox<String> orgStateComboBox;
    private javax.swing.JComboBox<String> orgTypeComboBox;
    private javax.swing.JTextField orgZipTextBox;
    private javax.swing.JTextField parent1TextBox;
    private javax.swing.JTextField parent2TextBox;
    private com.alee.extended.date.WebDateField regiestionReportLastFiledTextBox;
    private com.alee.extended.date.WebDateField registrationLetterSentTextBox;
    private javax.swing.JCheckBox validCheckBox;
    // End of variables declaration//GEN-END:variables
}
