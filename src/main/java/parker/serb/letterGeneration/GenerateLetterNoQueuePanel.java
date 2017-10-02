/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterGeneration;

import com.alee.laf.optionpane.WebOptionPane;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.generateDocument;
import parker.serb.bookmarkProcessing.questionsCMDSModel;
import parker.serb.bookmarkProcessing.questionsCMDSPanel;
import parker.serb.sql.Activity;
import parker.serb.sql.Audit;
import parker.serb.sql.CMDSDocuments;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.FileService;

/**
 *
 * @author parker
 */
public class GenerateLetterNoQueuePanel extends javax.swing.JDialog {

    SMDSDocuments SMDSdocumentToGenerate;
    CMDSDocuments CMDSdocumentToGenerate;

    public GenerateLetterNoQueuePanel(java.awt.Frame parent, boolean modal, SMDSDocuments SMDSdocumentToGeneratePassed, CMDSDocuments CMDSdocumentToGeneratePassed) {
        super(parent, modal);
        initComponents();
        loadPanel(SMDSdocumentToGeneratePassed, CMDSdocumentToGeneratePassed);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void loadPanel(SMDSDocuments SMDSdocumentToGeneratePassed, CMDSDocuments CMDSdocumentToGeneratePassed) {
        loadingPanel.setVisible(true);
        SMDSdocumentToGenerate = SMDSdocumentToGeneratePassed;
        CMDSdocumentToGenerate = CMDSdocumentToGeneratePassed;

        if (SMDSdocumentToGeneratePassed != null){
            switch (SMDSdocumentToGeneratePassed.type) {
                case "Directive":
                    HeaderLabel.setText("Generating Directive");
                    break;
                case "Misc":
                    HeaderLabel.setText("Generating Misc");
                    break;
                case "Memo":
                    HeaderLabel.setText("Generating Memo");
                    break;
                default:
                    break;
            }
        }
        processThread();
    }

    private void processThread() {
        Thread temp = new Thread(() -> {
            generateLetter();
            dispose();
        });
        temp.start();
    }

    private void generateLetter() {
        String docName = null;
        questionsCMDSModel answers = null;

        switch (Global.activeSection) {
            case "CMDS":
                int count = CMDSDocuments.CMDSQuestionCount(CMDSdocumentToGenerate);
                if (count > 0) {
                    questionsCMDSPanel returnInfo = new questionsCMDSPanel(Global.root, true, CMDSdocumentToGenerate, count);
                    answers = returnInfo.answers;
                    returnInfo.dispose();
                } else {
                    answers = new questionsCMDSModel();
                }
                docName = generateDocument.generateCMDSdocument(CMDSdocumentToGenerate, answers, 0, null, null);
            case "ORG":
            case "Civil Service Commission":
                break;
            default:
                docName = generateDocument.generateSMDSdocument(SMDSdocumentToGenerate, 0, null, null, null, null, true);
                break;
        }

        if (docName != null) {
            switch (Global.activeSection) {
                case "CMDS":
                    Activity.addActivty("Generated " + CMDSdocumentToGenerate.LetterName, docName);
                    Audit.addAuditEntry("Generated " + CMDSdocumentToGenerate.LetterName);
                    break;
                case "ORG":
                case "Civil Service Commission":
                    break;
                default:
                    String activityDocName = null;

                    switch (SMDSdocumentToGenerate.type) {
                        case "Directive":
                        case "Misc":
                            break;
                        case "Memo":
                        case "Invest":
                            activityDocName = docName;
                            break;
                        default:
                            break;
                    }
                    Activity.addActivty("Generated " + (SMDSdocumentToGenerate.historyDescription == null ? SMDSdocumentToGenerate.description : SMDSdocumentToGenerate.historyDescription), activityDocName);
                    Audit.addAuditEntry("Generated " + SMDSdocumentToGenerate.historyDescription == null ? SMDSdocumentToGenerate.description : SMDSdocumentToGenerate.historyDescription);
                    break;
            }

            switch (Global.activeSection) {
                case "ORG":
                case "Civil Service Commission":
                case "Hearings":
                    break;
                default:
                    FileService.openFile(docName);
                    break;
            }

            reloadActivity();
        } else {
            WebOptionPane.showMessageDialog(Global.root,
                    "<html><div style='text-align: center;'>Files required to generate documents are missing."
                    + "<br><br>Unable to generate " + SMDSdocumentToGenerate.historyDescription + "</html>",
                    "Required File Missing", WebOptionPane.ERROR_MESSAGE);
        }
    }

    private void reloadActivity() {
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
                Global.root.getHearingRootPanel1().getActivityPanel1().loadAllHearingActivity();
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

        jLayeredPane = new javax.swing.JLayeredPane();
        loadingPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        HeaderLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(340, 300));
        setResizable(false);

        jLayeredPane.setLayout(new javax.swing.OverlayLayout(jLayeredPane));

        loadingPanel.setMaximumSize(new java.awt.Dimension(340, 300));
        loadingPanel.setMinimumSize(new java.awt.Dimension(340, 300));
        loadingPanel.setPreferredSize(new java.awt.Dimension(340, 300));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        HeaderLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        HeaderLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HeaderLabel.setText("<<Generating>>");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Please Wait...");

        javax.swing.GroupLayout loadingPanelLayout = new javax.swing.GroupLayout(loadingPanel);
        loadingPanel.setLayout(loadingPanelLayout);
        loadingPanelLayout.setHorizontalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loadingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HeaderLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        loadingPanelLayout.setVerticalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HeaderLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLayeredPane.add(loadingPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel HeaderLabel;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JPanel loadingPanel;
    // End of variables declaration//GEN-END:variables
}
