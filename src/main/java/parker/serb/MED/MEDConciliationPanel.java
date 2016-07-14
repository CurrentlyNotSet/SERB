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
import javax.swing.ListModel;
import parker.serb.Global;
import parker.serb.sql.FactFinder;
import parker.serb.sql.MEDCase;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class MEDConciliationPanel extends javax.swing.JPanel {

    List<String> fullConcilList = new ArrayList<>();
    List<String> randomConcilList = new ArrayList<>();
    DefaultListModel concilList1Model = new DefaultListModel();
    DefaultListModel concilList2Model = new DefaultListModel();
    MEDCase orginalInformation;
    
    
    /**
     * Creates new form MEDConciliationPanel
     */
    public MEDConciliationPanel() {
        initComponents();
    }
    
    public void enableUpdate() {
        
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);
        
        //list 1
        conciliation1OrderDate.setEnabled(true);
        conciliation1OrderDate.setBackground(Color.WHITE);
        conciliation1SelectionDate.setEnabled(true);
        conciliation1SelectionDate.setBackground(Color.WHITE);
        conciliation1List.setEnabled(true);
        
        if(conciliation1List.getModel().getSize() == 0) {
            conciliation1GenerateButton.setEnabled(true);
        }
        
        //list 2
        conciliation2OrderDateTextBox.setEnabled(true);
        conciliation2OrderDateTextBox.setBackground(Color.WHITE);
        conciliation2SelectionDateTextBox.setEnabled(true);
        conciliation2SelectionDateTextBox.setBackground(Color.WHITE);
        conciliation2List.setEnabled(true);
        
        if(conciliation2List.getModel().getSize() == 0) {
            conciliation2GenerateButton.setEnabled(true);
        }
        
        //middle information
        appointmentDateTextBox.setEnabled(true);
        appointmentDateTextBox.setBackground(Color.white);
        conciliationTypeComboBox.setEnabled(true);
        conciliatorSelectionComboBox.setEnabled(true);
        replacementConciliatorComboBox.setEnabled(true);
        originalConciliationDateTextBox.setEnabled(true);
        originalConciliationDateTextBox.setBackground(Color.white);
        
    }
    
    public void disableUpdate(boolean save) {
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);
        
        conciliation1OrderDate.setEnabled(false);
        conciliation1OrderDate.setBackground(new Color(238,238,238));
        conciliation1SelectionDate.setEnabled(false);
        conciliation1SelectionDate.setBackground(new Color(238,238,238));
        conciliation1List.setEnabled(false);
        conciliation1GenerateButton.setEnabled(false);
        
        //list 2
        conciliation2OrderDateTextBox.setEnabled(false);
        conciliation2OrderDateTextBox.setBackground(new Color(238,238,238));
        conciliation2SelectionDateTextBox.setEnabled(false);
        conciliation2SelectionDateTextBox.setBackground(new Color(238,238,238));
        conciliation2List.setEnabled(false);
        conciliation2GenerateButton.setEnabled(false);
        
        //middle information
        appointmentDateTextBox.setEnabled(false);
        appointmentDateTextBox.setBackground(new Color(238,238,238));
        conciliationTypeComboBox.setEnabled(false);
        conciliatorSelectionComboBox.setEnabled(false);
        replacementConciliatorComboBox.setEnabled(false);
        originalConciliationDateTextBox.setEnabled(false);
        originalConciliationDateTextBox.setBackground(new Color(238,238,238));
        
        if(save) {
            saveInformation();
        }
        
        loadInformation();
    }
    
    public void loadInformation() {
        loadFullConcilList();
        
        orginalInformation = MEDCase.loadConciliationInformation();
        
        conciliation1OrderDate.setText(orginalInformation.concilList1OrderDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.concilList1OrderDate.getTime())) : "");
        conciliation1SelectionDate.setText(orginalInformation.concilList1SelectionDueDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.concilList1SelectionDueDate.getTime())) : "");
       
        if(orginalInformation.concilList1Name1 != null) {
            concilList1Model.removeAllElements();
            conciliation1List.setModel(concilList1Model);
            concilList1Model.add(0, orginalInformation.concilList1Name1);
            concilList1Model.add(1, orginalInformation.concilList1Name2);
            concilList1Model.add(2, orginalInformation.concilList1Name3);
            concilList1Model.add(3, orginalInformation.concilList1Name4);
            concilList1Model.add(4, orginalInformation.concilList1Name5);
            conciliation1List.setModel(concilList1Model);
        } else {
            concilList1Model.removeAllElements();
            conciliation1List.setModel(concilList1Model);
        }
        
        appointmentDateTextBox.setText(orginalInformation.concilAppointmentDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.concilAppointmentDate.getTime())) : "");
        conciliationTypeComboBox.setSelectedItem("");
        conciliatorSelectionComboBox.setSelectedItem("");
        replacementConciliatorComboBox.setSelectedItem("");
        concilOriginalConciliator.setText("");
        originalConciliationDateTextBox.setText("");
        
        conciliation1OrderDate.setText(orginalInformation.concilList1OrderDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.concilList1OrderDate.getTime())) : "");
        conciliation1SelectionDate.setText(orginalInformation.concilList1SelectionDueDate != null ? Global.mmddyyyy.format(new Date(orginalInformation.concilList1SelectionDueDate.getTime())) : "");
       
        if(orginalInformation.concilList2Name1 != null) {
            concilList2Model.removeAllElements();
            conciliation2List.setModel(concilList2Model);
            concilList2Model.add(0, orginalInformation.concilList2Name1);
            concilList2Model.add(1, orginalInformation.concilList2Name2);
            concilList2Model.add(2, orginalInformation.concilList2Name3);
            concilList2Model.add(3, orginalInformation.concilList2Name4);
            concilList2Model.add(4, orginalInformation.concilList2Name5);
            conciliation2List.setModel(concilList2Model);
        } else {
            concilList2Model.removeAllElements();
            conciliation2List.setModel(concilList2Model);
        }
        
    }
    
    private void saveInformation() {
        MEDCase newMEDCaseInformation = new MEDCase();
        
        newMEDCaseInformation.concilList1OrderDate = conciliation1OrderDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(conciliation1OrderDate.getText()));
        newMEDCaseInformation.concilList1SelectionDueDate = conciliation1SelectionDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(conciliation1SelectionDate.getText()));
        
        if(concilList1Model.getSize() == 5) {
            newMEDCaseInformation.concilList1Name1 = concilList1Model.get(0).toString();
            newMEDCaseInformation.concilList1Name2 = concilList1Model.get(1).toString();
            newMEDCaseInformation.concilList1Name3 = concilList1Model.get(2).toString();
            newMEDCaseInformation.concilList1Name4 = concilList1Model.get(3).toString();
            newMEDCaseInformation.concilList1Name5 = concilList1Model.get(4).toString();
        } else {
            newMEDCaseInformation.concilList1Name1 = null;
            newMEDCaseInformation.concilList1Name2 = null;
            newMEDCaseInformation.concilList1Name3 = null;
            newMEDCaseInformation.concilList1Name4 = null;
            newMEDCaseInformation.concilList1Name5 = null;
        }
        
