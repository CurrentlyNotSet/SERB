/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import java.sql.Date;
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
public class EmailOut {
    
    public int id;
    public String section;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String to;
    public String from;
    public String cc;
    public String bcc;
    public String subject;
    public String body;
    public int userID;
    public Date suggestedSendDate;
    public int attachementCount;
    public boolean okToSend;
    
    public static List<EmailOut> getEmailOutBySection(String section) {
        List<EmailOut> emailList = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT C.id, C.section, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.[to], C.[from], C.cc, "
                    + "C.bcc, C.subject, C.body, C.userID, C.suggestedSendDate, "
                    + "C.okToSend, COUNT(S.id) AS attachments "
                    + "FROM EmailOut C "
                    + "LEFT JOIN EmailOutAttachment S ON C.id = S.emailoutid "
                    + "WHERE Section = ? AND C.okToSend = 0 "
                    + "GROUP BY C.id, C.section, C.caseYear, C.caseType, C.caseMonth, "
                    + "C.caseNumber, C.[to], C.[from], C.cc, C.bcc, C.subject, "
                    + "C.body, C.userID, C.suggestedSendDate, C.okToSend";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                EmailOut eml = new EmailOut();
                eml.id = emailListRS.getInt("id");
                eml.section = emailListRS.getString("section");
                eml.caseYear = emailListRS.getString("caseYear");
                eml.caseType = emailListRS.getString("caseType");
                eml.caseMonth = emailListRS.getString("caseMonth");
                eml.caseNumber = emailListRS.getString("caseNumber");
                eml.to = emailListRS.getString("to") == null ? "" : emailListRS.getString("to");
                eml.from = emailListRS.getString("from") == null ? "" : emailListRS.getString("from");
                eml.cc = emailListRS.getString("cc") == null ? "" : emailListRS.getString("cc");
                eml.bcc = emailListRS.getString("bcc") == null ? "" : emailListRS.getString("bcc");
                eml.subject = emailListRS.getString("subject") == null ? "" : emailListRS.getString("subject");
                eml.body = emailListRS.getString("body") == null ? "" : emailListRS.getString("body");
                eml.userID = emailListRS.getInt("userID");
                eml.suggestedSendDate = emailListRS.getDate("suggestedSendDate");
                eml.attachementCount = emailListRS.getInt("attachments");
                eml.okToSend = emailListRS.getBoolean("okToSend");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailList;
    }
    
    public static EmailOut getEmailByID(int id) {
        EmailOut eml = null;
        
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from EmailOut where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet emailListRS = preparedStatement.executeQuery();
            
            while(emailListRS.next()) {
                eml = new EmailOut();
                eml.id = emailListRS.getInt("id");
                eml.section = emailListRS.getString("section");
                eml.caseYear = emailListRS.getString("caseYear");
                eml.caseType = emailListRS.getString("caseType");
                eml.caseMonth = emailListRS.getString("caseMonth");
                eml.caseNumber = emailListRS.getString("caseNumber");
                eml.to = emailListRS.getString("to") == null ? "" : emailListRS.getString("to");
                eml.from = emailListRS.getString("from") == null ? "" : emailListRS.getString("from");
                eml.cc = emailListRS.getString("cc") == null ? "" : emailListRS.getString("cc");
                eml.bcc = emailListRS.getString("bcc") == null ? "" : emailListRS.getString("bcc");
                eml.subject = emailListRS.getString("subject") == null ? "" : emailListRS.getString("subject");
                eml.body = emailListRS.getString("body") == null ? "" : emailListRS.getString("body");
                eml.userID = emailListRS.getInt("userID");
                eml.suggestedSendDate = emailListRS.getDate("suggestedSendDate");
                eml.okToSend = emailListRS.getBoolean("okToSend");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return eml;
    }
    
    public static void updateEmailOut(EmailOut item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE EmailOut SET "
                    + "body = ?, "
                    + "suggestedSendDate = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.body.equals("") ? null : item.body.trim());
            preparedStatement.setDate  (2, item.suggestedSendDate);
            preparedStatement.setInt   (3, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void markEmailReadyToSend(int id) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE EmailOut SET okToSend = true where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static int insertEmail(EmailOut item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO EmailOut ("
                    + "section, "       //01
                    + "caseYear, "      //02
                    + "caseType, "      //03
                    + "caseMonth, "     //04
                    + "caseNumber, "    //05
                    + "[to], "          //06
                    + "[from], "        //07
                    + "cc, "            //08
                    + "bcc, "           //09
                    + "subject, "       //10
                    + "body, "          //11
                    + "userID, "        //12
                    + "suggestedSendDate, " //13
                    + "okToSend "           //14
                    + ") VALUES (";
                    for(int i=0; i<13; i++){
                        sql += "?, ";   //01-13
                    }
                     sql += "?)";   //14
                     
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString ( 1, item.section);
            preparedStatement.setString ( 2, item.caseYear);
            preparedStatement.setString ( 3, item.caseType);
            preparedStatement.setString ( 4, item.caseMonth);
            preparedStatement.setString ( 5, item.caseNumber);
            preparedStatement.setString ( 6, item.to);
            preparedStatement.setString ( 7, item.from);
            preparedStatement.setString ( 8, item.cc);
            preparedStatement.setString ( 9, item.bcc);
            preparedStatement.setString (10, item.subject);
            preparedStatement.setString (11, item.body);
            preparedStatement.setInt    (12, item.userID);
            preparedStatement.setDate   (13, item.suggestedSendDate);
            preparedStatement.setBoolean(14, item.okToSend);
            preparedStatement.executeUpdate();
            
            ResultSet newRow = preparedStatement.getGeneratedKeys();
            if (newRow.next()){
                return newRow.getInt(1);
            }            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
}
