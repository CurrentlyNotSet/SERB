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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.swing.DefaultListModel;
import javax.swing.JFrame;
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
     * Creates new form MEDConciliationPanel
     */
    public MEDFactFinderPanel() {
        initComponents();
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
        asAgreedToParties.setEnabled(true);
        
        if(!FFTypeComboBox.getSelectedItem().toString().trim().equals("")) {
            FFSelectionComboBox.setEnabled(true);
            replacementFFComboBox.setEnabled(true);
            originalFFDateTextBox.setEnabled(true);
            originalFFDateTextBox.setBackground(Color.white);
        }   
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
        asAgreedToParties.setEnabled(false);
        
//        if(!conciliationTypeComboBox.getSelectedItem().toString().trim().equals("")) {
            FFSelectionComboBox.setEnabled(false);
            replacementFFComboBox.setEnabled(false);
            originalFFDateTextBox.setEnabled(false);
            originalFFDateTextBox.setBackground(new Color(238,238,238));
//        }
        
        
        
        
        if(save) {
            saveInformation();
        }
        
        loadInformation();
    }
    
    public void loadInformation() {
        loadFullConcilList();
        
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
        
        if(orginalInformation.concilList1Name1 != null) {
            concilList1Model.removeAllElements();
            FF1List.setModel(concilList1Model);
            concilList1Model.add(0, orginalInformation.FFList1Name1);
            concilList1Model.add(1, orginalInformation.FFList1Name2);
            concilList1Model.add(2, orginalInformation.FFList1Name3);
            concilList1Model.add(3, orginalInformation.FFList1Name4);
            concilList1Model.add(4, orginalInformation.FFlList1Name5);
            FF1List.setModel(FFList1Model);
            
            if(Global.root.getjButton2().getText().equals("Save")) {
                FF2GenerateButton.setEnabled(true);
            } else {
                FF2GenerateButton.setEnabled(false);
            }
            
            
        } else {
            concilList1Model.removeAllElements();
            FF1List.setModel(concilList1Model);
        }
        
        appointmentDateTextBox.setText(orginalInformation.FFAppointmentDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFAppointmentDate.getTime())) : "");
        FFTypeComboBox.setSelectedItem(orginalInformation.FFType != null ? orginalInformation.FFType : "");
        FFSelectionComboBox.setSelectedItem(orginalInformation.FFSelection != null ? orginalInformation.FFSelection : "");
        replacementFFComboBox.setSelectedItem(orginalInformation.FFReplacement != null ? orginalInformation.FFReplacement : "");
        concilOriginalFF.setText(orginalInformation.FFOriginalConciliator != null ? orginalInformation.FFOriginalConciliator : "");
        originalFFDateTextBox.setText(orginalInformation.FFOriginalConcilDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFOriginalConcilDate.getTime())) : "");
        asagreed

        FF2OrderDateTextBox.setText(orginalInformation.FFList2OrderDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFList2OrderDate.getTime())) : "");
        FF2SelectionDateTextBox.setText(orginalInformation.FFList2SelectionDueDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.FFList2SelectionDueDate.getTime())) : "");
       
        if(orginalInformation.FFList2Name1 != null) {
            FFList2Model.removeAllElements();
            FF2List.setModel(concilList2Model);
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
        
//        populateConciliatorSelection();
//        populateConciliatorReplacement();
    }
    
    private void saveInformation() {
        MEDCase newMEDCaseInformation = new MEDCase();
        
        newMEDCaseInformation.concilList1OrderDate = conciliation1OrderDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(conciliation1OrderDate.getText()));
        newMEDCaseInformation.concilList1SelectionDueDate = conciliation1SelectionDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(conciliation1SelectionDate.getText()));
        
        if(FFist1Model.getSize() == 5) {
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
        newMEDCaseInformation.FFType = FFTypeComboBox.getSelectedItem().toString().equals("") ? null : FFFFTypeComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFSelection = FFSelectionComboBox.getSelectedItem() == null || FFFFSelectionComboBox.getSelectedItem().toString().equals("") ? null : FFFFSelectionComboBox.getSelectedItem().toString();
        newMEDCaseInformation.FFReplacement = replacementFFComboBox.getSelectedItem() == null || replacementFFFFComboBox.getSelectedItem().toString().equals("") ? null : replacementFFComboBox.getSelectedItem().toString();
        
        
        //only set the value if it is blank
        if(FFSelectionComboBox.getSelectedItem() == null) {
            newMEDCaseInformation.FFOriginalConciliator = null;
        } else if(FFOriginalConciliator.getText().equals("")) {
            newMEDCaseInformation.FFOriginalConciliator = FFSelectionComboBox.getSelectedItem().toString().equals("") ? null : conciliatorSelectionComboBox.getSelectedItem().toString();
        } else {
            newMEDCaseInformation.FFOriginalConciliator = orginalInformation.FFOriginalFF;
        }
        
        newMEDCaseInformation.FFOriginalFFDate = originalFFDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(originalFFDateTextBox.getText()));
        asagreed
        
        newMEDCaseInformation.concilList2OrderDate = FF2OrderDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(FF2OrderDateTextBox.getText()));
        newMEDCaseInformation.concilList2SelectionDueDate = FF2SelectionDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(FF2SelectionDateTextBox.getText()));
        
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
        
        MEDCase.updateConciliation(newMEDCaseInformation, orginalInformation);
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
        FFOriginalFF.setText("");
        originalFFDateTextBox.setText("");
        asAgreedToParties.setSelected(false);
    }
    
    private void loadFullFFList() {
        fullConcilList = FactFinder.loadAllConciliators();
        randomConcilList = fullConcilList;
    }
    
    private void generateRandomConcilList(String whichList) {
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
            conciliation2List.setModel(FFList2Model);
            conciliation2GenerateButton.setEnabled(false);
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
        
        FFSelectionComboBox.setSelectedItem(currentSelectedConcil);
    }
    
    private void populateFullConciliatorSelection() {
        String currentSelectedConcil = "";
        
        currentSelectedConcil = conciliatorSelectionComboBox.getSelectedItem() == null ? "" : conciliatorSelectionComboBox.getSelectedItem().toString();
//        
//        conciliatorSelectionComboBox.setSelectedItem("");
//        replacementConciliatorComboBox.setSelectedItem("");
        
        conciliatorSelectionComboBox.removeAllItems();
        conciliatorSelectionComboBox.addItem("");
        
        for(int i = 0; i < fullConcilList.size(); i++) {
            conciliatorSelectionComboBox.addItem(fullConcilList.get(i));
        }
        
        conciliatorSelectionComboBox.setSelectedItem(currentSelectedConcil);
    }
    
    private void populateFullConciliatorReplacement() {
        String currentSelectedConcil = "";
        
        currentSelectedConcil = replacementConciliatorComboBox.getSelectedItem() == null ? "" : replacementConciliatorComboBox.getSelectedItem().toString();
        
//        conciliatorSelectionComboBox.setSelectedItem("");
//        replacementConciliatorComboBox.setSelectedItem("");
        
        replacementConciliatorComboBox.removeAllItems();
        replacementConciliatorComboBox.addItem("");
        
        for(int i = 0; i < fullConcilList.size(); i++) {
            replacementConciliatorComboBox.addItem(fullConcilList.get(i));
        }
        
//        for(int i = 0; i < conciliation2List.getModel().getSize(); i++) {
//            replacementConciliatorComboBox.addItem(conciliation2List.getModel().getElementAt(i));
//        }
        
        if(conciliatorSelectionComboBox.getSelectedItem() != null &&
                !conciliatorSelectionComboBox.getSelectedItem().toString().equals("")) {
            replacementConciliatorComboBox.removeItem(conciliatorSelectionComboBox.getSelectedItem().toString());
        }
        
        replacementConciliatorComboBox.setSelectedItem(currentSelectedConcil);
    }
    
    private void populateConciliatorReplacement() {
        String currentSelectedConcil = "";
        
        currentSelectedConcil = replacementConciliatorComboBox.getSelectedItem() == null ? "" : replacementConciliatorComboBox.getSelectedItem().toString();
        
//        conciliatorSelectionComboBox.setSelectedItem("");
//        replacementConciliatorComboBox.setSelectedItem("");
        
        replacementConciliatorComboBox.removeAllItems();
        replacementConciliatorComboBox.addItem("");
        
        for(int i = 0; i < conciliation1List.getModel().getSize(); i++) {
            replacementConciliatorComboBox.addItem(conciliation1List.getModel().getElementAt(i));
        }
        
        for(int i = 0; i < conciliation2List.getModel().getSize(); i++) {
            replacementConciliatorComboBox.addItem(conciliation2List.getModel().getElementAt(i));
        }
        
        if(conciliatorSelectionComboBox.getSelectedItem() != null &&
                !conciliatorSelectionComboBox.getSelectedItem().toString().equals("")) {
            replacementConciliatorComboBox.removeItem(conciliatorSelectionComboBox.getSelectedItem().toString());
        }
        
        replacementConciliatorComboBox.setSelectedItem(currentSelectedConcil);
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
    
    private void replaceConcilAfterRemoval(int concilLocation, String whichList) {
        if(whichList.equals("list1")) {
            String oldName = concilList1Model.get(concilLocation).toString();
            concilList1Model.remove(concilLocation);
            int randomNumber = new Random().nextInt(randomConcilList.size());
            String newName = randomConcilList.get(randomNumber);
            concilList1Model.add(concilLocation, newName);
            MEDCase.replaceList1Concil(concilLocation, newName, oldName);
            randomConcilList.remove(randomNumber);
            randomConcilList.add(concilList1Model.get(concilLocation).toString());
        } else if(whichList.equals("list2")) {
            String oldName = concilList2Model.get(concilLocation).toString();
            concilList2Model.remove(concilLocation);
            int randomNumber = new Random().nextInt(randomConcilList.size());
            String newName = randomConcilList.get(randomNumber);
            concilList2Model.add(concilLocation, newName);
            MEDCase.replaceList2Concil(concilLocation, newName, oldName);
            randomConcilList.remove(randomNumber);
            randomConcilList.add(concilList2Model.get(concilLocation).toString());
//            concilList2Model.remove(concilLocation);
//            int randomNumber = new Random().nextInt(randomConcilList.size());
//            concilList2Model.add(concilLocation, randomConcilList.get(randomNumber));
//            randomConcilList.remove(randomNumber);
//            randomConcilList.add(concilList2Model.get(concilLocation).toString());
        }
    }
    
//    private void enableConcilComboBoxes()

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
        conciliationTypeComboBox = new javax.swing.JComboBox<>();
        conciliatorSelectionComboBox = new javax.swing.JComboBox<>();
        replacementConciliatorComboBox = new javax.swing.JComboBox<>();
        concilOriginalConciliator = new javax.swing.JTextField();
        appointmentDateTextBox = new com.alee.extended.date.WebDateField();
        originalConciliationDateTextBox = new com.alee.extended.date.WebDateField();
        asAgreedToParties = new javax.swing.JCheckBox();
        jPanel2 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        conciliation1List = new javax.swing.JList<>();
        jLabel1 = new javax.swing.JLabel();
        conciliation1GenerateButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        conciliation1OrderDate = new com.alee.extended.date.WebDateField();
        conciliation1SelectionDate = new com.alee.extended.date.WebDateField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        conciliation2List = new javax.swing.JList<>();
        conciliation2GenerateButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        conciliation2OrderDateTextBox = new com.alee.extended.date.WebDateField();
        conciliation2SelectionDateTextBox = new com.alee.extended.date.WebDateField();

        jLabel7.setText("Appointment Date:");

        jLabel8.setText("Fact Finder Type:");

        jLabel9.setText("Fact Finder Selection:");

        jLabel10.setText("Replacement Fact Finder:");

        jLabel11.setText("Original Fact Finder:");

        jLabel12.setText("Original Fact Finder Date:");

        conciliationTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selected by parties", "Discretionary", "Replacement", "Alternate Selection", "" }));
        conciliationTypeComboBox.setSelectedIndex(4);
        conciliationTypeComboBox.setEnabled(false);
        conciliationTypeComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conciliationTypeComboBoxActionPerformed(evt);
            }
        });

        conciliatorSelectionComboBox.setEnabled(false);
        conciliatorSelectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conciliatorSelectionComboBoxActionPerformed(evt);
            }
        });

        replacementConciliatorComboBox.setEnabled(false);

        concilOriginalConciliator.setBackground(new java.awt.Color(238, 238, 238));
        concilOriginalConciliator.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        concilOriginalConciliator.setEnabled(false);

        appointmentDateTextBox.setEditable(false);
        appointmentDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        appointmentDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        appointmentDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        appointmentDateTextBox.setEnabled(false);
        appointmentDateTextBox.setDateFormat(Global.mmddyyyy);
        appointmentDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                appointmentDateTextBoxMouseClicked(evt);
            }
        });

        originalConciliationDateTextBox.setEditable(false);
        originalConciliationDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        originalConciliationDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        originalConciliationDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        originalConciliationDateTextBox.setEnabled(false);
        originalConciliationDateTextBox.setDateFormat(Global.mmddyyyy);
        originalConciliationDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                originalConciliationDateTextBoxMouseClicked(evt);
            }
        });

        asAgreedToParties.setText("As Agreed To By Parties");
        asAgreedToParties.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                asAgreedToPartiesActionPerformed(evt);
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
                        .addComponent(asAgreedToParties)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(conciliationTypeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(conciliatorSelectionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(replacementConciliatorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(concilOriginalConciliator)
                    .addComponent(appointmentDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(originalConciliationDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                    .addComponent(conciliationTypeComboBox)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(conciliatorSelectionComboBox)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(replacementConciliatorComboBox)
                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11)
                    .addComponent(concilOriginalConciliator, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(originalConciliationDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(asAgreedToParties)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        conciliation1List.setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);
        conciliation1List.setEnabled(false);
        conciliation1List.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conciliation1ListMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(conciliation1List);

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Fact Finder List");

        conciliation1GenerateButton.setText("Generate List");
        conciliation1GenerateButton.setEnabled(false);
        conciliation1GenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conciliation1GenerateButtonActionPerformed(evt);
            }
        });

        jLabel3.setText("Order Date:");

        jLabel4.setText("Selection DueDate:");

        conciliation1OrderDate.setEditable(false);
        conciliation1OrderDate.setBackground(new java.awt.Color(238, 238, 238));
        conciliation1OrderDate.setCaretColor(new java.awt.Color(0, 0, 0));
        conciliation1OrderDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        conciliation1OrderDate.setEnabled(false);
        conciliation1OrderDate.setDateFormat(Global.mmddyyyy);
        conciliation1OrderDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conciliation1OrderDateMouseClicked(evt);
            }
        });

        conciliation1SelectionDate.setEditable(false);
        conciliation1SelectionDate.setBackground(new java.awt.Color(238, 238, 238));
        conciliation1SelectionDate.setCaretColor(new java.awt.Color(0, 0, 0));
        conciliation1SelectionDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        conciliation1SelectionDate.setEnabled(false);
        conciliation1SelectionDate.setDateFormat(Global.mmddyyyy);
        conciliation1SelectionDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conciliation1SelectionDateMouseClicked(evt);
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
                    .addComponent(conciliation1GenerateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(conciliation1OrderDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(conciliation1SelectionDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(conciliation1OrderDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(conciliation1SelectionDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conciliation1GenerateButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        conciliation2List.setEnabled(false);
        conciliation2List.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conciliation2ListMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(conciliation2List);

        conciliation2GenerateButton.setText("Generate List");
        conciliation2GenerateButton.setEnabled(false);
        conciliation2GenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                conciliation2GenerateButtonActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Fact Finder List #2");

        jLabel5.setText("Order Date:");

        jLabel6.setText("Selection Due Date:");

        conciliation2OrderDateTextBox.setEditable(false);
        conciliation2OrderDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        conciliation2OrderDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        conciliation2OrderDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        conciliation2OrderDateTextBox.setEnabled(false);
        conciliation2OrderDateTextBox.setDateFormat(Global.mmddyyyy);
        conciliation2OrderDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conciliation2OrderDateTextBoxMouseClicked(evt);
            }
        });

        conciliation2SelectionDateTextBox.setEditable(false);
        conciliation2SelectionDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        conciliation2SelectionDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        conciliation2SelectionDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        conciliation2SelectionDateTextBox.setEnabled(false);
        conciliation2SelectionDateTextBox.setDateFormat(Global.mmddyyyy);
        conciliation2SelectionDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                conciliation2SelectionDateTextBoxMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(conciliation2GenerateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel6)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(conciliation2SelectionDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 182, Short.MAX_VALUE)
                            .addComponent(conciliation2OrderDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(conciliation2OrderDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(conciliation2SelectionDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(conciliation2GenerateButton)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 13, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 151, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void appointmentDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appointmentDateTextBoxMouseClicked
        clearDate(appointmentDateTextBox, evt);
    }//GEN-LAST:event_appointmentDateTextBoxMouseClicked

    private void originalConciliationDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_originalConciliationDateTextBoxMouseClicked
        clearDate(originalConciliationDateTextBox, evt);
    }//GEN-LAST:event_originalConciliationDateTextBoxMouseClicked

    private void conciliation1OrderDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation1OrderDateMouseClicked
        clearDate(conciliation1OrderDate, evt);
    }//GEN-LAST:event_conciliation1OrderDateMouseClicked

    private void conciliation1SelectionDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation1SelectionDateMouseClicked
        clearDate(conciliation1SelectionDate, evt);
    }//GEN-LAST:event_conciliation1SelectionDateMouseClicked

    private void conciliation2OrderDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation2OrderDateTextBoxMouseClicked
        clearDate(conciliation2OrderDateTextBox, evt);
    }//GEN-LAST:event_conciliation2OrderDateTextBoxMouseClicked

    private void conciliation2SelectionDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation2SelectionDateTextBoxMouseClicked
        clearDate(conciliation2SelectionDateTextBox, evt);
    }//GEN-LAST:event_conciliation2SelectionDateTextBoxMouseClicked

    private void conciliation2GenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conciliation2GenerateButtonActionPerformed
        generateRandomConcilList("2");
    }//GEN-LAST:event_conciliation2GenerateButtonActionPerformed

    private void conciliation1GenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conciliation1GenerateButtonActionPerformed
        generateRandomConcilList("1");
    }//GEN-LAST:event_conciliation1GenerateButtonActionPerformed

    private void conciliation1ListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation1ListMouseClicked
        if(conciliation1List.isEnabled() && conciliation1List.getSelectedIndex() >= 0) {
            if(evt.getClickCount() == 2) {
                System.out.println("MORE INFO: " + concilList1Model.get(conciliation1List.getSelectedIndex()));
            } else if(evt.getButton() == MouseEvent.BUTTON3) {
                MEDRemoveConciliatorDialog remove = new MEDRemoveConciliatorDialog(Global.root, true, concilList1Model.get(conciliation1List.getSelectedIndex()).toString());
                if(remove.removeConcil) {
                    System.out.println("REMOVE: " + concilList1Model.get(conciliation1List.getSelectedIndex()));
                    replaceConcilAfterRemoval(conciliation1List.getSelectedIndex(), "list1");
                    conciliation1List.clearSelection();
                }
                remove.dispose();
            }
        }
    }//GEN-LAST:event_conciliation1ListMouseClicked

    private void conciliation2ListMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation2ListMouseClicked
        if(conciliation2List.isEnabled() && conciliation2List.getSelectedIndex() >= 0) {
            if(evt.getClickCount() == 2) {
                System.out.println("MORE INFO: " + concilList2Model.get(conciliation2List.getSelectedIndex()));
            } else if(evt.getButton() == MouseEvent.BUTTON3) {
                MEDRemoveConciliatorDialog remove = new MEDRemoveConciliatorDialog(Global.root, true, concilList2Model.get(conciliation2List.getSelectedIndex()).toString());
                if(remove.removeConcil) {
                    System.out.println("REMOVE: " + concilList2Model.get(conciliation2List.getSelectedIndex()));
                    replaceConcilAfterRemoval(conciliation2List.getSelectedIndex(), "list2");
                    conciliation2List.clearSelection();
                }
                remove.dispose();
            }
        }
    }//GEN-LAST:event_conciliation2ListMouseClicked

    private void conciliatorSelectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conciliatorSelectionComboBoxActionPerformed
        switch(conciliationTypeComboBox.getSelectedItem().toString().trim()) {
                case "Selected by parties":
                    populateConciliatorReplacement();
                    break;
                case "Discretionary":
                    populateFullConciliatorReplacement();
                    break;
                case "Replacement":
                    populateFullConciliatorReplacement();
                    break;
                case "Alternate Selection":
                    populateFullConciliatorReplacement();
                    break;
            }
        
