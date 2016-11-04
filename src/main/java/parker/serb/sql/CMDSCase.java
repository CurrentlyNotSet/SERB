package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class CMDSCase {
    
    public int id;
    public boolean active;
    public String caseYear;
    public String caseType; 
    public String caseMonth;
    public String caseNumber;
    public String note;
    public Timestamp openDate;
    public String groupNumber;
    public int aljID;
    public Timestamp closeDate;
    public Timestamp inventoryStatusDate;
    public String inventroyStatusLine;
    public String caseStatus;
    public String result;

    public int mediatorID;
    public String PBRBox;
    public String groupType;
    public String reclassCode;
    public Timestamp mailedRR;
    public Timestamp mailedBO;
    public Timestamp mailedPO1;
    public Timestamp mailedPO2;
    public Timestamp mailedPO3;
    public Timestamp mailedPO4;
    public Timestamp remailedRR;
    public Timestamp remailedBO;
    public Timestamp remailedPO1;
    public Timestamp remailedPO2;
    public Timestamp remailedPO3;
    public Timestamp remailedPO4;
    public Timestamp returnReceiptRR;
    public Timestamp returnReceiptBO;
    public Timestamp returnReceiptPO1;
    public Timestamp returnReceiptPO2;
    public Timestamp returnReceiptPO3;
    public Timestamp returnReceiptPO4;
    public Timestamp pullDateRR;
    public Timestamp pullDateBO;
    public Timestamp pullDatePO1;
    public Timestamp pullDatePO2;
    public Timestamp pullDatePO3;
    public Timestamp pullDatePO4;
    public Timestamp hearingCompletedDate;
    public Timestamp postHearingBriefsDue;
    
    /**
     * Load a list of the most recent 250 REP case numbers
     * @return list of rep case numbers
     */
    public static List loadCMDSCaseNumbers() {
        
        //TODO: Limit the load to the last 6 months of filed dates
        
        List caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP 250"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber"
                    + " from CMDSCase"
                    + " Order By openDate DESC,"
                    + " CaseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                String createdCaseNumber = caseNumberRS.getString("caseYear")
                        + "-" +
                        caseNumberRS.getString("caseType")
                        + "-" +
                        caseNumberRS.getString("caseMonth")
                        + "-" +
                        caseNumberRS.getString("caseNumber");
                        
                caseNumberList.add(createdCaseNumber);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caseNumberList;
    }
    
    /**
     * Loads the notes that are related to the case
     * @return a stringified note
     */
    public static String loadNote() {
        String note = null;
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select Note"
                    + " from CMDSCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            caseNumberRS.next();
            
            note = caseNumberRS.getString("note");

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return note;
    }
    
    /**
     * Updates the note that is related to the case number
     * @param note the new note value to be stored
     */
    public static void updateNote(String note) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set note = ?"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, note);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            preparedStatement.executeUpdate();
            
            Audit.addAuditEntry("Updated Note for " + Global.caseNumber);
            Activity.addActivty("Updated Note", null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates a new REPCase entry
     * @param caseNumber the case number to be created 
     */
    public static void createCase(String caseYear, String caseType, String caseMonth, String caseNumber) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert into CMDSCase (CaseYear, CaseType, CaseMonth, CaseNumber, openDate) Values (?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            preparedStatement.setString(2, caseType);
            preparedStatement.setString(3, caseMonth);
            preparedStatement.setString(4, caseNumber);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                String fullCaseNumber = caseYear
                        + "-"
                        + caseType
                        + "-"
                        + caseMonth
                        + "-"
                        + caseNumber;
                        
                CaseNumber.updateNextCMDSCaseNumber(caseYear, String.valueOf(Integer.valueOf(caseNumber) + 1));
                Audit.addAuditEntry("Created Case: " + fullCaseNumber);
                Activity.addNewCaseActivty(fullCaseNumber, "Case was Filed and Started");
                Global.root.getcMDSHeaderPanel1().loadCases();
                Global.root.getcMDSHeaderPanel1().getjComboBox2().setSelectedItem(fullCaseNumber); 
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static boolean checkIfFristCMDSCaseOfMonth(String year, String month) {
        boolean firstCase = false;
        
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " COUNT(*) AS CasesThisMonth"
                    + " from CMDSCase"
                    + " where caseYear = ? "
                    + " and caseMonth = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, month);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            if(caseNumberRS.next()) {
                 if(caseNumberRS.getInt("CasesThisMonth") > 0) {
                     firstCase = false;
                 } else {
                     firstCase = true;
                 }
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return firstCase;
    }
    
    /**
     * Load information that is to be displayed in the header.  Dates are
     * formatted before being returned
     * @return a REPCase instance with the needed values
     */
    public static CMDSCase loadHeaderInformation() {
        CMDSCase cmds = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select openDate,"
                    + " groupNumber,"
                    + " aljID,"
                    + " closeDate,"
                    + " inventoryStatusLine,"
                    + " inventoryStatusDate,"
                    + " caseStatus,"
                    + " result"
                    + " from CMDSCase where caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseHeader = preparedStatement.executeQuery();
            
            if(caseHeader.next()) {
                cmds = new CMDSCase();
                cmds.openDate = caseHeader.getTimestamp("openDate");
                cmds.groupNumber =  caseHeader.getString("groupNumber");
                cmds.aljID = caseHeader.getInt("aljID");
                cmds.closeDate = caseHeader.getTimestamp("closeDate");
                cmds.inventroyStatusLine = caseHeader.getString("inventoryStatusLine");
                cmds.inventoryStatusDate = caseHeader.getTimestamp("inventoryStatusDate");
                cmds.caseStatus = caseHeader.getString("caseStatus");
                cmds.result = caseHeader.getString("result");
            }         
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return cmds;
    }
    
    public static CMDSCase loadCMDSCaseInformation() {
        CMDSCase cmds = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " opendate,"
                    + " groupNumber,"
                    + " aljid,"
                    + " pbrBox,"
                    + " closeDate,"
                    + " caseStatus,"
                    + " result,"
                    + " mediatorID,"
                    + " groupType,"
                    + " reclassCode,"
                    + " mailedRR,"
                    + " mailedBO,"
                    + " mailedPO1,"
                    + " mailedPO2,"
                    + " mailedPO3,"
                    + " mailedPO4,"
                    + " remailedRR,"
                    + " remailedBO,"
                    + " remailedPO1,"
                    + " remailedPO2,"
                    + " remailedPO3,"
                    + " remailedPO4,"
                    + " returnReceiptRR,"
                    + " returnReceiptBO,"
                    + " returnReceiptPO1,"
                    + " returnReceiptPO2,"
                    + " returnReceiptPO3,"
                    + " returnReceiptPO4,"
                    + " pullDateRR,"
                    + " pullDateBO,"
                    + " pullDatePO1,"
                    + " pullDatePO2,"
                    + " pullDatePO3,"
                    + " pullDatePO4,"
                    + " hearingCompletedDate,"
                    + " postHearingDriefsDue"
                    + " from CMDSCase where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseInformation = preparedStatement.executeQuery();
            
            if(caseInformation.next()) {
                cmds = new CMDSCase();
                cmds.caseYear = caseInformation.getString("caseYear");
                cmds.caseType = caseInformation.getString("caseType");
                cmds.caseMonth = caseInformation.getString("caseMonth");
                cmds.caseNumber = caseInformation.getString("caseNumber");
                cmds.aljID = caseInformation.getInt("aljID");
                cmds.openDate = caseInformation.getTimestamp("openDate");
                cmds.closeDate = caseInformation.getTimestamp("closeDate");
                cmds.groupNumber = caseInformation.getString("groupNumber");
                cmds.mediatorID = caseInformation.getInt("mediatorID");
                cmds.PBRBox = caseInformation.getString("pbrBox");
                cmds.groupType = caseInformation.getString("groupType");
                cmds.caseStatus = caseInformation.getString("caseStatus");
                cmds.reclassCode = caseInformation.getString("reclassCode");
                cmds.result = caseInformation.getString("result");
                
                cmds.mailedRR = caseInformation.getTimestamp("mailedRR");
                cmds.mailedBO = caseInformation.getTimestamp("mailedBO");
                cmds.mailedPO1 = caseInformation.getTimestamp("mailedPO1");
                cmds.mailedPO2 = caseInformation.getTimestamp("mailedPO2");
                cmds.mailedPO3 = caseInformation.getTimestamp("mailedPO3");
                cmds.mailedPO4 = caseInformation.getTimestamp("mailedPO4");
                
                cmds.remailedRR = caseInformation.getTimestamp("remailedRR");
                cmds.remailedBO = caseInformation.getTimestamp("remailedBO");
                cmds.remailedPO1 = caseInformation.getTimestamp("remailedPO1");
                cmds.remailedPO2 = caseInformation.getTimestamp("remailedPO2");
                cmds.remailedPO3 = caseInformation.getTimestamp("remailedPO3");
                cmds.remailedPO4 = caseInformation.getTimestamp("remailedPO4");
                
                cmds.returnReceiptRR = caseInformation.getTimestamp("returnReceiptRR");
                cmds.returnReceiptBO = caseInformation.getTimestamp("returnReceiptBO");
                cmds.returnReceiptPO1 = caseInformation.getTimestamp("returnReceiptPO1");
                cmds.returnReceiptPO2 = caseInformation.getTimestamp("returnReceiptPO2");
                cmds.returnReceiptPO3 = caseInformation.getTimestamp("returnReceiptPO3");
                cmds.returnReceiptPO4 = caseInformation.getTimestamp("returnReceiptPO4");
                
                cmds.pullDateRR = caseInformation.getTimestamp("pullDateRR");
                cmds.pullDateBO = caseInformation.getTimestamp("pullDateBO");
                cmds.pullDatePO1 = caseInformation.getTimestamp("pullDatePO1");
                cmds.pullDatePO2 = caseInformation.getTimestamp("pullDatePO2");
                cmds.pullDatePO3 = caseInformation.getTimestamp("pullDatePO3");
                cmds.pullDatePO4 = caseInformation.getTimestamp("pullDatePO4");
                
                cmds.hearingCompletedDate = caseInformation.getTimestamp("hearingCompletedDate");
                cmds.postHearingBriefsDue = caseInformation.getTimestamp("postHearingDriefsDue");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return cmds;
    }
    
//    public static CMDSCase loadCaseDetails(String caseYear, String caseType, String caseMonth, String caseNumber) {
//        CMDSCase rep = null;
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Select * from "
//                    + "REPCase where caseYear = ? "
//                    + " AND caseType = ? "
//                    + " AND caseMonth = ? "
//                    + " AND caseNumber = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, caseYear);
//            preparedStatement.setString(2, caseType);
//            preparedStatement.setString(3, caseMonth);
//            preparedStatement.setString(4, caseNumber);
//
//            ResultSet caseInformation = preparedStatement.executeQuery();
//            
//            if(caseInformation.next()) {
//                rep = new CMDSCase();
//                rep.id =  caseInformation.getInt("id");
//                rep.active = caseInformation.getBoolean("active");
//                rep.caseYear = caseInformation.getString("caseYear");
//                rep.caseType = caseInformation.getString("caseType"); 
//                rep.caseMonth = caseInformation.getString("caseMonth");
//                rep.caseNumber = caseInformation.getString("caseNumber");
//                rep.type = caseInformation.getString("type");
//                rep.status1 = caseInformation.getString("status1");
//                rep.status2 = caseInformation.getString("status2");
//                rep.currentOwnerID = caseInformation.getInt("currentOwnerID");
//                rep.county = caseInformation.getString("county");
//                rep.employerIDNumber = caseInformation.getString("employerIDNumber");
//                rep.deptInState = caseInformation.getString("deptInState");
//                rep.bargainingUnitNumber = caseInformation.getString("bargainingUnitNumber");
//                rep.boardCertified = caseInformation.getBoolean("boardCertified");
//                rep.deemedCertified = caseInformation.getBoolean("deemedCertified");
//                rep.certificationRevoked = caseInformation.getBoolean("certificationRevoked");
//                rep.note = caseInformation.getString("note");
//                rep.fileDate = caseInformation.getTimestamp("fileDate");
//                rep.amendedFiliingDate = caseInformation.getTimestamp("amendedFilingDate");
//                rep.alphaListDate = caseInformation.getTimestamp("alphaListDate");
//                rep.finalBoardDate = caseInformation.getTimestamp("finalBoardDate");
//                rep.registrationLetterSent = caseInformation.getTimestamp("registrationLetterSent");
//                rep.dateOfAppeal = caseInformation.getTimestamp("dateOfAppeal");
//                rep.courtClosedDate = caseInformation.getTimestamp("courtClosedDate");
//                rep.returnSOIDueDate = caseInformation.getTimestamp("returnSOIDueDate");
//                rep.actualSOIReturnDate = caseInformation.getTimestamp("actualSOIReturnDate");
//                rep.SOIReturnInitials = caseInformation.getInt("SOIReturnIntials");
//                rep.REPClosedCaseDueDate = caseInformation.getTimestamp("REPClosedCaseDueDate");
//                rep.actualREPClosedDate = caseInformation.getTimestamp("actualREPClosedDate");
//                rep.REPClosedUser = caseInformation.getInt("REPClosedUser");
//                rep.actualClerksClosedDate = caseInformation.getTimestamp("actualClerksClosedDate");
//                rep.clerksClosedUser = caseInformation.getInt("clerksClosedUser");                
//                rep.fileBy = caseInformation.getString("fileBy");
//                rep.bargainingUnitIncluded = caseInformation.getString("bargainingUnitIncluded");
//                rep.bargainingUnitExcluded = caseInformation.getString("bargainingUnitExcluded");
//                rep.optInIncluded = caseInformation.getString("optInIncluded");
//                rep.professionalNonProfessional = caseInformation.getBoolean("professionalNonProfessional");
//                rep.professionalIncluded = caseInformation.getString("professionalIncluded");
//                rep.professionalExcluded = caseInformation.getString("professionalExcluded");
//                rep.nonProfessionalIncluded = caseInformation.getString("nonProfessionalIncluded");
//                rep.nonProfessionalExcluded = caseInformation.getString("nonProfessionalExcluded");
//                rep.toReflect = caseInformation.getString("toReflect");
//                rep.typeFiledBy = caseInformation.getString("typeFiledBy");
//                rep.typeFiledVia = caseInformation.getString("typeFiledVia");
//                rep.positionStatementFiledBy = caseInformation.getString("positionStatementFiledBy");
//                rep.EEONameChangeFrom = caseInformation.getString("EEONameChangeFrom");
//                rep.EEONameChangeTo = caseInformation.getString("EEONameChangeTo");
//                rep.ERNameChangeFrom = caseInformation.getString("ERNameChangeFrom");
//                rep.ERNameChangeTo = caseInformation.getString("ERNameChangeTo");
//                rep.boardActionType = caseInformation.getString("boardActionType");
//                rep.boardActionDate = caseInformation.getTimestamp("boardActionDate");
//                rep.hearingPersonID = caseInformation.getInt("hearingPersonID");
//                rep.boardStatusNote = caseInformation.getString("boardStatusNote");
//                rep.boardStatusBlurb = caseInformation.getString("boardStatusBlurb");
//
//                //electionData
//                rep.multicaseElection = caseInformation.getBoolean("multicaseElection");
//                rep.electionType1 = caseInformation.getString("electionType1");
//                rep.electionType2 = caseInformation.getString("electionType2");
//                rep.electionType3 = caseInformation.getString("electionType3");
//                rep.eligibilityDate = caseInformation.getTimestamp("eligibilityDate");
//                rep.ballotOne = caseInformation.getString("ballotOne");
//                rep.ballotTwo = caseInformation.getString("ballotTwo");
//                rep.ballotThree = caseInformation.getString("ballotThree");
//                rep.ballotFour = caseInformation.getString("ballotFour");
//                rep.mailKitDate = caseInformation.getTimestamp("mailKitDate");
//                rep.pollingStartDate = caseInformation.getTimestamp("pollingStartDate");
//                rep.pollingEndDate = caseInformation.getTimestamp("pollingEndDate");
//                rep.ballotsCountDay = caseInformation.getString("ballotsCountDay");
//                rep.ballotsCountDate = caseInformation.getTimestamp("ballotsCountDate");
//                rep.ballotsCountTime = caseInformation.getTimestamp("ballotsCountTime");
//                rep.eligibilityListDate = caseInformation.getTimestamp("eligibilityListDate");
//                rep.preElectionConfDate = caseInformation.getTimestamp("preElectionConfDate");
//                rep.selfReleasing = caseInformation.getString("selfReleasing");
//
//                //Results
//                rep.resultApproxNumberEligibleVotes = caseInformation.getString("resultApproxNumberEligibleVoters");
//                rep.resultVoidBallots = caseInformation.getString("resultVoidBallots");
//                rep.resultVotesCastForEEO = caseInformation.getString("resultVotesCastForEEO");
//                rep.resultVotesCastForIncumbentEEO = caseInformation.getString("resultVotesCastForIncumbentEEO");
//                rep.resultVotesCastForRivalEEO1 = caseInformation.getString("resultVotesCastForRivalEEO1");
//                rep.resultVotesCastForRivalEEO2 = caseInformation.getString("resultVotesCastForRivalEEO2");
//                rep.resultVotesCastForRivalEEO3 = caseInformation.getString("resultVotesCastForRivalEEO3");
//                rep.resultVotesCastForNoRepresentative = caseInformation.getString("resultVotesCastForNoRepresentative");
//                rep.resultValidVotesCounted = caseInformation.getString("resultValidVotesCounted");
//                rep.resultChallengedBallots = caseInformation.getString("resultChallengedBallots");
//                rep.resultTotalBallotsCast = caseInformation.getString("resultTotalBallotsCast");
//                rep.resultWHoPrevailed = caseInformation.getObject("resultWHoPrevailed") == null ? "" : CaseParty.getCasePartyByIDForElection(caseInformation.getInt("resultWHoPrevailed"));
//
//                //Professional
//                rep.professionalApproxNumberEligible = caseInformation.getString("professionalApproxNumberEligible");
//                rep.professionalYES = caseInformation.getString("professionalYES");
//                rep.professionalNO = caseInformation.getString("professionalNO");
//                rep.professionalChallenged = caseInformation.getString("professionalChallenged");
//                rep.professionalTotalVotes = caseInformation.getString("professionalTotalVotes");
//                rep.professionalOutcome = caseInformation.getString("professionalOutcome");
//                rep.professionalWhoPrevailed = caseInformation.getObject("professionalWHoPrevailed") == null ? "" : CaseParty.getCasePartyByIDForElection(caseInformation.getInt("professionalWhoPrevailed"));
//                rep.professionalVoidBallots = caseInformation.getString("professionalVoidBallots");
//                rep.professionalValidVotes = caseInformation.getString("professionalValidVotes");
//                rep.professionalVotesCastForNoRepresentative = caseInformation.getString("professionalVotesCastForNoRepresentative");
//                rep.professionalVotesCastForEEO = caseInformation.getString("professionalVotesCastForEEO");
//                rep.professionalVotesCastForIncumbentEEO = caseInformation.getString("professionalVotesCastForIncumbentEEO");
//                rep.professionalVotesCastForRivalEEO1 = caseInformation.getString("professionalVotesCastForRivalEEO1");
//                rep.professionalVotesCastForRivalEEO2 = caseInformation.getString("professionalVotesCastForRivalEEO2");
//                rep.professionalVotesCastForRivalEEO3 = caseInformation.getString("professionalVotesCastForRivalEEO3");
//            
//                //Non-Professional
//                rep.nonprofessionalApproxNumberEligible = caseInformation.getString("nonprofessionalApproxNumberEligible");
//                rep.nonprofessionalYES = caseInformation.getString("nonprofessionalYES");
//                rep.nonprofessionalNO = caseInformation.getString("nonprofessionalNO");
//                rep.nonprofessionalChallenged = caseInformation.getString("nonprofessionalChallenged");
//                rep.nonprofessionalTotalVotes = caseInformation.getString("nonprofessionalTotalVotes");
//                rep.nonprofessionalOutcome = caseInformation.getString("nonprofessionalOutcome");
//                rep.nonprofessionalWhoPrevailed = caseInformation.getObject("nonprofessionalWhoPrevailed") == null ? "" : CaseParty.getCasePartyByIDForElection(caseInformation.getInt("nonprofessionalWHoPrevailed"));
//                rep.nonprofessionalVoidBallots = caseInformation.getString("nonprofessionalVoidBallots");
//                rep.nonprofessionalValidVotes = caseInformation.getString("nonprofessionalValidVotes");
//                rep.nonprofessionalVotesCastForNoRepresentative = caseInformation.getString("nonprofessionalVotesCastForNoRepresentative");
//                rep.nonprofessionalVotesCastForEEO = caseInformation.getString("nonprofessionalVotesCastForEEO");
//                rep.nonprofessionalVotesCastForIncumbentEEO = caseInformation.getString("nonprofessionalVotesCastForIncumbentEEO");
//                rep.nonprofessionalVotesCastForRivalEEO1 = caseInformation.getString("nonprofessionalVotesCastForRivalEEO1");
//                rep.nonprofessionalVotesCastForRivalEEO2 = caseInformation.getString("nonprofessionalVotesCastForRivalEEO2");
//                rep.nonprofessionalVotesCastForRivalEEO3 = caseInformation.getString("nonprofessionalVotesCastForRivalEEO3");
//
//                //Combined
//                rep.combinedApproxNumberEligible = caseInformation.getString("combinedApproxNumberEligible");
//                rep.combinedYES = caseInformation.getString("combinedYES");
//                rep.combinedlNO = caseInformation.getString("combinedNO");
//                rep.combinedChallenged = caseInformation.getString("combinedChallenged");
//                rep.combinedTotalVotes = caseInformation.getString("combinedTotalVotes");
//                rep.combinedOutcome = caseInformation.getString("combinedOutcome");
//                rep.combinedWhoPrevailed = caseInformation.getObject("combinedWhoPrevailed") == null ? "" : CaseParty.getCasePartyByIDForElection(caseInformation.getInt("combinedWHoPrevailed"));
//                rep.combinedVoidBallots = caseInformation.getString("combinedVoidBallots");
//                rep.combinedValidVotes = caseInformation.getString("combinedValidVotes");
//                rep.combinedVotesCastForNoRepresentative = caseInformation.getString("combinedVotesCastForNoRepresentative");
//                rep.combinedVotesCastForEEO = caseInformation.getString("combinedVotesCastForEEO");
//                rep.combinedVotesCastForIncumbentEEO = caseInformation.getString("combinedVotesCastForIncumbentEEO");
//                rep.combinedVotesCastForRivalEEO1 = caseInformation.getString("combinedVotesCastForRivalEEO1");
//                rep.combinedVotesCastForRivalEEO2 = caseInformation.getString("combinedVotesCastForRivalEEO2");
//                rep.combinedVotesCastForRivalEEO3 = caseInformation.getString("combinedVotesCastForRivalEEO3");
//            }
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//        return rep;
//    }
    
//    public static CMDSCase loadCaseDetails() {
//        CMDSCase rep = null;
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Select"
//                    + " type,"
//                    + " fileBy,"
//                    + " bargainingUnitIncluded,"
//                    + " bargainingUnitExcluded,"
//                    + " optInIncluded,"
//                    + " professionalNonProfessional,"
//                    + " professionalIncluded,"
//                    + " professionalExcluded,"
//                    + " nonProfessionalIncluded,"
//                    + " nonProfessionalExcluded,"
//                    + " toReflect,"
//                    + " typeFiledBy,"
//                    + " typeFiledVia,"
//                    + " positionStatementFiledBy,"
//                    + " EEONameChangeFrom,"
//                    + " EEONameChangeTo,"
//                    + " ERNameChangeFrom,"
//                    + " ERNameChangeTo"
//                    + " from REPCase where caseYear = ? "
//                    + " AND caseType = ? "
//                    + " AND caseMonth = ? "
//                    + " AND caseNumber = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, Global.caseYear);
//            preparedStatement.setString(2, Global.caseType);
//            preparedStatement.setString(3, Global.caseMonth);
//            preparedStatement.setString(4, Global.caseNumber);
//
//            ResultSet caseInformation = preparedStatement.executeQuery();
//            
//            if(caseInformation.next()) {
//                rep = new CMDSCase();
//                rep.type = caseInformation.getString("type");
//                rep.fileBy = caseInformation.getString("fileBy");
//                rep.bargainingUnitIncluded = caseInformation.getString("bargainingUnitIncluded");
//                rep.bargainingUnitExcluded = caseInformation.getString("bargainingUnitExcluded");
//                rep.optInIncluded = caseInformation.getString("optInIncluded");
//                rep.professionalNonProfessional = caseInformation.getBoolean("professionalNonProfessional");
//                rep.professionalIncluded = caseInformation.getString("professionalIncluded");
//                rep.professionalExcluded = caseInformation.getString("professionalExcluded");
//                rep.nonProfessionalIncluded = caseInformation.getString("nonProfessionalIncluded");
//                rep.nonProfessionalExcluded = caseInformation.getString("nonProfessionalExcluded");
//                rep.toReflect = caseInformation.getString("toReflect");
//                rep.typeFiledBy = caseInformation.getString("typeFiledBy");
//                rep.typeFiledVia = caseInformation.getString("typeFiledVia");
//                rep.positionStatementFiledBy = caseInformation.getString("positionStatementFiledBy");
//                rep.EEONameChangeFrom = caseInformation.getString("EEONameChangeFrom");
//                rep.EEONameChangeTo = caseInformation.getString("EEONameChangeTo");
//                rep.ERNameChangeFrom = caseInformation.getString("ERNameChangeFrom");
//                rep.ERNameChangeTo = caseInformation.getString("ERNameChangeTo");
//            }
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//        return rep;
//    }
    
//    public static CMDSCase loadBoardStatus() {
//        CMDSCase rep = null;
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Select"
//                    + " boardActionType,"
//                    + " boardActionDate,"
//                    + " hearingPersonID,"
//                    + " boardStatusNote,"
//                    + " boardStatusBlurb"
//                    + " from REPCase where caseYear = ? "
//                    + " AND caseType = ? "
//                    + " AND caseMonth = ? "
//                    + " AND caseNumber = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, Global.caseYear);
//            preparedStatement.setString(2, Global.caseType);
//            preparedStatement.setString(3, Global.caseMonth);
//            preparedStatement.setString(4, Global.caseNumber);
//
//            ResultSet caseInformation = preparedStatement.executeQuery();
//            
//            if(caseInformation.next()) {
//                rep = new CMDSCase();
//                rep.boardActionType = caseInformation.getString("boardActionType");
//                rep.boardActionDate = caseInformation.getTimestamp("boardActionDate");
//                rep.hearingPersonID = caseInformation.getInt("hearingPersonID");
//                rep.boardStatusNote = caseInformation.getString("boardStatusNote");
//                rep.boardStatusBlurb = caseInformation.getString("boardStatusBlurb");
//            }
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//        return rep;
//    }
    
//    public static CMDSCase loadElectionInformation() {
//        CMDSCase rep = null;
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Select"
//                    + " multicaseElection,"
//                    + " electionType1,"
//                    + " electionType2,"
//                    + " electionType3,"
//                    + " eligibilityDate,"
//                    + " ballotOne,"
//                    + " ballotTwo,"
//                    + " ballotThree,"
//                    + " ballotFour,"
//                    + " mailKitDate,"
//                    + " pollingStartDate,"
//                    + " pollingEndDate,"
//                    + " ballotsCountDay,"
//                    + " ballotsCountDate,"
//                    + " ballotsCountTime,"
//                    + " eligibilityListDate,"
//                    + " preElectionCOnfDate,"
//                    + " selfReleasing,"
//                    + " resultApproxNumberEligibleVoters,"
//                    + " resultVoidBallots,"
//                    + " resultVotesCastForEEO,"
//                    + " resultVotesCastForIncumbentEEO,"
//                    + " resultVotesCastForRivalEEO1,"
//                    + " resultVotesCastforRivalEEO2,"
//                    + " resultVotesCastForRivalEEO3,"
//                    + " resultVotesCastForNoRepresentative,"
//                    + " resultValidVotesCounted,"
//                    + " resultChallengedBallots,"
//                    + " resultTotalBallotsCast,"
//                    + " resultWhoPrevailed,"
//                    + " professionalApproxNumberEligible,"
//                    + " professionalYES,"
//                    + " professionalNO,"
//                    + " professionalChallenged,"
//                    + " professionalTotalVotes,"
//                    + " professionalOutcome,"
//                    + " professionalWhoPrevailed,"
//                    + " professionalVoidBallots,"
//                    + " professionalValidVotes,"
//                    + " professionalVotesCastforNoRepresentative,"
//                    + " professionalVotesCastForEEO,"
//                    + " professionalVotesCastForIncumbentEEO,"
//                    + " professionalVotesCastForRivalEEO1,"
//                    + " professionalVotesCastForRivalEEO2,"
//                    + " professionalVotesCastForRivalEEO3,"
//                    
//                    + " nonprofessionalApproxNumberEligible,"
//                    + " nonprofessionalYES,"
//                    + " nonprofessionalNO,"
//                    + " nonprofessionalChallenged,"
//                    + " nonprofessionalTotalVotes,"
//                    + " nonprofessionalOutcome,"
//                    + " nonprofessionalWhoPrevailed,"
//                    + " nonprofessionalVoidBallots,"
//                    + " nonprofessionalValidVotes,"
//                    + " nonprofessionalVotesCastforNoRepresentative,"
//                    + " nonprofessionalVotesCastForEEO,"
//                    + " nonprofessionalVotesCastForIncumbentEEO,"
//                    + " nonprofessionalVotesCastForRivalEEO1,"
//                    + " nonprofessionalVotesCastForRivalEEO2,"
//                    + " nonprofessionalVotesCastForRivalEEO3,"
//                    
//                    + " combinedApproxNumberEligible,"
//                    + " combinedYES,"
//                    + " combinedNO,"
//                    + " combinedChallenged,"
//                    + " combinedTotalVotes,"
//                    + " combinedOutcome,"
//                    + " combinedWhoPrevailed,"
//                    + " combinedVoidBallots,"
//                    + " combinedValidVotes,"
//                    + " combinedVotesCastforNoRepresentative,"
//                    + " combinedVotesCastForEEO,"
//                    + " combinedVotesCastForIncumbentEEO,"
//                    + " combinedVotesCastForRivalEEO1,"
//                    + " combinedVotesCastForRivalEEO2,"
//                    + " combinedVotesCastForRivalEEO3"
//                    
//                    + " from REPCase where caseYear = ? "
//                    + " AND caseType = ? "
//                    + " AND caseMonth = ? "
//                    + " AND caseNumber = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, Global.caseYear);
//            preparedStatement.setString(2, Global.caseType);
//            preparedStatement.setString(3, Global.caseMonth);
//            preparedStatement.setString(4, Global.caseNumber);
//
//            ResultSet caseInformation = preparedStatement.executeQuery();
//            
//            if(caseInformation.next()) {
//                rep = new CMDSCase();
//                rep.multicaseElection = caseInformation.getBoolean("multicaseElection");
//                rep.electionType1 = caseInformation.getString("electionType1");
//                rep.electionType2 = caseInformation.getString("electionType2");
//                rep.electionType3 = caseInformation.getString("electionType3");
//                rep.eligibilityDate = caseInformation.getTimestamp("eligibilityDate");
//                rep.ballotOne = caseInformation.getString("ballotOne");
//                rep.ballotTwo = caseInformation.getString("ballotTwo");
//                rep.ballotThree = caseInformation.getString("ballotThree");
//                rep.ballotFour = caseInformation.getString("ballotFour");
//                rep.mailKitDate = caseInformation.getTimestamp("mailKitDate");
//                rep.pollingStartDate = caseInformation.getTimestamp("pollingStartDate");
//                rep.pollingEndDate = caseInformation.getTimestamp("pollingEndDate");
//                rep.ballotsCountDay = caseInformation.getString("ballotsCountDay");
//                rep.ballotsCountDate = caseInformation.getTimestamp("ballotsCountDate");
//                rep.ballotsCountTime = caseInformation.getTimestamp("ballotsCountTime");
//                rep.eligibilityListDate = caseInformation.getTimestamp("eligibilityListDate");
//                rep.preElectionConfDate = caseInformation.getTimestamp("preElectionConfDate");
//                rep.selfReleasing = caseInformation.getString("selfReleasing");
//                
//                rep.resultApproxNumberEligibleVotes = caseInformation.getString("resultApproxNumberEligibleVoters");
//                rep.resultVoidBallots = caseInformation.getString("resultVoidBallots");
//                rep.resultVotesCastForEEO = caseInformation.getString("resultVotesCastForEEO");
//                rep.resultVotesCastForIncumbentEEO = caseInformation.getString("resultVotesCastForIncumbentEEO");
//                rep.resultVotesCastForRivalEEO1 = caseInformation.getString("resultVotesCastForRivalEEO1");
//                rep.resultVotesCastForRivalEEO2 = caseInformation.getString("resultVotesCastForRivalEEO2");
//                rep.resultVotesCastForRivalEEO3 = caseInformation.getString("resultVotesCastForRivalEEO3");
//                rep.resultVotesCastForNoRepresentative = caseInformation.getString("resultVotesCastForNoRepresentative");
//                rep.resultValidVotesCounted = caseInformation.getString("resultValidVotesCounted");
//                rep.resultChallengedBallots = caseInformation.getString("resultChallengedBallots");
//                rep.resultTotalBallotsCast = caseInformation.getString("resultTotalBallotsCast");
//                rep.resultWHoPrevailed = caseInformation.getObject("resultWHoPrevailed") == null ? "" : CaseParty.getCasePartyByIDForElection(caseInformation.getInt("resultWHoPrevailed"));
//                
//                rep.professionalApproxNumberEligible = caseInformation.getString("professionalApproxNumberEligible");
//                rep.professionalYES = caseInformation.getString("professionalYES");
//                rep.professionalNO = caseInformation.getString("professionalNO");
//                rep.professionalChallenged = caseInformation.getString("professionalChallenged");
//                rep.professionalTotalVotes = caseInformation.getString("professionalTotalVotes");
//                rep.professionalOutcome = caseInformation.getString("professionalOutcome");
//                rep.professionalWhoPrevailed = caseInformation.getObject("professionalWHoPrevailed") == null ? "" : CaseParty.getCasePartyByIDForElection(caseInformation.getInt("professionalWhoPrevailed"));
//                rep.professionalVoidBallots = caseInformation.getString("professionalVoidBallots");
//                rep.professionalValidVotes = caseInformation.getString("professionalValidVotes");
//                rep.professionalVotesCastForNoRepresentative = caseInformation.getString("professionalVotesCastForNoRepresentative");
//                rep.professionalVotesCastForEEO = caseInformation.getString("professionalVotesCastForEEO");
//                rep.professionalVotesCastForIncumbentEEO = caseInformation.getString("professionalVotesCastForIncumbentEEO");
//                rep.professionalVotesCastForRivalEEO1 = caseInformation.getString("professionalVotesCastForRivalEEO1");
//                rep.professionalVotesCastForRivalEEO2 = caseInformation.getString("professionalVotesCastForRivalEEO2");
//                rep.professionalVotesCastForRivalEEO3 = caseInformation.getString("professionalVotesCastForRivalEEO3");
//            
//                rep.nonprofessionalApproxNumberEligible = caseInformation.getString("nonprofessionalApproxNumberEligible");
//                rep.nonprofessionalYES = caseInformation.getString("nonprofessionalYES");
//                rep.nonprofessionalNO = caseInformation.getString("nonprofessionalNO");
//                rep.nonprofessionalChallenged = caseInformation.getString("nonprofessionalChallenged");
//                rep.nonprofessionalTotalVotes = caseInformation.getString("nonprofessionalTotalVotes");
//                rep.nonprofessionalOutcome = caseInformation.getString("nonprofessionalOutcome");
//                rep.nonprofessionalWhoPrevailed = caseInformation.getObject("nonprofessionalWhoPrevailed") == null ? "" : CaseParty.getCasePartyByIDForElection(caseInformation.getInt("nonprofessionalWHoPrevailed"));
//                rep.nonprofessionalVoidBallots = caseInformation.getString("nonprofessionalVoidBallots");
//                rep.nonprofessionalValidVotes = caseInformation.getString("nonprofessionalValidVotes");
//                rep.nonprofessionalVotesCastForNoRepresentative = caseInformation.getString("nonprofessionalVotesCastForNoRepresentative");
//                rep.nonprofessionalVotesCastForEEO = caseInformation.getString("nonprofessionalVotesCastForEEO");
//                rep.nonprofessionalVotesCastForIncumbentEEO = caseInformation.getString("nonprofessionalVotesCastForIncumbentEEO");
//                rep.nonprofessionalVotesCastForRivalEEO1 = caseInformation.getString("nonprofessionalVotesCastForRivalEEO1");
//                rep.nonprofessionalVotesCastForRivalEEO2 = caseInformation.getString("nonprofessionalVotesCastForRivalEEO2");
//                rep.nonprofessionalVotesCastForRivalEEO3 = caseInformation.getString("nonprofessionalVotesCastForRivalEEO3");
//
//                rep.combinedApproxNumberEligible = caseInformation.getString("combinedApproxNumberEligible");
//                rep.combinedYES = caseInformation.getString("combinedYES");
//                rep.combinedlNO = caseInformation.getString("combinedNO");
//                rep.combinedChallenged = caseInformation.getString("combinedChallenged");
//                rep.combinedTotalVotes = caseInformation.getString("combinedTotalVotes");
//                rep.combinedOutcome = caseInformation.getString("combinedOutcome");
//                rep.combinedWhoPrevailed = caseInformation.getObject("combinedWhoPrevailed") == null ? "" : CaseParty.getCasePartyByIDForElection(caseInformation.getInt("combinedWHoPrevailed"));
//                rep.combinedVoidBallots = caseInformation.getString("combinedVoidBallots");
//                rep.combinedValidVotes = caseInformation.getString("combinedValidVotes");
//                rep.combinedVotesCastForNoRepresentative = caseInformation.getString("combinedVotesCastForNoRepresentative");
//                rep.combinedVotesCastForEEO = caseInformation.getString("combinedVotesCastForEEO");
//                rep.combinedVotesCastForIncumbentEEO = caseInformation.getString("combinedVotesCastForIncumbentEEO");
//                rep.combinedVotesCastForRivalEEO1 = caseInformation.getString("combinedVotesCastForRivalEEO1");
//                rep.combinedVotesCastForRivalEEO2 = caseInformation.getString("combinedVotesCastForRivalEEO2");
//                rep.combinedVotesCastForRivalEEO3 = caseInformation.getString("combinedVotesCastForRivalEEO3");
//
//            }
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//        return rep;
//    }
//    
//    public static void updateBoardStatus(CMDSCase newCaseInformation, CMDSCase caseInformation) {
//        CMDSCase rep = null;
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Update REPCase set"
//                    + " boardActionType = ?,"
//                    + " boardActionDate = ?,"
//                    + " hearingPersonID = ?,"
//                    + " boardStatusNote = ?,"
//                    + " boardStatusBlurb = ?"
//                    + " from REPCase where caseYear = ? "
//                    + " AND caseType = ? "
//                    + " AND caseMonth = ? "
//                    + " AND caseNumber = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, newCaseInformation.boardActionType);
//            preparedStatement.setTimestamp(2, newCaseInformation.boardActionDate);
//            preparedStatement.setInt(3, newCaseInformation.hearingPersonID);
//            preparedStatement.setString(4, newCaseInformation.boardStatusNote);
//            preparedStatement.setString(5, newCaseInformation.boardStatusBlurb);
//            preparedStatement.setString(6, Global.caseYear);
//            preparedStatement.setString(7, Global.caseType);
//            preparedStatement.setString(8, Global.caseMonth);
//            preparedStatement.setString(9, Global.caseNumber);
//
//            int success = preparedStatement.executeUpdate();
//            
//            if(success == 1) {
//                detailedBoardStatusDetailsSaveInformation(newCaseInformation, caseInformation);
//            }
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//    }
//    
//    public static void updateElectionInformation(CMDSCase newCaseInformation, CMDSCase caseInformation, String[] professional, String[] nonprofessional, String[] combined) {
//        CMDSCase rep = null;
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Update REPCase set"
//                    + " multicaseElection = ?,"
//                    + " electionType1 = ?,"
//                    + " electionType2 = ?,"
//                    + " electionType3 = ?,"
//                    + " eligibilityDate = ?,"
//                    + " ballotOne = ?,"
//                    + " ballotTwo = ?,"
//                    + " ballotThree = ?,"
//                    + " ballotFour = ?,"
//                    + " mailKitDate = ?,"
//                    + " pollingStartDate = ?,"
//                    + " pollingEndDate = ?,"
//                    + " ballotsCountDay = ?,"
//                    + " ballotsCountDate = ?,"
//                    + " ballotsCountTime = ?,"
//                    + " eligibilityListDate = ?,"
//                    + " preElectionConfDate = ?,"
//                    + " selfReleasing = ?,"
//                    + " resultApproxNumberEligibleVoters = ?,"
//                    + " resultVoidBallots = ?,"
//                    + " resultVotesCastForEEO = ?,"
//                    + " resultVotesCastForIncumbentEEO = ?,"
//                    + " resultVotesCastForRivalEEO1 = ?,"
//                    + " resultVotesCastforRivalEEO2 = ?,"
//                    + " resultVotesCastForRivalEEO3 = ?,"
//                    + " resultVotesCastForNoRepresentative = ?,"
//                    + " resultValidVotesCounted = ?,"
//                    + " resultChallengedBallots = ?,"
//                    + " resultTotalBallotsCast = ?,"
//                    + " resultWhoPrevailed = ?,"
//                    
//                    + " professionalApproxNumberEligible = ?,"
//                    + " professionalYES = ?,"
//                    + " professionalNO = ?,"
//                    + " professionalChallenged = ?,"
//                    + " professionalTotalVotes = ?,"
//                    + " professionalOutcome = ?,"
//                    + " professionalWhoPrevailed = ?,"
//                    + " professionalVoidBallots = ?,"
//                    + " professionalValidVotes = ?,"
//                    + " professionalVotesCastforNoRepresentative = ?,"
//                    + " professionalVotesCastForEEO = ?,"
//                    + " professionalVotesCastForIncumbentEEO = ?,"
//                    + " professionalVotesCastForRivalEEO1 = ?,"
//                    + " professionalVotesCastForRivalEEO2 = ?,"
//                    + " professionalVotesCastForRivalEEO3 = ?,"
//                    
//                    + " nonprofessionalApproxNumberEligible = ?,"
//                    + " nonprofessionalYES = ?,"
//                    + " nonprofessionalNO = ?,"
//                    + " nonprofessionalChallenged = ?,"
//                    + " nonprofessionalTotalVotes = ?,"
//                    + " nonprofessionalOutcome = ?,"
//                    + " nonprofessionalWhoPrevailed = ?,"
//                    + " nonprofessionalVoidBallots = ?,"
//                    + " nonprofessionalValidVotes = ?,"
//                    + " nonprofessionalVotesCastforNoRepresentative = ?,"
//                    + " nonprofessionalVotesCastForEEO = ?,"
//                    + " nonprofessionalVotesCastForIncumbentEEO = ?,"
//                    + " nonprofessionalVotesCastForRivalEEO1 = ?,"
//                    + " nonprofessionalVotesCastForRivalEEO2 = ?,"
//                    + " nonprofessionalVotesCastForRivalEEO3 = ?,"
//                    
//                    + " combinedApproxNumberEligible = ?,"
//                    + " combinedYES = ?,"
//                    + " combinedNO = ?,"
//                    + " combinedChallenged = ?,"
//                    + " combinedTotalVotes = ?,"
//                    + " combinedOutcome = ?,"
//                    + " combinedWhoPrevailed = ?,"
//                    + " combinedVoidBallots = ?,"
//                    + " combinedValidVotes = ?,"
//                    + " combinedVotesCastforNoRepresentative = ?,"
//                    + " combinedVotesCastForEEO = ?,"
//                    + " combinedVotesCastForIncumbentEEO = ?,"
//                    + " combinedVotesCastForRivalEEO1 = ?,"
//                    + " combinedVotesCastForRivalEEO2 = ?,"
//                    + " combinedVotesCastForRivalEEO3 = ?"
//                    + " where caseYear = ?"
//                    + " AND caseType = ?"
//                    + " AND caseMonth = ?"
//                    + " AND caseNumber = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setBoolean(1, newCaseInformation.multicaseElection);
//            preparedStatement.setString(2, newCaseInformation.electionType1);
//            preparedStatement.setString(3, newCaseInformation.electionType2);
//            preparedStatement.setString(4, newCaseInformation.electionType3);   
//            preparedStatement.setTimestamp(5, newCaseInformation.eligibilityDate);  
//            preparedStatement.setString(6, newCaseInformation.ballotOne);  
//            preparedStatement.setString(7, newCaseInformation.ballotTwo);  
//            preparedStatement.setString(8, newCaseInformation.ballotThree);  
//            preparedStatement.setString(9, newCaseInformation.ballotFour);  
//            preparedStatement.setTimestamp(10, newCaseInformation.mailKitDate);  
//            preparedStatement.setTimestamp(11, newCaseInformation.pollingStartDate);  
//            preparedStatement.setTimestamp(12, newCaseInformation.pollingEndDate);  
//            preparedStatement.setString(13, newCaseInformation.ballotsCountDay);  
//            preparedStatement.setTimestamp(14, newCaseInformation.ballotsCountDate);  
//            preparedStatement.setTimestamp(15, newCaseInformation.ballotsCountTime);  
//            preparedStatement.setTimestamp(16, newCaseInformation.eligibilityListDate);  
//            preparedStatement.setTimestamp(17, newCaseInformation.preElectionConfDate);
//            preparedStatement.setObject(18, newCaseInformation.selfReleasing); 
//            preparedStatement.setObject(19, newCaseInformation.resultApproxNumberEligibleVotes); 
//            preparedStatement.setObject(20, newCaseInformation.resultVoidBallots);
//            preparedStatement.setObject(21, newCaseInformation.resultVotesCastForEEO);
//            preparedStatement.setObject(22, newCaseInformation.resultVotesCastForIncumbentEEO);
//            preparedStatement.setObject(23, newCaseInformation.resultVotesCastForRivalEEO1);
//            preparedStatement.setObject(24, newCaseInformation.resultVotesCastForRivalEEO2);
//            preparedStatement.setObject(25, newCaseInformation.resultVotesCastForRivalEEO3);
//            preparedStatement.setObject(26, newCaseInformation.resultVotesCastForNoRepresentative);
//            preparedStatement.setObject(27, newCaseInformation.resultValidVotesCounted);
//            preparedStatement.setObject(28, newCaseInformation.resultChallengedBallots);
//            preparedStatement.setObject(29, newCaseInformation.resultTotalBallotsCast);
//            preparedStatement.setObject(30, newCaseInformation.resultWHoPrevailed == null ? null : CaseParty.getElectionID(newCaseInformation.resultWHoPrevailed));
//            preparedStatement.setObject(31, professional[0].equals("") ? null : Integer.valueOf(professional[0]));
//            preparedStatement.setObject(32, professional[1].equals("") ? null : Integer.valueOf(professional[1]));
//            preparedStatement.setObject(33, professional[2].equals("") ? null : Integer.valueOf(professional[2]));
//            preparedStatement.setObject(34, professional[3].equals("") ? null : Integer.valueOf(professional[3]));
//            preparedStatement.setObject(35, professional[4].equals("") ? null : Integer.valueOf(professional[4]));
//            preparedStatement.setObject(36, professional[5].trim().equals("") ? null : professional[5]);
//            preparedStatement.setObject(37, professional[6].trim().equals("") ? null : CaseParty.getElectionID(professional[6].trim()));
//            preparedStatement.setObject(38, professional[7].equals("") ? null : Integer.valueOf(professional[7]));
//            preparedStatement.setObject(39, professional[8].equals("") ? null : Integer.valueOf(professional[8]));
//            preparedStatement.setObject(40, professional[9].equals("") ? null : Integer.valueOf(professional[9]));
//            preparedStatement.setObject(41, professional[10].equals("") ? null : Integer.valueOf(professional[10]));
//            preparedStatement.setObject(42, professional[11].equals("") ? null : Integer.valueOf(professional[11]));
//            preparedStatement.setObject(43, professional[12].equals("") ? null : Integer.valueOf(professional[12]));
//            preparedStatement.setObject(44, professional[13].equals("") ? null : Integer.valueOf(professional[13]));
//            preparedStatement.setObject(45, professional[14].equals("") ? null : Integer.valueOf(professional[14]));
//            
//            preparedStatement.setObject(46, nonprofessional[0].equals("") ? null : Integer.valueOf(nonprofessional[0]));
//            preparedStatement.setObject(47, nonprofessional[1].equals("") ? null : Integer.valueOf(nonprofessional[1]));
//            preparedStatement.setObject(48, nonprofessional[2].equals("") ? null : Integer.valueOf(nonprofessional[2]));
//            preparedStatement.setObject(49, nonprofessional[3].equals("") ? null : Integer.valueOf(nonprofessional[3]));
//            preparedStatement.setObject(50, nonprofessional[4].equals("") ? null : Integer.valueOf(nonprofessional[4]));
//            preparedStatement.setObject(51, nonprofessional[5].equals("") ? null : nonprofessional[5]);
//            preparedStatement.setObject(52, nonprofessional[6].trim().equals("") ? null : CaseParty.getElectionID(nonprofessional[6].trim()));
//            preparedStatement.setObject(53, nonprofessional[7].equals("") ? null : Integer.valueOf(nonprofessional[7]));
//            preparedStatement.setObject(54, nonprofessional[8].equals("") ? null : Integer.valueOf(nonprofessional[8]));
//            preparedStatement.setObject(55, nonprofessional[9].equals("") ? null : Integer.valueOf(nonprofessional[9]));
//            preparedStatement.setObject(56, nonprofessional[10].equals("") ? null : Integer.valueOf(nonprofessional[10]));
//            preparedStatement.setObject(57, nonprofessional[11].equals("") ? null : Integer.valueOf(nonprofessional[11]));
//            preparedStatement.setObject(58, nonprofessional[12].equals("") ? null : Integer.valueOf(nonprofessional[12]));
//            preparedStatement.setObject(59, nonprofessional[13].equals("") ? null : Integer.valueOf(nonprofessional[13]));
//            preparedStatement.setObject(60, nonprofessional[14].equals("") ? null : Integer.valueOf(nonprofessional[14]));
//            
//            preparedStatement.setObject(61, combined[0].equals("") ? null : Integer.valueOf(combined[0]));
//            preparedStatement.setObject(62, combined[1].equals("") ? null : Integer.valueOf(combined[1]));
//            preparedStatement.setObject(63, combined[2].equals("") ? null : Integer.valueOf(combined[2]));
//            preparedStatement.setObject(64, combined[3].equals("") ? null : Integer.valueOf(combined[3]));
//            preparedStatement.setObject(65, combined[4].equals("") ? null : Integer.valueOf(combined[4]));
//            preparedStatement.setObject(66, combined[5].equals("") ? null : combined[5]);
//            preparedStatement.setObject(67, combined[6].trim().equals("") ? null : CaseParty.getElectionID(combined[6].trim()));
//            preparedStatement.setObject(68, combined[7].equals("") ? null : Integer.valueOf(combined[7]));
//            preparedStatement.setObject(69, combined[8].equals("") ? null : Integer.valueOf(combined[8]));
//            preparedStatement.setObject(70, combined[9].equals("") ? null : Integer.valueOf(combined[9]));
//            preparedStatement.setObject(71, combined[10].equals("") ? null : Integer.valueOf(combined[10]));
//            preparedStatement.setObject(72, combined[11].equals("") ? null : Integer.valueOf(combined[11]));
//            preparedStatement.setObject(73, combined[12].equals("") ? null : Integer.valueOf(combined[12]));
//            preparedStatement.setObject(74, combined[13].equals("") ? null : Integer.valueOf(combined[13]));
//            preparedStatement.setObject(75, combined[14].equals("") ? null : Integer.valueOf(combined[14]));
//            
//            preparedStatement.setString(76, Global.caseYear);
//            preparedStatement.setString(77, Global.caseType);
//            preparedStatement.setString(78, Global.caseMonth);
//            preparedStatement.setString(79, Global.caseNumber);
//
//            int success = preparedStatement.executeUpdate();
//            
//            if(success == 1) {
//                Activity.addActivty("Updated Election Information", null);
//            }
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//    }
//    
    public static void updateCMDSInformation(CMDSCase newCaseInformation, CMDSCase caseInformation) {
        CMDSCase rep = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase set"
                    + " opendate = ?,"
                    + " groupNumber = ?,"
                    + " aljid = ?,"
                    + " pbrBox = ?,"
                    + " closeDate = ?,"
                    + " caseStatus = ?,"
                    + " result = ?,"
                    + " mediatorID = ?,"
                    + " groupType = ?,"
                    + " reclassCode = ?,"
                    + " mailedRR = ?,"
                    + " mailedBO = ?,"
                    + " mailedPO1 = ?,"
                    + " mailedPO2 = ?,"
                    + " mailedPO3 = ?,"
                    + " mailedPO4 = ?,"
                    + " remailedRR = ?,"
                    + " remailedBO = ?,"
                    + " remailedPO1 = ?,"
                    + " remailedPO2 = ?,"
                    + " remailedPO3 = ?,"
                    + " remailedPO4 = ?,"
                    + " returnReceiptRR = ?,"
                    + " returnReceiptBO = ?,"
                    + " returnReceiptPO1 = ?,"
                    + " returnReceiptPO2 = ?,"
                    + " returnReceiptPO3 = ?,"
                    + " returnReceiptPO4 = ?,"
                    + " pullDateRR = ?,"
                    + " pullDateBO = ?,"
                    + " pullDatePO1 = ?,"
                    + " pullDatePO2 = ?,"
                    + " pullDatePO3 = ?,"
                    + " pullDatePO4 = ?,"
                    + " hearingCompletedDate = ?,"
                    + " postHearingDriefsDue = ?"
                    + " from CMDSCase where"
                    + " caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, newCaseInformation.openDate);
            preparedStatement.setString(2, newCaseInformation.groupNumber);
            preparedStatement.setInt(3, newCaseInformation.aljID);
            preparedStatement.setString(4, newCaseInformation.PBRBox);
            preparedStatement.setTimestamp(5, newCaseInformation.closeDate);
            preparedStatement.setString(6, newCaseInformation.caseStatus);
            preparedStatement.setString(7, newCaseInformation.result);
            preparedStatement.setInt(8, newCaseInformation.mediatorID);
            preparedStatement.setString(9, newCaseInformation.groupType);
            preparedStatement.setString(10, newCaseInformation.reclassCode);
            preparedStatement.setTimestamp(11, newCaseInformation.mailedRR);
            preparedStatement.setTimestamp(12, newCaseInformation.mailedBO);
            preparedStatement.setTimestamp(13, newCaseInformation.mailedPO1);
            preparedStatement.setTimestamp(14, newCaseInformation.mailedPO2);
            preparedStatement.setTimestamp(15, newCaseInformation.mailedPO3);
            preparedStatement.setTimestamp(16, newCaseInformation.mailedPO4);
            preparedStatement.setTimestamp(17, newCaseInformation.remailedRR);
            preparedStatement.setTimestamp(18, newCaseInformation.remailedBO);
            preparedStatement.setTimestamp(19, newCaseInformation.remailedPO1);
            preparedStatement.setTimestamp(20, newCaseInformation.remailedPO2);
            preparedStatement.setTimestamp(21, newCaseInformation.remailedPO3);
            preparedStatement.setTimestamp(22, newCaseInformation.remailedPO4);
            preparedStatement.setTimestamp(23, newCaseInformation.returnReceiptRR);
            preparedStatement.setTimestamp(24, newCaseInformation.returnReceiptBO);
            preparedStatement.setTimestamp(25, newCaseInformation.returnReceiptPO1);
            preparedStatement.setTimestamp(26, newCaseInformation.returnReceiptPO2);
            preparedStatement.setTimestamp(27, newCaseInformation.returnReceiptPO3);
            preparedStatement.setTimestamp(28, newCaseInformation.returnReceiptPO4);
            preparedStatement.setTimestamp(29, newCaseInformation.pullDateRR);
            preparedStatement.setTimestamp(30, newCaseInformation.pullDateBO);
            preparedStatement.setTimestamp(31, newCaseInformation.pullDatePO1);
            preparedStatement.setTimestamp(32, newCaseInformation.pullDatePO2);
            preparedStatement.setTimestamp(33, newCaseInformation.pullDatePO3);
            preparedStatement.setTimestamp(34, newCaseInformation.pullDatePO4);
            preparedStatement.setTimestamp(35, newCaseInformation.hearingCompletedDate);
            preparedStatement.setTimestamp(36, newCaseInformation.postHearingBriefsDue);
            preparedStatement.setString(37, Global.caseYear);
            preparedStatement.setString(38, Global.caseType);
            preparedStatement.setString(39, Global.caseMonth);
            preparedStatement.setString(40, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                CMDSCaseSearchData.updateCaseEntryFromCaseInformation(newCaseInformation.openDate, User.getNameByID(newCaseInformation.aljID));
                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    /**
     * Creates a duplicate case, copying all case information, excluding activity,
     * as well as copying parties
     * @param caseNumber
     * @param duplicateCase 
     */
//    public static void createDuplicateCase(String caseNumber, String duplicateCase) {
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Select * from REPCase where caseNumber = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, duplicateCase);
//
//            int success = preparedStatement.executeUpdate();
//            
//            if(success == 1) {
////                SlackNotification.sendNotification("Case " + caseNumber + " Created");
////                CaseNumber.updateNextCaseNumber(caseNumber);
////                Activity.addNewCaseActivty(caseNumber);
//                Global.root.getrEPHeaderPanel1().loadCases();
//                Global.root.getrEPHeaderPanel1().getjComboBox2().setSelectedItem(caseNumber); 
//            } else {
//                SlackNotification.sendNotification("Case " + caseNumber + " Does not exist");
//            }
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//    }
    
    private static void detailedCaseInformationSaveInformation(CMDSCase newCaseInformation, CMDSCase oldCaseInformation) {
        //opendate
        if(newCaseInformation.openDate == null && oldCaseInformation.openDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.openDate.getTime())) + " from Open Date", null);
        } else if(newCaseInformation.openDate != null && oldCaseInformation.openDate == null) {
            Activity.addActivty("Set Open Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.openDate.getTime())), null);
        } else if(newCaseInformation.openDate != null && oldCaseInformation.openDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.openDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.openDate.getTime()))))
                Activity.addActivty("Changed Open Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.openDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.openDate.getTime())), null);
        }

        //groupNumber
        if(newCaseInformation.groupNumber == null && oldCaseInformation.groupNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.groupNumber + " from Group Number", null);
        } else if(newCaseInformation.groupNumber != null && oldCaseInformation.groupNumber == null) {
            Activity.addActivty("Set Group Number to " + newCaseInformation.groupNumber, null);
        } else if(newCaseInformation.groupNumber != null && oldCaseInformation.groupNumber != null) {
            if(!newCaseInformation.groupNumber.equals(oldCaseInformation.groupNumber)) 
                Activity.addActivty("Changed Group Number from " + oldCaseInformation.groupNumber + " to " + newCaseInformation.groupNumber, null);
        }
        
        //aljid
        if(newCaseInformation.aljID == 0 && oldCaseInformation.aljID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.aljID) + " from ALJ", null);
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID == 0) {
            Activity.addActivty("Set ALJ to " + User.getNameByID(newCaseInformation.aljID), null);
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID != 0) {
            if(newCaseInformation.aljID != oldCaseInformation.aljID) 
                Activity.addActivty("Changed ALJ from " + User.getNameByID(oldCaseInformation.aljID) + " to " + User.getNameByID(newCaseInformation.aljID), null);
        }
        
        //pbrBox
        if(newCaseInformation.PBRBox == null && oldCaseInformation.PBRBox != null) {
            Activity.addActivty("Removed " + oldCaseInformation.PBRBox + " from PBR Box", null);
        } else if(newCaseInformation.PBRBox != null && oldCaseInformation.PBRBox == null) {
            Activity.addActivty("Set PBR Box to " + newCaseInformation.PBRBox, null);
        } else if(newCaseInformation.PBRBox != null && oldCaseInformation.PBRBox != null) {
            if(!newCaseInformation.PBRBox.equals(oldCaseInformation.PBRBox)) 
                Activity.addActivty("Changed PBR Box from " + oldCaseInformation.PBRBox + " to " + newCaseInformation.PBRBox, null);
        }
        
        //closeDate
        if(newCaseInformation.closeDate == null && oldCaseInformation.closeDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.closeDate.getTime())) + " from Close Date", null);
        } else if(newCaseInformation.closeDate != null && oldCaseInformation.closeDate == null) {
            Activity.addActivty("Set Close Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.closeDate.getTime())), null);
        } else if(newCaseInformation.closeDate != null && oldCaseInformation.closeDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.closeDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.closeDate.getTime()))))
                Activity.addActivty("Changed Close Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.closeDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.closeDate.getTime())), null);
        }
        
        //caseStatus
        if(newCaseInformation.caseStatus == null && oldCaseInformation.caseStatus != null) {
            Activity.addActivty("Removed " + oldCaseInformation.caseStatus + " from Case Status", null);
        } else if(newCaseInformation.caseStatus != null && oldCaseInformation.caseStatus == null) {
            Activity.addActivty("Set Case Status to " + newCaseInformation.caseStatus, null);
        } else if(newCaseInformation.caseStatus != null && oldCaseInformation.caseStatus != null) {
            if(!newCaseInformation.caseStatus.equals(oldCaseInformation.caseStatus)) 
                Activity.addActivty("Changed Case Status from " + oldCaseInformation.caseStatus + " to " + newCaseInformation.caseStatus, null);
        }
        
        //result
        if(newCaseInformation.result == null && oldCaseInformation.result != null) {
            Activity.addActivty("Removed " + oldCaseInformation.result + " from Result", null);
        } else if(newCaseInformation.result != null && oldCaseInformation.result == null) {
            Activity.addActivty("Set Result to " + newCaseInformation.result, null);
        } else if(newCaseInformation.result != null && oldCaseInformation.result != null) {
            if(!newCaseInformation.result.equals(oldCaseInformation.result)) 
                Activity.addActivty("Changed Result from " + oldCaseInformation.result + " to " + newCaseInformation.result, null);
        }
        
        //mediatorID
        if(newCaseInformation.mediatorID == 0 && oldCaseInformation.mediatorID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.mediatorID) + " from Mediator", null);
        } else if(newCaseInformation.mediatorID != 0 && oldCaseInformation.mediatorID == 0) {
            Activity.addActivty("Set Mediator to " + User.getNameByID(newCaseInformation.mediatorID), null);
        } else if(newCaseInformation.mediatorID != 0 && oldCaseInformation.mediatorID != 0) {
            if(newCaseInformation.mediatorID != oldCaseInformation.mediatorID) 
                Activity.addActivty("Changed Mediator from " + User.getNameByID(oldCaseInformation.mediatorID) + " to " + User.getNameByID(newCaseInformation.mediatorID), null);
        }
        
        //groupType
        if(newCaseInformation.groupType == null && oldCaseInformation.groupType != null) {
            Activity.addActivty("Removed " + oldCaseInformation.groupType + " from Group Type", null);
        } else if(newCaseInformation.groupType != null && oldCaseInformation.groupType == null) {
            Activity.addActivty("Set Group Type to " + newCaseInformation.groupType, null);
        } else if(newCaseInformation.groupType != null && oldCaseInformation.groupType != null) {
            if(!newCaseInformation.groupType.equals(oldCaseInformation.groupType)) 
                Activity.addActivty("Changed Group Type from " + oldCaseInformation.groupType + " to " + newCaseInformation.groupType, null);
        }
        
        //reclassCode
        if(newCaseInformation.reclassCode == null && oldCaseInformation.reclassCode != null) {
            Activity.addActivty("Removed " + oldCaseInformation.reclassCode + " from Reclass Code", null);
        } else if(newCaseInformation.reclassCode != null && oldCaseInformation.reclassCode == null) {
            Activity.addActivty("Set Reclass Code to " + newCaseInformation.reclassCode, null);
        } else if(newCaseInformation.reclassCode != null && oldCaseInformation.reclassCode != null) {
            if(!newCaseInformation.reclassCode.equals(oldCaseInformation.reclassCode)) 
                Activity.addActivty("Changed Reclass Code from " + oldCaseInformation.reclassCode + " to " + newCaseInformation.reclassCode, null);
        }
    }
 
    public static List<String> loadRelatedCases() {
        
        List<String> caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select caseYear, caseType, caseMonth, caseNumber from CMDSCase  where openDate between DateAdd(DD,-7,GETDATE()) and GETDATE() Order By caseYear DESC, caseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                caseNumberList.add(caseNumberRS.getString("caseYear") + "-"
                    + caseNumberRS.getString("caseType") + "-" 
                    + caseNumberRS.getString("caseMonth") + "-"
                    + caseNumberRS.getString("caseNumber"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caseNumberList;
    }
    
    public static boolean validateCase(String caseNumber) {
        boolean validCase = false;
        
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select COUNT(*) AS CASECOUNT from CMDSCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber.split("-")[0]);
            preparedStatement.setString(2, caseNumber.split("-")[1]);
            preparedStatement.setString(3, caseNumber.split("-")[2]);
            preparedStatement.setString(4, caseNumber.split("-")[3]);
            
            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                if(caseNumberRS.getInt("CASECOUNT") > 0) {
                    validCase = true;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validCase;
    }
    
    public static void updateCMDSGroupNumber(String caseNumber, String groupNumber) {
        boolean validCase = false;
        
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update CMDSCase Set groupNumber = ?"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, groupNumber);
            preparedStatement.setString(2, caseNumber.split("-")[0]);
            preparedStatement.setString(3, caseNumber.split("-")[1]);
            preparedStatement.setString(4, caseNumber.split("-")[2]);
            preparedStatement.setString(5, caseNumber.split("-")[3]);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<String> loadGroupCases(String groupNumber) {
        
        List<String> caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select caseYear, caseType, caseMonth, caseNumber from CMDSCase  where groupNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, groupNumber);
            
            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                caseNumberList.add(caseNumberRS.getString("caseYear") + "-"
                    + caseNumberRS.getString("caseType") + "-" 
                    + caseNumberRS.getString("caseMonth") + "-"
                    + caseNumberRS.getString("caseNumber"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caseNumberList;
    }
    
//    
//    public static boolean checkIfFristCaseOfMonth(String year, String type, String month) {
//        boolean firstCase = false;
//        
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Select"
//                    + " COUNT(*) AS CasesThisMonth"
//                    + " from REPCase"
//                    + " where caseYear = ? "
//                    + " and caseType = ? "
//                    + " and caseMonth = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, year);
//            preparedStatement.setString(2, type);
//            preparedStatement.setString(3, month);
//
//            ResultSet caseNumberRS = preparedStatement.executeQuery();
//           
//            if(caseNumberRS.next()) {
//                 if(caseNumberRS.getInt("CasesThisMonth") > 0) {
//                     firstCase = false;
//                 } else {
//                     firstCase = true;
//                 }
//            }
//            stmt.close();
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return firstCase;
//    }
}
