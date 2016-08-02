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
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.util.NumberFormatService;

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
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return exec;
    }
}