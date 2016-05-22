/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.employer;

import java.awt.Color;
import java.util.List;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.sql.County;
import parker.serb.sql.Employer;
import parker.serb.sql.Jurisdiction;
import parker.serb.sql.NamePrefix;
import parker.serb.util.CancelUpdate;

/**
 *
 * @author parkerjohnston
 */
public class employerDetail extends javax.swing.JDialog {

    String empIDNumber;
    /**
     * Creates new form employerDetail
     */
    public employerDetail(java.awt.Frame parent, boolean modal, String passedEmpIDNumber) {
        super(parent, modal);
        initComponents();
        empIDNumber = passedEmpIDNumber;
        loadInformation(empIDNumber);
        setLocationRelativeTo(parent);
        setVisible(true);
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
        //nametitle
        address1TextBox.setText(emp.address1);
        address2TextBox.setText(emp.address2);
        address3TextBox.setText(emp.address3);
        cityTextBox.setText(emp.city);
        stateComboBox.setSelectedItem(emp.state);
        zipCodeTextBox.setText(emp.zipCode);
        countyComboBox.setSelectedItem(emp.county);
        jurisdictionComboBox.setSelectedItem(emp.jurisdiction);
        phoneNumberTextBox.setText(emp.phoneNumber1);
        emailAddressTextBox.setText(emp.emailAddress);
        
        
    }
    
    private void loadComboBoxes() { 
        loadStateComboBox();
        loadCountyComboBox();
        loadJurisdictionComboBox();
        loadPrefixComboBox();
    }
    
    private void loadStateComboBox() {
        stateComboBox.removeAllItems();
        
        for (String state : Global.states) {
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
//        idNumberTextBox.setEnabled(true);
//        idNumberTextBox.setBackground(Color.white);
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
        phoneNumberTextBox.setEnabled(true);
        phoneNumberTextBox.setBackground(Color.white);
        emailAddressTextBox.setEnabled(true);
        emailAddressTextBox.setBackground(Color.white);
//        additionalInformationTextArea.setEnabled(true);
//        additionalInformationTextArea.setBackground(Color.white);
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
        phoneNumberTextBox.setEnabled(false);
        phoneNumberTextBox.setBackground(new Color(238,238,238));
        emailAddressTextBox.setEnabled(false);
        emailAddressTextBox.setBackground(new Color(238,238,238));
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
        updateEmployer.phoneNumber1 = phoneNumberTextBox.getText().trim();
        updateEmployer.emailAddress = emailAddressTextBox.getText().trim();
        
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
        phoneNumberTextBox = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        emailAddressTextBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        salutationComboBox = new javax.swing.JComboBox<>();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Employer Information");

        jLabel2.setText("Employer Name:");

        jLabel3.setText("Contact Name:");

        jLabel4.setText("Address:");

        jLabel5.setText("Address:");

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

        jLabel11.setText("County:");

        countyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        countyComboBox.setEnabled(false);

        jLabel12.setText("Jurisdiction:");

        jurisdictionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        jurisdictionComboBox.setEnabled(false);

        jLabel13.setText("Phone Number:");

        phoneNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        phoneNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        phoneNumberTextBox.setEnabled(false);

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel8)
                            .addComponent(jLabel11)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(address1TextBox)
                            .addComponent(address2TextBox)
                            .addComponent(address3TextBox)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(phoneNumberTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(countyComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 188, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jurisdictionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel14)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(emailAddressTextBox))))
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
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(idNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(salutationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 76, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(firstNameTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(middleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(206, 206, 206))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addComponent(lastNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 194, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                                .addComponent(suffixTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel7)
                    .addComponent(employerNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(idNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(firstNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(middleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suffixTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(salutationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
                        .addComponent(jurisdictionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(phoneNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14)
                    .addComponent(emailAddressTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 17, Short.MAX_VALUE)
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
            CancelUpdate cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1TextBox;
    private javax.swing.JTextField address2TextBox;
    private javax.swing.JTextField address3TextBox;
    private javax.swing.JTextField cityTextBox;
    private javax.swing.JComboBox<String> countyComboBox;
    private javax.swing.JTextField emailAddressTextBox;
    private javax.swing.JTextField employerNameTextBox;
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
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JTextField phoneNumberTextBox;
    private javax.swing.JComboBox<String> salutationComboBox;
    private javax.swing.JComboBox<String> stateComboBox;
    private javax.swing.JTextField suffixTextBox;
    private javax.swing.JTextField zipCodeTextBox;
    // End of variables declaration//GEN-END:variables
}
