package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
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
public class CMDSCase {
    
    public int id;
    public boolean active;
    public String caseYear;
    public String caseType; 
    public String caseMonth;
    public String caseNumber;
    public String note;
    public Timestamp openDate;
    public String groupNumber;
    public int aljID;
    public Timestamp closeDate;
    public Timestamp inventoryStatusDate;
    public String inventroyStatusLine;
    public String caseStatus;
    public String result;

    public int mediatorID;
    public String PBRBox;
    public String groupType;
    public String reclassCode;
    public Timestamp mailedRR;
    public Timestamp mailedBO;
    public Timestamp mailedPO1;
    public Timestamp mailedPO2;
    public Timestamp mailedPO3;
    public Timestamp mailedPO4;
    public Timestamp remailedRR;
    public Timestamp remailedBO;
    public Timestamp remailedPO1;
    public Timestamp remailedPO2;
    public Timestamp remailedPO3;
    public Timestamp remailedPO4;
    public Timestamp returnReceiptRR;
    public Timestamp returnReceiptBO;
    public Timestamp returnReceiptPO1;
    public Timestamp returnReceiptPO2;
    public Timestamp returnReceiptPO3;
    public Timestamp returnReceiptPO4;
    public Timestamp pullDateRR;
    public Timestamp pullDateBO;
    public Timestamp pullDatePO1;
    public Timestamp pullDatePO2;
    public Timestamp pullDatePO3;
    public Timestamp pullDatePO4;
    public Timestamp hearingCompletedDate;
    public Timestamp postHearingBriefsDue;
    
    /**
     * Load a list of the most recent 250 REP case numbers
     * @return list of rep case numbers
     */
    public static List loadCMDSCaseNumbers() {
        List caseNumberList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select TOP 250"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber"
                    + " from CMDSCase"
                    + " Order By openDate DESC";

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
                loadCMDSCaseNumbers();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseNumberList;
    }
    
    public static List getGroupNumberList() {
        List orgNameList = new ArrayList<>();
            
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select "
                    + " caseYear, caseType, caseMonth, caseNumber, groupNumber" 
                    + " from CMDSCase"
                    + " where groupNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, getGroupNumber());

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                if(!(caseNumberRS.getString("groupNumber").equals("") 
                        || caseNumberRS.getString("groupNumber").equals("00000000")
                        || caseNumberRS.getString("groupNumber") == null))
                {
                    orgNameList.add(caseNumberRS.getString("caseYear") + "-" 
                        + caseNumberRS.getString("caseType") + "-"
                        + caseNumberRS.getString("caseMonth") + "-"
                        + caseNumberRS.getString("caseNumber"));
                }
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getGroupNumberList();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return orgNameList;
    }
    
    public static String getGroupNumber() {
        String note = null;
            
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select groupNumber"
                    + " from CMDSCase"
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
            
            note = caseNumberRS.getString("groupNumber");

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getGroupNumber();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return note;
    }
    
