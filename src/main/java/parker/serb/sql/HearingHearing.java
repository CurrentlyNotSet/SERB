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
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class HearingHearing {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String entryDate;
    public String hearingType;
    public String room;
    public String hearingDateTime;
    public int aljID;
    public String comments;

    public static void addHearing(Date hearingTime, 
            String hearingType, 
            String hearingRoom,
            String alj,
            String comments) {
        Statement stmt = null;
        
        HearingType type = HearingType.getHearingTypeByTypeCode(hearingType);
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO HearingHearing VALUES (1,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(6, hearingType);
            preparedStatement.setString(7, hearingRoom);
            preparedStatement.setTimestamp(8, new Timestamp(hearingTime.getTime()));
            preparedStatement.setInt(9, User.getUserID(alj));
            preparedStatement.setString(10, comments);

            int process = preparedStatement.executeUpdate();
            
            if(process == 1) {
                EmailOutInvites.addNewHearing(getCaseSection(),
                        generateHearingToAddress(CMDSCase.getALJemail(), HearingRoom.getHearingRoomEmailByName(hearingRoom)),
                        null,
                        "An upcoming " + (type.hearingDescription.equals("") ? hearingType : type.hearingDescription) + " is booked in " + hearingRoom + " at " + Global.mmddyyyyhhmma.format(hearingTime),
                        NumberFormatService.generateFullCaseNumber(),
                        hearingType,
                        hearingRoom,
                        hearingType,
                        new Timestamp(hearingTime.getTime()),
                        generateEmailOutInviteSubject(Global.caseYear, Global.caseType, Global.caseMonth, Global.caseNumber, hearingType, hearingTime));
                Activity.addActivty("Created a " + hearingType + " on " + Global.mmddyyyyhhmma.format(hearingTime) + " in " + hearingRoom, null);
            }

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addHearing(hearingTime, hearingType, hearingRoom, alj, comments);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    private static String generateHearingToAddress(String alj, String hearingRoom) {
        String emailTo = "";
        
        if(alj.equals("")) {
            emailTo = hearingRoom;
        } else {
            if(hearingRoom.equals("")) {
                emailTo = alj;
            } else {
                emailTo = alj + ";" + hearingRoom;
            }
        }
        return emailTo;
    }
    
    private static String getCaseSection() {
        String section = "";
        
        switch(Global.caseType) {
            case "ULP":
            case "ERC":
            case "JWD":
                section = "ULP";
                break;
            case "REP":
            case "RBT":
                section = "REP";
                break;
            case "MED":
            case "STK":
            case "NCN":
            case "CON":
                section = "MED";
                break;
        }
        return section;
    }
    
    public static List loadHearingsByCaseNumber() {
        List<HearingHearing> activityList = new ArrayList<HearingHearing>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select id,"
                    + " entryDate,"
                    + " hearingType,"
                    + " room,"
                    + " hearingDateTime,"
                    + " aljID, "
                    + " comments"
                    + " from HearingHearing"
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
                HearingHearing hearing = new HearingHearing();
                hearing.id = caseActivity.getInt("id");
                hearing.entryDate = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("entryDate").getTime()));
                hearing.hearingType = caseActivity.getString("hearingType");
                hearing.room = caseActivity.getString("room");
                hearing.hearingDateTime = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("hearingDateTime").getTime()));
                hearing.aljID = caseActivity.getInt("aljID");
                hearing.comments = caseActivity.getString("comments") != null ? caseActivity.getString("comments") : "";
                activityList.add(hearing);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadHearingsByCaseNumber();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
    
    public static void removeHearingByID(String id, String hearingInformation) {
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from HearingHearing where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            int passed = preparedStatement.executeUpdate();
            
            if(passed == 1) {
                Activity.addActivty(hearingInformation, null);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                removeHearingByID(id, hearingInformation);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateHearingByID(String id,
            String comments, 
            String hearingType,
            String hearingDate) 
    {
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update HearingHearing"
                    + " set comments = ?"
                    + " where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, comments);
            preparedStatement.setString(2, id);

            int passed = preparedStatement.executeUpdate();
            
            if(passed == 1) {
                Activity.addActivty("Updated Comments for " + hearingType + " on " + hearingDate, null);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateHearingByID(id, comments, hearingType, hearingDate);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    private static String generateEmailOutInviteSubject(String caseYear, 
            String caseType, String caseMonth, String caseNumber, String hearingType, Date hearingTime){
        String subject = "";
                
        String partyType = "";
        
        if (caseType.equals("ULP")){
            partyType = "charged";
        } else if (caseType.equals("REP") || caseType.equals("MED")) {
            partyType = "employer";
        }
                
        List<CaseParty> partylist = CaseParty.loadPartiesByCasePartyTypeNoRep(
                        caseYear, caseType, caseMonth, caseNumber, 
                        partyType);
                
        String partySet = "";
        for (CaseParty party : partylist){
            partySet += (partySet.trim().equals("") ? "" : " ") 
                    + (party.lastName.trim().equals("") ? party.companyName : party.lastName);
        }

        String hearingDateTime = hearingTime == null ? "" 
                : " on " + Global.mmddyyyy.format(hearingTime) + " at " + Global.hhmma.format(hearingTime);

        subject = hearingType + " " +  //Type Code
                CMDSCase.getALJinitials() + " " + //ALJ Initials
                partySet + " " + // Appellant
                NumberFormatService.generateFullCaseNumber() + // Full Case Number
                hearingDateTime; //Hearingdate Time

        return subject;
    }
}
