/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.report;

import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.CaseValidation;
import parker.serb.sql.SMDSDocuments;

/**
 *
 * @author parkerjohnston
 */
public class RequestedInfoCaseNumberPanel extends javax.swing.JDialog {

    private final SMDSDocuments report;
    
    /**
     * Creates new form AddNewRelatedCase
     * @param parent
     * @param modal
     * @param reportPassed
     */
    public RequestedInfoCaseNumberPanel(java.awt.Frame parent, boolean modal, SMDSDocuments reportPassed) {
        super(parent, modal);
        report = reportPassed;
        initComponents();
        setText(report.fileName);
        enableAddButton();
        addListeners();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }
    
    private void setText(String reportName) {
        jLabel3.setText(reportName);
        caseNotFoundLabel.setText("");
    }
    
    private void addListeners() {
        caseNumberTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableAddButton();    
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableAddButton(); 
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableAddButton(); 
            }
        });
    }
    
    private void enableAddButton() {
        caseNotFoundLabel.setText("");
        
        String[] parsedCaseNumber = caseNumberTextBox.getText().trim().split("-");
        
        if(caseNumberTextBox.getText().equals("") ||
                parsedCaseNumber.length < 4) {
            generateReportButton.setEnabled(false);
            caseNotFoundLabel.setText("Invalid Case Number");
        } else {
            if (CaseValidation.validateCaseNumber(caseNumberTextBox.getText().trim().toUpperCase())){
            generateReportButton.setEnabled(true);
            } else {
                caseNotFoundLabel.setText("Case Number Not Found");
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

        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        caseNumberTextBox = new javax.swing.JTextField();
        generateReportButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        caseNotFoundLabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REPORTNAME");

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Required Report Information");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Please enter the related case number");

        caseNumberTextBox.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        caseNumberTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                caseNumberTextBoxMouseClicked(evt);
            }
        });

        generateReportButton.setText("Generate Report");
        generateReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateReportButtonActionPerformed(evt);
            }
        });

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        caseNotFoundLabel.setForeground(new java.awt.Color(255, 0, 51));
        caseNotFoundLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        caseNotFoundLabel.setText("Case Number Not Found!");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 434, Short.MAX_VALUE)
                    .addComponent(caseNotFoundLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 129, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(68, 68, 68)
                .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 318, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addGap(30, 30, 30)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(caseNotFoundLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 44, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(generateReportButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void generateReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateReportButtonActionPerformed
        GenerateReport.generateCasenumberReport(caseNumberTextBox.getText().trim().toUpperCase(), report);
    }//GEN-LAST:event_generateReportButtonActionPerformed

    private void caseNumberTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_caseNumberTextBoxMouseClicked
        if (Global.activeSection.equals("ULP")){
        if(SwingUtilities.isRightMouseButton(evt) || evt.getButton() == MouseEvent.BUTTON3) {
            ULPCaseSearch search = new ULPCaseSearch(null, true);
            if (search != null){
                caseNumberTextBox.setText(search.caseNumber);
            }
            search.dispose();
        }
        }
    }//GEN-LAST:event_caseNumberTextBoxMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel caseNotFoundLabel;
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JButton generateReportButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    // End of variables declaration//GEN-END:variables
}
