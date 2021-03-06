/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.bunumber.buNumberSearch;
import parker.serb.employer.employerDetail;
import parker.serb.employer.employerSearch;
import parker.serb.relatedcase.AddNewRelatedCase;
import parker.serb.relatedcase.RemoveRelatedCaseDialog;
import parker.serb.sql.BargainingUnit;
import parker.serb.sql.County;
import parker.serb.sql.DocketNotifications;
import parker.serb.sql.REPCase;
import parker.serb.sql.REPCaseStatus;
import parker.serb.sql.REPCaseType;
import parker.serb.sql.RelatedCase;
import parker.serb.sql.User;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parker
 */
public class REPCaseInformationPanel extends javax.swing.JPanel {

    REPCase caseInformation;
    DefaultTableModel relatedCaseModel;

    /**
     * Creates new form REPCaseInformationPanel
     */
    public REPCaseInformationPanel() {
        initComponents();
        addRenderer();
        relatedCaseModel = (DefaultTableModel) relatedCaseTable.getModel();
        addRelatedCaseButton.setVisible(false);
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

        countyComboBox.setEnabled(true);
        employerIDNumberTextBox.setEnabled(true);
        employerIDNumberTextBox.setBackground(Color.WHITE);
        bargainingUnitNumberTextBox.setEnabled(true);
        bargainingUnitNumberTextBox.setBackground(Color.WHITE);

        boardCertifiedCheckBox.setEnabled(true);
        deemedCertifiedCheckBox.setEnabled(true);
        certificationRevokedCheckBox.setEnabled(true);

        notesTextArea.setEnabled(true);
        notesTextArea.setBackground(Color.WHITE);

        addRelatedCaseButton.setVisible(true);

        //Dates
        fileDateTextBox.setEnabled(true);
        fileDateTextBox.setBackground(Color.WHITE);
        amendedFilingDateTextBox.setEnabled(true);
        amendedFilingDateTextBox.setBackground(Color.WHITE);
        alphaListRecepitDateTextBox.setEnabled(true);
        alphaListRecepitDateTextBox.setBackground(Color.WHITE);
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
        SOIReturnInitials.setEnabled(true);
        REPClosedCaseDueDateTextBox.setEnabled(true);
        REPClosedCaseDueDateTextBox.setBackground(Color.WHITE);
        actualREPClosedDateTextBox.setEnabled(true);
        actualREPClosedDateTextBox.setBackground(Color.WHITE);
        repClosedUser.setEnabled(true);
        repClosedUser.setBackground(Color.WHITE);
        actualClerksClosedDate.setEnabled(true);
        actualClerksClosedDate.setBackground(Color.WHITE);
        clerksClosedUser.setEnabled(true);
        clerksClosedUser.setBackground(Color.WHITE);
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

        countyComboBox.setEnabled(false);
        employerIDNumberTextBox.setEnabled(false);
        employerIDNumberTextBox.setBackground(new Color(238,238,238));
        bargainingUnitNumberTextBox.setEnabled(false);
        bargainingUnitNumberTextBox.setBackground(new Color(238,238,238));

        boardCertifiedCheckBox.setEnabled(false);
        deemedCertifiedCheckBox.setEnabled(false);
        certificationRevokedCheckBox.setEnabled(false);

        notesTextArea.setEnabled(false);
        notesTextArea.setBackground(new Color(238,238,238));

        addRelatedCaseButton.setVisible(false);

        fileDateTextBox.setEnabled(false);
        fileDateTextBox.setBackground(new Color(238,238,238));
        amendedFilingDateTextBox.setEnabled(false);
        amendedFilingDateTextBox.setBackground(new Color(238,238,238));
        alphaListRecepitDateTextBox.setEnabled(false);
        alphaListRecepitDateTextBox.setBackground(new Color(238,238,238));
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
        SOIReturnInitials.setEnabled(false);
        REPClosedCaseDueDateTextBox.setEnabled(false);
        REPClosedCaseDueDateTextBox.setBackground(new Color(238,238,238));
        actualREPClosedDateTextBox.setEnabled(false);
        actualREPClosedDateTextBox.setBackground(new Color(238,238,238));
        repClosedUser.setEnabled(false);
        repClosedUser.setBackground(new Color(238,238,238));
        actualClerksClosedDate.setEnabled(false);
        actualClerksClosedDate.setBackground(new Color(238,238,238));
        clerksClosedUser.setEnabled(false);
        clerksClosedUser.setBackground(new Color(238,238,238));

        if(runSave)
            saveInformation();
    }

