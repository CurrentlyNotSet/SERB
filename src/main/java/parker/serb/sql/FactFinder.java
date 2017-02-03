package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;
import parker.serb.util.StringUtilities;

/**
 *
 * @author parkerjohnston
 */
public class FactFinder {
    
    public int id;
    public boolean active;
    public String status;
    public String firstName;
    public String middleName;
    public String lastName;
    public String address1;
    public String address2;
    public String address3;
    public String city;
    public String state;
    public String zip;
    public String email;
    public String phone;
    public String bioFileName;

    public static List loadAllConciliators() {
        List<String> factFinderList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from FactFinder"
                    + " where status = 'C'"
                    + " and active = 1"
                    + " order by lastName asc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet factFinderListRS = preparedStatement.executeQuery();
            
            while(factFinderListRS.next()) {
                String name = StringUtilities.buildFullName(
                        factFinderListRS.getString("firstName"), 
                        factFinderListRS.getString("middleName"),
                        factFinderListRS.getString("lastName"));
                factFinderList.add(name);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllConciliators();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return factFinderList;
    }
    
    public static List loadAllFF() {
        List<String> factFinderList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from FactFinder"
                    + " where status != 'O'"
                    + " and active = 1"
                    + " order by lastName asc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet factFinderListRS = preparedStatement.executeQuery();
            
            while(factFinderListRS.next()) {
                String name = StringUtilities.buildFullName(
                        factFinderListRS.getString("firstName"), 
                        factFinderListRS.getString("middleName"),
                        factFinderListRS.getString("lastName"));
                
                factFinderList.add(name);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllFF();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return factFinderList;
    }
        
    public static List loadActiveFF() {
        List<FactFinder> factFinderList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select * from FactFinder"
                    + " where status != 'O'"
                    + " and active = 1"
                    + " order by lastName asc";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                FactFinder item = new FactFinder();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.status = rs.getString("status") == null ? "" : rs.getString("status");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                item.address1 = rs.getString("address1") == null ? "" : rs.getString("address1");
                item.address2 = rs.getString("address2") == null ? "" : rs.getString("address2");
                item.address3 = rs.getString("address3") == null ? "" : rs.getString("address3");
                item.city = rs.getString("city") == null ? "" : rs.getString("city");
                item.state = rs.getString("state") == null ? "" : rs.getString("state");
                item.zip = rs.getString("zip") == null ? "" : rs.getString("zip");
                item.email = rs.getString("email") == null ? "" : rs.getString("email");
                item.phone = rs.getString("phone") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("phone"));
                item.bioFileName = rs.getString("bioFileName") == null ? "" : rs.getString("bioFileName");
                factFinderList.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadActiveFF();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return factFinderList;
    }
    
    
    public static List searchFactFinder(String[] param) {
        List<FactFinder> recommendationList = new ArrayList<>();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM FactFinder";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(status, firstName, middleName, lastName, "
                            + "address1, address2, address3, city, state, "
                            + "zip, email, phone) LIKE ?";
                }
            }
            sql += " ORDER BY lastName";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                FactFinder item = new FactFinder();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.status = rs.getString("status") == null ? "" : rs.getString("status");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                item.address1 = rs.getString("address1") == null ? "" : rs.getString("address1");
                item.address2 = rs.getString("address2") == null ? "" : rs.getString("address2");
                item.address3 = rs.getString("address3") == null ? "" : rs.getString("address3");
                item.city = rs.getString("city") == null ? "" : rs.getString("city");
                item.state = rs.getString("state") == null ? "" : rs.getString("state");
                item.zip = rs.getString("zip") == null ? "" : rs.getString("zip");
                item.email = rs.getString("email") == null ? "" : rs.getString("email");
                item.phone = rs.getString("phone") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("phone"));
                item.bioFileName = rs.getString("bioFileName") == null ? "" : rs.getString("bioFileName");
                recommendationList.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                searchFactFinder(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return recommendationList;
    }

    public static FactFinder getFactFinderByID(int id) {
        FactFinder item = new FactFinder();

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM FactFinder WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.status = rs.getString("status") == null ? "" : rs.getString("status");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                item.address1 = rs.getString("address1") == null ? "" : rs.getString("address1");
                item.address2 = rs.getString("address2") == null ? "" : rs.getString("address2");
                item.address3 = rs.getString("address3") == null ? "" : rs.getString("address3");
                item.city = rs.getString("city") == null ? "" : rs.getString("city");
                item.state = rs.getString("state") == null ? "" : rs.getString("state");
                item.zip = rs.getString("zip") == null ? "" : rs.getString("zip");
                item.email = rs.getString("email") == null ? "" : rs.getString("email");
                item.phone = rs.getString("phone") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("phone"));
                item.bioFileName = rs.getString("bioFileName") == null ? "" : rs.getString("bioFileName");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getFactFinderByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static FactFinder getFactFinderLikeName(String firstName, String lastName) {
        FactFinder item = null;

        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM FactFinder WHERE firstname LIKE ? AND lastname LIKE ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, "%" + firstName + "%");
            preparedStatement.setString(2, "%" + lastName + "%");

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item = new FactFinder();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.status = rs.getString("status") == null ? "" : rs.getString("status");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                item.address1 = rs.getString("address1") == null ? "" : rs.getString("address1");
                item.address2 = rs.getString("address2") == null ? "" : rs.getString("address2");
                item.address3 = rs.getString("address3") == null ? "" : rs.getString("address3");
                item.city = rs.getString("city") == null ? "" : rs.getString("city");
                item.state = rs.getString("state") == null ? "" : rs.getString("state");
                item.zip = rs.getString("zip") == null ? "" : rs.getString("zip");
                item.email = rs.getString("email") == null ? "" : rs.getString("email");
                item.phone = rs.getString("phone") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("phone"));
                item.bioFileName = rs.getString("bioFileName") == null ? "" : rs.getString("bioFileName");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getFactFinderLikeName(firstName, lastName);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }
    
    public static void createFactFinder(FactFinder item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO FactFinder ("
                    + "active, "
                    + "status, "
                    + "firstName, "
                    + "middleName, "
                    + "lastName, "
                    + "address1, "
                    + "address2, "
                    + "address3, "
                    + "city, "
                    + "state, "
                    + "zip, "
                    + "email, "
                    + "phone, "
                    + "bioFileName "
                    + ") VALUES (";
                    for(int i=0; i<13; i++){
                        sql += "?, ";   //01-13
                    }
                     sql += "?)";   //14

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, item.status.equals("") ? null : item.status);
            preparedStatement.setString(3, item.firstName.equals("") ? null : item.firstName);
            preparedStatement.setString(4, item.middleName.equals("") ? null : item.middleName);
            preparedStatement.setString(5, item.lastName.equals("") ? null : item.lastName);
            preparedStatement.setString(6, item.address1.equals("") ? null : item.address1);
            preparedStatement.setString(7, item.address2.equals("") ? null : item.address2);
            preparedStatement.setString(8, item.address3.equals("") ? null : item.address3);
            preparedStatement.setString(9, item.city.equals("") ? null : item.city);
            preparedStatement.setString(10, item.state.equals("") ? null : item.state);
            preparedStatement.setString(11, item.zip.equals("") ? null : item.zip);
            preparedStatement.setString(12, item.email.equals("") ? null : item.email);
            preparedStatement.setString(13, item.phone.equals("") ? null : NumberFormatService.convertPhoneNumberToString(item.phone));
            preparedStatement.setString(14, item.bioFileName.equals("") ? null : item.bioFileName);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createFactFinder(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateFactFinder(FactFinder item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE FactFinder SET "
                    + "active = ?, "
                    + "status = ?, "
                    + "firstName = ?, "
                    + "middleName = ?, "
                    + "lastName = ?, "
                    + "address1 = ?, "
                    + "address2 = ?, "
                    + "address3 = ?, "
                    + "city = ?, "
                    + "state = ?, "
                    + "zip = ?, "
                    + "email = ?, "
                    + "phone = ?, "
                    + "bioFileName = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.status.equals("") ? null : item.status);
            preparedStatement.setString(3, item.firstName.equals("") ? null : item.firstName);
            preparedStatement.setString(4, item.middleName.equals("") ? null : item.middleName);
            preparedStatement.setString(5, item.lastName.equals("") ? null : item.lastName);
            preparedStatement.setString(6, item.address1.equals("") ? null : item.address1);
            preparedStatement.setString(7, item.address2.equals("") ? null : item.address2);
            preparedStatement.setString(8, item.address3.equals("") ? null : item.address3);
            preparedStatement.setString(9, item.city.equals("") ? null : item.city);
            preparedStatement.setString(10, item.state.equals("") ? null : item.state);
            preparedStatement.setString(11, item.zip.equals("") ? null : item.zip);
            preparedStatement.setString(12, item.email.equals("") ? null : item.email);
            preparedStatement.setString(13, item.phone.equals("") ? null : NumberFormatService.convertPhoneNumberToString(item.phone));
            preparedStatement.setString(14, item.bioFileName.equals("") ? null : item.bioFileName);
            preparedStatement.setInt(15, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateFactFinder(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
