package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class HearingCaseSearchData {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String hearingStatus;
    public String hearingParties;
    public String hearingPCDate;
    public String hearingALJ;
    public String hearingBoardActionDate;
    
    public static List loadCaseSearchData() {
        List<HearingCaseSearchData> ulpCaseList = new ArrayList<>();
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from HearingCaseSearch ORDER BY hearingPCDate DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                HearingCaseSearchData repCase = new HearingCaseSearchData();
                repCase.id = caseActivity.getInt("id");
                repCase.caseYear = caseActivity.getString("caseYear");
                repCase.caseType = caseActivity.getString("caseType");
                repCase.caseMonth = caseActivity.getString("caseMonth");
                repCase.caseNumber = caseActivity.getString("caseNumber");
                repCase.hearingStatus = caseActivity.getString("hearingStatus") == null ? "" : caseActivity.getString("hearingStatus");
                repCase.hearingParties = caseActivity.getString("hearingParties") == null ? "" : caseActivity.getString("hearingParties");
                repCase.hearingPCDate = caseActivity.getTimestamp("hearingPCDate") == null ? "" : Global.mmddyyyy.format(new Date(caseActivity.getTimestamp("hearingPCDate").getTime()));
                repCase.hearingALJ = caseActivity.getString("hearingALJ") == null ? "" : caseActivity.getString("hearingALJ");
                repCase.hearingBoardActionDate = caseActivity.getTimestamp("hearingBoardActionDate") == null ? "" : Global.mmddyyyy.format(new Date(caseActivity.getTimestamp("hearingBoardActionDate").getTime()));
                ulpCaseList.add(repCase);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadCaseSearchData();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return ulpCaseList;
    }
    
    public static void createNewCaseEntry(String year, String type, String month, String number) {
        Statement stmt = null;    
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "INSERT INTO HearingCaseSearch (caseYear, caseType, caseMonth, caseNumber, hearingStatus) VALUES (?,?,?,?,'Open')";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, month);
            preparedStatement.setString(4, number);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createNewCaseEntry(year, type, month, number);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseEntryFromParties(String caseNumber) {        
        String parties = "";
        
        Statement stmt = null;
        
        try {
            List<CaseParty> caseParties = CaseParty.loadPartiesByCase(
                    caseNumber.split("-")[0], 
                    caseNumber.split("-")[1], 
                    caseNumber.split("-")[2], 
                    caseNumber.split("-")[3]
            );
        
            for(CaseParty caseParty: caseParties) {
                if(parties.equals("")) {
                    if(caseParty.firstName.equals("") 
                        && caseParty.lastName.equals("")) {
                        parties += caseParty.companyName;
                    } else {
                        parties += (caseParty.prefix.equals("") ? "" : (caseParty.prefix + " "))
                            + (caseParty.firstName.equals("") ? "" : (caseParty.firstName + " "))
                            + (caseParty.middleInitial.equals("") ? "" : (caseParty.middleInitial + ". "))
                            + (caseParty.lastName.equals("") ? "" : (caseParty.lastName))
                            + (caseParty.suffix.equals("") ? "" : (" " + caseParty.suffix))
                            + (caseParty.nameTitle.equals("") ? "" : (", " + caseParty.nameTitle));
                    }
                } else {
                    parties += ", ";
                    if(caseParty.firstName.equals("") 
                        && caseParty.lastName.equals("")) {
                        parties += caseParty.companyName;
                    } else {
                        parties += (caseParty.prefix.equals("") ? "" : (caseParty.prefix + " "))
                            + (caseParty.firstName.equals("") ? "" : (caseParty.firstName + " "))
                            + (caseParty.middleInitial.equals("") ? "" : (caseParty.middleInitial + ". "))
                            + (caseParty.lastName.equals("") ? "" : (caseParty.lastName))
                            + (caseParty.suffix.equals("") ? "" : (" " + caseParty.suffix))
                            + (caseParty.nameTitle.equals("") ? "" : (", " + caseParty.nameTitle));
                    }
                }
            }

            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE HearingCaseSearch SET"
                    + " hearingParties = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, parties);
            preparedStatement.setString(2, caseNumber.split("-")[0]);
            preparedStatement.setString(3, caseNumber.split("-")[1]);
            preparedStatement.setString(4, caseNumber.split("-")[2]);
            preparedStatement.setString(5, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseEntryFromParties(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseEntryFromCaseInformation(
            Timestamp boardActionPCDate,
            int aljID,
            Timestamp boardActionDate,
            String status
    ) {
        Statement stmt = null;   
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE HearingCaseSearch SET"
                    + " hearingPCDate = ?,"
                    + " hearingStatus = ?,"
                    + " hearingALJ = ?,"
                    + " hearingBoardActionDate = ?"
                    + " WHERE caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, boardActionPCDate);
            preparedStatement.setString(2, status);
            preparedStatement.setString(3, User.getNameByID(aljID));
            preparedStatement.setTimestamp(4, boardActionDate);
            preparedStatement.setString(5, Global.caseYear);
            preparedStatement.setString(6, Global.caseType);
            preparedStatement.setString(7, Global.caseMonth);
            preparedStatement.setString(8, Global.caseNumber);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseEntryFromCaseInformation(boardActionPCDate, aljID, boardActionDate, status);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
