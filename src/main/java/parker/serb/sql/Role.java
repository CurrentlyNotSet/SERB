package parker.serb.sql;

import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author parkerjohnston
 */
public class Role {
    
    public static void createTable() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "CREATE TABLE Role" +
                    "(id int IDENTITY (1,1) NOT NULL, " +
                    " active bit NOT NULL, " + 
                    " role varchar(50) NULL, " +
                    " PRIMARY KEY (id))"; 
            
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
}
