/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.SlackNotification;

/**
 *
 * @author User
 */
public class CMDSDocuments {
    
    public int ID;
    public boolean Active;
    public String MainCategory;
    public String SubCategory;
    public String LetterName;
    public String Location;
    public boolean MultiplePrint;
    public boolean ResponseDue;
    public boolean ActionAppealed;
    public boolean ClassificationTitle;
    public boolean ClassificationNumber;
    public boolean BarginingUnit;
    public boolean AppelantAppointed;
    public boolean ProbitionaryPeriod;
    public boolean HearingDate;
    public boolean HearingTime;
    public boolean HearingServed;
    public boolean MemorandumContra;
    public boolean Gender;
    public boolean AddressBlock;
    public boolean FirstLetterSent;
    public boolean CodeSection;
    public boolean CountyName;
    public boolean StayDate;
    public boolean CasePendingResolution;
    public boolean LastUpdate;
    public boolean DateGranted;
    public boolean MatterContinued;
    public boolean SettlementDue;
    public boolean FilingParty;
    public boolean RespondingParty;
    public boolean RequestingParty;
    public boolean Deposition;
    public boolean RepHimOrHer;
    public boolean TypeOfAction;
    public boolean CodeSectionFillIn;
    public boolean DocumentName;
    public boolean DateFiled;
    public boolean InfoRedacted;
    public boolean RedactorName;
    public boolean RedactorTitle;
    public boolean DatePOSent;
    public boolean AppealType;
    public boolean AppealType2;
    public boolean AppealTypeUF;
    public boolean AppealTypeLS;
    public boolean RequestingPartyC;
    public boolean DateRequested;
    public boolean PurposeOfExtension;
    public String emailSubject;
    public String emailBody;

