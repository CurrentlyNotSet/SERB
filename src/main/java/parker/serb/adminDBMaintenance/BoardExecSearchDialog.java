/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.ActiveStatus;
import parker.serb.sql.SystemExecutive;

/**
 *
 * @author User
 */
public class BoardExecSearchDialog extends javax.swing.JDialog {

    private String dept;
    
    /**
     * Creates new form BoardExecSearchDialog
     * @param parent
     * @param modal
     * @param departmentPassed
     */
    public BoardExecSearchDialog(java.awt.Frame parent, boolean modal, String departmentPassed) {
        super(parent, modal);
        initComponents();
        addRenderer();
        defaults(departmentPassed);
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }
    
    private void addRenderer() {
        SearchTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
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

    private void defaults(String departmentPassed){
        dept = departmentPassed;
        
        if (dept.equals("SERB")) {
            headerLabel.setText("SERB Executives Maintenance");
            BookmarkOrderButton.setVisible(true);
        } else if (dept.equals("SPBR")) {
            headerLabel.setText("PBR Executives Maintenance");
            //TODO - This of course works for both departments but they specifically said only give to SERB. Does not have the other half in the bookmarks changed for SBPR.
            BookmarkOrderButton.setVisible(false);
        }
        setColumnSize();
        loadingThread();
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
        
        //Position
        SearchTable.getColumnModel().getColumn(2).setMinWidth(200);
        SearchTable.getColumnModel().getColumn(2).setWidth(200);
        SearchTable.getColumnModel().getColumn(2).setMaxWidth(200);
        
//        //Name
//        SearchTable.getColumnModel().getColumn(3).setMinWidth(160);
//        SearchTable.getColumnModel().getColumn(3).setWidth(160);
//        SearchTable.getColumnModel().getColumn(3).setMaxWidth(160);

        //Phone Number
        SearchTable.getColumnModel().getColumn(4).setMinWidth(110);
        SearchTable.getColumnModel().getColumn(4).setWidth(110);
        SearchTable.getColumnModel().getColumn(4).setMaxWidth(110);

        //Email
        SearchTable.getColumnModel().getColumn(5).setMinWidth(250);
        SearchTable.getColumnModel().getColumn(5).setWidth(250);
        SearchTable.getColumnModel().getColumn(5).setMaxWidth(250);
        
        
    }
    
    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) SearchTable.getModel();
        model.setRowCount(0);

        String[] param = searchTextBox.getText().trim().split(" ");

        List<SystemExecutive> databaseList = SystemExecutive.loadExecsAllByDeptartment(dept, param);

        for (SystemExecutive item : databaseList) {
            String fullName = "";

            if (!item.firstName.equals("")) {
                fullName += item.firstName;
            }
            if (!item.middleName.equals("")) {
                fullName += " " + item.middleName;
            }
            if (!item.lastName.equals("")) {
                fullName += " " + item.lastName;
            }

            model.addRow(new Object[]{
                item.id,
                item.active,
                item.position,
                fullName,
                item.phoneNumber,
                item.email
            });
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
            
            ActiveStatus.updateActiveStatus("SystemExecutive", active, id);
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
        headerLabel = new javax.swing.JLabel();
        AddNewButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        SearchTable = new javax.swing.JTable();
        EditButton = new javax.swing.JButton();
        CloseButton = new javax.swing.JButton();
        BookmarkOrderButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel6.setText("Search:");

        searchTextBox.setEnabled(false);
        searchTextBox.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                searchTextBoxCaretUpdate(evt);
            }
        });

        headerLabel.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText("<<DEPT>> Executives Maintenance");

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
                "ID", "Active", "Position", "Name", "Phone Number", "Email"
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

        BookmarkOrderButton.setText("Change Bookmark Order");
        BookmarkOrderButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BookmarkOrderButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(headerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 873, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 44, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AddNewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(BookmarkOrderButton)
                        .addGap(18, 18, Short.MAX_VALUE)
                        .addComponent(EditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(headerLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(EditButton)
                    .addComponent(BookmarkOrderButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void AddNewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddNewButtonActionPerformed
        new BoardExecAddEdidDialog(Global.root, true, 0, dept);
        loadTable();
    }//GEN-LAST:event_AddNewButtonActionPerformed

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
        if ((int) SearchTable.getValueAt(SearchTable.getSelectedRow(), 0) > 0){
            new BoardExecAddEdidDialog(Global.root, true, (int) SearchTable.getValueAt(SearchTable.getSelectedRow(), 0), dept);
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

    private void BookmarkOrderButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BookmarkOrderButtonActionPerformed
        new BoardExecBookmarkDialog(Global.root, true, dept);
    }//GEN-LAST:event_BookmarkOrderButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddNewButton;
    private javax.swing.JButton BookmarkOrderButton;
    private javax.swing.JButton CloseButton;
    private javax.swing.JButton EditButton;
    private javax.swing.JTable SearchTable;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables
}
