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
public class CMDSCaseSearchData {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String dateOpen;
    public String appellant;
    public String appellee;
    public String alj;
    
    public static List loadCMDSCaseList() {
        List<CMDSCaseSearchData> ulpCaseList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from CMDSCaseSearch ORDER BY id DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                CMDSCaseSearchData repCase = new CMDSCaseSearchData();
                repCase.id = caseActivity.getInt("id");
                repCase.caseYear = caseActivity.getString("caseYear");
                repCase.caseType = caseActivity.getString("caseType");
                repCase.caseMonth = caseActivity.getString("caseMonth");
                repCase.caseNumber = caseActivity.getString("caseNumber");
                repCase.dateOpen = caseActivity.getTimestamp("dateOpened") == null ? "" : Global.mmddyyyy.format(new Timestamp(caseActivity.getTimestamp("dateOpened").getTime()));
                repCase.appellant = caseActivity.getString("appellant") == null ? "" : caseActivity.getString("appellant");
                repCase.appellee = caseActivity.getString("appellee") == null ? "" : caseActivity.getString("appellee");
                repCase.alj = caseActivity.getString("alj") == null ? "" : caseActivity.getString("alj");
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

            String sql = "INSERT INTO CMDSCaseSearch (caseYear, caseType, caseMonth, caseNumber, dateOpened) VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, month);
            preparedStatement.setString(4, number);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateCaseEntryFromParties(String appellant, String appellee) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE CMDSCaseSearch SET"
                    + " appellant = ?,"
                    + " appellee = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, appellant);
            preparedStatement.setString(2, appellee);
            preparedStatement.setString(3, Global.caseYear);
            preparedStatement.setString(4, Global.caseType);
            preparedStatement.setString(5, Global.caseMonth);
            preparedStatement.setString(6, Global.caseNumber);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateCaseEntryFromCaseInformation(
            Date dateOpened, String alj) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE CMDSCaseSearch SET"
                    + " dateOpened = ?,"
                    + " alj = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, dateOpened == null ? null : new Timestamp(dateOpened.getTime()));
            preparedStatement.setString(2, alj);
            preparedStatement.setString(3, Global.caseYear);
            preparedStatement.setString(4, Global.caseType);
            preparedStatement.setString(5, Global.caseMonth);
            preparedStatement.setString(6, Global.caseNumber);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
