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
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.SlackNotification;

/**
 *
 * @author User
 */
public class AnnualReport {
    
    public static long getCount(String sql) {
        long count = 0;
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();
            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            rs.next();
            count = rs.getLong("COLUMN1");
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getCount(sql);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return count;
    }
    
}
