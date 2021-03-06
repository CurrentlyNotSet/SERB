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
public class REPCaseSearchData {

    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String employerName;
    public String bunNumber;
    public String description;
    public String county;
    public String boardDeemed;
    public String employeeOrg;
    public String incumbent;

    public static List loadREPCaseList() {
        List<REPCaseSearchData> ulpCaseList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from REPCaseSearch ORDER BY caseYear DESC, caseMonth DESC, caseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while(caseActivity.next()) {
                REPCaseSearchData repCase = new REPCaseSearchData();
                repCase.id = caseActivity.getInt("id");
                repCase.caseYear = caseActivity.getString("caseYear");
                repCase.caseType = caseActivity.getString("caseType");
                repCase.caseMonth = caseActivity.getString("caseMonth");
                repCase.caseNumber = caseActivity.getString("caseNumber");
                repCase.employerName = caseActivity.getString("employerName") == null ? "" : caseActivity.getString("employerName");
                repCase.bunNumber = caseActivity.getString("bunNumber") == null ? "" : caseActivity.getString("bunNumber");
                repCase.description = caseActivity.getString("description") == null ? "" : caseActivity.getString("description");
                repCase.county = caseActivity.getString("county") == null ? "" : caseActivity.getString("county");
                repCase.boardDeemed = caseActivity.getString("boardDeemed") == null ? "" : caseActivity.getString("boardDeemed");
                repCase.employeeOrg = caseActivity.getString("employeeOrg") == null ? "" : caseActivity.getString("employeeOrg");
                repCase.incumbent = caseActivity.getString("incumbent") == null ? "" : caseActivity.getString("incumbent");
                ulpCaseList.add(repCase);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadREPCaseList();
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

            String sql = "INSERT INTO REPCaseSearch (caseYear, caseType, caseMonth, caseNumber) VALUES (?,?,?,?)";

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

    public static void updateCaseEntryFromParties(String employer, String employeeOrg, String incumbent) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE REPCaseSearch SET"
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseEntryFromParties(employer, employeeOrg, incumbent);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateCaseEntryFromCaseInformation(
            String bunnumber, String county, String boarddeemed)
    {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE REPCaseSearch SET"
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
