package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class EmployerCaseSearchData {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String status;
    public String fileDate;
    public String employer;
    public String employerID;
    
    public static List loadEmployerCaseList() {
        List<EmployerCaseSearchData> ulpCaseList = new ArrayList<>();
          
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from EmployerCaseSearchData ORDER BY filedate DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                EmployerCaseSearchData repCase = new EmployerCaseSearchData();
                repCase.id = caseActivity.getInt("id");
                repCase.caseYear = caseActivity.getString("caseYear");
                repCase.caseType = caseActivity.getString("caseType");
                repCase.caseMonth = caseActivity.getString("caseMonth");
                repCase.caseNumber = caseActivity.getString("caseNumber");
                repCase.status = caseActivity.getString("caseStatus");
                repCase.fileDate = caseActivity.getTimestamp("filedate") == null ? "" : Global.mmddyyyy.format(new Date(caseActivity.getTimestamp("filedate").getTime()));
                repCase.employer = caseActivity.getString("employer");
                repCase.employerID = caseActivity.getString("employerID");
                ulpCaseList.add(repCase);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadEmployerCaseList();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return ulpCaseList;
    }
    
    public static void createNewCaseEntry(String year, String type, String month, String number) {
        Statement stmt = null;   
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "INSERT INTO EmployerCaseSearchData (caseYear, caseType, caseMonth, caseNumber) VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, month);
            preparedStatement.setString(4, number);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createNewCaseEntry(year, type, month, number);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseStatus(String caseStatus) {
        Statement stmt = null;  
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE EmployerCaseSearchData SET"
                    + " caseStatus = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseStatus);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseStatus(caseStatus);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateFileDate(Timestamp fileDate) {
        Statement stmt = null;    
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE EmployerCaseSearchData SET"
                    + " fileDate = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, fileDate);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateFileDate(fileDate);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateEmployer(String employerID) {
        Statement stmt = null;    
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE EmployerCaseSearchData SET"
                    + " employer = ?,"
                    + " employerID = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Employer.loadEmployerByID(employerID).employerName);
            preparedStatement.setString(2, employerID);
            preparedStatement.setString(3, Global.caseYear);
            preparedStatement.setString(4, Global.caseType);
            preparedStatement.setString(5, Global.caseMonth);
            preparedStatement.setString(6, Global.caseNumber);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateEmployer(employerID);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseEntryFromCaseInformation(
            String bunnumber, String county, String boarddeemed) {
        Statement stmt = null;    
        
        try {   
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE MEDCaseSearch SET"
                    + " bunnumber = ?,"
                    + " county = ?,"
                    + " boarddeemed = ?,"
                    + " description = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, bunnumber);
            preparedStatement.setString(2, county);
            preparedStatement.setString(3, boarddeemed);
            preparedStatement.setString(4, bunnumber == null ? "" : BargainingUnit.getUnitDescription(bunnumber));
            preparedStatement.setString(5, Global.caseYear);
            preparedStatement.setString(6, Global.caseType);
            preparedStatement.setString(7, Global.caseMonth);
            preparedStatement.setString(8, Global.caseNumber);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseEntryFromCaseInformation(bunnumber, county, boarddeemed);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
