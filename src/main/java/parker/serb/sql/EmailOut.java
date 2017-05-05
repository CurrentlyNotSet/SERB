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
import java.util.Calendar;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

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
    public String internalNote;
    public Date creationDate;

    public static List<EmailOut> getEmailOutByGlobalSection() {
        List<EmailOut> emailList = new ArrayList<>();
        List<String> casetypes = null;

        if (Global.activeSection.equals("Hearings")){
            casetypes = CaseType.getCaseTypeHearings();
        } else {
            casetypes = CaseType.getCaseType();
        }

        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT C.id, C.section, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.[to], C.[from], C.cc, "
                    + "C.bcc, C.subject, C.body, C.userID, C.suggestedSendDate, "
                    + "C.okToSend, c.internalNote, C.creationDate, COUNT(S.id) AS attachments "
                    + "FROM EmailOut C "
                    + "LEFT JOIN EmailOutAttachment S ON C.id = S.emailoutid "
                    + "WHERE C.okToSend = 0 ";

                    if (!casetypes.isEmpty()) {
                        sql += "AND (";

                        for (String casetype : casetypes) {

                            sql += " Section = '" + casetype + "' OR";
                        }

                        sql = sql.substring(0, (sql.length() - 2)) + ") ";
                    }

                    sql += " GROUP BY C.id, C.section, C.caseYear, C.caseType, C.caseMonth, "
                    + "C.caseNumber, C.[to], C.[from], C.cc, C.bcc, C.subject, "
                    + "C.body, C.userID, C.suggestedSendDate, C.okToSend, c.internalNote, C.creationDate";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
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
                eml.internalNote = emailListRS.getString("internalNote");
                eml.creationDate = emailListRS.getDate("creationDate");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEmailOutByGlobalSection();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }

    public static EmailOut getEmailByID(int id) {
        EmailOut eml = null;

        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

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
                eml.internalNote = emailListRS.getString("internalNote");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEmailByID(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return eml;
    }

    public static void updateEmailOut(EmailOut item) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateEmailOut(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void markEmailReadyToSend(int id) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE EmailOut SET okToSend = 1 where id = ?";

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

    public static int insertEmail(EmailOut item) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

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
                    + "okToSend, "          //14
                    + "internalNote, "      //15
                    + "creationDate "       //16
                    + ") VALUES (";
                    for(int i=0; i<15; i++){
                        sql += "?, ";   //01-15
                    }
                     sql += "?)";   //16

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
            preparedStatement.setString (15, item.internalNote);
            preparedStatement.setDate   (16, new java.sql.Date(Calendar.getInstance().getTime().getTime()));
            preparedStatement.executeUpdate();

            ResultSet newRow = preparedStatement.getGeneratedKeys();
            if (newRow.next()){
                return newRow.getInt(1);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                insertEmail(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return 0;
    }

    public static int getEmailCount() {
        int count = 0;

        String casetypes = "";

        if (Global.activeSection.equals("Hearings")) {
            casetypes = "(C.section = 'MED' OR C.section = 'REP' OR C.section = 'ULP') ";
        } else {
            casetypes = "C.section = '" + (Global.activeSection.equals("Civil Service Commission") ? "CSC" : Global.activeSection) + "' ";
        }

        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT COUNT(*) AS [count] FROM emailout AS C WHERE " + casetypes
                    + " UNION ALL "
                    + " SELECT COUNT(*) AS [count] FROM postalOut AS C WHERE " + casetypes;

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet emailListRS = preparedStatement.executeQuery();

            while (emailListRS.next()) {
                count += emailListRS.getInt("count");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if (ex.getCause() instanceof SQLServerException) {
                getEmailCount();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return count;
    }

    public static void removeEmail(int id) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from EmailOut"
                    + " where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeEmail(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

}
