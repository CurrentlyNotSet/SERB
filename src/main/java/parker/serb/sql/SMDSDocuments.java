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
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class SMDSDocuments {
    public int id;
    public boolean active;
    public String section;
    public String type;
    public String description;
    public String fileName;
    public int dueDate;
    
    
    
    public static SMDSDocuments findDocumentByID(int id) {
        SMDSDocuments user = new SMDSDocuments();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from SMDSDocuments where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet foundDoc = preparedStatement.executeQuery();
            
            if(foundDoc.next()) {
                user.id = foundDoc.getInt("id");
                user.active = foundDoc.getBoolean("active");
                user.section = foundDoc.getString("section");
                user.type = foundDoc.getString("type");
                user.description = foundDoc.getString("description");
                user.fileName = foundDoc.getString("fileName");
                user.dueDate = foundDoc.getInt("dueDate");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SMDSDocuments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
}
