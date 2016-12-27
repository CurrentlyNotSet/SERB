/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import com.alee.extended.date.WebDateField;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.sql.REPElectionSiteInformation;
import parker.serb.util.ClearDateDialog;

/**
 *
 * @author parkerjohnston
 */
public class AddSiteElectionDialog extends javax.swing.JDialog {

    /**
     * Creates new form AddSiteElectionDialog
     */
    public AddSiteElectionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void clearDate(WebDateField dateField, MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON3 && dateField.isEnabled()) {
            ClearDateDialog dialog = new ClearDateDialog((JFrame) Global.root, true);
            if (dialog.isReset()) {
                dateField.setText("");
            }
            dialog.dispose();
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

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        placeTextBox = new javax.swing.JTextField();
        addressTextBox = new javax.swing.JTextField();
        address2TextBox = new javax.swing.JTextField();
        locationTextBox = new javax.swing.JTextField();
        startTimeTextBox = new javax.swing.JTextField();
        startTimeAMPM = new javax.swing.JComboBox<>();
        endTimeTextBox = new javax.swing.JTextField();
        endTimeAMPM = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        siteDateTextBox = new com.alee.extended.date.WebDateField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add On-Site Election");

        jLabel2.setText("Date:");

        jLabel3.setText("Place:");

        jLabel4.setText("Address:");

        jLabel5.setText("Address:");

        jLabel6.setText("Location:");

        jLabel7.setText("Start Time:");

        jLabel8.setText("End Time:");

        startTimeTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                startTimeTextBoxKeyTyped(evt);
            }
        });

        startTimeAMPM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM", " " }));
        startTimeAMPM.setSelectedIndex(2);

        endTimeTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                endTimeTextBoxKeyTyped(evt);
            }
        });

        endTimeAMPM.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM", " " }));
        endTimeAMPM.setSelectedIndex(2);

        jButton1.setText("Cancel");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("Save");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        siteDateTextBox.setEditable(false);
        siteDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        siteDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        siteDateTextBox.setDateFormat(Global.mmddyyyy);
        siteDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                siteDateTextBoxMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(placeTextBox)
                            .addComponent(addressTextBox)
                            .addComponent(address2TextBox)
                            .addComponent(locationTextBox)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(endTimeTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(startTimeTextBox))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(startTimeAMPM, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(endTimeAMPM, 0, 1, Short.MAX_VALUE)))
                            .addComponent(siteDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 148, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(siteDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(placeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(addressTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(address2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(locationTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(startTimeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(startTimeAMPM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(endTimeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(endTimeAMPM, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 26, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton1)
                    .addComponent(jButton2))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void siteDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_siteDateTextBoxMouseClicked
        clearDate(siteDateTextBox, evt);
    }//GEN-LAST:event_siteDateTextBoxMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void startTimeTextBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_startTimeTextBoxKeyTyped
        if (startTimeTextBox.getText().equals("") || !startTimeTextBox.getText().contains(":")) {
            startTimeAMPM.setSelectedItem(" ");
        } else {
            startTimeAMPM.setSelectedItem(Integer.parseInt(startTimeTextBox.getText().split(":")[0]) >= 7 && Integer.parseInt(startTimeTextBox.getText().split(":")[0]) <= 11 ? "AM" : "PM");
        }
    }//GEN-LAST:event_startTimeTextBoxKeyTyped

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        REPElectionSiteInformation.addSiteLocation(
                siteDateTextBox.getText().trim(),
                placeTextBox.getText().trim(),
                addressTextBox.getText().trim(),
                address2TextBox.getText().trim(),
                locationTextBox.getText().trim(),
                startTimeTextBox.getText().trim(),
                startTimeAMPM.getSelectedItem().toString(),
                endTimeTextBox.getText().trim(),
                endTimeAMPM.getSelectedItem().toString()
        );
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void endTimeTextBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_endTimeTextBoxKeyTyped
        if (endTimeTextBox.getText().equals("")) {
            endTimeAMPM.setSelectedItem(" ");
        } else {
            endTimeAMPM.setSelectedItem(Integer.parseInt(endTimeTextBox.getText().split(":")[0]) >= 7 && Integer.parseInt(endTimeTextBox.getText().split(":")[0]) <= 11 ? "AM" : "PM");
        }
    }//GEN-LAST:event_endTimeTextBoxKeyTyped

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField address2TextBox;
    private javax.swing.JTextField addressTextBox;
    private javax.swing.JComboBox<String> endTimeAMPM;
    private javax.swing.JTextField endTimeTextBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField locationTextBox;
    private javax.swing.JTextField placeTextBox;
    private com.alee.extended.date.WebDateField siteDateTextBox;
    private javax.swing.JComboBox<String> startTimeAMPM;
    private javax.swing.JTextField startTimeTextBox;
    // End of variables declaration//GEN-END:variables
}
