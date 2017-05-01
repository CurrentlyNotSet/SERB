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
import java.util.Date;
import parker.serb.Global;
import parker.serb.fileOperations.WordToPDF;
import parker.serb.sql.AdministrationInformation;
import parker.serb.sql.CaseParty;
import parker.serb.sql.PostalOut;
import parker.serb.util.JacobCOMBridge;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class processMailingAddressBookmarks {

    public static String processDoAEnvelopeInsert(String templatePath, String templateName, PostalOut item) {
        String dept = StringUtilities.getDepartment();
        AdministrationInformation sysAdminInfo = AdministrationInformation.loadAdminInfo(dept);

        //Setup Document
        String casePath = "";
        if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                    || Global.activeSection.equalsIgnoreCase("CSC")
                    || Global.activeSection.equalsIgnoreCase("ORG")) {
                casePath = Global.activityPath
                        + (Global.activeSection.equals("Civil Service Commission")
                        ? item.caseType : Global.activeSection) + File.separator + item.caseNumber + File.separator;
            } else {
                casePath = Global.activityPath + File.separatorChar
                        + Global.activeSection + File.separatorChar
                        + item.caseYear + File.separatorChar
                        + (item.caseYear + "-" + item.caseType + "-" + item.caseMonth + "-" + item.caseNumber)
                        + File.separatorChar;
            }


        File docPath = new File(casePath);
        docPath.mkdirs();
        String saveDocName = String.valueOf(new Date().getTime()) + "_Envelope" + ".docx";
        saveDocName = saveDocName.replaceAll("[:\\\\/*?|<>]", "_");

        ActiveXComponent eolWord = null;
        eolWord = JacobCOMBridge.setWordActive(true, false, eolWord);

        Dispatch Document = Dispatch.call(eolWord.getProperty("Documents").toDispatch(), "Open", templatePath + "ALL" + File.separator + dept + templateName).toDispatch();
        ActiveXComponent.call(eolWord.getProperty("Selection").toDispatch(), "Find").toDispatch();

        String serbAddress = "";
        if (!sysAdminInfo.Address1.equals("")) {
            serbAddress += sysAdminInfo.Address1.trim();
        }
        if (!sysAdminInfo.Address2.equals("")) {
            serbAddress += "/n" + sysAdminInfo.Address2.trim();
        }

        serbAddress += System.lineSeparator();

        String serbCityStateZip = "";
        if (!sysAdminInfo.City.equals("")) {
            serbAddress += sysAdminInfo.City.trim();
        }
        if (!sysAdminInfo.State.equals("")) {
            serbAddress += ", " + sysAdminInfo.State.trim();
        }
        if (!sysAdminInfo.Zip.equals("")) {
            serbAddress += " " + sysAdminInfo.Zip.trim();
        }

        String name = "";
        name += item.person.trim() + System.lineSeparator();
        if (!item.addressBlock.equals("")) {
            name += item.addressBlock.trim();
        }

        processBookmark.process("SerbAddress", serbAddress, Document);
        processBookmark.process("SerbCityStateZip", serbCityStateZip, Document);
        processBookmark.process("Address", name, Document);

        Dispatch WordBasic = (Dispatch) Dispatch.call(eolWord, "WordBasic").getDispatch();
        String newFilePath = docPath + File.separator + saveDocName;
        Dispatch.call(WordBasic, "FileSaveAs", newFilePath, new Variant(16));
        JacobCOMBridge.setWordActive(false, false, eolWord);

        return saveDocName;
    }

    public static String processSingleEnvelopeInsert(String templatePath, String templateName, CaseParty item) {
        String dept = StringUtilities.getDepartment();
        AdministrationInformation sysAdminInfo = AdministrationInformation.loadAdminInfo(dept);

        //Setup Document
        File docPath = new File(System.getProperty("java.io.tmpdir"));
        docPath.mkdirs();
        String saveDocName = String.valueOf(new Date().getTime()) + "_Envelope" + ".docx";
        saveDocName = saveDocName.replaceAll("[:\\\\/*?|<>]", "_");

        ActiveXComponent eolWord = null;
        eolWord = JacobCOMBridge.setWordActive(true, false, eolWord);

        Dispatch Document = Dispatch.call(eolWord.getProperty("Documents").toDispatch(), "Open", templatePath + "ALL" + File.separator + dept + templateName).toDispatch();
        ActiveXComponent.call(eolWord.getProperty("Selection").toDispatch(), "Find").toDispatch();

        String serbAddress = "";
        if (!sysAdminInfo.Address1.equals("")) {
            serbAddress += sysAdminInfo.Address1.trim();
        }
        if (!sysAdminInfo.Address2.equals("")) {
            serbAddress += " " + sysAdminInfo.Address2.trim();
        }

        serbAddress += System.lineSeparator();

        String serbCityStateZip = "";
        if (!sysAdminInfo.City.equals("")) {
            serbAddress += sysAdminInfo.City.trim();
        }
        if (!sysAdminInfo.State.equals("")) {
            serbAddress += ", " + sysAdminInfo.State.trim();
        }
        if (!sysAdminInfo.Zip.equals("")) {
            serbAddress += " " + sysAdminInfo.Zip.trim();
        }

        String name = StringUtilities.buildAddressBlockWithLineBreaks(item);

        processBookmark.process("SerbAddress", serbAddress, Document);
        processBookmark.process("SerbCityStateZip", serbCityStateZip, Document);
        processBookmark.process("Address", name, Document);

        Dispatch WordBasic = (Dispatch) Dispatch.call(eolWord, "WordBasic").getDispatch();
        String newFilePath = docPath + File.separator + saveDocName;
        Dispatch.call(WordBasic, "FileSaveAs", newFilePath, new Variant(16));
        JacobCOMBridge.setWordActive(false, false, eolWord);

        //Convert Envelope
        return WordToPDF.createPDF(docPath.toString() + File.separator, saveDocName);

    }

}
