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
import parker.serb.letterGeneration.LetterGenerationPanel;
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
            BOLettersSubComboBox.setEnabled(false);
            POQuestionsSubComboBox.setEnabled(false);

            loadReportRecsSubDropDown();
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

    private void generateDocument() {
        String selection = "";
        if (!BOLettersSubComboBox.getSelectedItem().toString().trim().equals("")) {
            selection = BOLettersSubComboBox.getSelectedItem().toString().trim();
        } else if (!POQuestionsSubComboBox.getSelectedItem().toString().trim().equals("")) {
            selection = POQuestionsSubComboBox.getSelectedItem().toString().trim();
        } else if (!ReportAndRecsSubComboBox.getSelectedItem().toString().trim().equals("")) {
            selection = ReportAndRecsSubComboBox.getSelectedItem().toString().trim();
        }

        if (!"".equals(selection)) {
            CMDSDocuments template = CMDSDocuments.findDocumentByName(selection);
            File templateFile = new File(Global.templatePath + Global.activeSection + File.separator + template.Location);

            if (templateFile.exists()){
                new LetterGenerationPanel(Global.root, true, null, template);
            } else {
                WebOptionPane.showMessageDialog(Global.root, "<html><center> Sorry, unable to locate template. <br><br>" + template.Location + "</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
            }

        }
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
                    .addComponent(ReportAndRecsSubComboBox, javax.swing.GroupLayout.Alignment.TRAILING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
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
    private javax.swing.JLabel jLabel5;
    // End of variables declaration//GEN-END:variables
}
