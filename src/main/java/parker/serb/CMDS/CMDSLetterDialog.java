/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import parker.serb.Global;
import parker.serb.letterGeneration.GenerateLetterNoQueuePanel;
import parker.serb.letterGeneration.LetterGenerationPanel;
import parker.serb.sql.Audit;
import parker.serb.sql.CMDSDocuments;
import parker.serb.util.Item;

/**
 *
 * @author parker
 */
public class CMDSLetterDialog extends javax.swing.JDialog {

    public CMDSLetterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadDropDowns();
        addListeners();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addListeners() {

        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        BOLettersSubComboBox.setModel(dt);
        BOLettersSubComboBox.addItem(new Item<>("0", ""));
        POQuestionsSubComboBox.setModel(dt);
        POQuestionsSubComboBox.addItem(new Item<>("0", ""));
        ReportAndRecsSubComboBox.setModel(dt);
        ReportAndRecsSubComboBox.addItem(new Item<>("0", ""));

        BOLettersMainComboBox.addItemListener((ItemEvent e) -> {
            POQuestionsMainComboBox.setSelectedItem("");
            ReportAndRecsMainComboBox.setSelectedItem("");
            POQuestionsSubComboBox.setSelectedItem(new Item<>("0", ""));
            ReportAndRecsSubComboBox.setSelectedItem(new Item<>("0", ""));
            specialtyDocumentsComboBox.setSelectedItem(new Item<>("0", ""));
            POQuestionsSubComboBox.setEnabled(false);
            ReportAndRecsSubComboBox.setEnabled(false);

            loadBoardOrderSubDropDown();
            generateButton.setEnabled(false);
        });

        POQuestionsMainComboBox.addItemListener((ItemEvent e) -> {
            ReportAndRecsMainComboBox.setSelectedItem("");
            BOLettersMainComboBox.setSelectedItem("");
            ReportAndRecsSubComboBox.setSelectedItem(new Item<>("0", ""));
            BOLettersSubComboBox.setSelectedItem(new Item<>("0", ""));
            specialtyDocumentsComboBox.setSelectedItem(new Item<>("0", ""));
            ReportAndRecsSubComboBox.setEnabled(false);
            BOLettersSubComboBox.setEnabled(false);

            loadProceduralOrderSubDropDown();
            generateButton.setEnabled(false);
        });

        ReportAndRecsMainComboBox.addItemListener((ItemEvent e) -> {
            BOLettersMainComboBox.setSelectedItem("");
            POQuestionsMainComboBox.setSelectedItem("");
            BOLettersSubComboBox.setSelectedItem(new Item<>("0", ""));
            POQuestionsSubComboBox.setSelectedItem(new Item<>("0", ""));
            specialtyDocumentsComboBox.setSelectedItem(new Item<>("0", ""));
            BOLettersSubComboBox.setEnabled(false);
            POQuestionsSubComboBox.setEnabled(false);

            loadReportRecsSubDropDown();
            generateButton.setEnabled(false);
        });

        specialtyDocumentsComboBox.addItemListener((ItemEvent e) -> {
            BOLettersMainComboBox.setSelectedItem("");
            POQuestionsMainComboBox.setSelectedItem("");
            ReportAndRecsMainComboBox.setSelectedItem("");
            ReportAndRecsSubComboBox.setSelectedItem(new Item<>("0", ""));
            BOLettersSubComboBox.setSelectedItem(new Item<>("0", ""));
            POQuestionsSubComboBox.setSelectedItem(new Item<>("0", ""));
            BOLettersSubComboBox.setEnabled(false);
            ReportAndRecsSubComboBox.setEnabled(false);
            POQuestionsSubComboBox.setEnabled(false);

            generateButton.setEnabled(false);
        });

        BOLettersSubComboBox.addItemListener((ItemEvent e) -> {
            if (BOLettersSubComboBox.getSelectedItem() != null) {
                if (!BOLettersSubComboBox.getSelectedItem().toString().equals("")) {
                    generateButton.setEnabled(true);
                } else {
                    generateButton.setEnabled(false);
                }
            } else {
                generateButton.setEnabled(false);
            }
        });

        POQuestionsSubComboBox.addItemListener((ItemEvent e) -> {
            if (POQuestionsSubComboBox.getSelectedItem() != null) {
                if (!POQuestionsSubComboBox.getSelectedItem().toString().equals("")) {
                    generateButton.setEnabled(true);
                } else {
                    generateButton.setEnabled(false);
                }
            } else {
                generateButton.setEnabled(false);
            }
        });

        ReportAndRecsSubComboBox.addItemListener((ItemEvent e) -> {
            if (ReportAndRecsSubComboBox.getSelectedItem() != null) {
                if (!ReportAndRecsSubComboBox.getSelectedItem().toString().equals("")) {
                    generateButton.setEnabled(true);
                } else {
                    generateButton.setEnabled(false);
                }
            } else {
                generateButton.setEnabled(false);
            }
        });


    }

