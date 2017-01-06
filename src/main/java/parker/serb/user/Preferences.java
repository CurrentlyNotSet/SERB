/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.user;

import java.awt.Color;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.login.ResetPasswordDialog;
import parker.serb.sql.User;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class Preferences extends javax.swing.JDialog {

    /**
     * Creates new form Preferences
     */
    public Preferences(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addListeners();
        loadInformation();
        setDisabled();
        getRootPane().setDefaultButton(jButton2);
        jButton2.requestFocus(true);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void addListeners() {
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateEntry();
            }
        });
        
        jTextField2.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateEntry();
            }
        });
        
        jTextField3.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateEntry();
            }
        });
        
        jTextField4.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateEntry();
            }
        });
        
        jTextField5.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateEntry();
            }
        });
        
        jTextField6.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                validateEntry();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                validateEntry();
            }
        });
    }
    
    private void validateEntry() {
        if(jTextField1.getText().trim().equals("") ||
                jTextField2.getText().trim().equals("") ||
                jTextField3.getText().trim().equals("") ||
                jTextField4.getText().trim().equals("") ||
                jTextField5.getText().trim().equals("") ||
                jTextField6.getText().trim().equals("") ||
                !jTextField6.getText().matches("(\\(\\d{3}\\)) \\d{3}-\\d{4}")) {
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
        }
    }
    
    private void setDisabled() {
        jTextField1.setEditable(false);
        jTextField1.setBackground(new Color(238,238,238));
        jTextField2.setEditable(false);
        jTextField2.setBackground(new Color(238,238,238));
        jTextField3.setEditable(false);
        jTextField3.setBackground(new Color(238,238,238));
        jTextField4.setEditable(false);
        jTextField4.setBackground(new Color(238,238,238));
        jTextField5.setEditable(false);
        jTextField5.setBackground(new Color(238,238,238));
        jTextField6.setEditable(false);
        jTextField6.setBackground(new Color(238,238,238));
        defaultSection.setEnabled(false);
    }
    
    private void setEditable() {
        jTextField1.setEditable(true);
        jTextField1.setBackground(Color.WHITE);
        jTextField2.setEditable(true);
        jTextField2.setBackground(Color.WHITE);
        jTextField3.setEditable(true);
        jTextField3.setBackground(Color.WHITE);
        jTextField4.setEditable(true);
        jTextField4.setBackground(Color.WHITE);
        jTextField5.setEditable(true);
        jTextField5.setBackground(Color.WHITE);
        jTextField6.setEditable(true);
        jTextField6.setBackground(Color.WHITE);
        defaultSection.setEnabled(true);
    }
    
    private void loadInformation() {
        jTextField1.setText(Global.activeUser.firstName);
        jTextField2.setText(Global.activeUser.middleInitial);
        jTextField3.setText(Global.activeUser.lastName);
        jTextField4.setText(Global.activeUser.username);
        jTextField5.setText(Global.activeUser.emailAddress);
        jTextField6.setText(NumberFormatService.convertStringToPhoneNumber(Global.activeUser.workPhone));
        loadDefaultSectionComboBox();
    }
    
    private void loadDefaultSectionComboBox() {
        defaultSection.removeAllItems();
        
        for(int i =0; i < Global.root.getjTabbedPane1().getTabCount(); i++) {
            defaultSection.addItem(Global.root.getjTabbedPane1().getTitleAt(i));
        }
        
        defaultSection.setSelectedItem(Global.activeUser.defaultSection);
    }
    
    private void updateInformation() {
        User user = new User();
        user.firstName = jTextField1.getText().trim();
        user.middleInitial = jTextField2.getText().trim();
        user.lastName = jTextField3.getText().trim();
        user.username = jTextField4.getText().trim();
        user.emailAddress = jTextField5.getText().trim();
        user.workPhone = NumberFormatService.convertPhoneNumberToString(jTextField6.getText().trim());
        user.defaultSection = defaultSection.getSelectedItem().toString();
        User.updateUserPrefs(user);
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel6 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jTextField2 = new javax.swing.JTextField();
        jTextField3 = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        jTextField5 = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jTextField6 = new javax.swing.JTextField();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        defaultSection = new javax.swing.JComboBox<>();

        jLabel6.setText("jLabel6");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Preferences");

        jLabel2.setText("Name:");

        jTextField1.setEditable(false);

        jTextField2.setEditable(false);

        jTextField3.setEditable(false);

        jLabel3.setText("Username:");

        jButton1.setText("Change Password");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Email:");

        jLabel5.setText("Phone:");

        jButton2.setText("Update");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("Cancel");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jLabel7.setText("(###) ###-####");

        jLabel8.setText("Default Section:");

        defaultSection.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        defaultSection.setEnabled(false);

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
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextField3, javax.swing.GroupLayout.DEFAULT_SIZE, 128, Short.MAX_VALUE))
                            .addComponent(jTextField4)
                            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jTextField5)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jTextField6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel7))
                            .addComponent(defaultSection, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(defaultSection)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton3))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jButton2.getText().equals("Update")) {
            jButton2.setText("Save");
            setEditable();
        } else if(jButton2.getText().equals("Save")) {
            jButton2.setText("Update");
            setDisabled();
            updateInformation();
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new ResetPasswordDialog((JFrame) Global.root.getRootPane().getParent(), true, false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton3ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> defaultSection;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    // End of variables declaration//GEN-END:variables
}
