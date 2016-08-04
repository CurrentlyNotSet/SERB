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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return prefixList;
    }
    
    public static NamePrefix getPreFixByID(int id) {
        NamePrefix item = new NamePrefix();
        
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }
    
    public static void createPrefix(NamePrefix item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO NamePrefix (active, prefix) VALUES (1, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.prefix.equals("") ? null : item.prefix.trim());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updatePrefix(NamePrefix item ){
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
