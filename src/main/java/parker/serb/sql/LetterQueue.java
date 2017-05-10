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
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author User
 */
public class LetterQueue {

    public int id;
    public String type;
    public String fullCaseNumber;
    public String creationDate;
    public String to;
    public String subject;
    public int attachementCount;
    public String suggestedSendDate;
    public int userID;
    public String userName;
    public String section;


    public static List<LetterQueue> getLetterQueueAllByGlobalSection() {
        List<LetterQueue> emailList = new ArrayList<>();
        String casetypes = "";
        String sql = "";

        if (Global.activeSection.equals("Hearings")){
            casetypes = "(C.section = 'MED' OR C.section = 'REP' OR C.section = 'ULP' OR C.section = 'Hearings') ";
        } else {
            casetypes = "C.section = '" + (Global.activeSection.equals("Civil Service Commission") ? "CSC" : Global.activeSection) + "' ";
        }

        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            //Start Out Master Table
            sql += "Select * FROM (";

            // Email Out Table
            sql += "SELECT C.id, 'Email' AS type, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.creationDate, C.[TO], C.subject, "
                    + "COUNT(S.id) AS attachments, C.suggestedSendDate, "
                    + "(U.firstName + ' ' + LEFT(U.lastName, 1) + '.') AS userName, C.section "
                    + "FROM EmailOut C "
                    + "LEFT JOIN EmailOutAttachment S ON C.id = S.emailoutid "
                    + "LEFT JOIN users U ON C.userID = U.id "
                    + "WHERE C.okToSend = 0 AND " + casetypes + " "
                    + "GROUP BY C.id, C.section, C.caseYear, C.caseType, "
                    + "C.caseMonth,  C.caseNumber, C.[TO], C.subject, "
                    + "C.suggestedSendDate, C.creationDate, U.firstName, U.lastName ";

            //Union the two tables
            sql += " UNION ALL ";

            // Postal Out Table
            sql += "SELECT C.id, 'Postal' AS type, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.creationDate, C.person AS [TO], "
                    + "C.historyDescription AS subject, COUNT(S.id) AS attachments, "
                    + "C.suggestedSendDate, (U.firstName + ' ' + LEFT(U.lastName, 1) + '.') AS userName, "
                    + "C.section "
                    + "FROM PostalOut C "
                    + "LEFT JOIN PostalOutAttachment S ON C.id = S.postaloutid "
                    + "LEFT JOIN users U ON C.userID = U.id "
                    + "WHERE " + casetypes + " "
                    + "GROUP BY C.id, C.section, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.person, C.suggestedSendDate, "
                    + "C.historyDescription, C.creationDate, U.firstName, U.lastName ";

            //End Massive Table
            sql += " ) AS MasterTable ";

            //Order "NEW" Table
            sql += " ORDER BY CASE WHEN suggestedSendDate IS NULL THEN 1 ELSE 0 END ASC, suggestedSendDate ASC, "
                    + "creationDate ASC, caseYear ASC, CaseMonth ASC, caseNumber ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                LetterQueue eml = new LetterQueue();
                eml.id = rs.getInt("id");
                eml.type = rs.getString("type");
                if (Global.activeSection.equalsIgnoreCase("ORG") ||Global.activeSection.equalsIgnoreCase("Civil Service Commission")){
                    eml.fullCaseNumber = rs.getString("caseNumber");
                } else {
                    eml.fullCaseNumber = rs.getString("caseYear") + "-" + rs.getString("caseType") + "-" + rs.getString("caseMonth") + "-" + rs.getString("caseNumber");
                }
                eml.creationDate = rs.getDate("creationDate") == null ? "" : Global.mmddyyyy.format(rs.getDate("creationDate"));
                eml.to = rs.getString("TO");
                eml.subject = rs.getString("subject") == null ? "" : rs.getString("subject");
                eml.attachementCount = rs.getInt("attachments");
                eml.suggestedSendDate = rs.getDate("suggestedSendDate") == null ? "" : Global.mmddyyyy.format(rs.getDate("suggestedSendDate"));
                eml.userName = rs.getString("userName");
                eml.section = rs.getString("section");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getLetterQueueAllByGlobalSection();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }

