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
import parker.serb.sql.HearingCase;
import parker.serb.sql.User;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class processHearingsbookmarks {

    public static Dispatch processDoAHearingsWordLetter(Dispatch Document, String section) {
        //get basic information

        HearingCase item = HearingCase.loadHearingCaseInformation();
        List<CaseParty> partyList = CaseParty.loadPartiesByCase(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber);

        String Respondent = "";
        String HearingCCList = "";
        String HearingParties = buildHearingParties(partyList, section);
        String ALJName = "";

        for (CaseParty party : partyList) {
            if (null != party.caseRelation) {
                switch (party.caseRelation.toLowerCase()) {
                    case "respondent":
                    case "respondant":
                    case "charged party":
                        Respondent += (Respondent.trim().equals("") ? ""
                                : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                }
            }

            HearingCCList += (HearingCCList.trim().equals("")
                    ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameCMDSCCBlock(party);
        }

        if (item.aljID > 0) {
            ALJName = User.getFullNameByID(item.aljID);
        }

        //ProcessBookmarks
        for (int i = 0; i < Global.BOOKMARK_LIMIT; i++) {
            processBookmark.process("ALJAssigned" + (i == 0 ? "" : i), ALJName, Document);
            processBookmark.process("HEARINGPARTIES" + (i == 0 ? "" : i), HearingParties, Document);
            processBookmark.process("HearingCCList" + (i == 0 ? "" : i), HearingCCList, Document);
            processBookmark.process("Respondent" + (i == 0 ? "" : i), Respondent, Document);
        }
        return Document;
    }


    private static String buildHearingParties(List<CaseParty> partyList, String section) {
        String employeeOrganization = "";
        String employer = "";
        String complaintant = "";
        String respondent = "";
        String rivalEmployeeOrganization = "";

        for (CaseParty party : partyList) {
            if (null != party.caseRelation) {
                switch (party.caseRelation.toLowerCase()) {
                    case "employee organization":
                        employeeOrganization += (employeeOrganization.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "employer":
                        employer += (employer.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "complaintant":
                    case "complainant":
                        complaintant += (complaintant.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "respondent":
                    case "respondant":
                    case "charged party":
                        respondent += (respondent.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "rival employee organization":
                        rivalEmployeeOrganization += (rivalEmployeeOrganization.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                }
            }
        }

        switch (section) {
            case "MED":
                return buildMEDHearingParties(employeeOrganization, employer);
            case "REP":
                return buildREPHearingParties(rivalEmployeeOrganization, employer);
            case "ULP":
                return buildULPHearingParties(complaintant, respondent);
            default :
                return "";
        }
    }

    private static String buildMEDHearingParties(String v1, String v2) {
        String HEARINGPARTIES = "";

        HEARINGPARTIES += v1;
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Employee Organization,";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "    and";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += v2;
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Employer.";

        return HEARINGPARTIES;
    }

    private static String buildULPHearingParties(String v1, String v2) {
        String HEARINGPARTIES = "";

        HEARINGPARTIES += v1;
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Complainant,";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "    and";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += v2;
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Respondent.";

        return HEARINGPARTIES;
    }

    private static String buildREPHearingParties(String v1, String v2) {
        String HEARINGPARTIES = "";

        HEARINGPARTIES += v1;
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Rival Employee Organization,";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "    and";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += v2;
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Employer";

        return HEARINGPARTIES;
    }
}
