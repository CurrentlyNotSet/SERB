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
public class FactFinder {
    
    public int id;
    public boolean active;
    public String status;
    public String firstName;
    public String middleName;
    public String lastName;
    public String address1;
    public String address2;
    public String address3;
    public String city;
    public String state;
    public String zip;
    public String email;
    public String phone;
    
//    /**
//     * Add an activity to the activity table, pulls the case number from the
//     * current selected case
//     * @param action the action that has been preformed
//     * @param filePath the filepath of a document - null if no file
//     */
//    public static void addActivty(String action, String filePath) {
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?)";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, Global.caseYear);
//            preparedStatement.setString(2, Global.caseType);
//            preparedStatement.setString(3, Global.caseMonth);
//            preparedStatement.setString(4, Global.caseNumber);
//            preparedStatement.setInt(5, Global.activeUser.id);
//            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
//            preparedStatement.setString(7, action);
//            preparedStatement.setString(8, filePath);
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            DbUtils.closeQuietly(stmt);
//        }
//    }
    
//    /**
//     * Creates activity entry when new cases are created
//     * @param caseNumber the new case number
//     */
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
    
//    /**
//     * Loads all activity for a specified case number, pulls the case number
//     * from global
//     * @param searchTerm term to limit the search results
//     * @return List of Activities
//     */
//    public static List loadCaseNumberActivity(String searchTerm) {
//        List<ActivityType> activityList = new ArrayList<>();
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
//                ActivityType act = new ActivityType();
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
    
    /**
     * Loads all activities without a limited result
     * @return list of all Activities per case
     */
    public static List loadAllConciliators() {
        List<String> factFinderList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from FactFinder"
                    + " where status = 'C'"
                    + " and active = 1"
                    + " order by lastName asc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, section);

            ResultSet factFinderListRS = preparedStatement.executeQuery();
            
            while(factFinderListRS.next()) {
                String name = factFinderListRS.getString("firstName") == null ? "" : factFinderListRS.getString("firstName");
                name += factFinderListRS.getString("middleName") == null ? "" : " " + factFinderListRS.getString("middleName");
                name += factFinderListRS.getString("lastName") == null ? "" : " " + factFinderListRS.getString("lastName");
                factFinderList.add(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return factFinderList;
    }
    
    /**
     * Loads all activities without a limited result
     * @return list of all Activities per case
     */
    public static List loadAllFF() {
        List<String> factFinderList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from FactFinder"
                    + " where status != 'O'"
                    + " and active = 1"
                    + " order by lastName asc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet factFinderListRS = preparedStatement.executeQuery();
            
            while(factFinderListRS.next()) {
                String name = factFinderListRS.getString("firstName") == null ? "" : factFinderListRS.getString("firstName");
                name += factFinderListRS.getString("middleName") == null ? "" : " " + factFinderListRS.getString("middleName");
                name += factFinderListRS.getString("lastName") == null ? "" : " " + factFinderListRS.getString("lastName");
                factFinderList.add(name);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return factFinderList;
    }
    
    public static String getFullType(String typeAbbrv) {
        String fullType = "";
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from ActivityType"
                    + " where descriptionAbbrv = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, typeAbbrv);

            ResultSet activityTypeListRS = preparedStatement.executeQuery();
            
            while(activityTypeListRS.next()) {
                fullType = activityTypeListRS.getString("descriptionFull");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return fullType;
    }
    
    public static String getTypeAbbrv(String typeFull) {
        String typeAbbrv = "";
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from ActivityType"
                    + " where descriptionFull = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, typeFull);

            ResultSet activityTypeListRS = preparedStatement.executeQuery();
            
            while(activityTypeListRS.next()) {
                typeAbbrv = activityTypeListRS.getString("descriptionAbbrv");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return typeAbbrv;
    }
}