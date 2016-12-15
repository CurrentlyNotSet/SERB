/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb;

import parker.serb.sql.User;
import parker.serb.REP.REPHeaderPanel;
import parker.serb.REP.REPRootPanel;
import parker.serb.REP.REPReportDialog;
import parker.serb.REP.REPLetterDialog;
import java.awt.CardLayout;
import java.awt.Dimension;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import parker.serb.CMDS.CMDSHeaderPanel;
import parker.serb.CMDS.CMDSLetterDialog;
import parker.serb.CMDS.CMDSRootPanel;
import parker.serb.CSC.CSCHeaderPanel;
import parker.serb.CSC.CSCRootPanel;
import parker.serb.Hearing.HearingHeaderPanel;
import parker.serb.Hearing.HearingRootPanel;
import parker.serb.MED.MEDBulkSendToBoardDialog;
import parker.serb.MED.MEDBulkSettleCasesDialog;
import parker.serb.MED.MEDHeaderPanel;
import parker.serb.MED.MEDLetterDialog;
import parker.serb.MED.MEDRootPanel;
import parker.serb.ORG.ORGAllLettersPanel;
import parker.serb.ORG.ORGHeaderPanel;
import parker.serb.ORG.ORGRootPanel;
import parker.serb.ULP.ULPHeaderPanel;
import parker.serb.ULP.ULPLetterDialog;
import parker.serb.ULP.ULPReportDialog;
import parker.serb.ULP.ULPRootPanel;
import parker.serb.adminDBMaintenance.AdminMainMenuPanel;
import parker.serb.admin.SystemMontiorDialog;
import parker.serb.letterQueue.LetterQueuePanel;
import parker.serb.sql.Audit;
import parker.serb.user.Preferences;
import parker.serb.util.CreateNewCaseDialog;
import parker.serb.login.ExitVerification;
import parker.serb.mailLogViewer.MailLogViewerPanel;
import parker.serb.publicRecords.PublicRecordsMainPanel;
import parker.serb.sql.DocketLock;
import parker.serb.sql.EmailOut;
import parker.serb.sql.NewCaseLock;
import parker.serb.util.CreateNewCSCDialog;
import parker.serb.util.CreateNewHearingDialog;
import parker.serb.util.CreateNewOrgDialog;
import parker.serb.util.NewCaseLockDialog;
import parker.serb.util.ReleaseNotesDialog;


/**
 *
 * @author parker
 */
public class RootPanel extends javax.swing.JFrame {

    /**
     * Creates new form RootPanel
     */
    public RootPanel() {
        initComponents();
        setIconImage( new ImageIcon(getClass().getResource("/SERBSeal.png")).getImage() );
        addListeners();
        Global.root = this;
        User.findAppliedRoles();
        enableTabs();
        setDefaultTab();
        Global.activeSection = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
        setHeaderCard();
        enableButtons();
        letterQueueThread();
        
        //TODO: Move this to a single call to speed up time
        User.updateLastLogInTime();
        User.updateLastPCName();
        User.updateApplicationVersion();
        User.updateActiveLogIn();
        Global.activeUser.activeLogIn = true;
        Audit.addAuditEntry("Logged In");
        setLocationRelativeTo(null);
        setVisible(true);        
    }
    
