/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.party;

import java.awt.Color;
import java.awt.Desktop;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.processMailingAddressBookmarks;
import parker.serb.sql.CaseParty;
import parker.serb.sql.NamePrefix;
import parker.serb.sql.Party;
import parker.serb.util.CancelUpdate;
import parker.serb.util.EmailValidation;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parker
 */
public class ViewUpdatePartyPanel extends javax.swing.JDialog {

    String id;
    public int updateStatus;
    Party partyInformation;

    /**
     * Creates new form ViewUpdatePartyPanel
     * @param parent
     * @param modal
     * @param passedId
     */
    public ViewUpdatePartyPanel(java.awt.Frame parent, boolean modal, String passedId) {
        super(parent, modal);
        initComponents();
        addListeners();
        loadPrefixComboBox();
        loadStateComboBox();
        id = passedId;
        loadInformation(id);
        disableAll();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addListeners() {
        emailAddressTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButton.setEnabled(EmailValidation.validEmail(emailAddressTextBox.getText().trim()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButton.setEnabled(EmailValidation.validEmail(emailAddressTextBox.getText().trim()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButton.setEnabled(EmailValidation.validEmail(emailAddressTextBox.getText().trim()));
            }
        });

        middleInitialTextBox.addKeyListener(new KeyListener() {

            @Override
            public void keyTyped(KeyEvent e) {
                if (middleInitialTextBox.getText().length() == 1) {
                    e.consume();
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });

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
                        SlackNotification.sendNotification(ex);
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
        stateComboBox.addItem("");

        for (String state : Global.STATES) {
            stateComboBox.addItem(state);
        }
    }

    private void loadPrefixComboBox() {
        List<String> prefixList = NamePrefix.loadActivePrefix();

        prefixComboBox.removeAllItems();
        prefixComboBox.addItem("");

        for (String singlePrefix : prefixList) {
            prefixComboBox.addItem(singlePrefix);
        }
    }


    private void loadInformation(String id) {
        partyInformation = Party.getPartyByID(String.valueOf(id));

        prefixComboBox.setSelectedItem(partyInformation.prefix);
        firstNameTextBox.setText(partyInformation.firstName);
        middleInitialTextBox.setText(partyInformation.middleInitial);
        lastNameTextBox.setText(partyInformation.lastName);
        suffixTextBox.setText(partyInformation.suffix);
        nameTitleTextBox.setText(partyInformation.nameTitle);
        jobTitleTextBox.setText(partyInformation.jobTitle);
        companyTextBox.setText(partyInformation.companyName);
        address1TextBox.setText(partyInformation.address1);
        address2TextBox.setText(partyInformation.address2);
        address3TextBox.setText(partyInformation.address3);
        cityTextBox.setText(partyInformation.city);
        stateComboBox.setSelectedItem(partyInformation.stateCode == null ? "" : partyInformation.stateCode);
        zipCodeTextBox.setText(partyInformation.zipCode);
        phoneNumberTextBox.setText(partyInformation.phone1);
        phone2NumberTextBox.setText(partyInformation.phone2);
        emailAddressTextBox.setText(partyInformation.emailAddress);
        faxNumberTextBox.setText(partyInformation.fax);
    }

    private void enableAll() {
        updateButton.setText("Save");
        closeButton.setText("Cancel");
        prefixComboBox.setEnabled(true);
        firstNameTextBox.setEnabled(true);
        firstNameTextBox.setBackground(Color.WHITE);
        middleInitialTextBox.setEnabled(true);
        middleInitialTextBox.setBackground(Color.WHITE);
        lastNameTextBox.setEnabled(true);
        lastNameTextBox.setBackground(Color.WHITE);
        suffixTextBox.setEnabled(true);
        suffixTextBox.setBackground(Color.WHITE);
        nameTitleTextBox.setEnabled(true);
        nameTitleTextBox.setBackground(Color.WHITE);
        jobTitleTextBox.setEnabled(true);
        jobTitleTextBox.setBackground(Color.WHITE);
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
        phone2NumberTextBox.setEnabled(true);
        phone2NumberTextBox.setBackground(Color.WHITE);
        emailAddressTextBox.setEnabled(true);
        emailAddressTextBox.setBackground(Color.WHITE);
        cityTextBox.setEnabled(true);
        cityTextBox.setBackground(Color.WHITE);
        zipCodeTextBox.setEnabled(true);
        zipCodeTextBox.setBackground(Color.WHITE);
        stateComboBox.setEnabled(true);
        faxNumberTextBox.setEnabled(true);
        faxNumberTextBox.setBackground(Color.WHITE);
    }

    private void disableAll() {
        updateButton.setText("Update");
        closeButton.setText("Close");
        prefixComboBox.setEnabled(false);
        firstNameTextBox.setEnabled(false);
        firstNameTextBox.setBackground(new Color(238,238,238));
        middleInitialTextBox.setEnabled(false);
        middleInitialTextBox.setBackground(new Color(238,238,238));
        lastNameTextBox.setEnabled(false);
        lastNameTextBox.setBackground(new Color(238,238,238));
        suffixTextBox.setEnabled(false);
        suffixTextBox.setBackground(new Color(238,238,238));
        nameTitleTextBox.setEnabled(false);
        nameTitleTextBox.setBackground(new Color(238,238,238));
        jobTitleTextBox.setEnabled(false);
        jobTitleTextBox.setBackground(new Color(238,238,238));
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
        phone2NumberTextBox.setEnabled(false);
        phone2NumberTextBox.setBackground(new Color(238,238,238));
        emailAddressTextBox.setEnabled(false);
        emailAddressTextBox.setBackground(new Color(238,238,238));
        cityTextBox.setEnabled(false);
        cityTextBox.setBackground(new Color(238,238,238));
        zipCodeTextBox.setEnabled(false);
        zipCodeTextBox.setBackground(new Color(238,238,238));
        stateComboBox.setEnabled(false);
        faxNumberTextBox.setEnabled(false);
        faxNumberTextBox.setBackground(new Color(238,238,238));
    }

    private void updatePartyInformation() {
        CaseParty updateParty = new CaseParty();

        updateParty.prefix = prefixComboBox.getSelectedItem() == null ? "" : prefixComboBox.getSelectedItem().toString().trim();
        updateParty.firstName = firstNameTextBox.getText().trim();
        updateParty.middleInitial = middleInitialTextBox.getText().trim();
        updateParty.lastName = lastNameTextBox.getText().trim();
        updateParty.suffix = suffixTextBox.getText().trim();
        updateParty.nameTitle = nameTitleTextBox.getText().trim();
        updateParty.jobTitle = jobTitleTextBox.getText().trim();
        updateParty.companyName = companyTextBox.getText().trim();
        updateParty.address1 = address1TextBox.getText().trim();
        updateParty.address2 = address2TextBox.getText().trim();
        updateParty.address3 = address3TextBox.getText().trim();
        updateParty.city = cityTextBox.getText().trim();
        updateParty.stateCode = stateComboBox.getSelectedItem().toString().trim();
        updateParty.zipcode = zipCodeTextBox.getText().trim();
        updateParty.phone1 = NumberFormatService.convertPhoneNumberToString(phoneNumberTextBox.getText().trim()).length() >= 10 ? NumberFormatService.convertPhoneNumberToString(phoneNumberTextBox.getText().trim()) : null;
        updateParty.phone2 = NumberFormatService.convertPhoneNumberToString(phone2NumberTextBox.getText().trim()).length() >= 10 ? NumberFormatService.convertPhoneNumberToString(phone2NumberTextBox.getText().trim()) : null;
        updateParty.fax = NumberFormatService.convertPhoneNumberToString(faxNumberTextBox.getText().trim()).length() >= 10 ? NumberFormatService.convertPhoneNumberToString(faxNumberTextBox.getText().trim()) : null;
        updateParty.emailAddress = emailAddressTextBox.getText().trim();

        Party.updateParty(updateParty, Integer.valueOf(id));
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
        closeButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        cityTextBox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        stateComboBox = new javax.swing.JComboBox();
        jLabel12 = new javax.swing.JLabel();
        zipCodeTextBox = new javax.swing.JTextField();
        middleInitialTextBox = new javax.swing.JTextField();
        lastNameTextBox = new javax.swing.JTextField();
        prefixComboBox = new javax.swing.JComboBox<>();
        suffixTextBox = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        nameTitleTextBox = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jobTitleTextBox = new javax.swing.JTextField();
        phone2NumberTextBox = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        faxNumberTextBox = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();

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

        jLabel8.setText("Phone 1:");

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

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        updateButton.setText("Update");
        updateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateButtonActionPerformed(evt);
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

        prefixComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Mrs.", "Ms.", "Mr.", "Dr. ", "Hon.", " " }));
        prefixComboBox.setSelectedIndex(5);

        suffixTextBox.setBackground(new java.awt.Color(238, 238, 238));
        suffixTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        suffixTextBox.setEnabled(false);

        jLabel10.setText("Job Title:");

        nameTitleTextBox.setBackground(new java.awt.Color(238, 238, 238));
        nameTitleTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel13.setText("Prof. Designation:");

        jobTitleTextBox.setBackground(new java.awt.Color(238, 238, 238));
        jobTitleTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        phone2NumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        phone2NumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel14.setText("Phone 2:");

        faxNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        faxNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel15.setText("Fax:");

        jButton3.setText("Print Envelope");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel2)
                            .addComponent(jLabel10)
                            .addComponent(jLabel13)
                            .addComponent(jLabel9)
                            .addComponent(jLabel14)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(faxNumberTextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(phone2NumberTextBox)
                            .addComponent(companyTextBox)
                            .addComponent(phoneNumberTextBox)
                            .addComponent(emailAddressTextBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(prefixComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 84, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(firstNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(middleInitialTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lastNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(suffixTextBox))
                            .addComponent(nameTitleTextBox)
                            .addComponent(jobTitleTextBox)
                            .addComponent(address1TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(address2TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(address3TextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cityTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 276, Short.MAX_VALUE)
                                .addGap(12, 12, 12)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(zipCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)))))
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
                    .addComponent(jLabel2)
                    .addComponent(prefixComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(suffixTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jobTitleTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(nameTitleTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(companyTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(address1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(address2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(address3TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(zipCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(cityTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phoneNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(phone2NumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel14))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(faxNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(emailAddressTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(updateButton)
                    .addComponent(jButton3))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        if(closeButton.getText().equals("Cancel")) {
            CancelUpdate cancel = new CancelUpdate(this, true);
            if(!cancel.isReset()) {
            } else {
                loadInformation(id);
                disableAll();
            }
        } else {
            updateStatus = 0;
            dispose();
        }
    }//GEN-LAST:event_closeButtonActionPerformed

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        if(updateButton.getText().equals("Update")) {
            enableAll();
            updateButton.setText("Save");
        } else {
            updatePartyInformation();
            disableAll();
            updateStatus = 1;
            dispose();
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        String envelopeFilePDF = processMailingAddressBookmarks.processSingleEnvelopeInsert(Global.templatePath, "EnvelopeInsert.docx", CaseParty.convertPartyToCasePartyModel(partyInformation));
        FileService.openFileFullPath(new File(System.getProperty("java.io.tmpdir") + envelopeFilePDF));
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address1TextBox;
    private javax.swing.JTextField address2TextBox;
    private javax.swing.JTextField address3TextBox;
    private javax.swing.JTextField cityTextBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField companyTextBox;
    private javax.swing.JTextField emailAddressTextBox;
    private javax.swing.JTextField faxNumberTextBox;
    private javax.swing.JTextField firstNameTextBox;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jobTitleTextBox;
    private javax.swing.JTextField lastNameTextBox;
    private javax.swing.JTextField middleInitialTextBox;
    private javax.swing.JTextField nameTitleTextBox;
    private javax.swing.JTextField phone2NumberTextBox;
    private javax.swing.JTextField phoneNumberTextBox;
    private javax.swing.JComboBox<String> prefixComboBox;
    private javax.swing.JComboBox stateComboBox;
    private javax.swing.JTextField suffixTextBox;
    private javax.swing.JButton updateButton;
    private javax.swing.JTextField zipCodeTextBox;
    // End of variables declaration//GEN-END:variables
}
