/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class Party {
    public int id;
    public boolean active;
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
    public String zipCode;
    public String phone1;
    public String phone2;
    public String emailAddress;
    
    /**
     * Gets a single party by the party ID
     * @param id the party id
     * @return an instance of the found Party
     */
    public static Party getPartyByID(String id) {
        Party party = new Party();
        
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from Party where id = ? ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet partyRS = preparedStatement.executeQuery();
            
            while(partyRS.next()) {
                party.id = partyRS.getInt("id");
                party.prefix = partyRS.getString("prefix") == null ? null : partyRS.getString("prefix");
                party.firstName = partyRS.getString("firstName") == null ? null : partyRS.getString("firstName");
                party.middleInitial = partyRS.getString("middleInitial") == null ? null : partyRS.getString("middleInitial");
                party.lastName = partyRS.getString("lastName") == null ? null : partyRS.getString("lastName");
                party.suffix = partyRS.getString("suffix") == null ? null : partyRS.getString("suffix");
                party.nameTitle = partyRS.getString("nameTitle") == null ? null : partyRS.getString("nameTitle");
                party.jobTitle = partyRS.getString("jobTitle") == null ? null : partyRS.getString("jobTitle");
                party.companyName = partyRS.getString("companyName") == null ? null : partyRS.getString("companyName");
                party.address1 = partyRS.getString("address1") == null ? null : partyRS.getString("address1");
                party.address2 = partyRS.getString("address2") == null ? null : partyRS.getString("address2");
                party.address3 = partyRS.getString("address3") == null ? null : partyRS.getString("address3");
                party.city = partyRS.getString("city") == null ? "" : partyRS.getString("city");
                party.stateCode = partyRS.getString("stateCode") == null ? null : partyRS.getString("stateCode");
                party.zipCode = partyRS.getString("zipCode") == null ? null : partyRS.getString("zipCode");
                party.phone1 = partyRS.getString("phone1") == null ? null : NumberFormatService.convertStringToPhoneNumber(partyRS.getString("phone1"));
                party.phone2 = partyRS.getString("phone2") == null ? null : NumberFormatService.convertStringToPhoneNumber(partyRS.getString("phone2"));
                party.emailAddress = partyRS.getString("emailAddress") == null ? "" : partyRS.getString("emailAddress");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return party;
    }
    
    /**
     * Loads a list of the top 250 parties from the party table.
     * @return A list of party instances of the found parties
     */
    public static List loadAllParties() {
        List parties = new ArrayList<Party>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from Party";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet partyRS = preparedStatement.executeQuery();
            
            while(partyRS.next()) {
                Party party = new Party();
                party.id = partyRS.getInt("id");
                party.active = partyRS.getBoolean("active");
                party.prefix = partyRS.getString("prefix") == null ? "" : partyRS.getString("prefix");
                party.firstName = partyRS.getString("firstName") == null ? "" : partyRS.getString("firstName");
                party.middleInitial = partyRS.getString("middleInitial") == null ? "" : partyRS.getString("middleInitial");
                party.lastName = partyRS.getString("lastName") == null ? "" : partyRS.getString("lastName");
                party.suffix = partyRS.getString("suffix") == null ? "" : partyRS.getString("suffix");
                party.nameTitle = partyRS.getString("nameTitle") == null ? "" : partyRS.getString("nameTitle");
                party.jobTitle = partyRS.getString("jobTitle") == null ? "" : partyRS.getString("jobTitle");
                party.companyName = partyRS.getString("companyName") == null ? "" : partyRS.getString("companyName");
                party.address1 = partyRS.getString("address1") == null ? "" : partyRS.getString("address1");
                party.address2 = partyRS.getString("address2") == null ? "" : partyRS.getString("address2");
                party.address3 = partyRS.getString("address3") == null ? "" : partyRS.getString("address3");
                party.city = partyRS.getString("city") == null ? "" : partyRS.getString("city");
                party.stateCode = partyRS.getString("stateCode") == null ? "" : partyRS.getString("stateCode");
                party.zipCode = partyRS.getString("zipCode") == null ? "" : partyRS.getString("zipCode");
                party.phone1 = partyRS.getString("phone1") == null ? "" : NumberFormatService.convertStringToPhoneNumber(partyRS.getString("phone1"));
                party.phone2 = partyRS.getString("phone2") == null ? "" : NumberFormatService.convertStringToPhoneNumber(partyRS.getString("phone2"));
                party.emailAddress = partyRS.getString("emailAddress") == null ? "" : partyRS.getString("emailAddress");
                
                parties.add(party);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return parties;
    }
    
    /**
     * Create a new party to be added to the list
     * @param party the party instance that should be created
     */
    public static void createParty(Party party) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Party VALUES ("
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
            preparedStatement.setString(1, party.prefix.equals("") ? null : party.prefix);
            preparedStatement.setString(2, party.firstName.equals("") ? null : party.firstName);
            preparedStatement.setString(3, party.middleInitial.equals("") ? null : party.middleInitial);
            preparedStatement.setString(4, party.lastName.equals("") ? null : party.lastName);
            preparedStatement.setString(5, party.suffix.equals("") ? null : party.suffix);
            preparedStatement.setString(6, party.nameTitle.equals("") ? null : party.nameTitle);
            preparedStatement.setString(7, party.jobTitle.equals("") ? null : party.jobTitle);
            preparedStatement.setString(8, party.companyName.equals("") ? null : party.companyName);
            preparedStatement.setString(9, party.address1.equals("") ? null : party.address1);
            preparedStatement.setString(10, party.address2.equals("") ? null : party.address2);
            preparedStatement.setString(11, party.address3.equals("") ? null : party.address3);
            preparedStatement.setString(12, party.city.equals("") ? null : party.city);
            preparedStatement.setString(13, party.stateCode.equals("") ? null : party.stateCode);
            preparedStatement.setString(14, party.zipCode.equals("") ? null : party.zipCode);
            preparedStatement.setString(15, party.phone1.equals("") ? null : party.phone1);
            preparedStatement.setString(16, party.emailAddress.equals("") ? null : party.emailAddress);
            preparedStatement.setString(17, party.phone2.equals("") ? null : party.phone2);
            preparedStatement.setBoolean(18, true);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateParty(CaseParty party, int id) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update Party set "
                    + "prefix = ?,"
                    + "firstName = ?,"
                    + "middleInitial = ?,"
                    + "lastName = ?,"
                    + "suffix = ?,"
                    + "nameTitle = ?,"
                    + "jobTitle = ?,"
                    + "companyName = ?,"
                    + "address1 = ?,"
                    + "address2 = ?,"
                    + "address3 = ?,"
                    + "city = ?,"
                    + "stateCode = ?,"
                    + "zipCode = ?,"
                    + "phone1 = ?,"
                    + "emailAddress = ?,"
                    + "phone2 = ? where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, party.prefix.equals("") ? null : party.prefix);
            preparedStatement.setString(2, party.firstName.equals("") ? null : party.firstName);
            preparedStatement.setString(3, party.middleInitial.equals("") ? null : party.middleInitial);
            preparedStatement.setString(4, party.lastName.equals("") ? null : party.lastName);
            preparedStatement.setString(5, party.suffix.equals("") ? null : party.suffix);
            preparedStatement.setString(6, party.nameTitle.equals("") ? null : party.nameTitle);
            preparedStatement.setString(7, party.jobTitle.equals("") ? null : party.jobTitle);
            preparedStatement.setString(8, party.companyName.equals("") ? null : party.companyName);
            preparedStatement.setString(9, party.address1.equals("") ? null : party.address1);
            preparedStatement.setString(10, party.address2.equals("") ? null : party.address2);
            preparedStatement.setString(11, party.address3.equals("") ? null : party.address3);
            preparedStatement.setString(12, party.city.equals("") ? null : party.city);
            preparedStatement.setString(13, party.stateCode.equals("") ? null : party.stateCode);
            preparedStatement.setString(14, party.zipcode.equals("") ? null : party.zipcode);
            preparedStatement.setString(15, party.phone1 == null ? null : NumberFormatService.convertPhoneNumberToString(party.phone1));
            preparedStatement.setString(16, party.emailAddress.equals("") ? null : party.emailAddress);
            preparedStatement.setString(17, party.phone2 == null ? null : NumberFormatService.convertPhoneNumberToString(party.phone2));
            preparedStatement.setInt(18, id);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
