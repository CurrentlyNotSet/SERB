/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterGeneration;

import com.alee.extended.date.WebDateField;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.CaseParty;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.StringUtilities;


/**
 *
 * @author parker
 */
public class LetterGeneration extends javax.swing.JDialog {

    SMDSDocuments docToGenerate;
    
    public LetterGeneration(java.awt.Frame parent, boolean modal, SMDSDocuments documentToGeneratePassed) {
        super(parent, modal);
        initComponents();
        loadPanel(documentToGeneratePassed);
        setLocationRelativeTo(parent);
        setVisible(true);   
    }
    
    private void loadPanel(SMDSDocuments documentToGeneratePassed) {
        docToGenerate = documentToGeneratePassed;
        documentLabel.setText(documentToGeneratePassed.description);
        loadPartyTable();
        loadActivityDocumentsTable();
        loadExtraAttachmentTable();        
    }
    
    private void loadPartyTable(){
        DefaultTableModel model = (DefaultTableModel) personTable.getModel();
        model.setRowCount(0);
        
        List<CaseParty> partyList = CaseParty.loadPartiesByCase();
        
        for (CaseParty party : partyList){
            model.addRow(new Object[]{
                party.id,           // ID
                "",                 // TO/CC
                "",                 // Email/Postal
                party.caseRelation,
                StringUtilities.buildCasePartyName(party),  // NAME
                party.emailAddress
            });
        }   
    }
    
    private void loadActivityDocumentsTable(){
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setRowCount(0);
        
        List<Activity> activtyList = Activity.loadActivityDocumentsCyGlobalCase();
        
        for (Activity doc : activtyList){
            model.addRow(new Object[]{
                doc.id,
                false,
                doc.action
            });
        }  
    }
    
    private void loadExtraAttachmentTable(){
        DefaultTableModel model = (DefaultTableModel) additionalDocsTable.getModel();
        model.setRowCount(0); 
        List<SMDSDocuments> documentList = null;
                
        switch (Global.activeSection) {
            case "REP":
                break;
            case "ULP":
                documentList = SMDSDocuments.loadDocumentNamesByTypeAndSection(Global.caseType, "Quest");
                break;
            case "ORG":
                break;
            case "MED":
                break;
            case "Hearings":
                break;
            case "Civil Service Commission":
                break;
            case "CMDS":
                break;
        }
        if (documentList != null) {
            for (SMDSDocuments doc : documentList) {
                model.addRow(new Object[]{
                    doc.id,
                    false,
                    doc.description
                });
            }
        }
    }
    
    private void clearDate(WebDateField dateField, MouseEvent evt) {
        if(evt.getButton() == MouseEvent.BUTTON3 && dateField.isEnabled()) {
            ClearDateDialog dialog = new ClearDateDialog((JFrame) Global.root, true);
            if(dialog.isReset()) {
                dateField.setText("");
            }
            dialog.dispose();
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        generateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        personTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        activityTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        additionalDocsTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        documentLabel = new javax.swing.JLabel();
        appointmentDateTextBox = new com.alee.extended.date.WebDateField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Letter");

        generateButton.setText("Generate");
        generateButton.setEnabled(false);
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        personTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "To/CC", "Destination", "Party Type", "Name", "Email"
            }
        ));
        jScrollPane1.setViewportView(personTable);

        activityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Attach", "Document"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(activityTable);
        if (activityTable.getColumnModel().getColumnCount() > 0) {
            activityTable.getColumnModel().getColumn(0).setResizable(false);
            activityTable.getColumnModel().getColumn(1).setResizable(false);
            activityTable.getColumnModel().getColumn(2).setResizable(false);
        }

        additionalDocsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Attach", "Document"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(additionalDocsTable);
        if (additionalDocsTable.getColumnModel().getColumnCount() > 0) {
            additionalDocsTable.getColumnModel().getColumn(0).setResizable(false);
            additionalDocsTable.getColumnModel().getColumn(1).setResizable(false);
            additionalDocsTable.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel2.setText("Send To:");

        documentLabel.setText("Document: <<DOCUMENT NAME>>");

        appointmentDateTextBox.setEditable(false);
        appointmentDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        appointmentDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        appointmentDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        appointmentDateTextBox.setEnabled(false);
        appointmentDateTextBox.setDateFormat(Global.mmddyyyy);
        appointmentDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                appointmentDateTextBoxMouseClicked(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Suggested Send Date:");

        jLabel5.setText("Case Documents:");

        jLabel6.setText("Additional Documents:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(documentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(appointmentDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(documentLabel)
                    .addComponent(appointmentDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addGap(22, 22, 22)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(52, 52, 52)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 284, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(70, 70, 70)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {appointmentDateTextBox, documentLabel, jLabel4});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        
    }//GEN-LAST:event_generateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void appointmentDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appointmentDateTextBoxMouseClicked
        clearDate(appointmentDateTextBox, evt);
    }//GEN-LAST:event_appointmentDateTextBoxMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable activityTable;
    private javax.swing.JTable additionalDocsTable;
    private com.alee.extended.date.WebDateField appointmentDateTextBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel documentLabel;
    private com.alee.extended.date.WebDateField filedDateTextBox;
    private com.alee.extended.date.WebDateField filedDateTextBox1;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.alee.extended.date.WebDateField originalFFDateTextBox;
    private javax.swing.JTable personTable;
    // End of variables declaration//GEN-END:variables
}
