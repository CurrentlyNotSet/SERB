/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.boardmeetings;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.BoardMeeting;
import parker.serb.sql.REPRecommendation;
import parker.serb.util.CancelUpdate;
import parker.serb.util.ClearDateDialog;

/**
 *
 * @author parkerjohnston
 */
public class UpdateREPBoardMeeting extends javax.swing.JDialog {

    String id;
    String agendaNumber;
    String rec;
    String date;
    String memoDate;
    /**
     * Creates new form AddULPBoardMeeting
     */
    public UpdateREPBoardMeeting(java.awt.Frame parent, boolean modal,
            String passedDate, String passedAgendaNumber, String passedRec, String passedID, String passedMemoDate) {
        super(parent, modal);
        initComponents();
        addListeners();
        id = passedID;
        date = passedDate;
        agendaNumber = passedAgendaNumber;
        rec = passedRec;
        memoDate = passedMemoDate;
        loadRecComboBox();
        loadInformation(date, agendaNumber, rec, memoDate);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void loadInformation(String date, String agendaNumber, String rec, String memoDate) {
        meetingDateTextBox.setText(date);
        agendaItemTextBox.setText(agendaNumber);
        recommendationComboBox.setSelectedItem("");
        recommendationTextArea.setText(rec);
        memoDateTextBox.setText(memoDate);
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
        
        recommendationComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(recommendationComboBox.getSelectedItem() != null)
                    recommendationTextArea.setText(recommendationComboBox.getSelectedItem().toString());
            }
        });
    }
    
    private void loadRecComboBox() {
        recommendationComboBox.removeAllItems();
        
        recommendationComboBox.addItem("");
        
        List recommendationList = REPRecommendation.loadAllREPRecommendations();
        
        for (Object recommendation : recommendationList) {
            REPRecommendation rec = (REPRecommendation) recommendation;
            recommendationComboBox.addItem(rec.recommendation);
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
        recommendationTextArea.setEnabled(true);
        recommendationTextArea.setBackground(Color.white);
        memoDateTextBox.setEnabled(true);
        memoDateTextBox.setBackground(Color.WHITE);
    }
    
    private void disableAllInputs(boolean save) {
        if(save) {
            saveInformation();
        } else {
            loadInformation(date, agendaNumber, rec, memoDate);
        }
        
        meetingDateTextBox.setEnabled(false);
        meetingDateTextBox.setBackground(new Color(238, 238, 238));
        agendaItemTextBox.setEnabled(false);
        agendaItemTextBox.setBackground(new Color(238, 238, 238));
        recommendationComboBox.setEnabled(false);
        memoDateTextBox.setEnabled(false);
        memoDateTextBox.setBackground(new Color(238, 238, 238));
        recommendationTextArea.setEnabled(false);
        recommendationTextArea.setBackground(new Color(238, 238, 238));
    }
    
    private void saveInformation() {
        BoardMeeting.updateREPBoardMeeting(
            id,
            meetingDateTextBox.getText(),
            agendaItemTextBox.getText(),
            recommendationTextArea.getText(),
            memoDateTextBox.getText()
        );
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
        jLabel5 = new javax.swing.JLabel();
        memoDateTextBox = new com.alee.extended.date.WebDateField();
        jScrollPane1 = new javax.swing.JScrollPane();
        recommendationTextArea = new javax.swing.JTextArea();

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
            meetingDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    meetingDateTextBoxMouseClicked(evt);
                }
            });

            jLabel5.setText("Memo Date:");

            memoDateTextBox.setEditable(false);
            memoDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
            memoDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
            memoDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            memoDateTextBox.setEnabled(false);
            memoDateTextBox.setDateFormat(Global.mmddyyyy);

            memoDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );
                memoDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        memoDateTextBoxMouseClicked(evt);
                    }
                });

                recommendationTextArea.setBackground(new java.awt.Color(235, 235, 235));
                recommendationTextArea.setColumns(20);
                recommendationTextArea.setLineWrap(true);
                recommendationTextArea.setRows(5);
                recommendationTextArea.setWrapStyleWord(true);
                recommendationTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                recommendationTextArea.setEnabled(false);
                jScrollPane1.setViewportView(recommendationTextArea);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(updateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(recommendationComboBox, 0, 444, Short.MAX_VALUE)
                                    .addComponent(agendaItemTextBox)
                                    .addComponent(meetingDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(memoDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(memoDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(updateButton)
                            .addComponent(closeButton))
                        .addContainerGap())
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

    private void meetingDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_meetingDateTextBoxMouseClicked
        clearDate(meetingDateTextBox, evt);
    }//GEN-LAST:event_meetingDateTextBoxMouseClicked

    private void memoDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_memoDateTextBoxMouseClicked
        clearDate(memoDateTextBox, evt);
    }//GEN-LAST:event_memoDateTextBoxMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField agendaItemTextBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private com.alee.extended.date.WebDateField meetingDateTextBox;
    private com.alee.extended.date.WebDateField memoDateTextBox;
    private javax.swing.JComboBox<String> recommendationComboBox;
    private javax.swing.JTextArea recommendationTextArea;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
