/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.employer;

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
import parker.serb.sql.Employer;

/**
 *
 * @author parkerjohnston
 */
public class employerSearch extends javax.swing.JDialog {

    String employerNumber;
    String employerName;
    List employers;
    String county = "";
    /**
     * Creates new form employerSearch
     * @param parent
     * @param modal
     * @param employerNumber
     */
    public employerSearch(java.awt.Frame parent, boolean modal, String employerNumber) {
        super(parent, modal);
        initComponents();
        addRenderer();
        setDefaultColumnWidth();
        addListeners();
        loadInformation(employerNumber);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    public employerSearch(java.awt.Frame parent, boolean modal, String employerNumber, String passedCounty) {
        super(parent, modal);
        initComponents();
        addRenderer();
        setDefaultColumnWidth();
        addListeners();
        loadInformation(employerNumber, passedCounty);
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    public employerSearch(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addRenderer();
        setDefaultColumnWidth();
        addListeners();
        loadInformation("");
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void addRenderer() {
       employerTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
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
    
    private void setDefaultColumnWidth() {
        employerTable.getColumnModel().getColumn(0).setPreferredWidth(100);
        employerTable.getColumnModel().getColumn(0).setMinWidth(100);
        employerTable.getColumnModel().getColumn(0).setMaxWidth(100);
        employerTable.getColumnModel().getColumn(2).setPreferredWidth(100);
        employerTable.getColumnModel().getColumn(2).setMinWidth(100);
        employerTable.getColumnModel().getColumn(2).setMaxWidth(100);
    }
    
    private void loadInformation(String number) {
        employerNumber = number;
        employers = Employer.loadEmployerList();
        
        DefaultTableModel model = (DefaultTableModel) employerTable.getModel();
        model.setRowCount(0);
        
        for (Object employer : employers) {
            Employer emp = (Employer) employer;
            model.addRow(new Object[] {emp.employerIDNumber, emp.employerName, emp.county});
        }
        
        searchTextBox.setText(number);
    }
    
    private void loadInformation(String number, String passedCounty) {
        employerNumber = number;
        county = passedCounty;
        employers = Employer.loadEmployerList();
        
        loadTable();
        
        searchTextBox.setText(number);
    }
    
    private void loadTable(){
        DefaultTableModel model = (DefaultTableModel) employerTable.getModel();
        model.setRowCount(0);
        
        for (Object employer : employers) {
            Employer emp = (Employer) employer;
            model.addRow(new Object[] {emp.employerIDNumber, emp.employerName, emp.county});
        }
    };
    
    private void addListeners() {
        employerTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                   employerNumber = employerTable.getValueAt(employerTable.getSelectedRow(), 0).toString().trim();
                   employerName = employerTable.getValueAt(employerTable.getSelectedRow(), 1).toString().trim();
                   county = employerName = employerTable.getValueAt(employerTable.getSelectedRow(), 2).toString().trim();
                   setVisible(false);
                } else if (e.getButton() == MouseEvent.BUTTON3){
                    new employerDetail((JFrame) Global.root.getRootPane().getParent(), true, employerTable.getValueAt(employerTable.getSelectedRow(), 0).toString().trim());
                    loadTable();
                    searchEmployers();
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
                searchEmployers();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchEmployers();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchEmployers();
            }
        });
    }
    
    private void searchEmployers() {
        DefaultTableModel model = (DefaultTableModel) employerTable.getModel();
        model.setRowCount(0);
        
        for (Object employer : employers) {
            Employer emp = (Employer) employer;
            if(emp.employerIDNumber.contains(searchTextBox.getText().trim()) ||
                    emp.employerName.toLowerCase().contains(searchTextBox.getText().trim().toLowerCase()) ||
                    emp.county.toLowerCase().contains(searchTextBox.getText().trim().toLowerCase()))
            model.addRow(new Object[] {emp.employerIDNumber, emp.employerName, emp.county});
        }
    }

    public String getEmployerNumber() {
        return employerNumber;
    }

    public String getEmployerName() {
        return employerName;
    }

    public String getCounty() {
        return county;
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
        employerTable = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        NewButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Employer Search");

        jLabel2.setText("Search:");

        employerTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Employer Number", "Employer Name", "County"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(employerTable);

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        NewButton.setText("New");
        NewButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                NewButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 632, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(NewButton, javax.swing.GroupLayout.PREFERRED_SIZE, 67, javax.swing.GroupLayout.PREFERRED_SIZE))
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
                    .addComponent(searchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(NewButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 292, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void NewButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_NewButtonActionPerformed
        employerDetailAdd eda = new employerDetailAdd(null, true);
        
        eda.dispose();
        employers = Employer.loadEmployerList();
        searchTextBox.setText(eda.empName);
        searchEmployers();
    }//GEN-LAST:event_NewButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton NewButton;
    private javax.swing.JTable employerTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables
}
