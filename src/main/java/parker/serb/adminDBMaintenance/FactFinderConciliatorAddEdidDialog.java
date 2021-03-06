/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import com.alee.laf.optionpane.WebOptionPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.FactFinder;
import parker.serb.util.EmailValidation;

/**
 *
 * @author Andrew
 */
public class FactFinderConciliatorAddEdidDialog extends javax.swing.JDialog {

    private int ID;
    private FactFinder item;
    
    /**
     * Creates new form FactFinderConciliatorAddEdidDialog
     * @param parent
     * @param modal
     * @param itemIDpassed
     */
    public FactFinderConciliatorAddEdidDialog(java.awt.Frame parent, boolean modal, int itemIDpassed) {
        super(parent, modal);
        initComponents();
        addListeners();
        setDefaults(itemIDpassed);
    }
    
    private void addListeners() {
        EmailTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                Global.root.getjButton2().setEnabled(EmailValidation.validEmail(EmailTextBox.getText().trim()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                Global.root.getjButton2().setEnabled(EmailValidation.validEmail(EmailTextBox.getText().trim()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                Global.root.getjButton2().setEnabled(EmailValidation.validEmail(EmailTextBox.getText().trim()));
            }
        });
    }

    private void setDefaults(int itemIDpassed) {
        loadStateComboBox();
        ID = itemIDpassed;
        if (ID > 0) {
            titleLabel.setText("Edit Fact Finder / Conciliator");
            editButton.setText("Save");
            loadInformation();
        } else {
            titleLabel.setText("Add Fact Finder / Conciliator");
            editButton.setText("Add");
            editButton.setEnabled(false);
            item = new FactFinder();
        }
        this.setLocationRelativeTo(Global.root);
        this.setVisible(true);
    }
    
    private void loadStateComboBox() {
        stateComboBox.removeAllItems();
        
        for (String state : Global.STATES) {
            stateComboBox.addItem(state);
        }
        stateComboBox.setSelectedItem("OH");
    }
    
    private void loadInformation() {
        item = FactFinder.getFactFinderByID(ID);
                
        Address1TextBox.setText(item.address1);
        Address2TextBox.setText(item.address2);
        Address3TextBox.setText(item.address3);
        EmailTextBox.setText(item.email);
        FirstNameTextField.setText(item.firstName);
        MiddleNameTextField.setText(item.middleName);
        LastNameTextField.setText(item.lastName);
        PhoneTextBox.setText(item.phone);
        cityTextBox.setText(item.city);
        stateComboBox.setSelectedItem(item.state);
        zipCodeTextBox.setText(item.zip);
        BioFileNameTextBox.setText(item.bioFileName);
        
        //Status ComboBox Selection
        switch (item.status) {
            case "F":
                StatusComboBox.setSelectedItem("Fact Finder");
                break;
            case "C":
                StatusComboBox.setSelectedItem("Conciliator & Fact Finder");
                break;
            case "O":
                StatusComboBox.setSelectedItem("Off the list / Unavailable");
                break;
            default:
                StatusComboBox.setSelectedItem("");
                break;
        }
    }
   
    private void saveInformation() {
        item.firstName = FirstNameTextField.getText().trim();
        item.middleName = MiddleNameTextField.getText().trim();
        item.lastName = LastNameTextField.getText().trim();
        item.address1 = Address1TextBox.getText().trim();
        item.address2 = Address2TextBox.getText().trim();
        item.address3 = Address3TextBox.getText().trim();
        item.city = cityTextBox.getText().trim();
        item.state = stateComboBox.getSelectedItem().toString().trim();
        item.zip = zipCodeTextBox.getText().trim();
        item.email = EmailTextBox.getText().trim();
        item.phone = PhoneTextBox.getText().trim();
        item.bioFileName = BioFileNameTextBox.getText().trim();
        item.id = ID;
        
        if (null != StatusComboBox.getSelectedItem().toString().trim()) {
            switch (StatusComboBox.getSelectedItem().toString().trim()) {
                case "Fact Finder":
                    item.status = "F";
                    break;
                case "Conciliator & Fact Finder":
                    item.status = "C";
                    break;
                case "Off the list / Unavailable":
                    item.status = "O";
                    break;
                default:
                    item.status = null;
                    break;
            }
        }
        
        if (ID > 0) {
            FactFinder.updateFactFinder(item);
        } else {
            FactFinder.createFactFinder(item);
        }
    }

    private void checkButton(){
        if (FirstNameTextField.getText().trim().equals("") ||
                LastNameTextField.getText().trim().equals("") ||
                StatusComboBox.getSelectedItem().toString().trim().equals("")){
            editButton.setEnabled(false);
        } else {
            editButton.setEnabled(true);
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

        titleLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        PhoneTextBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        EmailTextBox = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        StatusComboBox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        Address3TextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        Address2TextBox = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        Address1TextBox = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        FirstNameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LastNameTextField = new javax.swing.JTextField();
        MiddleNameTextField = new javax.swing.JTextField();
        zipCodeTextBox = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        stateComboBox = new javax.swing.JComboBox();
        jLabel13 = new javax.swing.JLabel();
        cityTextBox = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        BioFileNameTextBox = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        titleLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("<<TITLE>>");

        closeButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        editButton.setText("<<EDIT>>");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        PhoneTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel8.setText("Phone:");

        EmailTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel7.setText("Email:");

        StatusComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Fact Finder", "Conciliator & Fact Finder", "Off the list / Unavailable" }));
        StatusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StatusComboBoxActionPerformed(evt);
            }
        });

