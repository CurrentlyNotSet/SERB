package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.SlackNotification;

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

    /**
     * Loads all activities without a limited result
     * @param section
     * @return list of all Activities per case
     */
    public static List loadAllActivityTypeBySection(String section) {
        List<ActivityType> activityTypeList = new ArrayList<>();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllActivityTypeBySection(section);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityTypeList;
    }

    public static List loadActiveActivityTypeBySection(String section) {
        List<ActivityType> activityTypeList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from ActivityType"
                    + " where Section = ? AND active = 1"
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadActiveActivityTypeBySection(section);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityTypeList;
    }

    public static String getFullType(String typeAbbrv, String section) {
        String fullType = "";

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from ActivityType"
                    + " where descriptionAbbrv = ? AND "
                    + " section = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, typeAbbrv);
            preparedStatement.setString(2, section);

            ResultSet activityTypeListRS = preparedStatement.executeQuery();

            while(activityTypeListRS.next()) {
                fullType = activityTypeListRS.getString("descriptionFull");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getFullType(typeAbbrv, section);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getTypeAbbrv(typeFull);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return typeAbbrv;
    }




    public static List searchActivityType(String[] param) {
        List<ActivityType> list = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                searchActivityType(param);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }

    public static ActivityType getActivityTypeByID(int id) {
        ActivityType item = new ActivityType();

        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getActivityTypeByID(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static void createActivityType(ActivityType item) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createActivityType(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateActivityType(ActivityType item) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateActivityType(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}