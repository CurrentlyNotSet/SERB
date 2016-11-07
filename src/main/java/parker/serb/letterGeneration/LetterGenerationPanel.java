/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterGeneration;

import com.alee.extended.date.WebDateField;
import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.generateDocument;
import parker.serb.sql.Activity;
import parker.serb.sql.CaseParty;
import parker.serb.sql.FactFinder;
import parker.serb.sql.MEDCase;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.FileService;
import parker.serb.util.StringUtilities;


/**
 *
 * @author parker
 */
public class LetterGenerationPanel extends javax.swing.JDialog {

    SMDSDocuments docToGenerate;
    MEDCase medCaseData;
        
    public LetterGenerationPanel(java.awt.Frame parent, boolean modal, SMDSDocuments documentToGeneratePassed) {
        super(parent, modal);
        initComponents();
        loadPanel(documentToGeneratePassed);
        setLocationRelativeTo(parent);
        setVisible(true);   
    }
    
    private void loadPanel(SMDSDocuments documentToGeneratePassed) {
        docToGenerate = documentToGeneratePassed;
        documentLabel.setText("Document: " + documentToGeneratePassed.description);
        setColumnWidth();
        loadPartyTable();
        loadActivityDocumentsTable();
        loadExtraAttachmentTable();        
    }
    
    private void setColumnWidth() {
        personTable.getColumnModel().getColumn(0).setMinWidth(0);
        personTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        personTable.getColumnModel().getColumn(0).setMaxWidth(0);
        personTable.getColumnModel().getColumn(1).setMinWidth(80);
        personTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        personTable.getColumnModel().getColumn(1).setMaxWidth(80);
        personTable.getColumnModel().getColumn(2).setMinWidth(90);
        personTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        personTable.getColumnModel().getColumn(2).setMaxWidth(90);
                
        activityTable.getColumnModel().getColumn(0).setMinWidth(0);
        activityTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        activityTable.getColumnModel().getColumn(0).setMaxWidth(0);
        activityTable.getColumnModel().getColumn(1).setMinWidth(60);
        activityTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        activityTable.getColumnModel().getColumn(1).setMaxWidth(60);
        
        additionalDocsTable.getColumnModel().getColumn(0).setMinWidth(0);
        additionalDocsTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        additionalDocsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        additionalDocsTable.getColumnModel().getColumn(1).setMinWidth(60);
        additionalDocsTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        additionalDocsTable.getColumnModel().getColumn(1).setMaxWidth(60);
    }
    
    private JComboBox loadLocationComboBox() {
        JComboBox locationCombo = new JComboBox();
        locationCombo.removeAllItems();
        locationCombo.addItem("");
        locationCombo.addItem("Email");
        locationCombo.addItem("Postal");
        locationCombo.addItem("Both");
        return locationCombo;
    }
    
    private JComboBox loadToCCComboBox() {
        JComboBox ToCCCombo = new JComboBox();
        ToCCCombo.removeAllItems();
        ToCCCombo.addItem("");
        ToCCCombo.addItem("TO:");
        ToCCCombo.addItem("CC:");        
        return ToCCCombo;
    }
    
