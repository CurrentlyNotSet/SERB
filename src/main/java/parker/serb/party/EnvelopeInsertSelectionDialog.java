/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.party;

import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.processMailingAddressBookmarks;
import parker.serb.sql.Audit;
import parker.serb.sql.CSCCase;
import parker.serb.sql.CaseParty;
import parker.serb.sql.ORGCase;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class EnvelopeInsertSelectionDialog extends javax.swing.JDialog {

    /**
     * Creates new form EnvelopeInsertSelectionDialog
     *
     * @param parent
     * @param modal
     */
    public EnvelopeInsertSelectionDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDefaults();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void setDefaults() {
        loadingPanel.setVisible(false);
        InfoPanel.setVisible(true);
        jLayeredPane.moveToFront(InfoPanel);
        addRenderer();
        setColumnSizes();
        loadTable();
    }

    private void addRenderer() {
        personTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Global.ALTERNATE_ROW_COLOR);
                }
                return c;
            }
        });
    }

    private void setColumnSizes() {
        //ID
        personTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        personTable.getColumnModel().getColumn(0).setMinWidth(0);
        personTable.getColumnModel().getColumn(0).setMaxWidth(0);

        //Active (Yes/No)
        personTable.getColumnModel().getColumn(1).setMinWidth(60);
        personTable.getColumnModel().getColumn(1).setWidth(60);
        personTable.getColumnModel().getColumn(1).setMaxWidth(60);
    }

    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) personTable.getModel();
        model.setRowCount(0);

        CaseParty orgObject = null;
        List<CaseParty> partyList = null;

        switch (Global.activeSection) {
            case "CSC":
            case "Civil Service Commission":
                orgObject = convertCSCToCaseParty(CSCCase.loadCSCInformation());
                partyList = CaseParty.loadORGPartiesByCase();
                break;
            case "ORG":
                orgObject = convertOrgToCaseParty(ORGCase.loadORGInformation());
                partyList = CaseParty.loadORGPartiesByCase();
                break;
            default:
                partyList = CaseParty.loadPartiesByCaseForLetterGeneration();
                break;
        }

        if (orgObject != null){
            model.addRow(new Object[]{
                orgObject,
                SelectAllCheckBox.isSelected(),
                orgObject.caseRelation,
                StringUtilities.buildCasePartyNameNoPreFix(orgObject)
            });
        }

        for (CaseParty party : partyList) {
            model.addRow(new Object[]{
                party,
                SelectAllCheckBox.isSelected(),
                party.caseRelation,
                StringUtilities.buildCasePartyNameNoPreFix(party)
            });
        }

        if (model.getRowCount() == 0){
            GenerateButton.setEnabled(false);
        }
    }

    private void generateDocument() {
        String CaseNumber = "";
        switch (Global.activeSection) {
            case "CSC":
            case "Civil Service Commission":
                CaseNumber = "CSC Number: " + Global.caseNumber;
                break;
            case "ORG":
                CaseNumber = "ORG Number: " + Global.caseNumber;
                break;
            default:
                CaseNumber = "Case Number: " + NumberFormatService.generateFullCaseNumber();
                break;
        }

        Audit.addAuditEntry("Generted Envelope Inserts for " + CaseNumber);

        processThread();
        loadingPanel.setVisible(true);
        InfoPanel.setVisible(false);
        jLayeredPane.moveToFront(loadingPanel);
        GenerateButton.setEnabled(false);
        CloseButton.setEnabled(false);
    }

    private void processThread() {
        Thread temp = new Thread(() -> {
            createDocument();
            this.dispose();
        });
        temp.start();
    }

    private void createDocument() {
        String savedDoc = String.valueOf(new Date().getTime()) + "_EnvelopeInserts" + ".pdf";
        String tempFolderPath = System.getProperty("java.io.tmpdir");

        //Set up Initial Merge Utility
        PDFMergerUtility ut = new PDFMergerUtility();

        //List ConversionPDFs To Delete Later
        List<String> tempPDFList = new ArrayList<>();

        for (int i = 0; i < personTable.getRowCount(); i++) {
            if (personTable.getValueAt(i, 1).equals(true)) {
                String envelopeFilePDF = processMailingAddressBookmarks.processSingleEnvelopeInsert(
                        Global.templatePath, "EnvelopeInsert.docx", (CaseParty) personTable.getValueAt(i, 0)
                );
                //Add Email Body To PDF Merge
                try {
                    ut.addSource(tempFolderPath + envelopeFilePDF);
                    tempPDFList.add(tempFolderPath + envelopeFilePDF);
                } catch (FileNotFoundException ex) {
                    SlackNotification.sendNotification(ex);
                }
            }
        }

        //Set Merge File Destination
        ut.setDestinationFileName(tempFolderPath + savedDoc);

        //Try to Merge
        try {
            ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException ex) {
            SlackNotification.sendNotification(ex);
        }

        //Clean up temp PDFs
        for (String tempPDF : tempPDFList) {
            new File(tempPDF).delete();
        }

        FileService.openFileFullPath(new File(tempFolderPath + savedDoc));
    }

    private CaseParty convertOrgToCaseParty(ORGCase org){
        CaseParty item = new CaseParty();

        item.caseRelation = "Organization";

        item.companyName = org.orgName;
        item.address1 = org.orgAddress1;
        item.address2 = org.orgAddress2;
        item.city = org.orgCity;
        item.stateCode = org.orgState;
        item.zipcode = org.orgZip;

        return item;
    }

    private CaseParty convertCSCToCaseParty(CSCCase csc){
        CaseParty item = new CaseParty();

        item.caseRelation = "Civil Service Commission";

        item.companyName = csc.name;
        item.address1 = csc.address1;
        item.address2 = csc.address2;
        item.city = csc.city;
        item.stateCode = csc.state;
        item.zipcode = csc.zipCode;
        return item;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane = new javax.swing.JLayeredPane();
        InfoPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        personTable = new javax.swing.JTable();
        CloseButton = new javax.swing.JButton();
        GenerateButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        SelectAllCheckBox = new javax.swing.JCheckBox();
        loadingPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLayeredPane.setLayout(new javax.swing.OverlayLayout(jLayeredPane));

        personTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case Party Object", "", "Case Relation", "Name"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, true
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(personTable);
        if (personTable.getColumnModel().getColumnCount() > 0) {
            personTable.getColumnModel().getColumn(1).setResizable(false);
        }

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        GenerateButton.setText("Generate");
        GenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Envelope Inserts");

        SelectAllCheckBox.setSelected(true);
        SelectAllCheckBox.setText("Select All");
        SelectAllCheckBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SelectAllCheckBoxMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout InfoPanelLayout = new javax.swing.GroupLayout(InfoPanel);
        InfoPanel.setLayout(InfoPanelLayout);
        InfoPanelLayout.setHorizontalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 487, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addComponent(SelectAllCheckBox)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addComponent(CloseButton)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(GenerateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        InfoPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CloseButton, GenerateButton});

        InfoPanelLayout.setVerticalGroup(
            InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(InfoPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SelectAllCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 393, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CloseButton)
                    .addComponent(GenerateButton))
                .addContainerGap())
        );

        jLayeredPane.add(InfoPanel);

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setText("Generating Document");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Please Wait...");

        javax.swing.GroupLayout loadingPanelLayout = new javax.swing.GroupLayout(loadingPanel);
        loadingPanel.setLayout(loadingPanelLayout);
        loadingPanelLayout.setHorizontalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE)
            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 507, Short.MAX_VALUE))
        );
        loadingPanelLayout.setVerticalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadingPanelLayout.createSequentialGroup()
                .addGap(64, 64, 64)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addContainerGap(384, Short.MAX_VALUE))
            .addGroup(loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        );

        jLayeredPane.add(loadingPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLayeredPane)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void GenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateButtonActionPerformed
        generateDocument();
    }//GEN-LAST:event_GenerateButtonActionPerformed

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void SelectAllCheckBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SelectAllCheckBoxMouseClicked
        for (int i = 0; i < personTable.getRowCount(); i++) {
            personTable.getModel().setValueAt(SelectAllCheckBox.isSelected(), i, 1);
        }
    }//GEN-LAST:event_SelectAllCheckBoxMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton GenerateButton;
    private javax.swing.JPanel InfoPanel;
    private javax.swing.JCheckBox SelectAllCheckBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel loadingPanel;
    private javax.swing.JTable personTable;
    // End of variables declaration//GEN-END:variables
}
