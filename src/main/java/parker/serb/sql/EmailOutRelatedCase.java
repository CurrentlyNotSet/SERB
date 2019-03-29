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
public class EmailOutRelatedCase {
    public int id;
    public int emailOutId;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    
    public static void insertEmailOutRelatedCase(EmailOutRelatedCase related) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();
            String sql = "INSERT INTO EmailOutRelatedCase (emailOutId, caseYear, caseType, caseMonth, caseNumber) VALUES ("
                    + "?,"  //1 - emailOutId
                    + "?,"  //2 - caseYear
                    + "?,"  //3 - caseType
                    + "?,"  //4 - caseMonth
                    + "?)"; //5 - caseNumber

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setInt   (1, related.emailOutId);
            ps.setString(2, related.caseYear);
            ps.setString(3, related.caseType);
            ps.setString(4, related.caseMonth);
            ps.setString(5, related.caseNumber);
            ps.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                insertEmailOutRelatedCase(related);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static List<EmailOutRelatedCase> getEmailOutRelatedCaseByID(int emailOutRelatedID) {
        List<EmailOutRelatedCase> list = new ArrayList<>();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM EmailOutRelatedCase WHERE emailOutId = ?";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setInt(1, emailOutRelatedID);

            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                EmailOutRelatedCase item = new EmailOutRelatedCase();
                item.id = rs.getInt("id");
                item.emailOutId = rs.getInt("emailOutId");
                item.caseYear = rs.getString("caseYear") == null ? "" : rs.getString("caseYear");
                item.caseType = rs.getString("caseType") == null ? "" : rs.getString("caseType");
                item.caseMonth = rs.getString("caseMonth") == null ? "" : rs.getString("caseMonth");
                item.caseNumber = rs.getString("caseNumber") == null ? "" : rs.getString("caseNumber");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEmailOutRelatedCaseByID(emailOutRelatedID);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
}
