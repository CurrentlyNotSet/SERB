/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.party;

import java.util.List;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.CaseParty;
import parker.serb.sql.PartyType;


/**
 *
 * @author parker
 */
public class PartyTypeSelectionPanel extends javax.swing.JDialog {

    String id;
    String name;
    boolean selected = false;
    /**
     * Creates new form PartyTypeSelectionPanel
     */
    public PartyTypeSelectionPanel(java.awt.Frame parent, boolean modal, String passedName, String passedID) {
        super(parent, modal);
        initComponents();
        id = passedID;
        name = passedName;
        setLabel(name);
        loadPartyTypes();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void setLabel(String name) {
        jLabel1.setText("Please select a party type for " + name);
    }

    private void loadPartyTypes() {
        jComboBox1.removeAllItems();
        List types = null;

        if(Global.activeSection.equals("Hearings")) {
            types = PartyType.loadAllPartyTypesForHearings();
        } else {
            types = PartyType.loadAllPartyTypesBySection();
        }



        for(Object type: types) {
            jComboBox1.addItem(type.toString());
        }
    }

    public boolean isSelected() {
        return selected;
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel2 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jLabel1 = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel2.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Party Type");

        jButton1.setText("Add Party");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Cancel");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jScrollPane1.setBorder(null);

        jLabel1.setEditable(false);
        jLabel1.setBackground(new java.awt.Color(238, 238, 238));
        jLabel1.setColumns(20);
        jLabel1.setLineWrap(true);
        jLabel1.setRows(2);
        jLabel1.setWrapStyleWord(true);
        jLabel1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jLabel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 396, Short.MAX_VALUE)
                    .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        selected = true;
        CaseParty.createParty(id, jComboBox1.getSelectedItem().toString().trim());
        Activity.addActivty("Added " + name + " (" + jComboBox1.getSelectedItem().toString().trim() + ")", null);
        if((Global.activeSection.contains("ULP"))
                && !jComboBox1.getSelectedItem().toString().trim().contains("REP")
                && !jComboBox1.getSelectedItem().toString().trim().contains("Complainant")) {
            setVisible(false);
            new ProSeSelectionPanel((JFrame) Global.root.getParent(), rootPaneCheckingEnabled, jComboBox1.getSelectedItem().toString().trim(), id, name);
        }
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        selected = false;
        setVisible(false);
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JTextArea jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
