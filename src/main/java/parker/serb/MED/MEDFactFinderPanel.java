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
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.FactFinder;
import parker.serb.sql.MEDCase;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class MEDFactFinderPanel extends javax.swing.JPanel {

    List<String> fullFFList = new ArrayList<>();
    List<String> randomFFList = new ArrayList<>();
    DefaultListModel FFList1Model = new DefaultListModel();
    DefaultListModel FFList2Model = new DefaultListModel();
    MEDCase orginalInformation;
    
    /**
     * Creates new form MEDFFPanel
     */
    public MEDFactFinderPanel() {
        initComponents();
        addListeners();
    }
    
    private void addListeners() {
        FF1OrderDate.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(FF1OrderDate.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 7);
                    FF1SelectionDate.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    FF1SelectionDate.setText("");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(FF1OrderDate.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 7);
                    FF1SelectionDate.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    FF1SelectionDate.setText("");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(FF1OrderDate.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 7);
                    FF1SelectionDate.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    FF1SelectionDate.setText("");
                }
            }
        });
        
        FF2OrderDateTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(FF2OrderDateTextBox.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 7);
                    FF2SelectionDateTextBox.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    FF2SelectionDateTextBox.setText("");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(FF2OrderDateTextBox.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 7);
                    FF2SelectionDateTextBox.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    FF2SelectionDateTextBox.setText("");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(FF2OrderDateTextBox.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 7);
                    FF2SelectionDateTextBox.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    FF2SelectionDateTextBox.setText("");
                }
            }
        });
    }
    
    public void enableUpdate() {
        
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);
        
        //list 1
        FF1OrderDate.setEnabled(true);
        FF1OrderDate.setBackground(Color.WHITE);
        FF1SelectionDate.setEnabled(true);
        FF1SelectionDate.setBackground(Color.WHITE);
        FF1List.setEnabled(true);
        
        if(FF1List.getModel().getSize() == 0) {
            FF1GenerateButton.setEnabled(true);
        }
        
        //list 2
        FF2OrderDateTextBox.setEnabled(true);
        FF2OrderDateTextBox.setBackground(Color.WHITE);
        FF2SelectionDateTextBox.setEnabled(true);
        FF2SelectionDateTextBox.setBackground(Color.WHITE);
        FF2List.setEnabled(true);
        
        if(FF1List.getModel().getSize() > 0
                && FF2List.getModel().getSize() == 0) {
            FF2GenerateButton.setEnabled(true);
        } else {
            FF2GenerateButton.setEnabled(false);
        }
        
        //middle information
        appointmentDateTextBox.setEnabled(true);
        appointmentDateTextBox.setBackground(Color.white);
        FFTypeComboBox.setEnabled(true);
        asAgreedToByPartiesCheckBox.setEnabled(true);
        
        if(!FFTypeComboBox.getSelectedItem().toString().trim().equals("")) {
            FFSelectionComboBox.setEnabled(true);
            replacementFFComboBox.setEnabled(true);
            originalFFDateTextBox.setEnabled(true);
            originalFFDateTextBox.setBackground(Color.white);
        }   
        
        //lowerhalf
        EmployerTypeComboBox.setEnabled(true);
        EmployeeTypeComboBox.setEnabled(true);
        FFReportIssueDateTextBox.setEnabled(true);
        FFReportIssueDateTextBox.setBackground(Color.white);
        MediatedSettlementCheckBox.setEnabled(true);
        AcceptedByComboBox.setEnabled(true);
        DeemedAcceptedByComboBox.setEnabled(true);
        RejectedByComboBox.setEnabled(true);
        OverallResultComboBox.setEnabled(true);
        FFNote.setEnabled(true);
        FFNote.setBackground(Color.white);
    }
    
    public void disableUpdate(boolean save) {
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);
        
        FF1OrderDate.setEnabled(false);
        FF1OrderDate.setBackground(new Color(238,238,238));
        FF1SelectionDate.setEnabled(false);
        FF1SelectionDate.setBackground(new Color(238,238,238));
        FF1List.setEnabled(false);
        FF1GenerateButton.setEnabled(false);
        
        //list 2
        FF2OrderDateTextBox.setEnabled(false);
        FF2OrderDateTextBox.setBackground(new Color(238,238,238));
        FF2SelectionDateTextBox.setEnabled(false);
        FF2SelectionDateTextBox.setBackground(new Color(238,238,238));
        FF2List.setEnabled(false);
        FF2GenerateButton.setEnabled(false);
        
        //middle information
        appointmentDateTextBox.setEnabled(false);
        appointmentDateTextBox.setBackground(new Color(238,238,238));
        FFTypeComboBox.setEnabled(false);
        asAgreedToByPartiesCheckBox.setEnabled(false);
        
        FFSelectionComboBox.setEnabled(false);
        replacementFFComboBox.setEnabled(false);
        originalFFDateTextBox.setEnabled(false);
        originalFFDateTextBox.setBackground(new Color(238,238,238));
        
        //lowerhalf
        EmployerTypeComboBox.setEnabled(false);
        EmployeeTypeComboBox.setEnabled(false);
        FFReportIssueDateTextBox.setEnabled(false);
        FFReportIssueDateTextBox.setBackground(new Color(238,238,238));
        MediatedSettlementCheckBox.setEnabled(false);
        AcceptedByComboBox.setEnabled(false);
        DeemedAcceptedByComboBox.setEnabled(false);
        RejectedByComboBox.setEnabled(false);
        OverallResultComboBox.setEnabled(false);
        FFNote.setEnabled(false);
        FFNote.setBackground(new Color(238,238,238));
        
        if(save) {
            saveInformation();
        }
        
        loadInformation();
    }
    
    public void loadInformation() {
        loadFullFFList();
        
        orginalInformation = MEDCase.loadFFInformation();
        
        FF1OrderDate.setText(orginalInformation.FFList1OrderDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFList1OrderDate.getTime())) : "");
        FF1SelectionDate.setText(orginalInformation.FFList1SelectionDueDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFList1SelectionDueDate.getTime())) : "");    
        
        if(orginalInformation.FFType != null) {
            switch(orginalInformation.FFType) {
                case "Selected by parties":
                    populateFFSelection();
                    populateFFReplacement();
                    break;
                case "Discretionary":
                    populateFullFFSelection();
                    populateFullFFReplacement();
                    break;
                case "Replacement":
                    populateFullFFSelection();
                    populateFullFFReplacement();
                    break;
                case "Alternate Selection":
                    populateFullFFSelection();
                    populateFullFFReplacement();
                    break;
            } 
        }
        
        if(orginalInformation.FFList1Name1 != null) {
            FFList1Model.removeAllElements();
            FF1List.setModel(FFList1Model);
            FFList1Model.add(0, orginalInformation.FFList1Name1);
            FFList1Model.add(1, orginalInformation.FFList1Name2);
            FFList1Model.add(2, orginalInformation.FFList1Name3);
            FFList1Model.add(3, orginalInformation.FFList1Name4);
            FFList1Model.add(4, orginalInformation.FFList1Name5);
            FF1List.setModel(FFList1Model);
            
            if(Global.root.getjButton2().getText().equals("Save")) {
                FF2GenerateButton.setEnabled(true);
            } else {
                FF2GenerateButton.setEnabled(false);
            }
        } else {
            FFList1Model.removeAllElements();
            FF1List.setModel(FFList1Model);
        }
        
        appointmentDateTextBox.setText(orginalInformation.FFAppointmentDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFAppointmentDate.getTime())) : "");
        FFTypeComboBox.setSelectedItem(orginalInformation.FFType != null ? orginalInformation.FFType : "");
        FFSelectionComboBox.setSelectedItem(orginalInformation.FFSelection != null ? orginalInformation.FFSelection : "");
        replacementFFComboBox.setSelectedItem(orginalInformation.FFReplacement != null ? orginalInformation.FFReplacement : "");
        FFOriginalFactFinder.setText(orginalInformation.FFOriginalFactFinder != null ? orginalInformation.FFOriginalFactFinder : "");
        originalFFDateTextBox.setText(orginalInformation.FFOriginalFactFinderDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFOriginalFactFinderDate.getTime())) : "");
        asAgreedToByPartiesCheckBox.setSelected(orginalInformation.asAgreedToByParties);

        FF2OrderDateTextBox.setText(orginalInformation.FFList2OrderDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFList2OrderDate.getTime())) : "");
        FF2SelectionDateTextBox.setText(orginalInformation.FFList2SelectionDueDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFList2SelectionDueDate.getTime())) : "");
       
        if(orginalInformation.FFList2Name1 != null) {
            FFList2Model.removeAllElements();
            FF2List.setModel(FFList2Model);
            FFList2Model.add(0, orginalInformation.FFList2Name1);
            FFList2Model.add(1, orginalInformation.FFList2Name2);
            FFList2Model.add(2, orginalInformation.FFList2Name3);
            FFList2Model.add(3, orginalInformation.FFList2Name4);
            FFList2Model.add(4, orginalInformation.FFList2Name5);
            FF2List.setModel(FFList2Model);
        } else {
            FFList2Model.removeAllElements();
            FF2List.setModel(FFList2Model);
        }
        
        //lower half
        EmployerTypeComboBox.setSelectedItem(orginalInformation.FFEmployerType != null ? orginalInformation.FFEmployerType : " ");
        EmployeeTypeComboBox.setSelectedItem(orginalInformation.FFEmployeeType != null ? orginalInformation.FFEmployeeType : " ");
        FFReportIssueDateTextBox.setText(orginalInformation.FFReportIssueDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFReportIssueDate.getTime())) : "");
        MediatedSettlementCheckBox.setSelected(orginalInformation.FFMediatedSettlement);
        AcceptedByComboBox.setSelectedItem(orginalInformation.FFAcceptedBy != null ? orginalInformation.FFAcceptedBy : " ");
        DeemedAcceptedByComboBox.setSelectedItem(orginalInformation.FFDeemedAcceptedBy != null ? orginalInformation.FFDeemedAcceptedBy : " ");
        RejectedByComboBox.setSelectedItem(orginalInformation.FFRejectedBy != null ? orginalInformation.FFRejectedBy : " ");
        OverallResultComboBox.setSelectedItem(orginalInformation.FFOverallResult != null ? orginalInformation.FFOverallResult : " ");
        FFNote.setText(orginalInformation.FFNote == null ? "" : orginalInformation.FFNote);
    }
    
    private void saveInformation() {
        MEDCase newMEDCaseInformation = new MEDCase();
        
        newMEDCaseInformation.FFList1OrderDate = FF1OrderDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(FF1OrderDate.getText()));
        newMEDCaseInformation.FFList1SelectionDueDate = FF1SelectionDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(FF1SelectionDate.getText()));
        
        if(FFList1Model.getSize() == 5) {
            newMEDCaseInformation.FFList1Name1 = FFList1Model.get(0).toString();
            newMEDCaseInformation.FFList1Name2 = FFList1Model.get(1).toString();
            newMEDCaseInformation.FFList1Name3 = FFList1Model.get(2).toString();
            newMEDCaseInformation.FFList1Name4 = FFList1Model.get(3).toString();
            newMEDCaseInformation.FFList1Name5 = FFList1Model.get(4).toString();
        } else {
            newMEDCaseInformation.FFList1Name1 = null;
            newMEDCaseInformation.FFList1Name2 = null;
            newMEDCaseInformation.FFList1Name3 = null;
            newMEDCaseInformation.FFList1Name4 = null;
            newMEDCaseInformation.FFList1Name5 = null;
        }
        
        newMEDCaseInformation.FFAppointmentDate = appointmentDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(appointmentDateTextBox.getText()));
        newMEDCaseInformation.FFType = FFTypeComboBox.getSelectedItem().toString().equals("") ? null : FFTypeComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFSelection = FFSelectionComboBox.getSelectedItem() == null || FFSelectionComboBox.getSelectedItem().toString().equals("") ? null : FFSelectionComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFReplacement = replacementFFComboBox.getSelectedItem() == null || replacementFFComboBox.getSelectedItem().toString().equals("") ? null : replacementFFComboBox.getSelectedItem().toString();

        //only set the value if it is blank
        if(FFOriginalFactFinder.getText().equals("")) {
            if (FFSelectionComboBox.getSelectedItem() == null){
                newMEDCaseInformation.FFOriginalFactFinder = null;
            } else {
                newMEDCaseInformation.FFOriginalFactFinder = 
                        FFSelectionComboBox.getSelectedItem().toString().equals("") ? null : FFSelectionComboBox.getSelectedItem().toString();
            }
        } else if(FFSelectionComboBox.getSelectedItem() == null ||
            FFSelectionComboBox.getSelectedItem().toString().equals("")) {
            newMEDCaseInformation.FFOriginalFactFinder = null;
        } else {
            newMEDCaseInformation.FFOriginalFactFinder = orginalInformation.FFOriginalFactFinder;
        }
        
        newMEDCaseInformation.FFOriginalFactFinderDate = originalFFDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(originalFFDateTextBox.getText()));
        newMEDCaseInformation.asAgreedToByParties = asAgreedToByPartiesCheckBox.isSelected();
        
        newMEDCaseInformation.FFList2OrderDate = FF2OrderDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(FF2OrderDateTextBox.getText()));
        newMEDCaseInformation.FFList2SelectionDueDate = FF2SelectionDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(FF2SelectionDateTextBox.getText()));
        
        if(FFList2Model.getSize() == 5) {
            newMEDCaseInformation.FFList2Name1 = FFList2Model.get(0).toString();
            newMEDCaseInformation.FFList2Name2 = FFList2Model.get(1).toString();
            newMEDCaseInformation.FFList2Name3 = FFList2Model.get(2).toString();
            newMEDCaseInformation.FFList2Name4 = FFList2Model.get(3).toString();
            newMEDCaseInformation.FFList2Name5 = FFList2Model.get(4).toString();
        } else {
            newMEDCaseInformation.FFList2Name1 = null;
            newMEDCaseInformation.FFList2Name2 = null;
            newMEDCaseInformation.FFList2Name3 = null;
            newMEDCaseInformation.FFList2Name4 = null;
            newMEDCaseInformation.FFList2Name5 = null;
        }
        
        //lowerhalf
        newMEDCaseInformation.FFEmployerType = EmployerTypeComboBox.getSelectedItem().toString().trim().equals("") ? null : EmployerTypeComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFEmployeeType = EmployeeTypeComboBox.getSelectedItem().toString().trim().equals("") ? null : EmployeeTypeComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFReportIssueDate = FFReportIssueDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(FFReportIssueDateTextBox.getText()));
        newMEDCaseInformation.FFMediatedSettlement = MediatedSettlementCheckBox.isSelected();
        newMEDCaseInformation.FFAcceptedBy = AcceptedByComboBox.getSelectedItem().toString().trim().equals("") ? null : AcceptedByComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFDeemedAcceptedBy = DeemedAcceptedByComboBox.getSelectedItem().toString().trim().equals("") ? null : DeemedAcceptedByComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFRejectedBy = RejectedByComboBox.getSelectedItem().toString().trim().equals("") ? null : RejectedByComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFOverallResult = OverallResultComboBox.getSelectedItem().toString().trim().equals("") ? null : OverallResultComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFNote = FFNote.getText().trim().equals("") ? null : FFNote.getText();
        
        MEDCase.updateFF(newMEDCaseInformation, orginalInformation);
    }
    
    public void clearAll() {
        //list1
        FF1OrderDate.setText("");
        FF1SelectionDate.setText("");
        FFList1Model.removeAllElements();
        FF1List.setModel(FFList1Model);
        
        //list2
        FF2OrderDateTextBox.setText("");
        FF2SelectionDateTextBox.setText("");
        FFList2Model.removeAllElements();
        FF2List.setModel(FFList2Model);
        
        //middle information
        appointmentDateTextBox.setText("");
        FFTypeComboBox.setSelectedItem("");
        FFSelectionComboBox.setSelectedItem("");
        replacementFFComboBox.setSelectedItem("");
        FFOriginalFactFinder.setText("");
        originalFFDateTextBox.setText("");
        asAgreedToByPartiesCheckBox.setSelected(false);
        
        //lower half
        EmployerTypeComboBox.setSelectedItem(" ");
        EmployeeTypeComboBox.setSelectedItem(" ");
        FFReportIssueDateTextBox.setText("");
        MediatedSettlementCheckBox.setSelected(false);
        AcceptedByComboBox.setSelectedItem(" ");
        DeemedAcceptedByComboBox.setSelectedItem(" ");
        RejectedByComboBox.setSelectedItem(" ");
        OverallResultComboBox.setSelectedItem(" ");
        FFNote.setText("");
    }
    
    private void loadFullFFList() {
        fullFFList = FactFinder.loadAllFF();
        randomFFList = fullFFList;
    }
    
    private void generateRandomFFList(String whichList) {
        //remove list 1 names
        for(int i = 0; i < FF1List.getModel().getSize(); i++) {
            randomFFList.remove(FF1List.getModel().getElementAt(i));
        }
        
        //remove list 2 names
        for(int i = 0; i < FF2List.getModel().getSize(); i++) {
            randomFFList.remove(FF2List.getModel().getElementAt(i));
        }
        
        //if loading list 1
        if(whichList.equals("1")) {
            FFList1Model.removeAllElements();
            for(int i = 0; i < 5; i++) {
                int randomNumber = new Random().nextInt(randomFFList.size());
                FFList1Model.add(0, randomFFList.get(randomNumber));
                randomFFList.remove(randomNumber);
            }
            FF1List.setModel(FFList1Model);
            FF1GenerateButton.setEnabled(false);
            FF2GenerateButton.setEnabled(true);
            MEDCase.saveFFList1(FFList1Model);
        //if loading list 2
        } else if(whichList.equals("2")) {
            FFList2Model.removeAllElements();
            for(int i = 0; i < 5; i++) {
                int randomNumber = new Random().nextInt(randomFFList.size());
                FFList2Model.add(0, randomFFList.get(randomNumber));
                randomFFList.remove(randomNumber);
            }
            FF2List.setModel(FFList2Model);
            FF2GenerateButton.setEnabled(false);
            MEDCase.saveFFList2(FFList2Model);
        }
    }
    
    private void populateFFSelection() {
        String currentSelectedFF = "";
        
        currentSelectedFF = FFSelectionComboBox.getSelectedItem() == null ? "" : FFSelectionComboBox.getSelectedItem().toString();
        
        FFSelectionComboBox.removeAllItems();
        FFSelectionComboBox.addItem("");
        
        for(int i = 0; i < FF1List.getModel().getSize(); i++) {
            FFSelectionComboBox.addItem(FF1List.getModel().getElementAt(i));
        }
        
        for(int i = 0; i < FF2List.getModel().getSize(); i++) {
            FFSelectionComboBox.addItem(FF2List.getModel().getElementAt(i));
        }
        
        FFSelectionComboBox.setSelectedItem(currentSelectedFF);
    }
    
    private void populateFullFFSelection() {
        String currentSelectedFF = "";
        
        currentSelectedFF = FFSelectionComboBox.getSelectedItem() == null ? "" : FFSelectionComboBox.getSelectedItem().toString();
        
        FFSelectionComboBox.removeAllItems();
        FFSelectionComboBox.addItem("");
        
        for(int i = 0; i < fullFFList.size(); i++) {
            FFSelectionComboBox.addItem(fullFFList.get(i));
        }
        
        FFSelectionComboBox.setSelectedItem(currentSelectedFF);
    }
    
    private void populateFullFFReplacement() {
        String currentSelectedFF = "";
        
        currentSelectedFF = replacementFFComboBox.getSelectedItem() == null ? "" : replacementFFComboBox.getSelectedItem().toString();
        
        replacementFFComboBox.removeAllItems();
        replacementFFComboBox.addItem("");
        
        for(int i = 0; i < fullFFList.size(); i++) {
            replacementFFComboBox.addItem(fullFFList.get(i));
        }
        
        if(FFSelectionComboBox.getSelectedItem() != null &&
                !FFSelectionComboBox.getSelectedItem().toString().equals("")) {
            replacementFFComboBox.removeItem(FFSelectionComboBox.getSelectedItem().toString());
        }
        
        replacementFFComboBox.setSelectedItem(currentSelectedFF);
    }
    
    private void populateFFReplacement() {
        String currentSelectedFF = "";
        
        currentSelectedFF = replacementFFComboBox.getSelectedItem() == null ? "" : replacementFFComboBox.getSelectedItem().toString();
        
        replacementFFComboBox.removeAllItems();
        replacementFFComboBox.addItem("");
        
        for(int i = 0; i < FF1List.getModel().getSize(); i++) {
            replacementFFComboBox.addItem(FF1List.getModel().getElementAt(i));
        }
        
        for(int i = 0; i < FF2List.getModel().getSize(); i++) {
            replacementFFComboBox.addItem(FF2List.getModel().getElementAt(i));
        }
        
        if(FFSelectionComboBox.getSelectedItem() != null &&
                !FFSelectionComboBox.getSelectedItem().toString().equals("")) {
            replacementFFComboBox.removeItem(FFSelectionComboBox.getSelectedItem().toString());
        }
        
        replacementFFComboBox.setSelectedItem(currentSelectedFF);
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
    
    private void replaceFFAfterRemoval(int FFLocation, String whichList) {
        if(whichList.equals("list1")) {
            String oldName = FFList1Model.get(FFLocation).toString();
            FFList1Model.remove(FFLocation);
            int randomNumber = new Random().nextInt(randomFFList.size());
            String newName = randomFFList.get(randomNumber);
            FFList1Model.add(FFLocation, newName);
            MEDCase.replaceList1FF(FFLocation, newName, oldName);
            randomFFList.remove(randomNumber);
            randomFFList.add(FFList1Model.get(FFLocation).toString());
        } else if(whichList.equals("list2")) {
            String oldName = FFList2Model.get(FFLocation).toString();
            FFList2Model.remove(FFLocation);
            int randomNumber = new Random().nextInt(randomFFList.size());
            String newName = randomFFList.get(randomNumber);
            FFList2Model.add(FFLocation, newName);
            MEDCase.replaceList2FF(FFLocation, newName, oldName);
            randomFFList.remove(randomNumber);
            randomFFList.add(FFList2Model.get(FFLocation).toString());
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
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        FFTypeComboBox = new javax.swing.JComboBox<>();
        FFSelectionComboBox = new javax.swing.JComboBox<>();
        replacementFFComboBox = new javax.swing.JComboBox<>();
        FFOriginalFactFinder = new javax.swing.JTextField();
        appointmentDateTextBox = new com.alee.extended.date.WebDateField();
        originalFFDateTextBox = new com.alee.extended.date.WebDateField();
        asAgreedToByPartiesCheckBox = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        FF1List = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        FF1GenerateButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        FF1OrderDate = new com.alee.extended.date.WebDateField();
        FF1SelectionDate = new com.alee.extended.date.WebDateField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        FF2List = new javax.swing.JList<>();
        FF2GenerateButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        FF2OrderDateTextBox = new com.alee.extended.date.WebDateField();
        FF2SelectionDateTextBox = new com.alee.extended.date.WebDateField();
        jPanel4 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        EmployerTypeComboBox = new javax.swing.JComboBox<>();
        EmployeeTypeComboBox = new javax.swing.JComboBox<>();
        AcceptedByComboBox = new javax.swing.JComboBox<>();
        DeemedAcceptedByComboBox = new javax.swing.JComboBox<>();
        RejectedByComboBox = new javax.swing.JComboBox<>();
        OverallResultComboBox = new javax.swing.JComboBox<>();
        FFReportIssueDateTextBox = new com.alee.extended.date.WebDateField();
        MediatedSettlementCheckBox = new javax.swing.JCheckBox();
        jPanel5 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        FFNote = new javax.swing.JTextArea();

        jLabel7.setText("Appointment Date:");

        jLabel8.setText("Fact Finder Type:");

        jLabel9.setText("Fact Finder Selection:");

        jLabel10.setText("Replacement Fact Finder:");

        jLabel11.setText("Original Fact Finder:");

        jLabel12.setText("Original Fact Finder Date:");

        FFTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selected by parties", "Discretionary", "Replacement", "Alternate Selection", "" }));
        FFTypeComboBox.setSelectedIndex(4);
        FFTypeComboBox.setEnabled(false);
        FFTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FFTypeComboBoxActionPerformed(evt);
            }
        });

        FFSelectionComboBox.setEnabled(false);
        FFSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FFSelectionComboBoxActionPerformed(evt);
            }
        });

        replacementFFComboBox.setEnabled(false);

        FFOriginalFactFinder.setBackground(new java.awt.Color(238, 238, 238));
        FFOriginalFactFinder.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        FFOriginalFactFinder.setEnabled(false);

        appointmentDateTextBox.setEditable(false);
        appointmentDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        appointmentDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        appointmentDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        appointmentDateTextBox.setEnabled(false);
        appointmentDateTextBox.setDateFormat(Global.mmddyyyy);

        appointmentDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            appointmentDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    appointmentDateTextBoxMouseClicked(evt);
                }
            });

            originalFFDateTextBox.setEditable(false);
            originalFFDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
            originalFFDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
            originalFFDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            originalFFDateTextBox.setEnabled(false);
            originalFFDateTextBox.setDateFormat(Global.mmddyyyy);

            originalFFDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );
                originalFFDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        originalFFDateTextBoxMouseClicked(evt);
                    }
                });

                asAgreedToByPartiesCheckBox.setText("As Agreed To By Parties");
                asAgreedToByPartiesCheckBox.setEnabled(false);
                asAgreedToByPartiesCheckBox.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        asAgreedToByPartiesCheckBoxActionPerformed(evt);
                    }
                });

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel12)
                            .addComponent(jLabel11)
                            .addComponent(jLabel10)
                            .addComponent(jLabel9)
                            .addComponent(jLabel8)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(asAgreedToByPartiesCheckBox)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(FFTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FFSelectionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(replacementFFComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(FFOriginalFactFinder)
                            .addComponent(appointmentDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(originalFFDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())
                );
                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(appointmentDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(FFTypeComboBox)
                            .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(FFSelectionComboBox)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(replacementFFComboBox)
                            .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel11)
                            .addComponent(FFOriginalFactFinder, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(originalFFDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(asAgreedToByPartiesCheckBox)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                );

                FF1List.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
                FF1List.setEnabled(false);
                FF1List.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        FF1ListMouseClicked(evt);
                    }
                });
                jScrollPane1.setViewportView(FF1List);

                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                jLabel1.setText("Fact Finder List");

                FF1GenerateButton.setText("Generate List");
                FF1GenerateButton.setEnabled(false);
                FF1GenerateButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        FF1GenerateButtonActionPerformed(evt);
                    }
                });

                jLabel3.setText("Selection Date:");

                jLabel4.setText("Selection Due Date:");

                FF1OrderDate.setEditable(false);
                FF1OrderDate.setBackground(new java.awt.Color(238, 238, 238));
                FF1OrderDate.setCaretColor(new java.awt.Color(0, 0, 0));
                FF1OrderDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                FF1OrderDate.setEnabled(false);
                FF1OrderDate.setDateFormat(Global.mmddyyyy);

                FF1OrderDate.setCalendarCustomizer(new Customizer<WebCalendar> ()
                    {
                        @Override
                        public void customize ( final WebCalendar calendar )
                        {
                            calendar.setStartWeekFromSunday ( true );
                        }
                    } );
                    FF1OrderDate.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            FF1OrderDateMouseClicked(evt);
                        }
                    });

                    FF1SelectionDate.setEditable(false);
                    FF1SelectionDate.setBackground(new java.awt.Color(238, 238, 238));
                    FF1SelectionDate.setCaretColor(new java.awt.Color(0, 0, 0));
                    FF1SelectionDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                    FF1SelectionDate.setEnabled(false);
                    FF1SelectionDate.setDateFormat(Global.mmddyyyy);

                    FF1SelectionDate.setCalendarCustomizer(new Customizer<WebCalendar> ()
                        {
                            @Override
                            public void customize ( final WebCalendar calendar )
                            {
                                calendar.setStartWeekFromSunday ( true );
                            }
                        } );
                        FF1SelectionDate.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                FF1SelectionDateMouseClicked(evt);
                            }
                        });

                        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                        jPanel2.setLayout(jPanel2Layout);
                        jPanel2Layout.setHorizontalGroup(
                            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 311, Short.MAX_VALUE)
                                    .addComponent(FF1GenerateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(jLabel4)
                                            .addComponent(jLabel3))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(FF1OrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(FF1SelectionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addContainerGap())
                        );
                        jPanel2Layout.setVerticalGroup(
                            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addContainerGap()
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel3)
                                    .addComponent(FF1OrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(FF1SelectionDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(FF1GenerateButton)
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        );

                        FF2List.setEnabled(false);
                        FF2List.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                FF2ListMouseClicked(evt);
                            }
                        });
                        jScrollPane2.setViewportView(FF2List);

                        FF2GenerateButton.setText("Generate List");
                        FF2GenerateButton.setEnabled(false);
                        FF2GenerateButton.addActionListener(new java.awt.event.ActionListener() {
                            public void actionPerformed(java.awt.event.ActionEvent evt) {
                                FF2GenerateButtonActionPerformed(evt);
                            }
                        });

                        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
                        jLabel2.setText("Fact Finder List #2");

                        jLabel5.setText("Selection Date:");

                        jLabel6.setText("Selection Due Date:");

                        FF2OrderDateTextBox.setEditable(false);
                        FF2OrderDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                        FF2OrderDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
                        FF2OrderDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                        FF2OrderDateTextBox.setEnabled(false);
                        FF2OrderDateTextBox.setDateFormat(Global.mmddyyyy);

                        FF2OrderDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                            {
                                @Override
                                public void customize ( final WebCalendar calendar )
                                {
                                    calendar.setStartWeekFromSunday ( true );
                                }
                            } );
                            FF2OrderDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    FF2OrderDateTextBoxMouseClicked(evt);
                                }
                            });

                            FF2SelectionDateTextBox.setEditable(false);
                            FF2SelectionDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                            FF2SelectionDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
                            FF2SelectionDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                            FF2SelectionDateTextBox.setEnabled(false);
                            FF2SelectionDateTextBox.setDateFormat(Global.mmddyyyy);

                            FF2SelectionDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                {
                                    @Override
                                    public void customize ( final WebCalendar calendar )
                                    {
                                        calendar.setStartWeekFromSunday ( true );
                                    }
                                } );
                                FF2SelectionDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                                        FF2SelectionDateTextBoxMouseClicked(evt);
                                    }
                                });

                                javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                                jPanel3.setLayout(jPanel3Layout);
                                jPanel3Layout.setHorizontalGroup(
                                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(FF2GenerateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addGroup(jPanel3Layout.createSequentialGroup()
                                                .addGap(0, 0, Short.MAX_VALUE)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel6)
                                                    .addComponent(jLabel5))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(FF2SelectionDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                                                    .addComponent(FF2OrderDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                                        .addContainerGap())
                                );
                                jPanel3Layout.setVerticalGroup(
                                    jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel3Layout.createSequentialGroup()
                                        .addContainerGap()
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel5)
                                            .addComponent(FF2OrderDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                            .addComponent(jLabel6)
                                            .addComponent(FF2SelectionDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(jLabel2)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(FF2GenerateButton)
                                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                );

                                jLabel14.setText("Employer Type:");

                                jLabel15.setText("Employee Type:");

                                jLabel16.setText("FF Report Issue Date:");

                                jLabel17.setText("Mediated Settlement:");

                                jLabel18.setText("Accepted By:");

                                jLabel19.setText("Deemed Accepted By:");

                                jLabel20.setText("Rejected By:");

                                jLabel21.setText("Overall Result:");

                                EmployerTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "City", "County", "School District", "Township", "University", "State Govt.", "Other", " " }));
                                EmployerTypeComboBox.setSelectedIndex(7);
                                EmployerTypeComboBox.setEnabled(false);

                                EmployeeTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Police", "Fire", "Teaching", "Nursing", "Other", " " }));
                                EmployeeTypeComboBox.setSelectedIndex(5);
                                EmployeeTypeComboBox.setEnabled(false);

                                AcceptedByComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Union", "Employer", "Both", " " }));
                                AcceptedByComboBox.setSelectedIndex(3);
                                AcceptedByComboBox.setEnabled(false);
                                AcceptedByComboBox.setInheritsPopupMenu(true);

                                DeemedAcceptedByComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Union", "Employer", "Both", " " }));
                                DeemedAcceptedByComboBox.setSelectedIndex(3);
                                DeemedAcceptedByComboBox.setEnabled(false);

                                RejectedByComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Union", "Employer", "Both", " " }));
                                RejectedByComboBox.setSelectedIndex(3);
                                RejectedByComboBox.setEnabled(false);
                                RejectedByComboBox.setFocusTraversalPolicyProvider(true);

                                OverallResultComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Accepted", "Rejected", " " }));
                                OverallResultComboBox.setSelectedIndex(2);
                                OverallResultComboBox.setEnabled(false);

                                FFReportIssueDateTextBox.setEditable(false);
                                FFReportIssueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                FFReportIssueDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
                                FFReportIssueDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                FFReportIssueDateTextBox.setEnabled(false);
                                FFReportIssueDateTextBox.setDateFormat(Global.mmddyyyy);

                                FFReportIssueDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                    {
                                        @Override
                                        public void customize ( final WebCalendar calendar )
                                        {
                                            calendar.setStartWeekFromSunday ( true );
                                        }
                                    } );
                                    FFReportIssueDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                            FFReportIssueDateTextBoxMouseClicked(evt);
                                        }
                                    });

                                    MediatedSettlementCheckBox.setEnabled(false);
                                    MediatedSettlementCheckBox.addActionListener(new java.awt.event.ActionListener() {
                                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                            MediatedSettlementCheckBoxActionPerformed(evt);
                                        }
                                    });

                                    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                                    jPanel4.setLayout(jPanel4Layout);
                                    jPanel4Layout.setHorizontalGroup(
                                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel15)
                                                .addComponent(jLabel16)
                                                .addComponent(jLabel14)
                                                .addComponent(jLabel17)
                                                .addComponent(jLabel18)
                                                .addComponent(jLabel19)
                                                .addComponent(jLabel20)
                                                .addComponent(jLabel21))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(EmployerTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(EmployeeTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(AcceptedByComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(DeemedAcceptedByComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(RejectedByComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(OverallResultComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(FFReportIssueDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(MediatedSettlementCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addContainerGap())
                                    );
                                    jPanel4Layout.setVerticalGroup(
                                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addGap(2, 2, 2)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(EmployerTypeComboBox)
                                                .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(EmployeeTypeComboBox)
                                                .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel16)
                                                .addComponent(FFReportIssueDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addGap(8, 8, 8)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(MediatedSettlementCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 8, Short.MAX_VALUE)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(AcceptedByComboBox)
                                                .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(DeemedAcceptedByComboBox)
                                                .addComponent(jLabel19, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(RejectedByComboBox)
                                                .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(OverallResultComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGroup(jPanel4Layout.createSequentialGroup()
                                                    .addComponent(jLabel21, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(1, 1, 1)))
                                            .addGap(11, 11, 11))
                                    );

                                    jLabel13.setText("Notes:");

                                    FFNote.setBackground(new java.awt.Color(238, 238, 238));
                                    FFNote.setColumns(20);
                                    FFNote.setLineWrap(true);
                                    FFNote.setRows(5);
                                    FFNote.setWrapStyleWord(true);
                                    FFNote.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                    FFNote.setEnabled(false);
                                    jScrollPane3.setViewportView(FFNote);

                                    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                                    jPanel5.setLayout(jPanel5Layout);
                                    jPanel5Layout.setHorizontalGroup(
                                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane3)
                                                .addGroup(jPanel5Layout.createSequentialGroup()
                                                    .addComponent(jLabel13)
                                                    .addContainerGap(717, Short.MAX_VALUE))))
                                    );
                                    jPanel5Layout.setVerticalGroup(
                                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addComponent(jLabel13)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jScrollPane3)
                                            .addContainerGap())
                                    );

                                    javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                                    this.setLayout(layout);
                                    layout.setHorizontalGroup(
                                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                    .addContainerGap()
                                                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 95, Short.MAX_VALUE)
                                                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                    .addContainerGap())))
                                    );
                                    layout.setVerticalGroup(
                                        layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(0, 0, 0)
                                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(layout.createSequentialGroup()
                                                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addGap(0, 0, Short.MAX_VALUE))))
                                    );
                                }// </editor-fold>//GEN-END:initComponents

    private void appointmentDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appointmentDateTextBoxMouseClicked
        clearDate(appointmentDateTextBox, evt);
    }//GEN-LAST:event_appointmentDateTextBoxMouseClicked

    private void originalFFDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_originalFFDateTextBoxMouseClicked
        clearDate(originalFFDateTextBox, evt);
    }//GEN-LAST:event_originalFFDateTextBoxMouseClicked

    private void FF1OrderDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FF1OrderDateMouseClicked
        clearDate(FF1OrderDate, evt);
    }//GEN-LAST:event_FF1OrderDateMouseClicked

    private void FF1SelectionDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FF1SelectionDateMouseClicked
        clearDate(FF1SelectionDate, evt);
    }//GEN-LAST:event_FF1SelectionDateMouseClicked

    private void FF2OrderDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FF2OrderDateTextBoxMouseClicked
        clearDate(FF2OrderDateTextBox, evt);
    }//GEN-LAST:event_FF2OrderDateTextBoxMouseClicked

    private void FF2SelectionDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FF2SelectionDateTextBoxMouseClicked
        clearDate(FF2SelectionDateTextBox, evt);
    }//GEN-LAST:event_FF2SelectionDateTextBoxMouseClicked

    private void FF2GenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FF2GenerateButtonActionPerformed
        generateRandomFFList("2");
    }//GEN-LAST:event_FF2GenerateButtonActionPerformed

    private void FF1GenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FF1GenerateButtonActionPerformed
        generateRandomFFList("1");
    }//GEN-LAST:event_FF1GenerateButtonActionPerformed

    private void FF1ListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FF1ListMouseClicked
        if(FF1List.isEnabled() && FF1List.getSelectedIndex() >= 0) {
            if(evt.getClickCount() == 2) {
                System.out.println("MORE INFO: " + FFList1Model.get(FF1List.getSelectedIndex()));
            } else if(evt.getButton() == MouseEvent.BUTTON3) {
                MEDRemoveFFDialog remove = new MEDRemoveFFDialog(Global.root, true, FFList1Model.get(FF1List.getSelectedIndex()).toString());
                if(remove.removeFF) {
                    System.out.println("REMOVE: " + FFList1Model.get(FF1List.getSelectedIndex()));
                    replaceFFAfterRemoval(FF1List.getSelectedIndex(), "list1");
                    FF1List.clearSelection();
                }
                remove.dispose();
            }
        }
    }//GEN-LAST:event_FF1ListMouseClicked

    private void FF2ListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FF2ListMouseClicked
        if(FF2List.isEnabled() && FF2List.getSelectedIndex() >= 0) {
            if(evt.getClickCount() == 2) {
                System.out.println("MORE INFO: " + FFList2Model.get(FF2List.getSelectedIndex()));
            } else if(evt.getButton() == MouseEvent.BUTTON3) {
                MEDRemoveFFDialog remove = new MEDRemoveFFDialog(Global.root, true, FFList2Model.get(FF2List.getSelectedIndex()).toString());
                if(remove.removeFF) {
                    System.out.println("REMOVE: " + FFList2Model.get(FF2List.getSelectedIndex()));
                    replaceFFAfterRemoval(FF2List.getSelectedIndex(), "list2");
                    FF2List.clearSelection();
                }
                remove.dispose();
            }
        }
    }//GEN-LAST:event_FF2ListMouseClicked

    private void FFSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FFSelectionComboBoxActionPerformed
        switch(FFTypeComboBox.getSelectedItem().toString().trim()) {
            case "Selected by parties":
                populateFFReplacement();
                break;
            case "Discretionary":
                populateFullFFReplacement();
                break;
            case "Replacement":
                populateFullFFReplacement();
                break;
            case "Alternate Selection":
                populateFullFFReplacement();
                break;
        }
    }//GEN-LAST:event_FFSelectionComboBoxActionPerformed

    private void FFTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FFTypeComboBoxActionPerformed
        if(FFTypeComboBox.isEnabled()) {
            switch(FFTypeComboBox.getSelectedItem().toString().trim()) {
                case "Selected by parties":
                    FFSelectionComboBox.setSelectedItem("");
                    replacementFFComboBox.setSelectedItem("");
                    FFSelectionComboBox.setEnabled(true);
                    replacementFFComboBox.setEnabled(true);
                    originalFFDateTextBox.setEnabled(true);
                    originalFFDateTextBox.setBackground(Color.white);
                    populateFFSelection();
                    populateFFReplacement();
                    break;
                case "Discretionary":
                    FFSelectionComboBox.setSelectedItem("");
                    replacementFFComboBox.setSelectedItem("");
                    FFSelectionComboBox.setEnabled(true);
                    replacementFFComboBox.setEnabled(true);
                    originalFFDateTextBox.setEnabled(true);
                    originalFFDateTextBox.setBackground(Color.white);
                    populateFullFFSelection();
                    populateFullFFReplacement();
                    break;
                case "Replacement":
                    FFSelectionComboBox.setSelectedItem("");
                    replacementFFComboBox.setSelectedItem("");
                    FFSelectionComboBox.setEnabled(false);
                    FFSelectionComboBox.setSelectedItem("");
                    replacementFFComboBox.setEnabled(true);
                    originalFFDateTextBox.setEnabled(true);
                    originalFFDateTextBox.setBackground(Color.white);
                    populateFullFFSelection();
                    populateFullFFReplacement();
                    break;
                case "Alternate Selection":
                    FFSelectionComboBox.setSelectedItem("");
                    replacementFFComboBox.setSelectedItem("");
                    FFSelectionComboBox.setEnabled(true);
                    replacementFFComboBox.setEnabled(true);
                    originalFFDateTextBox.setEnabled(true);
                    originalFFDateTextBox.setBackground(Color.white);
                    populateFullFFSelection();
                    populateFullFFReplacement();
                    
                    break;
                default:
                    FFSelectionComboBox.setEnabled(false);
                    replacementFFComboBox.setEnabled(false);
                    originalFFDateTextBox.setEnabled(false);
                    originalFFDateTextBox.setBackground(new Color(238,238,238));
                    FFSelectionComboBox.setSelectedItem("");
                    replacementFFComboBox.setSelectedItem("");
                    break;
            }
        }
    }//GEN-LAST:event_FFTypeComboBoxActionPerformed

    private void asAgreedToByPartiesCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asAgreedToByPartiesCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_asAgreedToByPartiesCheckBoxActionPerformed

    private void FFReportIssueDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_FFReportIssueDateTextBoxMouseClicked
        clearDate(FFReportIssueDateTextBox, evt);
    }//GEN-LAST:event_FFReportIssueDateTextBoxMouseClicked

    private void MediatedSettlementCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MediatedSettlementCheckBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_MediatedSettlementCheckBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> AcceptedByComboBox;
    private javax.swing.JComboBox<String> DeemedAcceptedByComboBox;
    private javax.swing.JComboBox<String> EmployeeTypeComboBox;
    private javax.swing.JComboBox<String> EmployerTypeComboBox;
    private javax.swing.JButton FF1GenerateButton;
    private javax.swing.JList<String> FF1List;
    private com.alee.extended.date.WebDateField FF1OrderDate;
    private com.alee.extended.date.WebDateField FF1SelectionDate;
    private javax.swing.JButton FF2GenerateButton;
    private javax.swing.JList<String> FF2List;
    private com.alee.extended.date.WebDateField FF2OrderDateTextBox;
    private com.alee.extended.date.WebDateField FF2SelectionDateTextBox;
    private javax.swing.JTextArea FFNote;
    private javax.swing.JTextField FFOriginalFactFinder;
    private com.alee.extended.date.WebDateField FFReportIssueDateTextBox;
    private javax.swing.JComboBox<String> FFSelectionComboBox;
    private javax.swing.JComboBox<String> FFTypeComboBox;
    private javax.swing.JCheckBox MediatedSettlementCheckBox;
    private javax.swing.JComboBox<String> OverallResultComboBox;
    private javax.swing.JComboBox<String> RejectedByComboBox;
    private com.alee.extended.date.WebDateField appointmentDateTextBox;
    private javax.swing.JCheckBox asAgreedToByPartiesCheckBox;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private com.alee.extended.date.WebDateField originalFFDateTextBox;
    private javax.swing.JComboBox<String> replacementFFComboBox;
    // End of variables declaration//GEN-END:variables
}
