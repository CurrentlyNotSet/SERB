/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.recordRetention;

import com.alee.extended.date.WebCalendar;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.text.NumberFormat;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.util.FileService;
import parker.serb.util.ImageIconRenderer;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author andrew.schmidt
 */
public class RecordRetentionMainDialog extends javax.swing.JFrame {
    
    /**
     * Creates new form RecordRetentionMainDialog
     *
     * @param parent
     * @param modal
     */
    public RecordRetentionMainDialog(java.awt.Frame parent, boolean modal) {
        initComponents();
        addRenderer();
        setActive();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void addRenderer() {
        jTable1.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private final DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
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

    private void setActive() {
        jPanel1.setVisible(false);
        addListeners();
        setTableSize();
        loadSectionComboBox();
    }

    private void addListeners() {
        startDateField.addDateSelectionListener((Date date) -> {
            if (!startDateField.getText().equals("")
                    && !endDateField.getText().equals("")
                    && !sectionComboBox.getSelectedItem().toString().equals("")) {
                loadTableThread();
            }
        });

        endDateField.addDateSelectionListener((Date date) -> {
            if (!startDateField.getText().equals("")
                    && !endDateField.getText().equals("")
                    && !sectionComboBox.getSelectedItem().toString().equals("")) {
                
                loadTableThread();
            }
        });
    }

    private void loadSectionComboBox() {
        sectionComboBox.addItem("");
        sectionComboBox.addItem("CMDS");
        sectionComboBox.addItem("MED");
        sectionComboBox.addItem("ORG");
        sectionComboBox.addItem("ULP");
    }

    private void setTableSize() {
        //ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
        
        //CheckBox
        jTable1.getColumnModel().getColumn(1).setMinWidth(55);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(55);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(55);

        // Document Icon
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(25);
        jTable1.getColumnModel().getColumn(2).setMinWidth(25);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(25);
        
        //Case Number
        jTable1.getColumnModel().getColumn(3).setMinWidth(125);
        jTable1.getColumnModel().getColumn(3).setPreferredWidth(125);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(125);
        
        //Date
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(140);
        jTable1.getColumnModel().getColumn(4).setMinWidth(140);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(140);

        //User
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(7).setMinWidth(150);
        jTable1.getColumnModel().getColumn(7).setMaxWidth(150);
    }

    private void loadTableThread() {
        setPanelEnabled(false);
        
        Thread temp = new Thread(() -> {
            loadTable();
            setPanelEnabled(true);
        });
        temp.start();
    }
   
    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        List<Activity> caseList = null;
        String section = sectionComboBox.getSelectedItem().toString().trim();
        
        switch (section) {
            case "ORG":
                jTable1.getColumnModel().getColumn(3).setHeaderValue("Org Number");
                break;
            default:
                jTable1.getColumnModel().getColumn(3).setHeaderValue("Case Number");
                break;
        }
        
        countLabel.setText("Gathering Entries");
        
        switch (section) {
            case "CMDS":
                //caseList = Activity.loadPurgeCMDSActivities();
                break;
            case "MED":
                caseList = Activity.loadPurgeMEDActivities(startDateField.getDate(), endDateField.getDate());
                break;
            case "ORG":
                caseList = Activity.loadPurgeORGActivities();
                break;
            case "ULP":
                caseList = Activity.loadPurgeULPActivities();
                break;
        }
        
        countLabel.setText("Populating Table");
        
        if (caseList != null){
            for (Activity item : caseList) {
                String caseNumber = "";
                
                if (section.equals("ORG")){
                    caseNumber = item.caseNumber;
                } else {
                    caseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(
                        item.caseYear, item.caseType, item.caseMonth, item.caseNumber);
                }
                

                if (item.fileName != null){
                    jTable1.getColumnModel().getColumn(2).setCellRenderer(new ImageIconRenderer());
                }
                model.addRow(new Object[]{
                    item,
                    item.fileName == null,
                    item.fileName == null ? "" : item.fileName,
                    caseNumber,
                    item.date,
                    item.action,
                    item.from,
                    item.user
                });
            }
            countLabel.setText("Count: " + NumberFormat.getIntegerInstance().format(caseList.size()));
        } else {
            countLabel.setText("No Entries");
        }
    }

    private void setPanelEnabled(boolean enabled){
        closeButton.setEnabled(enabled);
        purgeButton.setEnabled(enabled);
        startDateField.setEnabled(enabled);
        endDateField.setEnabled(enabled);
        sectionComboBox.setEnabled(enabled);
        jTable1.setEnabled(enabled);
        jPanel1.setVisible(!enabled);
    }
    
    
    private void openFile(MouseEvent evt){
        if (jTable1.getSelectedRow() > -1 && jTable1.getSelectedColumn() != 1 && evt.getClickCount() == 2) {
            Activity rowObject = (Activity) jTable1.getValueAt(jTable1.getSelectedRow(), 0);
            String section = sectionComboBox.getSelectedItem().toString().trim();

            if (!rowObject.fileName.equals("")) {
                switch (section) {
                    case "ORG":
                        FileService.openFileWithORGNumber("ORG", rowObject.caseNumber, rowObject.fileName);
                        break;
                    case "CSC":
                    case "Civil Service Commission":
                        FileService.openFileWithORGNumber("CSC", rowObject.caseNumber, rowObject.fileName);
                        break;
                    default:
                        FileService.openFileWithCaseNumber(section, 
                                rowObject.caseYear, rowObject.caseType, rowObject.caseMonth, rowObject.caseNumber, 
                                rowObject.fileName);
                        break;
                }
            }
        }
    }
    
    private void purgeDocumentsThread() {
        setPanelEnabled(false);
        
        Thread temp = new Thread(() -> {
            purgeRecords();
            setPanelEnabled(true);
            WebOptionPane.showMessageDialog(Global.root, "Record Purge Complete", "Purged", WebOptionPane.INFORMATION_MESSAGE);
        });
        temp.start();
    } 
    
    private void purgeRecords(){
        //TODO: Need Direction from Justin in Order to Complete
    }
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        closeButton = new javax.swing.JButton();
        purgeButton = new javax.swing.JButton();
        sectionComboBox = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jPanel1 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        countLabel = new javax.swing.JLabel();
        startDateField = new com.alee.extended.date.WebDateField();
        jLabel2 = new javax.swing.JLabel();
        endDateField = new com.alee.extended.date.WebDateField();
        jLabel4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Record Retention");

        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        purgeButton.setText("Purge Records");
        purgeButton.setEnabled(false);
        purgeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                purgeButtonActionPerformed(evt);
            }
        });

        sectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionComboBoxActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 16)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Record Retention");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Section:");

        jLabel5.setBackground(new java.awt.Color(255, 255, 255));
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jLabel5, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 513, Short.MAX_VALUE)
        );

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Object", "Purge", "", "Case Number", "Date", "Activity", "From", "User"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class
            };
            boolean[] canEdit = new boolean [] {
                false, true, false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
            jTable1.getColumnModel().getColumn(1).setResizable(false);
            jTable1.getColumnModel().getColumn(2).setResizable(false);
            jTable1.getColumnModel().getColumn(3).setResizable(false);
            jTable1.getColumnModel().getColumn(4).setResizable(false);
        }

        jLayeredPane1.setLayer(jPanel1, javax.swing.JLayeredPane.DEFAULT_LAYER);
        jLayeredPane1.setLayer(jScrollPane1, javax.swing.JLayeredPane.DEFAULT_LAYER);

        org.jdesktop.layout.GroupLayout jLayeredPane1Layout = new org.jdesktop.layout.GroupLayout(jLayeredPane1);
        jLayeredPane1.setLayout(jLayeredPane1Layout);
        jLayeredPane1Layout.setHorizontalGroup(
            jLayeredPane1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jLayeredPane1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 1062, Short.MAX_VALUE))
        );
        jLayeredPane1Layout.setVerticalGroup(
            jLayeredPane1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .add(jLayeredPane1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                .add(org.jdesktop.layout.GroupLayout.TRAILING, jScrollPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
        );

        countLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        countLabel.setText("No Entries");

        startDateField.setEditable(false);
        startDateField.setCaretColor(new java.awt.Color(0, 0, 0));
        startDateField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        startDateField.setDateFormat(Global.mmddyyyy);

        startDateField.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );

            jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            jLabel2.setText("Begin Date:");

            endDateField.setEditable(false);
            endDateField.setCaretColor(new java.awt.Color(0, 0, 0));
            endDateField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            endDateField.setDateFormat(Global.mmddyyyy);

            endDateField.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );

                jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                jLabel4.setText("End Date:");

                org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(layout.createSequentialGroup()
                        .addContainerGap()
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLayeredPane1)
                            .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(layout.createSequentialGroup()
                                .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 64, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(sectionComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 161, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(36, 36, 36)
                                .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 70, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(startDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(38, 38, 38)
                                .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 60, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(endDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 150, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .add(countLabel, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 136, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                            .add(layout.createSequentialGroup()
                                .add(closeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 100, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED, 812, Short.MAX_VALUE)
                                .add(purgeButton, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 125, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                );

                layout.linkSize(new java.awt.Component[] {closeButton, purgeButton}, org.jdesktop.layout.GroupLayout.HORIZONTAL);

                layout.setVerticalGroup(
                    layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                        .addContainerGap()
                        .add(jLabel1)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(endDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel4))
                            .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                                .add(sectionComboBox, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel3)
                                .add(countLabel)
                                .add(startDateField, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .add(jLabel2)))
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jLayeredPane1)
                        .add(18, 18, 18)
                        .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(closeButton)
                            .add(purgeButton))
                        .addContainerGap())
                );

                layout.linkSize(new java.awt.Component[] {jLabel3, sectionComboBox}, org.jdesktop.layout.GroupLayout.VERTICAL);

                layout.linkSize(new java.awt.Component[] {jLabel2, startDateField}, org.jdesktop.layout.GroupLayout.VERTICAL);

                layout.linkSize(new java.awt.Component[] {endDateField, jLabel4}, org.jdesktop.layout.GroupLayout.VERTICAL);

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_closeButtonActionPerformed

    private void purgeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_purgeButtonActionPerformed
        int answer = WebOptionPane.showConfirmDialog(this, "Are you sure you want to purge these records?",
                "Purge?", WebOptionPane.YES_NO_OPTION);
        if (answer == WebOptionPane.YES_OPTION) {
            purgeDocumentsThread();
        }
    }//GEN-LAST:event_purgeButtonActionPerformed

    private void sectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionComboBoxActionPerformed
        if (!startDateField.getText().equals("") && !endDateField.getText().equals("") && !sectionComboBox.getSelectedItem().toString().equals("")){
            loadTableThread();
        }
    }//GEN-LAST:event_sectionComboBoxActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        openFile(evt);
    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JLabel countLabel;
    private com.alee.extended.date.WebDateField endDateField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JButton purgeButton;
    private javax.swing.JComboBox sectionComboBox;
    private com.alee.extended.date.WebDateField startDateField;
    // End of variables declaration//GEN-END:variables
}
