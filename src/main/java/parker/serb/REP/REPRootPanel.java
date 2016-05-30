/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import parker.serb.activity.ActivityPanel;
import parker.serb.Global;
import parker.serb.party.PartySearchDialog;
import parker.serb.sql.REPCaseSearchData;
import parker.serb.util.CancelUpdate;

/**
 *
 * @author parker
 */
public class REPRootPanel extends javax.swing.JPanel {

    /**
     * Creates new form REPRootPanel
     */
    public REPRootPanel() {
        initComponents();
//        jTabbedPane1.remove(7); //NOTES
//        jTabbedPane1.remove(6); //Mediation
        jTabbedPane1.remove(5);
//        jTabbedPane1.remove(4); //Board Status
//        jTabbedPane1.remove(3); //Details
//        jTabbedPane1.remove(2); //PARTIES
//        jTabbedPane1.remove(1); //INFORMATION
//        jTabbedPane1.remove(0); //Activity
        addListeners();
    }
    
    /**
     * Removes all content from previous stored cases
     */
    void clearAll() {
        Global.root.getrEPHeaderPanel1().clearAll();
        activityPanel1.clearAll();
        rEPCaseInformationPanel2.clearAll();
        notesPanel2.clearAll();
        partiesPanel1.clearAll();
        rEPCaseDetailsPanel1.clearAll();
    }
    
    private void addListeners() {
         jTabbedPane1.addChangeListener((ChangeEvent e) -> {
            
            if(Global.caseNumber != null) {
                setButtons();
                loadInformation();
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
            case "Information":
                rEPCaseInformationPanel2.loadInformation();
                break;
            case "Parties":
                partiesPanel1.loadAllParties();
                break;
            case "Details":
                rEPCaseDetailsPanel1.loadInformation();
                break;
            case "Board Status":
                rEPBoardStatusPanel1.loadInformation();
                break;
            case "Elections":
                break;
            case "Mediation":
                rEPMediationPanel1.loadAllMediations();
                break;
            case "Notes":
                notesPanel2.loadInformation();
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
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(false);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Information":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Parties":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Add Party");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(true);
                break;
            case "Details":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Board Status":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Elections":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
            case "Mediation":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Add Mediation");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(true);
                break;
            case "Notes":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
        }
    }
    
    private void disableTabs(int activeTab) {
        Global.root.getrEPHeaderPanel1().getjComboBox2().setEnabled(false);
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            if(i != activeTab) {
                jTabbedPane1.setEnabledAt(i, false);
            } else {
                jTabbedPane1.setEnabledAt(i, true);
            }
        }
    }
    
    private void enableTabs() {
        Global.root.getrEPHeaderPanel1().getjComboBox2().setEnabled(true);
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            jTabbedPane1.setEnabledAt(i, true);
        }
    }
    
