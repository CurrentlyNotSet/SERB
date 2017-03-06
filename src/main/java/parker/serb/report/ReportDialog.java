/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.report;

//TODO: Load all REP Reports

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.RequestedBoardDatePanel;
import parker.serb.sql.CMDSReport;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.Item;

/**
 *
 * @author parker
 */
public class ReportDialog extends javax.swing.JDialog {

    String section;

    /**
     * Creates new form REPReportDialog
     * @param parent
     * @param modal
     * @param sectionPassed
     */
    public ReportDialog(java.awt.Frame parent, boolean modal, String sectionPassed) {
        super(parent, modal);
        initComponents();
        setDefaults(sectionPassed);
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void setDefaults(String sectionPassed){
        section = sectionPassed;

        //load reports
        loadReports();

        //load agendas
        switch (section) {
            case "REP":
            case "ULP":
                loadAgendas();
                Agendalabel.setVisible(true);
                AgendaComboBox.setVisible(true);
                break;
            case "MED":
            case "ORG":
            case "Hearings":
            case "Civil Service Commission":
            case "CMDS":
                Agendalabel.setVisible(false);
                AgendaComboBox.setVisible(false);
                break;
            default:
                break;
        }

        addListeners();
        this.pack();
    }

    private void addListeners() {
        ReportComboBox.addItemListener((ItemEvent e) -> {
            AgendaComboBox.setSelectedItem(new Item<>("0", ""));
            enableGenerateButton();
        });

        AgendaComboBox.addItemListener((ItemEvent e) -> {
            ReportComboBox.setSelectedItem(new Item<>("0", ""));
            enableGenerateButton();
        });
    }

    private void loadReports() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        ReportComboBox.setModel(dt);
        ReportComboBox.addItem(new Item<>("0", ""));

        List<SMDSDocuments> smdsletterList = null;
        List<CMDSReport> cmdsletterList = null;

        switch (Global.activeSection) {
            case "REP":
            case "ULP":
            case "MED":
            case "Hearings":
                smdsletterList = SMDSDocuments.loadDocumentNamesByTypeAndSectionWithAll(section, "Report");

                for (SMDSDocuments letter : smdsletterList) {
                    ReportComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
                }
                ReportComboBox.setSelectedItem(new Item<>("0", ""));
                break;
            case "ORG":
                smdsletterList = SMDSDocuments.loadDocumentNamesByTypeAndSection(section, "Report");

                for (SMDSDocuments letter : smdsletterList) {
                    ReportComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
                }
                ReportComboBox.setSelectedItem(new Item<>("0", ""));
                break;
            case "CSC":
            case "Civil Service Commission":
                cmdsletterList = CMDSReport.loadActiveReportsBySection(section);

                for (CMDSReport letter : cmdsletterList) {
                    ReportComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
                }
                ReportComboBox.setSelectedItem(new Item<>("0", ""));
                break;
            case "CMDS":
                List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("ALL", "Report");

                for (SMDSDocuments letter : letterList) {
                    ReportComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
                }

                cmdsletterList = CMDSReport.loadActiveReportsBySection(section);

                for (CMDSReport letter : cmdsletterList) {
                    ReportComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
                }
                ReportComboBox.setSelectedItem(new Item<>("0", ""));

                break;
            default:
                break;
        }
    }

    private void loadAgendas(){
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        AgendaComboBox.setModel(dt);
        AgendaComboBox.addItem(new Item<>("0", ""));

        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection(Global.activeSection, "Agenda");
        for (SMDSDocuments letter : letterList) {
            AgendaComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
        }
        AgendaComboBox.setSelectedItem(new Item<>("0", ""));
    }

    private void generateReport() {
        Item item = (Item) ReportComboBox.getSelectedItem();
        int id = Integer.parseInt(item.getValue().toString());

        switch (Global.activeSection) {
            case "REP":
            case "ULP":
            case "MED":
            case "ORG":
            case "Hearings":
                SMDSDocuments report = SMDSDocuments.findDocumentByID(id);
                GenerateReport.runReport(report);
                break;
            case "Civil Service Commission":
            case "CSC":
            case "CMDS":
                SMDSDocuments SMDSreport = null;

                CMDSReport cmdsreport = CMDSReport.getReportByIDandDescription(id, ReportComboBox.getSelectedItem().toString());

                if (cmdsreport != null) {
                    SMDSreport = new SMDSDocuments();
                    SMDSreport.section = cmdsreport.section;
                    SMDSreport.description = cmdsreport.description;
                    SMDSreport.fileName = cmdsreport.fileName;
                    SMDSreport.parameters = cmdsreport.parameters;
                } else {
                    SMDSreport = SMDSDocuments.findDocumentByID(id);
                }

                if (SMDSreport != null) {
                    GenerateReport.runReport(SMDSreport);
                }
                break;
            default:
                break;
        }
    }

    private void generateAgenda() {
        Item item = (Item) AgendaComboBox.getSelectedItem();
        int id = Integer.parseInt(item.getValue().toString());

        SMDSDocuments report = SMDSDocuments.findDocumentByID(id);
        File templateFile = new File(Global.templatePath + Global.activeSection + File.separator + report.fileName);
        if (templateFile.exists()) {
            new RequestedBoardDatePanel(Global.root, true, report);
        } else {
            WebOptionPane.showMessageDialog(Global.root, "<html><center> Sorry, unable to locate files selected. <br><br>Unable to generate letter.</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
        }
    }

    private void enableGenerateButton() {
        if (AgendaComboBox.isVisible()){
            if(ReportComboBox.getSelectedItem().toString().equals("") && AgendaComboBox.getSelectedItem().toString().equals("")) {
                GenerateButton.setEnabled(false);
            } else {
                GenerateButton.setEnabled(true);
            }
        } else {
            if(ReportComboBox.getSelectedItem().toString().equals("")) {
                GenerateButton.setEnabled(false);
            } else {
                GenerateButton.setEnabled(true);
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

        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        ReportComboBox = new javax.swing.JComboBox();
        GenerateButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        AgendaComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        Agendalabel = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Report");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Please Select a Report to Generate");

        ReportComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ReportComboBoxActionPerformed(evt);
            }
        });

        GenerateButton.setText("Generate");
        GenerateButton.setEnabled(false);
        GenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        AgendaComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AgendaComboBoxActionPerformed(evt);
            }
        });

        jLabel3.setText("Report:");

        Agendalabel.setText("Agenda:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(CancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 299, Short.MAX_VALUE)
                        .addComponent(GenerateButton))
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(AgendaComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(ReportComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Agendalabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ReportComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Agendalabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(AgendaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(GenerateButton)
                    .addComponent(CancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateButtonActionPerformed
        if(ReportComboBox.getSelectedItem().toString().equals("")) {
                generateAgenda();
            } else {
                generateReport();
            }
    }//GEN-LAST:event_GenerateButtonActionPerformed

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void ReportComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ReportComboBoxActionPerformed

    }//GEN-LAST:event_ReportComboBoxActionPerformed

    private void AgendaComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AgendaComboBoxActionPerformed

    }//GEN-LAST:event_AgendaComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox AgendaComboBox;
    private javax.swing.JLabel Agendalabel;
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton GenerateButton;
    private javax.swing.JComboBox ReportComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JSeparator jSeparator4;
    // End of variables declaration//GEN-END:variables
}
