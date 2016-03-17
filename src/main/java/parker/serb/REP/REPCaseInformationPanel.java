/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import java.awt.Color;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.UIManager;
import parker.serb.Global;
import parker.serb.sql.DepartmentInState;
import parker.serb.sql.REPCase;
import parker.serb.sql.REPCaseStatus;
import parker.serb.sql.REPCaseType;
import parker.serb.sql.User;
import parker.serb.util.NumberFormatService;

//TODO: Determine how to replicate the boarders on all textboxes to match WLF
//TODO: Merge enable/disable update with a true false flag

//TODO: Save updated information
//TODO: Create History entry for updated information (Updated Case Information)
//TODO: Load Case Types
//TODO: Load Current Owner Box
//TODO: Load Dept In State Text Box

/**
 *
 * @author parker
 */
public class REPCaseInformationPanel extends javax.swing.JPanel {

    REPCase caseInformation;
    
    /**
     * Creates new form REPCaseInformationPanel
     */
    public REPCaseInformationPanel() {
        initComponents();
    }
    
    void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        
        Global.root.getjButton9().setVisible(true);
        
        //Information
        caseTypeComboBox.setEnabled(true);
        caseTypeComboBox.setBackground(Color.WHITE);
        status1ComboBox.setEnabled(true);
        status1ComboBox.setBackground(Color.WHITE);
        status2ComboBox.setEnabled(true);
        status2ComboBox.setBackground(Color.WHITE);
        currentOwnerComboBox.setEnabled(true);
        currentOwnerComboBox.setBackground(Color.WHITE);
        departmentInStateComboBox.setEnabled(true);
        departmentInStateComboBox.setBackground(Color.WHITE);
        
        countyTextBox.setEnabled(true);
        countyTextBox.setBackground(Color.WHITE);
        employerIDNumberTextBox.setEnabled(true);
        employerIDNumberTextBox.setBackground(Color.WHITE);
        bargainingUnitNumberTextBox.setEnabled(true);
        bargainingUnitNumberTextBox.setBackground(Color.WHITE);
        bargainingUnitNameTextBox.setEnabled(true);
        bargainingUnitNameTextBox.setBackground(Color.WHITE);
        
        boardCertifiedCheckBox.setEnabled(true);
        deemedCertifiedCheckBox.setEnabled(true);
        certificationRevokedCheckBox.setEnabled(true);
        
