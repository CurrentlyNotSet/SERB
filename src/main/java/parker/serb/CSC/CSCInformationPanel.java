/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CSC;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.CSCCase;
import parker.serb.sql.County;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.EmailValidation;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class CSCInformationPanel extends javax.swing.JPanel {

    CSCCase orginalInformation;
    /**
     * Creates new form ORGInformationPanel
     */
    public CSCInformationPanel() {
        initComponents();
        addListeners();
        loadOrgTypeComboBox();
        loadStateComboBox();
        loadFiscalYearEndingComboBox();
        loadDueDateComboBox();
    }
    
    private void addListeners() {
        cscEmailTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Global.root.getjButton2().setEnabled(EmailValidation.validEmail(cscEmailTextBox.getText().trim()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                Global.root.getjButton2().setEnabled(EmailValidation.validEmail(cscEmailTextBox.getText().trim()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                Global.root.getjButton2().setEnabled(EmailValidation.validEmail(cscEmailTextBox.getText().trim()));
            }
        });
    }
    
    private void loadOrgTypeComboBox() {
        cscTypeComboBox.removeAllItems();
        cscTypeComboBox.addItem("");
        cscTypeComboBox.addItem("Municipal");
        cscTypeComboBox.addItem("Township");
    }
    
    private void loadStateComboBox() {
        cscStateComboBox.removeAllItems();
        cscStateComboBox.addItem("");
        
        for (String state : Global.STATES) {
            cscStateComboBox.addItem(state);
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
    
    private void loadDueDateComboBox() {
        dueDateComboBox.removeAllItems();
        dueDateComboBox.addItem("");
        dueDateComboBox.addItem("January");
        dueDateComboBox.addItem("Febuary");
        dueDateComboBox.addItem("March");
        dueDateComboBox.addItem("April");
        dueDateComboBox.addItem("May");
        dueDateComboBox.addItem("June");
        dueDateComboBox.addItem("July");
        dueDateComboBox.addItem("August");
        dueDateComboBox.addItem("September");
        dueDateComboBox.addItem("October");
        dueDateComboBox.addItem("November");
        dueDateComboBox.addItem("December");
    }
    
    public void clearAll() {
        cscNameTextBox.setText("");
        alsoKnownAsTextBox.setText("");
        cscNumberTextBox.setText("");
        cscTypeComboBox.setSelectedItem("");
        cscPhone1TextBox.setText("");
        cscPhone2TextBox.setText("");
        cscFaxTextBox.setText("");
        cscAddress1TextBox.setText("");
        cscAddress2TextBox.setText("");
        cscCityTextBox.setText("");
        cscStateComboBox.setSelectedItem("");
        cscZipTextBox.setText("");
        cscCountyComboBox.setSelectedItem("");
        cscEmailTextBox.setText("");
        
        fiscalYearEndingComboBox.setSelectedItem("");
        activitiesLastFiledTextBox.setText("");
        lastNotificationTextBox.setText("");
        statutoryCheckBox.setSelected(false);
        validCheckBox.setSelected(false);
        dueDateComboBox.setSelectedItem("");
        dateFiledTextBox.setText("");
        previousFileDateTextBox.setText("");
        charterCheckBox.setSelected(false);
    }
    
    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);
        
        cscNameTextBox.setEnabled(true);
        cscNameTextBox.setBackground(Color.white);
        alsoKnownAsTextBox.setEnabled(true);
        alsoKnownAsTextBox.setBackground(Color.white);
        cscNumberTextBox.setEnabled(true);
        cscNumberTextBox.setBackground(Color.white);
        cscTypeComboBox.setEnabled(true);
        cscPhone1TextBox.setEnabled(true);
        cscPhone1TextBox.setBackground(Color.white);
        cscPhone2TextBox.setEnabled(true);
        cscPhone2TextBox.setBackground(Color.white);
        cscFaxTextBox.setEnabled(true);
        cscFaxTextBox.setBackground(Color.white);
        cscAddress1TextBox.setEnabled(true);
        cscAddress1TextBox.setBackground(Color.white);
        cscAddress2TextBox.setEnabled(true);
        cscAddress2TextBox.setBackground(Color.white);
        cscCityTextBox.setEnabled(true);
        cscCityTextBox.setBackground(Color.white);
        cscStateComboBox.setEnabled(true);
        cscZipTextBox.setEnabled(true);
        cscZipTextBox.setBackground(Color.white);
        cscCountyComboBox.setEnabled(true);
        cscEmailTextBox.setEnabled(true);
        cscEmailTextBox.setBackground(Color.white);
        
        fiscalYearEndingComboBox.setEnabled(true);
        dueDateComboBox.setEnabled(true);
        activitiesLastFiledTextBox.setEnabled(true);
        activitiesLastFiledTextBox.setBackground(Color.white);
        lastNotificationTextBox.setEnabled(true);
        lastNotificationTextBox.setBackground(Color.white);
        statutoryCheckBox.setEnabled(true);
        validCheckBox.setEnabled(true);
        dateFiledTextBox.setEnabled(true);
        dateFiledTextBox.setBackground(Color.white);
        previousFileDateTextBox.setBackground(Color.white);
        previousFileDateTextBox.setEnabled(true);
        charterCheckBox.setEnabled(true);
    }
    
    public void disableUpdate(boolean save) {
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);
        
        cscNameTextBox.setEnabled(false);
        cscNameTextBox.setBackground(new Color(238,238,238));
        alsoKnownAsTextBox.setEnabled(false);
        alsoKnownAsTextBox.setBackground(new Color(238,238,238));
        cscNumberTextBox.setEnabled(false);
        cscNumberTextBox.setBackground(new Color(238,238,238));
        cscTypeComboBox.setEnabled(false);
        cscPhone1TextBox.setEnabled(false);
        cscPhone1TextBox.setBackground(new Color(238,238,238));
        cscPhone2TextBox.setEnabled(false);
        cscPhone2TextBox.setBackground(new Color(238,238,238));
        cscFaxTextBox.setEnabled(false);
        cscFaxTextBox.setBackground(new Color(238,238,238));
        cscAddress1TextBox.setEnabled(false);
        cscAddress1TextBox.setBackground(new Color(238,238,238));
        cscAddress2TextBox.setEnabled(false);
        cscAddress2TextBox.setBackground(new Color(238,238,238));
        cscCityTextBox.setEnabled(false);
        cscCityTextBox.setBackground(new Color(238,238,238));
        cscStateComboBox.setEnabled(false);
        cscZipTextBox.setEnabled(false);
        cscZipTextBox.setBackground(new Color(238,238,238));
        cscCountyComboBox.setEnabled(false);
        cscEmailTextBox.setEnabled(false);
        cscEmailTextBox.setBackground(new Color(238,238,238));
        
        fiscalYearEndingComboBox.setEnabled(false);
        dueDateComboBox.setEnabled(false);
        activitiesLastFiledTextBox.setEnabled(false);
        activitiesLastFiledTextBox.setBackground(new Color(238,238,238));
        lastNotificationTextBox.setEnabled(false);
        lastNotificationTextBox.setBackground(new Color(238,238,238));
        statutoryCheckBox.setEnabled(false);
        validCheckBox.setEnabled(false);
        dateFiledTextBox.setEnabled(false);
        dateFiledTextBox.setBackground(new Color(238,238,238));
        previousFileDateTextBox.setBackground(new Color(238,238,238));
        previousFileDateTextBox.setEnabled(false);
        charterCheckBox.setEnabled(false);

        if(save) {
            saveInformation();
        }
        
        loadInformation();
    }
    
    private void saveInformation() {
        CSCCase newInformation = new CSCCase();
        
        newInformation.name = cscNameTextBox.getText().trim().equals("") ? null : cscNameTextBox.getText().trim();
        newInformation.alsoKnownAs = alsoKnownAsTextBox.getText().trim().equals("") ? null : alsoKnownAsTextBox.getText().trim();
        newInformation.type = cscTypeComboBox.getSelectedItem().toString().trim().equals("") ? null : cscTypeComboBox.getSelectedItem().toString();
        newInformation.cscNumber = cscNumberTextBox.getText().trim().equals("") ? null : cscNumberTextBox.getText().trim();
        newInformation.address1 = cscAddress1TextBox.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(cscAddress1TextBox.getText());
        newInformation.address2 = cscAddress2TextBox.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(cscAddress2TextBox.getText());
        newInformation.city = cscCityTextBox.getText().trim().equals("") ? null : cscCityTextBox.getText().trim();
        newInformation.state = cscStateComboBox.getSelectedItem().toString().trim().equals("") ? null : cscStateComboBox.getSelectedItem().toString();
        newInformation.zipCode = cscZipTextBox.getText().trim().equals("") ? null : cscZipTextBox.getText().trim();
        newInformation.phone1 = cscPhone1TextBox.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(cscPhone1TextBox.getText());
        newInformation.phone2 = cscPhone2TextBox.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(cscPhone2TextBox.getText());
        newInformation.fax = cscFaxTextBox.getText().trim().equals("") ? null : NumberFormatService.convertPhoneNumberToString(cscFaxTextBox.getText());
        newInformation.email = cscEmailTextBox.getText().trim().equals("") ? null : cscEmailTextBox.getText().trim();
        newInformation.statutory = statutoryCheckBox.isSelected();
        newInformation.charter = charterCheckBox.isSelected();
        newInformation.fiscalYearEnding = fiscalYearEndingComboBox.getSelectedItem().toString().trim().equals("") ? null : fiscalYearEndingComboBox.getSelectedItem().toString();
        newInformation.lastNotification = lastNotificationTextBox.getText().equals("") ? null : lastNotificationTextBox.getText();
        newInformation.activityLastFiled = activitiesLastFiledTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(activitiesLastFiledTextBox.getText()));
        newInformation.previousFileDate = previousFileDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(previousFileDateTextBox.getText()));
        newInformation.dueDate = dueDateComboBox.getSelectedItem().toString().trim().equals("") ? null : dueDateComboBox.getSelectedItem().toString();
        newInformation.filed = dateFiledTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(dateFiledTextBox.getText()));
        newInformation.valid = validCheckBox.isSelected();
        newInformation.county = cscCountyComboBox.getSelectedItem().toString().trim().equals("") ? null : cscCountyComboBox.getSelectedItem().toString();
        CSCCase.updateCSCInformation(newInformation, orginalInformation);
        
        if(!newInformation.name.equals(orginalInformation.name)) {
            Global.root.getcSCHeaderPanel1().loadCases();
            Global.root.getcSCHeaderPanel1().getjComboBox2().setSelectedItem(newInformation.name);
        }
    }
    
    public void loadInformation() {
        orginalInformation = CSCCase.loadCSCInformation();
        
        cscNameTextBox.setText(orginalInformation.name != null ? orginalInformation.name : "");
        alsoKnownAsTextBox.setText(orginalInformation.alsoKnownAs != null ? orginalInformation.alsoKnownAs : "");
        cscNumberTextBox.setText(orginalInformation.cscNumber != null ? orginalInformation.cscNumber : "");
        cscTypeComboBox.setSelectedItem(orginalInformation.type != null ? orginalInformation.type : "");
        cscPhone1TextBox.setText(orginalInformation.phone1 != null ? NumberFormatService.convertStringToPhoneNumber(orginalInformation.phone1) : "");
        cscPhone2TextBox.setText(orginalInformation.phone2 != null ? NumberFormatService.convertStringToPhoneNumber(orginalInformation.phone2) : "");
        cscFaxTextBox.setText(orginalInformation.fax != null ? NumberFormatService.convertStringToPhoneNumber(orginalInformation.fax) : "");
        cscAddress1TextBox.setText(orginalInformation.address1 != null ? orginalInformation.address1 : "");
        cscAddress2TextBox.setText(orginalInformation.address2 != null ? orginalInformation.address2 : "");
        cscCityTextBox.setText(orginalInformation.city != null ? orginalInformation.city : "");
        cscStateComboBox.setSelectedItem(orginalInformation.state != null ? orginalInformation.state : "");
        cscZipTextBox.setText(orginalInformation.zipCode != null ? orginalInformation.zipCode : "");
        cscCountyComboBox.setSelectedItem(orginalInformation.county != null ? orginalInformation.county : "");
        cscEmailTextBox.setText(orginalInformation.email != null ? orginalInformation.email : "");
        
        //right side
        fiscalYearEndingComboBox.setSelectedItem(orginalInformation.fiscalYearEnding != null ? orginalInformation.fiscalYearEnding : "");
        dueDateComboBox.setSelectedItem(orginalInformation.dueDate != null ? orginalInformation.dueDate : "");
        dateFiledTextBox.setText(orginalInformation.filed != null ? Global.mmddyyyy.format(new Date(orginalInformation.filed.getTime())) : "");
        activitiesLastFiledTextBox.setText(orginalInformation.activityLastFiled != null ? Global.mmddyyyy.format(new Date(orginalInformation.activityLastFiled.getTime())) : "");
        previousFileDateTextBox.setText(orginalInformation.previousFileDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.previousFileDate.getTime())) : "");
        charterCheckBox.setSelected(orginalInformation.charter == true);
        lastNotificationTextBox.setText(orginalInformation.lastNotification != null ? orginalInformation.lastNotification : "");
        statutoryCheckBox.setSelected(orginalInformation.statutory == true);
        validCheckBox.setSelected(orginalInformation.valid == true);
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
        cscNameTextBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        cscNumberTextBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        cscTypeComboBox = new javax.swing.JComboBox<>();
        jLabel4 = new javax.swing.JLabel();
        cscPhone1TextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        cscPhone2TextBox = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        cscFaxTextBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        cscAddress1TextBox = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        cscAddress2TextBox = new javax.swing.JTextField();
        cscCityTextBox = new javax.swing.JTextField();
        cscStateComboBox = new javax.swing.JComboBox<>();
        cscZipTextBox = new javax.swing.JTextField();
        cscEmailTextBox = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        alsoKnownAsTextBox = new javax.swing.JTextField();
        cscCountyComboBox = new javax.swing.JComboBox<>();
        jPanel2 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        fiscalYearEndingComboBox = new javax.swing.JComboBox<>();
        activitiesLastFiledTextBox = new com.alee.extended.date.WebDateField();
        dateFiledTextBox = new com.alee.extended.date.WebDateField();
        statutoryCheckBox = new javax.swing.JCheckBox();
        validCheckBox = new javax.swing.JCheckBox();
        lastNotificationTextBox = new javax.swing.JTextField();
        dueDateComboBox = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        previousFileDateTextBox = new com.alee.extended.date.WebDateField();
        charterCheckBox = new javax.swing.JCheckBox();

        jLabel1.setText("CSC Name:");

        cscNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscNameTextBox.setEnabled(false);
        cscNameTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                cscNameTextBoxKeyTyped(evt);
            }
            public void keyPressed(java.awt.event.KeyEvent evt) {
                cscNameTextBoxKeyPressed(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                cscNameTextBoxKeyReleased(evt);
            }
        });

        jLabel2.setText("CSC Number:");

        cscNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscNumberTextBox.setEnabled(false);

        jLabel3.setText("CSC Type:");

        cscTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cscTypeComboBox.setEnabled(false);

        jLabel4.setText("CSC Phone 1:");

        cscPhone1TextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscPhone1TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscPhone1TextBox.setEnabled(false);

        jLabel5.setText("CSC Phone 2:");

        cscPhone2TextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscPhone2TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscPhone2TextBox.setEnabled(false);

        jLabel6.setText("CSC Fax:");

        cscFaxTextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscFaxTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscFaxTextBox.setEnabled(false);

        jLabel8.setText("CSC Address 1:");

        cscAddress1TextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscAddress1TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscAddress1TextBox.setEnabled(false);

        jLabel9.setText("CSC Address 2:");

        jLabel10.setText("CSC City:");

        jLabel11.setText("CSC State:");

        jLabel12.setText("CSC Zip:");

        jLabel13.setText("CSC County:");

        jLabel14.setText("CSC Email:");

        cscAddress2TextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscAddress2TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscAddress2TextBox.setEnabled(false);

        cscCityTextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscCityTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscCityTextBox.setEnabled(false);

        cscStateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cscStateComboBox.setEnabled(false);
        cscStateComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cscStateComboBoxActionPerformed(evt);
            }
        });

        cscZipTextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscZipTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscZipTextBox.setEnabled(false);

        cscEmailTextBox.setBackground(new java.awt.Color(238, 238, 238));
        cscEmailTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cscEmailTextBox.setEnabled(false);

        jLabel15.setText("Also Known As:");

        alsoKnownAsTextBox.setBackground(new java.awt.Color(238, 238, 238));
        alsoKnownAsTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        alsoKnownAsTextBox.setEnabled(false);

        cscCountyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        cscCountyComboBox.setEnabled(false);
        cscCountyComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cscCountyComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel15)
                        .addComponent(jLabel14)
                        .addComponent(jLabel13)
                        .addComponent(jLabel12)
                        .addComponent(jLabel11)
                        .addComponent(jLabel10)
                        .addComponent(jLabel8)
                        .addComponent(jLabel6)
                        .addComponent(jLabel5)
                        .addComponent(jLabel4)
                        .addComponent(jLabel3)
                        .addComponent(jLabel2)
                        .addComponent(jLabel1))
                    .addComponent(jLabel9))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cscAddress2TextBox)
                    .addComponent(cscNameTextBox)
                    .addComponent(cscNumberTextBox)
                    .addComponent(cscTypeComboBox, 0, 422, Short.MAX_VALUE)
                    .addComponent(cscPhone1TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cscPhone2TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cscFaxTextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cscAddress1TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(cscCityTextBox)
                    .addComponent(cscStateComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cscZipTextBox)
                    .addComponent(cscEmailTextBox)
                    .addComponent(alsoKnownAsTextBox)
                    .addComponent(cscCountyComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(cscNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(alsoKnownAsTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cscNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(cscTypeComboBox)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(cscPhone1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(cscPhone2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(cscFaxTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cscAddress1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(cscAddress2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cscCityTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cscStateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cscZipTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cscCountyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cscEmailTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addContainerGap(53, Short.MAX_VALUE))
        );

        jLabel16.setText("Fiscal Year Ending:");

        jLabel17.setText("Activites Last Filed:");

        jLabel21.setText("Last Notification:");

        jLabel27.setText("Due Date:");

        jLabel28.setText("Date Filed:");

        fiscalYearEndingComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        fiscalYearEndingComboBox.setEnabled(false);

        activitiesLastFiledTextBox.setEditable(false);
        activitiesLastFiledTextBox.setBackground(new java.awt.Color(238, 238, 238));
        activitiesLastFiledTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        activitiesLastFiledTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        activitiesLastFiledTextBox.setEnabled(false);
        activitiesLastFiledTextBox.setDateFormat(Global.mmddyyyy);

        activitiesLastFiledTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            activitiesLastFiledTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    activitiesLastFiledTextBoxMouseClicked(evt);
                }
            });
            activitiesLastFiledTextBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    activitiesLastFiledTextBoxActionPerformed(evt);
                }
            });

            dateFiledTextBox.setEditable(false);
            dateFiledTextBox.setBackground(new java.awt.Color(238, 238, 238));
            dateFiledTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
            dateFiledTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            dateFiledTextBox.setEnabled(false);
            dateFiledTextBox.setDateFormat(Global.mmddyyyy);

            dateFiledTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );
                dateFiledTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        dateFiledTextBoxMouseClicked(evt);
                    }
                });

                statutoryCheckBox.setText("Statutory");
                statutoryCheckBox.setEnabled(false);
                statutoryCheckBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        statutoryCheckBoxActionPerformed(evt);
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

                dueDateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
                dueDateComboBox.setEnabled(false);
                dueDateComboBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        dueDateComboBoxActionPerformed(evt);
                    }
                });

                jLabel18.setText("Previous File Date:");

                previousFileDateTextBox.setEditable(false);
                previousFileDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                previousFileDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
                previousFileDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                previousFileDateTextBox.setEnabled(false);
                previousFileDateTextBox.setDateFormat(Global.mmddyyyy);

                previousFileDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                    {
                        @Override
                        public void customize ( final WebCalendar calendar )
                        {
                            calendar.setStartWeekFromSunday ( true );
                        }
                    } );
                    previousFileDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            previousFileDateTextBoxMouseClicked(evt);
                        }
                    });
                    previousFileDateTextBox.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            previousFileDateTextBoxActionPerformed(evt);
                        }
                    });

                    charterCheckBox.setText("Home Rule (Charter)");
                    charterCheckBox.setEnabled(false);
                    charterCheckBox.addActionListener(new java.awt.event.ActionListener() {
                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                            charterCheckBoxActionPerformed(evt);
                        }
                    });

                    javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                    jPanel2.setLayout(jPanel2Layout);
                    jPanel2Layout.setHorizontalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel18)
                                .addComponent(jLabel28)
                                .addComponent(jLabel27)
                                .addComponent(jLabel21)
                                .addComponent(jLabel17)
                                .addComponent(jLabel16))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(activitiesLastFiledTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(lastNotificationTextBox)
                                .addComponent(fiscalYearEndingComboBox, 0, 397, Short.MAX_VALUE)
                                .addComponent(dateFiledTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(dueDateComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(previousFileDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(charterCheckBox)
                                        .addComponent(statutoryCheckBox)
                                        .addComponent(validCheckBox))
                                    .addGap(0, 0, Short.MAX_VALUE)))
                            .addContainerGap())
                    );
                    jPanel2Layout.setVerticalGroup(
                        jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addContainerGap()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(fiscalYearEndingComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(dueDateComboBox)
                                .addComponent(jLabel27, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel28)
                                .addComponent(dateFiledTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(activitiesLastFiledTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel17))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(jLabel18)
                                .addComponent(previousFileDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(lastNotificationTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel21))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(charterCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(statutoryCheckBox)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(validCheckBox)
                            .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    );

                    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                    this.setLayout(layout);
                    layout.setHorizontalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    );

                    layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel1, jPanel2});

                    layout.setVerticalGroup(
                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    );
                }// </editor-fold>//GEN-END:initComponents

    private void activitiesLastFiledTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_activitiesLastFiledTextBoxMouseClicked
        clearDate(activitiesLastFiledTextBox, evt);
    }//GEN-LAST:event_activitiesLastFiledTextBoxMouseClicked

    private void dateFiledTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateFiledTextBoxMouseClicked
        clearDate(dateFiledTextBox, evt);
    }//GEN-LAST:event_dateFiledTextBoxMouseClicked

    private void statutoryCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statutoryCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_statutoryCheckBoxActionPerformed

    private void validCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_validCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_validCheckBoxActionPerformed

    private void cscStateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cscStateComboBoxActionPerformed
        if(cscStateComboBox.getSelectedItem() != null) {
            if(cscStateComboBox.getSelectedItem().toString().equals("")) {
                cscCountyComboBox.removeAllItems();
                cscCountyComboBox.addItem("");
                cscCountyComboBox.setSelectedItem("");
            } else {
                cscCountyComboBox.removeAllItems();
                cscCountyComboBox.addItem("");
                List counties = County.loadCountyListByState(cscStateComboBox.getSelectedItem().toString());
                for(int i = 0; i < counties.size(); i++) {
                    County county = (County) counties.get(i);
                    cscCountyComboBox.addItem(county.countyName);
                }
            }
        }
    }//GEN-LAST:event_cscStateComboBoxActionPerformed

    private void cscCountyComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cscCountyComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_cscCountyComboBoxActionPerformed

    private void cscNameTextBoxKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cscNameTextBoxKeyPressed
        if(cscNameTextBox.getText().trim().equals("")) {
            Global.root.getjButton2().setEnabled(false);
        } else {
            Global.root.getjButton2().setEnabled(true);
        }
    }//GEN-LAST:event_cscNameTextBoxKeyPressed

    private void cscNameTextBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cscNameTextBoxKeyTyped
        if(cscNameTextBox.getText().trim().equals("")) {
            Global.root.getjButton2().setEnabled(false);
        } else {
            Global.root.getjButton2().setEnabled(true);
        }
    }//GEN-LAST:event_cscNameTextBoxKeyTyped

    private void cscNameTextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_cscNameTextBoxKeyReleased
        if(cscNameTextBox.getText().trim().equals("")) {
            Global.root.getjButton2().setEnabled(false);
        } else {
            Global.root.getjButton2().setEnabled(true);
        }
    }//GEN-LAST:event_cscNameTextBoxKeyReleased

    private void activitiesLastFiledTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_activitiesLastFiledTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_activitiesLastFiledTextBoxActionPerformed

    private void dueDateComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_dueDateComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_dueDateComboBoxActionPerformed

    private void previousFileDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_previousFileDateTextBoxMouseClicked
       clearDate(activitiesLastFiledTextBox, evt);
    }//GEN-LAST:event_previousFileDateTextBoxMouseClicked

    private void previousFileDateTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_previousFileDateTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_previousFileDateTextBoxActionPerformed

    private void charterCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_charterCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_charterCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.extended.date.WebDateField activitiesLastFiledTextBox;
    private javax.swing.JTextField alsoKnownAsTextBox;
    private javax.swing.JCheckBox charterCheckBox;
    private javax.swing.JTextField cscAddress1TextBox;
    private javax.swing.JTextField cscAddress2TextBox;
    private javax.swing.JTextField cscCityTextBox;
    private javax.swing.JComboBox<String> cscCountyComboBox;
    private javax.swing.JTextField cscEmailTextBox;
    private javax.swing.JTextField cscFaxTextBox;
    private javax.swing.JTextField cscNameTextBox;
    private javax.swing.JTextField cscNumberTextBox;
    private javax.swing.JTextField cscPhone1TextBox;
    private javax.swing.JTextField cscPhone2TextBox;
    private javax.swing.JComboBox<String> cscStateComboBox;
    private javax.swing.JComboBox<String> cscTypeComboBox;
    private javax.swing.JTextField cscZipTextBox;
    private com.alee.extended.date.WebDateField dateFiledTextBox;
    private javax.swing.JComboBox<String> dueDateComboBox;
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
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JTextField lastNotificationTextBox;
    private com.alee.extended.date.WebDateField previousFileDateTextBox;
    private javax.swing.JCheckBox statutoryCheckBox;
    private javax.swing.JCheckBox validCheckBox;
    // End of variables declaration//GEN-END:variables
}
