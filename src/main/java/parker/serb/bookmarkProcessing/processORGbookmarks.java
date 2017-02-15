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

    public static Dispatch processDoAORGWordLetter(Dispatch Document, boolean toRep, List<Integer> toParties, List<Integer> ccParties, ORGCase item) {
        //get basic information  
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(null, "ORG", null, item.orgNumber);

        String repNames = "";
        String officerNames = "";
        String repAddressBlock = "";
        String officerAddressBlock = "";
        String toAddressBlock = "";
        String ccNameBlock = "";
        String certifiedDate = "";
        
        for (CaseParty party : partyList){
            
            for (int person : toParties){
                if (person == party.id) {
                     if (!"".equals(toAddressBlock.trim())){
                        toAddressBlock += "\n\n";
                    }
                     toAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                }
            }
            
            for (int person : ccParties){
                if (person == party.id) {
                     if (!"".equals(ccNameBlock.trim())){
                        ccNameBlock += ", ";
                    }
                     ccNameBlock += StringUtilities.buildCasePartyNameNoPreFix(party);
                }
            }
            
            if (null != party.caseRelation)switch (party.caseRelation) {
                case "Representative":
                    if (!"".equals(repNames.trim())){
                        repNames += ", ";
                    }
                    if (!"".equals(repAddressBlock.trim())){
                        repAddressBlock += "\n\n";
                    }
                    repAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                    repNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                    break;
                case "Officer":
                    if (!"".equals(officerNames.trim())){
                        officerNames += ", ";
                    }
                    if (!"".equals(officerAddressBlock.trim())){
                        officerAddressBlock += "\n\n";
                    }
                    officerAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                    officerNames += StringUtilities.buildCasePartyNameNoPreFix(party);
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
        orgAddressBlock = StringUtilities.buildCasePartyNameNoPreFix(orgAddress);
        
        if (item.certifiedDate != null){
            certifiedDate = Global.MMMMddyyyy.format(item.certifiedDate);
        }
                        
        //ProcessBookmarks
        for (int i = 0; i < Global.BOOKMARK_LIMIT; i++) {   
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
            processBookmark.process("CertifiedDate" + (i == 0 ? "" : i), certifiedDate, Document);
            
            processBookmark.process("CCList" + (i == 0 ? "" : i), ccNameBlock, Document);
        }
        return Document;
    }
    
}
