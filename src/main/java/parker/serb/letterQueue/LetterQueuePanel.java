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
import parker.serb.sql.PostalOutBulk;
import parker.serb.sql.PostalOutRelatedCase;

/**
 *
 * @author parker
 */
public class LetterQueuePanel extends javax.swing.JDialog {

    private List<LetterQueue> queueList;

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
        setCheckboxText();
        setColumnWidth();
        loadPanelInformation();
    }

    private void setCheckboxText() {
        switch (typeComboBox.getSelectedItem().toString()) {
            case "Email":
                selectAllCheckBox.setText("Select All");
                break;
            default:
                selectAllCheckBox.setText("Select Top " + Global.LETTER_QUEUE_SELECT_LIMIT);
                break;
        }
    }

    private void setColumnWidth() {
        // ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);

        //Active (Yes/No)
        jTable1.getColumnModel().getColumn(1).setMinWidth(40);
        jTable1.getColumnModel().getColumn(1).setWidth(40);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(40);

        // Type
        jTable1.getColumnModel().getColumn(2).setMinWidth(60);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(60);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(60);

        // Case Number
        jTable1.getColumnModel().getColumn(3).setMinWidth(125);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(125);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(125);

        // Creation Date
        jTable1.getColumnModel().getColumn(4).setMinWidth(100);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(100);

        //UserID (5)
        jTable1.getColumnModel().getColumn(5).setMinWidth(100);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(100);

        //To  (6)
        //NO Preference

        //Subject  (7)
        //NO Preference

        // Attachment Number
        jTable1.getColumnModel().getColumn(8).setMinWidth(100);
        jTable1.getColumnModel().getColumn(8).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(8).setMaxWidth(100);

        // Suggested Send Date
        jTable1.getColumnModel().getColumn(9).setMinWidth(100);
        jTable1.getColumnModel().getColumn(9).setPreferredWidth(100);
        jTable1.getColumnModel().getColumn(9).setMaxWidth(100);

    }

    private void loadLetterQueue(){
        String searchTerm = searchTextBox.getText().trim();

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        if (queueList != null) {
            for (LetterQueue item : queueList) {
                if (item.type.contains(searchTerm.toLowerCase())
                        || item.fullCaseNumber.toLowerCase().contains(searchTerm.toLowerCase())
                        || item.userName.toLowerCase().contains(searchTerm.toLowerCase())
                        || item.to.toLowerCase().contains(searchTerm.toLowerCase())
                        || item.subject.toLowerCase().contains(searchTerm.toLowerCase())) {

                    model.addRow(new Object[]{
                        item.id, // ID
                        selectAllCheckBox.isSelected(),
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
        jLayeredPane1.moveToBack(jPanel1);
        toggleSearchInteractionHandling(true);
    }

    private void processTableAction(java.awt.event.MouseEvent evt) {
        if(evt.getClickCount() >= 2) {
            if (jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString().equals("Email")){
                new DetailedEmailOutPanel(Global.root, true, (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            } else if (jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString().equals("Postal")) {
                new DetailedPostalOutPanel(Global.root, true, (int) jTable1.getValueAt(jTable1.getSelectedRow(), 0));
            }
        }
    }

    private void processSendButton() {
            new ConfirmationBulkDialog(Global.root, true, jTable1);
    }

    private void processDeleteButton() {
        int answer = WebOptionPane.showConfirmDialog(this, "Are you sure you wish to remove the message(s) from queue.", "Remove", WebOptionPane.YES_NO_OPTION);
        if (answer == WebOptionPane.YES_OPTION) {
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if (jTable1.getValueAt(i, 1).equals(true)) {
                    int rowID = (int) jTable1.getValueAt(i, 0);
                    String sendType = jTable1.getValueAt(i, 2).toString().trim();

                    if (sendType.equals("Email")) {
                        EmailOut.removeEmail(rowID);
                        EmailOutAttachment.removeEmailAttachment(rowID);
                    } else if (sendType.equals("Postal")) {
                        PostalOut.removeEntry(rowID);
                        PostalOutAttachment.removeEntry(rowID);
                        PostalOutRelatedCase.deletePostalOutRelatedCaseByID(rowID);
                        PostalOutBulk.removeEntry(rowID);
                    }
                }
            }
        }
    }

    private void loadPanelInformation() {
        Thread temp = new Thread(() -> {
            toggleSearchInteractionHandling(false);
            selectAllCheckBox.setSelected(false);
            jLayeredPane1.moveToFront(jPanel1);
            DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
            model.setRowCount(0);

            switch (typeComboBox.getSelectedItem().toString()) {
                case "Email":
                    queueList = LetterQueue.getLetterQueueEmailByGlobalSection();
                    break;
                case "Postal":
                    queueList = LetterQueue.getLetterQueuePostalByGlobalSection();
                    break;
                default:
                    queueList = LetterQueue.getLetterQueueAllByGlobalSection();
                    break;
            }
            loadLetterQueue();
        });
        temp.start();
    }

    private void toggleSearchInteractionHandling(boolean enabled){
        searchTextBox.setEnabled(enabled);
        clearSearchButton.setEnabled(enabled);
        jScrollPane1.setEnabled(enabled);
    }

    private boolean verifySelectionSize() {
        int count = 0;
        if (typeComboBox.getSelectedItem().toString().equals("Both") || typeComboBox.getSelectedItem().toString().equals("Postal")) {
            //Get Count from table
            for (int i = 0; i < jTable1.getRowCount(); i++) {
                if (jTable1.getValueAt(i, 1).equals(true)) {
                    count++;
                }
            }
            if (count == 0){
                WebOptionPane.showMessageDialog(
                        Global.root,
                        "<html><center> Sorry, unable to process. "
                                + "<br><br>No items are currently selected.</center></html>",
                        "No Items Selected",
                        WebOptionPane.WARNING_MESSAGE
                );
                return false;
            }


            //Compare Count
            if (count > Global.LETTER_QUEUE_SELECT_LIMIT) {
                WebOptionPane.showMessageDialog(
                        Global.root,
                        "<html><center> Sorry, unable to process. "
                                + "<br><br>Maximum message selection limit exceeded"
                                + "<br><br>Please remove " + (count - Global.LETTER_QUEUE_SELECT_LIMIT) + " items</center></html>",
                        "Too Large",
                        WebOptionPane.WARNING_MESSAGE
                );
                return false;
            }
        }
        return true;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        searchTextBox = new javax.swing.JTextField();
        clearSearchButton = new javax.swing.JButton();
        deleteButton = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox<>();
        selectAllCheckBox = new javax.swing.JCheckBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        headerLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText("<<Letter Queue>>");

        sendButton.setText("Send");
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

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
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
        deleteButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deleteButtonActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1104, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 427, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 427, Short.MAX_VALUE))
        );

        jLayeredPane1.add(jPanel1);

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Type", "Case Number", "Created", "Created By", "To", "Subject", "Attachments", "Send Date"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

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
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(1).setResizable(false);
        }

        jLayeredPane1.add(jScrollPane1);

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Type:");

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Both", "Email", "Postal" }));
        typeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                typeComboBoxActionPerformed(evt);
            }
        });

        selectAllCheckBox.setText("Select All");
        selectAllCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                selectAllCheckBoxMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(selectAllCheckBox)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(headerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(cancelButton)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 402, Short.MAX_VALUE)
                                .addComponent(deleteButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 402, Short.MAX_VALUE)
                                .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(searchTextBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(clearSearchButton)))
                        .addContainerGap())))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPane1)
                    .addContainerGap()))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, sendButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(clearSearchButton)
                    .addComponent(searchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(selectAllCheckBox)
                .addGap(458, 458, 458)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(sendButton)
                    .addComponent(cancelButton)
                    .addComponent(deleteButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(99, 99, 99)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(60, Short.MAX_VALUE)))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
    if(verifySelectionSize()){
        processSendButton();
        loadPanelInformation();
    }
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
        loadPanelInformation();
    }//GEN-LAST:event_deleteButtonActionPerformed

    private void typeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_typeComboBoxActionPerformed
        setCheckboxText();
        loadPanelInformation();
    }//GEN-LAST:event_typeComboBoxActionPerformed

    private void selectAllCheckBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_selectAllCheckBoxMouseClicked
        switch (typeComboBox.getSelectedItem().toString()) {
            case "Email":
                for (int i = 0; i < jTable1.getRowCount(); i++) {
                    jTable1.getModel().setValueAt(selectAllCheckBox.isSelected(), i, 1);
                }
                break;
            default:
                for (int i = 0;
                        i < (jTable1.getRowCount() > Global.LETTER_QUEUE_SELECT_LIMIT ? Global.LETTER_QUEUE_SELECT_LIMIT : jTable1.getRowCount());
                        i++) {
                    jTable1.getModel().setValueAt(selectAllCheckBox.isSelected(), i, 1);
                }
                break;
        }
    }//GEN-LAST:event_selectAllCheckBoxMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton clearSearchButton;
    private javax.swing.JButton deleteButton;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField searchTextBox;
    private javax.swing.JCheckBox selectAllCheckBox;
    private javax.swing.JButton sendButton;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
