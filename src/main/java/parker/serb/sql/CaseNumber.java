package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    
    
//    public static void createTable() {
//        Statement stmt = null;
//        try {
//            
//            stmt = Database.connectToDB().createStatement();
//            
//            String sql = "CREATE TABLE Activity" +
//                    "(id int IDENTITY (1,1) NOT NULL, " +
//                    " caseNumber varchar(16) NOT NULL, " + 
//                    " userID varchar(1), " +
//                    " date datetime NOT NULL, " +
//                    " action text NOT NULL, " +
//                    " PRIMARY KEY (id))"; 
//            
//            stmt.executeUpdate(sql);
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                stmt.close();
//            } catch (SQLException ex) {
//                Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
    /**
     * Gets the next case number to be used for case type.  If nothing is found
     * the default returned is 1
     * @param caseType the type of the case
     * @param year the year the case is added
     * @return returns the next number of the case type
     */
    public static String getCaseNumber(String caseType, String year) {
        String nextNumber = "0001";
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select caseNumber from CaseNumber where caseType = ? and year = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseType);
            preparedStatement.setString(2, year);

            ResultSet caseNumber = preparedStatement.executeQuery();
            
            if(caseNumber.next()) {
                nextNumber = caseNumber.getString("caseNumber");
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextNumber;
    }
    
    public static String getCMDSCaseNumber(String year) {
        String nextNumber = "0001";
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select caseNumber from CaseNumber where year = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);

            ResultSet caseNumber = preparedStatement.executeQuery();
            
            if(caseNumber.next()) {
                nextNumber = caseNumber.getString("caseNumber");
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextNumber;
    }
    
    public static String getORGNumber() {
        String nextNumber = null;
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select caseNumber from CaseNumber where caseType = 'ORG'";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumber = preparedStatement.executeQuery();
            
            if(caseNumber.next()) {
                nextNumber = caseNumber.getString("caseNumber");
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextNumber;
    }
    
    public static String getCSCNumber() {
        String nextNumber = null;
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select caseNumber from CaseNumber where caseType = 'CSC'";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumber = preparedStatement.executeQuery();
            
            if(caseNumber.next()) {
                nextNumber = caseNumber.getString("caseNumber");
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return nextNumber;
    }
    
    /**
     * Update the case number to the next number.  This will reflect a new case
     * number has been created
     * @param caseNumber the case number that was created
     */
    public static void updateNextCaseNumber(String caseYear, String caseType, String caseNumber) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateNextCMDSCaseNumber(String caseYear, String caseNumber) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateOrgCaseNumber(String orgNumber) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update CaseNumber set caseNumber = ? where caseType = 'ORG'";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(Integer.parseInt(orgNumber) + 1));

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateCSCCaseNumber(String orgNumber) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update CaseNumber set caseNumber = ? where caseType = 'CSC'";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, String.valueOf(Integer.parseInt(orgNumber) + 1));

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Creates an entry for an instance when a year and case type are not found
     * @param year the year of the case
     * @param type the type of the case
     */
    private static void createCaseNumber(String year, String type) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert into CaseNumber Values (?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, "2");

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void createCaseNumber(String year) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert into CaseNumber Values (?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, "2");

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
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
}
