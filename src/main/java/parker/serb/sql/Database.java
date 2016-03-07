package parker.serb.sql;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.lang3.time.DateUtils;
import parker.serb.DBConnectionInfo;
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class Database {
    
    public Date lastBackup;
    public Date nextBackup;
    
    public static Connection connectToDB() {
        Connection connection = null;
        ResultSet rs = null;
        String url = DBConnectionInfo.url;
        String driver = DBConnectionInfo.driver;
        String userName = DBConnectionInfo.userName;
        String password = DBConnectionInfo.password;
        try {
            Class.forName(driver);
            connection = DriverManager.getConnection(url, userName, password);
        } catch (ClassNotFoundException | SQLException e) {} 
        return connection;
    }
    
    public static void createTables() {
//        User.createTable();
//        Audit.createTable(); 
//        REPCase.createTable();
//        Role.createTable();
//        UserRole.createTable();
//        Activity.createTable();
    }
    
    public static void createDevData() {
//        insertAdminUser();
//        Role.insertDefulatData();
    }
    
    public static void createDBBackup(String location, String db) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static Database getBackupInformation() {
        Database db = new Database();
        
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select * from [Database]"; 

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet result = preparedStatement.executeQuery();
            
            result.next();
            
            db.lastBackup = result.getTimestamp("lastBackup");
            db.nextBackup = result.getTimestamp("nextBackup");
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return db;
    }
    
    private static void updateBackupInformation() {
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getDBStats() {
        String stats = "";
        
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT (size*8)/1024 SizeMB\n" +
                "FROM sys.master_files\n" +
                "WHERE DB_NAME(database_id) = 'SERB' and name = 'SERB'"; 

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet result = preparedStatement.executeQuery();
            
            result.next();
            
            stats = result.getString("SizeMB");
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stats;
    }
}