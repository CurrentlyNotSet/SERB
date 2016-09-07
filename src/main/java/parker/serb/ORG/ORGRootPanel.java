/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ORG;

import parker.serb.MED.*;
import javax.swing.JFrame;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import parker.serb.activity.ActivityPanel;
import parker.serb.Global;
import parker.serb.party.PartiesPanel;
import parker.serb.party.PartySearchDialog;
import parker.serb.sql.MEDCaseSearchData;
import parker.serb.util.CancelUpdate;

/**
 *
 * @author parker
 */
public class ORGRootPanel extends javax.swing.JPanel {

    String currentTab = "Activity";
    boolean singleFire = true;
    /**
     * Creates new form REPRootPanel
     */
    public ORGRootPanel() {
        initComponents();
        addListeners();
    }
    
    /**
     * Removes all content from previous stored cases
     */
    void clearAll() {
        Global.root.getoRGHeaderPanel2().clearAll();
        activityPanel1.clearAll();
        oRGParentChildPanel1.clearAll();
        notesPanel2.clearAll();
        oRGInformationPanel1.clearAll();
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
            case "Related Orgs":
                oRGParentChildPanel1.loadInformation();
                break;
            case "Org Information":
                oRGInformationPanel1.loadInformation();
                break;
//            case "Status":
//                mEDCaseStatusPanel1.loadInformation();
//                break;
//            case "Conciliation":
//                mEDConciliationPanel2.loadInformation();
//                break;
//            case "Strike":
//                mEDStrikePanel1.loadInformaiton();
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
            case "Related Orgs":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Link Org");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
//            case "Parties":
//                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
//                Global.root.getjButton2().setText("Add Party");
//                Global.root.getjButton2().setEnabled(true);
//                Global.root.getjButton9().setVisible(true);
//                break;
            case "Org Information":
                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
                Global.root.getjButton2().setText("Update");
                Global.root.getjButton2().setEnabled(true);
                Global.root.getjButton9().setVisible(false);
                break;
//            case "Status":
//                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
//                Global.root.getjButton2().setText("Update");
//                Global.root.getjButton2().setEnabled(true);
//                Global.root.getjButton9().setVisible(false);
//                break;
//            case "Conciliation":
//                System.out.println(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()));
//                Global.root.getjButton2().setText("Update");
//                Global.root.getjButton2().setEnabled(true);
//                Global.root.getjButton9().setVisible(false);
//                break;
//            case "Strike":
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
        Global.root.getoRGHeaderPanel2().getjComboBox2().setEnabled(false);
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            if(i != activeTab) {
                jTabbedPane1.setEnabledAt(i, false);
            } else {
                jTabbedPane1.setEnabledAt(i, true);
            }
        }
    }
    
    private void enableTabs() {
        Global.root.getoRGHeaderPanel2().getjComboBox2().setEnabled(true);
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            jTabbedPane1.setEnabledAt(i, true);
        }
    }
    
    /**
     * Used to update the information in the DB with information from a panel. 
     * Uses the currently selected panel index
     * @param buttonText the text of the current button
     */
    public void orgUpdate(String buttonText) {
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                activityPanel1.loadAllActivity();
                break;
//            case "Parties":
//                new PartySearchDialog((JFrame) this.getRootPane().getParent(), true);
//                partiesPanel1.loadParties();
//                Global.root.getmEDHeaderPanel1().loadHeaderInformation();
//                MEDCaseSearchData.updateCaseEntryFromParties(
//                        Global.root.getmEDHeaderPanel1().getEmployerTextBox().getText().trim(),
//                        Global.root.getmEDHeaderPanel1().getEmployeeOrgTextBox().getText().trim());
//                break;
            case "Related Orgs":
                new ORGAddLinkedOrg(Global.root, true);
                oRGParentChildPanel1.loadInformation();
                break;
            case "Org Information":
                if(buttonText.equals("Update")) {
                    disableTabs(jTabbedPane1.getSelectedIndex());
                    oRGInformationPanel1.enableUpdate();
                } else {
                    enableTabs();
                    Global.root.enableTabsAfterSave();
                    Global.root.enableButtonsAfterCancel();
                    oRGInformationPanel1.disableUpdate(true);
                    Global.root.getoRGHeaderPanel2().loadUpdatedHeaderInformation();
                }
                break;
//            case "Fact Finder":
//                if(buttonText.equals("Update")) {
//                    disableTabs(jTabbedPane1.getSelectedIndex());
//                    mEDFactFinderPanel1.enableUpdate();
//                } else {
//                    enableTabs();
//                    Global.root.enableTabsAfterSave();
//                    Global.root.enableButtonsAfterCancel();
//                    mEDFactFinderPanel1.disableUpdate(true);
//                }
//                break;
//                
//            case "Conciliation":
//                if(buttonText.equals("Update")) {
//                    disableTabs(jTabbedPane1.getSelectedIndex());
//                    mEDConciliationPanel2.enableUpdate();
//                } else {
//                    enableTabs();
//                    Global.root.enableTabsAfterSave();
//                    Global.root.enableButtonsAfterCancel();
//                    mEDConciliationPanel2.disableUpdate(true);
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
    public void orgDelete() {
        CancelUpdate cancel;
        switch (jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex())) {
            case "Activity":
                break;
//            case "Parties":
//                partiesPanel1.removeParty();
//                partiesPanel1.loadParties();
//                Global.root.getmEDHeaderPanel1().loadHeaderInformation();
//                MEDCaseSearchData.updateCaseEntryFromParties(
//                        Global.root.getmEDHeaderPanel1().getEmployerTextBox().getText().trim(),
//                        Global.root.getmEDHeaderPanel1().getEmployeeOrgTextBox().getText().trim());
//                break;
            case "Related Orgs":
                String wording = "Are you sure you want to unlink " + oRGParentChildPanel1.getParentChildTable().getValueAt(oRGParentChildPanel1.getParentChildTable().getSelectedRow(), 1).toString() + " from " + Global.root.getoRGHeaderPanel2().getjComboBox2().getSelectedItem().toString();
                new ORGRemoveLinkedOrgDialog(Global.root, true, wording, oRGParentChildPanel1.getParentChildTable().getValueAt(oRGParentChildPanel1.getParentChildTable().getSelectedRow(), 2).toString(), oRGParentChildPanel1.getParentChildTable().getValueAt(oRGParentChildPanel1.getParentChildTable().getSelectedRow(), 1).toString());
                oRGParentChildPanel1.loadInformation();
                break;
            case "Org Information":
                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
                if(!cancel.isReset()) {
                } else {
                    Global.root.enableButtonsAfterCancel();
                    Global.root.enableTabsAfterSave();
                    enableTabs();
                    oRGInformationPanel1.disableUpdate(false);
                }
                break;  
//            case "Conciliation":
//                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
//                if(!cancel.isReset()) {
//                } else {
//                    Global.root.enableButtonsAfterCancel();
//                    Global.root.enableTabsAfterSave();
//                    enableTabs();
//                    mEDConciliationPanel2.disableUpdate(false);
//                }
//                break;
//            case "Strike":
//                cancel = new CancelUpdate((JFrame) Global.root.getParent(), true);
//                if(!cancel.isReset()) {
//                } else {
//                    Global.root.enableButtonsAfterCancel();
//                    Global.root.enableTabsAfterSave();
//                    enableTabs();
//                    mEDStrikePanel1.disableUpdate(false);
//                }
//                break;
            case "Notes":
                cancel = new CancelUpdate(Global.root, true);
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
        jPanel3 = new javax.swing.JPanel();
        oRGInformationPanel1 = new parker.serb.ORG.ORGInformationPanel();
        jPanel2 = new javax.swing.JPanel();
        oRGParentChildPanel1 = new parker.serb.ORG.ORGParentChildPanel();
        jPanel8 = new javax.swing.JPanel();
        notesPanel2 = new parker.serb.notes.NotesPanel();

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(activityPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(activityPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Activity", jPanel1);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(oRGInformationPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(oRGInformationPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Org Information", jPanel3);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(oRGParentChildPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(oRGParentChildPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Related Orgs", jPanel2);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1010, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(notesPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 559, Short.MAX_VALUE)
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JTabbedPane jTabbedPane1;
    private parker.serb.notes.NotesPanel notesPanel2;
    private parker.serb.ORG.ORGInformationPanel oRGInformationPanel1;
    private parker.serb.ORG.ORGParentChildPanel oRGParentChildPanel1;
    private parker.serb.REP.REPCaseInformationPanel rEPCaseInformationPanel1;
    // End of variables declaration//GEN-END:variables
}
