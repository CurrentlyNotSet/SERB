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
import javax.swing.DefaultListModel;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class MEDCase {

    
    
    public int id;
    public boolean active;
    public String caseYear;
    public String caseType; 
    public String caseMonth;
    public String caseNumber;
    public Timestamp fileDate;
    public Timestamp concilList1OrderDate;
    public Timestamp concilList1SelectionDueDate;
    public String concilList1Name1;
    public String concilList1Name2;
    public String concilList1Name3;
    public String concilList1Name4;
    public String concilList1Name5;
    public Timestamp concilAppointmentDate;
    public String concilType;
    public String concilSelection;
    public String concilReplacement;
    public String concilOriginalConciliator;
    public Timestamp concilOriginalConcilDate;
    public Timestamp concilList2OrderDate;
    public Timestamp concilList2SelectionDueDate;
    public String concilList2Name1;
    public String concilList2Name2;
    public String concilList2Name3;
    public String concilList2Name4;
    public String concilList2Name5;
    
    
    /**
     * Load a list of the most recent 250 REP case numbers
     * @return list of rep case numbers
     */
    public static List loadMEDCaseNumbers() {
        
        //TODO: Limit the load to the last 6 months of filed dates
        
        List caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP 250"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber"
                    + " from MEDCase"
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
                    + " from MEDCase"
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

            String sql = "Update MEDCase"
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

            String sql = "Insert into MEDCase (CaseYear, CaseType, CaseMonth, CaseNumber, FileDate) Values (?,?,?,?,?)";

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
                Global.root.getmEDHeaderPanel1().loadCases();
                Global.root.getmEDHeaderPanel1().getjComboBox2().setSelectedItem(fullCaseNumber); 
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
    public static MEDCase loadHeaderInformation() {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " fileDate"
//                    + " status1,"
//                    + " [type],"
//                    + " bargainingUnitNumber"
                    + " from MEDCase where caseYear = ? AND"
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
                med = new MEDCase();
                med.fileDate = caseHeader.getTimestamp("fileDate");
//                rep.courtClosedDate = caseHeader.getTimestamp("courtClosedDate");
//                rep.status1 = caseHeader.getString("status1");
//                rep.type = caseHeader.getString("type");
//                rep.bargainingUnitNumber = caseHeader.getString("bargainingUnitNumber");
            }
                
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return med;
    }
    
    public static MEDCase loadCaseInformation() {
        MEDCase rep = null;
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
                rep = new MEDCase();
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
//                rep.fileDate = caseInformation.getTimestamp("fileDate");
//                rep.amendedFiliingDate = caseInformation.getTimestamp("amendedFilingDate");
//                rep.alphaListDate = caseInformation.getTimestamp("alphaListDate");
//                rep.finalBoardDate = caseInformation.getTimestamp("finalBoardDate");
//                rep.registrationLetterSent = caseInformation.getTimestamp("registrationLetterSent");
//                rep.dateOfAppeal = caseInformation.getTimestamp("dateOfAppeal");
//                rep.courtClosedDate = caseInformation.getTimestamp("courtClosedDate");
//                rep.returnSOIDueDate = caseInformation.getTimestamp("returnSOIDueDate");
//                rep.actualSOIReturnDate = caseInformation.getTimestamp("actualSOIReturnDate");
//                rep.comments = caseInformation.getString("comments");
//                rep.REPClosedCaseDueDate = caseInformation.getTimestamp("REPClosedCaseDueDate");
//                rep.actualREPClosedDate = caseInformation.getTimestamp("actualREPClosedDate");
//                rep.REPClosedUser = caseInformation.getInt("REPClosedUser");
//                rep.actualClerksClosedDate= caseInformation.getTimestamp("actualClerksClosedDate");
//                rep.clerksClosedUser = caseInformation.getInt("clerksClosedUser");
            }
        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
        }
        return rep;
    }
    
    public static MEDCase loadConciliationInformation() {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " concilList1OrderDate,"
                    + " concilList1SelectionDueDate,"
                    + " concilList1Name1,"
                    + " concilList1Name2,"
                    + " concilList1Name3,"
                    + " concilList1Name4,"
                    + " concilList1Name5,"
                    + " concilAppointmentDate,"
                    + " concilType,"
                    + " concilSelection,"
                    + " concilReplacement,"
                    + " concilOriginalConciliator,"
                    + " concilOriginalConcilDate,"
                    + " concilList2OrderDate,"
                    + " concilList2SelectionDueDate,"
                    + " concilList2Name1,"
                    + " concilList2Name2,"
                    + " concilList2Name3,"
                    + " concilList2Name4,"
                    + " concilList2Name5"
                    + " from MEDCase where caseYear = ? "
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
                med = new MEDCase();
//                
                med.concilList1OrderDate = caseInformation.getTimestamp("concilList1OrderDate");
                med.concilList1SelectionDueDate = caseInformation.getTimestamp("concilList1SelectionDueDate");
                med.concilList1Name1 = caseInformation.getString("concilList1Name1");
                med.concilList1Name2 = caseInformation.getString("concilList1Name2");
                med.concilList1Name3 = caseInformation.getString("concilList1Name3");
                med.concilList1Name4 = caseInformation.getString("concilList1Name4");
                med.concilList1Name5 = caseInformation.getString("concilList1Name5");
                med.concilAppointmentDate = caseInformation.getTimestamp("concilAppointmentDate");
                med.concilType = caseInformation.getString("concilType");
                med.concilSelection = caseInformation.getString("concilSelection");
                med.concilReplacement = caseInformation.getString("concilReplacement");
                med.concilOriginalConciliator = caseInformation.getString("concilOriginalConciliator");
                med.concilOriginalConcilDate = caseInformation.getTimestamp("concilOriginalConcilDate");
                med.concilList2OrderDate = caseInformation.getTimestamp("concilList2OrderDate");
                med.concilList2SelectionDueDate = caseInformation.getTimestamp("concilList2SelectionDueDate");
                med.concilList2Name1 = caseInformation.getString("concilList2Name1");
                med.concilList2Name2 = caseInformation.getString("concilList2Name2");
                med.concilList2Name3 = caseInformation.getString("concilList2Name3");
                med.concilList2Name4 = caseInformation.getString("concilList2Name4");
                med.concilList2Name5 = caseInformation.getString("concilList2Name5");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return med;
    }
    
    public static MEDCase loadCaseDetails() {
        MEDCase rep = null;
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
                rep = new MEDCase();
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
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return rep;
    }
    
    public static MEDCase loadBoardStatus() {
        MEDCase rep = null;
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
                rep = new MEDCase();
//                rep.boardActionType = caseInformation.getString("boardActionType");
//                rep.boardActionDate = caseInformation.getTimestamp("boardActionDate");
//                rep.hearingPersonID = caseInformation.getInt("hearingPersonID");
//                rep.boardStatusNote = caseInformation.getString("boardStatusNote");
//                rep.boardStatusBlurb = caseInformation.getString("boardStatusBlurb");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return rep;
    }
    
    public static void saveConciliationList1(DefaultListModel concilList1Model) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();
