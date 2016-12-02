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
import parker.serb.sql.CSCCase;

/**
 *
 * @author Andrew
 */
public class processCSCbookmarks {

    public static Dispatch processDoACSCWordLetter(Dispatch Document, List<Integer> toParties, List<Integer> ccParties) {
        //get basic information  
        CSCCase item = CSCCase.loadCSCInformation();
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
                case "Chairman":
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
        CaseParty cscAddress = new CaseParty();
        
        cscAddress.address1 = item.address1;
        cscAddress.address2 = item.address2;
        cscAddress.city = item.city;
        cscAddress.stateCode = item.state;
        cscAddress.zipcode = item.zipCode;
        orgAddressBlock = StringUtilities.buildCasePartyName(cscAddress);
                
        
        
        //ProcessBookmarks
        for (int i = 0; i < Global.bookmarkLimit; i++) {
            processBookmark.process("RepSalutation" + (i == 0 ? "" : i), repNames, Document);
            
            processBookmark.process("CommissionName" + (i == 0 ? "" : i), item.name, Document);
            processBookmark.process("CommissionAddress1" + (i == 0 ? "" : i), item.address1, Document);
            processBookmark.process("CommissionAddress2" + (i == 0 ? "" : i), item.address2, Document);
            processBookmark.process("CommissionCity" + (i == 0 ? "" : i), item.city, Document);
            processBookmark.process("CommissionState" + (i == 0 ? "" : i), item.state, Document);
            processBookmark.process("CommissionZip" + (i == 0 ? "" : i), item.zipCode, Document);
            
            processBookmark.process("OrgAddress1" + (i == 0 ? "" : i), item.address1, Document);
            processBookmark.process("OrgAddress2" + (i == 0 ? "" : i), item.address2, Document);
            processBookmark.process("OrgCity" + (i == 0 ? "" : i), item.city, Document);
            processBookmark.process("OrgState" + (i == 0 ? "" : i), item.state, Document);
            processBookmark.process("OrgZipPlusFour" + (i == 0 ? "" : i), item.zipCode, Document);
        }
        
        return Document;
    }

    
}
