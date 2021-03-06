/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bunumber;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.BargainingUnit;

/**
 *
 * @author parkerjohnston
 */
public class buNumberSearch extends javax.swing.JDialog {

    String buNumber;
    String unitDesc;
    String certStatus;
    List bu;
    
    /**
     * Creates new form employerSearch
     */
    public buNumberSearch(java.awt.Frame parent, boolean modal, String empNumber, String passedBUNumber, String passedDesciption) {
        super(parent, modal);
        initComponents();
        addListeners();
        addRenderer();
        
        loadInformation(passedBUNumber.equals("") ? empNumber : passedBUNumber, passedDesciption);
        
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void addRenderer() {
        buTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
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
    
    private void loadInformation(String number, String desc) {
        buNumber = number;
        unitDesc = desc;
        certStatus = number.contains("-") ? BargainingUnit.getCertStatus(number) : "";
        bu = BargainingUnit.loadBUList();
        
        DefaultTableModel model = (DefaultTableModel) buTable.getModel();
        model.setRowCount(0);
        
        for (Object singleBU : bu) {
            BargainingUnit buInfo = (BargainingUnit) singleBU;
            model.addRow(new Object[] {buInfo.employerNumber + "-" + buInfo.unitNumber,
                buInfo.buEmployerName,
                buInfo.lUnion,
                buInfo.county,
                buInfo.unitDescription,
                buInfo.caseRefYear.equals("") ? "" : (buInfo.caseRefYear + "-" + buInfo.caseRefSection + "-" + buInfo.caseRefMonth + "-" + buInfo.caseRefSequence),
                buInfo.notes,
                buInfo.local,
                buInfo.cert});
        }
        
        searchTextBox.setText(number);
    }
    
    private void addListeners() {
        buTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                   buNumber = buTable.getValueAt(buTable.getSelectedRow(), 0).toString().trim();
                   unitDesc = buTable.getValueAt(buTable.getSelectedRow(), 4).toString().trim();
                   certStatus = buTable.getValueAt(buTable.getSelectedRow(), 8).toString().trim();
                   setVisible(false);
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
        
        searchTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchBU();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchBU();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchBU();
            }
        });
    }
    
    private void searchBU() {
        DefaultTableModel model = (DefaultTableModel) buTable.getModel();
        model.setRowCount(0);
        
        for (Object singleBU : bu) {
            BargainingUnit buInfo = (BargainingUnit) singleBU;
            if((buInfo.employerNumber + "-" + buInfo.unitNumber).contains(searchTextBox.getText().trim()) ||
                buInfo.buEmployerName.toLowerCase().contains(searchTextBox.getText().trim().toLowerCase()))
            {
                model.addRow(new Object[] {buInfo.employerNumber + "-" + buInfo.unitNumber,
                    buInfo.buEmployerName,
                    buInfo.lUnion,
                    buInfo.county,
                    buInfo.unitDescription,
                    buInfo.caseRefYear.equals("") ? "" : (buInfo.caseRefYear + "-" + buInfo.caseRefSection + "-" + buInfo.caseRefMonth + "-" + buInfo.caseRefSequence),
                    buInfo.notes,
                    buInfo.local,
                    buInfo.cert});
            }
        }
    }

    public String getBuNumber() {
        return buNumber;
    }

    public String getUnitDesc() {
        return unitDesc;
    }

    public String getCertStatus() {
        return certStatus;
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
        jScrollPane1 = new javax.swing.JScrollPane();
        buTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("BU Number Search");

        jLabel2.setText("Search:");

        buTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "BU Number", "Employer Name", "International Org.", "County", "Unit Desc.", "Case Ref. ", "Notes", "Local", "Cert. Status"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        buTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(buTable);

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1116, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextBox))
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(searchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 295, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
         setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable buTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables
}
