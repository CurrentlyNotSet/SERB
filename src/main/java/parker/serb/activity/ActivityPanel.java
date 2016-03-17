/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.activity;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import parker.serb.sql.Activity;
import parker.serb.util.FileService;

//TODO: Investigate File Icon in Table
//TODO: Add notification for no file with 
//TODO: Remove the loadAllActivity method to be used with loadActivity(String)

/**
 *
 * @author parker
 */
public class ActivityPanel extends javax.swing.JPanel {

    List activty;
    ImageIcon aboutIcon = new ImageIcon(getClass().getResource("/file-icon.png"));
    
    /**
     * Creates new form ActivityPanel
     */
    public ActivityPanel() {
        initComponents();
        setTableColumnWidths();
        addListeners();
    }
    
    private void addListeners() {
        searchTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                loadActivity(searchTextBox.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadActivity(searchTextBox.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadActivity(searchTextBox.getText().trim());
            }
        });
        
        actvityTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String filePath = actvityTable.getValueAt(actvityTable.getSelectedRow(), 3).toString();
                if(e.getClickCount() == 2 && !filePath.equals("") && actvityTable.getSelectedColumn() == 3) {
                    FileService.openFile(filePath);
                } else if(e.getClickCount() == 2 && actvityTable.getSelectedColumn() != 3) {
                    System.out.println("DETAIL PANEL");
                    //TODO: Create deatil panel for activity will require table changes
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
    
    /**
     * Set the width of all the columns in the table.
     */
    private void setTableColumnWidths() {
        actvityTable.getColumnModel().getColumn(0).setPreferredWidth(175);
        actvityTable.getColumnModel().getColumn(0).setMinWidth(175);
        actvityTable.getColumnModel().getColumn(0).setMaxWidth(175);
        actvityTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        actvityTable.getColumnModel().getColumn(2).setMinWidth(200);
        actvityTable.getColumnModel().getColumn(2).setMaxWidth(200);
        actvityTable.getColumnModel().getColumn(3).setPreferredWidth(25);
        actvityTable.getColumnModel().getColumn(3).setMinWidth(25);
        actvityTable.getColumnModel().getColumn(3).setMaxWidth(25);
    }

    /**
     * Load all activity for the currently selected case.  Takes a term as a 
     * parameter, but is not required.
     * @param searchTerm a string of a value, if blank pass ""
     */
    private void loadActivity(String searchTerm) {
        
        DefaultTableModel model = (DefaultTableModel) actvityTable.getModel();
        model.setRowCount(0);
        
        for (Object activty1 : activty) {
            Activity act = (Activity) activty1;
            if(act.action.toLowerCase().contains(searchTerm.toLowerCase())
                    || act.user.toLowerCase().contains(searchTerm.toLowerCase())) {
                if(act.fileName.equals("")) {
                    model.addRow(new Object[] {act.date, act.action, act.user, ""});
                } else {
                    model.addRow(new Object[] {act.date, act.action, act.user, aboutIcon});
                } 
            }
        }
    }
    
    /**
     * clears the search box and removes all results displayed from the table
     */
    public void clearAll() {
        searchTextBox.setText("");
        DefaultTableModel model = (DefaultTableModel) actvityTable.getModel();
        model.setRowCount(0);
    }
    
    /**
     * loads all activity non limited for the currently selected case
     * Will be removed in future releases
     */
    public void loadAllActivity() {
        
        searchTextBox.setText("");
        
        DefaultTableModel model = (DefaultTableModel) actvityTable.getModel();
        model.setRowCount(0);
        
        activty = Activity.loadCaseNumberActivity("%");
        
        for (Object activty1 : activty) {
            Activity act = (Activity) activty1;
            
            if(act.fileName.trim().equals("")) {
                model.addRow(new Object[] {act.date, act.action, act.user, act.fileName.trim()});
            } else {
                actvityTable.getColumnModel().getColumn(3).setCellRenderer(new ImageRenderer());
                model.addRow(new Object[] {act.date, act.action, act.user, act.fileName.trim()});
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
        searchTextBox = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        actvityTable = new javax.swing.JTable();
        clearSearchButton = new javax.swing.JButton();

        jLabel1.setText("Search:");

        actvityTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date", "Activity", "User", ""
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Byte.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(actvityTable);
        if (actvityTable.getColumnModel().getColumnCount() > 0) {
            actvityTable.getColumnModel().getColumn(3).setResizable(false);
        }

        clearSearchButton.setText("Clear");
        clearSearchButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                clearSearchButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTextBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(clearSearchButton)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 719, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(searchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(clearSearchButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void clearSearchButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_clearSearchButtonActionPerformed
        searchTextBox.setText("");
    }//GEN-LAST:event_clearSearchButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable actvityTable;
    private javax.swing.JButton clearSearchButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables

    
    class ImageRenderer extends DefaultTableCellRenderer {
        JLabel lbl = new JLabel();

        ImageIcon icon = new ImageIcon(getClass().getResource("/file-icon.png"));

        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
            boolean hasFocus, int row, int column) {
            
            if(!value.equals("")) {
                lbl.setIcon(icon);
            } else {
                lbl.setIcon(null);
            }

            lbl.setText("");
            lbl.setHorizontalAlignment(CENTER);
            return lbl;
        }
    }

}
