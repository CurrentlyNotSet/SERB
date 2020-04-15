/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb;

import parker.serb.login.LogInDialog;
import com.alee.laf.WebLookAndFeel;
import java.net.InetAddress;
import java.net.UnknownHostException;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parker
 */
public class SERB {
    
    public static void main(String[] args) {
        //install weblaf look and feel
        WebLookAndFeel.install();
        
        //Set Environment
        setServerLocation();
        setEnv(args);
        
        //Display Welcome
        displayWelcome();
        
        //display the log in dialog
        new LogInDialog(null);
    }
    
    
    private static void setServerLocation(){
        try {
            switch (InetAddress.getLocalHost().getHostName()) {
                case "Alienware15":
                case "Optiplex3010":
                case "Sniper":
                case "HP-8100":
                case "DESKTOP-3G61PRG":
                case "XLN-Anthony":
                    DBConnectionInfo.setLocation("XLN");
                    break;
                default: //SERB LOCATION
                    DBConnectionInfo.setLocation("SERB");
                    break;
            }
        } catch (UnknownHostException ex) {
            SlackNotification.sendNotification(ex);
        }
    }
    
    
    private static void setEnv(String[] args) {        
        //this logic will only only grab the first matching arg and use that, subsquential args will not work
        for (String arg : args) {
            if (arg.equalsIgnoreCase("-test") || arg.equalsIgnoreCase("-training") || arg.equalsIgnoreCase("-prod")) {
                switch (arg) {
                    case "-test":
                        DBConnectionInfo.setDatabase("test");
                        break;
                    case "-training":
                        DBConnectionInfo.setDatabase("training");
                        break;
                    case "-prod":
                    default:
                        DBConnectionInfo.setDatabase("prod");
                        break;
                }
                break;
            }
        }
        
        if (DBConnectionInfo.getDatabase() == null){
            DBConnectionInfo.setDatabase("prod");
        }
    }

    private static void displayWelcome() {
        System.out.println("\n\n\n");
        System.out.println("Starting SERB Case Management Application - v" + Global.APPLICATION_VERSION);
        if (!DBConnectionInfo.getDatabase().equals("SERB")){
            System.out.println("    SERB Case Management Application in non-production mode, no email will be sent.");
        }
        System.out.println("\n\n\n");
    }
}
