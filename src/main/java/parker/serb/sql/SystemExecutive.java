/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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

/**
 *
 * @author User
 */
public class SystemExecutive {
    
    public int id;
    public boolean active;
    public String department;
    public String position;
    public String firstName;
    public String middleName;
    public String lastName;
    public String phoneNumber;
    public String email;
    
    public static List loadExecs(String dept) {
        List execsList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SystemExecutive where active = 1 AND department = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, dept);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                SystemExecutive exec = new SystemExecutive();
                exec.id = rs.getInt("id");
                exec.department = rs.getString("department") == null ? "" : rs.getString("department");
                exec.position = rs.getString("position") == null ? "" : rs.getString("position");
                exec.phoneNumber = rs.getString("phoneNumber") == null ? "" : rs.getString("phoneNumber");
                exec.email = rs.getString("email") == null ? "" : rs.getString("email");
                exec.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                exec.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                exec.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                execsList.add(exec);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadExecs(dept);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return execsList;
    }
    
    public static List loadExecsAllByDeptartment(String dept, String[] param) {
        List execsList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SystemExecutive where department = ?";

            if (param.length > 0) {
                for (String term : param) {
                    sql += " AND CONCAT(position, firstName, middleName, lastName, "
                            + "phoneNumber, email) LIKE ?";
                }
            }
            sql += " ORDER BY lastName";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            ps.setString(1, dept);
            
            for (int i = 0; i < param.length; i++) {
                
                ps.setString((i + 2), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();
            
            while(rs.next()) {
                SystemExecutive exec = new SystemExecutive();
                exec.id = rs.getInt("id");
                exec.active = rs.getBoolean("active");
                exec.department = rs.getString("department") == null ? "" : rs.getString("department");
                exec.position = rs.getString("position") == null ? "" : rs.getString("position");
                exec.phoneNumber = rs.getString("phoneNumber") == null ? "" : rs.getString("phoneNumber");
                exec.email = rs.getString("email") == null ? "" : rs.getString("email");
                exec.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                exec.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                exec.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                execsList.add(exec);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadExecsAllByDeptartment(dept, param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return execsList;
    }
    
    
    
    public static SystemExecutive getSystemExecutiveByID(int id) {
        SystemExecutive item = new SystemExecutive();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SystemExecutive WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.department = rs.getString("department") == null ? "" : rs.getString("department");
                item.position = rs.getString("position") == null ? "" : rs.getString("position");
                item.phoneNumber = rs.getString("phoneNumber") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("phoneNumber"));
                item.email = rs.getString("email") == null ? "" : rs.getString("email");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.middleName = rs.getString("middleName") == null ? "" : rs.getString("middleName");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getSystemExecutiveByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static void createSystemExecutive(SystemExecutive item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO SystemExecutive ("
                    + "active, "        //01
                    + "department, "    //02
                    + "position, "      //03
                    + "phoneNumber, "   //04
                    + "email, "         //05
                    + "firstName, "     //06
                    + "middleName, "    //07
                    + "lastName "       //08
                    + ") VALUES (";
                    for(int i=0; i<7; i++){
                        sql += "?, ";   //01-07
                    }
                     sql += "?)";   //08

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, item.department.equals("") ? null : item.department);
            preparedStatement.setString(3, item.position.equals("") ? null : item.position);
            preparedStatement.setString(4, item.phoneNumber.equals("") ? null : NumberFormatService.convertPhoneNumberToString(item.phoneNumber));
            preparedStatement.setString(5, item.email.equals("") ? null : item.email);
            preparedStatement.setString(6, item.firstName.equals("") ? null : item.firstName);
            preparedStatement.setString(7, item.middleName.equals("") ? null : item.middleName);
            preparedStatement.setString(8, item.lastName.equals("") ? null : item.lastName);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createSystemExecutive(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateSystemExecutive(SystemExecutive item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE SystemExecutive SET "
                    + "active = ?, "     //01
                    + "department = ?, " //02
                    + "position = ?, "   //03
                    + "phoneNumber = ?, "//04
                    + "email = ?, "      //05
                    + "firstName = ?, "  //06
                    + "middleName = ?, " //07
                    + "lastName = ? "    //08
                    + "where id = ?";    //09

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.department.equals("") ? null : item.department);
            preparedStatement.setString(3, item.position.equals("") ? null : item.position);
            preparedStatement.setString(4, item.phoneNumber.equals("") ? null : NumberFormatService.convertPhoneNumberToString(item.phoneNumber));
            preparedStatement.setString(5, item.email.equals("") ? null : item.email);
            preparedStatement.setString(6, item.firstName.equals("") ? null : item.firstName);
            preparedStatement.setString(7, item.middleName.equals("") ? null : item.middleName);
            preparedStatement.setString(8, item.lastName.equals("") ? null : item.lastName);
            preparedStatement.setInt   (9, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateSystemExecutive(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
