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
    
    public static List loadAllMediators() {
        List<Mediator> mediatorList = new ArrayList<Mediator>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "select firstName, middleName, lastName"
                    + " from Mediator"
                    + " Where active = 1"
                    + " ORDER BY lastName ASC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
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
    
    
    
    
    public static List searchMediator(String[] param) {
        List<Mediator> list = new ArrayList<>();

        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Mediator";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(type, firstName, middleName, lastName, "
                            + " email, phone) LIKE ?";
                }
            }
            sql += " ORDER BY lastName";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Mediator item = new Mediator();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.type = rs.getString("type") == null ? "" : rs.getString("type");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                item.email = rs.getString("email") == null ? "" : rs.getString("email");
                item.phone = rs.getString("phone") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("phone"));
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }

    public static Mediator getMediatorByID(int id) {
        Mediator item = new Mediator();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Mediator WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.type = rs.getString("type") == null ? "" : rs.getString("type");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                item.email = rs.getString("email") == null ? "" : rs.getString("email");
                item.phone = rs.getString("phone") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("phone"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public static void createMediator(Mediator item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Mediator ("
                    + "active, "    //01
                    + "type, "      //02
                    + "firstName, " //03
                    + "middleName, "//04
                    + "lastName, "  //05
                    + "email, "     //06
                    + "phone "      //07
                    + ") VALUES (";
                    for(int i=0; i<6; i++){
                        sql += "?, ";   //01-06
                    }
                     sql += "?)";   //07

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, item.type.equals("") ? null : item.type);
            preparedStatement.setString(3, item.firstName.equals("") ? null : item.firstName);
            preparedStatement.setString(4, item.middleName.equals("") ? null : item.middleName);
            preparedStatement.setString(5, item.lastName.equals("") ? null : item.lastName);
            preparedStatement.setString(6, item.email.equals("") ? null : item.email);
            preparedStatement.setString(7, item.phone.equals("") ? null : NumberFormatService.convertPhoneNumberToString(item.phone));
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateMediator(Mediator item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE Mediator SET "
                    + "active = ?, "    //01
                    + "type = ?, "      //02
                    + "firstName = ?, " //03
                    + "middleName = ?, "//04
                    + "lastName = ?, "  //05
                    + "email = ?, "     //06
                    + "phone = ? "      //07
                    + "where id = ?";   //08

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.type.equals("") ? null : item.type);
            preparedStatement.setString(3, item.firstName.equals("") ? null : item.firstName);
            preparedStatement.setString(4, item.middleName.equals("") ? null : item.middleName);
            preparedStatement.setString(5, item.lastName.equals("") ? null : item.lastName);
            preparedStatement.setString(6, item.email.equals("") ? null : item.email);
            preparedStatement.setString(7, item.phone.equals("") ? null : NumberFormatService.convertPhoneNumberToString(item.phone));
            preparedStatement.setInt(8, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
