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
 * @author parkerjohnston
 */
public class NamePrefix {

    public int id;
    public boolean active;
    public String prefix;

    public static List loadActivePrefix() {
        List<String> prefixList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select prefix from NamePrefix where active = 1"
                    + " order by prefix";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet prefixRS = preparedStatement.executeQuery();

            while (prefixRS.next()) {
                prefixList.add(prefixRS.getString("prefix"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadActivePrefix();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return prefixList;
    }

    public static List loadAllPrefix() {
        List<NamePrefix> prefixList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM NamePrefix ORDER BY prefix";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet prefixRS = preparedStatement.executeQuery();

            while (prefixRS.next()) {
                NamePrefix item = new NamePrefix();
                item.id = prefixRS.getInt("id");
                item.active = prefixRS.getBoolean("active");
                item.prefix = prefixRS.getString("prefix");
                prefixList.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllPrefix();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return prefixList;
    }
    
    public static NamePrefix getPreFixByID(int id) {
        NamePrefix item = new NamePrefix();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM NamePrefix WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet prefixRS = preparedStatement.executeQuery();
            
            while(prefixRS.next()) {
                item.id = prefixRS.getInt("id");
                item.active = prefixRS.getBoolean("active");
                item.prefix = prefixRS.getString("prefix");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getPreFixByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }
    
    public static void createPrefix(NamePrefix item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO NamePrefix (active, prefix) VALUES (1, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.prefix.equals("") ? null : item.prefix.trim());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createPrefix(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updatePrefix(NamePrefix item ){
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE NamePrefix SET "
                    + "active = ?, "
                    + "prefix = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.prefix.equals("") ? null : item.prefix);
            preparedStatement.setInt(3, item.id);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updatePrefix(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