//        newMEDCaseInformation.concilList1OrderDate = conciliation1OrderDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(conciliation1OrderDate.getText()));

        newMEDCaseInformation.concilList2OrderDate = conciliation2OrderDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(conciliation2OrderDateTextBox.getText()));
        newMEDCaseInformation.concilList2SelectionDueDate = conciliation2SelectionDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(conciliation2SelectionDateTextBox.getText()));
        
        if(concilList2Model.getSize() == 5) {
            newMEDCaseInformation.concilList2Name1 = concilList2Model.get(0).toString();
            newMEDCaseInformation.concilList2Name2 = concilList2Model.get(1).toString();
            newMEDCaseInformation.concilList2Name3 = concilList2Model.get(2).toString();
            newMEDCaseInformation.concilList2Name4 = concilList2Model.get(3).toString();
            newMEDCaseInformation.concilList2Name5 = concilList2Model.get(4).toString();
        } else {
            newMEDCaseInformation.concilList2Name1 = null;
            newMEDCaseInformation.concilList2Name2 = null;
            newMEDCaseInformation.concilList2Name3 = null;
            newMEDCaseInformation.concilList2Name4 = null;
            newMEDCaseInformation.concilList2Name5 = null;
        }
        
        MEDCase.updateConciliation(newMEDCaseInformation, orginalInformation);
    }
    
    public void clearAll() {
        //list1
        conciliation1OrderDate.setText("");
        conciliation1SelectionDate.setText("");
        concilList1Model.removeAllElements();
        conciliation1List.setModel(concilList1Model);
        
        //list2
        conciliation2OrderDateTextBox.setText("");
        conciliation2SelectionDateTextBox.setText("");
        concilList2Model.removeAllElements();
        conciliation2List.setModel(concilList2Model);
        
        //middle information
        appointmentDateTextBox.setText("");
        conciliationTypeComboBox.setSelectedItem("");
        conciliatorSelectionComboBox.setSelectedItem("");
        replacementConciliatorComboBox.setSelectedItem("");
        concilOriginalConciliator.setText("");
        originalConciliationDateTextBox.setText("");
    }
    
    private void loadFullConcilList() {
        fullConcilList = FactFinder.loadAllConciliators();
        randomConcilList = fullConcilList;
    }
    
    private void generateRandomConcilList(String whichList) {
        //remove list 1 names
        for(int i = 0; i < conciliation1List.getModel().getSize(); i++) {
            randomConcilList.remove(conciliation1List.getModel().getElementAt(i));
        }
        
        //remove list 2 names
        for(int i = 0; i < conciliation2List.getModel().getSize(); i++) {
            randomConcilList.remove(conciliation2List.getModel().getElementAt(i));
        }
        
        //if loading list 1
        if(whichList.equals("1")) {
            concilList1Model.removeAllElements();
            for(int i = 0; i < 5; i++) {
                int randomNumber = new Random().nextInt(randomConcilList.size());
                concilList1Model.add(0, randomConcilList.get(randomNumber));
                randomConcilList.remove(randomNumber);
            }
            conciliation1List.setModel(concilList1Model);
            conciliation1GenerateButton.setEnabled(false);
        //if loading list 2
        } else if(whichList.equals("2")) {
            concilList2Model.removeAllElements();
            for(int i = 0; i < 5; i++) {
                int randomNumber = new Random().nextInt(randomConcilList.size());
                concilList2Model.add(0, randomConcilList.get(randomNumber));
                randomConcilList.remove(randomNumber);
            }
            conciliation2List.setModel(concilList2Model);
            conciliation2GenerateButton.setEnabled(false);
        }
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
            concilList1Model.remove(concilLocation);
            int randomNumber = new Random().nextInt(randomConcilList.size());
            concilList1Model.add(concilLocation, randomConcilList.get(randomNumber));
            randomConcilList.remove(randomNumber);
            randomConcilList.add(concilList1Model.get(concilLocation).toString());
        } else if(whichList.equals("list2")) {
            concilList2Model.remove(concilLocation);
            int randomNumber = new Random().nextInt(randomConcilList.size());
            concilList2Model.add(concilLocation, randomConcilList.get(randomNumber));
            randomConcilList.remove(randomNumber);
            randomConcilList.add(concilList2Model.get(concilLocation).toString());
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
        conciliationTypeComboBox = new javax.swing.JComboBox<>();
        conciliatorSelectionComboBox = new javax.swing.JComboBox<>();
        replacementConciliatorComboBox = new javax.swing.JComboBox<>();
        concilOriginalConciliator = new javax.swing.JTextField();
        appointmentDateTextBox = new com.alee.extended.date.WebDateField();
        originalConciliationDateTextBox = new com.alee.extended.date.WebDateField();
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

        jLabel8.setText("Conciliation Type:");

        jLabel9.setText("Conciliator Selection:");

        jLabel10.setText("Replacement Conciliator:");

        jLabel11.setText("Original Conciliator:");

        jLabel12.setText("Original Conciliation Date:");

        conciliationTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selected by parties", "Discretionary", "Replacement", "Alternate Selection", "" }));
        conciliationTypeComboBox.setSelectedIndex(4);
        conciliationTypeComboBox.setEnabled(false);

        conciliatorSelectionComboBox.setEnabled(false);

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
        appointmentDateTextBox.setDateFormat(Global.mmddyyyy);
        originalConciliationDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                originalConciliationDateTextBoxMouseClicked(evt);
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
                    .addComponent(conciliationTypeComboBox, 0, 0, Short.MAX_VALUE)
                    .addComponent(conciliatorSelectionComboBox, 0, 162, Short.MAX_VALUE)
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
        jLabel1.setText("Conciliator List");

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
        jLabel2.setText("Conciliator List #2");

        jLabel5.setText("Order Date:");

        jLabel6.setText("Selection Due Date:");

        conciliation2OrderDateTextBox.setEditable(false);
        conciliation2OrderDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        conciliation2OrderDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        conciliation2OrderDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        conciliation2OrderDateTextBox.setEnabled(false);
        appointmentDateTextBox.setDateFormat(Global.mmddyyyy);
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
        appointmentDateTextBox.setDateFormat(Global.mmddyyyy);
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void appointmentDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appointmentDateTextBoxMouseClicked
//        clearDate(assignedDateTextBox, evt);
    }//GEN-LAST:event_appointmentDateTextBoxMouseClicked

    private void originalConciliationDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_originalConciliationDateTextBoxMouseClicked
