/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import java.util.List;
import javax.swing.table.DefaultTableModel;
import parker.serb.sql.ActiveStatus;
import parker.serb.sql.NamePrefix;

/**
 *
 * @author User
 */
public class PreFixSearchDialog extends javax.swing.JDialog {

    /**
     * Creates new form PreFixSearchDialog
     * @param parent
     * @param modal
     */
    public PreFixSearchDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setColumnSize();
        loadingThread();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void loadingThread() {
        Thread temp = new Thread(() -> {
                loadTable();
                enableTextBoxes();
        });
        temp.start();
    }
    
    private void enableTextBoxes() {
        searchTextBox.setEnabled(true);
    }
    
    private void setColumnSize() {        
        //ID
        SearchTable.getColumnModel().getColumn(0).setMinWidth(0);
        SearchTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        SearchTable.getColumnModel().getColumn(0).setMaxWidth(0);
        
        //Active (Yes/No)
        SearchTable.getColumnModel().getColumn(1).setMinWidth(60);
        SearchTable.getColumnModel().getColumn(1).setWidth(60);
        SearchTable.getColumnModel().getColumn(1).setMaxWidth(60);
    }
    
    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel)SearchTable.getModel();
        model.setRowCount(0);
        
        List<NamePrefix> databaseList = NamePrefix.loadAllPrefix();
        
        for (NamePrefix item : databaseList) {
            if (item.prefix.toLowerCase().contains(searchTextBox.getText().toLowerCase())){
                model.addRow(new Object[] {
                    item.id, //ID
                    item.active, //Active
                    item.prefix //Prefix
                }); 
            }
        }
        EditButton.setEnabled(false);
    }
     
    private void tableClick(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 1) {
            if (SearchTable.getSelectedColumn() == 1){
                update();
            }
            EditButton.setEnabled(true);
        } else if (evt.getClickCount() >= 2){
            EditButtonActionPerformed(null);
        }
    }

    private void update(){
        if (SearchTable.getSelectedRow() > -1) {
            int id = (int) SearchTable.getValueAt(SearchTable.getSelectedRow(), 0);
            boolean active = (boolean) SearchTable.getValueAt(SearchTable.getSelectedRow(), 1);
            
            ActiveStatus.updateActiveStatus("NamePreFix", active, id);
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

        jLabel6 = new javax.swing.JLabel();
        searchTextBox = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        AddNewButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        SearchTable = new javax.swing.JTable();
        EditButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel6.setText("Search:");

        searchTextBox.setEnabled(false);
        searchTextBox.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                searchTextBoxCaretUpdate(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Party Name Prefix");

        AddNewButton.setText("Add New");
        AddNewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddNewButtonActionPerformed(evt);
            }
        });

        SearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Active", "Prefix"
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
        SearchTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                SearchTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(SearchTable);
        if (SearchTable.getColumnModel().getColumnCount() > 0) {
            SearchTable.getColumnModel().getColumn(0).setResizable(false);
            SearchTable.getColumnModel().getColumn(1).setResizable(false);
        }

        EditButton.setText("Edit");
        EditButton.setEnabled(false);
        EditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButtonActionPerformed(evt);
            }
        });

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddNewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 357, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(searchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AddNewButton))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 402, Short.MAX_VALUE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CloseButton)
                    .addComponent(EditButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddNewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewButtonActionPerformed
        new PreFixAddEdidDialog(null, true, 0);
        loadTable();
    }//GEN-LAST:event_AddNewButtonActionPerformed

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
        if ((int) SearchTable.getValueAt(SearchTable.getSelectedRow(), 0) > 0){
            new PreFixAddEdidDialog(null, true, (int) SearchTable.getValueAt(SearchTable.getSelectedRow(), 0));
            loadTable();
        }
    }//GEN-LAST:event_EditButtonActionPerformed

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void searchTextBoxCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_searchTextBoxCaretUpdate
        loadTable();
    }//GEN-LAST:event_searchTextBoxCaretUpdate

    private void SearchTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchTableMouseClicked
        tableClick(evt);
    }//GEN-LAST:event_SearchTableMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddNewButton;
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton EditButton;
    private javax.swing.JTable SearchTable;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables
}
