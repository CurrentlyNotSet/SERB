/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterQueue;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.EmailOut;
import parker.serb.sql.PostalOut;

/**
 *
 * @author parker
 */
public class LetterQueuePanel extends javax.swing.JDialog {

    public LetterQueuePanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadPanel();
        setLocationRelativeTo(parent);
        setVisible(true);   
    }
    
    private void loadPanel() {
        headerLabel.setText(Global.activeSection + " Letter Queue");
        setColumnWidth();
        loadLetterQueue();       
    }
    
    private void setColumnWidth() {
        // ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        
        // Type
        jTable1.getColumnModel().getColumn(1).setMinWidth(60);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(60);
        
        // Case Number
        jTable1.getColumnModel().getColumn(2).setMinWidth(125);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(125);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(125);
        
        // Attachment Number
        jTable1.getColumnModel().getColumn(5).setMinWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(100);
        
        // Date
        jTable1.getColumnModel().getColumn(6).setMinWidth(100);
        jTable1.getColumnModel().getColumn(6).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(100);
    }

    private void loadLetterQueue(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        List<EmailOut> emailList = EmailOut.getEmailOutBySection(Global.activeSection);
        List<PostalOut> postalList = PostalOut.getPostalOutBySection(Global.activeSection);
        
        for (EmailOut eml : emailList){
            String fullCaseNumber = eml.caseYear + "-" + eml.caseType + "-" + eml.caseMonth + "-" + eml.caseNumber;
            String sendDate = "";
            
            if (eml.suggestedSendDate != null) {
                sendDate = Global.mmddyyyy.format(eml.suggestedSendDate);
            }
            
            model.addRow(new Object[]{
                eml.id,              // ID
                "Email",             // Type
                fullCaseNumber,      // CaseNumber
                eml.to,              // To:
                eml.subject,         // Subject
                eml.attachementCount,// Attachments
                sendDate             // Suggest Send Date
            });
        }   
        
        for (PostalOut post : postalList){
            String fullCaseNumber = post.caseYear + "-" + post.caseType + "-" + post.caseMonth + "-" + post.caseNumber;
            String sendDate = "";
            
            if (post.suggestedSendDate != null) {
                sendDate = Global.mmddyyyy.format(post.suggestedSendDate);
            }
            
            model.addRow(new Object[]{
                post.id,              // ID
                "Postal",             // Type
                fullCaseNumber,       // CaseNumber
                post.person,          // To:
                post.addressBlock,    // Subject
                post.attachementCount,// Attachments
                sendDate              // Suggest Send Date
            });
        }   
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        headerLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText("<<Letter Queue>>");

        sendButton.setText("Send");
        sendButton.setEnabled(false);
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Close");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Type", "Case Number", "To", "Subject", "Attachments", "Send Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE)
                    .addComponent(headerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, sendButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 493, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        EmailOut.markEmailReadyToSend((int) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
        loadLetterQueue();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if(evt.getClickCount() == 1) {
            sendButton.setEnabled(true);
        } else if(evt.getClickCount() >= 2) {
            sendButton.setEnabled(false);
            if (jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString().equals("Email")){
                new DetailedEmailOutPanel(Global.root, true, (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            } else if (jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString().equals("Postal")) {
                new DetailedPostalOutPanel(Global.root, true, (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            }
            loadLetterQueue();
        }
    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
