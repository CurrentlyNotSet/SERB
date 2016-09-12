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
import parker.serb.util.StringUtilities;
import parker.serb.sql.ORGCase;

/**
 *
 * @author Andrew
 */
public class processORGbookmarks {

    public static Dispatch processDoAORGWordLetter(Dispatch Document, boolean toRep) {
        //get basic information  
        ORGCase item = ORGCase.loadORGInformation();
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);

        String repNames = "";
        String officerNames = "";
        String repAddressBlock = "";
        String officerAddressBlock = "";
        
        for (CaseParty party : partyList){
            if (null != party.caseRelation)switch (party.caseRelation) {
                case "Representative":
                    if (!"".equals(repNames.trim())){
                        repNames += ", ";
                    }
                    if (!"".equals(repAddressBlock.trim())){
                        repAddressBlock += "\n\n";
                    }
                    repAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                    repNames += StringUtilities.buildCasePartyName(party);
                    break;
                case "Officer":
                    if (!"".equals(officerNames.trim())){
                        officerNames += ", ";
                    }
                    if (!"".equals(officerAddressBlock.trim())){
                        officerAddressBlock += "\n\n";
                    }
                    officerAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                    officerNames += StringUtilities.buildCasePartyName(party);
                    break;
            }
        }
        
        String orgAddressBlock = "";
        CaseParty orgAddress = new CaseParty();
        
        orgAddress.address1 = item.orgAddress1;
        orgAddress.address2 = item.orgAddress2;
        orgAddress.city = item.orgCity;
        orgAddress.stateCode = item.orgState;
        orgAddress.zipcode = item.orgZip;
        orgAddressBlock = StringUtilities.buildCasePartyName(orgAddress);
                
        
        
        //ProcessBookmarks
        for (int i = 0; i < Global.bookmarkLimit; i++) {
            
            if (toRep){
                processBookmark.process("RepName" + (i == 0 ? "" : i), repNames, Document);
                processBookmark.process("RepSalutation" + (i == 0 ? "" : i), repNames, Document);
                processBookmark.process("OrgAddress" + (i == 0 ? "" : i), repAddressBlock, Document);

            } else {
                processBookmark.process("RepName" + (i == 0 ? "" : i), "", Document);
                processBookmark.process("RepSalutation" + (i == 0 ? "" : i), item.orgName, Document);
                processBookmark.process("OrgAddress" + (i == 0 ? "" : i), orgAddressBlock, Document);
            }

            processBookmark.process("OrgName" + (i == 0 ? "" : i), item.orgName, Document);
            processBookmark.process("OrgNum" + (i == 0 ? "" : i), item.orgNumber, Document);
            processBookmark.process("CertifiedDate" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(item.certifiedDate), Document);
            
        }
        
        return Document;
    }
    
    
    
    

//        if (toRep) {
//            processWordReplaceBookmark("RepFirstName", eod.RepFirstName, Document);
//            processWordReplaceBookmark("RepMiddleName", eod.RepMiddleInitial, Document);
//            processWordReplaceBookmark("RepLastName", eod.RepLastName, Document);
//    
//            processWordReplaceBookmark("OrgAddress1", eod.RepAddress1, Document);
//            processWordReplaceBookmark("OrgAddress2", eod.RepAddress2, Document);
//            processWordReplaceBookmark("OrgCity", eod.RepCity, Document);
//            processWordReplaceBookmark("OrgState", eod.RepState, Document);
//            processWordReplaceBookmark("OrgZipPlusFive", eod.RepZipPlusFive, Document);
//
//            processWordReplaceBookmark("RepSalutation", eod.RepFirstName + " " + eod.RepMiddleInitial + " " + eod.RepLastName, Document);
//        } else {
//            processWordReplaceBookmark("RepFirstName", "", Document);
//            processWordReplaceBookmark("RepMiddleName", "", Document);
//            processWordReplaceBookmark("RepLastName", "", Document);
//            
//            processWordReplaceBookmark("OrgAddress1", eod.OrgAddress1, Document);
//            processWordReplaceBookmark("OrgAddress2", eod.OrgAddress2, Document);
//            processWordReplaceBookmark("OrgCity", eod.OrgCity, Document);
//            processWordReplaceBookmark("OrgState", eod.OrgState, Document);
//            processWordReplaceBookmark("OrgZipPlusFive", eod.OrgZipPlusFive, Document);
//
//            processWordReplaceBookmark("RepSalutation", eod.OrgName, Document);
//        }
    
}
