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
public class ULPRecommendation {

    public int id;
    public boolean active;
    public String code;
    public String description;

    public static List loadAllULPRecommendations() {
        List<ULPRecommendation> recommendationList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from ULPRecommendation ORDER BY code";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                ULPRecommendation act = new ULPRecommendation();
                act.id = caseActivity.getInt("id");
                act.active = caseActivity.getBoolean("active");
                act.code = caseActivity.getString("code");
                act.description = caseActivity.getString("description");
                recommendationList.add(act);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllULPRecommendations();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return recommendationList;
    }

    public static List searchULPRecommendations(String[] param) {
        List<ULPRecommendation> recommendationList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM ULPRecommendation";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(code, description) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY code";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet caseActivity = ps.executeQuery();

            while (caseActivity.next()) {
                ULPRecommendation act = new ULPRecommendation();
                act.id = caseActivity.getInt("id");
                act.active = caseActivity.getBoolean("active");
                act.code = caseActivity.getString("code");
                act.description = caseActivity.getString("description");
                recommendationList.add(act);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                searchULPRecommendations(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return recommendationList;
    }

    public static ULPRecommendation getULPReccomendationByID(int id) {
        ULPRecommendation item = new ULPRecommendation();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM ULPRecommendation WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.code = rs.getString("code") == null ? "" : rs.getString("code").trim();
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getULPReccomendationByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static void createULPRec(ULPRecommendation item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO ULPRecommendation "
                    + "(active, code, description)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.code.equals("") ? null : item.code.trim());
            preparedStatement.setString(2, item.description.equals("") ? null : item.description.trim());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createULPRec(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateULPRec(ULPRecommendation item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE ULPRecommendation SET "
                    + "active = ?, "
                    + "code = ?, "
                    + "description = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.code.equals("") ? null : item.code);
            preparedStatement.setString(3, item.description.equals("") ? null : item.description);
            preparedStatement.setInt(4, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateULPRec(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
