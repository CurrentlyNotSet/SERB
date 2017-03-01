/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.mailLogViewer;

import com.alee.extended.date.WebCalendar;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.Component;
import java.util.Date;
import java.util.List;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.report.GenerateReport;
import parker.serb.sql.Activity;
import parker.serb.sql.CaseType;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author User
 */
public class MailLogViewerPanel extends javax.swing.JDialog {

    /**
     * Creates new form RequestedReportInformationPanel
     *
     * @param parent
     * @param modal
     */
    public MailLogViewerPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        setRenderer();
        setDefaults();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void setRenderer() {
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

    private void setDefaults() {
        headerLabel.setText(Global.activeSection + " Mail Log Viewer");

        if(Global.activeSection.equals("ORG") || Global.activeSection.equals("Civil Service Commission")){
            jTable1.getColumnModel().getColumn(2).setHeaderValue("Org Number");
        } else if (Global.activeSection.equals("CMDS")){
            jTable1.getColumnModel().getColumn(3).setHeaderValue("ALJ");
        }

        jTable1.getTableHeader().repaint();
        startDateField.setDate(new Date());
        endDateField.setDate(new Date());
        addListeners();
        setColumnSize();
        loadTable();
    }

    private void addListeners() {
        startDateField.addDateSelectionListener((Date date) -> {
            endDateField.setDate(date);
            loadTable();
        });

        endDateField.addDateSelectionListener((Date date) -> {
            loadTable();
        });
    }

    private void setColumnSize() {
        //ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);

        //Date Time
        jTable1.getColumnModel().getColumn(1).setMinWidth(150);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(150);

        //Case Number
        if (Global.activeSection.equals("ORG") || Global.activeSection.equals("Civil Service Commission")){
            jTable1.getColumnModel().getColumn(2).setMinWidth(90);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(90);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(90);
        } else {
            jTable1.getColumnModel().getColumn(2).setMinWidth(125);
            jTable1.getColumnModel().getColumn(2).setPreferredWidth(125);
            jTable1.getColumnModel().getColumn(2).setMaxWidth(125);
        }

        //FROM
        if (Global.activeSection.equals("CMDS")){
            jTable1.getColumnModel().getColumn(4).setMinWidth(0);
            jTable1.getColumnModel().getColumn(4).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(4).setMaxWidth(0);
        }
    }

    private void loadTable(){
        List<Activity> activityList = Activity.loadMailLogBySection(
                Global.SQLDateFormat.format(startDateField.getDate()), Global.SQLDateFormat.format(endDateField.getDate()));

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        for (Activity item : activityList) {
            String number = "";
            if (Global.activeSection.equals("ORG") || Global.activeSection.equals("Civil Service Commission")) {
                number = item.caseNumber;
            } else {
                number = NumberFormatService.generateFullCaseNumberNonGlobal(item.caseYear, item.caseType, item.caseMonth, item.caseNumber);
            }

            model.addRow(new Object[]{
                item.id,
                item.date,
                number,
                item.to,
                item.from,
                item.action,
                item.fileName
            });
        }
        PrintButton.setEnabled(jTable1.getRowCount() > 0);
    }

    private void generateReport() {
        SMDSDocuments doc = new SMDSDocuments();
        doc.section = "ALL";
        doc.fileName = "MailLog.jasper";

        List<String> casetypes = CaseType.getCaseType();

        String sqlWHERE = " Activity.mailLog >= '" + Global.SQLDateFormat.format(startDateField.getDate())
                + "'  AND Activity.mailLog <= '" + Global.SQLDateFormat.format(endDateField.getDate()) + "' "
                    + "AND Activity.fileName IS NOT NULL AND Activity.fileName != '' ";

            if (!casetypes.isEmpty()) {
                sqlWHERE += "AND (";

                for (String casetype : casetypes) {

                    sqlWHERE += " Activity.caseType = '" + casetype + "' OR";
                }

                sqlWHERE = sqlWHERE.substring(0, (sqlWHERE.length() - 2)) + ")";
            }

            sqlWHERE += " ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC, activity.id DESC";

        GenerateReport.generateExactStringReport(sqlWHERE, doc);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        headerLabel = new javax.swing.JLabel();
        startDateField = new com.alee.extended.date.WebDateField();
        jLabel7 = new javax.swing.JLabel();
        endDateField = new com.alee.extended.date.WebDateField();
        jLabel8 = new javax.swing.JLabel();
        CancelButton = new javax.swing.JButton();
        PrintButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Maillog Viewer");
        setResizable(false);

        headerLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText("Mail Log");

        startDateField.setDateFormat(Global.mmddyyyy);

        startDateField.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );

            jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            jLabel7.setText("Start Date:");

            endDateField.setDateFormat(Global.mmddyyyy);

            endDateField.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );

                jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                jLabel8.setText("End Date:");

                CancelButton.setText("Close");
                CancelButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        CancelButtonActionPerformed(evt);
                    }
                });

                PrintButton.setText("Print");
                PrintButton.addActionListener(new java.awt.event.ActionListener() {
                    public void actionPerformed(java.awt.event.ActionEvent evt) {
                        PrintButtonActionPerformed(evt);
                    }
                });

                jTable1.setModel(new javax.swing.table.DefaultTableModel(
                    new Object [][] {

                    },
                    new String [] {
                        "ID", "Date", "Case Number", "Investigator", "From", "Description", "FileName"
                    }
                ) {
                    boolean[] canEdit = new boolean [] {
                        false, false, false, false, false, false, false
                    };

                    public boolean isCellEditable(int rowIndex, int columnIndex) {
                        return canEdit [columnIndex];
                    }
                });
                jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        jTable1MouseClicked(evt);
                    }
                });
                jScrollPane1.setViewportView(jTable1);
                if (jTable1.getColumnModel().getColumnCount() > 0) {
                    jTable1.getColumnModel().getColumn(6).setResizable(false);
                }

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jScrollPane1)
                            .addComponent(headerLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 1082, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(PrintButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGap(290, 290, 290)
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(endDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addContainerGap())
                );

                layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {endDateField, startDateField});

                layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel7, jLabel8});

                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(headerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(endDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8)
                            .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CancelButton)
                            .addComponent(PrintButton))
                        .addContainerGap())
                );

                layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {endDateField, jLabel7, jLabel8, startDateField});

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void PrintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintButtonActionPerformed
        generateReport();
    }//GEN-LAST:event_PrintButtonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (jTable1.getSelectedRow() > -1){
            String fileName = jTable1.getValueAt(jTable1.getSelectedRow(), 6).toString();
            String[] caseNumber = jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString().trim().split("-");

            if(evt.getClickCount() == 2 && !fileName.equals("")) {
                switch (Global.activeSection) {
                    case "ORG":
                        FileService.openFileWithORGNumber("ORG", jTable1.getValueAt(jTable1.getSelectedRow(), 6).toString(), fileName);
                        break;
                    case "Civil Service Commission":
                        FileService.openFileWithORGNumber("CSC", jTable1.getValueAt(jTable1.getSelectedRow(), 6).toString(), fileName);
                        break;
                    default:
                        FileService.openFileWithCaseNumber(Global.activeSection, caseNumber[0], caseNumber[1], caseNumber[2], caseNumber[3], fileName);
                        break;
                }
            }
        }
    }//GEN-LAST:event_jTable1MouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JButton PrintButton;
    private com.alee.extended.date.WebDateField endDateField;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private com.alee.extended.date.WebDateField startDateField;
    // End of variables declaration//GEN-END:variables
}
