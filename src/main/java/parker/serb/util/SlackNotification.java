/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import in.ashwanthkumar.slack.webhook.Slack;
import in.ashwanthkumar.slack.webhook.SlackMessage;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;

//TODO: Update file to Slack (DB Backup)

/**
 *
 * @author parkerjohnston
 */
public class SlackNotification {
    
    public static void enableNotifications() {
        try {
            switch(InetAddress.getLocalHost().getHostName()) {
                case "Parkers-MacBook-Air.local":
                case "Alienware15":
                case "Sniper":
                    Global.errorNotifications = true;
                    break;
                default:
                    Global.errorNotifications = false;
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(FileService.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void sendNotification(Exception ex) {
        
        String message = "User: " + Global.activeUser.username + "\n";
        message += "Class Name: " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n";
        message += "Method Name: " + Thread.currentThread().getStackTrace()[2].getMethodName()+ "\n";
        message += "Exception Type: " + ex.getClass().getSimpleName() + "\n";
        message += "Stack Trace: " + convertStackTrace(ex);
        
        try {
            new Slack(Global.slackHook)
                    .icon(Global.slackIcon) // Ref - http://www.emoji-cheat-sheet.com/
                    .sendToChannel(Global.slackChannel)
                    .displayName(Global.slackUser)
                    .push(new SlackMessage(message));
        } catch (IOException e) {
            //nothing should go here -- let it fail to post to slack...nothing major
        }
    }
    
    private static String convertStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
    
    
    
    
//    public static void sendAttachement(String filePath) {
//        
//        try {
//            new Slack(Global.slackHook)
//                .icon(Global.slackIcon) // Ref - http://www.emoji-cheat-sheet.com/
//                .sendToChannel(Global.slackChannel)
//                .displayName(Global.slackUser)
//                .push(new SlackAttachment(filePath));
//        } catch (IOException ex) {
//            Logger.getLogger(SlackNotification.class.getName()).log(Level.SEVERE, null, ex);
//        }
//    }
    
}
