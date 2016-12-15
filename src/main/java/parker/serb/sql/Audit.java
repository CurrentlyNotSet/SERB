package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class Audit {
    
    /**
     * Adds an entry to the audit table
     * @param action performed action to be stored
     */
    public static void addAuditEntry(String action) {
        Statement stmt = null;
        try {
            
            stmt = Database.connectToDB().createStatement();
            
            String sql = "INSERT INTO Audit VALUES"
                    + "(?,?,?)";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setInt(2, Global.activeUser.id);
            preparedStatement.setString(3, action);
            
            int result = preparedStatement.executeUpdate();
            
            if(result != 1) {
                System.out.println("ERROR");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
