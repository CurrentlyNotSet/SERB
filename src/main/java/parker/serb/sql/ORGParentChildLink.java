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
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadParentCaseNumbers(orgID);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadChildCaseNumbers(orgID);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static void removeLinkByID(String id) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "delete ORGParentChildLink where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeLinkByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void addNewLink(String id, String relation) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addNewLink(id, relation);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
