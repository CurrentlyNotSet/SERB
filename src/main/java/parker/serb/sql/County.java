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
 * @author parkerjohnston
 */
public class County {
    
    public int id;
    public boolean active;
    public String stateCode;
    public String regionCode;
    public String countyCode;
    public String countyName;
    public String jurisCode;
    public String jurisName;

    public static List loadCountyList() {
        List<County> countyList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * "
                    + " FROM County"
                    + " WHERE active = 1 and stateCode = 'OH'"
                    + " ORDER BY countyName ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                County county = new County();
                county.id = caseActivity.getInt("id");
                county.active = caseActivity.getBoolean("active");
                county.stateCode = caseActivity.getString("stateCode");
                county.regionCode = caseActivity.getString("regionCode");
                county.countyCode = caseActivity.getString("countyCode");
                county.countyName = caseActivity.getString("countyName");
                countyList.add(county);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadCountyList();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return countyList;
    }
    
    public static List loadCountyListByState(String stateCode) {
        List<County> countyList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select countyName "
                    + " FROM County"
                    + " WHERE active = 1 and stateCode = ?"
                    + " ORDER BY countyName ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, stateCode);            

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                County county = new County();
                county.countyName = caseActivity.getString("countyName");
                countyList.add(county);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadCountyListByState(stateCode);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return countyList;
    }
}
