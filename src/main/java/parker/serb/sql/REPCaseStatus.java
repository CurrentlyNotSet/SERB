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
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class REPCaseStatus {
    
    public int id;
    public boolean active;
    public String statusType;
    public String status;
    
//    /**
//     * Creates an empty Activity Table
//     */
//    public static void createTable() {
//        Statement stmt = null;
//        try {
//            
//            stmt = Database.connectToDB().createStatement();
//            
//            String sql = "CREATE TABLE Activity" +
//                    "(id int IDENTITY (1,1) NOT NULL, " +
//                    " caseNumber varchar(16) NOT NULL, " + 
//                    " userID varchar(1), " +
//                    " date datetime NOT NULL, " +
//                    " action text NOT NULL, " +
//                    " PRIMARY KEY (id))"; 
//            
//            stmt.executeUpdate(sql);
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                stmt.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
   
    public static List loadAll() {
        List caseStatusList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from REPCaseStatus where active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseStatusRS = preparedStatement.executeQuery();
            
            while(caseStatusRS.next()) {
                REPCaseStatus rep = new REPCaseStatus();
                rep.id = caseStatusRS.getInt("id");
                rep.statusType = caseStatusRS.getString("statusType");
                rep.status = caseStatusRS.getString("status");
                caseStatusList.add(rep);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return caseStatusList;
    }
}
