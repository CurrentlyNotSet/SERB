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
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.processMailingAddressBookmarks;
import parker.serb.fileOperations.ImageToPDF;
import parker.serb.fileOperations.TXTtoPDF;
import parker.serb.fileOperations.WordToPDF;
import parker.serb.sql.Activity;
import parker.serb.sql.PostalOut;
import parker.serb.sql.PostalOutAttachment;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author User
 */
public class postalSend {

    public static String sendPostal(int sendID) {

        //Set up Initial Merge Utility
        PDFMergerUtility ut = new PDFMergerUtility();

        //List ConversionPDFs To Delete Later
        List<String> tempPDFList = new ArrayList<>();

        // Get Letter List
        PostalOut postalEntry = PostalOut.getPostalOutByID(sendID);
        List<PostalOutAttachment> attachmentList = PostalOutAttachment.getPostalOutAttachments(sendID);

        //Set Case Path
        String casePath = "";
        if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                    || Global.activeSection.equalsIgnoreCase("CSC")
                    || Global.activeSection.equalsIgnoreCase("ORG")) {
                casePath = Global.activityPath
                        + (Global.activeSection.equals("Civil Service Commission")
                        ? postalEntry.caseType : Global.activeSection) + File.separator + postalEntry.caseNumber + File.separator;
            } else {
                casePath = Global.activityPath + File.separatorChar
                        + Global.activeSection + File.separatorChar
                        + postalEntry.caseYear + File.separatorChar
                        + (postalEntry.caseYear + "-" + postalEntry.caseType + "-" + postalEntry.caseMonth + "-" + postalEntry.caseNumber)
                        + File.separatorChar;
            }

        //Envelope Insert
        if (!(Global.activeSection.equalsIgnoreCase("CSC") || Global.activeSection.equalsIgnoreCase("Civil Service Commission"))){
            //Generate Envelope Insert
            String envelopeFileName = processMailingAddressBookmarks.processDoAEnvelopeInsert(Global.templatePath, "EnvelopeInsert.docx", postalEntry);

            //Convert Envelope
            String envelopeFilePDF = WordToPDF.createPDF(casePath, envelopeFileName);

            //Add Envelope To PDF Merge
            try {
                ut.addSource(casePath + envelopeFilePDF);
                tempPDFList.add(casePath + envelopeFilePDF);
                tempPDFList.add(casePath + envelopeFileName);
            } catch (FileNotFoundException ex) {
                SlackNotification.sendNotification(ex);
            }
        }

        //Convert Attachments
        for (PostalOutAttachment attach : attachmentList) {
            String fileName = attach.fileName;
            String extension = FilenameUtils.getExtension(attach.fileName);

            //Convert attachments to PDF
            //If Image
            if (FileService.isImageFormat(fileName)) {
                fileName = ImageToPDF.createPDFFromImage(casePath, fileName);

                //Add Attachment To PDF Merge
                try {
                    ut.addSource(casePath + fileName);
                    tempPDFList.add(casePath + fileName);
                } catch (FileNotFoundException ex) {
                    SlackNotification.sendNotification(ex);
                }

                //If Word Doc
            } else if (extension.equals("docx") || extension.equals("doc")) {
                fileName = WordToPDF.createPDF(casePath, fileName);

                //Add Attachment To PDF Merge
                try {
                    ut.addSource(casePath + fileName);
                    tempPDFList.add(casePath + fileName);
                } catch (FileNotFoundException ex) {
                    SlackNotification.sendNotification(ex);
                }
                //If Text File
            } else if ("txt".equals(extension)) {
                fileName = TXTtoPDF.createPDF(casePath, fileName);

                //Add Attachment To PDF Merge
                try {
                    ut.addSource(casePath + fileName);
                    tempPDFList.add(casePath + fileName);
                } catch (FileNotFoundException ex) {
                    SlackNotification.sendNotification(ex);
                }

                //If PDF
            } else if (FilenameUtils.getExtension(fileName).equals("pdf")) {

                //Add Attachment To PDF Merge
                try {
                    ut.addSource(casePath + fileName);
                } catch (FileNotFoundException ex) {
                    SlackNotification.sendNotification(ex);
                }
            }
        }

        //DocumentFileName
        String savedDoc = String.valueOf(new Date().getTime()) + "_" 
                + postalEntry.historyDescription.replaceAll("[:\\\\/*?|<>]", "_") + ".pdf";

        //Set Merge File Destination
        ut.setDestinationFileName(casePath + savedDoc);

        //Try to Merge
        try {
            ut.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException ex) {
            SlackNotification.sendNotification(ex);
        }

        //Clean up temp PDFs
        for (String tempPDF : tempPDFList) {
            new File(tempPDF).delete();
        }

        //Create Activity
        if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                    || Global.activeSection.equalsIgnoreCase("CSC")
                    || Global.activeSection.equalsIgnoreCase("ORG")) {
        Activity.addActivtySendPostalORGCSC(
                "OUT - " + postalEntry.historyDescription,
                savedDoc,
                postalEntry.caseType,
                postalEntry.caseNumber,
                "", "", "", "", false, false);
        } else {
            Activity.addActivtySendPostal(
                "OUT - " + postalEntry.historyDescription,
                savedDoc,
                NumberFormatService.generateFullCaseNumberNonGlobal(
                        postalEntry.caseYear, postalEntry.caseType, postalEntry.caseMonth, postalEntry.caseNumber).split("-"),
                "", "", "", "", false, false);
        }

        //Remove SQL Entries for Postal
        PostalOut.removeEntry(sendID);
        PostalOutAttachment.removeEntry(sendID);

        //Update Activity List if Available
//        if (postalEntry.caseYear.equals(Global.caseYear)
//                && postalEntry.caseType.equals(Global.caseType)
//                && postalEntry.caseMonth.equals(Global.caseMonth)
//                && postalEntry.caseNumber.equals(Global.caseNumber)) {
//            switch (Global.activeSection) {
//                case "REP":
//                    Global.root.getrEPRootPanel1().getActivityPanel1().loadAllActivity();
//                    break;
//                case "ULP":
//                    Global.root.getuLPRootPanel1().getActivityPanel1().loadAllActivity();
//                    break;
//                case "ORG":
//                    Global.root.getoRGRootPanel1().getActivityPanel1().loadAllActivity();
//                    break;
//                case "MED":
//                    Global.root.getmEDRootPanel1().getActivityPanel1().loadAllActivity();
//                    break;
//                case "Hearings":
//                    break;
//                case "Civil Service Commission":
//                    Global.root.getcSCRootPanel1().getActivityPanel1().loadAllActivity();
//                    break;
//                case "CMDS":
//                    Global.root.getcMDSRootPanel1().getActivityPanel1().loadAllActivity();
//                    break;
//            }
//        }
        return casePath + savedDoc;
    }

}
