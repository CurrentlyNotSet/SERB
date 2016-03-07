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
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class BoardMeeting {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String boardMeetingDate;
    public String agendaItemNumber;
    public String recommendation;
    
    public static void addULPBoardMeeting(String meetingDate, String agendaItem, String recommendation) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO BoardMeeting VALUES (?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setTimestamp(5, new Timestamp(NumberFormatService.convertMMDDYYYY(meetingDate)));
            preparedStatement.setString(6, agendaItem);
            preparedStatement.setString(7, recommendation);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Added New Board Meeting Information", "");

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static List loadULPBoardMeetings() {
        List<BoardMeeting> boardMeetingList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from BoardMeeting where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?"
                    + " order by id desc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet boardMeetingRS = preparedStatement.executeQuery();
            
            while(boardMeetingRS.next()) {
                BoardMeeting meeting = new BoardMeeting();
                meeting.boardMeetingDate = Global.mmddyyyy.format(new Date(boardMeetingRS.getTimestamp("boardmeetingdate").getTime()));
                meeting.agendaItemNumber = boardMeetingRS.getString("agendaItemNumber");
                meeting.recommendation = boardMeetingRS.getString("recommendation");
                boardMeetingList.add(meeting);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return boardMeetingList;
    }
    
    /**
     * Creates activity entry when new cases are created
     * @param caseNumber the new case number
     */
//    public static void addNewCaseActivty(String caseNumber) {
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?)";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            String[] parsedCaseNumber = caseNumber.split("-");
//            preparedStatement.setString(1, parsedCaseNumber[0]);
//            preparedStatement.setString(2, parsedCaseNumber[1]);
//            preparedStatement.setString(3, parsedCaseNumber[2]);
//            preparedStatement.setString(4, parsedCaseNumber[3]);
//            preparedStatement.setInt(5, Global.activeUser.id);
//            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
//            preparedStatement.setString(7, "Case Created");
//            preparedStatement.setString(8, "");
//
//            preparedStatement.executeUpdate();
//
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
//    
//    /**
//     * Loads all activity for a specified case number, pulls the case number
//     * from global
//     * @param searchTerm term to limit the search results
//     * @return List of Activities
//     */
//    public static List loadCaseNumberActivity(String searchTerm) {
//        List<BoardMeeting> activityList = new ArrayList<>();
//        
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "select Activity.id,"
//                    + " caseYear,"
//                    + " caseType,"
//                    + " caseMonth,"
//                    + " caseNumber,"
//                    + " date,"
//                    + " action,"
//                    + " firstName,"
//                    + " lastName,"
//                    + " filePath"
//                    + " from Activity"
//                    + " INNER JOIN Users"
//                    + " ON Activity.userID = Users.id"
//                    + " where caseYear = ? and"
//                    + " caseType = ? and"
//                    + " caseMonth = ? and"
//                    + " caseNumber = ? and"
//                    + " (firstName like ? or"
//                    + " lastName like ? or"
//                    + " action like ?) ORDER BY date DESC ";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, Global.caseYear);
//            preparedStatement.setString(2, Global.caseType);
//            preparedStatement.setString(3, Global.caseMonth);
//            preparedStatement.setString(4, Global.caseNumber);
//            preparedStatement.setString(5, "%" + searchTerm + "%");
//            preparedStatement.setString(6, "%" + searchTerm + "%");
//            preparedStatement.setString(7, "%" + searchTerm + "%");
//
//            ResultSet caseActivity = preparedStatement.executeQuery();
//            
//            while(caseActivity.next()) {
//                BoardMeeting act = new BoardMeeting();
//                act.id = caseActivity.getInt("id");
//                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
//                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
//                act.action = caseActivity.getString("action");
//                act.filePath = caseActivity.getString("filePath");
//                activityList.add(act);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return activityList;
//    }
//    
//    /**
//     * Loads all activities without a limited result
//     * @return list of all Activities per case
//     */
//    public static List loadAllActivity() {
//        List<BoardMeeting> activityList = new ArrayList<BoardMeeting>();
//        
//        Statement stmt = null;
//            
//        try {
//
//            stmt = Database.connectToDB().createStatement();
//
//            String sql = "select Activity.id,"
//                    + " caseYear,"
//                    + " caseType,"
//                    + " caseMonth,"
//                    + " caseNumber,"
//                    + " date,"
//                    + " action,"
//                    + " firstName,"
//                    + " lastName,"
//                    + " filePath"
//                    + " from Activity"
//                    + " INNER JOIN Users"
//                    + " ON Activity.userID = Users.id"
//                    + " ORDER BY date DESC ";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//
//            ResultSet caseActivity = preparedStatement.executeQuery();
//            
//            while(caseActivity.next()) {
//                BoardMeeting act = new BoardMeeting();
//                act.id = caseActivity.getInt("id");
//                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
//                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
//                act.action = caseActivity.getString("action");
//                act.caseYear = caseActivity.getString("caseYear");
//                act.caseType = caseActivity.getString("caseType");
//                act.caseMonth = caseActivity.getString("caseMonth");
//                act.caseNumber = caseActivity.getString("caseNumber");
//                act.filePath = caseActivity.getString("filePath");
//                activityList.add(act);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return activityList;
//    }
}
