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
public class CSCCase {

    
    
    public int id;
    public boolean active;
    public String name;
    public String type; 
    public String cscNumber; 
    public String cscEmployerID; 
    public String address1; 
    public String address2; 
    public String city; 
    public String state; 
    public String zipCode; 
    public String phone1;
    public String phone2;
    public String fax;
    public String email;
    public boolean statutory;
    public String charter;
    public String fiscalYearEnding;
    public String lastNotification;
    public Timestamp activityLastFiled;
    public Timestamp previousFileDate;
    public String parent1;
    public String parent2;
    public String dueDate;
    public Timestamp filed;
    public boolean valid;
    public String note;
    public String alsoKnownAs;
    public String county;
    
    public static void createNewCSC(String number, String name) {
        try {
            
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Insert Into CSCCase (name, cscNumber, type) Values (?,?,'Local')";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, number);
            

            int success = preparedStatement.executeUpdate();
            
            if (success == 1) {
                CaseNumber.updateCSCCaseNumber(number);
                Global.caseType = "CSC";
                Global.caseNumber = number;
                Activity.addActivty("Civil Service Commission Created", null);
                Audit.addAuditEntry("Created New CSC: " + name);
                Global.root.getcSCHeaderPanel1().loadCases();
                Global.root.getcSCHeaderPanel1().getjComboBox2().setSelectedItem(name);
            }
            
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    public static List loadCSCNames() {
        
        List orgNameList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select "
                    + " name" 
                    + " from CSCCase"
                    + " Order By name ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                orgNameList.add(caseNumberRS.getString("name") != null ? caseNumberRS.getString("name") : "");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return orgNameList;
    }
    
//    public static List loadORGNamesAndIDs() {
//        
//        List orgNameList = new ArrayList<>();
//            
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Select "
//                    + " orgName, orgNumber" 
//                    + " from ORGCase"
//                    + " Order By orgName ASC";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//
//            ResultSet caseNumberRS = preparedStatement.executeQuery();
//            
//            while(caseNumberRS.next()) {
//                CSCCase org = new CSCCase();
//                org.orgName = caseNumberRS.getString("orgName") != null ? caseNumberRS.getString("orgName") : "";
//                org.orgNumber = caseNumberRS.getString("orgNumber") != null ? caseNumberRS.getString("orgNumber") : "";
//                orgNameList.add(org);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return orgNameList;
//    }
    
    public static List getCaseSearchData() {
        
        List orgNameList = new ArrayList<>();
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select "
                    + " name, cscNumber, alsoKnownAs" 
                    + " from CSCCase"
                    + " Order By name ASC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            while(caseNumberRS.next()) {
                CSCCase org = new CSCCase();
                org.name = caseNumberRS.getString("name") != null ? caseNumberRS.getString("name") : "";
                org.cscNumber = caseNumberRS.getString("cscNumber") != null ? caseNumberRS.getString("cscNumber") : "";
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
                    + " from CSCCase"
                    + " where cscNumber = ?";

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
    
    public static String getCSCName() {
        String name = null;
            
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select name"
                    + " from CSCCase"
                    + " where cscNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

            ResultSet caseNumberRS = preparedStatement.executeQuery();
            
            caseNumberRS.next();
            
            name = caseNumberRS.getString("name");

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

            String sql = "Update CSCCase"
                    + " set note = ?"
                    + " where cscNumber = ?";

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
     * Creates a new REPCase entry
     * @param caseNumber the case number to be created 
     */
//    public static void createCase(String caseYear, String caseType, String caseMonth, String caseNumber) {
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "Insert into MEDCase (CaseYear, CaseType, CaseMonth, CaseNumber, FileDate, caseStatus) Values (?,?,?,?,?,'Open')";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, caseYear);
//            preparedStatement.setString(2, caseType);
//            preparedStatement.setString(3, caseMonth);
//            preparedStatement.setString(4, caseNumber);
//            preparedStatement.setTimestamp(5, new Timestamp(System.currentTimeMillis()));
//
//            int success = preparedStatement.executeUpdate();
//            
//            if(success == 1) {
//                String fullCaseNumber = caseYear
//                        + "-"
//                        + caseType
//                        + "-"
//                        + caseMonth
//                        + "-"
//                        + caseNumber;
//                        
//                CaseNumber.updateNextCaseNumber(caseYear, caseType, String.valueOf(Integer.valueOf(caseNumber) + 1));
//                Audit.addAuditEntry("Created Case: " + fullCaseNumber);
//                Activity.addNewCaseActivty(fullCaseNumber, "Case was Filed and Started");
//                Global.root.getmEDHeaderPanel1().loadCases();
//                Global.root.getmEDHeaderPanel1().getjComboBox2().setSelectedItem(fullCaseNumber); 
//            }
//        } catch (SQLException ex) {
//            SlackNotification.sendNotification(ex.getMessage());
//        }
//    }
    
    /**
     * Load information that is to be displayed in the header.  Dates are
     * formatted before being returned
     * @return a REPCase instance with the needed values
     */
    public static CSCCase loadHeaderInformation() {
        CSCCase org = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " cscnumber"
                    + " from CSCCase where name = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

            ResultSet caseHeader = preparedStatement.executeQuery();
            
            if(caseHeader.next()) {
                org = new CSCCase();
                org.cscNumber = caseHeader.getString("cscNumber");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return org;
    }
    
    public static CSCCase loadCSCInformation() {
        CSCCase org = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Select"
                    + " name,"
                    + " alsoKnownAs,"
                    + " type,"
                    + " cscNumber,"
                    + " cscEmployerID,"
                    + " address1,"
                    + " address2,"
                    + " city,"
                    + " state,"
                    + " zipCode,"
                    + " phone1,"
                    + " phone2,"
                    + " fax,"
                    + " email,"
                    + " statutory,"
                    + " charter,"
                    + " fiscalYearEnding,"
                    + " lastNotification,"
                    + " activityLastFiled,"
                    + " previousFileDate,"
                    + " dueDate,"
                    + " filed,"
                    + " valid,"
                    + " county"
                    + " from CSCCase where cscNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseNumber);

            ResultSet caseInformation = preparedStatement.executeQuery();
            
            if(caseInformation.next()) {
                org = new CSCCase();
                org.name = caseInformation.getString("name");
                org.alsoKnownAs = caseInformation.getString("alsoKnownAs");
                org.type = caseInformation.getString("type");
                org.cscNumber = caseInformation.getString("cscNumber");
                org.cscEmployerID = caseInformation.getString("cscEmployerID");
                org.address1 = caseInformation.getString("address1");
                org.address2 = caseInformation.getString("address2");
                org.city = caseInformation.getString("city");
                org.state = caseInformation.getString("state");
                org.zipCode = caseInformation.getString("zipCode");
                org.phone1 = caseInformation.getString("phone1");
                org.phone2 = caseInformation.getString("phone2");
                org.fax = caseInformation.getString("fax");
                org.email = caseInformation.getString("email");
                org.charter = caseInformation.getString("charter");
                org.fiscalYearEnding = caseInformation.getString("fiscalYearEnding");
                org.lastNotification = caseInformation.getString("lastNotification");
                org.previousFileDate = caseInformation.getTimestamp("previousFileDate");
                org.dueDate = caseInformation.getString("dueDate");
                org.filed = caseInformation.getTimestamp("filed");
                org.valid = caseInformation.getBoolean("valid");
                org.county = caseInformation.getString("county");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
        return org;
    }
    
//    public static List<CSCCase> getOrgCasesAllLettersDefault(){
//        List orgLettersList = new ArrayList<>();
//            
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "SELECT * FROM ORGCase WHERE "
//                    + "registrationReport IS NULL AND "
//                    + "DATEADD(day, 60, registrationLetterSent ) <= getDATE()";
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            ResultSet rs = preparedStatement.executeQuery();
//            
//            while(rs.next()) {
//                CSCCase org = new CSCCase();
//                org.orgName = rs.getString("orgName");
//                org.alsoKnownAs = rs.getString("alsoKnownAs");
//                org.orgNumber = rs.getString("orgNumber");
//                org.orgType = rs.getString("orgType");
//                org.orgPhone1 = rs.getString("orgPhone1");
//                org.orgPhone2 = rs.getString("orgPhone2");
//                org.orgFax = rs.getString("orgFax");
//                org.employerID = rs.getString("employerID");
//                org.orgAddress1 = rs.getString("orgAddress1");
//                org.orgAddress2 = rs.getString("orgAddress2");
//                org.orgCity = rs.getString("orgCity");
//                org.orgState = rs.getString("orgState");
//                org.orgZip = rs.getString("orgZip");
//                org.orgCounty = rs.getString("orgCounty");
//                org.orgEmail = rs.getString("orgEmail");
//                org.fiscalYearEnding = rs.getString("fiscalYearEnding");
//                org.annualReport = rs.getTimestamp("annualReport");
//                org.financialReport = rs.getTimestamp("financialReport");
//                org.registrationReport = rs.getTimestamp("registrationReport");
//                org.constructionAndByLaws = rs.getTimestamp("constructionAndByLaws");
//                org.lastNotification = rs.getString("lastNotification");
//                org.deemedCertified = rs.getBoolean("deemedCertified");
//                org.boardCertified = rs.getBoolean("boardCertified");
//                org.filedByParent = rs.getBoolean("filedByParent");
//                org.valid = rs.getBoolean("valid");
//                org.parent1 = rs.getString("parent1");
//                org.parent2 = rs.getString("parent2");
//                org.outsideCase = rs.getString("outsideCase");
//                org.filingDueDate = rs.getString("filingDueDate");
//                org.dateFiled = rs.getTimestamp("dateFiled");
////                org.certifiedDate = rs.getTimestamp("certifiedDate");
//                org.registrationLetterSent = rs.getTimestamp("registrationLetterSent");
//                org.extensionDate = rs.getTimestamp("extensionDate");
//                orgLettersList.add(org);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return orgLettersList;        
//    }
    
//    public static List<CSCCase> getOrgCasesAllLetters(String month, String date){
//        List orgLettersList = new ArrayList<>();
//            
//        try {
//            Statement stmt = Database.connectToDB().createStatement();
//
//            String sql = "SELECT * FROM ORGCase WHERE "
//                    + "Active = 1 AND Valid = 1 AND FiledByParent = 0 AND fiscalYearEnding = ? "
//                    + "AND ( constructionAndByLaws = null OR registrationReport = null "
//                    + "OR annualReport = null OR annualReport < ? OR financialReport = null "
//                    + "OR financialReport < ?) Order By orgName ASC";
//
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setString(1, month);
//            preparedStatement.setString(2, date);
//            preparedStatement.setString(3, date);
//            ResultSet rs = preparedStatement.executeQuery();
//            
//            while(rs.next()) {
//                CSCCase org = new CSCCase();
//                org.orgName = rs.getString("orgName");
//                org.alsoKnownAs = rs.getString("alsoKnownAs");
//                org.orgNumber = rs.getString("orgNumber");
//                org.orgType = rs.getString("orgType");
//                org.orgPhone1 = rs.getString("orgPhone1");
//                org.orgPhone2 = rs.getString("orgPhone2");
//                org.orgFax = rs.getString("orgFax");
//                org.employerID = rs.getString("employerID");
//                org.orgAddress1 = rs.getString("orgAddress1");
//                org.orgAddress2 = rs.getString("orgAddress2");
//                org.orgCity = rs.getString("orgCity");
//                org.orgState = rs.getString("orgState");
//                org.orgZip = rs.getString("orgZip");
//                org.orgCounty = rs.getString("orgCounty");
//                org.orgEmail = rs.getString("orgEmail");
//                org.fiscalYearEnding = rs.getString("fiscalYearEnding");
//                org.annualReport = rs.getTimestamp("annualReport");
//                org.financialReport = rs.getTimestamp("financialReport");
//                org.registrationReport = rs.getTimestamp("registrationReport");
//                org.constructionAndByLaws = rs.getTimestamp("constructionAndByLaws");
//                org.lastNotification = rs.getString("lastNotification");
//                org.deemedCertified = rs.getBoolean("deemedCertified");
//                org.boardCertified = rs.getBoolean("boardCertified");
//                org.filedByParent = rs.getBoolean("filedByParent");
//                org.valid = rs.getBoolean("valid");
//                org.parent1 = rs.getString("parent1");
//                org.parent2 = rs.getString("parent2");
//                org.outsideCase = rs.getString("outsideCase");
//                org.filingDueDate = rs.getString("filingDueDate");
//                org.dateFiled = rs.getTimestamp("dateFiled");
////                org.certifiedDate = rs.getTimestamp("certifiedDate");
//                org.registrationLetterSent = rs.getTimestamp("registrationLetterSent");
//                org.extensionDate = rs.getTimestamp("extensionDate");
//                orgLettersList.add(org);
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return orgLettersList;        
//    }
//    
    public static void updateCSCInformation(CSCCase newCaseInformation, CSCCase caseInformation) {
        CSCCase med = null;
        try {
            Statement stmt = Database.connectToDB().createStatement();  
            
            String sql = "Update CSCCase Set"
                    + " name = ?,"
                    + " alsoKnownAs = ?,"
                    + " type = ?,"
                    + " cscNumber = ?,"
                    + " cscEmployerID = ?,"
                    + " address1 = ?,"
                    + " address2 = ?,"
                    + " city = ?,"
                    + " state = ?,"
                    + " zipCode = ?,"
                    + " phone1 = ?,"
                    + " phone2 = ?,"
                    + " fax = ?,"
                    + " email = ?,"
                    + " statutory = ?,"
                    + " charter = ?,"
                    + " fiscalYearEnding = ?,"
                    + " lastNotification = ?,"
                    + " activityLastFiled = ?,"
                    + " previousFileDate = ?,"
                    + " dueDate = ?,"
                    + " filed = ?,"
                    + " valid = ?,"
                    + " county = ?"
                    + " from CSCCase where cscNumber = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, newCaseInformation.name);
            preparedStatement.setString(2, newCaseInformation.alsoKnownAs);
            preparedStatement.setString(3, newCaseInformation.type);
            preparedStatement.setString(4, newCaseInformation.cscNumber);
            preparedStatement.setString(5, newCaseInformation.cscEmployerID);
            preparedStatement.setString(6, newCaseInformation.address1);
            preparedStatement.setString(7, newCaseInformation.address2);
            preparedStatement.setString(8, newCaseInformation.city);
            preparedStatement.setString(9, newCaseInformation.state);
            preparedStatement.setString(10, newCaseInformation.zipCode);
            preparedStatement.setString(11, newCaseInformation.phone1);
            preparedStatement.setString(12, newCaseInformation.phone2);
            preparedStatement.setString(13, newCaseInformation.fax);
            preparedStatement.setString(14, newCaseInformation.email);
            preparedStatement.setBoolean(15, newCaseInformation.statutory);
            preparedStatement.setString(16, newCaseInformation.charter);
            preparedStatement.setString(17, newCaseInformation.fiscalYearEnding);
            preparedStatement.setString(18, newCaseInformation.lastNotification);
            preparedStatement.setTimestamp(19, newCaseInformation.activityLastFiled);
            preparedStatement.setTimestamp(20, newCaseInformation.previousFileDate);
            preparedStatement.setString(21, newCaseInformation.dueDate);
            preparedStatement.setTimestamp(22, newCaseInformation.filed);
            preparedStatement.setBoolean(23, newCaseInformation.valid);
            preparedStatement.setString(24, newCaseInformation.county);
            preparedStatement.setString(25, Global.caseNumber);

            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                detailedSaveCSCInformation(newCaseInformation, caseInformation);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    private static void detailedSaveCSCInformation(CSCCase newCaseInformation, CSCCase oldCaseInformation) {
        
        //name
        if(newCaseInformation.name == null && oldCaseInformation.name != null) {
            Activity.addActivty("Removed " + oldCaseInformation.name + " from CSC Name", null);
        } else if(newCaseInformation.name != null && oldCaseInformation.name == null) {
            Activity.addActivty("Set CSC Name to " + newCaseInformation.name, null);
        } else if(newCaseInformation.name != null && oldCaseInformation.name != null) {
            if(!oldCaseInformation.name.equals(newCaseInformation.name))
                Activity.addActivty("Changed CSC Name from " + oldCaseInformation.name + " to " + newCaseInformation.name, null);
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
        
        //number
        if(newCaseInformation.cscNumber == null && oldCaseInformation.cscNumber != null) {
            Activity.addActivty("Removed " + oldCaseInformation.cscNumber + " from CSC Number", null);
        } else if(newCaseInformation.cscNumber != null && oldCaseInformation.cscNumber == null) {
            Activity.addActivty("Set CSC Number to " + newCaseInformation.cscNumber, null);
        } else if(newCaseInformation.cscNumber != null && oldCaseInformation.cscNumber != null) {
            if(!oldCaseInformation.cscNumber.equals(newCaseInformation.cscNumber))
                Activity.addActivty("Changed CSC Number from " + oldCaseInformation.cscNumber + " to " + newCaseInformation.cscNumber, null);
        }
        
        //type
        if(newCaseInformation.type == null && oldCaseInformation.type != null) {
            Activity.addActivty("Removed " + oldCaseInformation.type + " from CSC Type", null);
        } else if(newCaseInformation.type != null && oldCaseInformation.type == null) {
            Activity.addActivty("Set CSC Type to " + newCaseInformation.type, null);
        } else if(newCaseInformation.type != null && oldCaseInformation.type != null) {
            if(!oldCaseInformation.type.equals(newCaseInformation.type))
                Activity.addActivty("Changed CSC Type from " + oldCaseInformation.type + " to " + newCaseInformation.type, null);
        }
        
        //phone1
        if(newCaseInformation.phone1 == null && oldCaseInformation.phone1 != null) {
            Activity.addActivty("Removed " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.phone1) + " from CSC Phone 1", null);
        } else if(newCaseInformation.phone1 != null && oldCaseInformation.phone1 == null) {
            Activity.addActivty("Set CSC Phone 1 to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.phone1), null);
        } else if(newCaseInformation.phone1 != null && oldCaseInformation.phone1 != null) {
            if(!oldCaseInformation.phone1.equals(newCaseInformation.phone1))
                Activity.addActivty("Changed CSC Phone 1 from " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.phone1) + " to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.phone1), null);
        }
        
        //phone2
        if(newCaseInformation.phone2 == null && oldCaseInformation.phone2 != null) {
            Activity.addActivty("Removed " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.phone2) + " from CSC Phone 2", null);
        } else if(newCaseInformation.phone2 != null && oldCaseInformation.phone2 == null) {
            Activity.addActivty("Set CSC Phone 2 to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.phone2), null);
        } else if(newCaseInformation.phone2 != null && oldCaseInformation.phone2 != null) {
            if(!oldCaseInformation.phone2.equals(newCaseInformation.phone2))
                Activity.addActivty("Changed CSC Phone 2 from " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.phone2) + " to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.phone2), null);
        }
        
        //fax
        if(newCaseInformation.fax == null && oldCaseInformation.fax != null) {
            Activity.addActivty("Removed " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.fax) + " from CSC Fax", null);
        } else if(newCaseInformation.fax != null && oldCaseInformation.fax == null) {
            Activity.addActivty("Set CSC Fax to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.fax), null);
        } else if(newCaseInformation.fax != null && oldCaseInformation.fax != null) {
            if(!oldCaseInformation.fax.equals(newCaseInformation.fax))
                Activity.addActivty("Changed CSC Fax from " + NumberFormatService.convertStringToPhoneNumber(oldCaseInformation.fax) + " to " + NumberFormatService.convertStringToPhoneNumber(newCaseInformation.fax), null);
        }
        
        //employerID
        if(newCaseInformation.cscEmployerID == null && oldCaseInformation.cscEmployerID != null) {
            Activity.addActivty("Removed " + oldCaseInformation.cscEmployerID + " from Employer ID", null);
        } else if(newCaseInformation.cscEmployerID != null && oldCaseInformation.cscEmployerID == null) {
            Activity.addActivty("Set Employer ID to " + newCaseInformation.cscEmployerID, null);
        } else if(newCaseInformation.cscEmployerID != null && oldCaseInformation.cscEmployerID != null) {
            if(!oldCaseInformation.cscEmployerID.equals(newCaseInformation.cscEmployerID))
                Activity.addActivty("Changed Employer ID from " + oldCaseInformation.cscEmployerID + " to " + newCaseInformation.cscEmployerID, null);
        }
        
        //address1
        if(newCaseInformation.address1 == null && oldCaseInformation.address1 != null) {
            Activity.addActivty("Removed " + oldCaseInformation.address1 + " from CSC Address 1", null);
        } else if(newCaseInformation.address1 != null && oldCaseInformation.address1 == null) {
            Activity.addActivty("Set CSC Address 1 to " + newCaseInformation.address1, null);
        } else if(newCaseInformation.address1 != null && oldCaseInformation.address1 != null) {
            if(!oldCaseInformation.address1.equals(newCaseInformation.address1))
                Activity.addActivty("Changed CSC Address 1 from " + oldCaseInformation.address1 + " to " + newCaseInformation.address1, null);
        }
        
        //address2
        if(newCaseInformation.address2 == null && oldCaseInformation.address2 != null) {
            Activity.addActivty("Removed " + oldCaseInformation.address2 + " from CSC Address 2", null);
        } else if(newCaseInformation.address2 != null && oldCaseInformation.address2 == null) {
            Activity.addActivty("Set CSC Address 2 to " + newCaseInformation.address2, null);
        } else if(newCaseInformation.address2 != null && oldCaseInformation.address2 != null) {
            if(!oldCaseInformation.address2.equals(newCaseInformation.address2))
                Activity.addActivty("Changed CSC Address 2 from " + oldCaseInformation.address2 + " to " + newCaseInformation.address2, null);
        }
        
        //city
        if(newCaseInformation.city == null && oldCaseInformation.city != null) {
            Activity.addActivty("Removed " + oldCaseInformation.city + " from CSC City", null);
        } else if(newCaseInformation.city != null && oldCaseInformation.city == null) {
            Activity.addActivty("Set CSC City to " + newCaseInformation.city, null);
        } else if(newCaseInformation.city != null && oldCaseInformation.city != null) {
            if(!oldCaseInformation.city.equals(newCaseInformation.city))
                Activity.addActivty("Changed CSC City from " + oldCaseInformation.city + " to " + newCaseInformation.city, null);
        }
        
        //state
        if(newCaseInformation.state == null && oldCaseInformation.state != null) {
            Activity.addActivty("Removed " + oldCaseInformation.state + " from CSC State", null);
        } else if(newCaseInformation.state != null && oldCaseInformation.state == null) {
            Activity.addActivty("Set CSC State to " + newCaseInformation.state, null);
        } else if(newCaseInformation.state != null && oldCaseInformation.state != null) {
            if(!oldCaseInformation.state.equals(newCaseInformation.state))
                Activity.addActivty("Changed CSC State from " + oldCaseInformation.state + " to " + newCaseInformation.state, null);
        }
        
        //zip
        if(newCaseInformation.zipCode == null && oldCaseInformation.zipCode != null) {
            Activity.addActivty("Removed " + oldCaseInformation.zipCode + " from CSC Zip", null);
        } else if(newCaseInformation.zipCode != null && oldCaseInformation.zipCode == null) {
            Activity.addActivty("Set CSC Zip to " + newCaseInformation.zipCode, null);
        } else if(newCaseInformation.zipCode != null && oldCaseInformation.zipCode != null) {
            if(!oldCaseInformation.zipCode.equals(newCaseInformation.zipCode))
                Activity.addActivty("Changed CSC Zip from " + oldCaseInformation.zipCode + " to " + newCaseInformation.zipCode, null);
        }
        
        //county
        if(newCaseInformation.county == null && oldCaseInformation.county != null) {
            Activity.addActivty("Removed " + oldCaseInformation.county + " from CSC County", null);
        } else if(newCaseInformation.county != null && oldCaseInformation.county == null) {
            Activity.addActivty("Set CSC County to " + newCaseInformation.county, null);
        } else if(newCaseInformation.county != null && oldCaseInformation.county != null) {
            if(!oldCaseInformation.county.equals(newCaseInformation.county))
                Activity.addActivty("Changed CSC County from " + oldCaseInformation.county + " to " + newCaseInformation.county, null);
        }
        
        //email
        if(newCaseInformation.email == null && oldCaseInformation.email != null) {
            Activity.addActivty("Removed " + oldCaseInformation.email + " from CSC Email", null);
        } else if(newCaseInformation.email != null && oldCaseInformation.email == null) {
            Activity.addActivty("Set CSC Email to " + newCaseInformation.email, null);
        } else if(newCaseInformation.email != null && oldCaseInformation.email != null) {
            if(!oldCaseInformation.email.equals(newCaseInformation.email))
                Activity.addActivty("Changed CSC Email from " + oldCaseInformation.email + " to " + newCaseInformation.email, null);
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
        
        //dueDate
        if(newCaseInformation.dueDate == null && oldCaseInformation.dueDate != null) {
            Activity.addActivty("Removed " + oldCaseInformation.dueDate + " from Due Date", null);
        } else if(newCaseInformation.dueDate != null && oldCaseInformation.dueDate == null) {
            Activity.addActivty("Set Due Date to " + newCaseInformation.dueDate, null);
        } else if(newCaseInformation.dueDate != null && oldCaseInformation.dueDate != null) {
            if(!oldCaseInformation.dueDate.equals(newCaseInformation.dueDate))
                Activity.addActivty("Changed Due Date from " + oldCaseInformation.dueDate + " to " + newCaseInformation.dueDate, null);
        }
        
        //dateFiled
        if(newCaseInformation.filed == null && oldCaseInformation.filed != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.filed.getTime())) + " from Date Filed", null);
        } else if(newCaseInformation.filed != null && oldCaseInformation.filed == null) {
            Activity.addActivty("Set Date Filed to " + Global.mmddyyyy.format(new Date(newCaseInformation.filed.getTime())), null);
        } else if(newCaseInformation.filed != null && oldCaseInformation.filed != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.filed.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.filed.getTime()))))
                Activity.addActivty("Changed Date Filed from " + Global.mmddyyyy.format(new Date(oldCaseInformation.filed.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.filed.getTime())), null);
        }
        
        //activites last files
        if(newCaseInformation.activityLastFiled == null && oldCaseInformation.activityLastFiled != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.activityLastFiled.getTime())) + " from Activities Report Last Filed", null);
        } else if(newCaseInformation.activityLastFiled != null && oldCaseInformation.activityLastFiled == null) {
            Activity.addActivty("Set Activities Report Last Filed to " + Global.mmddyyyy.format(new Date(newCaseInformation.activityLastFiled.getTime())), null);
        } else if(newCaseInformation.activityLastFiled != null && oldCaseInformation.activityLastFiled != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.activityLastFiled.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.activityLastFiled.getTime()))))
                Activity.addActivty("Changed Activities Report Last Filed from " + Global.mmddyyyy.format(new Date(oldCaseInformation.activityLastFiled.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.activityLastFiled.getTime())), null);
        }
        
        //previous file date
        if(newCaseInformation.previousFileDate == null && oldCaseInformation.previousFileDate != null) {
            Activity.addActivty("Removed " + Global.mmddyyyy.format(new Date(oldCaseInformation.previousFileDate.getTime())) + " from Previous File Date", null);
        } else if(newCaseInformation.previousFileDate != null && oldCaseInformation.previousFileDate == null) {
            Activity.addActivty("Set Previous File Date to " + Global.mmddyyyy.format(new Date(newCaseInformation.previousFileDate.getTime())), null);
        } else if(newCaseInformation.previousFileDate != null && oldCaseInformation.previousFileDate != null) {
            if(!Global.mmddyyyy.format(new Date(oldCaseInformation.previousFileDate.getTime())).equals(Global.mmddyyyy.format(new Date(newCaseInformation.previousFileDate.getTime()))))
                Activity.addActivty("Changed Previous File Date from " + Global.mmddyyyy.format(new Date(oldCaseInformation.previousFileDate.getTime())) + " to " + Global.mmddyyyy.format(new Date(newCaseInformation.previousFileDate.getTime())), null);
        }
        
        //charter
        if(newCaseInformation.charter == null && oldCaseInformation.charter != null) {
            Activity.addActivty("Removed " + oldCaseInformation.charter + " from Charter", null);
        } else if(newCaseInformation.charter != null && oldCaseInformation.charter == null) {
            Activity.addActivty("Set Charter to " + newCaseInformation.charter, null);
        } else if(newCaseInformation.charter != null && oldCaseInformation.charter != null) {
            if(!oldCaseInformation.charter.equals(newCaseInformation.charter))
                Activity.addActivty("Changed Charter from " + oldCaseInformation.charter + " to " + newCaseInformation.charter, null);
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
        
        //statutory
        if(newCaseInformation.statutory == false && oldCaseInformation.statutory != false) {
            Activity.addActivty("Unset Statutory", null);
        } else if(newCaseInformation.statutory != false && oldCaseInformation.statutory == false) {
            Activity.addActivty("Set Statutory", null);
        }
        
        //valid
        if(newCaseInformation.valid == false && oldCaseInformation.valid != false) {
            Activity.addActivty("Unset Valid", null);
        } else if(newCaseInformation.valid != false && oldCaseInformation.valid == false) {
            Activity.addActivty("Set Valid", null);
        }
    }
}
