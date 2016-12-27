package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class ULPCaseSearchData {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String chargingParty;
    public String chargedParty;
    public String employerNumber;
    public String unionNumber;
    
    public static List loadULPCaseList() {
        List<ULPCaseSearchData> ulpCaseList = new ArrayList<>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select * from ULPCaseSearch ORDER BY id DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                ULPCaseSearchData ulpCase = new ULPCaseSearchData();
                ulpCase.id = caseActivity.getInt("id");
                ulpCase.caseYear = caseActivity.getString("caseYear");
                ulpCase.caseType = caseActivity.getString("caseType");
                ulpCase.caseMonth = caseActivity.getString("caseMonth");
                ulpCase.caseNumber = caseActivity.getString("caseNumber");
                ulpCase.chargingParty = caseActivity.getString("chargingParty") == null ? "" : caseActivity.getString("chargingParty");
                ulpCase.chargedParty = caseActivity.getString("chargedParty") == null ? "" : caseActivity.getString("chargedParty");
                ulpCase.employerNumber = caseActivity.getString("employerNumber") == null ? "" : caseActivity.getString("employerNumber");
                ulpCase.unionNumber = caseActivity.getString("unionNumber") == null ? "" : caseActivity.getString("unionNumber");
                ulpCaseList.add(ulpCase);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.toString());
                loadULPCaseList();
            } else {
                SlackNotification.sendNotification(ex.toString());
            }
        }
        return ulpCaseList;
    }
    
    public static void createNewCaseEntry(String year, String type, String month, String number) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "INSERT INTO ULPCaseSearch VALUES (?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, month);
            preparedStatement.setString(4, number);
            preparedStatement.setString(5, null);
            preparedStatement.setString(6, null);
            preparedStatement.setString(7, null);
            preparedStatement.setString(8, null);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.toString());
                createNewCaseEntry(year, type, month, number);
            } else {
                SlackNotification.sendNotification(ex.toString());
            }
        }
    }
    
    public static void updateCaseEntryFromParties(String charged, String charging) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE ULPCaseSearch SET"
                    + " chargingParty = ?,"
                    + " chargedParty = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, charging);
            preparedStatement.setString(2, charged);
            preparedStatement.setString(3, Global.caseYear);
            preparedStatement.setString(4, Global.caseType);
            preparedStatement.setString(5, Global.caseMonth);
            preparedStatement.setString(6, Global.caseNumber);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.toString());
                updateCaseEntryFromParties(charged, charging);
            } else {
                SlackNotification.sendNotification(ex.toString());
            }
        }
    }
    
    public static void updateCaseEntryFromStatus(String employerNumber, String unionNumber) {
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE ULPCaseSearch SET"
                    + " employerNumber = ?,"
                    + " unionNumber = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, employerNumber);
            preparedStatement.setString(2, unionNumber);
            preparedStatement.setString(3, Global.caseYear);
            preparedStatement.setString(4, Global.caseType);
            preparedStatement.setString(5, Global.caseMonth);
            preparedStatement.setString(6, Global.caseNumber);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex.toString());
                updateCaseEntryFromStatus(employerNumber, unionNumber);
            } else {
                SlackNotification.sendNotification(ex.toString());
            }
        }
    }
}
