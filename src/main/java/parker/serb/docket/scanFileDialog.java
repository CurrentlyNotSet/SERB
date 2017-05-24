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
import parker.serb.Global;
import parker.serb.sql.ActivityType;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CSCCase;
import parker.serb.sql.CaseNumber;
import parker.serb.sql.ORGCase;
import parker.serb.sql.REPCase;
import parker.serb.sql.ULPCase;
import parker.serb.sql.User;
import parker.serb.util.FileService;

/**
 *
 * @author parkerjohnston
 */
public class scanFileDialog extends javax.swing.JDialog {

    String selectedSection = "";
    boolean orgProcess = false;
    /**
     * Creates new form scanFileDialog
     * @param parent
     * @param modal
     * @param file
     * @param time
     * @param section
     */
    public scanFileDialog(java.awt.Frame parent, boolean modal, String file, String section, String time) {
        super(parent, modal);
        initComponents();
        selectedSection = section;
        setCaseNumberTitle(section);
        loadData(section, file, time);
        addListeners(section);
        this.pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void setCaseNumberTitle(String section) {
        switch(section) {
            case "ORG":
                jLabel8.setText("ORG Number(s):");
                orgNameLabel.setVisible(true);
                orgNameComboBox.setVisible(true);
                break;
            case "CSC":
                jLabel8.setText("CSC Number(s):");
                orgNameLabel.setVisible(true);
                orgNameComboBox.setVisible(true);
                break;
            default:
                jLabel8.setText("Case Number(s):");
                orgNameLabel.setVisible(false);
                orgNameComboBox.setVisible(false);
                break;
        }
    }

    private void loadData(String section, String file, String time) {
        if(section.equals("ORG")) {
            orgNameComboBox.removeAllItems();
            orgNameComboBox.addItem("");

            List caseNumberList = ORGCase.loadORGNames();

            caseNumberList.stream().forEach((caseNumber) -> {
                orgNameComboBox.addItem(caseNumber.toString());
            });
        }

        if(section.equals("CSC")) {
            orgNameComboBox.removeAllItems();
            orgNameComboBox.addItem("");

            List caseNumberList = CSCCase.loadCSCNames();

            caseNumberList.stream().forEach((caseNumber) -> {
                orgNameComboBox.addItem(caseNumber.toString());
            });
        }

        fileNameTextBox.setText(file);
        loadToComboBox(section);
        loadTypeComboBox();
        scanDateTextBox.setText(time.split(" ")[0]);
        hourTextBox.setText(time.split(" ")[1].split(":")[0]);
        minuteTextBox.setText(time.split(" ")[1].split(":")[1]);
        amPMComboBox.setSelectedItem(time.split(" ")[2]);

    }

    private void addListeners(String section) {
        orgNameComboBox.addActionListener((ActionEvent e) -> {
            if (!orgProcess) {
                if (orgNameComboBox.getSelectedItem() != null) {
                    if (orgNameComboBox.getSelectedItem().toString().equals("")) {
                        caseNumberTextBox.setText("");
                    } else {
                        if (section.equals("CSC")) {
                            caseNumberTextBox.setText(CSCCase.getCSCNumber(orgNameComboBox.getSelectedItem().toString()));
                        } else if (section.equals("ORG")) {
                            caseNumberTextBox.setText(ORGCase.getORGNumber(orgNameComboBox.getSelectedItem().toString()));
                        }
                    }
                }
            }
        });

        caseNumberTextBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getClickCount() >= 2) {
                    switch (section) {
                        case "ORG":
                            orgProcess = true;
                            DocketORGCaseSearch orgSearch = new DocketORGCaseSearch(Global.root, true);
                            caseNumberTextBox.setText(orgSearch.orgNumber);
                            orgNameComboBox.setSelectedItem(orgSearch.orgName);
                            orgSearch.dispose();
                            validateCaseNumber();
                            orgProcess = false;
                            break;
                        case "CSC":
                        case "Civil Service Commission":
                            orgProcess = true;
                            DocketCSCCaseSearch cscSearch = new DocketCSCCaseSearch(Global.root, true);
                            caseNumberTextBox.setText(cscSearch.orgNumber);
                            orgNameComboBox.setSelectedItem(cscSearch.orgName);
                            cscSearch.dispose();
                            validateCaseNumber();
                            orgProcess = false;
                            break;
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });

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
            public void focusGained(FocusEvent e) {}

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
            enableButton();
        });

        fileNameTextBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    FileService.openScanFile(fileNameTextBox.getText().trim(), selectedSection);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    private void autoCompleteTimeOfDay() {
        if(hourTextBox.getText().equals("")) {
            //place holder
        } else if(Integer.parseInt(hourTextBox.getText()) >= 7 && Integer.parseInt(hourTextBox.getText()) <= 11) {
            amPMComboBox.setSelectedItem("AM");
        } else {
            amPMComboBox.setSelectedItem("PM");
        }
    }

    private void enableButton() {
        if(caseNumberTextBox.getText().equals("")
                || fromTextBox.getText().equals("")
                || toComboBox.getSelectedItem().toString().equals("")
                || typeComboBox.getSelectedItem().toString().equals("")) {
            fileButton.setEnabled(false);
        } else {
            fileButton.setEnabled(true);
        }
    }

    private void loadToComboBox(String section) {
        List userList = null;

        if(section.equals("REP") || section.equals("MED") || section.equals("ULP")) {
            userList = User.loadSectionDropDownsPlusALJ(section);
        } else {
            userList = User.loadSectionDropDowns(section);
        }

        toComboBox.setMaximumRowCount(6);

        toComboBox.removeAllItems();

        toComboBox.addItem("");

        for(int i = 0; i < userList.size(); i++) {
            toComboBox.addItem(userList.get(i).toString());
        }
    }

    private void loadTypeComboBox() {
        List typeList = ActivityType.loadActiveActivityTypeBySection(selectedSection);

        typeComboBox.setMaximumRowCount(10);

        typeComboBox.removeAllItems();

        typeComboBox.addItem("");

        for(int i = 0; i < typeList.size(); i++) {
            ActivityType item = (ActivityType) typeList.get(i);
            typeComboBox.addItem(item.descriptionAbbrv + " - " + item.descriptionFull);
        }
    }

    private void validateCaseNumber() {
        String[] caseNumbers = caseNumberTextBox.getText().split(",");

        String caseNumberFail = "";

        switch(selectedSection) {
            case "ULP":
                caseNumberFail = CaseNumber.validateULPCaseNumber(caseNumbers);
                break;
            case "REP":
                caseNumberFail = CaseNumber.validateREPCaseNumber(caseNumbers);
                break;
            case "MED":
                caseNumberFail = CaseNumber.validateMEDCaseNumber(caseNumbers);
                break;
            case "ORG":
                caseNumberFail = CaseNumber.validateORGCaseNumber(caseNumbers);
                //orgNameComboBox.setSelectedItem("");
                break;
            case "CMDS":
                caseNumberFail = CaseNumber.validateCMDSCaseNumber(caseNumbers);
                break;
            case "CSC":
                caseNumberFail = CaseNumber.validateCSCCaseNumber(caseNumbers);
                //orgNameComboBox.setSelectedItem("");
                break;
        }

        if(!caseNumberFail.equals("")) {
            new docketingCaseNotFound((JFrame) Global.root.getRootPane().getParent(), true, caseNumberFail);
            caseNumberTextBox.setText("");
//            caseNumberTextBox.requestFocus();
        }

        if(!caseNumberTextBox.getText().equals("")) {
            if(!caseNumberTextBox.getText().equals("")) {

                switch (selectedSection) {
                    case "ULP":
                        toComboBox.setSelectedItem(ULPCase.DocketTo(caseNumberTextBox.getText()));
                        break;
                    case "REP":
                        toComboBox.setSelectedItem(REPCase.DocketTo(caseNumberTextBox.getText()));
                        break;
                    case "MED":
                        toComboBox.setSelectedItem("Mary Laurent");
                        break;
                    case "ORG":
                        orgNameComboBox.setSelectedItem(ORGCase.getORGName(caseNumberTextBox.getText()));
                        break;
                    case "CMDS":
                        toComboBox.setSelectedItem(CMDSCase.DocketTo(caseNumberTextBox.getText()));
                        break;
                    case "CSC":
                        orgNameComboBox.setSelectedItem(CSCCase.getCSCName(caseNumberTextBox.getText()));
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
        if (amPMComboBox.getSelectedItem().toString().equalsIgnoreCase("PM") && hour == 12){
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
        jLabel7 = new javax.swing.JLabel();
        redactedCheckBox = new javax.swing.JCheckBox();
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
        orgNameLabel = new javax.swing.JLabel();
        orgNameComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Scan Filing");

        jLabel2.setText("File Name:");

        jLabel3.setText("From:");

        jLabel4.setText("To:");

        jLabel5.setText("Type:");

        jLabel6.setText("Comment:");

        jLabel7.setText("Redacted:");

        fileNameTextBox.setEditable(false);
        fileNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fileNameTextBox.setEnabled(false);
        fileNameTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileNameTextBoxActionPerformed(evt);
            }
        });

        toComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
            scanDateTextBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    scanDateTextBoxActionPerformed(evt);
                }
            });

            hourTextBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    hourTextBoxActionPerformed(evt);
                }
            });

            jLabel10.setText(":");

            minuteTextBox.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    minuteTextBoxActionPerformed(evt);
                }
            });

            amPMComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM" }));

            jLabel11.setText("In or Out:");

            directionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IN", "OUT" }));

            orgNameLabel.setText("Org Name:");

            orgNameComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                                .addComponent(jLabel2)
                                .addComponent(jLabel8)
                                .addComponent(jLabel3)
                                .addComponent(jLabel4)
                                .addComponent(jLabel5)
                                .addComponent(jLabel6)
                                .addComponent(jLabel7)
                                .addComponent(jLabel9)
                                .addComponent(jLabel11)
                                .addComponent(orgNameLabel))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                                .addComponent(directionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(redactedCheckBox)
                                    .addGap(0, 0, Short.MAX_VALUE))
                                .addComponent(orgNameComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(fileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(orgNameComboBox)
                        .addComponent(orgNameLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(directionComboBox)
                        .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel6)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(redactedCheckBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
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
        String[] caseNumbers = caseNumberTextBox.getText().trim().split(",");
        FileService.docketScan(caseNumbers,
                fileNameTextBox.getText(),
                selectedSection,
                typeComboBox.getSelectedItem().toString().split("-", 2)[0].trim(),
                typeComboBox.getSelectedItem().toString().split("-", 2)[1].trim(),
                fromTextBox.getText(),
                toComboBox.getSelectedItem().toString(),
                commentTextBox.getText(),
                redactedCheckBox.isSelected(),
                generateDate(),
                directionComboBox.getSelectedItem().toString());
        dispose();
    }//GEN-LAST:event_fileButtonActionPerformed

    private void scanDateTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_scanDateTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_scanDateTextBoxActionPerformed

    private void hourTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_hourTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_hourTextBoxActionPerformed

    private void minuteTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_minuteTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_minuteTextBoxActionPerformed

    private void fileNameTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileNameTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_fileNameTextBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> amPMComboBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JTextArea commentTextBox;
    private javax.swing.JComboBox<String> directionComboBox;
    private javax.swing.JButton fileButton;
    private javax.swing.JTextField fileNameTextBox;
    private javax.swing.JTextField fromTextBox;
    private javax.swing.JTextField hourTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField minuteTextBox;
    private javax.swing.JComboBox<String> orgNameComboBox;
    private javax.swing.JLabel orgNameLabel;
    private javax.swing.JCheckBox redactedCheckBox;
    private com.alee.extended.date.WebDateField scanDateTextBox;
    private javax.swing.JComboBox<String> toComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
