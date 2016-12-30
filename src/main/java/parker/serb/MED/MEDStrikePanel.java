/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import com.alee.extended.date.WebDateField;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.MEDCase;
import parker.serb.sql.Mediator;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class MEDStrikePanel extends javax.swing.JPanel {

    MEDCase orginalInformation;
    
    /**
     * Creates new form MEDStrikePanel
     */
    public MEDStrikePanel() {
        initComponents();
        addListeners();
    }
    
    private void addListeners() {
        strikeBeganTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateTotalDays();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateTotalDays();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateTotalDays();
            }
        });
        
        strikeEndedTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                calculateTotalDays();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                calculateTotalDays();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                calculateTotalDays();
            }
        });
    }
    
    private void calculateTotalDays() {
        if(strikeBeganTextBox.getText().equals("") ||
                strikeEndedTextBox.getText().equals("")) {
            totalNumberOfDaysTextBox.setText("");
        } else {
            try {
                Date date1 = Global.mmddyyyy.parse(strikeBeganTextBox.getText());
                Date date2 = Global.mmddyyyy.parse(strikeEndedTextBox.getText());
                long diffInMillies = date2.getTime() - date1.getTime();
                int days = (int) (diffInMillies / (1000*60*60*24));
                totalNumberOfDaysTextBox.setText(Integer.toString(days));
            } catch (ParseException ex) {
                SlackNotification.sendNotification(ex);
            }
        }
    }
        
    
    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);
        
        strikeFileDateTextBox.setEnabled(true);
        strikeFileDateTextBox.setBackground(Color.white);
        //skip med case number
        relatedCaseNumberTextBox.setEnabled(true);
        relatedCaseNumberTextBox.setBackground(Color.white);
//        medCaseNumberTextBox.setBackground(Color.white); //remove ability to edit per Andrew
        descriptionTextBox.setEnabled(true);
        descriptionTextBox.setBackground(Color.white);
        unitSizeTextBox.setEnabled(true);
        unitSizeTextBox.setBackground(Color.white);
        unauthorizedStrikeCheckBox.setEnabled(true);
        noticeOfIntentToStrikeOnlyCheckBox.setEnabled(true);
        intendedDateStrikeTextBox.setEnabled(true);
        intendedDateStrikeTextBox.setBackground(Color.white);
        noticeOfIntentToPicketOnlyCheckBox.setEnabled(true);
        intendedDatePicketTextBox.setEnabled(true);
        intendedDatePicketTextBox.setBackground(Color.white);
        informationCheckBox.setEnabled(true);
        noticeOfIntentToStrikeAndPicketCheckBox.setEnabled(true);
        
        strikeOccuredComboBox.setEnabled(true);
        strikeStatusComboBox.setEnabled(true);
        strikeBeganTextBox.setEnabled(true);
        strikeBeganTextBox.setBackground(Color.white);
        strikeEndedTextBox.setEnabled(true);
        strikeEndedTextBox.setBackground(Color.white);
        //skip total number of days
        mediatorAppointedComboBox.setEnabled(true);
        strikeNotesTextArea.setEnabled(true);
        strikeNotesTextArea.setBackground(Color.white);
    }
    
    public void disableUpdate(boolean save) {
        
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);
        
        strikeFileDateTextBox.setEnabled(false);
        strikeFileDateTextBox.setBackground(new Color(238,238,238));
        //skip med case number
        relatedCaseNumberTextBox.setEnabled(false); //remove ability to edit per Andrew
        relatedCaseNumberTextBox.setBackground(new Color(238,238,238));
