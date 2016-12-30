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
public class SystemErrorEmailList {
    
    public int id;
    public boolean active;
    public String firstName;
    public String lastName;
    public String emailAddress;
    
    public static List<SystemErrorEmailList> loadAllErrorEmailList(String[] param) {
        List<SystemErrorEmailList> list = new ArrayList<>();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SystemErrorEmailList";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(firstName, lastName, emailAddress) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY lastName";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SystemErrorEmailList item = new SystemErrorEmailList();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                item.emailAddress = rs.getString("emailAddress") == null ? "" : rs.getString("emailAddress");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllErrorEmailList(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
    public static SystemErrorEmailList getErrorEmailListByID(int id) {
        SystemErrorEmailList item = new SystemErrorEmailList();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SystemErrorEmailList WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                item.emailAddress = rs.getString("emailAddress") == null ? "" : rs.getString("emailAddress");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getErrorEmailListByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static void createErrorEmailUser(SystemErrorEmailList item) {
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO SystemErrorEmailList "
                    + "(active, firstName, lastName, emailAddress)"
                    + " VALUES "
                    + "(1, ?, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.firstName.equals("") ? null : item.firstName.trim());
            preparedStatement.setString(2, item.lastName.equals("") ? null : item.lastName.trim());
            preparedStatement.setString(3, item.emailAddress.equals("") ? null : item.emailAddress.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createErrorEmailUser(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateErrorEmailUser(SystemErrorEmailList item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE SystemErrorEmailList SET "
                    + "active = ?, "
                    + "firstName = ?, "
                    + "lastName = ?, "
                    + "emailAddress = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.firstName.equals("") ? null : item.firstName.trim());
            preparedStatement.setString(3, item.lastName.equals("") ? null : item.lastName.trim());
            preparedStatement.setString(4, item.emailAddress.equals("") ? null : item.emailAddress.trim());
            preparedStatement.setInt(5, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateErrorEmailUser(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
        
}
