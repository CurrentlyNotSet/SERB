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
import parker.serb.util.SlackNotification;

/**
 *
 * @author User
 */
public class UserRole {
    
    public int userID;
    public int roleID;
    public String roleName;
    
    public static List<UserRole> loadRolesByUser(int ID) {
        List<UserRole> roleList = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from UserRole LEFT JOIN Role on UserRole.roleID = Role.id where userID = ? ORDER BY role";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, ID);
            
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                UserRole act = new UserRole();
                act.userID = rs.getInt("userID");
                act.roleID = rs.getInt("roleID");
                act.roleName = rs.getString("role");
                roleList.add(act);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                loadRolesByUser(ID);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return roleList;
    }
    
    public static void addRole(UserRole item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO UserRole ("
                    + "userID, "
                    + "RoleID"
                    + ") VALUES ("
                    + "?, "
                    + "?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, item.userID);
            preparedStatement.setInt(2, item.roleID);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                addRole(item);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
    public static void removeRole(int userID, int roleID) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "DELETE FROM UserRole WHERE userID = ? AND RoleID = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            preparedStatement.setInt(2, roleID);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                removeRole(userID, roleID);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
}
