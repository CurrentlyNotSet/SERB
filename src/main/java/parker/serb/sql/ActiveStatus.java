/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class ActiveStatus {
    
    public static void updateActiveStatus(String tableName, boolean active, int id) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE  " + tableName + " SET active = ? where id = ?";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setBoolean(1, active);
            ps.setInt(2, id);
            
            ps.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
