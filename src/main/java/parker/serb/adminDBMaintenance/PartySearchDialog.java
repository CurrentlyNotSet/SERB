/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import parker.serb.party.*;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.ActiveStatus;
import parker.serb.sql.Party;
import parker.serb.util.StringUtilities;

/**
 *
 * @author parker
 */
public class PartySearchDialog extends javax.swing.JDialog {

    List<Party> parties;

    /**
     * Creates new form PartySearchDialog
     *
     * @param parent
     * @param modal
     */
    public PartySearchDialog(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setTableColumnWidths();
        loadAndSearch();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void setTableColumnWidths() {
        //ID
        SearchTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        SearchTable.getColumnModel().getColumn(0).setMinWidth(0);
        SearchTable.getColumnModel().getColumn(0).setMaxWidth(0);

        //Active
        SearchTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        SearchTable.getColumnModel().getColumn(1).setMinWidth(60);
        SearchTable.getColumnModel().getColumn(1).setMaxWidth(60);

        //Name
        SearchTable.getColumnModel().getColumn(2).setPreferredWidth(200);
        SearchTable.getColumnModel().getColumn(2).setMinWidth(200);
        SearchTable.getColumnModel().getColumn(2).setMaxWidth(200);

        //Phone
        SearchTable.getColumnModel().getColumn(6).setPreferredWidth(125);
        SearchTable.getColumnModel().getColumn(6).setMinWidth(125);
        SearchTable.getColumnModel().getColumn(6).setMaxWidth(125);
    }

    private String getID() {
        return SearchTable.getValueAt(SearchTable.getSelectedRow(), SearchTable.getColumn("ID").getModelIndex()).toString();
    }

    private void loadAndSearch() {
        Thread temp = new Thread(() -> {
            DefaultTableModel model = (DefaultTableModel) SearchTable.getModel();
            model.setRowCount(0);
            parties = Party.loadAllParties();
            searchParties();
        });
        temp.start();
    }

    private void loadParties() {
        Thread temp = new Thread(() -> {
            parties = Party.loadAllParties();
        });
        temp.start();
    }

    private void searchParties() {
        DefaultTableModel model = (DefaultTableModel) SearchTable.getModel();
        model.setRowCount(0);

        for (Party party : parties) {
            Party partyInfo = party;

            if (searchlimiter(partyInfo)) {
                model.addRow(
                        new Object[]{
                            partyInfo.id,
                            partyInfo.active,
                            StringUtilities.buildFullNameWithTitles(partyInfo),
                            partyInfo.companyName,
                            StringUtilities.buildAddressBlock(partyInfo),
                            partyInfo.emailAddress,
                            partyInfo.phone1
                        }
                );
            }
        }
    }

    private boolean searchlimiter(Party partyInfo) {
        String term = searchTextBox.getText().toLowerCase();
        return partyInfo.firstName.toLowerCase().contains(term)
                || partyInfo.lastName.toLowerCase().contains(term)
                || partyInfo.companyName.toLowerCase().contains(term)
                || partyInfo.address1.toLowerCase().contains(term)
                || partyInfo.address2.toLowerCase().contains(term)
                || partyInfo.address3.toLowerCase().contains(term)
                || partyInfo.city.toLowerCase().contains(term)
                || partyInfo.stateCode.toLowerCase().contains(term)
                || partyInfo.zipCode.toLowerCase().contains(term)
                || partyInfo.emailAddress.toLowerCase().contains(term)
                || partyInfo.phone1.contains(term);
    }

    private void tableClick(java.awt.event.MouseEvent evt) {
        if (evt.getClickCount() == 1) {
            if (SearchTable.getSelectedColumn() == 1) {
                update();
                loadParties();
            }
            EditButton.setEnabled(true);
        } else if (evt.getClickCount() >= 2) {
            EditButtonActionPerformed(null);
        }
    }

    private void update() {
        if (SearchTable.getSelectedRow() > -1) {
            int id = (int) SearchTable.getValueAt(SearchTable.getSelectedRow(), 0);
            boolean active = (boolean) SearchTable.getValueAt(SearchTable.getSelectedRow(), 1);

            ActiveStatus.updateActiveStatus("Party", active, id);
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
        SearchTable = new javax.swing.JTable();
        closeButton = new javax.swing.JButton();
        EditButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Parties Maintenance");

        jLabel2.setText("Search:");

        searchTextBox.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                searchTextBoxCaretUpdate(evt);
            }
        });

        jButton1.setText("New");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        SearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Active", "Name", "Company", "Address", "Email ", "Phone"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false
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

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        EditButton.setText("Edit");
        EditButton.setEnabled(false);
        EditButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EditButtonActionPerformed(evt);
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
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(EditButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 383, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(closeButton)
                    .addComponent(EditButton))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        new CreateNewPartyDialog((JFrame) Global.root.getParent(), true);
        loadAndSearch();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void SearchTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_SearchTableMouseClicked
        tableClick(evt);
    }//GEN-LAST:event_SearchTableMouseClicked

    private void EditButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EditButtonActionPerformed
        ViewUpdatePartyPanel party = new ViewUpdatePartyPanel((JFrame) Global.root.getParent(), true, getID());
        if (party.updateStatus == 1) {
            loadAndSearch();
        }
    }//GEN-LAST:event_EditButtonActionPerformed

    private void searchTextBoxCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_searchTextBoxCaretUpdate
        searchParties();
    }//GEN-LAST:event_searchTextBoxCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton EditButton;
    private javax.swing.JTable SearchTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables
}
