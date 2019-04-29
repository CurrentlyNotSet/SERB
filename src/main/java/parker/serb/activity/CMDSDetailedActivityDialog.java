/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.activity;

import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.ActivityType;
import parker.serb.sql.Audit;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CMDSHistoryCategory;
import parker.serb.sql.CMDSHistoryDescription;
import parker.serb.sql.User;
import parker.serb.util.CancelUpdate;
import parker.serb.util.FileService;

/**
 *
 * @author parkerjohnston
 */
public class CMDSDetailedActivityDialog extends javax.swing.JDialog {

    String fileName;
    Activity activityInfo;
    Activity updatedActivity = new Activity();
    String passedID;
    String passedUser;
    /**
     * Creates new form DetailedActivityDialog
     * @param parent
     * @param modal
     * @param id
     * @param userName
     */
    public CMDSDetailedActivityDialog(java.awt.Frame parent, boolean modal, String id, String userName) {
        super(parent, modal);
        initComponents();
        passedID = id;
        passedUser = userName;
        updateInventoryStatusLineButton.setVisible(false);
        loadComboBoxes();
        enableInputs(false);
        loadInformation(id, userName);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void loadComboBoxes() {
        loadToComboBox();
        loadTypeComboBox();
        loadEntryDescriptionComboBox();
    }

    private void loadToComboBox() {
        List userList = User.loadSectionDropDowns("CMDS");

        toComboBox.setMaximumRowCount(6);
        toComboBox.removeAllItems();
        toComboBox.addItem("");

        for(int i = 0; i < userList.size(); i++) {
            toComboBox.addItem(userList.get(i).toString());
        }
    }

    private void loadTypeComboBox() {
        List<CMDSHistoryCategory> entryTypes = CMDSHistoryCategory.loadActiveCMDSHistoryDescriptions();

        typeComboBox.setMaximumRowCount(10);
        typeComboBox.removeAllItems();
        typeComboBox.addItem("");

        for(int i = 0; i < entryTypes.size(); i++) {
            CMDSHistoryCategory item = entryTypes.get(i);
            typeComboBox.addItem(item.entryType + " - " + item.description);
        }
    }

    private void loadEntryDescriptionComboBox() {
        descriptionComboBox.removeAllItems();

        List<CMDSHistoryDescription> entryTypes = CMDSHistoryDescription.loadAllStatusTypes(typeComboBox.getSelectedItem().toString().split("-")[0].trim());

        for(int i = 0; i < entryTypes.size(); i++) {
            descriptionComboBox.addItem(entryTypes.get(i).description);
        }
    }

    private void loadInformation(String id, String userName) {
        activityInfo = Activity.loadActivityByID(id, userName);

        dateTextBox.setText(activityInfo.date);
        actionTextBox.setText(activityInfo.action);
        fromTextBox.setText(activityInfo.from);

        if(((DefaultComboBoxModel)toComboBox.getModel()).getIndexOf(activityInfo.to) < 0) {
            toComboBox.addItem(activityInfo.to );
        }

        toComboBox.setSelectedItem(activityInfo.to);

        String typeLoad = "";
        String descriptionLoad = "";

        if (activityInfo.type != null) {
            String[] activtyTypeSplit = activityInfo.type.split("-", 2);

            if (activtyTypeSplit.length == 1) {
                typeLoad = getCategory(activtyTypeSplit[0].trim());
            }
            if (activtyTypeSplit.length == 2) {
                typeLoad = getCategory(activtyTypeSplit[0].trim());
                descriptionLoad = activtyTypeSplit[1].trim();
            }
        }

        typeComboBox.setSelectedItem(typeLoad);
        descriptionComboBox.setSelectedItem(descriptionLoad);
        commentTextArea.setText(activityInfo.comment == null ? "" : activityInfo.comment.trim());

        if(activityInfo.fileName == null) {
            viewFileButton.setVisible(false);
        } else switch (activityInfo.fileName) {
            case "":
                viewFileButton.setVisible(false);
                break;
            default:
                fileName = activityInfo.fileName;
                break;
        }
    }

    private String getCategory(String type) {
        return CMDSHistoryCategory.getCategotyByLetter(type);
    }

    private void enableInputs(boolean value) {
        descriptionComboBox.setEnabled(value);
    }

    private void updateAction() {
        updatedActivity.id = activityInfo.id;

        if(activityInfo.type == null ||
                typeComboBox.getSelectedItem().equals("") ||
                descriptionComboBox.getSelectedItem().equals("")
                ) {
            updatedActivity.action = actionTextBox.getText().trim();
        } else {
            String[] activtyTypeSplit = activityInfo.type.split("-", 2);
            if (activtyTypeSplit.length == 2) {
                updatedActivity.action = actionTextBox.getText().trim().replace(activtyTypeSplit[1].trim(), descriptionComboBox.getSelectedItem().toString().trim());
            }
        }

        updatedActivity.from = fromTextBox.getText().equals("") ? null
                : fromTextBox.getText();
        updatedActivity.to = toComboBox.getSelectedItem() == null ? null
                : toComboBox.getSelectedItem().toString().equals("") ? null :toComboBox.getSelectedItem().toString();

        if(!typeComboBox.getSelectedItem().toString().equals("") &&
            !descriptionComboBox.getSelectedItem().toString().equals("")) {
            updatedActivity.type = typeComboBox.getSelectedItem().toString().split("-")[0].trim() + " - " + descriptionComboBox.getSelectedItem().toString().trim();
        } else {
            updatedActivity.type = null;
        }

        updatedActivity.comment = commentTextArea.getText().trim().equals("") ? null : commentTextArea.getText().trim();

        Activity.updateActivtyEntry(updatedActivity);
    }

    private void updateFileName() {
        if(Global.activeSection.equals("CMDS")) {
            updatedActivity.fileName = activityInfo.fileName;
        } else if(activityInfo.type != null) {
            if(!activityInfo.type.equals(typeComboBox.getSelectedItem().toString())) {
                if(activityInfo.fileName != null) {
                    FileService.renameActivtyFile(activityInfo.fileName, typeComboBox.getSelectedItem().toString());

                    updatedActivity.fileName = activityInfo.fileName.split("_")[0] + "_"
                            + ActivityType.getTypeAbbrv(typeComboBox.getSelectedItem().toString()).replace(" ", "_")
                            + "." + activityInfo.fileName.split("\\.")[1];
                    fileName = updatedActivity.fileName;
                }
            } else {
                updatedActivity.fileName = activityInfo.fileName;
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
        dateTextBox = new javax.swing.JTextField();
        actionTextBox = new javax.swing.JTextField();
        fromTextBox = new javax.swing.JTextField();
        toComboBox = new javax.swing.JComboBox<>();
        typeComboBox = new javax.swing.JComboBox<>();
        viewFileButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        updateButton = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        descriptionComboBox = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentTextArea = new javax.swing.JTextArea();
        updateInventoryStatusLineButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Activity Detail");

        jLabel2.setText("Date:");

        jLabel3.setText("Action:");

        jLabel4.setText("From:");

        jLabel5.setText("To:");

        jLabel6.setText("Category:");

        dateTextBox.setEditable(false);
        dateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dateTextBox.setEnabled(false);

        actionTextBox.setEditable(false);
        actionTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        actionTextBox.setEnabled(false);
        actionTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                actionTextBoxActionPerformed(evt);
            }
        });

        fromTextBox.setEditable(false);
        fromTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fromTextBox.setEnabled(false);

        toComboBox.setEnabled(false);

        typeComboBox.setEnabled(false);
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });

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

        jLabel7.setText("Description:");

        descriptionComboBox.setEnabled(false);

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("Comment:");

        commentTextArea.setColumns(20);
        commentTextArea.setRows(5);
        commentTextArea.setEnabled(false);
        jScrollPane1.setViewportView(commentTextArea);

        updateInventoryStatusLineButton.setText("Update Inventory Status Line");
        updateInventoryStatusLineButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                updateInventoryStatusLineButtonActionPerformed(evt);
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
                                .addGap(20, 20, 20)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(dateTextBox)
                                    .addComponent(actionTextBox)
                                    .addComponent(fromTextBox)
                                    .addComponent(toComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(typeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(viewFileButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 347, Short.MAX_VALUE)
                                    .addComponent(descriptionComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1)))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(closeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(updateInventoryStatusLineButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionComboBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 140, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(viewFileButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 39, Short.MAX_VALUE)
                .addComponent(updateInventoryStatusLineButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
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
            enableInputs(!typeComboBox.getSelectedItem().toString().equals(""));
            commentTextArea.setEnabled(true);
            updateInventoryStatusLineButton.setVisible(true);
            updateButton.setText("Save");
            closeButton.setText("Cancel");
        } else if(updateButton.getText().equals("Save")) {
            Audit.addAuditEntry("Clicked Save Button for Activity: " + passedID);
            updateFileName();
            updateAction();
            enableInputs(false);
            commentTextArea.setEnabled(false);
            updateInventoryStatusLineButton.setVisible(false);
            loadInformation(passedID, passedUser);
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
                loadInformation(passedID, passedUser);
                enableInputs(false);
                updateInventoryStatusLineButton.setVisible(false);
                updateButton.setText("Update");
                closeButton.setText("Close");
            }
        }
    }//GEN-LAST:event_closeButtonActionPerformed

    private void viewFileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_viewFileButtonActionPerformed
        Audit.addAuditEntry("Opened File for Activity: " + passedID);
        FileService.openFile(fileName);
    }//GEN-LAST:event_viewFileButtonActionPerformed

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        if(typeComboBox.getSelectedItem() != null) {
            if(typeComboBox.getSelectedItem().toString().equals("")) {
                descriptionComboBox.removeAllItems();
                descriptionComboBox.setEnabled(false);
            } else {
                loadEntryDescriptionComboBox();
                descriptionComboBox.setEnabled(false);
            }
        }
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void updateInventoryStatusLineButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateInventoryStatusLineButtonActionPerformed
        try {
            CMDSCase.updateCaseInventoryStatusLines(actionTextBox.getText() + (commentTextArea.getText().trim().equals("") ? "" : " " + commentTextArea.getText().trim()), Global.mmddyyyyhhmma.parse(dateTextBox.getText()));
            Global.root.getcMDSHeaderPanel1().loadHeaderInformation();
            descriptionComboBox.setEnabled(false);
            commentTextArea.setEnabled(false);
            updateInventoryStatusLineButton.setVisible(false);
            updateButton.setText("Update");
            closeButton.setText("Close");
        } catch (ParseException ex) {
            Logger.getLogger(CMDSDetailedActivityDialog.class.getName()).log(Level.SEVERE, null, ex);
        }
    }//GEN-LAST:event_updateInventoryStatusLineButtonActionPerformed

    private void actionTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_actionTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_actionTextBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField actionTextBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JTextArea commentTextArea;
    private javax.swing.JTextField dateTextBox;
    private javax.swing.JComboBox<String> descriptionComboBox;
    private javax.swing.JTextField fromTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> toComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JButton updateButton;
    private javax.swing.JButton updateInventoryStatusLineButton;
    private javax.swing.JButton viewFileButton;
    // End of variables declaration//GEN-END:variables
}
