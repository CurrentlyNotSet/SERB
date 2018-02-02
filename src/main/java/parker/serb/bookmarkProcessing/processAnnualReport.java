/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.com.Dispatch;
import parker.serb.sql.AnnualReport;

/**
 *
 * @author User
 */
public class processAnnualReport {

    public static Dispatch processAnnualReportTemplate(Dispatch Document, String startDate, String endDate) {

        //place dates in header
        processBookmark.process("Dates", startDate + " - " + endDate, Document);

        //FIRST SECTION - COMPLETED
        //2 Total MED Cases
        long two = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.caseType = 'MED')");
        processBookmark.process("two", String.valueOf(two), Document);
        //3
        long three = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.caseType = 'STK')");
        processBookmark.process("three", String.valueOf(three), Document);
        //4
        long four = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM REPCase WHERE (REPCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (REPCase.caseType = 'REP')");
        processBookmark.process("four", String.valueOf(four), Document);
        //5
        long five = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM REPCase WHERE (REPCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (REPCase.caseType = 'RBT')");
        processBookmark.process("five", String.valueOf(five), Document);
        //6
        long six = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM ULPCase WHERE (ULPCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (ULPCase.caseType = 'ULP')");
        processBookmark.process("six", String.valueOf(six), Document);
        //7
        long seven = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM ULPCase WHERE (ULPCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (ULPCase.caseType = 'ERC')");
        processBookmark.process("seven", String.valueOf(seven), Document);
        //8
        long eight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM ULPCase WHERE (ULPCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (ULPCase.caseType = 'JWD')");
        processBookmark.process("eight", String.valueOf(eight), Document);
        //1 Sum of 2-8
        long one = two + three + four + five + six + seven + eight;
        processBookmark.process("one", String.valueOf(one), Document);
        //END OF FIRST SECTION

        //START OF SECOND SECTION
        //9 - Modified due to request R3-030
        long nine = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.mediatorAppointedDate BETWEEN '" 
                + startDate + "' AND '" + endDate + "') AND (MEDCase.stateMediatorAppointedID IS NOT NULL)");
        processBookmark.process("nine", String.valueOf(nine), Document);
        //10 - Modified due to request R3-030
        long ten = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.mediatorAppointedDate BETWEEN '" 
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FMCSMediatorAppointedID IS NOT NULL) "
                        + "AND (MEDCase.mediatorAppointedDate IS NOT NULL)");
        processBookmark.process("ten", String.valueOf(ten), Document);
        //11
        long oneone = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFOriginalFactFinderDate BETWEEN '" + startDate + "' AND '" + endDate
                + "') OR (MEDCase.FFAppointmentDate BETWEEN '" + startDate + "' AND '" + endDate + "')");
        processBookmark.process("oneone", String.valueOf(oneone), Document);
        //12
        long onetwo = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.concilOriginalConcilDate BETWEEN '" + startDate + "' AND '" + endDate
                + "') OR (MEDCase.concilAppointmentDate BETWEEN '" + startDate + "' AND '" + endDate + "')");
        processBookmark.process("onetwo", String.valueOf(onetwo), Document);
        //13
        long onethree = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.strikeBegan BETWEEN '" + startDate + "' AND '" + endDate
                + "') AND (MEDCase.strikeOccured = 'Yes' AND MEDCase.strikeBegan IS NOT NULL)");
        processBookmark.process("onethree", String.valueOf(onethree), Document);
        //14
        long onefour = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM REPCase WHERE (REPCase.ballotsCountDate BETWEEN '" 
                + startDate + "' AND '" + endDate + "') AND (REPCase.resultWhoPrevailed IS NOT NULL)");
        processBookmark.process("onefour", String.valueOf(onefour), Document);
        //15 Same as #93 - NOT CORRECT - ERROR
        long onefive = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.complaintIssuedDate BETWEEN '" + startDate + "' AND '" + endDate + "')");
        processBookmark.process("onefive", String.valueOf(onefive), Document);
        //16  - NEEDS MORE SQL
        ////long onesix = AnnualReport.getCount();
        processBookmark.process("onesix", "MAN", Document);
        //17 - NEED SQL
        ////long oneseven = AnnualReport.getCount();
        processBookmark.process("oneseven", "MAN", Document);
        //18 - NEED SQL
        long oneeight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.issuanceOfOptionOrDirectiveDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (HearingCase.opinion IS NOT NULL)");
        processBookmark.process("oneeight", String.valueOf(oneeight), Document);
        //END OF SECOND SECTION

        //START OF THIRD SECTION
        //19 - Modified due to request R3-030
        long onenine = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingsMediation WHERE (HearingsMediation.MediationDate BETWEEN '" 
                + startDate + "' AND '" + endDate + "') AND (HearingsMediation.PCPreD = 'Pre-D' OR HearingsMediation.PCPreD = 'PreD') "
                        + "AND (HearingsMediation.caseType = 'ULP')");
        processBookmark.process("onenine", String.valueOf(onenine), Document);
        //20
        long twozero = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingsMediation WHERE (HearingsMediation.MediationDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (HearingsMediation.PCPreD = 'PC') AND (HearingsMediation.caseType = 'ULP')");
        processBookmark.process("twozero", String.valueOf(twozero), Document);
        //21 - Modified due to request R3-030
        long twoone = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingsMediation WHERE (HearingsMediation.MediationDate BETWEEN '" 
                + startDate + "' AND '" + endDate + "') AND (HearingsMediation.PCPreD = 'Pre-D' OR HearingsMediation.PCPreD = 'PreD') "
                        + "AND (HearingsMediation.caseType = 'REP')");
        processBookmark.process("twoone", String.valueOf(twoone), Document);
        //22
        long twotwo = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingsMediation WHERE (HearingsMediation.MediationDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (HearingsMediation.PCPreD = 'PC') AND (HearingsMediation.caseType = 'REP')");
        processBookmark.process("twotwo", String.valueOf(twotwo), Document);
        //23 Total of 19-22
        long twothree = onenine + twozero + twoone + twotwo;
        processBookmark.process("twothree", String.valueOf(twothree), Document);
        //END OF THIRD SECTION

        //START OF FOURTH SECTION
        //25
        long twofive = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.settlementDate BETWEEN '"
                + startDate + "' AND '" + endDate + "')");
        processBookmark.process("twofive", String.valueOf(twofive), Document);
        //26
        long twosix = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM REPCase WHERE (REPCase.ballotsCountDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (REPCase.boardActionType LIKE '%cert results%')");
        processBookmark.process("twosix", String.valueOf(twosix), Document);
        //27
        long twoseven = AnnualReport.getCount("SELECT COUNT(DISTINCT (BoardMeeting.caseYear + '-' + BoardMeeting.caseType + '-' + BoardMeeting.caseMonth + '-' + BoardMeeting.caseNumber)) "
                + "AS COLUMN1 FROM BoardMeeting LEFT JOIN REPCase ON (BoardMeeting.caseYear = REPCase.caseYear "
                + "AND BoardMeeting.caseType = REPCase.caseType AND BoardMeeting.caseMonth = REPCase.caseMonth "
                + "AND BoardMeeting.caseNumber = REPCase.caseNumber) WHERE (BoardMeeting.boardMeetingDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (REPCase.boardActionType LIKE '%cert vr%' OR REPCase.boardActionType LIKE '%cert opt-in vr%')");
        processBookmark.process("twoseven", String.valueOf(twoseven), Document);
        //28
        long twoeight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM BoardMeeting INNER JOIN REPCase ON (BoardMeeting.caseYear = REPCase.caseYear "
                + "AND BoardMeeting.caseType = REPCase.caseType AND BoardMeeting.caseMonth = REPCase.caseMonth "
                + "AND BoardMeeting.caseNumber = REPCase.caseNumber) WHERE (BoardMeeting.boardMeetingDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (REPCase.boardActionType LIKE 'dism rd' "
                + "OR REPCase.boardActionType LIKE 'dism rc' OR REPCase.boardActionType LIKE 'dism vr' "
                + "OR REPCase.boardActionType LIKE 'dism opt-in rc' "
                + "OR REPCase.boardActionType LIKE 'dism' "
                + "OR REPCase.boardActionType LIKE 'dism opt-in vr')");
        processBookmark.process("twoeight", String.valueOf(twoeight), Document);
        //29
        long twonine = AnnualReport.getCount("SELECT COUNT(DISTINCT (BoardMeeting.caseYear + '-' + BoardMeeting.caseType + '-' + BoardMeeting.caseMonth + '-' + BoardMeeting.caseNumber)) AS COLUMN1 "
                + "FROM BoardMeeting LEFT JOIN REPCase ON (BoardMeeting.caseYear = REPCase.caseYear "
                + "AND BoardMeeting.caseType = REPCase.caseType AND BoardMeeting.caseMonth = REPCase.caseMonth "
                + "AND BoardMeeting.caseNumber = REPCase.caseNumber) WHERE (BoardMeeting.boardMeetingDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (REPCase.boardActionType LIKE '%approve%' "
                + "OR REPCase.boardActionType LIKE '%appr ac%' OR REPCase.boardActionType LIKE '%appr jtac%' "
                + "OR REPCase.boardActionType LIKE '%dism ac%' OR REPCase.boardActionType LIKE '%dism jtac%' "
                + "OR REPCase.boardActionType LIKE '%appr uc%' OR REPCase.boardActionType LIKE '%dism uc%' "
                + "OR REPCase.boardActionType LIKE '%revoke%')");
        processBookmark.process("twonine", String.valueOf(twonine), Document);
        //30
        long threezero = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM BoardMeeting LEFT JOIN REPCase ON (BoardMeeting.caseYear = REPCase.caseYear "
                + "AND BoardMeeting.caseType = REPCase.caseType AND BoardMeeting.caseMonth = REPCase.caseMonth "
                + "AND BoardMeeting.caseNumber = REPCase.caseNumber) WHERE (BoardMeeting.boardMeetingDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (REPCase.boardActionType LIKE '%dism rbt%' "
                + "OR REPCase.boardActionType LIKE '%approve rbt%')");
        processBookmark.process("threezero", String.valueOf(threezero), Document);
        //31
        long threeone = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM BoardMeeting LEFT JOIN ULPCase ON (BoardMeeting.caseYear = ULPCase.caseYear "
                + "AND BoardMeeting.caseType = ULPCase.caseType AND BoardMeeting.caseMonth = ULPCase.caseMonth "
                + "AND BoardMeeting.caseNumber = ULPCase.caseNumber) WHERE (BoardMeeting.boardMeetingDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (BoardMeeting.recommendation LIKE 'DM%') AND "
                        + "(BoardMeeting.caseType = 'ULP' OR BoardMeeting.caseType = 'ERC' OR BoardMeeting.caseType = 'JWD') "
                        + "AND (ULPCase.dismissalDate IS NOT NULL)");
        processBookmark.process("threeone", String.valueOf(threeone), Document);
        //32
        long threetwo = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM ULPCase WHERE (ULPCase.dismissalDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (ULPCase.caseType != 'ERC' OR ULPCase.caseType != 'JWD') "
                + "AND ULPCase.finalDispositionStatus LIKE '%W%' AND ULPCase.dismissalDate IS NOT NULL");
        processBookmark.process("threetwo", String.valueOf(threetwo), Document);
        //33
        long threethree = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM BoardMeeting LEFT JOIN ULPCase ON "
                + "(BoardMeeting.caseYear = ULPCase.caseYear AND BoardMeeting.caseType = ULPCase.caseType "
                + "AND BoardMeeting.caseMonth = ULPCase.caseMonth AND BoardMeeting.caseNumber = ULPCase.caseNumber) "
                + "WHERE (BoardMeeting.recommendation LIKE 'DF%' AND BoardMeeting.boardMeetingDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (ULPCase.caseType != 'ERC' OR ULPCase.caseType != 'JWD')");
        processBookmark.process("threethree", String.valueOf(threethree), Document);
        //34
        long threefour = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.boardActionDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (HearingCase.caseType = 'ULP') "
                + "AND (HearingCase.finalResult = 'Settled') AND (HearingCase.complaintIssuedDate IS NOT NULL)");
        processBookmark.process("threefour", String.valueOf(threefour), Document);
        //24 Total of 25-34
        long twofour = twofive + twosix + twoseven + twoeight + twonine + threezero + threethree + threefour;
        processBookmark.process("twofour", String.valueOf(twofour), Document);
        //END OF THE FOURTH SECTION

        //Start of the 5th section
        //36
        long threesix = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.caseType = 'MED')");
        processBookmark.process("threesix", String.valueOf(threesix), Document);
        //38 - Modified due to request R3-030
        long threeeight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.caseType = 'STK')");
        processBookmark.process("threeeight", String.valueOf(threeeight), Document);
        //39 - Modified due to request R3-030
        long threenine = nine + ten;
        processBookmark.process("threenine", String.valueOf(threenine), Document);
        //40 - Modified due to request R3-030
        long fourzero = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFOriginalFactFinderDate BETWEEN '" + startDate + "' AND '" + endDate
                + "') OR (MEDCase.FFAppointmentDate BETWEEN '" + startDate + "' AND '" + endDate + "')");
        processBookmark.process("fourzero", String.valueOf(fourzero), Document);
        //41 - Modified due to request R3-030
        long fourone = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.concilOriginalConcilDate BETWEEN '" + startDate + "' AND '" + endDate
                + "') OR (MEDCase.concilAppointmentDate BETWEEN '" + startDate + "' AND '" + endDate + "')");
        processBookmark.process("fourone", String.valueOf(fourone), Document);
        //end of the 5th section

        //start of the 6th section
        //42
        long fourtwo = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.negotiationType LIKE 'SI%')");
        processBookmark.process("fourtwo", String.valueOf(fourtwo), Document);
        //43
        long fourthree = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.negotiationType LIKE 'SR%')");
        processBookmark.process("fourthree", String.valueOf(fourthree), Document);
        //44
        long fourfour = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.negotiationType LIKE 'SE%')");
        processBookmark.process("fourfour", String.valueOf(fourfour), Document);
        //45
        long fourfive = fourtwo + fourthree + fourfour;
        processBookmark.process("fourfive", String.valueOf(fourfive), Document);
        //46
        long foursix = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.negotiationType LIKE 'ZI%')");
        processBookmark.process("foursix", String.valueOf(foursix), Document);
        //47
        long fourseven = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.negotiationType LIKE 'ZR%')");
        processBookmark.process("fourseven", String.valueOf(fourseven), Document);
        //48
        long foureight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.negotiationType LIKE 'ZE%')");
        processBookmark.process("foureight", String.valueOf(foureight), Document);
        //49
        long fournine = foursix + fourseven + foureight;
        processBookmark.process("fournine", String.valueOf(fournine), Document);
        //50
        long fivezero = fourtwo + foursix;
        processBookmark.process("fivezero", String.valueOf(fivezero), Document);
        //51
        long fiveone = fourthree + fourseven;
        processBookmark.process("fiveone", String.valueOf(fiveone), Document);
        //52
        long fivetwo = fourfour + foureight;
        processBookmark.process("fivetwo", String.valueOf(fivetwo), Document);
        //53
        long fivethree = fourfive + fournine;
        processBookmark.process("fivethree", String.valueOf(fivethree), Document);
        //END of the 6th section

        //start of the 7th section
        //56
        long fivesix = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN + '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFAcceptedBy = 'Both')");
        processBookmark.process("fivesix", String.valueOf(fivesix), Document);

        //58
        long fiveeight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN + '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFDeemedAcceptedBy = 'Union') "
                + "AND (MEDCase.FFOverallResult = 'Accepted')");
        processBookmark.process("fiveeight", String.valueOf(fiveeight), Document);
        //59
        long fivenine = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFDeemedAcceptedBy = 'Employer') "
                + "AND (MEDCase.FFOverallResult = 'Accepted')");
        processBookmark.process("fivenine", String.valueOf(fivenine), Document);
        //60
        long sixzero = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFDeemedAcceptedBy = 'Both') "
                + "AND (MEDCase.FFOverallResult = 'Accepted')");
        processBookmark.process("sixzero", String.valueOf(sixzero), Document);
        //62
        long sixtwo = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFRejectedBy = 'Union') "
                + "AND (MEDCase.FFOverallResult = 'Rejected')");
        processBookmark.process("sixtwo", String.valueOf(sixtwo), Document);
        //63
        long sixthree = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFRejectedBy = 'Employer') "
                + "AND (MEDCase.FFOverallResult = 'Rejected')");
        processBookmark.process("sixthree", String.valueOf(sixthree), Document);
        //64
        long sixfour = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFRejectedBy = 'Both') "
                + "AND (MEDCase.FFOverallResult = 'Rejected')");
        processBookmark.process("sixfour", String.valueOf(sixfour), Document);
        //57
        long fiveseven = fiveeight + fivenine + sixzero;
        processBookmark.process("fiveseven", String.valueOf(fiveseven), Document);
        //55
        long fivefive = fivesix + fiveseven;
        processBookmark.process("fivefive", String.valueOf(fivefive), Document);
        //61
        long sixone = sixtwo + sixthree + sixfour;
        processBookmark.process("sixone", String.valueOf(sixone), Document);
        //65
        long sixfive = fivefive + sixone;
        processBookmark.process("sixfive", String.valueOf(sixfive), Document);
        //end of the 7th section

        //start of the 8th section
        //66
        long sixsix = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployerType = 'City')");
        processBookmark.process("sixsix", String.valueOf(sixsix), Document);
        //67
        long sixseven = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployerType = 'County')");
        processBookmark.process("sixseven", String.valueOf(sixseven), Document);
        //68
        long sixeight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployerType = 'School District')");
        processBookmark.process("sixeight", String.valueOf(sixeight), Document);
        //69
        long sixnine = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployerType = 'Township')");
        processBookmark.process("sixnine", String.valueOf(sixnine), Document);
        //70
        long sevenzero = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployerType = 'University')");
        processBookmark.process("sevenzero", String.valueOf(sevenzero), Document);
        //71
        long sevenone = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployerType = 'State Govt.')");
        processBookmark.process("sevenone", String.valueOf(sevenone), Document);
        //72
        long seventwo = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployerType = 'Other')");
        processBookmark.process("seventwo", String.valueOf(seventwo), Document);
        //End of the 8th section

        //Start of the 9th section
        //73
        long seventhree = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFOverallResult = 'Rejected')");
        processBookmark.process("sevenfour", String.valueOf(seventhree), Document);
        //74
        long sevenfour = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFOverallResult = 'Accepted')");
        processBookmark.process("seventhree", String.valueOf(sevenfour), Document);
        //end of the 9th section

        //start of 10th section
        //75
        long sevenfive = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployeeType = 'Police')");
        processBookmark.process("sevenfive", String.valueOf(sevenfive), Document);
        //76
        long sevensix = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployeeType = 'Fire')");
        processBookmark.process("sevensix", String.valueOf(sevensix), Document);
        //77
        long sevenseven = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployeeType = 'Teaching')");
        processBookmark.process("sevenseven", String.valueOf(sevenseven), Document);
        //78
        long seveneight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployeeType = 'Nursing')");
        processBookmark.process("seveneight", String.valueOf(seveneight), Document);
        //79
        long sevennine = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.FFReportIssueDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.FFEmployeeType = 'Other')");
        processBookmark.process("sevennine", String.valueOf(sevennine), Document);
        //end of the 10th section

        //start of the 11th section
        //80
        long eightzero = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.strikeBegan BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.strikeOccured = 'Yes') AND (MEDCase.FFEmployerType = 'School Dist%')");
        processBookmark.process("eightzero", String.valueOf(eightzero), Document);
        //81
        long eightone = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.strikeBegan BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.strikeOccured = 'Yes') AND (MEDCase.FFEmployerType = 'City')");
        processBookmark.process("eightone", String.valueOf(eightone), Document);
        //82
        long eighttwo = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.strikeBegan BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.strikeOccured = 'Yes') AND (MEDCase.FFEmployerType = 'County')");
        processBookmark.process("eighttwo", String.valueOf(eighttwo), Document);
        //83
        long eightthree = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.strikeBegan BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.strikeOccured = 'Yes') AND (MEDCase.FFEmployerType = 'Township')");
        processBookmark.process("eightthree", String.valueOf(eightthree), Document);
        //84
        long eightfour = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM MEDCase WHERE (MEDCase.strikeBegan BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (MEDCase.strikeOccured = 'Yes') AND (MEDCase.FFEmployerType = 'Other')");
        processBookmark.process("eightfour", String.valueOf(eightfour), Document);
        //85
        long eightfive = eightzero + eightone + eighttwo + eightthree + eightfour;
        processBookmark.process("eightfive", String.valueOf(eightfive), Document);
        //end of the 11th section

        //start of the 12th section
        //86
        long eightsix = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM REPCase WHERE (REPCase.pollingEndDate BETWEEN '" 
                + startDate + "' AND '" + endDate + "')");
        processBookmark.process("eightsix", String.valueOf(eightsix), Document);
        //87
        long eightseven = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM REPCase WHERE (REPCase.pollingEndDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (REPCase.ProfessionalNonProfessional = 1)");
        processBookmark.process("eightseven", String.valueOf(eightseven), Document);
        //88
        long eighteight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM REPCase WHERE "
                + "(REPCase.pollingEndDate BETWEEN  '" + startDate + "' AND '" + endDate + "') AND "
                + "(REPCase.pollingStartDate IS NOT NULL) AND (REPCase.pollingEndDate IS NOT NULL) AND "
                + "(REPCase.resultWhoPrevailed != 0 AND REPCase.resultWhoPrevailed IS NOT NULL)");
        processBookmark.process("eighteight", String.valueOf(eighteight), Document);
        //89 - Modified due to request R3-030
        long eightnine = AnnualReport.getCount("SELECT (SUM(CASE WHEN REPCase.combinedApproxNumberEligible IS NULL THEN 0 ELSE REPCase.combinedApproxNumberEligible END)  "
                + "+ SUM(CASE WHEN REPCase.professionalApproxNumberEligible IS NULL THEN 0 ELSE REPCase.professionalApproxNumberEligible END) "
                + "+ SUM(CASE WHEN REPCase.nonprofessionalApproxNumberEligible IS NULL THEN 0 ELSE REPCase.nonprofessionalApproxNumberEligible END) "
                + "+ SUM(CASE WHEN REPCase.resultApproxNumberEligibleVoters IS NULL THEN 0 ELSE REPCase.resultApproxNumberEligibleVoters END)) AS COLUMN1 FROM REPCase WHERE "
                + "(REPCase.ballotsCountDate BETWEEN '" + startDate + "' AND '" + endDate + "')");
        processBookmark.process("eightnine", String.valueOf(eightnine), Document);
        //90 - Modified due to request R3-030
        long ninezero = AnnualReport.getCount("SELECT (SUM(CASE WHEN REPCase.combinedTotalVotes IS NULL THEN 0 ELSE REPCase.combinedTotalVotes END) "
                + "+ SUM(CASE WHEN REPCase.professionalTotalVotes IS NULL THEN 0 ELSE REPCase.professionalTotalVotes END ) "
                + "+ SUM(CASE WHEN REPCase.nonprofessionalTotalVotes IS NULL THEN 0 ELSE REPCase.nonprofessionalTotalVotes END) "
                + "+ SUM(CASE WHEN REPCase.resultTotalBallotsCast IS NULL THEN 0 ELSE REPCase.resultTotalBallotsCast END)) AS COLUMN1 FROM REPCase WHERE (REPCase.ballotsCountDate "
                + "BETWEEN '" + startDate + "' AND '" + endDate + "')");
        processBookmark.process("ninezero", String.valueOf(ninezero), Document);
        //91
        long nineone = AnnualReport.getCount("SELECT COUNT(DISTINCT (BoardMeeting.caseYear + '-' + BoardMeeting.caseType + '-' + BoardMeeting.caseMonth + '-' + BoardMeeting.caseNumber)) "
                + "AS COLUMN1 FROM BoardMeeting LEFT JOIN REPCase "
                + "ON (BoardMeeting.caseYear = REPCase.caseYear AND BoardMeeting.caseType = REPCase.caseType "
                + "AND BoardMeeting.caseMonth = REPCase.caseMonth AND BoardMeeting.caseNumber = REPCase.caseNumber) "
                + "WHERE (BoardMeeting.boardMeetingDate BETWEEN '" + startDate + "' AND '" + endDate + "') "
                + "AND (REPCase.boardActionType LIKE '%cert vr%' OR REPCase.boardActionType LIKE '%cert opt-in vr%')");
        processBookmark.process("nineone", String.valueOf(nineone), Document);
        //end of the 12th section

        //start of the 13th section
        //92 - Modified due to request R3-030
        long ninetwo = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM ULPCase WHERE (ULPCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND ((ULPCase.caseType = 'ULP') OR (ULPCase.caseType = 'JWD') OR (ULPCase.caseType = 'ERC'))");
        processBookmark.process("ninetwo", String.valueOf(ninetwo), Document);
        //93
        long ninethree = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM BoardMeeting LEFT JOIN ULPCase ON (BoardMeeting.caseYear = ULPCase.caseYear "
                + "AND BoardMeeting.caseType = ULPCase.caseType AND BoardMeeting.caseMonth = ULPCase.caseMonth "
                + "AND BoardMeeting.caseNumber = ULPCase.caseNumber) WHERE (BoardMeeting.boardMeetingDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (BoardMeeting.recommendation LIKE 'DM%') AND "
                + "(BoardMeeting.caseType = 'ULP' OR BoardMeeting.caseType = 'ERC' OR BoardMeeting.caseType = 'JWD') "
                + "AND (ULPCase.dismissalDate IS NOT NULL)");
        processBookmark.process("ninethree", String.valueOf(ninethree), Document);
        //94 - Modified due to request R3-057 (Issue with code never checked Re-Fixed in R3-072)
        long ninefour = threetwo;
        processBookmark.process("ninefour", String.valueOf(ninefour), Document);
        //95
        long ninefive = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.boardActionPCDate "
                + "BETWEEN '" + startDate + "' AND '" + endDate + "') AND (HearingCase.caseType = 'ULP')");
        processBookmark.process("ninefive", String.valueOf(ninefive), Document);
        //96
        long ninesix = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM BoardMeeting WHERE "
                + "(BoardMeeting.boardMeetingDate BETWEEN '" + startDate + "' AND '" + endDate + "') "
                + "AND (BoardMeeting.recommendation LIKE 'DF%') AND (BoardMeeting.caseType = 'ULP')");
        processBookmark.process("ninesix", String.valueOf(ninesix), Document);
        //97 - Modified due to request R3-030
        long nineseven = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE "
                + "(HearingCase.boardActionDate BETWEEN '" + startDate + "' AND '" + endDate + "') "
                + "AND (HearingCase.finalResult = 'Settled') AND (HearingCase.complaintIssuedDate IS NOT NULL) AND (HearingCase.caseType = 'ULP')");
        processBookmark.process("nineseven", String.valueOf(nineseven), Document);
        //98
        long nineeight = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.boardActionDate "
                + "BETWEEN '" + startDate + "' AND '" + endDate + "') AND (HearingCase.finalResult = 'Adjudicated - Violation' "
                + "OR HearingCase.finalResult = 'Adjudicated - No Violation' OR HearingCase.finalResult = 'Adjudicated - Dismissed') "
                + "AND complaintIssuedDate IS NOT NULL");
        processBookmark.process("nineeight", String.valueOf(nineeight), Document);
        //end of the 13th section

        //start of the 14th section
        //100
        long onezerozero = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM ULPCase WHERE (ULPCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (ULPCase.allegation LIKE '(A)%')");
        processBookmark.process("onezerozero", String.valueOf(onezerozero), Document);
        //101
        long onezeroone = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM ULPCase WHERE (ULPCase.fileDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (ULPCase.allegation LIKE '(B)%')");
        processBookmark.process("onezeroone", String.valueOf(onezeroone), Document);
        //99
        long ninenine = onezerozero + onezeroone;
        processBookmark.process("ninenine", String.valueOf(ninenine), Document);
        //end of the 14th section

        //start of the 15th section
        //102
        long onezerotwo = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.boardActionDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (HearingCase.finalResult = 'Ajudicated - Violation')");
        processBookmark.process("onezerotwo", String.valueOf(onezerotwo), Document);
        //end of the 15th section

        //start of the 16th section
        //103
        long onezerothree = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.proposedRecIssuedDate BETWEEN '"
                + startDate + "' AND '" + endDate + "')");
        processBookmark.process("onezerothree", String.valueOf(onezerothree), Document);
        //104
        long onezerofour = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.preHearingDate BETWEEN '"
                + startDate + "' AND '" + endDate + "')");
        processBookmark.process("onezerofour", String.valueOf(onezerofour), Document);
        //105 - Modified due to request R3-030
        long onezerofive = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.hearingDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (HearingCase.aljID IS NOT NULL AND (HearingCase.aljID != 1385 AND HearingCase.aljID != 1393 "
				+ "AND HearingCase.aljID != 1449 AND HearingCase.aljID != 0 ))");
        processBookmark.process("onezerofive", String.valueOf(onezerofive), Document);
        //106
        long onezerosix = AnnualReport.getCount("SELECT COUNT(*) AS COLUMN1 FROM HearingCase WHERE (HearingCase.boardActionDate BETWEEN '"
                + startDate + "' AND '" + endDate + "') AND (HearingCase.finalResult = 'Settled') "
                + "AND (HearingCase.complaintIssuedDate IS NOT NULL)");
        processBookmark.process("onezerosix", String.valueOf(onezerosix), Document);
        //end of the 16th section

        return Document;
    }

}
