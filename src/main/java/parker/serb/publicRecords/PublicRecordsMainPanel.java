/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.publicRecords;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.CSCCase;
import parker.serb.sql.ORGCase;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class PublicRecordsMainPanel extends javax.swing.JDialog {

    /**
     * Creates new form fileSelector
     *
     * @param parent
     * @param modal
     */
    public PublicRecordsMainPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setDefaults();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void setDefaults() {
        headerLabel.setText(Global.activeSection + " Public Records");
        if (Global.caseNumber != null) {
            switch (Global.activeSection) {
                case "ORG":
                    caseDocsLabel.setText("Documents for " + ORGCase.getORGName());
                    break;
                case "Civil Service Commission":
                    caseDocsLabel.setText("Documents for " + CSCCase.getCSCName());
                    break;
                default:
                    caseDocsLabel.setText("Documents for " + NumberFormatService.generateFullCaseNumber());
                    break;
            }
        } else {
            jTabbedPane1.remove(caseDocsPanel);
        }
        setListeners();
        setTableColumns();
        setActiveTab();
    }

    private void setListeners() {
        caseDocsTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (caseDocsTable.getValueAt(row, 3).toString().contains("REDACTED - ") && caseDocsTable.getValueAt(row, 5).toString().equals("false")) {
                    if (isSelected) {
                        c.setBackground(Color.BLACK);
                        c.setForeground(Color.RED);
                    } else {
                        c.setBackground(Color.RED);
                        c.setForeground(Color.BLACK);
                    }
                } else if (isSelected) {
                    c.setBackground(caseDocsTable.getSelectionBackground());
                    c.setForeground(caseDocsTable.getSelectionForeground());
                } else {
                    c.setBackground(caseDocsTable.getBackground());
                    c.setForeground(caseDocsTable.getForeground());
                }
                return c;
            }
        });
    }

    private void setTableColumns() {
        caseDocsTable.getColumnModel().getColumn(0).setMinWidth(0);
        caseDocsTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        caseDocsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        caseDocsTable.getColumnModel().getColumn(1).setMinWidth(60);
        caseDocsTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        caseDocsTable.getColumnModel().getColumn(1).setMaxWidth(60);
        caseDocsTable.getColumnModel().getColumn(2).setMinWidth(150);
        caseDocsTable.getColumnModel().getColumn(2).setPreferredWidth(150);
        caseDocsTable.getColumnModel().getColumn(2).setMaxWidth(150);
        caseDocsTable.getColumnModel().getColumn(4).setMinWidth(0);
        caseDocsTable.getColumnModel().getColumn(4).setPreferredWidth(0);
        caseDocsTable.getColumnModel().getColumn(4).setMaxWidth(0);
        caseDocsTable.getColumnModel().getColumn(5).setMinWidth(0);
        caseDocsTable.getColumnModel().getColumn(5).setPreferredWidth(0);
        caseDocsTable.getColumnModel().getColumn(5).setMaxWidth(0);

        awaitingTable.getColumnModel().getColumn(0).setMinWidth(0);
        awaitingTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        awaitingTable.getColumnModel().getColumn(0).setMaxWidth(0);
        awaitingTable.getColumnModel().getColumn(1).setMinWidth(80);
        awaitingTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        awaitingTable.getColumnModel().getColumn(1).setMaxWidth(80);
        awaitingTable.getColumnModel().getColumn(2).setMinWidth(130);
        awaitingTable.getColumnModel().getColumn(2).setPreferredWidth(130);
        awaitingTable.getColumnModel().getColumn(2).setMaxWidth(130);
        awaitingTable.getColumnModel().getColumn(4).setMinWidth(0);
        awaitingTable.getColumnModel().getColumn(4).setPreferredWidth(0);
        awaitingTable.getColumnModel().getColumn(4).setMaxWidth(0);

        switch (Global.activeSection) {
            case "Civil Service Commission":
            case "ORG":
                awaitingTable.getColumnModel().getColumn(2).setHeaderValue("Org Number");
                awaitingTable.getColumnModel().getColumn(2).setMinWidth(90);
                awaitingTable.getColumnModel().getColumn(2).setPreferredWidth(90);
                awaitingTable.getColumnModel().getColumn(2).setMaxWidth(90);
                break;
            default:
                break;
        }
    }

    private void setActiveTab() {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Case Documents":
                loadCaseDocsTable();

                jButton1.setText("Send Docs");
                jButton1.setVisible(true);
                jButton1.setEnabled(true);
                jButton2.setText("Redact Docs");
                jButton2.setVisible(true);
                jButton2.setEnabled(true);
                break;
            case "Awaiting Redaction":
                loadRedactionTable();

                jButton1.setText("Approve");
                jButton1.setEnabled(true);
                jButton1.setVisible(true);
                jButton2.setVisible(false);
                break;
        }
    }

    private void loadCaseDocsTable() {
        DefaultTableModel model = (DefaultTableModel) caseDocsTable.getModel();
        model.setRowCount(0);
        List<Activity> activtyList = null;

        switch (Global.activeSection) {
            case "REP":
            case "ULP":
            case "MED":
            case "CMDS":
            case "Hearings":
                activtyList = Activity.loadActivityDocumentsByGlobalCasePublicRectords();
                break;
            case "Civil Service Commission":
            case "ORG":
                activtyList = Activity.loadActivityDocumentsByGlobalCaseORGCSCPublicRectords();
                break;
        }

        for (Activity doc : activtyList) {
            model.addRow(new Object[]{
                doc.id,
                false,
                doc.date,
                doc.action,
                doc.fileName,
                doc.redacted
            });
        }
    }

    private void loadRedactionTable() {
        DefaultTableModel model = (DefaultTableModel) awaitingTable.getModel();
        model.setRowCount(0);

        List<Activity> activtyList = Activity.loadDocumentsBySectionAwaitingRedaction();

        for (Activity doc : activtyList) {
            model.addRow(new Object[]{
                doc.id,
                false,
                NumberFormatService.generateFullCaseNumberNonGlobal(doc.caseYear, doc.caseType, doc.caseMonth, doc.caseNumber),
                doc.action,
                doc.fileName
            });
        }
    }

    private void sendDocs() {
        List<Activity> docsList = new ArrayList<>();

        for (int i = 0; i < caseDocsTable.getRowCount(); i++) {
            if (caseDocsTable.getValueAt(i, 1).equals(true)) {
                Activity act = new Activity();
                act.id = Integer.valueOf(caseDocsTable.getValueAt(i, 0).toString());
                act.fileName = caseDocsTable.getValueAt(i, 4).toString();
                act.action = caseDocsTable.getValueAt(i, 3).toString();
                docsList.add(act);
            }
        }

        if (docsList.size() > 0) {
            //SEND panel
            new PublicRecordsEmailPanel(Global.root, true, docsList);
            loadCaseDocsTable();
        } else {
            WebOptionPane.showMessageDialog(Global.root, "No Documents Selected to Send",
                    "Send Warning", WebOptionPane.WARNING_MESSAGE);
        }
    }

    private void redactDocs() {
        for (int i = 0; i < caseDocsTable.getRowCount(); i++) {
            if (caseDocsTable.getValueAt(i, 1).equals(true)) {
                String fileName = caseDocsTable.getValueAt(i, 4).toString();
                String[] file = fileName.split("_", 2);
                int id = Integer.valueOf(caseDocsTable.getValueAt(i, 0).toString());

                //Copy File
                boolean copySuccess = FileService.renamePublicRecordsFile(caseDocsTable.getValueAt(i, 4).toString());

                if (copySuccess) {
                    //Get Full Activity
                    Activity doup = Activity.getFULLActivityByID(id);

                    //Set Original Activity Entry as Unredacted
                    Activity.updateUnRedactedAction("UNREDACTED - " + doup.action, id);

                    //Duplicate Activity Entry
                    doup.action = "REDACTED - " + doup.action;
                    doup.fileName = file[0] + "_REDACTED-" + file[1];
                    Activity.duplicatePublicRecordActivty(doup);
                }
            }
        }
        loadCaseDocsTable();
    }

    private void approveDocs() {
        for (int i = 0; i < awaitingTable.getRowCount(); i++) {
            if (awaitingTable.getValueAt(i, 1).equals(true)) {
                Activity.updateRedactedStatus(true, Integer.valueOf(awaitingTable.getValueAt(i, 0).toString()));
            }
        }
        loadRedactionTable();
    }

    private void reloadActivity() {
        switch (Global.activeSection) {
            case "REP":
                Global.root.getrEPRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "ULP":
                Global.root.getuLPRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "ORG":
                Global.root.getoRGRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "MED":
                Global.root.getmEDRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "Hearings":
                break;
            case "Civil Service Commission":
                Global.root.getcSCRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "CMDS":
                Global.root.getcMDSRootPanel1().getActivityPanel1().loadAllActivity();
                break;
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

        headerLabel = new javax.swing.JLabel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        caseDocsPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        caseDocsTable = new javax.swing.JTable();
        caseDocsLabel = new javax.swing.JLabel();
        awaitingRedactPanel = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        awaitingTable = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        headerLabel.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText("Public Records");

        jTabbedPane1.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                jTabbedPane1StateChanged(evt);
            }
        });

        caseDocsTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Include", "Date", "Description", "FileName", "Redacted"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        caseDocsTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                caseDocsTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(caseDocsTable);
        if (caseDocsTable.getColumnModel().getColumnCount() > 0) {
            caseDocsTable.getColumnModel().getColumn(0).setResizable(false);
            caseDocsTable.getColumnModel().getColumn(1).setResizable(false);
            caseDocsTable.getColumnModel().getColumn(2).setResizable(false);
            caseDocsTable.getColumnModel().getColumn(3).setResizable(false);
            caseDocsTable.getColumnModel().getColumn(4).setResizable(false);
            caseDocsTable.getColumnModel().getColumn(5).setResizable(false);
        }

        caseDocsLabel.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        caseDocsLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        caseDocsLabel.setText("Documents for <<CASENUMBER>>");

        javax.swing.GroupLayout caseDocsPanelLayout = new javax.swing.GroupLayout(caseDocsPanel);
        caseDocsPanel.setLayout(caseDocsPanelLayout);
        caseDocsPanelLayout.setHorizontalGroup(
            caseDocsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(caseDocsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(caseDocsLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
        );
        caseDocsPanelLayout.setVerticalGroup(
            caseDocsPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, caseDocsPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(caseDocsLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 544, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Case Documents", caseDocsPanel);

        awaitingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Complete", "CaseNumber", "Description", "FileName"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        awaitingTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                awaitingTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(awaitingTable);
        if (awaitingTable.getColumnModel().getColumnCount() > 0) {
            awaitingTable.getColumnModel().getColumn(0).setResizable(false);
            awaitingTable.getColumnModel().getColumn(1).setResizable(false);
            awaitingTable.getColumnModel().getColumn(2).setResizable(false);
            awaitingTable.getColumnModel().getColumn(3).setResizable(false);
            awaitingTable.getColumnModel().getColumn(4).setResizable(false);
        }

        javax.swing.GroupLayout awaitingRedactPanelLayout = new javax.swing.GroupLayout(awaitingRedactPanel);
        awaitingRedactPanel.setLayout(awaitingRedactPanelLayout);
        awaitingRedactPanelLayout.setHorizontalGroup(
            awaitingRedactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 877, Short.MAX_VALUE)
        );
        awaitingRedactPanelLayout.setVerticalGroup(
            awaitingRedactPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(awaitingRedactPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 570, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Awaiting Redaction", awaitingRedactPanel);

        jPanel3.setMinimumSize(new java.awt.Dimension(105, 100));

        jButton1.setText("BUTTON 1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("BUTTON 2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
            .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(headerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jTabbedPane1)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        reloadActivity();
        this.dispose();
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void jTabbedPane1StateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_jTabbedPane1StateChanged
        setActiveTab();
    }//GEN-LAST:event_jTabbedPane1StateChanged

    private void caseDocsTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_caseDocsTableMouseClicked
        if (evt.getClickCount() > 1 && caseDocsTable.getSelectedRow() > -1) {
            FileService.openFile(caseDocsTable.getValueAt(caseDocsTable.getSelectedRow(), 4).toString());
        }
    }//GEN-LAST:event_caseDocsTableMouseClicked

    private void awaitingTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_awaitingTableMouseClicked
        if (evt.getClickCount() > 1 && awaitingTable.getSelectedRow() > -1) {
            String fileName = awaitingTable.getValueAt(awaitingTable.getSelectedRow(), 4).toString();
            String[] caseNumber = awaitingTable.getValueAt(awaitingTable.getSelectedRow(), 2).toString().split("-");

            FileService.openFileWithCaseNumber(Global.activeSection, caseNumber[0], caseNumber[1], caseNumber[2], caseNumber[3], fileName);
        }
    }//GEN-LAST:event_awaitingTableMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Case Documents":
                sendDocs();
                break;
            case "Awaiting Redaction":
                approveDocs();
                break;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Case Documents":
                redactDocs();
                break;
            case "Awaiting Redaction":
                break;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JPanel awaitingRedactPanel;
    private javax.swing.JTable awaitingTable;
    private javax.swing.JLabel caseDocsLabel;
    private javax.swing.JPanel caseDocsPanel;
    private javax.swing.JTable caseDocsTable;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTabbedPane jTabbedPane1;
    // End of variables declaration//GEN-END:variables
}
