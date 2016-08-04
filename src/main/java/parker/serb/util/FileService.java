/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import com.alee.utils.FileUtils;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Date;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.ActivityType;
import parker.serb.sql.Audit;
import parker.serb.sql.Email;
import parker.serb.sql.EmailAttachment;
import parker.serb.sql.ULPCase;

/**
 *
 * @author parkerjohnston
 */
public class FileService {
    
    public static void setFilePath() {
        try {
            switch(InetAddress.getLocalHost().getHostName()) {
                case "Parkers-MacBook-Air.local": //Parker MBA on APE
                case "Parkers-Air": //Parker MBA on any non APE-router
                    Global.scanPath = "/Users/parkerjohnston/Desktop/SERB/Scan/";
                    Global.emailPath = "/Users/parkerjohnston/Desktop/SERB/Email/";
                    Global.activityPath = "/Users/parkerjohnston/Desktop/SERB/Activity/";
                    Global.mediaPath = "/Users/parkerjohnston/Desktop/SERB/Media/";
                    Global.templatePath = "/Users/parkerjohnston/Desktop/SERB/Template/";
                    break;
                //TODO: Add in other machines with the correct paths
                case "Alienware15":
                case "Optiplex3010":
                case "Sniper":
                    Global.scanPath = "C:\\SERB\\Scan\\";
                    Global.emailPath = "C:\\SERB\\Email\\";
                    Global.activityPath = "C:\\SERB\\Activity\\";
                    Global.mediaPath = "C:\\SERB\\Media\\";
                    Global.templatePath = "C:\\SERB\\Template\\";
                    break;
                case "NW103087":
                    Global.scanPath = "C:\\Users\\johnp10\\Desktop\\SERB\\Scan\\";
                    Global.emailPath = "C:\\Users\\johnp10\\Desktop\\SERB\\Email\\";
                    Global.activityPath = "C:\\Users\\johnp10\\Desktop\\SERB\\Activity\\";
                    Global.mediaPath = "C:\\Users\\johnp10\\Desktop\\SERB\\Media\\";
                    Global.templatePath = "C:\\Users\\johnp10\\Desktop\\SERB\\Template\\";
                    break;    
                default:
                    //SERB LOCATIONS
            }
        } catch (UnknownHostException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void openFile(String fileName) {
        try {
            Desktop.getDesktop().open(new File(Global.activityPath
                    + File.separatorChar
                    + Global.activeSection
                    + File.separatorChar
                    + Global.caseYear
                    + File.separatorChar
                    + (Global.caseYear + "-" + Global.caseType + "-" + Global.caseMonth + "-" + Global.caseNumber)
                    + File.separatorChar
                    + fileName));
        } catch (IOException | NullPointerException | IllegalArgumentException ex) {
            new FileNotFoundDialog((JFrame) Global.root.getRootPane().getParent(), true, fileName);
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void openScanFile(String fileName, String section) {
        try {
            Desktop.getDesktop().open(new File(Global.scanPath
                    + File.separatorChar
                    + section
                    + File.separatorChar
                    + fileName));
        } catch (IOException | NullPointerException | IllegalArgumentException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void openAttachmentFile(String id, String section) {
        try {
            Desktop.getDesktop().open(new File(Global.emailPath
                    + File.separatorChar
                    + section
                    + File.separatorChar
                    + EmailAttachment.getEmailAttachmentFileByID(id)));
        } catch (IOException | NullPointerException | IllegalArgumentException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void openEmailBodyFile(String id, String section) {
        try {
            Desktop.getDesktop().open(new File(Global.emailPath
                    + File.separatorChar
                    + section
                    + File.separatorChar
                    + Email.getEmailBodyFileByID(id)));
        } catch (IOException | NullPointerException | IllegalArgumentException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void docketScan(String[] caseNumbers,
            String fileName,
            String section,
            String typeAbbrv,
            String typeFull,
            String from, 
            String to,
            String comment,
            boolean redacted,
            Date activityDate) {
        
        File docketFile = new File(Global.scanPath + section + File.separatorChar + fileName);
        
        if(docketFile.exists()) {
            for (String caseNumber : caseNumbers) {
                String[] caseNumberParts = caseNumber.trim().split("-");
                File caseArchiveFile = new File(
                        Global.activityPath 
                        + section
                        + File.separatorChar
                        + caseNumberParts[0]
                        + File.separatorChar
                        + caseNumber.trim());
                
                caseArchiveFile.mkdirs();
                
                String fileDate = String.valueOf(new Date().getTime());
                
                FileUtils.copyFile(docketFile, new File(caseArchiveFile + File.separator + fileDate + "_" + typeAbbrv + (redacted ? "_REDACTED.pdf" : ".pdf")));
                Activity.addActivtyFromDocket("Filed " + typeFull + " from " + from + (redacted ? " (REDACTED)" : ""),
                        fileDate + "_" + typeAbbrv + (redacted ? "_REDACTED.pdf" : ".pdf"),
                        caseNumberParts,from, to, typeFull, comment, redacted, false, activityDate);
                Audit.addAuditEntry("Filed " + typeFull + " from " + from + (redacted ? " (REDACTED)" : ""));
                
                switch(section) {
                    case "ULP": ULPCase.ULPDocketNotification(caseNumber);
                }
            }
        }
        
        docketFile.delete();
    }
    
    //docketMedia
    public static void docketMedia(String[] caseNumbers,
            String fileName,
            String section,
            String typeAbbrv,
            String typeFull,
            String from, 
            String to,
            String comment) {
        
        File docketFile = new File(Global.mediaPath + section + File.separatorChar + fileName);
        
        if(docketFile.exists()) {
            for (String caseNumber : caseNumbers) {
                String[] caseNumberParts = caseNumber.trim().split("-");
                File caseArchiveFile = new File(
                        Global.activityPath 
                        + section
                        + File.separatorChar
                        + caseNumberParts[0]
                        + File.separatorChar
                        + caseNumber.trim());
                
                caseArchiveFile.mkdirs();
                
                String fileDate = String.valueOf(new Date().getTime());
                
                FileUtils.copyFile(docketFile, new File(caseArchiveFile + File.separator + fileDate + "_" + typeAbbrv + fileName.substring(fileName.lastIndexOf("."))));
                Activity.addActivtyFromDocket("Filed " + typeFull + " from " + from,
                        fileDate + "_" + typeAbbrv + fileName.substring(fileName.lastIndexOf(".")),
                        caseNumberParts,from, to, typeFull, comment, false, false);
                Audit.addAuditEntry("Filed " + typeFull + " from " + from);
                
                switch(section) {
                    case "ULP": ULPCase.ULPDocketNotification(caseNumber);
                }
            }
        }
        
        docketFile.delete();
    }
    
    public static void docketEmailBody(String[] caseNumbers,
            String emailID,
            String section,
            String from, 
            String to,
            String subject,
            Date activityDate) {
        
        String fileName = Email.getEmailBodyFileByID(emailID);
        
        File docketFile = new File(Global.emailPath + section + File.separatorChar + fileName);
        
        if(docketFile.exists()) {
            for (String caseNumber : caseNumbers) {
                String[] caseNumberParts = caseNumber.trim().split("-");
                File caseArchiveFile = new File(
                        Global.activityPath 
                        + section
                        + File.separatorChar
                        + caseNumberParts[0]
                        + File.separatorChar
                        + caseNumber.trim());
                
                caseArchiveFile.mkdirs();
                
                String fileDate = String.valueOf(new Date().getTime());
                
                FileUtils.copyFile(docketFile, new File(caseArchiveFile + File.separator + fileDate + "_BODY.pdf"));
                Activity.addActivtyFromDocket("Filed Email Body from " + from,
                        fileDate + "_BODY.pdf",
                        caseNumberParts,from, to, "Email Body", "", false, true, activityDate);
                Audit.addAuditEntry("Filed Email Body from " + from);
                
                switch(section) {
                    case "ULP": ULPCase.ULPDocketNotification(caseNumber);
                }
            }
        }
        docketFile.delete();
        
       
    }
    
    public static void docketEmailAttachment(String[] caseNumbers,
            String atachmentID,
            String emailID,
            String section,
            String from, 
            String to,
            String subject,
            String fileName,
            String type,
            String comment,
            Date activityDate) {
        
        File docketFile = new File(Global.emailPath + section + File.separatorChar + fileName);
        
        if(docketFile.exists()) {
            for (String caseNumber : caseNumbers) {
                String[] caseNumberParts = caseNumber.trim().split("-");
                File caseArchiveFile = new File(
                        Global.activityPath 
                        + section
                        + File.separatorChar
                        + caseNumberParts[0]
                        + File.separatorChar
                        + caseNumber.trim());
                
                caseArchiveFile.mkdirs();
                
                String fileDate = String.valueOf(new Date().getTime());
                
                String fileExtenstion = fileName.substring(fileName.lastIndexOf(".")); //. included
                
                String fullType = ActivityType.getFullType(type);
                
                FileUtils.copyFile(docketFile, new File(caseArchiveFile + File.separator + fileDate + "_" + type + fileExtenstion));
                Activity.addActivtyFromDocket("Filed " + fullType + " from " + from,
                        fileDate + "_" + type + fileExtenstion,
                        caseNumberParts, from, to, fullType, comment, false, fileExtenstion.endsWith("pdf"), activityDate);
                Audit.addAuditEntry("Filed " + fullType + " from " + from);
            }
        }
        docketFile.delete();
    }
    
    public static void renameActivtyFile(String fileName, String updatedType) {
        
        FileUtils.copyFile(new File(Global.activityPath + Global.activeSection 
                + File.separator + Global.caseYear
                + File.separator + Global.caseYear + "-" + Global.caseType
                + "-" + Global.caseMonth + "-" + Global.caseNumber
                + File.separator + fileName), new File(Global.activityPath  + Global.activeSection
                + File.separator + Global.caseYear
                + File.separator + Global.caseYear + "-" + Global.caseType
                + "-" + Global.caseMonth + "-" + Global.caseNumber
                + File.separator + fileName.split("_")[0] + "_"
                + ActivityType.getTypeAbbrv(updatedType) + "." + fileName.split("\\.")[1]));
        
        new File(Global.activityPath + Global.activeSection 
                + File.separator + Global.caseYear
                + File.separator + Global.caseYear + "-" + Global.caseType
                + "-" + Global.caseMonth + "-" + Global.caseNumber
                + File.separator + fileName).delete();
    }
}