    /**
     * Used to update the information in the DB with information from a panel. 
     * Uses the currently selected panel index
     * @param buttonText the text of the current button
     */
    public void repUpdate(String buttonText) {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                activityPanel1.loadAllActivity();
                break;
            case "Information":
                if(buttonText.equals("Update")) {
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    rEPCaseInformationPanel2.enableUpdate();
                } else {
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    rEPCaseInformationPanel2.disableUpdate(true);
                    Global.root.getrEPHeaderPanel1().loadHeaderInformation();
                }
                break;
            case "Parties":
                new PartySearchDialog((JFrame) this.getRootPane().getParent(), true);
                partiesPanel1.loadAllParties();
                Global.root.getrEPHeaderPanel1().loadHeaderInformation();
                REPCaseSearchData.updateCaseEntryFromParties(
                        Global.root.getrEPHeaderPanel1().getEmployerTextBox().getText().trim(),
                        Global.root.getrEPHeaderPanel1().getEmployeeOrgTextBox().getText().trim(),
                        Global.root.getrEPHeaderPanel1().getIncumbentEEOTextBox().getText().trim());
                break;
            case "Details":
                if(buttonText.equals("Update")) {
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    rEPCaseDetailsPanel1.enableUpdate();
                } else {
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    rEPCaseDetailsPanel1.disableUpdate(true);
                }
                break;
            case "Board Status":
                if(buttonText.equals("Update")) {
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    rEPBoardStatusPanel1.enableUpdate();
                } else {
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    rEPBoardStatusPanel1.disableUpdate(true);
                    rEPBoardStatusPanel1.loadInformation();
                }
                break;
            case "Elections":
                break;
            case "Mediation":
                new REPAddMediationDialog((JFrame) Global.root.getParent(), true);
                rEPMediationPanel1.loadAllMediations();
                break;
            case "Notes":
                if(buttonText.equals("Update")) {
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    notesPanel2.enableUpdate();
                } else {
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    notesPanel2.disableUpdate(true);
                }
                break;
        }
    }
    
    /**
     * Determines if the delete button should be enabled, as well as the desired
     * functionality
     */
    public void repDelete() {
        CancelUpdate cancel;
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                break;
            case "Information":
                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
                if(!cancel.isReset()) {
                } else {
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    rEPCaseInformationPanel2.disableUpdate(false);
                    rEPCaseInformationPanel2.loadCaseInformation();
                }
                break;
            case "Parties":
                partiesPanel1.removeParty();
                Global.root.getrEPHeaderPanel1().loadHeaderInformation();
                REPCaseSearchData.updateCaseEntryFromParties(
                        Global.root.getrEPHeaderPanel1().getEmployerTextBox().getText().trim(),
                        Global.root.getrEPHeaderPanel1().getEmployeeOrgTextBox().getText().trim(),
                        Global.root.getrEPHeaderPanel1().getIncumbentEEOTextBox().getText().trim());
                break;
            case "Details":
                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
                if(!cancel.isReset()) {
                } else {
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    rEPCaseDetailsPanel1.disableUpdate(false);
                    rEPCaseDetailsPanel1.loadInformation();
                }
                break;
            case "Board Status":
                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
                if(!cancel.isReset()) {
                } else {
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    rEPBoardStatusPanel1.disableUpdate(false);
                    rEPBoardStatusPanel1.loadInformation();
                }
                break;
            case "Elections":
                break;
            case "Mediation":
                rEPMediationPanel1.removeMediation();
                break;
            case "Notes":
                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
                if(!cancel.isReset()) {
                } else {
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
        jPanel2 = new javax.swing.JPanel();
        rEPCaseInformationPanel2 = new parker.serb.REP.REPCaseInformationPanel();
        jPanel3 = new javax.swing.JPanel();
        partiesPanel1 = new parker.serb.party.PartiesPanel();
        jPanel4 = new javax.swing.JPanel();
        rEPCaseDetailsPanel1 = new parker.serb.REP.REPCaseDetailsPanel();
        jPanel5 = new javax.swing.JPanel();
        rEPBoardStatusPanel1 = new parker.serb.REP.REPBoardStatusPanel();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        rEPMediationPanel1 = new parker.serb.REP.REPMediationPanel();
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
            .addComponent(activityPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Activity", jPanel1);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPCaseInformationPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPCaseInformationPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Information", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(partiesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(partiesPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Parties", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPCaseDetailsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPCaseDetailsPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Details", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPBoardStatusPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPBoardStatusPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Board Status", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 972, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 575, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Elections", jPanel6);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPMediationPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1028, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPMediationPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Mediation", jPanel7);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 972, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE)
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
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private parker.serb.activity.ActivityPanel activityPanel1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private parker.serb.notes.NotesPanel notesPanel2;
    private parker.serb.party.PartiesPanel partiesPanel1;
    private parker.serb.REP.REPBoardStatusPanel rEPBoardStatusPanel1;
    private parker.serb.REP.REPCaseDetailsPanel rEPCaseDetailsPanel1;
    private parker.serb.REP.REPCaseInformationPanel rEPCaseInformationPanel1;
    private parker.serb.REP.REPCaseInformationPanel rEPCaseInformationPanel2;
    private parker.serb.REP.REPMediationPanel rEPMediationPanel1;
    // End of variables declaration//GEN-END:variables
}
