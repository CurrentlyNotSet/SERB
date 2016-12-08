/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

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

    public static CMDSDocuments findDocumentByID(int id) {
        CMDSDocuments doc = new CMDSDocuments();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
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
            Logger.getLogger(SMDSDocuments.class.getName()).log(Level.SEVERE, null, ex);
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
    
}
