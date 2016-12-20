/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.Hearing;

import parker.serb.CMDS.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.CMDSHearing;
import parker.serb.sql.EmailOutInvites;
import parker.serb.sql.HearingHearing;
import parker.serb.sql.HearingRoom;
import parker.serb.sql.HearingType;
import parker.serb.sql.User;


/**
 *
 * @author parkerjohnston
 */
public class HearingUpdateHearingDialog extends javax.swing.JDialog {

    String id = "";
    String hearingType = "";
    String hearingDate = "";
    /**
     * Creates new form REPAddMediationDialog
     * @param parent
     * @param modal
     */
    public HearingUpdateHearingDialog(java.awt.Frame parent, boolean modal,
            String passedID,
            String comments,
            String passedHearingType,
            String passedHearingDate) {
        super(parent, modal);
        initComponents();
        id = passedID;
        hearingType = passedHearingType;
        hearingDate = passedHearingDate;
        jTextArea1.setText(comments);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
//    private void loadInformation() {
//        HearingHearing hearing = HearingHearing.loadHearingInformationByID(id);
//        
//        
//    }
    
//    private void loadTypeComboBox() {
//        hearingTypeComboBox.removeAllItems();
//        hearingTypeComboBox.addItem("");
//        
//        for (HearingType type : HearingType.loadActiveHearingTypesBySection("CMDS")){
//            hearingTypeComboBox.addItem(type.hearingType);
//        }
//    }
    
//    private void loadRoomComboBox() {
//        hearingRoomComboBox.removeAllItems();
//        hearingRoomComboBox.addItem("");
//        
//        for (HearingRoom room : HearingRoom.loadActiveHearingRooms()){
//            hearingRoomComboBox.addItem(room.roomAbbreviation);
//        }
//    }
    
//    private void loadALJComboBox() {
//        aljComboBox.removeAllItems();
//        aljComboBox.addItem("");
//        
//        List userList = User.loadULPComboBox();
//        
//        for (Object user : userList) {
//            aljComboBox.addItem((String) user);
//        }
//    }
    
    
    
//    private void addListeners() {
//        
//        hourTextBox.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                if(hourTextBox.getText().equals("")) {
//                    enableSaveButton();
//                    amPMComboBox.setSelectedItem("");
//                } else {
//                    enableSaveButton();
//                    amPMComboBox.setSelectedItem(Integer.parseInt(hourTextBox.getText()) >= 7 && Integer.parseInt(hourTextBox.getText()) <= 11 ? "AM" : "PM");
//                }
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                if(hourTextBox.getText().equals("")) {
//                    enableSaveButton();
//                    amPMComboBox.setSelectedItem("");
//                } else {
//                    enableSaveButton();
//                    amPMComboBox.setSelectedItem(Integer.parseInt(hourTextBox.getText()) >= 7 && Integer.parseInt(hourTextBox.getText()) <= 11 ? "AM" : "PM");
//                }
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                if(hourTextBox.getText().equals("")) {
//                    enableSaveButton();
//                    amPMComboBox.setSelectedItem("");
//                } else {
//                    enableSaveButton();
//                    amPMComboBox.setSelectedItem(Integer.parseInt(hourTextBox.getText()) >= 7 && Integer.parseInt(hourTextBox.getText()) <= 11 ? "AM" : "PM");
//                }
//            }
//        });
//        
//        hourTextBox.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                
//                if (hourTextBox.getText().length() == 2) {
//                    e.consume();
//                } else {
//                    char c = e.getKeyChar();
//                    if (!((c >= '0') && (c <= '9') ||
//                       (c == KeyEvent.VK_BACK_SPACE) ||
//                       (c == KeyEvent.VK_DELETE))) {
//                      e.consume();
//                    }
//                }
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {}
//
//            @Override
//            public void keyReleased(KeyEvent e) {}
//        });
//        
//        minuteTextBox.addKeyListener(new KeyListener() {
//            @Override
//            public void keyTyped(KeyEvent e) {
//                if (minuteTextBox.getText().length() == 2) {
//                    e.consume();
//                } else {
//                    char c = e.getKeyChar();
//                    if (!((c >= '0') && (c <= '9') ||
//                       (c == KeyEvent.VK_BACK_SPACE) ||
//                       (c == KeyEvent.VK_DELETE))) {
//                      e.consume();
//                    }
//                }
//            }
//
//            @Override
//            public void keyPressed(KeyEvent e) {}
//
//            @Override
//            public void keyReleased(KeyEvent e) {}
//        });
//        
//        dateTextBox.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                enableSaveButton();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                enableSaveButton();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                enableSaveButton();
//            }
//        });
//        
//        minuteTextBox.getDocument().addDocumentListener(new DocumentListener() {
//            @Override
//            public void insertUpdate(DocumentEvent e) {
//                enableSaveButton();
//            }
//
//            @Override
//            public void removeUpdate(DocumentEvent e) {
//                enableSaveButton();
//            }
//
//            @Override
//            public void changedUpdate(DocumentEvent e) {
//                enableSaveButton();
//            }
//        });
//        
//        amPMComboBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                enableSaveButton();
//            }
//        });
//        
//        hearingTypeComboBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                enableSaveButton();
//            }
//        });
//        
//        hearingRoomComboBox.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                enableSaveButton();
//            }
//        });
//    }
    
//    private void enableSaveButton() {
//        if(dateTextBox.getText().equals("") ||
//            hourTextBox.getText().trim().equals("") ||
//            minuteTextBox.getText().trim().equals("") ||
//            amPMComboBox.getSelectedItem().equals("") ||
//            hearingTypeComboBox.getSelectedItem().equals("") ||
//            hearingRoomComboBox.getSelectedItem().equals("") ||
//            aljComboBox.getSelectedItem().equals("")) 
//        {
//            saveButton.setEnabled(false);
//        } else {
//            if(!(Integer.valueOf(hourTextBox.getText().trim()) <= 12 && Integer.valueOf(hourTextBox.getText().trim()) > 0) ||
//            !(Integer.valueOf(minuteTextBox.getText().trim()) < 60 && Integer.valueOf(minuteTextBox.getText().trim()) > -1) ||
//            minuteTextBox.getText().trim().length() != 2)
//            {
//                saveButton.setEnabled(false);
//            } else {
//                saveButton.setEnabled(true);
//            }
//        }
//    }
    

//    private Date generateDate() {
//        int hour = Integer.valueOf(hourTextBox.getText().trim());
//   
//        Calendar cal = Calendar.getInstance();
//        cal.set(Calendar.YEAR, Integer.valueOf(dateTextBox.getText().split("/")[2]));
//        cal.set(Calendar.MONTH, Integer.valueOf(dateTextBox.getText().split("/")[0]) - 1);
//        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dateTextBox.getText().split("/")[1]));
//        cal.set(Calendar.HOUR_OF_DAY, amPMComboBox.getSelectedItem().toString().equalsIgnoreCase("AM") || hour == 12 ? hour : hour + 12);
//        cal.set(Calendar.MINUTE, Integer.valueOf(minuteTextBox.getText().trim()));
//        cal.set(Calendar.SECOND, 0);
//        cal.set(Calendar.MILLISECOND, 0);
//        
//        return cal.getTime();
//    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        saveButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Update Hearing");

        saveButton.setText("Update");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jLabel7.setText("Comments:");

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setWrapStyleWord(true);
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel7)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 96, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(saveButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        HearingHearing.updateHearingByID(id,
                jTextArea1.getText().trim(),
                hearingType,
                hearingDate);
        
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JButton saveButton;
    // End of variables declaration//GEN-END:variables
}
