/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

//TODO: Load all of the letter types
import com.alee.laf.optionpane.WebOptionPane;
import java.awt.event.ItemEvent;
import java.io.File;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import parker.serb.Global;
import parker.serb.letterGeneration.GenerateLetterNoQueuePanel;
import parker.serb.letterGeneration.LetterGenerationPanel;
import parker.serb.sql.Audit;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.Item;

/**
 *
 * @author parker
 */
public class REPLetterDialog extends javax.swing.JDialog {

    /**
     * Creates new form REPReportDialog
     *
     * @param parent
     * @param modal
     */
    public REPLetterDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        loadDefaults();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void loadDefaults() {
        memosComboBox1.addItem("");
        memosComboBox1.addItem("AC");
        memosComboBox1.addItem("All Types");
        memosComboBox1.addItem("Mail Ballot");
        memosComboBox1.addItem("JTAC");
        memosComboBox1.addItem("On-Site");
        memosComboBox1.addItem("RBT");
        memosComboBox1.addItem("RC");
        memosComboBox1.addItem("RD");
        memosComboBox1.addItem("REVOKE");
        memosComboBox1.addItem("UC");
        memosComboBox1.addItem("VR");
        memosComboBox1.setSelectedItem("");
        loadDropDowns();
        addListeners();
    }

    private void loadDropDowns() {
        loadLetters();
        loadDirectives();
        loadMemos2();
        loadMiscDocs();
    }

    private void addListeners() {
        lettersComboBox.addItemListener((ItemEvent e) -> {
            directivesComboBox.setSelectedItem(new Item<>("0", ""));
            memosComboBox2.setSelectedItem(new Item<>("0", ""));
            miscComboBox.setSelectedItem(new Item<>("0", ""));
            enableGenerateButton();
        });

        directivesComboBox.addItemListener((ItemEvent e) -> {
            lettersComboBox.setSelectedItem(new Item<>("0", ""));
            memosComboBox2.setSelectedItem(new Item<>("0", ""));
            miscComboBox.setSelectedItem(new Item<>("0", ""));
            enableGenerateButton();
        });

        memosComboBox1.addItemListener((ItemEvent e) -> {
            lettersComboBox.setSelectedItem(new Item<>("0", ""));
            directivesComboBox.setSelectedItem(new Item<>("0", ""));
            memosComboBox2.setSelectedItem(new Item<>("0", ""));
            miscComboBox.setSelectedItem(new Item<>("0", ""));
            loadMemos2();
            enableGenerateButton();
        });

        memosComboBox2.addItemListener((ItemEvent e) -> {
            lettersComboBox.setSelectedItem(new Item<>("0", ""));
            directivesComboBox.setSelectedItem(new Item<>("0", ""));
            miscComboBox.setSelectedItem(new Item<>("0", ""));
            enableGenerateButton();
        });

        miscComboBox.addItemListener((ItemEvent e) -> {
            lettersComboBox.setSelectedItem(new Item<>("0", ""));
            directivesComboBox.setSelectedItem(new Item<>("0", ""));
            memosComboBox2.setSelectedItem(new Item<>("0", ""));
            enableGenerateButton();
        });
    }

