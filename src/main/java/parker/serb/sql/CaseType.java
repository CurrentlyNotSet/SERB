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
public class CaseType {
    
    public int id;
    public boolean active;
    public String section;
    public String caseType;
    public String description;
    
    /**
     * Return a list of all case types that are possible for a section.  The
     * current selection is gathered from the global file
     * @return list of strings of all found case types
     */
    public static List<String> getCaseType() {
        Statement stmt = null;
        
        List activityList = new ArrayList<String>();
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Select caseType from CaseType where Section = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.activeSection);

            ResultSet caseType = preparedStatement.executeQuery();
            
            while(caseType.next()) {
                activityList.add(caseType.getString("caseType"));
            }
            
            switch(Global.activeSection) {
            case "ORG":
                activityList.add("ORG");
                break;
            case "Civil Service Commission":
                activityList.add("CSC");
                break;
            default:
                break;
        }   
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activityList;
    }
    
    public static List<CaseType> loadAllStatusTypes(String[] param) {
        List<CaseType> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CaseType";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(section, caseType, description) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY section, caseType";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CaseType item = new CaseType();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.caseType = rs.getString("caseType") == null ? "" : rs.getString("caseType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static CaseType getTypeByID(int id) {
        CaseType item = new CaseType();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CaseType WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.caseType = rs.getString("caseType") == null ? "" : rs.getString("caseType");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public static void createStatusType(CaseType item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CaseType "
                    + "(active, section, caseType, description)"
                    + " VALUES "
                    + "(1, ?, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.section.equals("") ? null : item.section.trim());
            preparedStatement.setString(2, item.caseType.equals("") ? null : item.caseType.trim());
            preparedStatement.setString(3, item.description.equals("") ? null : item.description.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateCaseType(CaseType item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE CaseType SET "
                    + "active = ?, "
                    + "section = ?, "
                    + "caseType = ?, "
                    + "description = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString (2, item.section.equals("") ? null : item.section.trim());
            preparedStatement.setString (3, item.caseType.equals("") ? null : item.caseType.trim());
            preparedStatement.setString (4, item.description.equals("") ? null : item.description.trim());
            preparedStatement.setInt    (5, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
}
