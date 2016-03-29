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
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class CaseParty {
    
    public int id;
    public String name;
    public String type;
    public String phoneNumber;
    public String email;
    
    /**
     * add a party to the case
     * @param id id of the party
     * @param type party type
     */
    public static void createParty(String id, String type) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CaseParty VALUES ("
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, id);
            preparedStatement.setString(6, type);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createPartyForCaseDuplication(int id, String type, String newCaseNumber) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CaseParty VALUES ("
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseNumber.split("-")[0]);
            preparedStatement.setString(2, newCaseNumber.split("-")[1]);
            preparedStatement.setString(3, newCaseNumber.split("-")[2]);
            preparedStatement.setString(4, newCaseNumber.split("-")[3]);
            preparedStatement.setInt(5, id);
            preparedStatement.setString(6, type);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Returns a list of all parties that are associated with a case.  Joins the
     * Party and CaseParty Table to aquire all information
     * @return a list of all parties that are related to a case
     */
    public static List<CaseParty> loadPartiesByCase() {
        List<CaseParty> parties = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select CaseParty.partyID AS id,"
                    + " caseRelation,"
                    + " firstName,"
                    + " middleInitial,"
                    + " lastName,"
                    + " workPhone,"
                    + " emailAddress from CaseParty inner join Party "
                    + " on Party.id = CaseParty.partyID "
                    + " where caseParty.caseYear = ?"
                    + " and caseParty.caseType = ?"
                    + " and caseParty.caseMonth = ?"
                    + " and caseParty.caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            
            ResultSet casePartyRS = preparedStatement.executeQuery();
            
            while(casePartyRS.next()) {
                CaseParty party = new CaseParty();
                party.id = casePartyRS.getInt("id");
                party.type = casePartyRS.getString("caseRelation");
                party.name = casePartyRS.getString("firstName") + " " + casePartyRS.getString("lastName");
                party.email = casePartyRS.getString("emailAddress");
                party.phoneNumber = NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("workPhone"));
                parties.add(party);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parties;
    }
    
    public static List<CaseParty> loadPartiesByCase(String caseYear,
        String caseType, String caseMonth, String caseNumber) {
        List<CaseParty> parties = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select CaseParty.partyID AS id,"
                    + " caseRelation,"
                    + " firstName,"
                    + " middleInitial,"
                    + " lastName,"
                    + " workPhone,"
                    + " emailAddress from CaseParty inner join Party "
                    + " on Party.id = CaseParty.partyID "
                    + " where caseParty.caseYear = ?"
                    + " AND caseParty.caseType = ?"
                    + " AND caseParty.caseMonth = ?"
                    + " AND caseParty.caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            preparedStatement.setString(2, caseType);
            preparedStatement.setString(3, caseMonth);
            preparedStatement.setString(4, caseNumber);
            
            ResultSet casePartyRS = preparedStatement.executeQuery();
            
            while(casePartyRS.next()) {
                CaseParty party = new CaseParty();
                party.id = casePartyRS.getInt("id");
                party.type = casePartyRS.getString("caseRelation");
                party.name = casePartyRS.getString("firstName") + " " + casePartyRS.getString("lastName");
                party.email = casePartyRS.getString("emailAddress");
                party.phoneNumber = NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("workPhone"));
                parties.add(party);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parties;
    }
    
    public static void removePartyFromCase(String id, String caseRelation) {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Delete from CaseParty where partyID = ? and CaseNumber = ? and CaseRelation = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, Global.caseNumber);
            preparedStatement.setString(3, caseRelation);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean duplicateParty(String id) {
        boolean validEntry = true;
        
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from CaseParty where partyID = ? and CaseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            preparedStatement.setString(2, Global.caseNumber);
            
            ResultSet results = preparedStatement.executeQuery();
            
            if (results.next()) {
                validEntry = false;
            } 
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validEntry;
    }
    
    public static void duplicatePartyInformation(String newCase, String oldCase) {
        
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select partyID, caseRelation from CaseParty where CaseYear = ? and"
                    + " CaseType = ? and CaseMonth = ? and CaseNumber = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, oldCase.split("-")[0]);
            preparedStatement.setString(2, oldCase.split("-")[1]);
            preparedStatement.setString(3, oldCase.split("-")[2]);
            preparedStatement.setString(4, oldCase.split("-")[3]);
            
            ResultSet results = preparedStatement.executeQuery();
            
            while(results.next()) {
                createPartyForCaseDuplication(results.getInt("partyID"), results.getString("caseRelation"), newCase);
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseParty.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
