/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.publicRecords;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.utils.swing.Customizer;
import java.awt.event.MouseEvent;
import java.io.File;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.io.FilenameUtils;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.CaseType;
import parker.serb.sql.EmailOut;
import parker.serb.sql.EmailOutAttachment;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.EmailValidation;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;
import parker.serb.util.StringUtilities;

/**
 *
 * @author parker
 */
public class PublicRecordsEmailPanel extends javax.swing.JDialog {

    List<String> filesMissing = new ArrayList<>();
    boolean fileInUse = false;
    List<String> fileInUseList = new ArrayList<>();
    double attachmentSize = 0.0;

    public PublicRecordsEmailPanel(java.awt.Frame parent, boolean modal, List<Activity> docsList) {
        super(parent, modal);
        initComponents();
        loadPanel(docsList);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void loadPanel(List<Activity> docsList) {
        listeners();
        setSubjectLine();
        setColumnWidth();
        loadAttachments(docsList);
    }

    private void listeners() {
        toTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                sendButton.setEnabled(EmailValidation.validEmail(toTextBox.getText().trim()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                sendButton.setEnabled(EmailValidation.validEmail(toTextBox.getText().trim()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                sendButton.setEnabled(EmailValidation.validEmail(toTextBox.getText().trim()));
            }
        });

        ccTextbox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                sendButton.setEnabled(EmailValidation.validEmail(ccTextbox.getText().trim()));
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                sendButton.setEnabled(EmailValidation.validEmail(ccTextbox.getText().trim()));
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                sendButton.setEnabled(EmailValidation.validEmail(ccTextbox.getText().trim()));
            }
        });
    }

    private void setSubjectLine() {
        if (Global.activeSection.equals("ORG")
                || Global.activeSection.equals("Civil Service Commission")
                || Global.activeSection.equals("CSC")) {
            subjectTextbox.setText("Public Records Request");
        } else {
            subjectTextbox.setText("Public Records Request for Case #" + NumberFormatService.generateFullCaseNumber());
        }
    }

    private void setColumnWidth() {
        // ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);

        // FileName
        jTable1.getColumnModel().getColumn(1).setMinWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(0);
    }

    private void loadAttachments(List<Activity> docsList) {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Activity item : docsList) {
            model.addRow(new Object[]{
                item.id,
                item.fileName,
                item.action
            });
        }
    }

    private void sendButtonProcess() {
        if (verifyFilesExist()) {
            if (Global.EMAIL_SIZE_LIMIT >= attachmentSize) {
                if (fileInUse) {
                    filesInUseMessage();
                } else {
                    sendEmail();
                }
            } else {
                tooLargeMessage();
            }
        } else {
            String listOfFiles = "";
            for (String file : filesMissing) {
                listOfFiles += "<br>" + file;
            }
            WebOptionPane.showMessageDialog(
                    Global.root,
                    "<html><center> Sorry, unable to locate file(s) required to send.<br>" + listOfFiles + "</center></html>",
                    "Error",
                    WebOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void sendEmail() {
        //insert email and return ID
        int emailID = insertEmail();

        //insert attachments
        for (int i = 0; i < jTable1.getRowCount(); i++) {
            insertGeneratedAttachementEmail(emailID, jTable1.getValueAt(i, 1).toString());
        }

        //mark email ready to send
        EmailOut.markEmailReadyToSend(emailID);
    }

    private int insertEmail() {
        String emailBody = emailBodyTextArea.getText();

        emailBody += System.lineSeparator() + System.lineSeparator()
                + StringUtilities.buildFullName(Global.activeUser.firstName, Global.activeUser.middleInitial, Global.activeUser.lastName)
                + System.lineSeparator() + (Global.activeUser.jobTitle == null ? "" : Global.activeUser.jobTitle + System.lineSeparator())
                + StringUtilities.generateDepartmentAddressBlock() + System.lineSeparator()
                + (Global.activeUser.workPhone == null ? "" : "Telephone: " + Global.activeUser.workPhone);

        EmailOut eml = new EmailOut();

        eml.section = Global.activeSection;
        eml.caseYear = Global.caseYear;
        eml.caseType = Global.caseType;
        eml.caseMonth = Global.caseMonth;
        eml.caseNumber = Global.caseNumber;
        eml.to = toTextBox.getText().trim().equals("") ? null : toTextBox.getText().trim();
        eml.from = Global.activeUser.emailAddress;
        eml.cc = ccTextbox.getText().trim().equals("") ? null : ccTextbox.getText().trim();
        eml.bcc = "serbeoarchive@serb.state.oh.us";
        eml.subject = subjectTextbox.getText().trim().equals("") ? null : subjectTextbox.getText().trim();
        eml.body = emailBody;
        eml.userID = Global.activeUser.id;
        eml.suggestedSendDate = suggestedSendDatePicker.getText().equals("") ? null : new Date(NumberFormatService.convertMMDDYYYY(suggestedSendDatePicker.getText()));
        eml.okToSend = false;
        eml.internalNote = internalNotesTextArea.getText().trim().equals("") ? null : internalNotesTextArea.getText().trim();

        return EmailOut.insertEmail(eml);
    }

    private void insertGeneratedAttachementEmail(int emailID, String docName) {
        if (emailID > 0) {
            EmailOutAttachment attach = new EmailOutAttachment();

            attach.emailOutID = emailID;
            attach.fileName = docName;
            attach.primaryAttachment = true;
            EmailOutAttachment.insertAttachment(attach);
        }
    }

    private void clearDate(WebDateField dateField, MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON3 && dateField.isEnabled()) {
            ClearDateDialog dialog = new ClearDateDialog((JFrame) Global.root, true);
            if (dialog.isReset()) {
                dateField.setText("");
            }
            dialog.dispose();
        }
    }

    private boolean verifyFilesExist() {
        boolean allExist = true;
        String path = "";

        if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                || Global.activeSection.equalsIgnoreCase("CSC")
                || Global.activeSection.equalsIgnoreCase("ORG")) {
            path = Global.activityPath
                    + (Global.activeSection.equals("Civil Service Commission")
                    ? Global.caseType : Global.activeSection) + File.separator
                    + Global.caseNumber + File.separator;
        } else if (Global.activeSection.equalsIgnoreCase("Hearings")) {
            path = Global.activityPath + File.separatorChar
                    + CaseType.getSectionFromCaseType(Global.caseType) + File.separatorChar
                    + Global.caseYear + File.separatorChar
                    + NumberFormatService.generateFullCaseNumber()
                    + File.separatorChar;
        } else {
            path = Global.activityPath + File.separatorChar
                    + Global.activeSection + File.separatorChar
                    + Global.caseYear + File.separatorChar
                    + NumberFormatService.generateFullCaseNumber()
                    + File.separatorChar;
        }

        filesMissing = new ArrayList<>();

        for (int i = 0; i < jTable1.getRowCount(); i++) {
            String fileName = jTable1.getValueAt(i, 1).toString();

            File attachment = new File(path + fileName);
            if (!attachment.exists()) {
                allExist = false;
                filesMissing.add(fileName);
            } else {
                if ("docx".equalsIgnoreCase(FilenameUtils.getExtension(fileName))
                        || "doc".equalsIgnoreCase(FilenameUtils.getExtension(fileName))) {
                    if (!attachment.renameTo(attachment)) {
                        fileInUse = true;
                        fileInUseList.add(fileName);
                    }
                    attachmentSize += attachment.length();
                }
            }

        }

        return allExist;
    }

    private void filesInUseMessage() {
        String listOfFiles = "";
        for (String file : fileInUseList) {
            listOfFiles += "<br>" + file;
        }
        WebOptionPane.showMessageDialog(
                Global.root,
                "<html><center> Sorry, files in use. Please close documents before sending.<br>" + listOfFiles + "</center></html>",
                "Error",
                WebOptionPane.ERROR_MESSAGE
        );
    }

    private void tooLargeMessage() {
        WebOptionPane.showMessageDialog(
                Global.root,
                "<html><center> Sorry, email size exceeds server limit, unable to send.</center></html>",
                "Error",
                WebOptionPane.ERROR_MESSAGE
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        sendButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        toTextBox = new javax.swing.JTextField();
        ccTextbox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        subjectTextbox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        emailBodyTextArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        suggestedSendDatePicker = new com.alee.extended.date.WebDateField();
        jScrollPane3 = new javax.swing.JScrollPane();
        internalNotesTextArea = new javax.swing.JTextArea();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        headerLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText("Public Records Email");

        sendButton.setText("Send");
        sendButton.setEnabled(false);
        sendButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sendButtonActionPerformed(evt);
            }
        });

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
                "ID", "Attachement", "Description"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
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

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("TO:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("CC:");

        toTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        ccTextbox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Subject:");

        subjectTextbox.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        jLabel5.setText("Body:");

        emailBodyTextArea.setColumns(20);
        emailBodyTextArea.setRows(5);
        jScrollPane2.setViewportView(emailBodyTextArea);

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

            internalNotesTextArea.setColumns(20);
            internalNotesTextArea.setRows(5);
            jScrollPane3.setViewportView(internalNotesTextArea);

            jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
            jLabel8.setText("Internal Notes:");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 600, Short.MAX_VALUE)
                        .addComponent(headerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(cancelButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(sendButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(suggestedSendDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(toTextBox)
                                .addComponent(ccTextbox, javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(subjectTextbox, javax.swing.GroupLayout.DEFAULT_SIZE, 545, Short.MAX_VALUE)))
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addContainerGap())
            );

            layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, sendButton});

            layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4});

            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(headerLabel)
                    .addGap(20, 20, 20)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(suggestedSendDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(toTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(ccTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(subjectTextbox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel8)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jLabel6)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 157, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(sendButton)
                        .addComponent(cancelButton))
                    .addContainerGap())
            );

            layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel2, jLabel3, jLabel4, jLabel7});

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void sendButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sendButtonActionPerformed
        sendButtonProcess();
        dispose();
    }//GEN-LAST:event_sendButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void suggestedSendDatePickerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suggestedSendDatePickerMouseClicked
        clearDate(suggestedSendDatePicker, evt);
    }//GEN-LAST:event_suggestedSendDatePickerMouseClicked

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (evt.getClickCount() > 1) {
            String fileName = jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString();

            switch (Global.activeSection) {
                case "CSC":
                case "Civil Service Commission":
                    FileService.openFileWithORGNumber("CSC", Global.caseNumber, fileName);
                    break;
                case "ORG":
                    FileService.openFileWithORGNumber("ORG", Global.caseNumber, fileName);
                    break;
                case "Hearings":
                    FileService.openFileWithCaseNumber(CaseType.getSectionFromCaseType(Global.caseType), Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber, fileName);
                    break;
                default:
                    FileService.openFileWithCaseNumber(Global.activeSection, Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber, fileName);
                    break;
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField ccTextbox;
    private javax.swing.JTextArea emailBodyTextArea;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JTextArea internalNotesTextArea;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton sendButton;
    private javax.swing.JTextField subjectTextbox;
    private com.alee.extended.date.WebDateField suggestedSendDatePicker;
    private javax.swing.JTextField toTextBox;
    // End of variables declaration//GEN-END:variables
}
