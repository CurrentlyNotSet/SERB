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
public class Jurisdiction {
    
    public int id;
    public String jurisCode;
    public String jurisName;
    
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @param searchTerm term to limit the search results
     * @return List of Activities
     */
    public static List loadJurisdictionList() {
        List<Jurisdiction> jurisList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from Jurisdiction ORDER BY jurisCode ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                Jurisdiction juris = new Jurisdiction();
                juris.id = caseActivity.getInt("id");
                juris.jurisCode = caseActivity.getString("jurisCode");
                juris.jurisName = caseActivity.getString("jurisName");
                jurisList.add(juris);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return jurisList;
    }
}
