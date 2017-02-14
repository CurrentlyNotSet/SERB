/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.com.Dispatch;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.CaseParty;
import parker.serb.util.StringUtilities;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CMDSDocuments;
import parker.serb.sql.CMDSHearing;
import parker.serb.sql.EmailOutInvites;
import parker.serb.sql.User;
import parker.serb.util.DateConversion;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author Andrew
 */
public class processCMDSbookmarks {

    public static Dispatch processDoACMDSWordLetter(Dispatch Document, CMDSDocuments template, questionsCMDSModel answers, List<Integer> toParties, List<Integer> ccParties) {
        //get basic information  
        CMDSCase item = CMDSCase.loadCMDSCaseInformation();
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);

        String ccNameBlock = "";
        String ccAddressBlock = "";

        String appellantNames = "";
        String appellantRep1Names = "";
        String appellantRep2Names = "";
        String appelleeNames = "";
        String appelleeRep1Names = "";
        String appelleeRep2Names = "";
        String appellee2Names = "";
        String appellee2Rep1Names = "";
        String appellee2Rep2Names = "";
        String courtesyCopy1Names = "";
        String courtesyCopy2Names = "";
        String courtesyCopy3Names = "";
        String courtesyCopy4Names = "";
        String otherInterestedPartiesNames = "";
        String addresseeName = "";

        String appellantAddressBlock = "";
        String appellantRep1AddressBlock = "";
        String appellantRep2AddressBlock = "";
        String appelleeAddressBlock = "";
        String appelleeRep1AddressBlock = "";
        String appelleeRep2AddressBlock = "";
        String appellee2AddressBlock = "";
        String appellee2Rep1AddressBlock = "";
        String appellee2Rep2AddressBlock = "";
        String courtesyCopy1AddressBlock = "";
        String courtesyCopy2AddressBlock = "";
        String courtesyCopy3AddressBlock = "";
        String courtesyCopy4AddressBlock = "";
        String otherInterestedPartiesAddressBlock = "";
        String addresseeAddressBlock = "";

        String appellantRep1Title = "";
        String hisHerString = "";
        String himHerString = "";
        String heSheString = "";
        String dateHearingServed = "";
        String hearingDateString = "";
        String dateFiledString = "";
        String dateRequestedString = "";
        String cost = "N/A";
        String total = "$25.00";
        String ALJname = "";

        String HearingDatesSE = "N/A";
        String HearingDatesST = "N/A";
        String HearingDatesRH = "N/A";
        String PreHearingDates = "N/A";
        String dateResponseDueString = "";

