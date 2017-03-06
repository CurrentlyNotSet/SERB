/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.com.Dispatch;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.BoardMeeting;
import parker.serb.sql.CaseParty;
import parker.serb.sql.RelatedCase;
import parker.serb.sql.ULPCase;
import parker.serb.sql.User;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;
import parker.serb.util.StringUtilities;

/**
 *
 * @author Andrew
 */
public class processULPbookmarks {

    public static Dispatch processDoAULPWordLetter(Dispatch Document, List<Integer> toParties, List<Integer> ccParties) {
        //get basic information
        List<User> userList = User.loadAllUsers();
        ULPCase item = ULPCase.loadULPCaseDetails(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);
        List<String> relatedCasesList = RelatedCase.loadRelatedCases();
        List<BoardMeeting> boardMeetingList = BoardMeeting.loadULPBoardMeetings();

        String investigatorFullName = "";
        String investigatorPhone = "";
        String mediatorName = "";
        String relatedCases = "";
        String boardMeetingDate = "";
        String boardMeetingAgendaItem = "";
        String chargingPartyNames = "";
        String chargingPartyREPNames = "";
        String chargedPartyNames = "";
        String chargedPartyREPNames = "";
        String chargingPartyAddressBlock = "";
        String chargingPartyREPAddressBlock = "";
        String chargedPartyAddressBlock = "";
        String chargedPartyREPAddressBlock = "";
        String toAddressBlock = "";
        String ccNameBlock = "";
        String DIRECCBlock = "";


        for (CaseParty party : partyList){

            if (!DIRECCBlock.trim().equals("")){
                DIRECCBlock += "\n";
            }
            DIRECCBlock += StringUtilities.buildCasePartyNameNoPreFix(party);
            DIRECCBlock += party.emailAddress == null ? "" : ", " + party.emailAddress.trim();

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

            if (null != party.caseRelation)switch (party.caseRelation) {
                case "Charging Party":
                    if (!"".equals(chargingPartyNames.trim())){
                        chargingPartyNames += ", ";
                    }
                    if (!"".equals(chargingPartyAddressBlock.trim())){
                        chargingPartyAddressBlock += "\n\n";
                    }
                    chargingPartyAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                    chargingPartyNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                    break;
                case "Charging Party REP":
                    if (!"".equals(chargingPartyREPNames.trim())){
                        chargingPartyREPNames += ", ";
                    }
                    if (!"".equals(chargingPartyREPAddressBlock.trim())){
                        chargingPartyREPAddressBlock += "\n\n";
                    }
                    chargingPartyREPAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                    chargingPartyREPNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                    break;
                case "Charged Party":
                    if (!"".equals(chargedPartyNames.trim())){
                        chargedPartyNames += ", ";
                    }
                    if (!"".equals(chargedPartyAddressBlock.trim())){
                        chargedPartyAddressBlock += "\n\n";
                    }
                    chargedPartyAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                    chargedPartyNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                    break;
                case "Charged Party REP":
                    if (!"".equals(chargedPartyREPNames.trim())){
                        chargedPartyREPNames += ", ";
                    }
                    if (!"".equals(chargedPartyREPAddressBlock.trim())){
                        chargedPartyREPAddressBlock += "\n\n";
                    }
                    chargedPartyREPAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                    chargedPartyREPNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                    break;
            }
        }

        for (User user : userList){
            if (user.id == item.investigatorID){
                investigatorFullName = StringUtilities.buildFullName(user.firstName, "", user.lastName);
                investigatorPhone = user.workPhone;
            }
            if (user.id == item.mediatorAssignedID){
                mediatorName = StringUtilities.buildFullName(user.firstName, "", user.lastName);
            }
        }

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

        for (String relatedCase : relatedCasesList){
            if ("".equals(relatedCases)){
                relatedCases = ", " + relatedCase;
            } else {
                relatedCases = relatedCase;
            }
        }

        for (BoardMeeting bm: boardMeetingList){
            try {
                boardMeetingDate = Global.MMMMddyyyy.format(Global.mmddyyyy.parse(bm.boardMeetingDate));
            } catch (ParseException ex) {
                SlackNotification.sendNotification(ex);
            }
            boardMeetingAgendaItem = bm.agendaItemNumber;
            break;
        }


        //ProcessBookmarks
        for (int i = 0; i < Global.BOOKMARK_LIMIT; i++) {
            //CaseNumber Related Information
            processBookmark.process("STATEMENT" + (i == 0 ? "" : i), item.statement, Document);
            processBookmark.process("RECOMMENDATION" + (i == 0 ? "" : i), item.recommendation, Document);
            processBookmark.process("ALLEGATION" + (i == 0 ? "" : i), item.allegation, Document);
            processBookmark.process("INVEST" + (i == 0 ? "" : i), item.investigationReveals, Document);
            processBookmark.process("CASENUMBER" + (i == 0 ? "" : i), NumberFormatService.generateFullCaseNumber(), Document);
            processBookmark.process("FILEDATE" + (i == 0 ? "" : i), (item.fileDate == null ? "" : Global.MMMMddyyyy.format(item.fileDate)), Document);
            processBookmark.process("STATUS" + (i == 0 ? "" : i), item.currentStatus, Document);
            processBookmark.process("EMPLOYERNUMBER" + (i == 0 ? "" : i), item.employerIDNumber, Document);
            processBookmark.process("BARGININGUNITNUMBER" + (i == 0 ? "" : i), item.barginingUnitNo, Document);
            processBookmark.process("PRIORITY" + (i == 0 ? "" : i), (item.priority == false ? "No" : "Yes"), Document);
            processBookmark.process("ASSIGNEDDATE" + (i == 0 ? "" : i), (item.assignedDate == null ? "" : Global.MMMMddyyyy.format(item.assignedDate)), Document);
            processBookmark.process("TURNINDATE" + (i == 0 ? "" : i), (item.turnInDate == null ? "" : Global.MMMMddyyyy.format(item.turnInDate)), Document);
            processBookmark.process("REPORTDATE" + (i == 0 ? "" : i), (item.reportDueDate == null ? "" : Global.MMMMddyyyy.format(item.reportDueDate)), Document);
            processBookmark.process("PROBABLE" + (i == 0 ? "" : i), (item.probableCause == false ? "No" : "Yes"), Document);
            processBookmark.process("FINALDISPOSTION" + (i == 0 ? "" : i), item.finalDispositionStatus, Document);
            processBookmark.process("DISMISSALBOARDMEETINGDATE" + (i == 0 ? "" : i), (item.dismissalDate == null ? "" : Global.MMMMddyyyy.format(item.dismissalDate)), Document);
            processBookmark.process("DEFERREDBOARDMEETINGDATE" + (i == 0 ? "" : i), (item.deferredDate == null ? "" : Global.MMMMddyyyy.format(item.deferredDate)), Document);
            processBookmark.process("LONGFILEDATE" + (i == 0 ? "" : i), (item.fileDate == null ? "" : Global.MMMMMdyyyy.format(item.fileDate)), Document);
            processBookmark.process("LONGDEFERREDBOARDMEETINGDATE" + (i == 0 ? "" : i), (item.deferredDate == null ? "" : Global.MMMMMdyyyy.format(item.deferredDate)), Document);
            processBookmark.process("LONGDEFFEREDMEETINGDATE" + (i == 0 ? "" : i), (item.deferredDate == null ? "" : Global.MMMMMdyyyy.format(item.deferredDate)), Document);
            processBookmark.process("LONGDISMISSALBOARDMEETINGDATE" + (i == 0 ? "" : i), (item.dismissalDate == null ? "" : Global.MMMMMdyyyy.format(item.dismissalDate)), Document);
            processBookmark.process("LRSNAME" + (i == 0 ? "" : i), investigatorFullName, Document);
            processBookmark.process("LRSPHONE" + (i == 0 ? "" : i), investigatorPhone, Document);
            processBookmark.process("MEDIATOR" + (i == 0 ? "" : i), mediatorName, Document);
            processBookmark.process("RELATEDCASES" + (i == 0 ? "" : i), relatedCases, Document);

            //Parties List
            processBookmark.process("CPNAME" + (i == 0 ? "" : i), chargingPartyNames, Document);
            processBookmark.process("CPADDRESSBLOCK" + (i == 0 ? "" : i), chargingPartyAddressBlock, Document);
            processBookmark.process("CPREPNAME" + (i == 0 ? "" : i), chargingPartyREPNames, Document);
            processBookmark.process("CPREPADDRESSBLOCK" + (i == 0 ? "" : i), chargingPartyREPAddressBlock, Document);
            processBookmark.process("CHDNAME" + (i == 0 ? "" : i), chargedPartyNames, Document);
            processBookmark.process("CHDADDRESSBLOCK" + (i == 0 ? "" : i), chargedPartyAddressBlock, Document);
            processBookmark.process("CHDREPNAME" + (i == 0 ? "" : i), chargedPartyREPNames, Document);
            processBookmark.process("CHDREPADDRESSBLOCK" + (i == 0 ? "" : i), chargedPartyREPAddressBlock, Document);
            processBookmark.process("CCList" + (i == 0 ? "" : i), ccNameBlock, Document);
            processBookmark.process("DIRECCList" + (i == 0 ? "" : i), DIRECCBlock, Document);

            //Latest (by date) board meeting
            processBookmark.process("LONGMEETINGDATE" + (i == 0 ? "" : i), ("".equals(boardMeetingDate) ? "" : boardMeetingDate), Document);
            processBookmark.process("BOARDMEETINGDATE" + (i == 0 ? "" : i), ("".equals(boardMeetingDate) ? "" : boardMeetingDate), Document);
            processBookmark.process("AGENDAITEM" + (i == 0 ? "" : i), boardMeetingAgendaItem, Document);
        }

        return Document;
    }

