package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;
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
}
