/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.activity;

import java.util.List;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.ActivityType;
import parker.serb.sql.Audit;
import parker.serb.sql.User;
import parker.serb.util.CancelUpdate;
import parker.serb.util.FileService;

/**
 *
 * @author parkerjohnston
 */
public class DetailedActivityDialog extends javax.swing.JDialog {

    String fileName;
    Activity orgActivity;
    Activity updatedActivity = new Activity();
    String passedID;
    /**
     * Creates new form DetailedActivityDialog
     */
    public DetailedActivityDialog(java.awt.Frame parent, boolean modal, String id) {
        super(parent, modal);
        initComponents();
        passedID = id;
        displayUpdateButton();
        loadComboBoxes();
        loadInformation(id);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void displayUpdateButton() {
        if(!Global.activeUserRoles.contains("Admin")) {
            updateButton.setVisible(false);
        }
    }
    
    private void loadComboBoxes() {
        loadToComboBox();
        loadTypeComboBox();
    }
    
    private void loadToComboBox() {
        List userList = null;
                
        switch (Global.activeSection) {
            case "ULP":  userList = User.loadSectionDropDowns("ULP"); break;
            case "REP":  userList = User.loadSectionDropDowns("REP"); break;
            case "MED":  userList = User.loadSectionDropDowns("MED"); break;
            case "ORG":  userList = User.loadSectionDropDowns("ORG"); break;
            case "Civil Service Commission":  userList = User.loadSectionDropDowns("CSC"); break;
            case "CMDS":  userList = User.loadSectionDropDowns("CMDS"); break;
            case "Hearings":  userList = User.loadSectionDropDowns("Hearings"); break;
            default: break;
        }
        
        toComboBox.setMaximumRowCount(6);
        
        toComboBox.removeAllItems();
        
        toComboBox.addItem("");
        
        for(int i = 0; i < userList.size(); i++) {
            toComboBox.addItem(userList.get(i).toString());
        }
    }
    
    private void loadTypeComboBox() {
        List typeList = ActivityType.loadAllActivityTypeBySection(Global.activeSection);
        
        typeComboBox.setMaximumRowCount(10);
        
        typeComboBox.removeAllItems();
        
        typeComboBox.addItem("");
        
        for(int i = 0; i < typeList.size(); i++) {
            ActivityType item = (ActivityType) typeList.get(i);
            typeComboBox.addItem(item.descriptionFull);
        }
    }
    
    private void loadInformation(String id) {
        orgActivity = Activity.loadActivityByID(id);
        
        dateTextBox.setText(orgActivity.date);
        actionTextBox.setText(orgActivity.action);
        fromTextBox.setText(orgActivity.from);
        toComboBox.setSelectedItem(orgActivity.to);
        typeComboBox.setSelectedItem(orgActivity.type);
        commentTextArea.setText(orgActivity.comment);
        
        if(orgActivity.fileName == null) {
            viewFileButton.setVisible(false);
        } else {
            fileName = orgActivity.fileName;
        }
    }
    
    private void enableInputs(boolean value) {
        typeComboBox.setEnabled(value);
        commentTextArea.setEditable(value);
    }
    
    private void updateAction() {
        updatedActivity.id = orgActivity.id;
        updatedActivity.action = actionTextBox.getText().trim();
        updatedActivity.comment = commentTextArea.getText();
        updatedActivity.from = fromTextBox.getText();
        updatedActivity.to = toComboBox.getSelectedItem() == null ? "" : toComboBox.getSelectedItem().toString();
        updatedActivity.type = typeComboBox.getSelectedItem().toString();
        
        Activity.updateActivtyEntry(updatedActivity);
    }
    
    private void updateFileName() {
        if(orgActivity.type != null) 
        {
            if(!orgActivity.type.equals(typeComboBox.getSelectedItem().toString())) {
                if(orgActivity.fileName != null) {
                    FileService.renameActivtyFile(orgActivity.fileName, typeComboBox.getSelectedItem().toString());

                    updatedActivity.fileName = orgActivity.fileName.split("_")[0] + "_"
                            + ActivityType.getTypeAbbrv(typeComboBox.getSelectedItem().toString()).replace(" ", "_")
                            + "." + orgActivity.fileName.split("\\.")[1];
                    fileName = updatedActivity.fileName;
                }
            } else {
                updatedActivity.fileName = orgActivity.fileName;
            }
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        dateTextBox = new javax.swing.JTextField();
        actionTextBox = new javax.swing.JTextField();
        fromTextBox = new javax.swing.JTextField();
        toComboBox = new javax.swing.JComboBox<>();
        typeComboBox = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentTextArea = new javax.swing.JTextArea();
        viewFileButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Activity Detail");

        jLabel2.setText("Date:");

        jLabel3.setText("Action:");

        jLabel4.setText("From:");

        jLabel5.setText("To:");

        jLabel6.setText("Type:");

        jLabel7.setText("Comment:");

        dateTextBox.setEditable(false);
        dateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dateTextBox.setEnabled(false);

        actionTextBox.setEditable(false);
        actionTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        actionTextBox.setEnabled(false);

        fromTextBox.setEditable(false);
        fromTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fromTextBox.setEnabled(false);

        toComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        toComboBox.setEnabled(false);

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        typeComboBox.setEnabled(false);

        commentTextArea.setEditable(false);
        commentTextArea.setColumns(20);
        commentTextArea.setLineWrap(true);
        commentTextArea.setRows(5);
        jScrollPane1.setViewportView(commentTextArea);

        viewFileButton.setText("View File");
        viewFileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                viewFileButtonActionPerformed(evt);
            }
        });

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateTextBox)
                                    .addComponent(actionTextBox)
                                    .addComponent(fromTextBox)
                                    .addComponent(toComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 379, Short.MAX_VALUE)
                                    .addComponent(typeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(viewFileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(closeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(actionTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fromTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(toComboBox)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(typeComboBox)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(viewFileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 25, Short.MAX_VALUE)
                .addComponent(updateButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(closeButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        if(updateButton.getText().equals("Update")) {
            Audit.addAuditEntry("Clicked Update Button for Activity: " + passedID);
            enableInputs(true);
            updateButton.setText("Save");
            closeButton.setText("Cancel");
        } else if(updateButton.getText().equals("Save")) {
            Audit.addAuditEntry("Clicked Save Button for Activity: " + passedID);
            updateFileName();
            updateAction();
            enableInputs(false);
            Audit.addAuditEntry("Updated Information for Activity: " + passedID);
            updateButton.setText("Update");
            closeButton.setText("Close");
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        if(closeButton.getText().equals("Close")) {
            Audit.addAuditEntry("Closed Detailed Activity Dialog");
            dispose();
        } else if(closeButton.getText().equals("Cancel")) {
            CancelUpdate cancel = new CancelUpdate(Global.root, true);
            if(!cancel.isReset()) {
            } else {
                Audit.addAuditEntry("Canceled Update of Activity " + passedID);
                loadInformation(passedID);
                enableInputs(false);
                updateButton.setText("Update");
                closeButton.setText("Close");
            }
        }
    }//GEN-LAST:event_closeButtonActionPerformed

    private void viewFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewFileButtonActionPerformed
        Audit.addAuditEntry("Opened File for Activity: " + passedID);
        FileService.openFile(fileName);
    }//GEN-LAST:event_viewFileButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField actionTextBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextArea commentTextArea;
    private javax.swing.JTextField dateTextBox;
    private javax.swing.JTextField fromTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> toComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton viewFileButton;
    // End of variables declaration//GEN-END:variables
}
