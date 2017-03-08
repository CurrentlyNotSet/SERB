/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ULP;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import parker.serb.Global;
import parker.serb.activity.ActivityPanel;
import parker.serb.activity.AddActivtyForSMDSDialog;
import parker.serb.activity.RemoveActivityEntryDialog;
import parker.serb.party.PartiesPanel;
import parker.serb.party.PartySearchDialog;
import parker.serb.sql.Audit;
import parker.serb.sql.ULPCaseSearchData;
import parker.serb.util.CancelUpdate;

/**
 *
 * @author parker
 */
public class ULPRootPanel extends javax.swing.JPanel {

    String currentTab = "Activity";

    /**
     * Creates new form REPRootPanel
     */
    public ULPRootPanel() {
        initComponents();
        addListeners();
    }

    void clearAll() {
        Global.root.getuLPHeaderPanel1().clearAll();
        activityPanel1.clearAll();
        partiesPanel1.clearAll();
        uLPStatusPanel1.clearAll();
        uLPStatement1.clearAll();
        uLPRecommendation1.clearAll();
        uLPInvestigationReveals1.clearAll();
        notesPanel2.clearAll();
    }

    private void addListeners() {

        jTabbedPane1.addChangeListener((ChangeEvent e) -> {
            if(Global.caseNumber != null) {
                setButtons();
                loadInformation();
                currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
                Audit.addAuditEntry("Navigated to " + Global.activeSection + " - " + currentTab);
            }
        });
    }

