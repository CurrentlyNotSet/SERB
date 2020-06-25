/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.employer;

import com.alee.laf.optionpane.WebOptionPane;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.County;
import parker.serb.sql.Employer;
import parker.serb.sql.Jurisdiction;

/**
 *
 * @author parkerjohnston
 */
public class employerDetailAdd extends javax.swing.JDialog {

    String empIDNumber;
    String empName;
    /**
     * Creates new form employerDetail
     * @param parent
     * @param modal
     * @param employerID
     */
    public employerDetailAdd(java.awt.Frame parent, boolean modal, String employerID) {
        super(parent, modal);
        initComponents();
        loadComboBoxes();
        if (!employerID.isEmpty()){
            loadInformation(employerID);
            checkButton();
        }
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void loadComboBoxes() { 
        loadCountyComboBox();
        loadJurisdictionComboBox();
        loadEmployerTypeComboBox();
    }
    
    private void loadCountyComboBox() {
        List<String> countyList = County.loadCountyList();
        
        countyComboBox.removeAllItems();
        countyComboBox.addItem("");
        
        for (Object singleCounty : countyList) {
            County county = (County) singleCounty;
            countyComboBox.addItem(county.countyName);
        }
    }
    
    private void loadJurisdictionComboBox() {
        List<String> jurisdictionList = Jurisdiction.loadDistinctJurisdictionList();
        
        jurisdictionComboBox.removeAllItems();
        jurisdictionComboBox.addItem("");
        
        for (Object singleJurisdition : jurisdictionList) {
            Jurisdiction juris = (Jurisdiction) singleJurisdition;
            jurisdictionComboBox.addItem(juris.jurisCode);
        }
    }
    
    private void loadEmployerTypeComboBox() {
        
        EmployerTypeCodeComboBox.removeAllItems();
        EmployerTypeCodeComboBox.addItem("");
        
        for (String type : Global.employerTypeCodes) {
            EmployerTypeCodeComboBox.addItem(type);
        }
    }
        
    private void loadInformation(String employerID) {
        Employer emp = Employer.loadEmployerByID(employerID);
        
        employerNameTextBox.setText(emp.employerName);
        idNumberTextBox.setText(emp.employerIDNumber);
        countyComboBox.setSelectedItem(emp.county);
        jurisdictionComboBox.setSelectedItem(emp.jurisdiction);
        EmployerTypeCodeComboBox.setSelectedItem(emp.employerTypeCode);        
    }
    
    private void saveInformation() {
        Employer emp = new Employer();

        //Test and Add leading Zeros
        int idLength = idNumberTextBox.getText().trim().length();
        switch (idLength) {
            case 1:
                emp.employerIDNumber = "000" + idNumberTextBox.getText().trim();
                break;
            case 2:
                emp.employerIDNumber = "00" + idNumberTextBox.getText().trim();
                break;
            case 3:
                emp.employerIDNumber = "0" + idNumberTextBox.getText().trim();
                break;
            default:
                emp.employerIDNumber = idNumberTextBox.getText().trim();
                break;
        }

        emp.employerName = employerNameTextBox.getText().trim();
        emp.county = countyComboBox.getSelectedItem().toString();
        emp.jurisdiction = jurisdictionComboBox.getSelectedItem().toString();
        emp.employerType = 2;
        emp.employerTypeCode = EmployerTypeCodeComboBox.getSelectedItem().toString();

        String exists = Employer.getEmployerNameByID(emp.employerIDNumber);
        
        if (!exists.isEmpty()){
            WebOptionPane.showMessageDialog(Global.root, "<html><center>The Employer ID: " + emp.employerIDNumber + " Is currently in use.<br><br>Please Choose Another Employer ID</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
        } else {
            Employer.createEmployer(emp);
            empName = employerNameTextBox.getText();
            empIDNumber = idNumberTextBox.getText();
            setVisible(false);
        }
    }
    
    private void checkButton(){
        if (employerNameTextBox.getText().trim().equals("") || 
                idNumberTextBox.getText().trim().equals("") || 
                countyComboBox.getSelectedItem().toString().trim().equals("") || 
                jurisdictionComboBox.getSelectedItem().toString().trim().equals("") || 
                EmployerTypeCodeComboBox.getSelectedItem().toString().trim().equals("")){
            jButton2.setEnabled(false);
        } else {
            jButton2.setEnabled(true);
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

        jRadioButton1 = new javax.swing.JRadioButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        employerNameTextBox = new javax.swing.JTextField();
        idNumberTextBox = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        countyComboBox = new javax.swing.JComboBox<>();
        jLabel12 = new javax.swing.JLabel();
        jurisdictionComboBox = new javax.swing.JComboBox<>();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel24 = new javax.swing.JLabel();
        EmployerTypeCodeComboBox = new javax.swing.JComboBox<>();

        jRadioButton1.setText("jRadioButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Add Employer");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Employer Name:");

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Employer ID Number:");

        employerNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        employerNameTextBox.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                employerNameTextBoxCaretUpdate(evt);
            }
        });

        idNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        idNumberTextBox.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                idNumberTextBoxCaretUpdate(evt);
            }
        });

        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("County:");

        countyComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                countyComboBoxActionPerformed(evt);
            }
        });

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("Jurisdiction:");

        jurisdictionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jurisdictionComboBoxActionPerformed(evt);
            }
        });

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

        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel24.setText("Employer Type Code:");
        jLabel24.setMaximumSize(new java.awt.Dimension(140, 14));
        jLabel24.setMinimumSize(new java.awt.Dimension(140, 14));
        jLabel24.setPreferredSize(new java.awt.Dimension(140, 14));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 115, Short.MAX_VALUE)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addComponent(employerNameTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jurisdictionComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, 402, Short.MAX_VALUE)
                                    .addComponent(countyComboBox, javax.swing.GroupLayout.Alignment.LEADING, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(idNumberTextBox)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(EmployerTypeCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 402, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel11, jLabel12, jLabel2, jLabel24, jLabel7});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(22, 22, 22)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(idNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(employerNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(countyComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jurisdictionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel12))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EmployerTypeCodeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 41, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jButton2)
                    .addComponent(jButton1))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {countyComboBox, employerNameTextBox, idNumberTextBox, jLabel11, jLabel12, jLabel2, jLabel24, jLabel7, jurisdictionComboBox});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        saveInformation();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void countyComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_countyComboBoxActionPerformed
        checkButton();
    }//GEN-LAST:event_countyComboBoxActionPerformed

    private void jurisdictionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jurisdictionComboBoxActionPerformed
        checkButton();
    }//GEN-LAST:event_jurisdictionComboBoxActionPerformed

    private void employerNameTextBoxCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_employerNameTextBoxCaretUpdate
        checkButton();
    }//GEN-LAST:event_employerNameTextBoxCaretUpdate

    private void idNumberTextBoxCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_idNumberTextBoxCaretUpdate
        checkButton();
    }//GEN-LAST:event_idNumberTextBoxCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> EmployerTypeCodeComboBox;
    private javax.swing.JComboBox<String> countyComboBox;
    private javax.swing.JTextField employerNameTextBox;
    private javax.swing.JTextField idNumberTextBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JComboBox<String> jurisdictionComboBox;
    // End of variables declaration//GEN-END:variables
}
