package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author parkerjohnston
 */
public class HearingOutcome {
    
    public int id;
    public boolean active;
    public String type;
    public String description;
    
    /**
     * Loads all activities without a limited result
     * @return list of all Activities per case
     */
    public static List loadOutcomesByType(String section) {
        List<HearingOutcome> activityTypeList = new ArrayList<HearingOutcome>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from HearingOutcome"
                    + " where type = ?"
                    + " order by description asc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);

            ResultSet activityTypeListRS = preparedStatement.executeQuery();
            
            while(activityTypeListRS.next()) {
                HearingOutcome activityType = new HearingOutcome();
                activityType.id = activityTypeListRS.getInt("id");
                activityType.active = activityTypeListRS.getBoolean("active");
                activityType.type = activityTypeListRS.getString("type");
                activityType.description = activityTypeListRS.getString("description");
                activityTypeList.add(activityType);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return activityTypeList;
    }
}
