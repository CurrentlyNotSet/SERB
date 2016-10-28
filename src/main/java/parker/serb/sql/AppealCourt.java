/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Andrew
 */
public class AppealCourt {
    
    public int id;
    public boolean active;
    public String type;
    public String courtName;
    
    public static List<AppealCourt> loadAllAppealCourt(String[] param) {
        List<AppealCourt> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM AppealCourt";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(type, courtName) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY type, courtName";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                AppealCourt item = new AppealCourt();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.type = rs.getString("type") == null ? "" : rs.getString("type");
                item.courtName = rs.getString("courtName") == null ? "" : rs.getString("courtName");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static AppealCourt getAppealCourtByID(int id) {
        AppealCourt item = new AppealCourt();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM AppealCourt WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.type = rs.getString("type") == null ? "" : rs.getString("type").trim();
                item.courtName = rs.getString("courtName") == null ? "" : rs.getString("courtName").trim();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public static void createAppealCourt(AppealCourt item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO AppealCourt "
                    + "(active, type, courtName)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.type.equals("") ? null : item.type.trim());
            preparedStatement.setString(2, item.courtName.equals("") ? null : item.courtName.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateAppealCourt(AppealCourt item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE AppealCourt SET "
                    + "active = ?, "
                    + "type = ?, "
                    + "courtName = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.type.equals("") ? null : item.type.trim());
            preparedStatement.setString(3, item.courtName.equals("") ? null : item.courtName.trim());
            preparedStatement.setInt(4, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