    public static List<CMDSDocuments> loadAllCMDSDocuments(String[] param) {
        List<CMDSDocuments> list = new ArrayList<>();

        Statement stmt = null; 
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSDocuments";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(MainCategory, SubCategory, LetterName) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY MainCategory, SubCategory, LetterName";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                CMDSDocuments item = new CMDSDocuments();
                item.ID = rs.getInt("id");
                item.Active = rs.getBoolean("active");
                item.MainCategory = rs.getString("MainCategory") == null ? "" : rs.getString("MainCategory");
                item.SubCategory = rs.getString("SubCategory") == null ? "" : rs.getString("SubCategory");
                item.LetterName = rs.getString("LetterName") == null ? "" : rs.getString("LetterName");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllCMDSDocuments(param);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
    public static CMDSDocuments findDocumentByID(int id) {
        CMDSDocuments doc = new CMDSDocuments();
        
        Statement stmt = null;
        
        try {
            
            stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from CMDSDocuments where id = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet foundDoc = preparedStatement.executeQuery();
            
            if(foundDoc.next()) {
                doc.ID = foundDoc.getInt("ID");
                doc.Active = foundDoc.getBoolean("Active");
                doc.MainCategory = foundDoc.getString("MainCategory");
                doc.SubCategory = foundDoc.getString("SubCategory");
                doc.LetterName = foundDoc.getString("LetterName");
                doc.Location = foundDoc.getString("Location");
                doc.MultiplePrint = foundDoc.getBoolean("MultiplePrint");
                doc.ResponseDue = foundDoc.getBoolean("ResponseDue");
                doc.ActionAppealed = foundDoc.getBoolean("ActionAppealed");
                doc.ClassificationTitle = foundDoc.getBoolean("ClassificationTitle");
                doc.ClassificationNumber = foundDoc.getBoolean("ClassificationNumber");
                doc.BarginingUnit = foundDoc.getBoolean("BarginingUnit");
                doc.AppelantAppointed = foundDoc.getBoolean("AppelantAppointed");
                doc.ProbitionaryPeriod = foundDoc.getBoolean("AppelantAppointed");
                doc.HearingDate = foundDoc.getBoolean("HearingDate");
                doc.HearingTime = foundDoc.getBoolean("HearingTime");
                doc.HearingServed = foundDoc.getBoolean("HearingServed");
                doc.MemorandumContra = foundDoc.getBoolean("MemorandumContra");
                doc.Gender = foundDoc.getBoolean("Gender");
                doc.AddressBlock = foundDoc.getBoolean("AddressBlock");
                doc.FirstLetterSent = foundDoc.getBoolean("FirstLetterSent");
                doc.CodeSection = foundDoc.getBoolean("CodeSection");
                doc.CountyName = foundDoc.getBoolean("CountyName");
                doc.StayDate = foundDoc.getBoolean("StayDate");
                doc.CasePendingResolution = foundDoc.getBoolean("CasePendingResolution");
                doc.LastUpdate = foundDoc.getBoolean("LastUpdate");
                doc.DateGranted = foundDoc.getBoolean("DateGranted");
                doc.MatterContinued = foundDoc.getBoolean("MatterContinued");
                doc.SettlementDue = foundDoc.getBoolean("SettlementDue");
                doc.FilingParty = foundDoc.getBoolean("FilingParty");
                doc.RespondingParty = foundDoc.getBoolean("RespondingParty");
                doc.RequestingParty = foundDoc.getBoolean("RequestingParty");
                doc.Deposition = foundDoc.getBoolean("Deposition");
                doc.RepHimOrHer = foundDoc.getBoolean("RepHimOrHer");
                doc.TypeOfAction = foundDoc.getBoolean("TypeOfAction");
                doc.CodeSectionFillIn = foundDoc.getBoolean("CodeSectionFillIn");
                doc.DocumentName = foundDoc.getBoolean("DocumentName");
                doc.DateFiled = foundDoc.getBoolean("DateFiled");
                doc.InfoRedacted = foundDoc.getBoolean("InfoRedacted");
                doc.RedactorName = foundDoc.getBoolean("RedactorName");
                doc.RedactorTitle = foundDoc.getBoolean("RedactorTitle");
                doc.DatePOSent = foundDoc.getBoolean("DatePOSent");
                doc.AppealType = foundDoc.getBoolean("AppealType");
                doc.AppealType2 = foundDoc.getBoolean("AppealType2");
                doc.AppealTypeUF = foundDoc.getBoolean("AppealTypeUF");
                doc.AppealTypeLS = foundDoc.getBoolean("AppealTypeLS");
                doc.RequestingPartyC = foundDoc.getBoolean("RequestingPartyC");
                doc.DateRequested = foundDoc.getBoolean("DateRequested");
                doc.PurposeOfExtension = foundDoc.getBoolean("PurposeOfExtension");

            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findDocumentByID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return doc;
    }
    
    public static CMDSDocuments findDocumentByName(String name) {
        CMDSDocuments doc = new CMDSDocuments();
        
        Statement stmt = null;
        
        try {
            
            stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from CMDSDocuments where LetterName = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, name);
            
            ResultSet foundDoc = preparedStatement.executeQuery();
            
            if(foundDoc.next()) {
                doc.ID = foundDoc.getInt("ID");
                doc.Active = foundDoc.getBoolean("Active");
                doc.MainCategory = foundDoc.getString("MainCategory");
                doc.SubCategory = foundDoc.getString("SubCategory");
                doc.LetterName = foundDoc.getString("LetterName");
                doc.Location = foundDoc.getString("Location");
                doc.MultiplePrint = foundDoc.getBoolean("MultiplePrint");
                doc.ResponseDue = foundDoc.getBoolean("ResponseDue");
                doc.ActionAppealed = foundDoc.getBoolean("ActionAppealed");
                doc.ClassificationTitle = foundDoc.getBoolean("ClassificationTitle");
                doc.ClassificationNumber = foundDoc.getBoolean("ClassificationNumber");
                doc.BarginingUnit = foundDoc.getBoolean("BarginingUnit");
                doc.AppelantAppointed = foundDoc.getBoolean("AppelantAppointed");
                doc.ProbitionaryPeriod = foundDoc.getBoolean("AppelantAppointed");
                doc.HearingDate = foundDoc.getBoolean("HearingDate");
                doc.HearingTime = foundDoc.getBoolean("HearingTime");
                doc.HearingServed = foundDoc.getBoolean("HearingServed");
                doc.MemorandumContra = foundDoc.getBoolean("MemorandumContra");
                doc.Gender = foundDoc.getBoolean("Gender");
                doc.AddressBlock = foundDoc.getBoolean("AddressBlock");
                doc.FirstLetterSent = foundDoc.getBoolean("FirstLetterSent");
                doc.CodeSection = foundDoc.getBoolean("CodeSection");
                doc.CountyName = foundDoc.getBoolean("CountyName");
                doc.StayDate = foundDoc.getBoolean("StayDate");
                doc.CasePendingResolution = foundDoc.getBoolean("CasePendingResolution");
                doc.LastUpdate = foundDoc.getBoolean("LastUpdate");
                doc.DateGranted = foundDoc.getBoolean("DateGranted");
                doc.MatterContinued = foundDoc.getBoolean("MatterContinued");
                doc.SettlementDue = foundDoc.getBoolean("SettlementDue");
                doc.FilingParty = foundDoc.getBoolean("FilingParty");
                doc.RespondingParty = foundDoc.getBoolean("RespondingParty");
                doc.RequestingParty = foundDoc.getBoolean("RequestingParty");
                doc.Deposition = foundDoc.getBoolean("Deposition");
                doc.RepHimOrHer = foundDoc.getBoolean("RepHimOrHer");
                doc.TypeOfAction = foundDoc.getBoolean("TypeOfAction");
                doc.CodeSectionFillIn = foundDoc.getBoolean("CodeSectionFillIn");
                doc.DocumentName = foundDoc.getBoolean("DocumentName");
                doc.DateFiled = foundDoc.getBoolean("DateFiled");
                doc.InfoRedacted = foundDoc.getBoolean("InfoRedacted");
                doc.RedactorName = foundDoc.getBoolean("RedactorName");
                doc.RedactorTitle = foundDoc.getBoolean("RedactorTitle");
                doc.DatePOSent = foundDoc.getBoolean("DatePOSent");
                doc.AppealType = foundDoc.getBoolean("AppealType");
                doc.AppealType2 = foundDoc.getBoolean("AppealType2");
                doc.AppealTypeUF = foundDoc.getBoolean("AppealTypeUF");
                doc.AppealTypeLS = foundDoc.getBoolean("AppealTypeLS");
                doc.RequestingPartyC = foundDoc.getBoolean("RequestingPartyC");
                doc.DateRequested = foundDoc.getBoolean("DateRequested");
                doc.PurposeOfExtension = foundDoc.getBoolean("PurposeOfExtension");

            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findDocumentByName(name);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return doc;
    }
    
    public static int CMDSQuestionCount(CMDSDocuments template){
        int count = 0;
        
        if (template.LetterName != null) {
            if (template.LetterName.equals("Appeal Notice")) {
                count += 1;
            }
        }
        if (template.ResponseDue) {
            count += 1;
        }
        if (template.Gender) {
            count += 1;
        }
        if (template.ActionAppealed) {
            count += 1;
        }
        if (template.MemorandumContra) {
            count += 1;
        }
        if (template.ClassificationTitle) {
            count += 1;
        }
        if (template.BarginingUnit) {
            count += 1;
        }
        if (template.ClassificationNumber) {
            count += 1;
        }
        if (template.AppelantAppointed) {
            count += 1;
        }
        if (template.ProbitionaryPeriod) {
            count += 1;
        }
        if (template.HearingDate) {
            count += 1;
        }
        if (template.HearingTime) {
            count += 1;
        }
        if (template.HearingTime) {
            count += 1;
        }
        if (template.AddressBlock) {
            count += 1;
        }
        if (template.FirstLetterSent) {
            count += 1;
        }
        if (template.CodeSection) {
            count += 1;
        }
        if (template.CountyName) {
            count += 1;
        }
        if (template.StayDate) {
            count += 1;
        }
        if (template.CasePendingResolution) {
            count += 1;
        }
        if (template.LastUpdate) {
            count += 1;
        }
        if (template.MatterContinued) {
            count += 1;
        }
        if (template.SettlementDue) {
            count += 1;
        }
        if (template.FilingParty) {
            count += 1;
        }
        if (template.RespondingParty) {
            count += 1;
        }
        if (template.RequestingParty) {
            count += 1;
        }
        if (template.Deposition) {
            count += 1;
        }
        if (template.RepHimOrHer) {
            count += 1;
        }
        if (template.CodeSectionFillIn) {
            count += 1;
        }
        if (template.DocumentName) {
            count += 1;
        }
        if (template.DateFiled) {
            count += 1;
        }
        if (template.InfoRedacted) {
            count += 1;
        }
        if (template.RedactorName) {
            count += 1;
        }
        if (template.RedactorTitle) {
            count += 1;
        }
        if (template.DatePOSent) {
            count += 1;
        }
        if (template.AppealType) {
            count += 1;
        }
        if (template.AppealType2) {
            count += 1;
        }
        if (template.AppealTypeUF) {
            count += 1;
        }
        if (template.AppealTypeLS) {
            count += 1;
        }
        if (template.RequestingPartyC && template.HearingTime) {
            count += 1;
        }
        if (template.DateRequested && template.HearingTime) {
            count += 1;
        }
        if (template.RequestingPartyC && template.PurposeOfExtension) {
            count += 1;
        }
        if (template.DateRequested && template.PurposeOfExtension) {
            count += 1;
        }
        if (template.PurposeOfExtension) {
            count += 1;
        }
        if (template.RequestingPartyC && !template.PurposeOfExtension && !template.HearingTime) {
            count += 1;
        }
        
        return count;
    }
        
    public static List<String> findSubCategoriesByMainCategory(String mainCat)  {
        List<String> subTypesList = new ArrayList<>();
           
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT DISTINCT subCategory FROM CMDSDocuments WHERE active = 1 "
                    + "AND mainCategory = ? ORDER BY subCategory";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            preparedStatement.setString(1, mainCat);
            
            ResultSet caseStatusRS = preparedStatement.executeQuery();
            
            while(caseStatusRS.next()) {
                subTypesList.add(caseStatusRS.getString("subCategory"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findDocumentsBySubCategories(mainCat);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return subTypesList;
    }
    
    public static List<CMDSDocuments> findDocumentsBySubCategories(String subCat)  {
        List<CMDSDocuments> list = new ArrayList<>();
           
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM CMDSDocuments WHERE active = 1 "
                    + "AND subCategory = ? ORDER BY LetterName";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            
            preparedStatement.setString(1, subCat);
            
            ResultSet rs = preparedStatement.executeQuery();
            
            while(rs.next()) {
                CMDSDocuments item = new CMDSDocuments();
                item.ID = rs.getInt("id");
                item.Active = rs.getBoolean("active");
                item.MainCategory = rs.getString("MainCategory") == null ? "" : rs.getString("MainCategory");
                item.SubCategory = rs.getString("SubCategory") == null ? "" : rs.getString("SubCategory");
                item.LetterName = rs.getString("LetterName") == null ? "" : rs.getString("LetterName");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findDocumentsBySubCategories(subCat);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }
    
    public static void createCMDSDocument(CMDSDocuments item) {
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO CMDSDocuments ("
                    + "Active, "
                    + "MainCategory, "
                    + "SubCategory, "
                    + "LetterName, "
                    + "Location, "
                    + "MultiplePrint, "
                    + "ResponseDue, "
                    + "ActionAppealed, "
                    + "ClassificationTitle, "
                    + "ClassificationNumber, "
                    + "BarginingUnit, "
                    + "AppelantAppointed, "
                    + "ProbitionaryPeriod, "
                    + "HearingDate, "
                    + "HearingTime, "
                    + "HearingServed, "
                    + "MemorandumContra, "
                    + "Gender, "
                    + "AddressBlock, "
                    + "FirstLetterSent, "
                    + "CodeSection, "
                    + "CountyName, "
                    + "StayDate, "
                    + "CasePendingResolution, "
                    + "LastUpdate, "
                    + "DateGranted, "
                    + "MatterContinued, "
                    + "SettlementDue, "
                    + "FilingParty, "
                    + "RespondingParty, "
                    + "RequestingParty, "
                    + "Deposition, "
                    + "RepHimOrHer, "
                    + "TypeOfAction, "
                    + "CodeSectionFillIn, "
                    + "DocumentName, "
                    + "DateFiled, "
                    + "InfoRedacted, "
                    + "RedactorName, "
                    + "RedactorTitle, "
                    + "DatePOSent, "
                    + "AppealType, "
                    + "AppealType2, "
                    + "AppealTypeUF, "
                    + "AppealTypeLS, "
                    + "RequestingPartyC, "
                    + "DateRequested, "
                    + "PurposeOfExtension, "
                    + "emailSubject, "
                    + "emailBody "
                    + ") VALUES (";
                    for(int i=0; i<49; i++){
                        sql += "?, ";   //01-49
                    }
                     sql += "?)"; //50

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean( 1, true);
            preparedStatement.setString ( 2, item.MainCategory.equals("") ? null : item.MainCategory.trim());
            preparedStatement.setString ( 3, item.SubCategory.equals("") ? null : item.SubCategory.trim());
            preparedStatement.setString ( 4, item.LetterName.equals("") ? null : item.LetterName.trim());
            preparedStatement.setString ( 5, item.Location.equals("") ? null : item.Location.trim());
            preparedStatement.setBoolean( 6, item.MultiplePrint);
            preparedStatement.setBoolean( 7, item.ResponseDue);
            preparedStatement.setBoolean( 8, item.ActionAppealed);
            preparedStatement.setBoolean( 9, item.ClassificationTitle);
            preparedStatement.setBoolean(10, item.ClassificationNumber);
            preparedStatement.setBoolean(11, item.BarginingUnit);
            preparedStatement.setBoolean(12, item.AppelantAppointed);
            preparedStatement.setBoolean(13, item.ProbitionaryPeriod);
            preparedStatement.setBoolean(14, item.HearingDate);
            preparedStatement.setBoolean(15, item.HearingTime);
            preparedStatement.setBoolean(16, item.HearingServed);
            preparedStatement.setBoolean(17, item.MemorandumContra);
            preparedStatement.setBoolean(18, item.Gender);
            preparedStatement.setBoolean(19, item.AddressBlock);
            preparedStatement.setBoolean(20, item.FirstLetterSent);
            preparedStatement.setBoolean(21, item.CodeSection);
            preparedStatement.setBoolean(22, item.CountyName);
            preparedStatement.setBoolean(23, item.StayDate);
            preparedStatement.setBoolean(24, item.CasePendingResolution);
            preparedStatement.setBoolean(25, item.LastUpdate);
            preparedStatement.setBoolean(26, item.DateGranted);
            preparedStatement.setBoolean(27, item.MatterContinued);
            preparedStatement.setBoolean(28, item.SettlementDue);
            preparedStatement.setBoolean(29, item.FilingParty);
            preparedStatement.setBoolean(30, item.RespondingParty);
            preparedStatement.setBoolean(31, item.RequestingParty);
            preparedStatement.setBoolean(32, item.Deposition);
            preparedStatement.setBoolean(33, item.RepHimOrHer);
            preparedStatement.setBoolean(34, item.TypeOfAction);
            preparedStatement.setBoolean(35, item.CodeSectionFillIn);
            preparedStatement.setBoolean(36, item.DocumentName);
            preparedStatement.setBoolean(37, item.DateFiled);
            preparedStatement.setBoolean(38, item.InfoRedacted);
            preparedStatement.setBoolean(39, item.RedactorName);
            preparedStatement.setBoolean(40, item.RedactorTitle);
            preparedStatement.setBoolean(41, item.DatePOSent);
            preparedStatement.setBoolean(42, item.AppealType);
            preparedStatement.setBoolean(43, item.AppealType2);
            preparedStatement.setBoolean(44, item.AppealTypeUF);
            preparedStatement.setBoolean(45, item.AppealTypeLS);
            preparedStatement.setBoolean(46, item.RequestingPartyC);
            preparedStatement.setBoolean(47, item.DateRequested);
            preparedStatement.setBoolean(48, item.PurposeOfExtension);
            preparedStatement.setString (49, item.emailSubject.equals("") ? null : item.emailSubject.trim());
            preparedStatement.setString (50, item.emailBody.equals("") ? null : item.emailBody.trim());
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createCMDSDocument(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateCMDSDocument(CMDSDocuments item) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE CMDSDocuments SET "
                    + "Active = ?, "
                    + "MainCategory = ?, "
                    + "SubCategory = ?, "
                    + "LetterName = ?, "
                    + "Location = ?, "
                    + "MultiplePrint = ?, "
                    + "ResponseDue = ?, "
                    + "ActionAppealed = ?, "
                    + "ClassificationTitle = ?, "
                    + "ClassificationNumber = ?, "
                    + "BarginingUnit = ?, "
                    + "AppelantAppointed = ?, "
                    + "ProbitionaryPeriod = ?, "
                    + "HearingDate = ?, "
                    + "HearingTime = ?, "
                    + "HearingServed = ?, "
                    + "MemorandumContra = ?, "
                    + "Gender = ?, "
                    + "AddressBlock = ?, "
                    + "FirstLetterSent = ?, "
                    + "CodeSection = ?, "
                    + "CountyName = ?, "
                    + "StayDate = ?, "
                    + "CasePendingResolution = ?, "
                    + "LastUpdate = ?, "
                    + "DateGranted = ?, "
                    + "MatterContinued = ?, "
                    + "SettlementDue = ?, "
                    + "FilingParty = ?, "
                    + "RespondingParty = ?, "
                    + "RequestingParty = ?, "
                    + "Deposition = ?, "
                    + "RepHimOrHer = ?, "
                    + "TypeOfAction = ?, "
                    + "CodeSectionFillIn = ?, "
                    + "DocumentName = ?, "
                    + "DateFiled = ?, "
                    + "InfoRedacted = ?, "
                    + "RedactorName = ?, "
                    + "RedactorTitle = ?, "
                    + "DatePOSent = ?, "
                    + "AppealType = ?, "
                    + "AppealType2 = ?, "
                    + "AppealTypeUF = ?, "
                    + "AppealTypeLS = ?, "
                    + "RequestingPartyC = ?, "
                    + "DateRequested = ?, "
                    + "PurposeOfExtension = ?, "
                    + "emailSubject = ?, "
                    + "emailBody = ? "
                    + "where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean( 1, item.Active);
            preparedStatement.setString ( 2, item.MainCategory.equals("") ? null : item.MainCategory.trim());
            preparedStatement.setString ( 3, item.SubCategory.equals("") ? null : item.SubCategory.trim());
            preparedStatement.setString ( 4, item.LetterName.equals("") ? null : item.LetterName.trim());
            preparedStatement.setString ( 5, item.Location.equals("") ? null : item.Location.trim());
            preparedStatement.setBoolean( 6, item.MultiplePrint);
            preparedStatement.setBoolean( 7, item.ResponseDue);
            preparedStatement.setBoolean( 8, item.ActionAppealed);
            preparedStatement.setBoolean( 9, item.ClassificationTitle);
            preparedStatement.setBoolean(10, item.ClassificationNumber);
            preparedStatement.setBoolean(11, item.BarginingUnit);
            preparedStatement.setBoolean(12, item.AppelantAppointed);
            preparedStatement.setBoolean(13, item.ProbitionaryPeriod);
            preparedStatement.setBoolean(14, item.HearingDate);
            preparedStatement.setBoolean(15, item.HearingTime);
            preparedStatement.setBoolean(16, item.HearingServed);
            preparedStatement.setBoolean(17, item.MemorandumContra);
            preparedStatement.setBoolean(18, item.Gender);
            preparedStatement.setBoolean(19, item.AddressBlock);
            preparedStatement.setBoolean(20, item.FirstLetterSent);
            preparedStatement.setBoolean(21, item.CodeSection);
            preparedStatement.setBoolean(22, item.CountyName);
            preparedStatement.setBoolean(23, item.StayDate);
            preparedStatement.setBoolean(24, item.CasePendingResolution);
            preparedStatement.setBoolean(25, item.LastUpdate);
            preparedStatement.setBoolean(26, item.DateGranted);
            preparedStatement.setBoolean(27, item.MatterContinued);
            preparedStatement.setBoolean(28, item.SettlementDue);
            preparedStatement.setBoolean(29, item.FilingParty);
            preparedStatement.setBoolean(30, item.RespondingParty);
            preparedStatement.setBoolean(31, item.RequestingParty);
            preparedStatement.setBoolean(32, item.Deposition);
            preparedStatement.setBoolean(33, item.RepHimOrHer);
            preparedStatement.setBoolean(34, item.TypeOfAction);
            preparedStatement.setBoolean(35, item.CodeSectionFillIn);
            preparedStatement.setBoolean(36, item.DocumentName);
            preparedStatement.setBoolean(37, item.DateFiled);
            preparedStatement.setBoolean(38, item.InfoRedacted);
            preparedStatement.setBoolean(39, item.RedactorName);
            preparedStatement.setBoolean(40, item.RedactorTitle);
            preparedStatement.setBoolean(41, item.DatePOSent);
            preparedStatement.setBoolean(42, item.AppealType);
            preparedStatement.setBoolean(43, item.AppealType2);
            preparedStatement.setBoolean(44, item.AppealTypeUF);
            preparedStatement.setBoolean(45, item.AppealTypeLS);
            preparedStatement.setBoolean(46, item.RequestingPartyC);
            preparedStatement.setBoolean(47, item.DateRequested);
            preparedStatement.setBoolean(48, item.PurposeOfExtension);
            preparedStatement.setString (49, item.emailSubject);
            preparedStatement.setString (50, item.emailBody);
            preparedStatement.setInt    (51, item.ID);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateCMDSDocument(item);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
