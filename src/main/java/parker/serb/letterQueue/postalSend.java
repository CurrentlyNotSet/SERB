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
import org.apache.commons.io.FileUtils;
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
import parker.serb.sql.PostalOutBulk;
import parker.serb.sql.PostalOutRelatedCase;
import parker.serb.sql.RelatedCase;
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
        List<PostalOutBulk> postalAddressList = PostalOutBulk.getPostalOutBulkEntries(sendID);

        //Set Case Path
        String casePath = "";
        if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                || Global.activeSection.equalsIgnoreCase("CSC")
                || Global.activeSection.equalsIgnoreCase("ORG")) {
            casePath = Global.activityPath
                    + (Global.activeSection.equals("Civil Service Commission")
                    ? postalEntry.caseType : Global.activeSection) + File.separator + postalEntry.caseNumber + File.separator;
        } else if (Global.activeSection.equals("Hearings")) {
            casePath = Global.activityPath
                    + FileService.getCaseSectionFolderByCaseType(postalEntry.caseType) + File.separatorChar
                    + postalEntry.caseYear + File.separatorChar
                    + (postalEntry.caseYear + "-" + postalEntry.caseType + "-" + postalEntry.caseMonth + "-" + postalEntry.caseNumber)
                    + File.separatorChar;
        } else {
            casePath = Global.activityPath + File.separatorChar
                    + Global.activeSection + File.separatorChar
                    + postalEntry.caseYear + File.separatorChar
                    + (postalEntry.caseYear + "-" + postalEntry.caseType + "-" + postalEntry.caseMonth + "-" + postalEntry.caseNumber)
                    + File.separatorChar;
        }

        if (0 == postalAddressList.size()){
            //Envelope Insert
            if (!(Global.activeSection.equalsIgnoreCase("CSC") || Global.activeSection.equalsIgnoreCase("Civil Service Commission"))) {
                //Generate Envelope Insert
                String envelopeFileName = processMailingAddressBookmarks.processDoAEnvelopeInsert(Global.templatePath, "EnvelopeInsert.docx", postalEntry, null);

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
                } else if (extension.equals("docx") || extension.equals("doc")) {
                    fileName = WordToPDF.createPDF(casePath, fileName);
                } else if ("txt".equals(extension)) {
                    fileName = TXTtoPDF.createPDF(casePath, fileName);
                } 

                //Add Attachment To PDF Merge
                try {
                    ut.addSource(casePath + fileName);
                    // if NOT pdf add it to the temp list for removal
                    if (!FilenameUtils.getExtension(fileName).equals("pdf")) {
                        tempPDFList.add(casePath + fileName);
                    }
                } catch (FileNotFoundException ex) {
                    SlackNotification.sendNotification(ex);
                }
            }

        } else {
            List<String> envelopePDFList = new ArrayList<>();
            List<String> attachmentPDFList = new ArrayList<>();
            
            //Envelope Insert
            if (!(Global.activeSection.equalsIgnoreCase("CSC") || Global.activeSection.equalsIgnoreCase("Civil Service Commission"))) {
                for (PostalOutBulk postalParty : postalAddressList) {
                    //Generate Envelope Insert
                    String envelopeFileName = processMailingAddressBookmarks.processDoAEnvelopeInsert(Global.templatePath, "EnvelopeInsert.docx", postalEntry, postalParty);

                    //Convert Envelope
                    String envelopeFilePDF = WordToPDF.createPDF(casePath, envelopeFileName);

                    //Add to Lists
                    envelopePDFList.add(casePath + envelopeFilePDF);
                    tempPDFList.add(casePath + envelopeFilePDF);
                    tempPDFList.add(casePath + envelopeFileName);
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
                } else if (extension.equals("docx") || extension.equals("doc")) {
                    fileName = WordToPDF.createPDF(casePath, fileName);
                } else if ("txt".equals(extension)) {
                    fileName = TXTtoPDF.createPDF(casePath, fileName);
                } 

                //Add to Lists
                attachmentPDFList.add(casePath + fileName);
                
                // if NOT pdf add it to the temp list for removal
                if (!FilenameUtils.getExtension(fileName).equals("pdf")) {
                    tempPDFList.add(casePath + fileName);
                }
            }
            
            for (String person : envelopePDFList) {
                try {
                    ut.addSource(person);
                } catch (FileNotFoundException ex) {
                    SlackNotification.sendNotification(ex);
                }

                for (String attachment : attachmentPDFList) {
                    try {
                        ut.addSource(attachment);
                    } catch (FileNotFoundException ex) {
                        SlackNotification.sendNotification(ex);
                    }
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
        
        //Copy to related case folders for MED
        if (Global.activeSection.equals("MED")) {
            List<RelatedCase> relatedMedList = RelatedCase.loadRelatedCasesNonGlobal(postalEntry.caseYear, postalEntry.caseType, postalEntry.caseMonth, postalEntry.caseNumber);
            if (relatedMedList.size() > 0) {
                for (RelatedCase related : relatedMedList) {
                    //Generate Case Number
                    String caseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(related.relatedCaseYear, related.relatedCaseType, related.relatedCaseMonth, related.relatedCaseNumber);

                    //Copy finalized document to proper folder
                    File srcFile = new File(casePath + savedDoc);

                    File destPath = new File((Global.activeSection.equalsIgnoreCase("CSC") || Global.activeSection.equalsIgnoreCase("ORG"))
                            ? FileService.getCaseFolderORGCSCLocationNonGlobal(caseNumber) : FileService.getCaseFolderLocationNonGlobal(caseNumber));
                    destPath.mkdirs();

                    try {
                        FileUtils.copyFileToDirectory(srcFile, destPath);
                    } catch (IOException ex) {
                        SlackNotification.sendNotification(ex);
                    }

                    //Add Related Case Activity Entry
                    Activity.addActivtySendPostal(
                    "OUT - " + postalEntry.historyDescription,
                    savedDoc,
                    caseNumber.split("-"),
                    "", "", "", "", false, false);
                }
            }
        } else {
            List<PostalOutRelatedCase> relatedList = PostalOutRelatedCase.getPostalOutRelatedCaseByID(sendID);
            if (relatedList.size() > 0) {
                for (PostalOutRelatedCase related : relatedList) {
                    //Generate Case Number
                    String caseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(related.caseYear, related.caseType, related.caseMonth, related.caseNumber);

                    //Copy finalized document to proper folder
                    File srcFile = new File(casePath + savedDoc);

                    File destPath = new File((Global.activeSection.equalsIgnoreCase("CSC") || Global.activeSection.equalsIgnoreCase("ORG"))
                            ? FileService.getCaseFolderORGCSCLocationNonGlobal(caseNumber) : FileService.getCaseFolderLocationNonGlobal(caseNumber));
                    destPath.mkdirs();

                    try {
                        FileUtils.copyFileToDirectory(srcFile, destPath);
                    } catch (IOException ex) {
                        SlackNotification.sendNotification(ex);
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
                                caseNumber.split("-"),
                                "", "", "", "", false, false);
                    }
                }
            }
        }
        
        //Remove SQL Entries for Postal
        PostalOut.removeEntry(sendID);
        PostalOutAttachment.removeEntry(sendID);
        PostalOutRelatedCase.deletePostalOutRelatedCaseByID(sendID);
        PostalOutBulk.removeEntry(sendID);

        return casePath + savedDoc;
    }

}