//        medCaseNumberTextBox.setBackground(Color.white); //remove ability to edit per Andrew
        descriptionTextBox.setEnabled(false);
        descriptionTextBox.setBackground(new Color(238,238,238));
        unitSizeTextBox.setEnabled(false);
        unitSizeTextBox.setBackground(new Color(238,238,238));
        unauthorizedStrikeCheckBox.setEnabled(false);
        noticeOfIntentToStrikeOnlyCheckBox.setEnabled(false);
        intendedDateStrikeTextBox.setEnabled(false);
        intendedDateStrikeTextBox.setBackground(new Color(238,238,238));
        noticeOfIntentToPicketOnlyCheckBox.setEnabled(false);
        intendedDatePicketTextBox.setEnabled(false);
        intendedDatePicketTextBox.setBackground(new Color(238,238,238));
        informationCheckBox.setEnabled(false);
        noticeOfIntentToStrikeAndPicketCheckBox.setEnabled(false);
        
        strikeOccuredComboBox.setEnabled(false);
        strikeStatusComboBox.setEnabled(false);
        strikeBeganTextBox.setEnabled(false);
        strikeBeganTextBox.setBackground(new Color(238,238,238));
        strikeEndedTextBox.setEnabled(false);
        strikeEndedTextBox.setBackground(new Color(238,238,238));
        //skip total number of days
        mediatorAppointedComboBox.setEnabled(false);
        strikeNotesTextArea.setEnabled(false);
        strikeNotesTextArea.setBackground(new Color(238,238,238));
        
        if(save) {
            saveInformation();
        }
        
        loadInformaiton();
    }
    
    public void loadMediators() {
        mediatorAppointedComboBox.removeAllItems();
        
        mediatorAppointedComboBox.addItem("");
        
        List currentOwnerList = Mediator.loadAllMediators();
        
        for (Object currentOwners : currentOwnerList) {
            Mediator med = (Mediator) currentOwners;
            
            mediatorAppointedComboBox.addItem(med.firstName + " " + med.lastName);
        }
    }
    
    public void loadInformaiton() {
        
        loadMediators();
        
        orginalInformation = MEDCase.loadStrikeInformation();
        
        strikeFileDateTextBox.setText(orginalInformation.strikeFileDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.strikeFileDate.getTime())) : "");
        relatedCaseNumberTextBox.setText(orginalInformation.relatedCaseNumber != null ? orginalInformation.relatedCaseNumber : "");
//        medCaseNumberTextBox.setText(orginalInformation.medCaseNumber != null ? orginalInformation.medCaseNumber : "");
        descriptionTextBox.setText(orginalInformation.description != null ? orginalInformation.description : "");
        unitSizeTextBox.setText(orginalInformation.unitSize != null ? orginalInformation.unitSize : "");
        unauthorizedStrikeCheckBox.setSelected(orginalInformation.unauthorizedStrike == true);
        noticeOfIntentToStrikeOnlyCheckBox.setSelected(orginalInformation.noticeOfIntentToStrikeOnly == true);
        intendedDateStrikeTextBox.setText(orginalInformation.intendedDateStrike != null ? Global.mmddyyyy.format(new Date(orginalInformation.intendedDateStrike.getTime())) : "");
        noticeOfIntentToPicketOnlyCheckBox.setSelected(orginalInformation.noticeOfIntentToPicketOnly == true);
        intendedDatePicketTextBox.setText(orginalInformation.intendedDatePicket != null ? Global.mmddyyyy.format(new Date(orginalInformation.intendedDatePicket.getTime())) : "");
        informationCheckBox.setSelected(orginalInformation.informational == true);
        noticeOfIntentToStrikeAndPicketCheckBox.setSelected(orginalInformation.noticeOfIntentToStrikeAndPicket == true);
        strikeOccuredComboBox.setSelectedItem(orginalInformation.strikeOccured != null ? orginalInformation.strikeOccured : " ");
        strikeStatusComboBox.setSelectedItem(orginalInformation.strikeStatus != null ? orginalInformation.strikeStatus : " ");
        strikeBeganTextBox.setText(orginalInformation.strikeBegan != null ? Global.mmddyyyy.format(new Date(orginalInformation.strikeBegan.getTime())) : "");
        strikeEndedTextBox.setText(orginalInformation.strikeEnded != null ? Global.mmddyyyy.format(new Date(orginalInformation.strikeEnded.getTime())) : "");
        totalNumberOfDaysTextBox.setText(orginalInformation.totalNumberOfDays != null ? orginalInformation.totalNumberOfDays : "");
        mediatorAppointedComboBox.setSelectedItem(orginalInformation.strikeMediatorAppointedID != null ? Mediator.getMediatorNameByID(orginalInformation.strikeMediatorAppointedID) : "");
        strikeNotesTextArea.setText(orginalInformation.strikeNotes != null ? orginalInformation.strikeNotes : "");
    }
    
    public void saveInformation() {
        
        MEDCase newCaseInformation = new MEDCase();
        
        newCaseInformation.strikeFileDate = strikeFileDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(strikeFileDateTextBox.getText()));
        newCaseInformation.relatedCaseNumber = relatedCaseNumberTextBox.getText().equals("") ? null : relatedCaseNumberTextBox.getText();
