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
public class SystemEmail {
    public int id;
    public boolean active;
    public String section;
    public String emailAddress;
    public String username;
    public String password;
    public String incomingURL;
    public String incomingProtocol;
    public int incomingPort;
    public String incomingFolder;
    public String outgoingURL;
    public String outgoingProtocol;
    public int outgoingPort;
    public String outgoingFolder;
	
    public static List loadEmailAccounts(String[] param) {
        List emailList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SystemEmail";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(section, emailAddress) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY section";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                SystemEmail item = new SystemEmail();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.emailAddress = rs.getString("emailAddress") == null ? "" : rs.getString("emailAddress");
                item.username = rs.getString("username") == null ? "" : rs.getString("username");
                item.password = rs.getString("password") == null ? "" : rs.getString("password");
                item.incomingURL = rs.getString("incomingURL") == null ? "" : rs.getString("incomingURL");
                item.incomingProtocol = rs.getString("incomingProtocol") == null ? "" : rs.getString("incomingProtocol");
                item.incomingPort = rs.getInt("incomingPort");
                item.incomingFolder = rs.getString("incomingFolder") == null ? "" : rs.getString("incomingFolder");
                item.outgoingURL = rs.getString("outgoingURL") == null ? "" : rs.getString("outgoingURL");
                item.outgoingProtocol = rs.getString("outgoingProtocol") == null ? "" : rs.getString("outgoingProtocol");
                item.outgoingPort = rs.getInt("outgoingPort");
                item.outgoingFolder = rs.getString("outgoingFolder") == null ? "" : rs.getString("outgoingFolder");
                emailList.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadEmailAccounts(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }
    
    public static SystemEmail getSystemEmailByID(int id) {
        SystemEmail item = new SystemEmail();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SystemEmail WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.emailAddress = rs.getString("emailAddress") == null ? "" : rs.getString("emailAddress");
                item.username = rs.getString("username") == null ? "" : rs.getString("username");
                item.password = rs.getString("password") == null ? "" : rs.getString("password");
                item.incomingURL = rs.getString("incomingURL") == null ? "" : rs.getString("incomingURL");
                item.incomingProtocol = rs.getString("incomingProtocol") == null ? "" : rs.getString("incomingProtocol");
                item.incomingPort = rs.getInt("incomingPort");
                item.incomingFolder = rs.getString("incomingFolder") == null ? "" : rs.getString("incomingFolder");
                item.outgoingURL = rs.getString("outgoingURL") == null ? "" : rs.getString("outgoingURL");
                item.outgoingProtocol = rs.getString("outgoingProtocol") == null ? "" : rs.getString("outgoingProtocol");
                item.outgoingPort = rs.getInt("outgoingPort");
                item.outgoingFolder = rs.getString("outgoingFolder") == null ? "" : rs.getString("outgoingFolder");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getSystemEmailByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }
    
    public static void updateSystemEmail(SystemEmail item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE SystemEmail SET "
                    + "password = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.password.equals("") ? null : item.password);
            preparedStatement.setInt(2, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateSystemEmail(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
}
