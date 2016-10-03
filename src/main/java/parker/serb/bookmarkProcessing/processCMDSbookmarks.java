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
import parker.serb.sql.CMDSCase;

/**
 *
 * @author Andrew
 */
public class processCMDSbookmarks {

    public static Dispatch processDoACMDSWordLetter(Dispatch Document) {
//        //get basic information  
//        CMDSCase item = CMDSCase.
//        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);
//
//        String repNames = "";
//        String officerNames = "";
//        String repAddressBlock = "";
//        String officerAddressBlock = "";
//        
//        for (CaseParty party : partyList){
//            if (null != party.caseRelation)switch (party.caseRelation) {
//                case "Representative":
//                    if (!"".equals(repNames.trim())){
//                        repNames += ", ";
//                    }
//                    if (!"".equals(repAddressBlock.trim())){
//                        repAddressBlock += "\n\n";
//                    }
//                    repAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
//                    repNames += StringUtilities.buildCasePartyName(party);
//                    break;
//                case "Chairman":
//                    if (!"".equals(officerNames.trim())){
//                        officerNames += ", ";
//                    }
//                    if (!"".equals(officerAddressBlock.trim())){
//                        officerAddressBlock += "\n\n";
//                    }
//                    officerAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
//                    officerNames += StringUtilities.buildCasePartyName(party);
//                    break;
//            }
//        }
//        
//        String orgAddressBlock = "";
//        CaseParty cscAddress = new CaseParty();
//        
//        cscAddress.address1 = item.address1;
//        cscAddress.address2 = item.address2;
//        cscAddress.city = item.city;
//        cscAddress.stateCode = item.state;
//        cscAddress.zipcode = item.zipCode;
//        orgAddressBlock = StringUtilities.buildCasePartyName(cscAddress);
//                
//        
//        
//        //ProcessBookmarks
//        for (int i = 0; i < Global.bookmarkLimit; i++) {
//            processBookmark.process("RepSalutation" + (i == 0 ? "" : i), repNames, Document);
//        }
//        
        return Document;
    }

    
}