//        newCaseInformation.medCaseNumber = medCaseNumberTextBox.getText().equals("") ? null : medCaseNumberTextBox.getText();
        newCaseInformation.description = descriptionTextBox.getText().equals("") ? null : descriptionTextBox.getText();
        newCaseInformation.unitSize = unitSizeTextBox.getText().equals("") ? null : unitSizeTextBox.getText();
        newCaseInformation.unauthorizedStrike = unauthorizedStrikeCheckBox.isSelected();
        newCaseInformation.noticeOfIntentToStrikeOnly = noticeOfIntentToStrikeOnlyCheckBox.isSelected();
        newCaseInformation.intendedDateStrike = intendedDateStrikeTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(intendedDateStrikeTextBox.getText()));
        newCaseInformation.noticeOfIntentToPicketOnly = noticeOfIntentToPicketOnlyCheckBox.isSelected();        
        newCaseInformation.intendedDatePicket = intendedDatePicketTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(intendedDatePicketTextBox.getText()));
        newCaseInformation.informational = informationCheckBox.isSelected();        
        newCaseInformation.noticeOfIntentToStrikeAndPicket = noticeOfIntentToStrikeAndPicketCheckBox.isSelected();        
        newCaseInformation.informational = informationCheckBox.isSelected();        
        newCaseInformation.strikeOccured = strikeOccuredComboBox.getSelectedItem().toString().trim().equals("") ? null : strikeOccuredComboBox.getSelectedItem().toString();
        newCaseInformation.strikeStatus = strikeStatusComboBox.getSelectedItem().toString().trim().equals("") ? null : strikeStatusComboBox.getSelectedItem().toString();
        newCaseInformation.strikeBegan = strikeBeganTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(strikeBeganTextBox.getText()));
        newCaseInformation.strikeEnded = strikeEndedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(strikeEndedTextBox.getText()));
        newCaseInformation.strikeEnded = strikeEndedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(strikeEndedTextBox.getText()));
        newCaseInformation.totalNumberOfDays = totalNumberOfDaysTextBox.getText().equals("") ? null : totalNumberOfDaysTextBox.getText();
        newCaseInformation.strikeMediatorAppointedID = mediatorAppointedComboBox.getSelectedItem().toString().equals("") ? null : Mediator.getMediatorIDByName(mediatorAppointedComboBox.getSelectedItem().toString());
        newCaseInformation.strikeNotes = strikeNotesTextArea.getText().equals("") ? null : strikeNotesTextArea.getText();
        
        MEDCase.updateStrikeInformation(newCaseInformation, orginalInformation);
    }
    
    public void clearAll() {
        strikeFileDateTextBox.setText("");
        relatedCaseNumberTextBox.setText("");
//        relatedCaseNumberTextBox.setText("");
        descriptionTextBox.setText("");
        unitSizeTextBox.setText("");
        unauthorizedStrikeCheckBox.setSelected(false);
        noticeOfIntentToStrikeOnlyCheckBox.setSelected(false);
        intendedDateStrikeTextBox.setText("");
        noticeOfIntentToPicketOnlyCheckBox.setSelected(false);
        intendedDatePicketTextBox.setText("");
        informationCheckBox.setSelected(false);
        noticeOfIntentToStrikeAndPicketCheckBox.setSelected(false);
        
        strikeOccuredComboBox.setSelectedItem(" ");
        strikeStatusComboBox.setSelectedItem(" ");
        strikeBeganTextBox.setText("");
        strikeEndedTextBox.setText("");
        totalNumberOfDaysTextBox.setText("");
        mediatorAppointedComboBox.setSelectedItem("");
        strikeNotesTextArea.setText("");
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

        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        informationCheckBox = new javax.swing.JCheckBox();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        unauthorizedStrikeCheckBox = new javax.swing.JCheckBox();
        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        noticeOfIntentToPicketOnlyCheckBox = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        strikeFileDateTextBox = new com.alee.extended.date.WebDateField();
        intendedDatePicketTextBox = new com.alee.extended.date.WebDateField();
        noticeOfIntentToStrikeAndPicketCheckBox = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        descriptionTextBox = new javax.swing.JTextField();
        intendedDateStrikeTextBox = new com.alee.extended.date.WebDateField();
        jLabel10 = new javax.swing.JLabel();
        noticeOfIntentToStrikeOnlyCheckBox = new javax.swing.JCheckBox();
        unitSizeTextBox = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        relatedCaseNumberTextBox = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        strikeOccuredComboBox = new javax.swing.JComboBox<>();
        jLabel14 = new javax.swing.JLabel();
        strikeStatusComboBox = new javax.swing.JComboBox<>();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        strikeNotesTextArea = new javax.swing.JTextPane();
        strikeBeganTextBox = new com.alee.extended.date.WebDateField();
        strikeEndedTextBox = new com.alee.extended.date.WebDateField();
        totalNumberOfDaysTextBox = new javax.swing.JTextField();
        mediatorAppointedComboBox = new javax.swing.JComboBox<>();

        jLabel5.setText("Unit Size:");

        jLabel2.setText("Related Case Number:");

        informationCheckBox.setEnabled(false);

        jLabel11.setText("Intended Date:");

        jLabel12.setText("Intended Date:");

        unauthorizedStrikeCheckBox.setEnabled(false);

        jLabel6.setText("Unauthorized Strike:");

        jLabel1.setText("File Date:");

        noticeOfIntentToPicketOnlyCheckBox.setEnabled(false);

        jLabel4.setText("Description:");

        strikeFileDateTextBox.setEditable(false);
        strikeFileDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        strikeFileDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        strikeFileDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        strikeFileDateTextBox.setEnabled(false);
        strikeFileDateTextBox.setDateFormat(Global.mmddyyyy);
        strikeFileDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                strikeFileDateTextBoxMouseClicked(evt);
            }
        });

        intendedDatePicketTextBox.setEditable(false);
        intendedDatePicketTextBox.setBackground(new java.awt.Color(238, 238, 238));
        intendedDatePicketTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        intendedDatePicketTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        intendedDatePicketTextBox.setEnabled(false);
        intendedDatePicketTextBox.setDateFormat(Global.mmddyyyy);
        intendedDatePicketTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                intendedDatePicketTextBoxMouseClicked(evt);
            }
        });
        intendedDatePicketTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                intendedDatePicketTextBoxActionPerformed(evt);
            }
        });

        noticeOfIntentToStrikeAndPicketCheckBox.setEnabled(false);

        jLabel9.setText("Informational:");

        descriptionTextBox.setBackground(new java.awt.Color(238, 238, 238));
        descriptionTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        descriptionTextBox.setEnabled(false);

        intendedDateStrikeTextBox.setEditable(false);
        intendedDateStrikeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        intendedDateStrikeTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        intendedDateStrikeTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        intendedDateStrikeTextBox.setEnabled(false);
        intendedDateStrikeTextBox.setDateFormat(Global.mmddyyyy);
        intendedDateStrikeTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                intendedDateStrikeTextBoxMouseClicked(evt);
            }
        });

        jLabel10.setText("Notice of Intent to Strike AND Picket:");

        noticeOfIntentToStrikeOnlyCheckBox.setEnabled(false);

        unitSizeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        unitSizeTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unitSizeTextBox.setEnabled(false);

        jLabel7.setText("Notice of Intent to Strike Only:");

        relatedCaseNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        relatedCaseNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        relatedCaseNumberTextBox.setEnabled(false);
        relatedCaseNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatedCaseNumberTextBoxMouseClicked(evt);
            }
        });

        jLabel8.setText("Notice of Intent to Picket Only:");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel10)
                    .addComponent(jLabel9)
                    .addComponent(jLabel8)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel5)
                    .addComponent(jLabel4)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(unauthorizedStrikeCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(unitSizeTextBox)
                            .addComponent(strikeFileDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(relatedCaseNumberTextBox, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(descriptionTextBox)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(noticeOfIntentToStrikeOnlyCheckBox)
                                    .addComponent(noticeOfIntentToPicketOnlyCheckBox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel12)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(intendedDatePicketTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel11)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(intendedDateStrikeTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE))))
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(noticeOfIntentToStrikeAndPicketCheckBox)
                                    .addComponent(informationCheckBox))
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(strikeFileDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(relatedCaseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(unitSizeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(unauthorizedStrikeCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(intendedDateStrikeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel11))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(noticeOfIntentToStrikeOnlyCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(intendedDatePicketTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(noticeOfIntentToPicketOnlyCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(informationCheckBox))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(noticeOfIntentToStrikeAndPicketCheckBox))
                .addContainerGap(38, Short.MAX_VALUE))
        );

        jLabel13.setText("Strike Occured:");

        strikeOccuredComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No", " " }));
        strikeOccuredComboBox.setSelectedIndex(2);
        strikeOccuredComboBox.setEnabled(false);

        jLabel14.setText("Strike Status:");

        strikeStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "TA Reached", "Settlement Reached", "STRIKE", "Negotiations Continued", " " }));
        strikeStatusComboBox.setSelectedIndex(4);
        strikeStatusComboBox.setEnabled(false);

        jLabel15.setText("Strike Began:");

        jLabel16.setText("Strike Ended:");

        jLabel17.setText("Total Number of Days:");

        jLabel18.setText("Mediator Appointed:");

        jLabel19.setText("Notes:");

        strikeNotesTextArea.setBackground(new java.awt.Color(238, 238, 238));
        strikeNotesTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        strikeNotesTextArea.setEnabled(false);
        jScrollPane1.setViewportView(strikeNotesTextArea);

        strikeBeganTextBox.setEditable(false);
        strikeBeganTextBox.setBackground(new java.awt.Color(238, 238, 238));
        strikeBeganTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        strikeBeganTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        strikeBeganTextBox.setEnabled(false);
        strikeBeganTextBox.setDateFormat(Global.mmddyyyy);
        strikeBeganTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                strikeBeganTextBoxMouseClicked(evt);
            }
        });

        strikeEndedTextBox.setEditable(false);
        strikeEndedTextBox.setBackground(new java.awt.Color(238, 238, 238));
        strikeEndedTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        strikeEndedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        strikeEndedTextBox.setEnabled(false);
        strikeEndedTextBox.setDateFormat(Global.mmddyyyy);
        strikeEndedTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                strikeEndedTextBoxMouseClicked(evt);
            }
        });

        totalNumberOfDaysTextBox.setBackground(new java.awt.Color(238, 238, 238));
        totalNumberOfDaysTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        totalNumberOfDaysTextBox.setEnabled(false);

        mediatorAppointedComboBox.setEnabled(false);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel19)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel18)
                            .addComponent(jLabel17)
                            .addComponent(jLabel16)
                            .addComponent(jLabel15)
                            .addComponent(jLabel14)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(strikeOccuredComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(strikeStatusComboBox, 0, 372, Short.MAX_VALUE)
                            .addComponent(strikeBeganTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(strikeEndedTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalNumberOfDaysTextBox)
                            .addComponent(mediatorAppointedComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(6, 6, 6)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(strikeOccuredComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(strikeStatusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(strikeBeganTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(strikeEndedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(totalNumberOfDaysTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(mediatorAppointedComboBox)
                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel19)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void strikeFileDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_strikeFileDateTextBoxMouseClicked
        clearDate(strikeFileDateTextBox, evt);
    }//GEN-LAST:event_strikeFileDateTextBoxMouseClicked

    private void strikeBeganTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_strikeBeganTextBoxMouseClicked
        clearDate(strikeBeganTextBox, evt);
    }//GEN-LAST:event_strikeBeganTextBoxMouseClicked

    private void strikeEndedTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_strikeEndedTextBoxMouseClicked
        clearDate(strikeEndedTextBox, evt);
    }//GEN-LAST:event_strikeEndedTextBoxMouseClicked

    private void intendedDatePicketTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_intendedDatePicketTextBoxActionPerformed
//        clearDate(intendedDatePicketTextBox, evt);
    }//GEN-LAST:event_intendedDatePicketTextBoxActionPerformed

    private void intendedDateStrikeTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_intendedDateStrikeTextBoxMouseClicked
        clearDate(intendedDateStrikeTextBox, evt);
    }//GEN-LAST:event_intendedDateStrikeTextBoxMouseClicked

    private void intendedDatePicketTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_intendedDatePicketTextBoxMouseClicked
        clearDate(intendedDatePicketTextBox, evt);
    }//GEN-LAST:event_intendedDatePicketTextBoxMouseClicked

    private void relatedCaseNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relatedCaseNumberTextBoxMouseClicked
        if(relatedCaseNumberTextBox.isEnabled()) {
            if(evt.getButton() == MouseEvent.BUTTON3) {
                MEDClearCaseDialog clear = new MEDClearCaseDialog((JFrame) Global.root.getRootPane().getParent(), true);
                if(clear.reset) {
                    relatedCaseNumberTextBox.setText("");
                }
                clear.dispose();
            } else if(evt.getClickCount() == 2) {
                MEDAddRelatedCaseDialog related = new MEDAddRelatedCaseDialog((JFrame) Global.root.getRootPane().getParent(), true);
                relatedCaseNumberTextBox.setText(related.getRelatedCase());
                related.dispose();
            }
        }
    }//GEN-LAST:event_relatedCaseNumberTextBoxMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField descriptionTextBox;
    private javax.swing.JCheckBox informationCheckBox;
    private com.alee.extended.date.WebDateField intendedDatePicketTextBox;
    private com.alee.extended.date.WebDateField intendedDateStrikeTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> mediatorAppointedComboBox;
    private javax.swing.JCheckBox noticeOfIntentToPicketOnlyCheckBox;
    private javax.swing.JCheckBox noticeOfIntentToStrikeAndPicketCheckBox;
    private javax.swing.JCheckBox noticeOfIntentToStrikeOnlyCheckBox;
    private javax.swing.JTextField relatedCaseNumberTextBox;
    private com.alee.extended.date.WebDateField strikeBeganTextBox;
    private com.alee.extended.date.WebDateField strikeEndedTextBox;
    private com.alee.extended.date.WebDateField strikeFileDateTextBox;
    private javax.swing.JTextPane strikeNotesTextArea;
    private javax.swing.JComboBox<String> strikeOccuredComboBox;
    private javax.swing.JComboBox<String> strikeStatusComboBox;
    private javax.swing.JTextField totalNumberOfDaysTextBox;
    private javax.swing.JCheckBox unauthorizedStrikeCheckBox;
    private javax.swing.JTextField unitSizeTextBox;
    // End of variables declaration//GEN-END:variables
}
