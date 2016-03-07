/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ULP;

import java.awt.Color;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.ULPCase;
import parker.serb.sql.ULPRecommendation;

/**
 *
 * @author parkerjohnston
 */
public class ULPRecommendationPanel extends javax.swing.JPanel {

    String orginalRec = "";
    /**
     * Creates new form ULPInvestigationReveals
     */
    public ULPRecommendationPanel() {
        initComponents();
        recommendationComboBox.setVisible(false);
        addRecButton.setVisible(false);
    }
    
    public void clearAll() {
        jTextArea1.setText("");
    }
    
    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);
        
        recommendationComboBox.setVisible(true);
        addRecButton.setVisible(true);
        jTextArea1.setEnabled(true);
        jTextArea1.setBackground(Color.white);
        
        orginalRec = jTextArea1.getText();
        
    }
    
    private void loadRecComboBox() {
        recommendationComboBox.removeAllItems();
        
        recommendationComboBox.addItem("");
        
        List recommendationList = ULPRecommendation.loadAllULPRecommendations();
        
        for (Object recommendation : recommendationList) {
            ULPRecommendation rec = (ULPRecommendation) recommendation;
            recommendationComboBox.addItem(rec.code + " - " + rec.description);
        }
    }
    
    public void disableUpdate() {
        saveInformation();
        
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);
        
        jTextArea1.setBackground(new Color(238,238,238));
        jTextArea1.setEnabled(false);
        
        recommendationComboBox.setSelectedItem("");
        
        recommendationComboBox.setVisible(false);
        addRecButton.setVisible(false);
        
    }
    
    public void loadInformation() {
        loadRecComboBox();
        jTextArea1.setText(ULPCase.loadRecommendation());
    }
    
    public void saveInformation() {
        ULPCase.updateRecommendation(jTextArea1.getText(), orginalRec);
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
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        recommendationComboBox = new javax.swing.JComboBox<String>();
        addRecButton = new javax.swing.JButton();

        jLabel1.setText("Recommendation:");

        jTextArea1.setBackground(new java.awt.Color(238, 238, 238));
        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea1.setEnabled(false);
        jScrollPane1.setViewportView(jTextArea1);

        recommendationComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        addRecButton.setText("Add");
        addRecButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addRecButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(recommendationComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addRecButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(recommendationComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(addRecButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 231, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void addRecButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRecButtonActionPerformed
        System.out.println("add item");
        jTextArea1.setText(recommendationComboBox.getSelectedItem().toString());
    }//GEN-LAST:event_addRecButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addRecButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JComboBox<String> recommendationComboBox;
    // End of variables declaration//GEN-END:variables
}
