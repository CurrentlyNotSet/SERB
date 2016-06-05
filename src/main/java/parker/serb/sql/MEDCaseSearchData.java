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
public class MEDCaseSearchData {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String employerName;
    public String unionName;
    public String county;
    public String employerID;
    public String bunNumber;
    
    public static List loadMEDCaseList() {
        List<MEDCaseSearchData> ulpCaseList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from MEDCaseSearch ORDER BY id DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                MEDCaseSearchData repCase = new MEDCaseSearchData();
                repCase.id = caseActivity.getInt("id");
                repCase.caseYear = caseActivity.getString("caseYear");
                repCase.caseType = caseActivity.getString("caseType");
                repCase.caseMonth = caseActivity.getString("caseMonth");
                repCase.caseNumber = caseActivity.getString("caseNumber");
//                repCase.employerName = caseActivity.getString("employerName") == null ? "" : caseActivity.getString("employerName");
//                repCase.bunNumber = caseActivity.getString("bunNumber") == null ? "" : caseActivity.getString("bunNumber");
//                repCase.description = caseActivity.getString("description") == null ? "" : caseActivity.getString("description");
//                repCase.county = caseActivity.getString("county") == null ? "" : caseActivity.getString("county");
//                repCase.boardDeemed = caseActivity.getString("boardDeemed") == null ? "" : caseActivity.getString("boardDeemed");
//                repCase.employeeOrg = caseActivity.getString("employeeOrg") == null ? "" : caseActivity.getString("employeeOrg");
//                repCase.incumbent = caseActivity.getString("incumbent") == null ? "" : caseActivity.getString("incumbent");
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
    
    public static void updateCaseEntryFromParties(String employer, String employeeOrg, String incumbent) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE MEDCaseSearch SET"
                    + " employerName = ?,"
                    + " employeeOrg = ?,"
                    + " incumbent = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, employer);
            preparedStatement.setString(2, employeeOrg);
            preparedStatement.setString(3, incumbent);
            preparedStatement.setString(4, Global.caseYear);
            preparedStatement.setString(5, Global.caseType);
            preparedStatement.setString(6, Global.caseMonth);
            preparedStatement.setString(7, Global.caseNumber);

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
