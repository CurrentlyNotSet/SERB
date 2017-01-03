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
import parker.serb.sql.CMDSDocuments;
import parker.serb.sql.SystemExecutive;
import parker.serb.util.JacobCOMBridge;
import parker.serb.util.NumberFormatService;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class generateDocument {
    
    public static String generateSMDSdocument(SMDSDocuments template, int senderID, List<Integer> toParties, List<Integer> ccParties){
        String saveDocName = null;
        ActiveXComponent eolWord = null;
        eolWord = JacobCOMBridge.setWordActive(true, false, eolWord);
        if (eolWord != null){
            //Setup Document
            File docPath = new File(Global.activityPath
                    + Global.activeSection + File.separator
                    + Global.caseYear + File.separator
                    + NumberFormatService.generateFullCaseNumber());
            docPath.mkdirs();
            saveDocName = String.valueOf(new Date().getTime()) + "_" 
                    + (template.historyFileName == null ? template.description : template.historyFileName)
                    + ".docx";

            Dispatch document = Dispatch.call(eolWord.getProperty("Documents").toDispatch(), "Open",
                    Global.templatePath + template.section + File.separator + template.fileName).toDispatch();
            ActiveXComponent.call(eolWord.getProperty("Selection").toDispatch(), "Find").toDispatch();

            //section
            if (null != Global.activeSection) { 
                switch (Global.activeSection) {
                    case "ULP":
                        document = defaultSMDSBookmarks(document, template.dueDate);
                        document = processULPbookmarks.processDoAULPWordLetter(document, toParties, ccParties);
                        break;
                    case "REP":
                        document = defaultSMDSBookmarks(document, template.dueDate);
                        document = processREPbookmarks.processDoAREPWordLetter(document, senderID, toParties, ccParties);
                        break;
                    case "MED":
                        document = defaultSMDSBookmarks(document, template.dueDate);
                        document = processMEDbookmarks.processDoAMEDWordLetter(document, toParties, ccParties);
                        break;
                    case "ORG":
                        document = defaultSMDSBookmarks(document, template.dueDate);
                        document = processORGbookmarks.processDoAORGWordLetter(document, true, toParties, ccParties);
                        break;
                    case "CSC":
                        document = defaultCMDSBookmarks(document);
                        document = processCSCbookmarks.processDoACSCWordLetter(document, toParties, ccParties);
                        break;
                    default:
                        break;
                }
            }

            Dispatch WordBasic = (Dispatch) Dispatch.call(eolWord, "WordBasic").getDispatch();
            String newFilePath = docPath + File.separator + saveDocName;
            Dispatch.call(WordBasic, "FileSaveAs", newFilePath, new Variant(16));
            JacobCOMBridge.setWordActive(false, false, eolWord);
        }

        return saveDocName;
    }

    public static String generateCMDSdocument(CMDSDocuments template, questionsCMDSModel answers, int senderID, List<Integer> toParties, List<Integer> ccParties){
        String saveDocName = null;
        ActiveXComponent eolWord = null;
        eolWord = JacobCOMBridge.setWordActive(true, false, eolWord);
        if (eolWord != null){
            //Setup Document
            File docPath = new File(Global.activityPath
                    + Global.activeSection + File.separator
                    + Global.caseYear + File.separator
                    + NumberFormatService.generateFullCaseNumber());
            docPath.mkdirs();
            saveDocName = String.valueOf(new Date().getTime()) + "_" + template.LetterName + ".docx";

            Dispatch document = Dispatch.call(eolWord.getProperty("Documents").toDispatch(), "Open",
                    Global.templatePath + Global.activeSection + File.separator + template.Location).toDispatch();
            ActiveXComponent.call(eolWord.getProperty("Selection").toDispatch(), "Find").toDispatch();

            //section
            if (null != Global.activeSection) { 
                switch (Global.activeSection) {
                    case "CMDS":
                        document = defaultCMDSBookmarks(document);
                        document = processCMDSbookmarks.processDoACMDSWordLetter(document, template, answers, toParties, ccParties);
                        break;
                    default:
                        break;
                }
            }

            Dispatch WordBasic = (Dispatch) Dispatch.call(eolWord, "WordBasic").getDispatch();
            String newFilePath = docPath + File.separator + saveDocName;
            Dispatch.call(WordBasic, "FileSaveAs", newFilePath, new Variant(16));
            JacobCOMBridge.setWordActive(false, false, eolWord);
        }

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
        String personnelAddressBlock = "";
        
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
            serbAddress += ", " + sysAdminInfo.Address2.trim();
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
              
        if (!chairmanFullName.trim().equals("")) {
            personnelAddressBlock += chairmanFullName + ", Chair";
        }
        if (personnelAddressBlock.trim().equals("")) {
            personnelAddressBlock += "\n";
        }
        if (!viceChairmanFullName.trim().equals("")) {
            personnelAddressBlock += viceChairmanFullName + ", Vice Chair";
        }
        if (personnelAddressBlock.trim().equals("")) {
            personnelAddressBlock += "\n";
        }
        if (!boardMemberFullName.trim().equals("")) {
            personnelAddressBlock += boardMemberFullName + ", Board Member";
        }
        if (personnelAddressBlock.trim().equals("")) {
            personnelAddressBlock += "\n\n";
        }
        if (!executiveDirectorFullName.trim().equals("")) {
            personnelAddressBlock += executiveDirectorFullName + ", Executive Director";
        }
        
        //ProcessBookmarks
        for (int i = 0; i < Global.BOOKMARK_LIMIT; i++) {
            //System Executives
            processBookmark.process("BOARDCHAIRLASTNAME" + (i == 0 ? "" : i), chairmanLastName, Document);
            processBookmark.process("BOARDVICECHAIRLASTNAME" + (i == 0 ? "" : i), viceChairmanLastName, Document);
            processBookmark.process("BOARDMEMBERLASTNAME" + (i == 0 ? "" : i), boardMemberLastName, Document);
            processBookmark.process("BOARDCHAIRMANNAME" + (i == 0 ? "" : i), chairmanFullName, Document); 
            
            processBookmark.process("HeaderChairmanName" + (i == 0 ? "" : i), chairmanFullName + ", Chair", Document);
            processBookmark.process("HeaderViceChairmanName" + (i == 0 ? "" : i), viceChairmanFullName + ", Vice Chair", Document);
            processBookmark.process("HeaderBoardMemberName" + (i == 0 ? "" : i), boardMemberFullName + ", Board Member", Document);
            processBookmark.process("HeaderExecutiveDirectorName" + (i == 0 ? "" : i), executiveDirectorFullName + ", Executive Director", Document);
            
            processBookmark.process("HeaderPersonnelBlock" + (i == 0 ? "" : i), personnelAddressBlock, Document); //No Governor
            
            //System Administration Information
            processBookmark.process("GovernorName" + (i == 0 ? "" : i), sysAdminInfo.governorName + ", Governor", Document);
            processBookmark.process("LtGovernorName" + (i == 0 ? "" : i), sysAdminInfo.LtGovernorName, Document);
            processBookmark.process("SerbAddress" + (i == 0 ? "" : i), serbAddress, Document);
            processBookmark.process("SerbCityStateZip" + (i == 0 ? "" : i), serbCityStateZip, Document);
            processBookmark.process("SerbURL" + (i == 0 ? "" : i), sysAdminInfo.Url, Document);
            
            processBookmark.process("SerbFooter" + (i == 0 ? "" : i), sysAdminInfo.Footer, Document);
            processBookmark.process("SerbPhone" + (i == 0 ? "" : i), sysAdminInfo.Phone == null ? "" 
                    : "Tel:" + NumberFormatService.convertStringToPhoneNumber(sysAdminInfo.Phone), Document);
            processBookmark.process("SerbFax" + (i == 0 ? "" : i), sysAdminInfo.Fax == null ? "" 
                    : "Fax:" + NumberFormatService.convertStringToPhoneNumber(sysAdminInfo.Fax), Document);
            
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
    
    private static Dispatch defaultCMDSBookmarks(Dispatch Document){
        //get basic information
        List<SystemExecutive> execsList = SystemExecutive.loadExecs("SPBR");
        AdministrationInformation sysAdminInfo = AdministrationInformation.loadAdminInfo("SPBR");
                
        String pbrAddress = "";
        String pbrCityStateZip = "";
        String chairmanFullName = "";
        String chairmanLastName = "";
        String viceChairmanFullName = "";
        String viceChairmanLastName = "";
        String boardMemberFullName = "";
        String boardMemberLastName = "";
        String executiveDirectorFullName = "";
        String executiveDirectorLastName = "";
        String chiefAdminLawJudgeFullName = "";
        String chiefAdminLawJudgeLastName = "";
        String personnelAddressBlock = "";
        
        for (SystemExecutive exec : execsList){
            if (null != exec.position)switch (exec.position) {
                case "Chairman":
                    chairmanFullName = StringUtilities.buildFullName(exec.firstName, exec.middleName, exec.lastName);
                    chairmanLastName = exec.lastName;
                    break;
                case "ViceChairman":
                    viceChairmanFullName = StringUtilities.buildFullName(exec.firstName, exec.middleName, exec.lastName);
                    viceChairmanLastName = exec.lastName;
                    break;
                case "BoardMember":
                    boardMemberFullName = StringUtilities.buildFullName(exec.firstName, exec.middleName, exec.lastName);
                    boardMemberLastName = exec.lastName;
                    break;
                case "ExecutiveDirector":
                    executiveDirectorFullName = StringUtilities.buildFullName(exec.firstName, exec.middleName, exec.lastName);
                    executiveDirectorLastName = exec.lastName;
                    break;
                case "ChiefAdministratorLawJudge":
                    chiefAdminLawJudgeFullName = StringUtilities.buildFullName(exec.firstName, exec.middleName, exec.lastName);
                    chiefAdminLawJudgeLastName = exec.lastName;
                    break;
                default:
                    break;
            }
        }
        
        if (!sysAdminInfo.Address1.equals("")) {
            pbrAddress += sysAdminInfo.Address1.trim();
        }
        if (!sysAdminInfo.Address2.equals("")) {
            pbrAddress += " " + sysAdminInfo.Address2.trim();
        }
        if (!sysAdminInfo.City.equals("")) {
            pbrCityStateZip += sysAdminInfo.City.trim();
        }
        if (!sysAdminInfo.State.equals("")) {
            pbrCityStateZip += ", " + sysAdminInfo.State.trim();
        }
        if (!sysAdminInfo.Zip.equals("")) {
            pbrCityStateZip += " " + sysAdminInfo.Zip.trim();
        }
                
        //Date Calculation
        Date today = new Date();
        Calendar c = Calendar.getInstance();
        
        c.setTime(today);
        c.add(Calendar.DATE, 7);
        Date sevenDayOut = c.getTime();
        c.setTime(today);
        c.add(Calendar.DATE, 15);
        Date Date2WeeksFromNow = c.getTime();
        c.setTime(today);
        c.add(Calendar.DATE, 15);
        Date fifteenDayOut = c.getTime();
        c.setTime(today);
        c.add(Calendar.DATE, 21);
        Date twentyOneDayOut = c.getTime();
        
        
        if (!chairmanFullName.trim().equals("")) {
            personnelAddressBlock += chairmanFullName + ", Chair";
        }
        if (personnelAddressBlock.trim().equals("")) {
            personnelAddressBlock += "\n";
        }
        if (!viceChairmanFullName.trim().equals("")) {
            personnelAddressBlock += viceChairmanFullName + ", Vice Chair";
        }
        if (personnelAddressBlock.trim().equals("")) {
            personnelAddressBlock += "\n";
        }
        if (!boardMemberFullName.trim().equals("")) {
            personnelAddressBlock += boardMemberFullName + ", Board Member";
        }
        if (personnelAddressBlock.trim().equals("")) {
            personnelAddressBlock += "\n\n";
        }
        if (!executiveDirectorFullName.trim().equals("")) {
            personnelAddressBlock += executiveDirectorFullName + ", Executive Director";
        } 
        if (personnelAddressBlock.trim().equals("")) {
            personnelAddressBlock += "\n";
        }
        if (!chiefAdminLawJudgeFullName.trim().equals("")) {
            personnelAddressBlock += chiefAdminLawJudgeFullName + ", Chief Administrative Law Judge";
        }
        
        
        //ProcessBookmarks
        for (int i = 0; i < Global.BOOKMARK_LIMIT; i++) {
            //System Executives            
            processBookmark.process("ChairmanName" + (i == 0 ? "" : i), chairmanFullName, Document);
            processBookmark.process("ChairmanLastName" + (i == 0 ? "" : i), chairmanLastName, Document);
            processBookmark.process("ViceChairmanName" + (i == 0 ? "" : i), viceChairmanFullName, Document);
            processBookmark.process("ViceChairmanLastName" + (i == 0 ? "" : i), viceChairmanLastName, Document);
            processBookmark.process("BoardmanName" + (i == 0 ? "" : i), boardMemberFullName, Document);
            processBookmark.process("BoardmanLastName" + (i == 0 ? "" : i), boardMemberLastName, Document);
            processBookmark.process("ExecutativeDirectorName" + (i == 0 ? "" : i), executiveDirectorFullName, Document);
            processBookmark.process("ExecutativeDirectorLastName" + (i == 0 ? "" : i), executiveDirectorLastName, Document);
            processBookmark.process("ChiefAdminLawJudgeName" + (i == 0 ? "" : i), chiefAdminLawJudgeFullName, Document);
            processBookmark.process("ChiefAdminLawJudgeLastName" + (i == 0 ? "" : i), chiefAdminLawJudgeLastName, Document);
            
            processBookmark.process("HeaderPersonnelBlock" + (i == 0 ? "" : i), personnelAddressBlock, Document); //No Governor
            
            //System Administration Information
            processBookmark.process("GovernorName" + (i == 0 ? "" : i), sysAdminInfo.governorName, Document);
            processBookmark.process("LtGovernorName" + (i == 0 ? "" : i), sysAdminInfo.LtGovernorName, Document);
            processBookmark.process("PBRAddress" + (i == 0 ? "" : i), pbrAddress, Document);
            processBookmark.process("PBRCityStateZip" + (i == 0 ? "" : i), pbrCityStateZip, Document);
            processBookmark.process("PBRURL" + (i == 0 ? "" : i), sysAdminInfo.Url, Document);
            
            processBookmark.process("PBRPhone" + (i == 0 ? "" : i), sysAdminInfo.Phone, Document);
            processBookmark.process("PBRFooter" + (i == 0 ? "" : i), sysAdminInfo.Footer, Document);
            processBookmark.process("PBRFax" + (i == 0 ? "" : i), sysAdminInfo.Fax, Document);
            
            //Made up stuff
            processBookmark.process("TODAYSDATE" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(new Date()), Document);
            processBookmark.process("Maileddate" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(new Date()), Document);
            processBookmark.process("CurrentDate" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(new Date()), Document);
            processBookmark.process("DateMatterCameOn" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(new Date()), Document);            
            processBookmark.process("DAY" + (i == 0 ? "" : i), Calendar.getInstance().get(Calendar.DAY_OF_MONTH) 
                    + Global.daySuffixes[Calendar.getInstance().get(Calendar.DAY_OF_MONTH)], Document);
            processBookmark.process("MONTH" + (i == 0 ? "" : i), Global.MMMMM.format(new Date()), Document);
            processBookmark.process("Year" + (i == 0 ? "" : i), Global.yyyy.format(new Date()), Document);
            processBookmark.process("PLUS7" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(sevenDayOut), Document);
            processBookmark.process("Date2WeeksFromNow" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(Date2WeeksFromNow), Document);
            processBookmark.process("PLUS15" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(fifteenDayOut), Document);
            processBookmark.process("PLUS21" + (i == 0 ? "" : i), Global.MMMMddyyyy.format(twentyOneDayOut), Document);
        }
        
        return Document;
    }
}
