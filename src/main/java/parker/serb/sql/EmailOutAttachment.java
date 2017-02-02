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
public class EmailOutAttachment {
    public int id;
    public int emailOutID;
    public String fileName;
    public boolean primaryAttachment;
    
    public static List<EmailOutAttachment> getEmailAttachments(int id) {
        List<EmailOutAttachment> emailList = new ArrayList<>();
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM EmailOutAttachment WHERE emailOutID = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                EmailOutAttachment eml = new EmailOutAttachment();
                eml.id = emailListRS.getInt("id");
                eml.emailOutID = emailListRS.getInt("emailOutID");
                eml.fileName = emailListRS.getString("fileName");
                eml.primaryAttachment = emailListRS.getBoolean("primaryAttachment");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEmailAttachments(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }
    
    public static void insertAttachment(EmailOutAttachment item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO EmailOutAttachment ("
                    + "emailOutID, "
                    + "fileName, "
                    + "primaryAttachment "
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)";   //03

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, item.emailOutID);
            preparedStatement.setString(2, item.fileName);
            preparedStatement.setBoolean(3, item.primaryAttachment);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                insertAttachment(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void removeEmailAttachment(int id) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from EmailOutAttachment"
                    + " where emailOutID = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeEmailAttachment(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
}