    private void loadDropDowns() {
        loadBoardOrderMainDropDown();
        loadProceduralOrderMainDropDown();
        loadReportRecsMainDropDown();
        loadSpecalityDropDown();
    }

    private void loadBoardOrderMainDropDown() {
        BOLettersMainComboBox.removeAllItems();
        BOLettersMainComboBox.addItem("");
        List<String> subTypeList = CMDSDocuments.findSubCategoriesByMainCategory("General");

        for (String subType : subTypeList) {
            BOLettersMainComboBox.addItem(subType);
        }
        BOLettersMainComboBox.setSelectedItem("");
    }

    private void loadBoardOrderSubDropDown() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        BOLettersSubComboBox.setModel(dt);
        BOLettersSubComboBox.addItem(new Item<>("0", ""));

        List<CMDSDocuments> letterList = CMDSDocuments.findDocumentsBySubCategories(BOLettersMainComboBox.getSelectedItem().toString());
        for (CMDSDocuments letter : letterList) {
            BOLettersSubComboBox.addItem(new Item<>(String.valueOf(letter.ID), letter.LetterName));
        }
        BOLettersSubComboBox.setSelectedItem(new Item<>("0", ""));
        BOLettersSubComboBox.setEnabled(true);
    }

    private void loadProceduralOrderMainDropDown() {
        POQuestionsMainComboBox.removeAllItems();
        POQuestionsMainComboBox.addItem("");
        List<String> subTypeList = CMDSDocuments.findSubCategoriesByMainCategory("Procedural");

        for (String subType : subTypeList) {
            POQuestionsMainComboBox.addItem(subType);
        }
        POQuestionsMainComboBox.setSelectedItem("");
    }

    private void loadProceduralOrderSubDropDown() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        POQuestionsSubComboBox.setModel(dt);
        POQuestionsSubComboBox.addItem(new Item<>("0", ""));

        List<CMDSDocuments> letterList = CMDSDocuments.findDocumentsBySubCategories(POQuestionsMainComboBox.getSelectedItem().toString());
        for (CMDSDocuments letter : letterList) {
            POQuestionsSubComboBox.addItem(new Item<>(String.valueOf(letter.ID), letter.LetterName));
        }
        POQuestionsSubComboBox.setSelectedItem(new Item<>("0", ""));
        POQuestionsSubComboBox.setEnabled(true);
    }

    private void loadReportRecsMainDropDown() {
        ReportAndRecsMainComboBox.removeAllItems();
        ReportAndRecsMainComboBox.addItem("");
        List<String> letterList = CMDSDocuments.findSubCategoriesByMainCategory("Report");

        for (String letter : letterList) {
            ReportAndRecsMainComboBox.addItem(letter);
        }
        ReportAndRecsMainComboBox.setSelectedItem("");
    }

    private void loadReportRecsSubDropDown() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        ReportAndRecsSubComboBox.setModel(dt);
        ReportAndRecsSubComboBox.addItem(new Item<>("0", ""));

        List<CMDSDocuments> letterList = CMDSDocuments.findDocumentsBySubCategories(ReportAndRecsMainComboBox.getSelectedItem().toString());
        for (CMDSDocuments letter : letterList) {
            ReportAndRecsSubComboBox.addItem(new Item<>(String.valueOf(letter.ID), letter.LetterName));
        }
        ReportAndRecsSubComboBox.setSelectedItem(new Item<>("0", ""));
        ReportAndRecsSubComboBox.setEnabled(true);
    }

    private void loadSpecalityDropDown() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        specialtyDocumentsComboBox.setModel(dt);
        specialtyDocumentsComboBox.addItem(new Item<>("0", ""));

        List<CMDSDocuments> letterList = CMDSDocuments.findDocumentsBySubCategories("Specialty");
        for (CMDSDocuments letter : letterList) {
            specialtyDocumentsComboBox.addItem(new Item<>(String.valueOf(letter.ID), letter.LetterName));
        }
        specialtyDocumentsComboBox.setSelectedItem(new Item<>("0", ""));
        specialtyDocumentsComboBox.setEnabled(true);
    }

    private void generateDocument() {
        String selectionName = "";
        int selectionID = 0;
        if (!BOLettersSubComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) BOLettersSubComboBox.getSelectedItem();
            selectionID = Integer.parseInt(item.getValue().toString());
            selectionName = BOLettersSubComboBox.getSelectedItem().toString().trim();
        } else if (!POQuestionsSubComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) POQuestionsSubComboBox.getSelectedItem();
            selectionID = Integer.parseInt(item.getValue().toString());
            selectionName = POQuestionsSubComboBox.getSelectedItem().toString().trim();
        } else if (!ReportAndRecsSubComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) ReportAndRecsSubComboBox.getSelectedItem();
            selectionID = Integer.parseInt(item.getValue().toString());
            selectionName = ReportAndRecsSubComboBox.getSelectedItem().toString().trim();
        } else if (!specialtyDocumentsComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) specialtyDocumentsComboBox.getSelectedItem();
            selectionID = Integer.parseInt(item.getValue().toString());
            selectionName = specialtyDocumentsComboBox.getSelectedItem().toString().trim();
            if (selectionName.equalsIgnoreCase("Initial Case Letter")){
                if (Global.caseType.equalsIgnoreCase("INV")){
                        selectionName = "Notice of Request for Investigation";
                        selectionID = 0;
                    } else {
                        selectionName = "Notice of Appeal";
                        selectionID = 0;
                    }
            }
        }

        if (!"".equals(selectionName) || selectionID > 0) {
            CMDSDocuments template = null;

            if (selectionID > 0) {
                template = CMDSDocuments.findDocumentByID(selectionID);
            } else {
                template = CMDSDocuments.findDocumentByName(selectionName);
            }

            if (template != null) {
                File templateFile = new File(Global.templatePath + Global.activeSection + File.separator + template.Location);

                if (templateFile.exists()) {
                    Audit.addAuditEntry("Generated CMDS Letter: " + templateFile);
                    if (specialtyDocumentsComboBox.getSelectedItem().toString().trim().equals("Initial Case Letter")) {
                        new GenerateLetterNoQueuePanel(Global.root, true, null, template);
                        if (Global.caseType.equalsIgnoreCase("REC")) {
                            generateRECdocument();
                        }
                    } else {
                        new LetterGenerationPanel(Global.root, true, null, template);
                    }
                } else {
                    missingTemplateMessage(template);
                }
            } else {
                missingTemplateMessage(template);
            }
        }
    }

    private void generateRECdocument() {
        CMDSDocuments template = CMDSDocuments.findDocumentByNameAndCategory("Procedural", "Reclassification", "Appellee Reclassification Questionnaire");
        if (template != null) {
            File templateFile = new File(Global.templatePath + Global.activeSection + File.separator + template.Location);
            if (templateFile.exists()) {
                Audit.addAuditEntry("Generated CMDS Letter: " + templateFile);
                new GenerateLetterNoQueuePanel(Global.root, true, null, template);
            } else {
                missingTemplateMessage(template);
            }
        } else {
            missingTemplateMessage(template);
        }
    }

    private void missingTemplateMessage(CMDSDocuments template){
        WebOptionPane.showMessageDialog(Global.root, "<html><center> Sorry, unable to locate template. <br><br>"
                    + template.Location + "</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        BOLettersMainComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        POQuestionsMainComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        ReportAndRecsMainComboBox = new javax.swing.JComboBox();
        generateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        BOLettersSubComboBox = new javax.swing.JComboBox();
        POQuestionsSubComboBox = new javax.swing.JComboBox();
        ReportAndRecsSubComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        specialtyDocumentsComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Document");

        jLabel2.setText("Board Orders & Letters:");

        jLabel3.setText("Procedural Orders & Questionaires:");

        jLabel5.setText("Report and Recommendations:");

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

        BOLettersSubComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "" }));
        BOLettersSubComboBox.setEnabled(false);

        POQuestionsSubComboBox.setEnabled(false);

        ReportAndRecsSubComboBox.setEnabled(false);

        jLabel4.setText("Specialty:");

        specialtyDocumentsComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                specialtyDocumentsComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BOLettersMainComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 636, Short.MAX_VALUE)
                    .addComponent(POQuestionsMainComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ReportAndRecsMainComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateButton))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(BOLettersSubComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(POQuestionsSubComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ReportAndRecsSubComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(specialtyDocumentsComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(BOLettersMainComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BOLettersSubComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(POQuestionsMainComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(POQuestionsSubComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ReportAndRecsMainComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ReportAndRecsSubComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(specialtyDocumentsComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 47, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(generateButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(cancelButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        generateDocument();
    }//GEN-LAST:event_generateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void specialtyDocumentsComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_specialtyDocumentsComboBoxActionPerformed
        if (specialtyDocumentsComboBox.getSelectedItem() != null) {
            if (!specialtyDocumentsComboBox.getSelectedItem().toString().equals("")) {
                generateButton.setEnabled(true);
            } else {
                generateButton.setEnabled(false);
            }
        } else {
            generateButton.setEnabled(false);
        }
    }//GEN-LAST:event_specialtyDocumentsComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox BOLettersMainComboBox;
    private javax.swing.JComboBox BOLettersSubComboBox;
    private javax.swing.JComboBox POQuestionsMainComboBox;
    private javax.swing.JComboBox POQuestionsSubComboBox;
    private javax.swing.JComboBox ReportAndRecsMainComboBox;
    private javax.swing.JComboBox ReportAndRecsSubComboBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox specialtyDocumentsComboBox;
    // End of variables declaration//GEN-END:variables
}
