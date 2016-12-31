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
public class CMDSResult {

    public int id;
    public boolean active;
    public String result;
    public String description;
    
    public static List<CMDSResult> loadAllResultTypes(String[] param) {
        List<CMDSResult> list = new ArrayList<>();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSResult";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(result, description) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY result";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CMDSResult item = new CMDSResult();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.result = rs.getString("result") == null ? "" : rs.getString("result");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllResultTypes(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
    public static CMDSResult getResultByID(int id) {
        CMDSResult item = new CMDSResult();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSResult WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.result = rs.getString("result") == null ? "" : rs.getString("result").trim();
                item.description = rs.getString("description") == null ? "" : rs.getString("description").trim();
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getResultByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static void createResultType(CMDSResult item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CMDSResult "
                    + "(active, result, description)"
                    + " VALUES "
                    + "(1, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.result.equals("") ? null : item.result.trim());
            preparedStatement.setString(2, item.description.equals("") ? null : item.description.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createResultType(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateResultType(CMDSResult item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE CMDSResult SET "
                    + "active = ?, "
                    + "result = ?, "
                    + "description = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.result.equals("") ? null : item.result.trim());
            preparedStatement.setString(3, item.description.equals("") ? null : item.description.trim());
            preparedStatement.setInt(4, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateResultType(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static List loadAll() {
        List caseStatusList = new ArrayList<>();
          
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from CMDSResult where active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseStatusRS = preparedStatement.executeQuery();
            
            while(caseStatusRS.next()) {
                caseStatusList.add(caseStatusRS.getString("result"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAll();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseStatusList;
    }
}
