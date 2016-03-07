package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;
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
    
    /**
     * Load a list of the most recent 250 REP case numbers
     * @return list of rep case numbers
     */
    public static List loadULPCaseNumbers() {
        
        //TODO: Limit the load to the last 6 months of filed dates
        
        List caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP 250"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber"
                    + " from ULPCase"
                    + " Order By CaseYear DESC,"
                    + " CaseNumber DESC";

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caseNumberList;
    }
    
    public static List loadULPCases() {
        
        List caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select TOP 50 * from ULPCase Order By caseYear DESC, caseNumber DESC";

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caseNumberList;
    }
    
    public static List loadULPCases(String caseYear,
            String caseType,
            String caseMonth,
            String caseNumber,
            String search) {
        
        List caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select *"
                    + " from ULPCase"
                    + " where caseYear like ?"
                    + " and caseType like ?"
                    + " and caseMonth like ?"
                    + " and caseNumber like ?"
                    + " Order By caseYear DESC,"
                    + " caseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseYear);
            preparedStatement.setString(2, caseType);
            preparedStatement.setString(3, caseMonth);
            preparedStatement.setString(4, caseNumber);
            
            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                ULPCase ulpCase = new ULPCase();
                ulpCase.caseNumber = caseNumberRS.getString("caseNumber");
//                ulpCase.employerIDNumber = caseNumberRS.getString("employerIDNumber") == null ? "" : caseNumberRS.getString("employerIDNumber");
//                ulpCase.barginingUnitNo = caseNumberRS.getString("barginingUnitNo") == null ? "" : caseNumberRS.getString("barginingUnitNo");
                caseNumberList.add(ulpCase);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return caseNumberList;
    }
    
    public static String loadNote() {
        String note = null;
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return note;
    }

    public static void updateNote(String note) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            
            Audit.addAuditEntry("Updated Note for " + Global.caseNumber);
            Activity.addActivty("Updated Note", "");
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String loadInvestigationReveals() {
        String investigationReveals = null;
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return investigationReveals;
    }

    public static void updateInvestigationReveals(String note) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            
            Audit.addAuditEntry("Updated Investigation Reveals for " + Global.caseNumber);
            Activity.addActivty("Updated Investigation Reveals", "");
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String loadRecommendation() {
        String recommendation = null;
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recommendation;
    }

    public static void updateRecommendation(String note, String orginal) {
        try {
            
            if(!note.equals(orginal)) {
                Statement stmt = Database.connectToDB().createStatement();

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

                Audit.addAuditEntry("Updated Recommendation for " + Global.caseNumber);
                Activity.addActivty("Updated Recommendation", "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static String loadStatement() {
        String recommendation = null;
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return recommendation;
    }
    
     public static ULPCase loadStatus() {
        ULPCase ulp = new ULPCase();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        
        
        return ulp;
    }

    public static void updateStatement(String note) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            
            Audit.addAuditEntry("Updated Statement for " + Global.caseNumber);
            Activity.addActivty("Updated Statement", "");
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean validateCaseNumber(String fullCaseNumber) {
        String[] caseNumberParts = fullCaseNumber.split("-");
        boolean valid = false;
        
        if(caseNumberParts.length != 4) {
            return false;
        }
        
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valid;
    }

    public static void createCase(String caseYear, 
            String caseType, 
            String caseMonth, 
            String caseNumber) 
    {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert into ULPCase"
                    + " (caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " FileDate)"
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
                Activity.addNewCaseActivty(fullCaseNumber);
                Global.root.getuLPHeaderPanel1().loadCases();
                Global.root.getuLPHeaderPanel1().getjComboBox2().setSelectedItem(fullCaseNumber); 
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static ULPCase loadHeaderInformation() {
        
        ULPCase ulp = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex.getMessage());
        }
        return ulp;
    }
    
    public static void updateCaseStatusInformation(ULPCase newCaseInformation, ULPCase caseInformation) {
        try {
            Statement stmt = Database.connectToDB().createStatement(); 

            String sql = "Update ULPCase"
                + " set allegation = ?,"
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
            preparedStatement.setString(1, newCaseInformation.allegation);
            preparedStatement.setString(2, newCaseInformation.currentStatus);
            preparedStatement.setBoolean(3, newCaseInformation.priority);
            preparedStatement.setTimestamp(4, newCaseInformation.assignedDate);
            preparedStatement.setTimestamp(5, newCaseInformation.reportDueDate);
            preparedStatement.setTimestamp(6, newCaseInformation.dismissalDate);
            preparedStatement.setTimestamp(7, newCaseInformation.deferredDate);
            preparedStatement.setTimestamp(8, newCaseInformation.appealDateReceived);
            preparedStatement.setTimestamp(9, newCaseInformation.appealDateSent);
            preparedStatement.setString(10, newCaseInformation.courtName);
            preparedStatement.setString(11, newCaseInformation.courtCaseNumber);
            preparedStatement.setString(12, newCaseInformation.SERBCaseNumber);
            preparedStatement.setString(13, newCaseInformation.finalDispositionStatus);
            preparedStatement.setInt(14, newCaseInformation.investigatorID);
            preparedStatement.setInt(15, newCaseInformation.mediatorAssignedID);
            preparedStatement.setInt(16, newCaseInformation.aljID);
            preparedStatement.setTimestamp(17, newCaseInformation.fileDate);
            preparedStatement.setBoolean(18, newCaseInformation.probableCause);
            preparedStatement.setString(19, Global.caseYear);
            preparedStatement.setString(20, Global.caseType);
            preparedStatement.setString(21, Global.caseMonth);
            preparedStatement.setString(22, Global.caseNumber);
            
            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
            } 
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
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
                Activity.addActivty("Changed Allegation from " + oldCaseInformation.allegation + " to " + newCaseInformation.allegation, "");
        }
        
        //currentStatus
        if(newCaseInformation.currentStatus == null && oldCaseInformation.currentStatus != null) {
            Activity.addActivty("Removed " + oldCaseInformation.currentStatus + " from Current Status", "");
        } else if(newCaseInformation.currentStatus != null && oldCaseInformation.currentStatus == null) {
            Activity.addActivty("Set Current Status to " + newCaseInformation.currentStatus, "");
        } else if(newCaseInformation.currentStatus != null && oldCaseInformation.currentStatus != null) {
            if(!newCaseInformation.currentStatus.equals(oldCaseInformation.currentStatus)) 
                Activity.addActivty("Changed Current Status from " + oldCaseInformation.currentStatus + " to " + newCaseInformation.currentStatus, "");
        }
        
        //priority
        if(newCaseInformation.priority != oldCaseInformation.priority) {
            Activity.addActivty("Changed Priority from " + (oldCaseInformation.priority ? "Yes" : "No") + " to " + (newCaseInformation.priority ? "Yes" : "No"), "");
        }
        
        //assigned Date
        if(newCaseInformation.assignedDate == null && oldCaseInformation.assignedDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.assignedDate.getTime())) + " from Assigned Date", "");
        } else if(newCaseInformation.assignedDate != null && oldCaseInformation.assignedDate == null) {
            Activity.addActivty("Set Assigned Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.assignedDate.getTime())), "");
        } else if(newCaseInformation.assignedDate != null && oldCaseInformation.assignedDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.assignedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.assignedDate.getTime()))))
                Activity.addActivty("Changed Assigned Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.assignedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.assignedDate.getTime())), "");
        }
        
        //reportDueDate
        if(newCaseInformation.reportDueDate == null && oldCaseInformation.reportDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.reportDueDate.getTime())) + " from Report Due Date", "");
        } else if(newCaseInformation.reportDueDate != null && oldCaseInformation.reportDueDate == null) {
            Activity.addActivty("Set Report Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.reportDueDate.getTime())), "");
        } else if(newCaseInformation.reportDueDate != null && oldCaseInformation.reportDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.reportDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.reportDueDate.getTime()))))
                Activity.addActivty("Changed Report Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.reportDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.reportDueDate.getTime())), "");
        }
        
        //dismissalDate
        if(newCaseInformation.dismissalDate == null && oldCaseInformation.dismissalDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.dismissalDate.getTime())) + " from Dismissal Date", "");
        } else if(newCaseInformation.dismissalDate != null && oldCaseInformation.dismissalDate == null) {
            Activity.addActivty("Set Dismissal Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.dismissalDate.getTime())), "");
        } else if(newCaseInformation.dismissalDate != null && oldCaseInformation.dismissalDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.dismissalDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.dismissalDate.getTime()))))
                Activity.addActivty("Changed Dismissal Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.dismissalDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.dismissalDate.getTime())), "");
        }
        
        //deferredDate
        if(newCaseInformation.deferredDate == null && oldCaseInformation.deferredDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.deferredDate.getTime())) + " from Deffered Date", "");
        } else if(newCaseInformation.deferredDate != null && oldCaseInformation.deferredDate == null) {
            Activity.addActivty("Set Deferred Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.deferredDate.getTime())), "");
        } else if(newCaseInformation.deferredDate != null && oldCaseInformation.deferredDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.deferredDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.deferredDate.getTime()))))
                Activity.addActivty("Changed Deferred Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.deferredDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.deferredDate.getTime())), "");
        }
        
        //appealDateReceived
        if(newCaseInformation.appealDateReceived == null && oldCaseInformation.appealDateReceived != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateReceived.getTime())) + " from Appeal Received", "");
        } else if(newCaseInformation.appealDateReceived != null && oldCaseInformation.appealDateReceived == null) {
            Activity.addActivty("Set Appeal Received to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateReceived.getTime())), "");
        } else if(newCaseInformation.appealDateReceived != null && oldCaseInformation.appealDateReceived != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateReceived.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.appealDateReceived.getTime()))))
                Activity.addActivty("Changed Appeal Received from " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateReceived.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateReceived.getTime())), "");
        }
                
        //appealDateSent
        if(newCaseInformation.appealDateSent == null && oldCaseInformation.appealDateSent != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateSent.getTime())) + " from Appeal Sent", "");
        } else if(newCaseInformation.appealDateSent != null && oldCaseInformation.appealDateSent == null) {
            Activity.addActivty("Set Appeal Sent to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateSent.getTime())), "");
        } else if(newCaseInformation.appealDateSent != null && oldCaseInformation.appealDateSent != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateSent.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.appealDateSent.getTime()))))
                Activity.addActivty("Changed Appeal Sent from " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateSent.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateSent.getTime())), "");
        }   
        
        //county
        if(newCaseInformation.courtName == null && oldCaseInformation.courtName != null) {
            Activity.addActivty("Removed " + oldCaseInformation.courtName + " from Court Name", "");
        } else if(newCaseInformation.courtName != null && oldCaseInformation.courtName == null) {
            Activity.addActivty("Set Court Name to " + newCaseInformation.courtName, "");
        } else if(newCaseInformation.courtName != null && oldCaseInformation.courtName != null) {
            if(!newCaseInformation.courtName.equals(oldCaseInformation.courtName)) 
                Activity.addActivty("Changed Court Name from " + oldCaseInformation.courtName + " to " + newCaseInformation.courtName, "");
        }
                
        //courtCaseNumber
        if(newCaseInformation.courtCaseNumber == null && oldCaseInformation.courtCaseNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.courtCaseNumber + " from Court Case Number", "");
        } else if(newCaseInformation.courtCaseNumber != null && oldCaseInformation.courtCaseNumber == null) {
            Activity.addActivty("Set Court Case Number to " + newCaseInformation.courtCaseNumber, "");
        } else if(newCaseInformation.courtCaseNumber != null && oldCaseInformation.courtCaseNumber != null) {
            if(!newCaseInformation.courtCaseNumber.equals(oldCaseInformation.courtCaseNumber)) 
                Activity.addActivty("Changed Court Case Number from " + oldCaseInformation.courtCaseNumber + " to " + newCaseInformation.courtCaseNumber, "");
        }
        
        //finalDispositionStatus
        if(newCaseInformation.finalDispositionStatus == null && oldCaseInformation.finalDispositionStatus != null) {
            Activity.addActivty("Removed " + oldCaseInformation.finalDispositionStatus + " from Final Disposition Status", "");
        } else if(newCaseInformation.finalDispositionStatus != null && oldCaseInformation.finalDispositionStatus == null) {
            Activity.addActivty("Set Final Disposition Status to " + newCaseInformation.finalDispositionStatus, "");
        } else if(newCaseInformation.finalDispositionStatus != null && oldCaseInformation.finalDispositionStatus != null) {
            if(!newCaseInformation.finalDispositionStatus.equals(oldCaseInformation.finalDispositionStatus)) 
                Activity.addActivty("Changed Final Disposition Status from " + oldCaseInformation.finalDispositionStatus + " to " + newCaseInformation.finalDispositionStatus, "");
        }
        
        //investigatorID
        if(newCaseInformation.investigatorID == 0 && oldCaseInformation.investigatorID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.investigatorID) + " from Investigator", "");
        } else if(newCaseInformation.investigatorID != 0 && oldCaseInformation.investigatorID == 0) {
            Activity.addActivty("Set Investigator to " + User.getNameByID(newCaseInformation.investigatorID), "");
        } else if(newCaseInformation.investigatorID != 0 && oldCaseInformation.investigatorID != 0) {
            if(newCaseInformation.investigatorID != oldCaseInformation.investigatorID) 
                Activity.addActivty("Changed Investigator from " + User.getNameByID(oldCaseInformation.investigatorID) + " to " + User.getNameByID(newCaseInformation.investigatorID), "");
        }
        
        //mediatorAssignedID
        if(newCaseInformation.mediatorAssignedID == 0 && oldCaseInformation.mediatorAssignedID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.mediatorAssignedID) + " from Mediator", "");
        } else if(newCaseInformation.mediatorAssignedID != 0 && oldCaseInformation.mediatorAssignedID == 0) {
            Activity.addActivty("Set Mediator to " + User.getNameByID(newCaseInformation.mediatorAssignedID), "");
        } else if(newCaseInformation.mediatorAssignedID != 0 && oldCaseInformation.mediatorAssignedID != 0) {
            if(newCaseInformation.mediatorAssignedID != oldCaseInformation.mediatorAssignedID) 
                Activity.addActivty("Changed Mediator from " + User.getNameByID(oldCaseInformation.mediatorAssignedID) + " to " + User.getNameByID(newCaseInformation.mediatorAssignedID), "");
        }
        
        //aljID
        if(newCaseInformation.aljID == 0 && oldCaseInformation.aljID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.aljID) + " from ALJ", "");
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID == 0) {
            Activity.addActivty("Set ALJ to " + User.getNameByID(newCaseInformation.aljID), "");
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID != 0) {
            if(newCaseInformation.aljID != oldCaseInformation.aljID) 
                Activity.addActivty("Changed ALJ from " + User.getNameByID(oldCaseInformation.aljID) + " to " + User.getNameByID(newCaseInformation.aljID), "");
        }
        
        //fileDate
        if(newCaseInformation.fileDate == null && oldCaseInformation.fileDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " from File Date", "");
        } else if(newCaseInformation.appealDateSent != null && oldCaseInformation.fileDate == null) {
            Activity.addActivty("Set File Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), "");
        } else if(newCaseInformation.fileDate != null && oldCaseInformation.fileDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime()))))
                Activity.addActivty("Changed File Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), "");
        } 
        
        //TODO: THis is a big, updates at the wrong times or just does not update
        //probableCause
        if(newCaseInformation.probableCause != oldCaseInformation.probableCause) {
            Activity.addActivty("Changed Priority from " + (oldCaseInformation.probableCause ? "Yes" : "No") + " to " + (newCaseInformation.probableCause ? "Yes" : "No"), "");
        }
    }
}
