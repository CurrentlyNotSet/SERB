/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import parker.serb.Global;
import parker.serb.letterGeneration.LetterGenerationPanel;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.Item;

/**
 *
 * @author parker
 */
public class MEDLetterDialog extends javax.swing.JDialog {

    public MEDLetterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadDropDowns();
        addListeners();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void loadDropDowns() {
        loadLetters();
        loadDirectives();
        loadAgenda();
    }

    private void addListeners() {
        letterComboBox.addItemListener((ItemEvent e) -> {
            directiveComboBox.setSelectedItem(new Item<>("0", ""));
            memoComboBox.setSelectedItem(new Item<>("0", ""));
            enableGenerateButton();
        });

        directiveComboBox.addItemListener((ItemEvent e) -> {
            letterComboBox.setSelectedItem(new Item<>("0", ""));
            memoComboBox.setSelectedItem(new Item<>("0", ""));
            enableGenerateButton();
        });

        memoComboBox.addItemListener((ItemEvent e) -> {
            directiveComboBox.setSelectedItem(new Item<>("0", ""));
            letterComboBox.setSelectedItem(new Item<>("0", ""));
            enableGenerateButton();
        });
    }

    private void enableGenerateButton() {
        if(letterComboBox.getSelectedItem().toString().equals("")
                && directiveComboBox.getSelectedItem().toString().equals("")
                && memoComboBox.getSelectedItem().toString().equals("")) {
            generateButton.setEnabled(false);
        } else {
            generateButton.setEnabled(true);
        }
    }

    private void loadLetters() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        letterComboBox.setModel(dt);
        letterComboBox.addItem(new Item<>("0", ""));

        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("MED", "Letter");
        for (SMDSDocuments letter : letterList) {
            letterComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
        }
        letterComboBox.setSelectedItem(new Item<>("0", ""));
    }

    private void loadDirectives() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        directiveComboBox.setModel(dt);
        directiveComboBox.addItem(new Item<>("0", ""));

        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("MED", "Directive");
        for (SMDSDocuments letter : letterList) {
            directiveComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
        }
        directiveComboBox.setSelectedItem(new Item<>("0", ""));
    }

    private void loadAgenda() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        memoComboBox.setModel(dt);
        memoComboBox.addItem(new Item<>("0", ""));

        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("MED", "Memo");
        for (SMDSDocuments letter : letterList) {
            memoComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
        }
        memoComboBox.setSelectedItem(new Item<>("0", ""));
    }


    private void generateDocument() {
        int selection = 0;
        if (!letterComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) letterComboBox.getSelectedItem();
            selection = Integer.parseInt(item.getValue().toString());
        } else if (!directiveComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) directiveComboBox.getSelectedItem();
            selection = Integer.parseInt(item.getValue().toString());
        } else if (!memoComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) memoComboBox.getSelectedItem();
            selection = Integer.parseInt(item.getValue().toString());
        }

        if (selection > 0) {
            SMDSDocuments template = SMDSDocuments.findDocumentByID(selection);
            File templateFile = new File(Global.templatePath + Global.activeSection + File.separator + template.fileName);

            if (templateFile.exists()){
                new LetterGenerationPanel(Global.root, true, template, null);
            } else {
                WebOptionPane.showMessageDialog(Global.root, "<html><center> Sorry, unable to locate template. <br><br>" + template.fileName + "</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
            }
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        letterComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        directiveComboBox = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        memoComboBox = new javax.swing.JComboBox();
        generateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Letter");

        jLabel2.setText("Letters");

        jLabel3.setText("Directives");

        jLabel5.setText("Memo");

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(12, 12, 12)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(letterComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 443, Short.MAX_VALUE)
                    .addComponent(directiveComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(memoComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(generateButton))
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(12, 12, 12))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(letterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(directiveComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memoComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 20, Short.MAX_VALUE)
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
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox directiveComboBox;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JComboBox letterComboBox;
    private javax.swing.JComboBox memoComboBox;
    // End of variables declaration//GEN-END:variables
}
