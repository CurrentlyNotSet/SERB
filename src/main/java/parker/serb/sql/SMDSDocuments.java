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
    public String CHDCHG;
    public String questionsFileName;
    public String emailSubject;
    public String parameters;
    public String emailBody;
    public double sortOrder;
    
    public static List loadDocumentNamesByTypeAndSection(String section, String type) {
        List<SMDSDocuments> documentList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from SMDSDocuments where"
                    + " section = ? AND"
                    + " type = ? AND"
                    + " active = 1"
                    + " order by cast(description as nvarchar(max))";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, type);

            ResultSet docList = preparedStatement.executeQuery();
            
            while(docList.next()) {
                SMDSDocuments doc = new SMDSDocuments();
                doc.id = docList.getInt("id");
                doc.active = docList.getBoolean("active");
                doc.section = docList.getString("section");
                doc.type = docList.getString("type");
                doc.description = docList.getString("description");
                doc.fileName = docList.getString("fileName");
                doc.dueDate = docList.getInt("dueDate");
                doc.group = docList.getString("group");
                doc.historyFileName = docList.getString("historyFileName");
                doc.historyDescription = docList.getString("historyDescription");
                doc.CHDCHG = docList.getString("CHDCHG");
                doc.questionsFileName = docList.getString("questionsFileName");
                doc.emailSubject = docList.getString("emailSubject");
                doc.parameters = docList.getString("parameters");
                doc.emailBody = docList.getString("emailBody");
                doc.sortOrder = docList.getDouble("sortOrder");
                documentList.add(doc);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return documentList;
    }
    
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
                doc.CHDCHG = foundDoc.getString("CHDCHG");
                doc.questionsFileName = foundDoc.getString("questionsFileName");
                doc.emailSubject = foundDoc.getString("emailSubject");
                doc.parameters = foundDoc.getString("parameters");
                doc.emailBody = foundDoc.getString("emailBody");
                doc.sortOrder = foundDoc.getDouble("sortOrder");
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
                doc.CHDCHG = foundDoc.getString("CHDCHG");
                doc.questionsFileName = foundDoc.getString("questionsFileName");
                doc.emailSubject = foundDoc.getString("emailSubject");
                doc.parameters = foundDoc.getString("parameters");
                doc.emailBody = foundDoc.getString("emailBody");
                doc.sortOrder = foundDoc.getDouble("sortOrder");
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
                doc.CHDCHG = foundDoc.getString("CHDCHG");
                doc.questionsFileName = foundDoc.getString("questionsFileName");
                doc.emailSubject = foundDoc.getString("emailSubject");
                doc.parameters = foundDoc.getString("parameters");
                doc.emailBody = foundDoc.getString("emailBody");
                doc.sortOrder = foundDoc.getDouble("sortOrder");
            }
        } catch (SQLException ex) {
            Logger.getLogger(SMDSDocuments.class.getName()).log(Level.SEVERE, null, ex);
        }
        return doc;
    }
    
}