    private void loadPartyTable(){
        DefaultTableModel model = (DefaultTableModel) personTable.getModel();
        model.setRowCount(0);
                
        JComboBox toCCComboBox = loadToCCComboBox();
        TableColumn rowOneCombo = personTable.getColumnModel().getColumn(1);
        rowOneCombo.setCellEditor(new DefaultCellEditor(toCCComboBox));
   
        toCCComboBox.addActionListener((ActionEvent e) -> {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Runnable task = () -> {
                if (personTable.getSelectedRow() >= 0 && personTable.getSelectedColumn() == 1) {
                    if (!personTable.getValueAt(personTable.getSelectedRow(), 1).toString().equals("") 
                            && !personTable.getValueAt(personTable.getSelectedRow(), 5).toString().equals("")
                            && personTable.getValueAt(personTable.getSelectedRow(), 2).toString().equals("")) {
                        personTable.setValueAt("Email", personTable.getSelectedRow(), 2);
                    } else if (!personTable.getValueAt(personTable.getSelectedRow(), 1).toString().equals("") 
                            && personTable.getValueAt(personTable.getSelectedRow(), 5).toString().equals("")
                            && personTable.getValueAt(personTable.getSelectedRow(), 2).toString().equals("")) {
                        personTable.setValueAt("Postal", personTable.getSelectedRow(), 2);
                    } else if (personTable.getValueAt(personTable.getSelectedRow(), 1).toString().equals("")) {
                        personTable.setValueAt("", personTable.getSelectedRow(), 2);
                    }
                }
            };
            executor.submit(task);
        });

        JComboBox destinationComboBox = loadLocationComboBox();
        TableColumn rowTwoCombo = personTable.getColumnModel().getColumn(2);
        rowTwoCombo.setCellEditor(new DefaultCellEditor(destinationComboBox));
        
        // SET TO/CC Listener
        destinationComboBox.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (e.getItem().equals("") && personTable.getSelectedColumn() == 2) {
                    personTable.setValueAt("", personTable.getSelectedRow(), 1);
                } 
                if (personTable.getValueAt(personTable.getSelectedRow(), 1).equals("")){
                    personTable.setValueAt("", personTable.getSelectedRow(), 2);
                }
            }
        });
             
        List<CaseParty> partyList = CaseParty.loadPartiesByCase();
        
        for (CaseParty party : partyList){
            String toCC = "";
            String emailPostal = "";
            
            if (Global.activeSection.equals("MED") 
                    && (party.caseRelation.equals("Employer REP") || party.caseRelation.equals("Employee Organization REP"))){
                if (!party.emailAddress.trim().equals("")){
                    emailPostal = "Email";
                } else {
                    emailPostal = "Postal";
                }
                toCC = "TO:";
            }
                        
            model.addRow(new Object[]{
                party.id,           // ID
                toCC,               // TO/CC
                emailPostal,        // Email/Postal
                party.caseRelation,
                StringUtilities.buildCasePartyName(party),  // NAME
                party.emailAddress
            });
        }   
    }
    
    private void loadActivityDocumentsTable(){
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setRowCount(0);
        
        List<Activity> activtyList = Activity.loadActivityDocumentsCyGlobalCase();
        
        for (Activity doc : activtyList){
            model.addRow(new Object[]{
                doc.id,
                false,
                doc.action
            });
        }  
    }
    
    private void loadExtraAttachmentTable(){
        DefaultTableModel model = (DefaultTableModel) additionalDocsTable.getModel();
        model.setRowCount(0); 
        List<SMDSDocuments> documentList = null;
        List<FactFinder> ffList = null;
                
        switch (Global.activeSection) {
            case "REP":
                documentList = SMDSDocuments.loadDocumentNamesByTypeAndSection(Global.caseType, "");
                break;
            case "ULP":
                documentList = SMDSDocuments.loadDocumentNamesByTypeAndSection(Global.caseType, "Quest");
                break;
            case "ORG":
                break;
            case "MED":
                additionalDocumentsLabel.setText("Fact Finder / Conciliator Bios:");
                additionalDocsTable.getColumnModel().getColumn(2).setHeaderValue("Person");
                ffList = FactFinder.loadActiveFF();
                break;
            case "Hearings":
                break;
            case "Civil Service Commission":
                break;
            case "CMDS":
                break;
        }
        if (documentList != null && !Global.activeSection.equals("MED")) {
            for (SMDSDocuments doc : documentList) {
                model.addRow(new Object[]{
                    doc.id,
                    false,
                    doc.description
                });
            }
        }
        
        if (ffList != null && Global.activeSection.equals("MED")) {
            medCaseData = MEDCase.loadEntireCaseInformation();
            
            for (FactFinder ff : ffList) {
                //Format Name
                String person = StringUtilities.buildFullName(ff.firstName, ff.middleName, ff.lastName);
                
                //check for checkmark
                boolean selected = false;
                if (docToGenerate.description.contains("Panel") && medCaseData != null){
                        selected = setMEDFFConcCheckMark(person);
                }
                
                //load table
                model.addRow(new Object[]{
                    ff.id,
                    selected,
                    person
                });
            }
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
    
    private boolean setMEDFFConcCheckMark(String Name){        
        if (!medCaseData.FFList2Name1.equals("") || !medCaseData.concilList2Name1.equals("")){
            if (medCaseData.FFList2Name1.equals(Name) || 
                    medCaseData.FFList2Name2.equals(Name) || 
                    medCaseData.FFList2Name3.equals(Name) || 
                    medCaseData.FFList2Name4.equals(Name) || 
                    medCaseData.FFList2Name5.equals(Name) ||
                    medCaseData.concilList2Name1.equals(Name) ||
                    medCaseData.concilList2Name2.equals(Name) ||
                    medCaseData.concilList2Name3.equals(Name) ||
                    medCaseData.concilList2Name4.equals(Name) ||
                    medCaseData.concilList2Name5.equals(Name)){
                return true;
            }
        } else {
            if (medCaseData.FFList1Name1.equals(Name) || 
                    medCaseData.FFList1Name2.equals(Name) || 
                    medCaseData.FFList1Name3.equals(Name) || 
                    medCaseData.FFList1Name4.equals(Name) || 
                    medCaseData.FFList1Name5.equals(Name) ||
                    medCaseData.concilList1Name1.equals(Name) ||
                    medCaseData.concilList1Name2.equals(Name) ||
                    medCaseData.concilList1Name3.equals(Name) ||
                    medCaseData.concilList1Name4.equals(Name) ||
                    medCaseData.concilList1Name5.equals(Name)){
                return true;
            }
        }
        return false;
    }
    
    private void generateLetter() {
        String docName = generateDocument.generateSMDSdocument(docToGenerate, 0);
        if (docName != null) {
            Activity.addActivty("Created " + docToGenerate.historyDescription, docName);
            reloadActivity();
            FileService.openFile(docName);
        } else {
            WebOptionPane.showMessageDialog(Global.root,
                    "<html><div style='text-align: center;'>Files required to generate documents are missing."
                    + "<br><br>Unable to generate " + docToGenerate.description + "</html>",
                    "Required File Missing", WebOptionPane.ERROR_MESSAGE);
        }
    }

    private void reloadActivity(){
        
        switch (Global.activeSection) {
            case "REP":
                Global.root.getrEPRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "ULP":
                Global.root.getuLPRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "ORG":
                Global.root.getoRGRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "MED":
                Global.root.getmEDRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "Hearings":
                break;
            case "Civil Service Commission":
                Global.root.getcSCRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "CMDS":
                Global.root.getcMDSRootPanel1().getActivityPanel1().loadAllActivity();
                break;
        }
    }
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        generateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        personTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        activityTable = new javax.swing.JTable();
        jScrollPane3 = new javax.swing.JScrollPane();
        additionalDocsTable = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        documentLabel = new javax.swing.JLabel();
        suggestedSendDatePicker = new com.alee.extended.date.WebDateField();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        additionalDocumentsLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Letter");

        generateButton.setText("Generate");
        generateButton.setEnabled(false);
        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        personTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "To/CC", "Destination", "Party Type", "Name", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                true, true, true, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        personTable.setCellSelectionEnabled(true);
        personTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(personTable);
        personTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

        activityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Attach", "Document"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane2.setViewportView(activityTable);
        if (activityTable.getColumnModel().getColumnCount() > 0) {
            activityTable.getColumnModel().getColumn(0).setResizable(false);
            activityTable.getColumnModel().getColumn(1).setResizable(false);
            activityTable.getColumnModel().getColumn(2).setResizable(false);
        }

        additionalDocsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Attach", "Document"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane3.setViewportView(additionalDocsTable);
        if (additionalDocsTable.getColumnModel().getColumnCount() > 0) {
            additionalDocsTable.getColumnModel().getColumn(0).setResizable(false);
            additionalDocsTable.getColumnModel().getColumn(1).setResizable(false);
            additionalDocsTable.getColumnModel().getColumn(2).setResizable(false);
        }

        jLabel2.setText("Send To:");

        documentLabel.setText("Document: <<DOCUMENT NAME>>");

        suggestedSendDatePicker.setEditable(false);
        suggestedSendDatePicker.setCaretColor(new java.awt.Color(0, 0, 0));
        suggestedSendDatePicker.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        suggestedSendDatePicker.setDateFormat(Global.mmddyyyy);
        suggestedSendDatePicker.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                suggestedSendDatePickerMouseClicked(evt);
            }
        });

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Suggested Send Date:");

        jLabel5.setText("Case Documents:");

        additionalDocumentsLabel.setText("Additional Documents:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 432, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(77, 77, 77)))
                        .addComponent(additionalDocumentsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(documentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(suggestedSendDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(documentLabel)
                    .addComponent(suggestedSendDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(additionalDocumentsLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 250, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateButton)
                    .addComponent(cancelButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {documentLabel, jLabel4, suggestedSendDatePicker});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        generateLetter();
        dispose();
    }//GEN-LAST:event_generateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void suggestedSendDatePickerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suggestedSendDatePickerMouseClicked
        clearDate(suggestedSendDatePicker, evt);
    }//GEN-LAST:event_suggestedSendDatePickerMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable activityTable;
    private javax.swing.JTable additionalDocsTable;
    private javax.swing.JLabel additionalDocumentsLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel documentLabel;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTable personTable;
    private com.alee.extended.date.WebDateField suggestedSendDatePicker;
    // End of variables declaration//GEN-END:variables
}
