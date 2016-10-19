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
public class HearingRoom {
    
    public int id;
    public boolean active;
    public String roomAbbreviation;
    public String roomName;
    public String roomEmail;
    
    public static List<HearingRoom> loadAllHearingRooms(String[] param) {
        List<HearingRoom> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM hearingroom";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(roomAbbreviation, roomName, roomEmail) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY roomAbbreviation";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HearingRoom item = new HearingRoom();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.roomAbbreviation = rs.getString("roomAbbreviation") == null ? "" : rs.getString("roomAbbreviation");
                item.roomName = rs.getString("roomName") == null ? "" : rs.getString("roomName");
                item.roomEmail = rs.getString("roomEmail") == null ? "" : rs.getString("roomEmail");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
    public static HearingRoom getHearingRoomByID(int id) {
        HearingRoom item = new HearingRoom();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingRoom WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.roomAbbreviation = rs.getString("roomAbbreviation") == null ? "" : rs.getString("roomAbbreviation").trim();
                item.roomName = rs.getString("roomName") == null ? "" : rs.getString("roomName").trim();
                item.roomEmail = rs.getString("roomEmail") == null ? "" : rs.getString("roomEmail").trim();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }

    public static void createHearingRoom(HearingRoom item) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO HearingRoom "
                    + "(active, roomAbbreviation, roomName, roomEmail)"
                    + " VALUES "
                    + "(1, ?, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, item.roomAbbreviation.equals("") ? null : item.roomAbbreviation.trim());
            preparedStatement.setString(2, item.roomName.equals("") ? null : item.roomName.trim());
            preparedStatement.setString(3, item.roomEmail.equals("") ? null : item.roomEmail.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateHearingRoom(HearingRoom item) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE HearingRoom SET "
                    + "active = ?, "
                    + "roomAbbreviation = ?, "
                    + "roomName = ?, "
                    + "roomEmail = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString(2, item.roomAbbreviation.equals("") ? null : item.roomAbbreviation.trim());
            preparedStatement.setString(3, item.roomName.equals("") ? null : item.roomName.trim());
            preparedStatement.setString(4, item.roomEmail.equals("") ? null : item.roomEmail.trim());
            preparedStatement.setInt(5, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static List<HearingRoom> loadActiveHearingRooms() {
        List<HearingRoom> list = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();
            String sql = "SELECT * FROM hearingroom WHERE active = 1 ORDER BY roomAbbreviation";
            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                HearingRoom item = new HearingRoom();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.roomAbbreviation = rs.getString("roomAbbreviation") == null ? "" : rs.getString("roomAbbreviation");
                item.roomName = rs.getString("roomName") == null ? "" : rs.getString("roomName");
                item.roomEmail = rs.getString("roomEmail") == null ? "" : rs.getString("roomEmail");
                list.add(item);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);

        }
        return list;
    }
    
}
