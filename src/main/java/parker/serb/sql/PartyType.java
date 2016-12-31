package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class PartyType {
    
    /**
     * Load the list of party types by the current selected section
     * @return a list of all party types for the selected section
     */
    public static List loadAllPartyTypesBySection() {
        List partyTypes = new ArrayList<>();
          
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "select type from PartyType where section = ? ORDER BY type ASC";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            if(Global.activeSection.equals("Civil Service Commission")) {
                preparedStatement.setString(1, "CSC");
            } else {
                preparedStatement.setString(1, Global.activeSection);
            }

            ResultSet partyTypeRS = preparedStatement.executeQuery();
            
            while(partyTypeRS.next()) {
                partyTypes.add(partyTypeRS.getString("type"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllPartyTypesBySection();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return partyTypes;
    }
    
    public static List loadAllPartyTypesForHearings() {
        List partyTypes = new ArrayList<>();
            
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "select type from PartyType where section = ? ORDER BY type ASC";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            switch(Global.caseType) {
                case "ULP":
                case "ERC":
                case "JWD":
                    preparedStatement.setString(1, "ULP");
                    break;
                case "REP":
                case "RBT":
                    preparedStatement.setString(1, "REP");
                    break;
                case "MED":
                case "STK":
                case "NCN":
                case "CON":
                    preparedStatement.setString(1, "MED");
                    break;
            }

            ResultSet partyTypeRS = preparedStatement.executeQuery();
            
            while(partyTypeRS.next()) {
                partyTypes.add(partyTypeRS.getString("type"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllPartyTypesForHearings();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return partyTypes;
    }
}
