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
import parker.serb.sql.MEDCase;
import parker.serb.util.NumberFormatService;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class processMEDbookmarks {
    
    public static Dispatch processDoAMEDWordLetter(Dispatch Document) {
        MEDCase item = null;
//                item = MEDCase.loadCaseInformation();
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);
        
        String employerNames = "";
        String employerREPNames = "";
        String employeeOrgNames = "";
        String employeeOrgREPNames = "";
        String employerAddresses = "";
        String employerREPAddresses = "";
        String employeeOrgAddresses = "";
        String employeeOrgREPAddresses = "";

        
        for (CaseParty party : partyList){
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
                    }
                    if (!"".equals(employerREPAddresses.trim())){
                        employerREPAddresses += "\n\n";
                    }
                    employerREPAddresses += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    employerREPNames += StringUtilities.buildCasePartyName(party);
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
                    }
                    if (!"".equals(employeeOrgREPAddresses.trim())){
                        employeeOrgREPAddresses += "\n\n";
                    }
                    employeeOrgREPAddresses += StringUtilities.buildAddressBlockWithPhoneAndEmail(party);
                    employeeOrgREPNames += StringUtilities.buildCasePartyName(party);
                    break;
            }
        } 
        

        for (int i = 0; i < Global.bookmarkLimit; i++) {            
            //Case Number Related Information
            processBookmark.process("CASENUM" + (i == 0 ? "" : i), NumberFormatService.generateFullCaseNumber(), Document);
                       
            //Party Information
            processBookmark.process("EMREPADDRESSBLOCK" + (i == 0 ? "" : i), employerREPAddresses, Document);
            processBookmark.process("EMPORGREPADDRESSBLOCK" + (i == 0 ? "" : i), employeeOrgREPAddresses, Document);
       
            //Fact Finder Information
            
            
            //Conciliator Information
            processBookmark.process("CONCILIATOR1" + (i == 0 ? "" : i), 
                    (item.concilList2Name1 == null ? item.concilList1Name1.trim() : item.concilList2Name1.trim()), Document);
            processBookmark.process("CONCILIATOR2" + (i == 0 ? "" : i), 
                    (item.concilList2Name2 == null ? item.concilList1Name2.trim() : item.concilList2Name2.trim()), Document);
            processBookmark.process("CONCILIATOR3" + (i == 0 ? "" : i), 
                    (item.concilList2Name3 == null ? item.concilList1Name3.trim() : item.concilList2Name3.trim()), Document);
            processBookmark.process("CONCILIATOR4" + (i == 0 ? "" : i), 
                    (item.concilList2Name4 == null ? item.concilList1Name4.trim() : item.concilList2Name4.trim()), Document);
            processBookmark.process("CONCILIATOR5" + (i == 0 ? "" : i), 
                    (item.concilList2Name5 == null ? item.concilList1Name5.trim() : item.concilList2Name5.trim()), Document);
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
        }
        return Document;
    }
    
    
    
    
   
//    public void processDoAMEDWordLetter(MEDCaseData ucd, boolean toRep, String SourcePath, String SourceTemplate, String[] DestinationPath, String DestinationFileName, String PartyType) throws UnsupportedEncodingException, IOException, InterruptedException, PrinterException {
//
//        BlobFileData statement = statement.getBlobFileDataRecord(Global.getLogger(), Global.getDba(), "BlobFile", "CaseNumber = '" + ucd.CaseNumber.trim() + "' AND SelectorA = 'Statement'");
//        BlobFileData rec = rec.getBlobFileDataRecord(Global.getLogger(), Global.getDba(), "BlobFile", "CaseNumber = '" + ucd.CaseNumber.trim() + "' AND SelectorA = 'Recommendation'");
// 
//        processWordReplaceBookmark("EMPORGNAME", ucd.EmployeeOrgName.trim().replace("\"", ""), Document);
//        processWordReplaceBookmark("EMPNAME", ucd.EmployerName.trim().replace("\"", ""), Document);            
//
//        processWordReplaceBookmark("EMPORGREPSAL", ucd.EmployeeOrgREPSal.trim(), Document);
//        processWordReplaceBookmark("EMPORGREPNAME", ucd.EmployeeOrgREPName.trim(), Document);

