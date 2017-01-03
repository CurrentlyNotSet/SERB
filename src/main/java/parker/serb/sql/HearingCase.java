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
    
    public Timestamp boardActionDate;
    public String otherAction;
    public int aljID;
    public Timestamp complaintIssuedDate;
    public Timestamp hearingDate;
    public Timestamp proposedRecIssuedDate;
    public Timestamp responseFilingDate;
    public Timestamp IssuanceOfOptionOrDirectiveDate;
    public String FinalResult;
    public Timestamp opinion;
    public String companionCases;
    
    public static List loadHearingCaseNumbers() {
        List caseNumberList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP 250"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber"
                    + " from HearingCase"
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadHearingCaseNumbers();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseNumberList;
    }
    
    public static Timestamp getBoardActionPCDate() {
        Timestamp boardActionPCDate = null;
        
        Statement stmt = null;
           
        try {
            stmt = Database.connectToDB().createStatement();
            
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getBoardActionPCDate();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return boardActionPCDate;
    }

    public static void createCase(String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

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
                        
                HearingCaseSearchData.createNewCaseEntry(
                        caseNumber.split("-")[0],
                        caseNumber.split("-")[1],
                        caseNumber.split("-")[2],
                        caseNumber.split("-")[3]);
                HearingCaseSearchData.updateCaseEntryFromParties(caseNumber);
                Global.root.getHearingHeaderPanel1().loadCases();
                Global.root.getHearingHeaderPanel1().getjComboBox2().setSelectedItem(fullCaseNumber); 
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createCase(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static HearingCase loadHeaderInformation() {
        Statement stmt = null;
        HearingCase hearings = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select aljID,"
                    + " boardActionPCDate,"
                    + " openClose,"
                    + " finalResult"
                    + " from HearingCase"
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
                hearings.aljID = caseHeader.getInt("aljID");
                hearings.boardActionPCDate = caseHeader.getTimestamp("boardActionPCDate");
                hearings.openClose = caseHeader.getString("openClose");
                hearings.FinalResult = caseHeader.getString("finalResult");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadHeaderInformation();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return hearings;
    }
    
    public static void updateHearingCaseInformation(HearingCase newCaseInformation, HearingCase caseInformation) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement(); 

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
                + " exceptionFilingDate = ?,"
                + " boardActionDate = ?,"
                + " otherAction = ?,"
                + " aljID = ?,"
                + " complaintIssuedDate = ?,"
                + " hearingDate = ?,"
                + " proposedRecIssuedDate = ?,"
                + " responseFilingDate = ?,"
                + " issuanceOfOptionOrDirectiveDate = ?,"
                + " finalResult = ?,"
                + " opinion = ?,"
                + " companionCases = ?"
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
            
            preparedStatement.setTimestamp(12, newCaseInformation.boardActionDate);
            preparedStatement.setString(13, newCaseInformation.otherAction);
            preparedStatement.setInt(14, newCaseInformation.aljID);
            preparedStatement.setTimestamp(15, newCaseInformation.complaintIssuedDate);
            preparedStatement.setTimestamp(16, newCaseInformation.hearingDate);
            preparedStatement.setTimestamp(17, newCaseInformation.proposedRecIssuedDate);
            preparedStatement.setTimestamp(18, newCaseInformation.responseFilingDate);
            preparedStatement.setTimestamp(19, newCaseInformation.IssuanceOfOptionOrDirectiveDate);
            preparedStatement.setString(20, newCaseInformation.FinalResult);
            preparedStatement.setTimestamp(21, newCaseInformation.opinion);
            preparedStatement.setString(22, newCaseInformation.companionCases);
            
            preparedStatement.setString(23, Global.caseYear);
            preparedStatement.setString(24, Global.caseType);
            preparedStatement.setString(25, Global.caseMonth);
            preparedStatement.setString(26, Global.caseNumber);
            
            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
                HearingCaseSearchData.updateCaseEntryFromCaseInformation(
                    newCaseInformation.boardActionPCDate,
                    newCaseInformation.aljID,
                    newCaseInformation.boardActionDate,
                    newCaseInformation.openClose
                );
            } 
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateHearingCaseInformation(newCaseInformation, caseInformation);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
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
        
        //boardActionPCDate
        if(newCaseInformation.boardActionPCDate == null && oldCaseInformation.boardActionPCDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionPCDate.getTime())) + " from Board Action PC Date", null);
        } else if(newCaseInformation.boardActionPCDate != null && oldCaseInformation.boardActionPCDate == null) {
            Activity.addActivty("Set Board Action PC Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardActionPCDate.getTime())), null);
        } else if(newCaseInformation.boardActionPCDate != null && oldCaseInformation.boardActionPCDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionPCDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.boardActionPCDate.getTime()))))
                Activity.addActivty("Changed Board Action PC Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionPCDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardActionPCDate.getTime())), null);
        }
        
        //boardActionPreDDate
        if(newCaseInformation.boardActionPreDDate == null && oldCaseInformation.boardActionPreDDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionPreDDate.getTime())) + " from Board Action Pre-D Date", null);
        } else if(newCaseInformation.boardActionPreDDate != null && oldCaseInformation.boardActionPreDDate == null) {
            Activity.addActivty("Set Board Action Pre-D Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardActionPreDDate.getTime())), null);
        } else if(newCaseInformation.boardActionPreDDate != null && oldCaseInformation.boardActionPreDDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionPreDDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.boardActionPreDDate.getTime()))))
                Activity.addActivty("Changed Board Action Pre-D Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionPreDDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardActionPreDDate.getTime())), null);
        }
        
        //directIssuedDate
        if(newCaseInformation.directiveIssuedDate == null && oldCaseInformation.directiveIssuedDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.directiveIssuedDate.getTime())) + " from Directive Issued Date", null);
        } else if(newCaseInformation.directiveIssuedDate != null && oldCaseInformation.directiveIssuedDate == null) {
            Activity.addActivty("Set Directive Issued Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.directiveIssuedDate.getTime())), null);
        } else if(newCaseInformation.directiveIssuedDate != null && oldCaseInformation.directiveIssuedDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.directiveIssuedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.directiveIssuedDate.getTime()))))
                Activity.addActivty("Changed Directive Issued Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.directiveIssuedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.directiveIssuedDate.getTime())), null);
        }
        
        //expedited
        if(newCaseInformation.expedited != false && oldCaseInformation.expedited == false) {
            Activity.addActivty("Set Expedited to Yes", null);
        } else if(newCaseInformation.expedited == false && oldCaseInformation.expedited != false) {
            Activity.addActivty("Set Expedited to No", null);
        }
        
        //complaintDueDate
        if(newCaseInformation.complaintDueDate == null && oldCaseInformation.complaintDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.complaintDueDate.getTime())) + " from Complaint Due Date", null);
        } else if(newCaseInformation.complaintDueDate != null && oldCaseInformation.complaintDueDate == null) {
            Activity.addActivty("Set Complaint Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.complaintDueDate.getTime())), null);
        } else if(newCaseInformation.complaintDueDate != null && oldCaseInformation.complaintDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.complaintDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.complaintDueDate.getTime()))))
                Activity.addActivty("Changed Complaint Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.complaintDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.complaintDueDate.getTime())), null);
        }
        
        //draftComplaintToHearing
        if(newCaseInformation.draftComplaintToHearingDate == null && oldCaseInformation.draftComplaintToHearingDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.draftComplaintToHearingDate.getTime())) + " from Draft Complaint to Hearing Date", null);
        } else if(newCaseInformation.draftComplaintToHearingDate != null && oldCaseInformation.draftComplaintToHearingDate == null) {
            Activity.addActivty("Set Draft Complaint to Hearing Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.draftComplaintToHearingDate.getTime())), null);
        } else if(newCaseInformation.draftComplaintToHearingDate != null && oldCaseInformation.draftComplaintToHearingDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.draftComplaintToHearingDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.draftComplaintToHearingDate.getTime()))))
                Activity.addActivty("Changed Draft Complaint to Hearing Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.draftComplaintToHearingDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.draftComplaintToHearingDate.getTime())), null);
        }
        
        //prehearingDate
        if(newCaseInformation.preHearingDate == null && oldCaseInformation.preHearingDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.preHearingDate.getTime())) + " from Pre-Hearing Date", null);
        } else if(newCaseInformation.preHearingDate != null && oldCaseInformation.preHearingDate == null) {
            Activity.addActivty("Set Pre-Hearing Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.preHearingDate.getTime())), null);
        } else if(newCaseInformation.preHearingDate != null && oldCaseInformation.preHearingDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.preHearingDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.preHearingDate.getTime()))))
                Activity.addActivty("Changed Pre-Hearing Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.preHearingDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.preHearingDate.getTime())), null);
        }
        
        //proposedRecDate
        if(newCaseInformation.proposedRecDueDate == null && oldCaseInformation.proposedRecDueDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.proposedRecDueDate.getTime())) + " from Proposed Rec Due Date", null);
        } else if(newCaseInformation.proposedRecDueDate != null && oldCaseInformation.proposedRecDueDate == null) {
            Activity.addActivty("Set Proposed Rec Due Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.proposedRecDueDate.getTime())), null);
        } else if(newCaseInformation.proposedRecDueDate != null && oldCaseInformation.proposedRecDueDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.proposedRecDueDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.proposedRecDueDate.getTime()))))
                Activity.addActivty("Changed Proposed Rec Due Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.proposedRecDueDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.proposedRecDueDate.getTime())), null);
        }
        
        //exceptionsDate
        if(newCaseInformation.exceptionFilingDate == null && oldCaseInformation.exceptionFilingDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.exceptionFilingDate.getTime())) + " from Exception Filing Date", null);
        } else if(newCaseInformation.exceptionFilingDate != null && oldCaseInformation.exceptionFilingDate == null) {
            Activity.addActivty("Set Exception Filing Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.exceptionFilingDate.getTime())), null);
        } else if(newCaseInformation.exceptionFilingDate != null && oldCaseInformation.exceptionFilingDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.exceptionFilingDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.exceptionFilingDate.getTime()))))
                Activity.addActivty("Changed Exception Filing Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.exceptionFilingDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.exceptionFilingDate.getTime())), null);
        }
        
        //boardActionDate
        if(newCaseInformation.boardActionDate == null && oldCaseInformation.boardActionDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionDate.getTime())) + " from Board Action Date", null);
        } else if(newCaseInformation.boardActionDate != null && oldCaseInformation.boardActionDate == null) {
            Activity.addActivty("Set Board Action Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardActionDate.getTime())), null);
        } else if(newCaseInformation.boardActionDate != null && oldCaseInformation.boardActionDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.boardActionDate.getTime()))))
                Activity.addActivty("Changed Board Action Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.boardActionDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.boardActionDate.getTime())), null);
        }
        
        //otherAction
        if(newCaseInformation.otherAction == null && oldCaseInformation.otherAction != null) {
            Activity.addActivty("Removed " + oldCaseInformation.otherAction + " from Other Action", null);
        } else if(newCaseInformation.otherAction != null && oldCaseInformation.otherAction == null) {
            Activity.addActivty("Set Other Action to " + newCaseInformation.otherAction, null);
        } else if(newCaseInformation.otherAction != null && oldCaseInformation.otherAction != null) {
            if(!newCaseInformation.otherAction.equals(oldCaseInformation.otherAction)) 
                Activity.addActivty("Changed Other Action from " + oldCaseInformation.otherAction + " to " + newCaseInformation.otherAction, null);
        }
        
        //alj
        if(newCaseInformation.aljID == 0 && oldCaseInformation.aljID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.aljID) + " from ALJ", null);
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID == 0) {
            Activity.addActivty("Set ALJ to " + User.getNameByID(newCaseInformation.aljID), null);
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID != 0) {
            if(newCaseInformation.aljID != oldCaseInformation.aljID) 
                Activity.addActivty("Changed ALJ from " + User.getNameByID(oldCaseInformation.aljID) + " to " + User.getNameByID(newCaseInformation.aljID), null);
        }
        
        //complaintIssuedDate
        if(newCaseInformation.complaintIssuedDate == null && oldCaseInformation.complaintIssuedDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.complaintIssuedDate.getTime())) + " from Complaint Issued Date", null);
        } else if(newCaseInformation.complaintIssuedDate != null && oldCaseInformation.complaintIssuedDate == null) {
            Activity.addActivty("Set Complaint Issued Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.complaintIssuedDate.getTime())), null);
        } else if(newCaseInformation.complaintIssuedDate != null && oldCaseInformation.complaintIssuedDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.complaintIssuedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.complaintIssuedDate.getTime()))))
                Activity.addActivty("Changed Complaint Issued Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.complaintIssuedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.complaintIssuedDate.getTime())), null);
        }
        
        //hearingDate
        if(newCaseInformation.hearingDate == null && oldCaseInformation.hearingDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.hearingDate.getTime())) + " from Hearing Date", null);
        } else if(newCaseInformation.hearingDate != null && oldCaseInformation.hearingDate == null) {
            Activity.addActivty("Set Hearing Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.hearingDate.getTime())), null);
        } else if(newCaseInformation.hearingDate != null && oldCaseInformation.hearingDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.hearingDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.hearingDate.getTime()))))
                Activity.addActivty("Changed Hearing Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.hearingDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.hearingDate.getTime())), null);
        }
        
        //proposedRecIssuedDate
        if(newCaseInformation.proposedRecIssuedDate == null && oldCaseInformation.proposedRecIssuedDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.proposedRecIssuedDate.getTime())) + " from Proposed Rec Issued Date", null);
        } else if(newCaseInformation.proposedRecIssuedDate != null && oldCaseInformation.proposedRecIssuedDate == null) {
            Activity.addActivty("Set Proposed Rec Issued Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.proposedRecIssuedDate.getTime())), null);
        } else if(newCaseInformation.proposedRecIssuedDate != null && oldCaseInformation.proposedRecIssuedDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.proposedRecIssuedDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.proposedRecIssuedDate.getTime()))))
                Activity.addActivty("Changed Proposed Rec Issued Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.proposedRecIssuedDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.proposedRecIssuedDate.getTime())), null);
        }
        
        //responseFilingDate
        if(newCaseInformation.responseFilingDate == null && oldCaseInformation.responseFilingDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.responseFilingDate.getTime())) + " from Response Filing Date", null);
        } else if(newCaseInformation.responseFilingDate != null && oldCaseInformation.responseFilingDate == null) {
            Activity.addActivty("Set Response Filing Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.responseFilingDate.getTime())), null);
        } else if(newCaseInformation.responseFilingDate != null && oldCaseInformation.responseFilingDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.responseFilingDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.responseFilingDate.getTime()))))
                Activity.addActivty("Changed Response Filing Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.responseFilingDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.responseFilingDate.getTime())), null);
        }
        
        //issuanceOfOptionOrDirectiveDate
        if(newCaseInformation.IssuanceOfOptionOrDirectiveDate == null && oldCaseInformation.IssuanceOfOptionOrDirectiveDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.IssuanceOfOptionOrDirectiveDate.getTime())) + " from Issuance Of Option Or Directive Date", null);
        } else if(newCaseInformation.IssuanceOfOptionOrDirectiveDate != null && oldCaseInformation.IssuanceOfOptionOrDirectiveDate == null) {
            Activity.addActivty("Set Issuance Of Option Or Directive Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.IssuanceOfOptionOrDirectiveDate.getTime())), null);
        } else if(newCaseInformation.IssuanceOfOptionOrDirectiveDate != null && oldCaseInformation.IssuanceOfOptionOrDirectiveDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.IssuanceOfOptionOrDirectiveDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.IssuanceOfOptionOrDirectiveDate.getTime()))))
                Activity.addActivty("Changed Issuance Of Option Or Directive Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.IssuanceOfOptionOrDirectiveDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.IssuanceOfOptionOrDirectiveDate.getTime())), null);
        }
        
        //finalResult
        if(newCaseInformation.FinalResult == null && oldCaseInformation.FinalResult != null) {
            Activity.addActivty("Removed " + oldCaseInformation.FinalResult + " from Final Result", null);
        } else if(newCaseInformation.FinalResult != null && oldCaseInformation.FinalResult == null) {
            Activity.addActivty("Set Final Result to " + newCaseInformation.FinalResult, null);
        } else if(newCaseInformation.FinalResult != null && oldCaseInformation.FinalResult != null) {
            if(!newCaseInformation.FinalResult.equals(oldCaseInformation.FinalResult)) 
                Activity.addActivty("Changed Final Result from " + oldCaseInformation.FinalResult + " to " + newCaseInformation.FinalResult, null);
        }
        
        //opinion
        if(newCaseInformation.opinion == null && oldCaseInformation.opinion != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.opinion.getTime())) + " from Opinion Date", null);
        } else if(newCaseInformation.opinion != null && oldCaseInformation.opinion == null) {
            Activity.addActivty("Set Opinion Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.opinion.getTime())), null);
        } else if(newCaseInformation.opinion != null && oldCaseInformation.opinion != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.opinion.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.opinion.getTime()))))
                Activity.addActivty("Changed Opinion Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.opinion.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.opinion.getTime())), null);
        }
        
        //companionCases
        if(newCaseInformation.companionCases == null && oldCaseInformation.companionCases != null) {
            Activity.addActivty("Removed " + oldCaseInformation.companionCases + " from Companion Case", null);
        } else if(newCaseInformation.companionCases != null && oldCaseInformation.companionCases == null) {
            Activity.addActivty("Set Companion Case to " + newCaseInformation.companionCases, null);
        } else if(newCaseInformation.companionCases != null && oldCaseInformation.companionCases != null) {
            if(!newCaseInformation.companionCases.equals(oldCaseInformation.companionCases)) 
                Activity.addActivty("Changed Companion Case from " + oldCaseInformation.companionCases + " to " + newCaseInformation.companionCases, null);
        }
    }
    
    public static HearingCase loadHearingCaseInformation() {
        HearingCase hearingCase = new HearingCase();
        
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();
            
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
                    + " exceptionFilingDate,"
                    + " boardActionDate,"
                    + " otherAction,"
                    + " aljID,"
                    + " complaintIssuedDate,"
                    + " hearingDate,"
                    + " proposedRecIssuedDate,"
                    + " responseFilingDate,"
                    + " issuanceOfOptionOrDirectiveDate,"
                    + " finalResult,"
                    + " opinion,"
                    + " companionCases"
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
                
                hearingCase.boardActionDate = rs.getTimestamp("boardActionDate");
                hearingCase.otherAction = rs.getString("otherAction");
                hearingCase.aljID = rs.getInt("aljID");
                hearingCase.complaintIssuedDate = rs.getTimestamp("complaintIssuedDate");
                hearingCase.hearingDate = rs.getTimestamp("hearingDate");
                hearingCase.proposedRecIssuedDate = rs.getTimestamp("proposedRecIssuedDate");
                hearingCase.responseFilingDate = rs.getTimestamp("responseFilingDate");
                hearingCase.IssuanceOfOptionOrDirectiveDate = rs.getTimestamp("IssuanceOfOptionOrDirectiveDate");
                hearingCase.FinalResult = rs.getString("FinalResult");
                hearingCase.opinion = rs.getTimestamp("opinion");
                hearingCase.companionCases = rs.getString("companionCases");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadHearingCaseInformation();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return hearingCase;
    }
    
    public static String getALJemail() {
        String email = "";
        
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getALJemail();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return email;
    }
    
    public static boolean validateCaseNumber(String fullCaseNumber) {
        String[] caseNumberParts = fullCaseNumber.split("-");
        boolean valid = false;
        
        Statement stmt = null;
        
        if(caseNumberParts.length != 4) {
            return false;
        }
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select Count(*) As results"
                    + " from HearingCase"
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
}
