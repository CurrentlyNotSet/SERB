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
public class PostalOutRelatedCase {
    public int id;
    public int postalOutId;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    
    public static void insertPostalOutRelatedCase(PostalOutRelatedCase related) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();
            String sql = "INSERT INTO PostalOutRelatedCase (postalOutId, caseYear, caseType, caseMonth, caseNumber) VALUES ("
                    + "?,"  //1 - postalOutId
                    + "?,"  //2 - caseYear
                    + "?,"  //3 - caseType
                    + "?,"  //4 - caseMonth
                    + "?)"; //5 - caseNumber

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setInt   (1, related.postalOutId);
            ps.setString(2, related.caseYear);
            ps.setString(3, related.caseType);
            ps.setString(4, related.caseMonth);
            ps.setString(5, related.caseNumber);
            ps.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                insertPostalOutRelatedCase(related);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static List<PostalOutRelatedCase> getPostalOutRelatedCaseByID(int postalOutID) {
        List<PostalOutRelatedCase> list = new ArrayList<>();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM PostalOutRelatedCase WHERE postalOutId = ?";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setInt(1, postalOutID);

            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                PostalOutRelatedCase item = new PostalOutRelatedCase();
                item.id = rs.getInt("id");
                item.postalOutId = rs.getInt("postalOutId");
                item.caseYear = rs.getString("caseYear") == null ? "" : rs.getString("caseYear");
                item.caseType = rs.getString("caseType") == null ? "" : rs.getString("caseType");
                item.caseMonth = rs.getString("caseMonth") == null ? "" : rs.getString("caseMonth");
                item.caseNumber = rs.getString("caseNumber") == null ? "" : rs.getString("caseNumber");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getPostalOutRelatedCaseByID(postalOutID);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
    public static void deletePostalOutRelatedCaseByID(int id) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from PostalOutRelatedCase where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                deletePostalOutRelatedCaseByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
}