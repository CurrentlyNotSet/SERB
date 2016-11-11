/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterQueue;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.processMailingAddressBookmarks;
import parker.serb.fileOperations.WordToPDF;
import parker.serb.sql.Activity;
import parker.serb.sql.PostalOut;
import parker.serb.sql.PostalOutAttachment;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class postalSend {

    public static void sendPostal(int sendID) {

        //Set up Initial Merge Utility
        PDFMergerUtility ut = new PDFMergerUtility();

        //List ConversionPDFs To Delete Later
        List<String> tempPDFList = new ArrayList<>();

        // Get Letter List
        PostalOut postalEntry = PostalOut.getPostalOutByID(sendID);
        List<PostalOutAttachment> attachmentList = PostalOutAttachment.getPostalOutAttachments(sendID);

        //Set Case Path
        String casePath = Global.activityPath
                + Global.activeSection + File.separator
                + postalEntry.caseYear + File.separator
                + NumberFormatService.generateFullCaseNumberNonGlobal(
                        postalEntry.caseYear, postalEntry.caseType, postalEntry.caseMonth, postalEntry.caseNumber) + File.separator;

        //Generate Envelope Insert
        String dept = StringUtilities.getDepartment();
        String envelopeFileName = processMailingAddressBookmarks.processDoAEnvelopeInser(Global.templatePath, "EnvelopeInsert.docx", dept, postalEntry);

        //Convert Envelope
        String envelopeFilePDF = WordToPDF.createPDF(casePath, envelopeFileName);

        //Add Envelope To PDF Merge
        try {
            ut.addSource(casePath + envelopeFilePDF);
            tempPDFList.add(casePath + envelopeFilePDF);
            tempPDFList.add(casePath + envelopeFileName);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(postalSend.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Convert Attachments
        for (PostalOutAttachment attach : attachmentList) {
            if (!FilenameUtils.getExtension(attach.fileName).equals(".pdf")) {
                String attachment = WordToPDF.createPDF(casePath, attach.fileName);

                //Add Attachment To PDF Merge
                try {
                    ut.addSource(casePath + attachment);
                    tempPDFList.add(casePath + attachment);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(postalSend.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (FilenameUtils.getExtension(attach.fileName).equals(".pdf")) {
            
                //Add Attachment To PDF Merge
                try {
                    ut.addSource(casePath + attach.fileName);
                } catch (FileNotFoundException ex) {
                    Logger.getLogger(postalSend.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        //DocumentFileName
        String savedDoc = String.valueOf(new Date().getTime()) + "_" + postalEntry.historyDescription + ".pdf";

        //Set Merge File Destination
        ut.setDestinationFileName(casePath + savedDoc);

        //Try to Merge
        try {
            ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException ex) {
            Logger.getLogger(postalSend.class.getName()).log(Level.SEVERE, null, ex);
        }

        //Open File
        FileService.openFileFullPath(new File(casePath + savedDoc));

        //Clean up temp PDFs
        for (String tempPDF : tempPDFList) {
            new File(tempPDF).delete();
        }

        //Create Activity
        Activity.addActivtyFromDocket(
                "OUT - " + postalEntry.historyDescription,
                savedDoc,
                NumberFormatService.generateFullCaseNumberNonGlobal(
                        postalEntry.caseYear, postalEntry.caseType, postalEntry.caseMonth, postalEntry.caseNumber).split("-"),
                "", "", "", "", false, false);
        
        //Remove SQL Entries for Postal
        PostalOut.removeEntry(sendID);
        PostalOutAttachment.removeEntry(sendID);
        
        //Update Activity List if Available
        if (postalEntry.caseYear.equals(Global.caseYear) && 
                postalEntry.caseType.equals(Global.caseType) &&
                postalEntry.caseMonth.equals(Global.caseMonth) && 
                postalEntry.caseNumber.equals(Global.caseNumber)){
            switch (Global.activeSection) {
            case "REP":
                Global.root.getrEPRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "ULP":
                Global.root.getuLPRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "ORG":
                Global.root.getoRGRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "MED":
                Global.root.getmEDRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "Hearings":
                break;
            case "Civil Service Commission":
                Global.root.getcSCRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "CMDS":
                Global.root.getcMDSRootPanel1().getActivityPanel1().loadAllActivity();
                break;
        }
        }
    }

}
