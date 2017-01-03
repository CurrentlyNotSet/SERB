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
public class Jurisdiction {
    
    public int id;
    public String jurisCode;
    public String jurisName;

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadJurisdictionList();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return jurisList;
    }
}
