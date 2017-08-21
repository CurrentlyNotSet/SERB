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
import parker.serb.sql.ORGCase;
import parker.serb.util.StringUtilities;

/**
 *
 * @author Andrew
 */
public class processORGbookmarks {

    public static Dispatch processDoAORGWordLetter(Dispatch Document, boolean toRep, List<Integer> toParties, List<Integer> ccParties, ORGCase item) {
        //get basic information
        List<CaseParty> partyList = CaseParty.loadORGPartiesByCase("ORG", item.orgNumber);

        String repNames = "";
        String officerNames = "";
        String repAddressBlock = "";
        String officerAddressBlock = "";
        String toAddressBlock = "";
        String ccNameBlock = "";
        String certifiedDate = "";
        String OrgHeaderBlock = "";
        String repNameBookmark = "";
        String repSalutationBookmark = "";
        String orgAddressBookmark = "";
        String annualReport = "";
        String financialStatement = "";
        String constAndBylaws = "";
        

        for (CaseParty party : partyList){

            if (toParties != null) {
                for (int person : toParties) {
                    if (person == party.id) {
                        if (!"".equals(toAddressBlock.trim())) {
                            toAddressBlock += "\n\n";
                        }
                        toAddressBlock += StringUtilities.buildCasePartyAddressBlock(party);
                    }
                }
            }

            if (ccParties != null) {
                for (int person : ccParties) {
                    if (person == party.id) {
                        if (!"".equals(ccNameBlock.trim())) {
                            ccNameBlock += ",\n";
                        }
                        ccNameBlock += StringUtilities.buildCasePartyNameNoPreFix(party);
                    }
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
                    repAddressBlock += StringUtilities.buildORGBookmarkAddressWithLineBreaks(party);
                    repNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                    break;
                case "Officer":
                    if (!"".equals(officerNames.trim())){
                        officerNames += ", ";
                    }
                    if (!"".equals(officerAddressBlock.trim())){
                        officerAddressBlock += "\n\n";
                    }
                    officerAddressBlock += StringUtilities.buildORGBookmarkAddressWithLineBreaks(party);
                    officerNames += StringUtilities.buildCasePartyNameNoPreFix(party);
                    break;
            }
        }

        String orgAddressBlock = "";
        CaseParty orgAddress = new CaseParty();

        orgAddress.address1 = item.orgAddress1 == null ? "" : item.orgAddress1.trim();
        orgAddress.address2 = item.orgAddress2 == null ? "" : item.orgAddress2.trim();
        orgAddress.address3 = "";
        orgAddress.city = item.orgCity;
        orgAddress.stateCode = item.orgState;
        orgAddress.zipcode = item.orgZip;
        orgAddressBlock = StringUtilities.buildORGBookmarkAddressWithLineBreaks(orgAddress);

        if (item.certifiedDate != null){
            certifiedDate = Global.MMMMddyyyy.format(item.certifiedDate);
        }

        //PROD178 Additions
        if (item.annualReport != null){
            annualReport = Global.mmddyyyy.format(item.annualReport);
        }
        if (item.financialReport != null){
            financialStatement = Global.mmddyyyy.format(item.financialReport);
        }
        if (item.constitutionAndByLaws != null){
            constAndBylaws = Global.mmddyyyy.format(item.constitutionAndByLaws);
        }
                
        if (toRep){
            repNameBookmark = repNames;
            repSalutationBookmark = repNames;
            orgAddressBookmark = repAddressBlock;
            OrgHeaderBlock += repNames.trim();
            OrgHeaderBlock += OrgHeaderBlock.trim().equals("") ? item.orgName.trim() : "\n" + item.orgName.trim();
            OrgHeaderBlock += OrgHeaderBlock.trim().equals("") ? repAddressBlock.trim() : "\n" + repAddressBlock.trim();
        } else {
            repNameBookmark = "";
            repSalutationBookmark = item.orgName;
            orgAddressBookmark = orgAddressBlock;
            OrgHeaderBlock += OrgHeaderBlock.trim().equals("") ? item.orgName.trim() : "\n" + item.orgName.trim();
            OrgHeaderBlock += OrgHeaderBlock.trim().equals("") ? orgAddressBlock.trim() : "\n" + orgAddressBlock.trim();
        }

        //ProcessBookmarks
        for (int i = 0; i < Global.BOOKMARK_LIMIT; i++) {
            processBookmark.process("OrgHeaderBlock" + (i == 0 ? "" : i), OrgHeaderBlock, Document);
            processBookmark.process("RepName" + (i == 0 ? "" : i), repNameBookmark, Document);
            processBookmark.process("RepSalutation" + (i == 0 ? "" : i), repSalutationBookmark, Document);
            processBookmark.process("OrgAddress" + (i == 0 ? "" : i), orgAddressBookmark, Document);
            processBookmark.process("OrgName" + (i == 0 ? "" : i), item.orgName, Document);
            processBookmark.process("OrgNum" + (i == 0 ? "" : i), item.orgNumber, Document);
            processBookmark.process("CertifiedDate" + (i == 0 ? "" : i), certifiedDate, Document);
            processBookmark.process("CCList" + (i == 0 ? "" : i), ccNameBlock, Document);
            
            //PROD178 Additions
            processBookmark.process("ORGAnnReptLF" + (i == 0 ? "" : i), annualReport, Document);
            processBookmark.process("ORGFSLF" + (i == 0 ? "" : i), financialStatement, Document);
            processBookmark.process("ORGCONBYLAWSLF" + (i == 0 ? "" : i), constAndBylaws, Document);
        }
        return Document;
    }

}
