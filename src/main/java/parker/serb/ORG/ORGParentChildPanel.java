/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ORG;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.ORGParentChildLink;

/**
 *
 * @author parkerjohnston
 */
public class ORGParentChildPanel extends javax.swing.JPanel {

    List parentList;
    List childList;
    /**
     * Creates new form ORGParentChildPanel
     */
    public ORGParentChildPanel() {
        initComponents();
        addRenderer();
        addListeners();
        setColumnSize();
    }
    
    private void addRenderer() {
        parentChildTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
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
        
        parentChildTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(parentChildTable.getSelectedRow() >= 0) {
                    Global.root.getjButton9().setText("Delete");
                    Global.root.getjButton9().setVisible(true);
                    Global.root.getjButton9().setEnabled(true);
                } else {
                    Global.root.getjButton9().setVisible(false);
                    Global.root.getjButton9().setEnabled(false);
                }
            }
        });
    }
    
    private void setColumnSize() {
        parentChildTable.getColumnModel().getColumn(0).setPreferredWidth(175);
        parentChildTable.getColumnModel().getColumn(0).setMinWidth(175);
        parentChildTable.getColumnModel().getColumn(0).setMaxWidth(175);
        parentChildTable.getColumnModel().getColumn(2).setPreferredWidth(0);
        parentChildTable.getColumnModel().getColumn(2).setMinWidth(0);
        parentChildTable.getColumnModel().getColumn(2).setMaxWidth(0);
    }
    
    public void clearAll() {
        DefaultTableModel model = (DefaultTableModel) parentChildTable.getModel();
        model.setRowCount(0);
        
        parentChildTable.getRowSorter().setSortKeys(null);
    }
    
    public void loadInformation() {
        parentChildTable.clearSelection();
        parentList = ORGParentChildLink.loadParentCaseNumbers(Global.caseNumber);
        childList =  ORGParentChildLink.loadChildCaseNumbers(Global.caseNumber);
        
        DefaultTableModel model = (DefaultTableModel) parentChildTable.getModel();
        model.setRowCount(0);
        
        
        
        if(childList.size() > 0) {
            model.addRow(new Object[] {"<html><b>Parent</b></html>", "", ""});
            
            for (Object child : childList) {
                ORGParentChildLink act = (ORGParentChildLink) child;
                model.addRow(new Object[] {act.parentOrgNumber, act.orgName, act.id});
            }
        }
        
        if(parentList.size() > 0 && childList.size() > 0) {
            model.addRow(new Object[] {"", "", ""});
        }
        
        if(parentList.size() > 0) {
            model.addRow(new Object[] {"<html><b>Children</b></html>", "", ""});
            
            for (Object parent : parentList) {
                ORGParentChildLink act = (ORGParentChildLink) parent;
                model.addRow(new Object[] {act.childOrgNumber, act.orgName, act.id});
            }
        }
        
        
    }

    public JTable getParentChildTable() {
        return parentChildTable;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        parentChildTable = new javax.swing.JTable();

        parentChildTable.setAutoCreateRowSorter(true);
        parentChildTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Organization Number", "Organization Name", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        parentChildTable.getTableHeader().setReorderingAllowed(false);
        parentChildTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                parentChildTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(parentChildTable);
        if (parentChildTable.getColumnModel().getColumnCount() > 0) {
            parentChildTable.getColumnModel().getColumn(0).setResizable(false);
            parentChildTable.getColumnModel().getColumn(2).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 322, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void parentChildTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_parentChildTableMouseClicked
        if(evt.getClickCount() == 2) {
            if(!parentChildTable.getValueAt(parentChildTable.getSelectedRow(), 0).toString().trim().equals("")
                    && !parentChildTable.getValueAt(parentChildTable.getSelectedRow(), 0).toString().trim().contains("Parent")
                    && !parentChildTable.getValueAt(parentChildTable.getSelectedRow(), 0).toString().trim().contains("Children"))
            Global.root.getoRGHeaderPanel2().getjComboBox2().setSelectedItem(parentChildTable.getValueAt(parentChildTable.getSelectedRow(), 1));
        }
    }//GEN-LAST:event_parentChildTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable parentChildTable;
    // End of variables declaration//GEN-END:variables
}
