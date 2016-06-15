/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import java.awt.CardLayout;
import java.awt.Color;
import java.sql.Timestamp;
import parker.serb.Global;
import parker.serb.sql.REPCase;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class REPElectionPanel extends javax.swing.JPanel {

    CardLayout resultsCard, siteCard;
    REPCase repCase;
    /**
     * Creates new form REPElectionPanel
     */
    public REPElectionPanel() {
        initComponents();
        resultsCard = (CardLayout)jPanel4.getLayout();
        siteCard = (CardLayout) jPanel14.getLayout();
        jPanel4.setVisible(false);
//        hideNotRequiredInformation();
    }
    
    private void hideNotRequiredInformation() {
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel4.setVisible(false);
        addMultiCaseElectionButton.setVisible(false);
        addSiteInformation.setVisible(false);
    }
    
    //if missing a descriptor it is on the electronic/mail panel
    
    public void  loadInformation() {
        repCase = REPCase.loadElectionInformation();
        
        multiCaseElectionCheckBox.setSelected(repCase.multicaseElection);
        
        if(multiCaseElectionCheckBox.isSelected()) {
            //TODO: loadMultiCaseElection
        }
        
        if(jPanel2.isVisible()) {
            //TODO: loadSites
        }
        
        electionType1ComboBox.setSelectedItem(repCase.electionType1 == null ? " " : repCase.electionType1);
        electionType2ComboBox.setSelectedItem(repCase.electionType2 == null ? " " : repCase.electionType2);
        electionType3ComboBox.setSelectedItem(repCase.electionType3 == null ? " " : repCase.electionType3);
        
        eligibiltyDateTextBox.setText(repCase.eligibilityDate == null ? "" : Global.mmddyyyy.format(repCase.eligibilityDate.getTime()));
        eligibilityDate.setText(repCase.eligibilityDate == null ? "" : Global.mmddyyyy.format(repCase.eligibilityDate.getTime()));
        ballotOneTextBox.setText(repCase.ballotOne == null ? "" : repCase.ballotOne);
        ballotOne.setText(repCase.ballotOne == null ? "" : repCase.ballotOne);
        ballotTwoTextBox.setText(repCase.ballotTwo == null ? "" : repCase.ballotTwo);
        ballotTwo.setText(repCase.ballotTwo == null ? "" : repCase.ballotTwo);
        ballotThreeTextBox.setText(repCase.ballotThree == null ? "" : repCase.ballotThree);
        ballotThree.setText(repCase.ballotThree == null ? "" : repCase.ballotThree);
        ballotFourTextBox.setText(repCase.ballotFour == null ? "" : repCase.ballotFour);
        ballotFour.setText(repCase.ballotFour == null ? "" : repCase.ballotFour);
        mailKitDate.setText(repCase.mailKitDate == null ? "" : Global.mmddyyyy.format(repCase.mailKitDate));
        pollingStartDate.setText(repCase.pollingStartDate == null ? "" : Global.mmddyyyy.format(repCase.pollingStartDate));
        pollingEndDate.setText(repCase.pollingEndDate == null ? "" : Global.mmddyyyy.format(repCase.pollingEndDate));
        ballotsCountDay.setSelectedItem(repCase.ballotsCountDay == null ? "" : repCase.ballotsCountDay);
        ballotsCountDate.setText(repCase.ballotsCountDate == null ? "" : Global.mmddyyyy.format(repCase.ballotsCountDate));
//        ballotsCountTime.setText(repCase.ballotsCountTime);
        eligibilityListDate.setText(repCase.eligibilityListDate == null ? "" : Global.mmddyyyy.format(repCase.eligibilityListDate));
        preElectionConfDateTextBox.setText(repCase.preElectionConfDate == null ? "" : Global.mmddyyyy.format(repCase.preElectionConfDate.getTime()));
        selfReleasingTextBox.setText(repCase.selfReleasing == null ? "" : repCase.selfReleasing);
    }
    
    public void disableUpdate(boolean runSave) {
        
        Global.root.getjButton2().setText("Update");
        
        Global.root.getjButton9().setVisible(false);
        
        multiCaseElectionCheckBox.setEnabled(false);
        addMultiCaseElectionButton.setVisible(false);
        
        electionType1ComboBox.setEnabled(false);
        electionType2ComboBox.setEnabled(false);
        electionType3ComboBox.setEnabled(false);
        
        eligibiltyDateTextBox.setEnabled(false);
        eligibiltyDateTextBox.setBackground(new Color(238, 238, 238));
        eligibilityDate.setEnabled(false);
        eligibilityDate.setBackground(new Color(238, 238, 238));
        ballotOneTextBox.setEnabled(false);
        ballotOneTextBox.setBackground(new Color(238, 238, 238));
        ballotOne.setEnabled(false);
        ballotOne.setBackground(new Color(238, 238, 238));
        ballotTwoTextBox.setEnabled(false);
        ballotTwoTextBox.setBackground(new Color(238, 238, 238));
        ballotTwo.setEnabled(false);
        ballotTwo.setBackground(new Color(238, 238, 238));
        ballotThreeTextBox.setEnabled(false);
        ballotThreeTextBox.setBackground(new Color(238, 238, 238));
        ballotThree.setEnabled(false);
        ballotThree.setBackground(new Color(238, 238, 238));
        ballotFourTextBox.setEnabled(false);
        ballotFourTextBox.setBackground(new Color(238, 238, 238));
        ballotFour.setEnabled(false);
        ballotFour.setBackground(new Color(238, 238, 238));
        mailKitDate.setEnabled(false);
        mailKitDate.setBackground(new Color(238, 238, 238));
        pollingStartDate.setEnabled(false);
        pollingStartDate.setBackground(new Color(238, 238, 238));
        pollingEndDate.setEnabled(false);
        pollingEndDate.setBackground(new Color(238, 238, 238));
        ballotsCountDay.setEnabled(false);
        ballotsCountDate.setEnabled(false);
        ballotsCountDate.setBackground(new Color(238, 238, 238));
        ballotsCountTime.setEnabled(false);
        ballotsCountTime.setBackground(new Color(238, 238, 238));
        amPMComboBox.setEnabled(false);
        eligibilityListDate.setEnabled(false);
        eligibilityListDate.setBackground(new Color(238, 238, 238));
        preElectionConfDateTextBox.setEnabled(false);
        preElectionConfDateTextBox.setBackground(new Color(238, 238, 238));
        selfReleasingTextBox.setEnabled(false);
        selfReleasingTextBox.setBackground(new Color(238, 238, 238));
        
        addSiteInformation.setVisible(false);
        
        if(runSave) {
            saveInfomration();
        } else {
            loadInformation();
        }
    }
    
    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        
        Global.root.getjButton9().setVisible(true);
        
        multiCaseElectionCheckBox.setEnabled(true);
        addMultiCaseElectionButton.setVisible(true);
        
        electionType1ComboBox.setEnabled(true);
        electionType2ComboBox.setEnabled(true);
        electionType3ComboBox.setEnabled(true);
        
        eligibiltyDateTextBox.setEnabled(true);
        eligibiltyDateTextBox.setBackground(Color.WHITE);
        eligibilityDate.setEnabled(true);
        eligibilityDate.setBackground(Color.WHITE);
        ballotOneTextBox.setEnabled(true);
        ballotOneTextBox.setBackground(Color.WHITE);
        ballotOne.setEnabled(true);
        ballotOne.setBackground(Color.WHITE);
        ballotTwoTextBox.setEnabled(true);
        ballotTwoTextBox.setBackground(Color.WHITE);
        ballotTwo.setEnabled(true);
        ballotTwo.setBackground(Color.WHITE);
        ballotThreeTextBox.setEnabled(true);
        ballotThreeTextBox.setBackground(Color.WHITE);
        ballotThree.setEnabled(true);
        ballotThree.setBackground(Color.WHITE);
        ballotFourTextBox.setEnabled(true);
        ballotFourTextBox.setBackground(Color.WHITE);
        ballotFour.setEnabled(true);
        ballotFour.setBackground(Color.WHITE);
        mailKitDate.setEnabled(true);
        mailKitDate.setBackground(Color.WHITE);
        pollingStartDate.setEnabled(true);
        pollingStartDate.setBackground(Color.WHITE);
        pollingEndDate.setEnabled(true);
        pollingEndDate.setBackground(Color.WHITE);
        ballotsCountDay.setEnabled(true);
        ballotsCountDate.setEnabled(true);
        ballotsCountDate.setBackground(Color.WHITE);
        ballotsCountTime.setEnabled(true);
        ballotsCountTime.setBackground(Color.WHITE);
        amPMComboBox.setEnabled(true);
        eligibilityListDate.setEnabled(true);
        eligibilityListDate.setBackground(Color.WHITE);
        preElectionConfDateTextBox.setEnabled(true);
        preElectionConfDateTextBox.setBackground(Color.WHITE);
        selfReleasingTextBox.setEnabled(true);
        selfReleasingTextBox.setBackground(Color.WHITE);
        
        addSiteInformation.setVisible(true);
    }
    
    private void saveInfomration(){
        REPCase newCaseInformation = new REPCase();
        
        newCaseInformation.multicaseElection = multiCaseElectionCheckBox.isSelected();
        newCaseInformation.electionType1 = electionType1ComboBox.getSelectedItem().toString().trim().equals("") ? null : electionType1ComboBox.getSelectedItem().toString();
        newCaseInformation.electionType2 = electionType2ComboBox.getSelectedItem().toString().trim().equals("") ? null : electionType2ComboBox.getSelectedItem().toString();
        newCaseInformation.electionType3 = electionType3ComboBox.getSelectedItem().toString().trim().equals("") ? null : electionType3ComboBox.getSelectedItem().toString();
        
        if(newCaseInformation.electionType1.equals("On-Site")) {
            //eligibilityDate
            newCaseInformation.eligibilityDate = eligibiltyDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(eligibiltyDateTextBox.getText()));
            //ballotOne
            newCaseInformation.ballotOne = ballotOneTextBox.getText().equals("") ? null : ballotOneTextBox.getText();
            //ballotTwo
            newCaseInformation.ballotTwo = ballotTwoTextBox.getText().equals("") ? null : ballotTwoTextBox.getText();
            //ballotThree
            newCaseInformation.ballotThree = ballotThreeTextBox.getText().equals("") ? null : ballotThreeTextBox.getText();
            //ballotFour
            newCaseInformation.ballotFour = ballotFourTextBox.getText().equals("") ? null : ballotFourTextBox.getText();
            //mailKitDate --> null
            newCaseInformation.mailKitDate = null;
            //pollingStartDate --> null
            newCaseInformation.pollingStartDate = null;
            //pollingEndDate --> null
            newCaseInformation.pollingEndDate = null;
            //ballotsCountDay --> null
            newCaseInformation.ballotsCountDay = null;
            //ballotsCountDate --> null
            newCaseInformation.ballotsCountDate = null;
            //ballotsCountTime --> null
            newCaseInformation.ballotsCountTime = null;
            //eligibilityListDate --> null
            newCaseInformation.eligibilityListDate = null;
            //preElectionConfDate
            newCaseInformation.preElectionConfDate = preElectionConfDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(preElectionConfDateTextBox.getText()));
            //selfReleasing
            newCaseInformation.selfReleasing = selfReleasingTextBox.getText().equals("") ? null : selfReleasingTextBox.getText();
            
