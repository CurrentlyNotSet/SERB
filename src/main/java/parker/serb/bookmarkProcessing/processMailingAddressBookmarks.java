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
import parker.serb.sql.AdministrationInformation;
import parker.serb.util.JacobCOMBridge;

/**
 *
 * @author User
 */
public class processMailingAddressBookmarks {
    
    public static String name;
    public static String address1;
    public static String address2;
    public static String city;
    public static String state;
    public static String zip;
    
    public static void processDoAULPWordLetter(String templatePath, String templateName) {
        
        AdministrationInformation sysAdminInfo = AdministrationInformation.loadAdminInfo("SERB");
                        
        //Setup Document
        String docPath = "C:\\Users\\User\\Desktop\\";
        String docName = String.valueOf(new Date().getTime()) + ".docx";
        ActiveXComponent eolWord = null;
        eolWord = JacobCOMBridge.setWordActive(true, false, eolWord);

        Dispatch Document = Dispatch.call(eolWord.getProperty("Documents").toDispatch(), "Open", templatePath + templateName).toDispatch();
        ActiveXComponent.call(eolWord.getProperty("Selection").toDispatch(), "Find").toDispatch();
                      
        String serbAddress = "";
        if (!sysAdminInfo.Address1.equals("")) {
            serbAddress += sysAdminInfo.Address1.trim();
        }
        if (!sysAdminInfo.Address2.equals("")) {
            serbAddress += " " + sysAdminInfo.Address2.trim();
        }
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
        
        processBookmark.process("REPNAME", name, Document);
        processBookmark.process("REPADDRESS1", address1, Document);
        processBookmark.process("REPADDRESS2", address2, Document);
        processBookmark.process("REPCITY", city, Document); 
        processBookmark.process("REPSTATE", state, Document);
        processBookmark.process("REPZIP", zip, Document);
        processBookmark.process("SerbAddress", serbAddress, Document);
        processBookmark.process("SerbCityStateZip", serbCityStateZip, Document);
                    
        Dispatch WordBasic = (Dispatch) Dispatch.call(eolWord, "WordBasic").getDispatch();
        if (!new File(docPath).exists()) {
            //create folder location
        }
        String newFilePath = docPath + docName;
        Dispatch.call(WordBasic, "FileSaveAs", newFilePath, new Variant(16));
        JacobCOMBridge.setWordActive(false, false, eolWord);
    }
    
}
