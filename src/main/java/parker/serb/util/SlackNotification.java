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
import parker.serb.Global;
import parker.serb.sql.SystemError;

/**
 *
 * @author parkerjohnston
 */
public class SlackNotification {
    
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
                .push(new SlackMessage(message)); 
            
            SystemError.addSystemErrorEntry
            (
                    Thread.currentThread().getStackTrace()[2].getClassName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    ex.getClass().getSimpleName(),
                    ex.toString(),
                    convertStackTrace(ex)
            );
        } catch (IOException ex1) {
            //leave blank
        }
    }
    
    private static String convertStackTrace(Exception ex) {
        StringWriter sw = new StringWriter();
        ex.printStackTrace(new PrintWriter(sw));
        return sw.toString();
    }
}
