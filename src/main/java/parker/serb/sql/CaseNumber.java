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
public class CaseNumber {
    
    public int id;
    public String user;
    public String action;
    public String date;
    public String filePath;
    
    /**
     * Gets the next case number to be used for case type.  If nothing is found
     * the default returned is 1
     * @param caseType the type of the case
     * @param year the year the case is added
     * @return returns the next number of the case type
     */
    public static String getCaseNumber(String caseType, String year) {
        String nextNumber = "0001";
            
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select caseNumber from CaseNumber where caseType = ? and year = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseType);
            preparedStatement.setString(2, year);

            ResultSet caseNumber = preparedStatement.executeQuery();
            
            if(caseNumber.next()) {
                nextNumber = caseNumber.getString("caseNumber");
            } 
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getCaseNumber(caseType, year);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return nextNumber;
    }
    
    public static String getCMDSCaseNumber(String year) {
        String nextNumber = "0001";
            
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select caseNumber from CaseNumber where year = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);

            ResultSet caseNumber = preparedStatement.executeQuery();
            
            if(caseNumber.next()) {
                nextNumber = caseNumber.getString("caseNumber");
            } 
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getCMDSCaseNumber(year);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return nextNumber;
    }
    
    public static String getORGNumber() {
        String nextNumber = null;
            
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select caseNumber from CaseNumber where caseType = 'ORG'";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumber = preparedStatement.executeQuery();
            
            if(caseNumber.next()) {
                nextNumber = caseNumber.getString("caseNumber");
            } 
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getORGNumber();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return nextNumber;
    }
    
    public static String getCSCNumber() {
        String nextNumber = null;
            
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select caseNumber from CaseNumber where caseType = 'CSC'";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumber = preparedStatement.executeQuery();
            
            if(caseNumber.next()) {
                nextNumber = caseNumber.getString("caseNumber");
            } 
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getCSCNumber();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return nextNumber;
    }
    
    /**
     * Update the case number to the next number.  This will reflect a new case
     * number has been created
     * @param caseNumber the case number that was created
     */
    public static void updateNextCaseNumber(String caseYear, String caseType, String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CaseNumber set caseNumber = ? where"
                    + " year = ? and caseType = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber);
            preparedStatement.setString(2, caseYear);
            preparedStatement.setString(3, caseType);

            int success = preparedStatement.executeUpdate();
            
            if(success == 0) {
                createCaseNumber(caseYear, caseType);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateNextCaseNumber(caseYear, caseType, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateNextCMDSCaseNumber(String caseYear, String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CaseNumber set caseNumber = ? where"
                    + " year = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber);
            preparedStatement.setString(2, caseYear);

            int success = preparedStatement.executeUpdate();
            
            if(success == 0) {
                createCaseNumber(caseYear);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateNextCMDSCaseNumber(caseYear, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateOrgCaseNumber(String orgNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CaseNumber set caseNumber = ? where caseType = 'ORG'";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(Integer.parseInt(orgNumber) + 1));

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateOrgCaseNumber(orgNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCSCCaseNumber(String orgNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CaseNumber set caseNumber = ? where caseType = 'CSC'";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(Integer.parseInt(orgNumber) + 1));

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCSCCaseNumber(orgNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    /**
     * Creates an entry for an instance when a year and case type are not found
     * @param year the year of the case
     * @param type the type of the case
     */
    private static void createCaseNumber(String year, String type) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert into CaseNumber Values (?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, "2");

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createCaseNumber(year, type);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    private static void createCaseNumber(String year) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert into CaseNumber Values (?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, "2");

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createCaseNumber(year);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static String validateULPCaseNumber(String[] caseNumbers) {
        boolean valid = true;
        String caseNumber = "";
        
        for(int i = 0; i < caseNumbers.length; i++) {
            valid = ULPCase.validateCaseNumber(caseNumbers[i].trim());
            
            if(!valid) {
                valid = false;
                caseNumber = caseNumbers[i].trim();
                break;
            } 
        }
        return caseNumber;
    }
    
    public static String validateREPCaseNumber(String[] caseNumbers) {
        boolean valid = true;
        String caseNumber = "";
        
        for(int i = 0; i < caseNumbers.length; i++) {
            valid = REPCase.validateCaseNumber(caseNumbers[i].trim());
            
            if(!valid) {
                valid = false;
                caseNumber = caseNumbers[i].trim();
                break;
            } 
        }
        return caseNumber;
    }
}
