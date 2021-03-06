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
public class REPBoardActionType {
    
    public int id;
    public boolean active;
    public String shortDescription;
    public String longDescription;
        
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @return List of Activities
     */
    public static List loadAllREPBoardActionTypes() {
        List<String> REPBoardActionTypeList = new ArrayList<String>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select shortDescription from REPBoardActionType where active = 1 ORDER BY shortDescription";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                REPBoardActionTypeList.add(caseActivity.getString("shortDescription"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllREPBoardActionTypes();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return REPBoardActionTypeList;
    }
    
    public static List searchREPBoardActionTypes(String[] param) {
        List<REPBoardActionType> recommendationList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM REPBoardActionType";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(shortDescription, longDescription) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY shortDescription";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet caseActivity = ps.executeQuery();

            while (caseActivity.next()) {
                REPBoardActionType act = new REPBoardActionType();
                act.id = caseActivity.getInt("id");
                act.active = caseActivity.getBoolean("active");
                act.shortDescription = caseActivity.getString("shortDescription");
                act.longDescription = caseActivity.getString("longDescription");
                recommendationList.add(act);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                searchREPBoardActionTypes(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return recommendationList;
    }

    public static REPBoardActionType getREPBoardActionTypeByID(int id) {
        REPBoardActionType item = new REPBoardActionType();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM REPBoardActionType WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.shortDescription = rs.getString("shortDescription") == null ? "" : rs.getString("shortDescription").trim();
                item.longDescription = rs.getString("longDescription") == null ? "" : rs.getString("longDescription");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getREPBoardActionTypeByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static void createREPBoardActionType(REPBoardActionType item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO REPBoardActionType "
                    + "(active, shortDescription, longDescription)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.shortDescription.equals("") ? null : item.shortDescription.trim());
            preparedStatement.setString(2, item.longDescription.equals("") ? null : item.longDescription.trim());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createREPBoardActionType(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateREPBoardActionType(REPBoardActionType item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE REPBoardActionType SET "
                    + "active = ?, "
                    + "shortDescription = ?, "
                    + "longDescription = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.shortDescription.equals("") ? null : item.shortDescription);
            preparedStatement.setString(3, item.longDescription.equals("") ? null : item.longDescription);
            preparedStatement.setInt(4, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateREPBoardActionType(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
