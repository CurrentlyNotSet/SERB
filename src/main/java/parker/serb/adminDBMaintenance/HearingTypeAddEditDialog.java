/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import com.alee.laf.optionpane.WebOptionPane;
import parker.serb.Global;
import parker.serb.sql.HearingType;

/**
 *
 * @author Andrew
 */
public class HearingTypeAddEditDialog extends javax.swing.JDialog {

    private int ID;
    private HearingType item;
    
    /**
     * Creates new form HearingTypeAddEditDialog
     * @param parent
     * @param modal
     * @param itemIDpassed
     */
    public HearingTypeAddEditDialog(java.awt.Frame parent, boolean modal, int itemIDpassed) {
        super(parent, modal);
        initComponents();
        setDefaults(itemIDpassed);
    }

    private void setDefaults(int itemIDpassed) {
        ID = itemIDpassed;
        loadSectionComboBox();
        if (ID > 0) {
            titleLabel.setText("Edit Hearing Type");
            editButton.setText("Save");
            loadInformation();
        } else {
            titleLabel.setText("Add Hearing Type");
            editButton.setText("Add");
            editButton.setEnabled(false);
            item = new HearingType();
        }
        this.setLocationRelativeTo(Global.root);
        this.setVisible(true);
    }
        
    private void loadSectionComboBox(){
        sectionComboBox.addItem("");
        sectionComboBox.addItem("CMDS");
        sectionComboBox.addItem("CSC");
        sectionComboBox.addItem("Hearings");
        sectionComboBox.addItem("MED");
        sectionComboBox.addItem("ORG");
        sectionComboBox.addItem("REP");
        sectionComboBox.addItem("ULP");
    }
    
    private void loadInformation() {
        item = HearingType.getHearingTypeByID(ID);
        
        sectionComboBox.setSelectedItem(item.section);
        typeTextField.setText(item.hearingType);
        descriptionTextField.setText(item.hearingDescription);
    }
    
    private void saveInformation() {
        item.id = ID;
        item.section = sectionComboBox.getSelectedItem().toString().trim();
        item.hearingType = typeTextField.getText().trim();
        item.hearingDescription = descriptionTextField.getText().trim();
                       
        if (ID > 0){
            HearingType.updateHearingType(item);
        } else {
            HearingType.createHearingType(item);
        }
    }

    private void checkButton(){
        if (sectionComboBox.getSelectedItem().toString().trim().equals("") || 
                typeTextField.getText().trim().equals("") || 
                descriptionTextField.getText().trim().equals("")){
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
        jLabel4 = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        typeTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        sectionComboBox = new javax.swing.JComboBox<>();

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

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Section:");

        editButton.setText("<<EDIT>>");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Type:");

        typeTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        typeTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        typeTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                typeTextFieldCaretUpdate(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Description:");

        descriptionTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        descriptionTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        descriptionTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                descriptionTextFieldCaretUpdate(evt);
            }
        });

        sectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionComboBoxActionPerformed(evt);
            }
        });

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
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(sectionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 38, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(sectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

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

    private void typeTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_typeTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_typeTextFieldCaretUpdate

    private void descriptionTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_descriptionTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_descriptionTextFieldCaretUpdate

    private void sectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionComboBoxActionPerformed
        checkButton();
    }//GEN-LAST:event_sectionComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox<String> sectionComboBox;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField typeTextField;
    // End of variables declaration//GEN-END:variables
}