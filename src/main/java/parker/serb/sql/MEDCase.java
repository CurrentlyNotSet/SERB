package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.DefaultListModel;
import org.apache.commons.dbutils.DbUtils;
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
    public String note;

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

    //strike informatin
    public Timestamp strikeFileDate;
    public String description;
    public String unitSize;
    public boolean unauthorizedStrike;
    public boolean noticeOfIntentToStrikeOnly;
    public Timestamp intendedDateStrike;
    public boolean noticeOfIntentToPicketOnly;
    public Timestamp intendedDatePicket;
    public boolean informational;
    public boolean noticeOfIntentToStrikeAndPicket;
    public String strikeOccured;
    public String strikeStatus;
    public Timestamp strikeBegan;
    public Timestamp strikeEnded;
    public String totalNumberOfDays;
    public String strikeMediatorAppointedID;
    public String strikeNotes;

    public static List loadMEDCaseNumbers() {
        List caseNumberList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP 250"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber"
                    + " from MEDCase"
                    + " Order By FileDate DESC, caseYear DESC, caseMonth DESC, caseNumber DESC";

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadMEDCaseNumbers();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseNumberList;
    }

    public static boolean validateCaseNumber(String fullCaseNumber) {
        String[] caseNumberParts = fullCaseNumber.split("-");
        boolean valid = false;

        if(caseNumberParts.length != 4) {
            return false;
        }

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select Count(*) As results"
                    + " from MEDCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumberParts[0]);
            preparedStatement.setString(2, caseNumberParts[1]);
            preparedStatement.setString(3, caseNumberParts[2]);
            preparedStatement.setString(4, caseNumberParts[3]);

            ResultSet validRS = preparedStatement.executeQuery();

            validRS.next();

            valid = validRS.getInt("results") > 0;

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                validateCaseNumber(fullCaseNumber);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return valid;
    }

    /**
     * Loads the notes that are related to the case
     * @return a stringified note
     */
    public static String loadNote() {
        String note = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadNote();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return note;
    }

    /**
     * Updates the note that is related to the case number
     * @param note the new note value to be stored
     */
    public static void updateNote(String note) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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

            Audit.addAuditEntry("Updated Note for " + NumberFormatService.generateFullCaseNumber());
            Activity.addActivty("Updated Note", null);
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateNote(note);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    /**
     * Creates a new REPCase entry
     * @param caseNumber the case number to be created
     */
    public static void createCase(String caseYear, String caseType, String caseMonth, String caseNumber) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert into MEDCase (CaseYear, CaseType, CaseMonth, CaseNumber, FileDate, caseStatus) Values (?,?,?,?,?,'Open')";

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createCase(caseYear, caseType, caseMonth, caseNumber);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    /**
     * Load information that is to be displayed in the header.  Dates are
     * formatted before being returned
     * @return a REPCase instance with the needed values
     */
    public static MEDCase loadHeaderInformation() {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadHeaderInformation();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return med;
    }

    public static MEDCase loadConciliationInformation() {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadConciliationInformation();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return med;
    }
    
    public static MEDCase loadConciliationInformation(String caseNumber) {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            preparedStatement.setString(1, caseNumber.split("-")[0]);
            preparedStatement.setString(2, caseNumber.split("-")[1]);
            preparedStatement.setString(3, caseNumber.split("-")[2]);
            preparedStatement.setString(4, caseNumber.split("-")[3]);

            ResultSet caseInformation = preparedStatement.executeQuery();

            if(caseInformation.next()) {
                med = new MEDCase();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadConciliationInformation();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return med;
    }
    
    

    public static MEDCase loadFFInformation() {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadFFInformation();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return med;
    }
    
    public static MEDCase loadFFInformation(String caseNumber) {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            preparedStatement.setString(1, caseNumber.split("-")[0]);
            preparedStatement.setString(2, caseNumber.split("-")[1]);
            preparedStatement.setString(3, caseNumber.split("-")[2]);
            preparedStatement.setString(4, caseNumber.split("-")[3]);

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadFFInformation();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return med;
    }

    public static MEDCase loadStatusInformation() {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadStatusInformation();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return med;
    }

    public static MEDCase loadStrikeInformation() {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " strikeFileDate,"
                    + " relatedCaseNumber,"
                    + " unitDescription,"
                    + " FFEmployerType,"
                    + " FFEmployeeType,"
                    + " unitSize,"
                    + " unauthorizedStrike,"
                    + " noticeOfIntentToStrikeOnly,"
                    + " intendedDateStrike,"
                    + " noticeOfIntentToPicketOnly,"
                    + " informational,"
                    + " noticeOfIntentToStrikeAndPicket,"
                    + " strikeOccured,"
                    + " strikeStatus,"
                    + " strikeBegan,"
                    + " strikeEnded,"
                    + " totalNumberOfDays,"
                    + " strikeMediatorAppointedID,"
                    + " strikeNote"
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

                med.strikeFileDate = caseInformation.getTimestamp("strikeFileDate");
                med.relatedCaseNumber = caseInformation.getString("relatedCaseNumber");
                med.description = caseInformation.getString("unitDescription");
                med.FFEmployerType = caseInformation.getString("FFEmployerType");
                med.FFEmployeeType = caseInformation.getString("FFEmployeeType");
                med.unitSize = caseInformation.getString("unitSize");
                med.unauthorizedStrike = caseInformation.getBoolean("unauthorizedStrike");
                med.noticeOfIntentToStrikeOnly = caseInformation.getBoolean("noticeOfIntentToStrikeOnly");
                med.intendedDateStrike = caseInformation.getTimestamp("intendedDateStrike");
                med.noticeOfIntentToPicketOnly = caseInformation.getBoolean("noticeOfIntentToPicketOnly");
                med.informational = caseInformation.getBoolean("informational");
                med.noticeOfIntentToStrikeAndPicket = caseInformation.getBoolean("noticeOfIntentToStrikeAndPicket");
                med.strikeOccured = caseInformation.getString("strikeOccured");
                med.strikeStatus = caseInformation.getString("strikeStatus");
                med.strikeBegan = caseInformation.getTimestamp("strikeBegan");
                med.strikeEnded = caseInformation.getTimestamp("strikeEnded");
                med.totalNumberOfDays = caseInformation.getString("totalNumberOfDays");
                med.strikeMediatorAppointedID = caseInformation.getString("strikeMediatorAppointedID");
                med.strikeNotes = caseInformation.getString("strikeNote");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadStrikeInformation();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return med;
    }

    public static void updateStrikeInformation(MEDCase newCaseInfo, MEDCase existingCaseInco) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase Set"
                    + " strikeFileDate = ?,"    //01
                    + " relatedCaseNumber = ?," //02
                    + " unitDescription = ?,"   //03
                    + " unitSize = ?,"          //04
                    + " unauthorizedStrike = ?,"//05
                    + " noticeOfIntentToStrikeOnly = ?,"        //06
                    + " intendedDateStrike = ?,"                //07
                    + " noticeOfIntentToPicketOnly = ?,"        //08
                    + " intendedDatePicket = ?,"                //09
                    + " informational = ?,"                     //10
                    + " noticeOfIntentToStrikeAndPicket = ?,"   //11
                    + " strikeOccured = ?,"             //12
                    + " strikeStatus = ?,"              //13
                    + " strikeBegan = ?,"               //14
                    + " strikeEnded = ?,"               //15
                    + " totalNumberOfDays = ?,"         //16
                    + " strikeMediatorAppointedID = ?," //17
                    + " FFEmployerType = ?,"            //18
                    + " FFEmployeeType = ?,"            //19
                    + " strikeNote = ?"                 //20
                    + " WHERE caseYear = ? "            //21
                    + " AND caseType = ? "              //22
                    + " AND caseMonth = ? "             //23
                    + " AND caseNumber = ?";            //24

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp  ( 1, newCaseInfo.strikeFileDate);
            preparedStatement.setString     ( 2, newCaseInfo.relatedCaseNumber);
            preparedStatement.setString     ( 3, newCaseInfo.description);
            preparedStatement.setString     ( 4, newCaseInfo.unitSize);
            preparedStatement.setBoolean    ( 5, newCaseInfo.unauthorizedStrike);
            preparedStatement.setBoolean    ( 6, newCaseInfo.noticeOfIntentToStrikeOnly);
            preparedStatement.setTimestamp  ( 7, newCaseInfo.intendedDateStrike);
            preparedStatement.setBoolean    ( 8, newCaseInfo.noticeOfIntentToPicketOnly);
            preparedStatement.setTimestamp  ( 9, newCaseInfo.intendedDatePicket);
            preparedStatement.setBoolean    (10, newCaseInfo.informational);
            preparedStatement.setBoolean    (11, newCaseInfo.noticeOfIntentToStrikeAndPicket);
            preparedStatement.setString     (12, newCaseInfo.strikeOccured);
            preparedStatement.setString     (13, newCaseInfo.strikeStatus);
            preparedStatement.setTimestamp  (14, newCaseInfo.strikeBegan);
            preparedStatement.setTimestamp  (15, newCaseInfo.strikeEnded);
            preparedStatement.setString     (16, newCaseInfo.totalNumberOfDays);
            preparedStatement.setString     (17, newCaseInfo.strikeMediatorAppointedID);
            preparedStatement.setString     (18, newCaseInfo.FFEmployerType);
            preparedStatement.setString     (19, newCaseInfo.FFEmployeeType);
            preparedStatement.setString     (20, newCaseInfo.strikeNotes);
            preparedStatement.setString     (21, Global.caseYear);
            preparedStatement.setString     (22, Global.caseType);
            preparedStatement.setString     (23, Global.caseMonth);
            preparedStatement.setString     (24, Global.caseNumber);

            int success = preparedStatement.executeUpdate();

            if(success == 1) {
                detailedStrikeSaveInformation(newCaseInfo, existingCaseInco);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateStrikeInformation(newCaseInfo, existingCaseInco);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void saveConciliationList1(DefaultListModel concilList1Model) {

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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

                saveGroupedConciliationList1(concilList1Model);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                saveConciliationList1(concilList1Model);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void saveGroupedConciliationList1(DefaultListModel concilList1Model) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {
            Statement stmt = null;
            try {
                stmt = Database.connectToDB().createStatement();

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
                preparedStatement.setString(6, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(7, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(8, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(9, relatedCases.get(i).split("-")[3]);

                int success = preparedStatement.executeUpdate();

                if(success == 1) {
                    String names = concilList1Model.get(0).toString().substring(0, 1) + "." + concilList1Model.get(0).toString().substring(concilList1Model.get(0).toString().lastIndexOf(" "));
                    names += ", " + concilList1Model.get(1).toString().substring(0, 1) + "." + concilList1Model.get(1).toString().substring(concilList1Model.get(1).toString().lastIndexOf(" "));
                    names += ", " + concilList1Model.get(2).toString().substring(0, 1) + "." + concilList1Model.get(2).toString().substring(concilList1Model.get(2).toString().lastIndexOf(" "));
                    names += ", " + concilList1Model.get(3).toString().substring(0, 1) + "." + concilList1Model.get(3).toString().substring(concilList1Model.get(3).toString().lastIndexOf(" "));
                    names += ", " + concilList1Model.get(4).toString().substring(0, 1) + "." + concilList1Model.get(4).toString().substring(concilList1Model.get(4).toString().lastIndexOf(" "));

                    Activity.addActivtyNonGlobalCaseNumber("Generated Conciliator List (" + names + ")", null, relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    saveGroupedConciliationList1(concilList1Model);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

    public static void saveConciliationList2(DefaultListModel concilList2Model) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

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

                saveGroupedConciliationList2(concilList2Model);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                saveConciliationList2(concilList2Model);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void saveGroupedConciliationList2(DefaultListModel concilList2Model) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {

            Statement stmt = null;

            try {
                stmt = Database.connectToDB().createStatement();

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
                preparedStatement.setString(6, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(7, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(8, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(9, relatedCases.get(i).split("-")[3]);

                int success = preparedStatement.executeUpdate();

                if(success == 1) {
                    String names = concilList2Model.get(0).toString().substring(0, 1) + "." + concilList2Model.get(0).toString().substring(concilList2Model.get(0).toString().lastIndexOf(" "));
                    names += ", " + concilList2Model.get(1).toString().substring(0, 1) + "." + concilList2Model.get(1).toString().substring(concilList2Model.get(1).toString().lastIndexOf(" "));
                    names += ", " + concilList2Model.get(2).toString().substring(0, 1) + "." + concilList2Model.get(2).toString().substring(concilList2Model.get(2).toString().lastIndexOf(" "));
                    names += ", " + concilList2Model.get(3).toString().substring(0, 1) + "." + concilList2Model.get(3).toString().substring(concilList2Model.get(3).toString().lastIndexOf(" "));
                    names += ", " + concilList2Model.get(4).toString().substring(0, 1) + "." + concilList2Model.get(4).toString().substring(concilList2Model.get(4).toString().lastIndexOf(" "));

                    Activity.addActivtyNonGlobalCaseNumber("Generated Conciliator List (" + names + ")", null, relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    saveGroupedConciliationList2(concilList2Model);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

    public static void replaceList2Concil(int location, String newName, String oldName) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

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
                replaceGroupedList2Concil(location, newName, oldName);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                replaceList2Concil(location, newName, oldName);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void replaceGroupedList2Concil(int location, String newName, String oldName) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {

            Statement stmt = null;

            try {
                stmt = Database.connectToDB().createStatement();

                String sql = "Update MEDCase set"
                        + " concilList2Name" + String.valueOf(location + 1) + " = ?"
                        + " where caseYear = ? "
                        + " AND caseType = ? "
                        + " AND caseMonth = ? "
                        + " AND caseNumber = ?";

                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(3, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(4, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(5, relatedCases.get(i).split("-")[3]);

                int success = preparedStatement.executeUpdate();

                if(success == 1) {
                    Activity.addActivtyNonGlobalCaseNumber("Replaced " + oldName + " with " + newName, null, relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    replaceGroupedList2Concil(location, newName, oldName);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

    public static void replaceList1Concil(int location, String newName, String oldName) {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
                replaceGroupedList1Concil(location, newName, oldName);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                replaceList1Concil(location, newName, oldName);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void replaceGroupedList1Concil(int location, String newName, String oldName) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {
            Statement stmt = null;

            try {
                stmt = Database.connectToDB().createStatement();

                String sql = "Update MEDCase set"
                        + " concilList1Name" + String.valueOf(location + 1) + " = ?"
                        + " where caseYear = ? "
                        + " AND caseType = ? "
                        + " AND caseMonth = ? "
                        + " AND caseNumber = ?";

                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(3, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(4, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(5, relatedCases.get(i).split("-")[3]);

                int success = preparedStatement.executeUpdate();

                if(success == 1) {
                    Activity.addActivtyNonGlobalCaseNumber("Replaced " + oldName + " with " + newName, null, relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    replaceGroupedList1Concil(location, newName, oldName);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

    public static void saveFFList1(DefaultListModel concilList1Model) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

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

                saveGroupedFFList1(concilList1Model);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                saveFFList1(concilList1Model);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void saveGroupedFFList1(DefaultListModel concilList1Model) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {
            Statement stmt = null;

            try {
                stmt = Database.connectToDB().createStatement();

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
                preparedStatement.setString(6, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(7, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(8, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(9, relatedCases.get(i).split("-")[3]);

                int success = preparedStatement.executeUpdate();

                if(success == 1) {
                    String names = concilList1Model.get(0).toString().substring(0, 1) + "." + concilList1Model.get(0).toString().substring(concilList1Model.get(0).toString().lastIndexOf(" "));
                    names += ", " + concilList1Model.get(1).toString().substring(0, 1) + "." + concilList1Model.get(1).toString().substring(concilList1Model.get(1).toString().lastIndexOf(" "));
                    names += ", " + concilList1Model.get(2).toString().substring(0, 1) + "." + concilList1Model.get(2).toString().substring(concilList1Model.get(2).toString().lastIndexOf(" "));
                    names += ", " + concilList1Model.get(3).toString().substring(0, 1) + "." + concilList1Model.get(3).toString().substring(concilList1Model.get(3).toString().lastIndexOf(" "));
                    names += ", " + concilList1Model.get(4).toString().substring(0, 1) + "." + concilList1Model.get(4).toString().substring(concilList1Model.get(4).toString().lastIndexOf(" "));
    
                    Activity.addActivtyNonGlobalCaseNumber("Generated Fact Finder List (" + names + ")", null, relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    saveGroupedFFList1(concilList1Model);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

    public static void saveFFList2(DefaultListModel concilList2Model) {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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

                saveGroupedFFList2(concilList2Model);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                saveFFList2(concilList2Model);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void saveGroupedFFList2(DefaultListModel concilList2Model) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {
            Statement stmt = null;

            try {
                stmt = Database.connectToDB().createStatement();

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
                preparedStatement.setString(6, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(7, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(8, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(9, relatedCases.get(i).split("-")[3]);

                int success = preparedStatement.executeUpdate();

                if(success == 1) {
                    String names = concilList2Model.get(0).toString().substring(0, 1) + "." + concilList2Model.get(0).toString().substring(concilList2Model.get(0).toString().lastIndexOf(" "));
                    names += ", " + concilList2Model.get(1).toString().substring(0, 1) + "." + concilList2Model.get(1).toString().substring(concilList2Model.get(1).toString().lastIndexOf(" "));
                    names += ", " + concilList2Model.get(2).toString().substring(0, 1) + "." + concilList2Model.get(2).toString().substring(concilList2Model.get(2).toString().lastIndexOf(" "));
                    names += ", " + concilList2Model.get(3).toString().substring(0, 1) + "." + concilList2Model.get(3).toString().substring(concilList2Model.get(3).toString().lastIndexOf(" "));
                    names += ", " + concilList2Model.get(4).toString().substring(0, 1) + "." + concilList2Model.get(4).toString().substring(concilList2Model.get(4).toString().lastIndexOf(" "));
    
                    Activity.addActivtyNonGlobalCaseNumber("Generated Fact Finder List (" + names + ")", null, relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    saveGroupedFFList2(concilList2Model);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

    public static void replaceList2FF(int location, String newName, String oldName) {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
                replaceGroupedList2FF(location, newName, oldName);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                replaceList2FF(location, newName, oldName);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void replaceGroupedList2FF(int location, String newName, String oldName) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {

            Statement stmt = null;
            try {
                stmt = Database.connectToDB().createStatement();

                String sql = "Update MEDCase set"
                        + " FFList2Name" + String.valueOf(location + 1) + " = ?"
                        + " where caseYear = ? "
                        + " AND caseType = ? "
                        + " AND caseMonth = ? "
                        + " AND caseNumber = ?";

                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(3, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(4, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(5, relatedCases.get(i).split("-")[3]);

                int success = preparedStatement.executeUpdate();

                if(success == 1) {
                    Activity.addActivtyNonGlobalCaseNumber("Replaced " + oldName + " with " + newName, null, relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    replaceGroupedList2FF(location, newName, oldName);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

    public static void replaceList1FF(int location, String newName, String oldName) {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
                replaceGroupedList1FF(location, newName, oldName);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                replaceList1FF(location, newName, oldName);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void replaceGroupedList1FF(int location, String newName, String oldName) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {
            Statement stmt = null;
            try {
                stmt = Database.connectToDB().createStatement();

                String sql = "Update MEDCase set"
                        + " FFList1Name" + String.valueOf(location + 1) + " = ?"
                        + " where caseYear = ? "
                        + " AND caseType = ? "
                        + " AND caseMonth = ? "
                        + " AND caseNumber = ?";

                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, newName);
                preparedStatement.setString(2, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(3, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(4, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(5, relatedCases.get(i).split("-")[3]);

                int success = preparedStatement.executeUpdate();

                if(success == 1) {
                    Activity.addActivtyNonGlobalCaseNumber("Replaced " + oldName + " with " + newName, null, relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    replaceGroupedList1FF(location, newName, oldName);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

    public static void updateConciliation(MEDCase newCaseInformation, MEDCase caseInformation) {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
                updateGroupedConciliation(newCaseInformation);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateConciliation(newCaseInformation, caseInformation);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateGroupedConciliation(MEDCase newCaseInformation) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {

            Statement stmt = null;

            try {
                stmt = Database.connectToDB().createStatement();

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
                preparedStatement.setString(11, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(12, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(13, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(14, relatedCases.get(i).split("-")[3]);
                
                int success = preparedStatement.executeUpdate();
    
                if(success == 1) {
                    detailedGroupConciliationDetailSaveInformation(newCaseInformation, loadConciliationInformation(relatedCases.get(i)), relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    updateGroupedConciliation(newCaseInformation);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

    public static void updateStatusInformation(MEDCase newCaseInformation, MEDCase caseInformation) {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
                MEDCaseSearchData.updateCaseEntryFromCaseStatus(newCaseInformation.employerIDNumber, newCaseInformation.bargainingUnitNumber);
                detailedStatusSaveInformation(newCaseInformation, caseInformation);
                EmployerCaseSearchData.updateFileDate(
                        newCaseInformation.fileDate);
                EmployerCaseSearchData.updateCaseStatus(
                        newCaseInformation.caseStatus);
                EmployerCaseSearchData.updateEmployer(
                        newCaseInformation.employerIDNumber);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateStatusInformation(newCaseInformation, caseInformation);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateFF(MEDCase newCaseInformation, MEDCase caseInformation) {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
                updateGroupedFF(newCaseInformation);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateFF(newCaseInformation, caseInformation);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateGroupedFF(MEDCase newCaseInformation) {
        List<String> relatedCases = RelatedCase.loadRelatedCases();

        for(int i = 0; i < relatedCases.size(); i++) {
            Statement stmt = null;

            try {
                stmt = Database.connectToDB().createStatement();

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
                preparedStatement.setString(21, relatedCases.get(i).split("-")[0]);
                preparedStatement.setString(22, relatedCases.get(i).split("-")[1]);
                preparedStatement.setString(23, relatedCases.get(i).split("-")[2]);
                preparedStatement.setString(24, relatedCases.get(i).split("-")[3]);

                int success = preparedStatement.executeUpdate();
    
                if(success == 1) {
                    detailedGroupFFDetailSaveInformation(newCaseInformation, loadFFInformation(relatedCases.get(i)), relatedCases.get(i));
                }
            } catch (SQLException ex) {
                SlackNotification.sendNotification(ex);
                if(ex.getCause() instanceof SQLServerException) {
                    updateGroupedFF(newCaseInformation);
                }
            } finally {
                DbUtils.closeQuietly(stmt);
            }
        }
    }

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
        
        //originalconciliationSelection
        if(newCaseInformation.concilOriginalConciliator == null && oldCaseInformation.concilOriginalConciliator != null) {
            Activity.addActivty("Removed " + oldCaseInformation.concilOriginalConciliator + " from Original Conciliator Selected", null);
        } else if(newCaseInformation.concilOriginalConciliator != null && oldCaseInformation.concilOriginalConciliator == null) {
            Activity.addActivty("Set Original Conciliator Selected to " + newCaseInformation.concilOriginalConciliator, null);
        } else if(newCaseInformation.concilOriginalConciliator != null && oldCaseInformation.concilOriginalConciliator != null) {
            if(!newCaseInformation.concilOriginalConciliator.equals(oldCaseInformation.concilOriginalConciliator))
                Activity.addActivty("Changed Original Conciliator Selected from " + oldCaseInformation.concilOriginalConciliator + " to " + newCaseInformation.concilOriginalConciliator, null);
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
    
    private static void detailedGroupConciliationDetailSaveInformation(MEDCase newCaseInformation, MEDCase oldCaseInformation, String caseNumber) {

        //list1orderdate
        if(newCaseInformation.concilList1OrderDate == null && oldCaseInformation.concilList1OrderDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1OrderDate.getTime())) + " from Conciliation List 1 Order Date", null, caseNumber);
        } else if(newCaseInformation.concilList1OrderDate != null && oldCaseInformation.concilList1OrderDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Conciliation List 1 Order Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList1OrderDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.concilList1OrderDate != null && oldCaseInformation.concilList1OrderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1OrderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilList1OrderDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Conciliation List 1 Order Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1OrderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList1OrderDate.getTime())), null, caseNumber);
        }

        //list1SelectionDueDate
        if(newCaseInformation.concilList1SelectionDueDate == null && oldCaseInformation.concilList1SelectionDueDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1SelectionDueDate.getTime())) + " from Conciliation List 1 Selection Due Date", null, caseNumber);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.concilList1SelectionDueDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Conciliation List 1 Selection Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList1SelectionDueDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.concilList1SelectionDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1SelectionDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilList1SelectionDueDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Conciliation List 1 Selection Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList1SelectionDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList1SelectionDueDate.getTime())), null, caseNumber);
        }

        //appointmentDate
        if(newCaseInformation.concilAppointmentDate == null && oldCaseInformation.concilAppointmentDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilAppointmentDate.getTime())) + " from Conciliation Appointment Date", null, caseNumber);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.concilAppointmentDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Conciliation Appointment Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilAppointmentDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.concilAppointmentDate != null && oldCaseInformation.concilAppointmentDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilAppointmentDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilAppointmentDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Conciliation Appointment Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilAppointmentDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilAppointmentDate.getTime())), null, caseNumber);
        }

        //conciliationType
        if(newCaseInformation.concilType == null && oldCaseInformation.concilType != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.concilType + " from Conciliation Type", null, caseNumber);
        } else if(newCaseInformation.concilType != null && oldCaseInformation.concilType == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Conciliation Type to " + newCaseInformation.concilType, null, caseNumber);
        } else if(newCaseInformation.concilType != null && oldCaseInformation.concilType != null) {
            if(!newCaseInformation.concilType.equals(oldCaseInformation.concilType))
                Activity.addActivtyNonGlobalCaseNumber("Changed Conciliation Type from " + oldCaseInformation.concilType + " to " + newCaseInformation.concilType, null, caseNumber);
        }

        //conciliationSelection
        if(newCaseInformation.concilSelection == null && oldCaseInformation.concilSelection != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.concilSelection + " from Conciliator Selected", null, caseNumber);
        } else if(newCaseInformation.concilSelection != null && oldCaseInformation.concilSelection == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Conciliator Selected to " + newCaseInformation.concilSelection, null, caseNumber);
        } else if(newCaseInformation.concilSelection != null && oldCaseInformation.concilSelection != null) {
            if(!newCaseInformation.concilSelection.equals(oldCaseInformation.concilSelection))
                Activity.addActivtyNonGlobalCaseNumber("Changed Conciliator Selected from " + oldCaseInformation.concilSelection + " to " + newCaseInformation.concilSelection, null, caseNumber);
        }

        //replacementConciliation
        if(newCaseInformation.concilReplacement == null && oldCaseInformation.concilReplacement != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.concilReplacement + " from Replacement Conciliation", null, caseNumber);
        } else if(newCaseInformation.concilReplacement != null && oldCaseInformation.concilReplacement == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Replacement Conciliation to " + newCaseInformation.concilReplacement, null, caseNumber);
        } else if(newCaseInformation.concilReplacement != null && oldCaseInformation.concilReplacement != null) {
            if(!newCaseInformation.concilReplacement.equals(oldCaseInformation.concilReplacement))
                Activity.addActivtyNonGlobalCaseNumber("Changed Replacement Conciliation from " + oldCaseInformation.concilReplacement + " to " + newCaseInformation.concilReplacement, null, caseNumber);
        }

        //orgConciliationDate
        if(newCaseInformation.concilOriginalConcilDate == null && oldCaseInformation.concilOriginalConcilDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilOriginalConcilDate.getTime())) + " from Original Conciliation Date", null, caseNumber);
        } else if(newCaseInformation.concilOriginalConcilDate != null && oldCaseInformation.concilOriginalConcilDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Original Conciliation Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilOriginalConcilDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.concilOriginalConcilDate != null && oldCaseInformation.concilOriginalConcilDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilOriginalConcilDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilOriginalConcilDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Original Conciliation Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilOriginalConcilDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilOriginalConcilDate.getTime())), null, caseNumber);
        }

        //list2orderdate
        if(newCaseInformation.concilList2OrderDate == null && oldCaseInformation.concilList2OrderDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2OrderDate.getTime())) + " from Conciliation List 2 Order Date", null, caseNumber);
        } else if(newCaseInformation.concilList2OrderDate != null && oldCaseInformation.concilList2OrderDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Conciliation List 2 Order Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList2OrderDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.concilList2OrderDate != null && oldCaseInformation.concilList2OrderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2OrderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilList2OrderDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Conciliation List 2 Order Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2OrderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList2OrderDate.getTime())), null, caseNumber);
        }

        //list2SelectionDueDate
        if(newCaseInformation.concilList2SelectionDueDate == null && oldCaseInformation.concilList2SelectionDueDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2SelectionDueDate.getTime())) + " from Conciliation List 2 Selection Due Date", null, caseNumber);
        } else if(newCaseInformation.concilList2SelectionDueDate != null && oldCaseInformation.concilList2SelectionDueDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Conciliation List 2 Selection Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList2SelectionDueDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.concilList2SelectionDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2SelectionDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.concilList2SelectionDueDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Conciliation List 2 Selection Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.concilList2SelectionDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.concilList2SelectionDueDate.getTime())), null, caseNumber);
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
        
        //FFOrgSelection
        if(newCaseInformation.FFOriginalFactFinder == null && oldCaseInformation.FFOriginalFactFinder != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FFOriginalFactFinder + " from Original Fact Finder Selected", null);
        } else if(newCaseInformation.FFOriginalFactFinder != null && oldCaseInformation.FFOriginalFactFinder == null) {
            Activity.addActivty("Set Original Fact Finder Selected to " + newCaseInformation.FFOriginalFactFinder, null);
        } else if(newCaseInformation.FFOriginalFactFinder != null && oldCaseInformation.FFOriginalFactFinder != null) {
            if(!newCaseInformation.FFOriginalFactFinder.equals(oldCaseInformation.FFOriginalFactFinder))
                Activity.addActivty("Changed Original Fact Finder Selected from " + oldCaseInformation.FFOriginalFactFinder + " to " + newCaseInformation.FFOriginalFactFinder, null);
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
            Activity.addActivty("Updated Fact Finder Notes", null);
        } else if(newCaseInformation.FFNote != null && oldCaseInformation.FFNote == null) {
            Activity.addActivty("Updated Fact Finder Notes", null);
        } else if(newCaseInformation.FFNote != null && oldCaseInformation.FFNote != null) {
            if(!newCaseInformation.FFNote.equals(oldCaseInformation.FFNote))
                Activity.addActivty("Updated Fact Finder Notes", null);
        }
    }

    private static void detailedGroupFFDetailSaveInformation(MEDCase newCaseInformation, MEDCase oldCaseInformation, String caseNumber) {

        //list1orderdate
        if(newCaseInformation.FFList1OrderDate == null && oldCaseInformation.FFList1OrderDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1OrderDate.getTime())) + " from Fact Finder List 1 Order Date", null, caseNumber);
        } else if(newCaseInformation.FFList1OrderDate != null && oldCaseInformation.FFList1OrderDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder List 1 Order Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList1OrderDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.FFList1OrderDate != null && oldCaseInformation.FFList1OrderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1OrderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFList1OrderDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder List 1 Order Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1OrderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList1OrderDate.getTime())), null, caseNumber);
        }

        //list1SelectionDueDate
        if(newCaseInformation.FFList1SelectionDueDate == null && oldCaseInformation.FFList1SelectionDueDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1SelectionDueDate.getTime())) + " from Fact Finder List 1 Selection Due Date", null, caseNumber);
        } else if(newCaseInformation.FFList1SelectionDueDate != null && oldCaseInformation.FFList1SelectionDueDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder List 1 Selection Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList1SelectionDueDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.FFList1SelectionDueDate != null && oldCaseInformation.FFList1SelectionDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1SelectionDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFList1SelectionDueDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder List 1 Selection Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList1SelectionDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList1SelectionDueDate.getTime())), null, caseNumber);
        }

        //appointmentDate
        if(newCaseInformation.FFAppointmentDate == null && oldCaseInformation.FFAppointmentDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFAppointmentDate.getTime())) + " from Fact Finder Appointment Date", null, caseNumber);
        } else if(newCaseInformation.FFAppointmentDate != null && oldCaseInformation.FFAppointmentDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Appointment Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFAppointmentDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.FFAppointmentDate != null && oldCaseInformation.FFAppointmentDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFAppointmentDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFAppointmentDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Appointment Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFAppointmentDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFAppointmentDate.getTime())), null, caseNumber);
        }

        //FFType
        if(newCaseInformation.FFType == null && oldCaseInformation.FFType != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.FFType + " from Fact Finder Type", null, caseNumber);
        } else if(newCaseInformation.FFType != null && oldCaseInformation.FFType == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Type to " + newCaseInformation.FFType, null, caseNumber);
        } else if(newCaseInformation.FFType != null && oldCaseInformation.FFType != null) {
            if(!newCaseInformation.FFType.equals(oldCaseInformation.FFType))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Type from " + oldCaseInformation.FFType + " to " + newCaseInformation.FFType, null, caseNumber);
        }

        //FFSelection
        if(newCaseInformation.FFSelection == null && oldCaseInformation.FFSelection != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.FFSelection + " from Fact Finder Selected", null, caseNumber);
        } else if(newCaseInformation.FFSelection != null && oldCaseInformation.FFSelection == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Selected to " + newCaseInformation.FFSelection, null, caseNumber);
        } else if(newCaseInformation.FFSelection != null && oldCaseInformation.FFSelection != null) {
            if(!newCaseInformation.FFSelection.equals(oldCaseInformation.FFSelection))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Selected from " + oldCaseInformation.FFSelection + " to " + newCaseInformation.FFSelection, null, caseNumber);
        }

        //replacementFF
        if(newCaseInformation.FFReplacement == null && oldCaseInformation.FFReplacement != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.FFReplacement + " from Replacement Fact Finder", null, caseNumber);
        } else if(newCaseInformation.FFReplacement != null && oldCaseInformation.FFReplacement == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Replacement Fact Finder to " + newCaseInformation.FFReplacement, null, caseNumber);
        } else if(newCaseInformation.FFReplacement != null && oldCaseInformation.FFReplacement != null) {
            if(!newCaseInformation.FFReplacement.equals(oldCaseInformation.FFReplacement))
                Activity.addActivtyNonGlobalCaseNumber("Changed Replacement Fact Finder from " + oldCaseInformation.FFReplacement + " to " + newCaseInformation.FFReplacement, null, caseNumber);
        }

        //orgFFDate
        if(newCaseInformation.FFOriginalFactFinderDate == null && oldCaseInformation.FFOriginalFactFinderDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFOriginalFactFinderDate.getTime())) + " from Original Fact Finder Date", null, caseNumber);
        } else if(newCaseInformation.FFOriginalFactFinderDate != null && oldCaseInformation.FFOriginalFactFinderDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Original Fact Finder Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFOriginalFactFinderDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.FFOriginalFactFinderDate != null && oldCaseInformation.FFOriginalFactFinderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFOriginalFactFinderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFOriginalFactFinderDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Original Fact Finder Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFOriginalFactFinderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFOriginalFactFinderDate.getTime())), null, caseNumber);
        }

        //asAgreedToByParties
        if(newCaseInformation.asAgreedToByParties == false && oldCaseInformation.asAgreedToByParties != false) {
            Activity.addActivtyNonGlobalCaseNumber("Unset As Agreed To By Parties", null, caseNumber);
        } else if(newCaseInformation.asAgreedToByParties != false && oldCaseInformation.asAgreedToByParties == false) {
            Activity.addActivtyNonGlobalCaseNumber("Set As Agreed To By Parties", null, caseNumber);
        }

        //list2orderdate
        if(newCaseInformation.FFList2OrderDate == null && oldCaseInformation.FFList2OrderDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2OrderDate.getTime())) + " from Fact Finder List 2 Order Date", null, caseNumber);
        } else if(newCaseInformation.FFList2OrderDate != null && oldCaseInformation.FFList2OrderDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder List 2 Order Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2OrderDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.FFList2OrderDate != null && oldCaseInformation.FFList2OrderDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2OrderDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFList2OrderDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder List 2 Order Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2OrderDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2OrderDate.getTime())), null, caseNumber);
        }

        //list2SelectionDueDate
        if(newCaseInformation.FFList2SelectionDueDate == null && oldCaseInformation.FFList2SelectionDueDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2SelectionDueDate.getTime())) + " from Fact Finder List 2 Selection Due Date", null, caseNumber);
        } else if(newCaseInformation.FFList2SelectionDueDate != null && oldCaseInformation.FFList2SelectionDueDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder List 2 Selection Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2SelectionDueDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.concilList1SelectionDueDate != null && oldCaseInformation.FFList2SelectionDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2SelectionDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFList2SelectionDueDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder List 2 Selection Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFList2SelectionDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2SelectionDueDate.getTime())), null, caseNumber);
        }

        //EmployerType
        if(newCaseInformation.FFEmployerType == null && oldCaseInformation.FFEmployerType != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.FFEmployerType + " from Fact Finder Employer Type", null, caseNumber);
        } else if(newCaseInformation.FFEmployerType != null && oldCaseInformation.FFEmployerType == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Employer Type to " + newCaseInformation.FFEmployerType, null, caseNumber);
        } else if(newCaseInformation.FFEmployerType != null && oldCaseInformation.FFEmployerType != null) {
            if(!newCaseInformation.FFEmployerType.equals(oldCaseInformation.FFEmployerType))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Employer Type from " + oldCaseInformation.FFEmployerType + " to " + newCaseInformation.FFEmployerType, null, caseNumber);
        }

        //EmployeeType
        if(newCaseInformation.FFEmployeeType == null && oldCaseInformation.FFEmployeeType != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.FFEmployeeType + " from Fact Finder Employee Type", null, caseNumber);
        } else if(newCaseInformation.FFEmployeeType != null && oldCaseInformation.FFEmployeeType == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Employee Type to " + newCaseInformation.FFEmployeeType, null, caseNumber);
        } else if(newCaseInformation.FFEmployeeType != null && oldCaseInformation.FFEmployeeType != null) {
            if(!newCaseInformation.FFEmployeeType.equals(oldCaseInformation.FFEmployeeType))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Employee Type from " + oldCaseInformation.FFEmployeeType + " to " + newCaseInformation.FFEmployeeType, null, caseNumber);
        }

        //FFReportIssueDate
        if(newCaseInformation.FFReportIssueDate == null && oldCaseInformation.FFReportIssueDate != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFReportIssueDate.getTime())) + " from Fact Finder Report Issue Date", null, caseNumber);
        } else if(newCaseInformation.FFReportIssueDate != null && oldCaseInformation.FFReportIssueDate == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Report Issue Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFReportIssueDate.getTime())), null, caseNumber);
        } else if(newCaseInformation.FFReportIssueDate != null && oldCaseInformation.FFReportIssueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.FFReportIssueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.FFReportIssueDate.getTime()))))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Report Issue Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.FFReportIssueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.FFList2OrderDate.getTime())), null, caseNumber);
        }

        //MediatedSettlement
        if(newCaseInformation.FFMediatedSettlement == false && oldCaseInformation.FFMediatedSettlement != false) {
            Activity.addActivtyNonGlobalCaseNumber("Unset Mediated Settlement", null, caseNumber);
        } else if(newCaseInformation.FFMediatedSettlement != false && oldCaseInformation.FFMediatedSettlement == false) {
            Activity.addActivtyNonGlobalCaseNumber("Set Mediated Settlement", null, caseNumber);
        }

        //AcceptedBy
        if(newCaseInformation.FFAcceptedBy == null && oldCaseInformation.FFAcceptedBy != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.FFAcceptedBy + " from Fact Finder Accepted By", null, caseNumber);
        } else if(newCaseInformation.FFAcceptedBy != null && oldCaseInformation.FFAcceptedBy == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Accepted By to " + newCaseInformation.FFAcceptedBy, null, caseNumber);
        } else if(newCaseInformation.FFAcceptedBy != null && oldCaseInformation.FFAcceptedBy != null) {
            if(!newCaseInformation.FFAcceptedBy.equals(oldCaseInformation.FFAcceptedBy))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Accepted By from " + oldCaseInformation.FFAcceptedBy + " to " + newCaseInformation.FFAcceptedBy, null, caseNumber);
        }

        //DeemedAcceptedBy
        if(newCaseInformation.FFDeemedAcceptedBy == null && oldCaseInformation.FFDeemedAcceptedBy != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.FFDeemedAcceptedBy + " from Fact Finder Deemed Accepted By", null, caseNumber);
        } else if(newCaseInformation.FFDeemedAcceptedBy != null && oldCaseInformation.FFDeemedAcceptedBy == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Deemed Accepted By to " + newCaseInformation.FFDeemedAcceptedBy, null, caseNumber);
        } else if(newCaseInformation.FFDeemedAcceptedBy != null && oldCaseInformation.FFDeemedAcceptedBy != null) {
            if(!newCaseInformation.FFDeemedAcceptedBy.equals(oldCaseInformation.FFDeemedAcceptedBy))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Deemed Accepted By from " + oldCaseInformation.FFDeemedAcceptedBy + " to " + newCaseInformation.FFDeemedAcceptedBy, null, caseNumber);
        }

        //RejectedBy
        if(newCaseInformation.FFRejectedBy == null && oldCaseInformation.FFRejectedBy != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.FFRejectedBy + " from Fact Finder Rejected By", null, caseNumber);
        } else if(newCaseInformation.FFRejectedBy != null && oldCaseInformation.FFRejectedBy == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Rejected By to " + newCaseInformation.FFRejectedBy, null, caseNumber);
        } else if(newCaseInformation.FFRejectedBy != null && oldCaseInformation.FFRejectedBy != null) {
            if(!newCaseInformation.FFRejectedBy.equals(oldCaseInformation.FFRejectedBy))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Rejected By from " + oldCaseInformation.FFRejectedBy + " to " + newCaseInformation.FFRejectedBy, null, caseNumber);
        }

        //OverallResult
        if(newCaseInformation.FFOverallResult == null && oldCaseInformation.FFOverallResult != null) {
            Activity.addActivtyNonGlobalCaseNumber("Removed " + oldCaseInformation.FFOverallResult + " from Fact Finder Overall Result", null, caseNumber);
        } else if(newCaseInformation.FFOverallResult != null && oldCaseInformation.FFOverallResult == null) {
            Activity.addActivtyNonGlobalCaseNumber("Set Fact Finder Overall Result to " + newCaseInformation.FFOverallResult, null, caseNumber);
        } else if(newCaseInformation.FFOverallResult != null && oldCaseInformation.FFOverallResult != null) {
            if(!newCaseInformation.FFOverallResult.equals(oldCaseInformation.FFOverallResult))
                Activity.addActivtyNonGlobalCaseNumber("Changed Fact Finder Overall Result from " + oldCaseInformation.FFOverallResult + " to " + newCaseInformation.FFOverallResult, null, caseNumber);
        }

        //Note
        if(newCaseInformation.FFNote == null && oldCaseInformation.FFNote != null) {
            Activity.addActivtyNonGlobalCaseNumber("Updated Fact Finder Notes", null, caseNumber);
        } else if(newCaseInformation.FFNote != null && oldCaseInformation.FFNote == null) {
            Activity.addActivtyNonGlobalCaseNumber("Updated Fact Finder Notes", null, caseNumber);
        } else if(newCaseInformation.FFNote != null && oldCaseInformation.FFNote != null) {
            if(!newCaseInformation.FFNote.equals(oldCaseInformation.FFNote))
                Activity.addActivtyNonGlobalCaseNumber("Updated Fact Finder Notes", null, caseNumber);
        }
    }
    
    private static void detailedStatusSaveInformation(MEDCase newCaseInformation, MEDCase oldCaseInformation) {

        //file date - T
        if(newCaseInformation.fileDate == null && oldCaseInformation.fileDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " from File Date", null);
        } else if(newCaseInformation.fileDate != null && oldCaseInformation.fileDate == null) {
            Activity.addActivty("Set File Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
        } else if(newCaseInformation.fileDate != null && oldCaseInformation.fileDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime()))))
                Activity.addActivty("Changed File Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
        }

        //employerIDNumber - T
        if(newCaseInformation.employerIDNumber == null && oldCaseInformation.employerIDNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.employerIDNumber + " from Employer ID Number", null);
        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber == null) {
            Activity.addActivty("Set Employer ID Number to " + newCaseInformation.employerIDNumber, null);
        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber != null) {
            if(!newCaseInformation.employerIDNumber.equals(oldCaseInformation.employerIDNumber))
                Activity.addActivty("Changed Employer ID Number from " + oldCaseInformation.employerIDNumber + " to " + newCaseInformation.employerIDNumber, null);
        }

        //BUNNumber - T
        if(newCaseInformation.bargainingUnitNumber == null && oldCaseInformation.bargainingUnitNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.bargainingUnitNumber + " from Bargaining Unit Number", null);
        } else if(newCaseInformation.bargainingUnitNumber != null && oldCaseInformation.bargainingUnitNumber == null) {
            Activity.addActivty("Set Bargaining Unit Number to " + newCaseInformation.bargainingUnitNumber, null);
        } else if(newCaseInformation.bargainingUnitNumber != null && oldCaseInformation.bargainingUnitNumber != null) {
            if(!newCaseInformation.bargainingUnitNumber.equals(oldCaseInformation.bargainingUnitNumber))
                Activity.addActivty("Changed Bargaining Unit Number from " + oldCaseInformation.bargainingUnitNumber + " to " + newCaseInformation.bargainingUnitNumber, null);
        }

        //approxnumberofemployees - T
        if(newCaseInformation.approxNumberOfEmployees == null && oldCaseInformation.approxNumberOfEmployees != null) {
            Activity.addActivty("Removed " + oldCaseInformation.approxNumberOfEmployees + " from Approximate Number of Employees", null);
        } else if(newCaseInformation.approxNumberOfEmployees != null && oldCaseInformation.approxNumberOfEmployees == null) {
            Activity.addActivty("Set Approximate Number of Employees to " + newCaseInformation.approxNumberOfEmployees, null);
        } else if(newCaseInformation.approxNumberOfEmployees != null && oldCaseInformation.approxNumberOfEmployees != null) {
            if(!newCaseInformation.approxNumberOfEmployees.equals(oldCaseInformation.approxNumberOfEmployees))
                Activity.addActivty("Changed Approximate Number of Employees from " + oldCaseInformation.approxNumberOfEmployees + " to " + newCaseInformation.approxNumberOfEmployees, null);
        }

        ///duplicatecasenumber
        if(newCaseInformation.duplicateCaseNumber == null && oldCaseInformation.duplicateCaseNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.duplicateCaseNumber + " from Duplicate Case Number", null);
        } else if(newCaseInformation.duplicateCaseNumber != null && oldCaseInformation.duplicateCaseNumber == null) {
            Activity.addActivty("Set Duplicate Case Number to " + newCaseInformation.duplicateCaseNumber, null);
        } else if(newCaseInformation.duplicateCaseNumber != null && oldCaseInformation.duplicateCaseNumber != null) {
            if(!newCaseInformation.duplicateCaseNumber.equals(oldCaseInformation.duplicateCaseNumber))
                Activity.addActivty("Changed Duplicate Case Number from " + oldCaseInformation.duplicateCaseNumber + " to " + newCaseInformation.duplicateCaseNumber, null);
        }

        //related case number - T
        if(newCaseInformation.relatedCaseNumber == null && oldCaseInformation.relatedCaseNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.relatedCaseNumber + " from Related Case Number", null);
        } else if(newCaseInformation.relatedCaseNumber != null && oldCaseInformation.relatedCaseNumber == null) {
            Activity.addActivty("Set Related Case Number to " + newCaseInformation.relatedCaseNumber, null);
        } else if(newCaseInformation.relatedCaseNumber != null && oldCaseInformation.relatedCaseNumber != null) {
            if(!newCaseInformation.relatedCaseNumber.equals(oldCaseInformation.relatedCaseNumber))
                Activity.addActivty("Changed Related Case Number from " + oldCaseInformation.relatedCaseNumber + " to " + newCaseInformation.relatedCaseNumber, null);
        }

        //negotiation type - T
        if(newCaseInformation.negotiationType == null && oldCaseInformation.negotiationType != null) {
            Activity.addActivty("Removed " + oldCaseInformation.negotiationType + " from Negotiation Type", null);
        } else if(newCaseInformation.negotiationType != null && oldCaseInformation.negotiationType == null) {
            Activity.addActivty("Set Negotiation Type to " + newCaseInformation.negotiationType, null);
        } else if(newCaseInformation.negotiationType != null && oldCaseInformation.negotiationType != null) {
            if(!newCaseInformation.negotiationType.equals(oldCaseInformation.negotiationType))
                Activity.addActivty("Changed Negotiation Type from " + oldCaseInformation.negotiationType + " to " + newCaseInformation.negotiationType, null);
        }

        //expirationdate - T
        if(newCaseInformation.expirationDate == null && oldCaseInformation.expirationDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.expirationDate.getTime())) + " from Expiration Date", null);
        } else if(newCaseInformation.expirationDate != null && oldCaseInformation.expirationDate == null) {
            Activity.addActivty("Set Expiration Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.expirationDate.getTime())), null);
        } else if(newCaseInformation.expirationDate != null && oldCaseInformation.expirationDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.expirationDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.expirationDate.getTime()))))
                Activity.addActivty("Changed Expiration Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.expirationDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.expirationDate.getTime())), null);
        }

        //ntn filed by - T
        if(newCaseInformation.NTNFiledBy == null && oldCaseInformation.NTNFiledBy != null) {
            Activity.addActivty("Removed " + oldCaseInformation.NTNFiledBy + " from NTN Filed By", null);
        } else if(newCaseInformation.NTNFiledBy != null && oldCaseInformation.NTNFiledBy == null) {
            Activity.addActivty("Set NTN Filed By to " + newCaseInformation.NTNFiledBy, null);
        } else if(newCaseInformation.NTNFiledBy != null && oldCaseInformation.NTNFiledBy != null) {
            if(!newCaseInformation.NTNFiledBy.equals(oldCaseInformation.NTNFiledBy))
                Activity.addActivty("Changed NTN Filed By from " + oldCaseInformation.NTNFiledBy + " to " + newCaseInformation.NTNFiledBy, null);
        }

        //negoitation period - T
        if(newCaseInformation.negotiationPeriod == null && oldCaseInformation.negotiationPeriod != null) {
            Activity.addActivty("Removed " + oldCaseInformation.negotiationPeriod + " from Negotiation Period", null);
        } else if(newCaseInformation.negotiationPeriod != null && oldCaseInformation.negotiationPeriod == null) {
            Activity.addActivty("Set Negotiation Period to " + newCaseInformation.negotiationPeriod, null);
        } else if(newCaseInformation.negotiationPeriod != null && oldCaseInformation.negotiationPeriod != null) {
            if(!newCaseInformation.negotiationPeriod.equals(oldCaseInformation.negotiationPeriod))
                Activity.addActivty("Changed Negotiation Period from " + oldCaseInformation.negotiationPeriod + " to " + newCaseInformation.negotiationPeriod, null);
        }

        //multi unit bargaining - T
        if(newCaseInformation.multiunitBargainingRequested == false && oldCaseInformation.multiunitBargainingRequested != false) {
            Activity.addActivty("Unset Multi Unit Bargaining Requested", null);
        } else if(newCaseInformation.multiunitBargainingRequested != false && oldCaseInformation.multiunitBargainingRequested == false) {
            Activity.addActivty("Set Multi Unit Bargaining Requested", null);
        }

        //mediatior appointed date - T
        if(newCaseInformation.mediatorAppointedDate == null && oldCaseInformation.mediatorAppointedDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.mediatorAppointedDate.getTime())) + " from Mediator Appointed Date", null);
        } else if(newCaseInformation.mediatorAppointedDate != null && oldCaseInformation.mediatorAppointedDate == null) {
            Activity.addActivty("Set Mediator Appointed Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.mediatorAppointedDate.getTime())), null);
        } else if(newCaseInformation.mediatorAppointedDate != null && oldCaseInformation.mediatorAppointedDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.mediatorAppointedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.mediatorAppointedDate.getTime()))))
                Activity.addActivty("Changed Mediator Appointed Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.mediatorAppointedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.mediatorAppointedDate.getTime())), null);
        }

        //mediator replacement - T
        if(newCaseInformation.mediatorReplacement == false && oldCaseInformation.mediatorReplacement != false) {
            Activity.addActivty("Unset Mediator Replacement", null);
        } else if(newCaseInformation.mediatorReplacement != false && oldCaseInformation.mediatorReplacement == false) {
            Activity.addActivty("Set Mediator Replacement", null);
        }

        //state mediator appointed
        if(newCaseInformation.stateMediatorAppointedID == null && oldCaseInformation.stateMediatorAppointedID != null) {
            Activity.addActivty("Removed " + Mediator.getMediatorNameByID(oldCaseInformation.stateMediatorAppointedID) + " from State Mediator Appointed", null);
        } else if(newCaseInformation.stateMediatorAppointedID != null && oldCaseInformation.stateMediatorAppointedID == null) {
            Activity.addActivty("Set State Mediator Appointed to " + Mediator.getMediatorNameByID(newCaseInformation.stateMediatorAppointedID), null);
        } else if(newCaseInformation.stateMediatorAppointedID != null && oldCaseInformation.stateMediatorAppointedID != null) {
            if(!newCaseInformation.stateMediatorAppointedID.equals(oldCaseInformation.stateMediatorAppointedID))
                Activity.addActivty("Changed State Mediator Appointed from " + Mediator.getMediatorNameByID(oldCaseInformation.stateMediatorAppointedID) + " to " + Mediator.getMediatorNameByID(newCaseInformation.stateMediatorAppointedID), null);
        }

        //fmcs mediator appointed
        if(newCaseInformation.FMCSMediatorAppointedID == null && oldCaseInformation.FMCSMediatorAppointedID != null) {
            Activity.addActivty("Removed " + Mediator.getMediatorNameByID(oldCaseInformation.FMCSMediatorAppointedID) + " from FMCS Mediator Appointed", null);
        } else if(newCaseInformation.FMCSMediatorAppointedID != null && oldCaseInformation.FMCSMediatorAppointedID == null) {
            Activity.addActivty("Set FMCS Mediator Appointed to " + Mediator.getMediatorNameByID(newCaseInformation.FMCSMediatorAppointedID), null);
        } else if(newCaseInformation.FMCSMediatorAppointedID != null && oldCaseInformation.FMCSMediatorAppointedID != null) {
            if(!newCaseInformation.FMCSMediatorAppointedID.equals(oldCaseInformation.FMCSMediatorAppointedID))
                Activity.addActivty("Changed FMCS Mediator Appointed from " + Mediator.getMediatorNameByID(oldCaseInformation.FMCSMediatorAppointedID) + " to " + Mediator.getMediatorNameByID(newCaseInformation.FMCSMediatorAppointedID), null);
        }

        //settlement date - T
        if(newCaseInformation.settlementDate == null && oldCaseInformation.settlementDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.settlementDate.getTime())) + " from Settlement Date", null);
        } else if(newCaseInformation.settlementDate != null && oldCaseInformation.settlementDate == null) {
            Activity.addActivty("Set Settlement Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.settlementDate.getTime())), null);
        } else if(newCaseInformation.settlementDate != null && oldCaseInformation.settlementDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.settlementDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.settlementDate.getTime()))))
                Activity.addActivty("Changed Settlement Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.settlementDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.settlementDate.getTime())), null);
        }

        //status - T
        if(newCaseInformation.caseStatus == null && oldCaseInformation.caseStatus != null) {
            Activity.addActivty("Removed " + oldCaseInformation.caseStatus + " from Status", null);
        } else if(newCaseInformation.caseStatus != null && oldCaseInformation.caseStatus == null) {
            Activity.addActivty("Set Status to " + newCaseInformation.caseStatus, null);
        } else if(newCaseInformation.caseStatus != null && oldCaseInformation.caseStatus != null) {
            if(!newCaseInformation.caseStatus.equals(oldCaseInformation.caseStatus))
                Activity.addActivty("Changed Status from " + oldCaseInformation.caseStatus + " to " + newCaseInformation.caseStatus, null);
        }

        //send to board to close - T
        if(newCaseInformation.sendToBoardToClose == false && oldCaseInformation.sendToBoardToClose != false) {
            Activity.addActivty("Unset Send To Board To Close", null);
        } else if(newCaseInformation.sendToBoardToClose != false && oldCaseInformation.sendToBoardToClose == false) {
            Activity.addActivty("Set Send To Board To Close", null);
        }

        //board final date - T
        if(newCaseInformation.boardFinalDate == null && oldCaseInformation.boardFinalDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardFinalDate.getTime())) + " from Board Final Date", null);
        } else if(newCaseInformation.boardFinalDate != null && oldCaseInformation.boardFinalDate == null) {
            Activity.addActivty("Set Board Final Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardFinalDate.getTime())), null);
        } else if(newCaseInformation.boardFinalDate != null && oldCaseInformation.boardFinalDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.boardFinalDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.boardFinalDate.getTime()))))
                Activity.addActivty("Changed Board Final Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardFinalDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardFinalDate.getTime())), null);
        }

        //late filing - T
        if(newCaseInformation.lateFiling == false && oldCaseInformation.lateFiling != false) {
            Activity.addActivty("Unset Late Filing", null);
        } else if(newCaseInformation.lateFiling != false && oldCaseInformation.lateFiling == false) {
            Activity.addActivty("Set Late Filing", null);
        }

        //impasse - T
        if(newCaseInformation.impasse == false && oldCaseInformation.impasse != false) {
            Activity.addActivty("Unset Impasse", null);
        } else if(newCaseInformation.impasse != false && oldCaseInformation.impasse == false) {
            Activity.addActivty("Set Impasse", null);
        }

        //settled - T
        if(newCaseInformation.settled == false && oldCaseInformation.settled != false) {
            Activity.addActivty("Unset Settled", null);
        } else if(newCaseInformation.settled != false && oldCaseInformation.settled == false) {
            Activity.addActivty("Set Settled", null);
        }

        //ta - T
        if(newCaseInformation.TA == false && oldCaseInformation.TA != false) {
            Activity.addActivty("Unset TA", null);
        } else if(newCaseInformation.TA != false && oldCaseInformation.TA == false) {
            Activity.addActivty("Set TA", null);
        }

        //mad - T
        if(newCaseInformation.MAD == false && oldCaseInformation.MAD != false) {
            Activity.addActivty("Unset MAD", null);
        } else if(newCaseInformation.MAD != false && oldCaseInformation.MAD == false) {
            Activity.addActivty("Set MAD", null);
        }

        //withdrawl - T
        if(newCaseInformation.withdrawl == false && oldCaseInformation.withdrawl != false) {
            Activity.addActivty("Unset Withdrawl", null);
        } else if(newCaseInformation.withdrawl != false && oldCaseInformation.withdrawl == false) {
            Activity.addActivty("Set Withdrawl", null);
        }

        //motion - T
        if(newCaseInformation.motion == false && oldCaseInformation.motion != false) {
            Activity.addActivty("Unset Motion", null);
        } else if(newCaseInformation.motion != false && oldCaseInformation.motion == false) {
            Activity.addActivty("Set Motion", null);
        }

        //dismissed - T
        if(newCaseInformation.dismissed == false && oldCaseInformation.dismissed != false) {
            Activity.addActivty("Unset Dismissed", null);
        } else if(newCaseInformation.dismissed != false && oldCaseInformation.dismissed == false) {
            Activity.addActivty("Set Dismissed", null);
        }
    }

    public static List<String> loadRelatedCases() {
        List<String> caseNumberList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadRelatedCases();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseNumberList;
    }

    private static void detailedStrikeSaveInformation(MEDCase newCaseInformation, MEDCase oldCaseInformation) {

        // strike file date - T
        if(newCaseInformation.strikeFileDate == null && oldCaseInformation.strikeFileDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.strikeFileDate.getTime())) + " from Strike File Date", null);
        } else if(newCaseInformation.strikeFileDate != null && oldCaseInformation.strikeFileDate == null) {
            Activity.addActivty("Set Strike File Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.strikeFileDate.getTime())), null);
        } else if(newCaseInformation.strikeFileDate != null && oldCaseInformation.strikeFileDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.strikeFileDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.strikeFileDate.getTime()))))
                Activity.addActivty("Changed Strike File Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.strikeFileDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.strikeFileDate.getTime())), null);
        }

        //related Case Number
        if(newCaseInformation.relatedCaseNumber == null && oldCaseInformation.relatedCaseNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.relatedCaseNumber + " from Related Case Number", null);
        } else if(newCaseInformation.relatedCaseNumber != null && oldCaseInformation.relatedCaseNumber == null) {
            Activity.addActivty("Set Related Case Number to " + newCaseInformation.relatedCaseNumber, null);
        } else if(newCaseInformation.relatedCaseNumber != null && oldCaseInformation.relatedCaseNumber != null) {
            if(!newCaseInformation.relatedCaseNumber.equals(oldCaseInformation.relatedCaseNumber))
                Activity.addActivty("Changed Related Case Number from " + oldCaseInformation.relatedCaseNumber + " to " + newCaseInformation.relatedCaseNumber, null);
        }

        //description - T
        if(newCaseInformation.description == null && oldCaseInformation.description != null) {
            Activity.addActivty("Removed " + oldCaseInformation.description + " from Unit Description", null);
        } else if(newCaseInformation.description != null && oldCaseInformation.description == null) {
            Activity.addActivty("Set Unit Description to " + newCaseInformation.description, null);
        } else if(newCaseInformation.description != null && oldCaseInformation.description != null) {
            if(!newCaseInformation.description.equals(oldCaseInformation.description))
                Activity.addActivty("Changed Unit Description from " + oldCaseInformation.description + " to " + newCaseInformation.description, null);
        }

        //unitsize - T
        if(newCaseInformation.unitSize == null && oldCaseInformation.unitSize != null) {
            Activity.addActivty("Removed " + oldCaseInformation.unitSize + " from Unit Size", null);
        } else if(newCaseInformation.unitSize != null && oldCaseInformation.unitSize == null) {
            Activity.addActivty("Set Unit Size to " + newCaseInformation.unitSize, null);
        } else if(newCaseInformation.unitSize != null && oldCaseInformation.unitSize != null) {
            if(!newCaseInformation.unitSize.equals(oldCaseInformation.unitSize))
                Activity.addActivty("Changed Unit Size from " + oldCaseInformation.unitSize + " to " + newCaseInformation.unitSize, null);
        }

        //unautorized strike
        if(newCaseInformation.unauthorizedStrike == false && oldCaseInformation.unauthorizedStrike != false) {
            Activity.addActivty("Unset Unauthorized Strike", null);
        } else if(newCaseInformation.unauthorizedStrike != false && oldCaseInformation.unauthorizedStrike == false) {
            Activity.addActivty("Set Unauthorized Strike", null);
        }

        //notice of intent to strike only
        if(newCaseInformation.noticeOfIntentToStrikeOnly == false && oldCaseInformation.noticeOfIntentToStrikeOnly != false) {
            Activity.addActivty("Unset Notice of Intent to Strike Only", null);
        } else if(newCaseInformation.noticeOfIntentToStrikeOnly != false && oldCaseInformation.noticeOfIntentToStrikeOnly == false) {
            Activity.addActivty("Set Notice of Intent to Strike Only", null);
        }

        //intended strike date
        if(newCaseInformation.intendedDateStrike == null && oldCaseInformation.intendedDateStrike != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.intendedDateStrike.getTime())) + " from Intended Strike Date", null);
        } else if(newCaseInformation.intendedDateStrike != null && oldCaseInformation.intendedDateStrike == null) {
            Activity.addActivty("Set Intended Strike Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.intendedDateStrike.getTime())), null);
        } else if(newCaseInformation.intendedDateStrike != null && oldCaseInformation.intendedDateStrike != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.intendedDateStrike.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.intendedDateStrike.getTime()))))
                Activity.addActivty("Changed Intended Strike Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.intendedDateStrike.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.intendedDateStrike.getTime())), null);
        }

        //notice of intent to picket only
        if(newCaseInformation.noticeOfIntentToPicketOnly == false && oldCaseInformation.noticeOfIntentToPicketOnly != false) {
            Activity.addActivty("Unset Notice of Intent to Picket Only", null);
        } else if(newCaseInformation.noticeOfIntentToPicketOnly != false && oldCaseInformation.noticeOfIntentToPicketOnly == false) {
            Activity.addActivty("Set Notice of Intent to Picket Only", null);
        }

        //intended picket date
        if(newCaseInformation.intendedDatePicket == null && oldCaseInformation.intendedDatePicket != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.intendedDatePicket.getTime())) + " from Intended Picket Date", null);
        } else if(newCaseInformation.intendedDatePicket != null && oldCaseInformation.intendedDatePicket == null) {
            Activity.addActivty("Set Intended Picket Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.intendedDatePicket.getTime())), null);
        } else if(newCaseInformation.intendedDatePicket != null && oldCaseInformation.intendedDatePicket != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.intendedDatePicket.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.intendedDatePicket.getTime()))))
                Activity.addActivty("Changed Intended Picket Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.intendedDatePicket.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.intendedDatePicket.getTime())), null);
        }

        //infomrational
        if(newCaseInformation.informational == false && oldCaseInformation.informational != false) {
            Activity.addActivty("Unset Informational", null);
        } else if(newCaseInformation.informational != false && oldCaseInformation.informational == false) {
            Activity.addActivty("Set Informational", null);
        }

        //notice of intent to strike and picket
        if(newCaseInformation.noticeOfIntentToStrikeAndPicket == false && oldCaseInformation.noticeOfIntentToStrikeAndPicket != false) {
            Activity.addActivty("Unset Notice of Intent to Strike and Picket", null);
        } else if(newCaseInformation.noticeOfIntentToStrikeAndPicket != false && oldCaseInformation.noticeOfIntentToStrikeAndPicket == false) {
            Activity.addActivty("Set Notice of Intent to Strike and Picket", null);
        }

        //strike occured
        if(newCaseInformation.strikeOccured == null && oldCaseInformation.strikeOccured != null) {
            Activity.addActivty("Removed " + oldCaseInformation.strikeOccured + " from Strike Occured", null);
        } else if(newCaseInformation.strikeOccured != null && oldCaseInformation.strikeOccured == null) {
            Activity.addActivty("Set Strike Occured to " + newCaseInformation.strikeOccured, null);
        } else if(newCaseInformation.strikeOccured != null && oldCaseInformation.strikeOccured != null) {
            if(!newCaseInformation.strikeOccured.equals(oldCaseInformation.strikeOccured))
                Activity.addActivty("Changed Strike Occured from " + oldCaseInformation.strikeOccured + " to " + newCaseInformation.strikeOccured, null);
        }

        //strike status
        if(newCaseInformation.strikeStatus == null && oldCaseInformation.strikeStatus != null) {
            Activity.addActivty("Removed " + oldCaseInformation.strikeStatus + " from Strike Status", null);
        } else if(newCaseInformation.strikeStatus != null && oldCaseInformation.strikeStatus == null) {
            Activity.addActivty("Set Strike Status to " + newCaseInformation.strikeStatus, null);
        } else if(newCaseInformation.strikeStatus != null && oldCaseInformation.strikeStatus != null) {
            if(!newCaseInformation.strikeStatus.equals(oldCaseInformation.strikeStatus))
                Activity.addActivty("Changed Strike Status from " + oldCaseInformation.strikeStatus + " to " + newCaseInformation.strikeStatus, null);
        }

        //strike began
        if(newCaseInformation.strikeBegan == null && oldCaseInformation.strikeBegan != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.strikeBegan.getTime())) + " from Strike Began", null);
        } else if(newCaseInformation.strikeBegan != null && oldCaseInformation.strikeBegan == null) {
            Activity.addActivty("Set Strike Began to " + Global.mmddyyyy.format(new Date(newCaseInformation.strikeBegan.getTime())), null);
        } else if(newCaseInformation.strikeBegan != null && oldCaseInformation.strikeBegan != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.strikeBegan.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.strikeBegan.getTime()))))
                Activity.addActivty("Changed Strike Began from " + Global.mmddyyyy.format(new Date(oldCaseInformation.strikeBegan.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.strikeBegan.getTime())), null);
        }

        //strike ended
        if(newCaseInformation.strikeEnded == null && oldCaseInformation.strikeEnded != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.strikeEnded.getTime())) + " from Strike Ended", null);
        } else if(newCaseInformation.strikeEnded != null && oldCaseInformation.strikeEnded == null) {
            Activity.addActivty("Set Strike Ended to " + Global.mmddyyyy.format(new Date(newCaseInformation.strikeEnded.getTime())), null);
        } else if(newCaseInformation.strikeEnded != null && oldCaseInformation.strikeEnded != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.strikeEnded.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.strikeEnded.getTime()))))
                Activity.addActivty("Changed Strike Ended from " + Global.mmddyyyy.format(new Date(oldCaseInformation.strikeEnded.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.strikeEnded.getTime())), null);
        }

        //mediator appointed id
        if(newCaseInformation.strikeMediatorAppointedID == null && oldCaseInformation.strikeMediatorAppointedID != null) {
            Activity.addActivty("Removed " + Mediator.getMediatorNameByID(oldCaseInformation.strikeMediatorAppointedID) + " from Strike Mediator Appointed", null);
        } else if(newCaseInformation.strikeMediatorAppointedID != null && oldCaseInformation.strikeMediatorAppointedID == null) {
            Activity.addActivty("Set Strike Mediator Appointed to " + Mediator.getMediatorNameByID(newCaseInformation.strikeMediatorAppointedID), null);
        } else if(newCaseInformation.strikeMediatorAppointedID != null && oldCaseInformation.strikeMediatorAppointedID != null) {
            if(!newCaseInformation.strikeMediatorAppointedID.equals(oldCaseInformation.strikeMediatorAppointedID))
                Activity.addActivty("Changed Strike Mediator Appointed from " + Mediator.getMediatorNameByID(oldCaseInformation.strikeMediatorAppointedID) + " to " + Mediator.getMediatorNameByID(newCaseInformation.strikeMediatorAppointedID), null);
        }

        //strike notes
        if(newCaseInformation.strikeNotes == null && oldCaseInformation.strikeNotes != null) {
            Activity.addActivty("Updated Strike Notes", null);
        } else if(newCaseInformation.strikeNotes != null && oldCaseInformation.strikeNotes == null) {
            Activity.addActivty("Updated Strike Notes", null);
        } else if(newCaseInformation.strikeNotes != null && oldCaseInformation.strikeNotes != null) {
            if(!newCaseInformation.strikeNotes.equals(oldCaseInformation.strikeNotes))
                Activity.addActivty("Updated Strike Notes", null);
        }

    }

    public static boolean checkIfFristCaseOfMonth(String year, String type, String month) {
        boolean firstCase = false;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                checkIfFristCaseOfMonth(year, type, month);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return firstCase;
    }

    public static List<String> getSettleCaseYears() {
        List<String> yearList = new ArrayList<>();

        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT DISTINCT caseYear FROM medcase WHERE caseStatus = 'open' "
                    + "AND settlementDate IS NULL ORDER BY caseYear DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();

            while(caseNumberRS.next()) {
                yearList.add(caseNumberRS.getString("caseYear"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getSettleCaseYears();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return yearList;
    }

    public static List<String> getSettleCaseMonths(String caseYear) {
        List<String> monthList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT DISTINCT caseMonth FROM medcase WHERE caseStatus = 'open' "
                    + "AND settlementDate IS NULL AND caseYear = ? ORDER BY caseMonth ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            ResultSet caseNumberRS = preparedStatement.executeQuery();

            while(caseNumberRS.next()) {
                monthList.add(caseNumberRS.getString("caseMonth"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getSettleCaseMonths(caseYear);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return monthList;
    }

    public static void updateSettledCases(String caseNumber, java.sql.Date settleDate) {
        NumberFormatService num = NumberFormatService.parseFullCaseNumberNoNGlobal(caseNumber);

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set "
                    + " settlementDate = ? "
                    + " where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, settleDate);
            preparedStatement.setString(2, num.caseYear);
            preparedStatement.setString(3, num.caseType);
            preparedStatement.setString(4, num.caseMonth);
            preparedStatement.setString(5, num.caseNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateSettledCases(caseNumber, settleDate);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateSendToBoardCases(String caseNumber) {
        NumberFormatService num = NumberFormatService.parseFullCaseNumberNoNGlobal(caseNumber);

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set "
                    + "SendToBoardToClose = ? "
                    + "WHERE caseYear = ? "
                    + "AND caseType = ? "
                    + "AND caseMonth = ? "
                    + "AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, num.caseYear);
            preparedStatement.setString(3, num.caseType);
            preparedStatement.setString(4, num.caseMonth);
            preparedStatement.setString(5, num.caseNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateSendToBoardCases(caseNumber);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

     public static List<MEDCase> getSettleList(String caseYear, String caseMonth) {
        List<MEDCase> medcaseList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT "
                    + "MEDCase.caseYear AS CaseYear, "
                    + "MEDCase.caseType AS CaseType, "
                    + "MEDCase.caseMonth AS CaseMonth, "
                    + "MEDCase.caseNumber AS CaseNumber,"

                    + "EmployerName = STUFF((SELECT ';  ' + CASE WHEN (CaseParty.\"firstName\" IS NULL AND CaseParty.\"lastName\" IS NULL) "
                    + "THEN (CaseParty.\"companyName\") ELSE ((ISNULL(CaseParty.\"firstName\" + ' ', '')) + (ISNULL(CaseParty.\"middleInitial\" + ' ', '')) "
                    + "+ (ISNULL(CaseParty.\"lastName\" + ' ', '')) + (ISNULL(CaseParty.\"suffix\" + ' ', '')) + (ISNULL(CaseParty.\"nameTitle\" + ' ', '')) "
                    + "+ (ISNULL(CaseParty.\"jobTitle\", ''))) END AS EmployerName FROM caseparty "
                    + "WHERE CaseParty.caseYear = medcase.caseYear AND CaseParty.caseType = medcase.caseType "
                    + "AND CaseParty.caseMonth = medcase.caseMonth AND CaseParty.caseNumber = medcase.caseNumber "
                    + "AND CaseParty.caseRelation = 'Employer' FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, ''), "

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getSettleList(caseYear, caseMonth);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return medcaseList;
    }

     public static List<MEDCase> getSendToBoardList(Date startDate, Date endDate) {
        List<MEDCase> medcaseList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT "
                    + "MEDCase.caseYear AS CaseYear, "
                    + "MEDCase.caseType AS CaseType, "
                    + "MEDCase.caseMonth AS CaseMonth, "
                    + "MEDCase.caseNumber AS CaseNumber,"

                    + "EmployerName = STUFF((SELECT ';  ' + CASE WHEN (CaseParty.\"firstName\" IS NULL AND CaseParty.\"lastName\" IS NULL) "
                    + "THEN (CaseParty.\"companyName\") ELSE ((ISNULL(CaseParty.\"firstName\" + ' ', '')) + (ISNULL(CaseParty.\"middleInitial\" + ' ', '')) "
                    + "+ (ISNULL(CaseParty.\"lastName\" + ' ', '')) + (ISNULL(CaseParty.\"suffix\" + ' ', '')) + (ISNULL(CaseParty.\"nameTitle\" + ' ', '')) "
                    + "+ (ISNULL(CaseParty.\"jobTitle\", ''))) END AS EmployerName FROM caseparty "
                    + "WHERE CaseParty.caseYear = medcase.caseYear AND CaseParty.caseType = medcase.caseType "
                    + "AND CaseParty.caseMonth = medcase.caseMonth AND CaseParty.caseNumber = medcase.caseNumber "
                    + "AND CaseParty.caseRelation = 'Employer' FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, ''), "

                    + "MEDCase.fileDate AS FileDate "
                    + "FROM  MEDCase "
                    + "WHERE MEDCase.SendToBoardToClose != 1 "
                    + "AND (MEDCase.SettlementDate >= CAST(? AS DATE) AND MEDCase.SettlementDate <= CAST(? AS DATE)) "
                    + "ORDER BY MEDCase.caseYear, MEDCase.caseMonth, MEDCase.caseNumber";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(2, new java.sql.Date(endDate.getTime()));
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getSendToBoardList(startDate, endDate);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return medcaseList;
    }

     public static List<MEDCase> getCloseList() {
        List<MEDCase> medcaseList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT "
                    + "MEDCase.caseYear AS CaseYear, "
                    + "MEDCase.caseType AS CaseType, "
                    + "MEDCase.caseMonth AS CaseMonth, "
                    + "MEDCase.caseNumber AS CaseNumber,"

                    + "EmployerName = STUFF((SELECT ';  ' + CASE WHEN (CaseParty.\"firstName\" IS NULL AND CaseParty.\"lastName\" IS NULL) "
                    + "THEN (CaseParty.\"companyName\") ELSE ((ISNULL(CaseParty.\"firstName\" + ' ', '')) + (ISNULL(CaseParty.\"middleInitial\" + ' ', '')) "
                    + "+ (ISNULL(CaseParty.\"lastName\" + ' ', '')) + (ISNULL(CaseParty.\"suffix\" + ' ', '')) + (ISNULL(CaseParty.\"nameTitle\" + ' ', '')) "
                    + "+ (ISNULL(CaseParty.\"jobTitle\", ''))) END AS EmployerName FROM caseparty "
                    + "WHERE CaseParty.caseYear = medcase.caseYear AND CaseParty.caseType = medcase.caseType "
                    + "AND CaseParty.caseMonth = medcase.caseMonth AND CaseParty.caseNumber = medcase.caseNumber "
                    + "AND CaseParty.caseRelation = 'Employer' FOR XML PATH(''), TYPE).value('.', 'NVARCHAR(MAX)'), 1, 1, ''), "

                    + "MEDCase.fileDate AS FileDate "
                    + "FROM  MEDCase "
                    + "WHERE MEDCase.SendToBoardToClose = 1 "
                    + "AND MEDCase.caseStatus = 'Open' "
                    + "ORDER BY MEDCase.caseYear, MEDCase.caseMonth, MEDCase.caseNumber";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getCloseList();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return medcaseList;
    }

     public static MEDCase loadEntireCaseInformation() {
        MEDCase med = null;

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM MEDCase"
                    + " WHERE caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet rs = preparedStatement.executeQuery();

            if(rs.next()) {
                med = new MEDCase();
                med.id = rs.getInt("id");
                med.active = rs.getBoolean("active");
                med.caseYear = rs.getString("caseYear");
                med.caseType = rs.getString("caseType");
                med.caseMonth = rs.getString("caseMonth");
                med.caseNumber = rs.getString("caseNumber");
                med.note = rs.getString("note") == null ? "" : rs.getString("note");
                med.fileDate = rs.getTimestamp("fileDate");
                med.concilList1OrderDate = rs.getTimestamp("concilList1OrderDate");
                med.concilList1SelectionDueDate = rs.getTimestamp("concilList1SelectionDueDate");
                med.concilList1Name1 = rs.getString("concilList1Name1") == null ? "" : rs.getString("concilList1Name1");
                med.concilList1Name2 = rs.getString("concilList1Name2") == null ? "" : rs.getString("concilList1Name2");
                med.concilList1Name3 = rs.getString("concilList1Name3") == null ? "" : rs.getString("concilList1Name3");
                med.concilList1Name4 = rs.getString("concilList1Name4") == null ? "" : rs.getString("concilList1Name4");
                med.concilList1Name5 = rs.getString("concilList1Name5") == null ? "" : rs.getString("concilList1Name5");
                med.concilAppointmentDate = rs.getTimestamp("concilAppointmentDate");
                med.concilType = rs.getString("concilType") == null ? "" : rs.getString("concilType");
                med.concilSelection = rs.getString("concilSelection") == null ? "" : rs.getString("concilSelection");
                med.concilReplacement = rs.getString("concilReplacement") == null ? "" : rs.getString("concilReplacement");
                med.concilOriginalConciliator = rs.getString("concilOriginalConciliator") == null ? "" : rs.getString("concilOriginalConciliator");
                med.concilOriginalConcilDate = rs.getTimestamp("concilOriginalConcilDate");
                med.concilList2OrderDate = rs.getTimestamp("concilList2OrderDate");
                med.concilList2SelectionDueDate = rs.getTimestamp("concilList2SelectionDueDate");
                med.concilList2Name1 = rs.getString("concilList2Name1") == null ? "" : rs.getString("concilList2Name1");
                med.concilList2Name2 = rs.getString("concilList2Name2") == null ? "" : rs.getString("concilList2Name2");
                med.concilList2Name3 = rs.getString("concilList2Name3") == null ? "" : rs.getString("concilList2Name3");
                med.concilList2Name4 = rs.getString("concilList2Name4") == null ? "" : rs.getString("concilList2Name4");
                med.concilList2Name5 = rs.getString("concilList2Name5") == null ? "" : rs.getString("concilList2Name5");
                med.FFList1OrderDate = rs.getTimestamp("FFList1OrderDate");
                med.FFList1SelectionDueDate = rs.getTimestamp("FFList1SelectionDueDate");
                med.FFList1Name1 = rs.getString("FFList1Name1") == null ? "" : rs.getString("FFList1Name1");
                med.FFList1Name2 = rs.getString("FFList1Name2") == null ? "" : rs.getString("FFList1Name2");
                med.FFList1Name3 = rs.getString("FFList1Name3") == null ? "" : rs.getString("FFList1Name3");
                med.FFList1Name4 = rs.getString("FFList1Name4") == null ? "" : rs.getString("FFList1Name4");
                med.FFList1Name5 = rs.getString("FFList1Name5") == null ? "" : rs.getString("FFList1Name5");
                med.FFAppointmentDate = rs.getTimestamp("FFAppointmentDate");
                med.FFType = rs.getString("FFType") == null ? "" : rs.getString("FFType");
                med.FFSelection = rs.getString("FFSelection") == null ? "" : rs.getString("FFSelection");
                med.FFReplacement = rs.getString("FFReplacement") == null ? "" : rs.getString("FFReplacement");
                med.FFOriginalFactFinder = rs.getString("FFOriginalFactFinder") == null ? "" : rs.getString("FFOriginalFactFinder");
                med.FFOriginalFactFinderDate = rs.getTimestamp("FFOriginalFactFinderDate");
                med.asAgreedToByParties = rs.getBoolean("asAgreedToByParties");
                med.FFList2OrderDate = rs.getTimestamp("FFList2OrderDate");
                med.FFList2SelectionDueDate = rs.getTimestamp("FFList2SelectionDueDate");
                med.FFList2Name1 = rs.getString("FFList2Name1") == null ? "" : rs.getString("FFList2Name1");
                med.FFList2Name2 = rs.getString("FFList2Name2") == null ? "" : rs.getString("FFList2Name2");
                med.FFList2Name3 = rs.getString("FFList2Name3") == null ? "" : rs.getString("FFList2Name3");
                med.FFList2Name4 = rs.getString("FFList2Name4") == null ? "" : rs.getString("FFList2Name4");
                med.FFList2Name5 = rs.getString("FFList2Name5") == null ? "" : rs.getString("FFList2Name5");
                med.FFEmployerType = rs.getString("FFEmployerType") == null ? "" : rs.getString("FFEmployerType");
                med.FFEmployeeType = rs.getString("FFEmployeeType") == null ? "" : rs.getString("FFEmployeeType");
                med.FFReportIssueDate = rs.getTimestamp("FFReportIssueDate");
                med.FFMediatedSettlement = rs.getBoolean("FFMediatedSettlement");
                med.FFAcceptedBy = rs.getString("FFAcceptedBy") == null ? "" : rs.getString("FFAcceptedBy");
                med.FFDeemedAcceptedBy = rs.getString("FFDeemedAcceptedBy") == null ? "" : rs.getString("FFDeemedAcceptedBy");
                med.FFRejectedBy = rs.getString("FFRejectedBy") == null ? "" : rs.getString("FFRejectedBy");
                med.FFOverallResult = rs.getString("FFOverallResult") == null ? "" : rs.getString("FFOverallResult");
                med.FFNote = rs.getString("FFNote") == null ? "" : rs.getString("FFNote");
                med.employerIDNumber = rs.getString("employerIDNumber") == null ? "" : rs.getString("employerIDNumber");
                med.bargainingUnitNumber = rs.getString("bargainingUnitNumber") == null ? "" : rs.getString("bargainingUnitNumber");
                med.approxNumberOfEmployees = rs.getString("approxNumberOfEmployees") == null ? "" : rs.getString("approxNumberOfEmployees");
                med.duplicateCaseNumber = rs.getString("duplicateCaseNumber") == null ? "" : rs.getString("duplicateCaseNumber");
                med.relatedCaseNumber = rs.getString("relatedCaseNumber") == null ? "" : rs.getString("relatedCaseNumber");
                med.negotiationType = rs.getString("negotiationType") == null ? "" : rs.getString("negotiationType");
                med.expirationDate = rs.getTimestamp("expirationDate");
                med.NTNFiledBy = rs.getString("NTNFiledBy") == null ? "" : rs.getString("NTNFiledBy");
                med.negotiationPeriod = rs.getString("negotiationPeriod") == null ? "" : rs.getString("negotiationPeriod");
                med.multiunitBargainingRequested = rs.getBoolean("multiunitBargainingRequested");
                med.mediatorAppointedDate = rs.getTimestamp("mediatorAppointedDate");
                med.mediatorReplacement = rs.getBoolean("mediatorReplacement");
                med.stateMediatorAppointedID = rs.getString("stateMediatorAppointedID") == null ? "0" : rs.getString("stateMediatorAppointedID");
                med.FMCSMediatorAppointedID = rs.getString("FMCSMediatorAppointedID") == null ? "0" : rs.getString("FMCSMediatorAppointedID");
                med.settlementDate = rs.getTimestamp("settlementDate");
                med.caseStatus = rs.getString("caseStatus") == null ? "" : rs.getString("caseStatus");
                med.sendToBoardToClose = rs.getBoolean("sendToBoardToClose");
                med.boardFinalDate = rs.getTimestamp("boardFinalDate");
                med.retentionTicklerDate = rs.getTimestamp("retentionTicklerDate");
                med.lateFiling = rs.getBoolean("lateFiling");
                med.impasse = rs.getBoolean("impasse");
                med.settled = rs.getBoolean("settled");
                med.TA = rs.getBoolean("TA");
                med.MAD = rs.getBoolean("MAD");
                med.withdrawl = rs.getBoolean("withdrawl");
                med.motion = rs.getBoolean("motion");
                med.dismissed = rs.getBoolean("dismissed");
                med.strikeFileDate = rs.getTimestamp("strikeFileDate");
                med.description = rs.getString("unitDescription") == null ? "" : rs.getString("unitDescription");
                med.unitSize = rs.getString("unitSize") == null ? "" : rs.getString("unitSize");
                med.unauthorizedStrike = rs.getBoolean("unauthorizedStrike");
                med.noticeOfIntentToStrikeOnly = rs.getBoolean("noticeOfIntentToStrikeOnly");
                med.intendedDateStrike = rs.getTimestamp("intendedDateStrike");
                med.noticeOfIntentToPicketOnly = rs.getBoolean("noticeOfIntentToPicketOnly");
                med.intendedDatePicket = rs.getTimestamp("intendedDatePicket");
                med.informational = rs.getBoolean("informational");
                med.noticeOfIntentToStrikeAndPicket = rs.getBoolean("noticeOfIntentToStrikeAndPicket");
                med.strikeOccured = rs.getString("strikeOccured") == null ? "" : rs.getString("strikeOccured");
                med.strikeStatus = rs.getString("strikeStatus") == null ? "" : rs.getString("strikeStatus");
                med.strikeBegan = rs.getTimestamp("strikeBegan");
                med.strikeEnded = rs.getTimestamp("strikeEnded");
                med.totalNumberOfDays = rs.getString("totalNumberOfDays") == null ? "" : rs.getString("totalNumberOfDays");
                med.strikeMediatorAppointedID = rs.getString("strikeMediatorAppointedID") == null ? "" : rs.getString("strikeMediatorAppointedID");
                med.strikeNotes = rs.getString("strikeNote") == null ? "" : rs.getString("strikeNote");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadEntireCaseInformation();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return med;
    }

    public static void updateClosedMedCases(MEDCase item, java.sql.Date selectedDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date(selectedDate.getTime()));
        cal.add(Calendar.YEAR, 1);
        cal.add(Calendar.DATE, 45);

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update MEDCase set "
                    + "caseStatus = 'Closed', "
                    + "sendToBoardToClose = 0, "
                    + "boardFinalDate = ?, "
                    + "retentionTicklerDate = ? "
                    + "WHERE caseYear = ? "
                    + "AND caseType = ? "
                    + "AND caseMonth = ? "
                    + "AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, selectedDate);
            preparedStatement.setDate(2, new java.sql.Date(cal.getTimeInMillis()));
            preparedStatement.setString(3, item.caseYear);
            preparedStatement.setString(4, item.caseType);
            preparedStatement.setString(5, item.caseMonth);
            preparedStatement.setString(6, item.caseNumber);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if (ex.getCause() instanceof SQLServerException) {
                updateClosedMedCases(item, selectedDate);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

}
