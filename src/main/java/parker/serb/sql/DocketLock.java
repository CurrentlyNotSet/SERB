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
public class DocketLock {

    public String itemWithLock;
    public String section;
    public String lockedBy;
    public String lockDate;
    
    
    public static DocketLock checkLock(String section, String item) {
        DocketLock locked = null;
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from DocketLock where section = ?"
                    + " and itemWithLock = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, item);

            ResultSet lockedRs = preparedStatement.executeQuery();
            
            if(lockedRs.next()) {
                locked = new DocketLock();
                locked.itemWithLock = lockedRs.getString("itemWithLock");
                locked.section = lockedRs.getString("section");
                locked.lockedBy = lockedRs.getString("lockedBy");
                locked.lockDate = Global.mmddyyyyhhmma.format(new Date(lockedRs.getTimestamp("lockDate").getTime()));;
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                checkLock(section, item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return locked;
    }
    
    public static void addLock(String section, String item) {
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO DocketLock VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item);
            preparedStatement.setString(2, section);
            preparedStatement.setString(3, Global.activeUser.firstName + " " + Global.activeUser.lastName);
            preparedStatement.setTimestamp(4, new Timestamp(System.currentTimeMillis()));
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addLock(section, item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void removeLock(String section, String item) {
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from DocketLock where section = ?"
                    + " and itemWithLock = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, item);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeLock(section, item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void removeUserLocks() {
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from DocketLock where lockedBy = ?";

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
