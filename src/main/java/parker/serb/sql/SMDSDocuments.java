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
    public String group;
    public String historyFileName;
    public String historyDescription;
    
    
    
    public static SMDSDocuments findDocumentByID(int id) {
        SMDSDocuments doc = new SMDSDocuments();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from SMDSDocuments where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet foundDoc = preparedStatement.executeQuery();
            
            if(foundDoc.next()) {
                doc.id = foundDoc.getInt("id");
                doc.active = foundDoc.getBoolean("active");
                doc.section = foundDoc.getString("section");
                doc.type = foundDoc.getString("type");
                doc.description = foundDoc.getString("description");
                doc.fileName = foundDoc.getString("fileName");
                doc.dueDate = foundDoc.getInt("dueDate");
                doc.group = foundDoc.getString("group");
                doc.historyFileName = foundDoc.getString("historyFileName");
                doc.historyDescription = foundDoc.getString("historyDescription");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SMDSDocuments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }
    
    public static SMDSDocuments findDocumentByFileName(String fileName) {
        SMDSDocuments doc = new SMDSDocuments();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from SMDSDocuments where fileName = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, fileName);
            
            ResultSet foundDoc = preparedStatement.executeQuery();
            
            if(foundDoc.next()) {
                doc.id = foundDoc.getInt("id");
                doc.active = foundDoc.getBoolean("active");
                doc.section = foundDoc.getString("section");
                doc.type = foundDoc.getString("type");
                doc.description = foundDoc.getString("description");
                doc.fileName = foundDoc.getString("fileName");
                doc.dueDate = foundDoc.getInt("dueDate");
                doc.group = foundDoc.getString("group");
                doc.historyFileName = foundDoc.getString("historyFileName");
                doc.historyDescription = foundDoc.getString("historyDescription");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SMDSDocuments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }
    
    public static SMDSDocuments findDocumentByDescription(String description) {
        SMDSDocuments doc = new SMDSDocuments();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from SMDSDocuments where description = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, description);
            
            ResultSet foundDoc = preparedStatement.executeQuery();
            
            if(foundDoc.next()) {
                doc.id = foundDoc.getInt("id");
                doc.active = foundDoc.getBoolean("active");
                doc.section = foundDoc.getString("section");
                doc.type = foundDoc.getString("type");
                doc.description = foundDoc.getString("description");
                doc.fileName = foundDoc.getString("fileName");
                doc.dueDate = foundDoc.getInt("dueDate");
                doc.group = foundDoc.getString("group");
                doc.historyFileName = foundDoc.getString("historyFileName");
                doc.historyDescription = foundDoc.getString("historyDescription");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SMDSDocuments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }
    
}
