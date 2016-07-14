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
public class EmployerCaseSearchData {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String status;
    public String fileDate;
    public String employer;
    
    public static List loadEmployerCaseList() {
        List<EmployerCaseSearchData> ulpCaseList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

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
                ulpCaseList.add(repCase);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return ulpCaseList;
    }
    
    public static void createNewCaseEntry(String year, String type, String month, String number) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "INSERT INTO EmployerCaseSearchData (caseYear, caseType, caseMonth, caseNumber) VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, month);
            preparedStatement.setString(4, number);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateCaseStatus(String caseStatus) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateFileDate(Timestamp fileDate) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateEmployer(String employerID) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE EmployerCaseSearchData SET"
                    + " employer = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Employer.loadEmployerByID(employerID).employerName);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateCaseEntryFromCaseInformation(
            String bunnumber, String county, String boarddeemed) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
