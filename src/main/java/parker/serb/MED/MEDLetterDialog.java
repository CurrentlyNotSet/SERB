/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

//TODO: Load all of the letter types

import java.awt.event.ItemEvent;
import java.util.List;
import parker.serb.Global;
import parker.serb.letterGeneration.LetterGenerationPanel;
import parker.serb.sql.SMDSDocuments;


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
            directiveComboBox.setSelectedItem("");
            memoComboBox.setSelectedItem("");
            enableGenerateButton();
        });

        directiveComboBox.addItemListener((ItemEvent e) -> {
            letterComboBox.setSelectedItem("");
            memoComboBox.setSelectedItem("");
            enableGenerateButton();
        });
        
        memoComboBox.addItemListener((ItemEvent e) -> {
            directiveComboBox.setSelectedItem("");
            letterComboBox.setSelectedItem("");
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
        letterComboBox.removeAllItems();
        letterComboBox.addItem("");
        
        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("MED", "Letter");
        for (SMDSDocuments letter : letterList) {
            letterComboBox.addItem(letter.description);
        }
        letterComboBox.setSelectedItem("");
    }
    
    private void loadDirectives() {
        directiveComboBox.removeAllItems();
        directiveComboBox.addItem("");
        
        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("MED", "Directive");
        for (SMDSDocuments letter : letterList) {
            directiveComboBox.addItem(letter.description);
        }
        directiveComboBox.setSelectedItem("");
    }
    
    private void loadAgenda() {
        memoComboBox.removeAllItems();
        memoComboBox.addItem("");
        
        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("MED", "Memo");
        for (SMDSDocuments letter : letterList) {
            memoComboBox.addItem(letter.description);
        }
        memoComboBox.setSelectedItem("");
    }
    
    
    private void generateDocument() {
        String selection = "";
        if (!letterComboBox.getSelectedItem().toString().trim().equals("")) {
            selection = letterComboBox.getSelectedItem().toString().trim();
        } else if (!directiveComboBox.getSelectedItem().toString().trim().equals("")) {
            selection = directiveComboBox.getSelectedItem().toString().trim();
        } else if (!memoComboBox.getSelectedItem().toString().trim().equals("")) {
            selection = memoComboBox.getSelectedItem().toString().trim();
        }

        if (!"".equals(selection)) {
            SMDSDocuments template = SMDSDocuments.findDocumentByDescription(selection);
                     
            new LetterGenerationPanel(Global.root, true, template);
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

        letterComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel3.setText("Directives");

        directiveComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel5.setText("Memo");

        memoComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 325, Short.MAX_VALUE)
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
