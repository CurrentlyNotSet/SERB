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
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class CaseValidation {
    
    /**
     * Add an activity to the activity table, pulls the case number from the
     * current selected case
     * @param action the action that has been preformed
     * @param filePath the filepath of a document - null if no file
     */
    public static boolean validateCaseNumber(String caseNumber) {
        
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
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
        return validCase;
    }
}