        jLabel9.setText("Status:");

        Address3TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel5.setText("Address 3:");

        Address2TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel10.setText("Address 2:");

        Address1TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel3.setText("Address 1:");

        jLabel2.setText("First Name:");

        FirstNameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        FirstNameTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                FirstNameTextFieldCaretUpdate(evt);
            }
        });

        jLabel4.setText("Middle Name:");

        jLabel11.setText("Last Name:");

        LastNameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        LastNameTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                LastNameTextFieldCaretUpdate(evt);
            }
        });

        MiddleNameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("Zip:");

        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("State:");

        jLabel14.setText("City:");

        BioFileNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel15.setText("Bio File Name:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MiddleNameTextField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FirstNameTextField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EmailTextBox))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(StatusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Address1TextBox)
                                    .addComponent(LastNameTextField)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(Address2TextBox))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PhoneTextBox))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE)
                                    .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(cityTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 149, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(zipCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 86, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(Address3TextBox)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 88, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BioFileNameTextBox)))
                        .addGap(0, 38, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel14, jLabel15, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7, jLabel8, jLabel9});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(StatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(FirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(MiddleNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(LastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(Address1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(Address2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(Address3TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(cityTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel13)
                    .addComponent(stateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12)
                    .addComponent(zipCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EmailTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PhoneTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BioFileNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel15))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 49, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {Address1TextBox, Address2TextBox, Address3TextBox, EmailTextBox, FirstNameTextField, LastNameTextField, MiddleNameTextField, PhoneTextBox, StatusComboBox, cityTextBox, jLabel10, jLabel11, jLabel12, jLabel13, jLabel14, jLabel2, jLabel3, jLabel4, jLabel5, jLabel7, jLabel8, jLabel9, stateComboBox, zipCodeTextBox});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        int answer = WebOptionPane.showConfirmDialog(this, "Are you sure you wish to close this window. Any unsaved information will be lost.", "Cancel", WebOptionPane.YES_NO_OPTION);
        if (answer == WebOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_closeButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        saveInformation();
        this.dispose();
    }//GEN-LAST:event_editButtonActionPerformed

    private void StatusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StatusComboBoxActionPerformed
        checkButton();
    }//GEN-LAST:event_StatusComboBoxActionPerformed

    private void FirstNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_FirstNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_FirstNameTextFieldCaretUpdate

    private void LastNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_LastNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_LastNameTextFieldCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField Address1TextBox;
    private javax.swing.JTextField Address2TextBox;
    private javax.swing.JTextField Address3TextBox;
    private javax.swing.JTextField BioFileNameTextBox;
    private javax.swing.JTextField EmailTextBox;
    private javax.swing.JTextField FirstNameTextField;
    private javax.swing.JTextField LastNameTextField;
    private javax.swing.JTextField MiddleNameTextField;
    private javax.swing.JTextField PhoneTextBox;
    private javax.swing.JComboBox StatusComboBox;
    private javax.swing.JTextField cityTextBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton editButton;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JComboBox stateComboBox;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField zipCodeTextBox;
    // End of variables declaration//GEN-END:variables
}
