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
public class REPCaseType {
    
    public int id;
    public boolean active;
    public String typeAbbrevation;
    public String typeName;
    public String description;
    
    /**
     * Load the list of party types by the current selected section
     * @return a list of all party types for the selected section
     */
    public static List loadAllREPCaseTypes() {
        List repCaseTypes = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from REPCaseType";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet repCaseTypeRS = preparedStatement.executeQuery();
            
            while(repCaseTypeRS.next()) {
                REPCaseType caseType = new REPCaseType();
                caseType.id = repCaseTypeRS.getInt("id");
                caseType.active = repCaseTypeRS.getBoolean("active");
                caseType.typeAbbrevation = repCaseTypeRS.getString("typeAbbrevation");
                caseType.typeName = repCaseTypeRS.getString("typeName");
                caseType.description = repCaseTypeRS.getString("description");
                repCaseTypes.add(caseType);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return repCaseTypes;
    }
}
