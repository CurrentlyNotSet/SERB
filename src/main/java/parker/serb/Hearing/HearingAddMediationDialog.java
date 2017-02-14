/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.Hearing;

import com.alee.extended.date.WebCalendar;
import com.alee.utils.swing.Customizer;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.HearingOutcome;
import parker.serb.sql.HearingsMediation;
import parker.serb.sql.User;

/**
 *
 * @author parkerjohnston
 */
public class HearingAddMediationDialog extends javax.swing.JDialog {

    String id = "";
    /**
     * Creates new form REPAddMediationDialog
     * @param parent
     * @param modal
     */
    public HearingAddMediationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addListeners();
        loadALJComboBox();
        loadFinalResultComboBox();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    public void loadFinalResultComboBox() {
        outcomeComboBox.removeAllItems();
        
        outcomeComboBox.addItem("");
        
        List<HearingOutcome> userList = HearingOutcome.loadOutcomesByType("OUT");
        
        for (HearingOutcome user : userList) {
            outcomeComboBox.addItem(user.description);
        }
        
        outcomeComboBox.setSelectedItem("");
    }
    
    private void addListeners() {
        dateAssignedTextBox.getDocument().addDocumentListener(new DocumentListener() {
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
        
        mediationDateTextBox.getDocument().addDocumentListener(new DocumentListener() {
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
    
    private void loadALJComboBox() {
        mediatorComboBox.removeAllItems();
        mediatorComboBox.addItem("");
        
        List userList = User.loadSectionDropDowns("ALJ");
        
        for (Object user : userList) {
            mediatorComboBox.addItem((String) user);
        }
    }
    
    private void enableSaveButton() {
        if(mediatorComboBox.getSelectedItem() != null) {
            if(pcPreDComboBox.getSelectedItem().toString().trim().equals("") ||
                mediatorComboBox.getSelectedItem().toString().equals("") ||
                dateAssignedTextBox.getText().equals("") ||
                mediationDateTextBox.getText().equals("")) 
            {
                saveButton.setEnabled(false);
            } else {
                saveButton.setEnabled(true);
            }
        } else {
            saveButton.setEnabled(false);
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

        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        mediatorComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        pcPreDComboBox = new javax.swing.JComboBox<>();
        dateAssignedTextBox = new com.alee.extended.date.WebDateField();
        mediationDateTextBox = new com.alee.extended.date.WebDateField();
        jLabel9 = new javax.swing.JLabel();
        outcomeComboBox = new javax.swing.JComboBox<>();

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Mediation");

        jLabel3.setText("Mediator:");

        mediatorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM", " " }));
        mediatorComboBox.setSelectedIndex(2);
        mediatorComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                mediatorComboBoxActionPerformed(evt);
            }
        });

        jLabel5.setText("Date Assigned:");

        jLabel6.setText("Mediation Date:");

        saveButton.setText("Save");
        saveButton.setEnabled(false);
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel8.setText("PC / Pre-D:");

        pcPreDComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "PC", "Pre-D", " " }));
        pcPreDComboBox.setSelectedIndex(2);
        pcPreDComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pcPreDComboBoxActionPerformed(evt);
            }
        });

        dateAssignedTextBox.setEditable(false);
        dateAssignedTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        dateAssignedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dateAssignedTextBox.setDateFormat(Global.mmddyyyy);

        dateAssignedTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );

            mediationDateTextBox.setEditable(false);
            mediationDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
            mediationDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            mediationDateTextBox.setDateFormat(Global.mmddyyyy);

            mediationDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );

                jLabel9.setText("Outcome:");

                outcomeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Settled", "Not Settled", " " }));
                outcomeComboBox.setSelectedIndex(2);
                outcomeComboBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        outcomeComboBoxActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel9))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(pcPreDComboBox, 0, 244, Short.MAX_VALUE)
                                    .addComponent(mediatorComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateAssignedTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(mediationDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(outcomeComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(pcPreDComboBox))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(mediatorComboBox)
                            .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(dateAssignedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(mediationDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(outcomeComboBox))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(saveButton)
                            .addComponent(jButton2))
                        .addContainerGap())
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        HearingsMediation.addMediation(
                pcPreDComboBox.getSelectedItem().toString(),
                mediatorComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(mediatorComboBox.getSelectedItem().toString()),
                dateAssignedTextBox.getText().trim(),
                mediationDateTextBox.getText().trim(),
                outcomeComboBox.getSelectedItem().toString());

        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void pcPreDComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pcPreDComboBoxActionPerformed
        enableSaveButton();
    }//GEN-LAST:event_pcPreDComboBoxActionPerformed

    private void outcomeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_outcomeComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_outcomeComboBoxActionPerformed

    private void mediatorComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_mediatorComboBoxActionPerformed
        enableSaveButton();
    }//GEN-LAST:event_mediatorComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.extended.date.WebDateField dateAssignedTextBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JTextField jTextField3;
    private com.alee.extended.date.WebDateField mediationDateTextBox;
    private javax.swing.JComboBox<String> mediatorComboBox;
    private javax.swing.JComboBox<String> outcomeComboBox;
    private javax.swing.JComboBox<String> pcPreDComboBox;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
