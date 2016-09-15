/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ORG;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.CaseParty;
import parker.serb.sql.ORGCase;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.Item;

/**
 *
 * @author User
 */
public class ORGAllLettersPanel extends javax.swing.JDialog {

    List<ORGCase> orgCaseList;
    List<CaseParty> partyList;
    
    /**
     * Creates new form ORGAllLettersPanel
     * @param parent
     * @param modal
     */
    public ORGAllLettersPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDefaults();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }
    
    private void setDefaults(){
        setColumnSize();
        loadReports();
        enableGenerateButton();
    }
    
    private void setColumnSize() {        
        //ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        
        //ORG #
        jTable1.getColumnModel().getColumn(1).setMinWidth(60);
        jTable1.getColumnModel().getColumn(1).setWidth(60);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(60);
        
        //Org Name
        //NONE SET
        
        //ORG Via
        jTable1.getColumnModel().getColumn(3).setMinWidth(80);
        jTable1.getColumnModel().getColumn(3).setWidth(80);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(80);
        
        //Rep Via
        jTable1.getColumnModel().getColumn(4).setMinWidth(80);
        jTable1.getColumnModel().getColumn(4).setWidth(80);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(80);
        
        //AR Last
        jTable1.getColumnModel().getColumn(5).setMinWidth(80);
        jTable1.getColumnModel().getColumn(5).setWidth(80);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(80);
        
        //FS Last
        jTable1.getColumnModel().getColumn(6).setMinWidth(80);
        jTable1.getColumnModel().getColumn(6).setWidth(80);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(80);
    }
        
    private void loadReports() {
        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("ORG", "Letter");
            
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        letterComboBox.setModel(dt);
        letterComboBox.addItem(new Item<>("0", ""));
                
        for (SMDSDocuments letter : letterList) {
            letterComboBox.addItem(new Item<>( String.valueOf(letter.id), letter.description ));
        }
        letterComboBox.setSelectedItem(new Item<>("0", "" ));
    }
        
    private void processComboBoxSelection() {
        orgCaseList = null;
        partyList = null;
        Calendar cal = Calendar.getInstance();
        
        switch (letterComboBox.getSelectedItem().toString()) {
            case "Tickler 45 days":
                cal.set(Calendar.DAY_OF_MONTH, 15);
                cal.add(Calendar.MONTH, 1);
                cal.add(Calendar.MONTH, -5);
                processOverdueNumbers(cal);
                break;
            case "Tickler 10 days":
                cal.set(Calendar.DAY_OF_MONTH, 15);
                cal.add(Calendar.MONTH, -5);
                processOverdueNumbers(cal);
                break;
            case "Tickler 31 days overdue":
                cal.set(Calendar.DAY_OF_MONTH, 15);
                cal.add(Calendar.MONTH, -5);
                cal.add(Calendar.MONTH, -1);
                processOverdueNumbers(cal);
                break;
            default:
                orgCaseList = ORGCase.getOrgCasesAllLettersDefault();
                break;
        }
        
        FYEDuringTextField.setText(letterComboBox.getSelectedItem().toString().equals("") 
                ? "" : cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
        loadTable();
    }
    
    private void processOverdueNumbers(Calendar cal){
        String FYEMonthName = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);
        
        orgCaseList = ORGCase.getOrgCasesAllLetters(FYEMonthName, Global.SQLDateFormat.format(cal.getTime()));
    }
    
    private void loadTable(){
        int postalNumber = 0;
        int EmailNumber = 0;
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        for (ORGCase item : orgCaseList) {
            String orgVia = "";
            String repVia = "";
            
            partyList = CaseParty.loadORGPartiesByCase(item.orgNumber);
            for (CaseParty party : partyList) {
                if (party.caseRelation.equals("Representative")) {
                    if (item.orgEmail != null) {
                        EmailNumber++;
                        if (!repVia.trim().equals("")){
                            repVia += ", ";
                        }
                        repVia += "Email";
                    } else if (item.orgAddress1 != null & item.orgCity != null & item.orgState != null && item.orgZip != null) {
                        postalNumber++;
                        if (!repVia.trim().equals("")){
                            repVia += ", ";
                        }
                        repVia += "Postal";
                    }
                }
            }

            if (item.orgEmail != null){
                EmailNumber++;
                orgVia = "Email";
            } else if (item.orgAddress1 != null & item.orgCity != null & item.orgState != null && item.orgZip != null){
                postalNumber++;
                orgVia = "Postal";
            }
            
            model.addRow(new Object[]{
                item.id,        //id
                item.orgNumber, //org Number
                item.orgName,   //org Name
                orgVia,         //org via
                repVia,         //rep via
                Global.mmddyyyy.format(item.annualReport),  //AR Last
                Global.mmddyyyy.format(item.financialReport)//FS Last
            });
        }
        
        NumberOfOrgsTextField.setText(letterComboBox.getSelectedItem().toString().equals("") 
                ? "" : String.valueOf(orgCaseList.size()));
        PostalMailTextField.setText(letterComboBox.getSelectedItem().toString().equals("") 
                ? "" : String.valueOf(postalNumber));
        EMailTextField.setText(letterComboBox.getSelectedItem().toString().equals("") 
                ? "" : String.valueOf(EmailNumber));
    }
    
    private void enableGenerateButton() {
        if(letterComboBox.getSelectedItem().toString().equals("") || orgCaseList.isEmpty()) {
            GenerateButton.setEnabled(false);
        } else {
            GenerateButton.setEnabled(true);
        }
    }
    
    private void generateLetters(){
        for (ORGCase item : orgCaseList) {
            partyList = CaseParty.loadORGPartiesByCase(item.orgNumber);
            for (CaseParty party : partyList) {
                if (party.caseRelation.equals("Representative")) {
                    if (item.orgEmail != null) {
                        //TODO: Email Message to Rep
                    } else if (item.orgAddress1 != null & item.orgCity != null & item.orgState != null && item.orgZip != null) {
                        //TODO: Postal Message to Rep
                    }
                }
            }

            if (item.orgEmail != null){
                //TODO: Email Message to ORG
            } else if (item.orgAddress1 != null & item.orgCity != null & item.orgState != null && item.orgZip != null){
                //TODO: Postal Message to ORG
            }
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

        jLabel1 = new javax.swing.JLabel();
        letterComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        FYEDuringTextField = new javax.swing.JTextField();
        PostalMailTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        EMailTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        NumberOfOrgsTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        CloseButton = new javax.swing.JButton();
        GenerateButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Letters For All Organizations");

        letterComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letterComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Letter:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Fiscal Year Ending During:");

        FYEDuringTextField.setBackground(new java.awt.Color(238, 238, 238));
        FYEDuringTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        FYEDuringTextField.setEnabled(false);

        PostalMailTextField.setBackground(new java.awt.Color(238, 238, 238));
        PostalMailTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        PostalMailTextField.setEnabled(false);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Postal Mail:");

        EMailTextField.setBackground(new java.awt.Color(238, 238, 238));
        EMailTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        EMailTextField.setEnabled(false);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("E-Mail:");

        NumberOfOrgsTextField.setBackground(new java.awt.Color(238, 238, 238));
        NumberOfOrgsTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        NumberOfOrgsTextField.setEnabled(false);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Number of Organizations:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Org #", "Organization Name", "Org Via", "Rep Via", "AR Last", "FS Last"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        GenerateButton.setText("Generate Letters");
        GenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateButtonActionPerformed(evt);
            }
        });

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
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(GenerateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(letterComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EMailTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PostalMailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NumberOfOrgsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                            .addComponent(FYEDuringTextField))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, jLabel5});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(letterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(PostalMailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(EMailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(FYEDuringTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NumberOfOrgsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 243, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CloseButton)
                    .addComponent(GenerateButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {EMailTextField, FYEDuringTextField, NumberOfOrgsTextField, PostalMailTextField, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, letterComboBox});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void GenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateButtonActionPerformed
        generateLetters();
    }//GEN-LAST:event_GenerateButtonActionPerformed

    private void letterComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_letterComboBoxActionPerformed
        processComboBoxSelection();        
        enableGenerateButton();
    }//GEN-LAST:event_letterComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JTextField EMailTextField;
    private javax.swing.JTextField FYEDuringTextField;
    private javax.swing.JButton GenerateButton;
    private javax.swing.JTextField NumberOfOrgsTextField;
    private javax.swing.JTextField PostalMailTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox letterComboBox;
    // End of variables declaration//GEN-END:variables
}
