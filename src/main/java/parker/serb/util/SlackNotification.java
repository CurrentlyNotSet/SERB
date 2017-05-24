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
import parker.serb.Global;
import parker.serb.sql.SystemError;

/**
 *
 * @author parkerjohnston
 */
public class SlackNotification {

    public static void sendNotification(Exception ex) {

        try {
            String message = "User: " + (Global.activeUser != null ? Global.activeUser.username : "NO USER") + "\n" ;
            message += "Class Name: " + Thread.currentThread().getStackTrace()[2].getClassName() + "\n";
            message += "Method Name: " + Thread.currentThread().getStackTrace()[2].getMethodName() + "\n";
            message += "Exception Type: " + ex.getClass().getSimpleName() + "\n";
            message += "Exception Short: " + ex.toString() + "\n";
//            message += "Stack Trace: " + convertStackTrace(ex);

            new Slack(Global.SLACK_HOOK)
                .icon(Global.SLACK_ICON) // Ref - http://www.emoji-cheat-sheet.com/
                .sendToChannel(Global.SLACK_CHANNEL)
                .displayName(Global.SLACK_USER)
                .push(new SlackMessage(message));

            SystemError.addSystemErrorEntry(
                    Thread.currentThread().getStackTrace()[2].getClassName(),
                    Thread.currentThread().getStackTrace()[2].getMethodName(),
                    detailedExceptionType(ex),
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

    private static String detailedExceptionType(Exception ex) {
        String exceptionType = ex.getClass().getSimpleName();

        switch (exceptionType) {
            case "IOException":
                if (ex.toString().toLowerCase().contains("fatal error during installation")) {
                    if (ex.toString().toLowerCase().contains(".dcr")) {
                        exceptionType += " - Error Opening Default DCR Application";
                    }
                }
                if (ex.toString().toLowerCase().contains("doesn't exist")) {
                        exceptionType += " - File Does Not Exist";
                }
                if (ex.toString().toLowerCase().contains("failed to open ")) {
                        exceptionType += " - Unable To Open File";
                }
                break;
            case "IllegalArgumentException":
                if (ex.toString().toLowerCase().contains("doesn't exist")) {
                        exceptionType += " - File Does Not Exist";
                }
                break;
        }
        return exceptionType;
    }

}
