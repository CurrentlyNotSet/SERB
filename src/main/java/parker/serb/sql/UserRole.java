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
 * @author parkerjohnston
 */
public class UserRole {
    
    public static void createTable() {
        Statement stmt = null;
        try {
            
            stmt = Database.connectToDB().createStatement();
            
            String sql = "CREATE TABLE UserRole" +
                    "(userID int NOT NULL, " + 
                    " roleID int NOT NULL)"; 
            
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
//    public static void insertDefulatData() {
//        roles.add("Admin");
//        roles.add("Docket");
//        roles.add("REP");
//        roles.add("ULP");
//        roles.add("ORG");
//        roles.add("MED");
//        
//        Statement stmt = null;
//        
//        for(int i = 0; i < roles.size(); i++) {
//            
//            try {
//            
//                stmt = Database.connectToDB().createStatement();
//            
//                String sql = "INSERT INTO Role VALUES"
//                    + "(?,?)";
//            
//                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//                preparedStatement.setBoolean(1, true);
//                preparedStatement.setString(2, roles.get(i).toString());
//
//                int result = preparedStatement.executeUpdate();
//
//                if(result != 1) {
//                    System.out.println("ERROR");
//                }
//            
//            } catch (SQLException ex) {
//                Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
}
