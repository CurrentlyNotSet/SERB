/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.com.Dispatch;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.BoardMeeting;
import parker.serb.sql.CaseParty;
import parker.serb.sql.RelatedCase;
import parker.serb.util.StringUtilities;
import parker.serb.sql.ULPCase;
import parker.serb.sql.User;
import parker.serb.util.NumberFormatService;

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
        
        for (CaseParty party : partyList){
            
            for (int person : toParties){
                if (person == party.id) {
                     if (!"".equals(toAddressBlock.trim())){
                        toAddressBlock += "\n\n";
                    }
                     toAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                }
            }
            
            for (int person : toParties){
                if (person == party.id) {
                     if (!"".equals(ccNameBlock.trim())){
                        ccNameBlock += ", ";
                    }
                     ccNameBlock += StringUtilities.buildCasePartyName(party);
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
                    chargingPartyAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    chargingPartyNames += StringUtilities.buildCasePartyName(party);
                    break;
                case "Charging Party REP":
                    if (!"".equals(chargingPartyREPNames.trim())){
                        chargingPartyREPNames += ", ";
                    }
                    if (!"".equals(chargingPartyREPAddressBlock.trim())){
                        chargingPartyREPAddressBlock += "\n\n";
                    }
                    chargingPartyREPAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    chargingPartyREPNames += StringUtilities.buildCasePartyName(party);
                    break;
                case "Charged Party":
                    if (!"".equals(chargedPartyNames.trim())){
                        chargedPartyNames += ", ";
                    }
                    if (!"".equals(chargedPartyAddressBlock.trim())){
                        chargedPartyAddressBlock += "\n\n";
                    }
                    chargedPartyAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    chargedPartyNames += StringUtilities.buildCasePartyName(party);
                    break;
                case "Charged Party REP":
                    if (!"".equals(chargedPartyREPNames.trim())){
                        chargedPartyREPNames += ", ";
                    }
                    if (!"".equals(chargedPartyREPAddressBlock.trim())){
                        chargedPartyREPAddressBlock += "\n\n";
                    }
                    chargedPartyREPAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    chargedPartyREPNames += StringUtilities.buildCasePartyName(party);
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
        
        for (String relatedCase : relatedCasesList){
            if ("".equals(relatedCases)){
                relatedCases = ", " + relatedCase;
            } else {
                relatedCases = relatedCase;
            }
        }   
        
        for (BoardMeeting bm: boardMeetingList){
            boardMeetingDate = bm.boardMeetingDate;
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
            
            //Latest (by date) board meeting
            processBookmark.process("LONGMEETINGDATE" + (i == 0 ? "" : i), ("".equals(boardMeetingDate) ? "" : boardMeetingDate), Document);
            processBookmark.process("BOARDMEETINGDATE" + (i == 0 ? "" : i), ("".equals(boardMeetingDate) ? "" : boardMeetingDate), Document);
            processBookmark.process("AGENDAITEM" + (i == 0 ? "" : i), boardMeetingAgendaItem, Document);
        }
        
        return Document;
    }
    
}
