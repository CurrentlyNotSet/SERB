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
    
    public String caseNumber;
    public Timestamp fileDate;
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
    public String relatedCases;
    public String caseType;
    public Timestamp amendedFiliingDate;
    public Timestamp finalBoardDate;
    public Timestamp registrationLetterSent;
    public Timestamp dateOfAppeal;
    public Timestamp courtClosedDate;
    public Timestamp returnSOIDueDate;
    public Timestamp actualSOIReturnDate;
    public String SOIReturnInitials;
    public Timestamp REPClosedCaseDueDate;
    public Timestamp actualREPClosedDate;
    public String clerksClosedDateInitials;
    
    /**
     * Created an empty REPCase Table
     */
    public static void createTable() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "CREATE TABLE REPCase" +
                    "(id int IDENTITY (1,1) NOT NULL, " +
                    " active varchar(1) NOT NULL, " + 
                    " caseNumber varchar(16) NOT NULL, " +
                    " note text NOT NULL, " +
                    " PRIMARY KEY (id))"; 
            
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    
//    private static REPCase getCaseInformation(String caseNumber) {
//        REPCase caseInformation = new REPCase();
//        
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//            
//            String sql = "Select * from REPCase Where caseNumber = ?";
//            
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, caseNumber);
//            
//            ResultSet caseNumberRS = preparedStatement.executeQuery();
//            
//            caseNumberRS.next();
//            
//            caseInformation.caseNumber = caseNumberRS.getString("caseNumber");
//            caseInformation.fileDate = caseNumberRS.getTimestamp("fileDate");
//            
//        } catch (SQLException ex) {
//            Logger.getLogger(REPCase.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return caseInformation;
//    }
    
    /**
     * Load a list of the most recent 250 REP case numbers
     * @return list of rep case numbers
     */
    public static List loadREPCaseNumbers() {
        List caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP 250 CaseNumber from REPCase Order By CaseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                caseNumberList.add(caseNumberRS.getString("caseNumber"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caseNumberList;
    }
    
    public static List loadREPCases(int limit) {
        List caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP " + limit + " * from REPCase Order By CaseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                REPCase repCase = new REPCase();
                repCase.caseNumber = caseNumberRS.getString("caseNumber");
                caseNumberList.add(repCase);
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

            String sql = "Select Note from REPCase where CaseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

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
    public static void updateNote(String newNote, String oldNote) {
        try {
            
            if(!newNote.equals(oldNote)) {
                Statement stmt = Database.connectToDB().createStatement();

                String sql = "Update REPCase set note = ? where CaseNumber = ?";

                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, newNote);
                preparedStatement.setString(2, Global.caseNumber);

                preparedStatement.executeUpdate();

                Audit.addAuditEntry("Updated Note for " + Global.caseNumber);
                Activity.addActivty("Updated Note", "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates a new REPCase entry
     * @param caseNumber the case number to be created 
     */
    public static void createCase(String caseNumber) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert into REPCase (CaseNumber, FileDate) Values (?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber);
            preparedStatement.setTimestamp(2, new Timestamp(System.currentTimeMillis()));

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
//                SlackNotification.sendNotification("Case " + caseNumber + " Created");
//                CaseNumber.updateNextCaseNumber(caseNumber);
                Activity.addNewCaseActivty(caseNumber);
                Global.root.getrEPHeaderPanel1().loadCases();
                Global.root.getrEPHeaderPanel1().getjComboBox2().setSelectedItem(caseNumber); 
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

            String sql = "Select fileDate, status1, courtClosedDate, caseType, bargainingUnitNumber from REPCase where caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

            ResultSet caseHeader = preparedStatement.executeQuery();
            
            if(caseHeader.next()) {
                rep = new REPCase();
                rep.fileDate = caseHeader.getTimestamp("fileDate");
                rep.courtClosedDate = caseHeader.getTimestamp("courtClosedDate");
                rep.status1 = caseHeader.getString("status1");
                rep.caseType = caseHeader.getString("caseType");
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
                    + " caseType,"
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
                    + " relatedCases,"
                    + " fileDate,"
                    + " amendedFilingDate,"
                    + " finalBoardDate,"
                    + " registrationLetterSent,"
                    + " dateOfAppeal,"
                    + " courtClosedDate,"
                    + " returnSOIDueDate,"
                    + " actualSOIReturnDate,"
                    + " SOIReturnInitials,"
                    + " REPClosedCaseDueDate,"
                    + " ActualREPClosedDate,"
                    + " ClerksClosedDateInitials"
                    + " from REPCase where caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

            ResultSet caseInformation = preparedStatement.executeQuery();
            
            if(caseInformation.next()) {
                rep = new REPCase();
                rep.caseType = caseInformation.getString("caseType");
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
                rep.relatedCases = caseInformation.getString("relatedCases");
                rep.fileDate = caseInformation.getTimestamp("fileDate");
                rep.amendedFiliingDate = caseInformation.getTimestamp("amendedFilingDate");
                rep.finalBoardDate = caseInformation.getTimestamp("finalBoardDate");
                rep.registrationLetterSent = caseInformation.getTimestamp("registrationLetterSent");
                rep.dateOfAppeal = caseInformation.getTimestamp("dateOfAppeal");
                rep.courtClosedDate = caseInformation.getTimestamp("courtClosedDate");
                rep.returnSOIDueDate = caseInformation.getTimestamp("returnSOIDueDate");
                rep.actualSOIReturnDate = caseInformation.getTimestamp("actualSOIReturnDate");
                rep.SOIReturnInitials = caseInformation.getString("SOIReturnInitials");
                rep.REPClosedCaseDueDate = caseInformation.getTimestamp("REPClosedCaseDueDate");
                rep.actualREPClosedDate = caseInformation.getTimestamp("actualREPClosedDate");
                rep.clerksClosedDateInitials = caseInformation.getString("ClerksClosedDateInitials");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return rep;
    }
    
    /**
     * Creates a duplicate case, copying all case information, excluding activity,
     * as well as copying parties
     * @param caseNumber
     * @param duplicateCase 
     */
    public static void createDuplicateCase(String caseNumber, String duplicateCase) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select * from REPCase where caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, duplicateCase);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
//                SlackNotification.sendNotification("Case " + caseNumber + " Created");
//                CaseNumber.updateNextCaseNumber(caseNumber);
                Activity.addNewCaseActivty(caseNumber);
                Global.root.getrEPHeaderPanel1().loadCases();
                Global.root.getrEPHeaderPanel1().getjComboBox2().setSelectedItem(caseNumber); 
            } else {
                SlackNotification.sendNotification("Case " + caseNumber + " Does not exist");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void updateCaseInformation(REPCase newCaseInformation, REPCase caseInformation) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

             String sql = "Update REPCase set"
                    + " caseType = ?,"
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
                    + " relatedCases = ?,"
                    + " fileDate = ?,"
                    + " amendedFilingDate = ?,"
                    + " finalBoardDate = ?,"
                    + " registrationLetterSent = ?,"
                    + " dateOfAppeal = ?,"
                    + " courtClosedDate = ?,"
                    + " returnSOIDueDate = ?,"
                    + " actualSOIReturnDate = ?,"
                    + " SOIReturnInitials = ?,"
                    + " REPClosedCaseDueDate = ?,"
                    + " ActualREPClosedDate = ?,"
                    + " ClerksClosedDateInitials = ?"
                    + " where caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseInformation.caseType);
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
            preparedStatement.setString(12, newCaseInformation.relatedCases);
            preparedStatement.setTimestamp(13, newCaseInformation.fileDate);
            preparedStatement.setTimestamp(14, newCaseInformation.amendedFiliingDate);
            preparedStatement.setTimestamp(15, newCaseInformation.finalBoardDate);
            preparedStatement.setTimestamp(16, newCaseInformation.registrationLetterSent);
            preparedStatement.setTimestamp(17, newCaseInformation.dateOfAppeal);
            preparedStatement.setTimestamp(18, newCaseInformation.courtClosedDate);
            preparedStatement.setTimestamp(19, newCaseInformation.returnSOIDueDate);
            preparedStatement.setTimestamp(20, newCaseInformation.actualSOIReturnDate);
            preparedStatement.setString(21, newCaseInformation.SOIReturnInitials);
            preparedStatement.setTimestamp(22, newCaseInformation.REPClosedCaseDueDate);
            preparedStatement.setTimestamp(23, newCaseInformation.actualREPClosedDate);
            preparedStatement.setString(24, newCaseInformation.clerksClosedDateInitials);
            preparedStatement.setString(25, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                
                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
                
                
//                if(!Global.mmddyyyy.format(new Date(caseInformation.fileDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())))) {
//                    Activity.addActivty("Changed File Date from " + Global.mmddyyyy.format(new Date(caseInformation.fileDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
//                }
            } 
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    private static void detailedCaseInformationSaveInformation(REPCase newCaseInformation, REPCase oldCaseInformation) {
        //caseType
        if(newCaseInformation.caseType == null && oldCaseInformation.caseType != null) {
            Activity.addActivty("Removed " + oldCaseInformation.caseType + " from Case Type", null);
        } else if(newCaseInformation.caseType != null && oldCaseInformation.caseType == null) {
            Activity.addActivty("Set Case Type to " + newCaseInformation.caseType, null);
        } else if(newCaseInformation.caseType != null && oldCaseInformation.caseType != null) {
            if(!newCaseInformation.caseType.equals(oldCaseInformation.caseType)) 
                Activity.addActivty("Changed Case Type from " + oldCaseInformation.caseType + " to " + newCaseInformation.caseType, null);
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
        
        //DeptInState
        if(newCaseInformation.deptInState == null && oldCaseInformation.deptInState != null) {
            Activity.addActivty("Removed " + oldCaseInformation.deptInState + " from Department in State", null);
        } else if(newCaseInformation.deptInState != null && oldCaseInformation.deptInState == null) {
            Activity.addActivty("Set Department in State to " + newCaseInformation.deptInState, null);
        } else if(newCaseInformation.deptInState != null && oldCaseInformation.deptInState != null) {
            if(!newCaseInformation.deptInState.equals(oldCaseInformation.deptInState)) 
                Activity.addActivty("Changed Department in State from " + oldCaseInformation.deptInState + " to " + newCaseInformation.deptInState, null);
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
        
        //BoardCertified
        if(newCaseInformation.boardCertified == true && oldCaseInformation.boardCertified == false) {
            Activity.addActivty("Set Board Certified", null);
        } else if(newCaseInformation.boardCertified == false && oldCaseInformation.boardCertified == true) {
            Activity.addActivty("Unset Board Certified", null);
        } 
        
        //Deemed Certified
        if(newCaseInformation.deemedCertified == true && oldCaseInformation.deemedCertified == false) {
            Activity.addActivty("Set Deemed Certified", null);
        } else if(newCaseInformation.deemedCertified == false && oldCaseInformation.deemedCertified == true) {
            Activity.addActivty("Unset Deemed Certified", null);
        }
        
        //Certification Revoked
        if(newCaseInformation.certificationRevoked == true && oldCaseInformation.certificationRevoked == false) {
            Activity.addActivty("Set Certification Revoked", null);
        } else if(newCaseInformation.certificationRevoked == false && oldCaseInformation.certificationRevoked == true) {
            Activity.addActivty("Unset Certification Revoke", null);
        }
        
        //Related Cases
        if(newCaseInformation.relatedCases == null && oldCaseInformation.relatedCases != null) {
            Activity.addActivty("Removed " + oldCaseInformation.relatedCases + " from Related Cases", null);
        } else if(newCaseInformation.relatedCases != null && oldCaseInformation.relatedCases == null) {
            Activity.addActivty("Set Related Cases to " + newCaseInformation.relatedCases, null);
        } else if(newCaseInformation.relatedCases != null && oldCaseInformation.relatedCases != null) {
            if(!newCaseInformation.relatedCases.equals(oldCaseInformation.relatedCases)) 
                Activity.addActivty("Changed Related Cases from " + oldCaseInformation.relatedCases + " to " + newCaseInformation.relatedCases, null);
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
        if(newCaseInformation.SOIReturnInitials == null && oldCaseInformation.SOIReturnInitials != null) {
            Activity.addActivty("Removed " + oldCaseInformation.SOIReturnInitials + " from SOI Return Initials", null);
        } else if(newCaseInformation.SOIReturnInitials != null && oldCaseInformation.SOIReturnInitials == null) {
            Activity.addActivty("Set SOI Return Initials to " + newCaseInformation.SOIReturnInitials, null);
        } else if(newCaseInformation.SOIReturnInitials != null && oldCaseInformation.SOIReturnInitials != null) {
            if(!newCaseInformation.SOIReturnInitials.equals(oldCaseInformation.SOIReturnInitials)) 
                Activity.addActivty("Changed SOI Return Initials from " + oldCaseInformation.SOIReturnInitials + " to " + newCaseInformation.SOIReturnInitials, null);
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
        
        //Clerks Closed Date Initials
        if(newCaseInformation.clerksClosedDateInitials == null && oldCaseInformation.clerksClosedDateInitials != null) {
            Activity.addActivty("Removed " + oldCaseInformation.clerksClosedDateInitials + " from Clerks Closed Date Initials", null);
        } else if(newCaseInformation.clerksClosedDateInitials != null && oldCaseInformation.clerksClosedDateInitials == null) {
            Activity.addActivty("Set Clerks Closed Date Initials to " + newCaseInformation.clerksClosedDateInitials, null);
        } else if(newCaseInformation.clerksClosedDateInitials != null && oldCaseInformation.clerksClosedDateInitials != null) {
            if(!newCaseInformation.clerksClosedDateInitials.equals(oldCaseInformation.clerksClosedDateInitials)) 
                Activity.addActivty("Changed Clerks Closed Date Initials from " + oldCaseInformation.clerksClosedDateInitials + " to " + newCaseInformation.clerksClosedDateInitials, null);
        }
    }
}