        for (CaseParty party : partyList) {

            for (int person : ccParties) {
                if (person == party.id) {
                    if (!"".equals(ccNameBlock.trim())) {
                        ccNameBlock += ", ";
                    }
                    ccNameBlock += StringUtilities.buildCasePartyNameNoPreFix(party);
                    if (!"".equals(ccAddressBlock.trim())) {
                        ccAddressBlock += "\n\n";
                    }
                    ccAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                }
            }

            if (null != party.caseRelation) {
                switch (party.caseRelation) {
                    case "Appellant":
                        if (!"".equals(appellantNames.trim())) {
                            appellantNames += ", ";
                        }
                        if (!"".equals(appellantAddressBlock.trim())) {
                            appellantAddressBlock += "\n\n";
                        }
                        appellantNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                        appellantAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Appellant Rep 1":
                        if (!"".equals(appellantRep1Names.trim())) {
                            appellantRep1Names += ", ";
                        }
                        if (!"".equals(appellantRep1AddressBlock.trim())) {
                            appellantRep1AddressBlock += "\n\n";
                        }
                        appellantRep1Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        appellantRep1Title += party.jobTitle;
                        appellantRep1AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Appellant Rep 2":
                        if (!"".equals(appellantRep2Names.trim())) {
                            appellantRep2Names += ", ";
                        }
                        if (!"".equals(appellantRep2AddressBlock.trim())) {
                            appellantRep2AddressBlock += "\n\n";
                        }
                        appellantRep2Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        appellantRep2AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Appellee":
                        if (!"".equals(appelleeNames.trim())) {
                            appelleeNames += ", ";
                        }
                        if (!"".equals(appelleeAddressBlock.trim())) {
                            appelleeAddressBlock += "\n\n";
                        }
                        appelleeNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                        appelleeAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Appellee Rep 1":
                        if (!"".equals(appelleeRep1Names.trim())) {
                            appelleeRep1Names += ", ";
                        }
                        if (!"".equals(appelleeRep1AddressBlock.trim())) {
                            appelleeRep1AddressBlock += "\n\n";
                        }
                        appelleeRep1Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        appelleeRep1AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Appellee Rep 2":
                        if (!"".equals(appelleeRep2Names.trim())) {
                            appelleeRep2Names += ", ";
                        }
                        if (!"".equals(appelleeRep2AddressBlock.trim())) {
                            appelleeRep2AddressBlock += "\n\n";
                        }
                        appelleeRep2Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        appelleeRep2AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Appellee 2":
                        if (!"".equals(appellee2Names.trim())) {
                            appellee2Names += ", ";
                        }
                        if (!"".equals(appellee2AddressBlock.trim())) {
                            appellee2AddressBlock += "\n\n";
                        }
                        appellee2Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        appellee2AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Appellee 2 Rep 1":
                        if (!"".equals(appellee2Rep1Names.trim())) {
                            appellee2Rep1Names += ", ";
                        }
                        if (!"".equals(appellee2Rep1AddressBlock.trim())) {
                            appellee2Rep1AddressBlock += "\n\n";
                        }
                        appellee2Rep1Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        appellee2Rep1AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Appellee 2 Rep 2":
                        if (!"".equals(appellee2Rep2Names.trim())) {
                            appellee2Rep2Names += ", ";
                        }
                        if (!"".equals(appellee2Rep2AddressBlock.trim())) {
                            appellee2Rep2AddressBlock += "\n\n";
                        }
                        appellee2Rep2Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        appellee2Rep2AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Courtesy Copy 1":
                        if (!"".equals(courtesyCopy1Names.trim())) {
                            courtesyCopy1Names += ", ";
                        }
                        if (!"".equals(courtesyCopy1AddressBlock.trim())) {
                            courtesyCopy1AddressBlock += "\n\n";
                        }
                        courtesyCopy1Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        courtesyCopy1AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Courtesy Copy 2":
                        if (!"".equals(courtesyCopy2Names.trim())) {
                            courtesyCopy2Names += ", ";
                        }
                        if (!"".equals(courtesyCopy2AddressBlock.trim())) {
                            courtesyCopy2AddressBlock += "\n\n";
                        }
                        courtesyCopy2Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        courtesyCopy2AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Courtesy Copy 3":
                        if (!"".equals(courtesyCopy3Names.trim())) {
                            courtesyCopy3Names += ", ";
                        }
                        if (!"".equals(courtesyCopy3AddressBlock.trim())) {
                            courtesyCopy3AddressBlock += "\n\n";
                        }
                        courtesyCopy3Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        courtesyCopy3AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Courtesy Copy 4":
                        if (!"".equals(courtesyCopy4Names.trim())) {
                            courtesyCopy4Names += ", ";
                        }
                        if (!"".equals(courtesyCopy4AddressBlock.trim())) {
                            courtesyCopy4AddressBlock += "\n\n";
                        }
                        courtesyCopy4Names += StringUtilities.buildCasePartyNameNoPreFix(party);
                        courtesyCopy4AddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                    case "Other Interested Parties":
                        if (!"".equals(otherInterestedPartiesNames.trim())) {
                            otherInterestedPartiesNames += ", ";
                        }
                        if (!"".equals(otherInterestedPartiesAddressBlock.trim())) {
                            otherInterestedPartiesAddressBlock += "\n\n";
                        }
                        otherInterestedPartiesNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                        otherInterestedPartiesAddressBlock += StringUtilities.buildAddressBlockWithLineBreaks(party);
                        break;
                }
            }
        }

