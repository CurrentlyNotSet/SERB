package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;

/**
 *
 * @author parkerjohnston
 */
public class Mediator {
    
    public int id;
    public boolean active;
    public String type;
    public String firstName;
    public String middleName;
    public String lastName;
    public String phone;
    public String email;
    
    

    public static List loadMediators(String type) {
        List<Mediator> mediatorList = new ArrayList<Mediator>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "select firstName, middleName, lastName"
                    + " from Mediator"
                    + " Where active = 1 and type = ?"
                    + " ORDER BY lastName ASC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, type);
            
            ResultSet employerListRS = preparedStatement.executeQuery();
            
            
            while(employerListRS.next()) {
                Mediator med = new Mediator();
                med.firstName = employerListRS.getString("firstName") == null ? "" : employerListRS.getString("firstName");
                med.middleName = employerListRS.getString("middleName") == null ? "" : employerListRS.getString("middleName");
                med.lastName = employerListRS.getString("lastName") == null ? "" : employerListRS.getString("lastName");
                mediatorList.add(med);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return mediatorList;
    }
    
    public static String getMediatorPhoneNumber(String name) {
        String[] nameParts = name.split(" ");
        String phone = "";
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select phone from mediator where firstName = ? and lastName = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, nameParts[0]);
            preparedStatement.setString(2, nameParts[1]);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                phone = caseActivity.getString("phone");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phone;
    }
    
    public static String getMediatorNameByID(String id) {
        String name = "";
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select firstName, LastName from mediator where id = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                name = caseActivity.getString("firstName") + " ";
                name += caseActivity.getString("lastName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
    
    public static String getMediatorPhoneByID(String id) {
        String phone = "";
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select phone from mediator where id = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                phone = caseActivity.getString("phone");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return phone;
    }
    
    public static String getMediatorIDByName(String name) {
        String[] nameParts = name.split(" ");
        String id = "";
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select id from mediator where firstName = ? and lastName = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, nameParts[0]);
            preparedStatement.setString(2, nameParts[1]);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                id = caseActivity.getString("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id;
    }
}
