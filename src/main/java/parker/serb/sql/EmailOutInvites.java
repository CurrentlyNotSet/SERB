package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
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
    public String emailSubject;

    public static void addNewHearing(
            String section,
            String toAddress,
            String ccAddress,
            String emailBody,
            String caseNumber,
            String hearingType,
            String hearingRoomAbv,
            String hearingDescription,
            Timestamp hearingStartDateTime,
            String emailSubject) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO EmailOutInvites ("
                    + "section, "           //01
                    + "TOaddress, "         //02
                    + "CCaddress, "         //03
                    + "emailBody, "         //04
                    + "caseNumber, "        //05
                    + "hearingType, "       //06
                    + "hearingRoomAbv, "    //07
                    + "hearingdescription, "//08
                    + "hearingStartTime, "  //09
                    + "hearingEndtime, "    //10
                    + "emailSubject"        //11
                    + ") VALUES (";
                    for(int i=0; i<10; i++){
                        sql += "?, ";   //01-10
                    }
                     sql += "?)"; //11

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
            preparedStatement.setTimestamp(10, generateHearingEndTimeSevenHours(hearingStartDateTime));
            preparedStatement.setString(11, emailSubject);
            
            if(!toAddress.equals("")) {
                preparedStatement.executeUpdate();
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addNewHearing(section, toAddress, ccAddress, emailBody, caseNumber, hearingType, hearingRoomAbv, hearingDescription, hearingStartDateTime, emailSubject);
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

    public static Timestamp generateHearingTimeTenAM(Timestamp startDateTime){
        Timestamp time = new Timestamp(startDateTime.getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.set(Calendar.HOUR_OF_DAY, 10);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MILLISECOND, 0);
        return new Timestamp(cal.getTimeInMillis());
    }

    public static Timestamp generateHearingEndTimeSevenHours(Timestamp startDateTime){
        Timestamp time = new Timestamp(startDateTime.getTime());
        Calendar cal = Calendar.getInstance();
        cal.setTime(time);
        cal.add(Calendar.HOUR, 7);
        return new Timestamp(cal.getTimeInMillis());
    }
}
