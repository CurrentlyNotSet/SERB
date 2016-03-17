package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class CaseType {
    
//    public int id;
//    public String user;
//    public String action;
//    public String date;
//    public String filePath;
    
    
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
    
    /**
     * Return a list of all case types that are possible for a section.  The
     * current selection is gathered from the global file
     * @return list of strings of all found case types
     */
    public static List<String> getCaseType() {
        Statement stmt = null;
        
        List activityList = new ArrayList<String>();
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Select caseType from CaseType where Section = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.activeSection);

            ResultSet caseType = preparedStatement.executeQuery();
            
            while(caseType.next()) {
                activityList.add(caseType.getString("caseType"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activityList;
    }
}
