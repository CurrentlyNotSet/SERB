/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import com.alee.extended.date.WebDateField;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.CaseParty;
import parker.serb.sql.REPCase;
import parker.serb.sql.REPElectionMultiCase;
import parker.serb.sql.REPElectionSiteInformation;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class REPElectionPanel extends javax.swing.JPanel {

    CardLayout resultsCard, siteCard;
    REPCase repCase;
    String whereUserIsComingFrom = "Professional";
    String[] professional = new String[]{"","","","","","","","","","","","","","",""};
    String[] nonprofessional = new String[]{"","","","","","","","","","","","","","",""};
    String[] combined = new String[]{"","","","","","","","","","","","","","",""};
    
    /**
     * Creates new form REPElectionPanel
     */
    public REPElectionPanel() {
        
        initComponents();
        addListeners();
        setOnSiteTableColumnWidth();
        professionalButton.setSelected(true);
        resultsCard = (CardLayout)jPanel4.getLayout();
        siteCard = (CardLayout) jPanel14.getLayout();
        jPanel4.setVisible(false);
//        hideNotRequiredInformation();
    }
    
    private void setOnSiteTableColumnWidth() {
        sitesTable.getColumnModel().getColumn(4).setPreferredWidth(0);
        sitesTable.getColumnModel().getColumn(4).setMinWidth(0);
        sitesTable.getColumnModel().getColumn(4).setMaxWidth(0);
    }
    
    private void addListeners() {
        ballotsCountTime.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                if(ballotsCountTime.getText().equals("")) {
                    amPMComboBox.setSelectedItem(" ");
                } else {
                    amPMComboBox.setSelectedItem(Integer.parseInt(ballotsCountTime.getText().split(":")[0]) >= 7 && Integer.parseInt(ballotsCountTime.getText().split(":")[0]) <= 11 ? "AM" : "PM");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                if(ballotsCountTime.getText().equals("")) {
                    amPMComboBox.setSelectedItem(" ");
                } else {
                    amPMComboBox.setSelectedItem(Integer.parseInt(ballotsCountTime.getText().split(":")[0]) >= 7 && Integer.parseInt(ballotsCountTime.getText().split(":")[0]) <= 11 ? "AM" : "PM");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                if(ballotsCountTime.getText().equals("")) {
                    amPMComboBox.setSelectedItem(" ");
                } else {
                    amPMComboBox.setSelectedItem(Integer.parseInt(ballotsCountTime.getText().split(":")[0]) >= 7 && Integer.parseInt(ballotsCountTime.getText().split(":")[0]) <= 11 ? "AM" : "PM");
                }
            }
        });
        
        pollingStartDate.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(pollingStartDate.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 14);
                    pollingEndDate.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    pollingEndDate.setText("");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(pollingStartDate.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 14);
                    pollingEndDate.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    pollingEndDate.setText("");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(pollingStartDate.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.DATE, 14);
                    pollingEndDate.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    pollingEndDate.setText("");
                }
            }
        });
    }
    
    private void hideNotRequiredInformation() {
        jPanel1.setVisible(false);
        jPanel2.setVisible(false);
        jPanel3.setVisible(false);
        jPanel4.setVisible(false);
        addMultiCaseElectionButton.setVisible(false);
        addSiteInformation.setVisible(false);
    }
    
    private void hanldeProfessionalNonProfessionalElection(String headedTo) {
        switch(whereUserIsComingFrom) {
            case "Professional":
                professional[0] = approxNumberEligibleVotersTextBox.getText().trim();
                professional[1] = yesTextBox.getText().trim();
                professional[2] = noTextBox.getText().trim();
                professional[3] = challengedTextBox.getText().trim();
                professional[4] = totalBVotesTextBox.getText().trim();
                professional[5] = outcomeComboBox.getSelectedItem().toString();
                professional[6] = whoPrevailedComboBox.getSelectedItem().toString().trim();
                professional[7] = voidBallotsTextBox.getText().trim();
                professional[8] = validVotesTextBox.getText().trim();
                professional[9] = votesCastForNoRepresentativeTextBox.getText().trim();
                professional[10] = votesCastforEEOTextBox.getText().trim();
                professional[11] = votesCastForIncumbentEEOTextBox.getText().trim();
                professional[12] = votesCastForRivalEEO1TextBox.getText().trim();
                professional[13] = votesCastForRivalEEO2TextBox.getText().trim();
                professional[14] = votesCastForRivalEEO3TextBox.getText().trim();
                break;
            case "Non-Professional":
                nonprofessional[0] = approxNumberEligibleVotersTextBox.getText().trim();
                nonprofessional[1] = yesTextBox.getText().trim();
                nonprofessional[2] = noTextBox.getText().trim();
                nonprofessional[3] = challengedTextBox.getText().trim();
                nonprofessional[4] = totalBVotesTextBox.getText().trim();
                nonprofessional[5] = outcomeComboBox.getSelectedItem().toString();
                nonprofessional[6] = whoPrevailedComboBox.getSelectedItem().toString().trim();
                nonprofessional[7] = voidBallotsTextBox.getText().trim();
                nonprofessional[8] = validVotesTextBox.getText().trim();
                nonprofessional[9] = votesCastForNoRepresentativeTextBox.getText().trim();
                nonprofessional[10] = votesCastforEEOTextBox.getText().trim();
                nonprofessional[11] = votesCastForIncumbentEEOTextBox.getText().trim();
                nonprofessional[12] = votesCastForRivalEEO1TextBox.getText().trim();
                nonprofessional[13] = votesCastForRivalEEO2TextBox.getText().trim();
                nonprofessional[14] = votesCastForRivalEEO3TextBox.getText().trim();
                break;
            case "Combined":
                combined[0] = approxNumberEligibleVotersTextBox.getText().trim();
                combined[1] = yesTextBox.getText().trim();
                combined[2] = noTextBox.getText().trim();
                combined[3] = challengedTextBox.getText().trim();
                combined[4] = totalBVotesTextBox.getText().trim();
                combined[5] = outcomeComboBox.getSelectedItem().toString();
                combined[6] = whoPrevailedComboBox.getSelectedItem().toString().trim();
                combined[7] = voidBallotsTextBox.getText().trim();
                combined[8] = validVotesTextBox.getText().trim();
                combined[9] = votesCastForNoRepresentativeTextBox.getText().trim();
                combined[10] = votesCastforEEOTextBox.getText().trim();
                combined[11] = votesCastForIncumbentEEOTextBox.getText().trim();
                combined[12] = votesCastForRivalEEO1TextBox.getText().trim();
                combined[13] = votesCastForRivalEEO2TextBox.getText().trim();
                combined[14] = votesCastForRivalEEO3TextBox.getText().trim();
                break;
        }
        
        switch(headedTo) {
            case "Professional":
                approxNumberEligibleVotersTextBox.setText(professional[0]);
                yesTextBox.setText(professional[1]);
                noTextBox.setText(professional[2]);
                challengedTextBox.setText(professional[3]);
                totalBVotesTextBox.setText(professional[4]);
                outcomeComboBox.setSelectedItem(professional[5].trim().equals("") ? " " : professional[5].trim());
                whoPrevailedComboBox.setSelectedItem(professional[6]);
                voidBallotsTextBox.setText(professional[7]);
                validVotesTextBox.setText(professional[8]);
                votesCastForNoRepresentativeTextBox.setText(professional[9]);
                votesCastforEEOTextBox.setText(professional[10]);
                votesCastForIncumbentEEOTextBox.setText(professional[11]);
                votesCastForRivalEEO1TextBox.setText(professional[12]);
                votesCastForRivalEEO2TextBox.setText(professional[13]);
                votesCastForRivalEEO3TextBox.setText(professional[14]);
                break;
            case "Non-Professional":
                approxNumberEligibleVotersTextBox.setText(nonprofessional[0]);
                yesTextBox.setText(nonprofessional[1]);
                noTextBox.setText(nonprofessional[2]);
                challengedTextBox.setText(nonprofessional[3]);
                totalBVotesTextBox.setText(nonprofessional[4]);
                outcomeComboBox.setSelectedItem(nonprofessional[5].trim().equals("") ? " " : nonprofessional[5].trim());
                whoPrevailedComboBox.setSelectedItem(nonprofessional[6]);
                voidBallotsTextBox.setText(nonprofessional[7]);
                validVotesTextBox.setText(nonprofessional[8]);
                votesCastForNoRepresentativeTextBox.setText(nonprofessional[9]);
                votesCastforEEOTextBox.setText(nonprofessional[10]);
                votesCastForIncumbentEEOTextBox.setText(nonprofessional[11]);
                votesCastForRivalEEO1TextBox.setText(nonprofessional[12]);
                votesCastForRivalEEO2TextBox.setText(nonprofessional[13]);
                votesCastForRivalEEO3TextBox.setText(nonprofessional[14]);
                break;
            case "Combined":
                approxNumberEligibleVotersTextBox.setText(combined[0]);
                yesTextBox.setText(combined[1]);
                noTextBox.setText(combined[2]);
                challengedTextBox.setText(combined[3]);
                totalBVotesTextBox.setText(combined[4]);
                outcomeComboBox.setSelectedItem(combined[5].trim().equals("") ? " " : combined[5].trim());
                whoPrevailedComboBox.setSelectedItem(combined[6]);
                voidBallotsTextBox.setText(combined[7]);
                validVotesTextBox.setText(combined[8]);
                votesCastForNoRepresentativeTextBox.setText(combined[9]);
                votesCastforEEOTextBox.setText(combined[10]);
                votesCastForIncumbentEEOTextBox.setText(combined[11]);
                votesCastForRivalEEO1TextBox.setText(combined[12]);
                votesCastForRivalEEO2TextBox.setText(combined[13]);
                votesCastForRivalEEO3TextBox.setText(combined[14]);
                break;
        }
        
        whereUserIsComingFrom = headedTo;
    }
    
    private void loadElectionArrays(REPCase repCase) {
        professional[0] = repCase.professionalApproxNumberEligible == null ? "" : repCase.professionalApproxNumberEligible;
        professional[1] = repCase.professionalYES == null ? "" : repCase.professionalYES;
        professional[2] = repCase.professionalNO == null ? "" : repCase.professionalNO;
        professional[3] = repCase.professionalChallenged == null ? "" : repCase.professionalChallenged;
        professional[4] = repCase.professionalTotalVotes == null ? "" : repCase.professionalTotalVotes;
        professional[5] = repCase.professionalOutcome == null ? "" : repCase.professionalOutcome;
        professional[6] = repCase.professionalWhoPrevailed == null ? "" : repCase.professionalWhoPrevailed;
        professional[7] = repCase.professionalVoidBallots == null ? "" : repCase.professionalVoidBallots;
        professional[8] = repCase.professionalValidVotes == null ? "" : repCase.professionalValidVotes;
        professional[9] = repCase.professionalVotesCastForNoRepresentative == null ? "" : repCase.professionalVotesCastForNoRepresentative;
        professional[10] = repCase.professionalVotesCastForEEO == null ? "" : repCase.professionalVotesCastForEEO;
        professional[11] = repCase.professionalVotesCastForIncumbentEEO == null ? "" : repCase.professionalVotesCastForIncumbentEEO;
        professional[12] = repCase.professionalVotesCastForRivalEEO1 == null ? "" : repCase.professionalVotesCastForRivalEEO1;
        professional[13] = repCase.professionalVotesCastForRivalEEO2 == null ? "" : repCase.professionalVotesCastForRivalEEO2;
        professional[14] = repCase.professionalVotesCastForRivalEEO3 == null ? "" : repCase.professionalVotesCastForRivalEEO3;
        
        nonprofessional[0] = repCase.nonprofessionalApproxNumberEligible == null ? "" : repCase.nonprofessionalApproxNumberEligible;
        nonprofessional[1] = repCase.nonprofessionalYES == null ? "" : repCase.nonprofessionalYES;
        nonprofessional[2] = repCase.nonprofessionalNO == null ? "" : repCase.nonprofessionalNO;
        nonprofessional[3] = repCase.nonprofessionalChallenged == null ? "" : repCase.nonprofessionalChallenged;
        nonprofessional[4] = repCase.nonprofessionalTotalVotes == null ? "" : repCase.nonprofessionalTotalVotes;
        nonprofessional[5] = repCase.nonprofessionalOutcome == null ? "" : repCase.nonprofessionalOutcome;
        nonprofessional[6] = repCase.nonprofessionalWhoPrevailed == null ? "" : repCase.nonprofessionalWhoPrevailed;
        nonprofessional[7] = repCase.nonprofessionalVoidBallots == null ? "" : repCase.nonprofessionalVoidBallots;
        nonprofessional[8] = repCase.nonprofessionalValidVotes == null ? "" : repCase.nonprofessionalValidVotes;
        nonprofessional[9] = repCase.nonprofessionalVotesCastForNoRepresentative == null ? "" : repCase.nonprofessionalVotesCastForNoRepresentative;
        nonprofessional[10] = repCase.nonprofessionalVotesCastForEEO == null ? "" : repCase.nonprofessionalVotesCastForEEO;
        nonprofessional[11] = repCase.nonprofessionalVotesCastForIncumbentEEO == null ? "" : repCase.nonprofessionalVotesCastForIncumbentEEO;
        nonprofessional[12] = repCase.nonprofessionalVotesCastForRivalEEO1 == null ? "" : repCase.nonprofessionalVotesCastForRivalEEO1;
        nonprofessional[13] = repCase.nonprofessionalVotesCastForRivalEEO2 == null ? "" : repCase.nonprofessionalVotesCastForRivalEEO2;
        nonprofessional[14] = repCase.nonprofessionalVotesCastForRivalEEO3 == null ? "" : repCase.nonprofessionalVotesCastForRivalEEO3;
        
        combined[0] = repCase.combinedApproxNumberEligible == null ? "" : repCase.combinedApproxNumberEligible;
        combined[1] = repCase.combinedYES == null ? "" : repCase.combinedYES;
        combined[2] = repCase.combinedlNO == null ? "" : repCase.combinedlNO;
        combined[3] = repCase.combinedChallenged == null ? "" : repCase.combinedChallenged;
        combined[4] = repCase.combinedTotalVotes == null ? "" : repCase.combinedTotalVotes;
        combined[5] = repCase.combinedOutcome == null ? "" : repCase.combinedOutcome;
        combined[6] = repCase.combinedWhoPrevailed == null ? "" : repCase.combinedWhoPrevailed;
        combined[7] = repCase.combinedVoidBallots == null ? "" : repCase.combinedVoidBallots;
        combined[8] = repCase.combinedValidVotes == null ? "" : repCase.combinedValidVotes;
        combined[9] = repCase.combinedVotesCastForNoRepresentative == null ? "" : repCase.combinedVotesCastForNoRepresentative;
        combined[10] = repCase.combinedVotesCastForEEO == null ? "" : repCase.combinedVotesCastForEEO;
        combined[11] = repCase.combinedVotesCastForIncumbentEEO == null ? "" : repCase.combinedVotesCastForIncumbentEEO;
        combined[12] = repCase.combinedVotesCastForRivalEEO1 == null ? "" : repCase.combinedVotesCastForRivalEEO1;
        combined[13] = repCase.combinedVotesCastForRivalEEO2 == null ? "" : repCase.combinedVotesCastForRivalEEO2;
        combined[14] = repCase.combinedVotesCastForRivalEEO3 == null ? "" : repCase.combinedVotesCastForRivalEEO3;
        
        if(professionalButton.isSelected()) {
            approxNumberEligibleVotersTextBox.setText(professional[0]);
            yesTextBox.setText(professional[1]);
            noTextBox.setText(professional[2]);
            challengedTextBox.setText(professional[3]);
            totalBVotesTextBox.setText(professional[4]);
            outcomeComboBox.setSelectedItem(professional[5]);
            whoPrevailedComboBox.setSelectedItem(professional[6]);
            voidBallotsTextBox.setText(professional[7]);
            validVotesTextBox.setText(professional[8]);
            votesCastForNoRepresentativeTextBox.setText(professional[9]);
            votesCastforEEOTextBox.setText(professional[10]);
            votesCastForIncumbentEEOTextBox.setText(professional[11]);
            votesCastForRivalEEO1TextBox.setText(professional[12]);
            votesCastForRivalEEO2TextBox.setText(professional[13]);
            votesCastForRivalEEO3TextBox.setText(professional[14]);
        } else if(nonProfessionalButton.isSelected()) {
            approxNumberEligibleVotersTextBox.setText(nonprofessional[0]);
            yesTextBox.setText(nonprofessional[1]);
            noTextBox.setText(nonprofessional[2]);
            challengedTextBox.setText(nonprofessional[3]);
            totalBVotesTextBox.setText(nonprofessional[4]);
            outcomeComboBox.setSelectedItem(nonprofessional[5]);
            whoPrevailedComboBox.setSelectedItem(nonprofessional[6]);
            voidBallotsTextBox.setText(nonprofessional[7]);
            validVotesTextBox.setText(nonprofessional[8]);
            votesCastForNoRepresentativeTextBox.setText(nonprofessional[9]);
            votesCastforEEOTextBox.setText(nonprofessional[10]);
            votesCastForIncumbentEEOTextBox.setText(nonprofessional[11]);
            votesCastForRivalEEO1TextBox.setText(nonprofessional[12]);
            votesCastForRivalEEO2TextBox.setText(nonprofessional[13]);
            votesCastForRivalEEO3TextBox.setText(nonprofessional[14]);
        } else if(combinedButton.isSelected()) {
            approxNumberEligibleVotersTextBox.setText(combined[0]);
            yesTextBox.setText(combined[1]);
            noTextBox.setText(combined[2]);
            challengedTextBox.setText(combined[3]);
            totalBVotesTextBox.setText(combined[4]);
            outcomeComboBox.setSelectedItem(combined[5]);
            whoPrevailedComboBox.setSelectedItem(combined[6]);
            voidBallotsTextBox.setText(combined[7]);
            validVotesTextBox.setText(combined[8]);
            votesCastForNoRepresentativeTextBox.setText(combined[9]);
            votesCastforEEOTextBox.setText(combined[10]);
            votesCastForIncumbentEEOTextBox.setText(combined[11]);
            votesCastForRivalEEO1TextBox.setText(combined[12]);
            votesCastForRivalEEO2TextBox.setText(combined[13]);
            votesCastForRivalEEO3TextBox.setText(combined[14]);
        }
    }
    
    private void loadWhoPrevailed() {
        List comps = CaseParty.loadPartiesByCase();
        
        System.out.println(comps.size());
        
        whoPrevailedComboBox.removeAllItems();
        resultsWhoPrevailed.removeAllItems();
        
        whoPrevailedComboBox.addItem("");
        resultsWhoPrevailed.addItem("");
        
        whoPrevailedComboBox.addItem("No Representative");
        resultsWhoPrevailed.addItem("No Representative");
        
        for(int i = 0; i < comps.size(); i++) {
            CaseParty caseParty = (CaseParty) comps.get(i);
            if(caseParty.companyName.equals("")) {
                whoPrevailedComboBox.addItem(caseParty.firstName + " " + caseParty.lastName);
                resultsWhoPrevailed.addItem(caseParty.firstName + " " + caseParty.lastName);
            } else {
                whoPrevailedComboBox.addItem(caseParty.companyName);
                resultsWhoPrevailed.addItem(caseParty.companyName);
            }
        }
    }
    
    //if missing a descriptor it is on the electronic/mail panel
    public void  loadInformation() {
        clearAll();
        loadWhoPrevailed();
        
        repCase = REPCase.loadElectionInformation();
        
        multiCaseElectionCheckBox.setSelected(repCase.multicaseElection);
        
        if(multiCaseElectionCheckBox.isSelected()) {
            loadMultiCase();
        }
        
        electionType1ComboBox.setSelectedItem(repCase.electionType1 == null ? " " : repCase.electionType1);
        electionType2ComboBox.setSelectedItem(repCase.electionType2 == null ? " " : repCase.electionType2);
        electionType3ComboBox.setSelectedItem(repCase.electionType3 == null ? "" : repCase.electionType3);
        
        if(jPanel2.isVisible()) {
            loadSites();
        }
        
        if(electionType3ComboBox.getSelectedItem().toString().equals("Professional/Non-Professional")) {
            loadElectionArrays(repCase);
        } else {
            resultsApproxNumberOfEligibleVoters.setText(repCase.resultApproxNumberEligibleVotes == null ? "" : repCase.resultApproxNumberEligibleVotes);
            resultsVoidBallots.setText(repCase.resultVoidBallots == null ? "" : repCase.resultVoidBallots);
            resultsVotesCastForEEO.setText(repCase.resultVotesCastForEEO == null ? "" : repCase.resultVotesCastForEEO);
            resultsVotesCastForIncumbentEEO.setText(repCase.resultVotesCastForIncumbentEEO == null ? "" : repCase.resultVotesCastForIncumbentEEO);
            resultsVotesCastForRivalEEO1.setText(repCase.resultVotesCastForRivalEEO1 == null ? "" : repCase.resultVotesCastForRivalEEO1);
            resultsVotesCastForRivalEEO2.setText(repCase.resultVotesCastForRivalEEO2 == null ? "" : repCase.resultVotesCastForRivalEEO2);
            resultsVotesCastForRivalEEO3.setText(repCase.resultVotesCastForRivalEEO3 == null ? "" : repCase.resultVotesCastForRivalEEO3);
            resultsVotesCastForNoRepresentative.setText(repCase.resultVotesCastForNoRepresentative == null ? "" : repCase.resultVotesCastForNoRepresentative);
            resultsValidVotesCounted.setText(repCase.resultValidVotesCounted == null ? "" : repCase.resultValidVotesCounted);
            resultsChallengedBallots.setText(repCase.resultChallengedBallots == null ? "" : repCase.resultChallengedBallots);
            resultsTotalBallotsCast.setText(repCase.resultTotalBallotsCast == null ? "" : repCase.resultTotalBallotsCast);
            resultsWhoPrevailed.setSelectedItem(repCase.resultWHoPrevailed == null ? "" : repCase.resultWHoPrevailed);
        }
        
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
        ballotsCountDay.setSelectedItem(repCase.ballotsCountDay == null ? " " : repCase.ballotsCountDay);
        ballotsCountDate.setText(repCase.ballotsCountDate == null ? "" : Global.mmddyyyy.format(repCase.ballotsCountDate));
        ballotsCountTime.setText(repCase.ballotsCountTime == null ? "" : Global.hmma.format(repCase.ballotsCountTime).split(" ")[0]);
        amPMComboBox.setSelectedItem(repCase.ballotsCountTime == null ? " " : Global.hmma.format(repCase.ballotsCountTime).split(" ")[1]);
        eligibilityListDate.setText(repCase.eligibilityListDate == null ? "" : Global.mmddyyyy.format(repCase.eligibilityListDate));
        preElectionConfDateTextBox.setText(repCase.preElectionConfDate == null ? "" : Global.mmddyyyy.format(repCase.preElectionConfDate));
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
        
        resultsApproxNumberOfEligibleVoters.setEnabled(false);
        resultsApproxNumberOfEligibleVoters.setBackground(new Color(238, 238, 238));
        resultsVoidBallots.setEnabled(false);
        resultsVoidBallots.setBackground(new Color(238, 238, 238));
        resultsVotesCastForEEO.setEnabled(false);
        resultsVotesCastForEEO.setBackground(new Color(238, 238, 238));
        resultsVotesCastForIncumbentEEO.setEnabled(false);
        resultsVotesCastForIncumbentEEO.setBackground(new Color(238, 238, 238));
        resultsVotesCastForRivalEEO1.setEnabled(false);
        resultsVotesCastForRivalEEO1.setBackground(new Color(238, 238, 238));
        resultsVotesCastForRivalEEO2.setEnabled(false);
        resultsVotesCastForRivalEEO2.setBackground(new Color(238, 238, 238));
        resultsVotesCastForRivalEEO3.setEnabled(false);
        resultsVotesCastForRivalEEO3.setBackground(new Color(238, 238, 238));
        resultsVotesCastForNoRepresentative.setEnabled(false);
        resultsVotesCastForNoRepresentative.setBackground(new Color(238, 238, 238));
        resultsValidVotesCounted.setEnabled(false);
        resultsValidVotesCounted.setBackground(new Color(238, 238, 238));
        resultsChallengedBallots.setEnabled(false);
        resultsChallengedBallots.setBackground(new Color(238, 238, 238));
        resultsTotalBallotsCast.setEnabled(false);
        resultsTotalBallotsCast.setBackground(new Color(238, 238, 238));
        resultsWhoPrevailed.setEnabled(false);
        
        approxNumberEligibleVotersTextBox.setEnabled(false);
        approxNumberEligibleVotersTextBox.setBackground(new Color(238, 238, 238));
        yesTextBox.setEnabled(false);
        yesTextBox.setBackground(new Color(238, 238, 238));
        noTextBox.setEnabled(false);
        noTextBox.setBackground(new Color(238, 238, 238));
        challengedTextBox.setEnabled(false);
        challengedTextBox.setBackground(new Color(238, 238, 238));
        outcomeComboBox.setEnabled(false);
        whoPrevailedComboBox.setEnabled(false);
        voidBallotsTextBox.setEnabled(false);
        voidBallotsTextBox.setBackground(new Color(238, 238, 238));
        votesCastForNoRepresentativeTextBox.setEnabled(false);
        votesCastForNoRepresentativeTextBox.setBackground(new Color(238, 238, 238));
        votesCastforEEOTextBox.setEnabled(false);
        votesCastforEEOTextBox.setBackground(new Color(238, 238, 238));
        votesCastForIncumbentEEOTextBox.setEnabled(false);
        votesCastForIncumbentEEOTextBox.setBackground(new Color(238, 238, 238));
        votesCastForRivalEEO1TextBox.setEnabled(false);
        votesCastForRivalEEO1TextBox.setBackground(new Color(238, 238, 238));
        votesCastForRivalEEO2TextBox.setEnabled(false);
        votesCastForRivalEEO2TextBox.setBackground(new Color(238, 238, 238));
        votesCastForRivalEEO3TextBox.setEnabled(false);
        votesCastForRivalEEO3TextBox.setBackground(new Color(238, 238, 238));
        
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
        
        resultsApproxNumberOfEligibleVoters.setEnabled(true);
        resultsApproxNumberOfEligibleVoters.setBackground(Color.WHITE);
        resultsVoidBallots.setEnabled(true);
        resultsVoidBallots.setBackground(Color.WHITE);
        resultsVotesCastForEEO.setEnabled(true);
        resultsVotesCastForEEO.setBackground(Color.WHITE);
        resultsVotesCastForIncumbentEEO.setEnabled(true);
        resultsVotesCastForIncumbentEEO.setBackground(Color.WHITE);
        resultsVotesCastForRivalEEO1.setEnabled(true);
        resultsVotesCastForRivalEEO1.setBackground(Color.WHITE);
        resultsVotesCastForRivalEEO2.setEnabled(true);
        resultsVotesCastForRivalEEO2.setBackground(Color.WHITE);
        resultsVotesCastForRivalEEO3.setEnabled(true);
        resultsVotesCastForRivalEEO3.setBackground(Color.WHITE);
        resultsVotesCastForNoRepresentative.setEnabled(true);
        resultsVotesCastForNoRepresentative.setBackground(Color.WHITE);
        resultsChallengedBallots.setEnabled(true);
        resultsChallengedBallots.setBackground(Color.WHITE);
        resultsWhoPrevailed.setEnabled(true);
        
        approxNumberEligibleVotersTextBox.setEnabled(true);
        approxNumberEligibleVotersTextBox.setBackground(Color.WHITE);
        yesTextBox.setEnabled(true);
        yesTextBox.setBackground(Color.WHITE);
        noTextBox.setEnabled(true);
        noTextBox.setBackground(Color.WHITE);
        challengedTextBox.setEnabled(true);
        challengedTextBox.setBackground(Color.WHITE);
        outcomeComboBox.setEnabled(true);
        whoPrevailedComboBox.setEnabled(true);
        voidBallotsTextBox.setEnabled(true);
        voidBallotsTextBox.setBackground(Color.WHITE);
        votesCastForNoRepresentativeTextBox.setEnabled(true);
        votesCastForNoRepresentativeTextBox.setBackground(Color.WHITE);
        votesCastforEEOTextBox.setEnabled(true);
        votesCastforEEOTextBox.setBackground(Color.WHITE);
        votesCastForIncumbentEEOTextBox.setEnabled(true);
        votesCastForIncumbentEEOTextBox.setBackground(Color.WHITE);
        votesCastForRivalEEO1TextBox.setEnabled(true);
        votesCastForRivalEEO1TextBox.setBackground(Color.WHITE);
        votesCastForRivalEEO2TextBox.setEnabled(true);
        votesCastForRivalEEO2TextBox.setBackground(Color.WHITE);
        votesCastForRivalEEO3TextBox.setEnabled(true);
        votesCastForRivalEEO3TextBox.setBackground(Color.WHITE);
        
        addSiteInformation.setVisible(true);
    }
    
    private void saveInfomration(){
        if(professionalButton.isSelected()) {
            hanldeProfessionalNonProfessionalElection("Professional");
        } else if(nonProfessionalButton.isSelected()) {
            hanldeProfessionalNonProfessionalElection("Non-Professional");
        } else if(combinedButton.isSelected()){
            hanldeProfessionalNonProfessionalElection("Combined");
        }
        
        REPCase newCaseInformation = new REPCase();
        
        newCaseInformation.multicaseElection = multiCaseElectionCheckBox.isSelected();
        newCaseInformation.electionType1 = electionType1ComboBox.getSelectedItem().toString().trim().equals("") ? null : electionType1ComboBox.getSelectedItem().toString();
        newCaseInformation.electionType2 = electionType2ComboBox.getSelectedItem().toString().trim().equals("") ? null : electionType2ComboBox.getSelectedItem().toString();
        newCaseInformation.electionType3 = electionType3ComboBox.getSelectedItem().toString().trim().equals("") ? null : electionType3ComboBox.getSelectedItem().toString();
        
        switch (newCaseInformation.electionType1) {
            case "On-Site":
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
                break;
            case "Mail":
            case "Electronic":
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
                newCaseInformation.ballotsCountDate = ballotsCountDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(ballotsCountDate.getText()));
                //ballotsCountTime
                newCaseInformation.ballotsCountTime = ballotsCountTime.getText().equals("") ? null : new Timestamp(NumberFormatService.converthmma(ballotsCountTime.getText() + " " + amPMComboBox.getSelectedItem().toString()));
                //eligibilityListDate
                newCaseInformation.eligibilityListDate = eligibilityListDate.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(eligibilityListDate.getText()));
                //preElectionConfDate --> null
                newCaseInformation.preElectionConfDate = null;
                //selfReleasing --> null
                newCaseInformation.selfReleasing = null;
                break;
            default:
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
                break;
        }
        
        if(jPanel5.isVisible()) {
            newCaseInformation.resultApproxNumberEligibleVotes = resultsApproxNumberOfEligibleVoters.getText().equals("") ? null : resultsApproxNumberOfEligibleVoters.getText();
            newCaseInformation.resultVoidBallots = resultsVoidBallots.getText().equals("") ? null : resultsVoidBallots.getText();
            newCaseInformation.resultVotesCastForEEO = resultsVotesCastForEEO.getText().equals("") ? null : resultsVotesCastForEEO.getText();
            newCaseInformation.resultVotesCastForIncumbentEEO = resultsVotesCastForIncumbentEEO.getText().equals("") ? null : resultsVotesCastForIncumbentEEO.getText();
            newCaseInformation.resultVotesCastForRivalEEO1 = resultsVotesCastForRivalEEO1.getText().equals("") ? null : resultsVotesCastForRivalEEO1.getText();
            newCaseInformation.resultVotesCastForRivalEEO2 = resultsVotesCastForRivalEEO2.getText().equals("") ? null : resultsVotesCastForRivalEEO2.getText();
            newCaseInformation.resultVotesCastForRivalEEO3 = resultsVotesCastForRivalEEO3.getText().equals("") ? null : resultsVotesCastForRivalEEO3.getText();
            newCaseInformation.resultVotesCastForNoRepresentative = resultsVotesCastForNoRepresentative.getText().equals("") ? null : resultsVotesCastForNoRepresentative.getText();
            newCaseInformation.resultValidVotesCounted = resultsValidVotesCounted.getText().equals("") ? null : resultsValidVotesCounted.getText();
            newCaseInformation.resultChallengedBallots = resultsChallengedBallots.getText().equals("") ? null : resultsChallengedBallots.getText();
            newCaseInformation.resultTotalBallotsCast = resultsTotalBallotsCast.getText().equals("") ? null : resultsTotalBallotsCast.getText();
            newCaseInformation.resultWHoPrevailed = resultsWhoPrevailed.getSelectedItem().toString().equals("") ? null : resultsWhoPrevailed.getSelectedItem().toString();
        } else {
            newCaseInformation.resultApproxNumberEligibleVotes = null;
            newCaseInformation.resultVoidBallots = null;
            newCaseInformation.resultVotesCastForEEO = null;
            newCaseInformation.resultVotesCastForIncumbentEEO = null;
            newCaseInformation.resultVotesCastForRivalEEO1 = null;
            newCaseInformation.resultVotesCastForRivalEEO2 = null;
            newCaseInformation.resultVotesCastForRivalEEO3 = null;
            newCaseInformation.resultVotesCastForNoRepresentative = null;
            newCaseInformation.resultValidVotesCounted = null;
            newCaseInformation.resultChallengedBallots = null;
            newCaseInformation.resultTotalBallotsCast = null;
            newCaseInformation.resultWHoPrevailed = null;       
        }
        
        REPCase.updateElectionInformation(newCaseInformation, repCase, professional, nonprofessional, combined);
    }
    
    public void clearAll() {
        electionType3ComboBox.setSelectedIndex(1);
        electionType1ComboBox.setSelectedItem(" ");
        electionType2ComboBox.setSelectedItem(" ");
        
        DefaultTableModel model = (DefaultTableModel) multiCaseElectionTable.getModel();
        model.setRowCount(0);
        DefaultTableModel model2 = (DefaultTableModel) sitesTable.getModel();
        model2.setRowCount(0);
        hideNotRequiredInformation();
        multiCaseElectionCheckBox.setSelected(false);
        
        ballotOne.setText("");
        ballotOneTextBox.setText("");
        ballotTwo.setText("");
        ballotTwoTextBox.setText("");
        ballotThree.setText("");
        ballotThreeTextBox.setText("");
        ballotFour.setText("");
        ballotFourTextBox.setText("");
        eligibilityDate.setText("");
        preElectionConfDateTextBox.setText("");
        selfReleasingTextBox.setText("");
        eligibiltyDateTextBox.setText("");
        mailKitDate.setText("");
        pollingEndDate.setText("");
        pollingStartDate.setText("");
        ballotsCountDay.setSelectedIndex(7);
        ballotsCountDate.setText("");
        ballotsCountTime.setText("");
        amPMComboBox.setSelectedIndex(2);
        eligibilityListDate.setText("");
        
        professional = new String[]{"","","","","","","","","","","","","","",""};
        nonprofessional = new String[]{"","","","","","","","","","","","","","",""};
        combined = new String[]{"","","","","","","","","","","","","","",""};
        
        approxNumberEligibleVotersTextBox.setText("");
        yesTextBox.setText("");
        noTextBox.setText("");
        challengedTextBox.setText("");
        totalBVotesTextBox.setText("");
        outcomeComboBox.setSelectedIndex(2);
        whoPrevailedComboBox.setSelectedItem("");
        voidBallotsTextBox.setText("");
        validVotesTextBox.setText("");
        votesCastForNoRepresentativeTextBox.setText("");
        votesCastforEEOTextBox.setText("");
        votesCastForIncumbentEEOTextBox.setText("");
        votesCastForRivalEEO1TextBox.setText("");
        votesCastForRivalEEO2TextBox.setText("");
        votesCastForRivalEEO3TextBox.setText("");
        
        resultsApproxNumberOfEligibleVoters.setText("");
        resultsVoidBallots.setText("");
        resultsVotesCastForEEO.setText("");
        resultsVotesCastForIncumbentEEO.setText("");
        resultsVotesCastForRivalEEO1.setText("");
        resultsVotesCastForRivalEEO2.setText("");
        resultsVotesCastForRivalEEO3.setText("");
        resultsVotesCastForNoRepresentative.setText("");
        resultsValidVotesCounted.setText("");
        resultsChallengedBallots.setText("");
        resultsTotalBallotsCast.setText("");
        whoPrevailedComboBox.setSelectedItem("");
        
    }
    
    
    
    private void loadMultiCase() {
        DefaultTableModel model = (DefaultTableModel) multiCaseElectionTable.getModel();
        
        model.setRowCount(0);
        
        List relatedCases = REPElectionMultiCase.loadMultiCaseNumber();
        
        for (Object relatedCase : relatedCases) {
            model.addRow(new Object[] {relatedCase});
        }
        multiCaseElectionTable.clearSelection();
    }
    
    private void loadSites() {
        DefaultTableModel model = (DefaultTableModel) sitesTable.getModel();
        
        model.setRowCount(0);
        
        List relatedCases = REPElectionSiteInformation.loadSiteInformationByCaseNumber();
        
        for (Object relatedCase : relatedCases) {
            REPElectionSiteInformation siteData = (REPElectionSiteInformation) relatedCase;
            model.addRow(new Object[] {
                (siteData.siteDate == null ? "" : Global.mmddyyyy.format(siteData.siteDate.getTime())) 
                        + " "
                        + (siteData.siteStartTime == null ? "" : Global.hmma.format(siteData.siteStartTime.getTime()))
                        + (siteData.siteEndTime == null ? "" : " - " + Global.hmma.format(siteData.siteEndTime.getTime())), 
                siteData.sitePlace,
                siteData.siteAddress1 + (siteData.siteAddress2 == null ? "" : ", " + siteData.siteAddress2),
                siteData.siteLocation,
                siteData.id
            });
        }
        sitesTable.clearSelection();
    }
    
    private void sumResultsVotes() {
        int totalVotes = 0;
        int ballotsCast = 0;
        
        totalVotes += resultsVotesCastForEEO.getText().equals("") ? 0 : Integer.valueOf(resultsVotesCastForEEO.getText());
        totalVotes += resultsVotesCastForIncumbentEEO.getText().equals("") ? 0 : Integer.valueOf(resultsVotesCastForIncumbentEEO.getText());
        totalVotes += resultsVotesCastForRivalEEO1.getText().equals("") ? 0 : Integer.valueOf(resultsVotesCastForRivalEEO1.getText());
        totalVotes += resultsVotesCastForRivalEEO2.getText().equals("") ? 0 : Integer.valueOf(resultsVotesCastForRivalEEO2.getText());
        totalVotes += resultsVotesCastForRivalEEO3.getText().equals("") ? 0 : Integer.valueOf(resultsVotesCastForRivalEEO3.getText());
        totalVotes += resultsVotesCastForNoRepresentative.getText().equals("") ? 0 : Integer.valueOf(resultsVotesCastForNoRepresentative.getText());
        
        resultsValidVotesCounted.setText(String.valueOf(totalVotes));
        
        ballotsCast = totalVotes + (resultsChallengedBallots.getText().equals("") ? 0 : Integer.valueOf(resultsChallengedBallots.getText()));
        resultsTotalBallotsCast.setText(String.valueOf(ballotsCast));
    }
    
    private void sumVotes() {
        //9-14
        int professionalVotes = 0;
        int nonprofessionalVotes = 0;
        int combinedVotes = 0;
        
        int totalProfessional = 0;
        int totalNonProfessional = 0;
        int totalCombined = 0;
        
        if(professionalButton.isSelected()) {
            professionalVotes += professional[9].equals("") ? 0 : Integer.valueOf(professional[9]);
            professionalVotes += professional[10].equals("") ? 0 : Integer.valueOf(professional[10]);
            professionalVotes += professional[11].equals("") ? 0 : Integer.valueOf(professional[11]);
            professionalVotes += professional[12].equals("") ? 0 : Integer.valueOf(professional[12]);
            professionalVotes += professional[13].equals("") ? 0 : Integer.valueOf(professional[13]);
            professionalVotes += professional[14].equals("") ? 0 : Integer.valueOf(professional[14]);
            validVotesTextBox.setText(String.valueOf(professionalVotes));
            
            totalProfessional = professionalVotes + (professional[3].equals("") ? 0 : Integer.valueOf(professional[3]));
            totalBVotesTextBox.setText(String.valueOf(totalProfessional));
            
        } else if(nonProfessionalButton.isSelected()) {
            nonprofessionalVotes += nonprofessional[9].equals("") ? 0 : Integer.valueOf(nonprofessional[9]);
            nonprofessionalVotes += nonprofessional[10].equals("") ? 0 : Integer.valueOf(nonprofessional[10]);
            nonprofessionalVotes += nonprofessional[11].equals("") ? 0 : Integer.valueOf(nonprofessional[11]);
            nonprofessionalVotes += nonprofessional[12].equals("") ? 0 : Integer.valueOf(nonprofessional[12]);
            nonprofessionalVotes += nonprofessional[13].equals("") ? 0 : Integer.valueOf(nonprofessional[13]);
            nonprofessionalVotes += nonprofessional[14].equals("") ? 0 : Integer.valueOf(nonprofessional[14]);
            validVotesTextBox.setText(String.valueOf(nonprofessionalVotes));
            
            totalNonProfessional = nonprofessionalVotes + (nonprofessional[3].equals("") ? 0 : Integer.valueOf(nonprofessional[3]));
            totalBVotesTextBox.setText(String.valueOf(totalNonProfessional));
        } else {
            combinedVotes += combined[9].equals("") ? 0 : Integer.valueOf(combined[9]);
            combinedVotes += combined[10].equals("") ? 0 : Integer.valueOf(combined[10]);
            combinedVotes += combined[11].equals("") ? 0 : Integer.valueOf(combined[11]);
            combinedVotes += combined[12].equals("") ? 0 : Integer.valueOf(combined[12]);
            combinedVotes += combined[13].equals("") ? 0 : Integer.valueOf(combined[13]);
            combinedVotes += combined[14].equals("") ? 0 : Integer.valueOf(combined[14]);
            validVotesTextBox.setText(String.valueOf(combinedVotes));
            
            totalCombined = combinedVotes + (combined[3].equals("") ? 0 : Integer.valueOf(combined[3]));
            totalBVotesTextBox.setText(String.valueOf(totalCombined));
        }
    }
    
    private void clearDate(WebDateField dateField, MouseEvent evt) {
        if(evt.getButton() == MouseEvent.BUTTON3 && dateField.isEnabled()) {
            ClearDateDialog dialog = new ClearDateDialog((JFrame) Global.root, true);
            if(dialog.isReset()) {
                dateField.setText("");
            }
            dialog.dispose();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
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
        sitesTable = new javax.swing.JTable();
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
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        resultsApproxNumberOfEligibleVoters = new javax.swing.JTextField();
        resultsVoidBallots = new javax.swing.JTextField();
        resultsVotesCastForEEO = new javax.swing.JTextField();
        jLabel28 = new javax.swing.JLabel();
        resultsVotesCastForIncumbentEEO = new javax.swing.JTextField();
        jLabel29 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        resultsVotesCastForRivalEEO1 = new javax.swing.JTextField();
        resultsVotesCastForRivalEEO2 = new javax.swing.JTextField();
        resultsVotesCastForRivalEEO3 = new javax.swing.JTextField();
        resultsVotesCastForNoRepresentative = new javax.swing.JTextField();
        jLabel33 = new javax.swing.JLabel();
        jLabel34 = new javax.swing.JLabel();
        jLabel35 = new javax.swing.JLabel();
        jLabel36 = new javax.swing.JLabel();
        resultsValidVotesCounted = new javax.swing.JTextField();
        resultsChallengedBallots = new javax.swing.JTextField();
        resultsTotalBallotsCast = new javax.swing.JTextField();
        resultsWhoPrevailed = new javax.swing.JComboBox<>();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        professionalButton = new javax.swing.JButton();
        nonProfessionalButton = new javax.swing.JButton();
        combinedButton = new javax.swing.JButton();
        jLabel37 = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        approxNumberEligibleVotersTextBox = new javax.swing.JTextField();
        yesTextBox = new javax.swing.JTextField();
        noTextBox = new javax.swing.JTextField();
        totalBVotesTextBox = new javax.swing.JTextField();
        challengedTextBox = new javax.swing.JTextField();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        jLabel45 = new javax.swing.JLabel();
        whoPrevailedComboBox = new javax.swing.JComboBox<>();
        voidBallotsTextBox = new javax.swing.JTextField();
        validVotesTextBox = new javax.swing.JTextField();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jLabel48 = new javax.swing.JLabel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        votesCastForNoRepresentativeTextBox = new javax.swing.JTextField();
        votesCastforEEOTextBox = new javax.swing.JTextField();
        votesCastForIncumbentEEOTextBox = new javax.swing.JTextField();
        votesCastForRivalEEO1TextBox = new javax.swing.JTextField();
        votesCastForRivalEEO2TextBox = new javax.swing.JTextField();
        votesCastForRivalEEO3TextBox = new javax.swing.JTextField();
        outcomeComboBox = new javax.swing.JComboBox<>();

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

        electionType3ComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Professional/Non-Professional", "Standard" }));
        electionType3ComboBox.setSelectedIndex(1);
        electionType3ComboBox.setEnabled(false);
        electionType3ComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                electionType3ComboBoxActionPerformed(evt);
            }
        });

        jPanel1.setMaximumSize(new java.awt.Dimension(489, 47));
        jPanel1.setMinimumSize(new java.awt.Dimension(0, 0));

        jScrollPane1.setMinimumSize(new java.awt.Dimension(454, 49));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(454, 49));

        multiCaseElectionTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Multicase Election"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        multiCaseElectionTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                multiCaseElectionTableMouseClicked(evt);
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
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(addMultiCaseElectionButton, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 155, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(addMultiCaseElectionButton))
        );

        jPanel2.setSize(new java.awt.Dimension(489, 85));

        jLabel9.setText("Site Information:");

        addSiteInformation.setText("+");
        addSiteInformation.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                addSiteInformationActionPerformed(evt);
            }
        });

        sitesTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Date / Time", "Place", "Address", "Location", "ID"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.Object.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        sitesTable.setMinimumSize(new java.awt.Dimension(60, 29));
        sitesTable.setPreferredSize(new java.awt.Dimension(300, 30));
        sitesTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                sitesTableMouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(sitesTable);
        if (sitesTable.getColumnModel().getColumnCount() > 0) {
            sitesTable.getColumnModel().getColumn(4).setResizable(false);
            sitesTable.getColumnModel().getColumn(4).setPreferredWidth(0);
        }

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
                        .addGap(10, 10, 10)
                        .addComponent(addSiteInformation, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap())))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLabel9)
                .addGap(3, 3, 3)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(addSiteInformation, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 57, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 0, 0))
        );

        jPanel3.setPreferredSize(new java.awt.Dimension(785, 204));

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
                    .addComponent(ballotOneTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 264, Short.MAX_VALUE)
                    .addComponent(ballotTwoTextBox)
                    .addComponent(ballotThreeTextBox)
                    .addComponent(ballotFourTextBox))
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
                    .addComponent(preElectionConfDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 290, Short.MAX_VALUE)
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
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
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
                    .addComponent(eligibilityDate, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE))
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

        jLabel26.setText("Ballots Count Time:");

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
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                pollingStartDateMouseEntered(evt);
            }
        });
        pollingStartDate.addInputMethodListener(new java.awt.event.InputMethodListener() {
            public void inputMethodTextChanged(java.awt.event.InputMethodEvent evt) {
            }
            public void caretPositionChanged(java.awt.event.InputMethodEvent evt) {
                pollingStartDateCaretPositionChanged(evt);
            }
        });
        pollingStartDate.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                pollingStartDateActionPerformed(evt);
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
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel27)
                            .addComponent(jLabel26))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(ballotsCountTime)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(amPMComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 64, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(eligibilityListDate, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel25)
                                .addComponent(jLabel24, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 116, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(jLabel20, javax.swing.GroupLayout.Alignment.TRAILING))
                            .addComponent(jLabel23, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(pollingStartDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pollingEndDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ballotsCountDate, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ballotsCountDay, 0, 300, Short.MAX_VALUE)))))
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
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(ballotsCountDay, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jLabel24, javax.swing.GroupLayout.PREFERRED_SIZE, 21, javax.swing.GroupLayout.PREFERRED_SIZE)))
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
                .addContainerGap())
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
            .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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

        jLabel13.setText("Number of Eligible Voters:");

        jLabel21.setText("Void Ballots:");

        jLabel22.setText("Votes Cast for EEO:");

        resultsApproxNumberOfEligibleVoters.setBackground(new java.awt.Color(238, 238, 238));
        resultsApproxNumberOfEligibleVoters.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsApproxNumberOfEligibleVoters.setEnabled(false);

        resultsVoidBallots.setBackground(new java.awt.Color(238, 238, 238));
        resultsVoidBallots.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsVoidBallots.setEnabled(false);

        resultsVotesCastForEEO.setBackground(new java.awt.Color(238, 238, 238));
        resultsVotesCastForEEO.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsVotesCastForEEO.setEnabled(false);
        resultsVotesCastForEEO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resultsVotesCastForEEOKeyReleased(evt);
            }
        });

        jLabel28.setText("Votes Cast for Incumbent EEO:");

        resultsVotesCastForIncumbentEEO.setBackground(new java.awt.Color(238, 238, 238));
        resultsVotesCastForIncumbentEEO.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsVotesCastForIncumbentEEO.setEnabled(false);
        resultsVotesCastForIncumbentEEO.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resultsVotesCastForIncumbentEEOKeyReleased(evt);
            }
        });

        jLabel29.setText("Votes Cast For Rival EEO #1:");

        jLabel30.setText("Votes Cast For Rival EEO #2:");

        jLabel31.setText("Votes Cast for Rival EEO #3:");

        jLabel32.setText("Votes Cast For No Representative:");

        resultsVotesCastForRivalEEO1.setBackground(new java.awt.Color(238, 238, 238));
        resultsVotesCastForRivalEEO1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsVotesCastForRivalEEO1.setEnabled(false);
        resultsVotesCastForRivalEEO1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resultsVotesCastForRivalEEO1KeyReleased(evt);
            }
        });

        resultsVotesCastForRivalEEO2.setBackground(new java.awt.Color(238, 238, 238));
        resultsVotesCastForRivalEEO2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsVotesCastForRivalEEO2.setEnabled(false);
        resultsVotesCastForRivalEEO2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resultsVotesCastForRivalEEO2KeyReleased(evt);
            }
        });

        resultsVotesCastForRivalEEO3.setBackground(new java.awt.Color(238, 238, 238));
        resultsVotesCastForRivalEEO3.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsVotesCastForRivalEEO3.setEnabled(false);
        resultsVotesCastForRivalEEO3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resultsVotesCastForRivalEEO3KeyReleased(evt);
            }
        });

        resultsVotesCastForNoRepresentative.setBackground(new java.awt.Color(238, 238, 238));
        resultsVotesCastForNoRepresentative.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsVotesCastForNoRepresentative.setEnabled(false);
        resultsVotesCastForNoRepresentative.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resultsVotesCastForNoRepresentativeKeyReleased(evt);
            }
        });

        jLabel33.setText("Valid Votes Counted:");

        jLabel34.setText("Challenged Ballots:");

        jLabel35.setText("Total Ballots Cast:");

        jLabel36.setText("Who Prevailed:");

        resultsValidVotesCounted.setBackground(new java.awt.Color(238, 238, 238));
        resultsValidVotesCounted.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsValidVotesCounted.setEnabled(false);

        resultsChallengedBallots.setBackground(new java.awt.Color(238, 238, 238));
        resultsChallengedBallots.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsChallengedBallots.setEnabled(false);
        resultsChallengedBallots.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                resultsChallengedBallotsKeyReleased(evt);
            }
        });

        resultsTotalBallotsCast.setBackground(new java.awt.Color(238, 238, 238));
        resultsTotalBallotsCast.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        resultsTotalBallotsCast.setEnabled(false);

        resultsWhoPrevailed.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        resultsWhoPrevailed.setEnabled(false);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel28)
                    .addComponent(jLabel22)
                    .addComponent(jLabel21)
                    .addComponent(jLabel13))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(resultsVotesCastForEEO, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(resultsVoidBallots, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resultsVotesCastForIncumbentEEO)
                    .addComponent(resultsApproxNumberOfEligibleVoters, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel32)
                    .addComponent(jLabel31)
                    .addComponent(jLabel30)
                    .addComponent(jLabel29))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(resultsVotesCastForRivalEEO3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                    .addComponent(resultsVotesCastForRivalEEO2, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resultsVotesCastForRivalEEO1, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(resultsVotesCastForNoRepresentative))
                .addGap(10, 10, 10)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel34, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel35, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel36, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(resultsChallengedBallots, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(resultsValidVotesCounted, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(resultsTotalBallotsCast))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(resultsWhoPrevailed, 0, 320, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(resultsApproxNumberOfEligibleVoters, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel29)
                    .addComponent(resultsVotesCastForRivalEEO1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel33)
                    .addComponent(resultsValidVotesCounted, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(resultsVoidBallots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel30)
                    .addComponent(resultsVotesCastForRivalEEO2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel34)
                    .addComponent(resultsChallengedBallots, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel22)
                    .addComponent(resultsVotesCastForEEO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel31)
                    .addComponent(resultsVotesCastForRivalEEO3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel35)
                    .addComponent(resultsTotalBallotsCast, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel28)
                    .addComponent(resultsVotesCastForIncumbentEEO, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel32)
                    .addComponent(resultsVotesCastForNoRepresentative, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel36)
                    .addComponent(resultsWhoPrevailed, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 98, Short.MAX_VALUE))
        );

        jPanel4.add(jPanel5, "card2");

        jLabel11.setText("Professional/Non-Professional");

        professionalButton.setText("Professional");
        buttonGroup1.add(professionalButton);
        professionalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                professionalButtonActionPerformed(evt);
            }
        });
        jPanel9.add(professionalButton);

        nonProfessionalButton.setText("Non-Professional");
        buttonGroup1.add(nonProfessionalButton);
        nonProfessionalButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                nonProfessionalButtonActionPerformed(evt);
            }
        });
        jPanel9.add(nonProfessionalButton);

        combinedButton.setText("Combined");
        buttonGroup1.add(combinedButton);
        combinedButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combinedButtonActionPerformed(evt);
            }
        });
        jPanel9.add(combinedButton);

        jLabel37.setText("Number of Eligible Voters:");

        jLabel38.setText("YES:");

        jLabel39.setText("NO:");

        jLabel40.setText("Challenged:");

        jLabel41.setText("Total Votes:");

        approxNumberEligibleVotersTextBox.setBackground(new java.awt.Color(238, 238, 238));
        approxNumberEligibleVotersTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        approxNumberEligibleVotersTextBox.setEnabled(false);

        yesTextBox.setBackground(new java.awt.Color(238, 238, 238));
        yesTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        yesTextBox.setEnabled(false);

        noTextBox.setBackground(new java.awt.Color(238, 238, 238));
        noTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        noTextBox.setEnabled(false);

        totalBVotesTextBox.setBackground(new java.awt.Color(238, 238, 238));
        totalBVotesTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        totalBVotesTextBox.setEnabled(false);

        challengedTextBox.setBackground(new java.awt.Color(238, 238, 238));
        challengedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        challengedTextBox.setEnabled(false);
        challengedTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                challengedTextBoxKeyReleased(evt);
            }
        });

        jLabel42.setText("Outcome:");

        jLabel43.setText("Who Prevailed:");

        jLabel44.setText("Void Ballots:");

        jLabel45.setText("Valid Votes:");

        whoPrevailedComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));
        whoPrevailedComboBox.setEnabled(false);

        voidBallotsTextBox.setBackground(new java.awt.Color(238, 238, 238));
        voidBallotsTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        voidBallotsTextBox.setEnabled(false);

        validVotesTextBox.setBackground(new java.awt.Color(238, 238, 238));
        validVotesTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        validVotesTextBox.setEnabled(false);

        jLabel46.setText("Votes Cast For No Representative:");

        jLabel47.setText("Votes Cast for EEO:");

        jLabel48.setText("Votes Cast for Incumbent EEO:");

        jLabel49.setText("Votes Cast for Rival EEO 1:");

        jLabel50.setText("Votes Cast For Rival EEO 2:");

        jLabel51.setText("Votes Cast for Rival EEO 3:");

        votesCastForNoRepresentativeTextBox.setBackground(new java.awt.Color(238, 238, 238));
        votesCastForNoRepresentativeTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        votesCastForNoRepresentativeTextBox.setEnabled(false);
        votesCastForNoRepresentativeTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                votesCastForNoRepresentativeTextBoxKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                votesCastForNoRepresentativeTextBoxKeyReleased(evt);
            }
        });

        votesCastforEEOTextBox.setBackground(new java.awt.Color(238, 238, 238));
        votesCastforEEOTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        votesCastforEEOTextBox.setEnabled(false);
        votesCastforEEOTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                votesCastforEEOTextBoxKeyTyped(evt);
            }
            public void keyReleased(java.awt.event.KeyEvent evt) {
                votesCastforEEOTextBoxKeyReleased(evt);
            }
        });

        votesCastForIncumbentEEOTextBox.setBackground(new java.awt.Color(238, 238, 238));
        votesCastForIncumbentEEOTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        votesCastForIncumbentEEOTextBox.setEnabled(false);
        votesCastForIncumbentEEOTextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                votesCastForIncumbentEEOTextBoxKeyReleased(evt);
            }
        });

        votesCastForRivalEEO1TextBox.setBackground(new java.awt.Color(238, 238, 238));
        votesCastForRivalEEO1TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        votesCastForRivalEEO1TextBox.setEnabled(false);
        votesCastForRivalEEO1TextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                votesCastForRivalEEO1TextBoxKeyReleased(evt);
            }
        });

        votesCastForRivalEEO2TextBox.setBackground(new java.awt.Color(238, 238, 238));
        votesCastForRivalEEO2TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        votesCastForRivalEEO2TextBox.setEnabled(false);
        votesCastForRivalEEO2TextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                votesCastForRivalEEO2TextBoxKeyReleased(evt);
            }
        });

        votesCastForRivalEEO3TextBox.setBackground(new java.awt.Color(238, 238, 238));
        votesCastForRivalEEO3TextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        votesCastForRivalEEO3TextBox.setEnabled(false);
        votesCastForRivalEEO3TextBox.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                votesCastForRivalEEO3TextBoxKeyReleased(evt);
            }
        });

        outcomeComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No", " " }));
        outcomeComboBox.setSelectedIndex(2);
        outcomeComboBox.setEnabled(false);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 1020, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel51)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel38)
                                    .addComponent(jLabel37)
                                    .addComponent(jLabel39)
                                    .addComponent(jLabel40)
                                    .addComponent(jLabel41))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addComponent(totalBVotesTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(jLabel50))
                                    .addGroup(jPanel6Layout.createSequentialGroup()
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(challengedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel45))
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(noTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel44))
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(approxNumberEligibleVotersTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(jLabel42))
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addComponent(yesTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addGap(18, 18, 18)
                                                .addComponent(jLabel43)))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                                    .addComponent(validVotesTextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                                                    .addComponent(voidBallotsTextBox, javax.swing.GroupLayout.Alignment.LEADING))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jLabel48, javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel49, javax.swing.GroupLayout.Alignment.TRAILING)))
                                            .addGroup(jPanel6Layout.createSequentialGroup()
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(whoPrevailedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(outcomeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 225, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                    .addComponent(jLabel46)
                                                    .addComponent(jLabel47))))))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(votesCastForRivalEEO2TextBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 60, Short.MAX_VALUE)
                            .addComponent(votesCastForRivalEEO1TextBox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(votesCastForIncumbentEEOTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(votesCastforEEOTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(votesCastForNoRepresentativeTextBox, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(votesCastForRivalEEO3TextBox))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(jLabel11)
                .addGap(0, 0, 0)
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 33, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel42, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel37)
                        .addComponent(approxNumberEligibleVotersTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel46)
                        .addComponent(votesCastForNoRepresentativeTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(outcomeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(2, 2, 2)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel38)
                    .addComponent(yesTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(whoPrevailedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel47)
                    .addComponent(votesCastforEEOTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel43, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel39)
                    .addComponent(noTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel44)
                    .addComponent(voidBallotsTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel48)
                    .addComponent(votesCastForIncumbentEEOTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel40)
                    .addComponent(challengedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel45)
                    .addComponent(validVotesTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel49)
                    .addComponent(votesCastForRivalEEO1TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel41)
                    .addComponent(totalBVotesTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel50)
                    .addComponent(votesCastForRivalEEO2TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel51)
                    .addComponent(votesCastForRivalEEO3TextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(4, 4, 4))
        );

        jPanel4.add(jPanel6, "card3");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(electionType1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(electionType2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(electionType3ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(multiCaseElectionCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 204, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 826, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap())))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 26, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(electionType1ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(electionType2ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(electionType3ComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(multiCaseElectionCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(4, 4, 4)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void electionType1ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_electionType1ComboBoxActionPerformed
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
                if(electionType3ComboBox.getSelectedItem().toString().equals("Standard")) {
                    if(electionType1ComboBox.getSelectedItem().toString().trim().equals("")) {
                        jPanel4.setVisible(false);
                    } else {
                        jPanel4.setVisible(true);
                        resultsCard.show(jPanel4, "card2");
                    }
                } else {
                    jPanel4.setVisible(true);
                    resultsCard.show(jPanel4, "card3");
                }
                break;
            case "Mail":
            case "Electronic":
                jPanel14.add(jPanel16);
                jPanel14.remove(jPanel15);
                siteCard.show(jPanel14, "card3");
                if(electionType3ComboBox.getSelectedItem().toString().equals("Standard")) {
                    if(electionType1ComboBox.getSelectedItem().toString().trim().equals("")) {
                        jPanel4.setVisible(false);
                    } else {
                        jPanel4.setVisible(true);
                        resultsCard.show(jPanel4, "card2");
                    }
                } else {
                    jPanel4.setVisible(true);
                    resultsCard.show(jPanel4, "card3");
                }
                break;
            default:
                jPanel3.setVisible(false);
                jPanel4.setVisible(false);
                if(electionType3ComboBox.getSelectedItem().toString().equals("Standard")) {
                    if(electionType1ComboBox.getSelectedItem().toString().trim().equals("")) {
                        jPanel4.setVisible(false);
                    } else {
                        jPanel4.setVisible(true);
                        resultsCard.show(jPanel4, "card2");
                    }
                } else {
                    jPanel4.setVisible(true);
                    resultsCard.show(jPanel4, "card3");
                }
                break;
        }
    }//GEN-LAST:event_electionType1ComboBoxActionPerformed

    private void electionType3ComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_electionType3ComboBoxActionPerformed
        
        if(electionType3ComboBox.getSelectedItem().toString().equals("Standard")) {
            if(electionType1ComboBox.getSelectedItem().toString().trim().equals("")) {
                jPanel4.setVisible(false);
            } else {
                whereUserIsComingFrom = "Professional";
                professionalButton.setSelected(true);
                nonProfessionalButton.setSelected(false);
                combinedButton.setSelected(false);
                jPanel4.setVisible(true);
                resultsCard.show(jPanel4, "card2");
            }
        } else {
            jPanel4.setVisible(true);
            resultsCard.show(jPanel4, "card3");
        }
    }//GEN-LAST:event_electionType3ComboBoxActionPerformed

    private void eligibiltyDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eligibiltyDateTextBoxMouseClicked
        clearDate(eligibiltyDateTextBox, evt);
    }//GEN-LAST:event_eligibiltyDateTextBoxMouseClicked

    private void preElectionConfDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_preElectionConfDateTextBoxMouseClicked
        clearDate(preElectionConfDateTextBox, evt);
    }//GEN-LAST:event_preElectionConfDateTextBoxMouseClicked

    private void eligibilityDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eligibilityDateMouseClicked
        clearDate(eligibilityDate, evt);
    }//GEN-LAST:event_eligibilityDateMouseClicked

    private void mailKitDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_mailKitDateMouseClicked
        clearDate(mailKitDate, evt);
    }//GEN-LAST:event_mailKitDateMouseClicked

    private void pollingStartDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pollingStartDateMouseClicked
        clearDate(pollingStartDate, evt);
    }//GEN-LAST:event_pollingStartDateMouseClicked

    private void pollingEndDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pollingEndDateMouseClicked
        clearDate(pollingEndDate, evt);
    }//GEN-LAST:event_pollingEndDateMouseClicked

    private void ballotsCountDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_ballotsCountDateMouseClicked
        clearDate(ballotsCountDate, evt);
    }//GEN-LAST:event_ballotsCountDateMouseClicked

    private void eligibilityListDateMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_eligibilityListDateMouseClicked
        clearDate(eligibiltyDateTextBox, evt);
    }//GEN-LAST:event_eligibilityListDateMouseClicked

    private void multiCaseElectionCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_multiCaseElectionCheckBoxStateChanged
        jPanel1.setVisible(multiCaseElectionCheckBox.isSelected());
    }//GEN-LAST:event_multiCaseElectionCheckBoxStateChanged

    private void addMultiCaseElectionButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addMultiCaseElectionButtonActionPerformed
        new AddMultiCaseElection((JFrame) Global.root.getRootPane().getParent(), true);
        loadMultiCase();
    }//GEN-LAST:event_addMultiCaseElectionButtonActionPerformed

    private void pollingEndDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pollingEndDateActionPerformed
        
    }//GEN-LAST:event_pollingEndDateActionPerformed

    private void ballotsCountTimeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ballotsCountTimeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ballotsCountTimeActionPerformed

    private void nonProfessionalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_nonProfessionalButtonActionPerformed
        professionalButton.setSelected(false);
        nonProfessionalButton.setSelected(true);
        combinedButton.setSelected(false);
        
        hanldeProfessionalNonProfessionalElection("Non-Professional");
    }//GEN-LAST:event_nonProfessionalButtonActionPerformed

    private void professionalButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_professionalButtonActionPerformed
        professionalButton.setSelected(true);
        nonProfessionalButton.setSelected(false);
        combinedButton.setSelected(false);
        
        hanldeProfessionalNonProfessionalElection("Professional");
    }//GEN-LAST:event_professionalButtonActionPerformed

    private void combinedButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combinedButtonActionPerformed
        professionalButton.setSelected(false);
        nonProfessionalButton.setSelected(false);
        combinedButton.setSelected(true);
        
        hanldeProfessionalNonProfessionalElection("Combined");
    }//GEN-LAST:event_combinedButtonActionPerformed

    private void multiCaseElectionTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_multiCaseElectionTableMouseClicked
        if(Global.root.getjButton2().getText().equals("Save")) {
            if(multiCaseElectionTable.getSelectedRow() > -1) {
                if(evt.getButton() == MouseEvent.BUTTON3) {
                    new RemoveMultiCaseDialog(
                        (JFrame) Global.root.getRootPane().getParent(),
                        true,
                        multiCaseElectionTable.getValueAt(multiCaseElectionTable.getSelectedRow(), 0).toString().trim()
                    );
                    loadMultiCase();
                }
            }
        }
    }//GEN-LAST:event_multiCaseElectionTableMouseClicked

    private void votesCastForNoRepresentativeTextBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_votesCastForNoRepresentativeTextBoxKeyTyped
        hanldeProfessionalNonProfessionalElection(whereUserIsComingFrom);
        sumVotes();
    }//GEN-LAST:event_votesCastForNoRepresentativeTextBoxKeyTyped

    private void votesCastforEEOTextBoxKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_votesCastforEEOTextBoxKeyTyped
        hanldeProfessionalNonProfessionalElection(whereUserIsComingFrom);
        sumVotes();
    }//GEN-LAST:event_votesCastforEEOTextBoxKeyTyped

    private void votesCastForNoRepresentativeTextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_votesCastForNoRepresentativeTextBoxKeyReleased
        hanldeProfessionalNonProfessionalElection(whereUserIsComingFrom);
        sumVotes();
    }//GEN-LAST:event_votesCastForNoRepresentativeTextBoxKeyReleased

    private void votesCastforEEOTextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_votesCastforEEOTextBoxKeyReleased
        hanldeProfessionalNonProfessionalElection(whereUserIsComingFrom);
        sumVotes();
    }//GEN-LAST:event_votesCastforEEOTextBoxKeyReleased

    private void votesCastForIncumbentEEOTextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_votesCastForIncumbentEEOTextBoxKeyReleased
        hanldeProfessionalNonProfessionalElection(whereUserIsComingFrom);
        sumVotes();
    }//GEN-LAST:event_votesCastForIncumbentEEOTextBoxKeyReleased

    private void votesCastForRivalEEO1TextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_votesCastForRivalEEO1TextBoxKeyReleased
        hanldeProfessionalNonProfessionalElection(whereUserIsComingFrom);
        sumVotes();
    }//GEN-LAST:event_votesCastForRivalEEO1TextBoxKeyReleased

    private void votesCastForRivalEEO2TextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_votesCastForRivalEEO2TextBoxKeyReleased
        hanldeProfessionalNonProfessionalElection(whereUserIsComingFrom);
        sumVotes();
    }//GEN-LAST:event_votesCastForRivalEEO2TextBoxKeyReleased

    private void votesCastForRivalEEO3TextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_votesCastForRivalEEO3TextBoxKeyReleased
        hanldeProfessionalNonProfessionalElection(whereUserIsComingFrom);
        sumVotes();
    }//GEN-LAST:event_votesCastForRivalEEO3TextBoxKeyReleased

    private void challengedTextBoxKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_challengedTextBoxKeyReleased
        hanldeProfessionalNonProfessionalElection(whereUserIsComingFrom);
        sumVotes();
    }//GEN-LAST:event_challengedTextBoxKeyReleased

    private void pollingStartDateMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_pollingStartDateMouseEntered
        // TODO add your handling code here:
    }//GEN-LAST:event_pollingStartDateMouseEntered

    private void pollingStartDateCaretPositionChanged(java.awt.event.InputMethodEvent evt) {//GEN-FIRST:event_pollingStartDateCaretPositionChanged
        try {
            Date startDate = Global.mmddyyyy.parse(pollingStartDate.getText());
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.DATE, 14);
            pollingEndDate.setText(Global.mmddyyyy.format(cal.getTime()));
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
        
    }//GEN-LAST:event_pollingStartDateCaretPositionChanged

    private void pollingStartDateActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_pollingStartDateActionPerformed
        try {
            Date startDate = Global.mmddyyyy.parse(pollingStartDate.getText());
            Calendar cal = Calendar.getInstance();
            cal.setTime(startDate);
            cal.add(Calendar.DATE, 14);
            pollingEndDate.setText(Global.mmddyyyy.format(cal.getTime()));
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }//GEN-LAST:event_pollingStartDateActionPerformed

    private void resultsVotesCastForEEOKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsVotesCastForEEOKeyReleased
        sumResultsVotes();
    }//GEN-LAST:event_resultsVotesCastForEEOKeyReleased

    private void resultsVotesCastForIncumbentEEOKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsVotesCastForIncumbentEEOKeyReleased
        sumResultsVotes();
    }//GEN-LAST:event_resultsVotesCastForIncumbentEEOKeyReleased

    private void resultsVotesCastForRivalEEO1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsVotesCastForRivalEEO1KeyReleased
        sumResultsVotes();
    }//GEN-LAST:event_resultsVotesCastForRivalEEO1KeyReleased

    private void resultsVotesCastForRivalEEO2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsVotesCastForRivalEEO2KeyReleased
        sumResultsVotes();
    }//GEN-LAST:event_resultsVotesCastForRivalEEO2KeyReleased

    private void resultsVotesCastForRivalEEO3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsVotesCastForRivalEEO3KeyReleased
        sumResultsVotes();
    }//GEN-LAST:event_resultsVotesCastForRivalEEO3KeyReleased

    private void resultsVotesCastForNoRepresentativeKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsVotesCastForNoRepresentativeKeyReleased
        sumResultsVotes();
    }//GEN-LAST:event_resultsVotesCastForNoRepresentativeKeyReleased

    private void resultsChallengedBallotsKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_resultsChallengedBallotsKeyReleased
        sumResultsVotes();
    }//GEN-LAST:event_resultsChallengedBallotsKeyReleased

    private void sitesTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_sitesTableMouseClicked
        if(Global.root.getjButton2().getText().equals("Save")) {
            if(evt.getClickCount() == 2) {
//            new REPUpdateMediationDialog((JFrame) Global.root.getRootPane().getParent(),
//                    true,
//                    mediationTable.getValueAt(mediationTable.getSelectedRow(),0).toString());
//            loadAllMediations();
            } else if(evt.getButton() == MouseEvent.BUTTON3) {
                new RemoveSiteDialog(Global.root, true, (int)sitesTable.getValueAt(sitesTable.getSelectedRow(), 4));
                loadSites();
            }
        }
    }//GEN-LAST:event_sitesTableMouseClicked

    private void addSiteInformationActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addSiteInformationActionPerformed
        new AddSiteElectionDialog(Global.root, true);
        loadSites();
    }//GEN-LAST:event_addSiteInformationActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addMultiCaseElectionButton;
    private javax.swing.JButton addSiteInformation;
    private javax.swing.JComboBox<String> amPMComboBox;
    private javax.swing.JTextField approxNumberEligibleVotersTextBox;
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
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTextField challengedTextBox;
    private javax.swing.JButton combinedButton;
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
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
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
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private com.alee.extended.date.WebDateField mailKitDate;
    private javax.swing.JCheckBox multiCaseElectionCheckBox;
    private javax.swing.JTable multiCaseElectionTable;
    private javax.swing.JTextField noTextBox;
    private javax.swing.JButton nonProfessionalButton;
    private javax.swing.JComboBox<String> outcomeComboBox;
    private com.alee.extended.date.WebDateField pollingEndDate;
    private com.alee.extended.date.WebDateField pollingStartDate;
    private com.alee.extended.date.WebDateField preElectionConfDateTextBox;
    private javax.swing.JButton professionalButton;
    private javax.swing.JTextField resultsApproxNumberOfEligibleVoters;
    private javax.swing.JTextField resultsChallengedBallots;
    private javax.swing.JTextField resultsTotalBallotsCast;
    private javax.swing.JTextField resultsValidVotesCounted;
    private javax.swing.JTextField resultsVoidBallots;
    private javax.swing.JTextField resultsVotesCastForEEO;
    private javax.swing.JTextField resultsVotesCastForIncumbentEEO;
    private javax.swing.JTextField resultsVotesCastForNoRepresentative;
    private javax.swing.JTextField resultsVotesCastForRivalEEO1;
    private javax.swing.JTextField resultsVotesCastForRivalEEO2;
    private javax.swing.JTextField resultsVotesCastForRivalEEO3;
    private javax.swing.JComboBox<String> resultsWhoPrevailed;
    private javax.swing.JTextField selfReleasingTextBox;
    private javax.swing.JTable sitesTable;
    private javax.swing.JTextField totalBVotesTextBox;
    private javax.swing.JTextField validVotesTextBox;
    private javax.swing.JTextField voidBallotsTextBox;
    private javax.swing.JTextField votesCastForIncumbentEEOTextBox;
    private javax.swing.JTextField votesCastForNoRepresentativeTextBox;
    private javax.swing.JTextField votesCastForRivalEEO1TextBox;
    private javax.swing.JTextField votesCastForRivalEEO2TextBox;
    private javax.swing.JTextField votesCastForRivalEEO3TextBox;
    private javax.swing.JTextField votesCastforEEOTextBox;
    private javax.swing.JComboBox<String> whoPrevailedComboBox;
    private javax.swing.JTextField yesTextBox;
    // End of variables declaration//GEN-END:variables
}
