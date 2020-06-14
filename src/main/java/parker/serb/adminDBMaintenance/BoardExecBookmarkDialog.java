/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.Color;
import java.awt.Component;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;
import java.util.List;
import javax.swing.JComponent;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.TransferHandler;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.SystemExecutive;

public class BoardExecBookmarkDialog extends javax.swing.JDialog {

    private String dept;
    private DefaultTableModel modelBookmark;
    private DefaultTableModel modelAvail;

    /**
     * Creates new form BoardExecAddEdidDialog
     *
     * @param parent
     * @param modal
     * @param deptPassed
     */
    public BoardExecBookmarkDialog(java.awt.Frame parent, boolean modal, String deptPassed) {
        super(parent, modal);
        initComponents();
        addRenderer();
        setDefaults(deptPassed);
    }

    private void setDefaults(String deptPassed) {
        dept = deptPassed;

        if (dept.equals("SERB")) {
            titleLabel.setText("SERB Executives Bookmark Maintenance");
        } else if (dept.equals("SPBR")) {
            titleLabel.setText("PBR Executives Bookmark Maintenance");
        }

        modelAvail = (DefaultTableModel) AvailableTable.getModel();
        modelBookmark = (DefaultTableModel) BookmarkTable.getModel();
        AvailableTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        BookmarkTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        BookmarkTable.setDragEnabled(true);
        BookmarkTable.setTransferHandler(new TableTransferHandler());

        AvailableTable.getColumnModel().getColumn(0).setMinWidth(0);
        AvailableTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        AvailableTable.getColumnModel().getColumn(0).setMaxWidth(0);

        BookmarkTable.getColumnModel().getColumn(0).setMinWidth(0);
        BookmarkTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        BookmarkTable.getColumnModel().getColumn(0).setMaxWidth(0);

        loadInformation();

        this.setLocationRelativeTo(Global.root);
        this.setVisible(true);
    }

