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
 * @author User
 */
public class SystemExecutive {
    
    public int id;
    public boolean active;
    public String department;
    public String position;
    public String firstName;
    public String middleName;
    public String lastName;
    public String phoneNumber;
    public String email;
    
    
    public static List loadExecs(String dept) {
        List execsList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SystemExecutive where active = 1 AND department = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, dept);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                SystemExecutive exec = new SystemExecutive();
                exec.id = rs.getInt("id");
                exec.department = rs.getString("department") == null ? "" : rs.getString("department");
                exec.position = rs.getString("position") == null ? "" : rs.getString("position");
                exec.phoneNumber = rs.getString("phoneNumber") == null ? "" : rs.getString("phoneNumber");
                exec.email = rs.getString("email") == null ? "" : rs.getString("email");
                exec.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                exec.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                exec.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                execsList.add(exec);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return execsList;
    }
}
