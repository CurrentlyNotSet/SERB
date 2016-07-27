/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import java.io.File;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.AdministrationInformation;
import parker.serb.sql.SMDSDocuments;
import parker.serb.sql.SystemExecutive;
import parker.serb.util.JacobCOMBridge;
import parker.serb.util.NumberFormatService;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class generateDocument {
    
    public static String generateSMDSdocument(String documentDescription, int senderID){
        //Setup Document
        SMDSDocuments template = SMDSDocuments.findDocumentByDescription(documentDescription);
        File docPath = new File(Global.activityPath
                + Global.activeSection + File.separator
                + Global.caseYear + File.separator
                + NumberFormatService.generateFullCaseNumber());
        docPath.mkdirs();
        String saveDocName = String.valueOf(new Date().getTime()) + "_" + template.type + ".docx";
        ActiveXComponent eolWord = null;
        eolWord = JacobCOMBridge.setWordActive(true, false, eolWord);

        Dispatch document = Dispatch.call(eolWord.getProperty("Documents").toDispatch(), "Open",
                Global.templatePath + template.section + File.separator + template.fileName).toDispatch();
        ActiveXComponent.call(eolWord.getProperty("Selection").toDispatch(), "Find").toDispatch();
        
        //section
        if (null != Global.activeSection) { 
            switch (Global.activeSection) {
                case "ULP":
                    document = defaultSMDSBookmarks(document, template.dueDate);
                    document = processULPbookmarks.processDoAULPWordLetter(document);
                    break;
                case "REP":
                    document = defaultSMDSBookmarks(document, template.dueDate);
                    document = processREPbookmarks.processDoAREPWordLetter(document, senderID);
                    break;
                case "MED":
                    document = defaultSMDSBookmarks(document, template.dueDate);
//                    document = processMEDbookmarks.processDoAMEDWordLetter(document);
                    break;
                case "ORG":
                    document = defaultSMDSBookmarks(document, template.dueDate);
                    break;
                default:
                    break;
            }
        }

        Dispatch WordBasic = (Dispatch) Dispatch.call(eolWord, "WordBasic").getDispatch();
        String newFilePath = docPath + File.separator + saveDocName;
        Dispatch.call(WordBasic, "FileSaveAs", newFilePath, new Variant(16));
        JacobCOMBridge.setWordActive(false, false, eolWord);

        return saveDocName;
    }

    private static Dispatch defaultSMDSBookmarks(Dispatch Document, int docDue){
        //get basic information
        List<SystemExecutive> execsList = SystemExecutive.loadExecs("SERB");
        AdministrationInformation sysAdminInfo = AdministrationInformation.loadAdminInfo("SERB");
                
        String serbAddress = "";
        String serbCityStateZip = "";
        String chairmanLastName = "";
        String chairmanFullName = "";
        String viceChairmanLastName = "";
        String viceChairmanFullName = "";
        String boardMemberLastName = "";
        String boardMemberFullName = "";
        String executiveDirectorFullName = "";
        
        for (SystemExecutive exec : execsList){
            if (null != exec.position)switch (exec.position) {
                case "Chairman":
                    chairmanLastName = exec.lastName;
                    chairmanFullName = StringUtilities.buildFullName(exec.firstName, exec.middleName, exec.lastName);
                    break;
                case "ViceChairman":
                    viceChairmanLastName = exec.lastName;
                    viceChairmanFullName = StringUtilities.buildFullName(exec.firstName, exec.middleName, exec.lastName);
                    break;
                case "BoardMember":
                    boardMemberLastName = exec.lastName;
                    boardMemberFullName = StringUtilities.buildFullName(exec.firstName, exec.middleName, exec.lastName);
                    break;
                case "ExecutiveDirector":
                    executiveDirectorFullName = StringUtilities.buildFullName(exec.firstName, exec.middleName, exec.lastName);
                    break;
                default:
                    break;
            }
        }
        
        if (!sysAdminInfo.Address1.equals("")) {
            serbAddress += sysAdminInfo.Address1.trim();
        }
        if (!sysAdminInfo.Address2.equals("")) {
            serbAddress += " " + sysAdminInfo.Address2.trim();
        }
        if (!sysAdminInfo.City.equals("")) {
            serbCityStateZip += sysAdminInfo.City.trim();
        }
        if (!sysAdminInfo.State.equals("")) {
            serbCityStateZip += ", " + sysAdminInfo.State.trim();
        }
        if (!sysAdminInfo.Zip.equals("")) {
            serbCityStateZip += " " + sysAdminInfo.Zip.trim();
        }
        
        //DueDate Calculation
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(today); // Now use today date.
        c.add(Calendar.DATE, docDue);
        
        if(c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            c.add(Calendar.DATE, 2);
        } else if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){
            c.add(Calendar.DATE, 1);
        }
        Date dueDate = c.getTime();
        
        //Date Calculations
        c.setTime(today);
        c.add(Calendar.DATE, 7);
        Date sevenDayOut = c.getTime();
        c.setTime(today);
        c.add(Calendar.DATE, 14);
        Date fourteenDayOut = c.getTime();
        c.setTime(today);
        c.add(Calendar.DATE, 21);
        Date twentyOneDayOut = c.getTime();
        
               
        //ProcessBookmarks
        for (int i = 0; i < Global.bookmarkLimit; i++) {
            //System Executives
            processBookmark.process("BOARDCHAIRLASTNAME" + (i == 0 ? "" : i), chairmanLastName, Document);
            processBookmark.process("BOARDVICECHAIRLASTNAME" + (i == 0 ? "" : i), viceChairmanLastName, Document);
            processBookmark.process("BOARDMEMBERLASTNAME" + (i == 0 ? "" : i), boardMemberLastName, Document);
            processBookmark.process("BOARDCHAIRMANNAME" + (i == 0 ? "" : i), chairmanFullName, Document); 
            
            processBookmark.process("HeaderChairmanName" + (i == 0 ? "" : i), chairmanFullName, Document);
            processBookmark.process("HeaderViceChairmanName" + (i == 0 ? "" : i), viceChairmanFullName, Document);
            processBookmark.process("HeaderBoardMemberName" + (i == 0 ? "" : i), boardMemberFullName, Document);
            processBookmark.process("HeaderExecutiveDirectorName" + (i == 0 ? "" : i), executiveDirectorFullName, Document);
            
            //System Administration Information
            processBookmark.process("GovernorName" + (i == 0 ? "" : i), sysAdminInfo.governorName, Document);
            processBookmark.process("LtGovernorName" + (i == 0 ? "" : i), sysAdminInfo.LtGovernorName, Document);
            processBookmark.process("SerbAddress" + (i == 0 ? "" : i), serbAddress, Document);
            processBookmark.process("SerbCityStateZip" + (i == 0 ? "" : i), serbCityStateZip, Document);
            
            //Made up stuff
            processBookmark.process("TODAYSDATE" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(new Date()), Document);
            processBookmark.process("DAY" + (i == 0 ? "" : i), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) 
                    + Global.daySuffixes[Calendar.getInstance().get(Calendar.DAY_OF_MONTH)], Document);
            processBookmark.process("MONTH" + (i == 0 ? "" : i), Global.MMMMM.format(new Date()), Document);
            processBookmark.process("YEAR" + (i == 0 ? "" : i), Global.yyyy.format(new Date()), Document);
            processBookmark.process("DUEDATE" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(dueDate), Document);
            processBookmark.process("PLUS7" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(sevenDayOut), Document);
            processBookmark.process("PLUS14" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(fourteenDayOut), Document);
            processBookmark.process("PLUS21" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(twentyOneDayOut), Document);
        }
        
        return Document;
    }
    
}
