package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @return List of Activities
     */
    public static List loadAllREPRecommendations() {
        List<REPRecommendation> recommendationList = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                loadAllREPRecommendations();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return recommendationList;
    }
    
    public static List searchREPRecommendations(String[] param) {
        List<REPRecommendation> recommendationList = new ArrayList<>();

        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                searchREPRecommendations(param);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return recommendationList;
    }

    public static REPRecommendation getREPReccomendationByID(int id) {
        REPRecommendation item = new REPRecommendation();

        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                getREPReccomendationByID(id);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return item;
    }

    public static void createREPRec(REPRecommendation item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO REPRecommendation "
                    + "(active, type, recommendation)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.type.equals("") ? null : item.type.trim());
            preparedStatement.setString(2, item.recommendation.equals("") ? null : item.recommendation.trim());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                createREPRec(item);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }

    public static void updateREPRec(REPRecommendation item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                updateREPRec(item);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
}