//        if (ucd.EmployeeOrgREPSal.equals("Messrs.") || ucd.EmployeeOrgREPSal.equals("Mses.")) {
//            processWordReplaceBookmark("EMPREPSAL", ucd.EmployerREPSal.trim(), Document);
//        } else {
//            processWordReplaceBookmark("EMPREPSAL", ucd.EmployerREPSal.trim(), Document);
//        }
//        processWordReplaceBookmark("EMPREPEMAIL", ucd.EmployerREPEmail.trim(), Document);
//        processWordReplaceBookmark("EMPORGREPEMAIL", ucd.EmployeeOrgREPEmail.trim(), Document);
//
//        processWordReplaceBookmark("EMPREPNAME", ucd.EmployerREPName.trim(), Document);

//        processWordReplaceBookmark("CASENUM2", ucd.CaseNumber2.trim(), Document);
//        processWordReplaceBookmark("CASENUM3", ucd.CaseNumber3.trim(), Document);
//        processWordReplaceBookmark("CASENUM4", ucd.CaseNumber4.trim(), Document);
//        processWordReplaceBookmark("CASENUM5", ucd.CaseNumber5.trim(), Document);
//        processWordReplaceBookmark("CASENUM6", ucd.CaseNumber6.trim(), Document);
//
//        String whoRejected = "";
//
//        if (ucd.ResultsRejectedBy.equals("Union")) {
//            whoRejected = ucd.EmployeeOrgName.trim();
//        }
//
//        if (ucd.ResultsRejectedBy.equals("Employer")) {
//            whoRejected = ucd.EmployerName.trim();
//        }
//
//        if (ucd.ResultsRejectedBy.equals("Both")) {
//            whoRejected = ucd.EmployeeOrgName.trim() + " and " + ucd.EmployerName.trim();
//        }
//
//        if (ucd.ResultsRejectedBy.equals("")) {
//            whoRejected = "";
//        }
//
//        processWordReplaceBookmark("WHOREJECTED", whoRejected, Document);
//
//        String[] EmployeeLastname = ucd.EmployeeOrgREPName.split(" ");
//        int EmployeeSize = EmployeeLastname.length - 1;
//
//        processWordReplaceBookmark("EMPORGREPLASTNAME", EmployeeLastname[EmployeeSize], Document);
//
//        String[] EmployerLastname = ucd.EmployerREPName.split(" ");
//        int EmployerSize = EmployerLastname.length - 1;
//
//        processWordReplaceBookmark("EMPREPLASTNAME", EmployerLastname[EmployerSize], Document);
//
//
//        if (!ucd.StateMediatorAppt.equals("")) {
//            MEDMediatorsData med = new MEDMediatorsData();
//            med = med.getMEDMediatorsDataRecord(Global.getLogger(), Global.getDba(), Global.getMEDMediatorsTableName(), "Name = '" + ucd.StateMediatorAppt + "'");
//            processWordReplaceBookmark("MEDNAME", med.Name, Document);
//            processWordReplaceBookmark("MEDEMAIL", med.Email, Document);
//            processWordReplaceBookmark("MEDPHONE", med.Phone, Document);
//        } else if (!ucd.FMCSMediatorAppt.equals("")) {
//            MEDMediatorsData med = new MEDMediatorsData();
//            med = med.getMEDMediatorsDataRecord(Global.getLogger(), Global.getDba(), Global.getMEDMediatorsTableName(), "Name = '" + ucd.FMCSMediatorAppt + "'");
//            processWordReplaceBookmark("MEDNAME", med.Name, Document);
//            processWordReplaceBookmark("MEDEMAIL", med.Email, Document);
//            processWordReplaceBookmark("MEDPHONE", med.Phone, Document);
//        } else {
//            processWordReplaceBookmark("MEDNAME", "", Document);
//            processWordReplaceBookmark("MEDNAME", "", Document);
//            processWordReplaceBookmark("MEDNAME", "", Document);
//        }
//
//        processWordReplaceBookmark("STATEMEDIATORAPPT", ucd.StateMediatorAppt.trim(), Document);
//        processWordReplaceBookmark("STATEMEDIATORPHONE", ucd.StateMediatorPhone, Document);
//        processWordReplaceBookmark("FMCSMEDIATORAPPT", ucd.FMCSMediatorAppt.trim(), Document);
//        processWordReplaceBookmark("FMCSPHONE", ucd.FMCSMediatorPhone, Document);
//        processWordReplaceBookmark("NTNFILEDBY", ucd.NTNFiledBy.trim(), Document);
//        processWordReplaceBookmark("NEGOTIATIONPERIOD", ucd.NegotiationPeriod.trim(), Document);
//
//        if (ucd.FactFinder1Set2.equals("")) {
//            processWordReplaceBookmark("FACTFINDER1", ucd.FactFinder1.trim(), Document);
//            processWordReplaceBookmark("FACTFINDER2", ucd.FactFinder2.trim(), Document);
//            processWordReplaceBookmark("FACTFINDER3", ucd.FactFinder3.trim(), Document);
//            processWordReplaceBookmark("FACTFINDER4", ucd.FactFinder4.trim(), Document);
//            processWordReplaceBookmark("FACTFINDER5", ucd.FactFinder5.trim(), Document);
//        } else {
//            processWordReplaceBookmark("FACTFINDER1", ucd.FactFinder1Set2.trim(), Document);
//            processWordReplaceBookmark("FACTFINDER2", ucd.FactFinder2Set2.trim(), Document);
//            processWordReplaceBookmark("FACTFINDER3", ucd.FactFinder3Set2.trim(), Document);
//            processWordReplaceBookmark("FACTFINDER4", ucd.FactFinder4Set2.trim(), Document);
//            processWordReplaceBookmark("FACTFINDER5", ucd.FactFinder5Set2.trim(), Document);
//        }
//
//        if (ucd.FFPanelSelectionDue2.equals("")) {
//            processWordReplaceBookmark("FFPANELSELECTIONDUEDATE", ucd.FFPanelSelectionDue.trim(), Document);
//        } else {
//            processWordReplaceBookmark("FFPANELSELECTIONDUEDATE", ucd.FFPanelSelectionDue2.trim(), Document);
//        }
//
//        if (ucd.FactFinderRepalcement.equals("")) {
//            processWordReplaceBookmark("FACTFINDERSELECTED", ucd.FactFinderSelected.trim(), Document);
//        } else {
//            processWordReplaceBookmark("FACTFINDERSELECTED", ucd.FactFinderRepalcement.trim(), Document);
//        }
//
//        processWordReplaceBookmark("FACTFINDERREPORTDUEDATE", ucd.FactFinderReportDate.trim(), Document);
//
//        FactFindersData factfinder = new FactFindersData();
//        factfinder = factfinder.getFactFindersDataRecord(Global.getLogger(), Global.getDba(), Global.getFactFindersTableName(), "FactFinderName = '" + ucd.FactFinderSelected.trim() + "'");
//
//        String FFaddress = "";
//        if (factfinder != null) {
//            if (!factfinder.FactFinderAddress1.equals("")) {
//                FFaddress = factfinder.FactFinderAddress1;
//                if (!factfinder.FactFinderAddress2.equals("")) {
//                    FFaddress = "\n";
//                }
//            } else if (!factfinder.FactFinderAddress2.equals("")) {
//                FFaddress = factfinder.FactFinderAddress2;
//                if (!factfinder.FactFinderAddress3.equals("")) {
//                    FFaddress = "\n";
//                }
//            } else if (!factfinder.FactFinderAddress3.equals("")) {
//                FFaddress = factfinder.FactFinderAddress3;
//            }
//            processWordReplaceBookmark("FACTFINDERADDRESS", FFaddress, Document);
//            processWordReplaceBookmark("FACTFINDERCITY", factfinder.FactFinderCityStateZip.trim(), Document);
//        } else {
//            processWordReplaceBookmark("FACTFINDERADDRESS", "", Document);
//            processWordReplaceBookmark("FACTFINDERCITY", "", Document);
//        }
//        processWordReplaceBookmark("EMPORGREPNAME2", ucd.EmployeeOrgREPName.trim(), Document);
//
//        processWordReplaceBookmark("EMPORGREPPHONE", ucd.EmployeeOrgREPPhone, Document);
//
//        if (ucd.ResultsAppectedBy.equals("Union")) {
//            processWordReplaceBookmark("ACCEPTED", ucd.EmployeeOrgName, Document);
//            processWordReplaceBookmark("DEEMED", ucd.EmployerName, Document);
//        } else if (ucd.ResultsAppectedBy.equals("Employer")) {
//            processWordReplaceBookmark("ACCEPTED", ucd.EmployerName, Document);
//            processWordReplaceBookmark("DEEMED", ucd.EmployeeOrgName, Document);
//        }
//        processWordReplaceBookmark("EMPREPPHONE", ucd.EmployerREPPhone, Document);
//    }

    
}
