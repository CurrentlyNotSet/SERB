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
 * @author User
 */
public class HearingType {
    
    public int id;
    public boolean active;
    public String section;
    public String hearingType;
    public String hearingDescription;
    
    public static List<HearingType> loadAllHearingTypes(String[] param) {
        List<HearingType> list = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingType";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(section, hearingType, hearingDescription) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY hearingType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HearingType item = new HearingType();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.hearingType = rs.getString("hearingType") == null ? "" : rs.getString("hearingType");
                item.hearingDescription = rs.getString("hearingDescription") == null ? "" : rs.getString("hearingDescription");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllHearingTypes(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
    public static HearingType getHearingTypeByID(int id) {
        HearingType item = new HearingType();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingType WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section").trim();
                item.hearingType = rs.getString("hearingType") == null ? "" : rs.getString("hearingType").trim();
                item.hearingDescription = rs.getString("hearingDescription") == null ? "" : rs.getString("hearingDescription").trim();
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getHearingTypeByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static void createHearingType(HearingType item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO HearingType "
                    + "(active, section, hearingType, hearingDescription)"
                    + " VALUES "
                    + "(1, ?, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.section.equals("") ? null : item.section.trim());
            preparedStatement.setString(2, item.hearingType.equals("") ? null : item.hearingType.trim());
            preparedStatement.setString(3, item.hearingDescription.equals("") ? null : item.hearingDescription.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createHearingType(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateHearingType(HearingType item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE HearingType SET "
                    + "active = ?, "
                    + "section = ?, "
                    + "hearingType = ?, "
                    + "hearingDescription = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.section.equals("") ? null : item.section.trim());
            preparedStatement.setString(3, item.hearingType.equals("") ? null : item.hearingType.trim());
            preparedStatement.setString(4, item.hearingDescription.equals("") ? null : item.hearingDescription.trim());
            preparedStatement.setInt(5, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateHearingType(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static List<HearingType> loadActiveHearingTypesBySection(String section) {
        List<HearingType> list = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingType WHERE Active = 1 AND Section = ? ORDER BY hearingType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

                ps.setString(1, section);
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HearingType item = new HearingType();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.hearingType = rs.getString("hearingType") == null ? "" : rs.getString("hearingType");
                item.hearingDescription = rs.getString("hearingDescription") == null ? "" : rs.getString("hearingDescription");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadActiveHearingTypesBySection(section);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
}
