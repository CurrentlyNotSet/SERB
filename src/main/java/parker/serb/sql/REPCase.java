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
public class REPCase {
    
    public int id;
    public boolean active;
    public String caseYear;
    public String caseType; 
    public String caseMonth;
    public String caseNumber;
    public String type;
    public String status1;
    public String status2;
    public int currentOwnerID;
    public String county;
    public String employerIDNumber;
    public String deptInState;
    public String bargainingUnitNumber;
    public boolean boardCertified;
    public boolean deemedCertified;
    public boolean certificationRevoked;
    public Timestamp fileDate;
    public Timestamp amendedFiliingDate;
    public Timestamp alphaListDate;
    public Timestamp finalBoardDate;
    public Timestamp registrationLetterSent;
    public Timestamp dateOfAppeal;
    public Timestamp courtClosedDate;
    public Timestamp returnSOIDueDate;
    public Timestamp actualSOIReturnDate;
    public String comments;
    public Timestamp REPClosedCaseDueDate;
    public Timestamp actualREPClosedDate;
    public int REPClosedUser;
    public Timestamp actualClerksClosedDate;
    public int clerksClosedUser;
    public String fileBy;
    public String bargainingUnitIncluded;
    public String bargainingUnitExcluded;
    public String optInIncluded;
    public boolean professionalNonProfessional;
    public String professionalIncluded;
    public String professionalExcluded;
    public String nonProfessionalIncluded;
    public String nonProfessionalExcluded;
    public String toReflect;
    public String typeFiledBy;
    public String typeFiledVia;
    public String positionStatementFiledBy;
    public String EEONameChangeFrom;
    public String EEONameChangeTo;
    public String ERNameChangeFrom;
    public String ERNameChangeTo;
    public String boardActionType;
    public Timestamp boardActionDate;
    public int hearingPersonID;
    public String boardStatusNote;
    public String boardStatusBlurb;
    
    /**
     * Load a list of the most recent 250 REP case numbers
     * @return list of rep case numbers
     */
    public static List loadREPCaseNumbers() {
        
        //TODO: Limit the load to the last 6 months of filed dates
        
        List caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP 250"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber"
                    + " from REPCase"
                    + " Order By CaseYear DESC,"
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
                    + " from REPCase"
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

            String sql = "Update REPCase"
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

            String sql = "Insert into REPCase (CaseYear, CaseType, CaseMonth, CaseNumber, FileDate) Values (?,?,?,?,?)";

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
                        
                CaseNumber.updateNextCaseNumber(caseYear, caseType, String.valueOf(Integer.valueOf(caseNumber) + 1));
                Audit.addAuditEntry("Created Case: " + fullCaseNumber);
                Activity.addNewCaseActivty(fullCaseNumber, "Case was Filed and Started");
                Global.root.getrEPHeaderPanel1().loadCases();
                Global.root.getrEPHeaderPanel1().getjComboBox2().setSelectedItem(fullCaseNumber); 
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
    public static REPCase loadHeaderInformation() {
        REPCase rep = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select fileDate,"
                    + " courtClosedDate,"
                    + " status1,"
                    + " [type],"
                    + " bargainingUnitNumber"
                    + " from REPCase where caseYear = ? AND"
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
                rep = new REPCase();
                rep.fileDate = caseHeader.getTimestamp("fileDate");
                rep.courtClosedDate = caseHeader.getTimestamp("courtClosedDate");
                rep.status1 = caseHeader.getString("status1");
                rep.type = caseHeader.getString("type");
                rep.bargainingUnitNumber = caseHeader.getString("bargainingUnitNumber");
            }
                
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return rep;
    }
    
