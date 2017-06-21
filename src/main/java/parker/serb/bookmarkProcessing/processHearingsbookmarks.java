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
            ALJName = User.getFullNameByID(item.aljID).toUpperCase();
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
        switch (section) {
            case "MED":
                return buildMEDHearingParties(partyList);
            case "REP":
                return buildREPHearingParties(partyList);
            case "ULP":
                return buildULPHearingParties(partyList);
            default :
                return "";
        }
    }

    private static String buildMEDHearingParties(List<CaseParty> partyList) {
        String employeeOrganization = "";
        String employer = "";

        for (CaseParty party : partyList) {
            if (null != party.caseRelation) {
                switch (party.caseRelation.toLowerCase()) {
                    case "employee organization":
                        employeeOrganization += (employeeOrganization.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party) + ", ";
                        break;
                    case "employer":
                        employer += (employer.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party) + ", ";
                        break;
                }
            }
        }
                
        String HEARINGPARTIES = "";

        HEARINGPARTIES += employeeOrganization.toUpperCase();
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Employee Organization,";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "    and";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += employer.toUpperCase();
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Employer.";

        return HEARINGPARTIES;
    }

    private static String buildULPHearingParties(List<CaseParty> partyList) {
        String complaintant = "";
        String respondent = "";

        for (CaseParty party : partyList) {
            if (null != party.caseRelation) {
                switch (party.caseRelation.toLowerCase()) {
                    case "complaintant":
                    case "complainant":
                        complaintant += (complaintant.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party) + ", ";
                        break;
                    case "respondent":
                    case "respondant":
                    case "charged party":
                        respondent += (respondent.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party) + ", ";
                        break;
                }
            }
        }
        
        String HEARINGPARTIES = "";

        HEARINGPARTIES += complaintant.toUpperCase();
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Complainant,";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "    v.";
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += respondent.toUpperCase();
        HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
        HEARINGPARTIES += "Respondent.";

        return HEARINGPARTIES;
    }

    private static String buildREPHearingParties(List<CaseParty> partyList) {
        String conversionSchool = "";
        String employeeOrganization = "";
        String incumbentEmployeeOrganization = "";
        String petitioner = "";
        String rivalEmployeeOrganization = "";
        String intervener = "";
        String rivalemployeeOrginizationTwo = "";
        String rivalemployeeOrginizationThree = "";

        for (CaseParty party : partyList) {
            if (null != party.caseRelation) {
                switch (party.caseRelation.toLowerCase()) {
                    case "conversion school":
                        conversionSchool += (conversionSchool.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "employee organization":
                        employeeOrganization += (employeeOrganization.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "incumbent employee organization":
                        incumbentEmployeeOrganization += (incumbentEmployeeOrganization.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "petitioner":
                        petitioner += (petitioner.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "rival employee organization":
                        rivalEmployeeOrganization += (rivalEmployeeOrganization.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "intervener":
                        intervener += (intervener.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "rival employee organization 2":
                        rivalemployeeOrginizationTwo += (rivalemployeeOrginizationTwo.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                    case "rival employee organization 3":
                        rivalemployeeOrginizationThree += (rivalemployeeOrginizationThree.trim().equals("") ? "" : System.lineSeparator()) + StringUtilities.buildCasePartyNameNoPreFix(party);
                        break;
                }
            }
        }
                
        String seperatingText = System.lineSeparator() + System.lineSeparator();
        seperatingText += "    and";
        seperatingText += System.lineSeparator() + System.lineSeparator();

        String HEARINGPARTIES = "";
        
        if (!conversionSchool.trim().equals("")) {
            HEARINGPARTIES += HEARINGPARTIES.trim().equals("") ? "" : seperatingText;
            HEARINGPARTIES += conversionSchool;
            HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
            HEARINGPARTIES += "Rival Employee Organization,";
        }
        
        if (!employeeOrganization.trim().equals("")) {
            HEARINGPARTIES += HEARINGPARTIES.trim().equals("") ? "" : seperatingText;
            HEARINGPARTIES += employeeOrganization;
            HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
            HEARINGPARTIES += "Employee Organization,";
        }
        
        if (!incumbentEmployeeOrganization.trim().equals("")) {
            HEARINGPARTIES += HEARINGPARTIES.trim().equals("") ? "" : seperatingText;
            HEARINGPARTIES += incumbentEmployeeOrganization;
            HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
            HEARINGPARTIES += "Incumbent Employee Organization,";
        }
        
        if (!petitioner.trim().equals("")) {
            HEARINGPARTIES += HEARINGPARTIES.trim().equals("") ? "" : seperatingText;
            HEARINGPARTIES += petitioner;
            HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
            HEARINGPARTIES += "Petitioner,";
        }
        
        if (!rivalEmployeeOrganization.trim().equals("")) {
            HEARINGPARTIES += HEARINGPARTIES.trim().equals("") ? "" : seperatingText;
            HEARINGPARTIES += rivalEmployeeOrganization;
            HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
            HEARINGPARTIES += "Rival Employee Organization,";
        }

        if (!intervener.trim().equals("")) {
            HEARINGPARTIES += HEARINGPARTIES.trim().equals("") ? "" : seperatingText;
            HEARINGPARTIES += intervener;
            HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
            HEARINGPARTIES += "Intervener,";
        }
        
        if (!rivalemployeeOrginizationTwo.trim().equals("")) {
            HEARINGPARTIES += HEARINGPARTIES.trim().equals("") ? "" : seperatingText;
            HEARINGPARTIES += rivalemployeeOrginizationTwo;
            HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
            HEARINGPARTIES += "Rival Employee Organization,";
        }
        
        if (!rivalemployeeOrginizationThree.trim().equals("")) {
            HEARINGPARTIES += HEARINGPARTIES.trim().equals("") ? "" : seperatingText;
            HEARINGPARTIES += rivalemployeeOrginizationThree;
            HEARINGPARTIES += System.lineSeparator() + System.lineSeparator();
            HEARINGPARTIES += "Rival Employee Organization,";
        }
        
        return HEARINGPARTIES.substring(0, (HEARINGPARTIES.length() - 1)) + ".";
    }
}
