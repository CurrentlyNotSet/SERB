/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bunumber;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.List;
import javax.swing.JFrame;
import org.apache.commons.lang.StringUtils;
import parker.serb.Global;
import parker.serb.employer.employerSearch;
import parker.serb.sql.BargainingUnit;
import parker.serb.sql.County;
import parker.serb.sql.Employer;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class BUInformationNewDialog extends javax.swing.JDialog {

    String number = "";
    /**
     * Creates new form BUInformationUpdateDialog
     * @param parent
     * @param modal
     * @param passedNumber
     */
    public BUInformationNewDialog(java.awt.Frame parent, boolean modal, String passedNumber) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        loadDefaults(passedNumber);
        setVisible(true);
    }

    public String getNumber() {
        return number;
    }

    private void loadDefaults(String passedNumber){
        Employer loadedEmployer = null;
        enableAll();
        loadDropdowns();

        loadedEmployer = Employer.loadEmployerByID(StringUtils.left(passedNumber, 4));

        if (loadedEmployer != null){
            employerNumberTextBox.setText(loadedEmployer.employerIDNumber == null ? "" : loadedEmployer.employerIDNumber);
            employerNameTextBox.setText(loadedEmployer.employerName == null ? "" : loadedEmployer.employerName);
            countyComboBox.setSelectedItem(loadedEmployer.county == null ? "" : loadedEmployer.county);
        }
        this.pack();
    }

    private String getCertStatus(String certString) {
        String cert = "";

        switch(certString) {
            case "B":
                cert = "Board";
                break;
            case "D":
                cert = "Deemed";
                break;
            case "U":
                cert = "Unknown";
                break;
            case "Board":
                cert = "B";
                break;
            case "Deemed":
                cert = "D";
                break;
            case "Unknown":
                cert = "U";
                break;
        }

        return cert;
    }

    private void disableAll() {
        employerNumberTextBox.setEnabled(false);
        employerNumberTextBox.setBackground(new Color(238,238,238));
        employerNameTextBox.setEnabled(false);
        employerNameTextBox.setBackground(new Color(238,238,238));
        unionTextBox.setEnabled(false);
        unionTextBox.setBackground(new Color(238,238,238));
        localTextBox.setEnabled(false);
        localTextBox.setBackground(new Color(238,238,238));
        countyComboBox.setEnabled(false);
        CertStatusComboBox.setEnabled(false);
        activeCheckBox.setEnabled(false);
        unitDescriptionTextArea.setEnabled(false);
        unitDescriptionTextArea.setBackground(new Color(238,238,238));

        unitNumberTextBox.setEnabled(false);
        unitNumberTextBox.setBackground(new Color(238,238,238));
        certificationDateTextBox.setEnabled(false);
        certificationDateTextBox.setBackground(new Color(238,238,238));
        caseReferenceTextBox.setEnabled(false);
        caseReferenceTextBox.setBackground(new Color(238,238,238));
        jurisdictionTextBox.setEnabled(false);
        jurisdictionTextBox.setBackground(new Color(238,238,238));
        buCodeTextBox.setEnabled(false);
        buCodeTextBox.setBackground(new Color(238,238,238));
        strikeCheckBox.setEnabled(false);
        notesTextArea.setEnabled(false);
        notesTextArea.setBackground(new Color(238,238,238));

    }

    private void enableAll() {
        employerNumberTextBox.setEnabled(true);
        employerNumberTextBox.setBackground(Color.white);
        employerNameTextBox.setEnabled(true);
        employerNameTextBox.setBackground(Color.white);
        unionTextBox.setEnabled(true);
        unionTextBox.setBackground(Color.white);
        localTextBox.setEnabled(true);
        localTextBox.setBackground(Color.white);
        countyComboBox.setEnabled(true);
        CertStatusComboBox.setEnabled(true);
        activeCheckBox.setEnabled(true);
        unitDescriptionTextArea.setEnabled(true);
        unitDescriptionTextArea.setBackground(Color.white);

        unitNumberTextBox.setEnabled(true);
        unitNumberTextBox.setBackground(Color.white);
        certificationDateTextBox.setEnabled(true);
        certificationDateTextBox.setBackground(Color.white);
        caseReferenceTextBox.setEnabled(true);
        caseReferenceTextBox.setBackground(Color.white);
        jurisdictionTextBox.setEnabled(true);
        jurisdictionTextBox.setBackground(Color.white);
        buCodeTextBox.setEnabled(true);
        buCodeTextBox.setBackground(Color.white);
        strikeCheckBox.setEnabled(true);
        notesTextArea.setEnabled(true);
        notesTextArea.setBackground(Color.white);
    }


    private void loadDropdowns() {
        //load county
        List<String> countyList = County.loadCountyList();

        countyComboBox.removeAllItems();
        countyComboBox.addItem("");

        for (Object singleCounty : countyList) {
            County county = (County) singleCounty;
            countyComboBox.addItem(county.countyName);
        }

        //load cert status
        CertStatusComboBox.removeAllItems();
        CertStatusComboBox.addItem("");
        CertStatusComboBox.addItem("Board");
        CertStatusComboBox.addItem("Deemed");
        CertStatusComboBox.addItem("Unknown");
    }

    private void saveInformation() {
        BargainingUnit buUpdate = new BargainingUnit();

        buUpdate.employerNumber = employerNumberTextBox.getText();
        buUpdate.buEmployerName = employerNameTextBox.getText();
        buUpdate.lUnion = unionTextBox.getText();
        buUpdate.local = localTextBox.getText();
        buUpdate.county = countyComboBox.getSelectedItem().toString();
        buUpdate.cert = getCertStatus(CertStatusComboBox.getSelectedItem().toString());
        buUpdate.enabled = activeCheckBox.isSelected();
        buUpdate.unitDescription = unitDescriptionTextArea.getText().trim();

        buUpdate.unitNumber = unitNumberTextBox.getText();
        buUpdate.certDate = certificationDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(certificationDateTextBox.getText()));

        if(!caseReferenceTextBox.getText().equals("")) {
           buUpdate.caseRefYear = caseReferenceTextBox.getText().split("-")[0];
           buUpdate.caseRefSection = caseReferenceTextBox.getText().split("-")[1];
           buUpdate.caseRefMonth = caseReferenceTextBox.getText().split("-")[2];
           buUpdate.caseRefSequence = caseReferenceTextBox.getText().split("-")[3];
        } else {
           buUpdate.caseRefYear = null;
           buUpdate.caseRefSection = null;
           buUpdate.caseRefMonth = null;
           buUpdate.caseRefSequence = null;
        }

        buUpdate.jurisdiction = jurisdictionTextBox.getText();
        buUpdate.lGroup = buCodeTextBox.getText();
        buUpdate.strike = strikeCheckBox.isSelected();
        buUpdate.notes = notesTextArea.getText().trim();

        BargainingUnit.createBU(buUpdate);

        number = buUpdate.employerNumber + "-" + buUpdate.unitNumber;
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

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        employerNumberTextBox = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        employerNameTextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        unionTextBox = new javax.swing.JTextField();
        localTextBox = new javax.swing.JTextField();
        countyComboBox = new javax.swing.JComboBox<>();
        CertStatusComboBox = new javax.swing.JComboBox<>();
        activeCheckBox = new javax.swing.JCheckBox();
        jScrollPane1 = new javax.swing.JScrollPane();
        unitDescriptionTextArea = new javax.swing.JTextArea();
        jPanel5 = new javax.swing.JPanel();
        certificationDateTextBox = new com.alee.extended.date.WebDateField();
        jLabel3 = new javax.swing.JLabel();
        unitNumberTextBox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        notesTextArea = new javax.swing.JTextArea();
        caseReferenceTextBox = new javax.swing.JTextField();
        jurisdictionTextBox = new javax.swing.JTextField();
        buCodeTextBox = new javax.swing.JTextField();
        strikeCheckBox = new javax.swing.JCheckBox();
        updateButton = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bargining Unit Information");

        jLabel2.setText("Employer Number:");

        employerNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employerNumberTextBoxMouseClicked(evt);
            }
        });

        jLabel4.setText("Employer Name");

        jLabel5.setText("Union:");

        jLabel6.setText("Local:");

        jLabel7.setText("County:");

        jLabel8.setText("Cert Status:");

        jLabel9.setText("Active:");

        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel10.setText("Unit Description");

        countyComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        CertStatusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        unitDescriptionTextArea.setColumns(20);
        unitDescriptionTextArea.setLineWrap(true);
        unitDescriptionTextArea.setRows(5);
        unitDescriptionTextArea.setWrapStyleWord(true);
        jScrollPane1.setViewportView(unitDescriptionTextArea);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 344, Short.MAX_VALUE)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel9)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(employerNumberTextBox)
                            .addComponent(employerNameTextBox)
                            .addComponent(unionTextBox)
                            .addComponent(localTextBox)
                            .addComponent(countyComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CertStatusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(activeCheckBox)
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(employerNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(employerNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(unionTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(localTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(countyComboBox)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(CertStatusComboBox)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(activeCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        certificationDateTextBox.setEditable(false);
        certificationDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        certificationDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        certificationDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        certificationDateTextBox.setEnabled(false);
        certificationDateTextBox.setDateFormat(Global.mmddyyyy);

        certificationDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            certificationDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    certificationDateTextBoxMouseClicked(evt);
                }
            });

            jLabel3.setText("Unit Number:");

            jLabel11.setText("Certification Date:");

            jLabel12.setText("Case Reference:");

            jLabel13.setText("Jurisdiction:");

            jLabel14.setText("BU Code:");

            jLabel15.setText("Strike:");

            jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel16.setText("Notes");

            notesTextArea.setColumns(20);
            notesTextArea.setLineWrap(true);
            notesTextArea.setRows(5);
            notesTextArea.setWrapStyleWord(true);
            jScrollPane2.setViewportView(notesTextArea);

            javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
            jPanel5.setLayout(jPanel5Layout);
            jPanel5Layout.setHorizontalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel5Layout.createSequentialGroup()
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel15)
                                .addComponent(jLabel14)
                                .addComponent(jLabel13)
                                .addComponent(jLabel12)
                                .addComponent(jLabel11)
                                .addComponent(jLabel3))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(unitNumberTextBox)
                                .addComponent(certificationDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(caseReferenceTextBox)
                                .addComponent(jurisdictionTextBox)
                                .addComponent(buCodeTextBox)
                                .addGroup(jPanel5Layout.createSequentialGroup()
                                    .addComponent(strikeCheckBox)
                                    .addGap(0, 0, Short.MAX_VALUE))))
                        .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 372, Short.MAX_VALUE))
                    .addContainerGap())
            );
            jPanel5Layout.setVerticalGroup(
                jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel5Layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(unitNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(certificationDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel12)
                        .addComponent(caseReferenceTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel13)
                        .addComponent(jurisdictionTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel14)
                        .addComponent(buCodeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(strikeCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(48, 48, 48)
                    .addComponent(jLabel16)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
            );

            updateButton.setText("Save");
            updateButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    updateButtonActionPerformed(evt);
                }
            });

            jButton2.setText("Cancel");
            jButton2.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    jButton2ActionPerformed(evt);
                }
            });

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addGap(271, 271, 271)
                                    .addComponent(updateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(18, 18, 18)
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(updateButton)
                        .addComponent(jButton2))
                    .addContainerGap())
            );

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void certificationDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_certificationDateTextBoxMouseClicked
        clearDate(certificationDateTextBox, evt);
    }//GEN-LAST:event_certificationDateTextBoxMouseClicked

    private void updateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_updateButtonActionPerformed
        saveInformation();
        disableAll();
