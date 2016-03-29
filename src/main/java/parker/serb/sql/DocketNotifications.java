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
public class DocketNotifications {
    
    public int id;
    public String section;
    public String sendTo;
    public String messageSubject;
    public String messageBody;
    
    
    public static void addNotification(String caseNumber, String section, int mediatorID) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO DocketNotifications VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, User.getEmailByID(mediatorID));
            preparedStatement.setString(3, "New Documents for " + caseNumber);
            preparedStatement.setString(4, "New Documents have been filed for " + caseNumber + ".\n\nPlease check SMDS for more information.");
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