//        clearDate(assignedDateTextBox, evt);
    }//GEN-LAST:event_originalConciliationDateTextBoxMouseClicked

    private void conciliation1OrderDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation1OrderDateMouseClicked
        clearDate(conciliation1OrderDate, evt);
    }//GEN-LAST:event_conciliation1OrderDateMouseClicked

    private void conciliation1SelectionDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation1SelectionDateMouseClicked
        clearDate(conciliation1SelectionDate, evt);
    }//GEN-LAST:event_conciliation1SelectionDateMouseClicked

    private void conciliation2OrderDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation2OrderDateTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_conciliation2OrderDateTextBoxMouseClicked

    private void conciliation2SelectionDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_conciliation2SelectionDateTextBoxMouseClicked
        // TODO add your handling code here:
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
                MEDRemoveConciliatorDialog remove = new MEDRemoveConciliatorDialog(Global.root, true);
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
                MEDRemoveConciliatorDialog remove = new MEDRemoveConciliatorDialog(Global.root, true);
                if(remove.removeConcil) {
                    System.out.println("REMOVE: " + concilList2Model.get(conciliation2List.getSelectedIndex()));
                    replaceConcilAfterRemoval(conciliation2List.getSelectedIndex(), "list2");
                    conciliation2List.clearSelection();
                }
                remove.dispose();
            }
        }
    }//GEN-LAST:event_conciliation2ListMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private com.alee.extended.date.WebDateField appointmentDateTextBox;
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