    private void loadLetters() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        lettersComboBox.setModel(dt);
        lettersComboBox.addItem(new Item<>("0", ""));

        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("REP", "Letter");
        for (SMDSDocuments letter : letterList) {
            lettersComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
        }
        lettersComboBox.setSelectedItem(new Item<>("0", ""));
    }

    private void loadDirectives() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        directivesComboBox.setModel(dt);
        directivesComboBox.addItem(new Item<>("0", ""));

        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("REP", "Directive");
        for (SMDSDocuments letter : letterList) {
            directivesComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
        }
        directivesComboBox.setSelectedItem(new Item<>("0", ""));
    }

    @Deprecated
    private void loadMemos1() {
        memosComboBox1.removeAllItems();
        memosComboBox1.addItem("");

        List<String> letterList = SMDSDocuments.loadDocumentGroupByTypeAndSection("REP", "Memo");
        for (String letter : letterList) {
            memosComboBox1.addItem(letter);
        }
        memosComboBox1.setSelectedItem("");
    }

    private void loadMemos2() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        memosComboBox2.setModel(dt);
        memosComboBox2.addItem(new Item<>("0", ""));
        List<SMDSDocuments> letterList = null;

        letterList = SMDSDocuments.loadRepMemos(memosComboBox1.getSelectedItem().toString().trim());

        for (SMDSDocuments letter : letterList) {
            memosComboBox2.addItem(new Item<>(String.valueOf(letter.id), letter.description));
        }
        memosComboBox2.setSelectedItem(new Item<>("0", ""));
    }

    private void loadMiscDocs() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        miscComboBox.setModel(dt);
        miscComboBox.addItem(new Item<>("0", ""));

        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("REP", "Misc");
        for (SMDSDocuments letter : letterList) {
            miscComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
        }
        miscComboBox.setSelectedItem(new Item<>("0", ""));
    }

    private void enableGenerateButton() {
        if (lettersComboBox.getSelectedItem().toString().equals("")
                && directivesComboBox.getSelectedItem().toString().equals("")
                && memosComboBox2.getSelectedItem().toString().equals("")
                && miscComboBox.getSelectedItem().toString().equals("")) {
            generateButton.setEnabled(false);
        } else {
            generateButton.setEnabled(true);
        }
    }

    private void generateDocument() {
        int selection = 0;
        if (!lettersComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) lettersComboBox.getSelectedItem();
            selection = Integer.parseInt(item.getValue().toString());
        } else if (!directivesComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) directivesComboBox.getSelectedItem();
            selection = Integer.parseInt(item.getValue().toString());
        } else if (!memosComboBox2.getSelectedItem().toString().equals("")) {
            Item item = (Item) memosComboBox2.getSelectedItem();
            selection = Integer.parseInt(item.getValue().toString());
        } else if (!miscComboBox.getSelectedItem().toString().equals("")) {
            Item item = (Item) miscComboBox.getSelectedItem();
            selection = Integer.parseInt(item.getValue().toString());
        }

        if (selection > 0) {
            SMDSDocuments template = SMDSDocuments.findDocumentByID(selection);
            File templateFile = new File(Global.templatePath + Global.activeSection + File.separator + template.fileName);

            if (templateFile.exists()) {
                Audit.addAuditEntry("Generated REP Letter: " + templateFile);
                switch (template.type) {
                    case "Directive":
                    case "Misc":
                    case "Memo":
                        new GenerateLetterNoQueuePanel(Global.root, true, template);
                        break;
                    default:
                        new LetterGenerationPanel(Global.root, true, template, null);
                        break;
                }
            } else {
                WebOptionPane.showMessageDialog(Global.root, "<html><center> Sorry, unable to locate template. <br><br>" + template.fileName + "</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
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

        jSeparator4 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        lettersComboBox = new javax.swing.JComboBox();
        jLabel3 = new javax.swing.JLabel();
        directivesComboBox = new javax.swing.JComboBox();
        jLabel4 = new javax.swing.JLabel();
        memosComboBox1 = new javax.swing.JComboBox();
        memosComboBox2 = new javax.swing.JComboBox();
        generateButton = new javax.swing.JButton();
        cancelButton = new javax.swing.JButton();
        miscComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Document");

        jLabel2.setText("Letters");

        jLabel3.setText("Directives");

        jLabel4.setText("Memos");

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

        jLabel6.setText("Miscellaneous");

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
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(cancelButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 368, Short.MAX_VALUE)
                        .addComponent(generateButton))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addGap(12, 12, 12))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(miscComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
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
                .addGap(18, 18, 18)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(miscComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
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
    private javax.swing.JButton cancelButton;
    private javax.swing.JComboBox directivesComboBox;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JSeparator jSeparator4;
    private javax.swing.JComboBox lettersComboBox;
    private javax.swing.JComboBox memosComboBox1;
    private javax.swing.JComboBox memosComboBox2;
    private javax.swing.JComboBox miscComboBox;
    // End of variables declaration//GEN-END:variables
}
