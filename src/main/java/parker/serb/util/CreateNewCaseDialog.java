/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import java.awt.event.ActionEvent;
import java.text.DateFormatSymbols;
import java.util.Calendar;
import java.util.List;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CMDSCaseSearchData;
import parker.serb.sql.CaseNumber;
import parker.serb.sql.CaseParty;
import parker.serb.sql.CaseType;
import parker.serb.sql.EmployerCaseSearchData;
import parker.serb.sql.MEDCase;
import parker.serb.sql.MEDCaseSearchData;
import parker.serb.sql.REPCase;
import parker.serb.sql.REPCaseSearchData;
import parker.serb.sql.ULPCase;
import parker.serb.sql.ULPCaseSearchData;

/**
 *
 * @author parker
 */
public class CreateNewCaseDialog extends javax.swing.JDialog {

    /**
     * Creates new form CreateNewCaseDialog
     */
    public CreateNewCaseDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadInformation();
        addListeners();
        getNextCaseNumber(typeComboBox.getSelectedItem().toString(), yearComboBox.getSelectedItem().toString());
        loadRelatedCases();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void loadRelatedCases() {
        List<String> relatedCases = null;
        switch(Global.activeSection) {
            case "ULP":
                relatedCases = ULPCase.loadRelatedCases();
                break;
            case "REP":
                relatedCases = REPCase.loadRelatedCases();
                break;  
            case "MED":
                relatedCases = MEDCase.loadRelatedCases();
                break; 
            case "CMDS":
                relatedCases = CMDSCase.loadRelatedCases();
                break;     
            default:
                break;
        }
        
        similarCaseComboBox.removeAllItems();
        similarCaseComboBox.addItem("");
        
        if(relatedCases != null) {
            for(int i = 0; i <  relatedCases.size(); i++) {
                similarCaseComboBox.addItem(relatedCases.get(i));
            }
        }
    }
    
    private void getNextCaseNumber(String caseType, String year) {
        if(Global.activeSection.equals("CMDS")) {
            caseNumberTextBox.setText(String.format("%04d", Integer.parseInt(CaseNumber.getCMDSCaseNumber(year))));
        } else {
            caseNumberTextBox.setText(String.format("%04d", Integer.parseInt(CaseNumber.getCaseNumber(caseType, year))));
        }
    }
    
    private void addListeners() {
        typeComboBox.addActionListener((ActionEvent e) -> {
            getNextCaseNumber(typeComboBox.getSelectedItem().toString(), yearComboBox.getSelectedItem().toString());
        });
        
        yearComboBox.addActionListener((ActionEvent e) -> {
            getNextCaseNumber(typeComboBox.getSelectedItem().toString(), yearComboBox.getSelectedItem().toString());
        });
    }
    
    private void loadInformation() {
        loadYears();
        loadCaseTypes();
        loadMonths();
    }
    
    private void loadYears() {
        yearComboBox.removeAllItems();
        
        for(int i = 0; i < 5; i++) {
            yearComboBox.addItem(Calendar.getInstance().get(Calendar.YEAR) - i);
        }
    }
    
    private void loadCaseTypes() {
        
        List casetypes = CaseType.getCaseType();
        
        for (Object casetype : casetypes) {
            typeComboBox.addItem(casetype);
        }
    }
    
    private void loadMonths() {
        monthComboBox.removeAllItems();
        
        String[] months = new DateFormatSymbols().getMonths();
        for (int i = 0; i < months.length-1; i++) {
            String month = months[i];
            monthComboBox.addItem(String.format("%02d", (i+1)) + " - " + month);
        }
        
        monthComboBox.setSelectedIndex(Calendar.getInstance().get(Calendar.MONTH));
    }
    
    
    
    
    private String buildCaseNumber() {
        String caseNumber = yearComboBox.getSelectedItem().toString();
        caseNumber += "-";
        caseNumber += typeComboBox.getSelectedItem().toString();
        caseNumber += "-";
        caseNumber += monthComboBox.getSelectedItem().toString().substring(0, 2);
        caseNumber += "-";
        caseNumber += caseNumberTextBox.getText().trim();
        return caseNumber;
    }
    
