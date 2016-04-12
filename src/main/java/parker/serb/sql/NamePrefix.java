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
public class NamePrefix {
    
    public int id;
    public boolean active;
    public String prefix;
    
    public static List loadActivePrefix() {
        List<String> prefixList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from NamePrefix where active = 1"
                    + " order by prefix";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet prefixRS = preparedStatement.executeQuery();
            
            while(prefixRS.next()) {
                prefixList.add(prefixRS.getString("prefix"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return prefixList;
    }
    
   
}
