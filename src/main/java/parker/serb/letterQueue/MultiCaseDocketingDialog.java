/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterQueue;

import java.awt.Color;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.EmailOutRelatedCase;
import parker.serb.sql.PostalOutRelatedCase;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author User
 */
public class MultiCaseDocketingDialog extends javax.swing.JDialog {

    public List<String> selectedCaseList = new ArrayList<>();
    public boolean cancelled = false;
    private final String originLocation;
    private final int queueDatabaseID;
    private final String caseNumber;

    /**
     * Creates new form MultiCaseDocketingDialog
     *
     * @param parent
     * @param modal
     * @param caseNumber
     * @param groupNumber
     * @param originLocation
     * @param queueDatabaseID
     */
    public MultiCaseDocketingDialog(java.awt.Dialog parent, boolean modal, String caseNumber, String groupNumber, String originLocation, int queueDatabaseID) {
        super(parent, modal);
        initComponents();
        this.caseNumber = caseNumber;
        this.originLocation = originLocation;
        this.queueDatabaseID = queueDatabaseID;
        referenceCaseLabel.setText("CURRENT CASE: " + caseNumber + "  |  GROUP NUMBER: " + groupNumber);
        addRenderer();
        setColumnSize();
        loadingThread();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void addRenderer() {
        SearchTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private final DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

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

    private void setColumnSize() {
        //ID
//        SearchTable.getColumnModel().getColumn(0).setMinWidth(0);
//        SearchTable.getColumnModel().getColumn(0).setPreferredWidth(0);
//        SearchTable.getColumnModel().getColumn(0).setMaxWidth(0);

        //Active (Yes/No)
        SearchTable.getColumnModel().getColumn(1).setMinWidth(60);
        SearchTable.getColumnModel().getColumn(1).setWidth(60);
        SearchTable.getColumnModel().getColumn(1).setMaxWidth(60);
    }

    private void loadingThread() {
        jLayeredPane1.moveToFront(loadingPane);
        Thread temp = new Thread(() -> {    
            List caseList = gatherRelatedCases();
            List relatedOutList = gatherRelatedDocketCases();
            loadTable(caseList, relatedOutList);
        });
        temp.start();
    }

    private List gatherRelatedCases() {
        List caseList = null;

        switch (Global.activeSection) {
            case "CMDS":
                caseList = CMDSCase.getGroupNumberList(caseNumber);
                break;
            case "REP":
            case "ULP":
            case "Hearings":
            case "MED":
            case "ORG":
            case "Civil Service Commission":
                break;
            default:
                break;
        }
        return caseList;
    }

    private List gatherRelatedDocketCases() {
        List relatedOutList = null;

        switch (originLocation) {
            case "postalOut":
                relatedOutList = PostalOutRelatedCase.getPostalOutRelatedCaseByID(queueDatabaseID);
                break;
            case "emailOut":
                relatedOutList = EmailOutRelatedCase.getEmailOutRelatedCaseByID(queueDatabaseID);
                break;
            default:
                break;
        }
        return relatedOutList;
    }

    private void loadTable(List groupedCaseList, List relatedOutList) {
        DefaultTableModel model = (DefaultTableModel) SearchTable.getModel();
        model.setRowCount(0);
        
        
        
        //loop through entire case grouping
        for (Object item : groupedCaseList) {
            //skip over primary case
            if (caseNumber.equalsIgnoreCase(item.toString())) {
                //NOTHING TO DO: DONT ADD PRIMARY CASE to related list
            } else {
                
                //Each Loop reset variables
                boolean exists = false;
                int relatedKeyID = 0;
                String relatedCaseNumber = "";
                
                
                //Check for related case
                for (Object itemTwo : relatedOutList) {
                    exists = false;
                    relatedKeyID = 0;
                    relatedCaseNumber = "";

                    if (originLocation.equals("postalOut")) {
                        PostalOutRelatedCase docketPCase = (PostalOutRelatedCase) itemTwo;
                        relatedKeyID = docketPCase.id;
                        relatedCaseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(docketPCase.caseYear, docketPCase.caseType, docketPCase.caseMonth, docketPCase.caseNumber);
                    } else if (originLocation.equals("emailOut")) {
                        EmailOutRelatedCase docketECase = (EmailOutRelatedCase) itemTwo;
                        relatedKeyID = docketECase.id;
                        relatedCaseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(docketECase.caseYear, docketECase.caseType, docketECase.caseMonth, docketECase.caseNumber);
                    }


//                    switch (originLocation) {
//                        case "postalOut":
//                            PostalOutRelatedCase docketPCase = (PostalOutRelatedCase) itemTwo;
//                            relatedKeyID = docketPCase.id;
//                            relatedCaseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(docketPCase.caseYear, docketPCase.caseType, docketPCase.caseMonth, docketPCase.caseNumber);
//                            break;
//                        case "emailOut":
//                            EmailOutRelatedCase docketECase = (EmailOutRelatedCase) itemTwo;
//                            relatedKeyID = docketECase.id;
//                            relatedCaseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(docketECase.caseYear, docketECase.caseType, docketECase.caseMonth, docketECase.caseNumber);
//                            break;
//                        default:
//                            break;
//                    }
                    if (relatedCaseNumber.equalsIgnoreCase(item.toString())) {
                        exists = true;
                        break;
                    }
                }

                model.addRow(new Object[]{
                    relatedKeyID,
                    exists,
                    item.toString()
                });
            }
        }
        
        jLayeredPane1.moveToBack(loadingPane);
        loadingPane.setVisible(false);
    }
    
    
    private void addItem(){
        
    }
    
    

    private void updateCaseList() {
        for (int i = 0; i < SearchTable.getRowCount(); i++) {
            int dbKey = (int) SearchTable.getValueAt(i, 0);

            if (SearchTable.getValueAt(i, 1).toString().equals("true") && dbKey == 0) {
                NumberFormatService num = NumberFormatService.parseFullCaseNumberNoNGlobal(SearchTable.getValueAt(i, 2).toString());
                
                switch (originLocation) {
                    case "postalOut":
                        PostalOutRelatedCase postalRelatedModel = new PostalOutRelatedCase();
                        postalRelatedModel.postalOutId = queueDatabaseID;
                        postalRelatedModel.caseYear = num.caseYear;
                        postalRelatedModel.caseType = num.caseType;
                        postalRelatedModel.caseMonth = num.caseMonth;
                        postalRelatedModel.caseNumber = num.caseNumber;
                        
                        PostalOutRelatedCase.insertPostalOutRelatedCase(postalRelatedModel);
                        break;
                    case "emailOut":
                        EmailOutRelatedCase emailRelatedModel = new EmailOutRelatedCase();
                        emailRelatedModel.emailOutId = queueDatabaseID;
                        emailRelatedModel.caseYear = num.caseYear;
                        emailRelatedModel.caseType = num.caseType;
                        emailRelatedModel.caseMonth = num.caseMonth;
                        emailRelatedModel.caseNumber = num.caseNumber;
                        
                        EmailOutRelatedCase.insertEmailOutRelatedCase(emailRelatedModel);
                        break;
                    default:
                        break;
                }
            } else if (SearchTable.getValueAt(i, 1).toString().equals("false") && dbKey > 0){
                switch (originLocation) {
                    case "postalOut":
                        PostalOutRelatedCase.deletePostalOutRelatedCaseByID(dbKey);
                        break;
                    case "emailOut":
                        EmailOutRelatedCase.deleteEmailOutRelatedCaseByID(dbKey);
                        break;
                    default:
                        break;
                }
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
        SaveButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        referenceCaseLabel = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        tablePane = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        SearchTable = new javax.swing.JTable();
        loadingPane = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Multi Case Docketing");

        SaveButton.setText("Save");
        SaveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SaveButtonActionPerformed(evt);
            }
        });

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Please uncheck any additional cases you do");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("NOT want these outgoing documents to be docketed to");

        referenceCaseLabel.setText("CURRENT CASE:  <CASENUMBER>  GROUP: <GROUPNUMBER>");

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        SearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "", "Case Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        SearchTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(SearchTable);
        if (SearchTable.getColumnModel().getColumnCount() > 0) {
            SearchTable.getColumnModel().getColumn(0).setResizable(false);
            SearchTable.getColumnModel().getColumn(1).setResizable(false);
            SearchTable.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout tablePaneLayout = new javax.swing.GroupLayout(tablePane);
        tablePane.setLayout(tablePaneLayout);
        tablePaneLayout.setHorizontalGroup(
            tablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
        tablePaneLayout.setVerticalGroup(
            tablePaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
        );

        jLayeredPane1.add(tablePane);

        jLabel6.setBackground(new java.awt.Color(255, 255, 255));
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        javax.swing.GroupLayout loadingPaneLayout = new javax.swing.GroupLayout(loadingPane);
        loadingPane.setLayout(loadingPaneLayout);
        loadingPaneLayout.setHorizontalGroup(
            loadingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
        );
        loadingPaneLayout.setVerticalGroup(
            loadingPaneLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 418, Short.MAX_VALUE)
        );

        jLayeredPane1.add(loadingPane);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 480, Short.MAX_VALUE)
                    .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(SaveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(referenceCaseLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addGap(26, 26, 26)
                .addComponent(referenceCaseLabel)
                .addGap(18, 18, 18)
                .addComponent(jLayeredPane1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CloseButton)
                    .addComponent(SaveButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void SaveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SaveButtonActionPerformed
        updateCaseList();
        this.setVisible(false);
    }//GEN-LAST:event_SaveButtonActionPerformed

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        cancelled = true;
        this.setVisible(false);
    }//GEN-LAST:event_CloseButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton SaveButton;
    private javax.swing.JTable SearchTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel loadingPane;
    private javax.swing.JLabel referenceCaseLabel;
    private javax.swing.JPanel tablePane;
    // End of variables declaration//GEN-END:variables
}
