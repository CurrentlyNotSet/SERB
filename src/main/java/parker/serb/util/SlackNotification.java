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
import parker.serb.sql.SystemError;

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
        
        try {
            String message = "User: " + Global.activeUser.username + "\n";
            message += "Class Name: " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n";
            message += "Method Name: " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n";
            message += "Exception Type: " + ex.getClass().getSimpleName() + "\n";
            message += "Stack Trace: " + convertStackTrace(ex);
            
            new Slack(Global.SLACK_HOOK)
                .icon(Global.SLACK_ICON) // Ref - http://www.emoji-cheat-sheet.com/
                .sendToChannel(Global.SLACK_CHANNEL)
                .displayName(Global.SLACK_USER)
                .push(new SlackMessage(message)); //nothing should go here -- let it fail to post to slack...nothing major
            
            SystemError.addSystemErrorEntry
            (
                    Thread.currentThread().getStackTrace()[2].getClassName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    ex.getClass().getSimpleName(),
                    ex.toString(),
                    convertStackTrace(ex)
            );
        } catch (IOException ex1) {
            Logger.getLogger(SlackNotification.class.getName()).log(Level.SEVERE, null, ex1);
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
