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
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class ORGParentChildLink {
    
    public int id;
    public boolean active;
    public String parentOrgNumber;
    public String childOrgNumber;
    public String orgName;
    
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @param searchTerm term to limit the search results
     * @return List of Activities
     */
    public static List loadParentCaseNumbers(String orgID) {
        List<ORGParentChildLink> activityList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select ORGParentChildLink.*, orgname "
                    + " from ORGParentChildLink"
                    + " JOIN ORGCase on ORGParentChildLink.childOrgNumber = ORGCase.orgNumber"
                    + " where parentOrgNumber = ?"
                    + " and ORGParentChildLink.active = 1"
                    + " order by orgname asc";

            preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, orgID);
                
            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                ORGParentChildLink act = new ORGParentChildLink();
                
                act.parentOrgNumber = caseActivity.getString("parentOrgNumber");
                act.childOrgNumber = caseActivity.getString("childOrgNumber");
                act.orgName = caseActivity.getString("orgName");
                act.id = caseActivity.getInt("id");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return activityList;
    }
    
    public static List loadChildCaseNumbers(String orgID) {
        List<ORGParentChildLink> activityList = new ArrayList<>();
        PreparedStatement preparedStatement = null;
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select ORGParentChildLink.*, orgname "
                    + " from ORGParentChildLink"
                    + " JOIN ORGCase on ORGParentChildLink.parentOrgNumber = ORGCase.orgNumber"
                    + " where childOrgNumber = ?"
                    + " and ORGParentChildLink.active = 1"
                    + " order by orgname asc";

            preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, orgID);
                
            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                ORGParentChildLink act = new ORGParentChildLink();
                
                act.parentOrgNumber = caseActivity.getString("parentOrgNumber");
                act.childOrgNumber = caseActivity.getString("childOrgNumber");
                act.orgName = caseActivity.getString("orgName");
                act.id = caseActivity.getInt("id");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return activityList;
    }
    
    public static void removeLinkByID(String id) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "delete ORGParentChildLink where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addNewLink(String id, String relation) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert into ORGParentChildLink values (1, ?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            if(relation.equals("parent")) {
                preparedStatement.setString(1, id);
                preparedStatement.setString(2, Global.caseNumber);
            } else if(relation.equals("child")) {
                preparedStatement.setString(1, Global.caseNumber);
                preparedStatement.setString(2, id);
            }
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Loads all activities without a limited result
     * @return list of all Activities per case
     */
    public static List loadAllActivity() {
        List<ORGParentChildLink> activityList = new ArrayList<ORGParentChildLink>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            if(Global.caseType.equals("ORG")) {
                
            } else {
                
            }
            
            String sql = "select Activity.id,"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " date,"
                    + " action,"
                    + " firstName,"
                    + " lastName,"
                    + " fileName"
                    + " from Activity"
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    + " ORDER BY date DESC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                ORGParentChildLink act = new ORGParentChildLink();
                act.id = caseActivity.getInt("id");
//                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
//                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
//                act.action = caseActivity.getString("action");
//                act.caseYear = caseActivity.getString("caseYear");
//                act.caseType = caseActivity.getString("caseType");
//                act.caseMonth = caseActivity.getString("caseMonth");
//                act.caseNumber = caseActivity.getString("caseNumber");
//                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return activityList;
    }
    
    public static ORGParentChildLink loadActivityByID(String id) {
        ORGParentChildLink activity = new ORGParentChildLink();
        
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
                    + " Where Activity.id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                activity.id = caseActivity.getInt("id");
//                activity.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
//                activity.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
//                activity.action = caseActivity.getString("action");
//                activity.caseYear = caseActivity.getString("caseYear");
//                activity.caseType = caseActivity.getString("caseType");
//                activity.caseMonth = caseActivity.getString("caseMonth");
//                activity.caseNumber = caseActivity.getString("caseNumber");
//                activity.fileName = caseActivity.getString("fileName");
//                activity.to = caseActivity.getString("to");
//                activity.type = caseActivity.getString("type");
//                activity.comment = caseActivity.getString("comment");
//                activity.from = caseActivity.getString("from");
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return activity;
    }
    
    public static void updateActivtyEntry(ORGParentChildLink activty) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "update Activity SET"
                    + " [to] = ?,"
                    + " [from] = ?,"
                    + " type = ?,"
                    + " comment = ?,"
                    + " action = ?,"
                    + " fileName = ?"
                    + " Where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, activty.to);
//            preparedStatement.setString(2, activty.from);
//            preparedStatement.setString(3, activty.type);
//            preparedStatement.setString(4, activty.comment);
//            preparedStatement.setString(5, activty.action);
//            preparedStatement.setString(6, activty.fileName);
            preparedStatement.setInt(7, activty.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
    }
}
