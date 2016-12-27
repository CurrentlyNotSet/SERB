/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import com.alee.laf.optionpane.WebOptionPane;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.CMDSHistoryCategory;
import parker.serb.sql.CMDSHistoryDescription;

/**
 *
 * @author Andrew
 */
public class CMDSHistoryDescriptionAddEditDialog extends javax.swing.JDialog {

    private int ID;
    private CMDSHistoryDescription item;
    String section = "";
    
    /**
     * Creates new form CMDSStatusTypeAddEditDialog
     * @param parent
     * @param modal
     * @param itemIDpassed
     * @param sectionPassed
     */
    public CMDSHistoryDescriptionAddEditDialog(java.awt.Frame parent, boolean modal, int itemIDpassed, String sectionPassed) {
        super(parent, modal);
        initComponents();
        setDefaults(itemIDpassed, sectionPassed);
    }

    private void setDefaults(int itemIDpassed, String sectionPassed) {
        section = sectionPassed;
        ID = itemIDpassed;
        loadCategories();
        if (ID > 0) {
            titleLabel.setText("Edit History Description");
            editButton.setText("Save");
            loadInformation();
        } else {
            titleLabel.setText("Add History Description");
            editButton.setText("Add");
            editButton.setEnabled(false);
            item = new CMDSHistoryDescription();
        }
        this.setLocationRelativeTo(Global.root);
        this.setVisible(true);
    }
        
    private void loadCategories(){
        List<CMDSHistoryCategory> list = null;
        if (section.equals("CMDS")) {
            list = CMDSHistoryCategory.loadActiveCMDSHistoryDescriptions();
        } else {
            list = CMDSHistoryCategory.loadActiveHearingHistoryDescriptions();
        }        
        
        categoryComboBox.removeAllItems();
        categoryComboBox.addItem("");
        
        for (CMDSHistoryCategory cat : list){
            categoryComboBox.addItem(cat.entryType);
        }
    }
    
    private void loadInformation() {
        if (section.equals("CMDS")){
            item = CMDSHistoryDescription.getCMDSHistoryDescriptionByID(ID);
        } else {
            item = CMDSHistoryDescription.getHearingHistoryDescriptionByID(ID);
        }
        
        if (item.category != null){
            categoryComboBox.setSelectedItem(item.category);
        }
        DescriptionTextField.setText(item.description);
    }
    
    private void saveInformation() {
        item.id = ID;
        item.category = categoryComboBox.getSelectedItem().toString().trim();
        item.description = DescriptionTextField.getText().trim();
                       
        if (ID > 0){
            if (section.equals("CMDS")) {
                CMDSHistoryDescription.updateCMDSHistoryDescription(item);
            } else {
                CMDSHistoryDescription.updateHearingHistoryDescription(item);
            }
        } else {
            if (section.equals("CMDS")) {
                CMDSHistoryDescription.createCMDSHistoryDescription(item);
            } else {
                CMDSHistoryDescription.createHearingHistoryDescription(item);
            }
        }
    }

    private void checkButton(){
        if (categoryComboBox.getSelectedItem().toString().trim().equals("") || 
                DescriptionTextField.getText().trim().equals("")){
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
        DescriptionTextField = new javax.swing.JTextField();
        categoryComboBox = new javax.swing.JComboBox<>();

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
        jLabel4.setText("Category:");

        editButton.setText("<<EDIT>>");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Description:");

        DescriptionTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        DescriptionTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        DescriptionTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                DescriptionTextFieldCaretUpdate(evt);
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
                                .addComponent(categoryComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(DescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(categoryComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(DescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
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

    private void DescriptionTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_DescriptionTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_DescriptionTextFieldCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField DescriptionTextField;
    private javax.swing.JComboBox<String> categoryComboBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