    private void addRenderer() {
        AvailableTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
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

        BookmarkTable.setDefaultRenderer(Object.class, new TableCellRenderer() {
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

    private void loadInformation() {
        List<SystemExecutive> databaseList = SystemExecutive.loadExecsAllByDeptartmentForBookMarks(dept);

        modelAvail.setRowCount(0);
        modelBookmark.setRowCount(0);

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

            if (item.bookmarkorder == 0) {
                modelAvail.addRow(new Object[]{
                    item.id,
                    item.position,
                    fullName
                });
            } else {
                modelBookmark.addRow(new Object[]{
                    item.id,
                    item.position,
                    fullName
                });
            }
        }
    }

    private void saveInformation() {
        //Set Any In Available to ZERO
        for (int i = 0; i < AvailableTable.getRowCount(); i++) {
            SystemExecutive.updateSystemExecutiveBookmarkOrder(Integer.valueOf(AvailableTable.getValueAt(i, 0).toString()), 0);
        }
                
        //Set Any in Bookmarks to Number
        for (int i = 0; i < BookmarkTable.getRowCount(); i++) {
            SystemExecutive.updateSystemExecutiveBookmarkOrder(Integer.valueOf(BookmarkTable.getValueAt(i, 0).toString()), (i + 1));
        }
    }

    //------------------ Copied from Stack Overflow: Drag & Drop Code --------------------------
    abstract class StringTransferHandler extends TransferHandler {

        protected abstract String exportString(JComponent c);

        protected abstract void importString(JComponent c, String str);

        protected abstract void cleanup(JComponent c, boolean remove);

        @Override
        protected Transferable createTransferable(JComponent c) {
            return new StringSelection(exportString(c));
        }

        @Override
        public int getSourceActions(JComponent c) {
            return COPY_OR_MOVE;
        }

        @Override
        public boolean importData(JComponent c, Transferable t) {
            if (canImport(c, t.getTransferDataFlavors())) {
                try {
                    String str = (String) t.getTransferData(DataFlavor.stringFlavor);
                    importString(c, str);
                    return true;
                } catch (UnsupportedFlavorException | IOException ufe) {
                }
            }
            return false;
        }

        @Override
        protected void exportDone(JComponent c, Transferable data, int action) {
            cleanup(c, action == MOVE);
        }

        @Override
        public boolean canImport(JComponent c, DataFlavor[] flavors) {
            for (DataFlavor flavor : flavors) {
                if (DataFlavor.stringFlavor.equals(flavor)) {
                    return true;
                }
            }
            return false;
        }
    }

    class TableTransferHandler extends StringTransferHandler {

        public JTable target;
        public int[] rows = null;
        public int addIndex = -1; //Location where items were added
        public int addCount = 0;  //Number of items added.

        @Override
        protected String exportString(JComponent c) {
            JTable table = (JTable) c;
            rows = table.getSelectedRows();
            int colCount = table.getColumnCount();
            StringBuilder buff = new StringBuilder();
            for (int i = 0; i < rows.length; i++) {
                for (int j = 0; j < colCount; j++) {
                    Object val = table.getValueAt(rows[i], j);
                    buff.append(val == null ? "" : val.toString());
                    if (j != colCount - 1) {
                        buff.append(",");
                    }
                }
                if (i != rows.length - 1) {
                    buff.append("\n");
                }
            }
            return buff.toString();
        }

        @Override
        protected void importString(JComponent c, String str) {
            target = (JTable) c;
            DefaultTableModel model = (DefaultTableModel) target.getModel();
            int index = target.getSelectedRow();
            //Prevent the user from dropping data back on itself.
            //For example, if the user is moving rows #4,#5,#6 and #7 and
            //attempts to insert the rows after row #5, this would
            //be problematic when removing the original rows.
            //So this is not allowed.
            if (rows != null && index >= rows[0] - 1
                    && index <= rows[rows.length - 1]) {
                rows = null;
                return;
            }
            int max = model.getRowCount();
            if (index < 0) {
                index = max;
            } else {
                index++;
                if (index > max) {
                    index = max;
                }
            }
            addIndex = index;
            String[] values = str.split("\n");
            addCount = values.length;
            int colCount = target.getColumnCount();
            for (String value : values) {
                model.insertRow(index++, value.split(","));
            }
            //If we are moving items around in the same table, we
            //need to adjust the rows accordingly, since those
            //after the insertion point have moved.
            if (rows != null && addCount > 0) {
                for (int i = 0; i < rows.length; i++) {
                    if (rows[i] > addIndex) {
                        rows[i] += addCount;
                    }
                }
            }
        }

        @Override
        protected void cleanup(JComponent c, boolean remove) {
            JTable source = (JTable) c;
            if (remove && rows != null) {
                DefaultTableModel model
                        = (DefaultTableModel) source.getModel();
                for (int i = rows.length - 1; i >= 0; i--) {
                    model.removeRow(rows[i]);
                }
            }
            rows = null;
            addCount = 0;
            addIndex = -1;
        }
    }

    //--------------------------------------------
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        saveButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        AvailableTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        BookmarkTable = new javax.swing.JTable();
        ToBookmarkButton = new javax.swing.JButton();
        ToAvailableButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        titleLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("<<TITLE>>");

        closeButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        saveButton.setText("Save");
        saveButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                saveButtonActionPerformed(evt);
            }
        });

        AvailableTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Position", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        AvailableTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(AvailableTable);
        if (AvailableTable.getColumnModel().getColumnCount() > 0) {
            AvailableTable.getColumnModel().getColumn(0).setResizable(false);
            AvailableTable.getColumnModel().getColumn(1).setResizable(false);
            AvailableTable.getColumnModel().getColumn(2).setResizable(false);
        }

        BookmarkTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Position", "Name"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        BookmarkTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(BookmarkTable);
        if (BookmarkTable.getColumnModel().getColumnCount() > 0) {
            BookmarkTable.getColumnModel().getColumn(0).setResizable(false);
            BookmarkTable.getColumnModel().getColumn(1).setResizable(false);
            BookmarkTable.getColumnModel().getColumn(2).setResizable(false);
        }

        ToBookmarkButton.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ToBookmarkButton.setText(">");
        ToBookmarkButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        ToBookmarkButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToBookmarkButtonActionPerformed(evt);
            }
        });

        ToAvailableButton.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        ToAvailableButton.setText("<");
        ToAvailableButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ToAvailableButtonActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Bookmarks In Order (Drag & Drop to reorder)");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Available Board Members");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 19, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(saveButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ToAvailableButton, javax.swing.GroupLayout.DEFAULT_SIZE, 90, Short.MAX_VALUE)
                            .addComponent(ToBookmarkButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 452, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1)
                    .addComponent(jLabel2))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(ToBookmarkButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(ToAvailableButton, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(91, 91, 91)))
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(saveButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        int answer = WebOptionPane.showConfirmDialog(this, "Are you sure you wish to close this window. Any unsaved information will be lost.", "Cancel", WebOptionPane.YES_NO_OPTION);
        if (answer == WebOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_closeButtonActionPerformed

    private void saveButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_saveButtonActionPerformed
        saveInformation();
        this.dispose();
    }//GEN-LAST:event_saveButtonActionPerformed

    private void ToBookmarkButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToBookmarkButtonActionPerformed
        if (AvailableTable.getSelectedRow() > -1) {
            int id = (Integer) AvailableTable.getValueAt(AvailableTable.getSelectedRow(), 0);
            String position = (String) AvailableTable.getValueAt(AvailableTable.getSelectedRow(), 1);
            String name = (String) AvailableTable.getValueAt(AvailableTable.getSelectedRow(), 2);

            modelBookmark.addRow(new Object[]{
                id,
                position,
                name
            });

            modelAvail.removeRow(AvailableTable.getSelectedRow());
        }
    }//GEN-LAST:event_ToBookmarkButtonActionPerformed

    private void ToAvailableButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ToAvailableButtonActionPerformed
        if (BookmarkTable.getSelectedRow() > -1) {
            int id = (Integer) BookmarkTable.getValueAt(BookmarkTable.getSelectedRow(), 0);
            String position = (String) BookmarkTable.getValueAt(BookmarkTable.getSelectedRow(), 1);
            String name = (String) BookmarkTable.getValueAt(BookmarkTable.getSelectedRow(), 2);

            modelAvail.addRow(new Object[]{
                id,
                position,
                name
            });
            modelBookmark.removeRow(BookmarkTable.getSelectedRow());
        }
    }//GEN-LAST:event_ToAvailableButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable AvailableTable;
    private javax.swing.JTable BookmarkTable;
    private javax.swing.JButton ToAvailableButton;
    private javax.swing.JButton ToBookmarkButton;
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JButton saveButton;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
