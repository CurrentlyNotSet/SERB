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
public class ReleaseNotes {
    
    public int id;
    public String version;
    public String releaseNotes;
    public String releaseDate;
    
    /**
     * Creates an empty Activity Table
     */
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
     * Add an activity to the activity table, pulls the case number from the
     * current selected case
     * @param action the action that has been preformed
     * @param filePath the filepath of a document - null if no file
     */
//    public static void addActivty(String action, String filePath) {
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "Insert INTO Activity VALUES (?,?,?,?,?)";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, Global.caseNumber);
//            preparedStatement.setInt(2, Global.activeUser.id);
//            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
//            preparedStatement.setString(4, action);
//            preparedStatement.setString(5, filePath);
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
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
//            String sql = "Insert INTO Activity VALUES (?,?,?,?,?)";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, caseNumber);
//            preparedStatement.setInt(2, Global.activeUser.id);
//            preparedStatement.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
//            preparedStatement.setString(4, "Case Created");
//            preparedStatement.setString(5, "");
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @param searchTerm term to limit the search results
     * @return List of Activities
     */
    public static List loadReleasedVersions() {
        List<String> releaseList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select version from ReleaseNotes order by id DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet release = preparedStatement.executeQuery();
            
            while(release.next()) {
//                ReleaseNotes act = new ReleaseNotes();
//                act.id = release.getInt("id");
//                act.version = release.getString("firstName");
//                act.date = Global.mmddyyyy.format(new Date(release.getTimestamp("date").getTime()));
//                act.version = release.getString("version");
                releaseList.add(release.getString("version"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return releaseList;
    }
    
    public static ReleaseNotes loadReleaseInformation(String version) {
        ReleaseNotes releaseInformation = new ReleaseNotes();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from ReleaseNotes where version = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, version);

            ResultSet release = preparedStatement.executeQuery();
            
            while(release.next()) {
                releaseInformation.id = release.getInt("id");
                releaseInformation.version = release.getString("version");
                releaseInformation.releaseDate = Global.mmddyyyy.format(new Date(release.getTimestamp("releaseDate").getTime()));
                releaseInformation.releaseNotes = release.getString("releaseNotes");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return releaseInformation;
    }
    
    /**
     * Loads all activities without a limited result
     * @return list of all Activities per case
     */
//    public static List loadAllActivity() {
//        List<ReleaseNotes> activityList = new ArrayList<ReleaseNotes>();
//        
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "select Activity.id,"
//                    + " caseNumber,"
//                    + " date,"
//                    + " action,"
//                    + " firstName,"
//                    + " lastName"
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
//                ReleaseNotes act = new ReleaseNotes();
//                act.id = caseActivity.getInt("id");
//                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
//                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
//                act.action = caseActivity.getString("action");
//                act.caseNumber = caseActivity.getString("caseNumber");
//                activityList.add(act);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return activityList;
//    }
}
