/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.party;

import java.awt.event.ActionEvent;
import parker.serb.util.NumberFormatService;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.Party;

/**
 *
 * @author parker
 */
public class CreateNewPartyDialog extends javax.swing.JDialog {

    /**
     * Creates new form CreateNewPartyDialog
     * @param parent
     * @param modal
     */
    public CreateNewPartyDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        setUndecorated(true);
        initComponents();
        addListeners();
        loadStateComboBox();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void addListeners() {
        firstNameTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateCreateButton();
            }
        });
        
        address1TextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateCreateButton();
            }
        });
        
        cityTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateCreateButton();
            }
        });
        
        stateComboBox.addActionListener((ActionEvent e) -> {
            validateCreateButton();
        });
        
        zipCodeTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateCreateButton();
            }
        });
        
        emailTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateCreateButton();
            }
        });
        
        phoneTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateCreateButton();
            }
        });
        
        lastNameTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateCreateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateCreateButton();
            }
        });
    }
    
    private void validateCreateButton() {
        if(firstNameTextBox.getText().equals("") ||
                lastNameTextBox.getText().equals("") ||
                address1TextBox.getText().equals("") ||
                cityTextBox.getText().equals("") ||
                stateComboBox.getSelectedItem().toString().equals("") ||
                zipCodeTextBox.getText().equals("") ||
                emailTextBox.getText().equals("") ||
                phoneTextBox.getText().equals("")) {
            jButton1.setEnabled(false);
        } else {
            jButton1.setEnabled(true);
        }
    }
    
    private void loadStateComboBox() {
        stateComboBox.removeAllItems();
        
        for (String state : Global.states) {
            stateComboBox.addItem(state);
        }
        stateComboBox.setSelectedItem("OH");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        firstNameTextBox = new javax.swing.JTextField();
        companyTextBox = new javax.swing.JTextField();
        address1TextBox = new javax.swing.JTextField();
        address2TextBox = new javax.swing.JTextField();
        cityTextBox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        stateComboBox = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        zipCodeTextBox = new javax.swing.JTextField();
        emailTextBox = new javax.swing.JTextField();
        address3TextBox = new javax.swing.JTextField();
        phoneTextBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        middleInitialTextBox = new javax.swing.JTextField();
        lastNameTextBox = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Create New Party");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Please enter the following information to create a new party");

        jLabel3.setText("Name:");

        jLabel4.setText("Company:");

        jLabel5.setText("Address:");

        jLabel6.setText("Address:");

        jLabel7.setText("Address:");

        jLabel8.setText("City:");

        jLabel9.setText("Email:");

        jLabel10.setText("Phone:");

        jLabel11.setText("State:");

        stateComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Zip:");

        jButton1.setText("Create");
        jButton1.setEnabled(false);
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10)
                            .addComponent(jLabel11))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(companyTextBox)
                            .addComponent(address1TextBox)
                            .addComponent(address2TextBox)
                            .addComponent(cityTextBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 173, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zipCodeTextBox))
                            .addComponent(emailTextBox)
                            .addComponent(address3TextBox)
                            .addComponent(phoneTextBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(firstNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(middleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lastNameTextBox))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1)))
                .addGap(10, 10, 10))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(firstNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(middleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(companyTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(address1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(address2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(address3TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(cityTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(zipCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(emailTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(phoneTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        Party party = new Party();
        party.firstName = firstNameTextBox.getText().trim();
        party.middleInitial = middleInitialTextBox.getText().trim();
        party.lastName = lastNameTextBox.getText().trim();
        party.companyName = companyTextBox.getText().trim();
        party.address1 = address1TextBox.getText().trim();
        party.address2 = address2TextBox.getText().trim();
        party.address3 = address3TextBox.getText().trim();
        party.city = cityTextBox.getText().trim();
        party.state = stateComboBox.getSelectedItem().toString();
        party.zip = zipCodeTextBox.getText().trim();
        party.emailAddress = emailTextBox.getText().trim();
        party.workPhone = NumberFormatService.convertPhoneNumberToString(phoneTextBox.getText().trim());
        
        Party.createParty(party);
        
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1TextBox;
    private javax.swing.JTextField address2TextBox;
    private javax.swing.JTextField address3TextBox;
    private javax.swing.JTextField cityTextBox;
    private javax.swing.JTextField companyTextBox;
    private javax.swing.JTextField emailTextBox;
    private javax.swing.JTextField firstNameTextBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField lastNameTextBox;
    private javax.swing.JTextField middleInitialTextBox;
    private javax.swing.JTextField phoneTextBox;
    private javax.swing.JComboBox stateComboBox;
    private javax.swing.JTextField zipCodeTextBox;
    // End of variables declaration//GEN-END:variables
}
