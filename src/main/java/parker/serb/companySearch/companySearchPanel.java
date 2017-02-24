/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.companySearch;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.Audit;
import parker.serb.sql.EmployerCaseSearchData;

/**
 *
 * @author parkerjohnston
 */
public class companySearchPanel extends javax.swing.JPanel {

    DefaultTableModel model;
    List caseList;
    Object[][] tableData;
        
    /**
     * Creates new form companySearchPanel
     */
    public companySearchPanel() {
        initComponents();
        addRenderer();
        addListeners();
    }
    
    private void addRenderer() {
        employerTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
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
    
    private void addListeners() {
        employerSearchTerm.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                limitCaseList();
            }
        });
    }
    
    public void loadPastData() {
        if(Global.employerSearchTerm != null) {
            employerSearchTerm.setText(Global.employerSearchTerm);
        }
    }
    
    public void activity() {
        jLayeredPane1.moveToFront(jPanel1);
        Thread temp = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    loadAllCases();
                    if(!employerSearchTerm.getText().equals("")) {
                        limitCaseList();
                    }
                }
            }
        );
        temp.start();
    }
    
    private void loadAllCases() {
        
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }   
        };
        
        model.addColumn("Case Number");
        model.addColumn("Status");
        model.addColumn("Filed Date");
        model.addColumn("Employer");
        model.addColumn("Employer ID");
        
        caseList = EmployerCaseSearchData.loadEmployerCaseList();
        
        for (Object caseItem : caseList) {
            EmployerCaseSearchData act = (EmployerCaseSearchData) caseItem;
            
            model.addRow(new Object[] {
                (act.caseYear + "-" + act.caseType + "-" + act.caseMonth + "-" + act.caseNumber),
                act.status == null ? "" : act.status, //employer name
                act.fileDate, //file date
                act.employer == null ? "" : act.employer, //employer
                act.employerID == null ? "" : act.employerID //employerID
            }); 
        }
        getTableData();
        employerTable.setModel(model);
        jLayeredPane1.moveToBack(jPanel1);
        
    }
    
    public void getTableData() {
        int nRow = model.getRowCount(), nCol = model.getColumnCount();
        tableData = new Object[nRow][nCol];
        for (int i = 0 ; i < nRow ; i++) {
            for (int j = 0 ; j < nCol ; j++) {
                tableData[i][j] = model.getValueAt(i,j);
            }
        }
    }
    
    private void limitCaseList() {
        model.setRowCount(0);
        Audit.addAuditEntry("Searched Employer Search: " + employerSearchTerm.getText());
        
        for (int i = 0; i<tableData.length; i++)
        {
            if(tableData[i][3].toString().toLowerCase().contains(employerSearchTerm.getText().toLowerCase()) && !employerSearchTerm.equals("")
                    || tableData[i][4].toString().toLowerCase().contains(employerSearchTerm.getText().toLowerCase()) && !employerSearchTerm.equals(""))
            {
                if(statusComboBox.getSelectedItem().toString().equals("All")) {
                    model.addRow(new Object[] {tableData[i][0]
                    , tableData[i][1], tableData[i][2], tableData[i][3], tableData[i][4]}); 
                } else if(statusComboBox.getSelectedItem().toString().equals("Open")) {
                    if(tableData[i][1].toString().toLowerCase().equals("open")) {
                        model.addRow(new Object[] {tableData[i][0]
                    , tableData[i][1], tableData[i][2], tableData[i][3], tableData[i][4]}); 
                    }
                } else if(statusComboBox.getSelectedItem().toString().equals("Closed")) {
                    if(tableData[i][1].toString().toLowerCase().equals("closed")) {
                        model.addRow(new Object[] {tableData[i][0]
                    , tableData[i][1], tableData[i][2], tableData[i][3], tableData[i][4]}); 
                    }
                }
            }
        }
        employerTable.setModel(model);
    }

    public DefaultTableModel getModel() {
        return model;
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
        employerSearchTerm = new javax.swing.JTextField();
        refreshButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        statusComboBox = new javax.swing.JComboBox<>();
        clearButton = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        employerTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();

        jLabel1.setText("Search:");

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        jLabel2.setText("Status:");

        statusComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "All", "Open", "Closed" }));
        statusComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                statusComboBoxActionPerformed(evt);
            }
        });

        clearButton.setText("Clear");
        clearButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearButtonActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        employerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case Number", "Status", "File Date", "Employer", "Employer ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        employerTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                employerTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(employerTable);

        jLayeredPane1.add(jScrollPane1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1104, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1104, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
        );

        jLayeredPane1.add(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(employerSearchTerm)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(refreshButton)
                .addContainerGap())
            .addComponent(jLayeredPane1, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(employerSearchTerm, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton)
                    .addComponent(jLabel2)
                    .addComponent(statusComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        Audit.addAuditEntry("Clicked Refresh Employer Search Button");
        model.setNumRows(0);
        jLayeredPane1.moveToFront(jPanel1);
        activity();
    }//GEN-LAST:event_refreshButtonActionPerformed

    private void clearButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearButtonActionPerformed
        Audit.addAuditEntry("Clicked Clear Employer Search Button");
        statusComboBox.setSelectedItem("All");
        employerSearchTerm.setText("");
    }//GEN-LAST:event_clearButtonActionPerformed

    private void employerTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_employerTableMouseClicked
        if(evt.getClickCount() == 2) {
            String caseNumber = employerTable.getValueAt(employerTable.getSelectedRow(), 0).toString();
            Audit.addAuditEntry("Clicked " + caseNumber + " on Employer Search Table");
            switch(caseNumber.split("-")[1]) {
                case "MED":
                case "STK":
                case "NCN":
                case "CON":
                    Global.root.getjTabbedPane1().setSelectedIndex(Global.root.getjTabbedPane1().indexOfTab("MED"));
                    Global.root.getmEDHeaderPanel1().getjComboBox2().setSelectedItem(caseNumber);
                    break;
                case "REP":
                case "RBT":
                    Global.root.getjTabbedPane1().setSelectedIndex(Global.root.getjTabbedPane1().indexOfTab("REP"));
                    Global.root.getrEPHeaderPanel1().getjComboBox2().setSelectedItem(caseNumber);
                    break;
                case "ULP":
                case "ERC":
                case "JWD":
                    Global.root.getjTabbedPane1().setSelectedIndex(Global.root.getjTabbedPane1().indexOfTab("ULP"));
                    Global.root.getuLPHeaderPanel1().getjComboBox2().setSelectedItem(caseNumber);
                    break;
            }
        }
    }//GEN-LAST:event_employerTableMouseClicked

    private void statusComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_statusComboBoxActionPerformed
        Audit.addAuditEntry("Changed Status Combobox to " + statusComboBox.getSelectedItem().toString() + " on Employer Search");
        limitCaseList();    
    }//GEN-LAST:event_statusComboBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton clearButton;
    private javax.swing.JTextField employerSearchTerm;
    private javax.swing.JTable employerTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JComboBox<String> statusComboBox;
    // End of variables declaration//GEN-END:variables
}
