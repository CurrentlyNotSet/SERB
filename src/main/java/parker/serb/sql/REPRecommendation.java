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
public class REPRecommendation {
    
    public int id;
    public boolean active;
    public String type;
    public String recommendation;

    public static List loadAllREPRecommendations() {
        List<REPRecommendation> recommendationList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from REPRecommendation ORDER BY recommendation";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                REPRecommendation act = new REPRecommendation();
                act.id = caseActivity.getInt("id");
                act.type = caseActivity.getString("type");
                act.recommendation = caseActivity.getString("recommendation");
                recommendationList.add(act);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllREPRecommendations();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return recommendationList;
    }
    
    public static List searchREPRecommendations(String[] param) {
        List<REPRecommendation> recommendationList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM REPRecommendation";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(type, recommendation) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY type";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet caseActivity = ps.executeQuery();

            while (caseActivity.next()) {
                REPRecommendation act = new REPRecommendation();
                act.id = caseActivity.getInt("id");
                act.active = caseActivity.getBoolean("active");
                act.type = caseActivity.getString("type");
                act.recommendation = caseActivity.getString("recommendation");
                recommendationList.add(act);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                searchREPRecommendations(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return recommendationList;
    }

    public static REPRecommendation getREPReccomendationByID(int id) {
        REPRecommendation item = new REPRecommendation();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM REPRecommendation WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.type = rs.getString("type") == null ? "" : rs.getString("type").trim();
                item.recommendation = rs.getString("recommendation") == null ? "" : rs.getString("recommendation");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getREPReccomendationByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static void createREPRec(REPRecommendation item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO REPRecommendation "
                    + "(active, type, recommendation)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.type.equals("") ? null : item.type.trim());
            preparedStatement.setString(2, item.recommendation.equals("") ? null : item.recommendation.trim());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createREPRec(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateREPRec(REPRecommendation item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE REPRecommendation SET "
                    + "active = ?, "
                    + "type = ?, "
                    + "recommendation = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.type.equals("") ? null : item.type);
            preparedStatement.setString(3, item.recommendation.equals("") ? null : item.recommendation);
            preparedStatement.setInt(4, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateREPRec(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
