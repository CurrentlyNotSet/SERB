package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
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
public class REPElectionSiteInformation {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public Timestamp siteDate;
    public Timestamp siteStartTime;
    public Timestamp siteEndTime;
    public String sitePlace;
    public String siteAddress1;
    public String siteAddress2;
    public String siteLocation;
    
    public static void addSiteLocation(
            String siteDate,
            String sitePlace,
            String siteAddress1,
            String siteAddress2,
            String siteLocation,
            String siteStartTime,
            String startAMPM,
            String siteEndTime,
            String endAMPM
        ) 
    {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO REPElectionSiteInformation "
                    + "(active,"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " siteDate,"
                    + " sitePlace,"
                    + " siteAddress1,"
                    + " siteAddress2,"
                    + " siteLocation,"
                    + " siteStartTime,"
                    + " siteEndTime) VALUES (?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);
            preparedStatement.setTimestamp(6, siteDate.equals("") ? null : new Timestamp(Global.mmddyyyy.parse(siteDate).getTime()));
            preparedStatement.setString(7, sitePlace);
            preparedStatement.setString(8, siteAddress1);
            preparedStatement.setString(9, siteAddress2);
            preparedStatement.setString(10, siteLocation);
            preparedStatement.setTimestamp(11, siteStartTime.equals("") ? null : new Timestamp(NumberFormatService.converthmma(siteStartTime + " " + startAMPM)));
            preparedStatement.setTimestamp(12, siteEndTime.equals("") ? null : new Timestamp(NumberFormatService.converthmma(siteEndTime + " " + endAMPM)));

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Added On-Site Election Location", siteDate);
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                addSiteLocation(siteDate, sitePlace, siteAddress1, siteAddress2, siteLocation, siteStartTime, startAMPM, siteEndTime, endAMPM);
            } 
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void deleteSiteInformation(int id) {
        Statement stmt = null;    
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Delete from REPElectionSiteInformation where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            preparedStatement.executeUpdate();
            Activity.addActivty("Removed Site Election Information", null);
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                deleteSiteInformation(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static List loadSiteInformationByCaseNumber() {
        List<REPElectionSiteInformation> activityList = new ArrayList<>();
        
        Statement stmt = null; 
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select *"
                    + " from REPElectionSiteInformation"
                    + " where caseYear = ? and"
                    + " caseType = ? and"
                    + " caseMonth = ? and"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                REPElectionSiteInformation info = new REPElectionSiteInformation();
                info.siteDate = caseActivity.getTimestamp("siteDate");
                info.siteStartTime = caseActivity.getTimestamp("siteStartTime");
                info.siteEndTime = caseActivity.getTimestamp("siteEndTime");
                info.sitePlace = caseActivity.getString("sitePlace");
                info.siteAddress1 = caseActivity.getString("siteAddress1"); 
                info.siteAddress2 = caseActivity.getString("siteAddress2"); 
                info.siteLocation = caseActivity.getString("siteLocation");
                info.id = caseActivity.getInt("id");
                activityList.add(info);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadSiteInformationByCaseNumber();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }
}
