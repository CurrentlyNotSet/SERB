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
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class ULPCase {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String employerIDNumber;
    public String deptInState;
    public String barginingUnitNo;
    public String EONumber;
    public String allegation;
    public String currentStatus;
    public boolean priority;
    public Timestamp assignedDate;
    public Timestamp turnInDate;
    public Timestamp reportDueDate;
    public Timestamp dismissalDate;
    public Timestamp deferredDate;
    public Timestamp fileDate;
    public boolean probableCause;
    public Timestamp appealDateReceived;
    public Timestamp appealDateSent;
    public String courtName;
    public String courtCaseNumber;
    public String SERBCaseNumber;
    public String finalDispositionStatus;
    public int investigatorID;
    public int mediatorAssignedID;
    public int aljID;
    public String statement;
    public String recommendation;
    public String investigationReveals;
    public String note;
    
    public static List loadULPCaseNumbers() {
        List caseNumberList = new ArrayList<>();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP 250"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber"
                    + " from ULPCase"
                    + " Order By fileDate DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                String createdCaseNumber = caseNumberRS.getString("caseYear")
                        + "-" +
                        caseNumberRS.getString("caseType")
                        + "-" +
                        caseNumberRS.getString("caseMonth")
                        + "-" +
                        caseNumberRS.getString("caseNumber");
                        
                caseNumberList.add(createdCaseNumber);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadULPCaseNumbers();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        } 
        return caseNumberList;
    }
    
    public static List loadULPCases() {
        List caseNumberList = new ArrayList<>();
        
        Statement stmt = null;    
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "Select TOP 50 caseYear, caseType, caseMonth, caseNumber, employerIDNumber, barginingUnitNo from ULPCase Order By caseYear DESC, caseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                ULPCase ulpCase = new ULPCase();
                ulpCase.caseYear = caseNumberRS.getString("caseYear");
                ulpCase.caseType = caseNumberRS.getString("caseType");
                ulpCase.caseMonth = caseNumberRS.getString("caseMonth");
                ulpCase.caseNumber = caseNumberRS.getString("caseNumber");
                ulpCase.employerIDNumber = caseNumberRS.getString("employerIDNumber") == null ? "" : caseNumberRS.getString("employerIDNumber");
                ulpCase.barginingUnitNo = caseNumberRS.getString("barginingUnitNo") == null ? "" : caseNumberRS.getString("barginingUnitNo");

                caseNumberList.add(ulpCase);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadULPCases();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseNumberList;
    }
    
    public static List<String> loadRelatedCases() {
        List<String> caseNumberList = new ArrayList<>();
        
        Statement stmt =null;  
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "Select caseYear, caseType, caseMonth, caseNumber from ULPCase  where fileDate between DateAdd(DD,-7,GETDATE()) and GETDATE() Order By caseYear DESC, caseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                caseNumberList.add(caseNumberRS.getString("caseYear") + "-"
                    + caseNumberRS.getString("caseType") + "-" 
                    + caseNumberRS.getString("caseMonth") + "-"
                    + caseNumberRS.getString("caseNumber"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadRelatedCases();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseNumberList;
    }
    
    public static String loadNote() {
        String note = null;
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select Note"
                    + " from ULPCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            caseNumberRS.next();
            
            note = caseNumberRS.getString("note");
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadNote();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return note;
    }

    public static void updateNote(String note) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update ULPCase"
                    + " set note = ?"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, note);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            preparedStatement.executeUpdate();
            
            Audit.addAuditEntry("Updated Note for " + NumberFormatService.generateFullCaseNumber());
            Activity.addActivty("Updated Note", null);
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateNote(note);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static String loadInvestigationReveals() {
        String investigationReveals = null;
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select investigationReveals"
                    + " from ULPCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            caseNumberRS.next();
            
            investigationReveals = caseNumberRS.getString("investigationReveals");
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadInvestigationReveals();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return investigationReveals;
    }

    public static void updateInvestigationReveals(String note) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update ULPCase"
                    + " set investigationReveals = ?"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, note);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            preparedStatement.executeUpdate();
            
            Audit.addAuditEntry("Updated Investigation Reveals for " + NumberFormatService.generateFullCaseNumber());
            Activity.addActivty("Updated Investigation Reveals", null);
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateInvestigationReveals(note);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static String loadRecommendation() {
        String recommendation = null;
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select recommendation"
                    + " from ULPCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            caseNumberRS.next();
            
            recommendation = caseNumberRS.getString("recommendation");
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadRecommendation();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return recommendation;
    }

    public static void updateRecommendation(String note, String orginal) {
        Statement stmt = null;
        try {
            if(!note.equals(orginal)) {
                stmt = Database.connectToDB().createStatement();

                String sql = "Update ULPCase"
                        + " set recommendation = ?"
                        + " where caseYear = ?"
                        + " and caseType = ?"
                        + " and caseMonth = ?"
                        + " and caseNumber = ?";

                PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setString(1, note);
                preparedStatement.setString(2, Global.caseYear);
                preparedStatement.setString(3, Global.caseType);
                preparedStatement.setString(4, Global.caseMonth);
                preparedStatement.setString(5, Global.caseNumber);

                preparedStatement.executeUpdate();

                Audit.addAuditEntry("Updated Recommendation for " + NumberFormatService.generateFullCaseNumber());
                Activity.addActivty("Updated Recommendation", null);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateRecommendation(note, orginal);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static String loadStatement() {
        String recommendation = null;
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select statement"
                    + " from ULPCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            caseNumberRS.next();
            
            recommendation = caseNumberRS.getString("statement");

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadStatement();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return recommendation;
    }
    
    public static ULPCase loadStatus() {
        ULPCase ulp = new ULPCase();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " employerIDNumber,"
                    + " deptInState,"
                    + " barginingUnitNo,"
                    + " EONumber,"
                    + " allegation,"
                    + " currentStatus,"
                    + " priority,"
                    + " assignedDate,"
                    + " turnInDate,"
                    + " reportDueDate,"
                    + " fileDate,"
                    + " probableCause,"
                    + " appealDateReceived,"
                    + " appealDateSent,"
                    + " courtName,"
                    + " courtCaseNumber,"
                    + " SERBCaseNumber,"
                    + " finalDispositionStatus,"
                    + " investigatorID,"
                    + " mediatorAssignedID,"
                    + " aljID,"
                    + " dismissalDate,"
                    + " deferredDate"
                    + " from ULPCase"
                    + " where caseYear = ? "
                    + " and caseType = ? "
                    + " and caseMonth = ? "
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            caseNumberRS.next();
            
            ulp.employerIDNumber = caseNumberRS.getString("employerIDNumber");
            ulp.deptInState = caseNumberRS.getString("deptInState");
            ulp.barginingUnitNo = caseNumberRS.getString("barginingUnitNo");
            ulp.EONumber = caseNumberRS.getString("EONumber");
            ulp.allegation = caseNumberRS.getString("allegation");
            ulp.currentStatus = caseNumberRS.getString("currentStatus");
            ulp.priority = caseNumberRS.getBoolean("priority");
            ulp.assignedDate = caseNumberRS.getTimestamp("assignedDate");
            ulp.reportDueDate = caseNumberRS.getTimestamp("reportDueDate");
            ulp.dismissalDate = caseNumberRS.getTimestamp("dismissalDate");
            ulp.deferredDate = caseNumberRS.getTimestamp("deferredDate");
            ulp.appealDateReceived = caseNumberRS.getTimestamp("appealDateReceived");
            ulp.appealDateSent = caseNumberRS.getTimestamp("appealDateSent");
            ulp.courtName = caseNumberRS.getString("courtName");
            ulp.courtCaseNumber = caseNumberRS.getString("courtCaseNumber");
            ulp.SERBCaseNumber = caseNumberRS.getString("serbCaseNumber");
            ulp.finalDispositionStatus = caseNumberRS.getString("finalDispositionStatus");
            ulp.investigatorID = caseNumberRS.getInt("investigatorID");
            ulp.mediatorAssignedID = caseNumberRS.getInt("mediatorAssignedID");
            ulp.aljID = caseNumberRS.getInt("aljID");
            ulp.fileDate = caseNumberRS.getTimestamp("fileDate");
            ulp.probableCause = caseNumberRS.getBoolean("probableCause");
            
            stmt.close();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadStatus();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return ulp;
    }

    public static void updateStatement(String note) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update ULPCase"
                    + " set statement = ?"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, note);
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            preparedStatement.executeUpdate();
            
            Audit.addAuditEntry("Updated Statement for " + NumberFormatService.generateFullCaseNumber());
            Activity.addActivty("Updated Statement", null);
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateStatement(note);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static boolean validateCaseNumber(String fullCaseNumber) {
        String[] caseNumberParts = fullCaseNumber.split("-");
        boolean valid = false;
        
        if(caseNumberParts.length != 4) {
            return false;
        }
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select Count(*) As results"
                    + " from ULPCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumberParts[0]);
            preparedStatement.setString(2, caseNumberParts[1]);
            preparedStatement.setString(3, caseNumberParts[2]);
            preparedStatement.setString(4, caseNumberParts[3]);
            
            ResultSet validRS = preparedStatement.executeQuery();
            
            validRS.next();
            
            valid = validRS.getInt("results") > 0;
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                validateCaseNumber(fullCaseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return valid;
    }

    public static void createCase(String caseYear, 
            String caseType, 
            String caseMonth, 
            String caseNumber) 
    {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert into ULPCase"
                    + " (caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " fileDate)"
                    + " Values ("
                    + " ?,"
                    + " ?,"
                    + " ?,"
                    + " ?,"
                    + " ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            preparedStatement.setString(2, caseType);
            preparedStatement.setString(3, caseMonth);
            preparedStatement.setString(4, caseNumber);
            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                String fullCaseNumber = caseYear
                        + "-"
                        + caseType
                        + "-"
                        + caseMonth
                        + "-"
                        + caseNumber;
                        
                CaseNumber.updateNextCaseNumber(caseYear, caseType, String.valueOf(Integer.valueOf(caseNumber) + 1));
                Audit.addAuditEntry("Created Case: " + fullCaseNumber);
                Activity.addNewCaseActivty(fullCaseNumber, "Case was Received and Started");
                Activity.addNewCaseActivty(fullCaseNumber, "Case was Filed");
                Global.root.getuLPHeaderPanel1().loadCases();
                Global.root.getuLPHeaderPanel1().getjComboBox2().setSelectedItem(fullCaseNumber); 
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createCase(caseYear, caseType, caseMonth, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static ULPCase loadHeaderInformation() {
        ULPCase ulp = null;
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select fileDate, currentStatus, investigatorID, aljID "
                    + "from ULPCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseHeader = preparedStatement.executeQuery();
            
            if(caseHeader.next()) {
                ulp = new ULPCase();
                ulp.fileDate = caseHeader.getTimestamp("fileDate");
                ulp.currentStatus = caseHeader.getString("currentStatus");
                ulp.investigatorID = caseHeader.getInt("investigatorID");
                ulp.aljID = caseHeader.getInt("aljID");
                
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadHeaderInformation();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return ulp;
    }
    
    public static void updateCaseStatusInformation(ULPCase newCaseInformation, ULPCase caseInformation) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement(); 

            String sql = "Update ULPCase"
                + " set employerIDNumber = ?,"
                + " deptInState = ?,"
                + " barginingUnitNo = ?,"
                + " EONumber = ?,"
                + " allegation = ?,"
                + " currentStatus = ?,"
                + " priority = ?,"
                + " assignedDate = ?,"
                + " reportDueDate = ?,"
                + " dismissalDate = ?,"
                + " deferredDate = ?,"
                + " appealDateReceived = ?,"
                + " appealDateSent = ?,"
                + " courtName = ?,"
                + " courtCaseNumber = ?,"
                + " SERBCaseNumber = ?,"
                + " finalDispositionStatus = ?,"
                + " investigatorID = ?,"
                + " mediatorAssignedID = ?,"
                + " aljID = ?,"
                + " fileDate = ?,"
                + " probableCause = ?"
                + " where caseYear = ?"
                + " and caseType = ?"
                + " and caseMonth = ?"
                + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseInformation.employerIDNumber);
            preparedStatement.setString(2, newCaseInformation.deptInState);
            preparedStatement.setString(3, newCaseInformation.barginingUnitNo);
            preparedStatement.setString(4, newCaseInformation.EONumber);
            preparedStatement.setString(5, newCaseInformation.allegation);
            preparedStatement.setString(6, newCaseInformation.currentStatus);
            preparedStatement.setBoolean(7, newCaseInformation.priority);
            preparedStatement.setTimestamp(8, newCaseInformation.assignedDate);
            preparedStatement.setTimestamp(9, newCaseInformation.reportDueDate);
            preparedStatement.setTimestamp(10, newCaseInformation.dismissalDate);
            preparedStatement.setTimestamp(11, newCaseInformation.deferredDate);
            preparedStatement.setTimestamp(12, newCaseInformation.appealDateReceived);
            preparedStatement.setTimestamp(13, newCaseInformation.appealDateSent);
            preparedStatement.setString(14, newCaseInformation.courtName);
            preparedStatement.setString(15, newCaseInformation.courtCaseNumber);
            preparedStatement.setString(16, newCaseInformation.SERBCaseNumber);
            preparedStatement.setString(17, newCaseInformation.finalDispositionStatus);
            preparedStatement.setInt(18, newCaseInformation.investigatorID);
            preparedStatement.setInt(19, newCaseInformation.mediatorAssignedID);
            preparedStatement.setInt(20, newCaseInformation.aljID);
            preparedStatement.setTimestamp(21, newCaseInformation.fileDate);
            preparedStatement.setBoolean(22, newCaseInformation.probableCause);
            preparedStatement.setString(23, Global.caseYear);
            preparedStatement.setString(24, Global.caseType);
            preparedStatement.setString(25, Global.caseMonth);
            preparedStatement.setString(26, Global.caseNumber);
            
            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
                EmployerCaseSearchData.updateFileDate(
                        newCaseInformation.fileDate);
                EmployerCaseSearchData.updateCaseStatus(
                        newCaseInformation.currentStatus);
                EmployerCaseSearchData.updateEmployer(
                        newCaseInformation.employerIDNumber);
            } 
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseStatusInformation(newCaseInformation, caseInformation);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    private static void detailedCaseInformationSaveInformation(ULPCase newCaseInformation, ULPCase oldCaseInformation) {
        //allegation
        if(newCaseInformation.allegation == null && oldCaseInformation.allegation != null) {
            Activity.addActivty("Removed " + oldCaseInformation.allegation + " from Allegation", null);
        } else if(newCaseInformation.allegation != null && oldCaseInformation.allegation == null) {
            Activity.addActivty("Set Allegation to " + newCaseInformation.allegation, null);
        } else if(newCaseInformation.allegation != null && oldCaseInformation.allegation != null) {
            if(!newCaseInformation.allegation.equals(oldCaseInformation.allegation)) 
                Activity.addActivty("Changed Allegation from " + oldCaseInformation.allegation + " to " + newCaseInformation.allegation, null);
        }
        
        //currentStatus
        if(newCaseInformation.currentStatus == null && oldCaseInformation.currentStatus != null) {
            Activity.addActivty("Removed " + oldCaseInformation.currentStatus + " from Current Status", null);
        } else if(newCaseInformation.currentStatus != null && oldCaseInformation.currentStatus == null) {
            Activity.addActivty("Set Current Status to " + newCaseInformation.currentStatus, null);
        } else if(newCaseInformation.currentStatus != null && oldCaseInformation.currentStatus != null) {
            if(!newCaseInformation.currentStatus.equals(oldCaseInformation.currentStatus)) 
                Activity.addActivty("Changed Current Status from " + oldCaseInformation.currentStatus + " to " + newCaseInformation.currentStatus, null);
        }
        
        //priority
        if(newCaseInformation.priority != oldCaseInformation.priority) {
            Activity.addActivty("Changed Priority from " + (oldCaseInformation.priority ? "Yes" : "No") + " to " + (newCaseInformation.priority ? "Yes" : "No"), null);
        }
        
        //assigned Date
        if(newCaseInformation.assignedDate == null && oldCaseInformation.assignedDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.assignedDate.getTime())) + " from Assigned Date", null);
        } else if(newCaseInformation.assignedDate != null && oldCaseInformation.assignedDate == null) {
            Activity.addActivty("Set Assigned Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.assignedDate.getTime())), null);
        } else if(newCaseInformation.assignedDate != null && oldCaseInformation.assignedDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.assignedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.assignedDate.getTime()))))
                Activity.addActivty("Changed Assigned Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.assignedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.assignedDate.getTime())), null);
        }
        
        //reportDueDate
        if(newCaseInformation.reportDueDate == null && oldCaseInformation.reportDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.reportDueDate.getTime())) + " from Report Due Date", null);
        } else if(newCaseInformation.reportDueDate != null && oldCaseInformation.reportDueDate == null) {
            Activity.addActivty("Set Report Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.reportDueDate.getTime())), null);
        } else if(newCaseInformation.reportDueDate != null && oldCaseInformation.reportDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.reportDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.reportDueDate.getTime()))))
                Activity.addActivty("Changed Report Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.reportDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.reportDueDate.getTime())), null);
        }
        
        //dismissalDate
        if(newCaseInformation.dismissalDate == null && oldCaseInformation.dismissalDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.dismissalDate.getTime())) + " from Dismissal Date", null);
        } else if(newCaseInformation.dismissalDate != null && oldCaseInformation.dismissalDate == null) {
            Activity.addActivty("Set Dismissal Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.dismissalDate.getTime())), null);
        } else if(newCaseInformation.dismissalDate != null && oldCaseInformation.dismissalDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.dismissalDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.dismissalDate.getTime()))))
                Activity.addActivty("Changed Dismissal Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.dismissalDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.dismissalDate.getTime())), null);
        }
        
        //deferredDate
        if(newCaseInformation.deferredDate == null && oldCaseInformation.deferredDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.deferredDate.getTime())) + " from Deffered Date", null);
        } else if(newCaseInformation.deferredDate != null && oldCaseInformation.deferredDate == null) {
            Activity.addActivty("Set Deferred Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.deferredDate.getTime())), null);
        } else if(newCaseInformation.deferredDate != null && oldCaseInformation.deferredDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.deferredDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.deferredDate.getTime()))))
                Activity.addActivty("Changed Deferred Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.deferredDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.deferredDate.getTime())), null);
        }
        
        //appealDateReceived
        if(newCaseInformation.appealDateReceived == null && oldCaseInformation.appealDateReceived != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateReceived.getTime())) + " from Appeal Received", null);
        } else if(newCaseInformation.appealDateReceived != null && oldCaseInformation.appealDateReceived == null) {
            Activity.addActivty("Set Appeal Received to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateReceived.getTime())), null);
        } else if(newCaseInformation.appealDateReceived != null && oldCaseInformation.appealDateReceived != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateReceived.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.appealDateReceived.getTime()))))
                Activity.addActivty("Changed Appeal Received from " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateReceived.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateReceived.getTime())), null);
        }
                
        //appealDateSent
        if(newCaseInformation.appealDateSent == null && oldCaseInformation.appealDateSent != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateSent.getTime())) + " from Appeal Sent", null);
        } else if(newCaseInformation.appealDateSent != null && oldCaseInformation.appealDateSent == null) {
            Activity.addActivty("Set Appeal Sent to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateSent.getTime())), null);
        } else if(newCaseInformation.appealDateSent != null && oldCaseInformation.appealDateSent != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateSent.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.appealDateSent.getTime()))))
                Activity.addActivty("Changed Appeal Sent from " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateSent.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateSent.getTime())), null);
        }   
        
        //cournt name
        if(newCaseInformation.courtName == null && oldCaseInformation.courtName != null) {
            Activity.addActivty("Removed " + oldCaseInformation.courtName + " from Court Name", null);
        } else if(newCaseInformation.courtName != null && oldCaseInformation.courtName == null) {
            Activity.addActivty("Set Court Name to " + newCaseInformation.courtName, null);
        } else if(newCaseInformation.courtName != null && oldCaseInformation.courtName != null) {
            if(!newCaseInformation.courtName.equals(oldCaseInformation.courtName)) 
                Activity.addActivty("Changed Court Name from " + oldCaseInformation.courtName + " to " + newCaseInformation.courtName, null);
        }
                
        //courtCaseNumber
        if(newCaseInformation.courtCaseNumber == null && oldCaseInformation.courtCaseNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.courtCaseNumber + " from Court Case Number", null);
        } else if(newCaseInformation.courtCaseNumber != null && oldCaseInformation.courtCaseNumber == null) {
            Activity.addActivty("Set Court Case Number to " + newCaseInformation.courtCaseNumber, null);
        } else if(newCaseInformation.courtCaseNumber != null && oldCaseInformation.courtCaseNumber != null) {
            if(!newCaseInformation.courtCaseNumber.equals(oldCaseInformation.courtCaseNumber)) 
                Activity.addActivty("Changed Court Case Number from " + oldCaseInformation.courtCaseNumber + " to " + newCaseInformation.courtCaseNumber, null);
        }
        
        //serbCaseNumber
        if(newCaseInformation.SERBCaseNumber == null && oldCaseInformation.SERBCaseNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.SERBCaseNumber + " from SERB Case Number", null);
        } else if(newCaseInformation.SERBCaseNumber != null && oldCaseInformation.SERBCaseNumber == null) {
            Activity.addActivty("Set SERB Case Number to " + newCaseInformation.SERBCaseNumber, null);
        } else if(newCaseInformation.SERBCaseNumber != null && oldCaseInformation.SERBCaseNumber != null) {
            if(!newCaseInformation.SERBCaseNumber.equals(oldCaseInformation.SERBCaseNumber)) 
                Activity.addActivty("Changed SERB Case Number from " + oldCaseInformation.SERBCaseNumber + " to " + newCaseInformation.SERBCaseNumber, null);
        }
        
        //finalDispositionStatus
        if(newCaseInformation.finalDispositionStatus == null && oldCaseInformation.finalDispositionStatus != null) {
            Activity.addActivty("Removed " + oldCaseInformation.finalDispositionStatus + " from Final Disposition Status", null);
        } else if(newCaseInformation.finalDispositionStatus != null && oldCaseInformation.finalDispositionStatus == null) {
            Activity.addActivty("Set Final Disposition Status to " + newCaseInformation.finalDispositionStatus, null);
        } else if(newCaseInformation.finalDispositionStatus != null && oldCaseInformation.finalDispositionStatus != null) {
            if(!newCaseInformation.finalDispositionStatus.equals(oldCaseInformation.finalDispositionStatus)) 
                Activity.addActivty("Changed Final Disposition Status from " + oldCaseInformation.finalDispositionStatus + " to " + newCaseInformation.finalDispositionStatus, null);
        }
        
        //investigatorID
        if(newCaseInformation.investigatorID == 0 && oldCaseInformation.investigatorID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.investigatorID) + " from Investigator", null);
        } else if(newCaseInformation.investigatorID != 0 && oldCaseInformation.investigatorID == 0) {
            Activity.addActivty("Set Investigator to " + User.getNameByID(newCaseInformation.investigatorID), null);
        } else if(newCaseInformation.investigatorID != 0 && oldCaseInformation.investigatorID != 0) {
            if(newCaseInformation.investigatorID != oldCaseInformation.investigatorID) 
                Activity.addActivty("Changed Investigator from " + User.getNameByID(oldCaseInformation.investigatorID) + " to " + User.getNameByID(newCaseInformation.investigatorID), null);
        }
        
        //mediatorAssignedID
        if(newCaseInformation.mediatorAssignedID == 0 && oldCaseInformation.mediatorAssignedID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.mediatorAssignedID) + " from Mediator", null);
        } else if(newCaseInformation.mediatorAssignedID != 0 && oldCaseInformation.mediatorAssignedID == 0) {
            Activity.addActivty("Set Mediator to " + User.getNameByID(newCaseInformation.mediatorAssignedID), null);
        } else if(newCaseInformation.mediatorAssignedID != 0 && oldCaseInformation.mediatorAssignedID != 0) {
            if(newCaseInformation.mediatorAssignedID != oldCaseInformation.mediatorAssignedID) 
                Activity.addActivty("Changed Mediator from " + User.getNameByID(oldCaseInformation.mediatorAssignedID) + " to " + User.getNameByID(newCaseInformation.mediatorAssignedID), null);
        }
        
        //aljID
        if(newCaseInformation.aljID == 0 && oldCaseInformation.aljID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.aljID) + " from ALJ", null);
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID == 0) {
            Activity.addActivty("Set ALJ to " + User.getNameByID(newCaseInformation.aljID), null);
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID != 0) {
            if(newCaseInformation.aljID != oldCaseInformation.aljID) 
                Activity.addActivty("Changed ALJ from " + User.getNameByID(oldCaseInformation.aljID) + " to " + User.getNameByID(newCaseInformation.aljID), null);
        }
        
        //fileDate
        if(newCaseInformation.fileDate == null && oldCaseInformation.fileDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " from File Date", null);
        } else if(newCaseInformation.appealDateSent != null && oldCaseInformation.fileDate == null) {
            Activity.addActivty("Set File Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
        } else if(newCaseInformation.fileDate != null && oldCaseInformation.fileDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime()))))
                Activity.addActivty("Changed File Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
        } 
        
        //probableCause
        if(newCaseInformation.probableCause != oldCaseInformation.probableCause) {
            Activity.addActivty("Changed Probable Cause from " + (oldCaseInformation.probableCause ? "Yes" : "No") + " to " + (newCaseInformation.probableCause ? "Yes" : "No"), "");
        }
        
        //employer Number
        if(newCaseInformation.employerIDNumber == null && oldCaseInformation.employerIDNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.employerIDNumber + " from Employer ID Number", null);
        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber == null) {
            Activity.addActivty("Set Employer ID Number to " + newCaseInformation.employerIDNumber, null);
        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber != null) {
            if(!newCaseInformation.employerIDNumber.equals(oldCaseInformation.employerIDNumber)) 
                Activity.addActivty("Changed Employer ID Number from " + oldCaseInformation.employerIDNumber + " to " + newCaseInformation.employerIDNumber, null);
        }
        
        //union number
        if(newCaseInformation.barginingUnitNo == null && oldCaseInformation.barginingUnitNo != null) {
            Activity.addActivty("Removed " + oldCaseInformation.barginingUnitNo + " from Union Number", null);
        } else if(newCaseInformation.barginingUnitNo != null && oldCaseInformation.barginingUnitNo == null) {
            Activity.addActivty("Set Union Number to " + newCaseInformation.barginingUnitNo, null);
        } else if(newCaseInformation.barginingUnitNo != null && oldCaseInformation.barginingUnitNo != null) {
            if(!newCaseInformation.barginingUnitNo.equals(oldCaseInformation.barginingUnitNo)) 
                Activity.addActivty("Changed Union Number from " + oldCaseInformation.barginingUnitNo + " to " + newCaseInformation.barginingUnitNo, null);
        }
        
        //eo number
        if(newCaseInformation.EONumber == null && oldCaseInformation.EONumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.EONumber + " from EO Number", null);
        } else if(newCaseInformation.EONumber != null && oldCaseInformation.EONumber == null) {
            Activity.addActivty("Set EO Number to " + newCaseInformation.EONumber, null);
        } else if(newCaseInformation.EONumber != null && oldCaseInformation.EONumber != null) {
            if(!newCaseInformation.EONumber.equals(oldCaseInformation.EONumber)) 
                Activity.addActivty("Changed EO Number from " + oldCaseInformation.EONumber + " to " + newCaseInformation.EONumber, null);
        }
        
        //dept in state
        if(newCaseInformation.deptInState == null && oldCaseInformation.deptInState != null) {
            Activity.addActivty("Removed " + oldCaseInformation.deptInState + " from Department In State", null);
        } else if(newCaseInformation.deptInState != null && oldCaseInformation.deptInState == null) {
            Activity.addActivty("Set Department In State to " + newCaseInformation.deptInState, null);
        } else if(newCaseInformation.deptInState != null && oldCaseInformation.deptInState != null) {
            if(!newCaseInformation.deptInState.equals(oldCaseInformation.deptInState)) 
                Activity.addActivty("Changed Department In State from " + oldCaseInformation.deptInState + " to " + newCaseInformation.deptInState, null);
        }
    }
    
    public static String ULPDocketTo(String caseNumber) {
        String[] parsedCase = caseNumber.trim().split("-");
        String to = "";
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " investigatorID,"
                    + " aljID"
                    + " from ULPCase"
                    + " where caseYear = ? "
                    + " and caseType = ? "
                    + " and caseMonth = ? "
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, parsedCase[0]);
            preparedStatement.setString(2, parsedCase[1]);
            preparedStatement.setString(3, parsedCase[2]);
            preparedStatement.setString(4, parsedCase[3]);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            if(caseNumberRS.next()) {
                if(caseNumberRS.getInt("aljID") != 0) {
                    to = User.getNameByID(caseNumberRS.getInt("aljID"));
                } else if(caseNumberRS.getInt("investigatorID") != 0) {
                    to = User.getNameByID(caseNumberRS.getInt("investigatorID"));
                }
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                ULPDocketTo(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return to;
    }
    
    public static String ULPDocketNotification(String caseNumber) {
        String[] parsedCase = caseNumber.trim().split("-");
        String to = "";
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " mediatorAssignedID"
                    + " from ULPCase"
                    + " where caseYear = ? "
                    + " and caseType = ? "
                    + " and caseMonth = ? "
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, parsedCase[0]);
            preparedStatement.setString(2, parsedCase[1]);
            preparedStatement.setString(3, parsedCase[2]);
            preparedStatement.setString(4, parsedCase[3]);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            if(caseNumberRS.next()) {
                if(caseNumberRS.getInt("mediatorAssignedID") != 0) {
                    DocketNotifications.addNotification(caseNumber, "ULP", caseNumberRS.getInt("mediatorAssignedID"));
                } 
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                ULPDocketNotification(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return to;
    }
    
    public static boolean checkIfFristCaseOfMonth(String year, String type, String month) {
        boolean firstCase = false;
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " COUNT(*) AS CasesThisMonth"
                    + " from ULPCase"
                    + " where caseYear = ? "
                    + " and caseType = ? "
                    + " and caseMonth = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, month);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            if(caseNumberRS.next()) {
                 if(caseNumberRS.getInt("CasesThisMonth") > 0) {
                     firstCase = false;
                 } else {
                     firstCase = true;
                 }
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                checkIfFristCaseOfMonth(year, type, month);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return firstCase;
    }
    
    public static ULPCase loadULPCaseDetails(String caseYear,
            String caseType,
            String caseMonth,
            String caseNumber) 
    {
        ULPCase ulpCase = new ULPCase();
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "Select *"
                    + " from ULPCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, caseYear);
            preparedStatement.setString(2, caseType);
            preparedStatement.setString(3, caseMonth);
            preparedStatement.setString(4, caseNumber);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.first()) {
                ulpCase.id = rs.getInt("id");
                ulpCase.caseYear = rs.getString("caseYear").trim();
                ulpCase.caseType = rs.getString("caseType").trim();
                ulpCase.caseMonth = rs.getString("caseMonth").trim();
                ulpCase.caseNumber = rs.getString("caseNumber").trim();
                ulpCase.employerIDNumber = rs.getString("employerIDNumber") == null ? "" : rs.getString("employerIDNumber").trim();
                ulpCase.deptInState = rs.getString("deptInState") == null ? "" : rs.getString("deptInState").trim();
                ulpCase.barginingUnitNo = rs.getString("barginingUnitNo") == null ? "" : rs.getString("barginingUnitNo").trim();
                ulpCase.EONumber = rs.getString("EONumber") == null ? "" : rs.getString("EONumber").trim();
                ulpCase.allegation = rs.getString("allegation") == null ? "" : rs.getString("allegation").trim();
                ulpCase.currentStatus = rs.getString("currentStatus") == null ? "" : rs.getString("currentStatus").trim();
                ulpCase.priority = rs.getBoolean("priority");
                ulpCase.assignedDate = rs.getTimestamp("assignedDate");
                ulpCase.turnInDate = rs.getTimestamp("turnInDate");
                ulpCase.reportDueDate = rs.getTimestamp("reportDueDate");
                ulpCase.fileDate = rs.getTimestamp("fileDate");
                ulpCase.probableCause = rs.getBoolean("probableCause");
                ulpCase.appealDateReceived = rs.getTimestamp("appealDateReceived");
                ulpCase.appealDateSent = rs.getTimestamp("appealDateSent");
                ulpCase.courtName = rs.getString("courtName") == null ? "" : rs.getString("courtName").trim();
                ulpCase.courtCaseNumber = rs.getString("courtCaseNumber") == null ? "" : rs.getString("courtCaseNumber").trim();
                ulpCase.SERBCaseNumber = rs.getString("SERBCaseNumber") == null ? "" : rs.getString("SERBCaseNumber").trim();
                ulpCase.finalDispositionStatus = rs.getString("finalDispositionStatus") == null ? "" : rs.getString("finalDispositionStatus").trim();
                ulpCase.investigatorID = rs.getInt("investigatorID");
                ulpCase.mediatorAssignedID = rs.getInt("mediatorAssignedID");
                ulpCase.aljID = rs.getInt("aljID");
                ulpCase.statement = rs.getString("statement") == null ? "" : rs.getString("statement").trim();
                ulpCase.recommendation = rs.getString("recommendation") == null ? "" : rs.getString("recommendation").trim();
                ulpCase.investigationReveals = rs.getString("investigationReveals") == null ? "" : rs.getString("investigationReveals").trim();
                ulpCase.note = rs.getString("note") == null ? "" : rs.getString("note").trim();
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadULPCaseDetails(caseYear, caseType, caseMonth, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return ulpCase;
    }
    
    public static List<ULPCase> loadULPCasesToClose(Date boardDate) {
        List<ULPCase> ULPCaseList = new ArrayList<>();
        List casetypes = CaseType.getCaseTypeBySection("ULP");
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "SELECT ulpcase.id, ulpcase.caseYear, ulpcase.caseType, "
                    + "ulpcase.caseMonth, ulpcase.caseNumber, ulpcase.employerIDNumber, "
                    + "ulpcase.barginingUnitNo, ulpcase.fileDate, ulpcase.currentstatus "
                    + "FROM boardMeeting LEFT JOIN ulpcase ON boardMeeting.caseYear = ulpcase.caseYear "
                    + "AND boardMeeting.caseType = ulpcase.caseType "
                    + "AND boardMeeting.caseMonth = ulpcase.caseMonth "
                    + "AND boardMeeting.caseNumber = ulpcase.caseNumber "
                    + "WHERE boardMeetingDate = ? ";
                        
            if (!casetypes.isEmpty()) {
                sql += "AND (";
                
                for (Object casetype : casetypes) {
                    
                    sql += " boardMeeting.caseType = '" + casetype.toString() + "' OR";
                }
                
                sql = sql.substring(0, (sql.length() - 2)) + ")";
            }
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setDate(1, new java.sql.Date(boardDate.getTime()));           

            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                ULPCase ulpCase = new ULPCase();
                ulpCase.id = rs.getInt("id");
                ulpCase.caseYear = rs.getString("caseYear").trim();
                ulpCase.caseType = rs.getString("caseType").trim();
                ulpCase.caseMonth = rs.getString("caseMonth").trim();
                ulpCase.caseNumber = rs.getString("caseNumber").trim();
                ulpCase.employerIDNumber = rs.getString("employerIDNumber") == null ? "" : rs.getString("employerIDNumber").trim();
                ulpCase.barginingUnitNo = rs.getString("barginingUnitNo") == null ? "" : rs.getString("barginingUnitNo").trim();
                ulpCase.currentStatus = rs.getString("currentStatus") == null ? "" : rs.getString("currentStatus").trim();
                ulpCase.fileDate = rs.getTimestamp("fileDate");
                ULPCaseList.add(ulpCase);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadULPCasesToClose(boardDate);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return ULPCaseList;
    }
    
     public static void updateClosedCases(int id) {
        
        Statement stmt = null; 
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE ulpcase SET "
                    + "currentstatus = 'Closed' "
                    + "WHERE id = ? ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateClosedCases(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
}
