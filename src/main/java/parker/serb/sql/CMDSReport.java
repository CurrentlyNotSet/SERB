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

/**
 *
 * @author User
 */
public class CMDSReport {
    
    public int id;
    public boolean active;
    public String section;
    public String description;
    public String fileName;
    public String parameters;
    
    public static List<CMDSReport> loadAllReports(String[] param) {
        List<CMDSReport> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSReport";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(section, fileName, description) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY section, description";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CMDSReport item = new CMDSReport();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                item.fileName = rs.getString("fileName") == null ? "" : rs.getString("fileName");
                item.parameters = rs.getString("parameters") == null ? "" : rs.getString("parameters");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static List<CMDSReport> loadActiveReportsBySection(String section) {
        List<CMDSReport> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSReport WHERE active = 1 AND section = ?";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setString(1, section);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CMDSReport item = new CMDSReport();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                item.fileName = rs.getString("fileName") == null ? "" : rs.getString("fileName");
                item.parameters = rs.getString("parameters") == null ? "" : rs.getString("parameters");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static CMDSReport getReportByID(int id) {
        CMDSReport item = new CMDSReport();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSReport WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                item.fileName = rs.getString("fileName") == null ? "" : rs.getString("fileName");
                item.parameters = rs.getString("parameters") == null ? "" : rs.getString("parameters");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }
    
    public static void createReport(CMDSReport item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CMDSReport ("
                    + "active, "
                    + "section, "
                    + "description, "
                    + "fileName, "
                    + "parameters"
                    + ") VALUES ("
                    + "1, " // active
                    + "?, " // section
                    + "?, " // description
                    + "?, " // filename
                    + "? "  // parameters
                    + ")";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.section.equals("") ? null : item.section.trim());
            preparedStatement.setString(2, item.description.equals("") ? null : item.description.trim());
            preparedStatement.setString(3, item.fileName.equals("") ? null : item.fileName.trim());
            preparedStatement.setString(4, item.parameters.equals("") ? null : item.parameters.trim());
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateReport(CMDSReport item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE CMDSReport SET "
                    + "active = ?, "
                    + "section = ?, "
                    + "description = ?, "
                    + "fileName = ?, "
                    + "parameters = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString (2, item.section.equals("") ? null : item.section.trim());
            preparedStatement.setString (3, item.description.equals("") ? null : item.description.trim());
            preparedStatement.setString (4, item.fileName.equals("") ? null : item.fileName.trim());
            preparedStatement.setString (5, item.parameters.equals("") ? null : item.parameters.trim());
            preparedStatement.setInt    (6, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
