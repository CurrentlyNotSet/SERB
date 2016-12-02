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
public class Director {
    
    public int ID;
    public boolean Active;
    public String Agency;
    public String Title;
    public String Name;
    
    
    public static List<Director> loadAllDirectors(String[] param) {
        List<Director> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Director";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(Agency, Title, Name) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY Agency";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                Director item = new Director();
                item.ID = rs.getInt("id");
                item.Active = rs.getBoolean("active");
                item.Agency = rs.getString("Agency") == null ? "" : rs.getString("Agency");
                item.Title = rs.getString("Title") == null ? "" : rs.getString("Title");
                item.Name = rs.getString("Name") == null ? "" : rs.getString("Name");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static Director getDirectorByID(int id) {
        Director item = new Director();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Director WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.ID = rs.getInt("id");
                item.Active = rs.getBoolean("active");
                item.Agency = rs.getString("Agency") == null ? "" : rs.getString("Agency");
                item.Title = rs.getString("Title") == null ? "" : rs.getString("Title");
                item.Name = rs.getString("Name") == null ? "" : rs.getString("Name");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public static void createDirector(Director item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Director "
                    + "(active, Agency, Title, Name)"
                    + " VALUES "
                    + "(1, ?, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.Agency.equals("") ? null : item.Agency.trim());
            preparedStatement.setString(2, item.Title.equals("") ? null : item.Title.trim());
            preparedStatement.setString(3, item.Name.equals("") ? null : item.Name.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateDirector(Director item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE Director SET "
                    + "active = ?, "
                    + "Agency = ?, "
                    + "Title = ?, "
                    + "Name = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.Active);
            preparedStatement.setString(2, item.Agency.equals("") ? null : item.Agency.trim());
            preparedStatement.setString(3, item.Title.equals("") ? null : item.Title.trim());
            preparedStatement.setString(4, item.Name.equals("") ? null : item.Name.trim());
            preparedStatement.setInt(5, item.ID);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    
}
