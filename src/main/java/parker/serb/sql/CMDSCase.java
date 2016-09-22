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
    public String aljID;
    public Timestamp closeDate;
    public Timestamp inventoryStatusDate;
    public String inventroyStatusLine;
    public String caseStatus;
    public String result;
//    public String status1;
//    public String status2;
//    public int currentOwnerID;
//    public String county;
//    public String employerIDNumber;
//    public String deptInState;
//    public String bargainingUnitNumber;
//    public boolean boardCertified;
//    public boolean deemedCertified;
//    public boolean certificationRevoked;
//    public String note;
//    public Timestamp fileDate;
//    public Timestamp amendedFiliingDate;
//    public Timestamp alphaListDate;
//    public Timestamp finalBoardDate;
//    public Timestamp registrationLetterSent;
//    public Timestamp dateOfAppeal;
//    public Timestamp courtClosedDate;
//    public Timestamp returnSOIDueDate;
//    public Timestamp actualSOIReturnDate;
//    public int SOIReturnInitials;
//    public Timestamp REPClosedCaseDueDate;
//    public Timestamp actualREPClosedDate;
//    public int REPClosedUser;
//    public Timestamp actualClerksClosedDate;
//    public int clerksClosedUser;
//    public String fileBy;
//    public String bargainingUnitIncluded;
//    public String bargainingUnitExcluded;
//    public String optInIncluded;
//    public boolean professionalNonProfessional;
//    public String professionalIncluded;
//    public String professionalExcluded;
//    public String nonProfessionalIncluded;
//    public String nonProfessionalExcluded;
//    public String toReflect;
//    public String typeFiledBy;
//    public String typeFiledVia;
//    public String positionStatementFiledBy;
//    public String EEONameChangeFrom;
//    public String EEONameChangeTo;
//    public String ERNameChangeFrom;
//    public String ERNameChangeTo;
//    public String boardActionType;
//    public Timestamp boardActionDate;
//    public int hearingPersonID;
//    public String boardStatusNote;
//    public String boardStatusBlurb;
    
    //electionData
//    public boolean multicaseElection;
//    public String electionType1;
//    public String electionType2;
//    public String electionType3;
//    public Timestamp eligibilityDate;
//    public String ballotOne;
//    public String ballotTwo;
//    public String ballotThree;
//    public String ballotFour;
//    public Timestamp mailKitDate;
//    public Timestamp pollingStartDate;
//    public Timestamp pollingEndDate;
//    public String ballotsCountDay;
//    public Timestamp ballotsCountDate;
//    public Timestamp ballotsCountTime;
//    public Timestamp eligibilityListDate;
//    public Timestamp preElectionConfDate;
//    public String selfReleasing;
    
    //results
//    public String resultApproxNumberEligibleVotes;
//    public String resultVoidBallots;
//    public String resultVotesCastForEEO;
//    public String resultVotesCastForIncumbentEEO;
//    public String resultVotesCastForRivalEEO1;
//    public String resultVotesCastForRivalEEO2;
//    public String resultVotesCastForRivalEEO3;
//    public String resultVotesCastForNoRepresentative;
//    public String resultValidVotesCounted;
//    public String resultChallengedBallots;
//    public String resultTotalBallotsCast;
//    public String resultWHoPrevailed;
    
    //professional
//    public String professionalApproxNumberEligible;
//    public String professionalYES;
//    public String professionalNO;
//    public String professionalChallenged;
//    public String professionalTotalVotes;
//    public String professionalOutcome;
//    public String professionalWhoPrevailed;
//    public String professionalVoidBallots;
//    public String professionalValidVotes;
//    public String professionalVotesCastForNoRepresentative;
//    public String professionalVotesCastForEEO;
//    public String professionalVotesCastForIncumbentEEO;
//    public String professionalVotesCastForRivalEEO1;
//    public String professionalVotesCastForRivalEEO2;
//    public String professionalVotesCastForRivalEEO3;
    
    //nonprofessional
//    public String nonprofessionalApproxNumberEligible;
//    public String nonprofessionalYES;
//    public String nonprofessionalNO;
//    public String nonprofessionalChallenged;
//    public String nonprofessionalTotalVotes;
//    public String nonprofessionalOutcome;
//    public String nonprofessionalWhoPrevailed;
//    public String nonprofessionalVoidBallots;
//    public String nonprofessionalValidVotes;
//    public String nonprofessionalVotesCastForNoRepresentative;
//    public String nonprofessionalVotesCastForEEO;
//    public String nonprofessionalVotesCastForIncumbentEEO;
//    public String nonprofessionalVotesCastForRivalEEO1;
//    public String nonprofessionalVotesCastForRivalEEO2;
//    public String nonprofessionalVotesCastForRivalEEO3;
    
    //combined
//    public String combinedApproxNumberEligible;
//    public String combinedYES;
//    public String combinedlNO;
//    public String combinedChallenged;
//    public String combinedTotalVotes;
//    public String combinedOutcome;
//    public String combinedWhoPrevailed;
//    public String combinedVoidBallots;
//    public String combinedValidVotes;
//    public String combinedVotesCastForNoRepresentative;
//    public String combinedVotesCastForEEO;
//    public String combinedVotesCastForIncumbentEEO;
//    public String combinedVotesCastForRivalEEO1;
//    public String combinedVotesCastForRivalEEO2;
//    public String combinedVotesCastForRivalEEO3;
    
    
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
                cmds.aljID = caseHeader.getString("aljID");
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
    
