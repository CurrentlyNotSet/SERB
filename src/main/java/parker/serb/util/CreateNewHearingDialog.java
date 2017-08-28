/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import java.awt.Color;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.sql.HearingCase;
import parker.serb.sql.MEDCase;
import parker.serb.sql.REPCase;
import parker.serb.sql.ULPCase;

/**
 *
 * @author parker
 */
public class CreateNewHearingDialog extends javax.swing.JDialog {

    /**
     * Creates new form CreateNewCaseDialog
     */
    public CreateNewHearingDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadSections();
        addListeners();
        clearCaseLabel();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void loadSections() {
        sectionComboBox.removeAllItems();
        sectionComboBox.addItem("");
        sectionComboBox.addItem("MED");
        sectionComboBox.addItem("REP");
        sectionComboBox.addItem("ULP");
    }
    
    private void clearCaseLabel() {
        jLabel2.setText(" ");
    }
    
    private void addListeners() {
        caseNumberTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                clearCaseLabel();
                if(caseNumberTextBox.getText().split("-").length == 4) {
                    createHearingButton.setEnabled(validateCase());
                } else {
                    createHearingButton.setEnabled(false);
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                clearCaseLabel();
                if(caseNumberTextBox.getText().split("-").length == 4) {
                    createHearingButton.setEnabled(validateCase());
                } else {
                    createHearingButton.setEnabled(false);
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                clearCaseLabel();
                if(caseNumberTextBox.getText().split("-").length == 4) {
                    createHearingButton.setEnabled(validateCase());
                } else {
                    createHearingButton.setEnabled(false);
                }
            }
        });
    }
    
    private boolean validateCase() {
        boolean validCase = false;
        
        switch(sectionComboBox.getSelectedItem().toString()) {
            case "ULP":
                validCase = ULPCase.validateCaseNumber(caseNumberTextBox.getText().trim());
                break;
            case "REP":
                validCase = REPCase.validateCaseNumber(caseNumberTextBox.getText().trim());
                break;
            case "MED":
                validCase = MEDCase.validateCaseNumber(caseNumberTextBox.getText().trim());
                break;
        }
        
        return validCase;
    }
    
    private void createHearing() {
        if(HearingCase.validateCaseNumber(caseNumberTextBox.getText().trim())) {
            jLabel2.setText("Case Already Created!");
            jLabel2.setForeground(Color.red);
        } else {
            HearingCase.createCase(caseNumberTextBox.getText().trim());
            dispose();
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
        jLabel3 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        createHearingButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        sectionComboBox = new javax.swing.JComboBox();
        caseNumberTextBox = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Create New Hearing");

        jLabel3.setText("Section:");

        jLabel6.setText("Case Number:");

        createHearingButton.setText("Create");
        createHearingButton.setEnabled(false);
        createHearingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                createHearingButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        sectionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(38, 38, 38)
                                .addComponent(jLabel3))
                            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(sectionComboBox, 0, 207, Short.MAX_VALUE)
                            .addComponent(caseNumberTextBox)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(createHearingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 15, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(sectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 18, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(createHearingButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void createHearingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_createHearingButtonActionPerformed
        createHearing();    
    }//GEN-LAST:event_createHearingButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JButton createHearingButton;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JComboBox sectionComboBox;
    // End of variables declaration//GEN-END:variables
}
