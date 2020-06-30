package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class Employer {
    
    public int id;
    public boolean active;
    public int employerType;
    public String employerName;
    public String prefix;
    public String firstName;
    public String middleInitial;
    public String lastName;
    public String suffix;
    public String title;
    public String address1;
    public String address2;
    public String address3;
    public String city;
    public String state;
    public String zipCode;
    public String phoneNumber1;
    public String phoneNumber2;
    public String faxNumber;
    public String emailAddress;
    public String employerIDNumber;
    public String employerTypeCode;
    public String jurisdiction;
    public String region;
    public String assistantFirstName;
    public String assistantMiddleInitial;
    public String assistantLastName;
    public String assistantEmail;
    public String county;
    public String population;
    public String employerIRN;

    public static List loadEmployerList() {
        List<Employer> employerList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select employerIDNumber,"
                    + " EmployerName,"
                    + " County"
                    + " from Employers"
                    + " Where employerType = 2"
                    + " ORDER BY employerName ASC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            ResultSet employerListRS = preparedStatement.executeQuery();
            
            while(employerListRS.next()) {
                Employer emp = new Employer();
                emp.county = employerListRS.getString("County") == null ? "" : employerListRS.getString("County");
                emp.employerName = employerListRS.getString("employerName") == null ? "" : employerListRS.getString("employerName");
                emp.employerIDNumber = employerListRS.getString("EmployerIDNumber") == null ? "" : employerListRS.getString("EmployerIDNumber");
                employerList.add(emp);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadEmployerList();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return employerList;
    }
    
    public static Employer loadEmployerByID(String id) {
        Employer employer = new Employer();
           
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select  * from Employers where EmployerIDNumber = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            if(caseActivity == null) {
                employer.employerName = "";
            }
            
            while(caseActivity.next()) {
                employer.id = caseActivity.getInt("id");
                employer.active = caseActivity.getBoolean("active");
                employer.employerType = caseActivity.getInt("employerType");
                employer.employerName = caseActivity.getString("employerName");
                employer.prefix = caseActivity.getString("prefix");
                employer.firstName = caseActivity.getString("firstName");
                employer.middleInitial = caseActivity.getString("middleInitial");
                employer.lastName = caseActivity.getString("lastName");
                employer.suffix = caseActivity.getString("suffix");
                employer.title = caseActivity.getString("title");
                employer.address1 = caseActivity.getString("address1");
                employer.address2 = caseActivity.getString("address2");
                employer.address3 = caseActivity.getString("address3");
                employer.city = caseActivity.getString("city");
                employer.state = caseActivity.getString("state");
                employer.zipCode = caseActivity.getString("zipCode");
                employer.phoneNumber1 = caseActivity.getString("phone1");
                employer.phoneNumber2 = "";
                employer.faxNumber = "";
                employer.emailAddress = caseActivity.getString("emailAddress");
                employer.employerIDNumber = caseActivity.getString("employerIDNumber");
                employer.employerTypeCode = caseActivity.getString("employerTypeCode");
                employer.jurisdiction = caseActivity.getString("jurisdiction");
                employer.region = caseActivity.getString("region");
                employer.assistantFirstName = caseActivity.getString("assistantFirstName");
                employer.assistantMiddleInitial = caseActivity.getString("assistantMiddleInitial");
                employer.assistantLastName = caseActivity.getString("assistantLastName");
                employer.county = caseActivity.getString("county");
                employer.population = caseActivity.getString("population");
                employer.employerIRN = caseActivity.getString("EmployerIRN");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadEmployerByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return employer;
    }
    
    public static void updateEmployerByEmployerIDNumber(String id, Employer emp) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Employers set"
                    + " employerName = ?,"
                    + " prefix = ?,"
                    + " firstName = ?,"
                    + " middleInitial = ?,"
                    + " lastName = ?,"
                    + " suffix = ?,"
                    + " address1 = ?,"
                    + " address2 = ?,"
                    + " address3 = ?,"
                    + " city = ?,"
                    + " [state] = ?,"
                    + " zipCode = ?,"
                    + " county = ?,"
                    + " jurisdiction = ?,"
                    + " phone1 = ?,"
                    + " emailAddress = ?,"
                    + " population = ?,"
                    + " employerIRN = ? "
                    + " WHERE employerIDNumber = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, emp.employerName);
            preparedStatement.setString(2, emp.prefix);
            preparedStatement.setString(3, emp.firstName);
            preparedStatement.setString(4, emp.middleInitial);
            preparedStatement.setString(5, emp.lastName);
            preparedStatement.setString(6, emp.suffix);
            preparedStatement.setString(7, emp.address1);
            preparedStatement.setString(8, emp.address2);
            preparedStatement.setString(9, emp.address3);
            preparedStatement.setString(10, emp.city);
            preparedStatement.setString(11, emp.state);
            preparedStatement.setString(12, emp.zipCode);
            preparedStatement.setString(13, emp.county);
            preparedStatement.setString(14, emp.jurisdiction);
            preparedStatement.setString(15, emp.phoneNumber1);
            preparedStatement.setString(16, emp.emailAddress);
            preparedStatement.setString(17, emp.population);
            preparedStatement.setString(18, emp.employerIRN);
            preparedStatement.setString(19, emp.employerIDNumber);

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateEmployerByEmployerIDNumber(id, emp);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static String getEmployerCountyByID(String id) {
        Statement stmt = null;
        
        String county = "";
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select county from Employers"
                    + " WHERE employerIDNumber = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            if(caseActivity == null) {
                county = "";
            }
            
            while(caseActivity.next()) {
                county = caseActivity.getString("county");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEmployerCountyByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return county;
    }
    
    public static String getEmployerNameByID(String id) {
        String name = "";
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select employerName from Employers"
                    + " WHERE employerIDNumber = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            if(caseActivity == null) {
                name = "";
            }
            
            while(caseActivity.next()) {
                name = caseActivity.getString("employerName");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEmployerNameByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return name;
    }
    
    public static void updateEmployer(int databaseID, Employer item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Employers set "
                    + "employerIDNumber = ?, "      //01
                    + "employerName = ?, "          //02
                    + "county = ?, "                //03
                    + "jurisdiction = ?, "          //04
                    + "employerType = ?, "          //05
                    + "employerTypeCode = ? "       //06
                    + "WHERE id = ?"; //07

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.employerIDNumber.equals("") ? null : item.employerIDNumber.trim());
            preparedStatement.setString(2, item.employerName.equals("") ? null : item.employerName.trim());
            preparedStatement.setString(3, item.county.equals("") ? null : item.county.trim());
            preparedStatement.setString(4, item.jurisdiction.equals("") ? null : item.jurisdiction.trim());
            preparedStatement.setInt   (5, item.employerType);
            preparedStatement.setString(6, item.employerTypeCode.equals("") ? null : item.employerTypeCode.trim());
            preparedStatement.setInt   (7, databaseID);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createEmployer(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void createEmployer(Employer item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Employers ("
                    + "Active, "
                    + "employerIDNumber, "
                    + "employerName, "
                    + "county, "
                    + "jurisdiction, "
                    + "employerType, "
                    + "employerTypeCode"
                    + ") VALUES ("
                    + "1, "
                    + "?, " // 1 - employer ID Number
                    + "?, " // 2 - Employer Name
                    + "?, " // 3 - County
                    + "?, " // 4 - Jurisdiction
                    + "?, " // 5 - Employer Type
                    + "?)"; // 6 - Employer Type Code

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.employerIDNumber.equals("") ? null : item.employerIDNumber.trim());
            preparedStatement.setString(2, item.employerName.equals("") ? null : item.employerName.trim());
            preparedStatement.setString(3, item.county.equals("") ? null : item.county.trim());
            preparedStatement.setString(4, item.jurisdiction.equals("") ? null : item.jurisdiction.trim());
            preparedStatement.setInt(5, item.employerType);
            preparedStatement.setString(6, item.employerTypeCode.equals("") ? null : item.employerTypeCode.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createEmployer(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
}