        if (item.mediatorID > 0) {
            ALJname = User.getNameByID(item.mediatorID);
        }

        if (!answers.getGenderAppellant().equals("")) {
            if (answers.getGenderAppellant().equalsIgnoreCase("male")) {
                hisHerString = "his";
                himHerString = "him";
                heSheString = "he";
            } else {
                hisHerString = "her";
                himHerString = "her";
                heSheString = "she";
            }
        }

        if (!answers.getHearingServed().equals("")) {
            try {
                dateHearingServed = Global.MMMMMdyyyy.format(Global.mmddyyyy.parse(answers.getHearingServed()));
            } catch (ParseException ex) {
                SlackNotification.sendNotification(ex);
            }
        }

        if (!answers.getHearingDate().equals("")) {
            try {
                hearingDateString = Global.MMMMMdyyyy.format(Global.mmddyyyy.parse(answers.getHearingDate()));
            } catch (ParseException ex) {
                SlackNotification.sendNotification(ex);
            }
        }

        if (!answers.getDateFiled().equals("")) {
            try {
                dateFiledString = Global.MMMMMdyyyy.format(Global.mmddyyyy.parse(answers.getDateFiled()));
            } catch (ParseException ex) {
                SlackNotification.sendNotification(ex);
            }
        }

        if (answers.getAppealTypeLS() != null) {
            if (!answers.getAppealTypeLS().trim().equals("")) {
                dateRequestedString = answers.getAppealTypeLS();
            }
        }
        if (answers.getRequestingPartyContinuance() != null) {
            if (!answers.getRequestingPartyContinuance().trim().equals("")) {
                dateRequestedString = answers.getRequestingPartyContinuance();
            }
        }
        if (answers.getDateRequestedExtension() != null) {
            if (!answers.getDateRequestedExtension().trim().equals("")) {
                dateRequestedString = answers.getDateRequestedExtension();
            }
        }
        if (answers.getRequestingPartyConsolidation() != null) {
            if (!answers.getRequestingPartyConsolidation().trim().equals("")) {
                dateRequestedString = answers.getRequestingPartyConsolidation();
            }
        }

        if (!dateRequestedString.equals("")) {
            try {
                dateRequestedString = Global.MMMMMdyyyy.format(Global.mmddyyyy.parse(dateRequestedString));
            } catch (ParseException ex) {
                SlackNotification.sendNotification(ex);
            }
        }

        if (null != answers.getAddressBlockName()) {
            switch (answers.getAddressBlockName()) {
                case "Appellant":
                    addresseeName = appellantNames;
                    addresseeAddressBlock = appellantAddressBlock;
                    break;
                case "Appellant Rep 1":
                    addresseeName = appellantRep1Names;
                    addresseeAddressBlock = appellantRep1AddressBlock;
                    break;
                case "Appellant Rep 2":
                    addresseeName = appellantRep2Names;
                    addresseeAddressBlock = appellantRep2AddressBlock;
                    break;
                case "Appellee":
                    addresseeName = appelleeNames;
                    addresseeAddressBlock = appelleeAddressBlock;
                    break;
                case "Appellee Rep 1":
                    addresseeName = appelleeRep1Names;
                    addresseeAddressBlock = appelleeRep1AddressBlock;
                    break;
                case "Appellee Rep 2":
                    addresseeName = appelleeRep2Names;
                    addresseeAddressBlock = appelleeRep2AddressBlock;
                    break;
                case "Appellee 2":
                    addresseeName = appellee2Names;
                    addresseeAddressBlock = appellee2AddressBlock;
                    break;
                case "Appellee 2 Rep 1":
                    addresseeName = appellee2Rep1Names;
                    addresseeAddressBlock = appellee2Rep1AddressBlock;
                    break;
                case "Appellee 2 Rep 2":
                    addresseeName = appellee2Rep2Names;
                    addresseeAddressBlock = appellee2Rep2AddressBlock;
                    break;
                case "Courtesy Copy 1":
                    addresseeName = courtesyCopy1Names;
                    addresseeAddressBlock = courtesyCopy1AddressBlock;
                    break;
                case "Courtesy Copy 2":
                    addresseeName = courtesyCopy2Names;
                    addresseeAddressBlock = courtesyCopy2AddressBlock;
                    break;
                case "Courtesy Copy 3":
                    addresseeName = courtesyCopy3Names;
                    addresseeAddressBlock = courtesyCopy3AddressBlock;
                    break;
                case "Courtesy Copy 4":
                    addresseeName = courtesyCopy4Names;
                    addresseeAddressBlock = courtesyCopy4AddressBlock;
                    break;
                case "Other Interested Parties":
                    addresseeName = otherInterestedPartiesNames;
                    addresseeAddressBlock = otherInterestedPartiesAddressBlock;
                    break;
                case "Other":
                    addresseeName = answers.getAddressBlockName();
                    addresseeAddressBlock = answers.getAddressBlockBlock();
                    break;
            }
        }

