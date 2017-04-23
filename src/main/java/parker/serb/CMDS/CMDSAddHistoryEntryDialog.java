/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import com.alee.extended.date.WebCalendar;
import com.alee.utils.swing.Customizer;
import java.io.File;
import java.util.Date;
import java.util.List;
import javax.swing.JFileChooser;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.CMDSHistoryCategory;
import parker.serb.sql.CMDSHistoryDescription;

/**
 *
 * @author parkerjohnston
 */
public class CMDSAddHistoryEntryDialog extends javax.swing.JDialog {

    /**
     * Creates new form CMDSAddHistoryEntryDialog
     * @param parent
     * @param modal
     */
    public CMDSAddHistoryEntryDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addListeners();
        entryDateTextBox.setText(Global.mmddyyyy.format(new Date()));
        loadMailTypeComboBox();
        loadEntryTypeComboBox();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addListeners() {
        entryDateTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableSaveButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableSaveButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableSaveButton();
            }
        });
    }

    private void enableSaveButton() {
        if(entryDateTextBox.getText().equals("")
                || mailTypeComboBox.getSelectedItem().toString().equals("")
                || entryTypeComboBox.getSelectedItem().toString().equals("")
                || entryDescriptionComboBox.getSelectedItem() == null) {
            saveButton.setEnabled(false);
        } else {
            if(entryDescriptionComboBox.getSelectedItem().toString().equals("")) {
                saveButton.setEnabled(false);
            } else {
                saveButton.setEnabled(true);
            }
        }
    }

    private void loadMailTypeComboBox() {
        mailTypeComboBox.removeAllItems();

        mailTypeComboBox.addItem("");
        mailTypeComboBox.addItem("I");
        mailTypeComboBox.addItem("N");
        mailTypeComboBox.addItem("O");
    }

    private void loadEntryTypeComboBox() {
        entryTypeComboBox.removeAllItems();

        entryTypeComboBox.addItem("");

        List<CMDSHistoryCategory> entryTypes = CMDSHistoryCategory.loadActiveCMDSHistoryDescriptions();

        for(int i = 0; i < entryTypes.size(); i++) {
            entryTypeComboBox.addItem(entryTypes.get(i).entryType + " - " + entryTypes.get(i).description);
        }
    }

    private void loadEntryDescriptionComboBox() {
        entryDescriptionComboBox.removeAllItems();

        List<CMDSHistoryDescription> entryTypes = CMDSHistoryDescription.loadAllStatusTypes(entryTypeComboBox.getSelectedItem().toString().split("-")[0].trim());

        for(int i = 0; i < entryTypes.size(); i++) {
            entryDescriptionComboBox.addItem(entryTypes.get(i).description);
        }
    }

    private String partyName() {
        String party = "";

        if (appellantButton.isSelected()) {
            party = "Appellant";
        } else if(appelleeButton.isSelected()) {
            party = "Appellee";
        } else {
            party = "";
        }

        return party;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        buttonGroup2 = new javax.swing.ButtonGroup();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        entryDateTextBox = new com.alee.extended.date.WebDateField();
        jLabel3 = new javax.swing.JLabel();
        mailTypeComboBox = new javax.swing.JComboBox<>();
        originalButton = new javax.swing.JRadioButton();
        faxedButton = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        entryTypeComboBox = new javax.swing.JComboBox<>();
        entryDescriptionComboBox = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        extraTextBox = new javax.swing.JTextField();
        noneButton = new javax.swing.JRadioButton();
        appellantButton = new javax.swing.JRadioButton();
        appelleeButton = new javax.swing.JRadioButton();
        jLabel7 = new javax.swing.JLabel();
        documnetLinkTextBox = new javax.swing.JTextField();
        saveButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add History Entry");

        jLabel2.setText("Entry Date:");

        entryDateTextBox.setEditable(false);
        entryDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        entryDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        entryDateTextBox.setDateFormat(Global.mmddyyyy);

        entryDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );

            jLabel3.setText("Mail Type:");

            mailTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
            mailTypeComboBox.addPropertyChangeListener(new java.beans.PropertyChangeListener() {
                public void propertyChange(java.beans.PropertyChangeEvent evt) {
                    mailTypeComboBoxPropertyChange(evt);
                }
            });

            buttonGroup1.add(originalButton);
            originalButton.setSelected(true);
            originalButton.setText("Original");

            buttonGroup1.add(faxedButton);
            faxedButton.setText("Faxed");

            jLabel4.setText("Entry Type:");

            jLabel5.setText("Entry Description:");

            entryTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
            entryTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    entryTypeComboBoxActionPerformed(evt);
                }
            });

            entryDescriptionComboBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    entryDescriptionComboBoxActionPerformed(evt);
                }
            });

            jLabel6.setText("Extra Text:");

            buttonGroup2.add(noneButton);
            noneButton.setSelected(true);
            noneButton.setText("None");

            buttonGroup2.add(appellantButton);
            appellantButton.setText("Appellant");

            buttonGroup2.add(appelleeButton);
            appelleeButton.setText("Appellee");

            jLabel7.setText("Document Link:");

            documnetLinkTextBox.setEditable(false);
            documnetLinkTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    documnetLinkTextBoxMouseClicked(evt);
                }
            });

            saveButton.setText("Save");
            saveButton.setEnabled(false);
            saveButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    saveButtonActionPerformed(evt);
                }
            });

            cancelButton.setText("Cancel");
            cancelButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cancelButtonActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(entryDescriptionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(extraTextBox)
                                .addComponent(documnetLinkTextBox)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(mailTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(originalButton)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                    .addComponent(faxedButton)
                                    .addGap(158, 158, 158))
                                .addComponent(entryTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(entryDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(noneButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(appellantButton)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(appelleeButton)))
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(entryDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(mailTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(originalButton)
                            .addComponent(faxedButton)))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(entryTypeComboBox)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(entryDescriptionComboBox)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(extraTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(noneButton)
                        .addComponent(appellantButton)
                        .addComponent(appelleeButton))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(documnetLinkTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(saveButton)
                        .addComponent(cancelButton))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void mailTypeComboBoxPropertyChange(java.beans.PropertyChangeEvent evt) {//GEN-FIRST:event_mailTypeComboBoxPropertyChange
        enableSaveButton();
    }//GEN-LAST:event_mailTypeComboBoxPropertyChange

    private void entryTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryTypeComboBoxActionPerformed
        if(entryTypeComboBox.getSelectedItem() != null) {
            if(entryTypeComboBox.getSelectedItem().toString().equals("")) {
                entryDescriptionComboBox.removeAllItems();
                entryDescriptionComboBox.setEnabled(false);
            } else {
                loadEntryDescriptionComboBox();
                entryDescriptionComboBox.setEnabled(true);
            }
            enableSaveButton();
        }
    }//GEN-LAST:event_entryTypeComboBoxActionPerformed

    private void entryDescriptionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_entryDescriptionComboBoxActionPerformed
        if(entryDescriptionComboBox.getSelectedItem() != null) {
            enableSaveButton();
        }
    }//GEN-LAST:event_entryDescriptionComboBoxActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        String direction = "";
        if (mailTypeComboBox.getSelectedItem().toString().equals("I")){
            direction = "IN - ";
        } else if (mailTypeComboBox.getSelectedItem().toString().equals("O")){
            direction = "OUT - ";
        }

        CMDSCaseHistoryEntryTypes.updateCaseHistory(
            entryTypeComboBox.getSelectedItem().toString().split("-")[0].trim(),
            entryDescriptionComboBox.getSelectedItem().toString(),
            extraTextBox.getText(),
            partyName(),
            originalButton.isSelected() ? "" : "Faxed",
            entryDateTextBox.getText().trim(),
            this,
            documnetLinkTextBox.getText().trim(),
            direction
        );

        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void documnetLinkTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_documnetLinkTextBoxMouseClicked
        if(evt.getClickCount() == 2) {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));
            int result = fileChooser.showOpenDialog(this);
            if (result == JFileChooser.APPROVE_OPTION) {
                File selectedFile = fileChooser.getSelectedFile();
                documnetLinkTextBox.setText(selectedFile.getAbsolutePath());
            } else {
               documnetLinkTextBox.setText("");
            }
        }
    }//GEN-LAST:event_documnetLinkTextBoxMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JRadioButton appellantButton;
    private javax.swing.JRadioButton appelleeButton;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.ButtonGroup buttonGroup2;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField documnetLinkTextBox;
    private com.alee.extended.date.WebDateField entryDateTextBox;
    private javax.swing.JComboBox<String> entryDescriptionComboBox;
    private javax.swing.JComboBox<String> entryTypeComboBox;
    private javax.swing.JTextField extraTextBox;
    private javax.swing.JRadioButton faxedButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox<String> mailTypeComboBox;
    private javax.swing.JRadioButton noneButton;
    private javax.swing.JRadioButton originalButton;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