    private void loadInformation() {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                activityPanel1.loadAllActivity();
                break;
            case "Parties":
                partiesPanel1.loadParties();
                break;
            case "Status":
                uLPStatusPanel1.loadInformation();
                break;
            case "Statement":
                uLPStatement1.loadInformation();
                break;
            case "Recommendation":
                uLPRecommendation1.loadInformation();
                break;
            case "Investigation Reveals":
                uLPInvestigationReveals1.loadInformation();
                break;
            case "Notes":
                notesPanel2.loadInformation();
                break;
        }
    }

    private void setButtons() {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(false);
                Global.root.getjButton9().setVisible(true);
                break;
            case "Information":
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Parties":
                Global.root.getjButton2().setText("Add Party");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(true);
                break;
            case "Status":
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Statement":
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Recommendation":
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Investigation Reveals":
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Notes":
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
        }
    }

    private void disableTabs(int activeTab) {
        Global.root.getuLPHeaderPanel1().getjComboBox2().setEnabled(false);
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            if(i != activeTab) {
                jTabbedPane1.setEnabledAt(i, false);
            } else {
                jTabbedPane1.setEnabledAt(i, true);
            }
        }
    }

    private void enableTabs() {
        Global.root.getuLPHeaderPanel1().getjComboBox2().setEnabled(true);
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            jTabbedPane1.setEnabledAt(i, true);
        }
    }

    public void ulpUpdate(String buttonText) {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                Audit.addAuditEntry("Clicked ULP Add Acitivty Button");
                new AddActivtyForSMDSDialog(Global.root, true);
                activityPanel1.loadAllActivity();
                break;
            case "Parties":
                Audit.addAuditEntry("Clicked ULP Add Party Button");
                new PartySearchDialog((JFrame) this.getRootPane().getParent(), true);
                partiesPanel1.loadParties();
                Global.root.getuLPHeaderPanel1().loadHeaderInformation();
                ULPCaseSearchData.updateCaseEntryFromParties(
                        Global.root.getuLPHeaderPanel1().getChargedPartyTextBox().getText().trim(),
                        Global.root.getuLPHeaderPanel1().getChargingPartyTextBox().getText().trim());
                break;
            case "Status":

                if(buttonText.equals("Update")) {
                    Audit.addAuditEntry("Clicked ULP Update Status Button");
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    uLPStatusPanel1.enableUpdate();
                } else {
                    Audit.addAuditEntry("Clicked ULP Save Status Button");
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    uLPStatusPanel1.disableUpdate(true);
                    Global.root.getuLPHeaderPanel1().loadHeaderInformation();
                }
                break;
            case "Statement":
                if(buttonText.equals("Update")) {
                    Audit.addAuditEntry("Clicked ULP Update Statment Button");
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    uLPStatement1.enableUpdate();
                } else {
                    Audit.addAuditEntry("Clicked ULP Save Statment Button");
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    uLPStatement1.disableUpdate(true);
                }
                break;
            case "Recommendation":
                if(buttonText.equals("Update")) {
                    Audit.addAuditEntry("Clicked ULP Update Recommendation Button");
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    uLPRecommendation1.enableUpdate();
                } else {
                    Audit.addAuditEntry("Clicked ULP Save Recommendation Button");
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    uLPRecommendation1.disableUpdate(true);
                }
                break;
            case "Investigation Reveals":
                if(buttonText.equals("Update")) {
                    Audit.addAuditEntry("Clicked ULP Update Investigation Reveals Button");
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    uLPInvestigationReveals1.enableUpdate();
                } else {
                    Audit.addAuditEntry("Clicked ULP Save Investigation Reveals Button");
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    uLPInvestigationReveals1.disableUpdate(true);
                }
                break;
            case "Notes":
                if(buttonText.equals("Update")) {
                    Audit.addAuditEntry("Clicked ULP Update Notes Button");
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    notesPanel2.enableUpdate();
                } else {
                    Audit.addAuditEntry("Clicked ULP Save Notes Button");
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    notesPanel2.disableUpdate(true);
                }
                break;
        }
    }

    public void ulpDelete() {
        CancelUpdate cancel;
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                Audit.addAuditEntry("Clicked ULP Delete Acitivty Button");
                new RemoveActivityEntryDialog(
                    Global.root,
                    true,
                    activityPanel1.getActvityTable().getValueAt
                    (
                        activityPanel1.getActvityTable().getSelectedRow(),
                        6
                    ).toString()
                );
                activityPanel1.loadAllActivity();
                break;
            case "Parties":
                Audit.addAuditEntry("Clicked ULP Delete Party Button");
                partiesPanel1.removeParty();
                Global.root.getuLPHeaderPanel1().loadHeaderInformation();
                ULPCaseSearchData.updateCaseEntryFromParties(
                        Global.root.getuLPHeaderPanel1().getChargedPartyTextBox().getText().trim(),
                        Global.root.getuLPHeaderPanel1().getChargingPartyTextBox().getText().trim());
                break;
            case "Status":
                Audit.addAuditEntry("Clicked ULP Cancel Status Update Button");
                cancel = new CancelUpdate(Global.root, true);
                if(!cancel.isReset()) {
                } else {
                    Audit.addAuditEntry("Canceled ULP Status Update");
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    uLPStatusPanel1.disableUpdate(false);
                }
                break;
            case "Statement":
                Audit.addAuditEntry("Clicked ULP Cancel Statement Update Button");
                cancel = new CancelUpdate(Global.root, true);
                if(!cancel.isReset()) {
                } else {
                    Audit.addAuditEntry("Canceled ULP Statement Update");
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    uLPStatement1.disableUpdate(false);
                }
                break;
            case "Recommendation":
                Audit.addAuditEntry("Clicked ULP Cancel Recommendation Update Button");
                cancel = new CancelUpdate(Global.root, true);
                if(!cancel.isReset()) {
                } else {
                    Audit.addAuditEntry("Canceled ULP Recommendation Update");
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    uLPRecommendation1.disableUpdate(false);
                }
                break;
            case "Investigation Reveals":
                Audit.addAuditEntry("Clicked ULP Cancel Investigation Reveals Update Button");
                cancel = new CancelUpdate(Global.root, true);
                if(!cancel.isReset()) {
                } else {
                    Audit.addAuditEntry("Canceled ULP Investgifation Reveals Update");
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    uLPInvestigationReveals1.disableUpdate(false);
                }
                break;
            case "Notes":
                Audit.addAuditEntry("Clicked ULP Cancel Notes Update Button");
                cancel = new CancelUpdate(Global.root, true);
                if(!cancel.isReset()) {
                } else {
                    Audit.addAuditEntry("Canceled ULP Notes Update");
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    notesPanel2.disableUpdate(false);
                }
                break;
        }
    }

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }

    public ActivityPanel getActivityPanel1() {
        return activityPanel1;
    }

    public PartiesPanel getPartiesPanel1() {
        return partiesPanel1;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        rEPCaseInformationPanel1 = new parker.serb.REP.REPCaseInformationPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        activityPanel1 = new parker.serb.activity.ActivityPanel();
        jPanel3 = new javax.swing.JPanel();
        partiesPanel1 = new parker.serb.party.PartiesPanel();
        jPanel4 = new javax.swing.JPanel();
        uLPStatusPanel1 = new parker.serb.ULP.ULPStatusPanel();
        jPanel5 = new javax.swing.JPanel();
        uLPStatement1 = new parker.serb.ULP.ULPStatement();
        jPanel6 = new javax.swing.JPanel();
        uLPRecommendation1 = new parker.serb.ULP.ULPRecommendationPanel();
        jPanel7 = new javax.swing.JPanel();
        uLPInvestigationReveals1 = new parker.serb.ULP.ULPInvestigationReveals();
        jPanel8 = new javax.swing.JPanel();
        notesPanel2 = new parker.serb.notes.NotesPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(activityPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(activityPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Activity", jPanel1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(partiesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(partiesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Parties", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPStatusPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPStatusPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        uLPStatusPanel1.getAccessibleContext().setAccessibleName("Status");

        jTabbedPane1.addTab("Status", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPStatement1, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPStatement1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Statement", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPRecommendation1, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPRecommendation1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Recommendation", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPInvestigationReveals1, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPInvestigationReveals1, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Investigation Reveals", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 619, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Notes", jPanel8);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Status");
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private parker.serb.activity.ActivityPanel activityPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private parker.serb.notes.NotesPanel notesPanel2;
    private parker.serb.party.PartiesPanel partiesPanel1;
    private parker.serb.REP.REPCaseInformationPanel rEPCaseInformationPanel1;
    private parker.serb.ULP.ULPInvestigationReveals uLPInvestigationReveals1;
    private parker.serb.ULP.ULPRecommendationPanel uLPRecommendation1;
    private parker.serb.ULP.ULPStatement uLPStatement1;
    private parker.serb.ULP.ULPStatusPanel uLPStatusPanel1;
    // End of variables declaration//GEN-END:variables
}
