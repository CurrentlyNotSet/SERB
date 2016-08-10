/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import parker.serb.REP.*;
import parker.serb.relatedcase.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.CaseValidation;
import parker.serb.sql.REPElectionMultiCase;
import parker.serb.sql.RelatedCase;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class MEDAddRelatedCaseDialog extends javax.swing.JDialog {

    String relatedCase = "";
    
    /**
     * Creates new form AddNewRelatedCase
     */
    public MEDAddRelatedCaseDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addButton.setEnabled(false);
        caseNotFoundLabel.setText("");
        addListeners();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void addListeners() {
        caseNumberTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableAddButton();    
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableAddButton(); 
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableAddButton(); 
            }
        });
    }
    
    private void enableAddButton() {
        caseNotFoundLabel.setText("");
        
        String[] parsedCaseNumber = caseNumberTextBox.getText().trim().split("-");
        
        if(caseNumberTextBox.getText().equals("") ||
                parsedCaseNumber.length < 4) {
            addButton.setEnabled(false);
        } else {
            addButton.setEnabled(true);
        }
    }

    public String getRelatedCase() {
        return relatedCase;
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
        caseNumberTextBox = new javax.swing.JTextField();
        addButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        caseNotFoundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Related Case");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Please enter the case number");

        caseNumberTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);

        addButton.setText("Add");
        addButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        caseNotFoundLabel.setForeground(new java.awt.Color(255, 0, 51));
        caseNotFoundLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        caseNotFoundLabel.setText("Case Number Not Found!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 297, Short.MAX_VALUE)
                    .addComponent(caseNumberTextBox)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(addButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(caseNotFoundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caseNotFoundLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(addButton)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void addButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addButtonActionPerformed
        if(caseNumberTextBox.getText().trim().length() == 16) {
            if(caseNumberTextBox.getText().trim().toUpperCase().equals(NumberFormatService.generateFullCaseNumber())) {
                caseNotFoundLabel.setText("Current case can't be added");
            }
            else if(CaseValidation.validateCaseNumber(caseNumberTextBox.getText().trim().toUpperCase())) {
                relatedCase = caseNumberTextBox.getText().trim().toUpperCase();
//                RelatedCase.addNewMultiCase(caseNumberTextBox.getText().trim().toUpperCase());
//                dispose();    
                setVisible(false);
            }
            else {
                caseNotFoundLabel.setText("Case Number Not Found");
            }
        } else {
            caseNotFoundLabel.setText("Invalid Case Number");
        }
        
        
    }//GEN-LAST:event_addButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addButton;
    private javax.swing.JLabel caseNotFoundLabel;
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    // End of variables declaration//GEN-END:variables
}
