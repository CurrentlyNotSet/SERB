/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.com.Dispatch;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.CaseParty;
import parker.serb.sql.FactFinder;
import parker.serb.sql.MEDCase;
import parker.serb.sql.Mediator;
import parker.serb.sql.RelatedCase;
import parker.serb.util.NumberFormatService;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class processMEDbookmarks {
    
    public static Dispatch processDoAMEDWordLetter(Dispatch Document, List<Integer> toParties, List<Integer> ccParties) {
        Mediator stateMediator = null;
        Mediator fmcsMediator = null;
        MEDCase item = MEDCase.loadEntireCaseInformation();
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);
        List<String> relatedCasesList = RelatedCase.loadRelatedCases();
        if (item.stateMediatorAppointedID != null){
            stateMediator = Mediator.getMediatorByID(Integer.parseInt(item.stateMediatorAppointedID));
        }
        if (item.FMCSMediatorAppointedID != null){
            fmcsMediator = Mediator.getMediatorByID(Integer.parseInt(item.FMCSMediatorAppointedID));
        }
        
        String employerNames = "";
        String employerREPNames = "";
        String employeeOrgNames = "";
        String employeeOrgREPNames = "";
        String employerAddresses = "";
        String employerREPAddresses = "";
        String employeeOrgAddresses = "";
        String employeeOrgREPAddresses = "";
        String factFinderSelectionName = item.FFReplacement == null ? item.FFSelection : item.FFReplacement;
        String ffFirstName = "";
        String ffLastName = "";
        String ffAddress = "";
        String relatedCaseNumbers = "";
        String whoRejected = "";
        String accepted = "";
        String deemed = "";
        String mediatorName = "";
        String mediatorEmail = "";
        String mediatorPhone = "";       
        String employerRepSalutation = "";
        String employerRepLastName = "";
        String employerRepPhoneNumber = "";
        String employerRepEmail = "";
        String employeeOrgRepSalutation = "";
        String employeeOrgRepLastName = "";
        String employeeOrgRepEmail = "";
        String employeeOrgRepPhone = "";
        String toAddressBlock = "";
        String ccNameBlock = "";
        
                        
        for (String related: relatedCasesList){
            if (!relatedCaseNumbers.equals("")){
                relatedCaseNumbers += ", ";
            }
            relatedCaseNumbers += related;
        }
        
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
                case "Employer":
                    if (!"".equals(employerNames.trim())){
                        employerNames += ", ";
                    }
                    if (!"".equals(employerAddresses.trim())){
                        employerAddresses += "\n\n";
                    }
                    employerAddresses += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    employerNames += StringUtilities.buildCasePartyName(party);
                    break;
                case "Employer REP":
                    if (!"".equals(employerREPNames.trim())){
                        employerREPNames += ", ";
                        employerRepSalutation += ", ";
                        employerRepLastName += ", ";
                        employerRepPhoneNumber += ", ";
                        employerRepEmail += ", ";
                    }
                    if (!"".equals(employerREPAddresses.trim())){
                        employerREPAddresses += "\n\n";
                    }
                    employerREPAddresses += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    employerREPNames += StringUtilities.buildCasePartyName(party);
                    employerRepSalutation += party.prefix != null ? party.prefix : "";
                    employerRepLastName += party.lastName != null ? party.lastName : "";
                    employerRepEmail += party.emailAddress != null ? party.emailAddress : "";
                    employerRepPhoneNumber += party.phone1 != null ? NumberFormatService.convertStringToPhoneNumber(party.phone1) : "";
                    employerRepPhoneNumber += party.phone2 != null ? ", " + NumberFormatService.convertStringToPhoneNumber(party.phone2) : "";
                    
                    break;
                case "Employee Organization":
                    if (!"".equals(employeeOrgNames.trim())){
                        employeeOrgNames += ", ";
                    }
                    if (!"".equals(employeeOrgAddresses.trim())){
                        employeeOrgAddresses += "\n\n";
                    }
                    employeeOrgAddresses += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    employeeOrgNames += StringUtilities.buildCasePartyName(party);
                    break;
                case "Employee Organization REP":
                    if (!"".equals(employeeOrgREPNames.trim())){
                        employeeOrgREPNames += ", ";
                        employeeOrgRepSalutation += ", ";
                        employeeOrgRepLastName += ", ";
                        employeeOrgRepEmail += ", ";
                        employeeOrgRepPhone += ", ";
                    }
                    if (!"".equals(employeeOrgREPAddresses.trim())){
                        employeeOrgREPAddresses += "\n\n";
                    }
                    employeeOrgREPAddresses += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    employeeOrgREPNames += StringUtilities.buildCasePartyName(party);
                    
                    employeeOrgRepSalutation += party.prefix != null ? party.prefix : "";
                    employeeOrgRepLastName += party.lastName != null ? party.lastName : "";
                    employeeOrgRepEmail += party.emailAddress != null ? party.emailAddress : "";
                    employeeOrgRepPhone += party.phone1 != null ? NumberFormatService.convertStringToPhoneNumber(party.phone1) : "";
                    employeeOrgRepPhone += party.phone2 != null ? ", " + NumberFormatService.convertStringToPhoneNumber(party.phone2) : "";
                    break;
            }
        } 
        
        String[] ffName = factFinderSelectionName.split(" ");
            
            if (ffName.length == 2){
                ffFirstName = ffName[0];
                ffLastName = ffName[1];
            } else if (ffName.length == 3){
                ffFirstName = ffName[0];
                ffLastName = ffName[2];
            }
            
            FactFinder ffDetails = FactFinder.getFactFinderLikeName(ffFirstName, ffLastName);
            
        if (ffDetails != null) {
            if (!ffDetails.address1.equals("")) {
                ffAddress = ffDetails.address1;
                if (!ffDetails.address2.equals("")) {
                    ffAddress = "\n";
                }
            }
            if (!ffDetails.address2.equals("")) {
                ffAddress = ffDetails.address2;
                if (!ffDetails.address3.equals("")) {
                    ffAddress = "\n";
                }
            }
            if (!ffDetails.address3.equals("")) {
                ffAddress = ffDetails.address3;
                if (!ffDetails.address3.equals("")) {
                    ffAddress = "\n";
                }
            }
            if (!ffDetails.address3.equals("")) {
                ffAddress = ffDetails.address3;
                if (!ffDetails.address3.equals("")) {
                    ffAddress = "\n";
                }
            }
            ffAddress += ffDetails.city + ", " + ffDetails.state + " " + ffDetails.zip;
        }
        
        if (item.FFRejectedBy.equals("Union")) {
            whoRejected = employeeOrgNames;
        }
        if (item.FFRejectedBy.equals("Employer")) {
            whoRejected = employerNames;
        }
        if (item.FFRejectedBy.equals("Both")) {
            whoRejected = employeeOrgNames + " and " + employerNames;
        }
        
        if (item.FFAcceptedBy.equals("Union")) {
            accepted = employeeOrgNames;
            deemed = employerNames;
        } else if (item.FFAcceptedBy.equals("Employer")) {
            accepted = employerNames;
            deemed = employeeOrgNames;
        }
        
        if (item.stateMediatorAppointedID != null) {
            mediatorName = stateMediator.lastName == null ? "" : StringUtilities.buildFullName(stateMediator.firstName, stateMediator.middleName, stateMediator.lastName);
            mediatorEmail = stateMediator.email == null ? "" : stateMediator.email;
            mediatorPhone = stateMediator.phone == null ? "" : NumberFormatService.convertStringToPhoneNumber(stateMediator.phone);
        } else if (item.FMCSMediatorAppointedID != null) {
            mediatorName = fmcsMediator.lastName == null ? "" : StringUtilities.buildFullName(fmcsMediator.firstName, fmcsMediator.middleName, fmcsMediator.lastName);
            mediatorEmail = fmcsMediator.email == null ? "" : fmcsMediator.email;
            mediatorPhone = fmcsMediator.phone == null ? "" : NumberFormatService.convertStringToPhoneNumber(fmcsMediator.phone);
        }
        
        
        for (int i = 0; i < Global.bookmarkLimit; i++) {            
            //Case Number Related Information
            processBookmark.process("CASENUM" + (i == 0 ? "" : i), NumberFormatService.generateFullCaseNumber(), Document);
            processBookmark.process("RELATEDCASENUMBERS" + (i == 0 ? "" : i), relatedCaseNumbers, Document);
            processBookmark.process("NTNFILEDBY" + (i == 0 ? "" : i), item.NTNFiledBy != null ? item.NTNFiledBy : "" , Document);
            processBookmark.process("NEGOTIATIONPERIOD" + (i == 0 ? "" : i), item.negotiationPeriod != null ? item.negotiationPeriod : "" , Document);
                                   
            //Party Information
            processBookmark.process("EMREPADDRESSBLOCK" + (i == 0 ? "" : i), employerREPAddresses, Document);
            processBookmark.process("EMPORGREPADDRESSBLOCK" + (i == 0 ? "" : i), employeeOrgREPAddresses, Document);
            processBookmark.process("EMPORGNAME" + (i == 0 ? "" : i), employeeOrgNames, Document);
            processBookmark.process("EMPNAME" + (i == 0 ? "" : i), employerNames, Document);
            processBookmark.process("EMPORGREPNAME" + (i == 0 ? "" : i), employeeOrgREPNames, Document);
            processBookmark.process("EMPREPNAME" + (i == 0 ? "" : i), employerREPNames, Document);
            processBookmark.process("EMPREPLASTNAME" + (i == 0 ? "" : i), employerRepLastName, Document);
            processBookmark.process("EMPREPSAL" + (i == 0 ? "" : i), employerRepSalutation, Document);
            processBookmark.process("EMPREPPHONE" + (i == 0 ? "" : i), employerRepPhoneNumber, Document);
            processBookmark.process("EMPREPEMAIL" + (i == 0 ? "" : i), employerRepEmail, Document);
            processBookmark.process("EMPORGREPLASTNAME" + (i == 0 ? "" : i), employeeOrgRepLastName, Document);
            processBookmark.process("EMPORGREPSAL" + (i == 0 ? "" : i), employeeOrgRepSalutation, Document);
            processBookmark.process("EMPORGREPEMAIL" + (i == 0 ? "" : i), employeeOrgRepEmail, Document);
            processBookmark.process("EMPORGREPPHONE" + (i == 0 ? "" : i), employeeOrgRepPhone, Document);
                        
            //Fact Finder Information
            processBookmark.process("FACTFINDER1" + (i == 0 ? "" : i), 
                    (item.FFList2Name1 == null ? item.FFList1Name1 : item.FFList2Name1), Document);
            processBookmark.process("FACTFINDER2" + (i == 0 ? "" : i), 
                    (item.FFList2Name2 == null ? item.FFList1Name2 : item.FFList2Name2), Document);
            processBookmark.process("FACTFINDER3" + (i == 0 ? "" : i), 
                    (item.FFList2Name3 == null ? item.FFList1Name3 : item.FFList2Name3), Document);
            processBookmark.process("FACTFINDER4" + (i == 0 ? "" : i), 
                    (item.FFList2Name4 == null ? item.FFList1Name4 : item.FFList2Name4), Document);
            processBookmark.process("FACTFINDER5" + (i == 0 ? "" : i), 
                    (item.FFList2Name5 == null ? item.FFList1Name5 : item.FFList2Name5), Document);
            processBookmark.process("FACTFINDERSELECTED" + (i == 0 ? "" : i), factFinderSelectionName, Document);  
            if (item.FFList1SelectionDueDate != null || item.FFList2SelectionDueDate != null) {
                processBookmark.process("FFPANELSELECTIONDUEDATE" + (i == 0 ? "" : i),
                        (item.FFList2SelectionDueDate == null ? Global.mmddyyyy.format(item.FFList1SelectionDueDate) : Global.mmddyyyy.format(item.FFList2SelectionDueDate)), Document);
            } else {
                processBookmark.process("FFPANELSELECTIONDUEDATE" + (i == 0 ? "" : i), "", Document);
            }
            processBookmark.process("FACTFINDERREPORTDUEDATE" + (i == 0 ? "" : i), (item.FFReportIssueDate == null ? "" : Global.MMMMddyyyy.format(item.FFReportIssueDate)), Document);
            processBookmark.process("FACTFINDERADDRESSBLOCK" + (i == 0 ? "" : i), ffAddress, Document);
            processBookmark.process("WHOREJECTED" + (i == 0 ? "" : i), whoRejected, Document);
            processBookmark.process("ACCEPTED" + (i == 0 ? "" : i), accepted, Document);
            processBookmark.process("DEEMED" + (i == 0 ? "" : i), deemed, Document);
            
            //Conciliator Information
            processBookmark.process("CONCILIATOR1" + (i == 0 ? "" : i), 
                    (item.concilList2Name1 == null ? item.concilList1Name1 : item.concilList2Name1), Document);
            processBookmark.process("CONCILIATOR2" + (i == 0 ? "" : i), 
                    (item.concilList2Name2 == null ? item.concilList1Name2 : item.concilList2Name2), Document);
            processBookmark.process("CONCILIATOR3" + (i == 0 ? "" : i), 
                    (item.concilList2Name3 == null ? item.concilList1Name3 : item.concilList2Name3), Document);
            processBookmark.process("CONCILIATOR4" + (i == 0 ? "" : i), 
                    (item.concilList2Name4 == null ? item.concilList1Name4 : item.concilList2Name4), Document);
            processBookmark.process("CONCILIATOR5" + (i == 0 ? "" : i), 
                    (item.concilList2Name5 == null ? item.concilList1Name5 : item.concilList2Name5), Document);
            processBookmark.process("ConciliatorSelection" + (i == 0 ? "" : i), 
                    (item.concilSelection == null ? "" : item.concilSelection.trim()), Document);
            processBookmark.process("ARBITRATORSELECTED" + (i == 0 ? "" : i), 
                    (item.concilReplacement == null ? item.concilSelection.trim() : item.concilReplacement.trim()), Document);
            processBookmark.process("CONCILIATORAPPTDATE" + (i == 0 ? "" : i), 
                    (item.concilAppointmentDate == null ? Global.mmddyyyy.format(item.concilAppointmentDate) : Global.mmddyyyy.format(item.concilAppointmentDate)), Document);
            if (item.concilList1OrderDate != null || item.concilList2OrderDate != null) {
                processBookmark.process("CONCILIATIONORDERDATE" + (i == 0 ? "" : i),
                        (item.concilList2OrderDate == null ? Global.mmddyyyy.format(item.concilList1OrderDate) : Global.mmddyyyy.format(item.concilList2OrderDate)), Document);
            } else {
                processBookmark.process("CONCILIATIONORDERDATE" + (i == 0 ? "" : i), "", Document);
            }
            if (item.concilList1SelectionDueDate != null || item.concilList2SelectionDueDate != null) {
                processBookmark.process("CONCILIATIONSELECTIONDUEDATE" + (i == 0 ? "" : i),
                        (item.concilList2SelectionDueDate == null ? Global.mmddyyyy.format(item.concilList1SelectionDueDate) : Global.mmddyyyy.format(item.concilList2SelectionDueDate)), Document);
            } else {
                processBookmark.process("CONCILIATIONSELECTIONDUEDATE" + (i == 0 ? "" : i), "", Document);
            }
            
            //Mediators
            processBookmark.process("STATEMEDIATORAPPT" + (i == 0 ? "" : i), 
                    stateMediator.lastName != null ? StringUtilities.buildFullName(stateMediator.firstName, stateMediator.middleName, stateMediator.lastName) : "", Document);
            processBookmark.process("STATEMEDIATORPHONE" + (i == 0 ? "" : i), 
                    stateMediator.phone != null ? NumberFormatService.convertStringToPhoneNumber(stateMediator.phone) : "", Document);            
            processBookmark.process("FMCSMEDIATORAPPT" + (i == 0 ? "" : i), 
                    fmcsMediator.lastName != null ? StringUtilities.buildFullName(fmcsMediator.firstName, fmcsMediator.middleName, fmcsMediator.lastName) : "", Document);
            processBookmark.process("FMCSPHONE" + (i == 0 ? "" : i), 
                    fmcsMediator.phone != null ? NumberFormatService.convertStringToPhoneNumber(fmcsMediator.phone) : "", Document);
            processBookmark.process("MEDNAME" + (i == 0 ? "" : i), mediatorName, Document);
            processBookmark.process("MEDEMAIL" + (i == 0 ? "" : i), mediatorEmail, Document);
            processBookmark.process("MEDPHONE" + (i == 0 ? "" : i), mediatorPhone, Document);            
        }
        return Document;
    }
    
}
