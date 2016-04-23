/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ULP;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.CaseParty;
import parker.serb.sql.ULPCase;

/**
 *
 * @author parkerjohnston
 */
public class ULPCaseSearch extends javax.swing.JDialog {

    DefaultTableModel model;
    List caseList;
    Object[][] tableData;
    /**
     * Creates new form REPCaseSearch
     */
    public ULPCaseSearch(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        jLayeredPane1.moveToFront(jPanel1);
        activity();
        addListeners();
        setLocationRelativeTo(parent);
        setVisible(true);
    }
    
    private void addListeners() {
        caseYearTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                limitCaseList();
            }
        });
        
        caseTypeTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                limitCaseList();
            }
        });
        
        caseMonthTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                limitCaseList();
            }
        });
        
        caseNumberTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                limitCaseList();
            }
        });
        
        searchTextBox.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                limitCaseList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                limitCaseList();
            }
        });
        
        caseSearchTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    setVisible(false);
//                    Global.selectedFullCaseNumber = caseSearchTable.getValueAt(caseSearchTable.getSelectedRow(), 0).toString();
//                    Global.caseNumber = caseSearchTable.getValueAt(caseSearchTable.getSelectedRow(), 0).toString();
                    Global.root.getuLPHeaderPanel1().getjComboBox2().setSelectedItem(caseSearchTable.getValueAt(caseSearchTable.getSelectedRow(), 0).toString());
                    
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
    
    private void activity() {
        Thread temp = new Thread(
            new Runnable() {
                @Override
                public void run() {
                    loadAllCases();
                    enableTextBoxes();
                }
            }
        );
        temp.start();
    }
    
    private void enableTextBoxes() {
        caseYearTextBox.setEnabled(true);
        caseTypeTextBox.setEnabled(true);
        caseMonthTextBox.setEnabled(true);
        caseNumberTextBox.setEnabled(true);
        searchTextBox.setEnabled(true);
    }
    
    private void loadAllCases() {
        
//        model = new DefaultTableModel();
        
        model = new DefaultTableModel() {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }   
        };

       
        model.addColumn("Case Number");
        model.addColumn("Charging Party");
        model.addColumn("Charged Party");
        model.addColumn("Employer Number");
        model.addColumn("Union Number");
        
        caseList = ULPCase.loadULPCases();
        
        for (Object caseItem : caseList) {
            ULPCase act = (ULPCase) caseItem;
            
            List caseParties = CaseParty.loadPartiesByCase(act.caseYear,
                    act.caseType, act.caseMonth, act.caseNumber);
            
            String chargedParty = "";
            String chargingParty = "";
            
            for(Object caseParty: caseParties) {
                    CaseParty partyInformation = (CaseParty) caseParty;
                    
                    String name = (partyInformation.prefix.equals("") ? "" : (partyInformation.prefix + " "))
                        + (partyInformation.firstName.equals("") ? "" : (partyInformation.firstName + " "))
                        + (partyInformation.middleInitial.equals("") ? "" : (partyInformation.middleInitial + ". "))
                        + (partyInformation.lastName.equals("") ? "" : (partyInformation.lastName))
                        + (partyInformation.suffix.equals("") ? "" : (" " + partyInformation.suffix))
                        + (partyInformation.nameTitle.equals("") ? "" : (", " + partyInformation.nameTitle));

                switch (partyInformation.caseRelation) {
                    case "Charging Party":
                        if(chargingParty.equals("")) {
                            chargingParty += name;
                        } else {
                            chargingParty += ", " + name;
                        }
                        break;
                    case "Charged Party":
                        if(chargedParty.equals("")) {
                            chargedParty += name;
                        } else {
                            chargedParty += ", " + name;
                        }
                        break;
                }
            }
            model.addRow(new Object[] {(act.caseYear + "-" + act.caseType + "-" + act.caseMonth + "-" + act.caseNumber)
                    , chargingParty, chargedParty, act.employerIDNumber, act.barginingUnitNo}); 
        }
        getTableData();
        caseSearchTable.setModel(model);
        jLayeredPane1.moveToBack(jPanel1);
        
    }
    
    private void limitCaseList() {
        model.setRowCount(0);
        
        for (int i = 0; i<tableData.length; i++){
//            for (int j x= 0; j<tableData[i].length; j++){
//                if(j == 0) {
                    String[] parsedCaseNumber = String.valueOf(tableData[i][0]).split("-");
                    if(((parsedCaseNumber[0].contains(caseYearTextBox.getText()) && !caseYearTextBox.equals(""))
                        && (parsedCaseNumber[1].toLowerCase().contains(caseTypeTextBox.getText().toLowerCase()) && !caseTypeTextBox.equals(""))
                        && (parsedCaseNumber[2].contains(caseMonthTextBox.getText()) && !caseMonthTextBox.equals(""))
                        && (parsedCaseNumber[3].contains(caseNumberTextBox.getText()) && !caseNumberTextBox.equals("")))
                        && ((tableData[i][1].toString().toLowerCase().contains(searchTextBox.getText().toLowerCase()) && !searchTextBox.equals(""))
                        || (tableData[i][2].toString().toLowerCase().contains(searchTextBox.getText().toLowerCase()) && !searchTextBox.equals(""))
                        || (tableData[i][3].toString().toLowerCase().contains(searchTextBox.getText().toLowerCase()) && !searchTextBox.equals(""))
                        || (tableData[i][4].toString().toLowerCase().contains(searchTextBox.getText().toLowerCase()) && !searchTextBox.equals("")))) {
                        model.addRow(new Object[] {tableData[i][0]
                        , tableData[i][1], tableData[i][2], tableData[i][3], tableData[i][4]}); 
                        
//                    }
                }
//            } 
        }
        caseSearchTable.setModel(model);
    }
    
    public void getTableData() {
        int nRow = model.getRowCount(), nCol = model.getColumnCount();
        tableData = new Object[nRow][nCol];
        for (int i = 0 ; i < nRow ; i++) {
            for (int j = 0 ; j < nCol ; j++) {
                tableData[i][j] = model.getValueAt(i,j);
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
        jLabel2 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        caseSearchTable = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        searchTextBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        caseYearTextBox = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        caseTypeTextBox = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        caseMonthTextBox = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        caseNumberTextBox = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("ULP Case Search");

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Search:");
        jLabel2.setToolTipText("");

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        caseSearchTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case Number", "Charging Party", "Charged Party", "Employer Number", "Union Number"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.String.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        jScrollPane1.setViewportView(caseSearchTable);

        jLayeredPane1.add(jScrollPane1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel4.setBackground(new java.awt.Color(255, 255, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 981, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 981, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 391, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 312, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );

        jLayeredPane1.add(jPanel1);

        searchTextBox.setEnabled(false);

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel3.setText("Case Search:");

        caseYearTextBox.setEnabled(false);
        caseYearTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseYearTextBoxActionPerformed(evt);
            }
        });

        jLabel5.setText("-");

        caseTypeTextBox.setEnabled(false);

        jLabel6.setText("-");

        caseMonthTextBox.setEnabled(false);
        caseMonthTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseMonthTextBoxActionPerformed(evt);
            }
        });

        jLabel7.setText("-");

        caseNumberTextBox.setEnabled(false);
        caseNumberTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                caseNumberTextBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLayeredPane1)
                    .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(caseYearTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel5)
                        .addGap(0, 0, 0)
                        .addComponent(caseTypeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel6)
                        .addGap(0, 0, 0)
                        .addComponent(caseMonthTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, 0)
                        .addComponent(jLabel7)
                        .addGap(0, 0, 0)
                        .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 46, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(searchTextBox)))
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
                    .addComponent(jLabel3)
                    .addComponent(caseYearTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5)
                    .addComponent(caseTypeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(caseMonthTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel7)
                    .addComponent(caseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLayeredPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 391, Short.MAX_VALUE)
                .addGap(11, 11, 11)
                .addComponent(jButton1)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        setVisible(false);
    }//GEN-LAST:event_jButton1ActionPerformed

    private void caseYearTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caseYearTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_caseYearTextBoxActionPerformed

    private void caseMonthTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caseMonthTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_caseMonthTextBoxActionPerformed

    private void caseNumberTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_caseNumberTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_caseNumberTextBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField caseMonthTextBox;
    private javax.swing.JTextField caseNumberTextBox;
    private javax.swing.JTable caseSearchTable;
    private javax.swing.JTextField caseTypeTextBox;
    private javax.swing.JTextField caseYearTextBox;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables

}
