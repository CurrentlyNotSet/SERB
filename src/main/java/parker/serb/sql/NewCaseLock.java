package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class NewCaseLock {

    public String section;
    public String lockedBy;
    public String lockDate;
    
    public static NewCaseLock checkLock(String section) {
        NewCaseLock locked = null;
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from NewCaseLock where section = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);

            ResultSet lockedRs = preparedStatement.executeQuery();
            
            if(lockedRs.next()) {
                locked = new NewCaseLock();
                locked.section = lockedRs.getString("section");
                locked.lockedBy = lockedRs.getString("lockedBy");
                locked.lockDate = Global.mmddyyyyhhmma.format(new Date(lockedRs.getTimestamp("lockDate").getTime()));;
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                checkLock(section);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return locked;
    }
    
    public static void addLock(String section) {
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO NewCaseLock VALUES (?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, Global.activeUser.firstName + " " + Global.activeUser.lastName);
            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addLock(section);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void removeLock(String section) {
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from NewCaseLock where section = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeLock(section);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void removeUserLocks() {
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from NewCaseLock where lockedBy = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.activeUser.firstName + " " + Global.activeUser.lastName);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeUserLocks();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
