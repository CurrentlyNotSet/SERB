/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.activity;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.Audit;
import parker.serb.sql.HearingCase;
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
//        actvityTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
//            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
//            @Override
//            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
//                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
//
//                if (!isSelected) {
//                    c.setBackground(row % 2 == 0 ? Color.WHITE : Global.alternateRowColor);
//                }
//                return c;
//            }
//        });
        
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
        
        actvityTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(actvityTable.getSelectedRow() >= 0) {
                    Global.root.getjButton9().setEnabled(true);
                } else {
                    Global.root.getjButton9().setEnabled(false);
                }
            }
        });

                
        actvityTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                String filePath = actvityTable.getValueAt(actvityTable.getSelectedRow(), 4).toString();

                if (e.getClickCount() == 2 && !filePath.equals("") && actvityTable.getSelectedColumn() == 4) {
                    switch (Global.activeSection) {
                        case "ORG":
                            FileService.openFileWithORGNumber("ORG", Global.caseNumber, filePath);
                            break;
                        case "Civil Service Commission":
                            FileService.openFileWithORGNumber("CSC", Global.caseNumber, filePath);
                            break;
                        case "Hearings":
                            FileService.openHearingCaseFile(filePath);
                            break;
                        default:
                            FileService.openFile(filePath);
                            break;
                    }
                } else if (e.getClickCount() == 2 && actvityTable.getSelectedColumn() != 4) {
                    Audit.addAuditEntry("Viewing Activty Detail for ID: " + actvityTable.getValueAt(actvityTable.getSelectedRow(), 5).toString());
                    new DetailedActivityDialog((JFrame) Global.root.getRootPane().getParent(),
                            true,
                            actvityTable.getValueAt(actvityTable.getSelectedRow(), 5).toString());
                    loadAllActivity();
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
        actvityTable.getColumnModel().getColumn(3).setPreferredWidth(200);
        actvityTable.getColumnModel().getColumn(3).setMinWidth(200);
        actvityTable.getColumnModel().getColumn(3).setMaxWidth(200);
        actvityTable.getColumnModel().getColumn(4).setPreferredWidth(25);
        actvityTable.getColumnModel().getColumn(4).setMinWidth(25);
        actvityTable.getColumnModel().getColumn(4).setMaxWidth(25);
        actvityTable.getColumnModel().getColumn(5).setPreferredWidth(0);
        actvityTable.getColumnModel().getColumn(5).setMinWidth(0);
        actvityTable.getColumnModel().getColumn(5).setMaxWidth(0);
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
                if(act.fileName == null) {
                    model.addRow(new Object[] {act.date, act.action, act.comment, act.user, "", act.id});
                } else {
                    model.addRow(new Object[] {act.date, act.action, act.comment, act.user, aboutIcon, act.id});
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
            
            if(act.fileName == null) {
                model.addRow(new Object[] {act.date, act.action, act.comment, act.user, "", act.id});
            } else {
                actvityTable.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());
                model.addRow(new Object[] {act.date, act.action, act.comment, act.user, act.fileName.trim(), act.id});
            }      
        }
    }
    
    public void loadAllHearingActivity() {
        
        searchTextBox.setText("");
        
        DefaultTableModel model = (DefaultTableModel) actvityTable.getModel();
        model.setRowCount(0);
        
        if(HearingCase.getBoardActionPCDate() != null) {
            activty = Activity.loadHearingActivity();
        
            for (Object activty1 : activty) {
                Activity act = (Activity) activty1;

                if(act.fileName == null) {
                    model.addRow(new Object[] {act.date, act.action, act.comment, act.user, "", act.id});
                } else {
                    actvityTable.getColumnModel().getColumn(4).setCellRenderer(new ImageRenderer());
                    model.addRow(new Object[] {act.date, act.action, act.comment, act.user, act.fileName.trim(), act.id});
                }      
            }
        }
    }

    public JTable getActvityTable() {
        return actvityTable;
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
                "Date", "Activity", "Comments", "User", "", "id"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Byte.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        actvityTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(actvityTable);
        if (actvityTable.getColumnModel().getColumnCount() > 0) {
            actvityTable.getColumnModel().getColumn(4).setResizable(false);
            actvityTable.getColumnModel().getColumn(5).setResizable(false);
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
            
//            if (!isSelected) {
//                    lbl.setBackground(row % 2 == 0 ? Color.WHITE : Global.alternateRowColor);
//            } else {
//                lbl.setBackground(table.getSelectionBackground());
//            }

            lbl.setOpaque(true);
            lbl.setText("");
            lbl.setHorizontalAlignment(CENTER);
            return lbl;
        }
    }

}
