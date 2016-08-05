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
public class REPCaseStatus {
    
    public int id;
    public boolean active;
    public String statusType;
    public String status;
       
    public static List loadAll() {
        List caseStatusList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from REPCaseStatus where active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseStatusRS = preparedStatement.executeQuery();
            
            while(caseStatusRS.next()) {
                REPCaseStatus rep = new REPCaseStatus();
                rep.id = caseStatusRS.getInt("id");
                rep.statusType = caseStatusRS.getString("statusType");
                rep.status = caseStatusRS.getString("status");
                caseStatusList.add(rep);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return caseStatusList;
    }
    
    public static List searchREPStatusOptions(String[] param) {
        List<REPCaseStatus> recommendationList = new ArrayList<>();

        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM REPCaseStatus";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(statusType, status) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY statusType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet caseActivity = ps.executeQuery();

            while (caseActivity.next()) {
                REPCaseStatus act = new REPCaseStatus();
                act.id = caseActivity.getInt("id");
                act.active = caseActivity.getBoolean("active");
                act.statusType = caseActivity.getString("statusType");
                act.status = caseActivity.getString("status");
                recommendationList.add(act);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return recommendationList;
    }

    public static REPCaseStatus getREPCaseStatusByID(int id) {
        REPCaseStatus item = new REPCaseStatus();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM REPCaseStatus WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.statusType = rs.getString("statusType") == null ? "" : rs.getString("statusType").trim();
                item.status = rs.getString("status") == null ? "" : rs.getString("status");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public static void createREPStatusOption(REPCaseStatus item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO REPCaseStatus "
                    + "(active, statusType, status)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.statusType.equals("") ? null : item.statusType.trim());
            preparedStatement.setString(2, item.status.equals("") ? null : item.status.trim());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateREPStatusOption(REPCaseStatus item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE REPCaseStatus SET "
                    + "active = ?, "
                    + "statusType = ?, "
                    + "status = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.statusType.equals("") ? null : item.statusType);
            preparedStatement.setString(3, item.status.equals("") ? null : item.status);
            preparedStatement.setInt(4, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
