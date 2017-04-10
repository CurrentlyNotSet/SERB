/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.report;

import java.util.List;
import javax.swing.DefaultComboBoxModel;
import parker.serb.Global;
import parker.serb.sql.ActivityType;
import parker.serb.sql.SMDSDocuments;
import parker.serb.sql.User;
import parker.serb.util.Item;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class RequestedInfoDropdownPanel extends javax.swing.JDialog {

    SMDSDocuments report;
    String Type = "";

    /**
     * Creates new form RequestedReportInformationPanel
     *
     * @param parent
     * @param modal
     * @param reportPassed
     * @param TypePassed
     */
    public RequestedInfoDropdownPanel(java.awt.Frame parent, boolean modal, SMDSDocuments reportPassed, String TypePassed) {
        super(parent, modal);
        report = reportPassed;
        initComponents();
        setActive(report.fileName, TypePassed);
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void setActive(String reportName, String TypePassed) {
        Type = TypePassed;
        reportLabel.setText(reportName);
        switch (Type) {
            case "Month":
                comboBoxLabel.setText("Month:");
                break;
            case "UserID":
                comboBoxLabel.setText("User:");
                break;
            case "SectionUserID":
                comboBoxLabel.setText("User:");
                break;
            case "ActivityType":
                comboBoxLabel.setText("Activity Type:");
                break;
            case "InvestigatorID":
                comboBoxLabel.setText("ALJ:");
                break;
            default:
                break;
        }
        loadCombobox(Type);
        generateButton();
    }

    private void loadCombobox(String IDType) {
        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        ComboBox.setModel(dt);

        switch (IDType) {
            case "Month":
                ComboBox.addItem("");
                for (String month : Global.MONTH_LIST) {
                    ComboBox.addItem(month);
                }
                ComboBox.setSelectedItem("");
                break;
            case "UserID":
                ComboBox.addItem(new Item<>("%", "All"));
                List<User> userList = User.getEnabledUsers();
                for (User item : userList) {
                    ComboBox.addItem(new Item<>(
                            String.valueOf(item.id),
                            StringUtilities.buildFullName(item.firstName, item.middleInitial, item.lastName))
                    );
                }
                ComboBox.setSelectedItem(new Item<>("%", "All"));
                break;
            case "SectionUserID":
                ComboBox.addItem(new Item<>("%", "All"));
                List<User> sectionUserList = User.loadSectionUsersWithID(Global.activeSection);
                for (User item : sectionUserList) {
                    ComboBox.addItem(new Item<>(
                            String.valueOf(item.id),
                            StringUtilities.buildFullName(item.firstName, item.middleInitial, item.lastName))
                    );
                }
                ComboBox.setSelectedItem(new Item<>("%", "All"));
                break;
            case "ActivityType":
                comboBoxLabel.setText("Activity Type:");
                ComboBox.addItem(new Item<>("%", "All"));
                List<ActivityType> typeList = ActivityType.loadActiveActivityTypeBySection(Global.activeSection);
                for (ActivityType item : typeList) {
                    ComboBox.addItem(new Item<>(
                            String.valueOf(item.id),
                            item.descriptionFull)
                    );
                }
                ComboBox.setSelectedItem(new Item<>("%", "All"));
                break;
            case "InvestigatorID":
                comboBoxLabel.setText("ALJ:");
                ComboBox.addItem(new Item<>("%", "All"));
                List<User> investigatorList = User.getEnabledInvestigators();
                for (User item : investigatorList) {
                    ComboBox.addItem(new Item<>(
                            String.valueOf(item.id),
                            StringUtilities.buildFullName(item.firstName, item.middleInitial, item.lastName))
                    );
                }
                ComboBox.setSelectedItem(new Item<>("%", "All"));
                break;
            default:
                break;
        }
    }

    private void generateButton() {
        if (ComboBox.getSelectedIndex() > -1) {
            GenerateReportButton.setEnabled(true);
        } else {
            GenerateReportButton.setEnabled(false);
        }
    }

    private void generateReport() {
        switch (Type) {
            case "Month":
                GenerateReport.generateExactStringReport(ComboBox.getSelectedItem().toString(), report);
                break;
            case "UserID":
            case "InvestigatorID":
            case "ActivityType":
            case "SectionUserID":
                Item item = (Item) ComboBox.getSelectedItem();
                String comboBoxID = item.getValue().toString();
                GenerateReport.generateIDReport(comboBoxID, report);
                break;
            default:
                break;
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
        comboBoxLabel = new javax.swing.JLabel();
        GenerateReportButton = new javax.swing.JButton();
        CancelButton = new javax.swing.JButton();
        reportLabel = new javax.swing.JLabel();
        ComboBox = new javax.swing.JComboBox();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Required Report Information");
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Required Report Information");

        comboBoxLabel.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        comboBoxLabel.setText("<<LABEL>>");

        GenerateReportButton.setText("Generate Report");
        GenerateReportButton.setEnabled(false);
        GenerateReportButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateReportButtonActionPerformed(evt);
            }
        });

        CancelButton.setText("Cancel");
        CancelButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CancelButtonActionPerformed(evt);
            }
        });

        reportLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        reportLabel.setText("<<REPORTNAME>>");

        ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ComboBoxActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CancelButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(GenerateReportButton, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(reportLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 459, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(55, 55, 55)
                .addComponent(comboBoxLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 96, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(reportLabel)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboBoxLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CancelButton)
                    .addComponent(GenerateReportButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {ComboBox, comboBoxLabel});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CancelButtonActionPerformed

    private void GenerateReportButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateReportButtonActionPerformed
        generateReport();
    }//GEN-LAST:event_GenerateReportButtonActionPerformed

    private void ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ComboBoxActionPerformed
        generateButton();
    }//GEN-LAST:event_ComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CancelButton;
    private javax.swing.JComboBox ComboBox;
    private javax.swing.JButton GenerateReportButton;
    private javax.swing.JLabel comboBoxLabel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel reportLabel;
    // End of variables declaration//GEN-END:variables
}