    /**
     * Enable the tabs that a user is able to see.  This accounts for an admin
     * user not having any tabs removed
     * 
     * Admin - see all
     * Rep - only REP
     * etc
     */
    public void enableTabs() {
        if(!Global.activeUserRoles.contains("Admin")) {
            jMenuBar1.remove(jMenu2);
            jMenuBar1.remove(caseManagementMenu);
            for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
                if(!Global.activeUserRoles.contains(jTabbedPane1.getTitleAt(i))) {
                    jTabbedPane1.remove(i);
                }
            }
        }
    }
    
    private void setDefaultTab() {
        //TODO: Make sure default tab is still able to load else remove and load first tab
        
        if(Global.activeUser.defaultSection != null) {
            jTabbedPane1.setSelectedIndex(jTabbedPane1.indexOfTab(Global.activeUser.defaultSection));
        }
        
        if(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()).equals("Docketing")) {
            docketRootPanel1.loadDocketList();
        }
    }
    
    /**
     * Sets the card displayed depending on the active section
     * Sets the logo displayed depending on the active section
     */
    private void setHeaderCard() {
        CardLayout card = (CardLayout)jPanel9.getLayout();
        
        switch (Global.activeSection) {
            case "Docketing":
                card.show(jPanel9, "card2");
                jLabel1.setIcon(new ImageIcon(getClass().getResource("/SERBSeal.png")));
                break;
            case "REP":
                card.show(jPanel9, "card3");
                jLabel1.setIcon(new ImageIcon(getClass().getResource("/SERBSeal.png")));
                rEPHeaderPanel1.loadCases();
                break;
            case "ULP":
                card.show(jPanel9, "card4");
                jLabel1.setIcon(new ImageIcon(getClass().getResource("/SERBSeal.png")));
                uLPHeaderPanel1.loadCases();
                break;
            case "ORG":
                card.show(jPanel9, "card5");
                jLabel1.setIcon(new ImageIcon(getClass().getResource("/SERBSeal.png")));
                oRGHeaderPanel2.loadCases();
                break;
            case "MED":
                card.show(jPanel9, "card6");
                jLabel1.setIcon(new ImageIcon(getClass().getResource("/SERBSeal.png")));
                mEDHeaderPanel1.loadCases();
                break;    
            case "Hearings":
                card.show(jPanel9, "card7");
                jLabel1.setIcon(new ImageIcon(getClass().getResource("/SERBSeal.png")));
                hearingHeaderPanel1.loadCases();
                break; 
            case "Civil Service Commission":
                card.show(jPanel9, "card8");
                jLabel1.setIcon(new ImageIcon(getClass().getResource("/SPBRSeal.png")));
                cSCHeaderPanel1.loadCases();
                break;  
            case "CMDS":
                card.show(jPanel9, "card9");
                jLabel1.setIcon(new ImageIcon(getClass().getResource("/SPBRSeal.png")));
                cMDSHeaderPanel1.loadCases();
                break;  
            case "Employer Search":
                card.show(jPanel9, "card2");
                jLabel1.setIcon(new ImageIcon(getClass().getResource("/SERBSeal.png")));
                docketingSectionLabel.setText("");
                break;  
        }
    }
    
    /**
     * Add listeners that will watch for section change
     */
    
    private void addListeners() {
        jTabbedPane1.addChangeListener((ChangeEvent e) -> {
            if(Global.activeSection != null) { 
                Audit.addAuditEntry("Navigated to " + Global.activeSection + " section");
                Global.activeSection = jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex());
                
                System.out.println(Global.activeSection);
                
                setHeaderCard();
                enableButtons();
                
                if(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()).equals("Docketing")) {
                    docketRootPanel1.loadDocketList();
                } else if(jTabbedPane1.getTitleAt(jTabbedPane1.getSelectedIndex()).equals("Employer Search")) {
                    if(companySearchPanel1.getModel() == null) {
                        companySearchPanel1.activity();
                    }
                } 
            }
        });
    }
    
    /**
     * Set the visibility and title of all left hand buttons depending on the
     * section that is in active use
     */
    private void enableButtons() {
        Dimension dim = new Dimension(108, 29);
        switch (Global.activeSection) {
            case "Docketing":
                jButton1.setSize(dim);
                jButton1.setMinimumSize(dim);
                jButton1.setMaximumSize(dim);
                jButton1.setText("File");
                jButton1.setEnabled(false);
                jButton1.setVisible(true);
                jButton2.setVisible(false);
                jButton2.setEnabled(false);
                jButton3.setVisible(false);
                jButton4.setVisible(false);
                jButton5.setVisible(false);
                jButton6.setVisible(false);
                jButton7.setVisible(false);
                jButton8.setVisible(false);
                jButton9.setSize(dim);
                jButton9.setMinimumSize(dim);
                jButton9.setMaximumSize(dim);
                jButton9.setVisible(true);
                break;
            case "REP":
                jButton1.setSize(dim);
                jButton1.setMinimumSize(dim);
                jButton1.setMaximumSize(dim);
                jButton1.setText("New Case");
                jButton1.setEnabled(true);
                jButton1.setVisible(true);
                jButton2.setSize(dim);
                jButton2.setMinimumSize(dim);
                jButton2.setMaximumSize(dim);
                jButton2.setVisible(true);
                jButton2.setText("Update");
                jButton2.setEnabled(false);
                jButton3.setVisible(false);
                jButton3.setText("Letters");
                jButton4.setVisible(true);
                jButton4.setText("Reports");
                jButton5.setVisible(false);
                jButton5.setText("Queue");
                jButton6.setVisible(false);
                jButton6.setText("Public Records");
                jButton7.setVisible(false);
                jButton7.setText("Mail Log");
                jButton8.setVisible(false);
                jButton9.setSize(dim);
                jButton9.setMinimumSize(dim);
                jButton9.setMaximumSize(dim);
                jButton9.setEnabled(false);
                jButton9.setVisible(false);
                break;
            case "MED":
                jButton1.setSize(dim);
                jButton1.setMinimumSize(dim);
                jButton1.setMaximumSize(dim);
                jButton1.setText("New Case");
                jButton1.setEnabled(true);
                jButton1.setVisible(true);
                jButton2.setSize(dim);
                jButton2.setMinimumSize(dim);
                jButton2.setMaximumSize(dim);
                jButton2.setVisible(true);
                jButton2.setText("Update");
                jButton2.setEnabled(false);
                jButton3.setVisible(true);
                jButton3.setText("Letters");
                jButton3.setEnabled(false);
                jButton4.setVisible(false);
                jButton4.setText("Reports");
                jButton5.setVisible(true);
                jButton5.setText("Queue");
                jButton6.setVisible(true);
                jButton6.setText("Public Records");
                jButton7.setVisible(true);
                jButton7.setText("Mail Log");
                jButton8.setVisible(false);
                break;
            case "ULP":
                jButton1.setSize(dim);
                jButton1.setMinimumSize(dim);
                jButton1.setMaximumSize(dim);
                jButton1.setText("New Case");
                jButton1.setEnabled(true);
                jButton1.setVisible(true);
                jButton2.setSize(dim);
                jButton2.setMinimumSize(dim);
                jButton2.setMaximumSize(dim);
                jButton2.setVisible(true);
                jButton2.setText("Update");
                jButton2.setEnabled(false);
                jButton3.setVisible(false);
                jButton3.setText("Letters");
                jButton4.setVisible(true);
                jButton4.setText("Reports");
                jButton5.setVisible(false);
                jButton5.setText("Queue");
                jButton6.setVisible(false);
                jButton6.setEnabled(false);
                jButton6.setText("Public Records");
                jButton7.setVisible(false);
                jButton7.setText("Mail Log");
                jButton8.setVisible(false);
                jButton9.setSize(dim);
                jButton9.setMinimumSize(dim);
                jButton9.setMaximumSize(dim);
                jButton9.setVisible(false);
                jButton9.setEnabled(false);
                break;
            case "ORG":
                jButton1.setSize(dim);
                jButton1.setMinimumSize(dim);
                jButton1.setMaximumSize(dim);
                jButton1.setText("New Org");
                jButton1.setEnabled(true);
                jButton1.setVisible(true);
                jButton2.setSize(dim);
                jButton2.setMinimumSize(dim);
                jButton2.setMaximumSize(dim);
                jButton2.setVisible(true);
                jButton2.setText("Update");
                jButton2.setEnabled(false);
                jButton3.setSize(dim);
                jButton3.setVisible(false);
                jButton3.setText("All Org Letters");
                jButton4.setVisible(false);
                jButton4.setText("Single Letter");
                jButton5.setVisible(false);
                jButton5.setText("Reports");                
                jButton6.setVisible(true);
                jButton6.setText("Queue");                
                jButton7.setVisible(true);
                jButton7.setText("Public Records");                
                jButton8.setVisible(true);
                jButton8.setText("Mail Log");
                break;
            case "Civil Service Commission":
                jButton1.setSize(dim);
                jButton1.setMinimumSize(dim);
                jButton1.setMaximumSize(dim);
                jButton1.setText("New CSC");
                jButton1.setEnabled(true);
                jButton1.setVisible(true);
                jButton2.setSize(dim);
                jButton2.setMinimumSize(dim);
                jButton2.setMaximumSize(dim);
                jButton2.setVisible(true);
                jButton2.setText("Update");
                jButton2.setEnabled(false);
                jButton3.setSize(dim);
                jButton3.setVisible(false);
                jButton3.setText("All Org Letters");
                jButton4.setVisible(false);
                jButton4.setText("Single Letter");
                jButton5.setVisible(false);
                jButton5.setText("Reports");
                jButton6.setVisible(true);
                jButton6.setText("Queue");
                jButton7.setVisible(true);
                jButton7.setText("Public Records");
                jButton8.setVisible(true);
                jButton8.setText("Mail Log");
                break;
            case "CMDS":
                jButton1.setSize(dim);
                jButton1.setMinimumSize(dim);
                jButton1.setMaximumSize(dim);
                jButton1.setText("New Case");
                jButton1.setEnabled(true);
                jButton1.setVisible(true);
                jButton2.setSize(dim);
                jButton2.setMinimumSize(dim);
                jButton2.setMaximumSize(dim);
                jButton2.setVisible(true);
                jButton2.setText("Update");
                jButton2.setEnabled(false);
                jButton3.setSize(dim);
                jButton3.setVisible(false);
                jButton3.setText("All Org Letters");
                jButton4.setVisible(true);
                jButton4.setText("Letters");
                jButton5.setVisible(false);
                jButton5.setText("Reports");
                jButton6.setVisible(true);
                jButton6.setText("Queue");
                jButton7.setVisible(true);
                jButton7.setText("Public Records");
                jButton8.setVisible(true);
                jButton8.setText("Mail Log");
                break;
            case "Hearings":
                jButton1.setSize(dim);
                jButton1.setMinimumSize(dim);
                jButton1.setMaximumSize(dim);
                jButton1.setText("New Hearing");
                jButton1.setEnabled(true);
                jButton1.setVisible(true);
                jButton2.setSize(dim);
                jButton2.setMinimumSize(dim);
                jButton2.setMaximumSize(dim);
                jButton2.setVisible(true);
                jButton2.setText("Update");
                jButton2.setEnabled(false);
                jButton3.setSize(dim);
                jButton3.setVisible(false);
                jButton3.setText("All Org Letters");
                jButton4.setVisible(false);
                jButton4.setText("Single Letter");
                jButton5.setVisible(false);
                jButton5.setText("Reports");
                jButton6.setVisible(false);
                jButton6.setText("Queue");
                jButton7.setVisible(false);
                jButton7.setText("Public Records");
                jButton8.setVisible(false);
                jButton8.setText("Mail Log");
                break;    
            case "Employer Search":
                jButton1.setSize(dim);
                jButton1.setMaximumSize(dim);
                jButton1.setText("");
                jButton1.setVisible(false);
                jButton2.setSize(dim);
                jButton2.setMaximumSize(dim);
                jButton2.setVisible(false);
                jButton2.setText("Update");
                jButton2.setEnabled(false);
                jButton3.setSize(dim);
                jButton3.setVisible(false);
                jButton3.setText("All Org Letters");
                jButton4.setVisible(false);
                jButton4.setText("Single Letter");
                jButton5.setVisible(false);
                jButton5.setText("Reports");
                jButton6.setVisible(false);
                jButton6.setText("Queue");
                jButton7.setVisible(false);
                jButton6.setText("Public Records");
                jButton8.setVisible(false);
                jButton9.setVisible(false);
                break;    
        }
    }
    
    /**
     * Thread for letter queue count
     */
    private void letterQueueThread(){
        Thread cmdsThread = new Thread() {
            @Override
            public void run() {
                updateLetterQueueButton();
            }
        };
        cmdsThread.start(); 
    }

    private void updateLetterQueueButton() {
        try {
            Thread.sleep(1000);
            while (true) {
                try {
                    updateQueueButtonText();
                    Thread.sleep(30000); //milliseconds  (30 sec)

                } catch (InterruptedException ex) {
                    System.err.println("Thread Interrupted");
                }
            }
        } catch (InterruptedException ex) {
            System.err.println("Thread Interrupted");
        }
    }

    private void updateQueueButtonText(){
        int count = EmailOut.getEmailCount(Global.activeSection);
        
        String amount = "";
//        if (count > 0){
            amount = " (" + String.valueOf(count) + ")";
//        }
        
        switch(Global.activeSection) {
            case "REP":
            case "ULP":
            case "MED":
                jButton5.setText("Queue" + amount);
                break;
            case "ORG":
            case "Civil Service Commission":
            case "CMDS":
                jButton6.setText("Queue" + amount);
                break;
            case "Hearings":
                break;    
            default:
                break;
        }
    }
    
    
    /**
     * This will disable all tabs that are not of the active section, this
     * prevents a user from navigating a way from the tab while editing.
     * 
     * @param activeTab - the current tab that represents teh section in use 
     */
    
    public void disableTabs(int activeTab) {
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            if(i != activeTab) {
                jTabbedPane1.setEnabledAt(i, false);
            } else {
                jTabbedPane1.setEnabledAt(i, true);
            }
        }
    }
    
    /**
     * Enable all tabs after a save action or cancel action has bee completed
     */
    //TODO: Rename this to enableTabs
    public void enableTabsAfterSave() {
        for(int i = jTabbedPane1.getTabCount()-1; i >= 0; i--) {
            jTabbedPane1.setEnabledAt(i, true);
        }
    }
    
    /**
     * Disable all button when updating a panel
     */
    private void disableButtons() {
        jButton1.setEnabled(false);
        jButton3.setEnabled(false);
        jButton4.setEnabled(false);
        jButton5.setEnabled(false);
        jButton6.setEnabled(false);
        jButton7.setEnabled(false);
        jButton8.setEnabled(false);
        jButton9.setEnabled(true);
        jButton9.setText("Cancel");
    }
    
    public void enableButtonsAfterCancel() {
        jButton1.setEnabled(true);
        jButton3.setEnabled(true);
        jButton4.setEnabled(true);
        jButton5.setEnabled(true);
        jButton6.setEnabled(true);
        jButton7.setEnabled(true);
        jButton8.setEnabled(true);
        jButton9.setEnabled(false);
        jButton9.setText("Delete");
    }

    public JTabbedPane getjTabbedPane1() {
        return jTabbedPane1;
    }

    public JButton getjButton1() {
        return jButton1;
    }
    
    public JButton getjButton2() {
        return jButton2;
    }
    
    public JButton getjButton6() {
        return jButton6;
    }
    
    public REPRootPanel getrEPRootPanel1() {
        return rEPRootPanel1;
    }

    public REPHeaderPanel getrEPHeaderPanel1() {
        return rEPHeaderPanel1;
    }

    public JButton getjButton9() {
        return jButton9;
    }

    public ULPHeaderPanel getuLPHeaderPanel1() {
        return uLPHeaderPanel1;
    }

    public ULPRootPanel getuLPRootPanel1() {
        return uLPRootPanel1;
    }

    public MEDHeaderPanel getmEDHeaderPanel1() {
        return mEDHeaderPanel1;
    }

    public MEDRootPanel getmEDRootPanel1() {
        return mEDRootPanel1;
    }

    public JLabel getDocketingSectionLabel() {
        return docketingSectionLabel;
    }

    public JPanel getjPanel13() {
        return jPanel13;
    }

    public JPanel getDocketing() {
        return Docketing;
    }

    public ORGHeaderPanel getoRGHeaderPanel2() {
        return oRGHeaderPanel2;
    }

    public ORGRootPanel getoRGRootPanel1() {
        return oRGRootPanel1;
    }

    public CSCHeaderPanel getcSCHeaderPanel1() {
        return cSCHeaderPanel1;
    }

    public CSCRootPanel getcSCRootPanel1() {
        return cSCRootPanel1;
    }

    public CMDSHeaderPanel getcMDSHeaderPanel1() {
        return cMDSHeaderPanel1;
    }

    public CMDSRootPanel getcMDSRootPanel1() {
        return cMDSRootPanel1;
    }

    public JButton getjButton3() {
        return jButton3;
    }

    public HearingHeaderPanel getHearingHeaderPanel1() {
        return hearingHeaderPanel1;
    }

    public HearingRootPanel getHearingRootPanel1() {
        return hearingRootPanel1;
    }
        
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        docketRootPanel = new parker.serb.docket.DocketRootPanel();
        jMenuItem2 = new javax.swing.JMenuItem();
        oRGHeaderPanel1 = new parker.serb.ORG.ORGHeaderPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        Docketing = new javax.swing.JPanel();
        jPanel13 = new javax.swing.JPanel();
        docketingSectionLabel = new javax.swing.JLabel();
        REP = new javax.swing.JPanel();
        rEPHeaderPanel1 = new parker.serb.REP.REPHeaderPanel();
        ULP = new javax.swing.JPanel();
        uLPHeaderPanel1 = new parker.serb.ULP.ULPHeaderPanel();
        ORG = new javax.swing.JPanel();
        oRGHeaderPanel2 = new parker.serb.ORG.ORGHeaderPanel();
        MED = new javax.swing.JPanel();
        mEDHeaderPanel1 = new parker.serb.MED.MEDHeaderPanel();
        Hearing = new javax.swing.JPanel();
        hearingHeaderPanel1 = new parker.serb.Hearing.HearingHeaderPanel();
        CSC = new javax.swing.JPanel();
        cSCHeaderPanel1 = new parker.serb.CSC.CSCHeaderPanel();
        CMDS = new javax.swing.JPanel();
        cMDSHeaderPanel1 = new parker.serb.CMDS.CMDSHeaderPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        docketRootPanel1 = new parker.serb.docket.DocketRootPanel();
        jPanel3 = new javax.swing.JPanel();
        rEPRootPanel1 = new parker.serb.REP.REPRootPanel();
        jPanel4 = new javax.swing.JPanel();
        oRGRootPanel1 = new parker.serb.ORG.ORGRootPanel();
        jPanel5 = new javax.swing.JPanel();
        uLPRootPanel1 = new parker.serb.ULP.ULPRootPanel();
        jPanel6 = new javax.swing.JPanel();
        mEDRootPanel1 = new parker.serb.MED.MEDRootPanel();
        jPanel10 = new javax.swing.JPanel();
        cSCRootPanel1 = new parker.serb.CSC.CSCRootPanel();
        jPanel11 = new javax.swing.JPanel();
        cMDSRootPanel1 = new parker.serb.CMDS.CMDSRootPanel();
        jPanel12 = new javax.swing.JPanel();
        hearingRootPanel1 = new parker.serb.Hearing.HearingRootPanel();
        jPanel14 = new javax.swing.JPanel();
        companySearchPanel1 = new parker.serb.companySearch.companySearchPanel();
        jPanel7 = new javax.swing.JPanel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jButton6 = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jButton8 = new javax.swing.JButton();
        jButton9 = new javax.swing.JButton();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem3 = new javax.swing.JMenuItem();
        jMenuItem4 = new javax.swing.JMenuItem();
        caseManagementMenu = new javax.swing.JMenu();
        jMenu4 = new javax.swing.JMenu();
        MEDSendToBoardToCloseMenuItem = new javax.swing.JMenuItem();
        MEDSettleCasesMenuItem = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();
        adminPanelMenuItem = new javax.swing.JMenuItem();
        jMenuItem5 = new javax.swing.JMenuItem();
        jMenu3 = new javax.swing.JMenu();
        jMenuItem6 = new javax.swing.JMenuItem();

        jMenuItem2.setText("jMenuItem2");

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("State Employment Relations Board");
        setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        setMinimumSize(new java.awt.Dimension(1199, 704));
        setSize(new java.awt.Dimension(1199, 704));
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowClosing(java.awt.event.WindowEvent evt) {
                formWindowClosing(evt);
            }
        });

        jPanel1.setPreferredSize(new java.awt.Dimension(1199, 179));

        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/SERBSeal.png"))); // NOI18N

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 144, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel9.setPreferredSize(new java.awt.Dimension(1037, 175));
        jPanel9.setLayout(new java.awt.CardLayout());

        Docketing.setPreferredSize(new java.awt.Dimension(1037, 173));

        docketingSectionLabel.setFont(new java.awt.Font("Lucida Grande", 0, 36)); // NOI18N
        docketingSectionLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        docketingSectionLabel.setText("ULP DOCKETING");

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(docketingSectionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 1079, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(docketingSectionLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 161, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout DocketingLayout = new javax.swing.GroupLayout(Docketing);
        Docketing.setLayout(DocketingLayout);
        DocketingLayout.setHorizontalGroup(
            DocketingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 1099, Short.MAX_VALUE)
            .addGroup(DocketingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(DocketingLayout.createSequentialGroup()
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGap(0, 0, Short.MAX_VALUE)))
        );
        DocketingLayout.setVerticalGroup(
            DocketingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 173, Short.MAX_VALUE)
            .addGroup(DocketingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel9.add(Docketing, "card2");

        javax.swing.GroupLayout REPLayout = new javax.swing.GroupLayout(REP);
        REP.setLayout(REPLayout);
        REPLayout.setHorizontalGroup(
            REPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPHeaderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        REPLayout.setVerticalGroup(
            REPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPHeaderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 200, Short.MAX_VALUE)
        );

        jPanel9.add(REP, "card3");

        javax.swing.GroupLayout ULPLayout = new javax.swing.GroupLayout(ULP);
        ULP.setLayout(ULPLayout);
        ULPLayout.setHorizontalGroup(
            ULPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPHeaderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        ULPLayout.setVerticalGroup(
            ULPLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ULPLayout.createSequentialGroup()
                .addComponent(uLPHeaderPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.add(ULP, "card4");

        javax.swing.GroupLayout ORGLayout = new javax.swing.GroupLayout(ORG);
        ORG.setLayout(ORGLayout);
        ORGLayout.setHorizontalGroup(
            ORGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(oRGHeaderPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        ORGLayout.setVerticalGroup(
            ORGLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(ORGLayout.createSequentialGroup()
                .addComponent(oRGHeaderPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.add(ORG, "card5");

        javax.swing.GroupLayout MEDLayout = new javax.swing.GroupLayout(MED);
        MED.setLayout(MEDLayout);
        MEDLayout.setHorizontalGroup(
            MEDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mEDHeaderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        MEDLayout.setVerticalGroup(
            MEDLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(MEDLayout.createSequentialGroup()
                .addComponent(mEDHeaderPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.add(MED, "card6");

        javax.swing.GroupLayout HearingLayout = new javax.swing.GroupLayout(Hearing);
        Hearing.setLayout(HearingLayout);
        HearingLayout.setHorizontalGroup(
            HearingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hearingHeaderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        HearingLayout.setVerticalGroup(
            HearingLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HearingLayout.createSequentialGroup()
                .addComponent(hearingHeaderPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.add(Hearing, "card7");

        javax.swing.GroupLayout CSCLayout = new javax.swing.GroupLayout(CSC);
        CSC.setLayout(CSCLayout);
        CSCLayout.setHorizontalGroup(
            CSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cSCHeaderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        CSCLayout.setVerticalGroup(
            CSCLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CSCLayout.createSequentialGroup()
                .addComponent(cSCHeaderPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.add(CSC, "card8");

        javax.swing.GroupLayout CMDSLayout = new javax.swing.GroupLayout(CMDS);
        CMDS.setLayout(CMDSLayout);
        CMDSLayout.setHorizontalGroup(
            CMDSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cMDSHeaderPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1099, Short.MAX_VALUE)
        );
        CMDSLayout.setVerticalGroup(
            CMDSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(CMDSLayout.createSequentialGroup()
                .addComponent(cMDSHeaderPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel9.add(CMDS, "card9");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 168, Short.MAX_VALUE))
                .addContainerGap())
        );

        jTabbedPane1.setName("Docket"); // NOI18N

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(docketRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1120, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(docketRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Docketing", jPanel2);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1120, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(rEPRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("REP", jPanel3);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(oRGRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(oRGRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ORG", jPanel4);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1120, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uLPRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("ULP", jPanel5);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mEDRootPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(mEDRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("MED", jPanel6);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cSCRootPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cSCRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Civil Service Commission", jPanel10);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cMDSRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(cMDSRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("CMDS", jPanel11);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hearingRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(hearingRootPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Hearings", jPanel12);

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(companySearchPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1120, Short.MAX_VALUE)
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(companySearchPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 681, Short.MAX_VALUE)
        );

        jTabbedPane1.addTab("Employer Search", jPanel14);

        jPanel7.setMaximumSize(new java.awt.Dimension(108, 363));
        jPanel7.setMinimumSize(new java.awt.Dimension(108, 363));

        jButton1.setText("BUTTON 1");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jButton2.setText("BUTTON 2");
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });

        jButton3.setText("BUTTON 3");
        jButton3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton3ActionPerformed(evt);
            }
        });

        jButton4.setText("BUTTON 4");
        jButton4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton4ActionPerformed(evt);
            }
        });

        jButton5.setText("BUTTON 5");
        jButton5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton5ActionPerformed(evt);
            }
        });

        jButton6.setText("BUTTON 6");
        jButton6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton6ActionPerformed(evt);
            }
        });

        jButton7.setText("BUTTON 7");
        jButton7.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton7ActionPerformed(evt);
            }
        });

        jButton8.setText("BUTTON 8");
        jButton8.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton8ActionPerformed(evt);
            }
        });

        jButton9.setText("Delete");
        jButton9.setEnabled(false);
        jButton9.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton9ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jButton2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jButton1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton8)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jButton9)
                .addContainerGap())
        );

        jMenu1.setText("File");

        jMenuItem1.setText("Preferences");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);
        jMenu1.add(jSeparator1);

        jMenuItem3.setText("Log Off");
        jMenuItem3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem3ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem3);

        jMenuItem4.setText("Quit");
        jMenuItem4.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem4ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem4);

        jMenuBar1.add(jMenu1);

        caseManagementMenu.setText("Case Management");

        jMenu4.setText("MED Bulk Edit");

        MEDSendToBoardToCloseMenuItem.setText("Send to Board");
        MEDSendToBoardToCloseMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MEDSendToBoardToCloseMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(MEDSendToBoardToCloseMenuItem);

        MEDSettleCasesMenuItem.setText("Settle Cases");
        MEDSettleCasesMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MEDSettleCasesMenuItemActionPerformed(evt);
            }
        });
        jMenu4.add(MEDSettleCasesMenuItem);

        caseManagementMenu.add(jMenu4);

        jMenuBar1.add(caseManagementMenu);

        jMenu2.setText("Admin");

        adminPanelMenuItem.setText("Admin Panel");
        adminPanelMenuItem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                adminPanelMenuItemActionPerformed(evt);
            }
        });
        jMenu2.add(adminPanelMenuItem);

        jMenuItem5.setText("Tracking");
        jMenuItem5.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem5ActionPerformed(evt);
            }
        });
        jMenu2.add(jMenuItem5);

        jMenuBar1.add(jMenu2);

        jMenu3.setText("Help");

        jMenuItem6.setText("Release Notes");
        jMenuItem6.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem6ActionPerformed(evt);
            }
        });
        jMenu3.add(jMenuItem6);

        jMenuBar1.add(jMenu3);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, 1296, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jTabbedPane1))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTabbedPane1)))
        );

        jTabbedPane1.getAccessibleContext().setAccessibleName("Hearings");

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButton9ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton9ActionPerformed
        switch(Global.activeSection) {
            case "REP":
                rEPRootPanel1.repDelete();
                break;
            case "ULP":
                uLPRootPanel1.ulpDelete();
                break;
            case "MED":
                mEDRootPanel1.medDelete();
                break;
            case "ORG":
                oRGRootPanel1.orgDelete();
                break;
            case "Docketing":
                docketRootPanel1.delete();
                break;
            case "Civil Service Commission":
                cSCRootPanel1.cscDelete();
                break;
            case "CMDS":
                cMDSRootPanel1.cmdsDelete();
                break;
            case "Hearings":
                hearingRootPanel1.hearingDelete();
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton9ActionPerformed

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        switch(Global.activeSection) {
            case "REP":
                new REPLetterDialog((JFrame) this.getRootPane().getParent(), true);
                break;
            case "ULP":
                new ULPLetterDialog((JFrame) this.getRootPane().getParent(), true);
                break;
            case "MED":
                new MEDLetterDialog((JFrame) this.getRootPane().getParent(), true);
                break;
            case "ORG":
                new ORGAllLettersPanel((JFrame) this.getRootPane().getParent(), true);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton3ActionPerformed

    private void jButton6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton6ActionPerformed
       switch(Global.activeSection) {
            case "ORG":
            case "CMDS":
            case "Civil Service Commission":   
                new LetterQueuePanel((JFrame) this.getRootPane().getParent(), true);
                break;
            default:
                new PublicRecordsMainPanel((JFrame) this.getRootPane().getParent(), true);
                break;
        }       
    }//GEN-LAST:event_jButton6ActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        NewCaseLock caseLock;
        switch(Global.activeSection) {
            
            case "Docketing":
                docketRootPanel1.displayFileDialog();
                break;
            case "REP":
            case "ULP":
            case "MED":
                caseLock = NewCaseLock.checkLock(Global.activeSection);
                if(caseLock == null) {
                    caseLock.addLock(Global.activeSection);
                    new CreateNewCaseDialog(Global.root, true);
                    caseLock.removeLock(Global.activeSection);
                } else {
                    new NewCaseLockDialog(Global.root, true, caseLock);
                }
                break;
            case "ORG":
                caseLock = NewCaseLock.checkLock(Global.activeSection);
                if(caseLock == null) {
                    caseLock.addLock(Global.activeSection);
                    new CreateNewOrgDialog(Global.root, true);
                    caseLock.removeLock(Global.activeSection);
                } else {
                    new NewCaseLockDialog(Global.root, true, caseLock);
                }
                break;
            case "Civil Service Commission":
                caseLock = NewCaseLock.checkLock("CSC");
                if(caseLock == null) {
                    caseLock.addLock("CSC");
                    new CreateNewCSCDialog(Global.root, true);
                    caseLock.removeLock("CSC");
                } else {
                    new NewCaseLockDialog(Global.root, true, caseLock);
                }
                break;
            case "CMDS":
                caseLock = NewCaseLock.checkLock(Global.activeSection);
                if(caseLock == null) {
                    caseLock.addLock(Global.activeSection);
                    new CreateNewCaseDialog(Global.root, true);
                    caseLock.removeLock(Global.activeSection);
                } else {
                    new NewCaseLockDialog(Global.root, true, caseLock);
                }
                break;
            case "Hearings":
//                caseLock = NewCaseLock.checkLock(Global.activeSection);
//                if(caseLock == null) {
//                    caseLock.addLock(Global.activeSection);
                    new CreateNewHearingDialog(Global.root, true);
//                    caseLock.removeLock(Global.activeSection);
//                } else {
//                    new NewCaseLockDialog(Global.root, true, caseLock);
//                }
                break;    
            default:
                break;
        }
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        if(jButton2.getText().equals("Update")) {
            disableTabs(jTabbedPane1.getSelectedIndex());
            disableButtons(); 
        }
        
        switch(Global.activeSection) {
            case "REP":
                rEPRootPanel1.repUpdate(jButton2.getText());
                break;
            case "ULP":
                uLPRootPanel1.ulpUpdate(jButton2.getText());
                break;
            case "MED":
                mEDRootPanel1.medUpdate(jButton2.getText());
                break;
            case "ORG":
                oRGRootPanel1.orgUpdate(jButton2.getText());
                break;
            case "Civil Service Commission":
                cSCRootPanel1.cscUpdate(jButton2.getText());
                break;
            case "CMDS":
                cMDSRootPanel1.cmdsUpdate(jButton2.getText());
                break;
            case "Hearings":
                hearingRootPanel1.hearingUpdate(jButton2.getText());
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton2ActionPerformed

    private void jButton4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton4ActionPerformed
        switch(Global.activeSection) {
            case "REP":
                new REPReportDialog((JFrame) this.getRootPane().getParent(), true);
                break;
            case "ULP":
                new ULPReportDialog((JFrame) this.getRootPane().getParent(), true);
                break;
            case "CMDS":
                new CMDSLetterDialog((JFrame) this.getRootPane().getParent(), true);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton4ActionPerformed

    private void formWindowClosing(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowClosing
        DocketLock.removeUserLocks();
        NewCaseLock.removeUserLocks();
        User.updateActiveLogIn();
        Audit.addAuditEntry("Logged Off");
        System.exit(0);
    }//GEN-LAST:event_formWindowClosing

    private void jMenuItem3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem3ActionPerformed
        new ExitVerification((JFrame) getRootPane().getParent(), true, "Log Off");
    }//GEN-LAST:event_jMenuItem3ActionPerformed

    private void jMenuItem4ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem4ActionPerformed
        new ExitVerification((JFrame) getRootPane().getParent(), true, "Quit");
    }//GEN-LAST:event_jMenuItem4ActionPerformed

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        new Preferences((JFrame) getRootPane().getParent(), true);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    private void jMenuItem5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem5ActionPerformed
        new SystemMontiorDialog((JFrame) getRootPane().getParent(), true);
    }//GEN-LAST:event_jMenuItem5ActionPerformed

    private void jMenuItem6ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem6ActionPerformed
        new ReleaseNotesDialog((JFrame) getRootPane().getParent(), true);
    }//GEN-LAST:event_jMenuItem6ActionPerformed

    private void adminPanelMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_adminPanelMenuItemActionPerformed
        new AdminMainMenuPanel((JFrame) getRootPane().getParent(), true);
    }//GEN-LAST:event_adminPanelMenuItemActionPerformed

    private void MEDSendToBoardToCloseMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MEDSendToBoardToCloseMenuItemActionPerformed
        new MEDBulkSendToBoardDialog((JFrame) getRootPane().getParent(), true);
    }//GEN-LAST:event_MEDSendToBoardToCloseMenuItemActionPerformed

    private void MEDSettleCasesMenuItemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MEDSettleCasesMenuItemActionPerformed
        new MEDBulkSettleCasesDialog((JFrame) getRootPane().getParent(), true);
    }//GEN-LAST:event_MEDSettleCasesMenuItemActionPerformed

    private void jButton5ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton5ActionPerformed
        switch(Global.activeSection) {
            case "REP":
            case "ULP":
            case "MED":    
                new LetterQueuePanel((JFrame) this.getRootPane().getParent(), true);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton5ActionPerformed

    private void jButton7ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton7ActionPerformed
        switch(Global.activeSection) {
            case "REP":
            case "ULP":
            case "MED":    
                new MailLogViewerPanel((JFrame) this.getRootPane().getParent(), true);
                break;
            case "ORG":
            case "Civil Service Commission":
            case "CMDS":    
                new PublicRecordsMainPanel((JFrame) this.getRootPane().getParent(), true);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton7ActionPerformed

    private void jButton8ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton8ActionPerformed
        switch(Global.activeSection) {
            case "ORG":
            case "Civil Service Commission":
            case "CMDS":    
                new MailLogViewerPanel((JFrame) this.getRootPane().getParent(), true);
                break;
            default:
                break;
        }
    }//GEN-LAST:event_jButton8ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel CMDS;
    private javax.swing.JPanel CSC;
    private javax.swing.JPanel Docketing;
    private javax.swing.JPanel Hearing;
    private javax.swing.JPanel MED;
    private javax.swing.JMenuItem MEDSendToBoardToCloseMenuItem;
    private javax.swing.JMenuItem MEDSettleCasesMenuItem;
    private javax.swing.JPanel ORG;
    private javax.swing.JPanel REP;
    private javax.swing.JPanel ULP;
    private javax.swing.JMenuItem adminPanelMenuItem;
    private parker.serb.CMDS.CMDSHeaderPanel cMDSHeaderPanel1;
    private parker.serb.CMDS.CMDSRootPanel cMDSRootPanel1;
    private parker.serb.CSC.CSCHeaderPanel cSCHeaderPanel1;
    private parker.serb.CSC.CSCRootPanel cSCRootPanel1;
    private javax.swing.JMenu caseManagementMenu;
    private parker.serb.companySearch.companySearchPanel companySearchPanel1;
    private parker.serb.docket.DocketRootPanel docketRootPanel;
    private parker.serb.docket.DocketRootPanel docketRootPanel1;
    private javax.swing.JLabel docketingSectionLabel;
    private parker.serb.Hearing.HearingHeaderPanel hearingHeaderPanel1;
    private parker.serb.Hearing.HearingRootPanel hearingRootPanel1;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButton5;
    private javax.swing.JButton jButton6;
    private javax.swing.JButton jButton7;
    private javax.swing.JButton jButton8;
    private javax.swing.JButton jButton9;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenu jMenu3;
    private javax.swing.JMenu jMenu4;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JMenuItem jMenuItem2;
    private javax.swing.JMenuItem jMenuItem3;
    private javax.swing.JMenuItem jMenuItem4;
    private javax.swing.JMenuItem jMenuItem5;
    private javax.swing.JMenuItem jMenuItem6;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JTabbedPane jTabbedPane1;
    private parker.serb.MED.MEDHeaderPanel mEDHeaderPanel1;
    private parker.serb.MED.MEDRootPanel mEDRootPanel1;
    private parker.serb.ORG.ORGHeaderPanel oRGHeaderPanel1;
    private parker.serb.ORG.ORGHeaderPanel oRGHeaderPanel2;
    private parker.serb.ORG.ORGRootPanel oRGRootPanel1;
    private parker.serb.REP.REPHeaderPanel rEPHeaderPanel1;
    private parker.serb.REP.REPRootPanel rEPRootPanel1;
    private parker.serb.ULP.ULPHeaderPanel uLPHeaderPanel1;
    private parker.serb.ULP.ULPRootPanel uLPRootPanel1;
    // End of variables declaration//GEN-END:variables
}
