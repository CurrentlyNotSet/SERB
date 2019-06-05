package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Date;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.time.DateUtils;
import parker.serb.DBConnectionInfo;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class Database {
    
    public Date lastBackup;
    public Date nextBackup;
    
    public static Connection connectToDB() {
        int nbAttempts = 0;
        
        Connection connection = null;
        
        while (true) {
            try {
                Class.forName(DBConnectionInfo.getDriver());
                connection = DriverManager.getConnection(
                        DBConnectionInfo.getUrl(), 
                        DBConnectionInfo.getUserName(), 
                        DBConnectionInfo.getPassword()
                );
                DriverManager.setLoginTimeout(30);
                break;
            } catch (ClassNotFoundException | SQLException e) {
                nbAttempts = DatabaseExceptionCatch.DatabaseConnectionError(nbAttempts);
            }
        }
        return connection;
    }
    
    //TODO
    public static void createDBBackup(String location, String db) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

//            String sql = "BACKUP DATABASE ? \n" +
//                "TO DISK = ?"; 
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, db);
//            preparedStatement.setString(2, location);
//
//            preparedStatement.executeUpdate();
            
            Audit.addAuditEntry("Created a backup of " + db);
            updateBackupInformation();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createDBBackup(location, db);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static Database getBackupInformation() {
        Database db = new Database();
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from [Database]"; 

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet result = preparedStatement.executeQuery();
            
            result.next();
            
            db.lastBackup = result.getTimestamp("lastBackup");
            db.nextBackup = result.getTimestamp("nextBackup");
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getBackupInformation();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return db;
    }
    
    private static void updateBackupInformation() {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update [Database] SET lastBackup = ?, nextBackup = ?";
            
            java.util.Date date = new java.util.Date();
            long t = date.getTime();
            java.sql.Date lastBackup = new java.sql.Date(t);
            java.sql.Date nextBackup = new java.sql.Date(DateUtils.addDays(date, 7).getTime());

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setDate(1, lastBackup);
            preparedStatement.setDate(2, nextBackup);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateBackupInformation();;
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static String getDBStats() {
        String stats = "";
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT (size*8)/1024 SizeMB\n" +
                "FROM sys.master_files\n" +
                "WHERE DB_NAME(database_id) = 'SERB' and name = 'SERB'"; 

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet result = preparedStatement.executeQuery();
            
            result.next();
            
            stats = result.getString("SizeMB");
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getDBStats();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return stats;
    }
}