    private void createCase() {
        switch (Global.activeSection) {
            case "REP":
                REPCase.createCase(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                REPCaseSearchData.createNewCaseEntry(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                EmployerCaseSearchData.createNewCaseEntry(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                break;
            case "MED":
                MEDCase.createCase(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                MEDCaseSearchData.createNewCaseEntry(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                EmployerCaseSearchData.createNewCaseEntry(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                break;
            case "ULP":
                ULPCase.createCase(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                ULPCaseSearchData.createNewCaseEntry(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                EmployerCaseSearchData.createNewCaseEntry(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                break;
            case "CMDS":
                CMDSCaseSearchData.createNewCaseEntry(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                CMDSCase.createCase(yearComboBox.getSelectedItem().toString(),
                        typeComboBox.getSelectedItem().toString(),
                        monthComboBox.getSelectedItem().toString().substring(0, 2),
                        caseNumberTextBox.getText().trim());
                break; 
        }
        dispose();
    }
    
    private void duplicateCaseInformation() {
        CaseParty.duplicatePartyInformation(buildCaseNumber(), similarCaseComboBox.getSelectedItem().toString().trim());
        
        switch(Global.activeSection) {
            case "ULP": 
                Global.root.getuLPHeaderPanel1().loadHeaderInformation();
                break;
            case "MED": 
                Global.root.getmEDHeaderPanel1().loadHeaderInformation();
                break;
            case "REP": 
                Global.root.getrEPHeaderPanel1().loadHeaderInformation();
                break;
            case "CMDS": 
                Global.root.getcMDSHeaderPanel1().loadHeaderInformation();
                break;
            default:
                break;
        }
    }
    
    private boolean isFirstCase() {
        
        boolean firstCase = false;
        
        switch(Global.activeSection) {
            case "ULP": firstCase =  ULPCase.checkIfFristCaseOfMonth(
                    yearComboBox.getSelectedItem().toString(),
                    typeComboBox.getSelectedItem().toString(),
                    monthComboBox.getSelectedItem().toString().substring(0, 2));
                break;
            case "REP": firstCase =  REPCase.checkIfFristCaseOfMonth(
                    yearComboBox.getSelectedItem().toString(),
                    typeComboBox.getSelectedItem().toString(),
                    monthComboBox.getSelectedItem().toString().substring(0, 2));
                break;
            case "MED": firstCase =  MEDCase.checkIfFristCaseOfMonth(
                    yearComboBox.getSelectedItem().toString(),
                    typeComboBox.getSelectedItem().toString(),
                    monthComboBox.getSelectedItem().toString().substring(0, 2));
                break;
            case "CMDS": firstCase =  CMDSCase.checkIfFristCMDSCaseOfMonth(
                    yearComboBox.getSelectedItem().toString(),
                    monthComboBox.getSelectedItem().toString().substring(0, 2));
                break;
        }
        return firstCase;
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        yearComboBox = new javax.swing.JComboBox();
        typeComboBox = new javax.swing.JComboBox();
        monthComboBox = new javax.swing.JComboBox();
        caseNumberTextBox = new javax.swing.JTextField();
        similarCaseComboBox = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Create New Case");

        jLabel3.setText("Year:");

        jLabel4.setText("Type:");

        jLabel5.setText("Month:");

        jLabel6.setText("Number:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("Similar Case (Copy Case Information)");

        jButton1.setText("Create");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        yearComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        monthComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        caseNumberTextBox.setEditable(false);

        similarCaseComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 301, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel3)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(yearComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(typeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(monthComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(caseNumberTextBox)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1))
                    .addComponent(similarCaseComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(yearComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(monthComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel7)
                .addGap(5, 5, 5)
                .addComponent(similarCaseComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        boolean firstCase = isFirstCase();
        
        if(firstCase) {
            firstCaseOfMonthDialog firstCaseDialog = new firstCaseOfMonthDialog(
                    (JFrame) Global.root.getParent(), true, buildCaseNumber());
            if(firstCaseDialog.isConfirmed()) {
                createCase();
                if(!similarCaseComboBox.getSelectedItem().toString().trim().equals("")) {
                    duplicateCaseInformation();
                } 
                firstCaseDialog.dispose();
                dispose();
            } else {
                firstCaseDialog.dispose();
            }
            
        } else {
            createCase();
            if(!similarCaseComboBox.getSelectedItem().toString().trim().equals("")) {
                duplicateCaseInformation();
            } 
            dispose();
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JComboBox monthComboBox;
    private javax.swing.JComboBox<String> similarCaseComboBox;
    private javax.swing.JComboBox typeComboBox;
    private javax.swing.JComboBox yearComboBox;
    // End of variables declaration//GEN-END:variables
}
