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
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public int partyID;
    public String caseRelation;
    public String prefix;
    public String firstName;
    public String middleInitial;
    public String lastName;
    public String suffix;
    public String nameTitle;
    public String jobTitle;
    public String companyName;
    public String address1;
    public String address2;
    public String address3;
    public String city;
    public String stateCode;
    public String zipcode;
    public String phone1;
    public String emailAddress;
    public String phone2;
    
    
    public static void createParty(String id, String type) {
        try {
            
            Party party = Party.getPartyByID(id);

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CaseParty VALUES ("
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
                    + "?,"
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
            preparedStatement.setInt(5, party.id);
            preparedStatement.setString(6, type);
            preparedStatement.setString(7, party.prefix);
            preparedStatement.setString(8, party.firstName);
            preparedStatement.setString(9, party.middleInitial);
            preparedStatement.setString(10, party.lastName);
            preparedStatement.setString(11, party.suffix);
            preparedStatement.setString(12, party.nameTitle);
            preparedStatement.setString(13, party.jobTitle);
            preparedStatement.setString(14, party.companyName);
            preparedStatement.setString(15, party.address1);
            preparedStatement.setString(16, party.address2);
            preparedStatement.setString(17, party.address3);
            preparedStatement.setString(18, party.city);
            preparedStatement.setString(19, party.stateCode);
            preparedStatement.setString(20, party.zipCode);
            preparedStatement.setString(21, party.phone1.equals("") ? "" : NumberFormatService.convertPhoneNumberToString(party.phone1));
            preparedStatement.setString(22, party.emailAddress);
            preparedStatement.setString(23, party.phone2.equals("") ? "" : NumberFormatService.convertPhoneNumberToString(party.phone2));
            
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void createPartyForCaseDuplication(int id, String type, String newCaseNumber) {
        try {
            
            Party partyInformation = Party.getPartyByID(String.valueOf(id));

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CaseParty VALUES ("
                    + "?," //caseYear
                    + "?," //caseType
                    + "?," //caseMonth
                    + "?," //caseYear
                    + "?," //partyID
                    + "?," //caseRelation
                    + "?," //prefix
                    + "?," //firstName
                    + "?," //middleInitial
                    + "?," //lastName
                    + "?," //suffix
                    + "?," //nameTitle
                    + "?," //jobTitle
                    + "?," //companyName
                    + "?," //address1
                    + "?," //address2
                    + "?," //address3
                    + "?," //city
                    + "?," //stateCode
                    + "?," //zipCode
                    + "?," //phone1
                    + "?" //email
                    + "?," //phone2
                    + ")";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseNumber.split("-")[0]);
            preparedStatement.setString(2, newCaseNumber.split("-")[1]);
            preparedStatement.setString(3, newCaseNumber.split("-")[2]);
            preparedStatement.setString(4, newCaseNumber.split("-")[3]);
            preparedStatement.setInt(5, id);
            preparedStatement.setString(6, type);
            preparedStatement.setString(7, partyInformation.prefix);
            preparedStatement.setString(8, partyInformation.firstName);
            preparedStatement.setString(9, partyInformation.middleInitial);
            preparedStatement.setString(10, partyInformation.lastName);
            preparedStatement.setString(11, partyInformation.suffix);
            preparedStatement.setString(12, partyInformation.nameTitle);
            preparedStatement.setString(13, partyInformation.jobTitle);
            preparedStatement.setString(14, partyInformation.companyName);
            preparedStatement.setString(15, partyInformation.address1);
            preparedStatement.setString(16, partyInformation.address2);
            preparedStatement.setString(17, partyInformation.address3);
            preparedStatement.setString(18, partyInformation.city);
            preparedStatement.setString(19, partyInformation.stateCode);
            preparedStatement.setString(20, partyInformation.zipCode);
            preparedStatement.setString(21, partyInformation.phone1.equals("") ? "" : NumberFormatService.convertPhoneNumberToString(partyInformation.phone1));
            preparedStatement.setString(22, partyInformation.emailAddress);
            preparedStatement.setString(23, partyInformation.phone2.equals("") ? "" : NumberFormatService.convertPhoneNumberToString(partyInformation.phone2));
            
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
            
            String sql = "Select * from CaseParty"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseParty.caseNumber = ?"
                    + " order by caseRelation";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            
            ResultSet casePartyRS = preparedStatement.executeQuery();
            
            while(casePartyRS.next()) {
                CaseParty party = new CaseParty();
                party.id = casePartyRS.getInt("id");
                party.caseYear = casePartyRS.getString("caseYear");
                party.caseType = casePartyRS.getString("caseType");
                party.caseMonth = casePartyRS.getString("caseMonth");
                party.caseNumber = casePartyRS.getString("caseNumber");
                party.partyID = casePartyRS.getInt("partyID");
                party.caseRelation = casePartyRS.getString("caseRelation");
                party.prefix = casePartyRS.getString("prefix");
                party.firstName = casePartyRS.getString("firstName");
                party.middleInitial = casePartyRS.getString("middleInitial");
                party.lastName = casePartyRS.getString("lastName");
                party.suffix = casePartyRS.getString("suffix");
                party.nameTitle = casePartyRS.getString("nameTitle");
                party.jobTitle = casePartyRS.getString("jobTitle");
                party.companyName = casePartyRS.getString("companyName");
                party.address1 = casePartyRS.getString("address1");
                party.address2 = casePartyRS.getString("address2");
                party.address3 = casePartyRS.getString("address3");
                party.city = casePartyRS.getString("city");
                party.stateCode = casePartyRS.getString("stateCode");
                party.zipcode = casePartyRS.getString("zipcode");
                party.phone1 = casePartyRS.getString("phone1").equals("") ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone1"));
                party.phone2 = casePartyRS.getString("phone2").equals("") ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone2"));
                party.emailAddress = casePartyRS.getString("email");
                
                parties.add(party);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parties;
    }
    
    public static void updateParty(CaseParty party, String id) {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update CaseParty"
                    + " set prefix = ?,"
                    + " firstName = ?,"
                    + " middleInitial = ?,"
                    + " lastName = ?,"
                    + " suffix = ?,"
                    + " nameTitle = ?,"
                    + " jobTitle = ?,"
                    + " companyName = ?,"
                    + " address1 = ?,"
                    + " address2 = ?,"
                    + " address3 = ?,"
                    + " city = ?,"
                    + " stateCode = ?,"
                    + " zipCode = ?,"
                    + " phone1 = ?,"
                    + " phone2 = ?,"
                    + " email = ?"
                    + " where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, party.prefix);
            preparedStatement.setString(2, party.firstName);
            preparedStatement.setString(3, party.middleInitial);
            preparedStatement.setString(4, party.lastName);
            preparedStatement.setString(5, party.suffix);
            preparedStatement.setString(6, party.nameTitle);
            preparedStatement.setString(7, party.jobTitle);
            preparedStatement.setString(8, party.companyName);
            preparedStatement.setString(9, party.address1);
            preparedStatement.setString(10, party.address2);
            preparedStatement.setString(11, party.address3);
            preparedStatement.setString(12, party.city);
            preparedStatement.setString(13, party.stateCode);
            preparedStatement.setString(14, party.zipcode);
            preparedStatement.setString(15, party.phone1.equals("") ? "" : NumberFormatService.convertPhoneNumberToString(party.phone1));
            preparedStatement.setString(16, party.phone2.equals("") ? "" : NumberFormatService.convertPhoneNumberToString(party.phone2));
            preparedStatement.setString(17, party.emailAddress);
            preparedStatement.setString(18, id);
            
            if(preparedStatement.executeUpdate() == 1) {
                Party.updateParty(party, CaseParty.getPartyID(id));
            }
        } catch (SQLException ex) {
            Logger.getLogger(CaseParty.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    public static int getPartyID(String id) {
        int partyID = 0;
        
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select partyID from CaseParty"
                    + " where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            
            ResultSet casePartyRS = preparedStatement.executeQuery();
            
            while(casePartyRS.next()) {
                partyID = casePartyRS.getInt("partyID");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return partyID;
    }
    
    public static CaseParty getCasePartyByID(String id) {
        CaseParty party = null;
        
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from CaseParty"
                    + " where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            
            ResultSet casePartyRS = preparedStatement.executeQuery();
            
            while(casePartyRS.next()) {
                party = new CaseParty();
                party.id = casePartyRS.getInt("id");
                party.caseYear = casePartyRS.getString("caseYear");
                party.caseType = casePartyRS.getString("caseType");
                party.caseMonth = casePartyRS.getString("caseMonth");
                party.caseNumber = casePartyRS.getString("caseNumber");
                party.partyID = casePartyRS.getInt("partyID");
                party.caseRelation = casePartyRS.getString("caseRelation");
                party.prefix = casePartyRS.getString("prefix");
                party.firstName = casePartyRS.getString("firstName");
                party.middleInitial = casePartyRS.getString("middleInitial");
                party.lastName = casePartyRS.getString("lastName");
                party.suffix = casePartyRS.getString("suffix");
                party.nameTitle = casePartyRS.getString("nameTitle");
                party.jobTitle = casePartyRS.getString("jobTitle");
                party.companyName = casePartyRS.getString("companyName");
                party.address1 = casePartyRS.getString("address1");
                party.address2 = casePartyRS.getString("address2");
                party.address3 = casePartyRS.getString("address3");
                party.city = casePartyRS.getString("city");
                party.stateCode = casePartyRS.getString("stateCode");
                party.zipcode = casePartyRS.getString("zipcode");
                party.phone1 = casePartyRS.getString("phone1").equals("") ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone1"));
                party.phone2 = casePartyRS.getString("phone2").equals("") ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone2"));
                party.emailAddress = casePartyRS.getString("email");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return party;
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
                    + " AND caseParty.caseNumber = ?"
                    + " order by caseRelation";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            preparedStatement.setString(2, caseType);
            preparedStatement.setString(3, caseMonth);
            preparedStatement.setString(4, caseNumber);
            
            ResultSet casePartyRS = preparedStatement.executeQuery();
            
            while(casePartyRS.next()) {
                CaseParty party = new CaseParty();
//                party.id = casePartyRS.getInt("id");
//                party.type = casePartyRS.getString("caseRelation");
//                party.name = casePartyRS.getString("firstName") + " " + casePartyRS.getString("lastName");
//                party.email = casePartyRS.getString("emailAddress");
//                party.phoneNumber = NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("workPhone"));
//                parties.add(party);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parties;
    }
    
    public static void removePartyFromCase(String id) {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Delete from CaseParty where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            
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
