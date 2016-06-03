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
    public String memoDate;
    
    public static void addULPBoardMeeting(String meetingDate, String agendaItem, String recommendation) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO BoardMeeting"
                    + " (caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " boardMeetingDate,"
                    + " agendaItemNumber,"
                    + " recommendation)"
                    + " VALUES (?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setTimestamp(5, new Timestamp(NumberFormatService.convertMMDDYYYY(meetingDate)));
            preparedStatement.setString(6, agendaItem);
            preparedStatement.setString(7, recommendation);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Added New Board Meeting Information", null);

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void addREPBoardMeeting(String meetingDate,
            String agendaItem,
            String recommendation,
            String memoDate) {
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO BoardMeeting"
                    + " (caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " boardMeetingDate,"
                    + " agendaItemNumber,"
                    + " recommendation,"
                    + " memoDate)"
                    + " VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setTimestamp(5, new Timestamp(NumberFormatService.convertMMDDYYYY(meetingDate)));
            preparedStatement.setString(6, agendaItem);
            preparedStatement.setString(7, recommendation);
            preparedStatement.setTimestamp(8, new Timestamp(NumberFormatService.convertMMDDYYYY(meetingDate)));

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Added New Board Meeting Information", null);

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
                    + " order by boardmeetingdate desc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet boardMeetingRS = preparedStatement.executeQuery();
            
            while(boardMeetingRS.next()) {
                BoardMeeting meeting = new BoardMeeting();
                meeting.id = boardMeetingRS.getInt("id");
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
    
    public static List loadREPBoardMeetings() {
        List<BoardMeeting> boardMeetingList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from BoardMeeting where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?"
                    + " order by boardmeetingdate desc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet boardMeetingRS = preparedStatement.executeQuery();
            
            while(boardMeetingRS.next()) {
                BoardMeeting meeting = new BoardMeeting();
                meeting.id = boardMeetingRS.getInt("id");
                meeting.boardMeetingDate = boardMeetingRS.getTimestamp("boardmeetingdate") == null ? "" : Global.mmddyyyy.format(new Date(boardMeetingRS.getTimestamp("boardmeetingdate").getTime()));
                meeting.agendaItemNumber = boardMeetingRS.getString("agendaItemNumber");
                meeting.recommendation = boardMeetingRS.getString("recommendation");
                meeting.memoDate = boardMeetingRS.getTimestamp("memoDate") == null ? "" : Global.mmddyyyy.format(new Date(boardMeetingRS.getTimestamp("memoDate").getTime()));
                boardMeetingList.add(meeting);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return boardMeetingList;
    }
    
    public static void updateULPBoardMeeting(String id, String date, String agenda, String rec) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update BoardMeeting SET"
                    + " boardMeetingDate = ?,"
                    + " agendaItemNumber = ?,"
                    + " recommendation = ?"
                    + " where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(NumberFormatService.convertMMDDYYYY(date)));
            preparedStatement.setString(2, agenda);
            preparedStatement.setString(3, rec);
            preparedStatement.setString(4, id);

            preparedStatement.executeUpdate();
            Activity.addActivty("Updated Board Meeting Information", null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateREPBoardMeeting(String id, String date, String agenda, String rec, String memoDate) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update BoardMeeting SET"
                    + " boardMeetingDate = ?,"
                    + " agendaItemNumber = ?,"
                    + " recommendation = ?,"
                    + " memoDate = ?"
                    + " where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(NumberFormatService.convertMMDDYYYY(date)));
            preparedStatement.setString(2, agenda);
            preparedStatement.setString(3, rec);
            preparedStatement.setTimestamp(4, new Timestamp(NumberFormatService.convertMMDDYYYY(memoDate)));
            preparedStatement.setString(5, id);
            

            preparedStatement.executeUpdate();
            Activity.addActivty("Updated Board Meeting Information", null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void removeBoardMeeting(String id)
    {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Delete from BoardMeeting"
                    + " where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            preparedStatement.executeUpdate();
            Activity.addActivty("Removed Board Meeting Information", null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
