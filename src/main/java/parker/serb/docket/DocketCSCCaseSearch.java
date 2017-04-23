/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.docket;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.Audit;
import parker.serb.sql.CSCCase;

/**
 *
 * @author parkerjohnston
 */
public class DocketCSCCaseSearch extends javax.swing.JDialog {

    DefaultTableModel model;
    List caseList;
    Object[][] tableData;
    public String orgNumber;
    public String orgName;
    /**
     * Creates new form REPCaseSearch
     * @param parent
     * @param modal
     */
    public DocketCSCCaseSearch(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addRenderer();
        jLayeredPane1.moveToFront(jPanel1);
        activity();
        addListeners();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addRenderer() {
        caseSearchTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
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
        searchTextBox.getDocument().addDocumentListener(new DocumentListener() {
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

        caseSearchTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() >= 2) {
                    if (caseSearchTable.getSelectedRow() > -1){
                        orgNumber = caseSearchTable.getValueAt(caseSearchTable.getSelectedRow(), 0).toString();
                        orgName = caseSearchTable.getValueAt(caseSearchTable.getSelectedRow(), 0).toString();
                        setVisible(false);
                    }
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

    private void activity() {
        Thread temp = new Thread(
            () -> {
                loadAllCases();
                enableTextBoxes();
        });
        temp.start();
    }

    private void enableTextBoxes() {
        searchTextBox.setEnabled(true);
    }

    private void loadAllCases() {
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        model.addColumn("CSC Number");
        model.addColumn("CSC Name");
        model.addColumn("Also Known As");

        caseList = CSCCase.getCaseSearchData();

        for (Object caseItem : caseList) {
            CSCCase act = (CSCCase) caseItem;

            model.addRow(new Object[] {act.cscNumber, act.name, act.alsoKnownAs});
        }
        getTableData();
        caseSearchTable.setModel(model);
        jLayeredPane1.moveToBack(jPanel1);

    }

    private void limitCaseList() {
        model.setRowCount(0);

        for (int i = 0; i<tableData.length; i++)
        {
            if(((tableData[i][0].toString().toLowerCase().contains(searchTextBox.getText().toLowerCase()) && !searchTextBox.equals(""))
                || (tableData[i][1].toString().toLowerCase().contains(searchTextBox.getText().toLowerCase()) && !searchTextBox.equals(""))
                || (tableData[i][2].toString().toLowerCase().contains(searchTextBox.getText().toLowerCase()) && !searchTextBox.equals("")))) {
                model.addRow(new Object[] {tableData[i][0]
                , tableData[i][1], tableData[i][2]});
            }
        }
        caseSearchTable.setModel(model);
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
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        caseSearchTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        searchTextBox = new javax.swing.JTextField();
        closeButton = new javax.swing.JButton();
        refreshButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("CSC Case Search");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Search:");
        jLabel2.setToolTipText("");

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        caseSearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Org Number", "Org Name", "Also Known As"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
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
        jScrollPane1.setViewportView(caseSearchTable);

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
            .addGap(0, 981, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 388, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jLayeredPane1.add(jPanel1);

        searchTextBox.setEnabled(false);

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        refreshButton.setText("Refresh");
        refreshButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                refreshButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLayeredPane1)
                    .addComponent(closeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextBox)
                        .addGap(18, 18, 18)
                        .addComponent(refreshButton)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(refreshButton)
                    .addComponent(jLabel2))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 388, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(closeButton)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void refreshButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_refreshButtonActionPerformed
        Audit.addAuditEntry("Clicked CSC Case Search Clear Button");
        refreshButton.setEnabled(false);
        model.setNumRows(0);
        jLayeredPane1.moveToFront(jPanel1);
        activity();
        refreshButton.setEnabled(true);
    }//GEN-LAST:event_refreshButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable caseSearchTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton refreshButton;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables

}
