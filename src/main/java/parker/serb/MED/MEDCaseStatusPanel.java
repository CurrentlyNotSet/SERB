/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.bunumber.buNumberSearch;
import parker.serb.employer.employerDetail;
import parker.serb.employer.employerSearch;
import parker.serb.sql.BargainingUnit;
import parker.serb.sql.MEDCase;
import parker.serb.sql.Mediator;
import parker.serb.sql.RelatedCase;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class MEDCaseStatusPanel extends javax.swing.JPanel {

    MEDCase orginalInformation;
    DefaultTableModel relatedCaseModel;
    /**
     * Creates new form MEDCaseStatusPanel
     */
    public MEDCaseStatusPanel() {
        initComponents();
        addRenderer();
        relatedCaseModel = (DefaultTableModel) relatedCaseTable.getModel();
        addMultiCaseButton.setVisible(false);
    }
    
    private void addRenderer() {
        relatedCaseTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Global.ALTERNATE_ROW_COLOR);
                }
                return c;
            }
        });
    }
    
    public void clearAll() {
        
        filingDateTextBox.setText("");
        employerIDNumberTextBox.setText("");
        bargainingUnitTextBox.setText("");
        boardCertifiedCheckBox.setSelected(false);
        deemedCertifiedCheckBox.setSelected(false);
        approxNumberOfEmployeesTextBox.setText("");
        duplicateCaseNumberTextBox.setText("");
        relatedCaseNumberTextBox.setText("");
        
        negotiationTypeComboBox.setSelectedItem(" ");
        expirationDateTextBox.setText("");
        NTNFiledByComboBox.setSelectedItem("");
        negotiationPeriodComboBox.setSelectedItem(" ");
        multiUnitBargainingRequestedCheckBox.setSelected(false);
        mediatorAppointedDateTextBox.setText("");
        mediatorReplacementCheckBox.setSelected(false);
        stateMediatorAppointedComboBox.setSelectedItem("");
        FCMSMediatorAppointedComboBox.setSelectedItem("");
        settlementDateTextBox.setText("");
        statusComboBox.setSelectedItem(" ");
        sendToBoardToCloseCheckBox.setSelected(false);
        boardFinalDateTextBox.setText("");
        retentionTicklerDateTextBox.setText("");
        lateFilingCheckBox.setSelected(false);
        impasseCheckBox.setSelected(false);
        settledCheckBox.setSelected(false);
        TACheckBox.setSelected(false);
        MADCheckBox.setSelected(false);
        withdrawlCheckBox.setSelected(false);
        motionCheckBox.setSelected(false);
        dismissedCheckBox.setSelected(false);
        relatedCaseModel.setRowCount(0);
    }
    
    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);
        
        filingDateTextBox.setEnabled(true);
        filingDateTextBox.setBackground(Color.white);
        employerIDNumberTextBox.setEnabled(true);
        employerIDNumberTextBox.setBackground(Color.white);
        bargainingUnitTextBox.setEnabled(true);
        bargainingUnitTextBox.setBackground(Color.white);
        
        approxNumberOfEmployeesTextBox.setEnabled(true);
        approxNumberOfEmployeesTextBox.setBackground(Color.white);
        duplicateCaseNumberTextBox.setEnabled(true);
        duplicateCaseNumberTextBox.setBackground(Color.white);
        relatedCaseNumberTextBox.setEnabled(true);
        relatedCaseNumberTextBox.setBackground(Color.white);
        addMultiCaseButton.setVisible(true);
        
        negotiationTypeComboBox.setEnabled(true);
        expirationDateTextBox.setEnabled(true);
        expirationDateTextBox.setBackground(Color.white);
        NTNFiledByComboBox.setEnabled(true);
        negotiationPeriodComboBox.setEnabled(true);
        multiUnitBargainingRequestedCheckBox.setEnabled(true);
        mediatorAppointedDateTextBox.setEnabled(true);
        mediatorAppointedDateTextBox.setBackground(Color.white);
        mediatorReplacementCheckBox.setEnabled(true);
        stateMediatorAppointedComboBox.setEnabled(true);
        FCMSMediatorAppointedComboBox.setEnabled(true);
        settlementDateTextBox.setEnabled(true);
        settlementDateTextBox.setBackground(Color.white);
        statusComboBox.setEnabled(true);
        sendToBoardToCloseCheckBox.setEnabled(true);
        boardFinalDateTextBox.setEnabled(true);
        boardFinalDateTextBox.setBackground(Color.white);
        lateFilingCheckBox.setEnabled(true);
        impasseCheckBox.setEnabled(true);
        settledCheckBox.setEnabled(true);
        TACheckBox.setEnabled(true);
        MADCheckBox.setEnabled(true);
        withdrawlCheckBox.setEnabled(true);
        motionCheckBox.setEnabled(true);
        dismissedCheckBox.setEnabled(true);
    }
    
    
    public void disableUpdate(boolean save) {
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);
        
        filingDateTextBox.setEnabled(false);
        filingDateTextBox.setBackground(new Color(238,238,238));
        employerIDNumberTextBox.setEnabled(false);
        employerIDNumberTextBox.setBackground(new Color(238,238,238));
        bargainingUnitTextBox.setEnabled(false);
        bargainingUnitTextBox.setBackground(new Color(238,238,238));
        
        approxNumberOfEmployeesTextBox.setEnabled(false);
        approxNumberOfEmployeesTextBox.setBackground(new Color(238,238,238));
        duplicateCaseNumberTextBox.setEnabled(false);
        duplicateCaseNumberTextBox.setBackground(new Color(238,238,238));
        relatedCaseNumberTextBox.setEnabled(false);
        relatedCaseNumberTextBox.setBackground(new Color(238,238,238));
        addMultiCaseButton.setVisible(false);
        
        negotiationTypeComboBox.setEnabled(false);
        expirationDateTextBox.setEnabled(false);
        expirationDateTextBox.setBackground(new Color(238,238,238));
        NTNFiledByComboBox.setEnabled(false);
        negotiationPeriodComboBox.setEnabled(false);
        multiUnitBargainingRequestedCheckBox.setEnabled(false);
        mediatorAppointedDateTextBox.setEnabled(false);
        mediatorAppointedDateTextBox.setBackground(new Color(238,238,238));
        mediatorReplacementCheckBox.setEnabled(false);
        stateMediatorAppointedComboBox.setEnabled(false);
        FCMSMediatorAppointedComboBox.setEnabled(false);
        settlementDateTextBox.setEnabled(false);
        settlementDateTextBox.setBackground(new Color(238,238,238));
        statusComboBox.setEnabled(false);
        sendToBoardToCloseCheckBox.setEnabled(false);
        boardFinalDateTextBox.setEnabled(false);
        boardFinalDateTextBox.setBackground(new Color(238,238,238));
        lateFilingCheckBox.setEnabled(false);
        impasseCheckBox.setEnabled(false);
        settledCheckBox.setEnabled(false);
        TACheckBox.setEnabled(false);
        MADCheckBox.setEnabled(false);
        withdrawlCheckBox.setEnabled(false);
        motionCheckBox.setEnabled(false);
        dismissedCheckBox.setEnabled(false);
        
        if(save) {
            saveInformation();
        }
        
        loadInformation();
    }
    
    private void saveInformation() {
        MEDCase newInformation = new MEDCase();
        
        newInformation.fileDate = filingDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(filingDateTextBox.getText()));

        newInformation.employerIDNumber = employerIDNumberTextBox.getText().equals("") ? null : employerIDNumberTextBox.getText();
        newInformation.bargainingUnitNumber = bargainingUnitTextBox.getText().equals("") ? null : bargainingUnitTextBox.getText();
        
        newInformation.approxNumberOfEmployees = approxNumberOfEmployeesTextBox.getText().equals("") ? null : approxNumberOfEmployeesTextBox.getText();
        
        newInformation.duplicateCaseNumber = duplicateCaseNumberTextBox.getText().equals("") ? null : duplicateCaseNumberTextBox.getText();
        newInformation.relatedCaseNumber = relatedCaseNumberTextBox.getText().equals("") ? null : relatedCaseNumberTextBox.getText();       

        newInformation.negotiationType = negotiationTypeComboBox.getSelectedItem().toString().trim().equals("") ? null : negotiationTypeComboBox.getSelectedItem().toString();
        newInformation.expirationDate = expirationDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(expirationDateTextBox.getText()));
        newInformation.NTNFiledBy = NTNFiledByComboBox.getSelectedItem().toString().trim().equals("") ? null : NTNFiledByComboBox.getSelectedItem().toString();
        newInformation.negotiationPeriod = negotiationPeriodComboBox.getSelectedItem().toString().trim().equals("") ? null : negotiationPeriodComboBox.getSelectedItem().toString();
        newInformation.multiunitBargainingRequested = multiUnitBargainingRequestedCheckBox.isSelected();
        newInformation.mediatorAppointedDate = mediatorAppointedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(mediatorAppointedDateTextBox.getText()));
        newInformation.mediatorReplacement = mediatorReplacementCheckBox.isSelected();
        newInformation.stateMediatorAppointedID = stateMediatorAppointedComboBox.getSelectedItem().toString().equals("") ? null : Mediator.getMediatorIDByName(stateMediatorAppointedComboBox.getSelectedItem().toString());
        newInformation.FMCSMediatorAppointedID = FCMSMediatorAppointedComboBox.getSelectedItem().toString().equals("") ? null : Mediator.getMediatorIDByName(FCMSMediatorAppointedComboBox.getSelectedItem().toString());
        newInformation.settlementDate = settlementDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(settlementDateTextBox.getText()));
        newInformation.caseStatus = statusComboBox.getSelectedItem().toString().trim().equals("") ? null : statusComboBox.getSelectedItem().toString();
        newInformation.sendToBoardToClose = sendToBoardToCloseCheckBox.isSelected();
        newInformation.boardFinalDate = boardFinalDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boardFinalDateTextBox.getText()));
        newInformation.retentionTicklerDate = retentionTicklerDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(retentionTicklerDateTextBox.getText()));
        newInformation.lateFiling = lateFilingCheckBox.isSelected();
        newInformation.impasse = impasseCheckBox.isSelected();
        newInformation.settled = settledCheckBox.isSelected();
        newInformation.TA = TACheckBox.isSelected();
        newInformation.MAD = MADCheckBox.isSelected();
        newInformation.withdrawl = withdrawlCheckBox.isSelected();
        newInformation.motion = motionCheckBox.isSelected();
        newInformation.dismissed = dismissedCheckBox.isSelected();
        
        MEDCase.updateStatusInformation(newInformation, orginalInformation);
    }
    
    public void loadStateMediators() {
        stateMediatorAppointedComboBox.removeAllItems();
        
        stateMediatorAppointedComboBox.addItem("");
        
        List currentOwnerList = Mediator.loadMediators("State");
        
        for (Object currentOwners : currentOwnerList) {
            Mediator med = (Mediator) currentOwners;
            
            stateMediatorAppointedComboBox.addItem(med.firstName + " " + med.lastName);
        }
    }
    
    public void loadFMCSMediators() {
        FCMSMediatorAppointedComboBox.removeAllItems();
        
        FCMSMediatorAppointedComboBox.addItem("");
        
        List currentOwnerList = Mediator.loadMediators("FMCS");
        
        for (Object currentOwners : currentOwnerList) {
            Mediator med = (Mediator) currentOwners;
            
            FCMSMediatorAppointedComboBox.addItem(med.firstName + " " + med.lastName);
        }
    }
    
    public void loadInformation() {
        orginalInformation = MEDCase.loadStatusInformation();
        
        loadStateMediators();
        loadFMCSMediators();
        loadRelatedCasesTable();
        
        filingDateTextBox.setText(orginalInformation.fileDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.fileDate.getTime())) : "");
        employerIDNumberTextBox.setText(orginalInformation.employerIDNumber != null ? orginalInformation.employerIDNumber : "");
        bargainingUnitTextBox.setText(orginalInformation.bargainingUnitNumber != null ? orginalInformation.bargainingUnitNumber : "");
        
        if(orginalInformation.bargainingUnitNumber != null) {
            setBUNumberCheckBoxes(BargainingUnit.getCertStatus(orginalInformation.bargainingUnitNumber));
        }
        
        approxNumberOfEmployeesTextBox.setText((orginalInformation.approxNumberOfEmployees != null ? orginalInformation.approxNumberOfEmployees : ""));
        
        duplicateCaseNumberTextBox.setText(orginalInformation.duplicateCaseNumber != null ? orginalInformation.duplicateCaseNumber : "");
        relatedCaseNumberTextBox.setText(orginalInformation.relatedCaseNumber != null ? orginalInformation.relatedCaseNumber : "");
        
        negotiationTypeComboBox.setSelectedItem(orginalInformation.negotiationType != null ? orginalInformation.negotiationType : " ");
        expirationDateTextBox.setText(orginalInformation.expirationDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.expirationDate.getTime())) : "");
        NTNFiledByComboBox.setSelectedItem(orginalInformation.NTNFiledBy != null ? orginalInformation.NTNFiledBy : " ");
        negotiationPeriodComboBox.setSelectedItem(orginalInformation.negotiationPeriod != null ? orginalInformation.negotiationPeriod : " ");
        multiUnitBargainingRequestedCheckBox.setSelected(orginalInformation.multiunitBargainingRequested == true);
        mediatorAppointedDateTextBox.setText(orginalInformation.mediatorAppointedDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.mediatorAppointedDate.getTime())) : "");
        mediatorReplacementCheckBox.setSelected(orginalInformation.mediatorReplacement == true);
        stateMediatorAppointedComboBox.setSelectedItem(orginalInformation.stateMediatorAppointedID != null ? Mediator.getMediatorNameByID(orginalInformation.stateMediatorAppointedID) : "");
        FCMSMediatorAppointedComboBox.setSelectedItem(orginalInformation.FMCSMediatorAppointedID != null ? Mediator.getMediatorNameByID(orginalInformation.FMCSMediatorAppointedID) : "");
        settlementDateTextBox.setText(orginalInformation.settlementDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.settlementDate.getTime())) : "");
        sendToBoardToCloseCheckBox.setSelected(orginalInformation.sendToBoardToClose == true);
        boardFinalDateTextBox.setText(orginalInformation.boardFinalDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.boardFinalDate.getTime())) : "");
        retentionTicklerDateTextBox.setText(orginalInformation.retentionTicklerDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.retentionTicklerDate.getTime())) : "");
        lateFilingCheckBox.setSelected(orginalInformation.lateFiling == true);
        impasseCheckBox.setSelected(orginalInformation.impasse == true);
        settledCheckBox.setSelected(orginalInformation.settled == true);
        TACheckBox.setSelected(orginalInformation.TA == true);
        MADCheckBox.setSelected(orginalInformation.MAD == true);
        withdrawlCheckBox.setSelected(orginalInformation.withdrawl == true);
        motionCheckBox.setSelected(orginalInformation.motion == true);
        dismissedCheckBox.setSelected(orginalInformation.dismissed == true);
        statusComboBox.setSelectedItem(orginalInformation.caseStatus == null ? "" : orginalInformation.caseStatus);
    }
    
    private void setBUNumberCheckBoxes(String certStatus) {

        switch(certStatus) {
            case "B":
                boardCertifiedCheckBox.setSelected(true);
                deemedCertifiedCheckBox.setSelected(false);
                break;
            case "D":
                boardCertifiedCheckBox.setSelected(false);
                deemedCertifiedCheckBox.setSelected(true);
                break;
            default:
                boardCertifiedCheckBox.setSelected(false);
                deemedCertifiedCheckBox.setSelected(false);
                break;
        }
    }
    
    public void loadRelatedCasesTable() {
     
        relatedCaseModel.setRowCount(0);
        
        List relatedCases = RelatedCase.loadRelatedCases();
        
        for (Object relatedCase : relatedCases) {
            relatedCaseModel.addRow(new Object[] {relatedCase});
        }
        relatedCaseTable.clearSelection();
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
        boardCertifiedCheckBox = new javax.swing.JCheckBox();
        jLabel4 = new javax.swing.JLabel();
        bargainingUnitTextBox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        approxNumberOfEmployeesTextBox = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        employerIDNumberTextBox = new javax.swing.JTextField();
        deemedCertifiedCheckBox = new javax.swing.JCheckBox();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        relatedCaseTable = new javax.swing.JTable();
        addMultiCaseButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        duplicateCaseNumberTextBox = new javax.swing.JTextField();
        relatedCaseNumberTextBox = new javax.swing.JTextField();
        filingDateTextBox = new com.alee.extended.date.WebDateField();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        multiUnitBargainingRequestedCheckBox = new javax.swing.JCheckBox();
        jLabel13 = new javax.swing.JLabel();
        mediatorReplacementCheckBox = new javax.swing.JCheckBox();
        jLabel15 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        sendToBoardToCloseCheckBox = new javax.swing.JCheckBox();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        lateFilingCheckBox = new javax.swing.JCheckBox();
        settledCheckBox = new javax.swing.JCheckBox();
        impasseCheckBox = new javax.swing.JCheckBox();
        TACheckBox = new javax.swing.JCheckBox();
        withdrawlCheckBox = new javax.swing.JCheckBox();
        MADCheckBox = new javax.swing.JCheckBox();
        motionCheckBox = new javax.swing.JCheckBox();
        dismissedCheckBox = new javax.swing.JCheckBox();
        negotiationTypeComboBox = new javax.swing.JComboBox<>();
        expirationDateTextBox = new com.alee.extended.date.WebDateField();
        NTNFiledByComboBox = new javax.swing.JComboBox<>();
        negotiationPeriodComboBox = new javax.swing.JComboBox<>();
        mediatorAppointedDateTextBox = new com.alee.extended.date.WebDateField();
        stateMediatorAppointedComboBox = new javax.swing.JComboBox<>();
        FCMSMediatorAppointedComboBox = new javax.swing.JComboBox<>();
        settlementDateTextBox = new com.alee.extended.date.WebDateField();
        boardFinalDateTextBox = new com.alee.extended.date.WebDateField();
        retentionTicklerDateTextBox = new com.alee.extended.date.WebDateField();
        statusComboBox = new javax.swing.JComboBox<>();

        boardCertifiedCheckBox.setText("Board Certified");
        boardCertifiedCheckBox.setEnabled(false);

        jLabel4.setText("BUN Number:");

        bargainingUnitTextBox.setBackground(new java.awt.Color(238, 238, 238));
        bargainingUnitTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        bargainingUnitTextBox.setEnabled(false);
        bargainingUnitTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bargainingUnitTextBoxMouseClicked(evt);
            }
        });

        jLabel11.setText("Related Case Number:");

        approxNumberOfEmployeesTextBox.setBackground(new java.awt.Color(238, 238, 238));
        approxNumberOfEmployeesTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        approxNumberOfEmployeesTextBox.setEnabled(false);

        jLabel10.setText("Duplicate Case Number:");

        jLabel3.setText("Employer ID Number:");

        employerIDNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        employerIDNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        employerIDNumberTextBox.setEnabled(false);
        employerIDNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employerIDNumberTextBoxMouseClicked(evt);
            }
        });

        deemedCertifiedCheckBox.setText("Deemed Certified");
        deemedCertifiedCheckBox.setEnabled(false);

        jLabel7.setText("Approx Number of Employees:");

        jPanel4.setPreferredSize(new java.awt.Dimension(409, 107));
        jPanel4.setSize(new java.awt.Dimension(409, 107));

        jLabel2.setText("Multi Case Numbers:");

        relatedCaseTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        relatedCaseTable.setRequestFocusEnabled(false);
        relatedCaseTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatedCaseTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(relatedCaseTable);

        addMultiCaseButton.setText("+");
        addMultiCaseButton.setMaximumSize(new java.awt.Dimension(29, 91));
        addMultiCaseButton.setMinimumSize(new java.awt.Dimension(29, 91));
        addMultiCaseButton.setPreferredSize(new java.awt.Dimension(29, 91));
        addMultiCaseButton.setSize(new java.awt.Dimension(29, 91));
        addMultiCaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMultiCaseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 398, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addMultiCaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addMultiCaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jLabel1.setText("Filing Date:");

        duplicateCaseNumberTextBox.setEditable(false);
        duplicateCaseNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        duplicateCaseNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        duplicateCaseNumberTextBox.setEnabled(false);
        duplicateCaseNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                duplicateCaseNumberTextBoxMouseClicked(evt);
            }
        });

        relatedCaseNumberTextBox.setEditable(false);
        relatedCaseNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        relatedCaseNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        relatedCaseNumberTextBox.setEnabled(false);
        relatedCaseNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                relatedCaseNumberTextBoxMouseClicked(evt);
            }
        });

        filingDateTextBox.setEditable(false);
        filingDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        filingDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        filingDateTextBox.setEnabled(false);
        filingDateTextBox.setDateFormat(Global.mmddyyyy);

        filingDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            filingDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    filingDateTextBoxMouseClicked(evt);
                }
            });

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                    .addContainerGap(10, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, 433, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                .addComponent(jLabel3)
                                .addComponent(jLabel1)
                                .addComponent(jLabel10)
                                .addComponent(jLabel11)
                                .addComponent(jLabel4))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addGroup(jPanel1Layout.createSequentialGroup()
                                    .addComponent(boardCertifiedCheckBox)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(deemedCertifiedCheckBox))
                                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel1Layout.createSequentialGroup()
                                        .addComponent(jLabel7)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(approxNumberOfEmployeesTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 75, Short.MAX_VALUE))
                                    .addComponent(employerIDNumberTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(filingDateTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(relatedCaseNumberTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(duplicateCaseNumberTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(bargainingUnitTextBox, javax.swing.GroupLayout.Alignment.LEADING)))))
                    .addGap(12, 12, 12))
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(filingDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(employerIDNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel4)
                        .addComponent(bargainingUnitTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(boardCertifiedCheckBox)
                        .addComponent(deemedCertifiedCheckBox))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(approxNumberOfEmployeesTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel10)
                        .addComponent(duplicateCaseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel11)
                        .addComponent(relatedCaseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 169, Short.MAX_VALUE))
            );

            jLabel5.setText("Negotiation Type:");

            jLabel6.setText("Expiration Date:");

            jLabel8.setText("NTN Filed By:");

            jLabel9.setText("Negotiation Period:");

            multiUnitBargainingRequestedCheckBox.setText("Multi-Unit Bargaining Requested");
            multiUnitBargainingRequestedCheckBox.setEnabled(false);

            jLabel13.setText("Mediator Appointed Date:");

            mediatorReplacementCheckBox.setText("Mediator Replacement");
            mediatorReplacementCheckBox.setEnabled(false);

            jLabel15.setText("State Mediator Appointed:");

            jLabel17.setText("FMCS Mediator Appointed:");

            jLabel19.setText("Settlement Date:");

            jLabel20.setText("Status:");

            sendToBoardToCloseCheckBox.setText("Send To Board To Close");
            sendToBoardToCloseCheckBox.setEnabled(false);

            jLabel22.setText("Board Final Date:");

            jLabel23.setText("Retention Tickler Date:");

            lateFilingCheckBox.setText("Late Filing");
            lateFilingCheckBox.setEnabled(false);

            settledCheckBox.setText("Settled");
            settledCheckBox.setEnabled(false);

            impasseCheckBox.setText("Impasse");
            impasseCheckBox.setEnabled(false);

            TACheckBox.setText("TA");
            TACheckBox.setEnabled(false);

            withdrawlCheckBox.setText("Withdrawl");
            withdrawlCheckBox.setEnabled(false);

            MADCheckBox.setText("MAD");
            MADCheckBox.setEnabled(false);

            motionCheckBox.setText("Motion");
            motionCheckBox.setEnabled(false);

            dismissedCheckBox.setText("Dismissed");
            dismissedCheckBox.setEnabled(false);

            negotiationTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "SE - Statutory Expiration", "SI - Statutory Initial", "SR - Statutory Reopener", "ZE - MAD Expiration", "ZI - MAD Initial", "ZR - MAD Reopener", " " }));
            negotiationTypeComboBox.setSelectedIndex(6);
            negotiationTypeComboBox.setEnabled(false);

            expirationDateTextBox.setEditable(false);
            expirationDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
            expirationDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            expirationDateTextBox.setEnabled(false);
            expirationDateTextBox.setDateFormat(Global.mmddyyyy);

            expirationDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );
                expirationDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        expirationDateTextBoxMouseClicked(evt);
                    }
                });

                NTNFiledByComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employer", "Union", "" }));
                NTNFiledByComboBox.setSelectedIndex(2);
                NTNFiledByComboBox.setEnabled(false);

                negotiationPeriodComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "60", "90", " " }));
                negotiationPeriodComboBox.setSelectedIndex(2);
                negotiationPeriodComboBox.setEnabled(false);

                mediatorAppointedDateTextBox.setEditable(false);
                mediatorAppointedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                mediatorAppointedDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                mediatorAppointedDateTextBox.setEnabled(false);
                mediatorAppointedDateTextBox.setDateFormat(Global.mmddyyyy);

                mediatorAppointedDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                    {
                        @Override
                        public void customize ( final WebCalendar calendar )
                        {
                            calendar.setStartWeekFromSunday ( true );
                        }
                    } );
                    mediatorAppointedDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            mediatorAppointedDateTextBoxMouseClicked(evt);
                        }
                    });

                    stateMediatorAppointedComboBox.setEnabled(false);

                    FCMSMediatorAppointedComboBox.setEnabled(false);

                    settlementDateTextBox.setEditable(false);
                    settlementDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                    settlementDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                    settlementDateTextBox.setEnabled(false);
                    settlementDateTextBox.setDateFormat(Global.mmddyyyy);

                    settlementDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                        {
                            @Override
                            public void customize ( final WebCalendar calendar )
                            {
                                calendar.setStartWeekFromSunday ( true );
                            }
                        } );
                        settlementDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                settlementDateTextBoxMouseClicked(evt);
                            }
                        });

                        boardFinalDateTextBox.setEditable(false);
                        boardFinalDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                        boardFinalDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                        boardFinalDateTextBox.setEnabled(false);
                        boardFinalDateTextBox.setDateFormat(Global.mmddyyyy);

                        boardFinalDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                            {
                                @Override
                                public void customize ( final WebCalendar calendar )
                                {
                                    calendar.setStartWeekFromSunday ( true );
                                }
                            } );
                            boardFinalDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    boardFinalDateTextBoxMouseClicked(evt);
                                }
                            });

                            retentionTicklerDateTextBox.setEditable(false);
                            retentionTicklerDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                            retentionTicklerDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                            retentionTicklerDateTextBox.setEnabled(false);
                            retentionTicklerDateTextBox.setDateFormat(Global.mmddyyyy);

                            retentionTicklerDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                {
                                    @Override
                                    public void customize ( final WebCalendar calendar )
                                    {
                                        calendar.setStartWeekFromSunday ( true );
                                    }
                                } );
                                retentionTicklerDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                                        retentionTicklerDateTextBoxMouseClicked(evt);
                                    }
                                });

                                statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Open", "Closed", "Error", " " }));
                                statusComboBox.setSelectedIndex(3);
                                statusComboBox.setEnabled(false);

                                javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                                jPanel2.setLayout(jPanel2Layout);
                                jPanel2Layout.setHorizontalGroup(
                                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel23)
                                            .addComponent(jLabel22)
                                            .addComponent(jLabel20)
                                            .addComponent(jLabel19)
                                            .addComponent(jLabel17)
                                            .addComponent(jLabel15)
                                            .addComponent(jLabel13)
                                            .addComponent(jLabel9)
                                            .addComponent(jLabel8)
                                            .addComponent(jLabel6)
                                            .addComponent(jLabel5))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(lateFilingCheckBox)
                                                .addGap(18, 18, 18)
                                                .addComponent(impasseCheckBox)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(stateMediatorAppointedComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(FCMSMediatorAppointedComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(settlementDateTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(statusComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addContainerGap())
                                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(mediatorAppointedDateTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(negotiationTypeComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel2Layout.createSequentialGroup()
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(multiUnitBargainingRequestedCheckBox)
                                                            .addComponent(mediatorReplacementCheckBox)
                                                            .addComponent(sendToBoardToCloseCheckBox)
                                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(settledCheckBox)
                                                                    .addComponent(MADCheckBox)
                                                                    .addComponent(motionCheckBox))
                                                                .addGap(39, 39, 39)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                                    .addComponent(dismissedCheckBox)
                                                                    .addComponent(withdrawlCheckBox)
                                                                    .addComponent(TACheckBox))))
                                                        .addGap(0, 30, Short.MAX_VALUE))
                                                    .addComponent(expirationDateTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(NTNFiledByComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addComponent(negotiationPeriodComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addGap(6, 6, 6))
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(boardFinalDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addComponent(retentionTicklerDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addContainerGap())))
                                );
                                jPanel2Layout.setVerticalGroup(
                                    jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addComponent(negotiationTypeComboBox)
                                            .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel6)
                                            .addComponent(expirationDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(NTNFiledByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGap(4, 4, 4)
                                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(negotiationPeriodComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)
                                        .addComponent(multiUnitBargainingRequestedCheckBox)
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel13)
                                            .addComponent(mediatorAppointedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(4, 4, 4)
                                        .addComponent(mediatorReplacementCheckBox)
                                        .addGap(4, 4, 4)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(stateMediatorAppointedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(FCMSMediatorAppointedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(settlementDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel19))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(sendToBoardToCloseCheckBox)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(boardFinalDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel22))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(retentionTicklerDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel23))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(lateFilingCheckBox)
                                            .addComponent(impasseCheckBox))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(TACheckBox)
                                            .addComponent(settledCheckBox))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(MADCheckBox)
                                            .addComponent(withdrawlCheckBox))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(dismissedCheckBox)
                                            .addComponent(motionCheckBox))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                );

                                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                                this.setLayout(layout);
                                layout.setHorizontalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                );

                                layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jPanel1, jPanel2});

                                layout.setVerticalGroup(
                                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 553, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                );
                            }// </editor-fold>//GEN-END:initComponents

    private void addMultiCaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMultiCaseButtonActionPerformed
        new AddMultiCaseDialog((JFrame) Global.root, true);
        loadRelatedCasesTable();
    }//GEN-LAST:event_addMultiCaseButtonActionPerformed

    private void filingDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filingDateTextBoxMouseClicked
        clearDate(filingDateTextBox, evt);
    }//GEN-LAST:event_filingDateTextBoxMouseClicked

    private void expirationDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_expirationDateTextBoxMouseClicked
        clearDate(expirationDateTextBox, evt);
    }//GEN-LAST:event_expirationDateTextBoxMouseClicked

    private void mediatorAppointedDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mediatorAppointedDateTextBoxMouseClicked
        clearDate(mediatorAppointedDateTextBox, evt);
    }//GEN-LAST:event_mediatorAppointedDateTextBoxMouseClicked

    private void settlementDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_settlementDateTextBoxMouseClicked
        clearDate(settlementDateTextBox, evt);
    }//GEN-LAST:event_settlementDateTextBoxMouseClicked

    private void boardFinalDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boardFinalDateTextBoxMouseClicked
        clearDate(boardFinalDateTextBox, evt);
    }//GEN-LAST:event_boardFinalDateTextBoxMouseClicked

    private void retentionTicklerDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_retentionTicklerDateTextBoxMouseClicked
        clearDate(retentionTicklerDateTextBox, evt);
    }//GEN-LAST:event_retentionTicklerDateTextBoxMouseClicked

    private void employerIDNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employerIDNumberTextBoxMouseClicked
        if(evt.getClickCount() == 2) {
            if(employerIDNumberTextBox.isEnabled()) {
                employerSearch search = new employerSearch((JFrame) Global.root.getRootPane().getParent(), true, employerIDNumberTextBox.getText().trim());
                employerIDNumberTextBox.setText(search.getEmployerNumber());
                search.dispose();
            } else {
                new employerDetail((JFrame) Global.root.getRootPane().getParent(), true, employerIDNumberTextBox.getText().trim());
            }
        }    
    }//GEN-LAST:event_employerIDNumberTextBoxMouseClicked

    private void bargainingUnitTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bargainingUnitTextBoxMouseClicked
        if(evt.getClickCount() == 2) {
            if(bargainingUnitTextBox.isEnabled()) {
                buNumberSearch search = new buNumberSearch((JFrame) Global.root.getRootPane().getParent(), true, employerIDNumberTextBox.getText().trim(), bargainingUnitTextBox.getText().trim(), "");
                bargainingUnitTextBox.setText(search.getBuNumber());
                setBUNumberCheckBoxes(search.getCertStatus() != null ? search.getCertStatus() : "");
                if(employerIDNumberTextBox.getText().equals("")) {
                    employerIDNumberTextBox.setText(search.getBuNumber().split("-")[0]);
                }
                search.dispose();
            } else {
//                new employerDetail((JFrame) Global.root.getRootPane().getParent(), true, employerIDNumberTextBox.getText().trim());
            }
        }
    }//GEN-LAST:event_bargainingUnitTextBoxMouseClicked

    private void relatedCaseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relatedCaseTableMouseClicked
        if(relatedCaseTable.getSelectedRow() > -1) {
            if(evt.getButton() == MouseEvent.BUTTON3) {
                new RemoveMultiCaseDialog(
                    (JFrame) Global.root.getRootPane().getParent(),
                    true,
                    relatedCaseTable.getValueAt(relatedCaseTable.getSelectedRow(), 0).toString().trim()
                );
                loadRelatedCasesTable();
            }
        }
    }//GEN-LAST:event_relatedCaseTableMouseClicked

    private void duplicateCaseNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_duplicateCaseNumberTextBoxMouseClicked
        if(duplicateCaseNumberTextBox.isEnabled()) {
            if(evt.getButton() == MouseEvent.BUTTON3) {
                MEDClearCaseDialog clear = new MEDClearCaseDialog((JFrame) Global.root.getRootPane().getParent(), true);
                if(clear.reset) {
                    duplicateCaseNumberTextBox.setText("");
                }
                clear.dispose();
            } else if(evt.getClickCount() == 2) {
                MEDAddDuplicateCaseDialog related = new MEDAddDuplicateCaseDialog((JFrame) Global.root.getRootPane().getParent(), true);
                duplicateCaseNumberTextBox.setText(related.getDuplicateCase());
                related.dispose();
            }
        }
    }//GEN-LAST:event_duplicateCaseNumberTextBoxMouseClicked

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
    private javax.swing.JComboBox<String> FCMSMediatorAppointedComboBox;
    private javax.swing.JCheckBox MADCheckBox;
    private javax.swing.JComboBox<String> NTNFiledByComboBox;
    private javax.swing.JCheckBox TACheckBox;
    private javax.swing.JButton addMultiCaseButton;
    private javax.swing.JTextField approxNumberOfEmployeesTextBox;
    private javax.swing.JTextField bargainingUnitTextBox;
    private javax.swing.JCheckBox boardCertifiedCheckBox;
    private com.alee.extended.date.WebDateField boardFinalDateTextBox;
    private javax.swing.JCheckBox deemedCertifiedCheckBox;
    private javax.swing.JCheckBox dismissedCheckBox;
    private javax.swing.JTextField duplicateCaseNumberTextBox;
    private javax.swing.JTextField employerIDNumberTextBox;
    private com.alee.extended.date.WebDateField expirationDateTextBox;
    private com.alee.extended.date.WebDateField filingDateTextBox;
    private javax.swing.JCheckBox impasseCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JCheckBox lateFilingCheckBox;
    private com.alee.extended.date.WebDateField mediatorAppointedDateTextBox;
    private javax.swing.JCheckBox mediatorReplacementCheckBox;
    private javax.swing.JCheckBox motionCheckBox;
    private javax.swing.JCheckBox multiUnitBargainingRequestedCheckBox;
    private javax.swing.JComboBox<String> negotiationPeriodComboBox;
    private javax.swing.JComboBox<String> negotiationTypeComboBox;
    private javax.swing.JTextField relatedCaseNumberTextBox;
    private javax.swing.JTable relatedCaseTable;
    private com.alee.extended.date.WebDateField retentionTicklerDateTextBox;
    private javax.swing.JCheckBox sendToBoardToCloseCheckBox;
    private javax.swing.JCheckBox settledCheckBox;
    private com.alee.extended.date.WebDateField settlementDateTextBox;
    private javax.swing.JComboBox<String> stateMediatorAppointedComboBox;
    private javax.swing.JComboBox<String> statusComboBox;
    private javax.swing.JCheckBox withdrawlCheckBox;
    // End of variables declaration//GEN-END:variables
}
