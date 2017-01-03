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
public class Role {
    
    public int id;
    public String active;
    public String role;
    
    public static List<Role> loadActiveRoles() {
        List<Role> roleList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Role WHERE active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                Role act = new Role();
                act.id = rs.getInt("id");
                act.active = rs.getString("active");
                act.role = rs.getString("role");
                roleList.add(act);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadActiveRoles();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return roleList;
    }
}
