/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import java.awt.Color;
import parker.serb.Global;
import parker.serb.sql.MEDCase;

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
    }
    
    public void enableUpdate() {
        strikeFileDateTextBox.setEnabled(true);
        strikeFileDateTextBox.setBackground(Color.white);
        //skip med case number
        medCaseNumberTextBox.setEnabled(true);
        medCaseNumberTextBox.setBackground(Color.white);
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
        strikeFileDateTextBox.setEnabled(false);
        strikeFileDateTextBox.setBackground(new Color(238,238,238));
        //skip med case number
        medCaseNumberTextBox.setEnabled(false);
        medCaseNumberTextBox.setBackground(Color.white);
        descriptionTextBox.setEnabled(false);
        descriptionTextBox.setBackground(Color.white);
        unitSizeTextBox.setEnabled(false);
        unitSizeTextBox.setBackground(Color.white);
        unauthorizedStrikeCheckBox.setEnabled(false);
        noticeOfIntentToStrikeOnlyCheckBox.setEnabled(false);
        intendedDateStrikeTextBox.setEnabled(false);
        intendedDateStrikeTextBox.setBackground(Color.white);
        noticeOfIntentToPicketOnlyCheckBox.setEnabled(false);
        intendedDatePicketTextBox.setEnabled(false);
        intendedDatePicketTextBox.setBackground(Color.white);
        informationCheckBox.setEnabled(false);
        noticeOfIntentToStrikeAndPicketCheckBox.setEnabled(false);
        
        strikeOccuredComboBox.setEnabled(false);
        strikeStatusComboBox.setEnabled(false);
        strikeBeganTextBox.setEnabled(false);
        strikeBeganTextBox.setBackground(Color.white);
        strikeEndedTextBox.setEnabled(false);
        strikeEndedTextBox.setBackground(Color.white);
        //skip total number of days
        mediatorAppointedComboBox.setEnabled(false);
        strikeNotesTextArea.setEnabled(false);
        strikeNotesTextArea.setBackground(Color.white);
        
        if(save) {
            saveInformation();
        }
        
        loadInformaiton();
    }
    
    public void loadInformaiton() {
        
//        orginalInformation = MEDCase.loadStrikeInformation();
        
        
    }
    
    public void saveInformation() {
        
        
        
    }
    
    public void clearAll() {
        strikeFileDateTextBox.setText("");
        strikeCaseNumberTextBox.setText("");
        medCaseNumberTextBox.setText("");
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
        mediatorAppointedComboBox.setSelectedItem(" ");
        strikeNotesTextArea.setText("");
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
        medCaseNumberTextBox = new javax.swing.JTextField();
        descriptionTextBox = new javax.swing.JTextField();
        intendedDateStrikeTextBox = new com.alee.extended.date.WebDateField();
        jLabel10 = new javax.swing.JLabel();
        noticeOfIntentToStrikeOnlyCheckBox = new javax.swing.JCheckBox();
        jLabel3 = new javax.swing.JLabel();
        unitSizeTextBox = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        strikeCaseNumberTextBox = new javax.swing.JTextField();
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

        jLabel2.setText("Strike Case Number:");

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
        strikeFileDateTextBox.setDateFormat(Global.mmddyyyy);
        intendedDatePicketTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                intendedDatePicketTextBoxMouseClicked(evt);
            }
        });

        noticeOfIntentToStrikeAndPicketCheckBox.setEnabled(false);

        jLabel9.setText("Informational:");

        medCaseNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        medCaseNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        medCaseNumberTextBox.setEnabled(false);

        descriptionTextBox.setBackground(new java.awt.Color(238, 238, 238));
        descriptionTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        descriptionTextBox.setEnabled(false);

        intendedDateStrikeTextBox.setEditable(false);
        intendedDateStrikeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        intendedDateStrikeTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        intendedDateStrikeTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        intendedDateStrikeTextBox.setEnabled(false);
        strikeFileDateTextBox.setDateFormat(Global.mmddyyyy);
        intendedDateStrikeTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                intendedDateStrikeTextBoxMouseClicked(evt);
            }
        });

        jLabel10.setText("Notice of Intent to Strike AND Picket:");

        noticeOfIntentToStrikeOnlyCheckBox.setEnabled(false);
        noticeOfIntentToStrikeOnlyCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                noticeOfIntentToStrikeOnlyCheckBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("MED Case Number:");

        unitSizeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        unitSizeTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        unitSizeTextBox.setEnabled(false);

        jLabel7.setText("Notice of Intent to Strike Only:");

        strikeCaseNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        strikeCaseNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        strikeCaseNumberTextBox.setEnabled(false);

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
                    .addComponent(jLabel3)
                    .addComponent(jLabel2)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(unauthorizedStrikeCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(unitSizeTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(noticeOfIntentToStrikeOnlyCheckBox)
                                    .addComponent(noticeOfIntentToPicketOnlyCheckBox)
                                    .addComponent(informationCheckBox)
                                    .addComponent(noticeOfIntentToStrikeAndPicketCheckBox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel11)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(intendedDateStrikeTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 158, Short.MAX_VALUE)
                                    .addComponent(intendedDatePicketTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                            .addComponent(strikeFileDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(strikeCaseNumberTextBox)
                            .addComponent(medCaseNumberTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descriptionTextBox, javax.swing.GroupLayout.Alignment.LEADING))
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
                    .addComponent(strikeCaseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(medCaseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(descriptionTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(unitSizeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(unauthorizedStrikeCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(intendedDateStrikeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(noticeOfIntentToStrikeOnlyCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(noticeOfIntentToPicketOnlyCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(intendedDatePicketTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(informationCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(noticeOfIntentToStrikeAndPicketCheckBox)
                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel13.setText("Strike Occured:");

        strikeOccuredComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        strikeOccuredComboBox.setEnabled(false);

        jLabel14.setText("Strike Status:");

        strikeStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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
        strikeFileDateTextBox.setDateFormat(Global.mmddyyyy);
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
        strikeFileDateTextBox.setDateFormat(Global.mmddyyyy);
        strikeEndedTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                strikeEndedTextBoxMouseClicked(evt);
            }
        });

        totalNumberOfDaysTextBox.setBackground(new java.awt.Color(238, 238, 238));
        totalNumberOfDaysTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        totalNumberOfDaysTextBox.setEnabled(false);

        mediatorAppointedComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
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
                            .addComponent(strikeOccuredComboBox, 0, 372, Short.MAX_VALUE)
                            .addComponent(strikeStatusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(strikeBeganTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(strikeEndedTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(totalNumberOfDaysTextBox)
                            .addComponent(mediatorAppointedComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(strikeOccuredComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
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
//        clearDate(FF1OrderDate, evt);
    }//GEN-LAST:event_strikeFileDateTextBoxMouseClicked

    private void noticeOfIntentToStrikeOnlyCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_noticeOfIntentToStrikeOnlyCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_noticeOfIntentToStrikeOnlyCheckBoxActionPerformed

    private void intendedDateStrikeTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_intendedDateStrikeTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_intendedDateStrikeTextBoxMouseClicked

    private void intendedDatePicketTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_intendedDatePicketTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_intendedDatePicketTextBoxMouseClicked

    private void strikeBeganTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_strikeBeganTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_strikeBeganTextBoxMouseClicked

    private void strikeEndedTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_strikeEndedTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_strikeEndedTextBoxMouseClicked


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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField medCaseNumberTextBox;
    private javax.swing.JComboBox<String> mediatorAppointedComboBox;
    private javax.swing.JCheckBox noticeOfIntentToPicketOnlyCheckBox;
    private javax.swing.JCheckBox noticeOfIntentToStrikeAndPicketCheckBox;
    private javax.swing.JCheckBox noticeOfIntentToStrikeOnlyCheckBox;
    private com.alee.extended.date.WebDateField strikeBeganTextBox;
    private javax.swing.JTextField strikeCaseNumberTextBox;
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