    void clearAll() {
        caseTypeComboBox.setSelectedItem("");
        status1ComboBox.setSelectedItem("");
        status2ComboBox.setSelectedItem("");
        currentOwnerComboBox.setSelectedItem("");
        countyComboBox.setSelectedItem("");
        employerIDNumberTextBox.setText("");
        bargainingUnitNumberTextBox.setText("");
        bargainingUnitNameTextBox.setText("");
        boardCertifiedCheckBox.setSelected(false);
        deemedCertifiedCheckBox.setSelected(false);
        certificationRevokedCheckBox.setSelected(false);
        notesTextArea.setText("");
        fileDateTextBox.setText("");
        amendedFilingDateTextBox.setText("");
        alphaListRecepitDateTextBox.setText("");
        finalBoardDateTextBox.setText("");
        registrationLetterSentTextBox.setText("");
        dateOfAppealTextBox.setText("");
        courtClosedDateTextBox.setText("");
        returnSOIDueDateTextBox.setText("");
        actualSOIReturnDateTextBox.setText("");
        SOIReturnInitials.setSelectedItem("");
        REPClosedCaseDueDateTextBox.setText("");
        actualREPClosedDateTextBox.setText("");
        repClosedUser.setSelectedItem("");
        actualClerksClosedDate.setText("");
        clerksClosedUser.setSelectedItem("");
        relatedCaseModel.setRowCount(0);
    }

