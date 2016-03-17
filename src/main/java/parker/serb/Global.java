/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb;

import parker.serb.sql.User;
import java.sql.Connection;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
//import org.bson.Document;

/**
 *
 * @author parker
 */
public class Global {
    
    public static final boolean verboseLogging = true;
    
    public static final String applicationVersion = "0.3";

    //Gets a temp directory for storing files.  This should only be used for MongoDB
    //public static String tempDir = System.getProperty("java.io.tmpdir");
    
    //All Date formatters
    public static SimpleDateFormat mmddyyyyhhmma = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    public static SimpleDateFormat mmddyyyy = new SimpleDateFormat("MM/dd/yyyy");
    public static SimpleDateFormat EMMMMMdyyyy = new SimpleDateFormat("E, MMMMM d, yyyy");
    
    //The active selected case number of the user
    public static String caseYear = null;
    public static String caseType = null;
    public static String caseMonth = null;
    public static String caseNumber = null;
    
    public static String employerSearchTerm = "";
//    public st
    
    
    //List of all the roles the logged in user can access
    public static List<String> activeUserRoles = new ArrayList<>();
    
    //Information about the active user
    public static User activeUser = null;
    
    
    //public static REPCase rep = null;
    
    //Slack Informaiton
    public static final String slackHook = "https://hooks.slack.com/services/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";
    public static final String slackIcon = ":computer:";
    public static final String slackChannel = "serbapplication";
    public static final String slackUser = "SERB";
    
    public static String activeSection;
    
    public static RootPanel root;
    
    //list of all states -> can limit this down if client wants
    public static String states[] = {"AL", "AK", "AS", "AZ", "AR", "CA", "CO", "CT", "DE", "DC",
        "FL", "FM", "GA", "GU", "HI", "ID", "IL", "IN", "IA", "KS", "KY", "LA",
        "ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT", "NE", "NV", "NH",
        "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR", "PW", "PA", "PR",
        "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VT", "VA", "VI", "WA", "WV",
        "WI", "WY"};
    
    public static String scanPath;
    public static String emailPath;
    public static String activityPath;
    public static boolean errorNotifications;
}
