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
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;
import parker.serb.sql.Activity;

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
                    break;
                //TODO: Add in other machines with the correct paths
                case "Alienware15":
                case "Sniper":
                    Global.scanPath = "C:\\SERB\\Scan\\";
                    Global.emailPath = "C:\\SERB\\Email\\";
                    Global.activityPath = "C:\\SERB\\Activity\\";
                    Global.mediaPath = "C:\\SERB\\Media\\";
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
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(Global.activityPath
                    + File.separatorChar
                    + Global.activeSection
                    + File.separatorChar
                    + Global.caseYear
                    + File.separatorChar
                    + (Global.caseYear + "-" + Global.caseType + "-" + Global.caseMonth + "-" + Global.caseNumber)
                    + File.separatorChar
                    + fileName));
        } catch (IOException | NullPointerException | IllegalArgumentException ex) {
            SlackNotification.sendNotification(ex.getMessage());
        }
    }
    
    public static void openScanFile(String fileName, String section) {
        try {
            Desktop desktop = Desktop.getDesktop();
            desktop.open(new File(Global.scanPath
                    + File.separatorChar
                    + section
                    + File.separatorChar
                    + fileName));
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
            boolean redacted) {
        
        File docketFile = new File(Global.scanPath + section + File.separatorChar + fileName);
        
        if(docketFile.exists()) {
            for (String caseNumber : caseNumbers) {
                String[] caseNumberParts = caseNumber.split("-");
                File caseArchiveFile = new File(
                        Global.activityPath 
                        + section
                        + File.separatorChar
                        + caseNumberParts[0]
                        + File.separatorChar
                        + caseNumber);
                
                caseArchiveFile.mkdirs();
                
                String fileDate = String.valueOf(new Date().getTime());
                
                FileUtils.copyFile(docketFile, new File(caseArchiveFile + File.separator + fileDate + "_" + typeAbbrv + (redacted ? "_REDACTED.pdf" : ".pdf")));
                Activity.addActivtyFromDocket("Filed " + typeFull + " from " + from + (redacted ? " (REDACTED)" : ""),
                        fileDate + "_" + typeAbbrv + (redacted ? "_REDACTED.pdf" : ".pdf"),
                        caseNumberParts,from, to, typeFull, comment, redacted, true);
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
                String[] caseNumberParts = caseNumber.split("-");
                File caseArchiveFile = new File(
                        Global.activityPath 
                        + section
                        + File.separatorChar
                        + caseNumberParts[0]
                        + File.separatorChar
                        + caseNumber);
                
                caseArchiveFile.mkdirs();
                
                String fileDate = String.valueOf(new Date().getTime());
                
                FileUtils.copyFile(docketFile, new File(caseArchiveFile + File.separator + fileDate + "_" + typeAbbrv + fileName.substring(fileName.lastIndexOf("."))));
                Activity.addActivtyFromDocket("Filed " + typeFull + " from " + from,
                        fileDate + "_" + typeAbbrv + fileName.substring(fileName.lastIndexOf(".")),
                        caseNumberParts,from, to, typeFull, comment, false, false);
            }
        }
        
        docketFile.delete();
    }
}
