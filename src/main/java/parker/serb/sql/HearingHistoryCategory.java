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
public class HearingHistoryCategory {
    
    public int id;
    public boolean active;
    public String entryType;
    public String description;
    
    public static List<HearingHistoryCategory> loadAllCMDSHistoryDescriptions(String[] param) {
        List<HearingHistoryCategory> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingHistoryCategory";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(entryType, description) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY entryType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HearingHistoryCategory item = new HearingHistoryCategory();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static List<HearingHistoryCategory> loadAllHearingHistoryDescriptions(String[] param) {
        List<HearingHistoryCategory> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingHistoryCategory";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(entryType, description) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY entryType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HearingHistoryCategory item = new HearingHistoryCategory();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static List<HearingHistoryCategory> loadActiveCMDSHistoryDescriptions() {
        List<HearingHistoryCategory> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingHistoryCategory WHERE active = 1 ORDER BY entryType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HearingHistoryCategory item = new HearingHistoryCategory();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static List<HearingHistoryCategory> loadActiveHistoryDescriptions() {
        List<HearingHistoryCategory> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingHistoryCategory WHERE active = 1 ORDER BY entryType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HearingHistoryCategory item = new HearingHistoryCategory();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static HearingHistoryCategory getCMDSHistoryDescriptionByID(int id) {
        HearingHistoryCategory item = new HearingHistoryCategory();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSHistoryCategory WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType").trim();
                item.description = rs.getString("description") == null ? "" : rs.getString("description").trim();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public static HearingHistoryCategory getHearingHistoryDescriptionByID(int id) {
        HearingHistoryCategory item = new HearingHistoryCategory();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingHistoryCategory WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType").trim();
                item.description = rs.getString("description") == null ? "" : rs.getString("description").trim();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }
    
    public static void createCMDSHistoryCategory(HearingHistoryCategory item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CMDSHistoryCategory "
                    + "(active, entryType, description)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.entryType.equals("") ? null : item.entryType.trim());
            preparedStatement.setString(2, item.description.equals("") ? null : item.description.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateCMDSHistoryCategory(HearingHistoryCategory item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE CMDSHistoryCategory SET "
                    + "active = ?, "
                    + "entryType = ?, "
                    + "description = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.entryType.equals("") ? null : item.entryType.trim());
            preparedStatement.setString(3, item.description.equals("") ? null : item.description.trim());
            preparedStatement.setInt(4, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static void createHearingHistoryCategory(HearingHistoryCategory item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO HearingHistoryCategory "
                    + "(active, entryType, description)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.entryType.equals("") ? null : item.entryType.trim());
            preparedStatement.setString(2, item.description.equals("") ? null : item.description.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateHearingHistoryCategory(HearingHistoryCategory item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE HearingHistoryCategory SET "
                    + "active = ?, "
                    + "entryType = ?, "
                    + "description = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.entryType.equals("") ? null : item.entryType.trim());
            preparedStatement.setString(3, item.description.equals("") ? null : item.description.trim());
            preparedStatement.setInt(4, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
