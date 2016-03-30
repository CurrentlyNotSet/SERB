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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
