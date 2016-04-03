/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.party;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.CaseParty;

//TODO: Implement duplicate party catch

/**
 *
 * @author parker
 */
public class PartiesPanel extends javax.swing.JPanel {

    List caseParties;
    /**
     * Creates new form PartiesPanel
     */
    public PartiesPanel() {
        initComponents();
        addListeners();
        setTableColumnWidths();
    }
    
    private void setTableColumnWidths() {
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
    }
    
    private void addListeners() {
        jTable1.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(jTable1.getSelectionModel().isSelectionEmpty()) {
                Global.root.getjButton9().setEnabled(false);
            } else {
                Global.root.getjButton9().setEnabled(true);
            }
        });
        
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                loadPartySearch(jTextField1.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadPartySearch(jTextField1.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadPartySearch(jTextField1.getText().trim());
            }
        });
        
        jTable1.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    new ViewUpdatePartyPanel((JFrame) Global.root.getParent(), true, jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                    loadAllParties();
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
    
    private void loadPartySearch(String searchTerm) {
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        for(Object caseParty: caseParties) {
            CaseParty partyInformation = (CaseParty) caseParty;
            if(partyInformation.name.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.type.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.phoneNumber.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.email.toLowerCase().contains(searchTerm.toLowerCase())) {
                model.addRow(new Object[] {partyInformation.id, partyInformation.name, partyInformation.type, partyInformation.phoneNumber, partyInformation.email});
            }
        }
    }
    
    public void clearAll() {
        jTextField1.setText("");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
    }
    
    public void loadAllParties() {
        jTextField1.setText("");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        caseParties = CaseParty.loadPartiesByCase();
        
        for(Object caseParty: caseParties) {
            CaseParty partyInformation = (CaseParty) caseParty;
            model.addRow(new Object[] {partyInformation.id, partyInformation.name, partyInformation.type, partyInformation.phoneNumber, partyInformation.email});
        }
    }
    
    public void removeParty() {
        new DeletePartyDialog((JFrame) Global.root.getParent(),
                true,
                jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString(),
                jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString(),
                jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString());
        
        loadAllParties();
    }

    public JTable getjTable1() {
        return jTable1;
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
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        jLabel1.setText("Search:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Type", "Phone Number", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
        }

        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTextField1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 279, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