        relatedCasesTextArea.setEnabled(true);
        relatedCasesTextArea.setBackground(Color.WHITE);
        
        
        //Dates
        fileDateTextBox.setEnabled(true);
        fileDateTextBox.setBackground(Color.WHITE);
        amendedFilingDateTextBox.setEnabled(true);
        amendedFilingDateTextBox.setBackground(Color.WHITE);
        finalBoardDateTextBox.setEnabled(true);
        finalBoardDateTextBox.setBackground(Color.WHITE);
        registrationLetterSentTextBox.setEnabled(true);
        registrationLetterSentTextBox.setBackground(Color.WHITE);
        dateOfAppealTextBox.setEnabled(true);
        dateOfAppealTextBox.setBackground(Color.WHITE);
        courtClosedDateTextBox.setEnabled(true);
        courtClosedDateTextBox.setBackground(Color.WHITE);
        returnSOIDueDateTextBox.setEnabled(true);
        returnSOIDueDateTextBox.setBackground(Color.WHITE);
        actualSOIReturnDateTextBox.setEnabled(true);
        actualSOIReturnDateTextBox.setBackground(Color.WHITE);
        SOIReturnInitialsTextBox.setEnabled(true);
        SOIReturnInitialsTextBox.setBackground(Color.WHITE);
        REPClosedCaseDueDateTextBox.setEnabled(true);
        REPClosedCaseDueDateTextBox.setBackground(Color.WHITE);
        actualREPClosedDateTextBox.setEnabled(true);
        actualREPClosedDateTextBox.setBackground(Color.WHITE);
        clerksClosedDateInitialsTextBox.setEnabled(true);
        clerksClosedDateInitialsTextBox.setBackground(Color.WHITE);
        
//        loadInformation();
    }
    
    void disableUpdate(boolean runSave) {
        Global.root.getjButton2().setText("Update");
        
        Global.root.getjButton9().setVisible(false);
        
        

        caseTypeComboBox.setEnabled(false);
        caseTypeComboBox.setBackground(new Color(238,238,238));
        status1ComboBox.setEnabled(false);
        status1ComboBox.setBackground(new Color(238,238,238));
        status2ComboBox.setEnabled(false);
        status2ComboBox.setBackground(new Color(238,238,238));
        currentOwnerComboBox.setEnabled(false);
        currentOwnerComboBox.setBackground(new Color(238,238,238));
        departmentInStateComboBox.setEnabled(false);
        departmentInStateComboBox.setBackground(new Color(238,238,238));
        
        countyTextBox.setEnabled(false);
        countyTextBox.setBackground(new Color(238,238,238));
        employerIDNumberTextBox.setEnabled(false);
        employerIDNumberTextBox.setBackground(new Color(238,238,238));
        bargainingUnitNumberTextBox.setEnabled(false);
        bargainingUnitNumberTextBox.setBackground(new Color(238,238,238));
        bargainingUnitNameTextBox.setEnabled(false);
        bargainingUnitNameTextBox.setBackground(new Color(238,238,238));
        
//        boardCertifiedCheckBox.set
        
        boardCertifiedCheckBox.setEnabled(false);
        deemedCertifiedCheckBox.setEnabled(false);
        certificationRevokedCheckBox.setEnabled(false);
        
        relatedCasesTextArea.setEnabled(false);
        relatedCasesTextArea.setBackground(new Color(238,238,238));
        
        fileDateTextBox.setEnabled(false);
        fileDateTextBox.setBackground(new Color(238,238,238));
        amendedFilingDateTextBox.setEnabled(false);
        amendedFilingDateTextBox.setBackground(new Color(238,238,238));
        finalBoardDateTextBox.setEnabled(false);
        finalBoardDateTextBox.setBackground(new Color(238,238,238));
        registrationLetterSentTextBox.setEnabled(false);
        registrationLetterSentTextBox.setBackground(new Color(238,238,238));
        dateOfAppealTextBox.setEnabled(false);
        dateOfAppealTextBox.setBackground(new Color(238,238,238));
        courtClosedDateTextBox.setEnabled(false);
        courtClosedDateTextBox.setBackground(new Color(238,238,238));
        returnSOIDueDateTextBox.setEnabled(false);
        returnSOIDueDateTextBox.setBackground(new Color(238,238,238));
        actualSOIReturnDateTextBox.setEnabled(false);
        actualSOIReturnDateTextBox.setBackground(new Color(238,238,238));
        SOIReturnInitialsTextBox.setEnabled(false);
        SOIReturnInitialsTextBox.setBackground(new Color(238,238,238));
        REPClosedCaseDueDateTextBox.setEnabled(false);
        REPClosedCaseDueDateTextBox.setBackground(new Color(238,238,238));
        actualREPClosedDateTextBox.setEnabled(false);
        actualREPClosedDateTextBox.setBackground(new Color(238,238,238));
        clerksClosedDateInitialsTextBox.setEnabled(false);
        clerksClosedDateInitialsTextBox.setBackground(new Color(238,238,238));
        
        if(runSave)
            saveInformation();
    }
    
    void clearAll() {
        fileDateTextBox.setText("");
        amendedFilingDateTextBox.setText("");
        finalBoardDateTextBox.setText("");
        registrationLetterSentTextBox.setText("");
        dateOfAppealTextBox.setText("");
        courtClosedDateTextBox.setText("");
        returnSOIDueDateTextBox.setText("");
        actualSOIReturnDateTextBox.setText("");
        SOIReturnInitialsTextBox.setText("");
        REPClosedCaseDueDateTextBox.setText("");
        actualREPClosedDateTextBox.setText("");
        clerksClosedDateInitialsTextBox.setText("");
    }
    
    void loadInformation() {
        loadCaseTypes();
        loadStatus();
        loadCurrentOwner();
        loadDepartmentInState();
        loadCaseInformation();
    }
    
    public void loadCaseTypes() {
        caseTypeComboBox.removeAllItems();
        caseTypeComboBox.addItem("");
        
        List repCaseTypeList = REPCaseType.loadAllREPCaseTypes();
        
        for (Object repCaseTypes : repCaseTypeList) {
            REPCaseType caseType = (REPCaseType) repCaseTypes;
            caseTypeComboBox.addItem(caseType.typeAbbrevation);
        }
    }
    
    public void loadStatus() {
        status1ComboBox.removeAllItems();
        status2ComboBox.removeAllItems();
        
        status1ComboBox.addItem("");
        status2ComboBox.addItem("");
        
        List caseStatusList = REPCaseStatus.loadAll();
        
        for (Object caseStatus : caseStatusList) {
            REPCaseStatus status = (REPCaseStatus) caseStatus;
            if(status.statusType.equals("1")) {
                status1ComboBox.addItem(status.status);
            } else {
                status2ComboBox.addItem(status.status);
            }
        }
    }
    
    public void loadCurrentOwner() {
        currentOwnerComboBox.removeAllItems();
        
        currentOwnerComboBox.addItem("");
        
        List currentOwnerList = User.loadAllREPCurrentOwners();
        
        for (Object currentOwners : currentOwnerList) {
            currentOwnerComboBox.addItem(currentOwners.toString());
        }
    }
    
    public void loadDepartmentInState() {
        departmentInStateComboBox.removeAllItems();
        
        departmentInStateComboBox.addItem("");
        
        List departmentInStateList = DepartmentInState.loadAllDepartments();
        
        for (Object departmentInState : departmentInStateList) {
            DepartmentInState department = (DepartmentInState) departmentInState;
            departmentInStateComboBox.addItem(department.code);
        }
    }
    
    private void loadCaseInformation() {
        caseInformation = REPCase.loadCaseInformation();
        
        caseTypeComboBox.setSelectedItem(caseInformation.caseType);
        status1ComboBox.setSelectedItem(caseInformation.status1);
        status2ComboBox.setSelectedItem(caseInformation.status2);
        if(((DefaultComboBoxModel)currentOwnerComboBox.getModel()).getIndexOf(User.getNameByID(caseInformation.currentOwnerID)) == -1 ) {
            currentOwnerComboBox.addItem(User.getNameByID(caseInformation.currentOwnerID) );
        }
        currentOwnerComboBox.setSelectedItem(caseInformation.currentOwnerID == 0 ? "" : User.getNameByID(caseInformation.currentOwnerID));
        countyTextBox.setText(caseInformation.county);
        employerIDNumberTextBox.setText(caseInformation.employerIDNumber);
        departmentInStateComboBox.setSelectedItem(caseInformation.deptInState);
        bargainingUnitNumberTextBox.setText(caseInformation.bargainingUnitNumber);
        //TODO: Load the name of the barg unit
//        bargainingUnitNumberTextBox.setText(BargainingUnit.getUnitName(caseInformation.barginingUnitNumber));
        boardCertifiedCheckBox.setSelected(caseInformation.boardCertified == true);
        deemedCertifiedCheckBox.setSelected(caseInformation.deemedCertified == true);
        certificationRevokedCheckBox.setSelected(caseInformation.certificationRevoked == true);
        relatedCasesTextArea.setText(caseInformation.relatedCases);
        
        fileDateTextBox.setText(caseInformation.fileDate != null ? Global.mmddyyyy.format(new Date(caseInformation.fileDate.getTime())) : ""); 
        amendedFilingDateTextBox.setText(caseInformation.amendedFiliingDate != null ? Global.mmddyyyy.format(new Date(caseInformation.amendedFiliingDate.getTime())) : "");
        finalBoardDateTextBox.setText(caseInformation.finalBoardDate != null ? Global.mmddyyyy.format(new Date(caseInformation.finalBoardDate.getTime())) : "");        
        registrationLetterSentTextBox.setText(caseInformation.registrationLetterSent != null ? Global.mmddyyyy.format(new Date(caseInformation.registrationLetterSent.getTime())) : "");  
        dateOfAppealTextBox.setText(caseInformation.dateOfAppeal != null ? Global.mmddyyyy.format(new Date(caseInformation.dateOfAppeal.getTime())) : "");  
        courtClosedDateTextBox.setText(caseInformation.courtClosedDate != null ? Global.mmddyyyy.format(new Date(caseInformation.courtClosedDate.getTime())) : "");  
        returnSOIDueDateTextBox.setText(caseInformation.returnSOIDueDate != null ? Global.mmddyyyy.format(new Date(caseInformation.returnSOIDueDate.getTime())) : "");  
        actualSOIReturnDateTextBox.setText(caseInformation.actualSOIReturnDate != null ? Global.mmddyyyy.format(new Date(caseInformation.actualSOIReturnDate.getTime())) : "");  
        SOIReturnInitialsTextBox.setText(caseInformation.SOIReturnInitials);  
        REPClosedCaseDueDateTextBox.setText(caseInformation.REPClosedCaseDueDate != null ? Global.mmddyyyy.format(new Date(caseInformation.REPClosedCaseDueDate.getTime())) : "");  
        actualREPClosedDateTextBox.setText(caseInformation.actualREPClosedDate != null ? Global.mmddyyyy.format(new Date(caseInformation.actualREPClosedDate.getTime())) : "");  
        clerksClosedDateInitialsTextBox.setText(caseInformation.clerksClosedDateInitials);  
    }
    
    void saveInformation() {
        REPCase newCaseInformation = new REPCase();
        
        newCaseInformation.caseType = caseTypeComboBox.getSelectedItem() == null || caseTypeComboBox.getSelectedItem().equals("") ? null : caseTypeComboBox.getSelectedItem().toString();
        newCaseInformation.status1 = status1ComboBox.getSelectedItem() == null || status1ComboBox.getSelectedItem().equals("") ? null : status1ComboBox.getSelectedItem().toString();
        newCaseInformation.status2 = status2ComboBox.getSelectedItem() == null || status2ComboBox.getSelectedItem().equals("") ? null : status2ComboBox.getSelectedItem().toString();
        newCaseInformation.currentOwnerID = currentOwnerComboBox.getSelectedItem().equals("") ? 0 : User.getCurrentOwnerID(currentOwnerComboBox.getSelectedItem().toString());
        newCaseInformation.county = countyTextBox.getText().equals("") ? null : countyTextBox.getText().trim();
        newCaseInformation.employerIDNumber = employerIDNumberTextBox.getText().equals("") ? null : employerIDNumberTextBox.getText().trim();
        newCaseInformation.deptInState = departmentInStateComboBox.getSelectedItem() == null ? null : departmentInStateComboBox.getSelectedItem().toString();
        newCaseInformation.bargainingUnitNumber = bargainingUnitNumberTextBox.getText().equals("") ? null : bargainingUnitNumberTextBox.getText().trim();
        newCaseInformation.boardCertified = boardCertifiedCheckBox.isSelected();
        newCaseInformation.deemedCertified = deemedCertifiedCheckBox.isSelected();
        newCaseInformation.certificationRevoked = certificationRevokedCheckBox.isSelected();
        newCaseInformation.relatedCases = relatedCasesTextArea.getText().equals("") ? null : relatedCasesTextArea.getText().trim();
        
        newCaseInformation.fileDate = fileDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(fileDateTextBox.getText()));
        newCaseInformation.amendedFiliingDate = amendedFilingDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(amendedFilingDateTextBox.getText()));
        newCaseInformation.finalBoardDate = finalBoardDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(finalBoardDateTextBox.getText()));
        newCaseInformation.registrationLetterSent = registrationLetterSentTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(registrationLetterSentTextBox.getText()));
        newCaseInformation.dateOfAppeal = dateOfAppealTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(dateOfAppealTextBox.getText()));
        newCaseInformation.registrationLetterSent = registrationLetterSentTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(registrationLetterSentTextBox.getText()));
        newCaseInformation.courtClosedDate = courtClosedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(courtClosedDateTextBox.getText()));
        newCaseInformation.returnSOIDueDate = returnSOIDueDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(returnSOIDueDateTextBox.getText()));
        newCaseInformation.actualSOIReturnDate = actualSOIReturnDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(actualSOIReturnDateTextBox.getText()));
        newCaseInformation.SOIReturnInitials = SOIReturnInitialsTextBox.getText().equals("") ? null : SOIReturnInitialsTextBox.getText().trim();
        newCaseInformation.REPClosedCaseDueDate = REPClosedCaseDueDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(REPClosedCaseDueDateTextBox.getText()));
        newCaseInformation.actualREPClosedDate = actualREPClosedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(actualREPClosedDateTextBox.getText()));
        newCaseInformation.clerksClosedDateInitials = clerksClosedDateInitialsTextBox.getText().equals("") ? null : clerksClosedDateInitialsTextBox.getText().trim();

        REPCase.updateCaseInformation(newCaseInformation, caseInformation);
        caseInformation = REPCase.loadCaseInformation();
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        boardCertifiedCheckBox = new javax.swing.JCheckBox();
        deemedCertifiedCheckBox = new javax.swing.JCheckBox();
        certificationRevokedCheckBox = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        relatedCasesTextArea = new javax.swing.JTextArea();
        caseTypeComboBox = new javax.swing.JComboBox();
        status1ComboBox = new javax.swing.JComboBox();
        status2ComboBox = new javax.swing.JComboBox();
        currentOwnerComboBox = new javax.swing.JComboBox();
        countyTextBox = new javax.swing.JTextField();
        employerIDNumberTextBox = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        departmentInStateComboBox = new javax.swing.JComboBox();
        bargainingUnitNumberTextBox = new javax.swing.JTextField();
        bargainingUnitNameTextBox = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        fileDateTextBox = new com.alee.extended.date.WebDateField();
        amendedFilingDateTextBox = new com.alee.extended.date.WebDateField();
        finalBoardDateTextBox = new com.alee.extended.date.WebDateField();
        registrationLetterSentTextBox = new com.alee.extended.date.WebDateField();
        dateOfAppealTextBox = new com.alee.extended.date.WebDateField();
        courtClosedDateTextBox = new com.alee.extended.date.WebDateField();
        returnSOIDueDateTextBox = new com.alee.extended.date.WebDateField();
        actualSOIReturnDateTextBox = new com.alee.extended.date.WebDateField();
        REPClosedCaseDueDateTextBox = new com.alee.extended.date.WebDateField();
        actualREPClosedDateTextBox = new com.alee.extended.date.WebDateField();
        clerksClosedDateInitialsTextBox = new javax.swing.JTextField();
        SOIReturnInitialsTextBox = new javax.swing.JTextField();

        setLayout(new java.awt.GridLayout(1, 0));

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Information"));
        jPanel1.setRequestFocusEnabled(false);

        jLabel2.setText("Case Type:");

        jLabel3.setText("Status 1:");

        jLabel4.setText("Status 2:");

        jLabel5.setText("Current Owner:");

        jLabel6.setText("County:");

        jLabel7.setText("Employer ID Number:");

        jLabel8.setText("Bargaining Unit Number:");

        boardCertifiedCheckBox.setText("Board Certified");
        boardCertifiedCheckBox.setEnabled(false);
        boardCertifiedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                boardCertifiedCheckBoxActionPerformed(evt);
            }
        });

        deemedCertifiedCheckBox.setText("Deemed Certified");
        deemedCertifiedCheckBox.setEnabled(false);
        deemedCertifiedCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                deemedCertifiedCheckBoxActionPerformed(evt);
            }
        });

        certificationRevokedCheckBox.setText("Certification Revoked");
        certificationRevokedCheckBox.setEnabled(false);

        jLabel9.setText("Related Cases:");

        relatedCasesTextArea.setBackground(new java.awt.Color(238, 238, 238));
        relatedCasesTextArea.setColumns(20);
        relatedCasesTextArea.setRows(5);
        relatedCasesTextArea.setEnabled(false);
        jScrollPane1.setViewportView(relatedCasesTextArea);

        caseTypeComboBox.setBackground(new java.awt.Color(255, 255, 255));
        caseTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        caseTypeComboBox.setEnabled(false);

        status1ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        status1ComboBox.setEnabled(false);

        status2ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        status2ComboBox.setEnabled(false);

        currentOwnerComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        currentOwnerComboBox.setEnabled(false);

        countyTextBox.setBackground(new java.awt.Color(238, 238, 238));
        countyTextBox.setEnabled(false);

        employerIDNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        employerIDNumberTextBox.setEnabled(false);

        jLabel1.setText("Dept. In State:");

        departmentInStateComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        departmentInStateComboBox.setEnabled(false);

        bargainingUnitNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        bargainingUnitNumberTextBox.setEnabled(false);

        bargainingUnitNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        bargainingUnitNameTextBox.setEnabled(false);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(caseTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(status1ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(status2ComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(currentOwnerComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(countyTextBox)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(employerIDNumberTextBox)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(departmentInStateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(bargainingUnitNumberTextBox)
                            .addComponent(bargainingUnitNameTextBox)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 2, Short.MAX_VALUE)
                        .addComponent(boardCertifiedCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(deemedCertifiedCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(certificationRevokedCheckBox)
                        .addGap(19, 19, 19)))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(caseTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(status1ComboBox)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(status2ComboBox)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(currentOwnerComboBox)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(countyTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(employerIDNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1)
                    .addComponent(departmentInStateComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(bargainingUnitNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(bargainingUnitNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(certificationRevokedCheckBox)
                    .addComponent(deemedCertifiedCheckBox)
                    .addComponent(boardCertifiedCheckBox))
                .addGap(10, 10, 10)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );

        add(jPanel1);

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Dates"));

        jLabel10.setText("File Date:");

        jLabel11.setText("Amended Filing Date:");

        jLabel12.setText("Final Board Date:");

        jLabel13.setText("Registration Letter Sent:");

        jLabel14.setText("Date of Appeal:");

        jLabel15.setText("Court Closed Date:");

        jLabel16.setText("Return SOI Due Date:");

        jLabel17.setText("Actual SOI Return Date:");

        jLabel18.setText("SOI Return Initials:");

        jLabel19.setText("REP Closed Case Due Date:");

        jLabel20.setText("Actual REP Closed Date:");

        jLabel21.setText("Clerks Closed Date Initials:");

        fileDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        fileDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fileDateTextBox.setEnabled(false);
        fileDateTextBox.setDateFormat(Global.mmddyyyy);

        amendedFilingDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        amendedFilingDateTextBox.setEnabled(false);
        amendedFilingDateTextBox.setDateFormat(Global.mmddyyyy);

        finalBoardDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        finalBoardDateTextBox.setEnabled(false);
        finalBoardDateTextBox.setDateFormat(Global.mmddyyyy);

        registrationLetterSentTextBox.setBackground(new java.awt.Color(238, 238, 238));
        registrationLetterSentTextBox.setEnabled(false);
        registrationLetterSentTextBox.setDateFormat(Global.mmddyyyy);

        dateOfAppealTextBox.setBackground(new java.awt.Color(238, 238, 238));
        dateOfAppealTextBox.setEnabled(false);
        dateOfAppealTextBox.setDateFormat(Global.mmddyyyy);

        courtClosedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        courtClosedDateTextBox.setEnabled(false);
        courtClosedDateTextBox.setDateFormat(Global.mmddyyyy);

        returnSOIDueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        returnSOIDueDateTextBox.setEnabled(false);
        returnSOIDueDateTextBox.setDateFormat(Global.mmddyyyy);

        actualSOIReturnDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        actualSOIReturnDateTextBox.setEnabled(false);
        actualSOIReturnDateTextBox.setDateFormat(Global.mmddyyyy);

        REPClosedCaseDueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        REPClosedCaseDueDateTextBox.setEnabled(false);
        REPClosedCaseDueDateTextBox.setDateFormat(Global.mmddyyyy);

        actualREPClosedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        actualREPClosedDateTextBox.setEnabled(false);
        actualREPClosedDateTextBox.setDateFormat(Global.mmddyyyy);

        clerksClosedDateInitialsTextBox.setBackground(new java.awt.Color(238, 238, 238));

        SOIReturnInitialsTextBox.setBackground(new java.awt.Color(238, 238, 238));

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel21)
                    .addComponent(jLabel20)
                    .addComponent(jLabel19)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16)
                    .addComponent(jLabel15)
                    .addComponent(jLabel14)
                    .addComponent(jLabel13)
                    .addComponent(jLabel12)
                    .addComponent(jLabel11)
                    .addComponent(jLabel10))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(fileDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(amendedFilingDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(finalBoardDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(registrationLetterSentTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(dateOfAppealTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(courtClosedDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(returnSOIDueDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(actualSOIReturnDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(REPClosedCaseDueDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(actualREPClosedDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 309, Short.MAX_VALUE)
                    .addComponent(clerksClosedDateInitialsTextBox)
                    .addComponent(SOIReturnInitialsTextBox))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(fileDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(amendedFilingDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(finalBoardDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(registrationLetterSentTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(dateOfAppealTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(courtClosedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(returnSOIDueDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(actualSOIReturnDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(SOIReturnInitialsTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(REPClosedCaseDueDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(actualREPClosedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(clerksClosedDateInitialsTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        add(jPanel2);
    }// </editor-fold>//GEN-END:initComponents

    private void boardCertifiedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardCertifiedCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boardCertifiedCheckBoxActionPerformed

    private void deemedCertifiedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deemedCertifiedCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deemedCertifiedCheckBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.extended.date.WebDateField REPClosedCaseDueDateTextBox;
    private javax.swing.JTextField SOIReturnInitialsTextBox;
    private com.alee.extended.date.WebDateField actualREPClosedDateTextBox;
    private com.alee.extended.date.WebDateField actualSOIReturnDateTextBox;
    private com.alee.extended.date.WebDateField amendedFilingDateTextBox;
    private javax.swing.JTextField bargainingUnitNameTextBox;
    private javax.swing.JTextField bargainingUnitNumberTextBox;
    private javax.swing.JCheckBox boardCertifiedCheckBox;
    private javax.swing.JComboBox caseTypeComboBox;
    private javax.swing.JCheckBox certificationRevokedCheckBox;
    private javax.swing.JTextField clerksClosedDateInitialsTextBox;
    private javax.swing.JTextField countyTextBox;
    private com.alee.extended.date.WebDateField courtClosedDateTextBox;
    private javax.swing.JComboBox currentOwnerComboBox;
    private com.alee.extended.date.WebDateField dateOfAppealTextBox;
    private javax.swing.JCheckBox deemedCertifiedCheckBox;
    private javax.swing.JComboBox departmentInStateComboBox;
    private javax.swing.JTextField employerIDNumberTextBox;
    private com.alee.extended.date.WebDateField fileDateTextBox;
    private com.alee.extended.date.WebDateField finalBoardDateTextBox;
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
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
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
    private com.alee.extended.date.WebDateField registrationLetterSentTextBox;
    private javax.swing.JTextArea relatedCasesTextArea;
    private com.alee.extended.date.WebDateField returnSOIDueDateTextBox;
    private javax.swing.JComboBox status1ComboBox;
    private javax.swing.JComboBox status2ComboBox;
    // End of variables declaration//GEN-END:variables
}
