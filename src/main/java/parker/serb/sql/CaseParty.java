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
    public String fax;
    
    
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
                    + "?,"
                    + "?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setInt(5, party.id);
            preparedStatement.setString(6, type);
            preparedStatement.setString(7, party.prefix == null ? null : party.prefix);
            preparedStatement.setString(8, party.firstName == null ? null : party.firstName);
            preparedStatement.setString(9, party.middleInitial == null ? null : party.middleInitial);
            preparedStatement.setString(10, party.lastName == null ? null : party.lastName);
            preparedStatement.setString(11, party.suffix == null ? null : party.suffix);
            preparedStatement.setString(12, party.nameTitle == null ? null : party.nameTitle);
            preparedStatement.setString(13, party.jobTitle == null ? null : party.jobTitle);
            preparedStatement.setString(14, party.companyName == null ? null : party.companyName);
            preparedStatement.setString(15, party.address1 == null ? null : party.address1);
            preparedStatement.setString(16, party.address2 == null ? null : party.address2);
            preparedStatement.setString(17, party.address3 == null ? null : party.address3);
            preparedStatement.setString(18, party.city == null ? null : party.city);
            preparedStatement.setString(19, party.stateCode == null ? null : party.stateCode);
            preparedStatement.setString(20, party.zipCode == null ? null : party.zipCode);
            preparedStatement.setString(21, party.phone1 == null ? null : NumberFormatService.convertPhoneNumberToString(party.phone1));
            preparedStatement.setString(22, party.emailAddress == null ? null : party.emailAddress);
            preparedStatement.setString(23, party.phone2 == null ? null : NumberFormatService.convertPhoneNumberToString(party.phone2));
            preparedStatement.setString(24, party.fax == null ? null : NumberFormatService.convertPhoneNumberToString(party.fax));
            
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
                    + "?,"  //email
                    + "?," //phone2
                    + "?"  //fax
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
            preparedStatement.setString(21, partyInformation.phone1 == null ? null : NumberFormatService.convertPhoneNumberToString(partyInformation.phone1));
            preparedStatement.setString(22, partyInformation.emailAddress);
            preparedStatement.setString(23, partyInformation.phone2 == null ? null : NumberFormatService.convertPhoneNumberToString(partyInformation.phone2));
            preparedStatement.setString(24, partyInformation.fax == null ? null : NumberFormatService.convertPhoneNumberToString(partyInformation.fax));
            
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
                party.prefix = casePartyRS.getString("prefix") == null ? "" : casePartyRS.getString("prefix");
                party.firstName = casePartyRS.getString("firstName") == null ? "" : casePartyRS.getString("firstName");
                party.middleInitial = casePartyRS.getString("middleInitial") == null ? "" : casePartyRS.getString("middleInitial");
                party.lastName = casePartyRS.getString("lastName") == null ? "" : casePartyRS.getString("lastName");
                party.suffix = casePartyRS.getString("suffix") == null ? "" : casePartyRS.getString("suffix");
                party.nameTitle = casePartyRS.getString("nameTitle") == null ? "" : casePartyRS.getString("nameTitle");
                party.jobTitle = casePartyRS.getString("jobTitle") == null ? "" : casePartyRS.getString("jobTitle");
                party.companyName = casePartyRS.getString("companyName") == null ? "" : casePartyRS.getString("companyName");
                party.address1 = casePartyRS.getString("address1") == null ? "" : casePartyRS.getString("address1");
                party.address2 = casePartyRS.getString("address2") == null ? "" : casePartyRS.getString("address2");
                party.address3 = casePartyRS.getString("address3") == null ? "" : casePartyRS.getString("address3");
                party.city = casePartyRS.getString("city") == null ? "" : casePartyRS.getString("city");
                party.stateCode = casePartyRS.getString("stateCode") == null ? "" : casePartyRS.getString("stateCode");
                party.zipcode = casePartyRS.getString("zipcode") == null ? "" : casePartyRS.getString("zipcode");
                party.phone1 = casePartyRS.getString("phone1") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone1"));
                party.phone2 = casePartyRS.getString("phone2") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone2"));
                party.emailAddress = casePartyRS.getString("email") == null ? "" : casePartyRS.getString("email");
                party.fax = casePartyRS.getString("fax") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("fax"));
                
                parties.add(party);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parties;
    }
    
    public static List<CaseParty> loadORGPartiesByCase() {
        List<CaseParty> parties = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT * FROM CaseParty Left JOIN Party on CaseParty.partyID = Party.id where"
//                    + " where caseYear = ?"
                    + " caseType = ?"
//                    + " and caseMonth = ?"
                    + " and caseParty.caseNumber = ?"
                    + " order by caseRelation";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseType);
            preparedStatement.setString(2, Global.caseNumber);
            
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
                party.prefix = casePartyRS.getString("prefix") == null ? "" : casePartyRS.getString("prefix");
                party.firstName = casePartyRS.getString("firstName") == null ? "" : casePartyRS.getString("firstName");
                party.middleInitial = casePartyRS.getString("middleInitial") == null ? "" : casePartyRS.getString("middleInitial");
                party.lastName = casePartyRS.getString("lastName") == null ? "" : casePartyRS.getString("lastName");
                party.suffix = casePartyRS.getString("suffix") == null ? "" : casePartyRS.getString("suffix");
                party.nameTitle = casePartyRS.getString("nameTitle") == null ? "" : casePartyRS.getString("nameTitle");
                party.jobTitle = casePartyRS.getString("jobTitle") == null ? "" : casePartyRS.getString("jobTitle");
                party.companyName = casePartyRS.getString("companyName") == null ? "" : casePartyRS.getString("companyName");
                party.address1 = casePartyRS.getString("address1") == null ? "" : casePartyRS.getString("address1");
                party.address2 = casePartyRS.getString("address2") == null ? "" : casePartyRS.getString("address2");
                party.address3 = casePartyRS.getString("address3") == null ? "" : casePartyRS.getString("address3");
                party.city = casePartyRS.getString("city") == null ? "" : casePartyRS.getString("city");
                party.stateCode = casePartyRS.getString("stateCode") == null ? "" : casePartyRS.getString("stateCode");
                party.zipcode = casePartyRS.getString("zipcode") == null ? "" : casePartyRS.getString("zipcode");
                party.phone1 = casePartyRS.getString("phone1") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone1"));
                party.phone2 = casePartyRS.getString("phone2") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone2"));
                party.emailAddress = casePartyRS.getString("email") == null ? "" : casePartyRS.getString("email");
                
                parties.add(party);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parties;
    }
    
    public static List<CaseParty> loadCSCPartiesByCase() {
        List<CaseParty> parties = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT * FROM CaseParty Left JOIN Party on CaseParty.partyID = Party.id where"
//                    + " where caseYear = ?"
                    + " caseType = ?"
//                    + " and caseMonth = ?"
                    + " and caseParty.caseNumber = ?"
                    + " order by caseRelation";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseType);
            preparedStatement.setString(2, Global.caseNumber);
            
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
                party.prefix = casePartyRS.getString("prefix") == null ? "" : casePartyRS.getString("prefix");
                party.firstName = casePartyRS.getString("firstName") == null ? "" : casePartyRS.getString("firstName");
                party.middleInitial = casePartyRS.getString("middleInitial") == null ? "" : casePartyRS.getString("middleInitial");
                party.lastName = casePartyRS.getString("lastName") == null ? "" : casePartyRS.getString("lastName");
                party.suffix = casePartyRS.getString("suffix") == null ? "" : casePartyRS.getString("suffix");
                party.nameTitle = casePartyRS.getString("nameTitle") == null ? "" : casePartyRS.getString("nameTitle");
                party.jobTitle = casePartyRS.getString("jobTitle") == null ? "" : casePartyRS.getString("jobTitle");
                party.companyName = casePartyRS.getString("companyName") == null ? "" : casePartyRS.getString("companyName");
                party.address1 = casePartyRS.getString("address1") == null ? "" : casePartyRS.getString("address1");
                party.address2 = casePartyRS.getString("address2") == null ? "" : casePartyRS.getString("address2");
                party.address3 = casePartyRS.getString("address3") == null ? "" : casePartyRS.getString("address3");
                party.city = casePartyRS.getString("city") == null ? "" : casePartyRS.getString("city");
                party.stateCode = casePartyRS.getString("stateCode") == null ? "" : casePartyRS.getString("stateCode");
                party.zipcode = casePartyRS.getString("zipcode") == null ? "" : casePartyRS.getString("zipcode");
                party.phone1 = casePartyRS.getString("phone1") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone1"));
                party.phone2 = casePartyRS.getString("phone2") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone2"));
                party.emailAddress = casePartyRS.getString("email") == null ? "" : casePartyRS.getString("email");
                
                parties.add(party);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parties;
    }
    
    public static List<CaseParty> loadORGPartiesByCase(String orgNumber) {
        List<CaseParty> parties = new ArrayList<>();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT * FROM CaseParty"
                    + " where caseYear IS NULL "
                    + " and caseType = 'ORG'"
                    + " and caseMonth IS NULL"
                    + " and caseParty.caseNumber = ?"
                    + " order by caseRelation";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, orgNumber);
            
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
                party.prefix = casePartyRS.getString("prefix") == null ? "" : casePartyRS.getString("prefix");
                party.firstName = casePartyRS.getString("firstName") == null ? "" : casePartyRS.getString("firstName");
                party.middleInitial = casePartyRS.getString("middleInitial") == null ? "" : casePartyRS.getString("middleInitial");
                party.lastName = casePartyRS.getString("lastName") == null ? "" : casePartyRS.getString("lastName");
                party.suffix = casePartyRS.getString("suffix") == null ? "" : casePartyRS.getString("suffix");
                party.nameTitle = casePartyRS.getString("nameTitle") == null ? "" : casePartyRS.getString("nameTitle");
                party.jobTitle = casePartyRS.getString("jobTitle") == null ? "" : casePartyRS.getString("jobTitle");
                party.companyName = casePartyRS.getString("companyName") == null ? "" : casePartyRS.getString("companyName");
                party.address1 = casePartyRS.getString("address1") == null ? "" : casePartyRS.getString("address1");
                party.address2 = casePartyRS.getString("address2") == null ? "" : casePartyRS.getString("address2");
                party.address3 = casePartyRS.getString("address3") == null ? "" : casePartyRS.getString("address3");
                party.city = casePartyRS.getString("city") == null ? "" : casePartyRS.getString("city");
                party.stateCode = casePartyRS.getString("stateCode") == null ? "" : casePartyRS.getString("stateCode");
                party.zipcode = casePartyRS.getString("zipcode") == null ? "" : casePartyRS.getString("zipcode");
                party.phone1 = casePartyRS.getString("phone1") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone1"));
                party.phone2 = casePartyRS.getString("phone2") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone2"));
                party.emailAddress = casePartyRS.getString("email") == null ? "" : casePartyRS.getString("email");
                
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
                    + " set caseRelation = ?,"
                    + " prefix = ?,"
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
                    + " email = ?,"
                    + " fax = ? "
                    + " where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, party.caseRelation);
            preparedStatement.setString(2, party.prefix.equals("") ? null : party.prefix);
            preparedStatement.setString(3, party.firstName.equals("") ? null : party.firstName);
            preparedStatement.setString(4, party.middleInitial.equals("") ? null : party.middleInitial);
            preparedStatement.setString(5, party.lastName.equals("") ? null : party.lastName);
            preparedStatement.setString(6, party.suffix.equals("") ? null : party.suffix);
            preparedStatement.setString(7, party.nameTitle.equals("") ? null : party.nameTitle);
            preparedStatement.setString(8, party.jobTitle.equals("") ? null : party.jobTitle);
            preparedStatement.setString(9, party.companyName.equals("") ? null : party.companyName);
            preparedStatement.setString(10, party.address1.equals("") ? null : party.address1);
            preparedStatement.setString(11, party.address2.equals("") ? null : party.address2);
            preparedStatement.setString(12, party.address3.equals("") ? null : party.address3);
            preparedStatement.setString(13, party.city.equals("") ? null : party.city);
            preparedStatement.setString(14, party.stateCode.equals("") ? null : party.stateCode);
            preparedStatement.setString(15, party.zipcode.equals("") ? null : party.zipcode);
            preparedStatement.setString(16, party.phone1 == null ? null : NumberFormatService.convertPhoneNumberToString(party.phone1));
            preparedStatement.setString(17, party.phone2 == null ? null : NumberFormatService.convertPhoneNumberToString(party.phone2));
            preparedStatement.setString(18, party.emailAddress.equals("") ? null : party.emailAddress);
            preparedStatement.setString(19, party.fax == null ? null : NumberFormatService.convertPhoneNumberToString(party.fax));
            preparedStatement.setString(20, id);
            
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
    
    public static String getCasePartyByIDForElection(int id) {
        String party = null;
        
        try {
            
            if(id == 0) {
                return "No Representative";
            }
            

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from CaseParty"
                    + " where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet casePartyRS = preparedStatement.executeQuery();
            
            while(casePartyRS.next()) {
//                party = new CaseParty();
//                party.id = casePartyRS.getInt("id");
//                party.caseYear = casePartyRS.getString("caseYear");
//                party.caseType = casePartyRS.getString("caseType");
//                party.caseMonth = casePartyRS.getString("caseMonth");
//                party.caseNumber = casePartyRS.getString("caseNumber");
//                party.partyID = casePartyRS.getInt("partyID");
//                party.caseRelation = casePartyRS.getString("caseRelation");
//                party.prefix = casePartyRS.getString("prefix");
//                party.firstName = casePartyRS.getString("firstName");
//                party.middleInitial = casePartyRS.getString("middleInitial");
//                party.lastName = casePartyRS.getString("lastName");
//                party.suffix = casePartyRS.getString("suffix");
//                party.nameTitle = casePartyRS.getString("nameTitle");
//                party.jobTitle = casePartyRS.getString("jobTitle");
//                party.companyName = casePartyRS.getString("companyName");
//                party.address1 = casePartyRS.getString("address1");
//                party.address2 = casePartyRS.getString("address2");
//                party.address3 = casePartyRS.getString("address3");
//                party.city = casePartyRS.getString("city");
//                party.stateCode = casePartyRS.getString("stateCode");
//                party.zipcode = casePartyRS.getString("zipcode");
//                party.phone1 = casePartyRS.getString("phone1") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone1"));
//                party.phone2 = casePartyRS.getString("phone2") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone2"));
//                party.emailAddress = casePartyRS.getString("email");
//                
                if(casePartyRS.getString("companyName") == null) {
                    party = casePartyRS.getString("firstName") + " " + casePartyRS.getString("LastName");
                } else {
                    party = casePartyRS.getString("companyName");
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return party;
    }
    
    public static int getElectionID(String name) {
        int partyID = 0;
        
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select COUNT(*) AS THERE from CaseParty"
                    + " where companyName = ?"
                    + " and caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);
            
            ResultSet casePartyRS = preparedStatement.executeQuery();
            
            casePartyRS.next();
            
            if(casePartyRS.getInt("THERE") > 0) {
                Statement stmt2 = Database.connectToDB().createStatement();
            
                String sql2 = "Select id from CaseParty"
                        + " where companyName = ?"
                        + " and caseYear = ? "
                        + " and caseType = ?"
                        + " and caseMonth = ?"
                        + " and caseNumber = ?";

                PreparedStatement preparedStatement2 = stmt2.getConnection().prepareStatement(sql2);
                preparedStatement2.setString(1, name);
                preparedStatement2.setString(2, Global.caseYear);
                preparedStatement2.setString(3, Global.caseType);
                preparedStatement2.setString(4, Global.caseMonth);
                preparedStatement2.setString(5, Global.caseNumber);

                ResultSet casePartyRS2 = preparedStatement2.executeQuery();
                
                if(casePartyRS2.next()) {
                    partyID = casePartyRS2.getInt("id");
                }
                
                
                
            } else {
                Statement stmt3 = Database.connectToDB().createStatement();
            
                String sql3 = "Select id from CaseParty"
                        + " where firstName = ? and LastName = ?"
                        + " and caseYear = ? "
                        + " and caseType = ?"
                        + " and caseMonth = ?"
                        + " and caseNumber = ?";

                PreparedStatement preparedStatement3 = stmt3.getConnection().prepareStatement(sql3);
                preparedStatement3.setString(1, name.split(" ")[0]);
                preparedStatement3.setString(2, name.split(" ")[1]);
                preparedStatement3.setString(3, Global.caseYear);
                preparedStatement3.setString(4, Global.caseType);
                preparedStatement3.setString(5, Global.caseMonth);
                preparedStatement3.setString(6, Global.caseNumber);

                ResultSet casePartyRS3 = preparedStatement3.executeQuery();
                
                if(casePartyRS3.next()) {
                    partyID = casePartyRS3.getInt("id");
                } 
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
                party.phone1 = casePartyRS.getString("phone1") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone1"));
                party.phone2 = casePartyRS.getString("phone2") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("phone2"));
                party.fax = casePartyRS.getString("fax") == null ? "" : NumberFormatService.convertStringToPhoneNumber(casePartyRS.getString("fax"));
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
            
            String sql = "Select partyID, caseRelation, prefix, "
                    + " firstName, middleInitial, lastName, suffix,"
                    + " nameTitle, companyName from caseParty"
                    + " where caseYear = ?"
                    + " AND caseType = ?"
                    + " AND caseMonth = ?"
                    + " AND caseNumber = ?"
                    + " order by caseRelation";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            preparedStatement.setString(2, caseType);
            preparedStatement.setString(3, caseMonth);
            preparedStatement.setString(4, caseNumber);
            
            ResultSet casePartyRS = preparedStatement.executeQuery();
            
            while(casePartyRS.next()) {
                CaseParty party = new CaseParty();
                party.partyID = casePartyRS.getInt("partyID");
                party.caseRelation = casePartyRS.getString("caseRelation");
                party.prefix = casePartyRS.getString("prefix");
                party.firstName = casePartyRS.getString("firstName");
                party.middleInitial = casePartyRS.getString("middleInitial");
                party.lastName = casePartyRS.getString("lastName");
                party.suffix = casePartyRS.getString("suffix");
                party.nameTitle = casePartyRS.getString("nameTitle");
                party.companyName = casePartyRS.getString("companyName");
                parties.add(party);
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