    public static REPCase loadCaseInformation() {
        REPCase rep = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " [type],"
                    + " status1,"
                    + " status2,"
                    + " currentOwnerID,"
                    + " county,"
                    + " employerIDNumber,"
                    + " deptInState,"
                    + " bargainingUnitNumber,"
                    + " boardCertified,"
                    + " deemedCertified,"
                    + " certificationRevoked,"
                    + " fileDate,"
                    + " amendedFilingDate,"
                    + " finalBoardDate,"
                    + " registrationLetterSent,"
                    + " dateOfAppeal,"
                    + " courtClosedDate,"
                    + " returnSOIDueDate,"
                    + " actualSOIReturnDate,"
                    + " comments,"
                    + " REPClosedCaseDueDate,"
                    + " actualREPClosedDate,"
                    + " REPClosedUser,"
                    + " actualClerksClosedDate,"
                    + " clerksClosedUser,"
                    + " alphaListDate"
                    + " from REPCase where caseYear = ? "
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
                rep = new REPCase();
                rep.type = caseInformation.getString("type");
                rep.status1 = caseInformation.getString("status1");
                rep.status2 = caseInformation.getString("status2");
                rep.currentOwnerID = caseInformation.getInt("currentOwnerID");
                rep.county = caseInformation.getString("county");
                rep.employerIDNumber = caseInformation.getString("employerIDNumber");
                rep.deptInState = caseInformation.getString("deptInState");
                rep.bargainingUnitNumber = caseInformation.getString("bargainingUnitNumber");
                rep.boardCertified = caseInformation.getBoolean("boardCertified");
                rep.deemedCertified = caseInformation.getBoolean("deemedCertified");
                rep.certificationRevoked = caseInformation.getBoolean("certificationRevoked");
                
                rep.fileDate = caseInformation.getTimestamp("fileDate");
                rep.amendedFiliingDate = caseInformation.getTimestamp("amendedFilingDate");
                rep.alphaListDate = caseInformation.getTimestamp("alphaListDate");
                rep.finalBoardDate = caseInformation.getTimestamp("finalBoardDate");
                rep.registrationLetterSent = caseInformation.getTimestamp("registrationLetterSent");
                rep.dateOfAppeal = caseInformation.getTimestamp("dateOfAppeal");
                rep.courtClosedDate = caseInformation.getTimestamp("courtClosedDate");
                rep.returnSOIDueDate = caseInformation.getTimestamp("returnSOIDueDate");
                rep.actualSOIReturnDate = caseInformation.getTimestamp("actualSOIReturnDate");
                rep.comments = caseInformation.getString("comments");
                rep.REPClosedCaseDueDate = caseInformation.getTimestamp("REPClosedCaseDueDate");
                rep.actualREPClosedDate = caseInformation.getTimestamp("actualREPClosedDate");
                rep.REPClosedUser = caseInformation.getInt("REPClosedUser");
                rep.actualClerksClosedDate= caseInformation.getTimestamp("actualClerksClosedDate");
                rep.clerksClosedUser = caseInformation.getInt("clerksClosedUser");
            }
        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
        }
        return rep;
    }
    
    public static REPCase loadCaseDetails() {
        REPCase rep = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " type,"
                    + " fileBy,"
                    + " bargainingUnitIncluded,"
                    + " bargainingUnitExcluded,"
                    + " optInIncluded,"
                    + " professionalNonProfessional,"
                    + " professionalIncluded,"
                    + " professionalExcluded,"
                    + " nonProfessionalIncluded,"
                    + " nonProfessionalExcluded,"
                    + " toReflect,"
                    + " typeFiledBy,"
                    + " typeFiledVia,"
                    + " positionStatementFiledBy,"
                    + " EEONameChangeFrom,"
                    + " EEONameChangeTo,"
                    + " ERNameChangeFrom,"
                    + " ERNameChangeTo"
                    + " from REPCase where caseYear = ? "
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
                rep = new REPCase();
                rep.type = caseInformation.getString("type");
                rep.fileBy = caseInformation.getString("fileBy");
                rep.bargainingUnitIncluded = caseInformation.getString("bargainingUnitIncluded");
                rep.bargainingUnitExcluded = caseInformation.getString("bargainingUnitExcluded");
                rep.optInIncluded = caseInformation.getString("optInIncluded");
                rep.professionalNonProfessional = caseInformation.getBoolean("professionalNonProfessional");
                rep.professionalIncluded = caseInformation.getString("professionalIncluded");
                rep.professionalExcluded = caseInformation.getString("professionalExcluded");
                rep.nonProfessionalIncluded = caseInformation.getString("nonProfessionalIncluded");
                rep.nonProfessionalExcluded = caseInformation.getString("nonProfessionalExcluded");
                rep.toReflect = caseInformation.getString("toReflect");
                rep.typeFiledBy = caseInformation.getString("typeFiledBy");
                rep.typeFiledVia = caseInformation.getString("typeFiledVia");
                rep.positionStatementFiledBy = caseInformation.getString("positionStatementFiledBy");
                rep.EEONameChangeFrom = caseInformation.getString("EEONameChangeFrom");
                rep.EEONameChangeTo = caseInformation.getString("EEONameChangeTo");
                rep.ERNameChangeFrom = caseInformation.getString("ERNameChangeFrom");
                rep.ERNameChangeTo = caseInformation.getString("ERNameChangeTo");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return rep;
    }
    
    public static REPCase loadBoardStatus() {
        REPCase rep = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " boardActionType,"
                    + " boardActionDate,"
                    + " hearingPersonID,"
                    + " boardStatusNote,"
                    + " boardStatusBlurb"
                    + " from REPCase where caseYear = ? "
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
                rep = new REPCase();
                rep.type = caseInformation.getString("boardActionType");
//                rep.fileBy = caseInformation.getString("fileBy") == null ? "" : caseInformation.getString("fileBy") ;
//                rep.bargainingUnitIncluded = caseInformation.getString("bargainingUnitIncluded");
//                rep.bargainingUnitExcluded = caseInformation.getString("bargainingUnitExcluded");
//                rep.optInIncluded = caseInformation.getString("optInIncluded");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return rep;
    }
    
    public static void updateBoardStatus(REPCase newCaseInformation, REPCase caseInformation) {
        REPCase rep = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update REPCase set"
                    + " boardActionType = ?,"
                    + " boardActionDate = ?,"
                    + " hearingPersonID = ?,"
                    + " boardStatusNote = ?,"
                    + " boardStatusBlurb = ?"
                    + " from REPCase where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseInformation.boardActionType);
            preparedStatement.setTimestamp(2, newCaseInformation.boardActionDate);
            preparedStatement.setInt(3, newCaseInformation.hearingPersonID);
            preparedStatement.setString(4, newCaseInformation.boardStatusNote);
            preparedStatement.setString(5, newCaseInformation.boardStatusBlurb);
            preparedStatement.setString(6, Global.caseYear);
            preparedStatement.setString(7, Global.caseType);
            preparedStatement.setString(8, Global.caseMonth);
            preparedStatement.setString(9, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
//                detailedCaseDetailsSaveInformation(newCaseInformation, caseInformation);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void updateCaseDetails(REPCase newCaseInformation, REPCase caseInformation) {
        REPCase rep = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update REPCase set"
                    + " fileBy = ?,"
                    + " bargainingUnitIncluded = ?,"
                    + " bargainingUnitExcluded = ?,"
                    + " optInIncluded = ?,"
                    + " professionalNonProfessional = ?,"
                    + " professionalIncluded = ?,"
                    + " professionalExcluded = ?,"
                    + " nonProfessionalIncluded = ?,"
                    + " nonProfessionalExcluded = ?,"
                    + " toReflect = ?,"
                    + " typeFiledBy = ?,"
                    + " typeFiledVia = ? ,"
                    + " positionStatementFiledBy = ?,"
                    + " EEONameChangeFrom = ?,"
                    + " EEONameChangeTo = ?,"
                    + " ERNameChangeFrom = ?,"
                    + " ERNameChangeTo = ?"
                    + " from REPCase where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseInformation.fileBy);
            preparedStatement.setString(2, newCaseInformation.bargainingUnitIncluded);
            preparedStatement.setString(3, newCaseInformation.bargainingUnitExcluded);
            preparedStatement.setString(4, newCaseInformation.optInIncluded);
            preparedStatement.setBoolean(5, newCaseInformation.professionalNonProfessional);
            preparedStatement.setString(6, newCaseInformation.professionalIncluded);
            preparedStatement.setString(7, newCaseInformation.professionalExcluded);
            preparedStatement.setString(8, newCaseInformation.nonProfessionalIncluded);
            preparedStatement.setString(9, newCaseInformation.nonProfessionalExcluded);
            preparedStatement.setString(10, newCaseInformation.toReflect);
            preparedStatement.setString(11, newCaseInformation.typeFiledBy);
            preparedStatement.setString(12, newCaseInformation.typeFiledVia);
            preparedStatement.setString(13, newCaseInformation.positionStatementFiledBy);
            preparedStatement.setString(14, newCaseInformation.EEONameChangeFrom);
            preparedStatement.setString(15, newCaseInformation.EEONameChangeTo);
            preparedStatement.setString(16, newCaseInformation.ERNameChangeFrom);
            preparedStatement.setString(17, newCaseInformation.ERNameChangeTo);
            preparedStatement.setString(18, Global.caseYear);
            preparedStatement.setString(19, Global.caseType);
            preparedStatement.setString(20, Global.caseMonth);
            preparedStatement.setString(21, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedCaseDetailsSaveInformation(newCaseInformation, caseInformation);
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
    
    public static void updateCaseInformation(REPCase newCaseInformation, REPCase caseInformation) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

             String sql = "Update REPCase set"
                    + " type = ?,"
                    + " status1 = ?,"
                    + " status2 = ?,"
                    + " currentOwnerID = ?,"
                    + " county = ?,"
                    + " employerIDNumber = ?,"
                    + " deptInState = ?,"
                    + " bargainingUnitNumber = ?,"
                    + " boardCertified = ?,"
                    + " deemedCertified = ?,"
                    + " certificationRevoked = ?,"
                    + " fileDate = ?,"
                    + " amendedFilingDate = ?,"
                    + " finalBoardDate = ?,"
                    + " registrationLetterSent = ?,"
                    + " dateOfAppeal = ?,"
                    + " courtClosedDate = ?,"
                    + " returnSOIDueDate = ?,"
                    + " actualSOIReturnDate = ?,"
                    + " comments = ?,"
                    + " REPClosedCaseDueDate = ?,"
                    + " ActualREPClosedDate = ?,"
                    + " REPClosedUser = ?,"
                    + " ActualClerksClosedDate = ?,"
                    + " ClerksClosedUser = ?,"
                    + " alphaListDate = ?"
                    + " where caseYear = ?"
                    + " AND caseType = ?"
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseInformation.type);
            preparedStatement.setString(2, newCaseInformation.status1);
            preparedStatement.setString(3, newCaseInformation.status2);
            preparedStatement.setInt(4, newCaseInformation.currentOwnerID);
            preparedStatement.setString(5, newCaseInformation.county);
            preparedStatement.setString(6, newCaseInformation.employerIDNumber);
            preparedStatement.setString(7, newCaseInformation.deptInState);
            preparedStatement.setString(8, newCaseInformation.bargainingUnitNumber);
            preparedStatement.setBoolean(9, newCaseInformation.boardCertified);
            preparedStatement.setBoolean(10, newCaseInformation.deemedCertified);
            preparedStatement.setBoolean(11, newCaseInformation.certificationRevoked);
            preparedStatement.setTimestamp(12, newCaseInformation.fileDate);
            preparedStatement.setTimestamp(13, newCaseInformation.amendedFiliingDate);
            preparedStatement.setTimestamp(14, newCaseInformation.finalBoardDate);
            preparedStatement.setTimestamp(15, newCaseInformation.registrationLetterSent);
            preparedStatement.setTimestamp(16, newCaseInformation.dateOfAppeal);
            preparedStatement.setTimestamp(17, newCaseInformation.courtClosedDate);
            preparedStatement.setTimestamp(18, newCaseInformation.returnSOIDueDate);
            preparedStatement.setTimestamp(19, newCaseInformation.actualSOIReturnDate);
            preparedStatement.setString(20, newCaseInformation.comments);
            preparedStatement.setTimestamp(21, newCaseInformation.REPClosedCaseDueDate);
            preparedStatement.setTimestamp(22, newCaseInformation.actualREPClosedDate);
            preparedStatement.setInt(23, newCaseInformation.REPClosedUser);
            preparedStatement.setTimestamp(24, newCaseInformation.actualClerksClosedDate);
            preparedStatement.setInt(25, newCaseInformation.clerksClosedUser);
            preparedStatement.setTimestamp(26, newCaseInformation.alphaListDate);
            preparedStatement.setString(27, Global.caseYear);
            preparedStatement.setString(28, Global.caseType);
            preparedStatement.setString(29, Global.caseMonth);
            preparedStatement.setString(30, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
                REPCaseSearchData.updateCaseEntryFromCaseInformation(
                        newCaseInformation.bargainingUnitNumber,
                        newCaseInformation.county,
                        getCertificationText(newCaseInformation));
            } 
        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    private static String getCertificationText(REPCase newCase) {
        if(newCase.boardCertified) {
            return "Board";
        } else if(newCase.deemedCertified) {
            return "Deemed";
        } else if(newCase.certificationRevoked) {
            return "Cert Revoked";
        } else {
            return "";
        }
    }
    
    private static void detailedCaseInformationSaveInformation(REPCase newCaseInformation, REPCase oldCaseInformation) {
        //caseType
        if(newCaseInformation.type == null && oldCaseInformation.type != null) {
            Activity.addActivty("Removed " + oldCaseInformation.type + " from Case Type", null);
        } else if(newCaseInformation.type != null && oldCaseInformation.type == null) {
            Activity.addActivty("Set Case Type to " + newCaseInformation.type, null);
        } else if(newCaseInformation.type != null && oldCaseInformation.type != null) {
            if(!newCaseInformation.type.equals(oldCaseInformation.type)) 
                Activity.addActivty("Changed Case Type from " + oldCaseInformation.type + " to " + newCaseInformation.type, null);
        }
        
        //status1
        if(newCaseInformation.status1 == null && oldCaseInformation.status1 != null) {
            Activity.addActivty("Removed " + oldCaseInformation.status1 + " from Status 1", null);
        } else if(newCaseInformation.status1 != null && oldCaseInformation.status1 == null) {
            Activity.addActivty("Set Status 1 to " + newCaseInformation.status1, null);
        } else if(newCaseInformation.status1 != null && oldCaseInformation.status1 != null) {
            if(!newCaseInformation.status1.equals(oldCaseInformation.status1)) 
                Activity.addActivty("Changed Status 1 from " + oldCaseInformation.status1 + " to " + newCaseInformation.status1, null);
        }
        
        //satus2
        if(newCaseInformation.status2 == null && oldCaseInformation.status2 != null) {
            Activity.addActivty("Removed " + oldCaseInformation.status2 + " from Status 2", null);
        } else if(newCaseInformation.status2 != null && oldCaseInformation.status2 == null) {
            Activity.addActivty("Set Status 2 to " + newCaseInformation.status2, null);
        } else if(newCaseInformation.status2 != null && oldCaseInformation.status2 != null) {
            if(!newCaseInformation.status2.equals(oldCaseInformation.status2)) 
                Activity.addActivty("Changed Status 2 from " + oldCaseInformation.status2 + " to " + newCaseInformation.status2, null);
        }
        
        //currentOwner
        if(newCaseInformation.currentOwnerID == 0 && oldCaseInformation.currentOwnerID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.currentOwnerID) + " from Current Owner", null);
        } else if(newCaseInformation.currentOwnerID != 0 && oldCaseInformation.currentOwnerID == 0) {
            Activity.addActivty("Set Current Owner to " + User.getNameByID(newCaseInformation.currentOwnerID), null);
        } else if(newCaseInformation.currentOwnerID != 0 && oldCaseInformation.currentOwnerID != 0) {
            if(newCaseInformation.currentOwnerID != oldCaseInformation.currentOwnerID) 
                Activity.addActivty("Changed Current Owner from " + User.getNameByID(oldCaseInformation.currentOwnerID) + " to " + User.getNameByID(newCaseInformation.currentOwnerID), null);
        }
        
        //county
        if(newCaseInformation.county == null && oldCaseInformation.county != null) {
            Activity.addActivty("Removed " + oldCaseInformation.county + " from County", null);
        } else if(newCaseInformation.county != null && oldCaseInformation.county == null) {
            Activity.addActivty("Set County to " + newCaseInformation.county, null);
        } else if(newCaseInformation.county != null && oldCaseInformation.county != null) {
            if(!newCaseInformation.county.equals(oldCaseInformation.county)) 
                Activity.addActivty("Changed County from " + oldCaseInformation.county + " to " + newCaseInformation.county, null);
        }
        
        //employerIDNumber
        if(newCaseInformation.employerIDNumber == null && oldCaseInformation.employerIDNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.employerIDNumber + " from Employer ID Number", null);
        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber == null) {
            Activity.addActivty("Set Employer ID Number to " + newCaseInformation.employerIDNumber, null);
        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber != null) {
            if(!newCaseInformation.employerIDNumber.equals(oldCaseInformation.employerIDNumber)) 
                Activity.addActivty("Changed Employer ID Number from " + oldCaseInformation.employerIDNumber + " to " + newCaseInformation.employerIDNumber, null);
        }
        
        //bargUnitNumber
        if(newCaseInformation.bargainingUnitNumber == null && oldCaseInformation.bargainingUnitNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.bargainingUnitNumber + " from Bargaining Unit", null);
        } else if(newCaseInformation.bargainingUnitNumber != null && oldCaseInformation.bargainingUnitNumber == null) {
            Activity.addActivty("Set Bargaining Unit to " + newCaseInformation.bargainingUnitNumber, null);
        } else if(newCaseInformation.bargainingUnitNumber != null && oldCaseInformation.bargainingUnitNumber != null) {
            if(!newCaseInformation.bargainingUnitNumber.equals(oldCaseInformation.bargainingUnitNumber)) 
                Activity.addActivty("Changed Bargaining Unit from " + oldCaseInformation.bargainingUnitNumber + " to " + newCaseInformation.bargainingUnitNumber, null);
        }
        
        //file date
        if(newCaseInformation.fileDate == null && oldCaseInformation.fileDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " from File Date", null);
        } else if(newCaseInformation.fileDate != null && oldCaseInformation.fileDate == null) {
            Activity.addActivty("Set File Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
        } else if(newCaseInformation.fileDate != null && oldCaseInformation.fileDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime()))))
                Activity.addActivty("Changed File Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
        }
        
        //amendedFilingDate
        if(newCaseInformation.amendedFiliingDate == null && oldCaseInformation.amendedFiliingDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.amendedFiliingDate.getTime())) + " from Amended Filing Date", null);
        } else if(newCaseInformation.amendedFiliingDate != null && oldCaseInformation.amendedFiliingDate == null) {
            Activity.addActivty("Set Amended Filing Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.amendedFiliingDate.getTime())), null);
        } else if(newCaseInformation.amendedFiliingDate != null && oldCaseInformation.amendedFiliingDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.amendedFiliingDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.amendedFiliingDate.getTime()))))
                Activity.addActivty("Changed Amended Filing Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.amendedFiliingDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.amendedFiliingDate.getTime())), null);
        }
        
        //Final Board Date
        if(newCaseInformation.finalBoardDate == null && oldCaseInformation.finalBoardDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.finalBoardDate.getTime())) + " from Final Board Date", null);
        } else if(newCaseInformation.finalBoardDate != null && oldCaseInformation.finalBoardDate == null) {
            Activity.addActivty("Set Final Board Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.finalBoardDate.getTime())), null);
        } else if(newCaseInformation.finalBoardDate != null && oldCaseInformation.finalBoardDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.finalBoardDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.finalBoardDate.getTime()))))
                Activity.addActivty("Changed Final Board Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.finalBoardDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.finalBoardDate.getTime())), null);
        }
        
        //Registration Letter Sent
        if(newCaseInformation.registrationLetterSent == null && oldCaseInformation.registrationLetterSent != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.registrationLetterSent.getTime())) + " from Registration Letter Sent", null);
        } else if(newCaseInformation.registrationLetterSent != null && oldCaseInformation.registrationLetterSent == null) {
            Activity.addActivty("Set Registration Letter Sent to " + Global.mmddyyyy.format(new Date(newCaseInformation.registrationLetterSent.getTime())), null);
        } else if(newCaseInformation.registrationLetterSent != null && oldCaseInformation.registrationLetterSent != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.registrationLetterSent.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.registrationLetterSent.getTime()))))
                Activity.addActivty("Changed Registration Letter Sent from " + Global.mmddyyyy.format(new Date(oldCaseInformation.registrationLetterSent.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.registrationLetterSent.getTime())), null);
        }
        
        //Date of Appeal
        if(newCaseInformation.dateOfAppeal == null && oldCaseInformation.dateOfAppeal != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.dateOfAppeal.getTime())) + " from Date of Appeal", null);
        } else if(newCaseInformation.dateOfAppeal != null && oldCaseInformation.dateOfAppeal == null) {
            Activity.addActivty("Set Date of Appeal to " + Global.mmddyyyy.format(new Date(newCaseInformation.dateOfAppeal.getTime())), null);
        } else if(newCaseInformation.dateOfAppeal != null && oldCaseInformation.dateOfAppeal != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.dateOfAppeal.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.dateOfAppeal.getTime()))))
                Activity.addActivty("Changed Date of Appeal from " + Global.mmddyyyy.format(new Date(oldCaseInformation.dateOfAppeal.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.dateOfAppeal.getTime())), null);
        }
        
        //Court Closed Date
        if(newCaseInformation.courtClosedDate == null && oldCaseInformation.courtClosedDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.courtClosedDate.getTime())) + " from Court Closed Date", null);
        } else if(newCaseInformation.courtClosedDate != null && oldCaseInformation.courtClosedDate == null) {
            Activity.addActivty("Set Court Closed Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.courtClosedDate.getTime())), null);
        } else if(newCaseInformation.courtClosedDate != null && oldCaseInformation.courtClosedDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.courtClosedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.courtClosedDate.getTime()))))
                Activity.addActivty("Changed Court Closed Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.courtClosedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.courtClosedDate.getTime())), null);
        }
        
        //Return SOI Due Date
        if(newCaseInformation.returnSOIDueDate == null && oldCaseInformation.returnSOIDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.returnSOIDueDate.getTime())) + " from Return SOI Due Date", null);
        } else if(newCaseInformation.returnSOIDueDate != null && oldCaseInformation.returnSOIDueDate == null) {
            Activity.addActivty("Set Return SOI Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.returnSOIDueDate.getTime())), null);
        } else if(newCaseInformation.returnSOIDueDate != null && oldCaseInformation.returnSOIDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.returnSOIDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.returnSOIDueDate.getTime()))))
                Activity.addActivty("Changed Return SOI Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.returnSOIDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.returnSOIDueDate.getTime())), null);
        }
        
        //Actual SOI return Date
        if(newCaseInformation.actualSOIReturnDate == null && oldCaseInformation.actualSOIReturnDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualSOIReturnDate.getTime())) + " from Actual SOI Return Date", null);
        } else if(newCaseInformation.actualSOIReturnDate != null && oldCaseInformation.actualSOIReturnDate == null) {
            Activity.addActivty("Set Actual SOI Return Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualSOIReturnDate.getTime())), null);
        } else if(newCaseInformation.actualSOIReturnDate != null && oldCaseInformation.actualSOIReturnDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.actualSOIReturnDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.actualSOIReturnDate.getTime()))))
                Activity.addActivty("Changed Actual SOI Return Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualSOIReturnDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualSOIReturnDate.getTime())), null);
        }
        
        //SOI Return Initials
        if(newCaseInformation.comments == null && oldCaseInformation.comments != null) {
            Activity.addActivty("Removed " + oldCaseInformation.comments + " from Comments", null);
        } else if(newCaseInformation.comments != null && oldCaseInformation.comments == null) {
            Activity.addActivty("Set Comments to " + newCaseInformation.comments, null);
        } else if(newCaseInformation.comments != null && oldCaseInformation.comments != null) {
            if(!newCaseInformation.comments.equals(oldCaseInformation.comments)) 
                Activity.addActivty("Changed Comments from " + oldCaseInformation.comments + " to " + newCaseInformation.comments, null);
        }
        
        //REPClsed Case Due Date
        if(newCaseInformation.REPClosedCaseDueDate == null && oldCaseInformation.REPClosedCaseDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.REPClosedCaseDueDate.getTime())) + " from REP Closed Case Due Date", null);
        } else if(newCaseInformation.REPClosedCaseDueDate != null && oldCaseInformation.REPClosedCaseDueDate == null) {
            Activity.addActivty("Set REP Closed Case Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.REPClosedCaseDueDate.getTime())), null);
        } else if(newCaseInformation.REPClosedCaseDueDate != null && oldCaseInformation.REPClosedCaseDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.REPClosedCaseDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.REPClosedCaseDueDate.getTime()))))
                Activity.addActivty("Changed REP Closed Case Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.REPClosedCaseDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.REPClosedCaseDueDate.getTime())), null);
        }
        
        
        
        //Actual REP Closed Date
        if(newCaseInformation.actualREPClosedDate == null && oldCaseInformation.actualREPClosedDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualREPClosedDate.getTime())) + " from Actual REP Closed Date", null);
        } else if(newCaseInformation.actualREPClosedDate != null && oldCaseInformation.actualREPClosedDate == null) {
            Activity.addActivty("Set Actual REP Closed Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualREPClosedDate.getTime())), null);
        } else if(newCaseInformation.actualREPClosedDate != null && oldCaseInformation.actualREPClosedDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.actualREPClosedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.actualREPClosedDate.getTime()))))
                Activity.addActivty("Changed Actual REP Closed Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualREPClosedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualREPClosedDate.getTime())), null);
        }
        
        //REP Closed User
        if(newCaseInformation.REPClosedUser == 0 && oldCaseInformation.REPClosedUser != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.REPClosedUser) + " from REP Closed By", null);
        } else if(newCaseInformation.REPClosedUser != 0 && oldCaseInformation.REPClosedUser == 0) {
            Activity.addActivty("Set REP Closed By to " + User.getNameByID(newCaseInformation.REPClosedUser), null);
        } else if(newCaseInformation.REPClosedUser != 0 && oldCaseInformation.REPClosedUser != 0) {
            if(newCaseInformation.REPClosedUser != oldCaseInformation.REPClosedUser) 
                Activity.addActivty("Changed REP Closed By from " + User.getNameByID(oldCaseInformation.REPClosedUser) + " to " + User.getNameByID(newCaseInformation.REPClosedUser), null);
        }
        
        //Actual Clerks Closed Date
        if(newCaseInformation.actualClerksClosedDate == null && oldCaseInformation.actualClerksClosedDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualClerksClosedDate.getTime())) + " from Actual Clerks Closed Date", null);
        } else if(newCaseInformation.actualClerksClosedDate != null && oldCaseInformation.actualClerksClosedDate == null) {
            Activity.addActivty("Set Actual Clerks Closed Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualClerksClosedDate.getTime())), null);
        } else if(newCaseInformation.actualClerksClosedDate != null && oldCaseInformation.actualClerksClosedDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.actualClerksClosedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.actualClerksClosedDate.getTime()))))
                Activity.addActivty("Changed Actual Clerks Closed Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.actualClerksClosedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.actualClerksClosedDate.getTime())), null);
        }
        
        //Clerks Closed User
        if(newCaseInformation.clerksClosedUser == 0 && oldCaseInformation.clerksClosedUser != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.clerksClosedUser) + " from Clerks Closed By", null);
        } else if(newCaseInformation.clerksClosedUser != 0 && oldCaseInformation.clerksClosedUser == 0) {
            Activity.addActivty("Set Clerks Closed By to " + User.getNameByID(newCaseInformation.clerksClosedUser), null);
        } else if(newCaseInformation.clerksClosedUser != 0 && oldCaseInformation.clerksClosedUser != 0) {
            if(newCaseInformation.clerksClosedUser != oldCaseInformation.currentOwnerID) 
                Activity.addActivty("Changed Clerks Closed By from " + User.getNameByID(oldCaseInformation.clerksClosedUser) + " to " + User.getNameByID(newCaseInformation.clerksClosedUser), null);
        }
        
        //Alpha List Date
        if(newCaseInformation.alphaListDate == null && oldCaseInformation.alphaListDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.alphaListDate.getTime())) + " from Alpha List Receipt Date", null);
        } else if(newCaseInformation.alphaListDate != null && oldCaseInformation.alphaListDate == null) {
            Activity.addActivty("Set Alpha List Receipt Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.alphaListDate.getTime())), null);
        } else if(newCaseInformation.alphaListDate != null && oldCaseInformation.alphaListDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.alphaListDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.alphaListDate.getTime()))))
                Activity.addActivty("Changed Alpha List Receipt Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.alphaListDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.alphaListDate.getTime())), null);
        }
    }
    
    private static void detailedCaseDetailsSaveInformation(REPCase newCaseInformation, REPCase oldCaseInformation) {
        //filedBy
        if(newCaseInformation.fileBy == null && oldCaseInformation.fileBy != null) {
            Activity.addActivty("Removed " + oldCaseInformation.fileBy + " from Filed By", null);
        } else if(newCaseInformation.fileBy != null && oldCaseInformation.fileBy == null) {
            Activity.addActivty("Set Filed By to " + newCaseInformation.fileBy, null);
        } else if(newCaseInformation.fileBy != null && oldCaseInformation.fileBy != null) {
            if(!newCaseInformation.fileBy.equals(oldCaseInformation.fileBy)) 
                Activity.addActivty("Changed Filed By from " + oldCaseInformation.fileBy + " to " + newCaseInformation.fileBy, null);
        }
        
        //bargainingUnitIncluded
        if(newCaseInformation.bargainingUnitIncluded == null && oldCaseInformation.bargainingUnitIncluded != null) {
            Activity.addActivty("Removed Bargaining Unit Included", null);
        } else if(newCaseInformation.bargainingUnitIncluded != null && oldCaseInformation.bargainingUnitIncluded == null) {
            Activity.addActivty("Set Bargaining Unit Included", null);
        } else if(newCaseInformation.bargainingUnitIncluded != null && oldCaseInformation.bargainingUnitIncluded != null) {
            if(!newCaseInformation.bargainingUnitIncluded.equals(oldCaseInformation.bargainingUnitIncluded)) 
                Activity.addActivty("Changed Bargaining Unit Included", null);
        }
        
        //bargainingUnitExcluded
        if(newCaseInformation.bargainingUnitExcluded == null && oldCaseInformation.bargainingUnitExcluded != null) {
            Activity.addActivty("Removed Bargaining Unit Excluded", null);
        } else if(newCaseInformation.bargainingUnitExcluded != null && oldCaseInformation.bargainingUnitExcluded == null) {
            Activity.addActivty("Set Bargaining Unit Excluded", null);
        } else if(newCaseInformation.bargainingUnitExcluded != null && oldCaseInformation.bargainingUnitExcluded != null) {
            if(!newCaseInformation.bargainingUnitExcluded.equals(oldCaseInformation.bargainingUnitExcluded)) 
                Activity.addActivty("Changed Bargaining Unit Excluded", null);
        }
        
//        if(newCaseInformation.bargainingUnitExcluded == null && oldCaseInformation.bargainingUnitExcluded != null) {
//            Activity.addActivty("Removed Bargaining Unit Excluded", null);
//        } else if(newCaseInformation.bargainingUnitExcluded != null && oldCaseInformation.bargainingUnitExcluded == null) {
//            Activity.addActivty("Set Bargaining Unit Excluded", null);
//        } else if(newCaseInformation.bargainingUnitExcluded != null && oldCaseInformation.bargainingUnitExcluded != null) {
//            if(!newCaseInformation.bargainingUnitExcluded.equals(oldCaseInformation.bargainingUnitExcluded)) 
//                Activity.addActivty("Changed Bargaining Unit Excluded", null);
//        }
        
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
//        if(newCaseInformation.comments == null && oldCaseInformation.comments != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.comments + " from Comments", null);
//        } else if(newCaseInformation.comments != null && oldCaseInformation.comments == null) {
//            Activity.addActivty("Set Comments to " + newCaseInformation.comments, null);
//        } else if(newCaseInformation.comments != null && oldCaseInformation.comments != null) {
//            if(!newCaseInformation.comments.equals(oldCaseInformation.comments)) 
//                Activity.addActivty("Changed Comments from " + oldCaseInformation.comments + " to " + newCaseInformation.comments, null);
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
//        
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
    }

    
    
    public static List<String> loadRelatedCases() {
        
        List<String> caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select caseYear, caseType, caseMonth, caseNumber from REPCase  where fileDate between DateAdd(DD,-7,GETDATE()) and GETDATE() Order By caseYear DESC, caseNumber DESC";

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
    
    public static boolean checkIfFristCaseOfMonth(String year, String type, String month) {
        boolean firstCase = false;
        
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " COUNT(*) AS CasesThisMonth"
                    + " from REPCase"
                    + " where caseYear = ? "
                    + " and caseType = ? "
                    + " and caseMonth = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, month);

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
}
