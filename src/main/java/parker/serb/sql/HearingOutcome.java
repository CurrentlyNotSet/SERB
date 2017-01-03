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
public class HearingOutcome {
    
    public int id;
    public boolean active;
    public String type;
    public String description;

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadOutcomesByType(section);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityTypeList;
    }
}
