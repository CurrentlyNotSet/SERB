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
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class Email {
    
    public int id;
    public String section;
    public String emailFrom;
    public String emailTo;
    public String emailSubject;
    public Date sentDate;
    public Date receivedDate;
    public String emailCC;
    public String emailBCC;
    public String emailBody;
    public String emailBodyFileName;
    public String type;
    public String attachmentCount;
    
    public static List getAllEmails(String section) {
        List emailList = new ArrayList<>();
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from Email where Section = ? and readyToFile = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setBoolean(2, true);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                Email docket = new Email();
                docket.id = emailListRS.getInt("id");
                docket.section = emailListRS.getString("section");
                docket.emailFrom = emailListRS.getString("emailFrom");
                docket.emailTo = emailListRS.getString("emailTo");
                docket.emailSubject = emailListRS.getString("emailSubject");
                docket.sentDate = new Date(emailListRS.getTimestamp("sentDate").getTime());
                docket.receivedDate = new Date(emailListRS.getTimestamp("receivedDate").getTime());
                docket.emailCC = emailListRS.getString("emailCC");
                docket.emailBCC = emailListRS.getString("emailBCC");
                docket.emailBody = emailListRS.getString("emailBody");
                docket.emailBodyFileName = emailListRS.getString("emailBodyFileName");
                docket.type = "Email";
                docket.attachmentCount = Integer.toString(EmailAttachment.getAttachmentList(Integer.toString(emailListRS.getInt("id"))).size());
                emailList.add(docket);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getAllEmails(section);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }
    
    public static void deleteEmailEntry(int id) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from Email where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                deleteEmailEntry(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static Email getEmailByID(String id) {
        Email email = null;
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from Email where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                email = new Email();
                email.id = emailListRS.getInt("id");
                email.emailFrom = emailListRS.getString("emailFrom");
                email.emailSubject = emailListRS.getString("emailSubject");
                email.receivedDate = new Date(emailListRS.getTimestamp("receivedDate").getTime());
                email.emailBody = emailListRS.getString("emailBody");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEmailByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return email;
    }
    
    public static String getEmailBodyFileByID(String id) {
        String fileName = "";
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from Email where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                fileName = emailListRS.getString("emailBodyFileName");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEmailBodyFileByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return fileName;
    }
}
