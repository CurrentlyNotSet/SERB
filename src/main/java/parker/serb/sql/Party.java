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
    public String firstName;
    public String middleInitial;
    public String lastName;
    public String companyName;
    public String emailAddress;
    public String address1;
    public String address2;
    public String address3;
    public String city;
    public String state;
    public String zip;
    public String workPhone;
    
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
                party.firstName = partyRS.getString("firstName");
                party.middleInitial = partyRS.getString("middleInitial");
                party.lastName = partyRS.getString("lastName");
                party.companyName = partyRS.getString("companyName");
                party.address1 = partyRS.getString("address1");
                party.address2 = partyRS.getString("address2");
                party.address3 = partyRS.getString("address3");
                party.city = partyRS.getString("city");
                party.state = partyRS.getString("state");
                party.zip = partyRS.getString("zipCode");
                party.workPhone = partyRS.getString("workPhone").equals("") ? "" : NumberFormatService.convertStringToPhoneNumber(partyRS.getString("workPhone"));
                party.emailAddress = partyRS.getString("emailAddress");
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

            String sql = "select Top 250 * from Party";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet partyRS = preparedStatement.executeQuery();
            
            while(partyRS.next()) {
                Party party = new Party();
                party.id = partyRS.getInt("id");
                party.firstName = partyRS.getString("firstName");
                party.middleInitial = partyRS.getString("middleInitial");
                party.lastName = partyRS.getString("lastName");
                party.companyName = partyRS.getString("companyName");
                party.address1 = partyRS.getString("address1");
                party.address2 = partyRS.getString("address2");
                party.address3 = partyRS.getString("address3");
                party.city = partyRS.getString("city");
                party.state = partyRS.getString("state");
                party.zip = partyRS.getString("zipCode");
                party.workPhone = partyRS.getString("workPhone").equals("") ? "" : NumberFormatService.convertStringToPhoneNumber(partyRS.getString("workPhone"));
                party.emailAddress = partyRS.getString("emailAddress");
                
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
                    + "?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, party.firstName);
            preparedStatement.setString(2, party.middleInitial);
            preparedStatement.setString(3, party.lastName);
            preparedStatement.setString(4, party.companyName);
            preparedStatement.setString(5, party.address1);
            preparedStatement.setString(6, party.address2);
            preparedStatement.setString(7, party.address3);
            preparedStatement.setString(8, party.city);
            preparedStatement.setString(9, party.state);
            preparedStatement.setString(10, party.zip);
            preparedStatement.setString(11, party.workPhone);
            preparedStatement.setString(12, party.emailAddress);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void updateParty(Party party, String id) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update Party set "
                    + "firstName = ?,"
                    + "middleInitial = ?,"
                    + "lastName = ?,"
                    + "companyName = ?,"
                    + "address1 = ?,"
                    + "address2 = ?,"
                    + "address3 = ?,"
                    + "city = ?,"
                    + "state = ?,"
                    + "zipCode = ?,"
                    + "workPhone = ?,"
                    + "emailAddress = ? where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, party.firstName);
            preparedStatement.setString(2, party.middleInitial);
            preparedStatement.setString(3, party.lastName);
            preparedStatement.setString(4, party.companyName);
            preparedStatement.setString(5, party.address1);
            preparedStatement.setString(6, party.address2);
            preparedStatement.setString(7, party.address3);
            preparedStatement.setString(8, party.city);
            preparedStatement.setString(9, party.state);
            preparedStatement.setString(10, party.zip);
            preparedStatement.setString(11, party.workPhone);
            preparedStatement.setString(12, party.emailAddress);
            preparedStatement.setString(13, id);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
