package parker.serb.sql;

import static java.nio.file.StandardCopyOption.*;
import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.FilenameUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class Activity {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String user;
    public String date;
    public String action;
    public String fileName;
    public String from;
    public String to;
    public String type;
    public String comment;
    public boolean redacted;
    public boolean awaitingScan;
    
    /**
     * Add an activity to the activity table, pulls the case number from the
     * current selected case
     * @param action the action that has been preformed
     * @param fileName the fileName of a document - null if no file
     */
    public static void addActivty(String action, String fileName) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName == null ? null : fileName);
            preparedStatement.setString(9, "");
            preparedStatement.setString(10, "");
            preparedStatement.setString(11, "");
            preparedStatement.setString(12, "");
            preparedStatement.setBoolean(13, false);
            preparedStatement.setBoolean(14, false);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void addCMDSActivty(String action, String fileName, String caseNumber) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber.split("-")[0]);
            preparedStatement.setString(2, caseNumber.split("-")[1]);
            preparedStatement.setString(3, caseNumber.split("-")[2]);
            preparedStatement.setString(4, caseNumber.split("-")[3]);
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName == null || fileName.equals("") ? null : FilenameUtils.getName(fileName));
            preparedStatement.setString(9, "");
            preparedStatement.setString(10, "");
            preparedStatement.setString(11, "");
            preparedStatement.setString(12, "");
            preparedStatement.setBoolean(13, false);
            preparedStatement.setBoolean(14, false);

            preparedStatement.executeUpdate();
            
            if(fileName != null && !fileName.equals("")) {
                File src = new File(fileName);
                File dst = new File(Global.activityPath + File.separator + "CMDS" + File.separator + caseNumber.split("-")[0] + File.separator + caseNumber + fileName.substring(fileName.lastIndexOf(File.separator)));
                dst.mkdirs();
                Files.copy(src.toPath(), dst.toPath(), REPLACE_EXISTING);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void addHearingActivty(String action, String fileName, String caseNumber) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber.split("-")[0]);
            preparedStatement.setString(2, caseNumber.split("-")[1]);
            preparedStatement.setString(3, caseNumber.split("-")[2]);
            preparedStatement.setString(4, caseNumber.split("-")[3]);
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName == null || fileName.equals("") ? null : FilenameUtils.getName(fileName));
            preparedStatement.setString(9, "");
            preparedStatement.setString(10, "");
            preparedStatement.setString(11, "");
            preparedStatement.setString(12, "");
            preparedStatement.setBoolean(13, false);
            preparedStatement.setBoolean(14, false);

            preparedStatement.executeUpdate();
            
            if(fileName != null && !fileName.equals("")) {
                File src = new File(fileName);
                File dst = new File(Global.activityPath + File.separator + "CMDS" + File.separator + caseNumber.split("-")[0] + File.separator + caseNumber + fileName.substring(fileName.lastIndexOf(File.separator)));
                dst.mkdirs();
                Files.copy(src.toPath(), dst.toPath(), REPLACE_EXISTING);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } catch (IOException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void disableActivtyByID(String id) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Update Activity set active = 0 where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            
            preparedStatement.executeUpdate();
            
            Audit.addAuditEntry("Deactivated Activity:" + id);
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
                disableActivtyByID(id);
            } else {
                SlackNotification.sendNotification(ex.getMessage());
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void addActivtyFromDocket(String action, String fileName,
            String[] caseNumber,
            String from, 
            String to, 
            String type, 
            String comment,
            boolean redacted,
            boolean needsTimestamp) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber[0].trim());
            preparedStatement.setString(2, caseNumber[1].trim());
            preparedStatement.setString(3, caseNumber[2].trim());
            preparedStatement.setString(4, caseNumber[3].trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName.equals("") ? null : fileName);
            preparedStatement.setString(9, from.equals("") ? null : from);
            preparedStatement.setString(10, to.equals("") ? null : to);
            preparedStatement.setString(11, type.equals("") ? null : type);
            preparedStatement.setString(12, comment.equals("") ? null : comment);
            preparedStatement.setBoolean(13, redacted);
            preparedStatement.setBoolean(14, needsTimestamp);


            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void addActivtyFromDocket(String action, String fileName,
            String[] caseNumber,
            String from, 
            String to, 
            String type, 
            String comment,
            boolean redacted,
            boolean needsTimestamp,
            Date activityDate) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber[0].trim());
            preparedStatement.setString(2, caseNumber[1].trim());
            preparedStatement.setString(3, caseNumber[2].trim());
            preparedStatement.setString(4, caseNumber[3].trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(activityDate.getTime()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName.equals("") ? null : fileName);
            preparedStatement.setString(9, from.equals("") ? null : from);
            preparedStatement.setString(10, to.equals("") ? null : to);
            preparedStatement.setString(11, type.equals("") ? null : type);
            preparedStatement.setString(12, comment.equals("") ? null : comment);
            preparedStatement.setBoolean(13, redacted);
            preparedStatement.setBoolean(14, needsTimestamp);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    /**
     * Creates activity entry when new cases are created
     * @param caseNumber the new case number
     */
    public static void addNewCaseActivty(String caseNumber, String message) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            String[] parsedCaseNumber = caseNumber.split("-");
            preparedStatement.setString(1, parsedCaseNumber[0]);
            preparedStatement.setString(2, parsedCaseNumber[1]);
            preparedStatement.setString(3, parsedCaseNumber[2]);
            preparedStatement.setString(4, parsedCaseNumber[3]);
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, message.equals("") ? null : message);
            preparedStatement.setString(8, null);
            preparedStatement.setString(9, null);
            preparedStatement.setString(10, null);
            preparedStatement.setString(11, null);
            preparedStatement.setString(12, null);
            preparedStatement.setBoolean(13, false);
            preparedStatement.setBoolean(14, false);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
    public static List loadCaseNumberActivity(String searchTerm) {
        List<Activity> activityList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            if(Global.caseType.equals("ORG") || Global.caseType.equals("CSC")) {
                String sql = "select Activity.id,"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " date,"
                    + " action,"
                    + " comment,"    
                    + " firstName,"
                    + " lastName,"
                    + " fileName"
                    + " from Activity"
                    + " LEFT JOIN Users"
                    + " ON Activity.userID = Users.id"
                    + " where "
                    + " caseType = ? and"
                    + " caseNumber = ? and"
                    + " (firstName like ? or"
                    + " lastName like ? or"
                    + " action like ?)"
                    + " and active = 1"
                    + " ORDER BY date DESC ";

                preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setObject(1, Global.caseType);
                preparedStatement.setObject(2, Global.caseNumber);
                preparedStatement.setString(3, "%" + searchTerm + "%");
                preparedStatement.setString(4, "%" + searchTerm + "%");
                preparedStatement.setString(5, "%" + searchTerm + "%");
            } else {
                String sql = "select Activity.id,"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " date,"
                    + " action,"
                    + " comment,"    
                    + " firstName,"
                    + " lastName,"
                    + " fileName"
                    + " from Activity"
                    + " LEFT JOIN Users"
                    + " ON Activity.userID = Users.id"
                    + " where caseYear = ? and"
                    + " caseType = ? and"
                    + " caseMonth = ? and"
                    + " caseNumber = ? and"
                    + " (firstName like ? or"
                    + " lastName like ? or"
                    + " action like ?) "
                    + " and Activity.active = 1"
                    + "ORDER BY date DESC ";

                preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setObject(1, Global.caseYear);
                preparedStatement.setObject(2, Global.caseType);
                preparedStatement.setObject(3, Global.caseMonth);
                preparedStatement.setObject(4, Global.caseNumber);
                preparedStatement.setString(5, "%" + searchTerm + "%");
                preparedStatement.setString(6, "%" + searchTerm + "%");
                preparedStatement.setString(7, "%" + searchTerm + "%");
            }

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                Activity act = new Activity();
                
                if(caseActivity.getString("firstName") == null && caseActivity.getString("lastName") == null) {
                    act.user = "SYSTEM";
                } else {
                    act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
                }
                
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.comment = caseActivity.getString("comment") == null ? "" : caseActivity.getString("comment");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static List loadAllActivity() {
        List<Activity> activityList = new ArrayList<Activity>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();
            
            String sql = "select TOP 50 Activity.id,"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " date,"
                    + " action,"
                    + " comment,"
                    + " firstName,"
                    + " lastName,"
                    + " fileName"
                    + " from Activity"
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    + " WHERE Activity.active = 1"
                    + " ORDER BY date DESC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.comment = caseActivity.getString("comment");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static List loadHearingActivity() {
        List<Activity> activityList = new ArrayList<Activity>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();
            
            String sql = "select Activity.id,"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " date,"
                    + " action,"
                    + " comment,"
                    + " firstName,"
                    + " lastName,"
                    + " fileName"
                    + " from Activity"
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    + " WHERE date >= ?"
                    + " AND CaseYear = ?"
                    + " AND CaseType = ?"
                    + " AND CaseMonth = ?"
                    + " AND CaseNumber = ?"
                    + " and Activity.active = 1"
                    + " ORDER BY date DESC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, HearingCase.getBoardActionPCDate());
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.comment = caseActivity.getString("comment");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                System.out.println("TESTING");
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static Activity loadActivityByID(String id) {
        Activity activity = new Activity();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select Activity.id,"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " date,"
                    + " [to],"
                    + " [from],"
                    + " type,"
                    + " comment,"
                    + " action,"
                    + " firstName,"
                    + " lastName,"
                    + " fileName"
                    + " from Activity"
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    + " Where Activity.id = ?"
                    + " and active = 1";
            

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                activity.id = caseActivity.getInt("id");
                activity.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
                activity.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                activity.action = caseActivity.getString("action");
                activity.caseYear = caseActivity.getString("caseYear");
                activity.caseType = caseActivity.getString("caseType");
                activity.caseMonth = caseActivity.getString("caseMonth");
                activity.caseNumber = caseActivity.getString("caseNumber");
                activity.fileName = caseActivity.getString("fileName");
                activity.to = caseActivity.getString("to");
                activity.type = caseActivity.getString("type");
                activity.comment = caseActivity.getString("comment");
                activity.from = caseActivity.getString("from");
                
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activity;
    }
    
    public static void updateActivtyEntry(Activity activty) {
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "update Activity SET"
                    + " [to] = ?,"
                    + " [from] = ?,"
                    + " type = ?,"
                    + " comment = ?,"
                    + " action = ?,"
                    + " fileName = ?"
                    + " Where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, activty.to);
            preparedStatement.setString(2, activty.from);
            preparedStatement.setString(3, activty.type);
            preparedStatement.setString(4, activty.comment);
            preparedStatement.setString(5, activty.action);
            preparedStatement.setString(6, activty.fileName);
            preparedStatement.setInt(7, activty.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static List<Activity> loadActivityDocumentsByGlobalCase() {
        List<Activity> activityList = new ArrayList<Activity>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT * FROM Activity WHERE "
                    + "awaitingTimestamp = 0 AND "
                    + "fileName IS NOT NULL AND "
                    + "caseYear = ? AND "
                    + "caseType = ? AND "
                    + "caseMonth = ? AND "
                    + "caseNumber = ? "
                    + " and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            
            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static List<Activity> loadActivityDocumentsByGlobalCasePublicRectords() {
        List<Activity> activityList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT * FROM Activity WHERE "
                    + "awaitingTimestamp = 0 AND "
                    + "fileName IS NOT NULL AND fileName <>'' AND "
                    + "caseYear = ? AND "
                    + "caseType = ? AND "
                    + "caseMonth = ? AND "
                    + "caseNumber = ? AND "
                    + "active = 1 and "
                    + "action NOT LIKE '%UNREDACTED%' "
                    + "ORDER BY date DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber); 
            
            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                act.redacted = caseActivity.getBoolean("redacted");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static List<Activity> loadActivityDocumentsByGlobalCaseORGCSCPublicRectords() {
        List<Activity> activityList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT * FROM Activity WHERE "
                    + "awaitingTimestamp = 0 AND "
                    + "fileName IS NOT NULL AND fileName <>'' AND "
                    + "caseYear IS NULL AND "
                    + "caseType = ? AND "
                    + "caseMonth IS NULL AND "
                    + "caseNumber = ? AND "
                    + "active = 1 and "
                    + "action NOT LIKE '%UNREDACTED%' "
                    + "ORDER BY date DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, Global.caseType);
            preparedStatement.setString(2, Global.caseNumber); 
            
            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                act.redacted = caseActivity.getBoolean("redacted");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static List<Activity> loadDocumentsBySectionAwaitingRedaction() {
        Statement stmt = null;
        List casetypes = CaseType.getCaseType();
        List<Activity> activityList = new ArrayList<>();
                    
        try {

            stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT * FROM Activity WHERE "
                    + "awaitingTimestamp = 0 AND "
                    + "fileName IS NOT NULL AND "
                    + "active = 1 AND"
                    + "redacted = 0 AND "
                    + "action LIKE 'REDACTED - %' ";
            
            if (!casetypes.isEmpty()) {
                sql += "AND (";
                
                for (Object casetype : casetypes) {
                    sql += " Activity.caseType = ? OR";
                }
                
                sql = sql.substring(0, (sql.length() - 2)) + ")";
            }
            
            sql += " ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            int count = 0;
            for (Object casetype : casetypes) {
                count = count + 1;
                preparedStatement.setString(count, casetype.toString());
            }
            
            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }    
    
    public static List<Activity> loadMailLogBySection(String startDate, String endDate) {
        List casetypes = CaseType.getCaseType();

        List<Activity> activityList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT * FROM Activity WHERE Activity.date >= ?  AND Activity.date <= ? "
                    + "AND Activity.fileName IS NOT NULL AND Activity.fileName != '' " 
                    + "AND (Activity.action LIKE 'IN -%' OR Activity.action LIKE 'OUT -%') "
                    + " and active = 1";
            
            if (!casetypes.isEmpty()) {
                sql += "AND (";
                
                for (Object casetype : casetypes) {
                    
                    sql += " Activity.caseType = ? OR";
                }
                
                sql = sql.substring(0, (sql.length() - 2)) + ")";
            }
            
            sql += " ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC, activity.id DESC";
            

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, startDate + " 00:00:00.000");
            preparedStatement.setString(2, endDate + " 23:59:59.999");
            int count = 2;
            for (Object casetype : casetypes) {
                count = count + 1;
                preparedStatement.setString(count, casetype.toString());
            }
            
            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                Activity activity = new Activity();
                activity.id = caseActivity.getInt("id");
                activity.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                activity.action = caseActivity.getString("action");
                activity.caseYear = caseActivity.getString("caseYear");
                activity.caseType = caseActivity.getString("caseType");
                activity.caseMonth = caseActivity.getString("caseMonth");
                activity.caseNumber = caseActivity.getString("caseNumber");
                activity.fileName = caseActivity.getString("fileName");
                activity.to = caseActivity.getString("to");
                activity.type = caseActivity.getString("type");
                activity.comment = caseActivity.getString("comment");
                activity.from = caseActivity.getString("from");
                activityList.add(activity);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                System.out.println("TESTING");
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static void updateRedactedStatus(boolean redacted, int id) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE  activity SET redacted = ? where id = ?";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setBoolean(1, redacted);
            ps.setInt(2, id);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateUnRedactedAction(String action, int id) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE activity SET action = ? where id = ?";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setString(1, action);
            ps.setInt(2, id);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static Activity getFULLActivityByID(int id) {
        Activity activity = new Activity();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Activity WHERE Activity.id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                activity.id = caseActivity.getInt("id");
                activity.caseYear = caseActivity.getString("caseYear");
                activity.caseType = caseActivity.getString("caseType");
                activity.caseMonth = caseActivity.getString("caseMonth");
                activity.caseNumber = caseActivity.getString("caseNumber");
                activity.user = caseActivity.getString("userID");
                activity.date = caseActivity.getTimestamp("date").toString();
                activity.action = caseActivity.getString("action");
                activity.fileName = caseActivity.getString("fileName");
                activity.from = caseActivity.getString("from");
                activity.to = caseActivity.getString("to");
                activity.type = caseActivity.getString("type");
                activity.comment = caseActivity.getString("comment");
                activity.redacted = caseActivity.getBoolean("redacted");
                activity.awaitingScan = caseActivity.getBoolean("awaitingTimestamp");
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activity;
    }
    
    public static void duplicatePublicRecordActivty(Activity item) {
        Timestamp time = null;

        try {
            time = new Timestamp(Global.SQLDateTimeFormat.parse(item.date).getTime());
        } catch (ParseException ex) {
            Logger.getLogger(Activity.class.getName()).log(Level.SEVERE, null, ex);
        }

        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity ("
                    + "caseYear, "        //01
                    + "caseType, "        //02
                    + "caseMonth, "       //03
                    + "caseNumber, "      //04
                    + "userID, "          //05
                    + "date, "            //06
                    + "action, "          //07
                    + "fileName, "        //08
                    + "[from], "          //09
                    + "[to], "            //10
                    + "type, "            //11
                    + "comment, "         //12
                    + "redacted, "        //13
                    + "awaitingTimeStamp "//14
                    + ") VALUES (";
                    for(int i=0; i<13; i++){
                        sql += "?, ";   //01-13
                    }
                     sql += "?)"; //14

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, item.caseYear);
            preparedStatement.setString(2, item.caseType);
            preparedStatement.setString(3, item.caseMonth);
            preparedStatement.setString(4, item.caseNumber);
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, time);
            preparedStatement.setString(7, item.action);
            preparedStatement.setString(8, item.fileName);
            preparedStatement.setString(9, item.from);
            preparedStatement.setString(10, item.to);
            preparedStatement.setString(11, item.type);
            preparedStatement.setString(12, item.comment);
            preparedStatement.setBoolean(13, item.redacted);
            preparedStatement.setBoolean(14, item.awaitingScan);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }  
}