    public static List<LetterQueue> getLetterQueueEmailByGlobalSection() {
        List<LetterQueue> emailList = new ArrayList<>();
        String casetypes = "";
        String sql = "";

        if (Global.activeSection.equals("Hearings")){
            casetypes = "(C.section = 'MED' OR C.section = 'REP' OR C.section = 'ULP' OR C.section = 'Hearings') ";
        } else {
            casetypes = "C.section = '" + (Global.activeSection.equals("Civil Service Commission") ? "CSC" : Global.activeSection) + "' ";
        }

        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            //Start Out Master Table
            sql += "Select * FROM (";

            // Email Out Table
            sql += "SELECT C.id, 'Email' AS type, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.creationDate, C.[TO], C.subject, "
                    + "COUNT(S.id) AS attachments, C.suggestedSendDate, "
                    + "(U.firstName + ' ' + LEFT(U.lastName, 1) + '.') AS userName, C.section "
                    + "FROM EmailOut C "
                    + "LEFT JOIN EmailOutAttachment S ON C.id = S.emailoutid "
                    + "LEFT JOIN users U ON C.userID = U.id "
                    + "WHERE C.okToSend = 0 AND " + casetypes + " "
                    + "GROUP BY C.id, C.section, C.caseYear, C.caseType, "
                    + "C.caseMonth,  C.caseNumber, C.[TO], C.subject, "
                    + "C.suggestedSendDate, C.creationDate, U.firstName, U.lastName ";

            //End Massive Table
            sql += " ) AS MasterTable ";

            //Order "NEW" Table
            sql += " ORDER BY CASE WHEN suggestedSendDate IS NULL THEN 1 ELSE 0 END ASC, suggestedSendDate ASC, "
                    + "creationDate ASC, caseYear ASC, CaseMonth ASC, caseNumber ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                LetterQueue eml = new LetterQueue();
                eml.id = rs.getInt("id");
                eml.type = rs.getString("type");
                if (Global.activeSection.equalsIgnoreCase("ORG") ||Global.activeSection.equalsIgnoreCase("Civil Service Commission")){
                    eml.fullCaseNumber = rs.getString("caseNumber");
                } else {
                    eml.fullCaseNumber = rs.getString("caseYear") + "-" + rs.getString("caseType") + "-" + rs.getString("caseMonth") + "-" + rs.getString("caseNumber");
                }
                eml.creationDate = rs.getDate("creationDate") == null ? "" : Global.mmddyyyy.format(rs.getDate("creationDate"));
                eml.to = rs.getString("TO");
                eml.subject = rs.getString("subject") == null ? "" : rs.getString("subject");
                eml.attachementCount = rs.getInt("attachments");
                eml.suggestedSendDate = rs.getDate("suggestedSendDate") == null ? "" : Global.mmddyyyy.format(rs.getDate("suggestedSendDate"));
                eml.userName = rs.getString("userName");
                eml.section = rs.getString("section");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getLetterQueueAllByGlobalSection();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }

    public static List<LetterQueue> getLetterQueuePostalByGlobalSection() {
        List<LetterQueue> emailList = new ArrayList<>();
        String casetypes = "";
        String sql = "";

        if (Global.activeSection.equals("Hearings")){
            casetypes = "(C.section = 'MED' OR C.section = 'REP' OR C.section = 'ULP' OR C.section = 'Hearings') ";
        } else {
            casetypes = "C.section = '" + (Global.activeSection.equals("Civil Service Commission") ? "CSC" : Global.activeSection) + "' ";
        }

        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            //Start Out Master Table
            sql += "Select * FROM (";

            // Postal Out Table
            sql += "SELECT C.id, 'Postal' AS type, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.creationDate, C.person AS [TO], "
                    + "C.historyDescription AS subject, COUNT(S.id) AS attachments, "
                    + "C.suggestedSendDate, (U.firstName + ' ' + LEFT(U.lastName, 1) + '.') AS userName, "
                    + "C.section "
                    + "FROM PostalOut C "
                    + "LEFT JOIN PostalOutAttachment S ON C.id = S.postaloutid "
                    + "LEFT JOIN users U ON C.userID = U.id "
                    + "WHERE " + casetypes + " "
                    + "GROUP BY C.id, C.section, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.person, C.suggestedSendDate, "
                    + "C.historyDescription, C.creationDate, U.firstName, U.lastName ";

            //End Massive Table
            sql += " ) AS MasterTable ";

            //Order "NEW" Table
            sql += " ORDER BY CASE WHEN suggestedSendDate IS NULL THEN 1 ELSE 0 END ASC, suggestedSendDate ASC, "
                    + "creationDate ASC, caseYear ASC, CaseMonth ASC, caseNumber ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                LetterQueue eml = new LetterQueue();
                eml.id = rs.getInt("id");
                eml.type = rs.getString("type");
                if (Global.activeSection.equalsIgnoreCase("ORG") ||Global.activeSection.equalsIgnoreCase("Civil Service Commission")){
                    eml.fullCaseNumber = rs.getString("caseNumber");
                } else {
                    eml.fullCaseNumber = rs.getString("caseYear") + "-" + rs.getString("caseType") + "-" + rs.getString("caseMonth") + "-" + rs.getString("caseNumber");
                }
                eml.creationDate = rs.getDate("creationDate") == null ? "" : Global.mmddyyyy.format(rs.getDate("creationDate"));
                eml.to = rs.getString("TO");
                eml.subject = rs.getString("subject") == null ? "" : rs.getString("subject");
                eml.attachementCount = rs.getInt("attachments");
                eml.suggestedSendDate = rs.getDate("suggestedSendDate") == null ? "" : Global.mmddyyyy.format(rs.getDate("suggestedSendDate"));
                eml.userName = rs.getString("userName");
                eml.section = rs.getString("section");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getLetterQueueAllByGlobalSection();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }

}
