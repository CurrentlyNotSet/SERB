/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.boardmeetings;

import com.alee.extended.date.WebCalendar;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.BoardMeeting;
import parker.serb.sql.ULPRecommendation;
import parker.serb.util.CancelUpdate;

/**
 *
 * @author parkerjohnston
 */
public class UpdateULPBoardMeeting extends javax.swing.JDialog {

    String id;
    String agendaNumber;
    String rec;
    String date;
    /**
     * Creates new form AddULPBoardMeeting
     */
    public UpdateULPBoardMeeting(java.awt.Frame parent, boolean modal,
            String passedDate, String passedAgendaNumber, String passedRec, String passedID) {
        super(parent, modal);
        initComponents();
        addListeners();
        id = passedID;
        date = passedDate;
        agendaNumber = passedAgendaNumber;
        rec = passedRec;
        loadRecComboBox();
        loadInformation(date, agendaNumber, rec);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void loadInformation(String date, String agendaNumber, String rec) {
        meetingDateTextBox.setText(date);
        agendaItemTextBox.setText(agendaNumber);
        recommendationComboBox.setSelectedItem(rec);
    }
    
    private void addListeners() {
        meetingDateTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableAddButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableAddButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableAddButton();
            }
        });
        
        agendaItemTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableAddButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableAddButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableAddButton();
            }
        });
        
    }
    
    private void loadRecComboBox() {
        recommendationComboBox.removeAllItems();
        
        recommendationComboBox.addItem("");
        
        List recommendationList = ULPRecommendation.loadAllULPRecommendations();
        
        for (Object recommendation : recommendationList) {
            ULPRecommendation rec = (ULPRecommendation) recommendation;
            recommendationComboBox.addItem(rec.code);
        }
    }
    
    private void enableAddButton() {
        if(meetingDateTextBox.getText().equals("") ||
                agendaItemTextBox.getText().equals("")) {
            updateButton.setEnabled(false);
        } else {
            updateButton.setEnabled(true);
        }
    }
    
    private void enableAllInputs() {
        meetingDateTextBox.setEnabled(true);
        meetingDateTextBox.setBackground(Color.white);
        agendaItemTextBox.setEnabled(true);
        agendaItemTextBox.setBackground(Color.white);
        recommendationComboBox.setEnabled(true);
    }
    
    private void disableAllInputs(boolean save) {
        if(save) {
            saveInformation();
        } else {
            loadInformation(date, agendaNumber, rec);
        }
        
        meetingDateTextBox.setEnabled(false);
        meetingDateTextBox.setBackground(new Color(238, 238, 238));
        agendaItemTextBox.setEnabled(false);
        agendaItemTextBox.setBackground(new Color(238, 238, 238));
        recommendationComboBox.setEnabled(false);
    }
    
    private void saveInformation() {
        BoardMeeting.updateULPBoardMeeting(
            id,
            meetingDateTextBox.getText(),
            agendaItemTextBox.getText(),
            recommendationComboBox.getSelectedItem().toString()
        );
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
        updateButton = new javax.swing.JButton();
        closeButton = new javax.swing.JButton();
        recommendationComboBox = new javax.swing.JComboBox<>();
        agendaItemTextBox = new javax.swing.JTextField();
        meetingDateTextBox = new com.alee.extended.date.WebDateField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Update Boarding Meeting");

        jLabel2.setText("Meeting Date:");

        jLabel3.setText("Agenda Item:");

        jLabel4.setText("Recommendation:");

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

        recommendationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        recommendationComboBox.setEnabled(false);

        agendaItemTextBox.setBackground(new java.awt.Color(238, 238, 238));
        agendaItemTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        agendaItemTextBox.setEnabled(false);

        meetingDateTextBox.setEditable(false);
        meetingDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        meetingDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        meetingDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        meetingDateTextBox.setEnabled(false);
        meetingDateTextBox.setDateFormat(Global.mmddyyyy);

        meetingDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );

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
                                .addComponent(jLabel3)
                                .addComponent(jLabel2)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(recommendationComboBox, 0, 241, Short.MAX_VALUE)
                                .addComponent(agendaItemTextBox)
                                .addComponent(meetingDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(meetingDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(agendaItemTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(recommendationComboBox)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(updateButton)
                        .addComponent(closeButton))
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

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

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField agendaItemTextBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private com.alee.extended.date.WebDateField meetingDateTextBox;
    private javax.swing.JComboBox<String> recommendationComboBox;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
