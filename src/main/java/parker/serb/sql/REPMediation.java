package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
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
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import static parker.serb.sql.REPBoardActionType.loadAllREPBoardActionTypes;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class REPMediation {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public Date   mediationEntryDate;
    public String mediationDate;
    public String mediationType;
    public String mediator;
    public String mediationOutcome;
    
    public static void addMediation(Date date, String type, String mediator, String outcome) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO REPMediation VALUES (?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setTimestamp(6, new Timestamp(date.getTime()));
            preparedStatement.setString(7, type);
            preparedStatement.setInt(8, User.getUserID(mediator));
            preparedStatement.setString(9, outcome.equals("") ? null : outcome);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Created " + type, null);

        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                addMediation(date, type, mediator, outcome);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    
    
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @param searchTerm term to limit the search results
     * @return List of Activities
     */
    public static List loadMediationsByCaseNumber() {
        List<REPMediation> mediationList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from REPMediation"
                    + " where caseYear = ? and"
                    + " caseType = ? and"
                    + " caseMonth = ? and"
                    + " caseNumber = ?"
                    + " ORDER BY mediationDate DESC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                REPMediation act = new REPMediation();
                
                act.id = caseActivity.getInt("id");
                act.mediationDate = caseActivity.getTimestamp("mediationDate") != null ? Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("mediationDate").getTime())) : "";
                act.mediationType = caseActivity.getString("mediationType") != null ? caseActivity.getString("mediationType") : "";
                act.mediator = User.getNameByID(caseActivity.getInt("mediatorID"));
                act.mediationOutcome = caseActivity.getString("mediationOutcome") != null ? caseActivity.getString("mediationOutcome") : "";
                mediationList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                loadMediationsByCaseNumber();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return mediationList;
    }
    
    public static REPMediation loadMeidationByID(String id) {
        REPMediation activity = new REPMediation();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * From REPMediation"
                    + " Where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                activity.id = caseActivity.getInt("id");
                activity.mediationDate = caseActivity.getTimestamp("mediationDate") == null ? "" : Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("mediationDate").getTime()));
                activity.mediationType = caseActivity.getString("mediationType") == null ? "" : caseActivity.getString("mediationType");
                activity.mediator = caseActivity.getInt("mediatorID") == 0 ? "" : User.getNameByID(caseActivity.getInt("mediatorID"));
                activity.mediationOutcome = caseActivity.getString("mediationOutcome") == null ? "" : caseActivity.getString("mediationOutcome");
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                loadMeidationByID(id);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return activity;
    }
    
    public static void updateMediationByID(String id,
            Date date,
            String type,
            String mediator,
            String outcome) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "update REPMediation SET"
                    + " mediationDate = ?,"
                    + " mediationType = ?,"
                    + " mediatorID = ?,"
                    + " mediationOutcome = ?"
                    + " Where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, date == null ? null : new Timestamp(date.getTime()));
            preparedStatement.setString(2, type.equals("") ? null : type);
            preparedStatement.setInt(3, mediator.equals("") ? 0 : User.getUserID(mediator));
            preparedStatement.setString(4, outcome.equals("") ? null : outcome);
            preparedStatement.setString(5, id);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Updated Mediation", null);
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                updateMediationByID(id, date, type, mediator, outcome);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
    public static void deleteMediationByID(int id) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "delete from REPMediation"
                    + " Where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Removed Mediation", null);
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                deleteMediationByID(id);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
}
