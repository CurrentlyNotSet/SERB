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
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class ReleaseNotes {
    
    public int id;
    public String version;
    public String releaseNotes;
    public String releaseDate;
    
    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     * @param searchTerm term to limit the search results
     * @return List of Activities
     */
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return releaseList;
    }
    
    public static ReleaseNotes loadReleaseInformation(String version) {
        ReleaseNotes releaseInformation = new ReleaseNotes();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return releaseInformation;
    }
}