    public static Dispatch processDoAULPAgenda(Dispatch Document, Date boardMeetingDate) {
        //get basic information
        List<ULPCase> caseList = ULPCase.loadULPCaseDetailsForAgendas(boardMeetingDate);

        //ProcessBookmarks
        for (int i = 0; i < caseList.size(); i++) {
            ULPCase item = caseList.get(i);
            List<CaseParty> partyList = CaseParty.loadPartiesByCase(item.caseYear, item.caseType, item.caseMonth, item.caseNumber);

            String chargingPartyNames = "";
            String chargingPartyREPNames = "";
            String chargedPartyNames = "";
            String chargedPartyREPNames = "";
            String chargingPartyEmails = "";
            String chargingPartyREPEmails = "";
            String chargedPartyEmails = "";
            String chargedPartyREPEmails = "";

            for (CaseParty party : partyList) {
                if (null != party.caseRelation) {
                    switch (party.caseRelation) {
                        case "Charging Party":
                            if (!"".equals(chargingPartyNames.trim())) {
                                chargingPartyNames += ", ";
                            }
                            if (!"".equals(chargingPartyEmails.trim())) {
                                chargingPartyEmails += ", ";
                            }
                            chargingPartyEmails += party.emailAddress == null ? "" : party.emailAddress.trim();
                            chargingPartyNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Charging Party REP":
                            if (!"".equals(chargingPartyREPNames.trim())) {
                                chargingPartyREPNames += ", ";
                            }
                            if (!"".equals(chargingPartyREPEmails.trim())) {
                                chargingPartyREPEmails += ", ";
                            }
                            chargingPartyREPEmails += party.emailAddress == null ? "" : party.emailAddress.trim();
                            chargingPartyREPNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Charged Party":
                            if (!"".equals(chargedPartyNames.trim())) {
                                chargedPartyNames += ", ";
                            }
                            if (!"".equals(chargedPartyEmails.trim())) {
                                chargedPartyEmails += ", ";
                            }
                            chargedPartyEmails += party.emailAddress == null ? "" : party.emailAddress.trim();
                            chargedPartyNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                        case "Charged Party REP":
                            if (!"".equals(chargedPartyREPNames.trim())) {
                                chargedPartyREPNames += ", ";
                            }
                            if (!"".equals(chargedPartyREPEmails.trim())) {
                                chargedPartyREPEmails += ", ";
                            }
                            chargedPartyREPEmails += party.emailAddress == null ? "" : party.emailAddress.trim();
                            chargedPartyREPNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                            break;
                    }
                }
            }

            //SHORT AND LONG AGENDA
            processBookmark.process("AgendaItem" + (i + 1), item.agendaItemNumber.trim() + ".", Document);
            processBookmark.process("CaseNumber" + (i + 1),
                    NumberFormatService.generateFullCaseNumberNonGlobal(item.caseYear, item.caseType, item.caseMonth, item.caseNumber), Document);
            processBookmark.process("CPName" + (i + 1), chargingPartyNames.trim() + " v.", Document);
            processBookmark.process("CHDName" + (i + 1), chargedPartyNames.trim(), Document);
            processBookmark.process("Recommendation" + (i + 1), "Recommendation: " + item.recommendation.trim(), Document);

            //MINUTES
            processBookmark.process("MAI" + (i + 1), item.agendaItemNumber.trim() + ".", Document);
            processBookmark.process("MCASENUMBER" + (i + 1),
                    "Case " + NumberFormatService.generateFullCaseNumberNonGlobal(item.caseYear, item.caseType, item.caseMonth, item.caseNumber), Document);
            processBookmark.process("MINFORMATION" + (i + 1), chargingPartyNames.trim() + " v. " + chargedPartyNames.trim(), Document);
            processBookmark.process("MSENTENCE" + (i + 1), "The unfair labor practice charge alleged that Charged Party violated Ohio Revised Code \u00a74117.11 " + item.allegation.trim() + " by " + item.statement.trim(), Document);
            processBookmark.process("MRATIONAL" + (i + 1), "Information gathered during the investigation revealed " + item.investigationReveals.trim(), Document);
            processBookmark.process("MRECOMMENDATION" + (i + 1), item.recommendation.trim(), Document);

            //EMAIL DIRECTIVE
            processBookmark.process("LONGBOARDMEETINGDATE", Global.MMMMddyyyy.format(boardMeetingDate), Document);
            processBookmark.process("EAI" + (i + 1), item.agendaItemNumber.trim(), Document);
            processBookmark.process("ECASENUMBER" + (i + 1),
                    NumberFormatService.generateFullCaseNumberNonGlobal(item.caseYear, item.caseType, item.caseMonth, item.caseNumber), Document);
            processBookmark.process("ECPNAME" + (i + 1), chargingPartyNames.trim() + " v.", Document);
            processBookmark.process("ECHDNAME" + (i + 1), chargedPartyNames.trim(), Document);
            processBookmark.process("ECPREPNAME" + (i + 1), chargingPartyREPNames.trim(), Document);
            processBookmark.process("ECPREPEMAIL" + (i + 1), chargingPartyREPEmails.trim(), Document);
            processBookmark.process("ECHDREPNAME" + (i + 1), chargedPartyREPNames.trim(), Document);
            processBookmark.process("ECHDREPEMAIL" + (i + 1), chargedPartyREPEmails.trim(), Document);
        }

        //Blank out the rest of the rows
        for (int i = caseList.size(); i < 50; i++) {
            processBookmark.process("AgendaItem" + (i + 1), "", Document);
            processBookmark.process("CaseNumber" + (i + 1), "", Document);
            processBookmark.process("CPName" + (i + 1), "", Document);
            processBookmark.process("CHDName" + (i + 1), "", Document);
            processBookmark.process("Recommendation" + (i + 1), "", Document);
            processBookmark.process("MAI" + (i + 1), "", Document);
            processBookmark.process("MCASENUMBER" + (i + 1), "", Document);
            processBookmark.process("MINFORMATION" + (i + 1), "", Document);
            processBookmark.process("MSENTENCE" + (i + 1), "", Document);
            processBookmark.process("MRATIONAL" + (i + 1), "", Document);
            processBookmark.process("MRECOMMENDATION" + (i + 1), "", Document);
        }
        return Document;
    }

}
