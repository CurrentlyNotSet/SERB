/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.docket;

import com.alee.extended.date.WebCalendar;
import com.alee.utils.swing.Customizer;
import java.awt.event.ActionEvent;
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
import parker.serb.CMDS.CMDSUpdateAllGroupCasesDialog;
import parker.serb.CMDS.CMDSUpdateInventoryStatusLineDialog;
import parker.serb.Global;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CMDSHistoryCategory;
import parker.serb.sql.CMDSHistoryDescription;
import parker.serb.sql.CaseNumber;
import parker.serb.sql.User;
import parker.serb.util.FileService;

/**
 *
 * @author parkerjohnston
 */
public class scanCMDSFileDialog extends javax.swing.JDialog {

    String selectedSection = "";
    boolean orgProcess = false;

    /**
     * Creates new form scanFileDialog
     *
     * @param parent
     * @param modal
     * @param file
     * @param time
     * @param section
     */
    public scanCMDSFileDialog(java.awt.Frame parent, boolean modal, String file, String section, String time) {
        super(parent, modal);
        initComponents();
        selectedSection = section;
        loadData(section, file, time);
        addListeners(section);
        this.pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void loadData(String section, String file, String time) {
        fileNameTextBox.setText(file);
        loadToComboBox(section);
        loadTypeComboBox();
        scanDateTextBox.setText(time.split(" ")[0]);
        hourTextBox.setText(time.split(" ")[1].split(":")[0]);
        minuteTextBox.setText(time.split(" ")[1].split(":")[1]);
        amPMComboBox.setSelectedItem(time.split(" ")[2]);
    }

    private void addListeners(String section) {
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
            public void focusGained(FocusEvent e) {
            }

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

        toComboBox.addActionListener((ActionEvent e) -> {
            enableButton();
        });

        typeComboBox.addActionListener((ActionEvent e) -> {
            if (!typeComboBox.getSelectedItem().toString().equals("")) {
                loadType2ComboBox();
            }
        });

        descriptionComboBox.addActionListener((ActionEvent e) -> {
            if (descriptionComboBox != null) {
                enableButton();
            } else {
                fileButton.setEnabled(false);
            }
        });

        fileNameTextBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() == 2) {
                    FileService.openScanFile(fileNameTextBox.getText().trim(), selectedSection);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {
            }

            @Override
            public void mouseReleased(MouseEvent e) {
            }

            @Override
            public void mouseEntered(MouseEvent e) {
            }

            @Override
            public void mouseExited(MouseEvent e) {
            }
        });
    }

    private void autoCompleteTimeOfDay() {
        if (hourTextBox.getText().equals("")) {
            //place holder
        } else if (Integer.parseInt(hourTextBox.getText()) >= 7 && Integer.parseInt(hourTextBox.getText()) <= 11) {
            amPMComboBox.setSelectedItem("AM");
        } else {
            amPMComboBox.setSelectedItem("PM");
        }
    }

    private void enableButton() {
        if (caseNumberTextBox.getText().equals("")
                || fromTextBox.getText().equals("")
                || toComboBox.getSelectedItem().toString().equals("")
                || typeComboBox.getSelectedItem().toString().equals("")
                || descriptionComboBox.getSelectedItem().toString().equals("")) {
            fileButton.setEnabled(false);
        } else {
            fileButton.setEnabled(true);
        }
    }

    private void loadToComboBox(String section) {
        List userList = User.loadSectionDropDowns(section);

        toComboBox.setMaximumRowCount(6);
        toComboBox.removeAllItems();
        toComboBox.addItem("");

        for (int i = 0; i < userList.size(); i++) {
            toComboBox.addItem(userList.get(i).toString());
        }
    }

    private void loadTypeComboBox() {

        List<CMDSHistoryCategory> entryTypes = CMDSHistoryCategory.loadActiveCMDSHistoryDescriptions();

        typeComboBox.setMaximumRowCount(10);
        typeComboBox.removeAllItems();
        typeComboBox.addItem("");

        for (int i = 0; i < entryTypes.size(); i++) {
            typeComboBox.addItem(entryTypes.get(i).entryType + " - " + entryTypes.get(i).description);
        }
    }

    private void loadType2ComboBox() {

        List<CMDSHistoryDescription> entryTypes = CMDSHistoryDescription.loadAllStatusTypes(typeComboBox.getSelectedItem().toString().split("-")[0].trim());

        descriptionComboBox.setMaximumRowCount(10);
        descriptionComboBox.removeAllItems();
        descriptionComboBox.addItem("");

        for (int i = 0; i < entryTypes.size(); i++) {
            descriptionComboBox.addItem(entryTypes.get(i).description);
        }
    }

    private void validateCaseNumber() {
        String[] caseNumbers = caseNumberTextBox.getText().split(",");

        String caseNumberFail = "";

        switch (selectedSection) {
            case "CMDS":
                caseNumberFail = CaseNumber.validateCMDSCaseNumber(caseNumbers);
                break;
        }

        if (!caseNumberFail.equals("")) {
            new docketingCaseNotFound((JFrame) Global.root.getRootPane().getParent(), true, caseNumberFail);
            caseNumberTextBox.setText("");
//            caseNumberTextBox.requestFocus();
        }

        if (!caseNumberTextBox.getText().equals("")) {
            if (!caseNumberTextBox.getText().equals("")) {

                switch (selectedSection) {
                    case "CMDS":
                        toComboBox.setSelectedItem(CMDSCase.DocketTo(caseNumberTextBox.getText()));
                        break;
                }
            }
        }
    }

    private Date generateDate() {
        int hour = Integer.valueOf(hourTextBox.getText().trim());

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, Integer.valueOf(scanDateTextBox.getText().split("/")[2]));
        cal.set(Calendar.MONTH, Integer.valueOf(scanDateTextBox.getText().split("/")[0]) - 1);
        cal.set(Calendar.DAY_OF_MONTH, Integer.valueOf(scanDateTextBox.getText().split("/")[1]));
        if (amPMComboBox.getSelectedItem().toString().equalsIgnoreCase("PM") && hour == 12) {
            cal.set(Calendar.HOUR_OF_DAY, hour);
        } else {
            cal.set(Calendar.HOUR_OF_DAY, amPMComboBox.getSelectedItem().toString().equalsIgnoreCase("AM") ? hour : hour + 12);
        }
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
        jLabel11 = new javax.swing.JLabel();
        directionComboBox = new javax.swing.JComboBox<>();
        descriptionComboBox = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Scan Filing");

        jLabel2.setText("File Name:");

        jLabel3.setText("From:");

        jLabel4.setText("To:");

        jLabel5.setText("Category:");

        jLabel6.setText("Comment:");

        fileNameTextBox.setEditable(false);
        fileNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fileNameTextBox.setEnabled(false);

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

        scanDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );

            jLabel10.setText(":");

            amPMComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

            jLabel11.setText("In or Out:");

            directionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IN", "OUT" }));

            jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            jLabel12.setText("Description:");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2)
                                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel9)
                                .addComponent(jLabel11)
                                .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(descriptionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(caseNumberTextBox)
                                .addComponent(fileNameTextBox)
                                .addComponent(fromTextBox)
                                .addComponent(toComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(typeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane1)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(scanDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(hourTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 0, 0)
                                    .addComponent(minuteTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(amPMComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addComponent(directionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(descriptionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(directionComboBox)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        //Set Verification of docketing
        boolean updateAllCases = false;
        boolean okToDocket = true;

        //getcaseNumber
        String[] caseNumbers = caseNumberTextBox.getText().trim().split(",");

        List<String> groupNumbers = CMDSCase.DistinctGroupNumberFromCMDSCaseNumbers(caseNumbers);

        if (groupNumbers.size() > 1) {
            //Update All question now
            CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(this, true);
            updateAllCases = update.isUpdateStatus();
            update.dispose();
        }

        //If true trim and strip duplicates
        if (updateAllCases) {
            //Get List of All Cases
            List<CMDSCase> caseList = CMDSCase.CMDSDocketingCaseList(caseNumbers, groupNumbers.toArray(new String[0]));

            if (caseList.size() > 0) {
                //User Selects specific cases
                CMDSMultiCaseDocketingDialog userSelected = new CMDSMultiCaseDocketingDialog(this, true, caseList);

                //Update the caseNumbers List with selected items.
                caseNumbers = userSelected.selectedCaseList.toArray(new String[0]);

                //inverse of cancelled cancelled = true then okToDocket = false.
                okToDocket = !userSelected.cancelled;

                //Dispose of the hidden panel now that all variables have been utalized
                userSelected.dispose();
            } else {
                okToDocket = false;
            }
        }

        if (okToDocket) {
            //UpdateInventory Status Line Dialog
            CMDSUpdateInventoryStatusLineDialog statusLineUpdate = new CMDSUpdateInventoryStatusLineDialog(
                    this, true, typeComboBox.getSelectedItem().toString() + " - " + descriptionComboBox.getSelectedItem().toString());

            FileService.docketCMDSScan(
                    caseNumbers, //caseNumber
                    selectedSection,
                    fromTextBox.getText(),
                    toComboBox.getSelectedItem().toString(),
                    fileNameTextBox.getText(), //fileName
                    typeComboBox.getSelectedItem().toString(), //fileType1
                    descriptionComboBox.getSelectedItem().toString(), //fileType2
                    commentTextBox.getText().trim(),
                    directionComboBox.getSelectedItem().toString(),
                    this,
                    generateDate(),
                    statusLineUpdate.isUpdateStatus()
            );
        }
        dispose();
    }//GEN-LAST:event_fileButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> amPMComboBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JTextArea commentTextBox;
    private javax.swing.JComboBox<String> descriptionComboBox;
    private javax.swing.JComboBox<String> directionComboBox;
    private javax.swing.JButton fileButton;
    private javax.swing.JTextField fileNameTextBox;
    private javax.swing.JTextField fromTextBox;
    private javax.swing.JTextField hourTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField minuteTextBox;
    private com.alee.extended.date.WebDateField scanDateTextBox;
    private javax.swing.JComboBox<String> toComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
