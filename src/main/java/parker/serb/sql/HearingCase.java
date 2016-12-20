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
public class HearingCase {
    
    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String openClose;
    
    
    public String caseStatusNotes;
    public Timestamp boardActionPCDate;
    public Timestamp boardActionPreDDate;
    public Timestamp directiveIssuedDate;
    public boolean expedited;
    public Timestamp complaintDueDate;
    public Timestamp draftComplaintToHearingDate;
    public Timestamp preHearingDate;
    public Timestamp proposedRecDueDate;
    public Timestamp exceptionFilingDate;
//    public String employerIDNumber;
//    public String deptInState;
//    public String barginingUnitNo;
//    public String EONumber;
//    public String allegation;
//    public String currentStatus;
//    public boolean priority;
//    public Timestamp assignedDate;
//    public Timestamp turnInDate;
//    public Timestamp reportDueDate;
//    public Timestamp dismissalDate;
//    public Timestamp deferredDate;
//    public Timestamp fileDate;
//    public boolean probableCause;
//    public Timestamp appealDateReceived;
//    public Timestamp appealDateSent;
//    public String courtName;
//    public String courtCaseNumber;
//    public String SERBCaseNumber;
//    public String finalDispositionStatus;
//    public int investigatorID;
//    public int mediatorAssignedID;
//    public int aljID;
//    public String statement;
//    public String recommendation;
//    public String investigationReveals;
//    public String note;
    
