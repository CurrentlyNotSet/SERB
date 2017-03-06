package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class CaseValidation {

    public static boolean validateCaseNumber(String caseNumber) {
        Statement stmt = null;

        String[] parsedCaseNumber = caseNumber.split("-");

        if(parsedCaseNumber.length < 4) {
            return false;
        }

        boolean validCase = false;
        String sql = null;

        if(caseNumber.contains("ULP")
                || caseNumber.contains("ERC")
                || caseNumber.contains("JWD")) {
            sql = "Select Count(*) AS totalrows from ULPCase where"
                    + " caseYear = ?"
                    + " AND caseType = ?"
                    + " AND caseMonth = ?"
                    + " AND caseNumber = ?";
        } else if(caseNumber.contains("REP")
                || caseNumber.contains("RBT")) {
            sql = "Select Count(*) AS totalrows from REPCase where"
                    + " caseYear = ?"
                    + " AND caseType = ?"
                    + " AND caseMonth = ?"
                    + " AND caseNumber = ?";
        } else if(caseNumber.contains("STK")
                || caseNumber.contains("MED")
                || caseNumber.contains("NCN")
                || caseNumber.contains("COM")) {
            sql = "Select Count(*) AS totalrows from MEDCase where"
                    + " caseYear = ?"
                    + " AND caseType = ?"
                    + " AND caseMonth = ?"
                    + " AND caseNumber = ?";
        } else {
            return false;
        }

        try {
            stmt = Database.connectToDB().createStatement();

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, parsedCaseNumber[0]);
            preparedStatement.setString(2, parsedCaseNumber[1]);
            preparedStatement.setString(3, parsedCaseNumber[2]);
            preparedStatement.setString(4, parsedCaseNumber[3]);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next();

            if(rs.getInt("totalrows") > 0) {
                validCase = true;
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                validateCaseNumber(caseNumber);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }

        return validCase;
    }

    public static boolean validateCaseNumberActiveSection(String caseNumber) {
        Statement stmt = null;

        String[] parsedCaseNumber = caseNumber.split("-");

        if(parsedCaseNumber.length < 4) {
            return false;
        }

        boolean validCase = false;
        String sql = null;
        String sectionTable = "";

        switch (Global.activeSection) {
            case "REP":
                sectionTable = "REPCase";
                break;
            case "ULP":
                sectionTable = "ULPCase";
                break;
            case "MED":
                sectionTable = "MEDCase";
                break;
            case "ORG":
                sectionTable = "ORGCase";
                break;
            case "Hearings":
                sectionTable = "HearingCase";
                break;
            case "Civil Service Commission":
            case "CSC":
                sectionTable = "CSCCase";
                break;
            case "CMDS":
                sectionTable = "CMDSCase";
                break;
            default:
                break;
        }

        sql = "SELECT COUNT(*) AS totalrows FROM " + sectionTable + " WHERE"
                    + " caseYear = ?"
                    + " AND caseType = ?"
                    + " AND caseMonth = ?"
                    + " AND caseNumber = ?";

        try {
            stmt = Database.connectToDB().createStatement();

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, parsedCaseNumber[0]);
            preparedStatement.setString(2, parsedCaseNumber[1]);
            preparedStatement.setString(3, parsedCaseNumber[2]);
            preparedStatement.setString(4, parsedCaseNumber[3]);

            ResultSet rs = preparedStatement.executeQuery();

            rs.next();

            if(rs.getInt("totalrows") > 0) {
                validCase = true;
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                validateCaseNumberActiveSection(caseNumber);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }

        return validCase;
    }
}
