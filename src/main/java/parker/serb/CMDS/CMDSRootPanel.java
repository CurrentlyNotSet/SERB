/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import parker.serb.Global;
import parker.serb.activity.ActivityPanel;
import parker.serb.activity.RemoveActivityEntryDialog;
import parker.serb.party.PartySearchDialog;
import parker.serb.sql.CMDSCaseSearchData;
import parker.serb.sql.CaseParty;
import parker.serb.util.CancelUpdate;

/**
 *
 * @author parker
 */
public class CMDSRootPanel extends javax.swing.JPanel {

    String currentTab = "Activity";
    boolean singleFire = true;
    /**
     * Creates new form REPRootPanel
     */
    public CMDSRootPanel() {
        initComponents();
        addListeners();
    }

    /**
     * Removes all content from previous stored cases
     */
    void clearAll() {
        Global.root.getcMDSHeaderPanel1().clearAll();
        activityPanel1.clearAll();
        notesPanel1.clearAll();
        cMDSInformationPanel1.clearAll();
        cMDSHearingsPanel1.clearTable();
        partiesPanel1.clearAll();
    }

    private void addListeners() {

        jTabbedPane1.addChangeListener((ChangeEvent e) -> {
            if(Global.caseNumber != null) {
                setButtons();
                loadInformation();
                currentTab = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
            }
        });
    }

    /**
     * load information about the case that is based on the tab that is selected,
     * trying to cheat and "lazy" load.  This my be re-factored to allow for full
     * case load while displaying a spinner.
     */
    private void loadInformation() {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                activityPanel1.loadAllActivity();
                break;
            case "Case Information":
                cMDSInformationPanel1.loadInformation();
                break;
            case "Parties":
                partiesPanel1.loadParties();
                break;
            case "Notes":
                notesPanel1.loadInformation();
                break;
            case "Hearings":
                cMDSHearingsPanel1.loadInformation();
                break;
        }
    }

    /**
     * Set the buttons to display the proper information and button status
     * depending on the selected tab index
     */
    private void setButtons() {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                Global.root.getjButton2().setText("Add Entry");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Parties":
                Global.root.getjButton2().setText("Add Party");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(true);
                break;
            case "Case Information":
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Notes":
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Hearings":
                Global.root.getjButton2().setText("Add Hearing");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
        }
    }

    private void disableTabs(int activeTab) {
        Global.root.getcMDSHeaderPanel1().getjComboBox2().setEnabled(false);
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            if(i != activeTab) {
                jTabbedPane1.setEnabledAt(i, false);
            } else {
                jTabbedPane1.setEnabledAt(i, true);
            }
        }
    }

    private void enableTabs() {
        Global.root.getcMDSHeaderPanel1().getjComboBox2().setEnabled(true);
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            jTabbedPane1.setEnabledAt(i, true);
        }
    }

    /**
     * Used to update the information in the DB with information from a panel.
     * Uses the currently selected panel index
     * @param buttonText the text of the current button
     */
    public void cmdsUpdate(String buttonText) {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                new CMDSAddHistoryEntryDialog((JFrame) this.getRootPane().getParent(), true);
                activityPanel1.loadAllActivity();
                Global.root.getcMDSHeaderPanel1().loadHeaderInformation();
                break;
            case "Parties":
                new PartySearchDialog((JFrame) this.getRootPane().getParent(), true);
                partiesPanel1.loadParties();
                Global.root.getcMDSHeaderPanel1().loadHeaderInformation();
                CMDSCaseSearchData.updateCaseEntryFromParties(
                        Global.root.getcMDSHeaderPanel1().getAppellantTextBox().getText(),
                        Global.root.getcMDSHeaderPanel1().getAppelleeTextBox().getText(),
                        CaseParty.loadCMDSRepByCase("Appellant"),
                        CaseParty.loadCMDSRepByCase("Appellee"));
                break;
            case "Case Information":
                if(buttonText.equals("Update")) {
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    cMDSInformationPanel1.enableUpdate();
                } else {
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    cMDSInformationPanel1.disableUpdate(true);
                    Global.root.getcMDSHeaderPanel1().loadHeaderInformation();
                }
                break;
            case "Notes":
                if(buttonText.equals("Update")) {
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    notesPanel1.enableUpdate();
                } else {
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    notesPanel1.disableUpdate(true);
                }
                break;
            case "Hearings":
                new CMDSAddHearingDialog(Global.root, true);
                cMDSHearingsPanel1.loadInformation();
                break;
        }
    }

    /**
     * Determines if the delete button should be enabled, as well as the desired
     * functionality
     */
    public void cmdsDelete() {
        CancelUpdate cancel;
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
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
                partiesPanel1.removeParty();
                partiesPanel1.loadParties();
                Global.root.getcMDSHeaderPanel1().loadHeaderInformation();
                CMDSCaseSearchData.updateCaseEntryFromParties(
                        Global.root.getcMDSHeaderPanel1().getAppellantTextBox().getText(),
                        Global.root.getcMDSHeaderPanel1().getAppelleeTextBox().getText(),
                        CaseParty.loadCMDSRepByCase("Appellant"),
                        CaseParty.loadCMDSRepByCase("Appellee"));
                break;
            case "Case Information":
                cancel = new CancelUpdate(Global.root, true);
                if(!cancel.isReset()) {
                } else {
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    cMDSInformationPanel1.disableUpdate(false);
                }
                break;
            case "Notes":
                cancel = new CancelUpdate(Global.root, true);
                if(!cancel.isReset()) {
                } else {
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    notesPanel1.disableUpdate(false);
                }
                break;
            case "Hearings":
                cMDSHearingsPanel1.removeHearing();
                break;
        }
    }

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }

    public ActivityPanel getActivityPanel1() {
        return activityPanel1;
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
        jPanel4 = new javax.swing.JPanel();
        partiesPanel1 = new parker.serb.party.PartiesPanel();
        jPanel3 = new javax.swing.JPanel();
        cMDSInformationPanel1 = new parker.serb.CMDS.CMDSInformationPanel();
        jPanel2 = new javax.swing.JPanel();
        cMDSHearingsPanel1 = new parker.serb.CMDS.CMDSHearingsPanel();
        jPanel8 = new javax.swing.JPanel();
        notesPanel1 = new parker.serb.notes.NotesPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(activityPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1134, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(activityPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Activity", jPanel1);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(partiesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1134, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(partiesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Parties", jPanel4);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cMDSInformationPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1134, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cMDSInformationPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Case Information", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cMDSHearingsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1134, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cMDSHearingsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Hearings", jPanel2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1134, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
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
    private parker.serb.CMDS.CMDSHearingsPanel cMDSHearingsPanel1;
    private parker.serb.CMDS.CMDSInformationPanel cMDSInformationPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private parker.serb.notes.NotesPanel notesPanel1;
    private parker.serb.party.PartiesPanel partiesPanel1;
    private parker.serb.REP.REPCaseInformationPanel rEPCaseInformationPanel1;
    // End of variables declaration//GEN-END:variables
}
