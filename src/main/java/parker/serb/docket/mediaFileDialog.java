/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.docket;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.ActivityType;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CaseNumber;
import parker.serb.sql.MEDCase;
import parker.serb.sql.REPCase;
import parker.serb.sql.ULPCase;
import parker.serb.sql.User;
import parker.serb.util.FileService;

/**
 *
 * @author parkerjohnston
 */
public class mediaFileDialog extends javax.swing.JDialog {

    String selectedSection = "";
    /**
     * Creates new form scanFileDialog
     */
    public mediaFileDialog(java.awt.Frame parent, boolean modal, String file, String section) {
        super(parent, modal);
        initComponents();
        selectedSection = section;
        loadData(section, file);
        addListeners();
        setLocationRelativeTo(parent);
        setVisible(true); 
    }
    
    private void loadData(String section, String file) {
        fileNameTextBox.setText(file);
        loadToComboBox();
        loadTypeComboBox();
    }
    
    private void addListeners() {
        caseNumberTextBox.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {}

            @Override
            public void focusLost(FocusEvent e) {
                validateCaseNumber();
            }
        });
        
        caseNumberTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableButton();
            }
            
            @Override
            public void removeUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableButton();
            }
         });
         
        fromTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                enableButton();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                enableButton();
            }
        });
         
        toComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                enableButton();
            }
        });
         
        typeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               enableButton();
            }
        });
        
        fileNameTextBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    FileService.openScanFile(fileNameTextBox.getText().trim(), selectedSection);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }
    
    private void enableButton() {
        if(caseNumberTextBox.getText().equals("")
                || fromTextBox.getText().equals("")
                || toComboBox.getSelectedItem().toString().equals("")
                || typeComboBox.getSelectedItem().toString().equals("")) {
            fileButton.setEnabled(false);
        } else {
            fileButton.setEnabled(true);
        }
    }
    
    private void loadToComboBox() {
        List userList = User.loadSectionDropDowns(selectedSection);
        
        toComboBox.setMaximumRowCount(6);
        
        toComboBox.removeAllItems();
        
        toComboBox.addItem("");
        
        for(int i = 0; i < userList.size(); i++) {
            toComboBox.addItem(userList.get(i).toString());
        }
        
        
    }
    
    private void loadTypeComboBox() {
        List typeList = ActivityType.loadAllActivityTypeBySection(selectedSection);
        
        typeComboBox.setMaximumRowCount(10);
        
        typeComboBox.removeAllItems();
        
        typeComboBox.addItem("");
        
        for(int i = 0; i < typeList.size(); i++) {
            ActivityType item = (ActivityType) typeList.get(i);
            typeComboBox.addItem(item.descriptionAbbrv + " - " + item.descriptionFull);
        }
    }
    
    private void validateCaseNumber() {
        String[] caseNumbers = caseNumberTextBox.getText().split(",");
        
        String caseNumberFail = "";
        
        switch(selectedSection) {
            case "ULP":
                caseNumberFail = CaseNumber.validateULPCaseNumber(caseNumbers);
                break;
            case "REP":
                caseNumberFail = CaseNumber.validateREPCaseNumber(caseNumbers);
                break;
            case "MED":
                caseNumberFail = CaseNumber.validateMEDCaseNumber(caseNumbers);
                break;
            case "ORG":
                caseNumberFail = CaseNumber.validateORGCaseNumber(caseNumbers);
                break;
            case "CMDS":
                caseNumberFail = CaseNumber.validateCMDSCaseNumber(caseNumbers);
                break;
            case "CSC":
                caseNumberFail = CaseNumber.validateCSCCaseNumber(caseNumbers);
                break;
        }   
        
        if(!caseNumberFail.equals("")) {
            new docketingCaseNotFound((JFrame) Global.root.getRootPane().getParent(), true, caseNumberFail);
            caseNumberTextBox.requestFocus();
        }
        
        if(!caseNumberTextBox.getText().equals("")) {
            switch (selectedSection) {
                case "ULP":  
                    toComboBox.setSelectedItem(ULPCase.DocketTo(caseNumberTextBox.getText()));
                    break;
                case "REP":
                    toComboBox.setSelectedItem(REPCase.DocketTo(caseNumberTextBox.getText()));
                    break;
                case "MED":
                    toComboBox.setSelectedItem("Mary Laurent");
                    break;
                case "ORG":
//                    toComboBox.setSelectedItem(ORGCase.DocketTo(caseNumberTextBox.getText()));
                    break;
                case "CMDS":
                    toComboBox.setSelectedItem(CMDSCase.DocketTo(caseNumberTextBox.getText()));
                    break;
            }
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
        fileNameTextBox = new javax.swing.JTextField();
        fromTextBox = new javax.swing.JTextField();
        toComboBox = new javax.swing.JComboBox<>();
        typeComboBox = new javax.swing.JComboBox<>();
        cancelButton = new javax.swing.JButton();
        fileButton = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        caseNumberTextBox = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        commentTextBox = new javax.swing.JTextArea();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Media Filing");

        jLabel2.setText("File Name:");

        jLabel3.setText("From:");

        jLabel4.setText("To:");

        jLabel5.setText("Type:");

        jLabel6.setText("Comment:");

        fileNameTextBox.setEditable(false);
        fileNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fileNameTextBox.setEnabled(false);

        toComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        typeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        cancelButton.setText("Cancel");
        cancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                cancelButtonActionPerformed(evt);
            }
        });

        fileButton.setText("File");
        fileButton.setEnabled(false);
        fileButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                fileButtonActionPerformed(evt);
            }
        });

        jLabel8.setText("Case Number(s):");

        commentTextBox.setColumns(20);
        commentTextBox.setLineWrap(true);
        commentTextBox.setRows(5);
        jScrollPane1.setViewportView(commentTextBox);

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
                            .addComponent(jLabel2)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(caseNumberTextBox)
                            .addComponent(fileNameTextBox)
                            .addComponent(fromTextBox)
                            .addComponent(toComboBox, 0, 279, Short.MAX_VALUE)
                            .addComponent(typeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(fileNameTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(fromTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(toComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(typeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(16, 16, 16)
                        .addComponent(jLabel6))
                    .addGroup(layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 102, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(cancelButton)
                    .addComponent(fileButton))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        // TODO add your handling code here:
        String[] caseNumbers = caseNumberTextBox.getText().trim().split(",");
        FileService.docketMedia(caseNumbers,
                fileNameTextBox.getText(),
                selectedSection,
                typeComboBox.getSelectedItem().toString().split("-")[0].trim(),
                typeComboBox.getSelectedItem().toString().split("-")[1].trim(),
                fromTextBox.getText(),
                toComboBox.getSelectedItem().toString(),
                commentTextBox.getText());
        dispose();
    }//GEN-LAST:event_fileButtonActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JTextArea commentTextBox;
    private javax.swing.JButton fileButton;
    private javax.swing.JTextField fileNameTextBox;
    private javax.swing.JTextField fromTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> toComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
