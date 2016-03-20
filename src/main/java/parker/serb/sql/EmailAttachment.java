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
 * @author parkerjohnston
 */
public class EmailAttachment {
    
    public int id;
    public int emailID;
    public String fileName;
    
    public static List getAttachmentList(String id) {
        List emailAttachmentList = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from EmailAttachment where emailID = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                EmailAttachment docket = new EmailAttachment();
                docket.id = emailListRS.getInt("id");
                docket.emailID = emailListRS.getInt("emailID");
                docket.fileName = emailListRS.getString("fileName");
                emailAttachmentList.add(docket);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailAttachmentList;
    }
    
    public static void deleteEmailAttachmentEntry(int id) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Delete from EmailAttachment where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String getEmailAttachmentFileByID(String id) {
        String emailAttachmentFileName = "";
        
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select fileName from EmailAttachment where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                emailAttachmentFileName = emailListRS.getString("fileName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailAttachmentFileName;
    }
}