//        dispose();
        setVisible(false);
    }//GEN-LAST:event_updateButtonActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        disableAll();
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void employerNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employerNumberTextBoxMouseClicked
        if(evt.getClickCount() == 2) {
            employerSearch search = new employerSearch((JFrame) Global.root.getParent(), rootPaneCheckingEnabled);
            Employer emp = Employer.loadEmployerByID(search.getEmployerNumber());
            search.dispose();
            employerNameTextBox.setText(emp.employerName);
            activeCheckBox.setSelected(true);
            unitNumberTextBox.setText("00");
            CertStatusComboBox.setSelectedItem("");
            employerNumberTextBox.setText(emp.employerIDNumber);
        }
    }//GEN-LAST:event_employerNumberTextBoxMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> CertStatusComboBox;
    private javax.swing.JCheckBox activeCheckBox;
    private javax.swing.JTextField buCodeTextBox;
    private javax.swing.JTextField caseReferenceTextBox;
    private com.alee.extended.date.WebDateField certificationDateTextBox;
    private javax.swing.JComboBox<String> countyComboBox;
    private javax.swing.JTextField employerNameTextBox;
    private javax.swing.JTextField employerNumberTextBox;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextField jurisdictionTextBox;
    private javax.swing.JTextField localTextBox;
    private javax.swing.JTextArea notesTextArea;
    private javax.swing.JCheckBox strikeCheckBox;
    private javax.swing.JTextField unionTextBox;
    private javax.swing.JTextArea unitDescriptionTextArea;
    private javax.swing.JTextField unitNumberTextBox;
    private javax.swing.JButton updateButton;
    // End of variables declaration//GEN-END:variables
}
