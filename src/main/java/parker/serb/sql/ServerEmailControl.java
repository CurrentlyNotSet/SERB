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
import java.sql.Timestamp;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author User
 */
public class ServerEmailControl {
    public int id;
    public Timestamp incomingEmail;
    public Timestamp calInvites;
    public Timestamp notificationEmail;
    public Timestamp outgoingEmail;
    public Timestamp stampScans;
    
    public static ServerEmailControl getServerEmailControl() {
        ServerEmailControl item = new ServerEmailControl();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM ServerEmailControl WHERE id = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.incomingEmail = rs.getTimestamp("incomingEmail") == null ? null : rs.getTimestamp("incomingEmail");
                item.calInvites = rs.getTimestamp("calInvites") == null ? null : rs.getTimestamp("calInvites");
                item.notificationEmail = rs.getTimestamp("notificationEmail") == null ? null : rs.getTimestamp("notificationEmail");
                item.outgoingEmail = rs.getTimestamp("outgoingEmail") == null ? null : rs.getTimestamp("outgoingEmail");
                item.stampScans = rs.getTimestamp("stampScans") == null ? null : rs.getTimestamp("stampScans");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return item;
    }
}
