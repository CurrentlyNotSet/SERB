/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.MED;

import parker.serb.MED.*;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import parker.serb.activity.ActivityPanel;
import parker.serb.Global;
import parker.serb.party.PartiesPanel;
import parker.serb.party.PartySearchDialog;
import parker.serb.sql.ULPCaseSearchData;
import parker.serb.util.CancelUpdate;

/**
 *
 * @author parker
 */
public class MEDRootPanel extends javax.swing.JPanel {

    String currentTab = "Activity";
    boolean singleFire = true;
    /**
     * Creates new form REPRootPanel
     */
    public MEDRootPanel() {
        initComponents();
        addListeners();
    }
    
    /**
     * Removes all content from previous stored cases
     */
    void clearAll() {
        Global.root.getmEDHeaderPanel1().clearAll();
        activityPanel1.clearAll();
        partiesPanel1.clearAll();
        notesPanel2.clearAll();
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
            case "Parties":
                partiesPanel1.loadULPParties();
                break;
//            case "Status":
//                uLPStatusPanel1.loadInformation();
//                break;
//            case "Statement":
//                uLPStatement1.loadInformation();
//                break;
//            case "Recommendation":
//                uLPRecommendation1.loadInformation();
//                break;
//            case "Investigation Reveals":
//                uLPInvestigationReveals1.loadInformation();
//                break;
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
//            case "Status":
//                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
//                Global.root.getjButton2().setText("Update");
//                Global.root.getjButton2().setEnabled(true);
//                Global.root.getjButton9().setVisible(false);
//                break;
//            case "Statement":
//                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
//                Global.root.getjButton2().setText("Update");
//                Global.root.getjButton2().setEnabled(true);
//                Global.root.getjButton9().setVisible(false);
//                break;
//            case "Recommendation":
//                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
//                Global.root.getjButton2().setText("Update");
//                Global.root.getjButton2().setEnabled(true);
//                Global.root.getjButton9().setVisible(false);
//                break;
//            case "Investigation Reveals":
//                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
//                Global.root.getjButton2().setText("Update");
//                Global.root.getjButton2().setEnabled(true);
//                Global.root.getjButton9().setVisible(false);
//                break;
            case "Notes":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
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
    
    /**
     * Used to update the information in the DB with information from a panel. 
     * Uses the currently selected panel index
     * @param buttonText the text of the current button
     */
    public void medUpdate(String buttonText) {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                activityPanel1.loadAllActivity();
                break;
            case "Parties":
                new PartySearchDialog((JFrame) this.getRootPane().getParent(), true);
                partiesPanel1.loadULPParties();
                Global.root.getmEDHeaderPanel1().loadHeaderInformation();
//                ULPCaseSearchData.updateCaseEntryFromParties(
//                        Global.root.getuLPHeaderPanel1().getChargedPartyTextBox().getText().trim(),
//                        Global.root.getuLPHeaderPanel1().getChargingPartyTextBox().getText().trim());
                break;
//            case "Status":
//                if(buttonText.equals("Update")) {
//                    disableTabs(jTabbedPane1.getSelectedIndex());
//                    uLPStatusPanel1.enableUpdate();
//                } else {
//                    enableTabs();
//                    Global.root.enableTabsAfterSave();
//                    Global.root.enableButtonsAfterCancel();
//                    uLPStatusPanel1.disableUpdate(true);
//                    Global.root.getuLPHeaderPanel1().loadHeaderInformation();
//                }
//                break;
//            case "Statement":
//                if(buttonText.equals("Update")) {
//                    disableTabs(jTabbedPane1.getSelectedIndex());
//                    uLPStatement1.enableUpdate();
//                } else {
//                    enableTabs();
//                    Global.root.enableTabsAfterSave();
//                    Global.root.enableButtonsAfterCancel();
//                    uLPStatement1.disableUpdate(true);
//                }
//                break;
//            case "Recommendation":
//                if(buttonText.equals("Update")) {
//                    disableTabs(jTabbedPane1.getSelectedIndex());
//                    uLPRecommendation1.enableUpdate();
//                } else {
//                    enableTabs();
//                    Global.root.enableTabsAfterSave();
//                    Global.root.enableButtonsAfterCancel();
//                    uLPRecommendation1.disableUpdate(true);
//                }
//                break;
//            case "Investigation Reveals":
//                if(buttonText.equals("Update")) {
//                    disableTabs(jTabbedPane1.getSelectedIndex());
//                    uLPInvestigationReveals1.enableUpdate();
//                } else {
//                    enableTabs();
//                    Global.root.enableTabsAfterSave();
//                    Global.root.enableButtonsAfterCancel();
//                    uLPInvestigationReveals1.disableUpdate(true);
//                }
//                break;
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
    public void medDelete() {
        CancelUpdate cancel;
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                break;
            case "Parties":
                partiesPanel1.removeParty();
                partiesPanel1.loadULPParties();
                Global.root.getmEDHeaderPanel1().loadHeaderInformation();
//                ULPCaseSearchData.updateCaseEntryFromParties(
//                        Global.root.getmEDHeaderPanel1().getChargedPartyTextBox().getText().trim(),
//                        Global.root.getmEDHeaderPanel1().getChargingPartyTextBox().getText().trim());
                break;
//            case "Status":
//                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
//                if(!cancel.isReset()) {
//                } else {
//                    Global.root.enableButtonsAfterCancel();
//                    Global.root.enableTabsAfterSave();
//                    enableTabs();
//                    uLPStatusPanel1.disableUpdate(false);
//                }
//                break;
//            case "Statement":
//                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
//                if(!cancel.isReset()) {
//                } else {
//                    Global.root.enableButtonsAfterCancel();
//                    Global.root.enableTabsAfterSave();
//                    enableTabs();
//                    uLPStatement1.disableUpdate(false);
//                }
//                break;
//            case "Recommendation":
//                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
//                if(!cancel.isReset()) {
//                } else {
//                    Global.root.enableButtonsAfterCancel();
//                    Global.root.enableTabsAfterSave();
//                    enableTabs();
//                    uLPRecommendation1.disableUpdate(false);
//                }
//                break;
//            case "Investigation Reveals":
//                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
//                if(!cancel.isReset()) {
//                } else {
//                    Global.root.enableButtonsAfterCancel();
//                    Global.root.enableTabsAfterSave();
//                    enableTabs();
//                    uLPInvestigationReveals1.disableUpdate(false);
//                }
//                break;
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
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private parker.serb.notes.NotesPanel notesPanel2;
    private parker.serb.party.PartiesPanel partiesPanel1;
    private parker.serb.REP.REPCaseInformationPanel rEPCaseInformationPanel1;
    // End of variables declaration//GEN-END:variables
}
