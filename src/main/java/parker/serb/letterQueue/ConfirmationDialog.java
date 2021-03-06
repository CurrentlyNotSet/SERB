/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterQueue;

import com.alee.laf.optionpane.WebOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import parker.serb.Global;
import parker.serb.sql.EmailOut;
import parker.serb.sql.EmailOutAttachment;
import parker.serb.sql.PostalOut;
import parker.serb.sql.PostalOutAttachment;
import parker.serb.util.FileService;

/**
 *
 * @author parker
 */
public class ConfirmationDialog extends javax.swing.JDialog {

    String type;
    int letterID;
    double attachmentSize = 0.0;
    List<String> filesMissing = new ArrayList<>();
    boolean fileInUse = false;
    List<String> fileInUseList = new ArrayList<>();

    public ConfirmationDialog(java.awt.Frame parent, boolean modal, String typePassed, int letterIDPassed) {
        super(parent, modal);
        initComponents();
        loadPanel(typePassed, letterIDPassed);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void loadPanel(String typePassed, int letterIDPassed) {
        loadingPanel.setVisible(false);
        type = typePassed;
        letterID = letterIDPassed;
    }

    private void sendLetter() {
        if (type.equals("Email")) {
            EmailOut.markEmailReadyToSend(letterID);
            WebOptionPane.showMessageDialog(Global.root, "<html><center>Email added to Outgoing Mail Queue</center></html>", "Process Complete", WebOptionPane.INFORMATION_MESSAGE);
        } else if (type.equals("Postal")) {
            String fullFilePath = postalSend.sendPostal(letterID);

            //Open File
            FileService.openFileFullPath(new File(fullFilePath));
        }
    }

    private void processThread() {
        Thread temp = new Thread(() -> {
            sendLetter();
            dispose();
        });
        temp.start();
    }

    private boolean verifyFilesExist() {
        boolean allExist = true;
        fileInUse = false;
        String path = "";
        if (type.equals("Email")) {
            EmailOut eml = EmailOut.getEmailByID(letterID);
            List<EmailOutAttachment> attachList = EmailOutAttachment.getEmailAttachments(letterID);
            if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                    || Global.activeSection.equalsIgnoreCase("CSC")
                    || Global.activeSection.equalsIgnoreCase("ORG")) {
                path = Global.activityPath
                        + (Global.activeSection.equals("Civil Service Commission")
                        ? eml.caseType : Global.activeSection) + File.separator + eml.caseNumber + File.separator;
            } else {
                path = Global.activityPath + File.separatorChar
                        + Global.activeSection + File.separatorChar
                        + eml.caseYear + File.separatorChar
                        + (eml.caseYear + "-" + eml.caseType + "-" + eml.caseMonth + "-" + eml.caseNumber)
                        + File.separatorChar;
            }

            for (EmailOutAttachment attach : attachList) {
                File attachment = new File(path + attach.fileName);
                if (!attachment.exists()) {
                    allExist = false;
                    filesMissing.add(attach.fileName);
                } else {
                    if ("docx".equalsIgnoreCase(FilenameUtils.getExtension(attach.fileName))
                            || "doc".equalsIgnoreCase(FilenameUtils.getExtension(attach.fileName))) {
                        if (!attachment.renameTo(attachment)) {
                            fileInUse = true;
                            fileInUseList.add(attach.fileName);
                        }
                        attachmentSize += attachment.length();
                    }
                }
            }

        } else if (type.equals("Postal")) {
            PostalOut post = PostalOut.getPostalOutByID(letterID);
            List<PostalOutAttachment> postList = PostalOutAttachment.getPostalOutAttachments(letterID);
            if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                    || Global.activeSection.equalsIgnoreCase("CSC")
                    || Global.activeSection.equalsIgnoreCase("ORG")) {
                path = Global.activityPath
                        + (Global.activeSection.equals("Civil Service Commission")
                        ? post.caseType : Global.activeSection) + File.separator + post.caseNumber + File.separator;
            } else {
                path = Global.activityPath + File.separatorChar
                        + Global.activeSection + File.separatorChar
                        + post.caseYear + File.separatorChar
                        + (post.caseYear + "-" + post.caseType + "-" + post.caseMonth + "-" + post.caseNumber)
                        + File.separatorChar;
            }

            for (PostalOutAttachment attach : postList) {
                File attachment = new File(path + attach.fileName);
                if (!attachment.exists()) {
                    allExist = false;
                    filesMissing.add(attach.fileName);
                } else {
                    if ("docx".equalsIgnoreCase(FilenameUtils.getExtension(attach.fileName))
                            || "doc".equalsIgnoreCase(FilenameUtils.getExtension(attach.fileName))) {
                        if (!attachment.renameTo(attachment)) {
                            fileInUse = true;
                            fileInUseList.add(attach.fileName);
                        }
                        attachmentSize += attachment.length();
                    }
                }
            }

        }
        return allExist;
    }

    private void missingFilesMessage() {
        String listOfFiles = "";
        for (String file : filesMissing) {
            listOfFiles += "<br>" + file;
        }
        WebOptionPane.showMessageDialog(
                Global.root,
                "<html><center> Sorry, unable to locate file(s) required to send.<br>" + listOfFiles + "</center></html>",
                "Error",
                WebOptionPane.ERROR_MESSAGE
        );
    }

    private void tooLargeMessage() {
        WebOptionPane.showMessageDialog(
                Global.root,
                "<html><center> Sorry, email size exceeds server limit unable to send.</center></html>",
                "Error",
                WebOptionPane.ERROR_MESSAGE
        );
    }

    private void filesInUseMessage() {
        String listOfFiles = "";
        for (String file : fileInUseList) {
            listOfFiles += "<br>" + file;
        }
        WebOptionPane.showMessageDialog(
                Global.root,
                "<html><center> Sorry, files in use. Please close documents before sending.<br>" + listOfFiles + "</center></html>",
                "Error",
                WebOptionPane.ERROR_MESSAGE
        );
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane = new javax.swing.JLayeredPane();
        InfoPanel = new javax.swing.JPanel();
        headerLabel = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        generateButton = new javax.swing.JButton();
        headerLabel1 = new javax.swing.JLabel();
        headerLabel2 = new javax.swing.JLabel();
        loadingPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMaximumSize(new java.awt.Dimension(340, 300));
        setMinimumSize(new java.awt.Dimension(340, 300));

        jLayeredPane.setLayout(new javax.swing.OverlayLayout(jLayeredPane));

        InfoPanel.setMaximumSize(new java.awt.Dimension(340, 300));
        InfoPanel.setMinimumSize(new java.awt.Dimension(340, 300));
        InfoPanel.setPreferredSize(new java.awt.Dimension(340, 300));

        headerLabel.setFont(new java.awt.Font("Lucida Grande", 1, 18)); // NOI18N
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        generateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                generateButtonActionPerformed(evt);
            }
        });

        headerLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        headerLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        headerLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 14)); // NOI18N
        headerLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout InfoPanelLayout = new javax.swing.GroupLayout(InfoPanel);
        InfoPanel.setLayout(InfoPanelLayout);
        InfoPanelLayout.setHorizontalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InfoPanelLayout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(headerLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(headerLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 320, Short.MAX_VALUE)
                    .addComponent(headerLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        InfoPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, generateButton});

        InfoPanelLayout.setVerticalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addGap(60, 60, 60)
                .addComponent(headerLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(headerLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 122, Short.MAX_VALUE)
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        jLayeredPane.add(InfoPanel);

        loadingPanel.setMaximumSize(new java.awt.Dimension(340, 300));
        loadingPanel.setMinimumSize(new java.awt.Dimension(340, 300));
        loadingPanel.setPreferredSize(new java.awt.Dimension(340, 300));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);

        javax.swing.GroupLayout loadingPanelLayout = new javax.swing.GroupLayout(loadingPanel);
        loadingPanel.setLayout(loadingPanelLayout);
        loadingPanelLayout.setHorizontalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loadingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        loadingPanelLayout.setVerticalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 220, Short.MAX_VALUE)
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

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        if (verifyFilesExist()) {
            if (Global.EMAIL_SIZE_LIMIT >= attachmentSize) {
                if (fileInUse) {
                    filesInUseMessage();
                } else {
                    processThread();
                    loadingPanel.setVisible(true);
                    InfoPanel.setVisible(false);
                    jLayeredPane.moveToFront(loadingPanel);
                    generateButton.setEnabled(false);
                    cancelButton.setEnabled(false);
                }
            } else {
                tooLargeMessage();
            }
        } else {
            missingFilesMessage();
        }
    }//GEN-LAST:event_generateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel InfoPanel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel headerLabel1;
    private javax.swing.JLabel headerLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JPanel loadingPanel;
    // End of variables declaration//GEN-END:variables
}
