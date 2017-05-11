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
import java.awt.event.ActionEvent;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.report.GenerateReport;
import parker.serb.sql.Activity;
import parker.serb.sql.SMDSDocuments;
import parker.serb.sql.User;
import parker.serb.util.FileService;
import parker.serb.util.Item;
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
        jTable1.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

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

        if (Global.activeSection.equals("ORG") || Global.activeSection.equals("Civil Service Commission")) {
            jTable1.getColumnModel().getColumn(2).setHeaderValue("Org Number");
        } else if (Global.activeSection.equals("CMDS")) {
            jTable1.getColumnModel().getColumn(3).setHeaderValue("ALJ");
        }

        jTable1.getTableHeader().repaint();
        startDateField.setDate(new Date());
        endDateField.setDate(new Date());
        addListeners();
        setColumnSize();
        loadDropdown();
        loadTable();
    }

    private void loadDropdown() {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        assignedToCombobox.setModel(dt);
        assignedToCombobox.addItem(new Item<>("%", "All"));

        List<User> userList = null;

        if (Global.activeSection.equals("REP") || Global.activeSection.equals("MED") || Global.activeSection.equals("ULP")) {
            userList = User.loadSectionDropDownsPlusALJWithID(Global.activeSection);
        } else {
            userList = User.loadSectionUsersWithID(Global.activeSection);
        }

        for (User item : userList) {
            assignedToCombobox.addItem(new Item<>(String.valueOf(item.id), item.firstName + " " + item.lastName));
        }
        assignedToCombobox.setSelectedItem(new Item<>("%", "All"));
    }

    private void addListeners() {
        assignedToCombobox.addActionListener((ActionEvent e) -> {
            loadTable();
        });

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

        //Recieved Date
        jTable1.getColumnModel().getColumn(1).setMinWidth(140);
        jTable1.getColumnModel().getColumn(1).setPreferredWidth(140);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(140);

        //Docket Date
        jTable1.getColumnModel().getColumn(2).setMinWidth(80);
        jTable1.getColumnModel().getColumn(2).setPreferredWidth(80);
        jTable1.getColumnModel().getColumn(2).setMaxWidth(80);

        //Case Number
        if (Global.activeSection.equals("ORG") || Global.activeSection.equals("Civil Service Commission")) {
            jTable1.getColumnModel().getColumn(3).setMinWidth(125);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(125);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(125);
        } else {
            jTable1.getColumnModel().getColumn(3).setMinWidth(125);
            jTable1.getColumnModel().getColumn(3).setPreferredWidth(125);
            jTable1.getColumnModel().getColumn(3).setMaxWidth(125);
        }

        //Investigator
        jTable1.getColumnModel().getColumn(4).setMinWidth(150);
        jTable1.getColumnModel().getColumn(4).setPreferredWidth(150);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(150);

        //FROM
        if (Global.activeSection.equals("CMDS")) {
            jTable1.getColumnModel().getColumn(5).setMinWidth(0);
            jTable1.getColumnModel().getColumn(5).setPreferredWidth(0);
            jTable1.getColumnModel().getColumn(5).setMaxWidth(0);
        }

        //FileName
        jTable1.getColumnModel().getColumn(7).setMinWidth(0);
        jTable1.getColumnModel().getColumn(7).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(7).setMaxWidth(0);
    }

    private List<Activity> loadSQLQuery() {
        List<Activity> activityList = null;
        activityList = Activity.loadMailLogBySectionActiveSection(
                Global.SQLDateFormat.format(startDateField.getDate()),
                Global.SQLDateFormat.format(endDateField.getDate()),
                assignedToCombobox.getSelectedItem().toString().trim().equals("All") ? "" : assignedToCombobox.getSelectedItem().toString().trim(),
                (Global.activeSection.equals("Civil Service Commission") ? "CSC" : Global.activeSection));
        return activityList;
    }

    private void loadTable() {
        List<Activity> activityList = loadSQLQuery();

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
                item.mailLog,
                number,
                item.to,
                item.from,
                item.action,
                item.fileName
            });
        }
        PrintButton.setEnabled(jTable1.getRowCount() > 0);
    }

