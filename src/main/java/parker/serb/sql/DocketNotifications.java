package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.SlackNotification;

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
        String userEmail = User.getEmailByID(mediatorID);


        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO DocketNotifications VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, userEmail);
            preparedStatement.setString(3, "New Documents for " + caseNumber);
            preparedStatement.setString(4, "New Documents have been filed for " + caseNumber + ".\n\nPlease check SMDS for more information.");

            if (!userEmail.equals("")){
                preparedStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addNotification(caseNumber, section, mediatorID);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void addFullNotification(String section, int mediatorID, String subject, String body) {
        Statement stmt = null;
        String userEmail = User.getEmailByID(mediatorID);


        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO DocketNotifications VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, userEmail);
            preparedStatement.setString(3, subject);
            preparedStatement.setString(4, body);

            if (!userEmail.equals("")){
                preparedStatement.executeUpdate();
            }

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addFullNotification(section, mediatorID, subject, body);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void addNewCaseNotification(String caseNumber, String section, int userID) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO DocketNotifications VALUES (?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, User.getEmailByID(userID));
            preparedStatement.setString(3, "New Case Created: " + caseNumber);
            preparedStatement.setString(4, "A new case was created: " + caseNumber + ".\n\nPlease check SMDS for more information.");
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addNotification(caseNumber, section, userID);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
