/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
 * @author Andrew
 */
public class CMDSHistoryCategory {
    
    public int id;
    public boolean active;
    public String entryType;
    public String description;
    
    public static List<CMDSHistoryCategory> loadAllCMDSHistoryDescriptions(String[] param) {
        List<CMDSHistoryCategory> list = new ArrayList<>();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSHistoryCategory";
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
                CMDSHistoryCategory item = new CMDSHistoryCategory();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllCMDSHistoryDescriptions(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
    public static List<CMDSHistoryCategory> loadAllHearingHistoryDescriptions(String[] param) {
        List<CMDSHistoryCategory> list = new ArrayList<>();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

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
                CMDSHistoryCategory item = new CMDSHistoryCategory();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllHearingHistoryDescriptions(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
    public static List<CMDSHistoryCategory> loadActiveCMDSHistoryDescriptions() {
        List<CMDSHistoryCategory> list = new ArrayList<>();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSHistoryCategory WHERE active = 1 ORDER BY entryType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CMDSHistoryCategory item = new CMDSHistoryCategory();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadActiveCMDSHistoryDescriptions();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }

    public static List<CMDSHistoryCategory> loadActiveHearingHistoryDescriptions() {
        List<CMDSHistoryCategory> list = new ArrayList<>();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingHistoryCategory WHERE active = 1 ORDER BY entryType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CMDSHistoryCategory item = new CMDSHistoryCategory();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.entryType = rs.getString("entryType") == null ? "" : rs.getString("entryType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadActiveHearingHistoryDescriptions();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
    public static CMDSHistoryCategory getCMDSHistoryDescriptionByID(int id) {
        CMDSHistoryCategory item = new CMDSHistoryCategory();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getCMDSHistoryDescriptionByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static CMDSHistoryCategory getHeaingHistoryDescriptionByID(int id) {
        CMDSHistoryCategory item = new CMDSHistoryCategory();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getHeaingHistoryDescriptionByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }
    
    public static String getCategotyByLetter(String type) {
        String item = "";

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingHistoryCategory WHERE entryType = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, type);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item = rs.getString("entryType") + " - " + rs.getString("description");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getCategotyByLetter(type);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }
    
    public static void createCMDSHistoryCategory(CMDSHistoryCategory item) {
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CMDSHistoryCategory "
                    + "(active, entryType, description)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.entryType.equals("") ? null : item.entryType.trim());
            preparedStatement.setString(2, item.description.equals("") ? null : item.description.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createCMDSHistoryCategory(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateCMDSHistoryCategory(CMDSHistoryCategory item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCMDSHistoryCategory(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
        
    public static void createHearingHistoryCategory(CMDSHistoryCategory item) {
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO HearingHistoryCategory "
                    + "(active, entryType, description)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.entryType.equals("") ? null : item.entryType.trim());
            preparedStatement.setString(2, item.description.equals("") ? null : item.description.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createHearingHistoryCategory(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateHearingHistoryCategory(CMDSHistoryCategory item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateHearingHistoryCategory(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
