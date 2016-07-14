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
public class REPElectionMultiCase {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String multiCase;
    
    /**
     * Add an activity to the activity table, pulls the case number from the
     * current selected case
     * @param action the action that has been preformed
     * @param fileName the fileName of a document - null if no file
     */
    public static void addMultiCase(String caseNumber) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO REPElectionMultiCase (caseYear, caseType, caseMonth, caseNumber, multiCase) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static boolean checkCaseIsAlreadyPresent(String caseNumber) {
        
        boolean newCase = true;
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from REPElectionMultiCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? AND"
                    + " cast(multicase as nvarchar(max)) = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            ResultSet relatedCaseRS = preparedStatement.executeQuery();
            
            while (relatedCaseRS.next()) {
                newCase = false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newCase;
    }
    
    public static void removeMultiCase(String caseNumber) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Delete from REPElectionMultiCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? AND"
                    + " cast(multicase as nvarchar(max)) = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
//            Activity.addActivty("Removed " + caseNumber + " from Related Case", null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @param searchTerm term to limit the search results
     * @return List of Activities
     */
    public static List loadMultiCaseNumber() {
        List<String> activityList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select multicase"
                    + " from REPElectionMultiCase"
                    + " where caseYear = ? and"
                    + " caseType = ? and"
                    + " caseMonth = ? and"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                activityList.add(caseActivity.getString("multiCase"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return activityList;
    }
}
