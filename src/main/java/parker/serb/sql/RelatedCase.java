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
public class RelatedCase {
    
    public int id;
    public String relatedCaseYear;
    public String relatedCaseType;
    public String relatedCaseMonth;
    public String relatedCaseNumber;
    
    
    public static void addNewRelatedCase(String caseNumber) {
        
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO RelatedCase VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Added " + caseNumber + " as Related Case", null);

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void addNewMultiCase(String caseNumber) {
        
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO RelatedCase VALUES (?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
            
            Activity.addActivty("Added " + caseNumber + " as Multi Case", null);

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List loadRelatedCases() {
        List<String> relatedCasesList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet relatedCaseRS = preparedStatement.executeQuery();
            
            while(relatedCaseRS.next()) {
                relatedCasesList.add((String) relatedCaseRS.getString("relatedCaseNumber"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return relatedCasesList;
    }
    
    public static boolean checkCaseIsAlreadyRelated(String caseNumber) {
        
        boolean newCase = true;
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? AND"
                    + " cast(relatedCaseNumber as nvarchar(max)) = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            ResultSet relatedCaseRS = preparedStatement.executeQuery();
            
            while (relatedCaseRS.next()) {
                newCase = false;
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return newCase;
    }
    
    public static void removeRelatedCase(String caseNumber) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Delete from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? AND"
                    + " cast(relatedCaseNumber as nvarchar(max)) = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
            Activity.addActivty("Removed " + caseNumber + " from Related Case", null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void removeMultiCase(String caseNumber) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Delete from RelatedCase where"
                    + " caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ? AND"
                    + " cast(relatedCaseNumber as nvarchar(max)) = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, caseNumber);

            preparedStatement.executeUpdate();
            Activity.addActivty("Removed " + caseNumber + " from Multi Case", null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
