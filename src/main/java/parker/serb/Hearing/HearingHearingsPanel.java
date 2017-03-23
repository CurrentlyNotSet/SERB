/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.Hearing;

import java.awt.Color;
import java.awt.Component;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.HearingHearing;
import parker.serb.sql.User;

/**
 *
 * @author parkerjohnston
 */
public class HearingHearingsPanel extends javax.swing.JPanel {

    /**
     * Creates new form CMDSHearingsPanel
     */
    public HearingHearingsPanel() {
        initComponents();
        addRenderer();
        setTableColumnWidth();
        addListeners();
    }
    
    private void addRenderer() {
        hearingTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
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
        hearingTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(hearingTable.getSelectionModel().isSelectionEmpty()) {
                Global.root.getjButton9().setEnabled(false);
                Global.root.getjButton9().setVisible(false);
            } else {
                Global.root.getjButton9().setEnabled(true);
                Global.root.getjButton9().setVisible(true);
            }
        });
    }
    
    public void removeHearing() {
        String id = hearingTable.getValueAt(hearingTable.getSelectedRow(), 0).toString();
        String hearingDate = hearingTable.getValueAt(hearingTable.getSelectedRow(), 1).toString();
        String hearingType = hearingTable.getValueAt(hearingTable.getSelectedRow(), 2).toString();
        String hearingRoom = hearingTable.getValueAt(hearingTable.getSelectedRow(), 3).toString();
        
        new HearingRemoveHearingDialog(Global.root, true, id, hearingDate, hearingType, hearingRoom);
        
        loadInformation();
    }
    
    private void setTableColumnWidth() {
        hearingTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        hearingTable.getColumnModel().getColumn(0).setMinWidth(0);
        hearingTable.getColumnModel().getColumn(0).setMaxWidth(0);
    }
    
    public void clearTable() {
        DefaultTableModel model = (DefaultTableModel) hearingTable.getModel();
        model.setRowCount(0);
    }
    
    public void loadInformation() {
        DefaultTableModel model = (DefaultTableModel) hearingTable.getModel();
        model.setRowCount(0);
        
         List hearing = HearingHearing.loadHearingsByCaseNumber();
        
        for (Object hearing1 : hearing) {
            HearingHearing act = (HearingHearing) hearing1;
            model.addRow(new Object[] {act.id, act.hearingDateTime, act.hearingType, act.room, User.getNameByID(act.aljID), act.comments});
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

        jScrollPane1 = new javax.swing.JScrollPane();
        hearingTable = new javax.swing.JTable();

        hearingTable.setAutoCreateRowSorter(true);
        hearingTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Hearing Date", "Hearing Type", "Hearing Room", "ALJ", "Comments"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        hearingTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                hearingTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(hearingTable);
        if (hearingTable.getColumnModel().getColumnCount() > 0) {
            hearingTable.getColumnModel().getColumn(0).setResizable(false);
            hearingTable.getColumnModel().getColumn(1).setResizable(false);
            hearingTable.getColumnModel().getColumn(2).setResizable(false);
            hearingTable.getColumnModel().getColumn(3).setResizable(false);
            hearingTable.getColumnModel().getColumn(4).setResizable(false);
            hearingTable.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    private void hearingTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_hearingTableMouseClicked
        if(evt.getClickCount() == 2) {
            new HearingUpdateHearingDialog(Global.root, true,
                hearingTable.getValueAt(hearingTable.getSelectedRow(), 0).toString(),
                hearingTable.getValueAt(hearingTable.getSelectedRow(), 5).toString(),  
                hearingTable.getValueAt(hearingTable.getSelectedRow(), 2).toString(),
                hearingTable.getValueAt(hearingTable.getSelectedRow(), 1).toString()
            );
            loadInformation();
        }
    }//GEN-LAST:event_hearingTableMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable hearingTable;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