    void loadInformation() {
        loadCaseTypes();
        loadStatus();
        loadCountyComboBox();
        loadCurrentOwner();
        loadREPClosedByComboBox();
        loadClerksClosedByComboBox();
        loadRelatedCasesTable();
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

    public void loadCountyComboBox() {
        List<String> countyList = County.loadCountyList();

        countyComboBox.removeAllItems();
        countyComboBox.addItem("");

        for (Object singleCounty : countyList) {
            County county = (County) singleCounty;
            countyComboBox.addItem(county.countyName);
        }
    }

    public void loadCurrentOwner() {
        currentOwnerComboBox.removeAllItems();

        currentOwnerComboBox.addItem("");

        List currentOwnerList = User.loadSectionDropDownsPlusALJ("REP");

        for (Object currentOwners : currentOwnerList) {
            currentOwnerComboBox.addItem(currentOwners.toString());
        }
    }

    public void loadREPClosedByComboBox() {
        repClosedUser.removeAllItems();
        SOIReturnInitials.removeAllItems();

        repClosedUser.addItem("");
        SOIReturnInitials.addItem("");

        List currentOwnerList = User.loadSectionDropDowns("REP");

        for (Object currentOwners : currentOwnerList) {
            repClosedUser.addItem(currentOwners.toString());
            SOIReturnInitials.addItem(currentOwners.toString());
        }
    }

    public void loadClerksClosedByComboBox() {
        clerksClosedUser.removeAllItems();

        clerksClosedUser.addItem("");

        List currentOwnerList = User.loadSectionDropDowns("REP");

        for (Object currentOwners : currentOwnerList) {
            clerksClosedUser.addItem(currentOwners.toString());
        }
    }

    public void loadCaseInformation() {
        caseInformation = REPCase.loadCaseInformation();

        caseTypeComboBox.setSelectedItem(caseInformation.type == null ? "" : caseInformation.type);
        status1ComboBox.setSelectedItem(caseInformation.status1 == null ? "" : caseInformation.status1);
        status2ComboBox.setSelectedItem(caseInformation.status2 == null ? "" : caseInformation.status2);
        if(((DefaultComboBoxModel)currentOwnerComboBox.getModel()).getIndexOf(User.getNameByID(caseInformation.currentOwnerID)) == -1 ) {
            currentOwnerComboBox.addItem(User.getNameByID(caseInformation.currentOwnerID) );
        }
        currentOwnerComboBox.setSelectedItem(caseInformation.currentOwnerID == 0 ? "" : User.getNameByID(caseInformation.currentOwnerID));
        countyComboBox.setSelectedItem(caseInformation.county == null ? "" : caseInformation.county);
        employerIDNumberTextBox.setText(caseInformation.employerIDNumber == null ? "" : caseInformation.employerIDNumber);
        bargainingUnitNumberTextBox.setText(caseInformation.bargainingUnitNumber == null ? "" : caseInformation.bargainingUnitNumber);
        bargainingUnitNameTextBox.setText(caseInformation.bargainingUnitNumber == null ? "" : BargainingUnit.getUnitDescription(caseInformation.bargainingUnitNumber));
        bargainingUnitNameTextBox.setCaretPosition(0);
        boardCertifiedCheckBox.setSelected(caseInformation.boardCertified == true);
        deemedCertifiedCheckBox.setSelected(caseInformation.deemedCertified == true);
        certificationRevokedCheckBox.setSelected(caseInformation.certificationRevoked == true);

        notesTextArea.setText(caseInformation.note == null ? "" : caseInformation.note);

        fileDateTextBox.setText(caseInformation.fileDate != null ? Global.mmddyyyy.format(new Date(caseInformation.fileDate.getTime())) : "");
        amendedFilingDateTextBox.setText(caseInformation.amendedFiliingDate != null ? Global.mmddyyyy.format(new Date(caseInformation.amendedFiliingDate.getTime())) : "");
        alphaListRecepitDateTextBox.setText(caseInformation.alphaListDate != null ? Global.mmddyyyy.format(new Date(caseInformation.alphaListDate.getTime())) : "");
        finalBoardDateTextBox.setText(caseInformation.finalBoardDate != null ? Global.mmddyyyy.format(new Date(caseInformation.finalBoardDate.getTime())) : "");
        registrationLetterSentTextBox.setText(caseInformation.registrationLetterSent != null ? Global.mmddyyyy.format(new Date(caseInformation.registrationLetterSent.getTime())) : "");
        dateOfAppealTextBox.setText(caseInformation.dateOfAppeal != null ? Global.mmddyyyy.format(new Date(caseInformation.dateOfAppeal.getTime())) : "");
        courtClosedDateTextBox.setText(caseInformation.courtClosedDate != null ? Global.mmddyyyy.format(new Date(caseInformation.courtClosedDate.getTime())) : "");
        returnSOIDueDateTextBox.setText(caseInformation.returnSOIDueDate != null ? Global.mmddyyyy.format(new Date(caseInformation.returnSOIDueDate.getTime())) : "");
        actualSOIReturnDateTextBox.setText(caseInformation.actualSOIReturnDate != null ? Global.mmddyyyy.format(new Date(caseInformation.actualSOIReturnDate.getTime())) : "");
        SOIReturnInitials.setSelectedItem(caseInformation.SOIReturnInitials == 0 ? "" : User.getNameByID(caseInformation.SOIReturnInitials));
        REPClosedCaseDueDateTextBox.setText(caseInformation.REPClosedCaseDueDate != null ? Global.mmddyyyy.format(new Date(caseInformation.REPClosedCaseDueDate.getTime())) : "");
        actualREPClosedDateTextBox.setText(caseInformation.actualREPClosedDate != null ? Global.mmddyyyy.format(new Date(caseInformation.actualREPClosedDate.getTime())) : "");
        repClosedUser.setSelectedItem(caseInformation.REPClosedUser == 0 ? "" : User.getNameByID(caseInformation.REPClosedUser));
        actualClerksClosedDate.setText(caseInformation.actualClerksClosedDate != null ? Global.mmddyyyy.format(new Date(caseInformation.actualClerksClosedDate.getTime())) : "");
        clerksClosedUser.setSelectedItem(caseInformation.clerksClosedUser == 0 ? "" : User.getNameByID(caseInformation.clerksClosedUser));
    }

    void saveInformation() {
        REPCase newCaseInformation = new REPCase();

        newCaseInformation.type = caseTypeComboBox.getSelectedItem() == "" ? null : caseTypeComboBox.getSelectedItem().toString();
        newCaseInformation.status1 = status1ComboBox.getSelectedItem() == "" ? null : status1ComboBox.getSelectedItem().toString();
        newCaseInformation.status2 = status2ComboBox.getSelectedItem() == "" ? null : status2ComboBox.getSelectedItem().toString();
        newCaseInformation.currentOwnerID = currentOwnerComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(currentOwnerComboBox.getSelectedItem().toString());
        newCaseInformation.county = countyComboBox.getSelectedItem() == "" ? null : countyComboBox.getSelectedItem().toString();
        newCaseInformation.employerIDNumber = employerIDNumberTextBox.getText().equals("") ? null : employerIDNumberTextBox.getText();
        newCaseInformation.bargainingUnitNumber = bargainingUnitNumberTextBox.getText().equals("") ? null : bargainingUnitNumberTextBox.getText();
        newCaseInformation.boardCertified = boardCertifiedCheckBox.isSelected();
        newCaseInformation.deemedCertified = deemedCertifiedCheckBox.isSelected();
        newCaseInformation.certificationRevoked = certificationRevokedCheckBox.isSelected();

        newCaseInformation.note = notesTextArea.getText().equals("") ? null : notesTextArea.getText();

        newCaseInformation.fileDate = fileDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(fileDateTextBox.getText()));
        newCaseInformation.amendedFiliingDate = amendedFilingDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(amendedFilingDateTextBox.getText()));
        newCaseInformation.alphaListDate = alphaListRecepitDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(alphaListRecepitDateTextBox.getText()));
        newCaseInformation.finalBoardDate = finalBoardDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(finalBoardDateTextBox.getText()));
        newCaseInformation.registrationLetterSent = registrationLetterSentTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(registrationLetterSentTextBox.getText()));
        newCaseInformation.dateOfAppeal = dateOfAppealTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(dateOfAppealTextBox.getText()));
        newCaseInformation.registrationLetterSent = registrationLetterSentTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(registrationLetterSentTextBox.getText()));
        newCaseInformation.courtClosedDate = courtClosedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(courtClosedDateTextBox.getText()));
        newCaseInformation.returnSOIDueDate = returnSOIDueDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(returnSOIDueDateTextBox.getText()));
        newCaseInformation.actualSOIReturnDate = actualSOIReturnDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(actualSOIReturnDateTextBox.getText()));
        newCaseInformation.SOIReturnInitials = SOIReturnInitials.getSelectedItem().toString().equals("") ? 0 : User.getUserID(SOIReturnInitials.getSelectedItem().toString());
        newCaseInformation.REPClosedCaseDueDate = REPClosedCaseDueDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(REPClosedCaseDueDateTextBox.getText()));
        newCaseInformation.actualREPClosedDate = actualREPClosedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(actualREPClosedDateTextBox.getText()));
        newCaseInformation.REPClosedUser = repClosedUser.getSelectedItem().equals("") ? 0 : User.getUserID(repClosedUser.getSelectedItem().toString());
        newCaseInformation.actualClerksClosedDate = actualClerksClosedDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(actualClerksClosedDate.getText()));
        newCaseInformation.clerksClosedUser = clerksClosedUser.getSelectedItem().equals("") ? 0 : User.getUserID(clerksClosedUser.getSelectedItem().toString());

        REPCase.updateCaseInformation(newCaseInformation, caseInformation);
        newCaseNotification();
        caseInformation = REPCase.loadCaseInformation();
    }

    private void newCaseNotification() {
        if (caseInformation.currentOwnerID == 0 && !currentOwnerComboBox.getSelectedItem().toString().trim().equals("")){
            int id = User.getUserID(currentOwnerComboBox.getSelectedItem().toString());
            if (id > 0){
                DocketNotifications.addNewCaseNotification(NumberFormatService.generateFullCaseNumber(), Global.activeSection, id);
            }
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

    private void setBUNumberCheckBoxes(String certStatus) {
        switch(certStatus) {
            case "B":
                boardCertifiedCheckBox.setSelected(true);
                deemedCertifiedCheckBox.setSelected(false);
                certificationRevokedCheckBox.setSelected(false);
                break;
            case "D":
                boardCertifiedCheckBox.setSelected(false);
                deemedCertifiedCheckBox.setSelected(true);
                certificationRevokedCheckBox.setSelected(false);
                break;
            case "U":
                boardCertifiedCheckBox.setSelected(false);
                deemedCertifiedCheckBox.setSelected(false);
                certificationRevokedCheckBox.setSelected(false);
                break;
            case "N":
                boardCertifiedCheckBox.setSelected(false);
                deemedCertifiedCheckBox.setSelected(false);
                certificationRevokedCheckBox.setSelected(true);
                break;
            default:
                boardCertifiedCheckBox.setSelected(false);
                deemedCertifiedCheckBox.setSelected(false);
                certificationRevokedCheckBox.setSelected(false);
                break;
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
        caseTypeComboBox = new javax.swing.JComboBox();
        status1ComboBox = new javax.swing.JComboBox();
        status2ComboBox = new javax.swing.JComboBox();
        currentOwnerComboBox = new javax.swing.JComboBox();
        employerIDNumberTextBox = new javax.swing.JTextField();
        bargainingUnitNumberTextBox = new javax.swing.JTextField();
        bargainingUnitNameTextBox = new javax.swing.JTextField();
        jScrollPane2 = new javax.swing.JScrollPane();
        relatedCaseTable = new javax.swing.JTable();
        addRelatedCaseButton = new javax.swing.JButton();
        countyComboBox = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        notesTextArea = new javax.swing.JTextArea();
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
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        actualClerksClosedDate = new com.alee.extended.date.WebDateField();
        jLabel1 = new javax.swing.JLabel();
        alphaListRecepitDateTextBox = new com.alee.extended.date.WebDateField();
        repClosedUser = new javax.swing.JComboBox<>();
        clerksClosedUser = new javax.swing.JComboBox<>();
        SOIReturnInitials = new javax.swing.JComboBox<>();

        setLayout(new java.awt.GridLayout(1, 0));

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

        caseTypeComboBox.setEnabled(false);

        status1ComboBox.setEnabled(false);

        status2ComboBox.setEnabled(false);

        currentOwnerComboBox.setEnabled(false);

        employerIDNumberTextBox.setEditable(false);
        employerIDNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        employerIDNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        employerIDNumberTextBox.setEnabled(false);
        employerIDNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employerIDNumberTextBoxMouseClicked(evt);
            }
        });

        bargainingUnitNumberTextBox.setEditable(false);
        bargainingUnitNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
        bargainingUnitNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        bargainingUnitNumberTextBox.setEnabled(false);
        bargainingUnitNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                bargainingUnitNumberTextBoxMouseClicked(evt);
            }
        });
        bargainingUnitNumberTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                bargainingUnitNumberTextBoxActionPerformed(evt);
            }
        });

        bargainingUnitNameTextBox.setBackground(new java.awt.Color(238, 238, 238));
        bargainingUnitNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        bargainingUnitNameTextBox.setEnabled(false);

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

        addRelatedCaseButton.setText("+");
        addRelatedCaseButton.setMaximumSize(new java.awt.Dimension(29, 91));
        addRelatedCaseButton.setMinimumSize(new java.awt.Dimension(29, 91));
        addRelatedCaseButton.setPreferredSize(new java.awt.Dimension(29, 91));
        addRelatedCaseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRelatedCaseButtonActionPerformed(evt);
            }
        });

        countyComboBox.setEnabled(false);

        jLabel24.setText("Notes:");

        notesTextArea.setBackground(new java.awt.Color(238, 238, 238));
        notesTextArea.setColumns(20);
        notesTextArea.setLineWrap(true);
        notesTextArea.setRows(4);
        notesTextArea.setWrapStyleWord(true);
        notesTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        notesTextArea.setEnabled(false);
        notesTextArea.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                notesTextAreaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(notesTextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
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
                            .addComponent(bargainingUnitNumberTextBox)
                            .addComponent(bargainingUnitNameTextBox)
                            .addComponent(countyComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(employerIDNumberTextBox)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(0, 12, Short.MAX_VALUE)
                        .addComponent(boardCertifiedCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(deemedCertifiedCheckBox)
                        .addGap(18, 18, 18)
                        .addComponent(certificationRevokedCheckBox)
                        .addGap(19, 19, 19))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel24)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(addRelatedCaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jScrollPane1))))
                .addGap(10, 10, 10))
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(countyComboBox)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(employerIDNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel9)
                        .addGap(0, 91, Short.MAX_VALUE))
                    .addComponent(addRelatedCaseButton, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        add(jPanel1);

        jLabel10.setText("File Date:");

        jLabel11.setText("Amended Filing Date:");

        jLabel12.setText("Final Board Date:");

        jLabel13.setText("Registration Letter Sent:");

        jLabel14.setText("Date of Appeal:");

        jLabel15.setText("Court Closed Date:");

        jLabel16.setText("Return SOI Due Date:");

        jLabel17.setText("Actual SOI Return Date:");

        jLabel18.setText("SOI Returned By:");

        jLabel19.setText("REP Closed Case Due Date:");

        jLabel20.setText("Actual REP Closed Date:");

        jLabel21.setText("Clerks Closed By:");

        fileDateTextBox.setEditable(false);
        fileDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        fileDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fileDateTextBox.setEnabled(false);
        fileDateTextBox.setDateFormat(Global.mmddyyyy);

        fileDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            fileDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    fileDateTextBoxMouseClicked(evt);
                }
            });

            amendedFilingDateTextBox.setEditable(false);
            amendedFilingDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
            amendedFilingDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            amendedFilingDateTextBox.setEnabled(false);
            amendedFilingDateTextBox.setDateFormat(Global.mmddyyyy);

            amendedFilingDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );
                amendedFilingDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        amendedFilingDateTextBoxMouseClicked(evt);
                    }
                });

                finalBoardDateTextBox.setEditable(false);
                finalBoardDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                finalBoardDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                finalBoardDateTextBox.setEnabled(false);
                finalBoardDateTextBox.setDateFormat(Global.mmddyyyy);

                finalBoardDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                    {
                        @Override
                        public void customize ( final WebCalendar calendar )
                        {
                            calendar.setStartWeekFromSunday ( true );
                        }
                    } );
                    finalBoardDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            finalBoardDateTextBoxMouseClicked(evt);
                        }
                    });

                    registrationLetterSentTextBox.setEditable(false);
                    registrationLetterSentTextBox.setBackground(new java.awt.Color(238, 238, 238));
                    registrationLetterSentTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                    registrationLetterSentTextBox.setEnabled(false);
                    registrationLetterSentTextBox.setDateFormat(Global.mmddyyyy);

                    registrationLetterSentTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                        {
                            @Override
                            public void customize ( final WebCalendar calendar )
                            {
                                calendar.setStartWeekFromSunday ( true );
                            }
                        } );
                        registrationLetterSentTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                registrationLetterSentTextBoxMouseClicked(evt);
                            }
                        });

                        dateOfAppealTextBox.setEditable(false);
                        dateOfAppealTextBox.setBackground(new java.awt.Color(238, 238, 238));
                        dateOfAppealTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                        dateOfAppealTextBox.setEnabled(false);
                        dateOfAppealTextBox.setDateFormat(Global.mmddyyyy);

                        dateOfAppealTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                            {
                                @Override
                                public void customize ( final WebCalendar calendar )
                                {
                                    calendar.setStartWeekFromSunday ( true );
                                }
                            } );
                            dateOfAppealTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    dateOfAppealTextBoxMouseClicked(evt);
                                }
                            });

                            courtClosedDateTextBox.setEditable(false);
                            courtClosedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                            courtClosedDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                            courtClosedDateTextBox.setEnabled(false);
                            courtClosedDateTextBox.setDateFormat(Global.mmddyyyy);

                            courtClosedDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                {
                                    @Override
                                    public void customize ( final WebCalendar calendar )
                                    {
                                        calendar.setStartWeekFromSunday ( true );
                                    }
                                } );
                                courtClosedDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                                        courtClosedDateTextBoxMouseClicked(evt);
                                    }
                                });
                                courtClosedDateTextBox.addActionListener(new java.awt.event.ActionListener() {
                                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                                        courtClosedDateTextBoxActionPerformed(evt);
                                    }
                                });

                                returnSOIDueDateTextBox.setEditable(false);
                                returnSOIDueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                returnSOIDueDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                returnSOIDueDateTextBox.setEnabled(false);
                                returnSOIDueDateTextBox.setDateFormat(Global.mmddyyyy);

                                returnSOIDueDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                    {
                                        @Override
                                        public void customize ( final WebCalendar calendar )
                                        {
                                            calendar.setStartWeekFromSunday ( true );
                                        }
                                    } );
                                    returnSOIDueDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                            returnSOIDueDateTextBoxMouseClicked(evt);
                                        }
                                    });

                                    actualSOIReturnDateTextBox.setEditable(false);
                                    actualSOIReturnDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                    actualSOIReturnDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                    actualSOIReturnDateTextBox.setEnabled(false);
                                    actualSOIReturnDateTextBox.setDateFormat(Global.mmddyyyy);

                                    actualSOIReturnDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                        {
                                            @Override
                                            public void customize ( final WebCalendar calendar )
                                            {
                                                calendar.setStartWeekFromSunday ( true );
                                            }
                                        } );
                                        actualSOIReturnDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                actualSOIReturnDateTextBoxMouseClicked(evt);
                                            }
                                        });

                                        REPClosedCaseDueDateTextBox.setEditable(false);
                                        REPClosedCaseDueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                        REPClosedCaseDueDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                        REPClosedCaseDueDateTextBox.setEnabled(false);
                                        REPClosedCaseDueDateTextBox.setDateFormat(Global.mmddyyyy);

                                        REPClosedCaseDueDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                            {
                                                @Override
                                                public void customize ( final WebCalendar calendar )
                                                {
                                                    calendar.setStartWeekFromSunday ( true );
                                                }
                                            } );
                                            REPClosedCaseDueDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                    REPClosedCaseDueDateTextBoxMouseClicked(evt);
                                                }
                                            });

                                            actualREPClosedDateTextBox.setEditable(false);
                                            actualREPClosedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                            actualREPClosedDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                            actualREPClosedDateTextBox.setEnabled(false);
                                            actualREPClosedDateTextBox.setDateFormat(Global.mmddyyyy);

                                            actualREPClosedDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                                {
                                                    @Override
                                                    public void customize ( final WebCalendar calendar )
                                                    {
                                                        calendar.setStartWeekFromSunday ( true );
                                                    }
                                                } );
                                                actualREPClosedDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                        actualREPClosedDateTextBoxMouseClicked(evt);
                                                    }
                                                });

                                                jLabel22.setText("REP Closed By:");

                                                jLabel23.setText("Actual Clerks Closed Date:");

                                                actualClerksClosedDate.setEditable(false);
                                                actualClerksClosedDate.setBackground(new java.awt.Color(238, 238, 238));
                                                actualClerksClosedDate.setCaretColor(new java.awt.Color(0, 0, 0));
                                                actualClerksClosedDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                                actualClerksClosedDate.setEnabled(false);
                                                actualClerksClosedDate.setDateFormat(Global.mmddyyyy);

                                                actualClerksClosedDate.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                                    {
                                                        @Override
                                                        public void customize ( final WebCalendar calendar )
                                                        {
                                                            calendar.setStartWeekFromSunday ( true );
                                                        }
                                                    } );
                                                    actualClerksClosedDate.addMouseListener(new java.awt.event.MouseAdapter() {
                                                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                            actualClerksClosedDateMouseClicked(evt);
                                                        }
                                                    });

                                                    jLabel1.setText("Alpha List Receipt Date:");

                                                    alphaListRecepitDateTextBox.setEditable(false);
                                                    alphaListRecepitDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                                    alphaListRecepitDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                                    alphaListRecepitDateTextBox.setEnabled(false);
                                                    alphaListRecepitDateTextBox.setDateFormat(Global.mmddyyyy);

                                                    alphaListRecepitDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                                        {
                                                            @Override
                                                            public void customize ( final WebCalendar calendar )
                                                            {
                                                                calendar.setStartWeekFromSunday ( true );
                                                            }
                                                        } );
                                                        alphaListRecepitDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                                alphaListRecepitDateTextBoxMouseClicked(evt);
                                                            }
                                                        });

                                                        repClosedUser.setEnabled(false);

                                                        clerksClosedUser.setEnabled(false);

                                                        SOIReturnInitials.setEnabled(false);

                                                        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                                                        jPanel2.setLayout(jPanel2Layout);
                                                        jPanel2Layout.setHorizontalGroup(
                                                            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                                .addGap(10, 10, 10)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                    .addComponent(jLabel1)
                                                                    .addComponent(jLabel23)
                                                                    .addComponent(jLabel22)
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
                                                                    .addComponent(amendedFilingDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(finalBoardDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(registrationLetterSentTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(dateOfAppealTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(courtClosedDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(returnSOIDueDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(actualSOIReturnDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(REPClosedCaseDueDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(actualREPClosedDateTextBox, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(actualClerksClosedDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(alphaListRecepitDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
                                                                    .addComponent(repClosedUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(clerksClosedUser, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                                    .addComponent(SOIReturnInitials, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                                                                    .addComponent(jLabel1)
                                                                    .addComponent(alphaListRecepitDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(SOIReturnInitials)
                                                                    .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(jLabel19)
                                                                    .addComponent(REPClosedCaseDueDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(jLabel20)
                                                                    .addComponent(actualREPClosedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(repClosedUser)
                                                                    .addComponent(jLabel22, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                                    .addComponent(actualClerksClosedDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                                    .addComponent(jLabel23))
                                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                                    .addComponent(clerksClosedUser)
                                                                    .addComponent(jLabel21, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                                .addContainerGap(20, Short.MAX_VALUE))
                                                        );

                                                        add(jPanel2);
                                                    }// </editor-fold>//GEN-END:initComponents

    private void boardCertifiedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_boardCertifiedCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_boardCertifiedCheckBoxActionPerformed

    private void deemedCertifiedCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_deemedCertifiedCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_deemedCertifiedCheckBoxActionPerformed

    private void addRelatedCaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRelatedCaseButtonActionPerformed
        new AddNewRelatedCase((JFrame) Global.root, true);
        loadRelatedCasesTable();
    }//GEN-LAST:event_addRelatedCaseButtonActionPerformed

    private void employerIDNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employerIDNumberTextBoxMouseClicked
        if(evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            if(employerIDNumberTextBox.isEnabled()) {
                employerSearch search = new employerSearch(
                        (JFrame) Global.root.getRootPane().getParent(),
                        true,
                        employerIDNumberTextBox.getText().trim(),
                        countyComboBox.getSelectedItem().toString());
                employerIDNumberTextBox.setText(search.getEmployerNumber());
                countyComboBox.setSelectedItem(search.getCounty());
                search.dispose();
            } else {
                if(employerIDNumberTextBox.getText().equals("")) {
                    new employerDetail((JFrame) Global.root.getRootPane().getParent(), true, employerIDNumberTextBox.getText().trim());
                }
            }
        } else if(evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON3) {
            if(bargainingUnitNumberTextBox.isEnabled()) {
                int answer = WebOptionPane.showConfirmDialog(this, "Would you like to clear the Employer Information", "Clear", WebOptionPane.YES_NO_OPTION);
                if (answer == WebOptionPane.YES_OPTION) {
                    employerIDNumberTextBox.setText("");
                    countyComboBox.setSelectedItem("");
                    bargainingUnitNumberTextBox.setText("");
                    bargainingUnitNameTextBox.setText("");
                    setBUNumberCheckBoxes("");
                }
            }
        }
    }//GEN-LAST:event_employerIDNumberTextBoxMouseClicked

    private void relatedCaseTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_relatedCaseTableMouseClicked
        if(relatedCaseTable.getSelectedRow() > -1) {
            if(evt.getButton() == MouseEvent.BUTTON3) {
                new RemoveRelatedCaseDialog(
                    (JFrame) Global.root.getRootPane().getParent(),
                    true,
                    relatedCaseTable.getValueAt(relatedCaseTable.getSelectedRow(), 0).toString().trim()
                );
                loadRelatedCasesTable();
            }
        }
    }//GEN-LAST:event_relatedCaseTableMouseClicked

    private void fileDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_fileDateTextBoxMouseClicked
        clearDate(fileDateTextBox, evt);
    }//GEN-LAST:event_fileDateTextBoxMouseClicked

    private void amendedFilingDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_amendedFilingDateTextBoxMouseClicked
        clearDate(amendedFilingDateTextBox, evt);
    }//GEN-LAST:event_amendedFilingDateTextBoxMouseClicked

    private void finalBoardDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_finalBoardDateTextBoxMouseClicked
        clearDate(finalBoardDateTextBox, evt);
    }//GEN-LAST:event_finalBoardDateTextBoxMouseClicked

    private void registrationLetterSentTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_registrationLetterSentTextBoxMouseClicked
        clearDate(registrationLetterSentTextBox, evt);
    }//GEN-LAST:event_registrationLetterSentTextBoxMouseClicked

    private void dateOfAppealTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dateOfAppealTextBoxMouseClicked
        clearDate(dateOfAppealTextBox, evt);
    }//GEN-LAST:event_dateOfAppealTextBoxMouseClicked

    private void courtClosedDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_courtClosedDateTextBoxMouseClicked
        clearDate(courtClosedDateTextBox, evt);
    }//GEN-LAST:event_courtClosedDateTextBoxMouseClicked

    private void returnSOIDueDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_returnSOIDueDateTextBoxMouseClicked
        clearDate(returnSOIDueDateTextBox, evt);
    }//GEN-LAST:event_returnSOIDueDateTextBoxMouseClicked

    private void actualSOIReturnDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualSOIReturnDateTextBoxMouseClicked
        clearDate(actualSOIReturnDateTextBox, evt);
    }//GEN-LAST:event_actualSOIReturnDateTextBoxMouseClicked

    private void REPClosedCaseDueDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_REPClosedCaseDueDateTextBoxMouseClicked
        clearDate(REPClosedCaseDueDateTextBox, evt);
    }//GEN-LAST:event_REPClosedCaseDueDateTextBoxMouseClicked

    private void actualREPClosedDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualREPClosedDateTextBoxMouseClicked
        clearDate(actualREPClosedDateTextBox, evt);
    }//GEN-LAST:event_actualREPClosedDateTextBoxMouseClicked

    private void actualClerksClosedDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_actualClerksClosedDateMouseClicked
        clearDate(actualClerksClosedDate, evt);
    }//GEN-LAST:event_actualClerksClosedDateMouseClicked

    private void bargainingUnitNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_bargainingUnitNumberTextBoxMouseClicked
        if(evt.getClickCount() == 2 && evt.getButton() == MouseEvent.BUTTON1) {
            if(bargainingUnitNumberTextBox.isEnabled()) {
                buNumberSearch search = new buNumberSearch(
                        (JFrame) Global.root.getRootPane().getParent(),
                        true,
                        employerIDNumberTextBox.getText().trim(),
                        bargainingUnitNumberTextBox.getText().trim(),
                        bargainingUnitNameTextBox.getText().trim());
                bargainingUnitNumberTextBox.setText(search.getBuNumber().contains("-") ? search.getBuNumber() : "");
                bargainingUnitNameTextBox.setText(search.getBuNumber().contains("-") ? search.getUnitDesc() : "");
                bargainingUnitNameTextBox.setCaretPosition(0);
                if(search.getCertStatus() != null || !search.getCertStatus().equals("")) {
                    setBUNumberCheckBoxes(search.getCertStatus());
                } else {
                    setBUNumberCheckBoxes("");
                }
                if(employerIDNumberTextBox.getText().equals("")) {
                    employerIDNumberTextBox.setText(search.getBuNumber().split("-")[0]);
                }
                search.dispose();
            }
        } else if(evt.getClickCount() == 1 && evt.getButton() == MouseEvent.BUTTON3) {
            if(bargainingUnitNumberTextBox.isEnabled()) {
                int answer = WebOptionPane.showConfirmDialog(this, "Would You Like to Clear the Bargaining Unit Information", "Clear", WebOptionPane.YES_NO_OPTION);
                if (answer == WebOptionPane.YES_OPTION) {
                    bargainingUnitNumberTextBox.setText("");
                    bargainingUnitNameTextBox.setText("");
                    setBUNumberCheckBoxes("");
                }
            }
        }
    }//GEN-LAST:event_bargainingUnitNumberTextBoxMouseClicked

    private void bargainingUnitNumberTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_bargainingUnitNumberTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_bargainingUnitNumberTextBoxActionPerformed

    private void alphaListRecepitDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_alphaListRecepitDateTextBoxMouseClicked
        clearDate(alphaListRecepitDateTextBox, evt);
    }//GEN-LAST:event_alphaListRecepitDateTextBoxMouseClicked

    private void notesTextAreaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_notesTextAreaMouseClicked
        if(evt.getClickCount() == 2 && !notesTextArea.getText().equals(""))
        {
            Global.root.getrEPRootPanel1().getjTabbedPane1().setSelectedIndex(Global.root.getrEPRootPanel1().getjTabbedPane1().indexOfTab("Notes"));
        }
    }//GEN-LAST:event_notesTextAreaMouseClicked

    private void courtClosedDateTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_courtClosedDateTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_courtClosedDateTextBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.extended.date.WebDateField REPClosedCaseDueDateTextBox;
    private javax.swing.JComboBox<String> SOIReturnInitials;
    private com.alee.extended.date.WebDateField actualClerksClosedDate;
    private com.alee.extended.date.WebDateField actualREPClosedDateTextBox;
    private com.alee.extended.date.WebDateField actualSOIReturnDateTextBox;
    private javax.swing.JButton addRelatedCaseButton;
    private com.alee.extended.date.WebDateField alphaListRecepitDateTextBox;
    private com.alee.extended.date.WebDateField amendedFilingDateTextBox;
    private javax.swing.JTextField bargainingUnitNameTextBox;
    private javax.swing.JTextField bargainingUnitNumberTextBox;
    private javax.swing.JCheckBox boardCertifiedCheckBox;
    private javax.swing.JComboBox caseTypeComboBox;
    private javax.swing.JCheckBox certificationRevokedCheckBox;
    private javax.swing.JComboBox<String> clerksClosedUser;
    private javax.swing.JComboBox<String> countyComboBox;
    private com.alee.extended.date.WebDateField courtClosedDateTextBox;
    private javax.swing.JComboBox currentOwnerComboBox;
    private com.alee.extended.date.WebDateField dateOfAppealTextBox;
    private javax.swing.JCheckBox deemedCertifiedCheckBox;
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
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
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
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea notesTextArea;
    private com.alee.extended.date.WebDateField registrationLetterSentTextBox;
    private javax.swing.JTable relatedCaseTable;
    private javax.swing.JComboBox<String> repClosedUser;
    private com.alee.extended.date.WebDateField returnSOIDueDateTextBox;
    private javax.swing.JComboBox status1ComboBox;
    private javax.swing.JComboBox status2ComboBox;
    // End of variables declaration//GEN-END:variables
}
