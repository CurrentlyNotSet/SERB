/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.employer;

import java.awt.Color;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.County;
import parker.serb.sql.Employer;
import parker.serb.sql.Jurisdiction;
import parker.serb.sql.NamePrefix;
import parker.serb.util.CancelUpdate;
import parker.serb.util.EmailValidation;

/**
 *
 * @author parkerjohnston
 */
public class employerDetailAddEdit extends javax.swing.JDialog {

    String empIDNumber;
    /**
     * Creates new form employerDetail
     * @param parent
     * @param modal
     * @param passedEmpIDNumber
     */
    public employerDetailAddEdit(java.awt.Frame parent, boolean modal, String passedEmpIDNumber) {
        super(parent, modal);
        initComponents();
        addListeners();
        empIDNumber = passedEmpIDNumber;
        loadInformation(empIDNumber);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void addListeners() {
        emailAddressTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                jButton2.setEnabled(EmailValidation.validEmail(emailAddressTextBox.getText().trim()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                jButton2.setEnabled(EmailValidation.validEmail(emailAddressTextBox.getText().trim()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                jButton2.setEnabled(EmailValidation.validEmail(emailAddressTextBox.getText().trim()));
            }
        });
    }
    
    private void loadInformation(String passedEmpIDNumber) {
        loadComboBoxes();
        
        Employer emp = Employer.loadEmployerByID(passedEmpIDNumber);
        employerNameTextBox.setText(emp.employerName);
        idNumberTextBox.setText(emp.employerIDNumber);
        salutationComboBox.setSelectedItem(emp.prefix);
        firstNameTextBox.setText(emp.firstName);
        middleInitialTextBox.setText(emp.middleInitial);
        lastNameTextBox.setText(emp.lastName);
        suffixTextBox.setText(emp.suffix);
        address1TextBox.setText(emp.address1);
        address2TextBox.setText(emp.address2);
        address3TextBox.setText(emp.address3);
        cityTextBox.setText(emp.city);
        stateComboBox.setSelectedItem(emp.state);
        zipCodeTextBox.setText(emp.zipCode);
        countyComboBox.setSelectedItem(emp.county);
        jurisdictionComboBox.setSelectedItem(emp.jurisdiction);
        phoneNumberOneTextBox.setText(emp.phoneNumber1);
        emailAddressTextBox.setText(emp.emailAddress);
        populationTextBox.setText(emp.population);
        employerIRNTextBox.setText(emp.employerIRN);
        
        
        //TODO
        //employerTypeComboBox.setSelectedItem(emp.employerType);
        contactTitleTextBox.setText(emp.title);
        phoneNumberTwoTextBox.setText(emp.phoneNumber2);
        faxNumberTextbox.setText(emp.faxNumber);
        employerTypeCodeTextBox.setText(emp.employerTypeCode);
        regionTextBox.setText(emp.region);
        assistantFirstNameTextBox.setText(emp.assistantFirstName);
        assistantMiddleInitialTextBox.setText(emp.assistantMiddleInitial);
        assistantLastNameTextBox.setText(emp.assistantLastName);
        assistantEmailAddressTextBox.setText(emp.assistantEmail);
    }
    
    private void loadComboBoxes() { 
        loadStateComboBox();
        loadCountyComboBox();
        loadJurisdictionComboBox();
        loadPrefixComboBox();
    }
    
    private void loadStateComboBox() {
        stateComboBox.removeAllItems();
        
        for (String state : Global.STATES) {
            stateComboBox.addItem(state);
        }
    }
    
    private void loadCountyComboBox() {
        List<String> countyList = County.loadCountyList();
        
        countyComboBox.removeAllItems();
        countyComboBox.addItem("");
        
        for (Object singleCounty : countyList) {
            County county = (County) singleCounty;
            countyComboBox.addItem(county.countyName);
        }
    }
    
    private void loadJurisdictionComboBox() {
        List<String> jurisdictionList = Jurisdiction.loadJurisdictionList();
        
        jurisdictionComboBox.removeAllItems();
        jurisdictionComboBox.addItem("");
        
        for (Object singleJurisdition : jurisdictionList) {
            Jurisdiction juris = (Jurisdiction) singleJurisdition;
            jurisdictionComboBox.addItem(juris.jurisCode);
        }
    }
    
    private void loadPrefixComboBox() {
        List<String> prefixList = NamePrefix.loadActivePrefix();
        
        salutationComboBox.removeAllItems();
        salutationComboBox.addItem("");
        
        for (String singlePrefix : prefixList) {
            salutationComboBox.addItem(singlePrefix);
        }
    }
    
    private void enableAllInputs() {
        employerNameTextBox.setEnabled(true);
        employerNameTextBox.setBackground(Color.white);
        if (!empIDNumber.equals("")){
            idNumberTextBox.setEnabled(true);
            idNumberTextBox.setBackground(Color.white);
        }
        salutationComboBox.setEnabled(true);
        firstNameTextBox.setEnabled(true);
        firstNameTextBox.setBackground(Color.white);
        middleInitialTextBox.setEnabled(true);
        middleInitialTextBox.setBackground(Color.white);
        lastNameTextBox.setEnabled(true);
        lastNameTextBox.setBackground(Color.white);
        suffixTextBox.setEnabled(true);
        suffixTextBox.setBackground(Color.white);
        address1TextBox.setEnabled(true);
        address1TextBox.setBackground(Color.white);
        address2TextBox.setEnabled(true);
        address2TextBox.setBackground(Color.white);
        address3TextBox.setEnabled(true);
        address3TextBox.setBackground(Color.white);
        cityTextBox.setEnabled(true);
        cityTextBox.setBackground(Color.white);
        stateComboBox.setEnabled(true);
        zipCodeTextBox.setEnabled(true);
        zipCodeTextBox.setBackground(Color.white);
        countyComboBox.setEnabled(true);
        jurisdictionComboBox.setEnabled(true);
        phoneNumberOneTextBox.setEnabled(true);
        phoneNumberOneTextBox.setBackground(Color.white);
        emailAddressTextBox.setEnabled(true);
        emailAddressTextBox.setBackground(Color.white);
        populationTextBox.setEnabled(true);
        populationTextBox.setBackground(Color.white);
        employerIRNTextBox.setEnabled(true);
        employerIRNTextBox.setBackground(Color.white);

        employerTypeComboBox.setEnabled(true);
        employerTypeComboBox.setBackground(Color.white);
        contactTitleTextBox.setEnabled(true);
        contactTitleTextBox.setBackground(Color.white);
        phoneNumberTwoTextBox.setEnabled(true);
        phoneNumberTwoTextBox.setBackground(Color.white);
        faxNumberTextbox.setEnabled(true);
        faxNumberTextbox.setBackground(Color.white);
        employerTypeCodeTextBox.setEnabled(true);
        employerTypeCodeTextBox.setBackground(Color.white);
        regionTextBox.setEnabled(true);
        regionTextBox.setBackground(Color.white);
        assistantFirstNameTextBox.setEnabled(true);
        assistantFirstNameTextBox.setBackground(Color.white);
        assistantMiddleInitialTextBox.setEnabled(true);
        assistantMiddleInitialTextBox.setBackground(Color.white);
        assistantLastNameTextBox.setEnabled(true);
        assistantLastNameTextBox.setBackground(Color.white);
        assistantEmailAddressTextBox.setEnabled(true);
        assistantEmailAddressTextBox.setBackground(Color.white);

    }
    
    private void disableAllInputs(boolean save) {
        if(save) {
            saveInformation();
        } else {
            loadInformation(empIDNumber);
        }
        
        employerNameTextBox.setEnabled(false);
        employerNameTextBox.setBackground(new Color(238,238,238));
        idNumberTextBox.setEnabled(false);
        idNumberTextBox.setBackground(new Color(238,238,238));
        salutationComboBox.setEnabled(false);
        firstNameTextBox.setEnabled(false);
        firstNameTextBox.setBackground(new Color(238,238,238));
        middleInitialTextBox.setEnabled(false);
        middleInitialTextBox.setBackground(new Color(238,238,238));
        lastNameTextBox.setEnabled(false);
        lastNameTextBox.setBackground(new Color(238,238,238));
        suffixTextBox.setEnabled(false);
        suffixTextBox.setBackground(new Color(238,238,238));
        address1TextBox.setEnabled(false);
        address1TextBox.setBackground(new Color(238,238,238));
        address2TextBox.setEnabled(false);
        address2TextBox.setBackground(new Color(238,238,238));
        address3TextBox.setEnabled(false);
        address3TextBox.setBackground(new Color(238,238,238));
        cityTextBox.setEnabled(false);
        cityTextBox.setBackground(new Color(238,238,238));
        stateComboBox.setEnabled(false);
        zipCodeTextBox.setEnabled(false);
        zipCodeTextBox.setBackground(new Color(238,238,238));
        countyComboBox.setEnabled(false);
        jurisdictionComboBox.setEnabled(false);
        phoneNumberOneTextBox.setEnabled(false);
        phoneNumberOneTextBox.setBackground(new Color(238,238,238));
        emailAddressTextBox.setEnabled(false);
        emailAddressTextBox.setBackground(new Color(238,238,238));
        populationTextBox.setEnabled(true);
        populationTextBox.setBackground(new Color(238,238,238));
        employerIRNTextBox.setEnabled(true);
        employerIRNTextBox.setBackground(new Color(238,238,238));
        
        
        
        employerTypeComboBox.setEnabled(false);
        employerTypeComboBox.setBackground(new Color(238,238,238));
        contactTitleTextBox.setEnabled(false);
        contactTitleTextBox.setBackground(new Color(238,238,238));
        phoneNumberTwoTextBox.setEnabled(false);
        phoneNumberTwoTextBox.setBackground(new Color(238,238,238));
        faxNumberTextbox.setEnabled(false);
        faxNumberTextbox.setBackground(new Color(238,238,238));
        employerTypeCodeTextBox.setEnabled(false);
        employerTypeCodeTextBox.setBackground(new Color(238,238,238));
        regionTextBox.setEnabled(false);
        regionTextBox.setBackground(new Color(238,238,238));
        assistantFirstNameTextBox.setEnabled(false);
        assistantFirstNameTextBox.setBackground(new Color(238,238,238));
        assistantMiddleInitialTextBox.setEnabled(false);
        assistantMiddleInitialTextBox.setBackground(new Color(238,238,238));
        assistantLastNameTextBox.setEnabled(false);
        assistantLastNameTextBox.setBackground(new Color(238,238,238));
        assistantEmailAddressTextBox.setEnabled(false);
        assistantEmailAddressTextBox.setBackground(new Color(238,238,238));

    }
    
    private void saveInformation() {
        Employer updateEmployer = new Employer();
        
        updateEmployer.employerName = employerNameTextBox.getText().trim();
        updateEmployer.employerIDNumber = idNumberTextBox.getText().trim();
        updateEmployer.prefix = salutationComboBox.getSelectedItem().toString();
        updateEmployer.firstName = firstNameTextBox.getText().trim();
        updateEmployer.middleInitial = middleInitialTextBox.getText().trim();
        updateEmployer.lastName = lastNameTextBox.getText().trim();
        updateEmployer.address1 = address1TextBox.getText().trim();
        updateEmployer.address2 = address2TextBox.getText().trim();
        updateEmployer.address3 = address3TextBox.getText().trim();
        updateEmployer.city = cityTextBox.getText().trim();
        updateEmployer.state = stateComboBox.getSelectedItem().toString();
        updateEmployer.zipCode = zipCodeTextBox.getText().trim();
        updateEmployer.county = countyComboBox.getSelectedItem().toString();
        updateEmployer.jurisdiction = jurisdictionComboBox.getSelectedItem().toString();
        updateEmployer.phoneNumber1 = phoneNumberOneTextBox.getText().trim();
        updateEmployer.emailAddress = emailAddressTextBox.getText().trim();
        updateEmployer.population = populationTextBox.getText().trim();
        updateEmployer.employerIRN = employerIRNTextBox.getText().trim();
        
        
        
        //TODO: Swap out for the actual code
        //updateEmployer.employerType = employerTypeComboBox.getSelectedItem().toString();
        updateEmployer.title = contactTitleTextBox.getText().trim();
        updateEmployer.phoneNumber2 = phoneNumberTwoTextBox.getText().trim();
        updateEmployer.faxNumber = faxNumberTextbox.getText().trim();
        updateEmployer.employerTypeCode = employerTypeCodeTextBox.getText().trim();
        updateEmployer.region = regionTextBox.getText().trim();
        updateEmployer.assistantFirstName = assistantFirstNameTextBox.getText().trim();
        updateEmployer.assistantMiddleInitial = assistantMiddleInitialTextBox.getText().trim();
        updateEmployer.assistantLastName = assistantLastNameTextBox.getText().trim();
        updateEmployer.assistantEmail = assistantEmailAddressTextBox.getText().trim();
        
        
        
        Employer.updateEmployerByEmployerIDNumber(updateEmployer.employerName, updateEmployer);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        employerNameTextBox = new javax.swing.JTextField();
        idNumberTextBox = new javax.swing.JTextField();
        firstNameTextBox = new javax.swing.JTextField();
        middleInitialTextBox = new javax.swing.JTextField();
        lastNameTextBox = new javax.swing.JTextField();
        suffixTextBox = new javax.swing.JTextField();
        address1TextBox = new javax.swing.JTextField();
        address2TextBox = new javax.swing.JTextField();
        address3TextBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        stateComboBox = new javax.swing.JComboBox<>();
        zipCodeTextBox = new javax.swing.JTextField();
        cityTextBox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        countyComboBox = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jurisdictionComboBox = new javax.swing.JComboBox<>();
        jLabel13 = new javax.swing.JLabel();
        phoneNumberOneTextBox = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        emailAddressTextBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        salutationComboBox = new javax.swing.JComboBox<>();
        populationTextBox = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        employerIRNTextBox = new javax.swing.JTextField();
        jLabel17 = new javax.swing.JLabel();
        employerTypeComboBox = new javax.swing.JComboBox<>();
        jLabel18 = new javax.swing.JLabel();
        contactTitleTextBox = new javax.swing.JTextField();
        phoneNumberTwoTextBox = new javax.swing.JTextField();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        faxNumberTextbox = new javax.swing.JTextField();
        regionTextBox = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        assistantFirstNameTextBox = new javax.swing.JTextField();
        assistantMiddleInitialTextBox = new javax.swing.JTextField();
        assistantLastNameTextBox = new javax.swing.JTextField();
        assistantEmailAddressTextBox = new javax.swing.JTextField();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        employerTypeCodeTextBox = new javax.swing.JTextField();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Employer Information");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel2.setText("Employer Name:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel3.setText("Contact Name:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel4.setText("Address:");

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel5.setText("Address:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel6.setText("Address:");

        jLabel7.setText("ID Number:");

        employerNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        employerNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        employerNameTextBox.setEnabled(false);

        idNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        idNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        idNumberTextBox.setEnabled(false);

        firstNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        firstNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        firstNameTextBox.setEnabled(false);

        middleInitialTextBox.setBackground(new java.awt.Color(238, 238, 238));
        middleInitialTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        middleInitialTextBox.setEnabled(false);

        lastNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        lastNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lastNameTextBox.setEnabled(false);

        suffixTextBox.setBackground(new java.awt.Color(238, 238, 238));
        suffixTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        suffixTextBox.setEnabled(false);

        address1TextBox.setBackground(new java.awt.Color(238, 238, 238));
        address1TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        address1TextBox.setEnabled(false);

        address2TextBox.setBackground(new java.awt.Color(238, 238, 238));
        address2TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        address2TextBox.setEnabled(false);

        address3TextBox.setBackground(new java.awt.Color(238, 238, 238));
        address3TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        address3TextBox.setEnabled(false);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel8.setText("City:");

        jLabel9.setText("State:");

        jLabel10.setText("Zip:");

        stateComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        stateComboBox.setEnabled(false);

        zipCodeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        zipCodeTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        zipCodeTextBox.setEnabled(false);

        cityTextBox.setBackground(new java.awt.Color(238, 238, 238));
        cityTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        cityTextBox.setEnabled(false);

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel11.setText("County:");

        countyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        countyComboBox.setEnabled(false);

        jLabel12.setText("Jurisdiction:");

        jurisdictionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jurisdictionComboBox.setEnabled(false);

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel13.setText("Phone Number 1:");

        phoneNumberOneTextBox.setBackground(new java.awt.Color(238, 238, 238));
        phoneNumberOneTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        phoneNumberOneTextBox.setEnabled(false);

        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel14.setText("Email:");

        emailAddressTextBox.setBackground(new java.awt.Color(238, 238, 238));
        emailAddressTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        emailAddressTextBox.setEnabled(false);

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        salutationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        salutationComboBox.setEnabled(false);

        populationTextBox.setBackground(new java.awt.Color(238, 238, 238));
        populationTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        populationTextBox.setEnabled(false);

        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel15.setText("Population:");

        jLabel16.setText("EmployerIRN:");

        employerIRNTextBox.setBackground(new java.awt.Color(238, 238, 238));
        employerIRNTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        employerIRNTextBox.setEnabled(false);

        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel17.setText("Employer Type:");

        employerTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        employerTypeComboBox.setEnabled(false);

        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel18.setText("Contact Title:");

        contactTitleTextBox.setEnabled(false);

        phoneNumberTwoTextBox.setBackground(new java.awt.Color(238, 238, 238));
        phoneNumberTwoTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        phoneNumberTwoTextBox.setEnabled(false);

        jLabel19.setText("Phone Number 2:");

        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel20.setText("Fax Number:");

        faxNumberTextbox.setBackground(new java.awt.Color(238, 238, 238));
        faxNumberTextbox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        faxNumberTextbox.setEnabled(false);

        regionTextBox.setBackground(new java.awt.Color(238, 238, 238));
        regionTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        regionTextBox.setEnabled(false);

        jLabel21.setText("Region:");

        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel22.setText("Assistant Name:");

        assistantFirstNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        assistantFirstNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        assistantFirstNameTextBox.setEnabled(false);
        assistantFirstNameTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                assistantFirstNameTextBoxActionPerformed(evt);
            }
        });

        assistantMiddleInitialTextBox.setBackground(new java.awt.Color(238, 238, 238));
        assistantMiddleInitialTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        assistantMiddleInitialTextBox.setEnabled(false);

        assistantLastNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        assistantLastNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        assistantLastNameTextBox.setEnabled(false);

        assistantEmailAddressTextBox.setBackground(new java.awt.Color(238, 238, 238));
        assistantEmailAddressTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        assistantEmailAddressTextBox.setEnabled(false);

        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.RIGHT);
        jLabel23.setText("Assistant Email:");

        jLabel24.setText("Employer Type Code:");

        employerTypeCodeTextBox.setEnabled(false);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                            .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(address1TextBox)
                            .addComponent(address2TextBox)
                            .addComponent(address3TextBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cityTextBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel9)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zipCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(employerNameTextBox)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employerTypeCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(salutationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(firstNameTextBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(middleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(206, 206, 206))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lastNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(suffixTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(employerTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(contactTitleTextBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(countyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jurisdictionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addComponent(emailAddressTextBox)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(faxNumberTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(phoneNumberOneTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 266, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel19)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(phoneNumberTwoTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(populationTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(49, 49, 49)
                                .addComponent(jLabel21)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(regionTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 32, Short.MAX_VALUE)
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(employerIRNTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(90, 90, 90))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(assistantFirstNameTextBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(assistantMiddleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(200, 200, 200))
                                    .addComponent(assistantLastNameTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(assistantEmailAddressTextBox))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel11, jLabel13, jLabel14, jLabel15, jLabel17, jLabel18, jLabel2, jLabel20, jLabel3, jLabel4, jLabel5, jLabel6, jLabel8});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(employerTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(employerNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel24)
                    .addComponent(employerTypeCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(firstNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(middleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suffixTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salutationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(contactTitleTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(address1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(address2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(address3TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(jLabel9)
                    .addComponent(jLabel10)
                    .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(zipCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(cityTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(countyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jurisdictionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(phoneNumberOneTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel19)
                    .addComponent(phoneNumberTwoTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(emailAddressTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(faxNumberTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel16)
                        .addComponent(employerIRNTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(populationTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel15)
                    .addComponent(regionTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel21))
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(assistantFirstNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assistantMiddleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(assistantLastNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(assistantEmailAddressTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 84, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        if(jButton1.getText().equals("Close")) {
            dispose();
        } else {
            CancelUpdate cancel = new CancelUpdate(Global.root, true);
            if(!cancel.isReset()) {
            } else {
                loadInformation(empIDNumber);
                jButton2.setText("Update");
                jButton1.setText("Close");
                disableAllInputs(false);
            }
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jButton2.getText().equals("Update")) {
            jButton2.setText("Save");
            jButton1.setText("Cancel");
            enableAllInputs();
        } else {
            jButton2.setText("Update");
            jButton1.setText("Close");
            disableAllInputs(true);
//            dispose();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void assistantFirstNameTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_assistantFirstNameTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_assistantFirstNameTextBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1TextBox;
    private javax.swing.JTextField address2TextBox;
    private javax.swing.JTextField address3TextBox;
    private javax.swing.JTextField assistantEmailAddressTextBox;
    private javax.swing.JTextField assistantFirstNameTextBox;
    private javax.swing.JTextField assistantLastNameTextBox;
    private javax.swing.JTextField assistantMiddleInitialTextBox;
    private javax.swing.JTextField cityTextBox;
    private javax.swing.JTextField contactTitleTextBox;
    private javax.swing.JComboBox<String> countyComboBox;
    private javax.swing.JTextField emailAddressTextBox;
    private javax.swing.JTextField employerIRNTextBox;
    private javax.swing.JTextField employerNameTextBox;
    private javax.swing.JTextField employerTypeCodeTextBox;
    private javax.swing.JComboBox<String> employerTypeComboBox;
    private javax.swing.JTextField faxNumberTextbox;
    private javax.swing.JTextField firstNameTextBox;
    private javax.swing.JTextField idNumberTextBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JComboBox<String> jurisdictionComboBox;
    private javax.swing.JTextField lastNameTextBox;
    private javax.swing.JTextField middleInitialTextBox;
    private javax.swing.JTextField phoneNumberOneTextBox;
    private javax.swing.JTextField phoneNumberTwoTextBox;
    private javax.swing.JTextField populationTextBox;
    private javax.swing.JTextField regionTextBox;
    private javax.swing.JComboBox<String> salutationComboBox;
    private javax.swing.JComboBox<String> stateComboBox;
    private javax.swing.JTextField suffixTextBox;
    private javax.swing.JTextField zipCodeTextBox;
    // End of variables declaration//GEN-END:variables
}
