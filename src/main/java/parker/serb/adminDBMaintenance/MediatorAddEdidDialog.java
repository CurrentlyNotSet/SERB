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
import parker.serb.sql.Mediator;
import parker.serb.util.EmailValidation;

/**
 *
 * @author Andrew
 */
public class MediatorAddEdidDialog extends javax.swing.JDialog {

    private int ID;
    private Mediator item;

    /**
     * Creates new form MediatorAddEdidDialog
     * @param parent
     * @param modal
     * @param itemIDpassed
     */
    public MediatorAddEdidDialog(java.awt.Frame parent, boolean modal, int itemIDpassed) {
        super(parent, modal);
        initComponents();
        addListeners();
        setDefaults(itemIDpassed);
    }

    private void addListeners() {
        EmailTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                editButton.setEnabled(EmailValidation.validEmail(EmailTextBox.getText().trim()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                editButton.setEnabled(EmailValidation.validEmail(EmailTextBox.getText().trim()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                editButton.setEnabled(EmailValidation.validEmail(EmailTextBox.getText().trim()));
            }
        });
    }

    private void setDefaults(int itemIDpassed) {
        ID = itemIDpassed;
        if (ID > 0) {
            titleLabel.setText("Edit Mediator");
            editButton.setText("Save");
            loadInformation();
        } else {
            titleLabel.setText("Add Mediator");
            editButton.setText("Add");
            editButton.setEnabled(false);
            item = new Mediator();
        }
        this.setLocationRelativeTo(Global.root);
        this.setVisible(true);
    }

    private void loadInformation() {
        item = Mediator.getMediatorByID(ID);

        EmailTextBox.setText(item.email);
        FirstNameTextField.setText(item.firstName);
        MiddleNameTextField.setText(item.middleName);
        LastNameTextField.setText(item.lastName);
        PhoneTextBox.setText(item.phone);

        //Type ComboBox Selection
        switch (item.type) {
            case "State":
                TypeComboBox.setSelectedItem("State");
                break;
            case "FMCS":
                TypeComboBox.setSelectedItem("FMCS");
                break;
            default:
                TypeComboBox.setSelectedItem("");
                break;
        }
    }

    private void saveInformation() {
        item.firstName = FirstNameTextField.getText().trim();
        item.middleName = MiddleNameTextField.getText().trim();
        item.lastName = LastNameTextField.getText().trim();
        item.email = EmailTextBox.getText().trim();
        item.phone = PhoneTextBox.getText().trim();
        item.id = ID;

        if (null != TypeComboBox.getSelectedItem().toString().trim()) {
            switch (TypeComboBox.getSelectedItem().toString().trim()) {
                case "State":
                    item.type = "State";
                    break;
                case "FMCS":
                    item.type = "FMCS";
                    break;
                default:
                    item.type = "";
                    break;
            }
        }
        if (ID > 0) {
            Mediator.updateMediator(item);
        } else {
            Mediator.createMediator(item);
        }
    }

    private void checkButton(){
        if (FirstNameTextField.getText().trim().equals("") ||
                LastNameTextField.getText().trim().equals("") ||
                TypeComboBox.getSelectedItem().toString().trim().equals("")){
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
        TypeComboBox = new javax.swing.JComboBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        FirstNameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LastNameTextField = new javax.swing.JTextField();
        MiddleNameTextField = new javax.swing.JTextField();

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

        TypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { " ", "FMCS", "State" }));
        TypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TypeComboBoxActionPerformed(evt);
            }
        });

        jLabel9.setText("Type:");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(MiddleNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FirstNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EmailTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TypeComboBox, 0, 300, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LastNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PhoneTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 247, Short.MAX_VALUE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel11, jLabel2, jLabel4, jLabel7, jLabel8, jLabel9});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(TypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(EmailTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(PhoneTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {EmailTextBox, FirstNameTextField, LastNameTextField, MiddleNameTextField, PhoneTextBox, TypeComboBox, jLabel11, jLabel2, jLabel4, jLabel7, jLabel8, jLabel9});

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

    private void TypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TypeComboBoxActionPerformed
        checkButton();
    }//GEN-LAST:event_TypeComboBoxActionPerformed

    private void FirstNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_FirstNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_FirstNameTextFieldCaretUpdate

    private void LastNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_LastNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_LastNameTextFieldCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EmailTextBox;
    private javax.swing.JTextField FirstNameTextField;
    private javax.swing.JTextField LastNameTextField;
    private javax.swing.JTextField MiddleNameTextField;
    private javax.swing.JTextField PhoneTextBox;
    private javax.swing.JComboBox TypeComboBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