//
            String sql = "Update MEDCase set"
                    + " concilList1Name1 = ?,"
                    + " concilList1Name2 = ?,"
                    + " concilList1Name3 = ?,"
                    + " concilList1Name4 = ?,"
                    + " concilList1Name5 = ?"
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, concilList1Model.get(0).toString());
            preparedStatement.setString(2, concilList1Model.get(1).toString());
            preparedStatement.setString(3, concilList1Model.get(2).toString());
            preparedStatement.setString(4, concilList1Model.get(3).toString());
            preparedStatement.setString(5, concilList1Model.get(4).toString());
            preparedStatement.setString(6, Global.caseYear);
            preparedStatement.setString(7, Global.caseType);
            preparedStatement.setString(8, Global.caseMonth);
            preparedStatement.setString(9, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                String names = concilList1Model.get(0).toString().substring(0, 1) + "." + concilList1Model.get(0).toString().substring(concilList1Model.get(0).toString().lastIndexOf(" "));
                names += ", " + concilList1Model.get(1).toString().substring(0, 1) + "." + concilList1Model.get(1).toString().substring(concilList1Model.get(1).toString().lastIndexOf(" "));
                names += ", " + concilList1Model.get(2).toString().substring(0, 1) + "." + concilList1Model.get(2).toString().substring(concilList1Model.get(2).toString().lastIndexOf(" "));
                names += ", " + concilList1Model.get(3).toString().substring(0, 1) + "." + concilList1Model.get(3).toString().substring(concilList1Model.get(3).toString().lastIndexOf(" "));
                names += ", " + concilList1Model.get(4).toString().substring(0, 1) + "." + concilList1Model.get(4).toString().substring(concilList1Model.get(4).toString().lastIndexOf(" "));

                Activity.addActivty("Generated Conciliator List (" + names + ")", null);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void saveConciliationList2(DefaultListModel concilList2Model) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set"
                    + " concilList2Name1 = ?,"
                    + " concilList2Name2 = ?,"
                    + " concilList2Name3 = ?,"
                    + " concilList2Name4 = ?,"
                    + " concilList2Name5 = ?"
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, concilList2Model.get(0).toString());
            preparedStatement.setString(2, concilList2Model.get(1).toString());
            preparedStatement.setString(3, concilList2Model.get(2).toString());
            preparedStatement.setString(4, concilList2Model.get(3).toString());
            preparedStatement.setString(5, concilList2Model.get(4).toString());
            preparedStatement.setString(6, Global.caseYear);
            preparedStatement.setString(7, Global.caseType);
            preparedStatement.setString(8, Global.caseMonth);
            preparedStatement.setString(9, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                String names = concilList2Model.get(0).toString().substring(0, 1) + "." + concilList2Model.get(0).toString().substring(concilList2Model.get(0).toString().lastIndexOf(" "));
                names += ", " + concilList2Model.get(1).toString().substring(0, 1) + "." + concilList2Model.get(1).toString().substring(concilList2Model.get(1).toString().lastIndexOf(" "));
                names += ", " + concilList2Model.get(2).toString().substring(0, 1) + "." + concilList2Model.get(2).toString().substring(concilList2Model.get(2).toString().lastIndexOf(" "));
                names += ", " + concilList2Model.get(3).toString().substring(0, 1) + "." + concilList2Model.get(3).toString().substring(concilList2Model.get(3).toString().lastIndexOf(" "));
                names += ", " + concilList2Model.get(4).toString().substring(0, 1) + "." + concilList2Model.get(4).toString().substring(concilList2Model.get(4).toString().lastIndexOf(" "));

                Activity.addActivty("Generated Conciliator List (" + names + ")", null);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void replaceList2Concil(int location, String newName, String oldName) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set"
                    + " concilList2Name" + String.valueOf(location + 1) + " = ?"
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                Activity.addActivty("Replaced " + oldName + " with " + newName, null);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void replaceList1Concil(int location, String newName, String oldName) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set"
                    + " concilList1Name" + String.valueOf(location + 1) + " = ?"
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newName);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                Activity.addActivty("Replaced " + oldName + " with " + newName, null);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void updateBoardStatus(MEDCase newCaseInformation, MEDCase caseInformation) {
//        MEDCase rep = null;
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
    }
    
    public static void updateConciliation(MEDCase newCaseInformation, MEDCase caseInformation) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();
