/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterQueue;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.EmailOut;
import parker.serb.sql.EmailOutAttachment;
import parker.serb.sql.LetterQueue;
import parker.serb.sql.PostalOut;
import parker.serb.sql.PostalOutAttachment;

/**
 *
 * @author parker
 */
public class LetterQueuePanel extends javax.swing.JDialog {

    public LetterQueuePanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setRenderer();
        loadPanel();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void setRenderer() {
        jTable1.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Global.ALTERNATE_ROW_COLOR);
                }
                return c;
            }
        });
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

        // Creation Date
        jTable1.getColumnModel().getColumn(3).setMinWidth(100);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(100);

        //UserID (4)
        jTable1.getColumnModel().getColumn(4).setMinWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(100);

        //To  (5)
        //NO Preference

        //Subject  (6)
        //NO Preference

        // Attachment Number
        jTable1.getColumnModel().getColumn(7).setMinWidth(100);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(7).setMaxWidth(100);

        // Suggested Send Date
        jTable1.getColumnModel().getColumn(8).setMinWidth(100);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(8).setMaxWidth(100);

    }

    private void loadLetterQueue(){
        String searchTerm = searchTextBox.getText().trim();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        List<LetterQueue> queueList = LetterQueue.getLetterQueueByGlobalSection();

        for (LetterQueue item : queueList){
            if (       item.type.contains(searchTerm.toLowerCase())
                    || item.fullCaseNumber.toLowerCase().contains(searchTerm.toLowerCase())
                    || item.to.toLowerCase().contains(searchTerm.toLowerCase())
                    || item.subject.toLowerCase().contains(searchTerm.toLowerCase())) {

                model.addRow(new Object[]{
                    item.id, // ID
                    item.type, // Type
                    item.fullCaseNumber, // CaseNumber
                    item.creationDate, //Date Created
                    item.userName, //UserName
                    item.to, // To:
                    item.subject, // Subject
                    item.attachementCount,// Attachments
                    item.suggestedSendDate // Suggest Send Date
                });
            }
        }
    }

    private void processTableAction(java.awt.event.MouseEvent evt) {
        if(evt.getClickCount() == 1) {
            deleteButton.setEnabled(true);
            sendButton.setEnabled(true);
        } else if(evt.getClickCount() >= 2) {
            deleteButton.setEnabled(false);
            sendButton.setEnabled(false);
            if (jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString().equals("Email")){
                new DetailedEmailOutPanel(Global.root, true, (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            } else if (jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString().equals("Postal")) {
                new DetailedPostalOutPanel(Global.root, true, (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            }
            loadLetterQueue();
        }
    }

    private void processSendButton() {
        if (jTable1.getSelectedRow() >= 0){
            new ConfirmationDialog(Global.root, true,
                    jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString(),
                    (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            loadLetterQueue();
        }
    }

    private void processDeleteButton() {
        int answer = WebOptionPane.showConfirmDialog(this, "Are you sure you wish to remove this message from queue.", "Remove", WebOptionPane.YES_NO_OPTION);
        if (answer == WebOptionPane.YES_OPTION) {
            int rowID = (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0);

            if (jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString().equals("Email")){
                EmailOut.removeEmail(rowID);
                EmailOutAttachment.removeEmailAttachment(rowID);
            } else if (jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString().equals("Postal")) {
                PostalOut.removeEntry(rowID);
                PostalOutAttachment.removeEntry(rowID);
            }
        }
        loadLetterQueue();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        searchTextBox = new javax.swing.JTextField();
        clearSearchButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();

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
                "ID", "Type", "Case Number", "Created", "Created By", "To", "Subject", "Attachments", "Send Date"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLabel1.setText("Search:");

        searchTextBox.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                searchTextBoxCaretUpdate(evt);
            }
        });

        clearSearchButton.setText("Clear");
        clearSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSearchButtonActionPerformed(evt);
            }
        });

        deleteButton.setText("Delete");
        deleteButton.setEnabled(false);
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

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
                        .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(clearSearchButton)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, sendButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(searchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 460, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendButton)
                    .addComponent(cancelButton)
                    .addComponent(deleteButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        processSendButton();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        processTableAction(evt);
    }//GEN-LAST:event_jTable1MouseClicked

    private void clearSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSearchButtonActionPerformed
        searchTextBox.setText("");
    }//GEN-LAST:event_clearSearchButtonActionPerformed

    private void searchTextBoxCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_searchTextBoxCaretUpdate
        loadLetterQueue();
    }//GEN-LAST:event_searchTextBoxCaretUpdate

    private void deleteButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deleteButtonActionPerformed
        processDeleteButton();
    }//GEN-LAST:event_deleteButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton clearSearchButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField searchTextBox;
    private javax.swing.JButton sendButton;
    // End of variables declaration//GEN-END:variables
}
