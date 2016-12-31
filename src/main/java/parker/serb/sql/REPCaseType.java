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
public class REPCaseType {
    
    public int id;
    public boolean active;
    public String typeAbbrevation;
    public String typeName;
    public String description;
    
    public static List loadAllREPCaseTypes() {
        List repCaseTypes = new ArrayList<>();
         
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from REPCaseType";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet repCaseTypeRS = preparedStatement.executeQuery();
            
            while(repCaseTypeRS.next()) {
                REPCaseType caseType = new REPCaseType();
                caseType.id = repCaseTypeRS.getInt("id");
                caseType.active = repCaseTypeRS.getBoolean("active");
                caseType.typeAbbrevation = repCaseTypeRS.getString("typeAbbrevation");
                caseType.typeName = repCaseTypeRS.getString("typeName");
                caseType.description = repCaseTypeRS.getString("description");
                repCaseTypes.add(caseType);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllREPCaseTypes();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return repCaseTypes;
    }
}
