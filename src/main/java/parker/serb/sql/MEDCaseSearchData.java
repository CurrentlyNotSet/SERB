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

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from MEDCaseSearch ORDER BY caseYear DESC, caseMonth DESC, caseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while(caseActivity.next()) {
                MEDCaseSearchData repCase = new MEDCaseSearchData();
                repCase.id = caseActivity.getInt("id");
                repCase.caseYear = caseActivity.getString("caseYear");
                repCase.caseType = caseActivity.getString("caseType");
                repCase.caseMonth = caseActivity.getString("caseMonth");
                repCase.caseNumber = caseActivity.getString("caseNumber");
                repCase.employerName = caseActivity.getString("employerName") == null ? "" : caseActivity.getString("employerName");
                repCase.unionName = caseActivity.getString("unionName") == null ? "" : caseActivity.getString("unionName");
                repCase.county = caseActivity.getString("county") == null ? "" : caseActivity.getString("county");
                repCase.employerID = caseActivity.getString("employerID") == null ? "" : caseActivity.getString("employerID");
                repCase.bunNumber = caseActivity.getString("bunNumber") == null ? "" : caseActivity.getString("bunNumber");
                ulpCaseList.add(repCase);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadMEDCaseList();
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

            String sql = "INSERT INTO MEDCaseSearch (caseYear, caseType, caseMonth, caseNumber) VALUES (?,?,?,?)";

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

    public static void updateCaseEntryFromParties(String employer, String unionName) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseEntryFromParties(employer, unionName);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateCaseEntryFromCaseStatus(
            String employerID, String bunnumber)
    {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseEntryFromCaseStatus(employerID, bunnumber);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