//            preElectionConfDateTextBox.setText(repCase.preElectionConfDate == null ? "" : Global.mmddyyyy.format(repCase.preElectionConfDate.getTime()));
//            selfReleasingTextBox.setText(repCase.selfReleasing == null ? "" : repCase.selfReleasing);
        } else if(newCaseInformation.electionType1.equals("Mail") ||
                newCaseInformation.electionType1.equals("Electronic")) {
            //eligibilityDate
            newCaseInformation.eligibilityDate = eligibilityDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(eligibilityDate.getText()));
            //ballotOne
            newCaseInformation.ballotOne = ballotOne.getText().equals("") ? null : ballotOne.getText();
            //ballotTwo
            newCaseInformation.ballotTwo = ballotTwo.getText().equals("") ? null : ballotTwo.getText();
            //ballotThree
            newCaseInformation.ballotThree = ballotThree.getText().equals("") ? null : ballotThree.getText();
            //ballotFour
            newCaseInformation.ballotFour = ballotFour.getText().equals("") ? null : ballotFour.getText();
            //mailKitDate
            newCaseInformation.mailKitDate = mailKitDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(mailKitDate.getText()));
            //pollingStartDate
            newCaseInformation.pollingStartDate = pollingStartDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(pollingStartDate.getText()));
            //pollingEndDate
            newCaseInformation.pollingEndDate = pollingEndDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(pollingEndDate.getText()));
            //ballotsCountDay
            newCaseInformation.ballotsCountDay = ballotsCountDay.getSelectedItem().toString().trim().equals("") ? null : ballotsCountDay.getSelectedItem().toString().trim();
            //ballotsCountDate
            //ballotsCountTime
            //eligibilityListDate
            newCaseInformation.eligibilityListDate = eligibilityListDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(eligibilityListDate.getText()));
            //preElectionConfDate --> null
            newCaseInformation.preElectionConfDate = null;
            //selfReleasing --> null
            newCaseInformation.selfReleasing = null;
        } else {
            //eligibilityDate --> null
            newCaseInformation.eligibilityDate = null;
            //ballotOne --> null
            newCaseInformation.ballotOne = null;
            //ballotTwo --> null
            newCaseInformation.ballotTwo = null;
            //ballotThree --> null
            newCaseInformation.ballotThree = null;
            //ballotFour --> null
            newCaseInformation.ballotFour = null;
            //mailKitDate --> null
            newCaseInformation.mailKitDate = null;
            //pollingStartDate --> null
            newCaseInformation.pollingStartDate = null;
            //pollingEndDate --> null
            newCaseInformation.pollingEndDate = null;
            //ballotsCountDay --> null
            newCaseInformation.ballotsCountDay = null;
            //ballotsCountDate --> null
            newCaseInformation.ballotsCountDate = null;
            //ballotsCountTime --> null
            newCaseInformation.ballotsCountTime = null;
            //eligibilityListDate --> null
            newCaseInformation.eligibilityListDate = null;
            //preElectionConfDate --> null
            newCaseInformation.preElectionConfDate = null;
            //selfReleasing --> null
            newCaseInformation.selfReleasing = null;
        }        
        REPCase.updateElectionInformation(newCaseInformation, repCase);
    }
    
    public void clearAll() {
        hideNotRequiredInformation();
        multiCaseElectionCheckBox.setSelected(false);
        electionType1ComboBox.setSelectedItem("");
        electionType2ComboBox.setSelectedItem("");
        electionType3ComboBox.setSelectedItem("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        multiCaseElectionCheckBox = new javax.swing.JCheckBox();
        jLabel1 = new javax.swing.JLabel();
        electionType1ComboBox = new javax.swing.JComboBox<>();
        electionType2ComboBox = new javax.swing.JComboBox<>();
        electionType3ComboBox = new javax.swing.JComboBox<>();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        multiCaseElectionTable = new javax.swing.JTable();
        addMultiCaseElectionButton = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        addSiteInformation = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTable2 = new javax.swing.JTable();
        jPanel3 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jPanel14 = new javax.swing.JPanel();
        jPanel15 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        ballotOneTextBox = new javax.swing.JTextField();
        ballotTwoTextBox = new javax.swing.JTextField();
        ballotThreeTextBox = new javax.swing.JTextField();
        ballotFourTextBox = new javax.swing.JTextField();
        jPanel18 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        selfReleasingTextBox = new javax.swing.JTextField();
        eligibiltyDateTextBox = new com.alee.extended.date.WebDateField();
        preElectionConfDateTextBox = new com.alee.extended.date.WebDateField();
        jPanel16 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        ballotOne = new javax.swing.JTextField();
        ballotTwo = new javax.swing.JTextField();
        ballotThree = new javax.swing.JTextField();
        ballotFour = new javax.swing.JTextField();
        eligibilityDate = new com.alee.extended.date.WebDateField();
        mailKitDate = new com.alee.extended.date.WebDateField();
        jPanel8 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jLabel24 = new javax.swing.JLabel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        ballotsCountDay = new javax.swing.JComboBox<>();
        ballotsCountTime = new javax.swing.JTextField();
        amPMComboBox = new javax.swing.JComboBox<>();
        pollingStartDate = new com.alee.extended.date.WebDateField();
        pollingEndDate = new com.alee.extended.date.WebDateField();
        ballotsCountDate = new com.alee.extended.date.WebDateField();
        eligibilityListDate = new com.alee.extended.date.WebDateField();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel21 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jLabel22 = new javax.swing.JLabel();
        jTextField3 = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jTextField4 = new javax.swing.JTextField();
        jTextField5 = new javax.swing.JTextField();
        jTextField6 = new javax.swing.JTextField();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jLabel33 = new javax.swing.JLabel();
        jTextField7 = new javax.swing.JTextField();
        jTextField8 = new javax.swing.JTextField();
        jTextField9 = new javax.swing.JTextField();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        jTextField10 = new javax.swing.JTextField();
        jTextField11 = new javax.swing.JTextField();
        jTextField12 = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();

        setMaximumSize(new java.awt.Dimension(32767, 570));
        setPreferredSize(new java.awt.Dimension(1032, 570));

        multiCaseElectionCheckBox.setText("Multi-Case Election");
        multiCaseElectionCheckBox.setEnabled(false);
        multiCaseElectionCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                multiCaseElectionCheckBoxStateChanged(evt);
            }
        });

        jLabel1.setText("Election Type:");

        electionType1ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "On-Site", "Mail", "Electronic", " " }));
        electionType1ComboBox.setSelectedIndex(3);
        electionType1ComboBox.setEnabled(false);
        electionType1ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                electionType1ComboBoxActionPerformed(evt);
            }
        });

        electionType2ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "RD", "RC-1", "RC-2", "Opt-In RC", " " }));
        electionType2ComboBox.setSelectedIndex(4);
        electionType2ComboBox.setEnabled(false);

        electionType3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Professional/Non-Professional", "" }));
        electionType3ComboBox.setSelectedIndex(1);
        electionType3ComboBox.setEnabled(false);
        electionType3ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                electionType3ComboBoxActionPerformed(evt);
            }
        });

        jPanel1.setMaximumSize(new java.awt.Dimension(489, 47));
        jPanel1.setMinimumSize(new java.awt.Dimension(489, 47));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(454, 49));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(454, 49));

        multiCaseElectionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Case Number"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(multiCaseElectionTable);
        if (multiCaseElectionTable.getColumnModel().getColumnCount() > 0) {
            multiCaseElectionTable.getColumnModel().getColumn(0).setResizable(false);
        }

        addMultiCaseElectionButton.setText("+");
        addMultiCaseElectionButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addMultiCaseElectionButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addMultiCaseElectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(addMultiCaseElectionButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel2.setSize(new java.awt.Dimension(489, 85));

        jLabel9.setText("Site Information:");

        addSiteInformation.setText("+");

        jTable2.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date / Time", "Place", "Address", "Location"
            }
        ));
        jTable2.setMinimumSize(new java.awt.Dimension(60, 29));
        jTable2.setPreferredSize(new java.awt.Dimension(300, 30));
        jScrollPane2.setViewportView(jTable2);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jScrollPane2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(addSiteInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 0, 0))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addSiteInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 57, Short.MAX_VALUE))
                .addGap(0, 0, 0))
        );

        jLabel2.setText("Basic Information");

        jPanel14.setLayout(new java.awt.CardLayout());

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Ballot One:");

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Ballot Two:");

        jLabel5.setText("Ballot Three:");

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Ballot Four:");

        ballotOneTextBox.setBackground(new java.awt.Color(238, 238, 238));
        ballotOneTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotOneTextBox.setEnabled(false);

        ballotTwoTextBox.setBackground(new java.awt.Color(238, 238, 238));
        ballotTwoTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotTwoTextBox.setEnabled(false);

        ballotThreeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        ballotThreeTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotThreeTextBox.setEnabled(false);

        ballotFourTextBox.setBackground(new java.awt.Color(238, 238, 238));
        ballotFourTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotFourTextBox.setEnabled(false);

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ballotOneTextBox)
                    .addComponent(ballotTwoTextBox)
                    .addComponent(ballotThreeTextBox)
                    .addComponent(ballotFourTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 408, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(ballotOneTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(ballotTwoTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(ballotThreeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(ballotFourTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Eligibility Date:");

        jLabel8.setText("Pre-Election Conf. Date:");

        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("Self-Releasing:");

        selfReleasingTextBox.setBackground(new java.awt.Color(238, 238, 238));
        selfReleasingTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        selfReleasingTextBox.setEnabled(false);

        eligibiltyDateTextBox.setEditable(false);
        eligibiltyDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        eligibiltyDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        eligibiltyDateTextBox.setEnabled(false);
        eligibiltyDateTextBox.setDateFormat(Global.mmddyyyy);
        eligibiltyDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eligibiltyDateTextBoxMouseClicked(evt);
            }
        });

        preElectionConfDateTextBox.setEditable(false);
        preElectionConfDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        preElectionConfDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        preElectionConfDateTextBox.setEnabled(false);
        preElectionConfDateTextBox.setDateFormat(Global.mmddyyyy);
        preElectionConfDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                preElectionConfDateTextBoxMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(selfReleasingTextBox)
                    .addComponent(preElectionConfDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 340, Short.MAX_VALUE)
                    .addComponent(eligibiltyDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(eligibiltyDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(preElectionConfDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(selfReleasingTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel15, "card2");

        jLabel17.setText("Ballot Three:");

        jLabel16.setText("Ballot Two:");

        jLabel14.setText("Eligibility Date:");

        jLabel18.setText("Ballot Four:");

        jLabel19.setText("Mail Kit Date:");

        jLabel15.setText("Ballot One:");

        ballotOne.setBackground(new java.awt.Color(238, 238, 238));
        ballotOne.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotOne.setEnabled(false);

        ballotTwo.setBackground(new java.awt.Color(238, 238, 238));
        ballotTwo.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotTwo.setEnabled(false);

        ballotThree.setBackground(new java.awt.Color(238, 238, 238));
        ballotThree.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotThree.setEnabled(false);

        ballotFour.setBackground(new java.awt.Color(238, 238, 238));
        ballotFour.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotFour.setEnabled(false);

        eligibilityDate.setEditable(false);
        eligibilityDate.setBackground(new java.awt.Color(238, 238, 238));
        eligibilityDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        eligibilityDate.setEnabled(false);
        eligibilityDate.setDateFormat(Global.mmddyyyy);
        eligibilityDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eligibilityDateMouseClicked(evt);
            }
        });

        mailKitDate.setEditable(false);
        mailKitDate.setBackground(new java.awt.Color(238, 238, 238));
        mailKitDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mailKitDate.setEnabled(false);
        mailKitDate.setDateFormat(Global.mmddyyyy);
        mailKitDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                mailKitDateMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel15)
                    .addComponent(jLabel16)
                    .addComponent(jLabel17)
                    .addComponent(jLabel18)
                    .addComponent(jLabel19))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(mailKitDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(ballotFour)
                    .addComponent(ballotThree)
                    .addComponent(ballotTwo)
                    .addComponent(ballotOne)
                    .addComponent(eligibilityDate, javax.swing.GroupLayout.DEFAULT_SIZE, 406, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(eligibilityDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel15)
                    .addComponent(ballotOne, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel16)
                    .addComponent(ballotTwo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel17)
                    .addComponent(ballotThree, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel18)
                    .addComponent(ballotFour, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(mailKitDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel20.setText("Polling Start Date:");

        jLabel23.setText("Polling End Date:");

        jLabel24.setText("Ballots Count Day:");

        jLabel25.setText("Ballots Count Date:");

        jLabel26.setText("Ballots Count TIme:");

        jLabel27.setText("Eligiblity List Date:");

        ballotsCountDay.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", " " }));
        ballotsCountDay.setSelectedIndex(7);
        ballotsCountDay.setEnabled(false);

        ballotsCountTime.setBackground(new java.awt.Color(238, 238, 238));
        ballotsCountTime.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotsCountTime.setEnabled(false);
        ballotsCountTime.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ballotsCountTimeActionPerformed(evt);
            }
        });

        amPMComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AM", "PM", " " }));
        amPMComboBox.setSelectedIndex(2);
        amPMComboBox.setEnabled(false);

        pollingStartDate.setEditable(false);
        pollingStartDate.setBackground(new java.awt.Color(238, 238, 238));
        pollingStartDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pollingStartDate.setEnabled(false);
        pollingStartDate.setDateFormat(Global.mmddyyyy);
        pollingStartDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pollingStartDateMouseClicked(evt);
            }
        });

        pollingEndDate.setEditable(false);
        pollingEndDate.setBackground(new java.awt.Color(238, 238, 238));
        pollingEndDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        pollingEndDate.setEnabled(false);
        pollingEndDate.setDateFormat(Global.mmddyyyy);
        pollingEndDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                pollingEndDateMouseClicked(evt);
            }
        });
        pollingEndDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pollingEndDateActionPerformed(evt);
            }
        });

        ballotsCountDate.setEditable(false);
        ballotsCountDate.setBackground(new java.awt.Color(238, 238, 238));
        ballotsCountDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ballotsCountDate.setEnabled(false);
        ballotsCountDate.setDateFormat(Global.mmddyyyy);
        ballotsCountDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                ballotsCountDateMouseClicked(evt);
            }
        });

        eligibilityListDate.setEditable(false);
        eligibilityListDate.setBackground(new java.awt.Color(238, 238, 238));
        eligibilityListDate.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        eligibilityListDate.setEnabled(false);
        eligibilityListDate.setDateFormat(Global.mmddyyyy);
        eligibilityListDate.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                eligibilityListDateMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel25)
                            .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ballotsCountDay, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pollingEndDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pollingStartDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ballotsCountDate, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(ballotsCountTime)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(amPMComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(eligibilityListDate, javax.swing.GroupLayout.PREFERRED_SIZE, 353, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel20)
                    .addComponent(pollingStartDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(pollingEndDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel24)
                    .addComponent(ballotsCountDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel25)
                    .addComponent(ballotsCountDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel26)
                    .addComponent(ballotsCountTime, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(amPMComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel27)
                    .addComponent(eligibilityListDate, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(0, 0, 0))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(jPanel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                    .addComponent(jPanel7, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel14.add(jPanel16, "card3");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(jLabel2)
                .addGap(0, 0, 0)
                .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel4.setLayout(new java.awt.CardLayout());

        jLabel10.setText("Results:");

        jLabel13.setText("jLabel13");

        jTextField1.setText("jTextField1");

        jLabel21.setText("jLabel21");

        jTextField2.setText("jTextField2");

        jLabel22.setText("jLabel22");

        jTextField3.setText("jTextField3");

        jLabel28.setText("jLabel28");

        jLabel29.setText("jLabel29");

        jLabel30.setText("jLabel30");

        jTextField4.setText("jTextField4");

        jTextField5.setText("jTextField5");

        jTextField6.setText("jTextField6");

        jLabel31.setText("jLabel31");

        jLabel32.setText("jLabel32");

        jLabel33.setText("jLabel33");

        jTextField7.setText("jTextField7");

        jTextField8.setText("jTextField8");

        jTextField9.setText("jTextField9");

        jLabel34.setText("jLabel34");

        jLabel35.setText("jLabel35");

        jLabel36.setText("jLabel36");

        jTextField10.setText("jTextField10");

        jTextField11.setText("jTextField11");

        jTextField12.setText("jTextField12");

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel31)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel34)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel21)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel29)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel35)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel30)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel33)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField9))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(jLabel28)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel32)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel36)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(565, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(jTextField7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(jTextField10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(jTextField5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(jTextField11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(jTextField3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(jTextField6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(jTextField9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(3, 3, 3)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(jTextField4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(jTextField8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(jTextField12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5, "card2");

        jLabel11.setText("Professional/Non-Professional");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 825, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 119, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel6, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(multiCaseElectionCheckBox)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(electionType1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(electionType2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(electionType3ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(multiCaseElectionCheckBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(electionType1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(electionType2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(electionType3ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void electionType1ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_electionType1ComboBoxActionPerformed
//        if(electionType1ComboBox.getSelectedItem() != null) {
            jPanel2.setVisible(electionType1ComboBox.getSelectedItem().equals("On-Site")); 
        
            if(electionType1ComboBox.getSelectedItem().equals("")) {
                jPanel3.setVisible(false);
                jPanel4.setVisible(false);
            } else {
                jPanel3.setVisible(true);
                jPanel4.setVisible(true);
            }

            switch(electionType1ComboBox.getSelectedItem().toString()) {
                case "On-Site":
                    jPanel14.remove(jPanel16);
                    jPanel14.add(jPanel15);
                    siteCard.show(jPanel14, "card2");
//                    Global.root.pack();
                    break;
                case "Mail":
                case "Electronic":
                    jPanel14.add(jPanel16);
                    jPanel14.remove(jPanel15);
                    siteCard.show(jPanel14, "card3");
                    break;
                default:
                    jPanel3.setVisible(false);
                    jPanel4.setVisible(false);
                    break;
            }
//        }
    }//GEN-LAST:event_electionType1ComboBoxActionPerformed

    private void electionType3ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_electionType3ComboBoxActionPerformed
        
        if(electionType3ComboBox.getSelectedItem().toString().equals("")) {
            if(electionType1ComboBox.getSelectedItem().toString().equals("")) {
                jPanel4.setVisible(false);
            } else {
                jPanel4.setVisible(true);
                resultsCard.show(jPanel4, "card2");
            }
        } else {
            jPanel4.setVisible(true);
            resultsCard.show(jPanel4, "card3");
        }
    }//GEN-LAST:event_electionType3ComboBoxActionPerformed

    private void eligibiltyDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eligibiltyDateTextBoxMouseClicked
//        clearDate(registrationLetterSentTextBox, evt);
    }//GEN-LAST:event_eligibiltyDateTextBoxMouseClicked

    private void preElectionConfDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_preElectionConfDateTextBoxMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_preElectionConfDateTextBoxMouseClicked

    private void eligibilityDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eligibilityDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_eligibilityDateMouseClicked

    private void mailKitDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mailKitDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_mailKitDateMouseClicked

    private void pollingStartDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pollingStartDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pollingStartDateMouseClicked

    private void pollingEndDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pollingEndDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_pollingEndDateMouseClicked

    private void ballotsCountDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ballotsCountDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_ballotsCountDateMouseClicked

    private void eligibilityListDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eligibilityListDateMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_eligibilityListDateMouseClicked

    private void multiCaseElectionCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_multiCaseElectionCheckBoxStateChanged
        jPanel1.setVisible(multiCaseElectionCheckBox.isSelected());
    }//GEN-LAST:event_multiCaseElectionCheckBoxStateChanged

    private void addMultiCaseElectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMultiCaseElectionButtonActionPerformed
        
    }//GEN-LAST:event_addMultiCaseElectionButtonActionPerformed

    private void pollingEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pollingEndDateActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_pollingEndDateActionPerformed

    private void ballotsCountTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ballotsCountTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ballotsCountTimeActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMultiCaseElectionButton;
    private javax.swing.JButton addSiteInformation;
    private javax.swing.JComboBox<String> amPMComboBox;
    private javax.swing.JTextField ballotFour;
    private javax.swing.JTextField ballotFourTextBox;
    private javax.swing.JTextField ballotOne;
    private javax.swing.JTextField ballotOneTextBox;
    private javax.swing.JTextField ballotThree;
    private javax.swing.JTextField ballotThreeTextBox;
    private javax.swing.JTextField ballotTwo;
    private javax.swing.JTextField ballotTwoTextBox;
    private com.alee.extended.date.WebDateField ballotsCountDate;
    private javax.swing.JComboBox<String> ballotsCountDay;
    private javax.swing.JTextField ballotsCountTime;
    private javax.swing.JComboBox<String> electionType1ComboBox;
    private javax.swing.JComboBox<String> electionType2ComboBox;
    private javax.swing.JComboBox<String> electionType3ComboBox;
    private com.alee.extended.date.WebDateField eligibilityDate;
    private com.alee.extended.date.WebDateField eligibilityListDate;
    private com.alee.extended.date.WebDateField eligibiltyDateTextBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable jTable2;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JTextField jTextField10;
    private javax.swing.JTextField jTextField11;
    private javax.swing.JTextField jTextField12;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextField3;
    private javax.swing.JTextField jTextField4;
    private javax.swing.JTextField jTextField5;
    private javax.swing.JTextField jTextField6;
    private javax.swing.JTextField jTextField7;
    private javax.swing.JTextField jTextField8;
    private javax.swing.JTextField jTextField9;
    private com.alee.extended.date.WebDateField mailKitDate;
    private javax.swing.JCheckBox multiCaseElectionCheckBox;
    private javax.swing.JTable multiCaseElectionTable;
    private com.alee.extended.date.WebDateField pollingEndDate;
    private com.alee.extended.date.WebDateField pollingStartDate;
    private com.alee.extended.date.WebDateField preElectionConfDateTextBox;
    private javax.swing.JTextField selfReleasingTextBox;
    // End of variables declaration//GEN-END:variables
}
