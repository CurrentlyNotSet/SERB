/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.docket;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import parker.serb.Global;
import parker.serb.sql.ActivityType;
import parker.serb.sql.CaseNumber;
import parker.serb.sql.Email;
import parker.serb.sql.EmailAttachment;
import parker.serb.sql.ULPCase;
import parker.serb.sql.User;
import parker.serb.util.FileService;

/**
 *
 * @author parker
 */
public class fileEmailDialog extends javax.swing.JDialog {
    
    JComboBox comboEditor = new JComboBox();
    String emailID = "";
    String emailSection = "";
    String passedTime;
    /**
     * Creates new form FileDocumentDialog
     */
    public fileEmailDialog(java.awt.Frame parent, boolean modal, String id, String section, String time) {
        super(parent, modal);
        initComponents();
        emailID = id;
        emailSection = section;
        passedTime = time;
        addListeners(section, id);
        loadData(section, id);
        setColumnWidth();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void setColumnWidth() {
        attachmentTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        attachmentTable.getColumnModel().getColumn(0).setMinWidth(0);
        attachmentTable.getColumnModel().getColumn(0).setMaxWidth(0);
        attachmentTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        attachmentTable.getColumnModel().getColumn(2).setMinWidth(150);
        attachmentTable.getColumnModel().getColumn(2).setMaxWidth(150);
        
    }
    
    private void addListeners(String section, String id) {
        caseNumberTextBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                validateCaseNumber();
            }
        });
        
        bodyTextArea.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    if(!bodyTextArea.getText().toString().trim().equals("")) {
                        FileService.openEmailBodyFile(id, section);
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        attachmentTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() >= 2) {
                    FileService.openAttachmentFile(attachmentTable.getValueAt(attachmentTable.getSelectedRow(), 0).toString(), section);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
        
        comboEditor.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(attachmentTable != null) {
                    enableButton();
                } 
            }
        });
        
        toComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableButton();
            }
        });
        
        caseNumberTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableButton();
            }
        });
        
        
    }
    
    private void enableButton() {
        boolean enableButton = true;
        
        for (int i = 0; i < attachmentTable.getRowCount(); i++) {
            if(attachmentTable.getValueAt(i, 2) != null) {
                if(attachmentTable.getValueAt(i, 2).toString().equals("")
                        || attachmentTable.getValueAt(i, 2).toString().equals("-----------")) {
                    attachmentTable.setValueAt("", i, 2);
                    enableButton = false;
                    break;
                }
            }
        }
        
        if(toComboBox.getSelectedItem() != null) {
            if(toComboBox.getSelectedItem().toString().equals("")) {
                enableButton = false;
            }
        }
        
        
        if(caseNumberTextBox.getText().equals("")) {
            enableButton = false;
        }
        
        fileButton.setEnabled(enableButton);
    }
    
    private void validateCaseNumber() {
        String[] caseNumbers = caseNumberTextBox.getText().split(",");
        
        String caseNumberFail = CaseNumber.validateULPCaseNumber(caseNumbers);
        
        if(!caseNumberFail.equals("")) {
            new docketingCaseNotFound((JFrame) Global.root.getRootPane().getParent(), true, caseNumberFail);
            caseNumberTextBox.requestFocus();
        }
        
        if(!caseNumberTextBox.getText().equals("")) {
            switch (emailSection) {
                case "ULP":  toComboBox.setSelectedItem(ULPCase.ULPDocketTo(caseNumberTextBox.getText()));
            }
        }
    }
     
    private void loadData(String section, String id) {
        loadToComboBox(section);
        loadEmailInformation(id);
        loadAttachmentTable(id, section);
    }
    
    private void loadToComboBox(String section) {
        List userList = User.loadSectionDropDowns(section);
        
        toComboBox.setMaximumRowCount(6);
        toComboBox.removeAllItems();
        toComboBox.addItem("");
        
        for(int i = 0; i < userList.size(); i++) {
            toComboBox.addItem(userList.get(i).toString());
        }
    }
    
    private void loadEmailInformation(String id) {
        Email emailToLoad = Email.getEmailByID(id);
        
        dateTextBox.setText(Global.mmddyyyyhhmma.format(emailToLoad.receivedDate));
        fromTextBox.setText(emailToLoad.emailFrom);
        subjectTextBox.setText(emailToLoad.emailSubject);
        bodyTextArea.setText(emailToLoad.emailBody);
        
    }
    
    private void loadAttachmentTable(String id, String section) {
        
        DefaultTableModel model = (DefaultTableModel) attachmentTable.getModel();
        
        TableColumn myColumn = attachmentTable.getColumnModel().getColumn(2);
        
        myColumn.setCellEditor(new DefaultCellEditor(loadTypeComboBox(section)));
        
        model.setRowCount(0);
        
        List<EmailAttachment> attachments = EmailAttachment.getAttachmentList(id);
        
        for (EmailAttachment attachment : attachments) {
            model.addRow(new Object[] {attachment.id, attachment.fileName, ""});
        }
    }
    
    private JComboBox loadTypeComboBox(String section) {
        List typeList = ActivityType.loadAllActivityTypeBySection(section);
        
        comboEditor.setMaximumRowCount(4);
        comboEditor.removeAllItems();
        comboEditor.addItem("DO NOT FILE");
        comboEditor.addItem("-----------");
        
        for(Object type : typeList) {
            ActivityType item = (ActivityType) type;
            comboEditor.addItem(item.descriptionAbbrv);
        }
        
        return comboEditor;
    }
    
    private void fileEmailAttachments(String[] caseNumbers) {
        //stop the possible edits on the table and file accordingly
        if(attachmentTable.getCellEditor() != null)
            attachmentTable.getCellEditor().stopCellEditing();

        for (int i = 0; i < attachmentTable.getRowCount(); i++) {
            if(!attachmentTable.getValueAt(i, 2).toString().equals("DO NOT FILE")) {
                FileService.docketEmailAttachment(caseNumbers,
                attachmentTable.getValueAt(i, 0).toString(),    //attachmentid
                emailID,                                        
                emailSection,                                   
                fromTextBox.getText(),
                toComboBox.getSelectedItem().toString(),
                subjectTextBox.getText(),
                attachmentTable.getValueAt(i, 1).toString(),    //fileName
                attachmentTable.getValueAt(i, 2).toString(),    //fileType
                attachmentTable.getValueAt(i, 3) != null
                        ? attachmentTable.getValueAt(i, 3).toString() : "",
                generateDate()); //comment
            }
        }
    }
    
    private Date generateDate() {
        //int hour = Integer.valueOf(hourTextBox.getText().trim());
        
        //03/02/2017 06:53 PM
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(passedTime.split(" ")[0].split("/")[2]));
        cal.set(Calendar.MONTH, Integer.valueOf(passedTime.split(" ")[0].split("/")[0]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(passedTime.split(" ")[0].split("/")[1]));
        cal.set(Calendar.HOUR_OF_DAY, passedTime.split(" ")[2].equals("AM") ? Integer.valueOf(passedTime.split(" ")[1].split(":")[0]) : Integer.valueOf(passedTime.split(" ")[1].split(":")[0]) + 12);
        cal.set(Calendar.MINUTE, Integer.valueOf(passedTime.split(" ")[1].split(":")[1]));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
    
    private void deleteEmail(String id) {
                    
        String emailBodyFileName = Email.getEmailBodyFileByID(id);

        File bodyFile = new File(Global.emailPath + emailSection + File.separatorChar + emailBodyFileName);

        if(bodyFile.exists()) {
            bodyFile.delete();
        }

        Email.deleteEmailEntry(Integer.valueOf(id));

        List emailAttachmentList = EmailAttachment.getAttachmentList(id);

        for(int j = 0; j < emailAttachmentList.size(); j++) {
            EmailAttachment attach = (EmailAttachment) emailAttachmentList.get(j);

            File attachFile = new File(Global.emailPath + emailSection + File.separatorChar + attach.fileName);

            if(attachFile.exists()) {
                attachFile.delete();
            }

            EmailAttachment.deleteEmailAttachmentEntry(attach.id);
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

        jScrollPane2 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        bodyTextArea = new javax.swing.JTextArea();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        attachmentTable = new javax.swing.JTable();
        caseNumberTextBox = new javax.swing.JTextField();
        dateTextBox = new javax.swing.JTextField();
        fromTextBox = new javax.swing.JTextField();
        subjectTextBox = new javax.swing.JTextField();
        fileButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        toComboBox = new javax.swing.JComboBox<>();
        jLabel8 = new javax.swing.JLabel();

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane2.setViewportView(jTable1);

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("File Email");

        jLabel2.setText("Case Number(s):");

        jLabel3.setText("Date:");

        jLabel4.setText("From:");

        jLabel5.setText("To:");

        bodyTextArea.setEditable(false);
        bodyTextArea.setColumns(20);
        bodyTextArea.setLineWrap(true);
        bodyTextArea.setRows(5);
        bodyTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        bodyTextArea.setEnabled(false);
        jScrollPane1.setViewportView(bodyTextArea);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Attachments");

        jLabel7.setText("Subject:");

        attachmentTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "id", "File Name", "Type", "Comments"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, true, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(attachmentTable);
        if (attachmentTable.getColumnModel().getColumnCount() > 0) {
            attachmentTable.getColumnModel().getColumn(0).setResizable(false);
            attachmentTable.getColumnModel().getColumn(1).setResizable(false);
            attachmentTable.getColumnModel().getColumn(2).setResizable(false);
            attachmentTable.getColumnModel().getColumn(3).setResizable(false);
        }

        dateTextBox.setEditable(false);
        dateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dateTextBox.setEnabled(false);

        fromTextBox.setEditable(false);
        fromTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fromTextBox.setEnabled(false);

        subjectTextBox.setEditable(false);
        subjectTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        subjectTextBox.setEnabled(false);

        fileButton.setText("File");
        fileButton.setEnabled(false);
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        toComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("Body");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(caseNumberTextBox)
                            .addComponent(dateTextBox)
                            .addComponent(fromTextBox)
                            .addComponent(subjectTextBox)
                            .addComponent(toComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(dateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(fromTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(toComboBox)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(subjectTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addComponent(jLabel8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileButton)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        //getcaseNumber
        String[] caseNumbers = caseNumberTextBox.getText().trim().split(",");
        //fileBody
        FileService.docketEmailBody(caseNumbers,
                emailID,
                emailSection,
                fromTextBox.getText(),
                toComboBox.getSelectedItem().toString(),
                subjectTextBox.getText(),
                generateDate());
        
        //fileAttachements
        fileEmailAttachments(caseNumbers);
        
        deleteEmail(emailID);
        
        dispose();
    }//GEN-LAST:event_fileButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable attachmentTable;
    private javax.swing.JTextArea bodyTextArea;
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JTextField dateTextBox;
    private javax.swing.JButton fileButton;
    private javax.swing.JTextField fromTextBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JTextField subjectTextBox;
    private javax.swing.JComboBox<String> toComboBox;
    // End of variables declaration//GEN-END:variables

    class MyComboBoxRenderer extends JComboBox implements TableCellRenderer {
        public MyComboBoxRenderer(String[] items) {
          super(items);
        }

        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
          if (isSelected) {
            setForeground(table.getSelectionForeground());
            super.setBackground(table.getSelectionBackground());
          } else {
            setForeground(table.getForeground());
            setBackground(table.getBackground());
          }
          setSelectedItem(value);
          return this;
        }
      }

      class MyComboBoxEditor extends DefaultCellEditor {
        public MyComboBoxEditor(String[] items) {
          super(new JComboBox(items));
        }
}

}






