/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.party;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.CaseParty;
import parker.serb.sql.Party;


/**
 *
 * @author parker
 */
public class PartySearchDialog extends javax.swing.JDialog {

    List parties;
    /**
     * Creates new form PartySearchDialog
     */
    public PartySearchDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addRenderer();
        setTableColumnWidths();
        addListeners();
        loadParties();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addRenderer() {
        jTable1.setDefaultRenderer(Object.class, new TableCellRenderer(){
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

    private void setTableColumnWidths() {
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(200);
        jTable1.getColumnModel().getColumn(1).setMinWidth(200);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(200);
        jTable1.getColumnModel().getColumn(5).setPreferredWidth(125);
        jTable1.getColumnModel().getColumn(5).setMinWidth(125);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(125);
    }

    private void addListeners() {
        searchTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                searchParties();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchParties();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchParties();
            }
        });

        jTable1.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
//                    if(duplicateParty()) {
                        PartyTypeSelectionPanel partySelector = new PartyTypeSelectionPanel(Global.root, true, getPartyName(), getID());
                        if(partySelector.selected) {
                            dispose();
                        }
                        partySelector.dispose();
//                    } else {
//                        new DuplicatePartyDialog((JFrame) Global.root.getParent(), true);
//                    }
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

    private boolean duplicateParty() {
        return CaseParty.duplicateParty(getID());
    }

    private String getPartyName() {
        return jTable1.getValueAt(jTable1.getSelectedRow(), jTable1.getColumn("Name").getModelIndex()).toString().equals("") ? jTable1.getValueAt(jTable1.getSelectedRow(), jTable1.getColumn("Company").getModelIndex()).toString() : jTable1.getValueAt(jTable1.getSelectedRow(), jTable1.getColumn("Name").getModelIndex()).toString();
    }

    private String getID() {
        return jTable1.getValueAt(jTable1.getSelectedRow(), jTable1.getColumn("ID").getModelIndex()).toString();
    }

    private void loadParties() {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        parties = Party.loadAllParties();

        for(Object party: parties) {
            Party partyInformation = (Party) party;
            model.addRow(new Object[] {partyInformation.id,
                (partyInformation.prefix.equals("") ? "" : (partyInformation.prefix + " "))
                        + (partyInformation.firstName.equals("") ? "" : (partyInformation.firstName + " "))
                        + (partyInformation.middleInitial.equals("") ? "" : (partyInformation.middleInitial + ". "))
                        + (partyInformation.lastName.equals("") ? "" : (partyInformation.lastName))
                        + (partyInformation.suffix.equals("") ? "" : (" " + partyInformation.suffix))
                        + (partyInformation.nameTitle.equals("") ? "" : (", " + partyInformation.nameTitle)),
                partyInformation.companyName,
                partyInformation.address1
                        + (partyInformation.address2.equals("") ? "" : (", " + partyInformation.address2))
                        + (partyInformation.address3.equals("") ? "" : (", " + partyInformation.address3))
                        + (partyInformation.city.equals("") ? "" : (", " + partyInformation.city))
                        + (partyInformation.stateCode.equals("") ? "" : (", " + partyInformation.stateCode))
                        + (partyInformation.zipCode.equals("") ? "" : (", " + partyInformation.zipCode)),
                partyInformation.emailAddress,
                partyInformation.phone1});
        }
    }

    private void searchParties() {

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for(Object party: parties) {
            Party partyInformation = (Party) party;

            String partyFirstAndLastName = partyInformation.firstName.toLowerCase() + " " + partyInformation.lastName.toLowerCase();
            String partyFirstMiddleLastName =
                    partyInformation.firstName.toLowerCase() +
                    " " + partyInformation.middleInitial.toLowerCase() + ". "+
                    partyInformation.lastName.toLowerCase();

            if(partyInformation.firstName.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.lastName.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyFirstAndLastName.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyFirstMiddleLastName.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.companyName.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.address1.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.address2.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.address3.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.city.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.stateCode.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.zipCode.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.emailAddress.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || partyInformation.phone1.contains(searchTextBox.getText().toLowerCase()))
            model.addRow(new Object[] {partyInformation.id,
               (partyInformation.prefix.equals("") ? "" : (partyInformation.prefix + " "))
                        + (partyInformation.firstName.equals("") ? "" : (partyInformation.firstName + " "))
                        + (partyInformation.middleInitial.equals("") ? "" : (partyInformation.middleInitial + ". "))
                        + (partyInformation.lastName.equals("") ? "" : (partyInformation.lastName))
                        + (partyInformation.suffix.equals("") ? "" : (" " + partyInformation.suffix))
                        + (partyInformation.nameTitle.equals("") ? "" : (", " + partyInformation.nameTitle)),
                partyInformation.companyName,
                partyInformation.address1
                        + (partyInformation.address2.equals("") ? "" : (", " + partyInformation.address2))
                        + (partyInformation.address3.equals("") ? "" : (", " + partyInformation.address3))
                        + (partyInformation.city.equals("") ? "" : (", " + partyInformation.city))
                        + (partyInformation.stateCode.equals("") ? "" : (", " + partyInformation.stateCode))
                        + (partyInformation.zipCode.equals("") ? "" : (", " + partyInformation.zipCode)),
                partyInformation.emailAddress,
                partyInformation.phone1});
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
        searchTextBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton2 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Search Parties");

        jLabel2.setText("Search:");

        jButton1.setText("New");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Company", "Address", "Email ", "Phone"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
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

        jButton2.setText("Close");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1070, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1))
                    .addComponent(jButton2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(searchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 285, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton2)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        dispose();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        CreateNewPartyDialog newParty = new CreateNewPartyDialog((JFrame) Global.root.getParent(), true);
        parties = Party.loadAllParties();
        searchTextBox.setText(newParty.getName());
        newParty.dispose();
        searchParties();
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables
}
