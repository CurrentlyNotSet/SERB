/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb;

import java.awt.Color;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import parker.serb.sql.User;

/**
 * Stores all of often used values through out the application
 *
 * @author parker
 */
public class Global {

    //Application Version (must always be greater than 3
    // Major Version       3 -- will stay 3
    // Minor Version       0 -- update with larger enhancments
    // Maintenence Version 0 -- bug fix releases
    public static final String APPLICATION_VERSION = "3.1.22";

    //All Date and Time formatters
    public static SimpleDateFormat mmddyyyyhhmma = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
    public static SimpleDateFormat hmma = new SimpleDateFormat("h:mm a");
    public static SimpleDateFormat mmddyyyy = new SimpleDateFormat("MM/dd/yyyy");
    public static SimpleDateFormat EMMMMMdyyyy = new SimpleDateFormat("E, MMMMM d, yyyy");
    public static SimpleDateFormat MMMMddyyyy = new SimpleDateFormat("MMMM dd, yyyy");
    public static SimpleDateFormat MMMMdyyyy = new SimpleDateFormat("MMMM d, yyyy");
    public static SimpleDateFormat MMMMMdyyyy = new SimpleDateFormat("MMMMM d, yyyy");
    public static SimpleDateFormat MMMMM = new SimpleDateFormat("MMMMM");
    public static SimpleDateFormat yyyy = new SimpleDateFormat("yyyy");
    public static SimpleDateFormat hhmma = new SimpleDateFormat("hh:mm a");
    public static SimpleDateFormat SQLDateFormat = new SimpleDateFormat("yyyy-MM-dd");
    public static SimpleDateFormat SQLDateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //The active selected case number of the user
    public static String caseYear = null;
    public static String caseType = null;
    public static String caseMonth = null;
    public static String caseNumber = null;

    //Table striping color, currently will be white until paid
    public static final Color ALTERNATE_ROW_COLOR = Color.WHITE;//new Color(255, 255, 204); //light yellow

    //Stores the current search term to allow data to be retained when leaving and coming back
    public static String employerSearchTerm = null;

    //List of all the roles the logged in user can access
    public static List<String> activeUserRoles = new ArrayList<>();

    //Information about the active user
    public static User activeUser = null;

    //Slack Informaiton
    //TODO: Move this hook and value to another gitignore file
    public static final String SLACK_HOOK = "https://hooks.slack.com/services/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";
    public static final String SLACK_ICON = ":computer:";
    public static final String SLACK_CHANNEL = "serbapplication";
    public static final String SLACK_USER = "SERB";

    //indicate which section the user is currently in
    public static String activeSection;

    //indicate which section the user last docketed in
    public static String docketSection = "";

    //overall root panel, referenced throughout the project
    public static RootPanel root;

    //list of all states -> can limit this down if client wants
    public static final String STATES[] = { "AL", "AK", "AS", "AZ", "AR", "CA", "CO",
        "CT", "DE", "DC", "FL", "FM", "GA", "GU", "HI", "ID", "IL", "IN", "IA",
        "KS", "KY", "LA", "ME", "MH", "MD", "MA", "MI", "MN", "MS", "MO", "MT",
        "NE", "NV", "NH", "NJ", "NM", "NY", "NC", "ND", "MP", "OH", "OK", "OR",
        "PW", "PA", "PR", "RI", "SC", "SD", "TN", "TX", "UM", "UT", "VT", "VA",
        "VI", "WA", "WV", "WI", "WY"};

    //List of Months
    public static final String MONTH_LIST[] = {"January", "Febuary", "March", "April",
        "May", "June", "July", "August", "September", "October", "November", "December"
    };

    //Paths that are used to store locations of all files
    public static String scanPath;
    public static String emailPath;
    public static String templatePath;
    public static String activityPath;
    public static String mediaPath;
    public static String reportingPath;
    public static String documentationPath;

    //boolean about if notigication should be sent
    public static boolean errorNotifications;

    //bookmark loop limit
    public static final int BOOKMARK_LIMIT = 10;

    //email File size Limit (22MB  25MB is the max, leaving overhead for conversions)
    public static final double EMAIL_SIZE_LIMIT = 22000000;

    //format Ending for dates
    public static final String[] DAY_SUFFIXES =
  //    0     1     2     3     4     5     6     7     8     9
     { "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
  //    10    11    12    13    14    15    16    17    18    19
       "th", "th", "th", "th", "th", "th", "th", "th", "th", "th",
  //    20    21    22    23    24    25    26    27    28    29
       "th", "st", "nd", "rd", "th", "th", "th", "th", "th", "th",
  //    30    31
       "th", "st" };
}
