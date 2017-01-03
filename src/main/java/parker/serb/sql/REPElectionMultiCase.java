package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

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

    public static void addMultiCase(String caseNumber) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO REPElectionMultiCase ("
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " multiCase)"
                    + " VALUES"
                    + " (?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addMultiCase(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static boolean checkCaseIsAlreadyPresent(String caseNumber) {
        boolean newCase = true;
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                checkCaseIsAlreadyPresent(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return newCase;
    }
    
    public static void removeMultiCase(String caseNumber) {
        Statement stmt = null;    
        try {
            stmt = Database.connectToDB().createStatement();

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
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeMultiCase(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadMultiCaseNumber();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
}
