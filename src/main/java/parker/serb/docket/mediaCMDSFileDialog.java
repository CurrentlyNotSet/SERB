/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.docket;

import java.awt.event.ActionEvent;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import parker.serb.Global;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CMDSHistoryCategory;
import parker.serb.sql.CMDSHistoryDescription;
import parker.serb.sql.CaseNumber;
import parker.serb.sql.User;
import parker.serb.util.FileService;

/**
 *
 * @author parkerjohnston
 */
public class mediaCMDSFileDialog extends javax.swing.JDialog {

    String selectedSection = "";
    boolean orgProcess = false;
    /**
     * Creates new form scanFileDialog
     * @param parent
     * @param modal
     * @param file
     * @param section
     */
    public mediaCMDSFileDialog(java.awt.Frame parent, boolean modal, String file, String section) {
        super(parent, modal);
        initComponents();
        selectedSection = section;
        loadData(section, file);
        addListeners();
        this.pack();
        setLocationRelativeTo(parent);
        setVisible(true);
    }



    private void loadData(String section, String file) {
        fileNameTextBox.setText(file);
        loadToComboBox(section);
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

        toComboBox.addActionListener((ActionEvent e) -> {
            enableButton();
        });

        typeComboBox.addActionListener((ActionEvent e) -> {
            if (!typeComboBox.getSelectedItem().toString().equals("")){
                loadType2ComboBox();
            }
        });

        descriptionComboBox.addActionListener((ActionEvent e) -> {
            if (descriptionComboBox != null){
                enableButton();
            } else {
                fileButton.setEnabled(false);
            }
        });

        fileNameTextBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    FileService.openMediaFile(fileNameTextBox.getText().trim(), selectedSection);
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
                || typeComboBox.getSelectedItem().toString().equals("")
                || descriptionComboBox.getSelectedItem().toString().equals("")) {
            fileButton.setEnabled(false);
        } else {
            fileButton.setEnabled(true);
        }
    }

    private void loadToComboBox(String section) {
        List userList = null;
        userList = User.loadSectionDropDowns(section);
        toComboBox.setMaximumRowCount(6);
        toComboBox.removeAllItems();
        toComboBox.addItem("");

        for(int i = 0; i < userList.size(); i++) {
            toComboBox.addItem(userList.get(i).toString());
        }
    }

    private void loadTypeComboBox() {

        List<CMDSHistoryCategory> entryTypes = CMDSHistoryCategory.loadActiveCMDSHistoryDescriptions();

        typeComboBox.setMaximumRowCount(10);
        typeComboBox.removeAllItems();
        typeComboBox.addItem("");

        for (int i = 0; i < entryTypes.size(); i++) {
            typeComboBox.addItem(entryTypes.get(i).entryType + " - " + entryTypes.get(i).description);
        }
    }

    private void loadType2ComboBox() {

        List<CMDSHistoryDescription> entryTypes = CMDSHistoryDescription.loadAllStatusTypes(typeComboBox.getSelectedItem().toString().split("-")[0].trim());

        descriptionComboBox.setMaximumRowCount(10);
        descriptionComboBox.removeAllItems();
        descriptionComboBox.addItem("");

        for (int i = 0; i < entryTypes.size(); i++) {
            descriptionComboBox.addItem(entryTypes.get(i).description);
        }
    }

    private void validateCaseNumber() {
        String[] caseNumbers = caseNumberTextBox.getText().split(",");
        String caseNumberFail = "";

        switch(selectedSection) {
            case "CMDS":
                caseNumberFail = CaseNumber.validateCMDSCaseNumber(caseNumbers);
                break;
        }

        if(!caseNumberFail.equals("")) {
            new docketingCaseNotFound((JFrame) Global.root.getRootPane().getParent(), true, caseNumberFail);
            caseNumberTextBox.setText("");
//            caseNumberTextBox.requestFocus();
        }

        if(!caseNumberTextBox.getText().equals("")) {
            switch (selectedSection) {
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
        jLabel7 = new javax.swing.JLabel();
        directionComboBox = new javax.swing.JComboBox<>();
        descriptionComboBox = new javax.swing.JComboBox<>();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Media Filing");

        jLabel2.setText("File Name:");

        jLabel3.setText("From:");

        jLabel4.setText("To:");

        jLabel5.setText("Category:");

        jLabel6.setText("Comment:");

        fileNameTextBox.setEditable(false);
        fileNameTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fileNameTextBox.setEnabled(false);

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

        jLabel7.setText("In or Out:");

        directionComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "IN", "OUT" }));

        jLabel9.setText("Description:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(fileButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel2)
                            .addComponent(jLabel8)
                            .addComponent(jLabel3)
                            .addComponent(jLabel4)
                            .addComponent(jLabel5)
                            .addComponent(jLabel6)
                            .addComponent(jLabel7)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(descriptionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(caseNumberTextBox)
                            .addComponent(fileNameTextBox)
                            .addComponent(fromTextBox)
                            .addComponent(toComboBox, 0, 279, Short.MAX_VALUE)
                            .addComponent(typeComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1)
                            .addComponent(directionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
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
                .addGap(18, 18, 18)
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
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(descriptionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(directionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel6)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(fileButton)
                    .addComponent(cancelButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void fileButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_fileButtonActionPerformed
        String[] caseNumbers = caseNumberTextBox.getText().trim().split(",");

        FileService.docketCMDSMedia(caseNumbers, //caseNumber
            selectedSection,
            fromTextBox.getText(),
            toComboBox.getSelectedItem().toString(),
            fileNameTextBox.getText(), //fileName
            typeComboBox.getSelectedItem().toString(), //fileType1
            descriptionComboBox.getSelectedItem().toString(), //fileType2
            commentTextBox.getText().trim().equals("") ? null : commentTextBox.getText().trim(),
            directionComboBox.getSelectedItem().toString(),
            this
        );

        dispose();
    }//GEN-LAST:event_fileButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton cancelButton;
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JTextArea commentTextBox;
    private javax.swing.JComboBox<String> descriptionComboBox;
    private javax.swing.JComboBox<String> directionComboBox;
    private javax.swing.JButton fileButton;
    private javax.swing.JTextField fileNameTextBox;
    private javax.swing.JTextField fromTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JComboBox<String> toComboBox;
    private javax.swing.JComboBox<String> typeComboBox;
    // End of variables declaration//GEN-END:variables
}