//    public static CMDSCase loadCaseInformation() {
//        CMDSCase rep = null;
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Select"
//                    + " [type],"
//                    + " status1,"
//                    + " status2,"
//                    + " currentOwnerID,"
//                    + " county,"
//                    + " employerIDNumber,"
//                    + " deptInState,"
//                    + " bargainingUnitNumber,"
//                    + " boardCertified,"
//                    + " deemedCertified,"
//                    + " certificationRevoked,"
//                    + " fileDate,"
//                    + " amendedFilingDate,"
//                    + " finalBoardDate,"
//                    + " registrationLetterSent,"
//                    + " dateOfAppeal,"
//                    + " courtClosedDate,"
//                    + " returnSOIDueDate,"
//                    + " actualSOIReturnDate,"
//                    + " SOIReturnIntials,"
//                    + " REPClosedCaseDueDate,"
//                    + " actualREPClosedDate,"
//                    + " REPClosedUser,"
//                    + " actualClerksClosedDate,"
//                    + " clerksClosedUser,"
//                    + " alphaListDate,"
//                    + " note"
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
//                
//                rep.note = caseInformation.getString("note");
//                
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
//                rep.actualClerksClosedDate= caseInformation.getTimestamp("actualClerksClosedDate");
//                rep.clerksClosedUser = caseInformation.getInt("clerksClosedUser");
//            }
//        } catch (SQLException ex) {
////            SlackNotification.sendNotification(ex.getMessage());
//        }
//        return rep;
//    }
    
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
//    public static void updateCaseDetails(CMDSCase newCaseInformation, CMDSCase caseInformation) {
//        CMDSCase rep = null;
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Update REPCase set"
//                    + " fileBy = ?,"
//                    + " bargainingUnitIncluded = ?,"
//                    + " bargainingUnitExcluded = ?,"
//                    + " optInIncluded = ?,"
//                    + " professionalNonProfessional = ?,"
//                    + " professionalIncluded = ?,"
//                    + " professionalExcluded = ?,"
//                    + " nonProfessionalIncluded = ?,"
//                    + " nonProfessionalExcluded = ?,"
//                    + " toReflect = ?,"
//                    + " typeFiledBy = ?,"
//                    + " typeFiledVia = ? ,"
//                    + " positionStatementFiledBy = ?,"
//                    + " EEONameChangeFrom = ?,"
//                    + " EEONameChangeTo = ?,"
//                    + " ERNameChangeFrom = ?,"
//                    + " ERNameChangeTo = ?"
//                    + " from REPCase where caseYear = ? "
//                    + " AND caseType = ? "
//                    + " AND caseMonth = ? "
//                    + " AND caseNumber = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, newCaseInformation.fileBy);
//            preparedStatement.setString(2, newCaseInformation.bargainingUnitIncluded);
//            preparedStatement.setString(3, newCaseInformation.bargainingUnitExcluded);
//            preparedStatement.setString(4, newCaseInformation.optInIncluded);
//            preparedStatement.setBoolean(5, newCaseInformation.professionalNonProfessional);
//            preparedStatement.setString(6, newCaseInformation.professionalIncluded);
//            preparedStatement.setString(7, newCaseInformation.professionalExcluded);
//            preparedStatement.setString(8, newCaseInformation.nonProfessionalIncluded);
//            preparedStatement.setString(9, newCaseInformation.nonProfessionalExcluded);
//            preparedStatement.setString(10, newCaseInformation.toReflect);
//            preparedStatement.setString(11, newCaseInformation.typeFiledBy);
//            preparedStatement.setString(12, newCaseInformation.typeFiledVia);
//            preparedStatement.setString(13, newCaseInformation.positionStatementFiledBy);
//            preparedStatement.setString(14, newCaseInformation.EEONameChangeFrom);
//            preparedStatement.setString(15, newCaseInformation.EEONameChangeTo);
//            preparedStatement.setString(16, newCaseInformation.ERNameChangeFrom);
//            preparedStatement.setString(17, newCaseInformation.ERNameChangeTo);
//            preparedStatement.setString(18, Global.caseYear);
//            preparedStatement.setString(19, Global.caseType);
//            preparedStatement.setString(20, Global.caseMonth);
//            preparedStatement.setString(21, Global.caseNumber);
//
//            int success = preparedStatement.executeUpdate();
//            
//            if(success == 1) {
//                detailedCaseDetailsSaveInformation(newCaseInformation, caseInformation);
//            }
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//    }
    
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
    
