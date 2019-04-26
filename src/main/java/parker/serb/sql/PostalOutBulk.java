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
 * @author Andrew
 */
public class PostalOutBulk {
    
    public int id;
    public int postalOutId;
    public String person;
    public String addressBlock;
    
    public static List<PostalOutBulk> getPostalOutBulkEntries(int id) {
        List<PostalOutBulk> emailList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM PostalOutBulk WHERE postalOutID = ? ORDER BY person ASC";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                PostalOutBulk eml = new PostalOutBulk();
                eml.id = emailListRS.getInt("id");
                eml.postalOutId = emailListRS.getInt("postalOutID");
                eml.person = emailListRS.getString("person");
                eml.addressBlock = emailListRS.getString("addressBlock");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getPostalOutBulkEntries(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }
    
    public static void insertAddress(PostalOutBulk item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO PostalOutBulk ("
                    + "postalOutID, "
                    + "person, "
                    + "addressBlock "
                    + ") VALUES (";
                    for(int i=0; i<2; i++){
                        sql += "?, ";   //01-02
                    }
                     sql += "?)";   //03

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, item.postalOutId);
            preparedStatement.setString(2, item.person);
            preparedStatement.setString(3, item.addressBlock);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                insertAddress(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void removeEntry(int id){
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "DELETE FROM PostalOutBulk WHERE postalOutID = ?";

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