//    private void generateReport() {
//        SMDSDocuments doc = new SMDSDocuments();
//        doc.section = "ALL";
//        doc.fileName = "MailLog.jasper";
//
//        GenerateReport.generateMailLogReport(Global.SQLDateFormat.format(startDateField.getDate()),
//                Global.SQLDateFormat.format(endDateField.getDate()),
//                assignedToCombobox.getSelectedItem().toString().trim().equals("All") ? "" : assignedToCombobox.getSelectedItem().toString().trim(),
//                Global.activeSection,
//                doc
//        );
//    }
    
    private void generateReport() {
        SMDSDocuments doc = new SMDSDocuments();
        doc.section = "ALL";
        doc.fileName = "MailLog.jasper";

        String section = Global.activeSection.equals("Civil Service Commission") ? "CSC" : Global.activeSection;
        String to = assignedToCombobox.getSelectedItem().toString().trim().equals("All") ? "" : assignedToCombobox.getSelectedItem().toString().trim();
        String where = "";

        //requires "SELECT Activity.* FROM Activity" in the report
        switch (section) {
            case "Hearings":
                where = " INNER JOIN HearingCase ON HearingCase.caseYear = Activity.caseYear "
                        + "AND HearingCase.caseType = Activity.caseType "
                        + "AND HearingCase.caseMonth = Activity.caseMonth "
                        + "AND HearingCase.caseNumber = Activity.caseNumber "
                        + "WHERE Activity.mailLog >= '" + Global.SQLDateFormat.format(startDateField.getDate()) + "'  AND Activity.mailLog <= '" + Global.SQLDateFormat.format(endDateField.getDate()) + "' "
                        + "AND Activity.fileName IS NOT NULL AND Activity.fileName != '' "
                        + "AND Activity.active = 1 AND Activity.[to] LIKE %" + to + "%' "
                        + "ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC, activity.id DESC";
                break;
            case "CSC":
            case "ORG":
                where = " WHERE Activity.mailLog >= '" + Global.SQLDateFormat.format(startDateField.getDate()) + "'  AND Activity.mailLog <= '" + Global.SQLDateFormat.format(endDateField.getDate()) + "' "
                        + "AND Activity.fileName IS NOT NULL AND Activity.fileName != '' "
                        + "AND Activity.active = 1 AND Activity.[to] LIKE %" + to + "%' "
                        + "AND activity.casetype = '" + section + "'"
                        + "ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC, activity.id DESC";
                break;
            default:
                where = " LEFT JOIN caseType ON activity.casetype = casetype.casetype "
                        + "WHERE Activity.mailLog >= '" + Global.SQLDateFormat.format(startDateField.getDate()) + "'  AND Activity.mailLog <= '" + Global.SQLDateFormat.format(endDateField.getDate()) + "' "
                        + "AND Activity.fileName IS NOT NULL AND Activity.fileName != '' "
                        + "AND Activity.active = 1 AND Activity.[to] LIKE %" + to + "%' "
                        + "AND CaseType.section = '" + section + "'"
                        + "ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC, activity.id DESC";
                break;
        }
        GenerateReport.generateMailLogFROMReport(where, doc);
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
        CancelButton = new javax.swing.JButton();
        PrintButton = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        startDateField = new com.alee.extended.date.WebDateField();
        endDateField = new com.alee.extended.date.WebDateField();
        jLabel8 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        assignedToCombobox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Maillog Viewer");
        setResizable(false);

        headerLabel.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        headerLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        headerLabel.setText("Mail Log");

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
                "ID", "Received", "Docketed", "Case Number", "Investigator", "From", "Description", "FileName"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false
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
            jTable1.getColumnModel().getColumn(7).setResizable(false);
        }

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Start Date:");

        startDateField.setDateFormat(Global.mmddyyyy);

        startDateField.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );

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

                jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                jLabel1.setText("Assigned To:");

                javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                jPanel1.setLayout(jPanel1Layout);
                jPanel1Layout.setHorizontalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 77, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(assignedToCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(endDateField, javax.swing.GroupLayout.PREFERRED_SIZE, 148, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())
                );

                jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {endDateField, startDateField});

                jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel7, jLabel8});

                jPanel1Layout.setVerticalGroup(
                    jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(endDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel8)
                        .addComponent(startDateField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(jLabel1)
                        .addComponent(assignedToCombobox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                );

                jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {endDateField, jLabel7, jLabel8, startDateField});

                javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
                getContentPane().setLayout(layout);
                layout.setHorizontalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(headerLabel, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(PrintButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())
                    .addGroup(layout.createSequentialGroup()
                        .addGap(250, 250, 250)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(252, Short.MAX_VALUE))
                );
                layout.setVerticalGroup(
                    layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(headerLabel)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 452, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(CancelButton)
                            .addComponent(PrintButton))
                        .addContainerGap())
                );

                pack();
            }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void PrintButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PrintButtonActionPerformed
        generateReport();
    }//GEN-LAST:event_PrintButtonActionPerformed

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTable1MouseClicked
        if (jTable1.getSelectedRow() > -1) {
            String fileName = jTable1.getValueAt(jTable1.getSelectedRow(), 7).toString();
            String[] caseNumber = jTable1.getValueAt(jTable1.getSelectedRow(), 3).toString().trim().split("-");

            if (evt.getClickCount() == 2 && !fileName.equals("")) {
                switch (Global.activeSection) {
                    case "ORG":
                        FileService.openFileWithORGNumber("ORG", jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString(), fileName);
                        break;
                    case "CSC":
                    case "Civil Service Commission":
                        FileService.openFileWithORGNumber("CSC", jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString(), fileName);
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
    private javax.swing.JComboBox assignedToCombobox;
    private com.alee.extended.date.WebDateField endDateField;
    private javax.swing.JLabel headerLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private com.alee.extended.date.WebDateField startDateField;
    // End of variables declaration//GEN-END:variables
}
