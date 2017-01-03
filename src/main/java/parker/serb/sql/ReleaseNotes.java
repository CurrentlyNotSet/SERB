package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class ReleaseNotes {
    
    public int id;
    public String version;
    public String releaseNotes;
    public String releaseDate;
    
    public static List loadReleasedVersions() {
        List<String> releaseList = new ArrayList<>();
        
        Statement stmt = null;   
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select version from ReleaseNotes order by id DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet release = preparedStatement.executeQuery();
            
            while(release.next()) {
                releaseList.add(release.getString("version"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadReleasedVersions();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return releaseList;
    }
    
    public static ReleaseNotes loadReleaseInformation(String version) {
        ReleaseNotes releaseInformation = new ReleaseNotes();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from ReleaseNotes where version = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, version);

            ResultSet release = preparedStatement.executeQuery();
            
            while(release.next()) {
                releaseInformation.id = release.getInt("id");
                releaseInformation.version = release.getString("version");
                releaseInformation.releaseDate = Global.mmddyyyy.format(new Date(release.getTimestamp("releaseDate").getTime()));
                releaseInformation.releaseNotes = release.getString("releaseNotes");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadReleaseInformation(version);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return releaseInformation;
    }
}
