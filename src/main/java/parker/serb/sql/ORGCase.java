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
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class ORGCase {

    
    
    public int id;
    public boolean active;
    public String orgName;
    public String orgNumber; 
    public String fiscalYearEnding; 
    public String filingDueDate; 
    public Timestamp annualReport; 
    public Timestamp financialReport; 
    public Timestamp registrationReport; 
    public Timestamp constructionAndByLaws; 
    public boolean filedByParent; 
    
    public String alsoKnownAs;
    public String orgType;
    public String orgPhone1;
    public String orgPhone2;
    public String orgFax;
    public String employerID;
    public String orgAddress1;
    public String orgAddress2;
    public String orgCity;
    public String orgState;
    public String orgZip;
    public String orgCounty;
    public String orgEmail;
    
    public String lastNotification;
    public boolean deemedCertified;
    public boolean boardCertified;
    public boolean valid;
    public String parent1;
    public String parent2;
    public String outsideCase;
    public Timestamp dateFiled;
    public Timestamp certifiedDate;
    public Timestamp registrationLetterSent;
    public Timestamp extensionDate;
    
    public static void createNewOrg(String number, String name) {
        try {
            
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert Into ORGCase (ORGName, ORGNumber, ORGType) Values (?,?,'Local')";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, number);
            

            int success = preparedStatement.executeUpdate();
            
            if (success == 1) {
                CaseNumber.updateOrgCaseNumber(number);
                Global.caseType = "ORG";
                Global.caseNumber = number;
                Activity.addActivty("Organization Created", null);
                Audit.addAuditEntry("Created New Organization: " + name);
                Global.root.getoRGHeaderPanel2().loadCases();
                Global.root.getoRGHeaderPanel2().getjComboBox2().setSelectedItem(name);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static List loadORGNames() {
        
        List orgNameList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select "
                    + " orgName" 
                    + " from ORGCase"
                    + " Order By orgName ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                orgNameList.add(caseNumberRS.getString("orgName") != null ? caseNumberRS.getString("orgName") : "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orgNameList;
    }
    
    public static List loadORGNamesAndIDs() {
        
        List orgNameList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select "
                    + " orgName, orgNumber" 
                    + " from ORGCase"
                    + " Order By orgName ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                ORGCase org = new ORGCase();
                org.orgName = caseNumberRS.getString("orgName") != null ? caseNumberRS.getString("orgName") : "";
                org.orgNumber = caseNumberRS.getString("orgNumber") != null ? caseNumberRS.getString("orgNumber") : "";
                orgNameList.add(org);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orgNameList;
    }
    
    public static List getCaseSearchData() {
        
        List orgNameList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select "
                    + " orgName, orgNumber, alsoKnownAs" 
                    + " from ORGCase"
                    + " Order By orgName ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                ORGCase org = new ORGCase();
                org.orgName = caseNumberRS.getString("orgName") != null ? caseNumberRS.getString("orgName") : "";
                org.orgNumber = caseNumberRS.getString("orgNumber") != null ? caseNumberRS.getString("orgNumber") : "";
                org.alsoKnownAs = caseNumberRS.getString("alsoKnownAs") != null ? caseNumberRS.getString("alsoKnownAs") : "";
                orgNameList.add(org);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orgNameList;
    }
    
    /**
     * Loads the notes that are related to the case
     * @return a stringified note
     */
    public static String loadNote() {
        String note = null;
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select Note"
                    + " from ORGCase"
                    + " where orgNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            caseNumberRS.next();
            
            note = caseNumberRS.getString("note");

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return note;
    }
    
    public static String getORGName() {
        String name = null;
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select orgName"
                    + " from ORGCase"
                    + " where orgNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            caseNumberRS.next();
            
            name = caseNumberRS.getString("orgName");

        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return name;
    }
//    
    /**
     * Updates the note that is related to the case number
     * @param note the new note value to be stored
     */
    public static void updateNote(String note) {
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update ORGCase"
                    + " set note = ?"
                    + " where orgNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, note);
            preparedStatement.setString(2, Global.caseNumber);

            preparedStatement.executeUpdate();
            
            Audit.addAuditEntry("Updated Note for " + Global.caseNumber);
            Activity.addActivty("Updated Note", null);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Load information that is to be displayed in the header.  Dates are
     * formatted before being returned
     * @return a REPCase instance with the needed values
     */
    public static ORGCase loadHeaderInformation() {
        ORGCase org = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " orgNumber,"
                    + " fiscalYearEnding,"
                    + " filingDueDate,"
                    + " annualReport,"
                    + " financialReport,"
                    + " registrationReport,"
                    + " constructionAndByLaws,"
                    + " filedByParent"
                    + " from ORGCase where orgName = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

            ResultSet caseHeader = preparedStatement.executeQuery();
            
            if(caseHeader.next()) {
                org = new ORGCase();
                org.orgNumber = caseHeader.getString("orgNumber");
                org.fiscalYearEnding = caseHeader.getString("fiscalYearEnding");
                org.filingDueDate = caseHeader.getString("filingDueDate");
                org.annualReport = caseHeader.getTimestamp("annualReport");
                org.financialReport = caseHeader.getTimestamp("financialReport");
                org.registrationReport = caseHeader.getTimestamp("registrationReport");
                org.constructionAndByLaws = caseHeader.getTimestamp("constructionAndByLaws");
                org.filedByParent = caseHeader.getBoolean("filedByParent");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        }
        return org;
    }
    
    public static ORGCase loadORGInformation() {
        ORGCase org = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " orgName,"
                    + " alsoKnownAs,"
                    + " orgNumber,"
                    + " orgType,"
                    + " orgPhone1,"
                    + " orgPhone2,"
                    + " orgFax,"
                    + " employerID,"
                    + " orgAddress1,"
                    + " orgAddress2,"
                    + " orgCity,"
                    + " orgState,"
                    + " orgZip,"
                    + " orgCounty,"
                    + " orgEmail,"
                    + " fiscalYearEnding,"
                    + " annualReport,"
                    + " financialReport,"
                    + " registrationReport,"
                    + " constructionAndByLaws,"
                    + " lastNotification,"
                    + " deemedCertified,"
                    + " boardCertified,"
                    + " filedByParent,"
                    + " valid,"
                    + " parent1,"
                    + " parent2,"
                    + " outsideCase,"
                    + " filingDueDate,"
                    + " dateFiled,"
                    + " registrationLetterSent,"
                    + " extensionDate"
                    + " from ORGCase where orgNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

            ResultSet caseInformation = preparedStatement.executeQuery();
            
            if(caseInformation.next()) {
                org = new ORGCase();
                org.orgName = caseInformation.getString("orgName");
                org.alsoKnownAs = caseInformation.getString("alsoKnownAs");
                org.orgNumber = caseInformation.getString("orgNumber");
                org.orgType = caseInformation.getString("orgType");
                org.orgPhone1 = caseInformation.getString("orgPhone1");
                org.orgPhone2 = caseInformation.getString("orgPhone2");
                org.orgFax = caseInformation.getString("orgFax");
                org.employerID = caseInformation.getString("employerID");
                org.orgAddress1 = caseInformation.getString("orgAddress1");
                org.orgAddress2 = caseInformation.getString("orgAddress2");
                org.orgCity = caseInformation.getString("orgCity");
                org.orgState = caseInformation.getString("orgState");
                org.orgZip = caseInformation.getString("orgZip");
                org.orgCounty = caseInformation.getString("orgCounty");
                org.orgEmail = caseInformation.getString("orgEmail");
                org.fiscalYearEnding = caseInformation.getString("fiscalYearEnding");
                org.annualReport = caseInformation.getTimestamp("annualReport");
                org.financialReport = caseInformation.getTimestamp("financialReport");
                org.registrationReport = caseInformation.getTimestamp("registrationReport");
                org.constructionAndByLaws = caseInformation.getTimestamp("constructionAndByLaws");
                org.lastNotification = caseInformation.getString("lastNotification");
                org.deemedCertified = caseInformation.getBoolean("deemedCertified");
                org.boardCertified = caseInformation.getBoolean("boardCertified");
                org.filedByParent = caseInformation.getBoolean("filedByParent");
                org.valid = caseInformation.getBoolean("valid");
                org.parent1 = caseInformation.getString("parent1");
                org.parent2 = caseInformation.getString("parent2");
                org.outsideCase = caseInformation.getString("outsideCase");
                org.filingDueDate = caseInformation.getString("filingDueDate");
                org.dateFiled = caseInformation.getTimestamp("dateFiled");
                org.registrationLetterSent = caseInformation.getTimestamp("registrationLetterSent");
                org.extensionDate = caseInformation.getTimestamp("extensionDate");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        }
        return org;
    }
    
    public static List<ORGCase> getOrgCasesAllLettersDefault(){
        List orgLettersList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM ORGCase WHERE "
                    + "registrationReport IS NULL AND "
                    + "DATEADD(day, 60, registrationLetterSent ) <= getDATE()";
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                ORGCase org = new ORGCase();
                org.orgName = rs.getString("orgName");
                org.alsoKnownAs = rs.getString("alsoKnownAs");
                org.orgNumber = rs.getString("orgNumber");
                org.orgType = rs.getString("orgType");
                org.orgPhone1 = rs.getString("orgPhone1");
                org.orgPhone2 = rs.getString("orgPhone2");
                org.orgFax = rs.getString("orgFax");
                org.employerID = rs.getString("employerID");
                org.orgAddress1 = rs.getString("orgAddress1");
                org.orgAddress2 = rs.getString("orgAddress2");
                org.orgCity = rs.getString("orgCity");
                org.orgState = rs.getString("orgState");
                org.orgZip = rs.getString("orgZip");
                org.orgCounty = rs.getString("orgCounty");
                org.orgEmail = rs.getString("orgEmail");
                org.fiscalYearEnding = rs.getString("fiscalYearEnding");
                org.annualReport = rs.getTimestamp("annualReport");
                org.financialReport = rs.getTimestamp("financialReport");
                org.registrationReport = rs.getTimestamp("registrationReport");
                org.constructionAndByLaws = rs.getTimestamp("constructionAndByLaws");
                org.lastNotification = rs.getString("lastNotification");
                org.deemedCertified = rs.getBoolean("deemedCertified");
                org.boardCertified = rs.getBoolean("boardCertified");
                org.filedByParent = rs.getBoolean("filedByParent");
                org.valid = rs.getBoolean("valid");
                org.parent1 = rs.getString("parent1");
                org.parent2 = rs.getString("parent2");
                org.outsideCase = rs.getString("outsideCase");
                org.filingDueDate = rs.getString("filingDueDate");
                org.dateFiled = rs.getTimestamp("dateFiled");
                org.registrationLetterSent = rs.getTimestamp("registrationLetterSent");
                org.extensionDate = rs.getTimestamp("extensionDate");
                orgLettersList.add(org);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orgLettersList;        
    }
    
    public static List<ORGCase> getOrgCasesAllLetters(String month, String date){
        List orgLettersList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM ORGCase WHERE "
                    + "Active = 1 AND Valid = 1 AND FiledByParent = 0 AND fiscalYearEnding = ? "
                    + "AND ( constructionAndByLaws = null OR registrationReport = null "
                    + "OR annualReport = null OR annualReport < ? OR financialReport = null "
                    + "OR financialReport < ?) Order By orgName ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, month);
            preparedStatement.setString(2, date);
            preparedStatement.setString(3, date);
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                ORGCase org = new ORGCase();
                org.orgName = rs.getString("orgName");
                org.alsoKnownAs = rs.getString("alsoKnownAs");
                org.orgNumber = rs.getString("orgNumber");
                org.orgType = rs.getString("orgType");
                org.orgPhone1 = rs.getString("orgPhone1");
                org.orgPhone2 = rs.getString("orgPhone2");
                org.orgFax = rs.getString("orgFax");
                org.employerID = rs.getString("employerID");
                org.orgAddress1 = rs.getString("orgAddress1");
                org.orgAddress2 = rs.getString("orgAddress2");
                org.orgCity = rs.getString("orgCity");
                org.orgState = rs.getString("orgState");
                org.orgZip = rs.getString("orgZip");
                org.orgCounty = rs.getString("orgCounty");
                org.orgEmail = rs.getString("orgEmail");
                org.fiscalYearEnding = rs.getString("fiscalYearEnding");
                org.annualReport = rs.getTimestamp("annualReport");
                org.financialReport = rs.getTimestamp("financialReport");
                org.registrationReport = rs.getTimestamp("registrationReport");
                org.constructionAndByLaws = rs.getTimestamp("constructionAndByLaws");
                org.lastNotification = rs.getString("lastNotification");
                org.deemedCertified = rs.getBoolean("deemedCertified");
                org.boardCertified = rs.getBoolean("boardCertified");
                org.filedByParent = rs.getBoolean("filedByParent");
                org.valid = rs.getBoolean("valid");
                org.parent1 = rs.getString("parent1");
                org.parent2 = rs.getString("parent2");
                org.outsideCase = rs.getString("outsideCase");
                org.filingDueDate = rs.getString("filingDueDate");
                org.dateFiled = rs.getTimestamp("dateFiled");
                org.registrationLetterSent = rs.getTimestamp("registrationLetterSent");
                org.extensionDate = rs.getTimestamp("extensionDate");
                orgLettersList.add(org);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orgLettersList;        
    }
    
    public static void updateORGInformation(ORGCase newCaseInformation, ORGCase caseInformation) {
        ORGCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();  
            
            String sql = "Update ORGCase Set"
                    + " orgName = ?,"
                    + " alsoKnownAs = ?,"
                    + " orgNumber = ?,"
                    + " orgType = ?,"
                    + " orgPhone1 = ? ,"
                    + " orgPhone2 = ?,"
                    + " orgFax = ?,"
                    + " employerID = ?,"
                    + " orgAddress1 = ?,"
                    + " orgAddress2 = ?,"
                    + " orgCity = ?,"
                    + " orgState = ?,"
                    + " orgZip = ?,"
                    + " orgCounty = ?,"
                    + " orgEmail = ?,"
                    + " fiscalYearEnding = ?,"
                    + " annualReport = ?,"
                    + " financialReport = ?,"
                    + " registrationReport = ?,"
                    + " constructionAndByLaws = ?,"
                    + " lastNotification = ?,"
                    + " deemedCertified = ?,"
                    + " boardCertified = ?,"
                    + " filedByParent = ?,"
                    + " valid = ?,"
                    + " parent1 = ?,"
                    + " parent2 = ?,"
                    + " outsideCase = ?,"
                    + " filingDueDate = ?,"
                    + " dateFiled = ?,"
                    + " registrationLetterSent = ?,"
                    + " extensionDate = ?"
                    + " from ORGCase where orgNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseInformation.orgName);
            preparedStatement.setString(2, newCaseInformation.alsoKnownAs);
            preparedStatement.setString(3, newCaseInformation.orgNumber);
            preparedStatement.setString(4, newCaseInformation.orgType);
            preparedStatement.setString(5, newCaseInformation.orgPhone1);
            preparedStatement.setString(6, newCaseInformation.orgPhone2);
            preparedStatement.setString(7, newCaseInformation.orgFax);
            preparedStatement.setString(8, newCaseInformation.employerID);
            preparedStatement.setString(9, newCaseInformation.orgAddress1);
            preparedStatement.setString(10, newCaseInformation.orgAddress2);
            preparedStatement.setString(11, newCaseInformation.orgCity);
            preparedStatement.setString(12, newCaseInformation.orgState);
            preparedStatement.setString(13, newCaseInformation.orgZip);
            preparedStatement.setString(14, newCaseInformation.orgCounty);
            preparedStatement.setString(15, newCaseInformation.orgEmail);
            preparedStatement.setString(16, newCaseInformation.fiscalYearEnding);
            preparedStatement.setTimestamp(17, newCaseInformation.annualReport);
            preparedStatement.setTimestamp(18, newCaseInformation.financialReport);
            preparedStatement.setTimestamp(19, newCaseInformation.registrationReport);
            preparedStatement.setTimestamp(20, newCaseInformation.constructionAndByLaws);
            preparedStatement.setString(21, newCaseInformation.lastNotification);
            preparedStatement.setBoolean(22, newCaseInformation.deemedCertified);
            preparedStatement.setBoolean(23, newCaseInformation.boardCertified);
            preparedStatement.setBoolean(24, newCaseInformation.filedByParent);
            preparedStatement.setBoolean(25, newCaseInformation.valid);
            preparedStatement.setString(26, newCaseInformation.parent1);
            preparedStatement.setString(27, newCaseInformation.parent2);
            preparedStatement.setString(28, newCaseInformation.outsideCase);
            preparedStatement.setString(29, newCaseInformation.filingDueDate);
            preparedStatement.setTimestamp(30, newCaseInformation.dateFiled);
            preparedStatement.setTimestamp(31, newCaseInformation.registrationLetterSent);
            preparedStatement.setTimestamp(32, newCaseInformation.extensionDate);
            preparedStatement.setString(33, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedSaveOrgInformation(newCaseInformation, caseInformation);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
        }
    }
    
    private static void detailedSaveOrgInformation(ORGCase newCaseInformation, ORGCase oldCaseInformation) {
        
        //orgName
        if(newCaseInformation.orgName == null && oldCaseInformation.orgName != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgName + " from Org Name", null);
        } else if(newCaseInformation.orgName != null && oldCaseInformation.orgName == null) {
            Activity.addActivty("Set Org Name to " + newCaseInformation.orgName, null);
        } else if(newCaseInformation.orgName != null && oldCaseInformation.orgName != null) {
            if(!oldCaseInformation.orgName.equals(newCaseInformation.orgName))
                Activity.addActivty("Changed Org Name from " + oldCaseInformation.orgName + " to " + newCaseInformation.orgName, null);
        }
        
        //alsoKnownAs
        if(newCaseInformation.alsoKnownAs == null && oldCaseInformation.alsoKnownAs != null) {
            Activity.addActivty("Removed " + oldCaseInformation.alsoKnownAs + " from Also Known As", null);
        } else if(newCaseInformation.alsoKnownAs != null && oldCaseInformation.alsoKnownAs == null) {
            Activity.addActivty("Set Also Known As to " + newCaseInformation.alsoKnownAs, null);
        } else if(newCaseInformation.alsoKnownAs != null && oldCaseInformation.alsoKnownAs != null) {
            if(!oldCaseInformation.alsoKnownAs.equals(newCaseInformation.alsoKnownAs))
                Activity.addActivty("Changed Also Known As from " + oldCaseInformation.alsoKnownAs + " to " + newCaseInformation.alsoKnownAs, null);
        }
        
        //orgNumber
        if(newCaseInformation.orgNumber == null && oldCaseInformation.orgNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgNumber + " from Org Number", null);
        } else if(newCaseInformation.orgNumber != null && oldCaseInformation.orgNumber == null) {
            Activity.addActivty("Set Org Number to " + newCaseInformation.orgNumber, null);
        } else if(newCaseInformation.orgNumber != null && oldCaseInformation.orgNumber != null) {
            if(!oldCaseInformation.orgNumber.equals(newCaseInformation.orgNumber))
                Activity.addActivty("Changed Org Number from " + oldCaseInformation.orgNumber + " to " + newCaseInformation.orgNumber, null);
        }
        
        //orgType
        if(newCaseInformation.orgType == null && oldCaseInformation.orgType != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgType + " from Org Type", null);
        } else if(newCaseInformation.orgType != null && oldCaseInformation.orgType == null) {
            Activity.addActivty("Set Org Type to " + newCaseInformation.orgType, null);
        } else if(newCaseInformation.orgType != null && oldCaseInformation.orgType != null) {
            if(!oldCaseInformation.orgType.equals(newCaseInformation.orgType))
                Activity.addActivty("Changed Org Type from " + oldCaseInformation.orgType + " to " + newCaseInformation.orgType, null);
        }
        
        //orgPhone1
        if(newCaseInformation.orgPhone1 == null && oldCaseInformation.orgPhone1 != null) {
            Activity.addActivty("Removed " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.orgPhone1) + " from Org Phone 1", null);
        } else if(newCaseInformation.orgPhone1 != null && oldCaseInformation.orgPhone1 == null) {
            Activity.addActivty("Set Org Phone 1 to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.orgPhone1), null);
        } else if(newCaseInformation.orgPhone1 != null && oldCaseInformation.orgPhone1 != null) {
            if(!oldCaseInformation.orgPhone1.equals(newCaseInformation.orgPhone1))
                Activity.addActivty("Changed Org Phone 1 from " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.orgPhone1) + " to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.orgPhone1), null);
        }
        
        //orgPhone2
        if(newCaseInformation.orgPhone2 == null && oldCaseInformation.orgPhone2 != null) {
            Activity.addActivty("Removed " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.orgPhone2) + " from Org Phone 2", null);
        } else if(newCaseInformation.orgPhone2 != null && oldCaseInformation.orgPhone2 == null) {
            Activity.addActivty("Set Org Phone 2 to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.orgPhone2), null);
        } else if(newCaseInformation.orgPhone2 != null && oldCaseInformation.orgPhone2 != null) {
            if(!oldCaseInformation.orgPhone2.equals(newCaseInformation.orgPhone2))
                Activity.addActivty("Changed Org Phone 2 from " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.orgPhone2) + " to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.orgPhone2), null);
        }
        
        //orgFax
        if(newCaseInformation.orgFax == null && oldCaseInformation.orgFax != null) {
            Activity.addActivty("Removed " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.orgFax) + " from Org Fax", null);
        } else if(newCaseInformation.orgFax != null && oldCaseInformation.orgFax == null) {
            Activity.addActivty("Set Org Fax to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.orgFax), null);
        } else if(newCaseInformation.orgFax != null && oldCaseInformation.orgFax != null) {
            if(!oldCaseInformation.orgFax.equals(newCaseInformation.orgFax))
                Activity.addActivty("Changed Org Fax from " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.orgFax) + " to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.orgFax), null);
        }
        
        //employerID
        if(newCaseInformation.employerID == null && oldCaseInformation.employerID != null) {
            Activity.addActivty("Removed " + oldCaseInformation.employerID + " from Employer ID", null);
        } else if(newCaseInformation.employerID != null && oldCaseInformation.employerID == null) {
            Activity.addActivty("Set Employer ID to " + newCaseInformation.employerID, null);
        } else if(newCaseInformation.employerID != null && oldCaseInformation.employerID != null) {
            if(!oldCaseInformation.employerID.equals(newCaseInformation.employerID))
                Activity.addActivty("Changed Employer ID from " + oldCaseInformation.employerID + " to " + newCaseInformation.employerID, null);
        }
        
        //orgAddress1
        if(newCaseInformation.orgAddress1 == null && oldCaseInformation.orgAddress1 != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgAddress1 + " from Org Address 1", null);
        } else if(newCaseInformation.orgAddress1 != null && oldCaseInformation.orgAddress1 == null) {
            Activity.addActivty("Set Org Address 1 to " + newCaseInformation.orgAddress1, null);
        } else if(newCaseInformation.orgAddress1 != null && oldCaseInformation.orgAddress1 != null) {
            if(!oldCaseInformation.orgAddress1.equals(newCaseInformation.orgAddress1))
                Activity.addActivty("Changed Org Address 1 from " + oldCaseInformation.orgAddress1 + " to " + newCaseInformation.orgAddress1, null);
        }
        
        //orgAddress2
        if(newCaseInformation.orgAddress2 == null && oldCaseInformation.orgAddress2 != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgAddress2 + " from Org Address 2", null);
        } else if(newCaseInformation.orgAddress2 != null && oldCaseInformation.orgAddress2 == null) {
            Activity.addActivty("Set Org Address 2 to " + newCaseInformation.orgAddress2, null);
        } else if(newCaseInformation.orgAddress2 != null && oldCaseInformation.orgAddress2 != null) {
            if(!oldCaseInformation.orgAddress2.equals(newCaseInformation.orgAddress2))
                Activity.addActivty("Changed Org Address 2 from " + oldCaseInformation.orgAddress2 + " to " + newCaseInformation.orgAddress2, null);
        }
        
        //orgCity
        if(newCaseInformation.orgCity == null && oldCaseInformation.orgCity != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgCity + " from Org City", null);
        } else if(newCaseInformation.orgCity != null && oldCaseInformation.orgCity == null) {
            Activity.addActivty("Set Org City to " + newCaseInformation.orgCity, null);
        } else if(newCaseInformation.orgCity != null && oldCaseInformation.orgCity != null) {
            if(!oldCaseInformation.orgCity.equals(newCaseInformation.orgCity))
                Activity.addActivty("Changed Org City from " + oldCaseInformation.orgCity + " to " + newCaseInformation.orgCity, null);
        }
        
        //orgState
        if(newCaseInformation.orgState == null && oldCaseInformation.orgState != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgState + " from Org State", null);
        } else if(newCaseInformation.orgState != null && oldCaseInformation.orgState == null) {
            Activity.addActivty("Set Org State to " + newCaseInformation.orgState, null);
        } else if(newCaseInformation.orgState != null && oldCaseInformation.orgState != null) {
            if(!oldCaseInformation.orgState.equals(newCaseInformation.orgState))
                Activity.addActivty("Changed Org State from " + oldCaseInformation.orgState + " to " + newCaseInformation.orgState, null);
        }
        
        //orgZip
        if(newCaseInformation.orgZip == null && oldCaseInformation.orgZip != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgZip + " from Org Zip", null);
        } else if(newCaseInformation.orgZip != null && oldCaseInformation.orgZip == null) {
            Activity.addActivty("Set Org Zip to " + newCaseInformation.orgZip, null);
        } else if(newCaseInformation.orgZip != null && oldCaseInformation.orgZip != null) {
            if(!oldCaseInformation.orgZip.equals(newCaseInformation.orgZip))
                Activity.addActivty("Changed Org Zip from " + oldCaseInformation.orgZip + " to " + newCaseInformation.orgZip, null);
        }
        
        //orgCounty
        if(newCaseInformation.orgCounty == null && oldCaseInformation.orgCounty != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgCounty + " from Org County", null);
        } else if(newCaseInformation.orgCounty != null && oldCaseInformation.orgCounty == null) {
            Activity.addActivty("Set Org County to " + newCaseInformation.orgCounty, null);
        } else if(newCaseInformation.orgCounty != null && oldCaseInformation.orgCounty != null) {
            if(!oldCaseInformation.orgCounty.equals(newCaseInformation.orgCounty))
                Activity.addActivty("Changed Org County from " + oldCaseInformation.orgCounty + " to " + newCaseInformation.orgCounty, null);
        }
        
        //orgEmail
        if(newCaseInformation.orgEmail == null && oldCaseInformation.orgEmail != null) {
            Activity.addActivty("Removed " + oldCaseInformation.orgEmail + " from Org Email", null);
        } else if(newCaseInformation.orgEmail != null && oldCaseInformation.orgEmail == null) {
            Activity.addActivty("Set Org Email to " + newCaseInformation.orgEmail, null);
        } else if(newCaseInformation.orgEmail != null && oldCaseInformation.orgEmail != null) {
            if(!oldCaseInformation.orgEmail.equals(newCaseInformation.orgEmail))
                Activity.addActivty("Changed Org Email from " + oldCaseInformation.orgEmail + " to " + newCaseInformation.orgEmail, null);
        }
        
        //fiscalYear
        if(newCaseInformation.fiscalYearEnding == null && oldCaseInformation.fiscalYearEnding != null) {
            Activity.addActivty("Removed " + oldCaseInformation.fiscalYearEnding + " from Fiscal Year Ending", null);
        } else if(newCaseInformation.fiscalYearEnding != null && oldCaseInformation.fiscalYearEnding == null) {
            Activity.addActivty("Set Fiscal Year Ending to " + newCaseInformation.fiscalYearEnding, null);
        } else if(newCaseInformation.fiscalYearEnding != null && oldCaseInformation.fiscalYearEnding != null) {
            if(!oldCaseInformation.fiscalYearEnding.equals(newCaseInformation.fiscalYearEnding))
                Activity.addActivty("Changed Fiscal Year Ending from " + oldCaseInformation.fiscalYearEnding + " to " + newCaseInformation.fiscalYearEnding, null);
        }
        
        //annualReportLF
        if(newCaseInformation.annualReport == null && oldCaseInformation.annualReport != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.annualReport.getTime())) + " from Annual Report Last Filed", null);
        } else if(newCaseInformation.annualReport != null && oldCaseInformation.annualReport == null) {
            Activity.addActivty("Set Annual Report Last Filed to " + Global.mmddyyyy.format(new Date(newCaseInformation.annualReport.getTime())), null);
        } else if(newCaseInformation.annualReport != null && oldCaseInformation.annualReport != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.annualReport.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.annualReport.getTime()))))
                Activity.addActivty("Changed Annual Report Last Filed from " + Global.mmddyyyy.format(new Date(oldCaseInformation.annualReport.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.annualReport.getTime())), null);
        }
        
        
        //financialReportLF
        if(newCaseInformation.financialReport == null && oldCaseInformation.financialReport != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.financialReport.getTime())) + " from Financial Report Last Filed", null);
        } else if(newCaseInformation.financialReport != null && oldCaseInformation.financialReport == null) {
            Activity.addActivty("Set Financial Report Last Filed to " + Global.mmddyyyy.format(new Date(newCaseInformation.financialReport.getTime())), null);
        } else if(newCaseInformation.financialReport != null && oldCaseInformation.financialReport != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.financialReport.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.financialReport.getTime()))))
                Activity.addActivty("Changed Financial Report Last Filed from " + Global.mmddyyyy.format(new Date(oldCaseInformation.financialReport.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.financialReport.getTime())), null);
        }
        
        //registrationReportLF
        if(newCaseInformation.registrationReport == null && oldCaseInformation.registrationReport != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.registrationReport.getTime())) + " from Registration Report Last Filed", null);
        } else if(newCaseInformation.registrationReport != null && oldCaseInformation.registrationReport == null) {
            Activity.addActivty("Set Registration Report Last Filed to " + Global.mmddyyyy.format(new Date(newCaseInformation.registrationReport.getTime())), null);
        } else if(newCaseInformation.registrationReport != null && oldCaseInformation.registrationReport != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.registrationReport.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.registrationReport.getTime()))))
                Activity.addActivty("Changed Registration Report Last Filed from " + Global.mmddyyyy.format(new Date(oldCaseInformation.registrationReport.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.registrationReport.getTime())), null);
        }
        
        //constructionAndByLaws
        if(newCaseInformation.constructionAndByLaws == null && oldCaseInformation.constructionAndByLaws != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.constructionAndByLaws.getTime())) + " from Construction and By Laws Filed", null);
        } else if(newCaseInformation.constructionAndByLaws != null && oldCaseInformation.constructionAndByLaws == null) {
            Activity.addActivty("Set Construction and By Laws Filed to " + Global.mmddyyyy.format(new Date(newCaseInformation.constructionAndByLaws.getTime())), null);
        } else if(newCaseInformation.constructionAndByLaws != null && oldCaseInformation.constructionAndByLaws != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.constructionAndByLaws.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.constructionAndByLaws.getTime()))))
                Activity.addActivty("Changed Construction and By Laws Filed from " + Global.mmddyyyy.format(new Date(oldCaseInformation.constructionAndByLaws.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.constructionAndByLaws.getTime())), null);
        }
        
        //lastNotifiation
        if(newCaseInformation.lastNotification == null && oldCaseInformation.lastNotification != null) {
            Activity.addActivty("Removed " + oldCaseInformation.lastNotification + " from Last Notification", null);
        } else if(newCaseInformation.lastNotification != null && oldCaseInformation.lastNotification == null) {
            Activity.addActivty("Set Last Notification to " + newCaseInformation.lastNotification, null);
        } else if(newCaseInformation.lastNotification != null && oldCaseInformation.lastNotification != null) {
            if(!oldCaseInformation.lastNotification.equals(newCaseInformation.lastNotification))
                Activity.addActivty("Changed Last Notification from " + oldCaseInformation.lastNotification + " to " + newCaseInformation.lastNotification, null);
        }
        
        //deemed
        if(newCaseInformation.deemedCertified == false && oldCaseInformation.deemedCertified != false) {
            Activity.addActivty("Unset Deemed Certified", null);
        } else if(newCaseInformation.deemedCertified != false && oldCaseInformation.deemedCertified == false) {
            Activity.addActivty("Set Deemed Certified", null);
        }
        
        //deemed
        if(newCaseInformation.boardCertified == false && oldCaseInformation.boardCertified != false) {
            Activity.addActivty("Unset Board Certified", null);
        } else if(newCaseInformation.boardCertified != false && oldCaseInformation.boardCertified == false) {
            Activity.addActivty("Set Board Certified", null);
        }
        
        //filedByparent
        if(newCaseInformation.filedByParent == false && oldCaseInformation.filedByParent != false) {
            Activity.addActivty("Unset Filed By Parent", null);
        } else if(newCaseInformation.filedByParent != false && oldCaseInformation.filedByParent == false) {
            Activity.addActivty("Set Filed By Parent", null);
        }
        
        //valid
        if(newCaseInformation.valid == false && oldCaseInformation.valid != false) {
            Activity.addActivty("Unset Valid", null);
        } else if(newCaseInformation.valid != false && oldCaseInformation.valid == false) {
            Activity.addActivty("Set Valid", null);
        }
        
        //parent1
        if(newCaseInformation.parent1 == null && oldCaseInformation.parent1 != null) {
            Activity.addActivty("Removed " + oldCaseInformation.parent1 + " from Parent 1", null);
        } else if(newCaseInformation.parent1 != null && oldCaseInformation.parent1 == null) {
            Activity.addActivty("Set Parent 1 to " + newCaseInformation.parent1, null);
        } else if(newCaseInformation.parent1 != null && oldCaseInformation.parent1 != null) {
            if(!oldCaseInformation.parent1.equals(newCaseInformation.parent1))
                Activity.addActivty("Changed Parent 1 from " + oldCaseInformation.parent1 + " to " + newCaseInformation.parent1, null);
        }
        
        //parent2
        if(newCaseInformation.parent2 == null && oldCaseInformation.parent2 != null) {
            Activity.addActivty("Removed " + oldCaseInformation.parent2 + " from Parent 2", null);
        } else if(newCaseInformation.parent2 != null && oldCaseInformation.parent2 == null) {
            Activity.addActivty("Set Parent 2 to " + newCaseInformation.parent2, null);
        } else if(newCaseInformation.parent2 != null && oldCaseInformation.parent2 != null) {
            if(!oldCaseInformation.parent2.equals(newCaseInformation.parent2))
                Activity.addActivty("Changed Parent 2 from " + oldCaseInformation.parent2 + " to " + newCaseInformation.parent2, null);
        }
        
        //case
        if(newCaseInformation.outsideCase == null && oldCaseInformation.outsideCase != null) {
            Activity.addActivty("Removed " + oldCaseInformation.outsideCase + " from Case", null);
        } else if(newCaseInformation.outsideCase != null && oldCaseInformation.outsideCase == null) {
            Activity.addActivty("Set Case to " + newCaseInformation.outsideCase, null);
        } else if(newCaseInformation.outsideCase != null && oldCaseInformation.outsideCase != null) {
            if(!oldCaseInformation.outsideCase.equals(newCaseInformation.outsideCase))
                Activity.addActivty("Changed Case from " + oldCaseInformation.outsideCase + " to " + newCaseInformation.outsideCase, null);
        }
        
        //dateFiled
        if(newCaseInformation.dateFiled == null && oldCaseInformation.dateFiled != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.dateFiled.getTime())) + " from Date Filed", null);
        } else if(newCaseInformation.dateFiled != null && oldCaseInformation.dateFiled == null) {
            Activity.addActivty("Set Date Filed to " + Global.mmddyyyy.format(new Date(newCaseInformation.dateFiled.getTime())), null);
        } else if(newCaseInformation.dateFiled != null && oldCaseInformation.dateFiled != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.dateFiled.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.dateFiled.getTime()))))
                Activity.addActivty("Changed Date Filed from " + Global.mmddyyyy.format(new Date(oldCaseInformation.dateFiled.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.dateFiled.getTime())), null);
        }
        
        //registrationLetterSent
        if(newCaseInformation.registrationLetterSent == null && oldCaseInformation.registrationLetterSent != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.registrationLetterSent.getTime())) + " from Registration Letter Sent", null);
        } else if(newCaseInformation.registrationLetterSent != null && oldCaseInformation.registrationLetterSent == null) {
            Activity.addActivty("Set Registration Letter Sent to " + Global.mmddyyyy.format(new Date(newCaseInformation.registrationLetterSent.getTime())), null);
        } else if(newCaseInformation.registrationLetterSent != null && oldCaseInformation.registrationLetterSent != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.registrationLetterSent.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.registrationLetterSent.getTime()))))
                Activity.addActivty("Changed Registration Letter Sent from " + Global.mmddyyyy.format(new Date(oldCaseInformation.registrationLetterSent.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.registrationLetterSent.getTime())), null);
        }
        
        //extension Date
        if(newCaseInformation.extensionDate == null && oldCaseInformation.extensionDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.extensionDate.getTime())) + " from Extension Date", null);
        } else if(newCaseInformation.extensionDate != null && oldCaseInformation.extensionDate == null) {
            Activity.addActivty("Set Extension Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.extensionDate.getTime())), null);
        } else if(newCaseInformation.extensionDate != null && oldCaseInformation.extensionDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.extensionDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.extensionDate.getTime()))))
                Activity.addActivty("Changed Extension Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.extensionDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.extensionDate.getTime())), null);
        }
    }
    
    public static boolean validateOrg(String orgNumber) {
        boolean valid = false;
        
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select Count(*) As results"
                    + " from OrgCase"
                    + " where orgNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, orgNumber);
            
            ResultSet validRS = preparedStatement.executeQuery();
            
            validRS.next();
            
            valid = validRS.getInt("results") > 0;
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return valid;
    }
}
