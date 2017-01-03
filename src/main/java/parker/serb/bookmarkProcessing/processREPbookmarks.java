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
import parker.serb.sql.REPCase;
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
        User user = null; //Need to get user by ID
        REPCase caseInfo = REPCase.loadCaseDetails(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);
        List<String> relatedCasesList = RelatedCase.loadRelatedCases();
        List<REPMediation> mediationList = REPMediation.loadMediationsByCaseNumber();

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
            
            if (null != party.caseRelation) {
                switch (party.caseRelation) {
                    case "Incumbent Employee Organization":
                        if (!"".equals(incumbentEmployeeOrganizationName.trim())) {
                            incumbentEmployeeOrganizationName += ", ";
                        }
                        if (!"".equals(incumbentEmployeeOrganizationAddressBlock.trim())) {
                            incumbentEmployeeOrganizationAddressBlock += "\n\n";
                        }
                        incumbentEmployeeOrganizationAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        incumbentEmployeeOrganizationName += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Incumbent Employee Organization REP":
                        if (!"".equals(incumbentEmployeeOrganizationRepName.trim())) {
                            incumbentEmployeeOrganizationRepName += ", ";
                        }
                        if (!"".equals(incumbentEmployeeOrganizationRepAddressBlock.trim())) {
                            incumbentEmployeeOrganizationRepAddressBlock += "\n\n";
                        }
                        incumbentEmployeeOrganizationRepAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        incumbentEmployeeOrganizationRepName += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Employer":
                        if (!"".equals(employerName.trim())) {
                            employerName += ", ";
                        }
                        if (!"".equals(employerAddressBlock.trim())) {
                            employerAddressBlock += "\n\n";
                        }
                        employerAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        employerName += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Employer REP":
                        if (!"".equals(employerRepName.trim())) {
                            employerRepName += ", ";
                        }
                        if (!"".equals(employerRepAddressBlock.trim())) {
                            employerRepAddressBlock += "\n\n";
                        }
                        employerRepAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        employerRepName += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Rival Employee Organization":
                        if (!"".equals(rivalEmployeeOrganizationName.trim())) {
                            rivalEmployeeOrganizationName += ", ";
                        }
                        if (!"".equals(rivalEmployeeOrganizationAddressBlock.trim())) {
                            rivalEmployeeOrganizationAddressBlock += "\n\n";
                        }
                        rivalEmployeeOrganizationAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        rivalEmployeeOrganizationName += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Rival Employee Organization REP":
                        if (!"".equals(rivalEmployeeOrganizationRepName.trim())) {
                            rivalEmployeeOrganizationRepName += ", ";
                        }
                        if (!"".equals(rivalEmployeeOrganizationRepAddressBlock.trim())) {
                            rivalEmployeeOrganizationRepAddressBlock += "\n\n";
                        }
                        rivalEmployeeOrganizationRepAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        rivalEmployeeOrganizationRepName += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Employee Organization":
                        if (!"".equals(employeeOrganizationName.trim())) {
                            employeeOrganizationName += ", ";
                        }
                        if (!"".equals(employeeOrganizationAddressBlock.trim())) {
                            employeeOrganizationAddressBlock += "\n\n";
                        }
                        employeeOrganizationAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        employeeOrganizationName += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Employee Organization REP":
                        if (!"".equals(employeeOrganizationRepName.trim())) {
                            employeeOrganizationRepName += ", ";
                        }
                        if (!"".equals(employeeOrganizationRepAddressBlock.trim())) {
                            employeeOrganizationRepAddressBlock += "\n\n";
                        }
                        employeeOrganizationRepAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        employeeOrganizationRepName += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Petitioner":
                        if (!"".equals(petitionerName.trim())) {
                            petitionerName += ", ";
                        }
                        if (!"".equals(petitionerAddressBlock.trim())) {
                            petitionerAddressBlock += "\n\n";
                        }
                        petitionerAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        petitionerName += StringUtilities.buildCasePartyName(party);
                        break;
                    case "Petitioner REP":
                        if (!"".equals(petitionerRepName.trim())) {
                            petitionerRepName += ", ";
                        }
                        if (!"".equals(petitionerRepAddressBlock.trim())) {
                            petitionerRepAddressBlock += "\n\n";
                        }
                        petitionerRepAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                        petitionerRepName += StringUtilities.buildCasePartyName(party);
                        break;
                    default:
                        if (party.caseRelation.startsWith("Rival Employee Organization") && !party.caseRelation.endsWith("REP")) {
                            if (!"".equals(rivalEmployeeOrganizationName.trim())) {
                                rivalEmployeeOrganizationName += ", ";
                            }
                            if (!"".equals(rivalEmployeeOrganizationAddressBlock.trim())) {
                                rivalEmployeeOrganizationAddressBlock += "\n\n";
                            }
                            rivalEmployeeOrganizationAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                            rivalEmployeeOrganizationName += StringUtilities.buildCasePartyName(party);
                        } else if (party.caseRelation.startsWith("Rival Employee Organization") && party.caseRelation.endsWith("REP")) {
                            if (!"".equals(rivalEmployeeOrganizationRepName.trim())) {
                                rivalEmployeeOrganizationRepName += ", ";
                            }
                            if (!"".equals(rivalEmployeeOrganizationRepAddressBlock.trim())) {
                                rivalEmployeeOrganizationRepAddressBlock += "\n\n";
                            }
                            rivalEmployeeOrganizationRepAddressBlock += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                            rivalEmployeeOrganizationRepName += StringUtilities.buildCasePartyName(party);
                        }
                        break;
                }
            }
        }

        //get mediation informations (Internal Mediation, 30 day mediation, post directive mediation)
        for (REPMediation mediation : mediationList){
            if (!"".equals(mediation.mediationDate.trim())){
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
        for (String relatedCase : relatedCasesList){
            if ("".equals(relatedCases)){
                relatedCases = ", " + relatedCase;
            } else {
                relatedCases = relatedCase;
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
            processBookmark.process("ELIGIBILITYDATE" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(caseInfo.eligibilityDate), Document);
            processBookmark.process("EXCLUDEDNEWORCURRENTUNIT" + (i == 0 ? "" : i), caseInfo.bargainingUnitExcluded, Document);
            processBookmark.process("PIN" + (i == 0 ? "" : i), caseInfo.professionalIncluded, Document);
            processBookmark.process("PEX" + (i == 0 ? "" : i), caseInfo.professionalExcluded, Document);
            processBookmark.process("OIN" + (i == 0 ? "" : i), caseInfo.optInIncluded, Document);
            processBookmark.process("INCLUDEDNEWORCURRENTUNIT" + (i == 0 ? "" : i), caseInfo.bargainingUnitIncluded, Document);
                        
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
            
            //Latest Mediation
            processBookmark.process("MEDIATEDAY" + (i == 0 ? "" : i), "", Document);
            processBookmark.process("MEDIATIONTIME" + (i == 0 ? "" : i), mediationTime, Document);
            processBookmark.process("MEDIATIONDATE" + (i == 0 ? "" : i), mediationDate, Document);
            processBookmark.process("CC" + (i == 0 ? "" : i), mediationCC, Document);
            
            //LRS Worker (Passed from Sender ComboBox)
            if (user != null){
//                processBookmark.process("NAME" + (i == 0 ? "" : i), user.name, Document);   //not yet setup in the Database
//                processBookmark.process("TITLE" + (i == 0 ? "" : i), user.title, Document); //not yet setup in the Database
                processBookmark.process("PHONE" + (i == 0 ? "" : i), NumberFormatService.convertStringToPhoneNumber(user.workPhone), Document);
                processBookmark.process("EMAIL" + (i == 0 ? "" : i), user.emailAddress, Document);
            }
        }
        
        return Document;
    }
    
}
