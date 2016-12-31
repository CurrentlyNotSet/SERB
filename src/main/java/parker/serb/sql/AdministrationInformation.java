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
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author User
 */
public class AdministrationInformation {
    public int id;
    public boolean active;
    public String department;
    public String governorName;
    public String LtGovernorName;
    public String Address1;
    public String Address2;
    public String City;
    public String State;
    public String Zip;
    public String Url;
    public String Phone;
    public String Fax;
    public String Footer;
    
    public static AdministrationInformation loadAdminInfo(String dept) {
        AdministrationInformation exec = null;
          
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM AdministrationInformation where active = 1 AND department = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, dept);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                exec = new AdministrationInformation();
                exec.id = rs.getInt("id");
                exec.department = rs.getString("department") == null ? "" : rs.getString("department");
                exec.governorName = rs.getString("governorName") == null ? "" : rs.getString("governorName");
                exec.LtGovernorName = rs.getString("LtGovernorName") == null ? "" : rs.getString("LtGovernorName");
                exec.Address1 = rs.getString("Address1") == null ? "" : rs.getString("Address1");
                exec.Address2 = rs.getString("Address2") == null ? "" : rs.getString("Address2");
                exec.City = rs.getString("City") == null ? "" : rs.getString("City");
                exec.State = rs.getString("State") == null ? "" : rs.getString("State");
                exec.Zip = rs.getString("Zip") == null ? "" : rs.getString("Zip");
                exec.Url = rs.getString("Url") == null ? "" : rs.getString("Url");
                exec.Phone = rs.getString("Phone") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("phone"));
                exec.Fax = rs.getString("Fax") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("Fax"));
                exec.Footer = rs.getString("Footer") == null ? "" : rs.getString("Footer");
                return exec;
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAdminInfo(dept);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return exec;
    }
    
    public static void updateAdministrationInformation(AdministrationInformation item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE AdministrationInformation SET "
                    + "governorName = ?, "  //01
                    + "LtGovernorName = ?, "//02
                    + "address1 = ?, "      //03
                    + "address2 = ?, "      //04
                    + "city = ?, "          //05
                    + "state = ?, "         //06
                    + "zip = ?, "           //07
                    + "url = ?, "           //08
                    + "phone = ?, "         //09
                    + "Fax = ?, "           //10
                    + "Footer = ? "         //11
                    + "where id = ?";       //12

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setString(1, item.governorName.equals("") ? null : item.governorName);
            ps.setString(2, item.LtGovernorName.equals("") ? null : item.LtGovernorName);
            ps.setString(3, item.Address1.equals("") ? null : item.Address1);
            ps.setString(4, item.Address2.equals("") ? null : item.Address2);
            ps.setString(5, item.City.equals("") ? null : item.City);
            ps.setString(6, item.State.equals("") ? null : item.State);
            ps.setString(7, item.Zip.equals("") ? null : item.Zip);
            ps.setString(8, item.Url.equals("") ? null : item.Url);
            ps.setString(9, item.Phone.equals("") ? null : NumberFormatService.convertPhoneNumberToString(item.Phone));
            ps.setString(10, item.Fax.equals("") ? null : NumberFormatService.convertPhoneNumberToString(item.Fax));
            ps.setString(11, item.Footer.equals("") ? null : item.Footer);
            ps.setInt   (12, item.id);

            ps.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateAdministrationInformation(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
