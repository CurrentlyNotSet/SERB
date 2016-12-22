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
public class HearingCaseSearchData {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String hearingStatus;
    public String hearingParties;
    public String hearingPCDate;
    public String hearingALJ;
    public String hearingBoardActionDate;
    
    public static List loadCaseSearchData() {
        List<HearingCaseSearchData> ulpCaseList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from HearingCaseSearch ORDER BY hearingPCDate DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                HearingCaseSearchData repCase = new HearingCaseSearchData();
                repCase.id = caseActivity.getInt("id");
                repCase.caseYear = caseActivity.getString("caseYear");
                repCase.caseType = caseActivity.getString("caseType");
                repCase.caseMonth = caseActivity.getString("caseMonth");
                repCase.caseNumber = caseActivity.getString("caseNumber");
                repCase.hearingStatus = caseActivity.getString("hearingStatus") == null ? "" : caseActivity.getString("hearingStatus");
                repCase.hearingParties = caseActivity.getString("hearingParties") == null ? "" : caseActivity.getString("hearingParties");
                repCase.hearingPCDate = caseActivity.getTimestamp("hearingPCDate") == null ? "" : Global.mmddyyyy.format(new Date(caseActivity.getTimestamp("hearingPCDate").getTime()));
                repCase.hearingALJ = caseActivity.getString("hearingALJ") == null ? "" : caseActivity.getString("hearingALJ");
                repCase.hearingBoardActionDate = caseActivity.getTimestamp("hearingBoardActionDate") == null ? "" : Global.mmddyyyy.format(new Date(caseActivity.getTimestamp("hearingBoardActionDate").getTime()));
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

            String sql = "INSERT INTO MEDCaseSearch (caseYear, caseType, caseMonth, caseNumber) VALUES (?,?,?,?)";

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
    
    public static void updateCaseEntryFromParties(String employer, String unionName) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE MEDCaseSearch SET"
                    + " employerName = ?,"
                    + " unionName = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, employer);
            preparedStatement.setString(2, unionName);
            preparedStatement.setString(3, Global.caseYear);
            preparedStatement.setString(4, Global.caseType);
            preparedStatement.setString(5, Global.caseMonth);
            preparedStatement.setString(6, Global.caseNumber);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateCaseEntryFromCaseStatus(
            String employerID, String bunnumber) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE MEDCaseSearch SET"
                    + " employerID = ?,"
                    + " bunnumber = ?,"
                    + " county = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, employerID);
            preparedStatement.setString(2, bunnumber);
            preparedStatement.setString(3, Employer.getEmployerCountyByID(employerID));
            preparedStatement.setString(4, Global.caseYear);
            preparedStatement.setString(5, Global.caseType);
            preparedStatement.setString(6, Global.caseMonth);
            preparedStatement.setString(7, Global.caseNumber);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
