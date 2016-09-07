/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

//TODO: Load all of the letter types

import java.awt.event.ItemEvent;
import java.util.List;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.generateDocument;
import parker.serb.sql.Activity;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.FileService;

/**
 *
 * @author parker
 */
public class REPLetterDialog extends javax.swing.JDialog {

    /**
     * Creates new form REPReportDialog
     * @param parent
     * @param modal
     */
    public REPLetterDialog(java.awt.Frame parent, boolean modal) {
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
        loadMemos1();
        loadMemos2();
        loadAgendas();
    }
    
    private void addListeners() {
        lettersComboBox.addItemListener((ItemEvent e) -> {
            directivesComboBox.setSelectedItem("");
            memosComboBox1.setSelectedItem("");
            memosComboBox2.setSelectedItem("");
            agendaComboBox.setSelectedItem("");
            enableGenerateButton();
        });

        directivesComboBox.addItemListener((ItemEvent e) -> {
            lettersComboBox.setSelectedItem("");
            memosComboBox1.setSelectedItem("");
            memosComboBox2.setSelectedItem("");
            agendaComboBox.setSelectedItem("");
            enableGenerateButton();
        });
        
        memosComboBox1.addItemListener((ItemEvent e) -> {
            lettersComboBox.setSelectedItem("");
            directivesComboBox.setSelectedItem("");
            memosComboBox2.setSelectedItem("");
            agendaComboBox.setSelectedItem("");
            enableGenerateButton();
        });
        
        memosComboBox2.addItemListener((ItemEvent e) -> {
            lettersComboBox.setSelectedItem("");
            directivesComboBox.setSelectedItem("");
            memosComboBox1.setSelectedItem("");
            agendaComboBox.setSelectedItem("");
            enableGenerateButton();
        });
        
        agendaComboBox.addItemListener((ItemEvent e) -> {
            lettersComboBox.setSelectedItem("");
            directivesComboBox.setSelectedItem("");
            memosComboBox1.setSelectedItem("");
            memosComboBox2.setSelectedItem("");
            enableGenerateButton();
        });       
    }
    
    private void loadLetters() {
        lettersComboBox.removeAllItems();
        lettersComboBox.addItem("");
        
        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("REP", "Letter");
        for (SMDSDocuments letter : letterList) {
            lettersComboBox.addItem(letter.description);
        }
        lettersComboBox.setSelectedItem("");
    }
       
    private void loadDirectives() {
        directivesComboBox.removeAllItems();
        directivesComboBox.addItem("");
        
        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("REP", "Directive");
        for (SMDSDocuments letter : letterList) {
            directivesComboBox.addItem(letter.description);
        }
        directivesComboBox.setSelectedItem("");
    }
    
    private void loadMemos1() {
        //load memo types??
    }
    
    private void loadMemos2() {
        memosComboBox2.removeAllItems();
        memosComboBox2.addItem("");
        
        List letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("REP", "Memo");
        for (Object letter : letterList) {
            memosComboBox2.addItem((String) letter);
        }
        memosComboBox2.setSelectedItem("");
    }
    
    private void loadAgendas() {
        agendaComboBox.removeAllItems();
        agendaComboBox.addItem("");
        
        List letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("REP", "Agenda");
        for (Object letter : letterList) {
            agendaComboBox.addItem((String) letter);
        }
        agendaComboBox.setSelectedItem("");
    }
    
    private void enableGenerateButton() {
        if(lettersComboBox.getSelectedItem().toString().equals("") 
                && directivesComboBox.getSelectedItem().toString().equals("")
                && memosComboBox1.getSelectedItem().toString().equals("")
                && memosComboBox2.getSelectedItem().toString().equals("")
                && agendaComboBox.getSelectedItem().toString().equals("")) {
            generateButton.setEnabled(false);
        } else {
            generateButton.setEnabled(true);
        }
    }
    
    private void generateDocument() {
        String selection = "";

        if (!lettersComboBox.getSelectedItem().toString().equals("")) {
            selection = lettersComboBox.getSelectedItem().toString().trim();
        } else if (!directivesComboBox.getSelectedItem().toString().equals("")) {
            selection = directivesComboBox.getSelectedItem().toString().trim();
        } else if (!memosComboBox1.getSelectedItem().toString().equals("")) {
            selection = memosComboBox1.getSelectedItem().toString().trim();
        } else if (!memosComboBox2.getSelectedItem().toString().equals("")) {
            selection = memosComboBox2.getSelectedItem().toString().trim();
        } else if (!agendaComboBox.getSelectedItem().toString().equals("")) {
            selection = agendaComboBox.getSelectedItem().toString().trim();
        }
        
        if (!"".equals(selection)){
            SMDSDocuments template = SMDSDocuments.findDocumentByDescription(selection);
            String docName = generateDocument.generateSMDSdocument(template, 0);
            Activity.addActivty("Created " + template.historyDescription, docName);
            Global.root.getuLPRootPanel1().getActivityPanel1().loadAllActivity();
            FileService.openFile(docName);
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

        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lettersComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        directivesComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        memosComboBox1 = new javax.swing.JComboBox();
        memosComboBox2 = new javax.swing.JComboBox();
        jLabel5 = new javax.swing.JLabel();
        agendaComboBox = new javax.swing.JComboBox();
        generateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Letter");

        jLabel2.setText("Letters");

        jLabel3.setText("Directives");

        jLabel4.setText("Memos");

        jLabel5.setText("Agenda");

        generateButton.setText("Generate");
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
                    .addComponent(lettersComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(directivesComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(memosComboBox1, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(memosComboBox2, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(agendaComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 140, Short.MAX_VALUE)
                        .addComponent(generateButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
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
                .addComponent(lettersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(directivesComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memosComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(memosComboBox2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(agendaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(generateButton)
                    .addComponent(cancelButton))
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
    private javax.swing.JComboBox agendaComboBox;
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox directivesComboBox;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JComboBox lettersComboBox;
    private javax.swing.JComboBox memosComboBox1;
    private javax.swing.JComboBox memosComboBox2;
    // End of variables declaration//GEN-END:variables
}
