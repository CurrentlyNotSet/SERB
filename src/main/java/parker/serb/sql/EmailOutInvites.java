package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class EmailOutInvites {
    
    public int id;
    public String section;
    public String TOaddress;
    public String CCaddress;
    public String emailBody;
    public String caseNumber;
    public String hearingType;
    public String hearingRoomAbv;
    public String hearinDescription;
    public String hearingStartTime;
    public String hearingEndTime;
    
    public static void addNewHearing(
            String section,
            String toAddress, 
            String ccAddress,
            String emailBody,
            String caseNumber,
            String hearingType,
            String hearingRoomAbv,
            String hearingDescription,
            Timestamp hearingStartDateTime) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO EmailOutInvites VALUES (?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, toAddress);
            preparedStatement.setString(3, ccAddress);
            preparedStatement.setString(4, emailBody);
            preparedStatement.setString(5, caseNumber);
            preparedStatement.setString(6, hearingType);
            preparedStatement.setString(7, hearingRoomAbv);
            preparedStatement.setString(8, hearingDescription);
            preparedStatement.setTimestamp(9, hearingStartDateTime);
            preparedStatement.setTimestamp(10, generateHearingEndDateTime(hearingStartDateTime, hearingType));

            if(!toAddress.equals("")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                System.out.println("TESTING");
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static Timestamp generateHearingEndDateTime(Timestamp startDateTime, String hearingType) {
        
        Timestamp endDateTime = new Timestamp(startDateTime.getTime());
        
        if(hearingType == null) {
            hearingType = "";
        }
        
        switch(hearingType) {
            case "PH":
            case "ST":
            case "MC":    
                endDateTime.setTime(startDateTime.getTime() + (1 * 60 * 60 * 1000));
                break;
            case "RH":
            case "SE":
            case "TC":
                endDateTime.setTime(startDateTime.getTime() + (2 * 60 * 60 * 1000));
                break; 
            default:
                endDateTime.setTime(startDateTime.getTime() + (15 * 60 * 1000));
                break;
        }
        return endDateTime;
    }
    
}
