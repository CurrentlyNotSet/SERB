package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;

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
    
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @param searchTerm term to limit the search results
     * @return List of Activities
     */
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return countyList;
    }
}
