package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class SystemError {

    public static void addSystemErrorEntry(
            String className,
            String methodName, 
            String exceptionType,
            String exceptionShort,
            String exceptionLong
        ) 
    {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "INSERT INTO SystemError VALUES (?,?,?,?,?,?,?)";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(2, Global.activeUser != null ? Global.activeUser.username : null);
            preparedStatement.setString(3, className);
            preparedStatement.setString(4, methodName);
            preparedStatement.setString(5, exceptionType);
            preparedStatement.setString(6, exceptionShort);
            preparedStatement.setString(7, exceptionLong);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addSystemErrorEntry(className, methodName, exceptionType, exceptionShort, exceptionLong);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
