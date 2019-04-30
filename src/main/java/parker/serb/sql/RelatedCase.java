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
public class RelatedCase {
    
    public int id;
    public String relatedCaseYear;
    public String relatedCaseType;
    public String relatedCaseMonth;
    public String relatedCaseNumber;
    
    
    public static void addNewRelatedCase(String caseNumber) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO RelatedCase VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Added " + caseNumber + " as Related Case", null);

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addNewRelatedCase(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void addNewMultiCase(String caseNumber) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO RelatedCase VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Added " + caseNumber + " as Multi Case", null);
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addNewMultiCase(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static List loadRelatedCases() {
        List<String> relatedCasesList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? ORDER BY"
                    + " relatedCaseNumber ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet relatedCaseRS = preparedStatement.executeQuery();
            
            while(relatedCaseRS.next()) {
                relatedCasesList.add((String) relatedCaseRS.getString("relatedCaseNumber"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadRelatedCases();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return relatedCasesList;
    }
    
    public static List<RelatedCase> loadRelatedCasesNonGlobal(String caseYear, String caseType, String caseMonth, String caseNumber) {
        List<RelatedCase> relatedCasesList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? ORDER BY"
                    + " relatedCaseNumber ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            preparedStatement.setString(2, caseType);
            preparedStatement.setString(3, caseMonth);
            preparedStatement.setString(4, caseNumber);

            ResultSet relatedCaseRS = preparedStatement.executeQuery();
            
            while(relatedCaseRS.next()) {
                RelatedCase item = new RelatedCase();
                item.id = relatedCaseRS.getInt("relatedCaseNumber");
                item.relatedCaseYear = relatedCaseRS.getString("caseYear");
                item.relatedCaseType = relatedCaseRS.getString("caseType");
                item.relatedCaseMonth = relatedCaseRS.getString("caseMonth");
                item.relatedCaseNumber = relatedCaseRS.getString("caseNumber");
                
                relatedCasesList.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadRelatedCasesNonGlobal(caseYear, caseType, caseMonth, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return relatedCasesList;
    }
    
    public static boolean checkCaseIsAlreadyRelated(String caseNumber) {
        boolean newCase = true;
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? AND"
                    + " cast(relatedCaseNumber as nvarchar(max)) = ?";

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
                checkCaseIsAlreadyRelated(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return newCase;
    }
    
    public static void removeRelatedCase(String caseNumber) {
        Statement stmt = null;    
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? AND"
                    + " cast(relatedCaseNumber as nvarchar(max)) = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
            Activity.addActivty("Removed " + caseNumber + " from Related Case", null);
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeRelatedCase(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void removeMultiCase(String caseNumber) {
        Statement stmt = null;    
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? AND"
                    + " cast(relatedCaseNumber as nvarchar(max)) = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
            Activity.addActivty("Removed " + caseNumber + " from Multi Case", null);
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeMultiCase(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
