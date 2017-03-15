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


    public static List<LetterQueue> getLetterQueueByGlobalSection() {
        List<LetterQueue> emailList = new ArrayList<>();
        List<String> casetypes = null;

        if (Global.activeSection.equals("Hearings")){
            casetypes = CaseType.getCaseTypeHearings();
        } else {
            casetypes = CaseType.getCaseType();
        }

        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            // Email Out Table
            String sql = "SELECT C.id, 'Email' AS type, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.creationDate, C.[TO], C.subject, "
                    + "COUNT(S.id) AS attachments, C.suggestedSendDate, C.userID, C.section "
                    + "FROM EmailOut C "
                    + "LEFT JOIN EmailOutAttachment S ON C.id = S.emailoutid "
                    + "WHERE C.okToSend = 0 ";

                    if (!casetypes.isEmpty()) {
                        sql += "AND (";

                        for (String casetype : casetypes) {

                            sql += " caseType = '" + casetype + "' OR";
                        }

                        sql = sql.substring(0, (sql.length() - 2)) + ") ";
                    }

                    sql += " GROUP BY C.id, C.section, C.caseYear, C.caseType, "
                            + "C.caseMonth,  C.caseNumber, C.[TO], C.subject, "
                            + "C.userID, C.suggestedSendDate, C.creationDate";

                    sql += " UNION ALL ";

            // Postal Out Table
            sql += "SELECT C.id, 'Postal' AS type, C.caseYear, C.caseType, "
                    + "C.caseMonth, C.caseNumber, C.creationDate, C.person AS [TO], "
                    + "C.historyDescription AS subject, COUNT(S.id) AS attachments, "
                    + "C.suggestedSendDate, C.userID, C.section "
                    + "FROM PostalOut C "
                    + "LEFT JOIN PostalOutAttachment S ON C.id = S.postaloutid "
                    + "WHERE ";

                    if (!casetypes.isEmpty()) {
                        sql += "(";

                        for (String casetype : casetypes) {

                            sql += " caseType = '" + casetype + "' OR";
                        }

                        sql = sql.substring(0, (sql.length() - 2)) + ") ";
                    }

                    sql += "GROUP BY C.id, C.section, C.caseYear, C.caseType, "
                            + "C.caseMonth, C.caseNumber, C.person, C.userID, "
                            + "C.suggestedSendDate, C.historyDescription, "
                            + "C.creationDate";

                    sql += " ORDER BY creationDate, caseYear, CaseMonth, caseNumber ASC";

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
                eml.userName = User.getNameLastInitialByID(rs.getInt("userID"));
                eml.section = rs.getString("section");
                emailList.add(eml);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getLetterQueueByGlobalSection();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return emailList;
    }

}
