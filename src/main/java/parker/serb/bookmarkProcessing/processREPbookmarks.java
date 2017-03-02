/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.com.Dispatch;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;
import parker.serb.sql.BoardMeeting;
import parker.serb.sql.CaseParty;
import parker.serb.sql.REPCase;
import parker.serb.sql.REPElectionMultiCase;
import parker.serb.sql.REPMediation;
import parker.serb.sql.RelatedCase;
import parker.serb.sql.User;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class processREPbookmarks {

    public static Dispatch processDoAREPWordLetter(Dispatch Document, int senderID, List<Integer> toParties, List<Integer> ccParties) {
        //get basic information
        REPCase caseInfo = REPCase.loadCaseDetails(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);
        List<String> relatedCasesList = RelatedCase.loadRelatedCases();
        List<REPMediation> mediationList = REPMediation.loadMediationsByCaseNumber();
        BoardMeeting meeting = BoardMeeting.loadLatestREPBoardMeeting();
        List multicaseElection = REPElectionMultiCase.loadMultiCaseNumber();

        User user = null;
        if (caseInfo.currentOwnerID > 0) {
            user = User.findUserByID(caseInfo.currentOwnerID); //Need to get user by ID
        }

        String relatedCases = "";
        String incumbentEmployeeOrganizationName = "";   //IEO
        String incumbentEmployeeOrganizationRepName = "";//IEOREP
        String employerName = "";                        //E
        String employerRepName = "";                     //EREP
        String rivalEmployeeOrganizationName = "";       //REO
        String rivalEmployeeOrganizationRepName = "";    //REOREP
        String employeeOrganizationName = "";            //EO
        String employeeOrganizationRepName = "";         //EOREP
        String petitionerName = "";                      //P
        String petitionerRepName = "";                   //PREP
        String incumbentEmployeeOrganizationAddressBlock = "";   //IEO
        String incumbentEmployeeOrganizationRepAddressBlock = "";//IEOREP
        String employerAddressBlock = "";                        //E
        String employerRepAddressBlock = "";                     //EREP
        String rivalEmployeeOrganizationAddressBlock = "";       //REO
        String rivalEmployeeOrganizationRepAddressBlock = "";    //REOREP
        String employeeOrganizationAddressBlock = "";            //EO
        String employeeOrganizationRepAddressBlock = "";         //EOREP
        String petitionerAddressBlock = "";                      //P
        String petitionerRepAddressBlock = "";                   //PREP
        String mediationDate = null;
        String mediationTime = null;
        String mediationCC = "";
        String toAddressBlock = "";
        String ccNameBlock = "";
        String DIRECCBlock = "";
        String polling = "";
        String multiCaseElection = "";
        String longMeetDate = "";

        for (CaseParty party : partyList) {

            if (toParties != null) {
                for (int person : toParties) {
                    if (person == party.id) {
                        if (!"".equals(toAddressBlock.trim())) {
                            toAddressBlock += "\n\n";
                        }
                        toAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                    }
                }
            }

            if (ccParties != null) {
                for (int person : ccParties) {
                    if (person == party.id) {
                        if (!"".equals(ccNameBlock.trim())) {
                            ccNameBlock += ",\n";
                        }
                        ccNameBlock += StringUtilities.buildCasePartyNameNoPreFix(party);
                    }
                }
            }

            if (!DIRECCBlock.trim().equals("")){
                DIRECCBlock += "\n";
            }
            DIRECCBlock += StringUtilities.buildCasePartyNameNoPreFix(party);
            DIRECCBlock += party.emailAddress == null ? "" : ", " + party.emailAddress.trim();


            if (null != party.caseRelation) {
                switch (party.caseRelation) {
                    case "Incumbent Employee Organization":
                        if (!"".equals(incumbentEmployeeOrganizationName.trim())) {
                            incumbentEmployeeOrganizationName += ", ";
                        }
                        if (!"".equals(incumbentEmployeeOrganizationAddressBlock.trim())) {
                            incumbentEmployeeOrganizationAddressBlock += "\n\n";
                        }
                        incumbentEmployeeOrganizationAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        incumbentEmployeeOrganizationName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "Incumbent Employee Organization REP":
                        if (!"".equals(incumbentEmployeeOrganizationRepName.trim())) {
                            incumbentEmployeeOrganizationRepName += ", ";
                        }
                        if (!"".equals(incumbentEmployeeOrganizationRepAddressBlock.trim())) {
                            incumbentEmployeeOrganizationRepAddressBlock += "\n\n";
                        }
                        incumbentEmployeeOrganizationRepAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        incumbentEmployeeOrganizationRepName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "Employer":
                        if (!"".equals(employerName.trim())) {
                            employerName += ", ";
                        }
                        if (!"".equals(employerAddressBlock.trim())) {
                            employerAddressBlock += "\n\n";
                        }
                        employerAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        employerName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "Employer REP":
                        if (!"".equals(employerRepName.trim())) {
                            employerRepName += ", ";
                        }
                        if (!"".equals(employerRepAddressBlock.trim())) {
                            employerRepAddressBlock += "\n\n";
                        }
                        employerRepAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        employerRepName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "Rival Employee Organization":
                        if (!"".equals(rivalEmployeeOrganizationName.trim())) {
                            rivalEmployeeOrganizationName += ", ";
                        }
                        if (!"".equals(rivalEmployeeOrganizationAddressBlock.trim())) {
                            rivalEmployeeOrganizationAddressBlock += "\n\n";
                        }
                        rivalEmployeeOrganizationAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        rivalEmployeeOrganizationName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "Rival Employee Organization REP":
                        if (!"".equals(rivalEmployeeOrganizationRepName.trim())) {
                            rivalEmployeeOrganizationRepName += ", ";
                        }
                        if (!"".equals(rivalEmployeeOrganizationRepAddressBlock.trim())) {
                            rivalEmployeeOrganizationRepAddressBlock += "\n\n";
                        }
                        rivalEmployeeOrganizationRepAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        rivalEmployeeOrganizationRepName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "Employee Organization":
                        if (!"".equals(employeeOrganizationName.trim())) {
                            employeeOrganizationName += ", ";
                        }
                        if (!"".equals(employeeOrganizationAddressBlock.trim())) {
                            employeeOrganizationAddressBlock += "\n\n";
                        }
                        employeeOrganizationAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        employeeOrganizationName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "Employee Organization REP":
                        if (!"".equals(employeeOrganizationRepName.trim())) {
                            employeeOrganizationRepName += ", ";
                        }
                        if (!"".equals(employeeOrganizationRepAddressBlock.trim())) {
                            employeeOrganizationRepAddressBlock += "\n\n";
                        }
                        employeeOrganizationRepAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        employeeOrganizationRepName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "Petitioner":
                        if (!"".equals(petitionerName.trim())) {
                            petitionerName += ", ";
                        }
                        if (!"".equals(petitionerAddressBlock.trim())) {
                            petitionerAddressBlock += "\n\n";
                        }
                        petitionerAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        petitionerName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "Petitioner REP":
                        if (!"".equals(petitionerRepName.trim())) {
                            petitionerRepName += ", ";
                        }
                        if (!"".equals(petitionerRepAddressBlock.trim())) {
                            petitionerRepAddressBlock += "\n\n";
                        }
                        petitionerRepAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        petitionerRepName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    default:
                        if (party.caseRelation.startsWith("Rival Employee Organization") && !party.caseRelation.endsWith("REP")) {
                            if (!"".equals(rivalEmployeeOrganizationName.trim())) {
                                rivalEmployeeOrganizationName += ", ";
                            }
                            if (!"".equals(rivalEmployeeOrganizationAddressBlock.trim())) {
                                rivalEmployeeOrganizationAddressBlock += "\n\n";
                            }
                            rivalEmployeeOrganizationAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                            rivalEmployeeOrganizationName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        } else if (party.caseRelation.startsWith("Rival Employee Organization") && party.caseRelation.endsWith("REP")) {
                            if (!"".equals(rivalEmployeeOrganizationRepName.trim())) {
                                rivalEmployeeOrganizationRepName += ", ";
                            }
                            if (!"".equals(rivalEmployeeOrganizationRepAddressBlock.trim())) {
                                rivalEmployeeOrganizationRepAddressBlock += "\n\n";
                            }
                            rivalEmployeeOrganizationRepAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                            rivalEmployeeOrganizationRepName += StringUtilities.buildCasePartyNameNoPreFix(party);
                        }
                        break;
                }
            }
        }

        //get mediation informations (Internal Mediation, 30 day mediation, post directive mediation)
        for (REPMediation mediation : mediationList) {
            if (!"".equals(mediation.mediationDate.trim())) {
                Timestamp mediationDateTime = null;
                try {
                    mediationDateTime = new Timestamp(Global.mmddyyyyhhmma.parse(mediation.mediationDate).getTime());
                } catch (ParseException ex) {
                    SlackNotification.sendNotification(ex);
                }
                mediationDate = Global.MMMMMdyyyy.format(mediationDateTime);
                mediationTime = Global.hhmma.format(mediationDateTime);
            }
            mediationCC = mediation.mediator;
            break;
        }

        //Related Cases
        if (relatedCasesList.size() == 2) {
            for (String relatedCase : relatedCasesList) {
                relatedCases += ("".equals(relatedCases) ? relatedCase : " and " + relatedCase);
            }
        } else {
            int i = 0;
            for (String relatedCase : relatedCasesList) {
                i++;
                if (i == relatedCasesList.size()) {
                    relatedCases += ("".equals(relatedCases) ? relatedCase : "; and " + relatedCase);
                } else {
                    relatedCases += ("".equals(relatedCases) ? relatedCase : "; " + relatedCase);
                }
            }
        }

        //Polling information
        if (caseInfo.pollingEndDate != null && caseInfo.pollingStartDate != null) {
                polling = Global.MMMMMdyyyy.format(caseInfo.pollingStartDate);
                polling += " through " + Global.MMMMMdyyyy.format(caseInfo.pollingEndDate);
            }

        //MultiCase Election
        for (Object relatedCase : multicaseElection) {
            if (!multiCaseElection.trim().equals("")){
                multiCaseElection += ", ";
            }
            multiCaseElection += relatedCase.toString();
        }

        //BoardMeeting
        if (meeting.boardMeetingDate == null ? false : !meeting.boardMeetingDate.equals("")){
            try {
                longMeetDate = Global.MMMMMdyyyy.format(Global.mmddyyyy.parse(meeting.boardMeetingDate));
                        } catch (ParseException ex) {
                Logger.getLogger(processREPbookmarks.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        //ProcessBookmarks
        for (int i = 0; i < Global.BOOKMARK_LIMIT; i++) {
            //Case Number Related Information
            processBookmark.process("CASENUMBER" + (i == 0 ? "" : i), NumberFormatService.generateFullCaseNumber(), Document);
            processBookmark.process("DATEFILED" + (i == 0 ? "" : i), (caseInfo.fileDate == null ? "" : Global.MMMMddyyyy.format(caseInfo.fileDate)), Document);
            processBookmark.process("BALLOTONE" + (i == 0 ? "" : i), caseInfo.ballotOne, Document);
            processBookmark.process("BALLOTTWO" + (i == 0 ? "" : i), caseInfo.ballotTwo, Document);
            processBookmark.process("BALLOTTHREE" + (i == 0 ? "" : i), caseInfo.ballotThree, Document);
            processBookmark.process("BALLOTFOUR" + (i == 0 ? "" : i), caseInfo.ballotFour, Document);
            processBookmark.process("BallotsCountDate" + (i == 0 ? "" : i), caseInfo.ballotsCountDate == null ? "" : Global.mmddyyyy.format(caseInfo.ballotsCountDate), Document);
            processBookmark.process("BallotsCountTime" + (i == 0 ? "" : i), caseInfo.ballotsCountTime == null ? "" : Global.hmma.format(caseInfo.ballotsCountTime), Document);
            processBookmark.process("ELIGIBILITYDATE" + (i == 0 ? "" : i), (caseInfo.eligibilityDate == null ? "" : Global.MMMMddyyyy.format(caseInfo.eligibilityDate)), Document);
            processBookmark.process("EXCLUDEDNEWORCURRENTUNIT" + (i == 0 ? "" : i), caseInfo.bargainingUnitExcluded, Document);
            processBookmark.process("PIN" + (i == 0 ? "" : i), caseInfo.professionalIncluded, Document);
            processBookmark.process("PEX" + (i == 0 ? "" : i), caseInfo.professionalExcluded, Document);
            processBookmark.process("OIN" + (i == 0 ? "" : i), caseInfo.optInIncluded, Document);
            processBookmark.process("NonProfessional_Included" + (i == 0 ? "" : i), caseInfo.nonProfessionalIncluded, Document);
            processBookmark.process("INCLUDEDNEWORCURRENTUNIT" + (i == 0 ? "" : i), caseInfo.bargainingUnitIncluded, Document);
            processBookmark.process("REFLECT" + (i == 0 ? "" : i), caseInfo.toReflect, Document);
            processBookmark.process("MULTICASEELECTIONCASE2" + (i == 0 ? "" : i), multiCaseElection, Document);
            processBookmark.process("MULTICASEELECTIONCASE3" + (i == 0 ? "" : i), multiCaseElection, Document);
            processBookmark.process("WHOFILED" + (i == 0 ? "" : i), caseInfo.fileBy, Document);
            processBookmark.process("BDMEETDATE" + (i == 0 ? "" : i), meeting.boardMeetingDate, Document);
            processBookmark.process("LONGMEETDATE" + (i == 0 ? "" : i), longMeetDate, Document);
            processBookmark.process("EMPLOYEEORGANIZATIONNAMECHANGEFROM" + (i == 0 ? "" : i), caseInfo.EEONameChangeFrom, Document);
            processBookmark.process("EMPLOYERNAMECHANGEFROM" + (i == 0 ? "" : i), caseInfo.ERNameChangeFrom, Document);
            processBookmark.process("SECONDCASENUMBER" + (i == 0 ? "" : i), multiCaseElection, Document);
            processBookmark.process("BOARDORDEEMED" + (i == 0 ? "" : i), caseInfo.boardCertified ? "Board" : "Deemed", Document);
            processBookmark.process("INCLUDEDPROPOSEDFORAMENDMENTSOROPT" + (i == 0 ? "" : i), caseInfo.optInIncluded, Document);
            processBookmark.process("INCLUDEDNEWORCURRENTUNIT" + (i == 0 ? "" : i), caseInfo.bargainingUnitIncluded, Document);
            processBookmark.process("EXCLUDEDPROPOSEDFORAMENDMENTSOROPT", caseInfo.bargainingUnitExcluded, Document);
            processBookmark.process("EXCLUDEDNEWORCURRENTUNIT" + (i == 0 ? "" : i), caseInfo.bargainingUnitExcluded, Document);
            processBookmark.process("CASE2INCLUDED" + (i == 0 ? "" : i), caseInfo.bargainingUnitIncluded, Document);
            processBookmark.process("CASE2EXCLUDED" + (i == 0 ? "" : i), caseInfo.bargainingUnitExcluded, Document);
            processBookmark.process("CASE3INCLUDED" + (i == 0 ? "" : i), caseInfo.bargainingUnitIncluded, Document);
            processBookmark.process("CASE3EXCLUDED" + (i == 0 ? "" : i), caseInfo.bargainingUnitExcluded, Document);
            processBookmark.process("POLLINGPERIOD" + (i == 0 ? "" : i), polling.trim(), Document);
            processBookmark.process("WHOPREVAILED" + (i == 0 ? "" : i), caseInfo.resultWHoPrevailed, Document);

            if (caseInfo.type != null) {
                if (caseInfo.type.equals("AC")) {
                    processBookmark.process("CASETYPE" + (i == 0 ? "" : i), "PETITION FOR REPRESENTATION", Document);
                } else if (caseInfo.type.equals("RC")) {
                    processBookmark.process("CASETYPE" + (i == 0 ? "" : i), "PETITION FOR AMENDMENT OF CERTIFICATION", Document);
                }
            }

            processBookmark.process("RIVALVOTES" + (i == 0 ? "" : i),
                        caseInfo.resultVotesCastForRivalEEO1 == null ? "0"
                                : NumberFormatService.convert(Long.parseLong(caseInfo.resultVotesCastForRivalEEO1)) + "(" + caseInfo.resultVotesCastForRivalEEO1 + ")", Document);

            processBookmark.process("RIVALVOTES2" + (i == 0 ? "" : i),
                        caseInfo.resultVotesCastForRivalEEO2 == null ? "0"
                                : NumberFormatService.convert(Long.parseLong(caseInfo.resultVotesCastForRivalEEO2)) + "(" + caseInfo.resultVotesCastForRivalEEO2 + ")", Document);

            processBookmark.process("RIVALVOTES3" + (i == 0 ? "" : i),
                        caseInfo.resultVotesCastForRivalEEO3 == null ? "0"
                                : NumberFormatService.convert(Long.parseLong(caseInfo.resultVotesCastForRivalEEO3)) + "(" + caseInfo.resultVotesCastForRivalEEO3 + ")", Document);

            processBookmark.process("EOVOTES" + (i == 0 ? "" : i),
                    caseInfo.resultVotesCastForEEO == null ? "0"
                            : NumberFormatService.convert(Long.parseLong(caseInfo.resultVotesCastForEEO)) + "(" + caseInfo.resultVotesCastForEEO + ")", Document);

            processBookmark.process("NOREPVOTES" + (i == 0 ? "" : i),
                    caseInfo.resultVotesCastForNoRepresentative == null ? "0"
                            : NumberFormatService.convert(Long.parseLong(caseInfo.resultVotesCastForNoRepresentative)) + "(" + caseInfo.resultVotesCastForNoRepresentative + ")", Document);

            processBookmark.process("VOIDBALLOTS" + (i == 0 ? "" : i),
                    caseInfo.resultVoidBallots == null ? "0"
                            : NumberFormatService.convert(Long.parseLong(caseInfo.resultVoidBallots)) + "(" + caseInfo.resultVoidBallots + ")", Document);

            processBookmark.process("LINE7" + (i == 0 ? "" : i),
                    caseInfo.resultChallengedBallots == null ? "0"
                            : NumberFormatService.convert(Long.parseLong(caseInfo.resultChallengedBallots)) + "(" + caseInfo.resultChallengedBallots + ")", Document);

            processBookmark.process("LINE6" + (i == 0 ? "" : i),
                    caseInfo.resultValidVotesCounted == null ? "0"
                            : NumberFormatService.convert(Long.parseLong(caseInfo.resultValidVotesCounted)) + "(" + caseInfo.resultValidVotesCounted + ")", Document);

            processBookmark.process("INCUMBENTVOTES" + (i == 0 ? "" : i),
                    caseInfo.resultVotesCastForIncumbentEEO == null ? "0"
                            : NumberFormatService.convert(Long.parseLong(caseInfo.resultVotesCastForIncumbentEEO)) + "(" + caseInfo.resultVotesCastForIncumbentEEO + ")", Document);

            //Party Information
            processBookmark.process("EMPLOYEEORG" + (i == 0 ? "" : i), employeeOrganizationName.trim(), Document);
            processBookmark.process("EMPLOYER" + (i == 0 ? "" : i), employerName.trim(), Document);
            processBookmark.process("EMPLOYERREP" + (i == 0 ? "" : i), employerRepName.trim(), Document);
            processBookmark.process("EMPLOYERSAL" + (i == 0 ? "" : i), employerRepName.trim(), Document);
            processBookmark.process("INCUMBENT" + (i == 0 ? "" : i), incumbentEmployeeOrganizationName.trim(), Document);
            processBookmark.process("INCUMBENTREP" + (i == 0 ? "" : i), incumbentEmployeeOrganizationRepName.trim(), Document);
            processBookmark.process("INCUMBENTREPSAL" + (i == 0 ? "" : i), incumbentEmployeeOrganizationRepName.trim(), Document);
            processBookmark.process("EMPLOYEEORGREP" + (i == 0 ? "" : i), employeeOrganizationRepName.trim(), Document);
            processBookmark.process("EMPLOYEEORGREPSAL" + (i == 0 ? "" : i), employeeOrganizationRepName.trim(), Document);
            processBookmark.process("RIVAL" + (i == 0 ? "" : i), rivalEmployeeOrganizationName.trim(), Document);
            processBookmark.process("RIVALREP" + (i == 0 ? "" : i), rivalEmployeeOrganizationRepName.trim(), Document);
            processBookmark.process("RIVALREPSAL" + (i == 0 ? "" : i), rivalEmployeeOrganizationRepName.trim(), Document);
            processBookmark.process("PETINTERSAL" + (i == 0 ? "" : i), petitionerName.trim(), Document);
            processBookmark.process("PETITIONERINTERVENOR" + (i == 0 ? "" : i), petitionerName.trim(), Document);
            processBookmark.process("PETINTERREP" + (i == 0 ? "" : i), petitionerName.trim(), Document);
            processBookmark.process("INCUMBENTADDRESSBLOCK" + (i == 0 ? "" : i), incumbentEmployeeOrganizationAddressBlock, Document);
            processBookmark.process("INCUMBENTREPADDRESSBLOCK" + (i == 0 ? "" : i), incumbentEmployeeOrganizationRepAddressBlock, Document);
            processBookmark.process("EMPLOYERADDRESSBLOCK" + (i == 0 ? "" : i), employerAddressBlock, Document);
            processBookmark.process("EMPLOYERREPADDRESSBLOCK" + (i == 0 ? "" : i), employerRepAddressBlock, Document);
            processBookmark.process("RIVALADDRESSBLOCK" + (i == 0 ? "" : i), rivalEmployeeOrganizationAddressBlock, Document);
            processBookmark.process("RIVALREPADDRESSBLOCK" + (i == 0 ? "" : i), rivalEmployeeOrganizationRepAddressBlock, Document);
            processBookmark.process("EMPLOYEEORGADDRESSBLOCK" + (i == 0 ? "" : i), employeeOrganizationAddressBlock, Document);
            processBookmark.process("EMPLOYEEORGREPADDRESSBLOCK" + (i == 0 ? "" : i), employeeOrganizationRepAddressBlock, Document);
            processBookmark.process("PETITIONERADDRESSBLOCK" + (i == 0 ? "" : i), petitionerAddressBlock, Document);
            processBookmark.process("PETITIONERREPADDRESSBLOCK" + (i == 0 ? "" : i), petitionerRepAddressBlock, Document);
            processBookmark.process("IEOCONTACT" + (i == 0 ? "" : i),
                    (!"".equals(incumbentEmployeeOrganizationRepName) ? incumbentEmployeeOrganizationRepName.trim() : incumbentEmployeeOrganizationName.trim()), Document);
            processBookmark.process("EMPCONTACT" + (i == 0 ? "" : i),
                    (!"".equals(employerRepName) ? employerRepName.trim() : employerName.trim()), Document);
            processBookmark.process("REOCONTACT" + (i == 0 ? "" : i),
                    (!"".equals(rivalEmployeeOrganizationRepName) ? rivalEmployeeOrganizationRepName.trim() : rivalEmployeeOrganizationName.trim()), Document);
            processBookmark.process("EOCONTACT" + (i == 0 ? "" : i),
                    (!"".equals(employeeOrganizationRepName) ? employeeOrganizationRepName.trim() : employeeOrganizationName.trim()), Document);
            processBookmark.process("CCList" + (i == 0 ? "" : i), ccNameBlock, Document);
            processBookmark.process("DIRECCList" + (i == 0 ? "" : i), DIRECCBlock, Document);
            processBookmark.process("REPCCList" + (i == 0 ? "" : i), DIRECCBlock, Document);

            //Latest Mediation
            processBookmark.process("MEDIATEDAY" + (i == 0 ? "" : i), "", Document);
            processBookmark.process("MEDIATIONTIME" + (i == 0 ? "" : i), mediationTime, Document);
            processBookmark.process("MEDIATIONDATE" + (i == 0 ? "" : i), mediationDate, Document);
            processBookmark.process("CC" + (i == 0 ? "" : i), mediationCC, Document);

            //LRS Worker (Passed from Sender ComboBox)
            if (user != null) {
                processBookmark.process("NAME" + (i == 0 ? "" : i), StringUtilities.buildFullName(user.firstName, user.middleInitial, user.lastName), Document);   //not yet setup in the Database
                processBookmark.process("TITLE" + (i == 0 ? "" : i), user.jobTitle, Document); //not yet setup in the Database
                processBookmark.process("PHONE" + (i == 0 ? "" : i), user.workPhone, Document);
                processBookmark.process("EMAIL" + (i == 0 ? "" : i), user.emailAddress, Document);
            }
        }
        return Document;
    }

    public static Dispatch processDoAREPAgenda(Dispatch Document, Date boardMeetingDate) {
        //get basic information
        List<REPCase> caseList = REPCase.loadCaseDetailsForAgenda(boardMeetingDate);

        //ProcessBookmarks
        for (int i = 0; i < caseList.size(); i++) {
            REPCase item = caseList.get(i);
            List<CaseParty> partyList = CaseParty.loadPartiesByCase(item.caseYear, item.caseType, item.caseMonth, item.caseNumber);

            String information = "";
            String pollDates = "";
            String results = "";
            String incumbentEmployeeOrganizationName = "";    //IEO
            String employerName = "";                         //E
            String rivalEmployeeOrganizationName = "";        //REO
            String rivalEmployeeOrganization2Name = "";       //REO2
            String rivalEmployeeOrganization3Name = "";       //REO3
            String employeeOrganizationName = "";             //EO
            String petitionerName = "";                       //P
            String ConversionSchoolName = "";                 //CS

            for (CaseParty party : partyList) {
                if (null != party.caseRelation) {
                    switch (party.caseRelation) {
                        case "Incumbent Employee Organization":
                            if (!"".equals(incumbentEmployeeOrganizationName.trim())) {
                                incumbentEmployeeOrganizationName += ", ";
                            }
                            incumbentEmployeeOrganizationName += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Employer":
                            if (!"".equals(employerName.trim())) {
                                employerName += ", ";
                            }
                            employerName += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Rival Employee Organization":
                            if (!"".equals(rivalEmployeeOrganizationName.trim())) {
                                rivalEmployeeOrganizationName += ", ";
                            }
                            rivalEmployeeOrganizationName += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Rival Employee Organization 2":
                            if (!"".equals(rivalEmployeeOrganization2Name.trim())) {
                                rivalEmployeeOrganization2Name += ", ";
                            }
                            rivalEmployeeOrganization2Name += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Rival Employee Organization 3":
                            if (!"".equals(rivalEmployeeOrganization3Name.trim())) {
                                rivalEmployeeOrganization3Name += ", ";
                            }
                            rivalEmployeeOrganization3Name += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Employee Organization":
                            if (!"".equals(employeeOrganizationName.trim())) {
                                employeeOrganizationName += ", ";
                            }
                            employeeOrganizationName += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Petitioner":
                            if (!"".equals(petitionerName.trim())) {
                                petitionerName += ", ";
                            }
                            petitionerName += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Conversion School":
                            if (!"".equals(ConversionSchoolName.trim())) {
                                ConversionSchoolName += ", ";
                            }
                            ConversionSchoolName += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        default:
                            if (party.caseRelation.startsWith("Rival Employee Organization") && !party.caseRelation.endsWith("REP")) {
                                if (!"".equals(rivalEmployeeOrganizationName.trim())) {
                                    rivalEmployeeOrganizationName += ", ";
                                }
                                rivalEmployeeOrganizationName += StringUtilities.buildCasePartyNameNoPreFix(party);
                            }
                            break;
                    }
                }
            }

            information = petitionerName
                    + " and " + employeeOrganizationName
                    + " and " + rivalEmployeeOrganizationName
                    + " and " + incumbentEmployeeOrganizationName
                    + " and " + ConversionSchoolName
                    + " and " + employerName;

            if (item.pollingStartDate != null && item.pollingEndDate != null) {
                pollDates = Global.MMMMMdyyyy.format(item.pollingStartDate);
                pollDates = "(" + pollDates + " - " + Global.MMMMMdyyyy.format(item.pollingEndDate) + ")";
            }

            if (item.resultApproxNumberEligibleVotes != null) {

                long ResultsTotalVotesCast = Long.parseLong(item.resultTotalBallotsCast.trim());
                long ResultsVoidBallots = Long.parseLong(item.resultVoidBallots.trim());
                long ResultsChallenged = 0;
                long ResultsVotesForNoREP = 0;
                long ResultsVotesForEEO = 0;
                long ResultsVotesForRival = 0;
                long ResultsVotesForRival2 = 0;
                long ResultsVotesForRival3 = 0;
                if (item.resultChallengedBallots != null) {
                    ResultsChallenged = Long.parseLong(item.resultChallengedBallots.trim());
                }
                if (item.resultVotesCastForNoRepresentative != null) {
                    ResultsVotesForNoREP = Long.parseLong(item.resultVotesCastForNoRepresentative.trim());
                }
                if (item.resultVotesCastForEEO != null) {
                    ResultsVotesForEEO = Long.parseLong(item.resultVotesCastForEEO.trim());
                }
                if (item.resultVotesCastForRivalEEO1 != null) {
                    ResultsVotesForRival = Long.parseLong(item.resultVotesCastForRivalEEO1.trim());
                }
                if (item.resultVotesCastForRivalEEO2 != null) {
                    ResultsVotesForRival2 = Long.parseLong(item.resultVotesCastForRivalEEO2.trim());
                }
                if (item.resultVotesCastForRivalEEO3 != null) {
                    ResultsVotesForRival3 = Long.parseLong(item.resultVotesCastForRivalEEO3.trim());
                }

                results += "\n-   There were " + NumberFormatService.convert(ResultsTotalVotesCast) + "(" + item.resultTotalBallotsCast + ")" + " valid ballots cast\n";
                results += "-   There were " + NumberFormatService.convert(ResultsVoidBallots) + "(" + item.resultVoidBallots.trim() + ")" + " void ballots\n";
                results += "-   There were " + NumberFormatService.convert(ResultsChallenged) + "(" + item.resultChallengedBallots.trim() + ")" + " challenged ballots\n";
                results += "-   No Representative received " + NumberFormatService.convert(ResultsVotesForNoREP) + "(" + item.resultVotesCastForNoRepresentative.trim() + ")" + " votes\n";
                results += "-   " + employeeOrganizationName.trim() + " received " + NumberFormatService.convert(ResultsVotesForEEO) + "(" + item.resultVotesCastForEEO.trim() + ") votes\n";
                if (item.resultVotesCastForRivalEEO1 != null) {
                    results += "-   " + rivalEmployeeOrganizationName.trim() + " received " + NumberFormatService.convert(ResultsVotesForRival) + "(" + item.resultVotesCastForRivalEEO1.trim() + ") votes\n";
                }
                if (!rivalEmployeeOrganization2Name.equals("")) {
                    results += "-   " + rivalEmployeeOrganization2Name.trim() + " received " + NumberFormatService.convert(ResultsVotesForRival2) + "(" + item.resultVotesCastForRivalEEO2.trim() + ") votes\n";
                }
                if (!rivalEmployeeOrganization3Name.equals("")) {
                    results += "-   " + rivalEmployeeOrganization3Name.trim() + " received " + NumberFormatService.convert(ResultsVotesForRival3) + "(" + item.resultVotesCastForRivalEEO3.trim() + ") votes\n";
                }
                results += "-   " + item.resultWHoPrevailed.trim() + " prevailed in this election\n";

            }

            String temp = item.boardStatusBlurb == null ? "" : item.boardStatusBlurb.trim();
            //+ "\n\nIt is respectfully recommended: " + item.BoardRec.trim();

            processBookmark.process("POLLINGDATES" + (i + 1), pollDates.trim(), Document);
            processBookmark.process("AI" + (i + 1), item.agendaItemNumber.trim() + ".", Document);
            processBookmark.process("CASENUMBER" + (i + 1), "Case  " + NumberFormatService.generateFullCaseNumberNonGlobal(item.caseYear, item.caseType, item.caseMonth, item.caseNumber), Document);
            processBookmark.process("INFORMATION" + (i + 1), information.trim(), Document);
            processBookmark.process("DATE", Global.MMMMMdyyyy.format(boardMeetingDate), Document);
            processBookmark.process("DATE2", Global.MMMMMdyyyy.format(boardMeetingDate), Document);
            processBookmark.process("RESULTS" + (i + 1), results, Document);
            processBookmark.process("RECOMMENDATION" + (i + 1), "Recommendation: " + item.boardMeetingRecommendation.trim(), Document);
            processBookmark.process("BLURBRECOMMENDATION" + (i + 1), temp, Document);
        }

        //Blank out the rest of the rows
        for (int i = caseList.size(); i < 50; i++) {
            processBookmark.process("AI" + (i + 1), "", Document);
            processBookmark.process("CASENUMBER" + (i + 1), "", Document);
            processBookmark.process("INFORMATION" + (i + 1), "", Document);
            processBookmark.process("POLLINGDATES" + (i + 1), "", Document);
            processBookmark.process("RESULTS" + (i + 1), "", Document);
            processBookmark.process("RECOMMENDATION" + (i + 1), "", Document);
            processBookmark.process("BLURBRECOMMENDATION" + (i + 1), "", Document);
        }
        return Document;
    }

}
