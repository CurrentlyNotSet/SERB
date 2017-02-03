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
public class PostalOutAttachment {
    public int id;
    public int PostalOutID;
    public String fileName;
    public boolean primaryAttachment;
    
    public static List<PostalOutAttachment> getPostalOutAttachments(int id) {
        List<PostalOutAttachment> emailList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM PostalOutAttachment WHERE postalOutID = ? ORDER BY primaryAttachment DESC";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                PostalOutAttachment eml = new PostalOutAttachment();
                eml.id = emailListRS.getInt("id");
                eml.PostalOutID = emailListRS.getInt("postalOutID");
                eml.fileName = emailListRS.getString("fileName");
                eml.primaryAttachment = emailListRS.getBoolean("primaryAttachment");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getPostalOutAttachments(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }
    
    public static void insertAttachment(PostalOutAttachment item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO PostalOutAttachment ("
                    + "postalOutID, "
                    + "fileName, "
                    + "primaryAttachment "
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)";   //03

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, item.PostalOutID);
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
    
    public static void removeEntry(int id){
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "DELETE FROM PostalOutAttachment WHERE postalOutID = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeEntry(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
}
