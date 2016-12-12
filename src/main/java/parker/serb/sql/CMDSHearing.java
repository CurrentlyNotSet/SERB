package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
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
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class CMDSHearing {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String entryDate;
    public String hearingType;
    public String room;
    public String hearingDateTime;
    public String hearingDate;
    public String hearingTime;
    
    public static void addHearing(Date hearingTime, String hearingType, String hearingRoom) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CMDSHearing VALUES (1,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(6, hearingType);
            preparedStatement.setString(7, hearingRoom);
            preparedStatement.setTimestamp(8, new Timestamp(hearingTime.getTime()));

            int process = preparedStatement.executeUpdate();
            
            if(process == 1) {
                EmailOutInvites.addNewHearing("CMDS",
                        CMDSCase.getALJemail(),
                        HearingRoom.getHearingRoomEmailByName(hearingRoom),
                        "An upcoming " + hearingType + " is booked in " + hearingRoom + " at " + Global.mmddyyyyhhmma.format(hearingTime),
                        NumberFormatService.generateFullCaseNumber(),
                        hearingType,
                        hearingRoom,
                        hearingType,
                        new Timestamp(hearingTime.getTime()));
                Activity.addActivty("Created a " + hearingType + " on " + Global.mmddyyyyhhmma.format(hearingTime) + " in " + hearingRoom, null);
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
    
    public static List loadHearingsByCaseNumber() {
        List<CMDSHearing> activityList = new ArrayList<CMDSHearing>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select id,"
                    + " entryDate,"
                    + " hearingType,"
                    + " room,"
                    + " hearingDateTime"
                    + " from CMDSHearing"
                    + " Where CaseYear = ?"
                    + " and CaseType = ?"
                    + " and CaseMonth = ?"
                    + " and CaseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                CMDSHearing hearing = new CMDSHearing();
                hearing.id = caseActivity.getInt("id");
                hearing.entryDate = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("entryDate").getTime()));
                hearing.hearingType = caseActivity.getString("hearingType");
                hearing.room = caseActivity.getString("room");
                hearing.hearingDateTime = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("hearingDateTime").getTime()));
                activityList.add(hearing);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                System.out.println("TESTING");
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return activityList;
    }
    
    public static void removeHearingByID(String id, String hearingInformation) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from CMDSHearing where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            int passed = preparedStatement.executeUpdate();
            
            if(passed == 1) {
                Activity.addActivty(hearingInformation, null);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                System.out.println("TESTING");
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public static CMDSHearing loadTopHearingByCaseNumberAndType(String type) {
        CMDSHearing hearing = null;
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select id,"
                    + " entryDate,"
                    + " hearingType,"
                    + " room,"
                    + " hearingDateTime"
                    + " from CMDSHearing"
                    + " Where CaseYear = ?"
                    + " and CaseType = ?"
                    + " and CaseMonth = ?"
                    + " and CaseNumber = ?"
                    + " and HearingType = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, type);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                hearing = new CMDSHearing();
                hearing.id = caseActivity.getInt("id");
                hearing.entryDate = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("entryDate").getTime()));
                hearing.hearingType = caseActivity.getString("hearingType");
                hearing.room = caseActivity.getString("room");
                hearing.hearingDateTime = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("hearingDateTime").getTime()));
                hearing.hearingDate = Global.MMMMMdyyyy.format(new Date(caseActivity.getTimestamp("hearingDateTime").getTime()));
                hearing.hearingTime = Global.hmma.format(new Date(caseActivity.getTimestamp("hearingDateTime").getTime()));
                break;
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                System.out.println("TESTING");
                SlackNotification.sendNotification(ex.getMessage());
            } else {
                SlackNotification.sendNotification(ex.getMessage());
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return hearing;
    }
}
