/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.com.Dispatch;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.CSCCase;
import parker.serb.sql.CaseParty;
import parker.serb.util.StringUtilities;

/**
 *
 * @author Andrew
 */
public class processCSCbookmarks {

    public static Dispatch processDoACSCWordLetter(Dispatch Document, List<Integer> toParties, List<Integer> ccParties, CSCCase cscCase) {
        //get basic information
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(null, "CSC", null, cscCase.cscNumber);

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
                    repNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                    break;
                case "Chairman":
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
        CaseParty cscAddress = new CaseParty();

        cscAddress.companyName = cscCase.name;
        cscAddress.address1 = cscCase.address1;
        cscAddress.address2 = cscCase.address2;
        cscAddress.city = cscCase.city;
        cscAddress.stateCode = cscCase.state;
        cscAddress.zipcode = cscCase.zipCode;
        orgAddressBlock = StringUtilities.buildAddressBlockWithLineBreaks(cscAddress);



        //ProcessBookmarks
        for (int i = 0; i < Global.BOOKMARK_LIMIT; i++) {
            processBookmark.process("RepSalutation" + (i == 0 ? "" : i), repNames, Document);

            processBookmark.process("CommissionAddressBlock" + (i == 0 ? "" : i), orgAddressBlock, Document);
            processBookmark.process("CommissionName" + (i == 0 ? "" : i), cscCase.name, Document);
            processBookmark.process("CommissionAddress1" + (i == 0 ? "" : i), cscCase.address1, Document);
            processBookmark.process("CommissionAddress2" + (i == 0 ? "" : i), cscCase.address2, Document);
            processBookmark.process("CommissionCity" + (i == 0 ? "" : i), cscCase.city, Document);
            processBookmark.process("CommissionState" + (i == 0 ? "" : i), cscCase.state, Document);
            processBookmark.process("CommissionZip" + (i == 0 ? "" : i), cscCase.zipCode, Document);
        }

        return Document;
    }


}
