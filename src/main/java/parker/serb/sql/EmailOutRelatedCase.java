/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
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
    
}