//    public static void updateCaseInformation(CMDSCase newCaseInformation, CMDSCase caseInformation) {
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//             String sql = "Update REPCase set"
//                    + " type = ?,"
//                    + " status1 = ?,"
//                    + " status2 = ?,"
//                    + " currentOwnerID = ?,"
//                    + " county = ?,"
//                    + " employerIDNumber = ?,"
//                    + " deptInState = ?,"
//                    + " bargainingUnitNumber = ?,"
//                    + " boardCertified = ?,"
//                    + " deemedCertified = ?,"
//                    + " certificationRevoked = ?,"
//                    + " fileDate = ?,"
//                    + " amendedFilingDate = ?,"
//                    + " finalBoardDate = ?,"
//                    + " registrationLetterSent = ?,"
//                    + " dateOfAppeal = ?,"
//                    + " courtClosedDate = ?,"
//                    + " returnSOIDueDate = ?,"
//                    + " actualSOIReturnDate = ?,"
//                    + " SOIReturnIntials = ?,"
//                    + " REPClosedCaseDueDate = ?,"
//                    + " ActualREPClosedDate = ?,"
//                    + " REPClosedUser = ?,"
//                    + " ActualClerksClosedDate = ?,"
//                    + " ClerksClosedUser = ?,"
//                    + " alphaListDate = ?,"
//                    + " note = ?"
//                    + " where caseYear = ?"
//                    + " AND caseType = ?"
//                    + " AND caseMonth = ? "
//                    + " AND caseNumber = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, newCaseInformation.type);
//            preparedStatement.setString(2, newCaseInformation.status1);
//            preparedStatement.setString(3, newCaseInformation.status2);
//            preparedStatement.setInt(4, newCaseInformation.currentOwnerID);
//            preparedStatement.setString(5, newCaseInformation.county);
//            preparedStatement.setString(6, newCaseInformation.employerIDNumber);
//            preparedStatement.setString(7, newCaseInformation.deptInState);
//            preparedStatement.setString(8, newCaseInformation.bargainingUnitNumber);
//            preparedStatement.setBoolean(9, newCaseInformation.boardCertified);
//            preparedStatement.setBoolean(10, newCaseInformation.deemedCertified);
//            preparedStatement.setBoolean(11, newCaseInformation.certificationRevoked);
//            preparedStatement.setTimestamp(12, newCaseInformation.fileDate);
//            preparedStatement.setTimestamp(13, newCaseInformation.amendedFiliingDate);
//            preparedStatement.setTimestamp(14, newCaseInformation.finalBoardDate);
//            preparedStatement.setTimestamp(15, newCaseInformation.registrationLetterSent);
//            preparedStatement.setTimestamp(16, newCaseInformation.dateOfAppeal);
//            preparedStatement.setTimestamp(17, newCaseInformation.courtClosedDate);
//            preparedStatement.setTimestamp(18, newCaseInformation.returnSOIDueDate);
//            preparedStatement.setTimestamp(19, newCaseInformation.actualSOIReturnDate);
//            preparedStatement.setInt(20, newCaseInformation.SOIReturnInitials);
//            preparedStatement.setTimestamp(21, newCaseInformation.REPClosedCaseDueDate);
//            preparedStatement.setTimestamp(22, newCaseInformation.actualREPClosedDate);
//            preparedStatement.setInt(23, newCaseInformation.REPClosedUser);
//            preparedStatement.setTimestamp(24, newCaseInformation.actualClerksClosedDate);
//            preparedStatement.setInt(25, newCaseInformation.clerksClosedUser);
//            preparedStatement.setTimestamp(26, newCaseInformation.alphaListDate);
//            preparedStatement.setString(27, newCaseInformation.note);
//            preparedStatement.setString(28, Global.caseYear);
//            preparedStatement.setString(29, Global.caseType);
//            preparedStatement.setString(30, Global.caseMonth);
//            preparedStatement.setString(31, Global.caseNumber);
//
//            int success = preparedStatement.executeUpdate();
//            
//            if(success == 1) {
//                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
//                REPCaseSearchData.updateCaseEntryFromCaseInformation(
//                        newCaseInformation.bargainingUnitNumber,
//                        newCaseInformation.county,
//                        getCertificationText(newCaseInformation));
//                EmployerCaseSearchData.updateFileDate(
//                        newCaseInformation.fileDate);
//                EmployerCaseSearchData.updateCaseStatus(
//                        newCaseInformation.status1);
//                EmployerCaseSearchData.updateEmployer(
//                        newCaseInformation.employerIDNumber);
//            } 
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//    }
//    
//    private static String getCertificationText(CMDSCase newCase) {
//        if(newCase.boardCertified) {
//            return "Board";
//        } else if(newCase.deemedCertified) {
//            return "Deemed";
//        } else if(newCase.certificationRevoked) {
//            return "Cert Revoked";
//        } else {
//            return "";
//        }
//    }
//    
//    private static void detailedCaseInformationSaveInformation(CMDSCase newCaseInformation, CMDSCase oldCaseInformation) {
//        //caseType
//        if(newCaseInformation.type == null && oldCaseInformation.type != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.type + " from Case Type", null);
//        } else if(newCaseInformation.type != null && oldCaseInformation.type == null) {
//            Activity.addActivty("Set Case Type to " + newCaseInformation.type, null);
//        } else if(newCaseInformation.type != null && oldCaseInformation.type != null) {
//            if(!newCaseInformation.type.equals(oldCaseInformation.type)) 
//                Activity.addActivty("Changed Case Type from " + oldCaseInformation.type + " to " + newCaseInformation.type, null);
//        }
//        
//        //status1
//        if(newCaseInformation.status1 == null && oldCaseInformation.status1 != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.status1 + " from Status 1", null);
//        } else if(newCaseInformation.status1 != null && oldCaseInformation.status1 == null) {
//            Activity.addActivty("Set Status 1 to " + newCaseInformation.status1, null);
//        } else if(newCaseInformation.status1 != null && oldCaseInformation.status1 != null) {
//            if(!newCaseInformation.status1.equals(oldCaseInformation.status1)) 
//                Activity.addActivty("Changed Status 1 from " + oldCaseInformation.status1 + " to " + newCaseInformation.status1, null);
//        }
//        
//        //satus2
//        if(newCaseInformation.status2 == null && oldCaseInformation.status2 != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.status2 + " from Status 2", null);
//        } else if(newCaseInformation.status2 != null && oldCaseInformation.status2 == null) {
//            Activity.addActivty("Set Status 2 to " + newCaseInformation.status2, null);
//        } else if(newCaseInformation.status2 != null && oldCaseInformation.status2 != null) {
//            if(!newCaseInformation.status2.equals(oldCaseInformation.status2)) 
//                Activity.addActivty("Changed Status 2 from " + oldCaseInformation.status2 + " to " + newCaseInformation.status2, null);
//        }
//        
//        //currentOwner
//        if(newCaseInformation.currentOwnerID == 0 && oldCaseInformation.currentOwnerID != 0) {
//            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.currentOwnerID) + " from Current Owner", null);
//        } else if(newCaseInformation.currentOwnerID != 0 && oldCaseInformation.currentOwnerID == 0) {
//            Activity.addActivty("Set Current Owner to " + User.getNameByID(newCaseInformation.currentOwnerID), null);
//        } else if(newCaseInformation.currentOwnerID != 0 && oldCaseInformation.currentOwnerID != 0) {
//            if(newCaseInformation.currentOwnerID != oldCaseInformation.currentOwnerID) 
//                Activity.addActivty("Changed Current Owner from " + User.getNameByID(oldCaseInformation.currentOwnerID) + " to " + User.getNameByID(newCaseInformation.currentOwnerID), null);
//        }
//        
//        //county
//        if(newCaseInformation.county == null && oldCaseInformation.county != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.county + " from County", null);
//        } else if(newCaseInformation.county != null && oldCaseInformation.county == null) {
//            Activity.addActivty("Set County to " + newCaseInformation.county, null);
//        } else if(newCaseInformation.county != null && oldCaseInformation.county != null) {
//            if(!newCaseInformation.county.equals(oldCaseInformation.county)) 
//                Activity.addActivty("Changed County from " + oldCaseInformation.county + " to " + newCaseInformation.county, null);
//        }
//        
//        //employerIDNumber
//        if(newCaseInformation.employerIDNumber == null && oldCaseInformation.employerIDNumber != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.employerIDNumber + " from Employer ID Number", null);
//        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber == null) {
//            Activity.addActivty("Set Employer ID Number to " + newCaseInformation.employerIDNumber, null);
//        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber != null) {
//            if(!newCaseInformation.employerIDNumber.equals(oldCaseInformation.employerIDNumber)) 
//                Activity.addActivty("Changed Employer ID Number from " + oldCaseInformation.employerIDNumber + " to " + newCaseInformation.employerIDNumber, null);
//        }
//        
//        //bargUnitNumber
//        if(newCaseInformation.bargainingUnitNumber == null && oldCaseInformation.bargainingUnitNumber != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.bargainingUnitNumber + " from Bargaining Unit", null);
//        } else if(newCaseInformation.bargainingUnitNumber != null && oldCaseInformation.bargainingUnitNumber == null) {
//            Activity.addActivty("Set Bargaining Unit to " + newCaseInformation.bargainingUnitNumber, null);
//        } else if(newCaseInformation.bargainingUnitNumber != null && oldCaseInformation.bargainingUnitNumber != null) {
//            if(!newCaseInformation.bargainingUnitNumber.equals(oldCaseInformation.bargainingUnitNumber)) 
//                Activity.addActivty("Changed Bargaining Unit from " + oldCaseInformation.bargainingUnitNumber + " to " + newCaseInformation.bargainingUnitNumber, null);
//        }
//        
//        //note
//        if(newCaseInformation.note == null && oldCaseInformation.note != null) {
//            Activity.addActivty("Updated Note", null);
//        } else if(newCaseInformation.note != null && oldCaseInformation.note == null) {
//            Activity.addActivty("Updated Note", null);
//        } else if(newCaseInformation.note != null && oldCaseInformation.note != null) {
//            if(!newCaseInformation.note.equals(oldCaseInformation.note)) 
//                Activity.addActivty("Updated Note", null);
//        }
//        
//        //file date
//        if(newCaseInformation.fileDate == null && oldCaseInformation.fileDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " from File Date", null);
//        } else if(newCaseInformation.fileDate != null && oldCaseInformation.fileDate == null) {
//            Activity.addActivty("Set File Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
//        } else if(newCaseInformation.fileDate != null && oldCaseInformation.fileDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime()))))
//                Activity.addActivty("Changed File Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
//        }
//        
//        //amendedFilingDate
//        if(newCaseInformation.amendedFiliingDate == null && oldCaseInformation.amendedFiliingDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.amendedFiliingDate.getTime())) + " from Amended Filing Date", null);
//        } else if(newCaseInformation.amendedFiliingDate != null && oldCaseInformation.amendedFiliingDate == null) {
//            Activity.addActivty("Set Amended Filing Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.amendedFiliingDate.getTime())), null);
//        } else if(newCaseInformation.amendedFiliingDate != null && oldCaseInformation.amendedFiliingDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.amendedFiliingDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.amendedFiliingDate.getTime()))))
//                Activity.addActivty("Changed Amended Filing Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.amendedFiliingDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.amendedFiliingDate.getTime())), null);
//        }
//        
//        //Final Board Date
//        if(newCaseInformation.finalBoardDate == null && oldCaseInformation.finalBoardDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.finalBoardDate.getTime())) + " from Final Board Date", null);
//        } else if(newCaseInformation.finalBoardDate != null && oldCaseInformation.finalBoardDate == null) {
//            Activity.addActivty("Set Final Board Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.finalBoardDate.getTime())), null);
//        } else if(newCaseInformation.finalBoardDate != null && oldCaseInformation.finalBoardDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.finalBoardDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.finalBoardDate.getTime()))))
//                Activity.addActivty("Changed Final Board Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.finalBoardDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.finalBoardDate.getTime())), null);
//        }
//        
//        //Registration Letter Sent
//        if(newCaseInformation.registrationLetterSent == null && oldCaseInformation.registrationLetterSent != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.registrationLetterSent.getTime())) + " from Registration Letter Sent", null);
//        } else if(newCaseInformation.registrationLetterSent != null && oldCaseInformation.registrationLetterSent == null) {
//            Activity.addActivty("Set Registration Letter Sent to " + Global.mmddyyyy.format(new Date(newCaseInformation.registrationLetterSent.getTime())), null);
//        } else if(newCaseInformation.registrationLetterSent != null && oldCaseInformation.registrationLetterSent != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.registrationLetterSent.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.registrationLetterSent.getTime()))))
//                Activity.addActivty("Changed Registration Letter Sent from " + Global.mmddyyyy.format(new Date(oldCaseInformation.registrationLetterSent.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.registrationLetterSent.getTime())), null);
//        }
//        
//        //Date of Appeal
//        if(newCaseInformation.dateOfAppeal == null && oldCaseInformation.dateOfAppeal != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.dateOfAppeal.getTime())) + " from Date of Appeal", null);
//        } else if(newCaseInformation.dateOfAppeal != null && oldCaseInformation.dateOfAppeal == null) {
//            Activity.addActivty("Set Date of Appeal to " + Global.mmddyyyy.format(new Date(newCaseInformation.dateOfAppeal.getTime())), null);
//        } else if(newCaseInformation.dateOfAppeal != null && oldCaseInformation.dateOfAppeal != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.dateOfAppeal.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.dateOfAppeal.getTime()))))
//                Activity.addActivty("Changed Date of Appeal from " + Global.mmddyyyy.format(new Date(oldCaseInformation.dateOfAppeal.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.dateOfAppeal.getTime())), null);
//        }
//        
//        //Court Closed Date
//        if(newCaseInformation.courtClosedDate == null && oldCaseInformation.courtClosedDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.courtClosedDate.getTime())) + " from Court Closed Date", null);
//        } else if(newCaseInformation.courtClosedDate != null && oldCaseInformation.courtClosedDate == null) {
//            Activity.addActivty("Set Court Closed Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.courtClosedDate.getTime())), null);
//        } else if(newCaseInformation.courtClosedDate != null && oldCaseInformation.courtClosedDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.courtClosedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.courtClosedDate.getTime()))))
//                Activity.addActivty("Changed Court Closed Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.courtClosedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.courtClosedDate.getTime())), null);
//        }
//        
//        //Return SOI Due Date
//        if(newCaseInformation.returnSOIDueDate == null && oldCaseInformation.returnSOIDueDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.returnSOIDueDate.getTime())) + " from Return SOI Due Date", null);
//        } else if(newCaseInformation.returnSOIDueDate != null && oldCaseInformation.returnSOIDueDate == null) {
//            Activity.addActivty("Set Return SOI Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.returnSOIDueDate.getTime())), null);
//        } else if(newCaseInformation.returnSOIDueDate != null && oldCaseInformation.returnSOIDueDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.returnSOIDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.returnSOIDueDate.getTime()))))
//                Activity.addActivty("Changed Return SOI Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.returnSOIDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.returnSOIDueDate.getTime())), null);
//        }
//        
//        //Actual SOI return Date
//        if(newCaseInformation.actualSOIReturnDate == null && oldCaseInformation.actualSOIReturnDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualSOIReturnDate.getTime())) + " from Actual SOI Return Date", null);
//        } else if(newCaseInformation.actualSOIReturnDate != null && oldCaseInformation.actualSOIReturnDate == null) {
//            Activity.addActivty("Set Actual SOI Return Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualSOIReturnDate.getTime())), null);
//        } else if(newCaseInformation.actualSOIReturnDate != null && oldCaseInformation.actualSOIReturnDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.actualSOIReturnDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.actualSOIReturnDate.getTime()))))
//                Activity.addActivty("Changed Actual SOI Return Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualSOIReturnDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualSOIReturnDate.getTime())), null);
//        }
//        
//        //SOI Return Initials
//        if(newCaseInformation.SOIReturnInitials == 0 && oldCaseInformation.SOIReturnInitials != 0) {
//            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.SOIReturnInitials) + " from SOI Return Initials", null);
//        } else if(newCaseInformation.SOIReturnInitials != 0 && oldCaseInformation.SOIReturnInitials == 0) {
//            Activity.addActivty("Set SOI Return Initials to " + User.getNameByID(newCaseInformation.SOIReturnInitials), null);
//        } else if(newCaseInformation.SOIReturnInitials != 0 && oldCaseInformation.SOIReturnInitials != 0) {
//            if(newCaseInformation.SOIReturnInitials != oldCaseInformation.SOIReturnInitials) 
//                Activity.addActivty("Changed SOI Return Initials from " + User.getNameByID(oldCaseInformation.SOIReturnInitials) + " to " + User.getNameByID(newCaseInformation.REPClosedUser), null);
//        }
//        
//        //REPClsed Case Due Date
//        if(newCaseInformation.REPClosedCaseDueDate == null && oldCaseInformation.REPClosedCaseDueDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.REPClosedCaseDueDate.getTime())) + " from REP Closed Case Due Date", null);
//        } else if(newCaseInformation.REPClosedCaseDueDate != null && oldCaseInformation.REPClosedCaseDueDate == null) {
//            Activity.addActivty("Set REP Closed Case Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.REPClosedCaseDueDate.getTime())), null);
//        } else if(newCaseInformation.REPClosedCaseDueDate != null && oldCaseInformation.REPClosedCaseDueDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.REPClosedCaseDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.REPClosedCaseDueDate.getTime()))))
//                Activity.addActivty("Changed REP Closed Case Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.REPClosedCaseDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.REPClosedCaseDueDate.getTime())), null);
//        }
//        
//        //Actual REP Closed Date
//        if(newCaseInformation.actualREPClosedDate == null && oldCaseInformation.actualREPClosedDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualREPClosedDate.getTime())) + " from Actual REP Closed Date", null);
//        } else if(newCaseInformation.actualREPClosedDate != null && oldCaseInformation.actualREPClosedDate == null) {
//            Activity.addActivty("Set Actual REP Closed Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualREPClosedDate.getTime())), null);
//        } else if(newCaseInformation.actualREPClosedDate != null && oldCaseInformation.actualREPClosedDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.actualREPClosedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.actualREPClosedDate.getTime()))))
//                Activity.addActivty("Changed Actual REP Closed Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualREPClosedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualREPClosedDate.getTime())), null);
//        }
//        
//        //REP Closed User
//        if(newCaseInformation.REPClosedUser == 0 && oldCaseInformation.REPClosedUser != 0) {
//            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.REPClosedUser) + " from REP Closed By", null);
//        } else if(newCaseInformation.REPClosedUser != 0 && oldCaseInformation.REPClosedUser == 0) {
//            Activity.addActivty("Set REP Closed By to " + User.getNameByID(newCaseInformation.REPClosedUser), null);
//        } else if(newCaseInformation.REPClosedUser != 0 && oldCaseInformation.REPClosedUser != 0) {
//            if(newCaseInformation.REPClosedUser != oldCaseInformation.REPClosedUser) 
//                Activity.addActivty("Changed REP Closed By from " + User.getNameByID(oldCaseInformation.REPClosedUser) + " to " + User.getNameByID(newCaseInformation.REPClosedUser), null);
//        }
//        
//        //Actual Clerks Closed Date
//        if(newCaseInformation.actualClerksClosedDate == null && oldCaseInformation.actualClerksClosedDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualClerksClosedDate.getTime())) + " from Actual Clerks Closed Date", null);
//        } else if(newCaseInformation.actualClerksClosedDate != null && oldCaseInformation.actualClerksClosedDate == null) {
//            Activity.addActivty("Set Actual Clerks Closed Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualClerksClosedDate.getTime())), null);
//        } else if(newCaseInformation.actualClerksClosedDate != null && oldCaseInformation.actualClerksClosedDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.actualClerksClosedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.actualClerksClosedDate.getTime()))))
//                Activity.addActivty("Changed Actual Clerks Closed Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualClerksClosedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualClerksClosedDate.getTime())), null);
//        }
//        
//        //Clerks Closed User
//        if(newCaseInformation.clerksClosedUser == 0 && oldCaseInformation.clerksClosedUser != 0) {
//            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.clerksClosedUser) + " from Clerks Closed By", null);
//        } else if(newCaseInformation.clerksClosedUser != 0 && oldCaseInformation.clerksClosedUser == 0) {
//            Activity.addActivty("Set Clerks Closed By to " + User.getNameByID(newCaseInformation.clerksClosedUser), null);
//        } else if(newCaseInformation.clerksClosedUser != 0 && oldCaseInformation.clerksClosedUser != 0) {
//            if(newCaseInformation.clerksClosedUser != oldCaseInformation.currentOwnerID) 
//                Activity.addActivty("Changed Clerks Closed By from " + User.getNameByID(oldCaseInformation.clerksClosedUser) + " to " + User.getNameByID(newCaseInformation.clerksClosedUser), null);
//        }
//        
//        //Alpha List Date
//        if(newCaseInformation.alphaListDate == null && oldCaseInformation.alphaListDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.alphaListDate.getTime())) + " from Alpha List Receipt Date", null);
//        } else if(newCaseInformation.alphaListDate != null && oldCaseInformation.alphaListDate == null) {
//            Activity.addActivty("Set Alpha List Receipt Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.alphaListDate.getTime())), null);
//        } else if(newCaseInformation.alphaListDate != null && oldCaseInformation.alphaListDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.alphaListDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.alphaListDate.getTime()))))
//                Activity.addActivty("Changed Alpha List Receipt Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.alphaListDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.alphaListDate.getTime())), null);
//        }
//    }
//    
//    private static void detailedCaseDetailsSaveInformation(CMDSCase newCaseInformation, CMDSCase oldCaseInformation) {
//        //filedBy
//        if(newCaseInformation.fileBy == null && oldCaseInformation.fileBy != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.fileBy + " from Filed By", null);
//        } else if(newCaseInformation.fileBy != null && oldCaseInformation.fileBy == null) {
//            Activity.addActivty("Set Filed By to " + newCaseInformation.fileBy, null);
//        } else if(newCaseInformation.fileBy != null && oldCaseInformation.fileBy != null) {
//            if(!newCaseInformation.fileBy.equals(oldCaseInformation.fileBy)) 
//                Activity.addActivty("Changed Filed By from " + oldCaseInformation.fileBy + " to " + newCaseInformation.fileBy, null);
//        }
//        
//        //bargainingUnitIncluded
//        if(newCaseInformation.bargainingUnitIncluded == null && oldCaseInformation.bargainingUnitIncluded != null) {
//            Activity.addActivty("Removed Bargaining Unit Included", null);
//        } else if(newCaseInformation.bargainingUnitIncluded != null && oldCaseInformation.bargainingUnitIncluded == null) {
//            Activity.addActivty("Set Bargaining Unit Included", null);
//        } else if(newCaseInformation.bargainingUnitIncluded != null && oldCaseInformation.bargainingUnitIncluded != null) {
//            if(!newCaseInformation.bargainingUnitIncluded.equals(oldCaseInformation.bargainingUnitIncluded)) 
//                Activity.addActivty("Changed Bargaining Unit Included", null);
//        }
//        
//        //bargainingUnitExcluded
//        if(newCaseInformation.bargainingUnitExcluded == null && oldCaseInformation.bargainingUnitExcluded != null) {
//            Activity.addActivty("Removed Bargaining Unit Excluded", null);
//        } else if(newCaseInformation.bargainingUnitExcluded != null && oldCaseInformation.bargainingUnitExcluded == null) {
//            Activity.addActivty("Set Bargaining Unit Excluded", null);
//        } else if(newCaseInformation.bargainingUnitExcluded != null && oldCaseInformation.bargainingUnitExcluded != null) {
//            if(!newCaseInformation.bargainingUnitExcluded.equals(oldCaseInformation.bargainingUnitExcluded)) 
//                Activity.addActivty("Changed Bargaining Unit Excluded", null);
//        }
//        
//        //opt in included
//        if(newCaseInformation.optInIncluded == null && oldCaseInformation.optInIncluded != null) {
//            Activity.addActivty("Removed Opt-In Included", null);
//        } else if(newCaseInformation.optInIncluded != null && oldCaseInformation.optInIncluded == null) {
//            Activity.addActivty("Set Opt-In Included", null);
//        } else if(newCaseInformation.optInIncluded != null && oldCaseInformation.optInIncluded != null) {
//            if(!newCaseInformation.optInIncluded.equals(oldCaseInformation.optInIncluded)) 
//                Activity.addActivty("Changed Opt-In Included", null);
//        }
//        
//        //professional non professional
//        if(newCaseInformation.professionalNonProfessional == false && oldCaseInformation.professionalNonProfessional != false) {
//            Activity.addActivty("Unset Professional/Non-Professional", null);
//        } else if(newCaseInformation.professionalNonProfessional != false && oldCaseInformation.professionalNonProfessional == false) {
//            Activity.addActivty("Set Professional/Non-Professional", null);
//        } 
//        
//        //professional included
//        if(newCaseInformation.professionalIncluded == null && oldCaseInformation.professionalIncluded != null) {
//            Activity.addActivty("Removed Professional Included", null);
//        } else if(newCaseInformation.professionalIncluded != null && oldCaseInformation.professionalIncluded == null) {
//            Activity.addActivty("Set Professional Included", null);
//        } else if(newCaseInformation.professionalIncluded != null && oldCaseInformation.professionalIncluded != null) {
//            if(!newCaseInformation.professionalIncluded.equals(oldCaseInformation.professionalIncluded)) 
//                Activity.addActivty("Changed Professional Included", null);
//        }
//        
//        //professional excluded
//        if(newCaseInformation.professionalExcluded == null && oldCaseInformation.professionalExcluded != null) {
//            Activity.addActivty("Removed Professional Excluded", null);
//        } else if(newCaseInformation.professionalExcluded != null && oldCaseInformation.professionalExcluded == null) {
//            Activity.addActivty("Set Professional Excluded", null);
//        } else if(newCaseInformation.professionalExcluded != null && oldCaseInformation.professionalExcluded != null) {
//            if(!newCaseInformation.professionalExcluded.equals(oldCaseInformation.professionalExcluded)) 
//                Activity.addActivty("Changed Professional Excluded", null);
//        }
//        
//        //nonprofessional included
//        if(newCaseInformation.nonProfessionalIncluded == null && oldCaseInformation.nonProfessionalIncluded != null) {
//            Activity.addActivty("Removed Non-Professional Included", null);
//        } else if(newCaseInformation.nonProfessionalIncluded != null && oldCaseInformation.nonProfessionalIncluded == null) {
//            Activity.addActivty("Set Non-Professional Included", null);
//        } else if(newCaseInformation.nonProfessionalIncluded != null && oldCaseInformation.nonProfessionalIncluded != null) {
//            if(!newCaseInformation.nonProfessionalIncluded.equals(oldCaseInformation.nonProfessionalIncluded)) 
//                Activity.addActivty("Changed Non-Professional Included", null);
//        }
//        
//        //nonprofessional excluded
//        if(newCaseInformation.nonProfessionalExcluded == null && oldCaseInformation.nonProfessionalExcluded != null) {
//            Activity.addActivty("Removed Non-Professional Excluded", null);
//        } else if(newCaseInformation.nonProfessionalExcluded != null && oldCaseInformation.nonProfessionalExcluded == null) {
//            Activity.addActivty("Set Non-Professional Excluded", null);
//        } else if(newCaseInformation.nonProfessionalExcluded != null && oldCaseInformation.nonProfessionalExcluded != null) {
//            if(!newCaseInformation.nonProfessionalExcluded.equals(oldCaseInformation.nonProfessionalExcluded)) 
//                Activity.addActivty("Changed Non-Professional Excluded", null);
//        }
//        
//        //toReflect
//        if(newCaseInformation.toReflect == null && oldCaseInformation.toReflect != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.toReflect + " from To Reflect", null);
//        } else if(newCaseInformation.toReflect != null && oldCaseInformation.toReflect == null) {
//            Activity.addActivty("Set To Reflect to " + newCaseInformation.toReflect, null);
//        } else if(newCaseInformation.toReflect != null && oldCaseInformation.toReflect != null) {
//            if(!newCaseInformation.toReflect.equals(oldCaseInformation.toReflect)) 
//                Activity.addActivty("Changed To Reflect from " + oldCaseInformation.toReflect + " to " + newCaseInformation.toReflect, null);
//        }
//        
//        //toReflect
//        if(newCaseInformation.toReflect == null && oldCaseInformation.toReflect != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.toReflect + " from To Reflect", null);
//        } else if(newCaseInformation.toReflect != null && oldCaseInformation.toReflect == null) {
//            Activity.addActivty("Set To Reflect to " + newCaseInformation.toReflect, null);
//        } else if(newCaseInformation.toReflect != null && oldCaseInformation.toReflect != null) {
//            if(!newCaseInformation.toReflect.equals(oldCaseInformation.toReflect)) 
//                Activity.addActivty("Changed To Reflect from " + oldCaseInformation.toReflect + " to " + newCaseInformation.toReflect, null);
//        }
//        
//        //typeFiledBy
//        if(newCaseInformation.typeFiledBy == null && oldCaseInformation.typeFiledBy != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.typeFiledBy + " from Type Filed By", null);
//        } else if(newCaseInformation.typeFiledBy != null && oldCaseInformation.typeFiledBy == null) {
//            Activity.addActivty("Set Type Filed By to " + newCaseInformation.typeFiledBy, null);
//        } else if(newCaseInformation.typeFiledBy != null && oldCaseInformation.typeFiledBy != null) {
//            if(!newCaseInformation.typeFiledBy.equals(oldCaseInformation.typeFiledBy)) 
//                Activity.addActivty("Changed Type Filed By from " + oldCaseInformation.typeFiledBy + " to " + newCaseInformation.typeFiledBy, null);
//        }
//        
//        //typeFiledVia
//        if(newCaseInformation.typeFiledVia == null && oldCaseInformation.typeFiledVia != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.typeFiledVia + " from Type Filed Via", null);
//        } else if(newCaseInformation.typeFiledVia != null && oldCaseInformation.typeFiledVia == null) {
//            Activity.addActivty("Set Type Filed Via to " + newCaseInformation.typeFiledVia, null);
//        } else if(newCaseInformation.typeFiledVia != null && oldCaseInformation.typeFiledVia != null) {
//            if(!newCaseInformation.typeFiledVia.equals(oldCaseInformation.typeFiledVia)) 
//                Activity.addActivty("Changed Type Filed Via from " + oldCaseInformation.typeFiledVia + " to " + newCaseInformation.typeFiledVia, null);
//        }
//        
//        //positionStatementFiledBy
//        if(newCaseInformation.positionStatementFiledBy == null && oldCaseInformation.positionStatementFiledBy != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.positionStatementFiledBy + " from Position Statement Filed By", null);
//        } else if(newCaseInformation.positionStatementFiledBy != null && oldCaseInformation.positionStatementFiledBy == null) {
//            Activity.addActivty("Set Position Statement Filed By to " + newCaseInformation.positionStatementFiledBy, null);
//        } else if(newCaseInformation.positionStatementFiledBy != null && oldCaseInformation.positionStatementFiledBy != null) {
//            if(!newCaseInformation.positionStatementFiledBy.equals(oldCaseInformation.positionStatementFiledBy)) 
//                Activity.addActivty("Changed Position Statement Filed By from " + oldCaseInformation.positionStatementFiledBy + " to " + newCaseInformation.positionStatementFiledBy, null);
//        }
//        
//        //EEO Name Change From
//        if(newCaseInformation.EEONameChangeFrom == null && oldCaseInformation.EEONameChangeFrom != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.EEONameChangeFrom + " from EEO Name Change From", null);
//        } else if(newCaseInformation.EEONameChangeFrom != null && oldCaseInformation.EEONameChangeFrom == null) {
//            Activity.addActivty("Set EEO Name Change From to " + newCaseInformation.EEONameChangeFrom, null);
//        } else if(newCaseInformation.EEONameChangeFrom != null && oldCaseInformation.EEONameChangeFrom != null) {
//            if(!newCaseInformation.EEONameChangeFrom.equals(oldCaseInformation.EEONameChangeFrom)) 
//                Activity.addActivty("Changed EEO Name Change From from " + oldCaseInformation.EEONameChangeFrom + " to " + newCaseInformation.EEONameChangeFrom, null);
//        }
//        
//        //EEO Name Change To
//        if(newCaseInformation.EEONameChangeTo == null && oldCaseInformation.EEONameChangeTo != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.EEONameChangeTo + " from EEO Name Change To", null);
//        } else if(newCaseInformation.EEONameChangeTo != null && oldCaseInformation.EEONameChangeTo == null) {
//            Activity.addActivty("Set EEO Name Change To to " + newCaseInformation.EEONameChangeTo, null);
//        } else if(newCaseInformation.EEONameChangeTo != null && oldCaseInformation.EEONameChangeTo != null) {
//            if(!newCaseInformation.EEONameChangeTo.equals(oldCaseInformation.EEONameChangeTo)) 
//                Activity.addActivty("Changed EEO Name Change To from " + oldCaseInformation.EEONameChangeTo + " to " + newCaseInformation.EEONameChangeTo, null);
//        }
//        
//        //ER Name Change From
//        if(newCaseInformation.ERNameChangeFrom == null && oldCaseInformation.ERNameChangeFrom != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.ERNameChangeFrom + " from ER Name Change From", null);
//        } else if(newCaseInformation.ERNameChangeFrom != null && oldCaseInformation.ERNameChangeFrom == null) {
//            Activity.addActivty("Set ER Name Change From to " + newCaseInformation.ERNameChangeFrom, null);
//        } else if(newCaseInformation.ERNameChangeFrom != null && oldCaseInformation.ERNameChangeFrom != null) {
//            if(!newCaseInformation.ERNameChangeFrom.equals(oldCaseInformation.ERNameChangeFrom)) 
//                Activity.addActivty("Changed ER Name Change From from " + oldCaseInformation.ERNameChangeFrom + " to " + newCaseInformation.ERNameChangeFrom, null);
//        }
//        
//        //ER Name Change To
//        if(newCaseInformation.ERNameChangeTo == null && oldCaseInformation.ERNameChangeTo != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.ERNameChangeTo + " from ER Name Change To", null);
//        } else if(newCaseInformation.ERNameChangeTo != null && oldCaseInformation.ERNameChangeTo == null) {
//            Activity.addActivty("Set ER Name Change To to " + newCaseInformation.ERNameChangeTo, null);
//        } else if(newCaseInformation.ERNameChangeTo != null && oldCaseInformation.ERNameChangeTo != null) {
//            if(!newCaseInformation.ERNameChangeTo.equals(oldCaseInformation.ERNameChangeTo)) 
//                Activity.addActivty("Changed ER Name Change To from " + oldCaseInformation.ERNameChangeTo + " to " + newCaseInformation.ERNameChangeTo, null);
//        }
//    }
//
//    private static void detailedBoardStatusDetailsSaveInformation(CMDSCase newCaseInformation, CMDSCase oldCaseInformation) {
//
//        //type
//        if(newCaseInformation.boardActionType == null && oldCaseInformation.boardActionType != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.boardActionType + " from Board Action Type", null);
//        } else if(newCaseInformation.boardActionType != null && oldCaseInformation.boardActionType == null) {
//            Activity.addActivty("Set Board Action Type to " + newCaseInformation.boardActionType, null);
//        } else if(newCaseInformation.boardActionType != null && oldCaseInformation.boardActionType != null) {
//            if(!newCaseInformation.boardActionType.equals(oldCaseInformation.boardActionType)) 
//                Activity.addActivty("Changed Board Action Type from " + oldCaseInformation.boardActionType + " to " + newCaseInformation.boardActionType, null);
//        }
//        
//        //boardactionDate
//        if(newCaseInformation.boardActionDate == null && oldCaseInformation.boardActionDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionDate.getTime())) + " from Board Action Date", null);
//        } else if(newCaseInformation.boardActionDate != null && oldCaseInformation.boardActionDate == null) {
//            Activity.addActivty("Set Board Action Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardActionDate.getTime())), null);
//        } else if(newCaseInformation.boardActionDate != null && oldCaseInformation.boardActionDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.boardActionDate.getTime()))))
//                Activity.addActivty("Changed Board Action Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardActionDate.getTime())), null);
//        }
//        
//        //hearingPerson
//        if(newCaseInformation.hearingPersonID == 0 && oldCaseInformation.hearingPersonID != 0) {
//            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.hearingPersonID) + " from Hearing Person", null);
//        } else if(newCaseInformation.hearingPersonID != 0 && oldCaseInformation.hearingPersonID == 0) {
//            Activity.addActivty("Set Hearing Person to " + User.getNameByID(newCaseInformation.hearingPersonID), null);
//        } else if(newCaseInformation.hearingPersonID != 0 && oldCaseInformation.hearingPersonID != 0) {
//            if(newCaseInformation.hearingPersonID != oldCaseInformation.hearingPersonID) 
//                Activity.addActivty("Changed Hearing Person from " + User.getNameByID(oldCaseInformation.hearingPersonID) + " to " + User.getNameByID(newCaseInformation.hearingPersonID), null);
//        }
//        
//        //note
//        if(newCaseInformation.boardStatusNote == null && oldCaseInformation.boardStatusNote != null) {
//            Activity.addActivty("Removed Board Status Note", null);
//        } else if(newCaseInformation.boardStatusNote != null && oldCaseInformation.boardStatusNote == null) {
//            Activity.addActivty("Set Board Status Note", null);
//        } else if(newCaseInformation.boardStatusNote != null && oldCaseInformation.boardStatusNote != null) {
//            if(!newCaseInformation.boardStatusNote.equals(oldCaseInformation.boardStatusNote)) 
//                Activity.addActivty("Changed Board Status Note", null);
//        }
//        
//        //blurb
//        if(newCaseInformation.boardStatusBlurb == null && oldCaseInformation.boardStatusBlurb != null) {
//            Activity.addActivty("Removed Board Status Blurb", null);
//        } else if(newCaseInformation.boardStatusBlurb != null && oldCaseInformation.boardStatusBlurb == null) {
//            Activity.addActivty("Set Board Status Blurb", null);
//        } else if(newCaseInformation.boardStatusBlurb != null && oldCaseInformation.boardStatusBlurb != null) {
//            if(!newCaseInformation.boardStatusBlurb.equals(oldCaseInformation.boardStatusBlurb)) 
//                Activity.addActivty("Changed Board Status Blurb", null);
//        }
//    }
//    
//    public static List<String> loadRelatedCases() {
//        
//        List<String> caseNumberList = new ArrayList<>();
//            
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//            
//            String sql = "Select caseYear, caseType, caseMonth, caseNumber from REPCase  where fileDate between DateAdd(DD,-7,GETDATE()) and GETDATE() Order By caseYear DESC, caseNumber DESC";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            
//            ResultSet caseNumberRS = preparedStatement.executeQuery();
//            
//            while(caseNumberRS.next()) {
//                caseNumberList.add(caseNumberRS.getString("caseYear") + "-"
//                    + caseNumberRS.getString("caseType") + "-" 
//                    + caseNumberRS.getString("caseMonth") + "-"
//                    + caseNumberRS.getString("caseNumber"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return caseNumberList;
//    }
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
