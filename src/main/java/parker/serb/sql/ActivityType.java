package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author parkerjohnston
 */
public class ActivityType {
    
    public int id;
    public boolean active;
    public String section;
    public String descriptionAbbrv;
    public String descriptionFull;
    
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
    public static List loadAllActivityTypeBySection(String section) {
        List<ActivityType> activityTypeList = new ArrayList<ActivityType>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from ActivityType"
                    + " where Section = ?"
                    + " order by descriptionAbbrv asc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);

            ResultSet activityTypeListRS = preparedStatement.executeQuery();
            
            while(activityTypeListRS.next()) {
                ActivityType activityType = new ActivityType();
                activityType.id = activityTypeListRS.getInt("id");
                activityType.active = activityTypeListRS.getBoolean("active");
                activityType.section = activityTypeListRS.getString("section");
                activityType.descriptionAbbrv = activityTypeListRS.getString("descriptionAbbrv");
                activityType.descriptionFull = activityTypeListRS.getString("descriptionFull");
                activityTypeList.add(activityType);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return activityTypeList;
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
    
    
    
    
    public static List searchActivityType(String[] param) {
        List<ActivityType> list = new ArrayList<>();

        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM ActivityType";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(section, descriptionAbbrv, descriptionFull) LIKE ?";
                }
            }
            sql += " ORDER BY section, descriptionAbbrv";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ActivityType item = new ActivityType();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section");
                item.descriptionAbbrv = rs.getString("descriptionAbbrv") == null ? "" : rs.getString("descriptionAbbrv");
                item.descriptionFull = rs.getString("descriptionFull") == null ? "" : rs.getString("descriptionFull");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    public static ActivityType getActivityTypeByID(int id) {
        ActivityType item = new ActivityType();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM ActivityType WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section");
                item.descriptionAbbrv = rs.getString("descriptionAbbrv") == null ? "" : rs.getString("descriptionAbbrv");
                item.descriptionFull = rs.getString("descriptionFull") == null ? "" : rs.getString("descriptionFull");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public static void createActivityType(ActivityType item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO ActivityType ("
                    + "active, "
                    + "section, "
                    + "descriptionAbbrv, "
                    + "descriptionFull "
                    + ") VALUES (";
                    for(int i=0; i<3; i++){
                        sql += "?, ";   //01-12
                    }
                     sql += "?)";   //13

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, item.section.equals("") ? null : item.section);
            preparedStatement.setString(3, item.descriptionAbbrv.equals("") ? null : item.descriptionAbbrv);
            preparedStatement.setString(4, item.descriptionFull.equals("") ? null : item.descriptionFull);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateActivityType(ActivityType item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE ActivityType SET "
                    + "active = ?, "
                    + "section = ?, "
                    + "descriptionAbbrv = ?, "
                    + "descriptionFull = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.section.equals("") ? null : item.section);
            preparedStatement.setString(3, item.descriptionAbbrv.equals("") ? null : item.descriptionAbbrv);
            preparedStatement.setString(4, item.descriptionFull.equals("") ? null : item.descriptionFull);
            preparedStatement.setInt(5, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
