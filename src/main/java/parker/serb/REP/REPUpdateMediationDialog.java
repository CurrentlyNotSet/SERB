/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import com.alee.extended.date.WebCalendar;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.REPMediation;
import parker.serb.sql.User;
import parker.serb.util.CancelUpdate;


/**
 *
 * @author parkerjohnston
 */
public class REPUpdateMediationDialog extends javax.swing.JDialog {

    String id;
    /**
     * Creates new form REPAddMediationDialog
     */
    public REPUpdateMediationDialog(java.awt.Frame parent, boolean modal, String passedID) {
        super(parent, modal);
        initComponents();
        addListeners();
        loadToComboBox();
        loadInformation(passedID);
        id = passedID;
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
//            !(Integer.valueOf(hourTextBox.getText().trim()) < 12 && Integer.valueOf(hourTextBox.getText().trim()) > 0) ||
//            !(Integer.valueOf(minuteTextBox.getText().trim()) < 60 && Integer.valueOf(minuteTextBox.getText().trim()) > -1) ||
            amPMComboBox.getSelectedItem().equals("") ||
            typeComboBox.getSelectedItem().equals("") ||
            mediatorComboBox.getSelectedItem().equals("")) 
        {
            updateButton.setEnabled(false);
        } else {
            updateButton.setEnabled(true);
        }
    }
    
    private void loadInformation(String id) {
        REPMediation repMed = REPMediation.loadMeidationByID(id);
        
        dateTextBox.setText(repMed.mediationDate.split(" ")[0]);
        hourTextBox.setText(repMed.mediationDate.split(" ")[1].split(":")[0]);
        minuteTextBox.setText(repMed.mediationDate.split(" ")[1].split(":")[1]);
        amPMComboBox.setSelectedItem(repMed.mediationDate.split(" ")[2]);
        typeComboBox.setSelectedItem(repMed.mediationType);
        mediatorComboBox.setSelectedItem(repMed.mediator);
        outcomeComboBox.setSelectedItem(repMed.mediationOutcome);
        
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
        
        userList = User.loadSectionDropDowns("REP");
        
        mediatorComboBox.setMaximumRowCount(6);
        mediatorComboBox.removeAllItems();
        mediatorComboBox.addItem("");
        
        for(int i = 0; i < userList.size(); i++) {
            mediatorComboBox.addItem(userList.get(i).toString());
        }
    }
    
    private void enableAllInputs() {
        dateTextBox.setEnabled(true);
        dateTextBox.setBackground(Color.white);
        hourTextBox.setEnabled(true);
        hourTextBox.setBackground(Color.white);
        minuteTextBox.setEnabled(true);
        minuteTextBox.setBackground(Color.white);
        amPMComboBox.setEnabled(true);
        typeComboBox.setEnabled(true);
        mediatorComboBox.setEnabled(true);
        outcomeComboBox.setEnabled(true);
    }
    
    private void disableAllInputs(boolean save) {
        if(save) {
            saveInformation();
        } else {
            loadInformation(id);
        }
        
        dateTextBox.setEnabled(false);
        dateTextBox.setBackground(new Color(238, 238, 238));
        hourTextBox.setEnabled(false);
        hourTextBox.setBackground(new Color(238, 238, 238));
        minuteTextBox.setEnabled(false);
        minuteTextBox.setBackground(new Color(238, 238, 238));
        amPMComboBox.setEnabled(false);
        typeComboBox.setEnabled(false);
        mediatorComboBox.setEnabled(false);
        outcomeComboBox.setEnabled(false);
    }
    
    private void saveInformation() {
        
        REPMediation.updateMediationByID(id,
                generateDate(),
                typeComboBox.getSelectedItem().toString().trim(),
                mediatorComboBox.getSelectedItem().toString().trim(),
                outcomeComboBox.getSelectedItem().toString().trim());
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
        updateButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();

        jTextField3.setText("jTextField3");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("New Mediation");

        jLabel2.setText("Date:");

        dateTextBox.setEditable(false);
        dateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        dateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dateTextBox.setEnabled(false);
        dateTextBox.setDateFormat(Global.mmddyyyy);

        dateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );

            jLabel3.setText("Time:");

            hourTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            hourTextBox.setEnabled(false);
            hourTextBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    hourTextBoxActionPerformed(evt);
                }
            });

            jLabel4.setText(":");

            minuteTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            minuteTextBox.setEnabled(false);
            minuteTextBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    minuteTextBoxActionPerformed(evt);
                }
            });

            amPMComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM", " " }));
            amPMComboBox.setSelectedIndex(2);
            amPMComboBox.setEnabled(false);

            jLabel5.setText("Type:");

            jLabel6.setText("Mediator:");

            jLabel7.setText("Outcome:");

            typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Internal Mediation", "30 Day Mediation", "Post-Directive Mediation", " " }));
            typeComboBox.setSelectedIndex(3);
            typeComboBox.setEnabled(false);

            mediatorComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
            mediatorComboBox.setEnabled(false);

            outcomeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Settled", "Not Settled", "Rescheduled", " " }));
            outcomeComboBox.setSelectedIndex(3);
            outcomeComboBox.setEnabled(false);

            updateButton.setText("Update");
            updateButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    updateButtonActionPerformed(evt);
                }
            });

            closeButton.setText("Close");
            closeButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    closeButtonActionPerformed(evt);
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
                                    .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                        .addComponent(updateButton)
                        .addComponent(closeButton))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        if(updateButton.getText().equals("Update")) {
            updateButton.setText("Save");
            closeButton.setText("Cancel");
            enableAllInputs();
        } else if(updateButton.getText().equals("Save")) {
            updateButton.setText("Update");
            closeButton.setText("Close");
            disableAllInputs(true);
        }
    }//GEN-LAST:event_updateButtonActionPerformed

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        if(closeButton.getText().equals("Close")) {
            dispose();
        } else if(closeButton.getText().equals("Cancel")) {
            CancelUpdate cancel = new CancelUpdate(Global.root, true);
            if(!cancel.isReset()) {
            } else {
                updateButton.setText("Update");
                closeButton.setText("Close");
                disableAllInputs(false);
            }
        }
    }//GEN-LAST:event_closeButtonActionPerformed

    private void hourTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hourTextBoxActionPerformed

    private void minuteTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minuteTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minuteTextBoxActionPerformed

    /**
     * @param args the command line arguments
     */


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> amPMComboBox;
    private javax.swing.JButton closeButton;
    private com.alee.extended.date.WebDateField dateTextBox;
    private javax.swing.JTextField hourTextBox;
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
    private javax.swing.JComboBox<String> typeComboBox;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
