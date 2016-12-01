/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import java.sql.Timestamp;
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class CMDSWhichGreenCardDialog extends javax.swing.JDialog {

    String whichType;
    Timestamp signedDate;
    Timestamp pullDate;
    
    /**
     * Creates new form CMDSWhichGreenCardDialog
     */
    public CMDSWhichGreenCardDialog(java.awt.Dialog parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    public String getWhichType() {
        return whichType;
    }

    public Timestamp getSignedDate() {
        return signedDate;
    }

    public Timestamp getPullDate() {
        return pullDate;
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
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        responseDueDateTextBox = new com.alee.extended.date.WebDateField();
        responseDueDateTextBox1 = new com.alee.extended.date.WebDateField();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Green Card Information");

        jLabel2.setText("Which Green Card:");

        jLabel3.setText("Signed Date:");

        jLabel4.setText("Pull Date:");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BO", "RR", "PO1", "PO2", "PO3", "PO4", " " }));
        jComboBox1.setSelectedIndex(6);

        responseDueDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        responseDueDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        responseDueDateTextBox.setDateFormat(Global.mmddyyyy);
        responseDueDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                responseDueDateTextBoxMouseClicked(evt);
            }
        });

        responseDueDateTextBox1.setCaretColor(new java.awt.Color(0, 0, 0));
        responseDueDateTextBox1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        responseDueDateTextBox.setDateFormat(Global.mmddyyyy);
        responseDueDateTextBox1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                responseDueDateTextBox1MouseClicked(evt);
            }
        });

        jButton1.setText("OK");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 246, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(responseDueDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(responseDueDateTextBox1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(responseDueDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(responseDueDateTextBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButton1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void responseDueDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_responseDueDateTextBoxMouseClicked

    }//GEN-LAST:event_responseDueDateTextBoxMouseClicked

    private void responseDueDateTextBox1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_responseDueDateTextBox1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_responseDueDateTextBox1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private com.alee.extended.date.WebDateField responseDueDateTextBox;
    private com.alee.extended.date.WebDateField responseDueDateTextBox1;
    // End of variables declaration//GEN-END:variables
}