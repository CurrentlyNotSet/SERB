/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import static parker.serb.sql.Activity.loadAllActivity;
import parker.serb.util.SlackNotification;

/**
 *
 * @author User
 */
public class PurgedActivity {
    
    public int id;
    public Date purgeDate;
    public int purgeUserID;
    public int activityID;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String user;
    public String userName;
    public Timestamp date;
    public String action;
    public String fileName;
    public String from;
    public String to;
    public String type;
    public String comment;
    public boolean redacted;
    public boolean awaitingTimestamp;
    public boolean active;
    public Date mailLog;
        
    public static List loadPurgeCMDSActivities() {
        List<PurgedActivity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();
            
            String excludeList = " AND ("
                    + "    Activity.action NOT LIKE '%%'"
                    + " AND Activity.action NOT LIKE '%%'"
                    + ")";

            String sql = "SELECT TOP 50 Activity.*,"
                    + " ISNULL(Users.firstName, '') + ' ' + ISNULL(Users.lastName, '') AS userName"
                    + " FROM Activity"
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    + " INNER JOIN caseType"
                    + " ON Activity.caseType = CaseType.caseType"
                    + " WHERE Activity.active = 1 AND CaseType.section = 'CMDS'"
                    + " ORDER BY Activity.caseYear ASC, Activity.caseMonth ASC, Activity.caseNumber ASC, date ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                PurgedActivity act = new PurgedActivity();
                act.activityID   = rs.getInt    ("id");
                act.caseYear     = rs.getString ("caseYear");
                act.caseType     = rs.getString ("caseType");
                act.caseMonth    = rs.getString ("caseMonth");
                act.caseNumber   = rs.getString ("caseNumber");
                act.user         = rs.getString ("userID");
                act.date         = rs.getTimestamp("date");
                act.action       = rs.getString ("action");
                act.fileName     = rs.getString ("fileName");
                act.from         = rs.getString ("from");
                act.to           = rs.getString ("to");
                act.type         = rs.getString ("type");
                act.comment      = rs.getString ("comment");
                act.redacted     = rs.getBoolean("redacted");
                act.awaitingTimestamp = rs.getBoolean("awaitingScan");
                act.active       = rs.getBoolean("active");
                act.mailLog      = rs.getDate   ("mailLog");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                loadAllActivity();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static List loadPurgeULPActivities(java.util.Date startDate, java.util.Date endDate) {
        List<PurgedActivity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            List<String> excludeList = RetentionExclusion.getActiveRetentionExclusionBySection("ULP");

            String sql = "SELECT Activity.*,"
                    + " ISNULL(Users.firstName, '') + ' ' + ISNULL(Users.lastName, '') AS userName"
                    + " FROM Activity"
                    //Join to Users To Get UserName
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    //Join to MEDCase to get Case Status
                    + " INNER JOIN ULPCase"
                    + " ON (ULPCase.caseYear = Activity.caseYear "
                    + " AND ULPCase.caseType = Activity.caseType "
                    + " AND ULPCase.caseMonth = Activity.caseMonth "
                    + " AND ULPCase.caseNumber = Activity.caseNumber)"
                    //Join to Hearings to get (opinion, PC-Date, Pre-D-Date)
                    + " LEFT JOIN HearingCase"
                    + " ON (HearingCase.caseYear = Activity.caseYear "
                    + " AND HearingCase.caseType = Activity.caseType "
                    + " AND HearingCase.caseMonth = Activity.caseMonth "
                    + " AND HearingCase.caseNumber = Activity.caseNumber) "
                    //Join to Board Meeting to get the date range criteria
                    + "LEFT JOIN BoardMeeting "
                    + "ON (BoardMeeting.caseYear = ULPCase.caseYear" 
                    + " AND BoardMeeting.caseType = ULPCase.caseType" 
                    + " AND BoardMeeting.caseMonth = ULPCase.caseMonth" 
                    + " AND BoardMeeting.caseNumber = ULPCase.caseNumber) "
                    //Where statement
                    + " WHERE ULPCase.currentStatus = 'Closed'"
                    + " AND (ULPCase.finalDispositionStatus = 'Closed' OR ISNULL(ULPCase.appealDateSent, '') = '')"
                    + " AND ISNULL(ULPCase.appealDateSent, '') = ''"
                    + " AND ISNULL(ULPCase.appealDateReceived, '') = ''"
                    + " AND ISNULL(HearingCase.opinion, '') = ''"
                    + " AND ISNULL(HearingCase.boardActionPCDate, '') = ''"
                    + " AND ISNULL(HearingCase.boardActionPreDDate, '') = ''";
                    if (excludeList.size() > 0) {
                        sql += " AND (";

                        for (int i = 0; i < excludeList.size(); i++) {
                            if (i > 0) {
                                sql += " AND ";
                            }
                            sql += "Activity.action NOT LIKE ?";
                        }
                        sql += ")";
                    }
                    sql += "AND ((DATEADD(day,411, BoardMeeting.boardMeetingDate)) BETWEEN ? AND ?) "
                    
                    + " ORDER BY ULPCase.caseYear ASC, ULPCase.caseMonth ASC, ULPCase.caseNumber ASC, date ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < excludeList.size(); i++) {
                preparedStatement.setString((i + 1), "%" + excludeList.get(i).trim() + "%");
            }
            
            preparedStatement.setDate(excludeList.size() + 1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(excludeList.size() + 2, new java.sql.Date(endDate.getTime()));
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                PurgedActivity act = new PurgedActivity();
                act.activityID   = rs.getInt    ("id");
                act.caseYear     = rs.getString ("caseYear");
                act.caseType     = rs.getString ("caseType");
                act.caseMonth    = rs.getString ("caseMonth");
                act.caseNumber   = rs.getString ("caseNumber");
                act.user         = rs.getString ("userID");
                act.userName     = rs.getString ("userName");
                act.date         = rs.getTimestamp("date");
                act.action       = rs.getString ("action");
                act.fileName     = rs.getString ("fileName");
                act.from         = rs.getString ("from");
                act.to           = rs.getString ("to");
                act.type         = rs.getString ("type");
                act.comment      = rs.getString ("comment");
                act.redacted     = rs.getBoolean("redacted");
                act.awaitingTimestamp = rs.getBoolean("awaitingTimestamp");
                act.active       = rs.getBoolean("active");
                act.mailLog      = rs.getDate   ("mailLog");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                loadAllActivity();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static List loadPurgeMEDActivities(java.util.Date startDate, java.util.Date endDate) {
        List<PurgedActivity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();
            
            List<String> excludeList = RetentionExclusion.getActiveRetentionExclusionBySection("MED");

            String sql = "SELECT Activity.*,"
                    + " ISNULL(Users.firstName, '') + ' ' + ISNULL(Users.lastName, '') AS userName"
                    + " FROM Activity"
                    //Join to Users To Get UserName
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    //Join to MEDCase to get Case Status
                    + " INNER JOIN MEDCase"
                    + " ON (MEDCase.caseYear = Activity.caseYear "
                    + " AND MEDCase.caseType = Activity.caseType "
                    + " AND MEDCase.caseMonth = Activity.caseMonth "
                    + " AND MEDCase.caseNumber = Activity.caseNumber)"
                    //Join to Hearings to get (opinion, PC-Date, Pre-D-Date)
                    + " LEFT JOIN HearingCase"
                    + " ON (HearingCase.caseYear = Activity.caseYear AND HearingCase.caseType = Activity.caseType "
                    + " AND HearingCase.caseMonth = Activity.caseMonth "
                    + " AND HearingCase.caseNumber = Activity.caseNumber)"
                    //Where statement
                    + " WHERE MEDCase.caseStatus = 'Closed'"
                    + " AND ISNULL(HearingCase.opinion, '') = ''"
                    + " AND ISNULL(HearingCase.boardActionPCDate, '') = ''"
                    + " AND ISNULL(HearingCase.boardActionPreDDate, '') = ''";
                    if (excludeList.size() > 0) {
                        sql += " AND (";

                        for (int i = 0; i < excludeList.size(); i++) {
                            if (i > 0) {
                                sql += " AND ";
                            }
                            sql += "Activity.action NOT LIKE ?";
                        }
                        sql += ")";
                    }
                    sql += " AND retentionTicklerDate >= ?  AND retentionTicklerDate <= ? "
                    + " ORDER BY MEDCase.caseYear ASC, MEDCase.caseMonth ASC, MEDCase.caseNumber ASC, date ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < excludeList.size(); i++) {
                preparedStatement.setString((i + 1), "%" + excludeList.get(i).trim() + "%");
            }
            
            preparedStatement.setDate(excludeList.size() + 1, new java.sql.Date(startDate.getTime()));
            preparedStatement.setDate(excludeList.size() + 2, new java.sql.Date(endDate.getTime()));
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                PurgedActivity act = new PurgedActivity();
                act.activityID   = rs.getInt    ("id");
                act.caseYear     = rs.getString ("caseYear");
                act.caseType     = rs.getString ("caseType");
                act.caseMonth    = rs.getString ("caseMonth");
                act.caseNumber   = rs.getString ("caseNumber");
                act.user         = rs.getString ("userID");
                act.userName     = rs.getString ("userName");
                act.date         = rs.getTimestamp("date");
                act.action       = rs.getString ("action");
                act.fileName     = rs.getString ("fileName");
                act.from         = rs.getString ("from");
                act.to           = rs.getString ("to");
                act.type         = rs.getString ("type");
                act.comment      = rs.getString ("comment");
                act.redacted     = rs.getBoolean("redacted");
                act.awaitingTimestamp = rs.getBoolean("awaitingTimestamp");
                act.active       = rs.getBoolean("active");
                act.mailLog      = rs.getDate   ("mailLog");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                loadAllActivity();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static List loadPurgeORGActivities() {
        List<PurgedActivity> activityList = new ArrayList<>();

        Statement stmt = null;

        List<String> excludeList = RetentionExclusion.getActiveRetentionExclusionBySection("ORG");
        
        try {

            stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT Activity.*, ORGCase.OrgName, "
                    + " ISNULL(Users.firstName, '') + ' ' + ISNULL(Users.lastName, '') AS userName"
                    + " FROM Activity"
                    //Join to Users To Get UserName
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    //Join to OrgCase to get entries older than [Due Date] minus 7 years
                    + " INNER JOIN ORGCase"
                    + " ON Activity.caseNumber = ORGCase.orgNumber"
                    + " WHERE AND Activity.caseType = 'ORG'"
                    + " AND Activity.date < CAST(REPLACE(ORGCase.filingDueDate, RIGHT(ORGCase.filingDueDate, 2), ' ') + CONVERT(varchar(4), (YEAR(GETDATE()) - 7), 4) AS datetime) ";
                    if (excludeList.size() > 0) {
                        sql += " AND (";

                        for (int i = 0; i < excludeList.size(); i++) {
                            if (i > 0) {
                                sql += " AND ";
                            }
                            sql += "Activity.action NOT LIKE ?";
                        }
                        sql += ")";
                    }                    
                    sql += " ORDER BY OrgName ASC, date ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < excludeList.size(); i++) {
                preparedStatement.setString((i + 1), "%" + excludeList.get(i).trim() + "%");
            }
            
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                PurgedActivity act = new PurgedActivity();
                act.activityID   = rs.getInt    ("id");
                act.caseYear     = rs.getString ("caseYear");
                act.caseType     = rs.getString ("caseType");
                act.caseMonth    = rs.getString ("caseMonth");
                act.caseNumber   = rs.getString ("caseNumber");
                act.user         = rs.getString ("userID");
                act.userName     = rs.getString ("userName");
                act.date         = rs.getTimestamp("date");
                act.action       = rs.getString ("action");
                act.fileName     = rs.getString ("fileName");
                act.from         = rs.getString ("from");
                act.to           = rs.getString ("to");
                act.type         = rs.getString ("type");
                act.comment      = rs.getString ("comment");
                act.redacted     = rs.getBoolean("redacted");
                act.awaitingTimestamp = rs.getBoolean("awaitingTimestamp");
                act.active       = rs.getBoolean("active");
                act.mailLog      = rs.getDate   ("mailLog");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                loadAllActivity();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static PurgedActivity loadActivityByID(int id) {
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM activity WHERE id = ?";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.first()){
                PurgedActivity activity = new PurgedActivity();
                activity.activityID   = rs.getInt    ("id");
                activity.caseYear     = rs.getString ("caseYear");
                activity.caseType     = rs.getString ("caseType");
                activity.caseMonth    = rs.getString ("caseMonth");
                activity.caseNumber   = rs.getString ("caseNumber");
                activity.user         = rs.getString ("userID");
                activity.date         = rs.getTimestamp("date");
                activity.action       = rs.getString ("action");
                activity.fileName     = rs.getString ("fileName");
                activity.from         = rs.getString ("from");
                activity.to           = rs.getString ("to");
                activity.type         = rs.getString ("type");
                activity.comment      = rs.getString ("comment");
                activity.redacted     = rs.getBoolean("redacted");
                activity.awaitingTimestamp = rs.getBoolean("awaitingScan");
                activity.active       = rs.getBoolean("active");
                activity.mailLog      = rs.getDate   ("mailLog");
                return activity;
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadActivityByID(id);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return null;
    }
    
    public static boolean insertPurgedRecord(PurgedActivity item) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO PurgedActivity ("
                    + "purgeDate, "        //01
                    + "purgeUserID, "      //02
                    + "activityID, "       //03
                    + "caseYear, "         //04
                    + "caseType, "         //05
                    + "caseMonth, "        //06
                    + "caseNumber, "       //07
                    + "userID, "           //08
                    + "date, "             //09
                    + "action, "           //10
                    + "fileName, "         //11
                    + "[from], "           //12
                    + "[to], "             //13
                    + "type, "             //14
                    + "comment, "          //15
                    + "redacted, "         //16
                    + "awaitingTimeStamp, "//17
                    + "active, "           //18
                    + "mailLog "           //19
                    + ") VALUES (";
                    for(int i=0; i<18; i++){
                        sql += "?, ";   //01-18
                    }
                     sql += "?)"; //19

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setDate     ( 1, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            ps.setInt      ( 2, Global.activeUser.id);
            ps.setInt      ( 3, item.activityID);
            ps.setString   ( 4, item.caseYear);
            ps.setString   ( 5, item.caseType);
            ps.setString   ( 6, item.caseMonth);
            ps.setString   ( 7, item.caseNumber);
            ps.setString   ( 8, item.user);
            ps.setTimestamp( 9, item.date);
            ps.setString   (10, item.action);
            ps.setString   (11, item.fileName);
            ps.setString   (12, item.from);
            ps.setString   (13, item.to);
            ps.setString   (14, item.type);
            ps.setString   (15, item.comment);
            ps.setBoolean  (16, item.redacted);
            ps.setBoolean  (17, item.awaitingTimestamp);
            ps.setBoolean  (18, item.active);
            ps.setDate     (19, item.mailLog);
            ps.executeUpdate();

            ResultSet newRow = ps.getGeneratedKeys();
            if (newRow.next()){
                return true;
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                insertPurgedRecord(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return false;
    }
    
    public static void deleteActivityByID(int id) {

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "DELETE FROM Activity WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                deleteActivityByID(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
}
