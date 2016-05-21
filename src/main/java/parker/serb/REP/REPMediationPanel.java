/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.REPMediation;
import parker.serb.util.FileService;

//TODO: Investigate File Icon in Table
//TODO: Add notification for no file with 
//TODO: Remove the loadAllActivity method to be used with loadActivity(String)

/**
 *
 * @author parker
 */
public class REPMediationPanel extends javax.swing.JPanel {

    List mediation;
    
    /**
     * Creates new form ActivityPanel
     */
    public REPMediationPanel() {
        initComponents();
//        setTableColumnWidths();
        addListeners();
    }
    
    private void addListeners() {
        searchTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
//                loadActivity(searchTextBox.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
//                loadActivity(searchTextBox.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
//                loadActivity(searchTextBox.getText().trim());
            }
        });
        
        mediationTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    new REPUpdateMediationDialog((JFrame) Global.root.getRootPane().getParent(),
                            true,
                            mediationTable.getValueAt(mediationTable.getSelectedRow(),0).toString());
                    loadAllMediations();
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
        
        mediationTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(mediationTable.getSelectionModel().isSelectionEmpty()) {
                Global.root.getjButton9().setEnabled(false);
            } else {
                Global.root.getjButton9().setEnabled(true);
            }
        });
    }
    
//    private void setTableColumnWidths() {
//        actvityTable.getColumnModel().getColumn(0).setPreferredWidth(175);
//        actvityTable.getColumnModel().getColumn(0).setMinWidth(175);
//        actvityTable.getColumnModel().getColumn(0).setMaxWidth(175);
//        actvityTable.getColumnModel().getColumn(1).setPreferredWidth(200);
//        actvityTable.getColumnModel().getColumn(1).setMinWidth(200);
//        actvityTable.getColumnModel().getColumn(1).setMaxWidth(200);
//        actvityTable.getColumnModel().getColumn(3).setPreferredWidth(0);
//        actvityTable.getColumnModel().getColumn(3).setMinWidth(0);
//        actvityTable.getColumnModel().getColumn(3).setMaxWidth(0);
//    }
    
    private void loadActivity() {
        
//        DefaultTableModel model = (DefaultTableModel) mediationTable.getModel();
//        model.setRowCount(0);
//        
//        for (Object activty1 : activty) {
//            Activity act = (Activity) activty1;
//            if(act.action.toLowerCase().contains(searchTerm.toLowerCase())
//                    || act.user.toLowerCase().contains(searchTerm.toLowerCase())) {
//                model.addRow(new Object[] {act.date, act.action, act.user, ""});
//            }
//        }
    }
    
    public void removeMediation() {
        new RemoveMediationDialog((JFrame) Global.root.getRootPane().getParent(),
                true,
                (int)mediationTable.getValueAt(mediationTable.getSelectedRow(), 0));
        loadAllMediations();
    }
    
    public void clearAll() {
        searchTextBox.setText("");
        DefaultTableModel model = (DefaultTableModel) mediationTable.getModel();
        model.setRowCount(0);
    }
    
    public void loadAllMediations() {
        
        searchTextBox.setText("");
        DefaultTableModel model = (DefaultTableModel) mediationTable.getModel();
        model.setRowCount(0);
        
        mediation = REPMediation.loadMediationsByCaseNumber();
        
        for (Object mediation1 : mediation) {
            REPMediation act = (REPMediation) mediation1;
            model.addRow(new Object[] {act.id, act.mediationDate, act.mediationType, act.mediator, act.mediationOutcome});
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
        mediationTable = new javax.swing.JTable();
        clearSearchButton = new javax.swing.JButton();

        jLabel1.setText("Search:");

        mediationTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Date", "Type", "Mediatior", "Outcome"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(mediationTable);
        if (mediationTable.getColumnModel().getColumnCount() > 0) {
            mediationTable.getColumnModel().getColumn(0).setMinWidth(0);
            mediationTable.getColumnModel().getColumn(0).setPreferredWidth(0);
            mediationTable.getColumnModel().getColumn(0).setMaxWidth(0);
            mediationTable.getColumnModel().getColumn(2).setResizable(false);
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
    private javax.swing.JButton clearSearchButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable mediationTable;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables
}
