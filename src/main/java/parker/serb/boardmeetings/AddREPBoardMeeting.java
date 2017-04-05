/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.boardmeetings;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.utils.swing.Customizer;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.BoardMeeting;
import parker.serb.sql.CaseParty;
import parker.serb.sql.REPCase;
import parker.serb.sql.REPRecommendation;
import parker.serb.util.ClearDateDialog;

/**
 *
 * @author parkerjohnston
 */
public class AddREPBoardMeeting extends javax.swing.JDialog {

    /**
     * Creates new form AddULPBoardMeeting
     */
    public AddREPBoardMeeting(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addListeners();
        loadRecComboBox();
//        jScrollPane1.setVisible(false);
        setLocationRelativeTo(parent);
        setVisible(true);
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

        recommendationComboBox.addActionListener((ActionEvent e) -> {
            if(recommendationComboBox.getSelectedItem() != null)
                handleRecommendationSelection(recommendationComboBox.getSelectedItem().toString());
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

    private void handleRecommendationSelection(String recText) {
        //List of potential "bookmarks"
        //<<Incumbent>>
        //<<Employer>>
        //<<Rival>>
        //<<PetitionerIntervenor>>
        //<<Polling_Period>>
        //<<CountBallotsDate>>


        recText = recText.replaceAll("«", "<<");
        recText = recText.replaceAll("»", ">>");

        //Party Information loading
        if (recText.contains("<<PetitionerIntervenor>>")
                || recText.contains("<<Employer>>")
                || recText.contains("<<Rival>>")
                || recText.contains("<<Incumbent>>")) {
            List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);

            String incumbentNames = "";
            String employerNames = "";
            String rivalNames = "";
            String petitionerIntervenorNames = "";

            for (CaseParty party : partyList) {
                if (null != party.caseRelation) {
                    switch (party.caseRelation) {
                        case "Incumbent Employee Organization":
                            if (!"".equals(incumbentNames.trim())) {
                                incumbentNames += ", ";
                            }
                            incumbentNames += party.companyName;
                            break;
                        case "Employer":
                            if (!"".equals(employerNames.trim())) {
                                employerNames += ", ";
                            }
                            employerNames += party.companyName;
                            break;
                        case "Rival Employee Organization":
                            if (!"".equals(rivalNames.trim())) {
                                rivalNames += ", ";
                            }
                            rivalNames += party.companyName;
                            break;
                        case "Petitioner":
                            if (!"".equals(petitionerIntervenorNames.trim())) {
                                petitionerIntervenorNames += ", ";
                            }
                            petitionerIntervenorNames += party.companyName;
                            break;
                        default:
                            if (party.caseRelation.startsWith("Rival Employee Organization") && !party.caseRelation.endsWith("REP")) {
                                if (!"".equals(rivalNames.trim())) {
                                    rivalNames += ", ";
                                }
                                rivalNames += party.companyName;
                            }
                            break;
                    }
                }
            }

            recText = recText.replaceAll("<<Incumbent>>", incumbentNames);
            recText = recText.replaceAll("<<Employer>>", employerNames);
            recText = recText.replaceAll("<<Rival>>", rivalNames);
            recText = recText.replaceAll("<<PetitionerIntervenor>>", petitionerIntervenorNames);
        }

        //Case Information Loading
        if (recText.contains("<<Polling_Period>>")
                || recText.contains("<<CountBallotsDate>>")) {
            REPCase caseInfo = REPCase.loadCaseDetails(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);

            String balloutCountDate = caseInfo.ballotsCountDate == null ? "" : Global.MMMMMdyyyy.format(caseInfo.ballotsCountDate);
            String pollingPeriod = "";
            //Polling information
            if (caseInfo.pollingEndDate != null && caseInfo.pollingStartDate != null) {
                pollingPeriod = Global.MMMMMdyyyy.format(caseInfo.pollingStartDate);
                pollingPeriod += " thru " + Global.MMMMMdyyyy.format(caseInfo.pollingEndDate);
            }

            recText = recText.replaceAll("<<Polling_Period>>", balloutCountDate);
            recText = recText.replaceAll("<<CountBallotsDate>>", pollingPeriod);
        }

        recommendationTextArea.setText(recText);
    }

    private void enableAddButton() {
        if(meetingDateTextBox.getText().equals("") ||
                agendaItemTextBox.getText().equals("")) {
            addBoardMeetingButton.setEnabled(false);
        } else {
            addBoardMeetingButton.setEnabled(true);
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
        addBoardMeetingButton = new javax.swing.JButton();
        cancelBoardMeetingButton = new javax.swing.JButton();
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
        jLabel1.setText("New Boarding Meeting");

        jLabel2.setText("Meeting Date:");

        jLabel3.setText("Agenda Item:");

        jLabel4.setText("Recommendation:");

        addBoardMeetingButton.setText("Add");
        addBoardMeetingButton.setEnabled(false);
        addBoardMeetingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addBoardMeetingButtonActionPerformed(evt);
            }
        });

        cancelBoardMeetingButton.setText("Cancel");
        cancelBoardMeetingButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelBoardMeetingButtonActionPerformed(evt);
            }
        });

        recommendationComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        recommendationComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                recommendationComboBoxActionPerformed(evt);
            }
        });

        meetingDateTextBox.setEditable(false);
        meetingDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        meetingDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
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
            memoDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
            memoDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
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

                recommendationTextArea.setColumns(20);
                recommendationTextArea.setLineWrap(true);
                recommendationTextArea.setRows(5);
                recommendationTextArea.setWrapStyleWord(true);
                jScrollPane1.setViewportView(recommendationTextArea);

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cancelBoardMeetingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(addBoardMeetingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel3)
                                    .addComponent(jLabel2)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(recommendationComboBox, 0, 444, Short.MAX_VALUE)
                                    .addComponent(agendaItemTextBox)
                                    .addComponent(meetingDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(memoDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jScrollPane1))))
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
                            .addComponent(cancelBoardMeetingButton)
                            .addComponent(addBoardMeetingButton))
                        .addContainerGap())
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void cancelBoardMeetingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelBoardMeetingButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelBoardMeetingButtonActionPerformed

    private void addBoardMeetingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBoardMeetingButtonActionPerformed
        BoardMeeting.addREPBoardMeeting(meetingDateTextBox.getText(), agendaItemTextBox.getText(), recommendationTextArea.getText(), memoDateTextBox.getText());
        dispose();
    }//GEN-LAST:event_addBoardMeetingButtonActionPerformed

    private void recommendationComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_recommendationComboBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_recommendationComboBoxActionPerformed

    private void meetingDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_meetingDateTextBoxMouseClicked
        clearDate(meetingDateTextBox, evt);
    }//GEN-LAST:event_meetingDateTextBoxMouseClicked

    private void memoDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_memoDateTextBoxMouseClicked
        clearDate(memoDateTextBox, evt);
    }//GEN-LAST:event_memoDateTextBoxMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addBoardMeetingButton;
    private javax.swing.JTextField agendaItemTextBox;
    private javax.swing.JButton cancelBoardMeetingButton;
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
    // End of variables declaration//GEN-END:variables
}
