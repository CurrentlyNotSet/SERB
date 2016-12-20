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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author User
 */
public class HearingsMediation {
    
    public int id;
    public boolean active;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String pcPreD;
    public int mediatorID;
    public Timestamp dateAssigned;
    public Timestamp mediationDate;
    public String outcome;
    
    
//    public static List<HearingsMediation> loadAllHearingRooms(String[] param) {
//        List<HearingsMediation> list = new ArrayList<>();
//
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "SELECT * FROM hearingroom";
//            if (param.length > 0) {
//                sql += " WHERE";
//                for (int i = 0; i < param.length; i++) {
//                    if (i > 0) {
//                        sql += " AND";
//                    }
//                    sql += " CONCAT(roomAbbreviation, roomName, roomEmail) "
//                            + "LIKE ?";
//                }
//            }
//            sql += " ORDER BY roomAbbreviation";
//
//            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
//
//            for (int i = 0; i < param.length; i++) {
//                ps.setString((i + 1), "%" + param[i].trim() + "%");
//            }
//            
//            ResultSet rs = ps.executeQuery();
//
//            while (rs.next()) {
//                HearingsMediation item = new HearingsMediation();
//                item.id = rs.getInt("id");
//                item.active = rs.getBoolean("active");
//                item.roomAbbreviation = rs.getString("roomAbbreviation") == null ? "" : rs.getString("roomAbbreviation");
//                item.roomName = rs.getString("roomName") == null ? "" : rs.getString("roomName");
//                item.roomEmail = rs.getString("roomEmail") == null ? "" : rs.getString("roomEmail");
//                list.add(item);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//
//        }
//        return list;
//    }
    
    public static List<HearingsMediation> loadAllMediationsByCaseNumber() {
        List<HearingsMediation> items = new ArrayList<>();

        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM HearingsMediation"
                    + " WHERE caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                HearingsMediation med = new HearingsMediation();
                med.id = rs.getInt("id");
                med.active = rs.getBoolean("active");
                med.caseYear = rs.getString("caseYear");
                med.caseType = rs.getString("caseType");
                med.caseMonth = rs.getString("caseMonth");
                med.caseNumber = rs.getString("caseNumber");
                med.pcPreD = rs.getString("pcPreD");
                med.mediatorID = rs.getInt("mediator");
                med.dateAssigned = rs.getTimestamp("dateAssigned");
                med.mediationDate = rs.getTimestamp("mediationDate");
                med.outcome = rs.getString("outcome");
                
                items.add(med);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return items;
    }

    public static void addMediation() {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO HearingRoom "
                    + "(active, roomAbbreviation, roomName, roomEmail)"
                    + " VALUES "
                    + "(1, ?, ?, ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, item.roomAbbreviation.equals("") ? null : item.roomAbbreviation.trim());
//            preparedStatement.setString(2, item.roomName.equals("") ? null : item.roomName.trim());
//            preparedStatement.setString(3, item.roomEmail.equals("") ? null : item.roomEmail.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void removeMediationByID(String id, String mediationDate) {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Delete from HearingsMediation where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);
            
            preparedStatement.executeUpdate();
            
            Activity.addActivty("Removed Meidation occuring " + mediationDate, null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void updateMediation(
            String id,
            String pcPreD,
            int mediatorID,
            String dateAsigned,
            String mediationDate,
            String outcome) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE HearingsMediation SET "
                    + "pcPreD = ?, "
                    + "mediator = ?, "
                    + "dateAssigned = ?, "
                    + "mediationDate = ?, "
                    + "outcome = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, pcPreD.equals("") ? null : pcPreD);
            preparedStatement.setInt(2, mediatorID);
            preparedStatement.setTimestamp(3, dateAsigned.equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(dateAsigned)));
            preparedStatement.setTimestamp(4, mediationDate.equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(mediationDate)));
            preparedStatement.setString(5, outcome.equals("") ? null : outcome);
            preparedStatement.setString(6, id);
            
            preparedStatement.executeUpdate();
            
            Activity.addActivty("Updated Information for Meidation on " + mediationDate, null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
