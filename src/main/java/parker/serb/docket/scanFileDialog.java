/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.docket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.ActivityType;
import parker.serb.sql.CaseNumber;
import parker.serb.sql.ULPCase;
import parker.serb.sql.User;
import parker.serb.util.FileService;

/**
 *
 * @author parkerjohnston
 */
public class scanFileDialog extends javax.swing.JDialog {

    String selectedSection = "";
    /**
     * Creates new form scanFileDialog
     */
    public scanFileDialog(java.awt.Frame parent, boolean modal, String file, String section, String time) {
        super(parent, modal);
        initComponents();
        selectedSection = section;
        loadData(section, file, time);
        addListeners();
        setLocationRelativeTo(parent);
        setVisible(true); 
    }
    
    private void loadData(String section, String file, String time) {
        fileNameTextBox.setText(file);
        loadToComboBox();
        loadTypeComboBox();
        scanDateTextBox.setText(time.split(" ")[0]);
        hourTextBox.setText(time.split(" ")[1].split(":")[0]);
        minuteTextBox.setText(time.split(" ")[1].split(":")[1]);
        amPMComboBox.setSelectedItem(time.split(" ")[2]);
        
    }
    
    private void addListeners() {
        hourTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                autoCompleteTimeOfDay();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                autoCompleteTimeOfDay();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                autoCompleteTimeOfDay();
            }
        });
        
        caseNumberTextBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                validateCaseNumber();
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
         
        fromTextBox.getDocument().addDocumentListener(new DocumentListener() {
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
         
        toComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableButton();
            }
        });
         
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               enableButton();
            }
        });
        
        fileNameTextBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    FileService.openScanFile(fileNameTextBox.getText().trim(), selectedSection);
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
    }
    
    private void autoCompleteTimeOfDay() {
        if(hourTextBox.getText().equals("")) {
            //place holder
        } else if(Integer.parseInt(hourTextBox.getText()) >= 7 && Integer.parseInt(hourTextBox.getText()) <= 11) {
            amPMComboBox.setSelectedItem("AM");
        } else {
            amPMComboBox.setSelectedItem("PM");
        }
    }
    
    private void enableButton() {
        if(caseNumberTextBox.getText().equals("")
                || fromTextBox.getText().equals("")
                || toComboBox.getSelectedItem().toString().equals("")
                || typeComboBox.getSelectedItem().toString().equals("")) {
            fileButton.setEnabled(false);
        } else {
            fileButton.setEnabled(true);
        }
    }
    
    private void loadToComboBox() {
        List userList = User.loadSectionDropDowns(selectedSection);
        
        toComboBox.setMaximumRowCount(6);
        
        toComboBox.removeAllItems();
        
        toComboBox.addItem("");
        
        for(int i = 0; i < userList.size(); i++) {
            toComboBox.addItem(userList.get(i).toString());
        }
    }
    
    private void loadTypeComboBox() {
        List typeList = ActivityType.loadAllActivityTypeBySection(selectedSection);
        
        typeComboBox.setMaximumRowCount(10);
        
        typeComboBox.removeAllItems();
        
        typeComboBox.addItem("");
        
        for(int i = 0; i < typeList.size(); i++) {
            ActivityType item = (ActivityType) typeList.get(i);
            typeComboBox.addItem(item.descriptionAbbrv + " - " + item.descriptionFull);
        }
    }
    
    private void validateCaseNumber() {
        String[] caseNumbers = caseNumberTextBox.getText().split(",");
        
        String caseNumberFail = CaseNumber.validateULPCaseNumber(caseNumbers);
        
        if(!caseNumberFail.equals("")) {
            new docketingCaseNotFound((JFrame) Global.root.getRootPane().getParent(), true, caseNumberFail);
            caseNumberTextBox.requestFocus();
        }
        
        if(!caseNumberTextBox.getText().equals("")) {
            switch (selectedSection) {
                case "ULP":  toComboBox.setSelectedItem(ULPCase.ULPDocketTo(caseNumberTextBox.getText()));
            }
        }
    }
    
    private Date generateDate() {
        int hour = Integer.valueOf(hourTextBox.getText().trim());
        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(scanDateTextBox.getText().split("/")[2]));
        cal.set(Calendar.MONTH, Integer.valueOf(scanDateTextBox.getText().split("/")[0]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(scanDateTextBox.getText().split("/")[1]));
        cal.set(Calendar.HOUR_OF_DAY, amPMComboBox.getSelectedItem().toString().equalsIgnoreCase("AM") ? hour : hour + 12);
        cal.set(Calendar.MINUTE, Integer.valueOf(minuteTextBox.getText().trim()));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        redactedCheckBox = new javax.swing.JCheckBox();
        fileNameTextBox = new javax.swing.JTextField();
        fromTextBox = new javax.swing.JTextField();
        toComboBox = new javax.swing.JComboBox<>();
        typeComboBox = new javax.swing.JComboBox<>();
        cancelButton = new javax.swing.JButton();
        fileButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        caseNumberTextBox = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentTextBox = new javax.swing.JTextArea();
        jLabel9 = new javax.swing.JLabel();
        scanDateTextBox = new com.alee.extended.date.WebDateField();
        hourTextBox = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        minuteTextBox = new javax.swing.JTextField();
        amPMComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Scan Filing");

        jLabel2.setText("File Name:");

        jLabel3.setText("From:");

        jLabel4.setText("To:");

        jLabel5.setText("Type:");

        jLabel6.setText("Comment:");

        jLabel7.setText("Redacted:");

        fileNameTextBox.setEditable(false);
        fileNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fileNameTextBox.setEnabled(false);

        toComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        fileButton.setText("File");
        fileButton.setEnabled(false);
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("Case Number(s):");

        commentTextBox.setColumns(20);
        commentTextBox.setLineWrap(true);
        commentTextBox.setRows(5);
        jScrollPane1.setViewportView(commentTextBox);

        jLabel9.setText("Date:");

        scanDateTextBox.setEditable(false);
        scanDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        scanDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        scanDateTextBox.setDateFormat(Global.mmddyyyy);
        scanDateTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                scanDateTextBoxActionPerformed(evt);
            }
        });

        hourTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourTextBoxActionPerformed(evt);
            }
        });

        jLabel10.setText(":");

        minuteTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minuteTextBoxActionPerformed(evt);
            }
        });

        amPMComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(caseNumberTextBox)
                            .addComponent(fileNameTextBox)
                            .addComponent(fromTextBox)
                            .addComponent(toComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(typeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(redactedCheckBox)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(scanDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(hourTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, 0)
                                .addComponent(minuteTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(amPMComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fileNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(scanDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(hourTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10)
                    .addComponent(minuteTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amPMComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fromTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(redactedCheckBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        // TODO add your handling code here:
        String[] caseNumbers = caseNumberTextBox.getText().trim().split(",");
        FileService.docketScan(caseNumbers,
                fileNameTextBox.getText(),
                selectedSection,
                typeComboBox.getSelectedItem().toString().split("-")[0].trim(),
                typeComboBox.getSelectedItem().toString().split("-")[1].trim(),
                fromTextBox.getText(),
                toComboBox.getSelectedItem().toString(),
                commentTextBox.getText(),
                redactedCheckBox.isSelected(),
                generateDate());
        dispose();
    }//GEN-LAST:event_fileButtonActionPerformed

    private void scanDateTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanDateTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scanDateTextBoxActionPerformed

    private void hourTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hourTextBoxActionPerformed

    private void minuteTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minuteTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minuteTextBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> amPMComboBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JTextArea commentTextBox;
    private javax.swing.JButton fileButton;
    private javax.swing.JTextField fileNameTextBox;
    private javax.swing.JTextField fromTextBox;
    private javax.swing.JTextField hourTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField minuteTextBox;
    private javax.swing.JCheckBox redactedCheckBox;
    private com.alee.extended.date.WebDateField scanDateTextBox;
    private javax.swing.JComboBox<String> toComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
