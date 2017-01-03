/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.Date;
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
public class PostalOut {
    
    public int id;
    public String section;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String person;
    public String addressBlock;
    public int userID;
    public Date suggestedSendDate;
    public int attachementCount;
    public String historyDescription;
    
    public static List<PostalOut> getPostalOutBySection(String section) {
        List<PostalOut> emailList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT C.id, C.section, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.person, C.addressBlock, C.userID, C.suggestedSendDate, "
                    + "C.historyDescription, COUNT(S.id) AS attachments "
                    + "FROM PostalOut C "
                    + "LEFT JOIN PostalOutAttachment S ON C.id = S.postaloutid "
                    + "WHERE Section = ? "
                    + "GROUP BY C.id, C.section, C.caseYear, C.caseType, C.caseMonth, "
                    + "C.caseNumber, C.person, C.addressBlock, C.userID, C.suggestedSendDate, C.historyDescription";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                PostalOut eml = new PostalOut();
                eml.id = emailListRS.getInt("id");
                eml.section = emailListRS.getString("section");
                eml.caseYear = emailListRS.getString("caseYear");
                eml.caseType = emailListRS.getString("caseType");
                eml.caseMonth = emailListRS.getString("caseMonth");
                eml.caseNumber = emailListRS.getString("caseNumber");
                eml.person = emailListRS.getString("person");
                eml.addressBlock = emailListRS.getString("addressBlock");
                eml.userID = emailListRS.getInt("userID");
                eml.suggestedSendDate = emailListRS.getDate("suggestedSendDate");
                eml.attachementCount = emailListRS.getInt("attachments");
                eml.historyDescription = emailListRS.getString("historyDescription");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getPostalOutBySection(section);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }
    
    public static PostalOut getPostalOutByID(int id) {
        PostalOut eml = null;
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from PostalOut where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                eml = new PostalOut();
                eml.id = emailListRS.getInt("id");
                eml.section = emailListRS.getString("section");
                eml.caseYear = emailListRS.getString("caseYear");
                eml.caseType = emailListRS.getString("caseType");
                eml.caseMonth = emailListRS.getString("caseMonth");
                eml.caseNumber = emailListRS.getString("caseNumber");
                eml.person = emailListRS.getString("person") == null ? "" : emailListRS.getString("person");
                eml.addressBlock = emailListRS.getString("addressBlock") == null ? "" : emailListRS.getString("addressBlock");
                eml.userID = emailListRS.getInt("userID");
                eml.suggestedSendDate = emailListRS.getDate("suggestedSendDate");
                eml.historyDescription = emailListRS.getString("historyDescription");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getPostalOutByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return eml;
    }
    
    public static void markEmailReadyToSend(int id) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE PostalOut SET okToSend = true where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                markEmailReadyToSend(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static int insertPostalOut(PostalOut item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO PostalOut ("
                    + "section, "       //01
                    + "caseYear, "      //02
                    + "caseType, "      //03
                    + "caseMonth, "     //04
                    + "caseNumber, "    //05
                    + "person, "        //06
                    + "addressBlock, "  //07
                    + "userID, "        //08
                    + "suggestedSendDate, " //09
                    + "historyDescription " //10
                    + ") VALUES (";
                    for(int i=0; i<9; i++){
                        sql += "?, ";   //01-09
                    }
                     sql += "?)";   //10
                     
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString ( 1, item.section);
            preparedStatement.setString ( 2, item.caseYear);
            preparedStatement.setString ( 3, item.caseType);
            preparedStatement.setString ( 4, item.caseMonth);
            preparedStatement.setString ( 5, item.caseNumber);
            preparedStatement.setString ( 6, item.person);
            preparedStatement.setString ( 7, item.addressBlock);
            preparedStatement.setInt    ( 8, item.userID);
            preparedStatement.setDate   ( 9, item.suggestedSendDate);
            preparedStatement.setString (10, item.historyDescription);
            preparedStatement.executeUpdate();
            
            ResultSet newRow = preparedStatement.getGeneratedKeys();
            if (newRow.next()){
                return newRow.getInt(1);
            }            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                insertPostalOut(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return 0;
    }
    
    
    public static void updatePostalOut(PostalOut item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE PostalOut SET "
                    + "suggestedSendDate = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setDate  (1, item.suggestedSendDate);
            preparedStatement.setInt   (2, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updatePostalOut(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void removeEntry(int id){
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "DELETE FROM PostalOut WHERE id = ?";

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
