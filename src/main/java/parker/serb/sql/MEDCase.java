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
import parker.serb.util.NumberFormatService;
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
   
    
    //concil
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
    
    //FactFinder
    public Timestamp FFList1OrderDate;
    public Timestamp FFList1SelectionDueDate;
    public String FFList1Name1;
    public String FFList1Name2;
    public String FFList1Name3;
    public String FFList1Name4;
    public String FFList1Name5;
    public Timestamp FFAppointmentDate;
    public String FFType;
    public String FFSelection;
    public String FFReplacement;
    public String FFOriginalFactFinder;
    public Timestamp FFOriginalFactFinderDate;
    public boolean asAgreedToByParties;
    public Timestamp FFList2OrderDate;
    public Timestamp FFList2SelectionDueDate;
    public String FFList2Name1;
    public String FFList2Name2;
    public String FFList2Name3;
    public String FFList2Name4;
    public String FFList2Name5;
    
    //lowerhalf of FF
    public String FFEmployerType;
    public String FFEmployeeType;
    public Timestamp FFReportIssueDate;
    public boolean FFMediatedSettlement;
    public String FFAcceptedBy;
    public String FFDeemedAcceptedBy;
    public String FFRejectedBy;
    public String FFOverallResult;
    public String FFNote;
    
    //caseStatus
    public Timestamp fileDate;
    public String employerIDNumber;
    public String bargainingUnitNumber;
    public String approxNumberOfEmployees;
    public String duplicateCaseNumber;
    public String relatedCaseNumber;
    public String negotiationType;
    public Timestamp expirationDate;
    public String NTNFiledBy;
    public String negotiationPeriod;
    public boolean multiunitBargainingRequested;
    public Timestamp mediatorAppointedDate;
    public boolean mediatorReplacement;
    public String stateMediatorAppointedID;
    public String FMCSMediatorAppointedID;
    public Timestamp settlementDate;
    public String caseStatus;
    public boolean sendToBoardToClose;
    public Timestamp boardFinalDate;
    public Timestamp retentionTicklerDate;
    public boolean lateFiling;
    public boolean impasse;
    public boolean settled;
    public boolean TA;
    public boolean MAD;
    public boolean withdrawl;
    public boolean motion;
    public boolean dismissed;
    
    
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
                    + " fileDate,"
                    + " stateMediatorAppointedID,"
                    + " FMCSMediatorAppointedID,"
                    + " caseStatus"
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
                med.stateMediatorAppointedID = caseHeader.getString("stateMediatorAppointedID");
                med.FMCSMediatorAppointedID = caseHeader.getString("FMCSMediatorAppointedID");
                med.caseStatus = caseHeader.getString("caseStatus");
            }
                
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return med;
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
    
    public static MEDCase loadFFInformation() {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " FFList1OrderDate,"
                    + " FFList1SelectionDueDate,"
                    + " FFList1Name1,"
                    + " FFList1Name2,"
                    + " FFList1Name3,"
                    + " FFList1Name4,"
                    + " FFList1Name5,"
                    + " FFAppointmentDate,"
                    + " FFType,"
                    + " FFSelection,"
                    + " FFReplacement,"
                    + " FFOriginalFactFinder,"
                    + " FFOriginalFactFinderDate,"
                    + " asAgreedToByParties,"
                    + " FFList2OrderDate,"
                    + " FFList2SelectionDueDate,"
                    + " FFList2Name1,"
                    + " FFList2Name2,"
                    + " FFList2Name3,"
                    + " FFList2Name4,"
                    + " FFList2Name5,"
                    + " FFEmployerType,"
                    + " FFEmployeeType,"
                    + " FFReportIssueDate,"
                    + " FFMediatedSettlement,"
                    + " FFAcceptedBy,"
                    + " FFDeemedAcceptedBy,"
                    + " FFRejectedBy,"
                    + " FFOverallResult,"
                    + " FFNote"
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
                med.FFList1OrderDate = caseInformation.getTimestamp("FFList1OrderDate");
                med.FFList1SelectionDueDate = caseInformation.getTimestamp("FFList1SelectionDueDate");
                med.FFList1Name1 = caseInformation.getString("FFList1Name1");
                med.FFList1Name2 = caseInformation.getString("FFList1Name2");
                med.FFList1Name3 = caseInformation.getString("FFList1Name3");
                med.FFList1Name4 = caseInformation.getString("FFList1Name4");
                med.FFList1Name5 = caseInformation.getString("FFList1Name5");
                med.FFAppointmentDate = caseInformation.getTimestamp("FFAppointmentDate");
                med.FFType = caseInformation.getString("FFType");
                med.FFSelection = caseInformation.getString("FFSelection");
                med.FFReplacement = caseInformation.getString("FFReplacement");
                med.FFOriginalFactFinder = caseInformation.getString("FFOriginalFactFinder");
                med.FFOriginalFactFinderDate = caseInformation.getTimestamp("FFOriginalFactFinderDate");
                med.asAgreedToByParties = caseInformation.getBoolean("asAgreedToByParties");
                med.FFList2OrderDate = caseInformation.getTimestamp("FFList2OrderDate");
                med.FFList2SelectionDueDate = caseInformation.getTimestamp("FFList2SelectionDueDate");
                med.FFList2Name1 = caseInformation.getString("FFList2Name1");
                med.FFList2Name2 = caseInformation.getString("FFList2Name2");
                med.FFList2Name3 = caseInformation.getString("FFList2Name3");
                med.FFList2Name4 = caseInformation.getString("FFList2Name4");
                med.FFList2Name5 = caseInformation.getString("FFList2Name5");
                med.FFEmployerType = caseInformation.getString("FFEmployerType");
                med.FFEmployeeType = caseInformation.getString("FFEmployeeType");
                med.FFReportIssueDate = caseInformation.getTimestamp("FFReportIssueDate");
                med.FFMediatedSettlement = caseInformation.getBoolean("FFMediatedSettlement");
                med.FFAcceptedBy = caseInformation.getString("FFAcceptedBy");
                med.FFDeemedAcceptedBy = caseInformation.getString("FFDeemedAcceptedBy");
                med.FFRejectedBy = caseInformation.getString("FFRejectedBy");
                med.FFOverallResult = caseInformation.getString("FFOverallResult");
                med.FFNote = caseInformation.getString("FFNote");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return med;
    }
    
    public static MEDCase loadStatusInformation() {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();            
            
            String sql = "Select"
                    + " fileDate,"
                    + " employerIDNumber,"
                    + " bargainingUnitNumber,"
                    + " approxNumberOfEmployees,"
                    + " duplicateCaseNumber,"
                    + " relatedCaseNumber,"
                    + " negotiationType,"
                    + " expirationDate,"
                    + " NTNFiledBy,"
                    + " negotiationPeriod,"
                    + " multiunitBargainingRequested,"
                    + " mediatorAppointedDate,"
                    + " mediatorReplacement,"
                    + " stateMediatorAppointedID,"
                    + " FMCSMediatorAppointedID,"
                    + " settlementDate,"
                    + " caseStatus,"
                    + " sendToBoardToClose,"
                    + " boardFinalDate,"
                    + " retentionTicklerDate,"
                    + " lateFiling,"
                    + " impasse,"
                    + " settled,"
                    + " TA,"
                    + " MAD,"
                    + " withdrawl,"
                    + " motion,"
                    + " dismissed"
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
                med.fileDate = caseInformation.getTimestamp("fileDate");
                med.employerIDNumber = caseInformation.getString("employerIDNumber");
                med.bargainingUnitNumber = caseInformation.getString("bargainingUnitNumber");
                med.approxNumberOfEmployees = caseInformation.getString("approxNumberOfEmployees");
                med.duplicateCaseNumber = caseInformation.getString("duplicateCaseNumber");
                med.relatedCaseNumber = caseInformation.getString("relatedCaseNumber");
                med.negotiationType = caseInformation.getString("negotiationType");
                med.expirationDate = caseInformation.getTimestamp("expirationDate");
                med.NTNFiledBy = caseInformation.getString("NTNFiledBy");
                med.negotiationPeriod = caseInformation.getString("negotiationPeriod");
                med.multiunitBargainingRequested = caseInformation.getBoolean("multiunitBargainingRequested");
                med.mediatorAppointedDate = caseInformation.getTimestamp("mediatorAppointedDate");
                med.mediatorReplacement = caseInformation.getBoolean("mediatorReplacement");
                med.stateMediatorAppointedID = caseInformation.getString("stateMediatorAppointedID");
                med.FMCSMediatorAppointedID = caseInformation.getString("FMCSMediatorAppointedID");
                med.settlementDate = caseInformation.getTimestamp("settlementDate");
                med.caseStatus = caseInformation.getString("caseStatus");
                med.sendToBoardToClose = caseInformation.getBoolean("sendToBoardToClose");
                med.boardFinalDate = caseInformation.getTimestamp("boardFinalDate");
                med.retentionTicklerDate = caseInformation.getTimestamp("retentionTicklerDate");
                med.lateFiling = caseInformation.getBoolean("lateFiling");
                med.impasse = caseInformation.getBoolean("impasse");
                med.settled = caseInformation.getBoolean("settled");
                med.TA = caseInformation.getBoolean("TA");
                med.MAD = caseInformation.getBoolean("MAD");
                med.withdrawl = caseInformation.getBoolean("withdrawl");
                med.motion = caseInformation.getBoolean("motion");
                med.dismissed = caseInformation.getBoolean("dismissed");
                
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return med;
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
    
    public static void saveFFList1(DefaultListModel concilList1Model) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();
//
            String sql = "Update MEDCase set"
                    + " FFList1Name1 = ?,"
                    + " FFList1Name2 = ?,"
                    + " FFList1Name3 = ?,"
                    + " FFList1Name4 = ?,"
                    + " FFList1Name5 = ?"
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

                Activity.addActivty("Generated Fact Finder List (" + names + ")", null);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void saveFFList2(DefaultListModel concilList2Model) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set"
                    + " FFList2Name1 = ?,"
                    + " FFList2Name2 = ?,"
                    + " FFList2Name3 = ?,"
                    + " FFList2Name4 = ?,"
                    + " FFList2Name5 = ?"
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

                Activity.addActivty("Generated Fact Finder List (" + names + ")", null);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void replaceList2FF(int location, String newName, String oldName) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set"
                    + " FFList2Name" + String.valueOf(location + 1) + " = ?"
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
    
    public static void replaceList1FF(int location, String newName, String oldName) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set"
                    + " FFList1Name" + String.valueOf(location + 1) + " = ?"
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
    
    public static void updateStatusInformation(MEDCase newCaseInformation, MEDCase caseInformation) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();
//
            String sql = "Update MEDCase set"
                    + " fileDate = ?,"
                    + " employerIDNumber = ?,"
                    + " bargainingUnitNumber = ?,"
                    + " approxNumberOfEmployees = ?,"
                    + " duplicateCaseNumber = ?,"
                    + " relatedCaseNumber = ?,"
                    + " negotiationType = ?,"
                    + " expirationDate = ?,"
                    + " NTNFiledBy = ?,"
                    + " negotiationPeriod = ?,"
                    + " multiunitBargainingRequested = ?,"
                    + " mediatorAppointedDate = ?,"
                    + " mediatorReplacement = ?,"
                    + " stateMediatorAppointedID = ?,"
                    + " FMCSMediatorAppointedID = ?,"
                    + " settlementDate = ?,"
                    + " caseStatus = ?,"
                    + " sendToBoardToClose = ?,"
                    + " boardFinalDate = ?,"
                    + " retentionTicklerDate = ?,"
                    + " lateFiling = ?,"
                    + " impasse = ?,"
                    + " settled = ?,"
                    + " TA = ?,"
                    + " MAD = ?,"
                    + " withdrawl = ?,"
                    + " motion = ?,"
                    + " dismissed = ?"
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";
//
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, newCaseInformation.fileDate);
            preparedStatement.setString(2, newCaseInformation.employerIDNumber);
            preparedStatement.setString(3, newCaseInformation.bargainingUnitNumber);
            preparedStatement.setString(4, newCaseInformation.approxNumberOfEmployees);
            preparedStatement.setString(5, newCaseInformation.duplicateCaseNumber);
            preparedStatement.setString(6, newCaseInformation.relatedCaseNumber);
            preparedStatement.setString(7, newCaseInformation.negotiationType);
            preparedStatement.setTimestamp(8, newCaseInformation.expirationDate);
            preparedStatement.setString(9, newCaseInformation.NTNFiledBy);
            preparedStatement.setString(10, newCaseInformation.negotiationPeriod);
            preparedStatement.setBoolean(11, newCaseInformation.multiunitBargainingRequested);
            preparedStatement.setTimestamp(12, newCaseInformation.mediatorAppointedDate);
            preparedStatement.setBoolean(13, newCaseInformation.mediatorReplacement);
            preparedStatement.setString(14, newCaseInformation.stateMediatorAppointedID);
            preparedStatement.setString(15, newCaseInformation.FMCSMediatorAppointedID);
            preparedStatement.setTimestamp(16, newCaseInformation.settlementDate);
            preparedStatement.setString(17, newCaseInformation.caseStatus);
            preparedStatement.setBoolean(18, newCaseInformation.sendToBoardToClose);
            preparedStatement.setTimestamp(19, newCaseInformation.boardFinalDate);
            preparedStatement.setTimestamp(20, newCaseInformation.retentionTicklerDate);
            preparedStatement.setBoolean(21, newCaseInformation.lateFiling);
            preparedStatement.setBoolean(22, newCaseInformation.impasse);
            preparedStatement.setBoolean(23, newCaseInformation.settled);
            preparedStatement.setBoolean(24, newCaseInformation.TA);
            preparedStatement.setBoolean(25, newCaseInformation.MAD);
            preparedStatement.setBoolean(26, newCaseInformation.withdrawl);
            preparedStatement.setBoolean(27, newCaseInformation.motion);
            preparedStatement.setBoolean(28, newCaseInformation.dismissed);
            preparedStatement.setString(29, Global.caseYear);
            preparedStatement.setString(30, Global.caseType);
            preparedStatement.setString(31, Global.caseMonth);
            preparedStatement.setString(32, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
//                detailedStatusSaveInformation(newCaseInformation, caseInformation);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void updateFF(MEDCase newCaseInformation, MEDCase caseInformation) {
        MEDCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();
//
            String sql = "Update MEDCase set"
                    + " FFList1OrderDate = ?,"
                    + " FFList1SelectionDueDate = ?,"
                    + " FFAppointmentDate = ?,"
                    + " FFType = ?,"
                    + " FFSelection = ?,"
                    + " FFReplacement = ?,"
                    + " FFOriginalFactFinder = ?,"
                    + " FFOriginalFactFinderDate = ?,"
                    + " asAgreedToByParties = ?,"
                    + " FFList2OrderDate = ?,"
                    + " FFList2SelectionDueDate = ?,"
                    + " FFEmployerType = ?,"
                    + " FFEmployeeType = ?,"
                    + " FFReportIssueDate = ?,"
                    + " FFMediatedSettlement = ?,"
                    + " FFAcceptedBy = ?,"
                    + " FFDeemedAcceptedBy = ?,"
                    + " FFRejectedBy = ?,"
                    + " FFOverallResult = ?,"
                    + " FFNote = ?"
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";
//
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, newCaseInformation.FFList1OrderDate);
            preparedStatement.setTimestamp(2, newCaseInformation.FFList1SelectionDueDate);
            preparedStatement.setTimestamp(3, newCaseInformation.FFAppointmentDate);
            preparedStatement.setString(4, newCaseInformation.FFType);
            preparedStatement.setString(5, newCaseInformation.FFSelection);
            preparedStatement.setString(6, newCaseInformation.FFReplacement);
            preparedStatement.setString(7, newCaseInformation.FFOriginalFactFinder);
            preparedStatement.setTimestamp(8, newCaseInformation.FFOriginalFactFinderDate);
            preparedStatement.setBoolean(9, newCaseInformation.asAgreedToByParties);
            preparedStatement.setTimestamp(10, newCaseInformation.FFList2OrderDate);
            preparedStatement.setTimestamp(11, newCaseInformation.FFList2SelectionDueDate);
            preparedStatement.setString(12, newCaseInformation.FFEmployerType);
            preparedStatement.setString(13, newCaseInformation.FFEmployeeType);
            preparedStatement.setTimestamp(14, newCaseInformation.FFReportIssueDate);
            preparedStatement.setBoolean(15, newCaseInformation.FFMediatedSettlement);
            preparedStatement.setString(16, newCaseInformation.FFAcceptedBy);
            preparedStatement.setString(17, newCaseInformation.FFDeemedAcceptedBy);
            preparedStatement.setString(18, newCaseInformation.FFRejectedBy);
            preparedStatement.setString(19, newCaseInformation.FFOverallResult);
            preparedStatement.setString(20, newCaseInformation.FFNote);
            preparedStatement.setString(21, Global.caseYear);
            preparedStatement.setString(22, Global.caseType);
            preparedStatement.setString(23, Global.caseMonth);
            preparedStatement.setString(24, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedFFDetailSaveInformation(newCaseInformation, caseInformation);
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
    
    private static void detailedFFDetailSaveInformation(MEDCase newCaseInformation, MEDCase oldCaseInformation) {
       
        //list1orderdate
        if(newCaseInformation.FFList1OrderDate == null && oldCaseInformation.FFList1OrderDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1OrderDate.getTime())) + " from Fact Finder List 1 Order Date", null);
        } else if(newCaseInformation.FFList1OrderDate != null && oldCaseInformation.FFList1OrderDate == null) {
            Activity.addActivty("Set Fact Finder List 1 Order Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList1OrderDate.getTime())), null);
        } else if(newCaseInformation.FFList1OrderDate != null && oldCaseInformation.FFList1OrderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1OrderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFList1OrderDate.getTime()))))
                Activity.addActivty("Changed Fact Finder List 1 Order Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1OrderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList1OrderDate.getTime())), null);
        }
        
        //list1SelectionDueDate
        if(newCaseInformation.FFList1SelectionDueDate == null && oldCaseInformation.FFList1SelectionDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1SelectionDueDate.getTime())) + " from Fact Finder List 1 Selection Due Date", null);
        } else if(newCaseInformation.FFList1SelectionDueDate != null && oldCaseInformation.FFList1SelectionDueDate == null) {
            Activity.addActivty("Set Fact Finder List 1 Selection Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList1SelectionDueDate.getTime())), null);
        } else if(newCaseInformation.FFList1SelectionDueDate != null && oldCaseInformation.FFList1SelectionDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1SelectionDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFList1SelectionDueDate.getTime()))))
                Activity.addActivty("Changed Fact Finder List 1 Selection Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1SelectionDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList1SelectionDueDate.getTime())), null);
        }
        
        //appointmentDate
        if(newCaseInformation.FFAppointmentDate == null && oldCaseInformation.FFAppointmentDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFAppointmentDate.getTime())) + " from Fact Finder Appointment Date", null);
        } else if(newCaseInformation.FFAppointmentDate != null && oldCaseInformation.FFAppointmentDate == null) {
            Activity.addActivty("Set Fact Finder Appointment Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFAppointmentDate.getTime())), null);
        } else if(newCaseInformation.FFAppointmentDate != null && oldCaseInformation.FFAppointmentDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFAppointmentDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFAppointmentDate.getTime()))))
                Activity.addActivty("Changed Fact Finder Appointment Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFAppointmentDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFAppointmentDate.getTime())), null);
        }
        
        //FFType
        if(newCaseInformation.FFType == null && oldCaseInformation.FFType != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFType + " from Fact Finder Type", null);
        } else if(newCaseInformation.FFType != null && oldCaseInformation.FFType == null) {
            Activity.addActivty("Set Fact Finder Type to " + newCaseInformation.FFType, null);
        } else if(newCaseInformation.FFType != null && oldCaseInformation.FFType != null) {
            if(!newCaseInformation.FFType.equals(oldCaseInformation.FFType)) 
                Activity.addActivty("Changed Fact Finder Type from " + oldCaseInformation.FFType + " to " + newCaseInformation.FFType, null);
        }
        
        //FFSelection
        if(newCaseInformation.FFSelection == null && oldCaseInformation.FFSelection != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFSelection + " from Fact Finder Selected", null);
        } else if(newCaseInformation.FFSelection != null && oldCaseInformation.FFSelection == null) {
            Activity.addActivty("Set Fact Finder Selected to " + newCaseInformation.FFSelection, null);
        } else if(newCaseInformation.FFSelection != null && oldCaseInformation.FFSelection != null) {
            if(!newCaseInformation.FFSelection.equals(oldCaseInformation.FFSelection)) 
                Activity.addActivty("Changed Fact Finder Selected from " + oldCaseInformation.FFSelection + " to " + newCaseInformation.FFSelection, null);
        }
        
        //replacementFF
        if(newCaseInformation.FFReplacement == null && oldCaseInformation.FFReplacement != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFReplacement + " from Replacement Fact Finder", null);
        } else if(newCaseInformation.FFReplacement != null && oldCaseInformation.FFReplacement == null) {
            Activity.addActivty("Set Replacement Fact Finder to " + newCaseInformation.FFReplacement, null);
        } else if(newCaseInformation.FFReplacement != null && oldCaseInformation.FFReplacement != null) {
            if(!newCaseInformation.FFReplacement.equals(oldCaseInformation.FFReplacement)) 
                Activity.addActivty("Changed Replacement Fact Finder from " + oldCaseInformation.FFReplacement + " to " + newCaseInformation.FFReplacement, null);
        }
        
        //orgFFDate
        if(newCaseInformation.FFOriginalFactFinderDate == null && oldCaseInformation.FFOriginalFactFinderDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFOriginalFactFinderDate.getTime())) + " from Original Fact Finder Date", null);
        } else if(newCaseInformation.FFOriginalFactFinderDate != null && oldCaseInformation.FFOriginalFactFinderDate == null) {
            Activity.addActivty("Set Original Fact Finder Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFOriginalFactFinderDate.getTime())), null);
        } else if(newCaseInformation.FFOriginalFactFinderDate != null && oldCaseInformation.FFOriginalFactFinderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFOriginalFactFinderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFOriginalFactFinderDate.getTime()))))
                Activity.addActivty("Changed Original Fact Finder Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFOriginalFactFinderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFOriginalFactFinderDate.getTime())), null);
        }
        
        //asAgreedToByParties
        if(newCaseInformation.asAgreedToByParties == false && oldCaseInformation.asAgreedToByParties != false) {
            Activity.addActivty("Unset As Agreed To By Parties", null);
        } else if(newCaseInformation.asAgreedToByParties != false && oldCaseInformation.asAgreedToByParties == false) {
            Activity.addActivty("Set As Agreed To By Parties", null);
        } 
        
        //list2orderdate
        if(newCaseInformation.FFList2OrderDate == null && oldCaseInformation.FFList2OrderDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2OrderDate.getTime())) + " from Fact Finder List 2 Order Date", null);
        } else if(newCaseInformation.FFList2OrderDate != null && oldCaseInformation.FFList2OrderDate == null) {
            Activity.addActivty("Set Fact Finder List 2 Order Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2OrderDate.getTime())), null);
        } else if(newCaseInformation.FFList2OrderDate != null && oldCaseInformation.FFList2OrderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2OrderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFList2OrderDate.getTime()))))
                Activity.addActivty("Changed Fact Finder List 2 Order Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2OrderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2OrderDate.getTime())), null);
        }
        
        //list2SelectionDueDate
        if(newCaseInformation.FFList2SelectionDueDate == null && oldCaseInformation.FFList2SelectionDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2SelectionDueDate.getTime())) + " from Fact Finder List 2 Selection Due Date", null);
        } else if(newCaseInformation.FFList2SelectionDueDate != null && oldCaseInformation.FFList2SelectionDueDate == null) {
            Activity.addActivty("Set Fact Finder List 2 Selection Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2SelectionDueDate.getTime())), null);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.FFList2SelectionDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2SelectionDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFList2SelectionDueDate.getTime()))))
                Activity.addActivty("Changed Fact Finder List 2 Selection Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2SelectionDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2SelectionDueDate.getTime())), null);
        }
        
        //EmployerType
        if(newCaseInformation.FFEmployerType == null && oldCaseInformation.FFEmployerType != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFEmployerType + " from Fact Finder Employer Type", null);
        } else if(newCaseInformation.FFEmployerType != null && oldCaseInformation.FFEmployerType == null) {
            Activity.addActivty("Set Fact Finder Employer Type to " + newCaseInformation.FFEmployerType, null);
        } else if(newCaseInformation.FFEmployerType != null && oldCaseInformation.FFEmployerType != null) {
            if(!newCaseInformation.FFEmployerType.equals(oldCaseInformation.FFEmployerType)) 
                Activity.addActivty("Changed Fact Finder Employer Type from " + oldCaseInformation.FFEmployerType + " to " + newCaseInformation.FFEmployerType, null);
        }
        
        //EmployeeType
        if(newCaseInformation.FFEmployeeType == null && oldCaseInformation.FFEmployeeType != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFEmployeeType + " from Fact Finder Employee Type", null);
        } else if(newCaseInformation.FFEmployeeType != null && oldCaseInformation.FFEmployeeType == null) {
            Activity.addActivty("Set Fact Finder Employee Type to " + newCaseInformation.FFEmployeeType, null);
        } else if(newCaseInformation.FFEmployeeType != null && oldCaseInformation.FFEmployeeType != null) {
            if(!newCaseInformation.FFEmployeeType.equals(oldCaseInformation.FFEmployeeType)) 
                Activity.addActivty("Changed Fact Finder Employee Type from " + oldCaseInformation.FFEmployeeType + " to " + newCaseInformation.FFEmployeeType, null);
        }
        
        //FFReportIssueDate
        if(newCaseInformation.FFReportIssueDate == null && oldCaseInformation.FFReportIssueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFReportIssueDate.getTime())) + " from Fact Finder Report Issue Date", null);
        } else if(newCaseInformation.FFReportIssueDate != null && oldCaseInformation.FFReportIssueDate == null) {
            Activity.addActivty("Set Fact Finder Report Issue Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFReportIssueDate.getTime())), null);
        } else if(newCaseInformation.FFReportIssueDate != null && oldCaseInformation.FFReportIssueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFReportIssueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFReportIssueDate.getTime()))))
                Activity.addActivty("Changed Fact Finder Report Issue Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFReportIssueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2OrderDate.getTime())), null);
        }
        
        //MediatedSettlement
        if(newCaseInformation.FFMediatedSettlement == false && oldCaseInformation.FFMediatedSettlement != false) {
            Activity.addActivty("Unset Mediated Settlement", null);
        } else if(newCaseInformation.FFMediatedSettlement != false && oldCaseInformation.FFMediatedSettlement == false) {
            Activity.addActivty("Set Mediated Settlement", null);
        } 
        
        //AcceptedBy
        if(newCaseInformation.FFAcceptedBy == null && oldCaseInformation.FFAcceptedBy != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFAcceptedBy + " from Fact Finder Accepted By", null);
        } else if(newCaseInformation.FFAcceptedBy != null && oldCaseInformation.FFAcceptedBy == null) {
            Activity.addActivty("Set Fact Finder Accepted By to " + newCaseInformation.FFAcceptedBy, null);
        } else if(newCaseInformation.FFAcceptedBy != null && oldCaseInformation.FFAcceptedBy != null) {
            if(!newCaseInformation.FFAcceptedBy.equals(oldCaseInformation.FFAcceptedBy)) 
                Activity.addActivty("Changed Fact Finder Accepted By from " + oldCaseInformation.FFAcceptedBy + " to " + newCaseInformation.FFAcceptedBy, null);
        }
        
        //DeemedAcceptedBy
        if(newCaseInformation.FFDeemedAcceptedBy == null && oldCaseInformation.FFDeemedAcceptedBy != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFDeemedAcceptedBy + " from Fact Finder Deemed Accepted By", null);
        } else if(newCaseInformation.FFDeemedAcceptedBy != null && oldCaseInformation.FFDeemedAcceptedBy == null) {
            Activity.addActivty("Set Fact Finder Deemed Accepted By to " + newCaseInformation.FFDeemedAcceptedBy, null);
        } else if(newCaseInformation.FFDeemedAcceptedBy != null && oldCaseInformation.FFDeemedAcceptedBy != null) {
            if(!newCaseInformation.FFDeemedAcceptedBy.equals(oldCaseInformation.FFDeemedAcceptedBy)) 
                Activity.addActivty("Changed Fact Finder Deemed Accepted By from " + oldCaseInformation.FFDeemedAcceptedBy + " to " + newCaseInformation.FFDeemedAcceptedBy, null);
        }
        
        //RejectedBy
        if(newCaseInformation.FFRejectedBy == null && oldCaseInformation.FFRejectedBy != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFRejectedBy + " from Fact Finder Rejected By", null);
        } else if(newCaseInformation.FFRejectedBy != null && oldCaseInformation.FFRejectedBy == null) {
            Activity.addActivty("Set Fact Finder Rejected By to " + newCaseInformation.FFRejectedBy, null);
        } else if(newCaseInformation.FFRejectedBy != null && oldCaseInformation.FFRejectedBy != null) {
            if(!newCaseInformation.FFRejectedBy.equals(oldCaseInformation.FFRejectedBy)) 
                Activity.addActivty("Changed Fact Finder Rejected By from " + oldCaseInformation.FFRejectedBy + " to " + newCaseInformation.FFRejectedBy, null);
        }
        
        //OverallResult
        if(newCaseInformation.FFOverallResult == null && oldCaseInformation.FFOverallResult != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFOverallResult + " from Fact Finder Overall Result", null);
        } else if(newCaseInformation.FFOverallResult != null && oldCaseInformation.FFOverallResult == null) {
            Activity.addActivty("Set Fact Finder Overall Result to " + newCaseInformation.FFOverallResult, null);
        } else if(newCaseInformation.FFOverallResult != null && oldCaseInformation.FFOverallResult != null) {
            if(!newCaseInformation.FFOverallResult.equals(oldCaseInformation.FFOverallResult)) 
                Activity.addActivty("Changed Fact Finder Overall Result from " + oldCaseInformation.FFOverallResult + " to " + newCaseInformation.FFOverallResult, null);
        }
        
        //Note
        if(newCaseInformation.FFNote == null && oldCaseInformation.FFNote != null) {
            Activity.addActivty("Update Fact Finder Notes", null);
        } else if(newCaseInformation.FFNote != null && oldCaseInformation.FFNote == null) {
            Activity.addActivty("Update Fact Finder Notes", null);
        } else if(newCaseInformation.FFNote != null && oldCaseInformation.FFNote != null) {
            if(!newCaseInformation.FFNote.equals(oldCaseInformation.FFNote)) 
                Activity.addActivty("Update Fact Finder Notes", null);
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
    
    
    public static List<String> getSettleCaseYears() {
        
        List<String> yearList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT DISTINCT caseYear FROM medcase WHERE caseStatus = 'open' "
                    + "AND settlementDate IS NULL ORDER BY caseYear DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                yearList.add(caseNumberRS.getString("caseYear"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return yearList;
    }
    
    public static List<String> getSettleCaseMonths(String caseYear) {
        
        List<String> monthList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT DISTINCT caseMonth FROM medcase WHERE caseStatus = 'open' "
                    + "AND settlementDate IS NULL AND caseYear = ? ORDER BY caseMonth ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                monthList.add(caseNumberRS.getString("caseMonth"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return monthList;
    }
        
    public static void updateSettledCases(String caseNumber, Date settleDate) {
         NumberFormatService num = NumberFormatService.parseFullCaseNumberNoNGlobal(caseNumber);
         
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set"
                    + " settlementDate = ?"
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, (java.sql.Date) settleDate);
            preparedStatement.setString(2, num.caseYear);
            preparedStatement.setString(3, num.caseType);
            preparedStatement.setString(4, num.caseMonth);
            preparedStatement.setString(5, num.caseNumber);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void updateClosedCases(String caseNumber, Date closeDate) {
         NumberFormatService num = NumberFormatService.parseFullCaseNumberNoNGlobal(caseNumber);
         
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set"
                    + " SendToBoardToClose = ?"
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, (java.sql.Date) closeDate);
            preparedStatement.setString(2, num.caseYear);
            preparedStatement.setString(3, num.caseType);
            preparedStatement.setString(4, num.caseMonth);
            preparedStatement.setString(5, num.caseNumber);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
     
     public static List<MEDCase> getSettleList(String caseYear, String caseMonth) {
        
        List<MEDCase> medcaseList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT "
                    + "MEDCase.caseYear AS CaseYear, "
                    + "MEDCase.caseType AS CaseType, "
                    + "MEDCase.caseMonth AS CaseMonth, "
                    + "MEDCase.caseNumber AS CaseNumber,"
                    
                    + "(SELECT CASE WHEN (CaseParty.\"firstName\" IS NULL AND CaseParty.\"lastName\" IS NULL) "
                    + "THEN (CaseParty.\"companyName\") ELSE ((ISNULL(CaseParty.\"firstName\" + ' ', '')) "
                    + "+ (ISNULL(CaseParty.\"middleInitial\" + ' ', '')) + (ISNULL(CaseParty.\"lastName\" + ' ', '')) "
                    + "+ (ISNULL(CaseParty.\"suffix\" + ' ', '')) + (ISNULL(CaseParty.\"nameTitle\" + ' ', '')) "
                    + "+ (ISNULL(CaseParty.\"jobTitle\", ''))) END AS EmployerName FROM CaseParty "
                    + "WHERE CaseParty.caseRelation = 'Employer' AND (CaseParty.caseYear =  MEDCase.caseYear  "
                    + "AND CaseParty.caseType = MEDCase.caseType AND CaseParty.caseMonth =  MEDCase.caseMonth  "
                    + "AND CaseParty.caseNumber =  MEDCase.caseNumber )) AS EmployerName, "
                    
                    + "MEDCase.fileDate AS FileDate "
                    + "FROM  MEDCase "
                    + "WHERE MEDCase.active = '1' "
                    + "AND   MEDCase.caseStatus = 'Open' "
                    + "AND   MEDCase.settlementDate IS NULL "
                    + "AND   MEDCase.caseYear = ? "
                    + "AND   MEDCase.caseMonth = ? "
                    + "ORDER BY MEDCase.caseYear, MEDCase.caseMonth, MEDCase.caseNumber";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            preparedStatement.setString(2, caseMonth);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                MEDCase item = new MEDCase();
                
                item.caseYear = rs.getString("caseYear");
                item.caseType = rs.getString("caseType");
                item.caseMonth = rs.getString("caseMonth");
                item.caseNumber = rs.getString("caseNumber");
                item.employerIDNumber = rs.getString("EmployerName");
                item.fileDate = rs.getTimestamp("fileDate");
                medcaseList.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return medcaseList;
    }
     
     public static List<MEDCase> getCloseList(Date startDate, Date endDate) {
        
        List<MEDCase> medcaseList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT medcase.CaseNumber, medcase.EmployerName, " 
                    + "medcase.CaseFileDate, medcase.Status" 
                    + "FROM medcase" 
                    + "WHERE medcase.CBAReceivedDate BETWEEN CAST(? as datetime) AND CAST(? as datetime)" 
                    + "AND medcase.Active = 1 AND medcase.tempholder4 != 'Send to Brd to Close' ORDER BY medcase.CaseNumber";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, (java.sql.Date) startDate);
            preparedStatement.setDate(2, (java.sql.Date) endDate);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                MEDCase item = new MEDCase();
                
                item.caseYear = rs.getString("caseYear");
                item.caseType = rs.getString("caseType");
                item.caseMonth = rs.getString("caseMonth");
                item.caseNumber = rs.getString("caseNumber");
                item.employerIDNumber = rs.getString("EmployerName");
                item.fileDate = rs.getTimestamp("fileDate");
                medcaseList.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return medcaseList;
    }
     
     
    
}
