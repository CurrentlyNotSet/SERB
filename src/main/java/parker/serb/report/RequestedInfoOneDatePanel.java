/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.report;

import com.alee.extended.date.WebCalendar;
import com.alee.utils.swing.Customizer;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.SMDSDocuments;

/**
 *
 * @author User
 */
public class RequestedInfoOneDatePanel extends javax.swing.JDialog {

    SMDSDocuments report;

    /**
     * Creates new form RequestedReportInformationPanel
     *
     * @param parent
     * @param modal
     * @param reportPassed
     */
    public RequestedInfoOneDatePanel(java.awt.Frame parent, boolean modal, SMDSDocuments reportPassed) {
        super(parent, modal);
        report = reportPassed;
        initComponents();
        addListener();
        setText();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void addListener() {
        startDateField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                generateButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                generateButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                generateButton();
            }
        });
    }

    private void setText() {
        jLabel3.setText(report.fileName);
        
        switch (report.parameters) {
            case "date":
                jLabel7.setText("Date: ");
                break;
            case "Board Meeting Date":
                jLabel7.setText("Board Meeting Date: ");
                break;
            default:
                break;
        }
    }

    private void generateButton() {
        if (startDateField.getText().trim().equals("")) {
            GenerateReportButton.setEnabled(false);
        } else {
            GenerateReportButton.setEnabled(true);
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
        startDateField = new com.alee.extended.date.WebDateField();
        jLabel7 = new javax.swing.JLabel();
        GenerateReportButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Required Report Information");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Required Report Information");

        startDateField.setEditable(false);
        startDateField.setDateFormat(Global.mmddyyyy);

        startDateField.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            startDateField.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    startDateFieldMouseClicked(evt);
                }
            });
            startDateField.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    startDateFieldActionPerformed(evt);
                }
            });

            jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            jLabel7.setText("Date:");

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

            jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel3.setText("REPORTNAME");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(CancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(GenerateReportButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                            .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 251, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel1)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel3)
                    .addGap(20, 20, 20)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel7))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 43, Short.MAX_VALUE)
                    .addComponent(GenerateReportButton)
                    .addGap(18, 18, 18)
                    .addComponent(CancelButton)
                    .addContainerGap())
            );

            layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {jLabel7, startDateField});

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void GenerateReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateReportButtonActionPerformed
        GenerateReport.generateSingleDatesReport(startDateField.getText(), report);
    }//GEN-LAST:event_GenerateReportButtonActionPerformed

    private void startDateFieldMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_startDateFieldMouseClicked
        generateButton();
    }//GEN-LAST:event_startDateFieldMouseClicked

    private void startDateFieldActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_startDateFieldActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_startDateFieldActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton GenerateReportButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel7;
    private com.alee.extended.date.WebDateField startDateField;
    // End of variables declaration//GEN-END:variables
}