        if (answers.getStayDate() != null) {
            if (!answers.getStayDate().equals("")) {
                double precost = Double.parseDouble(answers.getStayDate()) * 1.5;
                cost = "$" + String.format("%.2f", precost);

                double pretotal = precost + 25;
                total = "$" + String.format("%.2f", pretotal);
            }
        }

        CMDSHearing SEhearing = CMDSHearing.loadTopHearingByCaseNumberAndType("SE");
        CMDSHearing SThearing = CMDSHearing.loadTopHearingByCaseNumberAndType("ST");
        CMDSHearing RHhearing = CMDSHearing.loadTopHearingByCaseNumberAndType("RH");
        CMDSHearing PHhearing = CMDSHearing.loadTopHearingByCaseNumberAndType("PH");

        if (SEhearing != null) {
            HearingDatesSE = SEhearing.hearingDate + " at " + SEhearing.hearingTime;
        }
        if (SThearing != null) {
            HearingDatesST = SThearing.hearingDate + " at " + SThearing.hearingTime;
        }
        if (RHhearing != null) {
            HearingDatesRH = RHhearing.hearingDate + " at " + RHhearing.hearingTime;
        }
        if (PHhearing != null) {
            PreHearingDates = PHhearing.hearingDate + " at " + PHhearing.hearingTime;
        }

        if (answers.getResponseDueDate() != null) {
            Timestamp parsedDate = null;
            if (!answers.getResponseDueDate().equals("")) {
                try {
                    parsedDate = new Timestamp(Global.mmddyyyy.parse(answers.getResponseDueDate()).getTime());
                } catch (ParseException ex) {
                    SlackNotification.sendNotification(ex);
                }
                if (parsedDate != null) {
                    dateResponseDueString = Global.MMMMMdyyyy.format(parsedDate);
                    EmailOutInvites.addNewHearing("CMDS",
                            CMDSCase.getALJemail(),
                            null,
                            "Response due for " + NumberFormatService.generateFullCaseNumber(),
                            NumberFormatService.generateFullCaseNumber(),
                            null,
                            null,
                            null,
                            DateConversion.generateReminderStartDate(parsedDate)
                    );
                }
            }
        }

