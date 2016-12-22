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
public class HearingOutcome {
    
    public int id;
    public boolean active;
    public String type;
    public String description;
    
    /**
     * Loads all activities without a limited result
     * @return list of all Activities per case
     */
    public static List loadOutcomesByType(String section) {
        List<HearingOutcome> activityTypeList = new ArrayList<HearingOutcome>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from HearingOutcome"
                    + " where type = ?"
                    + " order by description asc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);

            ResultSet activityTypeListRS = preparedStatement.executeQuery();
            
            while(activityTypeListRS.next()) {
                HearingOutcome activityType = new HearingOutcome();
                activityType.id = activityTypeListRS.getInt("id");
                activityType.active = activityTypeListRS.getBoolean("active");
                activityType.type = activityTypeListRS.getString("type");
                activityType.description = activityTypeListRS.getString("description");
                activityTypeList.add(activityType);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return activityTypeList;
    }
    
//    public static String getFullType(String typeAbbrv) {
//        String fullType = "";
//        
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "select * from ActivityType"
//                    + " where descriptionAbbrv = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, typeAbbrv);
//
//            ResultSet activityTypeListRS = preparedStatement.executeQuery();
//            
//            while(activityTypeListRS.next()) {
//                fullType = activityTypeListRS.getString("descriptionFull");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return fullType;
//    }
//    
//    public static String getTypeAbbrv(String typeFull) {
//        String typeAbbrv = "";
//        
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "select * from ActivityType"
//                    + " where descriptionFull = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, typeFull);
//
//            ResultSet activityTypeListRS = preparedStatement.executeQuery();
//            
//            while(activityTypeListRS.next()) {
//                typeAbbrv = activityTypeListRS.getString("descriptionAbbrv");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return typeAbbrv;
//    }
//    
//    
//    
//    
//    public static List searchActivityType(String[] param) {
//        List<HearingOutcome> list = new ArrayList<>();
//
//        try {
//
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "SELECT * FROM ActivityType";
//            if (param.length > 0) {
//                sql += " WHERE";
//                for (int i = 0; i < param.length; i++) {
//                    if (i > 0) {
//                        sql += " AND";
//                    }
//                    sql += " CONCAT(section, descriptionAbbrv, descriptionFull) LIKE ?";
//                }
//            }
//            sql += " ORDER BY section, descriptionAbbrv";
//
//            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
//
//            for (int i = 0; i < param.length; i++) {
//                ps.setString((i + 1), "%" + param[i].trim() + "%");
//            }
//            
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                HearingOutcome item = new HearingOutcome();
//                item.id = rs.getInt("id");
//                item.active = rs.getBoolean("active");
//                item.section = rs.getString("section");
//                item.descriptionAbbrv = rs.getString("descriptionAbbrv") == null ? "" : rs.getString("descriptionAbbrv");
//                item.descriptionFull = rs.getString("descriptionFull") == null ? "" : rs.getString("descriptionFull");
//                list.add(item);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return list;
//    }
//
//    public static HearingOutcome getActivityTypeByID(int id) {
//        HearingOutcome item = new HearingOutcome();
//
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "SELECT * FROM ActivityType WHERE id = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setInt(1, id);
//
//            ResultSet rs = preparedStatement.executeQuery();
//
//            while (rs.next()) {
//                item.id = rs.getInt("id");
//                item.active = rs.getBoolean("active");
//                item.section = rs.getString("section");
//                item.descriptionAbbrv = rs.getString("descriptionAbbrv") == null ? "" : rs.getString("descriptionAbbrv");
//                item.descriptionFull = rs.getString("descriptionFull") == null ? "" : rs.getString("descriptionFull");
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return item;
//    }
//
//    public static void createActivityType(HearingOutcome item) {
//        try {
//
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Insert INTO ActivityType ("
//                    + "active, "
//                    + "section, "
//                    + "descriptionAbbrv, "
//                    + "descriptionFull "
//                    + ") VALUES (";
//                    for(int i=0; i<3; i++){
//                        sql += "?, ";   //01-12
//                    }
//                     sql += "?)";   //13
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setBoolean(1, true);
//            preparedStatement.setString(2, item.section.equals("") ? null : item.section);
//            preparedStatement.setString(3, item.descriptionAbbrv.equals("") ? null : item.descriptionAbbrv);
//            preparedStatement.setString(4, item.descriptionFull.equals("") ? null : item.descriptionFull);
//            preparedStatement.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//
//    public static void updateActivityType(HearingOutcome item) {
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "UPDATE ActivityType SET "
//                    + "active = ?, "
//                    + "section = ?, "
//                    + "descriptionAbbrv = ?, "
//                    + "descriptionFull = ? "
//                    + "where id = ?";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setBoolean(1, item.active);
//            preparedStatement.setString(2, item.section.equals("") ? null : item.section);
//            preparedStatement.setString(3, item.descriptionAbbrv.equals("") ? null : item.descriptionAbbrv);
//            preparedStatement.setString(4, item.descriptionFull.equals("") ? null : item.descriptionFull);
//            preparedStatement.setInt(5, item.id);
//
//            preparedStatement.executeUpdate();
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
    
}
