package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;

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
            
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return partyTypes;
    }
}