        //ProcessBookmarks
        for (int i = 0; i < Global.BOOKMARK_LIMIT; i++) {
            //Case Data
            processBookmark.process("CaseNumber" + (i == 0 ? "" : i), NumberFormatService.generateFullCaseNumberNonGlobal(
                    item.caseYear, item.caseType, item.caseMonth, item.caseNumber), Document);
            processBookmark.process("TypeofCase" + (i == 0 ? "" : i), item.caseType, Document);
            processBookmark.process("ALJName" + (i == 0 ? "" : i), ALJname, Document);
            processBookmark.process("HearingDatesSE" + (i == 0 ? "" : i), HearingDatesSE, Document);
            processBookmark.process("HearingDatesST" + (i == 0 ? "" : i), HearingDatesST, Document);
            processBookmark.process("HearingDatesRH" + (i == 0 ? "" : i), HearingDatesRH, Document);
            processBookmark.process("PreHearingDates" + (i == 0 ? "" : i), PreHearingDates, Document);

            //Parties
            processBookmark.process("AppellantAddresseeName" + (i == 0 ? "" : i), appellantNames, Document);
            processBookmark.process("AppellantName" + (i == 0 ? "" : i), appellantNames, Document);
            processBookmark.process("AppellantRep2" + (i == 0 ? "" : i), appellantRep2Names, Document);
            processBookmark.process("AppelleeName" + (i == 0 ? "" : i), appelleeNames, Document);
            processBookmark.process("AppelleeAddresseeName" + (i == 0 ? "" : i), appelleeNames, Document);
            processBookmark.process("Appellee2Name" + (i == 0 ? "" : i), appellee2Names, Document);
            processBookmark.process("NameOfAppelleeRep1" + (i == 0 ? "" : i), appelleeRep1Names, Document);
            processBookmark.process("NameOfAppelleeRep2" + (i == 0 ? "" : i), appelleeRep2Names, Document);
            processBookmark.process("AddresseeName" + (i == 0 ? "" : i), addresseeName, Document);
            processBookmark.process("AppellantAddressBlock" + (i == 0 ? "" : i), appellantAddressBlock, Document);
            processBookmark.process("AppelleeAddressBlock" + (i == 0 ? "" : i), appelleeAddressBlock, Document);
            processBookmark.process("AddressBlock" + (i == 0 ? "" : i), addresseeAddressBlock, Document);
            processBookmark.process("CaseName" + (i == 0 ? "" : i), appellantNames.trim() + " vs. " + appelleeNames.trim(), Document);
            processBookmark.process("NameOfRep" + (i == 0 ? "" : i), appellantRep1Names, Document);
            processBookmark.process("TitleOfRep" + (i == 0 ? "" : i), appellantRep1Title, Document);
            processBookmark.process("CCList" + (i == 0 ? "" : i), ccNameBlock, Document);
            processBookmark.process("ccListAddress" + (i == 0 ? "" : i), ccAddressBlock, Document);
            
            //Questions
            processBookmark.process("ActionAppealed" + (i == 0 ? "" : i), answers.getActionAppealed(), Document);
            processBookmark.process("FiledOrDidNotFile" + (i == 0 ? "" : i), answers.getMemorandumContra(), Document);
            processBookmark.process("ClassTitle" + (i == 0 ? "" : i), answers.getClassificationTitle(), Document);
            processBookmark.process("BargainingUnit" + (i == 0 ? "" : i), answers.getBargainingUnit(), Document);
            processBookmark.process("ClassNumber" + (i == 0 ? "" : i), answers.getClassificationNumber(), Document);
            processBookmark.process("AppellantAppointed" + (i == 0 ? "" : i), answers.getAppellantAppointed(), Document);
            processBookmark.process("ProbationaryPeriod" + (i == 0 ? "" : i), answers.getProbationaryPeriod(), Document);
            processBookmark.process("TimeofHearing" + (i == 0 ? "" : i), answers.getHearingTime(), Document);
            processBookmark.process("RequestingParty" + (i == 0 ? "" : i), answers.getRequestingParty(), Document);
            processBookmark.process("RequestingPartyNOS" + (i == 0 ? "" : i), answers.getRequestingParty().replace("'s", ""), Document);
            processBookmark.process("DateNoticeServed" + (i == 0 ? "" : i), dateHearingServed, Document);
            processBookmark.process("HisOrHer" + (i == 0 ? "" : i), hisHerString, Document);
            processBookmark.process("HimOrHer" + (i == 0 ? "" : i), himHerString, Document);
            processBookmark.process("HeOrShe" + (i == 0 ? "" : i), heSheString, Document);
            processBookmark.process("DateofHearing" + (i == 0 ? "" : i), hearingDateString, Document);
            processBookmark.process("CodeSection" + (i == 0 ? "" : i), answers.getCodeSelection(), Document);
            processBookmark.process("CountyName" + (i == 0 ? "" : i), answers.getCountyName(), Document);
            processBookmark.process("DateofStay" + (i == 0 ? "" : i), answers.getStayDate(), Document);
            processBookmark.process("CasePendingResolution" + (i == 0 ? "" : i), answers.getCasePendingResolution(), Document);
            processBookmark.process("DateofLastUpdate" + (i == 0 ? "" : i), answers.getLastUpdate(), Document);
            processBookmark.process("MatterContinued" + (i == 0 ? "" : i), answers.getMatterContinued(), Document);
            processBookmark.process("SettlementDue" + (i == 0 ? "" : i), answers.getSettlementDue(), Document);
            processBookmark.process("FilingParty" + (i == 0 ? "" : i), answers.getFilingParty(), Document);
            processBookmark.process("RespondingParty" + (i == 0 ? "" : i), answers.getRespondingParty(), Document);
            processBookmark.process("RequestingParty" + (i == 0 ? "" : i), answers.getRequestingParty(), Document);
            processBookmark.process("DepositionOrDepositions" + (i == 0 ? "" : i), answers.getDeposition(), Document);
            processBookmark.process("RepHimOrHer" + (i == 0 ? "" : i), answers.getGenderRep(), Document);
            processBookmark.process("TypeOfAction" + (i == 0 ? "" : i), answers.getActionAppealed(), Document);
            processBookmark.process("CodeSectionFillIn" + (i == 0 ? "" : i), answers.getCodeSectionFillIn(), Document);
            processBookmark.process("DocumentName" + (i == 0 ? "" : i), answers.getDocumentName(), Document);
            processBookmark.process("InfoRedacted" + (i == 0 ? "" : i), answers.getInfoRedacted(), Document);
            processBookmark.process("RedactorName" + (i == 0 ? "" : i), answers.getRedactorName(), Document);
            processBookmark.process("RedactorTitle" + (i == 0 ? "" : i), answers.getRedactorTitle(), Document);
            processBookmark.process("DatePOSent" + (i == 0 ? "" : i), answers.getDatePOSent(), Document);
            processBookmark.process("PurposeofExtension" + (i == 0 ? "" : i), answers.getPurposeofExtension(), Document);
            processBookmark.process("DateFiled" + (i == 0 ? "" : i), dateFiledString, Document);
            processBookmark.process("Date1stLetterSent" + (i == 0 ? "" : i), answers.getFirstLetterSent(), Document);
            processBookmark.process("AppealType" + (i == 0 ? "" : i), answers.getAppealType(), Document);
            processBookmark.process("AppealTypeONE" + (i == 0 ? "" : i), answers.getAppealType2() == null ? "" : answers.getAppealType2()[0], Document);
            processBookmark.process("AppealTypeTWO" + (i == 0 ? "" : i), answers.getAppealType2() == null ? "" : answers.getAppealType2()[1], Document);
            processBookmark.process("AppealTypeUF" + (i == 0 ? "" : i), answers.getAppealTypeUF() == null ? "" : answers.getAppealTypeUF()[1], Document);
            processBookmark.process("AppealTypeLS" + (i == 0 ? "" : i), answers.getAppealTypeLS(), Document);
            processBookmark.process("RequestingPartyC" + (i == 0 ? "" : i), answers.getAppealTypeLS(), Document);
            processBookmark.process("RequestingPartyE" + (i == 0 ? "" : i), answers.getRequestingPartyExtension(), Document);
            processBookmark.process("COST" + (i == 0 ? "" : i), cost, Document);
            processBookmark.process("TOTAL" + (i == 0 ? "" : i), total, Document);
            processBookmark.process("DateRequested" + (i == 0 ? "" : i), dateRequestedString, Document);
            processBookmark.process("DateResponseDue" + (i == 0 ? "" : i), dateResponseDueString, Document);
        }

        return Document;
    }

}