//        populateConciliatorReplacement();
    }//GEN-LAST:event_conciliatorSelectionComboBoxActionPerformed

    private void conciliationTypeComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_conciliationTypeComboBoxActionPerformed
//        enableUpdate();
//        System.out.println("TYPE: " + conciliationTypeComboBox.getSelectedItem().toString());
        
        if(conciliationTypeComboBox.isEnabled()) {
            switch(conciliationTypeComboBox.getSelectedItem().toString().trim()) {
                case "Selected by parties":
                    conciliatorSelectionComboBox.setSelectedItem("");
                    replacementConciliatorComboBox.setSelectedItem("");
                    conciliatorSelectionComboBox.setEnabled(true);
                    replacementConciliatorComboBox.setEnabled(true);
                    originalConciliationDateTextBox.setEnabled(true);
                    originalConciliationDateTextBox.setBackground(Color.white);
                    populateConciliatorSelection();
                    populateConciliatorReplacement();
                    break;
                case "Discretionary":
                    conciliatorSelectionComboBox.setSelectedItem("");
                    replacementConciliatorComboBox.setSelectedItem("");
                    conciliatorSelectionComboBox.setEnabled(true);
                    replacementConciliatorComboBox.setEnabled(true);
                    originalConciliationDateTextBox.setEnabled(true);
                    originalConciliationDateTextBox.setBackground(Color.white);
                    populateFullConciliatorSelection();
                    populateFullConciliatorReplacement();
                    break;
                case "Replacement":
                    conciliatorSelectionComboBox.setSelectedItem("");
                    replacementConciliatorComboBox.setSelectedItem("");
                    conciliatorSelectionComboBox.setEnabled(false);
                    conciliatorSelectionComboBox.setSelectedItem("");
                    replacementConciliatorComboBox.setEnabled(true);
                    originalConciliationDateTextBox.setEnabled(true);
                    originalConciliationDateTextBox.setBackground(Color.white);
                    populateFullConciliatorSelection();
                    populateFullConciliatorReplacement();
                    break;
                case "Alternate Selection":
                    conciliatorSelectionComboBox.setSelectedItem("");
                    replacementConciliatorComboBox.setSelectedItem("");
                    conciliatorSelectionComboBox.setEnabled(true);
                    replacementConciliatorComboBox.setEnabled(true);
                    originalConciliationDateTextBox.setEnabled(true);
                    originalConciliationDateTextBox.setBackground(Color.white);
                    populateFullConciliatorSelection();
                    populateFullConciliatorReplacement();
                    
                    break;
                default:
                    conciliatorSelectionComboBox.setEnabled(false);
                    replacementConciliatorComboBox.setEnabled(false);
                    originalConciliationDateTextBox.setEnabled(false);
                    originalConciliationDateTextBox.setBackground(new Color(238,238,238));
                    conciliatorSelectionComboBox.setSelectedItem("");
                    replacementConciliatorComboBox.setSelectedItem("");
                    break;
            }
        }
        
        
    }//GEN-LAST:event_conciliationTypeComboBoxActionPerformed

    private void asAgreedToPartiesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_asAgreedToPartiesActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_asAgreedToPartiesActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.extended.date.WebDateField appointmentDateTextBox;
    private javax.swing.JCheckBox asAgreedToParties;
    private javax.swing.JTextField concilOriginalConciliator;
    private javax.swing.JButton conciliation1GenerateButton;
    private javax.swing.JList<String> conciliation1List;
    private com.alee.extended.date.WebDateField conciliation1OrderDate;
    private com.alee.extended.date.WebDateField conciliation1SelectionDate;
    private javax.swing.JButton conciliation2GenerateButton;
    private javax.swing.JList<String> conciliation2List;
    private com.alee.extended.date.WebDateField conciliation2OrderDateTextBox;
    private com.alee.extended.date.WebDateField conciliation2SelectionDateTextBox;
    private javax.swing.JComboBox<String> conciliationTypeComboBox;
    private javax.swing.JComboBox<String> conciliatorSelectionComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
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
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.alee.extended.date.WebDateField originalConciliationDateTextBox;
    private javax.swing.JComboBox<String> replacementConciliatorComboBox;
    // End of variables declaration//GEN-END:variables
}