    /**
     * Loads the notes that are related to the case
     * @return a stringified note
     */
    public static String loadNote() {
        String note = null;
            
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select Note"
                    + " from CMDSCase"
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
    
    /**
     * Updates the note that is related to the case number
     * @param note the new note value to be stored
     */
    public static void updateNote(String note) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
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
    
    public static void updateAllGroupInventoryStatusLines(String activty, Date date) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set inventoryStatusLine = ?,"
                    + " inventoryStatusDate = ?"
                    + " where groupNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, activty);
            preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
            preparedStatement.setString(3, getGroupNumber());

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateAllGroupInventoryStatusLines(activty, date);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static String getCaseStatus() {
        String caseStatus = "";
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " caseStatus"
                    + " from CMDSCase"
                    + " where caseYear = ? "
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            if(caseNumberRS.next()) {
                 caseStatus = caseNumberRS.getString("caseStatus");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getCaseStatus();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseStatus;
    }
    
    public static CMDSCase getRRPOPullDates() {
        
        CMDSCase cmds = new CMDSCase();
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " pullDateRR,"
                    + " pullDatePO1,"
                    + " pullDatePO2,"
                    + " pullDatePO3,"
                    + " pullDatePO4"
                    + " from CMDSCase"
                    + " where caseYear = ? "
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            if(caseNumberRS.next()) {
                cmds.pullDateRR = caseNumberRS.getTimestamp("pullDateRR");
                cmds.pullDatePO1 = caseNumberRS.getTimestamp("pullDatePO1");
                cmds.pullDatePO2 = caseNumberRS.getTimestamp("pullDatePO2");
                cmds.pullDatePO3 = caseNumberRS.getTimestamp("pullDatePO3");
                cmds.pullDatePO4 = caseNumberRS.getTimestamp("pullDatePO4");
            }
            stmt.close();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getRRPOPullDates();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return cmds;
    }
    
    public static CMDSCase getmailedPODates() {
        CMDSCase cmds = new CMDSCase();
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " mailedPO1, mailedPO2, mailedPO3, mailedPO4"
                    + " from CMDSCase"
                    + " where caseYear = ? "
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            if(caseNumberRS.next()) {
                cmds.mailedPO1 = caseNumberRS.getTimestamp("mailedPO1");
                cmds.mailedPO2 = caseNumberRS.getTimestamp("mailedPO2");
                cmds.mailedPO3 = caseNumberRS.getTimestamp("mailedPO3");
                cmds.mailedPO4 = caseNumberRS.getTimestamp("mailedPO4");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getmailedPODates();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return cmds;
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
    
    public static void updateCaseInventoryStatusLines(String activty, Date date) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set inventoryStatusLine = ?,"
                    + " inventoryStatusDate = ?"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, activty);
            preparedStatement.setTimestamp(2, new Timestamp(date.getTime()));
            preparedStatement.setString(3, Global.caseYear);
            preparedStatement.setString(4, Global.caseType);
            preparedStatement.setString(5, Global.caseMonth);
            preparedStatement.setString(6, Global.caseNumber);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateAllGroupInventoryStatusLines(activty, date);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseByTypeDEntry(String result, Timestamp mailBODate, String caseStatus, String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set result = ?,"
                    + " mailedBO = ?,"
                    + " caseStatus = ?"
                    + " where caseYear = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, result);
            preparedStatement.setTimestamp(2, mailBODate);
            preparedStatement.setString(3, caseStatus);
            preparedStatement.setString(4, caseNumber.split("-")[0]);
            preparedStatement.setString(5, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseByTypeDEntry(result, mailBODate, caseStatus, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseByTypeEEntry(CMDSCase poDate, String caseStatus, String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set mailedPO1 = ?,"
                    + " mailedPO2 = ?,"
                    + " mailedPO3 = ?,"
                    + " mailedPO4 = ?,"
                    + " caseStatus = ?"
                    + " where caseYear = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, poDate.mailedPO1);
            preparedStatement.setTimestamp(2, poDate.mailedPO2);
            preparedStatement.setTimestamp(3, poDate.mailedPO3);
            preparedStatement.setTimestamp(4, poDate.mailedPO4);
            preparedStatement.setString(5, caseStatus);
            preparedStatement.setString(6, caseNumber.split("-")[0]);
            preparedStatement.setString(7, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseByTypeEEntry(poDate, caseStatus, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseByTypeFEntry(CMDSCase pullDates, String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set pullDateRR = ?,"
                    + " pullDatePO1 = ?,"
                    + " pullDatePO2 = ?,"
                    + " pullDatePO3 = ?,"
                    + " pullDatePO4 = ?"
                    + " where caseYear = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, pullDates.pullDateRR);
            preparedStatement.setTimestamp(2, pullDates.pullDatePO1);
            preparedStatement.setTimestamp(3, pullDates.pullDatePO2);
            preparedStatement.setTimestamp(4, pullDates.pullDatePO3);
            preparedStatement.setTimestamp(5, pullDates.pullDatePO4);
            preparedStatement.setString(6, caseNumber.split("-")[0]);
            preparedStatement.setString(7, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseByTypeFEntry(pullDates, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseByTypeMEntry(String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set caseStatus = 'A'"
                    + " where caseYear = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber.split("-")[0]);
            preparedStatement.setString(2, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseByTypeMEntry(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseByTypeNEntry(String whichDate, String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set " + whichDate + " = ?"
                    + " where caseYear = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, null);
            preparedStatement.setString(2, caseNumber.split("-")[0]);
            preparedStatement.setString(3, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseByTypeNEntry(whichDate, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseByTypeQEntry(String whichGreenCardDate,
            String whichPullDate,
            Timestamp signedDate,
            Timestamp pullDate,
            String caseNumber) 
    {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set " + whichGreenCardDate + " = ?,"
                    + " " + whichPullDate + " = ?"
                    + " where caseYear = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, signedDate);
            preparedStatement.setTimestamp(2, pullDate);
            preparedStatement.setString(3, caseNumber.split("-")[0]);
            preparedStatement.setString(4, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseByTypeQEntry(whichGreenCardDate, whichPullDate, signedDate, pullDate, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseByTypeREntry(String closeDate,String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set closeDate = ?,"
                    + " caseStatus = 'C'"
                    + " where caseYear = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, new Timestamp(Global.mmddyyyy.parse(closeDate).getTime()));
            preparedStatement.setString(2, caseNumber.split("-")[0]);
            preparedStatement.setString(3, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseByTypeREntry(closeDate, caseNumber);
            } 
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void updateCaseByTypeUEntry(String pbrBox, String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set pbrBox = ?"
                    + " where caseYear = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, pbrBox);
            preparedStatement.setString(2, caseNumber.split("-")[0]);
            preparedStatement.setString(3, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseByTypeUEntry(pbrBox, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        } 
    }
    
    public static void updateCaseByTypeVEntry(String whichRemail,
            Timestamp remailedDate,
            Timestamp pullDate,
            String caseNumber) 
    {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase"
                    + " set " + whichRemail + " = ?,"
                    + " pullDateRR = ?"
                    + " where caseYear = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, remailedDate);
            preparedStatement.setTimestamp(2, pullDate);
            preparedStatement.setString(3, caseNumber.split("-")[0]);
            preparedStatement.setString(4, caseNumber.split("-")[3]);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCaseByTypeVEntry(whichRemail, remailedDate, pullDate, caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        } 
    }
    
    /**
     * Creates a new REPCase entry
     * @param caseYear
     * @param caseType
     * @param caseNumber the case number to be created 
     * @param caseMonth 
     */
    public static void createCase(String caseYear, String caseType, String caseMonth, String caseNumber) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert into CMDSCase (CaseYear, CaseType, CaseMonth, CaseNumber, openDate) Values (?,?,?,?,?)";

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
                        
                CaseNumber.updateNextCMDSCaseNumber(caseYear, String.valueOf(Integer.valueOf(caseNumber) + 1));
                Audit.addAuditEntry("Created Case: " + fullCaseNumber);
                Activity.addNewCaseActivty(fullCaseNumber, "Case was Filed and Started");
                Global.root.getcMDSHeaderPanel1().loadCases();
                Global.root.getcMDSHeaderPanel1().getjComboBox2().setSelectedItem(fullCaseNumber); 
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
    
    public static boolean checkIfFristCMDSCaseOfMonth(String year, String month) {
        boolean firstCase = false;
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " COUNT(*) AS CasesThisMonth"
                    + " from CMDSCase"
                    + " where caseYear = ? "
                    + " and caseMonth = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, year);
            preparedStatement.setString(2, month);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
           
            caseNumberRS.next();
            
            firstCase = caseNumberRS.getInt("CasesThisMonth") > 0;
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                checkIfFristCMDSCaseOfMonth(year, month);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return firstCase;
    }
    
    /**
     * Load information that is to be displayed in the header.  Dates are
     * formatted before being returned
     * @return a REPCase instance with the needed values
     */
    public static CMDSCase loadHeaderInformation() {
        CMDSCase cmds = null;
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select openDate,"
                    + " groupNumber,"
                    + " aljID,"
                    + " closeDate,"
                    + " inventoryStatusLine,"
                    + " inventoryStatusDate,"
                    + " caseStatus,"
                    + " result"
                    + " from CMDSCase where caseYear = ? AND"
                    + " caseType = ? AND"
                    + " caseMonth = ? AND"
                    + " caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseHeader = preparedStatement.executeQuery();
            
            if(caseHeader.next()) {
                cmds = new CMDSCase();
                cmds.openDate = caseHeader.getTimestamp("openDate");
                cmds.groupNumber =  caseHeader.getString("groupNumber");
                cmds.aljID = caseHeader.getInt("aljID");
                cmds.closeDate = caseHeader.getTimestamp("closeDate");
                cmds.inventroyStatusLine = caseHeader.getString("inventoryStatusLine");
                cmds.inventoryStatusDate = caseHeader.getTimestamp("inventoryStatusDate");
                cmds.caseStatus = caseHeader.getString("caseStatus");
                cmds.result = caseHeader.getString("result");
            }         
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadHeaderInformation();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return cmds;
    }
    
    public static CMDSCase loadCMDSCaseInformation() {
        CMDSCase cmds = null;
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " opendate,"
                    + " groupNumber,"
                    + " aljid,"
                    + " pbrBox,"
                    + " closeDate,"
                    + " caseStatus,"
                    + " result,"
                    + " mediatorID,"
                    + " groupType,"
                    + " reclassCode,"
                    + " mailedRR,"
                    + " mailedBO,"
                    + " mailedPO1,"
                    + " mailedPO2,"
                    + " mailedPO3,"
                    + " mailedPO4,"
                    + " remailedRR,"
                    + " remailedBO,"
                    + " remailedPO1,"
                    + " remailedPO2,"
                    + " remailedPO3,"
                    + " remailedPO4,"
                    + " returnReceiptRR,"
                    + " returnReceiptBO,"
                    + " returnReceiptPO1,"
                    + " returnReceiptPO2,"
                    + " returnReceiptPO3,"
                    + " returnReceiptPO4,"
                    + " pullDateRR,"
                    + " pullDateBO,"
                    + " pullDatePO1,"
                    + " pullDatePO2,"
                    + " pullDatePO3,"
                    + " pullDatePO4,"
                    + " hearingCompletedDate,"
                    + " postHearingDriefsDue"
                    + " from CMDSCase where caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseInformation = preparedStatement.executeQuery();
            
            if(caseInformation.next()) {
                cmds = new CMDSCase();
                cmds.caseYear = caseInformation.getString("caseYear");
                cmds.caseType = caseInformation.getString("caseType");
                cmds.caseMonth = caseInformation.getString("caseMonth");
                cmds.caseNumber = caseInformation.getString("caseNumber");
                cmds.aljID = caseInformation.getInt("aljID");
                cmds.openDate = caseInformation.getTimestamp("openDate");
                cmds.closeDate = caseInformation.getTimestamp("closeDate");
                cmds.groupNumber = caseInformation.getString("groupNumber");
                cmds.mediatorID = caseInformation.getInt("mediatorID");
                cmds.PBRBox = caseInformation.getString("pbrBox");
                cmds.groupType = caseInformation.getString("groupType");
                cmds.caseStatus = caseInformation.getString("caseStatus");
                cmds.reclassCode = caseInformation.getString("reclassCode");
                cmds.result = caseInformation.getString("result");
                
                cmds.mailedRR = caseInformation.getTimestamp("mailedRR");
                cmds.mailedBO = caseInformation.getTimestamp("mailedBO");
                cmds.mailedPO1 = caseInformation.getTimestamp("mailedPO1");
                cmds.mailedPO2 = caseInformation.getTimestamp("mailedPO2");
                cmds.mailedPO3 = caseInformation.getTimestamp("mailedPO3");
                cmds.mailedPO4 = caseInformation.getTimestamp("mailedPO4");
                
                cmds.remailedRR = caseInformation.getTimestamp("remailedRR");
                cmds.remailedBO = caseInformation.getTimestamp("remailedBO");
                cmds.remailedPO1 = caseInformation.getTimestamp("remailedPO1");
                cmds.remailedPO2 = caseInformation.getTimestamp("remailedPO2");
                cmds.remailedPO3 = caseInformation.getTimestamp("remailedPO3");
                cmds.remailedPO4 = caseInformation.getTimestamp("remailedPO4");
                
                cmds.returnReceiptRR = caseInformation.getTimestamp("returnReceiptRR");
                cmds.returnReceiptBO = caseInformation.getTimestamp("returnReceiptBO");
                cmds.returnReceiptPO1 = caseInformation.getTimestamp("returnReceiptPO1");
                cmds.returnReceiptPO2 = caseInformation.getTimestamp("returnReceiptPO2");
                cmds.returnReceiptPO3 = caseInformation.getTimestamp("returnReceiptPO3");
                cmds.returnReceiptPO4 = caseInformation.getTimestamp("returnReceiptPO4");
                
                cmds.pullDateRR = caseInformation.getTimestamp("pullDateRR");
                cmds.pullDateBO = caseInformation.getTimestamp("pullDateBO");
                cmds.pullDatePO1 = caseInformation.getTimestamp("pullDatePO1");
                cmds.pullDatePO2 = caseInformation.getTimestamp("pullDatePO2");
                cmds.pullDatePO3 = caseInformation.getTimestamp("pullDatePO3");
                cmds.pullDatePO4 = caseInformation.getTimestamp("pullDatePO4");
                
                cmds.hearingCompletedDate = caseInformation.getTimestamp("hearingCompletedDate");
                cmds.postHearingBriefsDue = caseInformation.getTimestamp("postHearingDriefsDue");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadCMDSCaseInformation();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return cmds;
    }
    
    public static void updateCMDSInformation(CMDSCase newCaseInformation, CMDSCase caseInformation) {
        CMDSCase rep = null;
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update CMDSCase set"
                    + " opendate = ?,"
                    + " groupNumber = ?,"
                    + " aljid = ?,"
                    + " pbrBox = ?,"
                    + " closeDate = ?,"
                    + " caseStatus = ?,"
                    + " result = ?,"
                    + " mediatorID = ?,"
                    + " groupType = ?,"
                    + " reclassCode = ?,"
                    + " mailedRR = ?,"
                    + " mailedBO = ?,"
                    + " mailedPO1 = ?,"
                    + " mailedPO2 = ?,"
                    + " mailedPO3 = ?,"
                    + " mailedPO4 = ?,"
                    + " remailedRR = ?,"
                    + " remailedBO = ?,"
                    + " remailedPO1 = ?,"
                    + " remailedPO2 = ?,"
                    + " remailedPO3 = ?,"
                    + " remailedPO4 = ?,"
                    + " returnReceiptRR = ?,"
                    + " returnReceiptBO = ?,"
                    + " returnReceiptPO1 = ?,"
                    + " returnReceiptPO2 = ?,"
                    + " returnReceiptPO3 = ?,"
                    + " returnReceiptPO4 = ?,"
                    + " pullDateRR = ?,"
                    + " pullDateBO = ?,"
                    + " pullDatePO1 = ?,"
                    + " pullDatePO2 = ?,"
                    + " pullDatePO3 = ?,"
                    + " pullDatePO4 = ?,"
                    + " hearingCompletedDate = ?,"
                    + " postHearingDriefsDue = ?"
                    + " from CMDSCase where"
                    + " caseYear = ? "
                    + " AND caseType = ? "
                    + " AND caseMonth = ? "
                    + " AND caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, newCaseInformation.openDate);
            preparedStatement.setString(2, newCaseInformation.groupNumber);
            preparedStatement.setInt(3, newCaseInformation.aljID);
            preparedStatement.setString(4, newCaseInformation.PBRBox);
            preparedStatement.setTimestamp(5, newCaseInformation.closeDate);
            preparedStatement.setString(6, newCaseInformation.caseStatus);
            preparedStatement.setString(7, newCaseInformation.result);
            preparedStatement.setInt(8, newCaseInformation.mediatorID);
            preparedStatement.setString(9, newCaseInformation.groupType);
            preparedStatement.setString(10, newCaseInformation.reclassCode);
            preparedStatement.setTimestamp(11, newCaseInformation.mailedRR);
            preparedStatement.setTimestamp(12, newCaseInformation.mailedBO);
            preparedStatement.setTimestamp(13, newCaseInformation.mailedPO1);
            preparedStatement.setTimestamp(14, newCaseInformation.mailedPO2);
            preparedStatement.setTimestamp(15, newCaseInformation.mailedPO3);
            preparedStatement.setTimestamp(16, newCaseInformation.mailedPO4);
            preparedStatement.setTimestamp(17, newCaseInformation.remailedRR);
            preparedStatement.setTimestamp(18, newCaseInformation.remailedBO);
            preparedStatement.setTimestamp(19, newCaseInformation.remailedPO1);
            preparedStatement.setTimestamp(20, newCaseInformation.remailedPO2);
            preparedStatement.setTimestamp(21, newCaseInformation.remailedPO3);
            preparedStatement.setTimestamp(22, newCaseInformation.remailedPO4);
            preparedStatement.setTimestamp(23, newCaseInformation.returnReceiptRR);
            preparedStatement.setTimestamp(24, newCaseInformation.returnReceiptBO);
            preparedStatement.setTimestamp(25, newCaseInformation.returnReceiptPO1);
            preparedStatement.setTimestamp(26, newCaseInformation.returnReceiptPO2);
            preparedStatement.setTimestamp(27, newCaseInformation.returnReceiptPO3);
            preparedStatement.setTimestamp(28, newCaseInformation.returnReceiptPO4);
            preparedStatement.setTimestamp(29, newCaseInformation.pullDateRR);
            preparedStatement.setTimestamp(30, newCaseInformation.pullDateBO);
            preparedStatement.setTimestamp(31, newCaseInformation.pullDatePO1);
            preparedStatement.setTimestamp(32, newCaseInformation.pullDatePO2);
            preparedStatement.setTimestamp(33, newCaseInformation.pullDatePO3);
            preparedStatement.setTimestamp(34, newCaseInformation.pullDatePO4);
            preparedStatement.setTimestamp(35, newCaseInformation.hearingCompletedDate);
            preparedStatement.setTimestamp(36, newCaseInformation.postHearingBriefsDue);
            preparedStatement.setString(37, Global.caseYear);
            preparedStatement.setString(38, Global.caseType);
            preparedStatement.setString(39, Global.caseMonth);
            preparedStatement.setString(40, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                CMDSCaseSearchData.updateCaseEntryFromCaseInformation(newCaseInformation.openDate, User.getNameByID(newCaseInformation.aljID));
                detailedCaseInformationSaveInformation(newCaseInformation, caseInformation);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCMDSInformation(newCaseInformation, caseInformation);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    private static void detailedCaseInformationSaveInformation(CMDSCase newCaseInformation, CMDSCase oldCaseInformation) {
        //opendate
        if(newCaseInformation.openDate == null && oldCaseInformation.openDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.openDate.getTime())) + " from Open Date", null);
        } else if(newCaseInformation.openDate != null && oldCaseInformation.openDate == null) {
            Activity.addActivty("Set Open Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.openDate.getTime())), null);
        } else if(newCaseInformation.openDate != null && oldCaseInformation.openDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.openDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.openDate.getTime()))))
                Activity.addActivty("Changed Open Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.openDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.openDate.getTime())), null);
        }

        //groupNumber
        if(newCaseInformation.groupNumber == null && oldCaseInformation.groupNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.groupNumber + " from Group Number", null);
        } else if(newCaseInformation.groupNumber != null && oldCaseInformation.groupNumber == null) {
            Activity.addActivty("Set Group Number to " + newCaseInformation.groupNumber, null);
        } else if(newCaseInformation.groupNumber != null && oldCaseInformation.groupNumber != null) {
            if(!newCaseInformation.groupNumber.equals(oldCaseInformation.groupNumber)) 
                Activity.addActivty("Changed Group Number from " + oldCaseInformation.groupNumber + " to " + newCaseInformation.groupNumber, null);
        }
        
        //aljid
        if(newCaseInformation.aljID == 0 && oldCaseInformation.aljID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.aljID) + " from ALJ", null);
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID == 0) {
            Activity.addActivty("Set ALJ to " + User.getNameByID(newCaseInformation.aljID), null);
        } else if(newCaseInformation.aljID != 0 && oldCaseInformation.aljID != 0) {
            if(newCaseInformation.aljID != oldCaseInformation.aljID) 
                Activity.addActivty("Changed ALJ from " + User.getNameByID(oldCaseInformation.aljID) + " to " + User.getNameByID(newCaseInformation.aljID), null);
        }
        
        //pbrBox
        if(newCaseInformation.PBRBox == null && oldCaseInformation.PBRBox != null) {
            Activity.addActivty("Removed " + oldCaseInformation.PBRBox + " from PBR Box", null);
        } else if(newCaseInformation.PBRBox != null && oldCaseInformation.PBRBox == null) {
            Activity.addActivty("Set PBR Box to " + newCaseInformation.PBRBox, null);
        } else if(newCaseInformation.PBRBox != null && oldCaseInformation.PBRBox != null) {
            if(!newCaseInformation.PBRBox.equals(oldCaseInformation.PBRBox)) 
                Activity.addActivty("Changed PBR Box from " + oldCaseInformation.PBRBox + " to " + newCaseInformation.PBRBox, null);
        }
        
        //closeDate
        if(newCaseInformation.closeDate == null && oldCaseInformation.closeDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.closeDate.getTime())) + " from Close Date", null);
        } else if(newCaseInformation.closeDate != null && oldCaseInformation.closeDate == null) {
            Activity.addActivty("Set Close Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.closeDate.getTime())), null);
        } else if(newCaseInformation.closeDate != null && oldCaseInformation.closeDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.closeDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.closeDate.getTime()))))
                Activity.addActivty("Changed Close Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.closeDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.closeDate.getTime())), null);
        }
        
        //caseStatus
        if(newCaseInformation.caseStatus == null && oldCaseInformation.caseStatus != null) {
            Activity.addActivty("Removed " + oldCaseInformation.caseStatus + " from Case Status", null);
        } else if(newCaseInformation.caseStatus != null && oldCaseInformation.caseStatus == null) {
            Activity.addActivty("Set Case Status to " + newCaseInformation.caseStatus, null);
        } else if(newCaseInformation.caseStatus != null && oldCaseInformation.caseStatus != null) {
            if(!newCaseInformation.caseStatus.equals(oldCaseInformation.caseStatus)) 
                Activity.addActivty("Changed Case Status from " + oldCaseInformation.caseStatus + " to " + newCaseInformation.caseStatus, null);
        }
        
        //result
        if(newCaseInformation.result == null && oldCaseInformation.result != null) {
            Activity.addActivty("Removed " + oldCaseInformation.result + " from Result", null);
        } else if(newCaseInformation.result != null && oldCaseInformation.result == null) {
            Activity.addActivty("Set Result to " + newCaseInformation.result, null);
        } else if(newCaseInformation.result != null && oldCaseInformation.result != null) {
            if(!newCaseInformation.result.equals(oldCaseInformation.result)) 
                Activity.addActivty("Changed Result from " + oldCaseInformation.result + " to " + newCaseInformation.result, null);
        }
        
        //mediatorID
        if(newCaseInformation.mediatorID == 0 && oldCaseInformation.mediatorID != 0) {
            Activity.addActivty("Removed " + User.getNameByID(oldCaseInformation.mediatorID) + " from Mediator", null);
        } else if(newCaseInformation.mediatorID != 0 && oldCaseInformation.mediatorID == 0) {
            Activity.addActivty("Set Mediator to " + User.getNameByID(newCaseInformation.mediatorID), null);
        } else if(newCaseInformation.mediatorID != 0 && oldCaseInformation.mediatorID != 0) {
            if(newCaseInformation.mediatorID != oldCaseInformation.mediatorID) 
                Activity.addActivty("Changed Mediator from " + User.getNameByID(oldCaseInformation.mediatorID) + " to " + User.getNameByID(newCaseInformation.mediatorID), null);
        }
        
        //groupType
        if(newCaseInformation.groupType == null && oldCaseInformation.groupType != null) {
            Activity.addActivty("Removed " + oldCaseInformation.groupType + " from Group Type", null);
        } else if(newCaseInformation.groupType != null && oldCaseInformation.groupType == null) {
            Activity.addActivty("Set Group Type to " + newCaseInformation.groupType, null);
        } else if(newCaseInformation.groupType != null && oldCaseInformation.groupType != null) {
            if(!newCaseInformation.groupType.equals(oldCaseInformation.groupType)) 
                Activity.addActivty("Changed Group Type from " + oldCaseInformation.groupType + " to " + newCaseInformation.groupType, null);
        }
        
        //reclassCode
        if(newCaseInformation.reclassCode == null && oldCaseInformation.reclassCode != null) {
            Activity.addActivty("Removed " + oldCaseInformation.reclassCode + " from Reclass Code", null);
        } else if(newCaseInformation.reclassCode != null && oldCaseInformation.reclassCode == null) {
            Activity.addActivty("Set Reclass Code to " + newCaseInformation.reclassCode, null);
        } else if(newCaseInformation.reclassCode != null && oldCaseInformation.reclassCode != null) {
            if(!newCaseInformation.reclassCode.equals(oldCaseInformation.reclassCode)) 
                Activity.addActivty("Changed Reclass Code from " + oldCaseInformation.reclassCode + " to " + newCaseInformation.reclassCode, null);
        }
    }
 
    public static List<String> loadRelatedCases() {
        List<String> caseNumberList = new ArrayList<>();
           
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "Select caseYear, caseType, caseMonth, caseNumber from CMDSCase  where openDate between DateAdd(DD,-7,GETDATE()) and GETDATE() Order By caseYear DESC, caseNumber DESC";

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
    
    public static boolean validateCase(String caseNumber) {
        boolean validCase = false;
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "Select COUNT(*) AS CASECOUNT from CMDSCase"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber.split("-")[0]);
            preparedStatement.setString(2, caseNumber.split("-")[1]);
            preparedStatement.setString(3, caseNumber.split("-")[2]);
            preparedStatement.setString(4, caseNumber.split("-")[3]);
            
            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                if(caseNumberRS.getInt("CASECOUNT") > 0) {
                    validCase = true;
                }
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                validateCase(caseNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return validCase;
    }
    
    public static void updateCMDSGroupNumber(String caseNumber, String groupNumber) {
        boolean validCase = false;
        
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "Update CMDSCase Set groupNumber = ?"
                    + " where caseYear = ?"
                    + " and caseType = ?"
                    + " and caseMonth = ?"
                    + " and caseNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, groupNumber);
            preparedStatement.setString(2, caseNumber.split("-")[0]);
            preparedStatement.setString(3, caseNumber.split("-")[1]);
            preparedStatement.setString(4, caseNumber.split("-")[2]);
            preparedStatement.setString(5, caseNumber.split("-")[3]);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCMDSGroupNumber(caseNumber, groupNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static List<String> loadGroupCases(String groupNumber) {
        List<String> caseNumberList = new ArrayList<>();
        
        Statement stmt = null;
            
        try {
            stmt = Database.connectToDB().createStatement();
            
            String sql = "Select caseYear, caseType, caseMonth, caseNumber from CMDSCase  where groupNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, groupNumber);
            
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
                loadGroupCases(groupNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return caseNumberList;
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
                    + " from CMDSCase"
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