    public static List loadHearingCaseNumbers() {
        
        List caseNumberList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber"
                    + " from HearingCase"
                    //+ " WHERE openClose = 'Open'"
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
    
    public static Timestamp getBoardActionPCDate() {
        
        Timestamp boardActionPCDate = null;
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select boardActionPCDate from HearingCase"
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
            
            while(caseNumberRS.next()) {
                boardActionPCDate = caseNumberRS.getTimestamp("boardActionPCDate");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return boardActionPCDate;
    }

    public static void createCase(String caseNumber) 
    {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert into HearingCase"
                    + " (caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber)"
                    + " Values ("
                    + " ?,"
                    + " ?,"
                    + " ?,"
                    + " ?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber.split("-")[0]);
            preparedStatement.setString(2, caseNumber.split("-")[1]);
            preparedStatement.setString(3, caseNumber.split("-")[2]);
            preparedStatement.setString(4, caseNumber.split("-")[3]);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                String fullCaseNumber = caseNumber.split("-")[0]
                        + "-"
                        + caseNumber.split("-")[1]
                        + "-"
                        + caseNumber.split("-")[2]
                        + "-"
                        + caseNumber.split("-")[3];
                        
                Global.root.getHearingHeaderPanel1().loadCases();
                Global.root.getHearingHeaderPanel1().getjComboBox2().setSelectedItem(fullCaseNumber); 
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static HearingCase loadHeaderInformation() {
        
        HearingCase hearings = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select * "
                    + "from HearingCase"
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
                hearings = new HearingCase();
                hearings.openClose = caseHeader.getString("openClose");
//                ulp.currentStatus = caseHeader.getString("currentStatus");
//                ulp.investigatorID = caseHeader.getInt("investigatorID");
//                ulp.aljID = caseHeader.getInt("aljID");
                
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return hearings;
    }
    
    public static void updateHearingCaseInformation(HearingCase newCaseInformation, HearingCase caseInformation) {
        try {
            Statement stmt = Database.connectToDB().createStatement(); 

            String sql = "Update HearingCase"
                + " set caseStatusNotes = ?,"
                + " openClose = ?,"
                + " boardActionPCDate = ?,"
                + " boardActionPreDDate = ?,"
                + " directiveIssueDate = ?,"
                + " expedited = ?,"
                + " complaintDueDate = ?,"
                + " draftComplaintToHearingDate = ?,"
                + " preHearingDate = ?,"
                + " proposedRecDueDate = ?,"
                + " exceptionFilingDate = ?"
                + " where caseYear = ?"
                + " and caseType = ?"
                + " and caseMonth = ?"
                + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseInformation.caseStatusNotes);
            preparedStatement.setString(2, newCaseInformation.openClose);
            preparedStatement.setTimestamp(3, newCaseInformation.boardActionPCDate);
            preparedStatement.setTimestamp(4, newCaseInformation.boardActionPreDDate);
            preparedStatement.setTimestamp(5, newCaseInformation.directiveIssuedDate);
            preparedStatement.setBoolean(6, newCaseInformation.expedited);
            preparedStatement.setTimestamp(7, newCaseInformation.complaintDueDate);
            preparedStatement.setTimestamp(8, newCaseInformation.draftComplaintToHearingDate);
            preparedStatement.setTimestamp(9, newCaseInformation.preHearingDate);
            preparedStatement.setTimestamp(10, newCaseInformation.proposedRecDueDate);
            preparedStatement.setTimestamp(11, newCaseInformation.exceptionFilingDate);
            preparedStatement.setString(12, Global.caseYear);
            preparedStatement.setString(13, Global.caseType);
            preparedStatement.setString(14, Global.caseMonth);
            preparedStatement.setString(15, Global.caseNumber);
            
            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
            } 
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    private static void detailedCaseInformationSaveInformation(HearingCase newCaseInformation, HearingCase oldCaseInformation) {
//      caseStatusNotes
        if(newCaseInformation.caseStatusNotes == null && oldCaseInformation.caseStatusNotes != null) {
            Activity.addActivty("Update Case Status Notes", null);
        } else if(newCaseInformation.caseStatusNotes != null && oldCaseInformation.caseStatusNotes == null) {
            Activity.addActivty("Update Case Status Notes", null);
        } else if(newCaseInformation.caseStatusNotes != null && oldCaseInformation.caseStatusNotes != null) {
            if(!newCaseInformation.caseStatusNotes.equals(oldCaseInformation.caseStatusNotes)) 
                Activity.addActivty("Update Case Status Notes", null);
        }
        
        //currentStatus
//        if(newCaseInformation.currentStatus == null && oldCaseInformation.currentStatus != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.currentStatus + " from Current Status", null);
//        } else if(newCaseInformation.currentStatus != null && oldCaseInformation.currentStatus == null) {
//            Activity.addActivty("Set Current Status to " + newCaseInformation.currentStatus, null);
//        } else if(newCaseInformation.currentStatus != null && oldCaseInformation.currentStatus != null) {
//            if(!newCaseInformation.currentStatus.equals(oldCaseInformation.currentStatus)) 
//                Activity.addActivty("Changed Current Status from " + oldCaseInformation.currentStatus + " to " + newCaseInformation.currentStatus, null);
//        }
        
        //priority
//        if(newCaseInformation.priority != oldCaseInformation.priority) {
//            Activity.addActivty("Changed Priority from " + (oldCaseInformation.priority ? "Yes" : "No") + " to " + (newCaseInformation.priority ? "Yes" : "No"), null);
//        }
        
        //assigned Date
//        if(newCaseInformation.assignedDate == null && oldCaseInformation.assignedDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.assignedDate.getTime())) + " from Assigned Date", null);
//        } else if(newCaseInformation.assignedDate != null && oldCaseInformation.assignedDate == null) {
//            Activity.addActivty("Set Assigned Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.assignedDate.getTime())), null);
//        } else if(newCaseInformation.assignedDate != null && oldCaseInformation.assignedDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.assignedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.assignedDate.getTime()))))
//                Activity.addActivty("Changed Assigned Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.assignedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.assignedDate.getTime())), null);
//        }
        
        //reportDueDate
//        if(newCaseInformation.reportDueDate == null && oldCaseInformation.reportDueDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.reportDueDate.getTime())) + " from Report Due Date", null);
//        } else if(newCaseInformation.reportDueDate != null && oldCaseInformation.reportDueDate == null) {
//            Activity.addActivty("Set Report Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.reportDueDate.getTime())), null);
//        } else if(newCaseInformation.reportDueDate != null && oldCaseInformation.reportDueDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.reportDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.reportDueDate.getTime()))))
//                Activity.addActivty("Changed Report Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.reportDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.reportDueDate.getTime())), null);
//        }
        
        //dismissalDate
//        if(newCaseInformation.dismissalDate == null && oldCaseInformation.dismissalDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.dismissalDate.getTime())) + " from Dismissal Date", null);
//        } else if(newCaseInformation.dismissalDate != null && oldCaseInformation.dismissalDate == null) {
//            Activity.addActivty("Set Dismissal Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.dismissalDate.getTime())), null);
//        } else if(newCaseInformation.dismissalDate != null && oldCaseInformation.dismissalDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.dismissalDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.dismissalDate.getTime()))))
//                Activity.addActivty("Changed Dismissal Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.dismissalDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.dismissalDate.getTime())), null);
//        }
        
        //deferredDate
//        if(newCaseInformation.deferredDate == null && oldCaseInformation.deferredDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.deferredDate.getTime())) + " from Deffered Date", null);
//        } else if(newCaseInformation.deferredDate != null && oldCaseInformation.deferredDate == null) {
//            Activity.addActivty("Set Deferred Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.deferredDate.getTime())), null);
//        } else if(newCaseInformation.deferredDate != null && oldCaseInformation.deferredDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.deferredDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.deferredDate.getTime()))))
//                Activity.addActivty("Changed Deferred Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.deferredDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.deferredDate.getTime())), null);
//        }
        
        //appealDateReceived
//        if(newCaseInformation.appealDateReceived == null && oldCaseInformation.appealDateReceived != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateReceived.getTime())) + " from Appeal Received", null);
//        } else if(newCaseInformation.appealDateReceived != null && oldCaseInformation.appealDateReceived == null) {
//            Activity.addActivty("Set Appeal Received to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateReceived.getTime())), null);
//        } else if(newCaseInformation.appealDateReceived != null && oldCaseInformation.appealDateReceived != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateReceived.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.appealDateReceived.getTime()))))
//                Activity.addActivty("Changed Appeal Received from " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateReceived.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateReceived.getTime())), null);
//        }
                
        //appealDateSent
//        if(newCaseInformation.appealDateSent == null && oldCaseInformation.appealDateSent != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateSent.getTime())) + " from Appeal Sent", null);
//        } else if(newCaseInformation.appealDateSent != null && oldCaseInformation.appealDateSent == null) {
//            Activity.addActivty("Set Appeal Sent to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateSent.getTime())), null);
//        } else if(newCaseInformation.appealDateSent != null && oldCaseInformation.appealDateSent != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateSent.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.appealDateSent.getTime()))))
//                Activity.addActivty("Changed Appeal Sent from " + Global.mmddyyyy.format(new Date(oldCaseInformation.appealDateSent.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.appealDateSent.getTime())), null);
//        }   
        
        //cournt name
//        if(newCaseInformation.courtName == null && oldCaseInformation.courtName != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.courtName + " from Court Name", null);
//        } else if(newCaseInformation.courtName != null && oldCaseInformation.courtName == null) {
//            Activity.addActivty("Set Court Name to " + newCaseInformation.courtName, null);
//        } else if(newCaseInformation.courtName != null && oldCaseInformation.courtName != null) {
//            if(!newCaseInformation.courtName.equals(oldCaseInformation.courtName)) 
//                Activity.addActivty("Changed Court Name from " + oldCaseInformation.courtName + " to " + newCaseInformation.courtName, null);
//        }
                
        //courtCaseNumber
//        if(newCaseInformation.courtCaseNumber == null && oldCaseInformation.courtCaseNumber != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.courtCaseNumber + " from Court Case Number", null);
//        } else if(newCaseInformation.courtCaseNumber != null && oldCaseInformation.courtCaseNumber == null) {
//            Activity.addActivty("Set Court Case Number to " + newCaseInformation.courtCaseNumber, null);
//        } else if(newCaseInformation.courtCaseNumber != null && oldCaseInformation.courtCaseNumber != null) {
//            if(!newCaseInformation.courtCaseNumber.equals(oldCaseInformation.courtCaseNumber)) 
//                Activity.addActivty("Changed Court Case Number from " + oldCaseInformation.courtCaseNumber + " to " + newCaseInformation.courtCaseNumber, null);
//        }
        
        //serbCaseNumber
//        if(newCaseInformation.SERBCaseNumber == null && oldCaseInformation.SERBCaseNumber != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.SERBCaseNumber + " from SERB Case Number", null);
//        } else if(newCaseInformation.SERBCaseNumber != null && oldCaseInformation.SERBCaseNumber == null) {
//            Activity.addActivty("Set SERB Case Number to " + newCaseInformation.SERBCaseNumber, null);
//        } else if(newCaseInformation.SERBCaseNumber != null && oldCaseInformation.SERBCaseNumber != null) {
//            if(!newCaseInformation.SERBCaseNumber.equals(oldCaseInformation.SERBCaseNumber)) 
//                Activity.addActivty("Changed SERB Case Number from " + oldCaseInformation.SERBCaseNumber + " to " + newCaseInformation.SERBCaseNumber, null);
//        }
        
        //finalDispositionStatus
//        if(newCaseInformation.finalDispositionStatus == null && oldCaseInformation.finalDispositionStatus != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.finalDispositionStatus + " from Final Disposition Status", null);
//        } else if(newCaseInformation.finalDispositionStatus != null && oldCaseInformation.finalDispositionStatus == null) {
//            Activity.addActivty("Set Final Disposition Status to " + newCaseInformation.finalDispositionStatus, null);
//        } else if(newCaseInformation.finalDispositionStatus != null && oldCaseInformation.finalDispositionStatus != null) {
//            if(!newCaseInformation.finalDispositionStatus.equals(oldCaseInformation.finalDispositionStatus)) 
//                Activity.addActivty("Changed Final Disposition Status from " + oldCaseInformation.finalDispositionStatus + " to " + newCaseInformation.finalDispositionStatus, null);
//        }
        
        //investigatorID
//        if(newCaseInformation.investigatorID == 0 && oldCaseInformation.investigatorID != 0) {
//            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.investigatorID) + " from Investigator", null);
//        } else if(newCaseInformation.investigatorID != 0 && oldCaseInformation.investigatorID == 0) {
//            Activity.addActivty("Set Investigator to " + User.getNameByID(newCaseInformation.investigatorID), null);
//        } else if(newCaseInformation.investigatorID != 0 && oldCaseInformation.investigatorID != 0) {
//            if(newCaseInformation.investigatorID != oldCaseInformation.investigatorID) 
//                Activity.addActivty("Changed Investigator from " + User.getNameByID(oldCaseInformation.investigatorID) + " to " + User.getNameByID(newCaseInformation.investigatorID), null);
//        }
        
        //mediatorAssignedID
//        if(newCaseInformation.mediatorAssignedID == 0 && oldCaseInformation.mediatorAssignedID != 0) {
//            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.mediatorAssignedID) + " from Mediator", null);
//        } else if(newCaseInformation.mediatorAssignedID != 0 && oldCaseInformation.mediatorAssignedID == 0) {
//            Activity.addActivty("Set Mediator to " + User.getNameByID(newCaseInformation.mediatorAssignedID), null);
//        } else if(newCaseInformation.mediatorAssignedID != 0 && oldCaseInformation.mediatorAssignedID != 0) {
//            if(newCaseInformation.mediatorAssignedID != oldCaseInformation.mediatorAssignedID) 
//                Activity.addActivty("Changed Mediator from " + User.getNameByID(oldCaseInformation.mediatorAssignedID) + " to " + User.getNameByID(newCaseInformation.mediatorAssignedID), null);
//        }
        
        //aljID
//        if(newCaseInformation.aljID == 0 && oldCaseInformation.aljID != 0) {
//            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.aljID) + " from ALJ", null);
//        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID == 0) {
//            Activity.addActivty("Set ALJ to " + User.getNameByID(newCaseInformation.aljID), null);
//        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID != 0) {
//            if(newCaseInformation.aljID != oldCaseInformation.aljID) 
//                Activity.addActivty("Changed ALJ from " + User.getNameByID(oldCaseInformation.aljID) + " to " + User.getNameByID(newCaseInformation.aljID), null);
//        }
        
        //fileDate
//        if(newCaseInformation.fileDate == null && oldCaseInformation.fileDate != null) {
//            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " from File Date", null);
//        } else if(newCaseInformation.appealDateSent != null && oldCaseInformation.fileDate == null) {
//            Activity.addActivty("Set File Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
//        } else if(newCaseInformation.fileDate != null && oldCaseInformation.fileDate != null) {
//            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime()))))
//                Activity.addActivty("Changed File Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.fileDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.fileDate.getTime())), null);
//        } 
        
        //probableCause
//        if(newCaseInformation.probableCause != oldCaseInformation.probableCause) {
//            Activity.addActivty("Changed Probable Cause from " + (oldCaseInformation.probableCause ? "Yes" : "No") + " to " + (newCaseInformation.probableCause ? "Yes" : "No"), "");
//        }
        
        //employer Number
//        if(newCaseInformation.employerIDNumber == null && oldCaseInformation.employerIDNumber != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.employerIDNumber + " from Employer ID Number", null);
//        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber == null) {
//            Activity.addActivty("Set Employer ID Number to " + newCaseInformation.employerIDNumber, null);
//        } else if(newCaseInformation.employerIDNumber != null && oldCaseInformation.employerIDNumber != null) {
//            if(!newCaseInformation.employerIDNumber.equals(oldCaseInformation.employerIDNumber)) 
//                Activity.addActivty("Changed Employer ID Number from " + oldCaseInformation.employerIDNumber + " to " + newCaseInformation.employerIDNumber, null);
//        }
        
        //union number
//        if(newCaseInformation.barginingUnitNo == null && oldCaseInformation.barginingUnitNo != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.barginingUnitNo + " from Union Number", null);
//        } else if(newCaseInformation.barginingUnitNo != null && oldCaseInformation.barginingUnitNo == null) {
//            Activity.addActivty("Set Union Number to " + newCaseInformation.barginingUnitNo, null);
//        } else if(newCaseInformation.barginingUnitNo != null && oldCaseInformation.barginingUnitNo != null) {
//            if(!newCaseInformation.barginingUnitNo.equals(oldCaseInformation.barginingUnitNo)) 
//                Activity.addActivty("Changed Union Number from " + oldCaseInformation.barginingUnitNo + " to " + newCaseInformation.barginingUnitNo, null);
//        }
        
        //eo number
//        if(newCaseInformation.EONumber == null && oldCaseInformation.EONumber != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.EONumber + " from EO Number", null);
//        } else if(newCaseInformation.EONumber != null && oldCaseInformation.EONumber == null) {
//            Activity.addActivty("Set EO Number to " + newCaseInformation.EONumber, null);
//        } else if(newCaseInformation.EONumber != null && oldCaseInformation.EONumber != null) {
//            if(!newCaseInformation.EONumber.equals(oldCaseInformation.EONumber)) 
//                Activity.addActivty("Changed EO Number from " + oldCaseInformation.EONumber + " to " + newCaseInformation.EONumber, null);
//        }
        
        //dept in state
//        if(newCaseInformation.deptInState == null && oldCaseInformation.deptInState != null) {
//            Activity.addActivty("Removed " + oldCaseInformation.deptInState + " from Department In State", null);
//        } else if(newCaseInformation.deptInState != null && oldCaseInformation.deptInState == null) {
//            Activity.addActivty("Set Department In State to " + newCaseInformation.deptInState, null);
//        } else if(newCaseInformation.deptInState != null && oldCaseInformation.deptInState != null) {
//            if(!newCaseInformation.deptInState.equals(oldCaseInformation.deptInState)) 
//                Activity.addActivty("Changed Department In State from " + oldCaseInformation.deptInState + " to " + newCaseInformation.deptInState, null);
//        }
    }
    
    public static HearingCase loadHearingCaseInformation() {
        
        HearingCase hearingCase = new HearingCase();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select caseStatusNotes,"
                    + " openClose,"
                    + " boardActionPCDate,"
                    + " boardActionPreDDate,"
                    + " directiveIssueDate,"
                    + " expedited,"
                    + " complaintDueDate,"
                    + " draftComplaintToHearingDate,"
                    + " preHearingDate,"
                    + " proposedRecDueDate,"
                    + " exceptionFilingDate"
                    + " from HearingCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            if(rs.first()) {
                hearingCase.openClose = rs.getString("openClose");
                hearingCase.caseStatusNotes = rs.getString("caseStatusNotes");
                hearingCase.boardActionPCDate = rs.getTimestamp("boardActionPCDate");
                hearingCase.boardActionPreDDate = rs.getTimestamp("boardActionPreDDate");
                hearingCase.directiveIssuedDate = rs.getTimestamp("directiveIssueDate");
                hearingCase.expedited = rs.getBoolean("expedited");
                hearingCase.complaintDueDate = rs.getTimestamp("complaintDueDate");
                hearingCase.draftComplaintToHearingDate = rs.getTimestamp("draftComplaintToHearingDate");
                hearingCase.preHearingDate = rs.getTimestamp("preHearingDate");
                hearingCase.proposedRecDueDate = rs.getTimestamp("proposedRecDueDate");
                hearingCase.exceptionFilingDate = rs.getTimestamp("exceptionFilingDate");

//                ulpCase.id = rs.getInt("id");
//                ulpCase.caseYear = rs.getString("caseYear").trim();
//                ulpCase.caseType = rs.getString("caseType").trim();
//                ulpCase.caseMonth = rs.getString("caseMonth").trim();
//                ulpCase.caseNumber = rs.getString("caseNumber").trim();
//                ulpCase.employerIDNumber = rs.getString("employerIDNumber") == null ? "" : rs.getString("employerIDNumber").trim();
//                ulpCase.deptInState = rs.getString("deptInState") == null ? "" : rs.getString("deptInState").trim();
//                ulpCase.barginingUnitNo = rs.getString("barginingUnitNo") == null ? "" : rs.getString("barginingUnitNo").trim();
//                ulpCase.EONumber = rs.getString("EONumber") == null ? "" : rs.getString("EONumber").trim();
//                ulpCase.allegation = rs.getString("allegation") == null ? "" : rs.getString("allegation").trim();
//                ulpCase.currentStatus = rs.getString("currentStatus") == null ? "" : rs.getString("currentStatus").trim();
//                ulpCase.priority = rs.getBoolean("priority");
//                ulpCase.assignedDate = rs.getTimestamp("assignedDate");
//                ulpCase.turnInDate = rs.getTimestamp("turnInDate");
//                ulpCase.reportDueDate = rs.getTimestamp("reportDueDate");
////                ulpCase.dismissalDate = rs.getTimestamp("dismissalDate");
////                ulpCase.deferredDate = rs.getTimestamp("deferredDate");
//                ulpCase.fileDate = rs.getTimestamp("fileDate");
//                ulpCase.probableCause = rs.getBoolean("probableCause");
//                ulpCase.appealDateReceived = rs.getTimestamp("appealDateReceived");
//                ulpCase.appealDateSent = rs.getTimestamp("appealDateSent");
//                ulpCase.courtName = rs.getString("courtName") == null ? "" : rs.getString("courtName").trim();
//                ulpCase.courtCaseNumber = rs.getString("courtCaseNumber") == null ? "" : rs.getString("courtCaseNumber").trim();
//                ulpCase.SERBCaseNumber = rs.getString("SERBCaseNumber") == null ? "" : rs.getString("SERBCaseNumber").trim();
//                ulpCase.finalDispositionStatus = rs.getString("finalDispositionStatus") == null ? "" : rs.getString("finalDispositionStatus").trim();
//                ulpCase.investigatorID = rs.getInt("investigatorID");
//                ulpCase.mediatorAssignedID = rs.getInt("mediatorAssignedID");
//                ulpCase.aljID = rs.getInt("aljID");
//                ulpCase.statement = rs.getString("statement") == null ? "" : rs.getString("statement").trim();
//                ulpCase.recommendation = rs.getString("recommendation") == null ? "" : rs.getString("recommendation").trim();
//                ulpCase.investigationReveals = rs.getString("investigationReveals") == null ? "" : rs.getString("investigationReveals").trim();
//                ulpCase.note = rs.getString("note") == null ? "" : rs.getString("note").trim();
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return hearingCase;
    }
    
    public static String getALJemail() {
        String email = "";
        
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select emailAddress "
                    + "from Users"
                    + " INNER JOIN CMDSCase"
                    + " ON CMDSCase.aljID = Users.id"
                    + " where caseYear = ? and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            if(caseNumberRS.next()) {
                email = caseNumberRS.getString("emailAddress");
            }
            stmt.close();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return email;
    }
}
