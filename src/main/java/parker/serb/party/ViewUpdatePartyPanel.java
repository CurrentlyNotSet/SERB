/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.party;

import parker.serb.util.NumberFormatService;
import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.SwingUtilities;
import parker.serb.Global;
import parker.serb.sql.Party;

//TODO: Allow for a party to be updated from this panel
//TODO: Reload Table after changes have been made to name, phone number, email

/**
 *
 * @author parker
 */
public class ViewUpdatePartyPanel extends javax.swing.JDialog {

    String id;
    /**
     * Creates new form ViewUpdatePartyPanel
     * @param parent
     * @param modal
     * @param passedId
     */
    public ViewUpdatePartyPanel(java.awt.Frame parent, boolean modal, String passedId) {
        super(parent, modal);
        setUndecorated(true);
        initComponents();
        addListeners();
        loadStateComboBox();
        id = passedId;
        loadInformation(id);
        disableAll();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void addListeners() {
        emailAddressTextBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e) || e.isControlDown()) {
                    final String mailURIStr = String.format("mailto:%s",
                    emailAddressTextBox.getText().trim());
                    try {
                        final URI mailURI = new URI(mailURIStr);
                        Desktop desktop;
                        desktop = Desktop.getDesktop();
                        desktop.mail(mailURI);
                    } catch (URISyntaxException | IOException ex) {
                        Logger.getLogger(ViewUpdatePartyPanel.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
            
            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
    private void loadStateComboBox() {
        stateComboBox.removeAllItems();
        
        for (String state : Global.states) {
            stateComboBox.addItem(state);
        }
    }
    
    
    private void loadInformation(String id) {
        Party partyInformation = Party.getPartyByID(id);

        firstNameTextBox.setText(partyInformation.firstName);
        middleInitialTextBox.setText(partyInformation.middleInitial);
        lastNameTextBox.setText(partyInformation.lastName);
        companyTextBox.setText(partyInformation.companyName);
        address1TextBox.setText(partyInformation.address1);
        address2TextBox.setText(partyInformation.address2);
        address3TextBox.setText(partyInformation.address3);
        cityTextBox.setText(partyInformation.city);
        stateComboBox.setSelectedItem(partyInformation.state);
        zipCodeTextBox.setText(partyInformation.zip);
        phoneNumberTextBox.setText(NumberFormatService.convertStringToPhoneNumber(partyInformation.workPhone));
        emailAddressTextBox.setText(partyInformation.emailAddress);
    }
    
    private void enableAll() {
        jButton2.setText("Save");
        firstNameTextBox.setEnabled(true);
        firstNameTextBox.setBackground(Color.WHITE);
        middleInitialTextBox.setEnabled(true);
        middleInitialTextBox.setBackground(Color.WHITE);
        lastNameTextBox.setEnabled(true);
        lastNameTextBox.setBackground(Color.WHITE);
        companyTextBox.setEnabled(true);
        companyTextBox.setBackground(Color.WHITE);
        address1TextBox.setEnabled(true);
        address1TextBox.setBackground(Color.WHITE);
        address2TextBox.setEnabled(true);
        address2TextBox.setBackground(Color.WHITE);
        address3TextBox.setEnabled(true);
        address3TextBox.setBackground(Color.WHITE);
        phoneNumberTextBox.setEnabled(true);
        phoneNumberTextBox.setBackground(Color.WHITE);
        emailAddressTextBox.setEnabled(true);
        emailAddressTextBox.setBackground(Color.WHITE);
        cityTextBox.setEnabled(true);
        cityTextBox.setBackground(Color.WHITE);
        zipCodeTextBox.setEnabled(true);
        zipCodeTextBox.setBackground(Color.WHITE);
        stateComboBox.setEnabled(true);
    }
    
    private void disableAll() {
        jButton2.setText("Update");
        firstNameTextBox.setEnabled(false);
        firstNameTextBox.setBackground(new Color(238,238,238));
        middleInitialTextBox.setEnabled(false);
        middleInitialTextBox.setBackground(new Color(238,238,238));
        lastNameTextBox.setEnabled(false);
        lastNameTextBox.setBackground(new Color(238,238,238));
        companyTextBox.setEnabled(false);
        companyTextBox.setBackground(new Color(238,238,238));
        address1TextBox.setEnabled(false);
        address1TextBox.setBackground(new Color(238,238,238));
        address2TextBox.setEnabled(false);
        address2TextBox.setBackground(new Color(238,238,238));
        address3TextBox.setEnabled(false);
        address3TextBox.setBackground(new Color(238,238,238));
        phoneNumberTextBox.setEnabled(false);
        phoneNumberTextBox.setBackground(new Color(238,238,238));
        emailAddressTextBox.setEnabled(false);
        emailAddressTextBox.setBackground(new Color(238,238,238));
        cityTextBox.setEnabled(false);
        cityTextBox.setBackground(new Color(238,238,238));
        zipCodeTextBox.setEnabled(false);
        zipCodeTextBox.setBackground(new Color(238,238,238));
        stateComboBox.setEnabled(false);
    }
    
    private void updatePartyInformation() {
        Party updateParty = new Party();
        
        updateParty.firstName = firstNameTextBox.getText().trim();
        updateParty.middleInitial = middleInitialTextBox.getText().trim();
        updateParty.lastName = lastNameTextBox.getText().trim();
        updateParty.companyName = companyTextBox.getText().trim();
        updateParty.address1 = address1TextBox.getText().trim();
        updateParty.address2 = address2TextBox.getText().trim();
        updateParty.address3 = address3TextBox.getText().trim();
        updateParty.city = cityTextBox.getText().trim();
        updateParty.state = stateComboBox.getSelectedItem().toString().trim();
        updateParty.zip = zipCodeTextBox.getText().trim();
        updateParty.workPhone = NumberFormatService.convertPhoneNumberToString(phoneNumberTextBox.getText().trim());
        updateParty.emailAddress = emailAddressTextBox.getText().trim();
        
        Party.updateParty(updateParty, id);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
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
        firstNameTextBox = new javax.swing.JTextField();
        companyTextBox = new javax.swing.JTextField();
        address1TextBox = new javax.swing.JTextField();
        address2TextBox = new javax.swing.JTextField();
        address3TextBox = new javax.swing.JTextField();
        phoneNumberTextBox = new javax.swing.JTextField();
        emailAddressTextBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        cityTextBox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        stateComboBox = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        zipCodeTextBox = new javax.swing.JTextField();
        middleInitialTextBox = new javax.swing.JTextField();
        lastNameTextBox = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Party Information");

        jLabel2.setText("Name:");

        jLabel3.setText("Company:");

        jLabel4.setText("Address:");

        jLabel5.setText("Address:");

        jLabel6.setText("Address:");

        jLabel7.setText("City:");

        jLabel8.setText("Phone:");

        jLabel9.setText("Email:");

        firstNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        firstNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        firstNameTextBox.setEnabled(false);

        companyTextBox.setBackground(new java.awt.Color(238, 238, 238));
        companyTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        address1TextBox.setBackground(new java.awt.Color(238, 238, 238));
        address1TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        address2TextBox.setBackground(new java.awt.Color(238, 238, 238));
        address2TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        address3TextBox.setBackground(new java.awt.Color(238, 238, 238));
        address3TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        phoneNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        phoneNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        emailAddressTextBox.setBackground(new java.awt.Color(238, 238, 238));
        emailAddressTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

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

        cityTextBox.setBackground(new java.awt.Color(238, 238, 238));
        cityTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel11.setText("State:");

        stateComboBox.setMaximumRowCount(10);
        stateComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel12.setText("Zip:");

        zipCodeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        zipCodeTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        middleInitialTextBox.setBackground(new java.awt.Color(238, 238, 238));
        middleInitialTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        middleInitialTextBox.setEnabled(false);

        lastNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        lastNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        lastNameTextBox.setEnabled(false);

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
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(companyTextBox)
                            .addComponent(address1TextBox)
                            .addComponent(address2TextBox)
                            .addComponent(address3TextBox)
                            .addComponent(phoneNumberTextBox)
                            .addComponent(emailAddressTextBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cityTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zipCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(firstNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(middleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lastNameTextBox))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(firstNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(middleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lastNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(companyTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(jLabel7)
                    .addComponent(cityTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(zipCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(phoneNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(emailAddressTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 24, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jButton2.getText().equals("Update")) {
            enableAll();
            jButton2.setText("Save");
        } else {
            updatePartyInformation();
            disableAll();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1TextBox;
    private javax.swing.JTextField address2TextBox;
    private javax.swing.JTextField address3TextBox;
    private javax.swing.JTextField cityTextBox;
    private javax.swing.JTextField companyTextBox;
    private javax.swing.JTextField emailAddressTextBox;
    private javax.swing.JTextField firstNameTextBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTextField phoneNumberTextBox;
    private javax.swing.JComboBox stateComboBox;
    private javax.swing.JTextField zipCodeTextBox;
    // End of variables declaration//GEN-END:variables
}
