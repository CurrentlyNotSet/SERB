/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.report;

import parker.serb.Global;
import parker.serb.sql.SMDSDocuments;

/**
 *
 * @author User
 */
public class RequestedInfoMonthYearPanel extends javax.swing.JDialog {

    SMDSDocuments report;

    /**
     * Creates new form RequestedReportInformationPanel
     *
     * @param parent
     * @param modal
     * @param reportPassed
     */
    public RequestedInfoMonthYearPanel(java.awt.Frame parent, boolean modal, SMDSDocuments reportPassed) {
        super(parent, modal);
        report = reportPassed;
        initComponents();
        setActive(report.fileName);
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void setActive(String reportName) {
        reportLabel.setText(reportName);
        comboBoxLabel.setText("Month: ");
        TextFieldLabel.setText("Year: ");
        loadCombobox();
        generateButton();
    }

    private void loadCombobox() {
        ComboBox.addItem("");
        for (String month : Global.MONTH_LIST) {
            ComboBox.addItem(month);
        }
        ComboBox.setSelectedItem("");
    }

    private void generateButton() {
        if (TextField.getText().trim().equals("") || ComboBox.getSelectedItem().toString().equals("")) {
            GenerateReportButton.setEnabled(false);
        } else {
            GenerateReportButton.setEnabled(true);
        }
    }

    private void GenerateReport(){
        if (report.fileName.equalsIgnoreCase("All Organizations Noncompliance Report All Months.jasper")){
            GenerateReport.generateExactStringReport(
                    ORGNonComplianceAllWhereStatement.oneYearPriorData(ComboBox.getSelectedItem().toString(), TextField.getText().trim()),
                    report
            );
        } else {
            GenerateReport.generateMonthYearReport(ComboBox.getSelectedItem().toString(), TextField.getText().trim(), report);
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
        comboBoxLabel = new javax.swing.JLabel();
        GenerateReportButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        reportLabel = new javax.swing.JLabel();
        ComboBox = new javax.swing.JComboBox();
        TextFieldLabel = new javax.swing.JLabel();
        TextField = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Required Report Information");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Required Report Information");

        comboBoxLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        comboBoxLabel.setText("<<LABEL>>");

        GenerateReportButton.setText("Generate Report");
        GenerateReportButton.setEnabled(false);
        GenerateReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateReportButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        reportLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportLabel.setText("<<REPORTNAME>>");

        ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxActionPerformed(evt);
            }
        });

        TextFieldLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        TextFieldLabel.setText("<<LABEL>>");

        TextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                TextFieldKeyTyped(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 395, Short.MAX_VALUE)
                    .addComponent(reportLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(GenerateReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(comboBoxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(TextFieldLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(TextField)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(reportLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxLabel))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(TextFieldLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 23, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 50, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelButton)
                    .addComponent(GenerateReportButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ComboBox, comboBoxLabel});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void GenerateReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateReportButtonActionPerformed
        GenerateReport();
    }//GEN-LAST:event_GenerateReportButtonActionPerformed

    private void ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxActionPerformed
        generateButton();
    }//GEN-LAST:event_ComboBoxActionPerformed

    private void TextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_TextFieldKeyTyped
        generateButton();
    }//GEN-LAST:event_TextFieldKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JComboBox ComboBox;
    private javax.swing.JButton GenerateReportButton;
    private javax.swing.JTextField TextField;
    private javax.swing.JLabel TextFieldLabel;
    private javax.swing.JLabel comboBoxLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel reportLabel;
    // End of variables declaration//GEN-END:variables
}