//
            String sql = "Update MEDCase set"
                    + " concilList1OrderDate = ?,"
                    + " concilList1SelectionDueDate = ?,"
                    + " concilAppointmentDate = ?,"
                    + " concilType = ?,"
                    + " concilSelection = ?,"
                    + " concilReplacement = ?,"
                    + " concilOriginalConciliator = ?,"
                    + " concilOriginalConcilDate = ?,"
                    + " concilList2OrderDate = ?,"
                    + " concilList2SelectionDueDate = ?"
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";
//
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, newCaseInformation.concilList1OrderDate);
            preparedStatement.setTimestamp(2, newCaseInformation.concilList1SelectionDueDate);
            preparedStatement.setTimestamp(3, newCaseInformation.concilAppointmentDate);
            preparedStatement.setString(4, newCaseInformation.concilType);
            preparedStatement.setString(5, newCaseInformation.concilSelection);
            preparedStatement.setString(6, newCaseInformation.concilReplacement);
            preparedStatement.setString(7, newCaseInformation.concilOriginalConciliator);
            preparedStatement.setTimestamp(8, newCaseInformation.concilOriginalConcilDate);
            preparedStatement.setTimestamp(9, newCaseInformation.concilList2OrderDate);
            preparedStatement.setTimestamp(10, newCaseInformation.concilList2SelectionDueDate);
            preparedStatement.setString(11, Global.caseYear);
            preparedStatement.setString(12, Global.caseType);
            preparedStatement.setString(13, Global.caseMonth);
            preparedStatement.setString(14, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedConciliationDetailSaveInformation(newCaseInformation, caseInformation);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void updateCaseDetails(MEDCase newCaseInformation, MEDCase caseInformation) {
        MEDCase rep = null;
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
            preparedStatement.setString(18, Global.caseYear);
            preparedStatement.setString(19, Global.caseType);
            preparedStatement.setString(20, Global.caseMonth);
            preparedStatement.setString(21, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
//                detailedCaseDetailsSaveInformation(newCaseInformation, caseInformation);
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
    
    public static void updateCaseInformation(MEDCase newCaseInformation, MEDCase caseInformation) {
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
//            preparedStatement.setString(20, newCaseInformation.comments);
//            preparedStatement.setTimestamp(21, newCaseInformation.REPClosedCaseDueDate);
//            preparedStatement.setTimestamp(22, newCaseInformation.actualREPClosedDate);
//            preparedStatement.setInt(23, newCaseInformation.REPClosedUser);
//            preparedStatement.setTimestamp(24, newCaseInformation.actualClerksClosedDate);
//            preparedStatement.setInt(25, newCaseInformation.clerksClosedUser);
//            preparedStatement.setTimestamp(26, newCaseInformation.alphaListDate);
            preparedStatement.setString(27, Global.caseYear);
            preparedStatement.setString(28, Global.caseType);
            preparedStatement.setString(29, Global.caseMonth);
            preparedStatement.setString(30, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
//                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
//                REPCaseSearchData.updateCaseEntryFromCaseInformation(
//                        newCaseInformation.bargainingUnitNumber,
//                        newCaseInformation.county,
//                        getCertificationText(newCaseInformation));
            } 
        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
//    private static String getCertificationText(MEDCase newCase) {
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
    private static void detailedConciliationDetailSaveInformation(MEDCase newCaseInformation, MEDCase oldCaseInformation) {
       
        //list1orderdate
        if(newCaseInformation.concilList1OrderDate == null && oldCaseInformation.concilList1OrderDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1OrderDate.getTime())) + " from Conciliation List 1 Order Date", null);
        } else if(newCaseInformation.concilList1OrderDate != null && oldCaseInformation.concilList1OrderDate == null) {
            Activity.addActivty("Set Conciliation List 1 Order Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList1OrderDate.getTime())), null);
        } else if(newCaseInformation.concilList1OrderDate != null && oldCaseInformation.concilList1OrderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1OrderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilList1OrderDate.getTime()))))
                Activity.addActivty("Changed Conciliation List 1 Order Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1OrderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList1OrderDate.getTime())), null);
        }
        
        //list1SelectionDueDate
        if(newCaseInformation.concilList1SelectionDueDate == null && oldCaseInformation.concilList1SelectionDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1SelectionDueDate.getTime())) + " from Conciliation List 1 Selection Due Date", null);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.concilList1SelectionDueDate == null) {
            Activity.addActivty("Set Conciliation List 1 Selection Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList1SelectionDueDate.getTime())), null);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.concilList1SelectionDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1SelectionDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilList1SelectionDueDate.getTime()))))
                Activity.addActivty("Changed Conciliation List 1 Selection Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1SelectionDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList1SelectionDueDate.getTime())), null);
        }
        
        //appointmentDate
        if(newCaseInformation.concilAppointmentDate == null && oldCaseInformation.concilAppointmentDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilAppointmentDate.getTime())) + " from Conciliation Appointment Date", null);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.concilAppointmentDate == null) {
            Activity.addActivty("Set Conciliation Appointment Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilAppointmentDate.getTime())), null);
        } else if(newCaseInformation.concilAppointmentDate != null && oldCaseInformation.concilAppointmentDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilAppointmentDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilAppointmentDate.getTime()))))
                Activity.addActivty("Changed Conciliation Appointment Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilAppointmentDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilAppointmentDate.getTime())), null);
        }
        
        //conciliationType
        if(newCaseInformation.concilType == null && oldCaseInformation.concilType != null) {
            Activity.addActivty("Removed " + oldCaseInformation.concilType + " from Conciliation Type", null);
        } else if(newCaseInformation.concilType != null && oldCaseInformation.concilType == null) {
            Activity.addActivty("Set Conciliation Type to " + newCaseInformation.concilType, null);
        } else if(newCaseInformation.concilType != null && oldCaseInformation.concilType != null) {
            if(!newCaseInformation.concilType.equals(oldCaseInformation.concilType)) 
                Activity.addActivty("Changed Conciliation Type from " + oldCaseInformation.concilType + " to " + newCaseInformation.concilType, null);
        }
        
        //conciliationSelection
        if(newCaseInformation.concilSelection == null && oldCaseInformation.concilSelection != null) {
            Activity.addActivty("Removed " + oldCaseInformation.concilSelection + " from Conciliator Selected", null);
        } else if(newCaseInformation.concilSelection != null && oldCaseInformation.concilSelection == null) {
            Activity.addActivty("Set Conciliator Selected to " + newCaseInformation.concilSelection, null);
        } else if(newCaseInformation.concilSelection != null && oldCaseInformation.concilSelection != null) {
            if(!newCaseInformation.concilSelection.equals(oldCaseInformation.concilSelection)) 
                Activity.addActivty("Changed Conciliator Selected from " + oldCaseInformation.concilSelection + " to " + newCaseInformation.concilSelection, null);
        }
        
        //replacementConciliation
        if(newCaseInformation.concilReplacement == null && oldCaseInformation.concilReplacement != null) {
            Activity.addActivty("Removed " + oldCaseInformation.concilReplacement + " from Replacement Conciliation", null);
        } else if(newCaseInformation.concilReplacement != null && oldCaseInformation.concilReplacement == null) {
            Activity.addActivty("Set Replacement Conciliation to " + newCaseInformation.concilReplacement, null);
        } else if(newCaseInformation.concilReplacement != null && oldCaseInformation.concilReplacement != null) {
            if(!newCaseInformation.concilReplacement.equals(oldCaseInformation.concilReplacement)) 
                Activity.addActivty("Changed Replacement Conciliation from " + oldCaseInformation.concilReplacement + " to " + newCaseInformation.concilReplacement, null);
        }
        
        //orgConciliationDate
        if(newCaseInformation.concilOriginalConcilDate == null && oldCaseInformation.concilOriginalConcilDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilOriginalConcilDate.getTime())) + " from Original Conciliation Date", null);
        } else if(newCaseInformation.concilOriginalConcilDate != null && oldCaseInformation.concilOriginalConcilDate == null) {
            Activity.addActivty("Set Original Conciliation Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilOriginalConcilDate.getTime())), null);
        } else if(newCaseInformation.concilOriginalConcilDate != null && oldCaseInformation.concilOriginalConcilDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilOriginalConcilDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilOriginalConcilDate.getTime()))))
                Activity.addActivty("Changed Original Conciliation Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilOriginalConcilDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilOriginalConcilDate.getTime())), null);
        }
        
        //list2orderdate
        if(newCaseInformation.concilList2OrderDate == null && oldCaseInformation.concilList2OrderDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2OrderDate.getTime())) + " from Conciliation List 2 Order Date", null);
        } else if(newCaseInformation.concilList2OrderDate != null && oldCaseInformation.concilList2OrderDate == null) {
            Activity.addActivty("Set Conciliation List 2 Order Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList2OrderDate.getTime())), null);
        } else if(newCaseInformation.concilList2OrderDate != null && oldCaseInformation.concilList2OrderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2OrderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilList2OrderDate.getTime()))))
                Activity.addActivty("Changed Conciliation List 2 Order Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2OrderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList2OrderDate.getTime())), null);
        }
        
        //list2SelectionDueDate
        if(newCaseInformation.concilList2SelectionDueDate == null && oldCaseInformation.concilList2SelectionDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2SelectionDueDate.getTime())) + " from Conciliation List 2 Selection Due Date", null);
        } else if(newCaseInformation.concilList2SelectionDueDate != null && oldCaseInformation.concilList2SelectionDueDate == null) {
            Activity.addActivty("Set Conciliation List 2 Selection Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList2SelectionDueDate.getTime())), null);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.concilList2SelectionDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2SelectionDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilList2SelectionDueDate.getTime()))))
                Activity.addActivty("Changed Conciliation List 2 Selection Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2SelectionDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList2SelectionDueDate.getTime())), null);
        }
    }
    
    public static List<String> loadRelatedCases() {
        
        List<String> caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select caseYear, caseType, caseMonth, caseNumber from MEDCase  where fileDate between DateAdd(DD,-7,GETDATE()) and GETDATE() Order By caseYear DESC, caseNumber DESC";

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
                    + " from MEDCase"
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
