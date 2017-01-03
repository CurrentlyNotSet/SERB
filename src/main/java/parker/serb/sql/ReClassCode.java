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
 * @author User
 */
public class ReClassCode {
    
    public int id;
    public boolean active;
    public String code;
    
    public static List loadAll() {
        List caseStatusList = new ArrayList<>();
         
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select code from ReClassCode where active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseStatusRS = preparedStatement.executeQuery();
            
            while(caseStatusRS.next()) {
                caseStatusList.add(caseStatusRS.getString("code"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAll();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseStatusList;
    }
}
