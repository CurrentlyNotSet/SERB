/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

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
import parker.serb.sql.REPMediation;
import parker.serb.sql.User;


/**
 *
 * @author parkerjohnston
 */
public class REPAddMediationDialog extends javax.swing.JDialog {

    /**
     * Creates new form REPAddMediationDialog
     */
    public REPAddMediationDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addListeners();
        loadToComboBox();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void addListeners() {
        
        hourTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(hourTextBox.getText().equals("")) {
                    enableSaveButton();
                    amPMComboBox.setSelectedItem("");
                } else {
                    enableSaveButton();
                    amPMComboBox.setSelectedItem(Integer.parseInt(hourTextBox.getText()) >= 7 && Integer.parseInt(hourTextBox.getText()) <= 11 ? "AM" : "PM");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(hourTextBox.getText().equals("")) {
                    enableSaveButton();
                    amPMComboBox.setSelectedItem("");
                } else {
                    enableSaveButton();
                    amPMComboBox.setSelectedItem(Integer.parseInt(hourTextBox.getText()) >= 7 && Integer.parseInt(hourTextBox.getText()) <= 11 ? "AM" : "PM");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(hourTextBox.getText().equals("")) {
                    enableSaveButton();
                    amPMComboBox.setSelectedItem("");
                } else {
                    enableSaveButton();
                    amPMComboBox.setSelectedItem(Integer.parseInt(hourTextBox.getText()) >= 7 && Integer.parseInt(hourTextBox.getText()) <= 11 ? "AM" : "PM");
                }
            }
        });
        
        hourTextBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                
                if (hourTextBox.getText().length() == 2) {
                    e.consume();
                } else {
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9') ||
                       (c == KeyEvent.VK_BACK_SPACE) ||
                       (c == KeyEvent.VK_DELETE))) {
                      e.consume();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        minuteTextBox.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) {
                if (minuteTextBox.getText().length() == 2) {
                    e.consume();
                } else {
                    char c = e.getKeyChar();
                    if (!((c >= '0') && (c <= '9') ||
                       (c == KeyEvent.VK_BACK_SPACE) ||
                       (c == KeyEvent.VK_DELETE))) {
                      e.consume();
                    }
                }
            }

            @Override
            public void keyPressed(KeyEvent e) {}

            @Override
            public void keyReleased(KeyEvent e) {}
        });
        
        dateTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableSaveButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableSaveButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableSaveButton();
            }
        });
        
        minuteTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableSaveButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableSaveButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableSaveButton();
            }
        });
        
        amPMComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableSaveButton();
            }
        });
        
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableSaveButton();
            }
        });
        
        mediatorComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableSaveButton();
            }
        });
        
        outcomeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableSaveButton();
            }
        });
    }
    
    private void enableSaveButton() {
        if(dateTextBox.getText().equals("") ||
            hourTextBox.getText().trim().equals("") ||
            minuteTextBox.getText().trim().equals("") ||
            amPMComboBox.getSelectedItem().equals("") ||
            typeComboBox.getSelectedItem().equals("") ||
            mediatorComboBox.getSelectedItem().equals("")) 
        {
            saveButton.setEnabled(false);
        } else {
            if(!(Integer.valueOf(hourTextBox.getText().trim()) <= 12 && Integer.valueOf(hourTextBox.getText().trim()) > 0) ||
            !(Integer.valueOf(minuteTextBox.getText().trim()) < 60 && Integer.valueOf(minuteTextBox.getText().trim()) > -1) ||
            minuteTextBox.getText().trim().length() != 2)
            {
                saveButton.setEnabled(false);
            } else {
                saveButton.setEnabled(true);
            }
        }
    }
    

    private Date generateDate() {
        int hour = Integer.valueOf(hourTextBox.getText().trim());
//        
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(dateTextBox.getText().split("/")[2]));
        cal.set(Calendar.MONTH, Integer.valueOf(dateTextBox.getText().split("/")[0]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dateTextBox.getText().split("/")[1]));
        cal.set(Calendar.HOUR_OF_DAY, amPMComboBox.getSelectedItem().toString().equalsIgnoreCase("AM") ? hour : hour + 12);
        cal.set(Calendar.MINUTE, Integer.valueOf(minuteTextBox.getText().trim()));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        
        return cal.getTime();
    }
    
    private void loadToComboBox() {
        List userList = null;
        
        userList = User.loadREPComboBox();
        
        mediatorComboBox.setMaximumRowCount(6);
        mediatorComboBox.removeAllItems();
        mediatorComboBox.addItem("");
        
        for(int i = 0; i < userList.size(); i++) {
            mediatorComboBox.addItem(userList.get(i).toString());
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

        jTextField3 = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        dateTextBox = new com.alee.extended.date.WebDateField();
        jLabel3 = new javax.swing.JLabel();
        hourTextBox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        minuteTextBox = new javax.swing.JTextField();
        amPMComboBox = new javax.swing.JComboBox<>();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        typeComboBox = new javax.swing.JComboBox<>();
        mediatorComboBox = new javax.swing.JComboBox<>();
        outcomeComboBox = new javax.swing.JComboBox<>();
        saveButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("New Mediation");

        jLabel2.setText("Date:");

        dateTextBox.setEditable(false);
        dateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        dateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dateTextBox.setDateFormat(Global.mmddyyyy);

        jLabel3.setText("Time:");

        hourTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                hourTextBoxActionPerformed(evt);
            }
        });

        jLabel4.setText(":");

        minuteTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                minuteTextBoxActionPerformed(evt);
            }
        });

        amPMComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM", " " }));
        amPMComboBox.setSelectedIndex(2);

        jLabel5.setText("Type:");

        jLabel6.setText("Mediator:");

        jLabel7.setText("Outcome:");

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Internal Mediation", "30 Day Mediation", "Post-Directive Mediation", " " }));
        typeComboBox.setSelectedIndex(3);

        mediatorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        outcomeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Settled", "Not Settled", "Rescheduled", " " }));
        outcomeComboBox.setSelectedIndex(3);

        saveButton.setText("Save");
        saveButton.setEnabled(false);
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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel5)
                                    .addComponent(jLabel6)
                                    .addComponent(jLabel7))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(mediatorComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(0, 0, Short.MAX_VALUE)
                                        .addComponent(hourTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(0, 0, 0)
                                        .addComponent(jLabel4)
                                        .addGap(0, 0, 0)
                                        .addComponent(minuteTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 51, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(amPMComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE))
                                    .addComponent(typeComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(outcomeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(10, 10, 10))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(dateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(hourTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4)
                    .addComponent(minuteTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amPMComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(typeComboBox)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mediatorComboBox)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(outcomeComboBox)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        REPMediation.addMediation(generateDate(),
                typeComboBox.getSelectedItem().toString(),
                mediatorComboBox.getSelectedItem().toString(),
                outcomeComboBox.getSelectedItem().toString());
        dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void hourTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hourTextBoxActionPerformed

    private void minuteTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minuteTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minuteTextBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> amPMComboBox;
    private com.alee.extended.date.WebDateField dateTextBox;
    private javax.swing.JTextField hourTextBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JComboBox<String> mediatorComboBox;
    private javax.swing.JTextField minuteTextBox;
    private javax.swing.JComboBox<String> outcomeComboBox;
    private javax.swing.JButton saveButton;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
