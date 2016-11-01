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
    
    public static List<EmailOut> getEmailOutBySection(String section) {
        List<EmailOut> emailList = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT C.id, C.section, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.[to], C.[from], C.cc, "
                    + "C.bcc, C.subject, C.body, C.userID, C.suggestedSendDate, "
                    + "COUNT(S.id) AS attachments "
                    + "FROM EmailOut C "
                    + "LEFT JOIN EmailOutAttachment S ON C.id = S.emailoutid "
                    + "WHERE Section = ? "
                    + "GROUP BY C.id, C.section, C.caseYear, C.caseType, C.caseMonth, "
                    + "C.caseNumber, C.[to], C.[from], C.cc, C.bcc, C.subject, "
                    + "C.body, C.userID, C.suggestedSendDate";
            
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
                eml.to = emailListRS.getString("to");
                eml.from = emailListRS.getString("from");
                eml.cc = emailListRS.getString("cc");
                eml.bcc = emailListRS.getString("bcc");
                eml.subject = emailListRS.getString("subject");
                eml.body = emailListRS.getString("body");
                eml.userID = emailListRS.getInt("userID");
                eml.suggestedSendDate = emailListRS.getDate("suggestedSendDate");
                eml.attachementCount = emailListRS.getInt("attachments");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return emailList;
    }
    
}
