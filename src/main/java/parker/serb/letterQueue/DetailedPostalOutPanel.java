/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterQueue;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.PostalOut;
import parker.serb.sql.PostalOutAttachment;
import parker.serb.sql.PostalOutBulk;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parker
 */
public class DetailedPostalOutPanel extends javax.swing.JDialog {

    int postalID;
    PostalOut post;
    String groupNumber;

    public DetailedPostalOutPanel(java.awt.Frame parent, boolean modal, int id) {
        super(parent, modal);
        initComponents();
        setRenderer();
        loadPanel(id);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void setRenderer() {
        jTable1.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private final DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
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

    private void loadPanel(int id) {
        relatedCasesButton.setVisible(false);
        postalID = id;
        setColumnWidth();
        loadInfo();
        loadAttachments();
    }

    private void loadInfo() {
        post = PostalOut.getPostalOutByID(postalID);
        List<PostalOutBulk> postalAddressList = PostalOutBulk.getPostalOutBulkEntries(postalID);

        setRelatedCaseButton();
        
        if (postalAddressList.isEmpty()){
            addressBlockTextArea.setText(
                (post.addressBlock.startsWith(post.person) ? "" : post.person + System.lineSeparator())
                + post.addressBlock);
        } else {
            String AddressBlockText = "";
            for (PostalOutBulk person : postalAddressList){
                if (!AddressBlockText.isEmpty()){
                    AddressBlockText += System.lineSeparator() + System.lineSeparator();
                }
                AddressBlockText += "          ------------ Recipient ------------" + System.lineSeparator() + System.lineSeparator();
                AddressBlockText += (person.addressBlock.startsWith(person.person) ? "" : person.person + System.lineSeparator()) + person.addressBlock;
            }
            addressBlockTextArea.setText(AddressBlockText);
        }
        
        addressBlockTextArea.setCaretPosition(0);
        
        if (post.suggestedSendDate != null) {
            suggestedSendDatePicker.setText(Global.mmddyyyy.format(post.suggestedSendDate));
        }
    }

    private void setRelatedCaseButton(){
        switch(Global.activeSection) {
            case "CMDS":
                cmdsDisplayLogic();
                break;
            case "REP":
            case "ULP":
            case "Hearings":
            case "MED":
            case "ORG":
            case "Civil Service Commission":
            default:
                break;
        }
    }
        
    private void cmdsDisplayLogic() {
        groupNumber = CMDSCase.getGroupNumber(NumberFormatService.generateFullCaseNumberNonGlobal(post.caseYear, post.caseType, post.caseMonth, post.caseNumber));
        if (groupNumber != null){
            if (!groupNumber.equals("")){
                relatedCasesButton.setVisible(true);
            }
        }
    }
    
    private void setColumnWidth() {
        // ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
    }

    private void loadAttachments(){
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        List<PostalOutAttachment> postList = PostalOutAttachment.getPostalOutAttachments(postalID);

        for (PostalOutAttachment item : postList){
            model.addRow(new Object[]{
                item.id,
                item.fileName
            });
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

    private void multicaseDocketingWindow(){
        MultiCaseDocketingDialog multiCaseSelection = new MultiCaseDocketingDialog(this, 
                true, 
                NumberFormatService.generateFullCaseNumberNonGlobal(post.caseYear, post.caseType, post.caseMonth, post.caseNumber), 
                groupNumber, 
                "postalOut", 
                postalID
        );
        
        multiCaseSelection.dispose();
    }
    
    private void saveInfo(){
        if (suggestedSendDatePicker.getText().trim().equals("")){
            post.suggestedSendDate = null;
        } else {
            post.suggestedSendDate = new java.sql.Date(suggestedSendDatePicker.getDate().getTime());
        }
        PostalOut.updatePostalOut(post);
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        addressBlockTextArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        suggestedSendDatePicker = new com.alee.extended.date.WebDateField();
        saveButton = new javax.swing.JButton();
        relatedCasesButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        headerLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText("Postal");

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Attachement"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
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

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Address:");

        addressBlockTextArea.setEditable(false);
        addressBlockTextArea.setBackground(new java.awt.Color(238, 238, 238));
        addressBlockTextArea.setColumns(20);
        addressBlockTextArea.setRows(5);
        addressBlockTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        addressBlockTextArea.setEnabled(false);
        jScrollPane2.setViewportView(addressBlockTextArea);

        jLabel6.setText("Attachments:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Suggested Send Date:");

        suggestedSendDatePicker.setEditable(false);
        suggestedSendDatePicker.setCaretColor(new java.awt.Color(0, 0, 0));
        suggestedSendDatePicker.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        suggestedSendDatePicker.setDateFormat(Global.mmddyyyy);

        suggestedSendDatePicker.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            suggestedSendDatePicker.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    suggestedSendDatePickerMouseClicked(evt);
                }
            });

            saveButton.setText("Save");
            saveButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    saveButtonActionPerformed(evt);
                }
            });

            relatedCasesButton.setText("Related Cases");
            relatedCasesButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    relatedCasesButtonActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                        .addComponent(headerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(55, 55, 55)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(suggestedSendDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jScrollPane2)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(relatedCasesButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(headerLabel)
                    .addGap(20, 20, 20)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(suggestedSendDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 181, Short.MAX_VALUE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel6)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(saveButton)
                        .addComponent(relatedCasesButton))
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void suggestedSendDatePickerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suggestedSendDatePickerMouseClicked
        clearDate(suggestedSendDatePicker, evt);
    }//GEN-LAST:event_suggestedSendDatePickerMouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() > 1){
            String fileName = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();

            switch (post.section) {
                case "CSC":
                case "Civil Service Commission":
                case "ORG":
                    FileService.openFileWithORGNumber(post.section, post.caseNumber, fileName);
                    break;
                default:
                    FileService.openFileWithCaseNumber(post.section, post.caseYear, post.caseType, post.caseMonth, post.caseNumber, fileName);
                    break;
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveInfo();
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void relatedCasesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_relatedCasesButtonActionPerformed
        multicaseDocketingWindow();
    }//GEN-LAST:event_relatedCasesButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea addressBlockTextArea;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton relatedCasesButton;
    private javax.swing.JButton saveButton;
    private com.alee.extended.date.WebDateField suggestedSendDatePicker;
    // End of variables declaration//GEN-END:variables
}
