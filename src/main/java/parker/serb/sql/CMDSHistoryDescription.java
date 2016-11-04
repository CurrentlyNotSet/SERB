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
public class CMDSHistoryDescription {
    
    public int id;
    public boolean active;
    public String category;
    public String description;
    
    public static List<CMDSHistoryDescription> loadAllStatusTypes(String[] param) {
        List<CMDSHistoryDescription> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSHistoryDescription";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(category, description) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY category";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CMDSHistoryDescription item = new CMDSHistoryDescription();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.category = rs.getString("category") == null ? "" : rs.getString("category");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static CMDSHistoryDescription getStatusByID(int id) {
        CMDSHistoryDescription item = new CMDSHistoryDescription();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSHistoryDescription WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.category = rs.getString("category") == null ? "" : rs.getString("category").trim();
                item.description = rs.getString("description") == null ? "" : rs.getString("description").trim();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public static void createStatusType(CMDSHistoryDescription item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CMDSHistoryDescription "
                    + "(active, category, description)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.category.equals("") ? null : item.category.trim());
            preparedStatement.setString(2, item.description.equals("") ? null : item.description.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateStatusType(CMDSHistoryDescription item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE CMDSHistoryDescription SET "
                    + "active = ?, "
                    + "category = ?, "
                    + "description = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.category.equals("") ? null : item.category.trim());
            preparedStatement.setString(3, item.description.equals("") ? null : item.description.trim());
            preparedStatement.setInt(4, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}