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
    
//    public int id;
//    public String user;
//    public String action;
//    public String date;
//    public String filePath;
//    public String caseYear;
//    public String caseType;
//    public String caseMonth;
//    public String caseNumber;
    
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
    
    /**
     * Creates activity entry when new cases are created
     * @param caseNumber the new case number
     */
//    public static void addNewCaseActivty(String caseNumber) {
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?)";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            String[] parsedCaseNumber = caseNumber.split("-");
//            preparedStatement.setString(1, parsedCaseNumber[0]);
//            preparedStatement.setString(2, parsedCaseNumber[1]);
//            preparedStatement.setString(3, parsedCaseNumber[2]);
//            preparedStatement.setString(4, parsedCaseNumber[3]);
//            preparedStatement.setInt(5, Global.activeUser.id);
//            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
//            preparedStatement.setString(7, "Case Created");
//            preparedStatement.setString(8, "");
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    /**
//     * Loads all activity for a specified case number, pulls the case number
//     * from global
//     * @param searchTerm term to limit the search results
//     * @return List of Activities
//     */
//    public static List loadCaseNumberActivity(String searchTerm) {
//        List<CaseValidation> activityList = new ArrayList<>();
//        
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "select Activity.id,"
//                    + " caseYear,"
//                    + " caseType,"
//                    + " caseMonth,"
//                    + " caseNumber,"
//                    + " date,"
//                    + " action,"
//                    + " firstName,"
//                    + " lastName,"
//                    + " filePath"
//                    + " from Activity"
//                    + " INNER JOIN Users"
//                    + " ON Activity.userID = Users.id"
//                    + " where caseYear = ? and"
//                    + " caseType = ? and"
//                    + " caseMonth = ? and"
//                    + " caseNumber = ? and"
//                    + " (firstName like ? or"
//                    + " lastName like ? or"
//                    + " action like ?) ORDER BY date DESC ";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, Global.caseYear);
//            preparedStatement.setString(2, Global.caseType);
//            preparedStatement.setString(3, Global.caseMonth);
//            preparedStatement.setString(4, Global.caseNumber);
//            preparedStatement.setString(5, "%" + searchTerm + "%");
//            preparedStatement.setString(6, "%" + searchTerm + "%");
//            preparedStatement.setString(7, "%" + searchTerm + "%");
//
//            ResultSet caseActivity = preparedStatement.executeQuery();
//            
//            while(caseActivity.next()) {
//                CaseValidation act = new CaseValidation();
//                act.id = caseActivity.getInt("id");
//                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
//                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
//                act.action = caseActivity.getString("action");
//                act.filePath = caseActivity.getString("filePath");
//                activityList.add(act);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return activityList;
//    }
//    
//    /**
//     * Loads all activities without a limited result
//     * @return list of all Activities per case
//     */
//    public static List loadAllActivity() {
//        List<CaseValidation> activityList = new ArrayList<CaseValidation>();
//        
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "select Activity.id,"
//                    + " caseYear,"
//                    + " caseType,"
//                    + " caseMonth,"
//                    + " caseNumber,"
//                    + " date,"
//                    + " action,"
//                    + " firstName,"
//                    + " lastName,"
//                    + " filePath"
//                    + " from Activity"
//                    + " INNER JOIN Users"
//                    + " ON Activity.userID = Users.id"
//                    + " ORDER BY date DESC ";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//
//            ResultSet caseActivity = preparedStatement.executeQuery();
//            
//            while(caseActivity.next()) {
//                CaseValidation act = new CaseValidation();
//                act.id = caseActivity.getInt("id");
//                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
//                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
//                act.action = caseActivity.getString("action");
//                act.caseYear = caseActivity.getString("caseYear");
//                act.caseType = caseActivity.getString("caseType");
//                act.caseMonth = caseActivity.getString("caseMonth");
//                act.caseNumber = caseActivity.getString("caseNumber");
//                act.filePath = caseActivity.getString("filePath");
//                activityList.add(act);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return activityList;
//    }
}
