package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import parker.serb.Global;
import parker.serb.login.Password;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;
import parker.serb.util.StringUtilities;

/**
 *
 * @author parkerjohnston
 */
public class User {
    public int     id;
    public boolean active;
    public String  firstName;
    public String  middleInitial;
    public String  lastName;
    public String  workPhone;
    public String  emailAddress;
    public String  username;
    public long    passwordSalt;
    public String  password;
    public Date    lastLogInDateTime;
    public String  lastLogInPCName;
    public boolean activeLogIn;
    public boolean passwordReset;
    public String  applicationVersion;
    public String  defaultSection;
    public boolean ULPCaseWorker;
    public boolean REPCaseWorker;
    public boolean ULPDocketing;
    public boolean REPDocketing;
    public String  initials;
    public boolean investigator;
    public String  jobTitle;
    public boolean MEDCaseWorker;
    public boolean ORGCaseWorker;
    public boolean CSCCaseWorker;
    public boolean CMDSCaseWorker;
    public boolean HearingsCaseWorker;
    public String  lastTab;
    public String  lastCaseYear;
    public String  lastCaseType;
    public String  lastCaseMonth;
    public String  lastCaseNumber;
    public boolean ORGDocketing;
    public boolean MEDDocketing;
    public boolean CSCDocketing;
    public boolean CMDSDocketing;

    public static String[] createUser(User item) {
        Statement stmt = null;
        int returnedKey = -1;
        String tempPassword = "";
        try {
            long passwordSalt = Password.generatePasswordSalt();
            tempPassword = Password.generateTempPassword();

            stmt = Database.connectToDB().createStatement();
            String sql = "Insert INTO Users("
                    + "active, "            //01
                    + "firstName, "         //02
                    + "middleInitial, "     //03
                    + "lastName, "          //04
                    + "workPhone, "         //05
                    + "emailAddress, "      //06
                    + "username, "          //07
                    + "passwordSalt, "      //08
                    + "password, "          //09
                    + "lastLogInDateTime, " //10
                    + "lastLogInPCName, "   //11
                    + "activeLogIn, "       //12
                    + "passwordReset, "     //13
                    + "applicationVersion, "//14
                    + "defaultSection, "    //15
                    + "ULPCaseWorker, "     //16
                    + "REPCaseWorker, "     //17
                    + "ULPDocketing, "      //18
                    + "REPDocketing, "      //19
                    + "initials, "          //20
                    + "investigator, "      //21
                    + "jobTitle, "          //22
                    + "MEDCaseWorker, "     //23
                    + "ORGCaseWorker, "     //24
                    + "CSCCaseWorker, "     //25
                    + "CMDSCaseWorker, "    //26
                    + "HearingsCaseWorker, "//27
                    + "lastTab, "           //28
                    + "lastCaseYear, "      //29
                    + "lastCaseType, "      //30
                    + "lastCaseMonth, "     //31
                    + "lastCaseNumber, "    //32
                    + "ORGDocketing, "      //33
                    + "MEDDocketing, "      //34
                    + "CSCDocketing, "      //35
                    + "CMDSDocketing "      //36
                    + ") VALUES (";
                    for(int i=0; i<35; i++){
                        sql += "?, ";   //01-35
                    }
                     sql += "?)"; //36

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString (2, StringUtils.left(item.firstName, 25));
            preparedStatement.setString (3, StringUtils.left(item.middleInitial, 1));
            preparedStatement.setString (4, StringUtils.left(item.lastName, 50));
            preparedStatement.setString (5, StringUtils.left(item.workPhone, 10));
            preparedStatement.setString (6, StringUtils.left(item.emailAddress, 100));
            preparedStatement.setString (7, StringUtils.left(item.username, 100));
            preparedStatement.setLong   (8, passwordSalt);
            preparedStatement.setString (9, Password.hashPassword(passwordSalt, tempPassword));
            preparedStatement.setTimestamp(10, null);
            preparedStatement.setString (11, null);
            preparedStatement.setBoolean(12, false);
            preparedStatement.setBoolean(13, true);
            preparedStatement.setString (14, null);
            preparedStatement.setString (15, item.defaultSection);
            preparedStatement.setBoolean(16, item.ULPCaseWorker);
            preparedStatement.setBoolean(17, item.REPCaseWorker);
            preparedStatement.setBoolean(18, item.ULPDocketing);
            preparedStatement.setBoolean(19, item.REPDocketing);
            preparedStatement.setString (20, item.initials);
            preparedStatement.setBoolean(21, item.investigator);
            preparedStatement.setString (22, item.jobTitle);
            preparedStatement.setBoolean(23, item.MEDCaseWorker);
            preparedStatement.setBoolean(24, item.ORGCaseWorker);
            preparedStatement.setBoolean(25, item.CSCCaseWorker);
            preparedStatement.setBoolean(26, item.CMDSCaseWorker);
            preparedStatement.setBoolean(27, item.HearingsCaseWorker);
            preparedStatement.setString (28, null);
            preparedStatement.setString (29, null);
            preparedStatement.setString (30, null);
            preparedStatement.setString (31, null);
            preparedStatement.setString (32, null);
            preparedStatement.setBoolean(33, item.ORGDocketing);
            preparedStatement.setBoolean(34, item.MEDDocketing);
            preparedStatement.setBoolean(35, item.CSCDocketing);
            preparedStatement.setBoolean(36, item.CMDSDocketing);
            preparedStatement.executeUpdate();

            ResultSet newRow = preparedStatement.getGeneratedKeys();
            if (newRow.next()){
                returnedKey = newRow.getInt(1);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createUser(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        String[] returnedSet = {String.valueOf(returnedKey), tempPassword};
        return returnedSet;
    }

    /**
     * Locate and load a single user instance by searching based on username
     * @param username the username to be searched
     * @return a user instance of the found user
     */
    public static User findUserByUsername(String username) {
        User user = new User();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from Users where username = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);

            ResultSet foundUser = preparedStatement.executeQuery();

            if(foundUser.next()) {

                user.id = foundUser.getInt("id");
                user.active = foundUser.getBoolean("active");
                user.firstName = foundUser.getString("firstName");
                user.middleInitial = foundUser.getString("middleInitial");
                user.lastName = foundUser.getString("lastName");
                user.workPhone = NumberFormatService.convertStringToPhoneNumber(foundUser.getString("workPhone"));
                user.emailAddress = foundUser.getString("emailAddress");
                user.username = foundUser.getString("username");
                user.passwordSalt = foundUser.getLong("passwordSalt");
                user.password = foundUser.getString("password");
                user.lastLogInDateTime = foundUser.getTimestamp("lastLogInDateTime");
                user.lastLogInPCName = foundUser.getString("lastLogInPCName");
                user.activeLogIn = foundUser.getBoolean("activeLogIn");
                user.passwordReset = foundUser.getBoolean("passwordReset");
                user.applicationVersion = foundUser.getString("applicationVersion");
                user.defaultSection = foundUser.getString("defaultSection");
                user.ULPCaseWorker = foundUser.getBoolean("ULPCaseWorker");
                user.REPCaseWorker = foundUser.getBoolean("REPCaseWorker");
                user.ULPDocketing = foundUser.getBoolean("ULPDocketing");
                user.REPDocketing = foundUser.getBoolean("REPDocketing");
                user.initials = foundUser.getString("initials");
                user.investigator = foundUser.getBoolean("investigator");
                user.jobTitle = foundUser.getString("jobTitle");
                user.MEDCaseWorker = foundUser.getBoolean("MEDCaseWorker");
                user.ORGCaseWorker = foundUser.getBoolean("ORGCaseWorker");
                user.CSCCaseWorker = foundUser.getBoolean("CSCCaseWorker");
                user.CMDSCaseWorker = foundUser.getBoolean("CMDSCaseWorker");
                user.HearingsCaseWorker = foundUser.getBoolean("HearingsCaseWorker");
                user.lastTab = foundUser.getString("lastTab") == null ? "" : foundUser.getString("lastTab");
                user.lastCaseYear = foundUser.getString("lastCaseYear");
                user.lastCaseType = foundUser.getString("lastCaseType");
                user.lastCaseMonth = foundUser.getString("lastCaseMonth");
                user.lastCaseNumber = foundUser.getString("lastCaseNumber");
                user.ORGDocketing = foundUser.getBoolean("ORGDocketing");
                user.MEDDocketing = foundUser.getBoolean("MEDDocketing");
                user.CSCDocketing = foundUser.getBoolean("CSCDocketing");
                user.CMDSDocketing = foundUser.getBoolean("CMDSDocketing");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findUserByUsername(username);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return user;
    }

    public static User findUserByID(int ID) {
        User user = new User();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from Users where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, ID);

            ResultSet foundUser = preparedStatement.executeQuery();

            if(foundUser.next()) {
                user.id = foundUser.getInt("id");
                user.active = foundUser.getBoolean("active");
                user.firstName = foundUser.getString("firstName");
                user.middleInitial = foundUser.getString("middleInitial");
                user.lastName = foundUser.getString("lastName");
                user.workPhone = NumberFormatService.convertStringToPhoneNumber(foundUser.getString("workPhone"));
                user.emailAddress = foundUser.getString("emailAddress");
                user.username = foundUser.getString("username");
                user.passwordSalt = foundUser.getLong("passwordSalt");
                user.password = foundUser.getString("password");
                user.lastLogInDateTime = foundUser.getTimestamp("lastLogInDateTime");
                user.lastLogInPCName = foundUser.getString("lastLogInPCName");
                user.activeLogIn = foundUser.getBoolean("activeLogIn");
                user.passwordReset = foundUser.getBoolean("passwordReset");
                user.applicationVersion = foundUser.getString("applicationVersion");
                user.defaultSection = foundUser.getString("defaultSection");
                user.ULPCaseWorker = foundUser.getBoolean("ULPCaseWorker");
                user.REPCaseWorker = foundUser.getBoolean("REPCaseWorker");
                user.ULPDocketing = foundUser.getBoolean("ULPDocketing");
                user.REPDocketing = foundUser.getBoolean("REPDocketing");
                user.initials = foundUser.getString("initials");
                user.investigator = foundUser.getBoolean("investigator");
                user.jobTitle = foundUser.getString("jobTitle");
                user.MEDCaseWorker = foundUser.getBoolean("MEDCaseWorker");
                user.ORGCaseWorker = foundUser.getBoolean("ORGCaseWorker");
                user.CSCCaseWorker = foundUser.getBoolean("CSCCaseWorker");
                user.CMDSCaseWorker = foundUser.getBoolean("CMDSCaseWorker");
                user.HearingsCaseWorker = foundUser.getBoolean("HearingsCaseWorker");
                user.lastTab = foundUser.getString("lastTab") == null ? "" : foundUser.getString("lastTab");
                user.lastCaseYear = foundUser.getString("lastCaseYear");
                user.lastCaseType = foundUser.getString("lastCaseType");
                user.lastCaseMonth = foundUser.getString("lastCaseMonth");
                user.lastCaseNumber = foundUser.getString("lastCaseNumber");
                user.ORGDocketing = foundUser.getBoolean("ORGDocketing");
                user.MEDDocketing = foundUser.getBoolean("MEDDocketing");
                user.CSCDocketing = foundUser.getBoolean("CSCDocketing");
                user.CMDSDocketing = foundUser.getBoolean("CMDSDocketing");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findUserByID(ID);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return user;
    }

    /**
     * Load a list of all users currently logged into the application
     * @return a list of user instance
     */
    public static List<User> findActiveUsers() {
        List<User> activeUsers = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from Users where activeLogIn = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);

            ResultSet users = preparedStatement.executeQuery();

            while(users.next()) {
                User user = new User();
                user.id = users.getInt("id");
                user.active = users.getBoolean("active");
                user.activeLogIn = users.getBoolean("activeLogIn");
                user.emailAddress = users.getString("emailAddress");
                user.firstName = users.getString("firstName");
                user.lastName = users.getString("lastName");
                user.id = users.getInt("id");
                user.password = users.getString("password");
                user.passwordSalt = users.getLong("passwordSalt");
                user.passwordReset = users.getBoolean("passwordReset");
                user.username = users.getString("username");
                user.lastLogInPCName = users.getString("lastLogInPCName");
                user.lastLogInDateTime = users.getTimestamp("lastLogInDateTime");
                user.workPhone = users.getString("workPhone");
                user.middleInitial = users.getString("middleInitial");
                user.jobTitle = users.getString("jobTitle");
                user.lastTab = users.getString("lastTab") == null ? "" : users.getString("lastTab");
                user.lastCaseYear = users.getString("lastCaseYear");
                user.lastCaseType = users.getString("lastCaseType");
                user.lastCaseMonth = users.getString("lastCaseMonth");
                user.lastCaseNumber = users.getString("lastCaseNumber");
                activeUsers.add(user);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findActiveUsers();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activeUsers;
    }

    public static List<User> getEnabledUsers() {
        List<User> activeUsers = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from Users where active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet users = preparedStatement.executeQuery();

            while(users.next()) {
                User user = new User();
                user.id = users.getInt("id");
                user.active = users.getBoolean("active");
                user.firstName = users.getString("firstName");
                user.middleInitial = users.getString("middleInitial");
                user.lastName = users.getString("lastName");
                user.workPhone = users.getString("workPhone");
                user.emailAddress = users.getString("emailAddress");
                user.username = users.getString("username");
                user.passwordSalt = users.getLong("passwordSalt");
                user.password = users.getString("password");
                user.lastLogInDateTime = users.getTimestamp("lastLogInDateTime");
                user.lastLogInPCName = users.getString("lastLogInPCName");
                user.activeLogIn = users.getBoolean("activeLogIn");
                user.passwordReset = users.getBoolean("passwordReset");
                user.applicationVersion = users.getString("applicationVersion");
                user.defaultSection = users.getString("defaultSection");
                user.ULPCaseWorker = users.getBoolean("ULPCaseWorker");
                user.REPCaseWorker = users.getBoolean("REPCaseWorker");
                user.ULPDocketing = users.getBoolean("ULPDocketing");
                user.REPDocketing = users.getBoolean("REPDocketing");
                user.initials = users.getString("initials");
                user.investigator = users.getBoolean("investigator");
                user.jobTitle = users.getString("jobTitle");
                user.lastTab = users.getString("lastTab") == null ? "" : users.getString("lastTab");
                user.lastCaseYear = users.getString("lastCaseYear");
                user.lastCaseType = users.getString("lastCaseType");
                user.lastCaseMonth = users.getString("lastCaseMonth");
                user.lastCaseNumber = users.getString("lastCaseNumber");
                activeUsers.add(user);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEnabledUsers();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activeUsers;
    }

    public static List<User> getEnabledInvestigators() {
        List<User> activeUsers = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from Users where active = 1 and investigator = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet users = preparedStatement.executeQuery();

            while(users.next()) {
                User user = new User();
                user.id = users.getInt("id");
                user.active = users.getBoolean("active");
                user.firstName = users.getString("firstName");
                user.middleInitial = users.getString("middleInitial");
                user.lastName = users.getString("lastName");
                user.workPhone = users.getString("workPhone");
                user.emailAddress = users.getString("emailAddress");
                user.username = users.getString("username");
                user.passwordSalt = users.getLong("passwordSalt");
                user.password = users.getString("password");
                user.lastLogInDateTime = users.getTimestamp("lastLogInDateTime");
                user.lastLogInPCName = users.getString("lastLogInPCName");
                user.activeLogIn = users.getBoolean("activeLogIn");
                user.passwordReset = users.getBoolean("passwordReset");
                user.applicationVersion = users.getString("applicationVersion");
                user.defaultSection = users.getString("defaultSection");
                user.ULPCaseWorker = users.getBoolean("ULPCaseWorker");
                user.REPCaseWorker = users.getBoolean("REPCaseWorker");
                user.ULPDocketing = users.getBoolean("ULPDocketing");
                user.REPDocketing = users.getBoolean("REPDocketing");
                user.initials = users.getString("initials");
                user.investigator = users.getBoolean("investigator");
                user.jobTitle = users.getString("jobTitle");
                user.lastTab = users.getString("lastTab") == null ? "" : users.getString("lastTab");
                user.lastCaseYear = users.getString("lastCaseYear");
                user.lastCaseType = users.getString("lastCaseType");
                user.lastCaseMonth = users.getString("lastCaseMonth");
                user.lastCaseNumber = users.getString("lastCaseNumber");
                activeUsers.add(user);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEnabledUsers();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activeUsers;
    }

    public static List loadSectionDropDowns(String section) {
        String sectionColumnName = "";

        switch(section) {
            case "REP":
                sectionColumnName = "repcaseworker";
                break;
            case "ULP":
                sectionColumnName = "ulpcaseworker";
                break;
            case "MED":
                sectionColumnName = "medcaseworker";
                break;
            case "ORG":
                sectionColumnName = "orgcaseworker";
                break;
            case "Hearings":
                sectionColumnName = "hearingscaseworker";
                break;
            case "CSC":
                sectionColumnName = "csccaseworker";
                break;
            case "CMDS":
                sectionColumnName = "cmdscaseworker";
                break;
            case "ALJ":
                sectionColumnName = "investigator";
                break;
        }
        List activeUsers = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from Users where " + sectionColumnName + " = ? and active = 1 ORDER BY firstName"; //(ORDER BY firstName T#020 (Beta))

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);

            ResultSet users = preparedStatement.executeQuery();

            while(users.next()) {
                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadSectionDropDowns(section);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activeUsers;
    }

    public static List<User> loadSectionUsersWithID(String section) {
        String sectionColumnName = "";

        switch(section) {
            case "REP":
                sectionColumnName = "repcaseworker";
                break;
            case "ULP":
                sectionColumnName = "ulpcaseworker";
                break;
            case "MED":
                sectionColumnName = "medcaseworker";
                break;
            case "ORG":
                sectionColumnName = "orgcaseworker";
                break;
            case "Hearings":
                sectionColumnName = "hearingscaseworker";
                break;
            case "CSC":
                sectionColumnName = "csccaseworker";
                break;
            case "CMDS":
                sectionColumnName = "cmdscaseworker";
                break;
            case "ALJ":
                sectionColumnName = "investigator";
                break;
        }
        List<User> activeUsers = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from Users where " + sectionColumnName + " = ? and active = 1 ORDER BY firstName"; //(ORDER BY firstName T#020 (Beta))

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);

            ResultSet users = preparedStatement.executeQuery();

            while(users.next()) {
                User item = new User();
                item.id = users.getInt("id");
                item.firstName = users.getString("firstName");
                item.middleInitial = users.getString("middleInitial");
                item.lastName = users.getString("lastName");
                activeUsers.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadSectionDropDowns(section);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activeUsers;
    }

    public static List loadSectionDropDownsPlusALJ(String section) {
        String sectionColumnName = "";

        switch(section) {
            case "REP":
                sectionColumnName = "repcaseworker";
                break;
            case "ULP":
                sectionColumnName = "ulpcaseworker";
                break;
            case "MED":
                sectionColumnName = "medcaseworker";
                break;
            case "ORG":
                sectionColumnName = "orgcaseworker";
                break;
            case "Hearings":
                sectionColumnName = "hearingscaseworker";
                break;
            case "CSC":
                sectionColumnName = "csccaseworker";
                break;
            case "CMDS":
                sectionColumnName = "cmdscaseworker";
                break;
        }
        List activeUsers = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM users WHERE (" + sectionColumnName + " = ? OR investigator = 1) AND active = 1 ORDER BY firstName"; //(ORDER BY firstName T#020 (Beta))

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);

            ResultSet users = preparedStatement.executeQuery();

            while(users.next()) {
                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadSectionDropDowns(section);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activeUsers;
    }

    public static List<User> loadSectionDropDownsPlusALJWithID(String section) {
        String sectionColumnName = "";

        switch(section) {
            case "REP":
                sectionColumnName = "repcaseworker";
                break;
            case "ULP":
                sectionColumnName = "ulpcaseworker";
                break;
            case "MED":
                sectionColumnName = "medcaseworker";
                break;
            case "ORG":
                sectionColumnName = "orgcaseworker";
                break;
            case "Hearings":
                sectionColumnName = "hearingscaseworker";
                break;
            case "CSC":
                sectionColumnName = "csccaseworker";
                break;
            case "CMDS":
                sectionColumnName = "cmdscaseworker";
                break;
        }
        List<User> activeUsers = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM users WHERE (" + sectionColumnName + " = ? OR investigator = 1) AND active = 1 ORDER BY firstName"; //(ORDER BY firstName T#020 (Beta))

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);

            ResultSet users = preparedStatement.executeQuery();

            while(users.next()) {
                User item = new User();
                item.id = users.getInt("id");
                item.firstName = users.getString("firstName");
                item.lastName = users.getString("lastName");

                activeUsers.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadSectionDropDowns(section);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activeUsers;
    }

    public static void updateLogInInformation() {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Users"
                    + " SET"
                    + " applicationVersion = ?,"
                    + " lastLogInPCName = ?,"
                    + " lastLogInDateTime = ?,"
                    + " activeLogIn = ?"
                    + " where username = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.APPLICATION_VERSION);
            preparedStatement.setString(2, InetAddress.getLocalHost().getHostName());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
            preparedStatement.setBoolean(4, !Global.activeUser.activeLogIn);
            preparedStatement.setString(5, Global.activeUser.username);

            preparedStatement.executeUpdate();

            Audit.addAuditEntry("Logged In from " + InetAddress.getLocalHost().getHostName() + " as " + System.getProperty("user.name"));
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateLogInInformation();
            }
        } catch (UnknownHostException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    /**
     * Updates the boolean value of the current active log in
     */
    public static void updateActiveLogIn() {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Users SET activeLogIn = ? where username = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, !Global.activeUser.activeLogIn);
            preparedStatement.setString(2, Global.activeUser.username);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateActiveLogIn();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateLastTab(String tabName) {
        Statement stmt = null;
        try {
            String tab;

            switch(tabName) {
                case "Civil Service Commission":
                    tab = "CSC";
                    break;
                default:
                    tab = tabName;
                    break;
            }
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Users SET lastTab = ? where username = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, tab);
            preparedStatement.setString(2, Global.activeUser.username);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateLastTab(tabName);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateLastCaseNumber() {
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "Update Users SET"
                    + " lastCaseYear = ?,"
                    + " lastCaseType = ?,"
                    + " lastCaseMonth = ?,"
                    + " lastCaseNumber = ?"
                    + " where username = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setString(5, Global.activeUser.username);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                updateLastCaseNumber();
                SlackNotification.sendNotification(ex);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }

    /**
     * Update the active users password. Hashing occurs in this method
     * @param salt the randomly generated password salt for the new password
     * @param password the plain text password
     */
    public static void updatePassword(long salt, String password) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Users SET passwordSalt = ?, password = ? where username = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, salt);
            preparedStatement.setString(2, Password.hashPassword(salt, password));
            preparedStatement.setString(3, Global.activeUser.username);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updatePassword(salt, password);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void resetPassword(int ID, long salt, String password) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Users SET passwordSalt = ?, password = ? where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, salt);
            preparedStatement.setString(2, Password.hashPassword(salt, password));
            preparedStatement.setInt(3, ID);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updatePassword(salt, password);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    /**
     * Update the boolean value of the users password reset value
     */
    public static void updatePasswordReset() {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Users SET passwordReset = ? where username = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, !Global.activeUser.passwordReset);
            preparedStatement.setString(2, Global.activeUser.username);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updatePasswordReset();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    /**
     * Find all roles that the user is able to view
     */
    public static void findAppliedRoles() {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select Role AS Role from Role " +
            "INNER JOIN UserRole on UserRole.roleID = Role.id " +
            "WHERE UserRole.userID = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, Global.activeUser.id);

            ResultSet foundUser = preparedStatement.executeQuery();

            while (foundUser.next()) {
               Global.activeUserRoles.add(foundUser.getString("Role"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findAppliedRoles();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    /**
     * Update the user prefs.  Allowing for the user to update their own
     * information.  This is not meant for an admin update as the information
     * is scaled down to basic information
     * @param user
     */
    public static void updateUserPrefs(User user) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Users set"
                    + " firstName = ?,"
                    + " middleInitial = ?,"
                    + " lastName = ?,"
                    + " username = ?,"
                    + " emailAddress = ?,"
                    + " workPhone = ?,"
                    + " defaultSection = ?"
                    + " where id = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, StringUtils.left(user.firstName, 25));
            preparedStatement.setString(2, StringUtils.left(user.middleInitial, 1));
            preparedStatement.setString(3, StringUtils.left(user.lastName, 50));
            preparedStatement.setString(4, StringUtils.left(user.username, 10));
            preparedStatement.setString(5, StringUtils.left(user.emailAddress, 100));
            preparedStatement.setString(6, StringUtils.left(user.workPhone, 10));
            preparedStatement.setString(7, user.defaultSection);
            preparedStatement.setInt(8, Global.activeUser.id);

            int success = preparedStatement.executeUpdate();

            if(success == 1) {
                Global.activeUser = findUserByUsername(user.username);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateUserPrefs(user);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static String getNameByID(int userID) {
        String userName = "";

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select firstName, lastName from Users where ID = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userID);

            ResultSet success = preparedStatement.executeQuery();

            while (success.next()) {
                userName = success.getString("FirstName") + " " + success.getString("LastName");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getNameByID(userID);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return userName;
    }

    public static String getNameLastInitialByID(int userID) {
        String userName = "";

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT firstName, LEFT(LastName, 1) AS LastName FROM Users WHERE ID = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userID);

            ResultSet success = preparedStatement.executeQuery();

            while (success.next()) {
                userName = success.getString("FirstName") + " " + success.getString("LastName") + ".";
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getNameByID(userID);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return userName;
    }

    public static String getFullNameByID(int userID) {
        String userName = "";

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select firstName, middleInitial, lastName from Users where ID = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userID);

            ResultSet success = preparedStatement.executeQuery();

            while (success.next()) {
                userName = StringUtilities.buildFullName(success.getString("FirstName"), success.getString("middleInitial"), success.getString("lastName"));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getNameByID(userID);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return userName;
    }

    public static int getUserID(String userName) {
        int userID = 0;

        String[] parsedUserName = userName.split(" ");

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select id from Users where firstName = ? and lastName = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, parsedUserName[0]);
            preparedStatement.setString(2, parsedUserName[1]);

            ResultSet success = preparedStatement.executeQuery();

            while (success.next()) {
                userID = success.getInt("id");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getUserID(userName);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return userID;
    }

    public static String getEmailByID(int id) {
        String userEmail = "";

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select emailAddress from Users where id = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet success = preparedStatement.executeQuery();

            while (success.next()) {
                userEmail = success.getString("emailAddress");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getEmailByID(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return userEmail;
    }

    public static List getDocketSections() {
        List docketSections = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from Users where username = ? and active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.activeUser.username);

            ResultSet users = preparedStatement.executeQuery();


            while(users.next()) {
                if(users.getBoolean("ULPDocketing") == true) {
                    docketSections.add("ULP");
                }
                if(users.getBoolean("REPDocketing") == true) {
                    docketSections.add("REP");
                }
                if(users.getBoolean("ORGDocketing") == true) {
                    docketSections.add("ORG");
                }
                if(users.getBoolean("MEDDocketing") == true) {
                    docketSections.add("MED");
                }
                if(users.getBoolean("CSCDocketing") == true) {
                    docketSections.add("CSC");
                }
                if(users.getBoolean("CMDSDocketing") == true) {
                    docketSections.add("CMDS");
                }
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getDocketSections();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return docketSections;
    }

    public static List loadAllUsers() {
        List allUsers = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Users";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet rs = preparedStatement.executeQuery();

            while(rs.next()) {
                User user = new User();
                user.id = rs.getInt("id");
                user.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                user.middleInitial = rs.getString("middleInitial") == null ? "" : rs.getString("middleInitial");
                user.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                user.workPhone = rs.getString("workPhone") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("workPhone"));
                user.emailAddress = rs.getString("emailAddress") == null ? "" : rs.getString("emailAddress");
                allUsers.add(user);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllUsers();
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return allUsers;
    }

    public static List searchAllUsers(String[] param) {
        List<User> list = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Users";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(firstName, lastName, username, "
                            + "initials, emailAddress, workPhone) LIKE ?";
                }
            }
            sql += " ORDER BY lastName";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                User item = new User();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.firstName = rs.getString("firstName") == null ? "" : rs.getString("firstName");
                item.middleInitial = rs.getString("middleInitial") == null ? "" : rs.getString("middleInitial");
                item.lastName = rs.getString("lastName") == null ? "" : rs.getString("lastName");
                item.username = rs.getString("username") == null ? "" : rs.getString("username");
                item.initials = rs.getString("initials") == null ? "" : rs.getString("initials");
                item.emailAddress = rs.getString("emailAddress") == null ? "" : rs.getString("emailAddress");
                item.workPhone = rs.getString("workPhone") == null ? "" : NumberFormatService.convertStringToPhoneNumber(rs.getString("workPhone"));
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                searchAllUsers(param);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }

    public static void updateUser(User item ){
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE Users SET "
                    + "firstName = ?, "
                    + "middleInitial = ?, "
                    + "lastName = ?, "
                    + "workPhone = ?, "
                    + "emailAddress = ?, "
                    + "username = ?, "
                    + "activeLogIn = ?, "
                    + "passwordReset = ?, "
                    + "defaultSection = ?, "
                    + "ULPCaseWorker = ?, "
                    + "REPCaseWorker = ?, "
                    + "ULPDocketing = ?, "
                    + "REPDocketing = ?, "
                    + "initials = ?, "
                    + "investigator = ?, "
                    + "jobTitle = ?, "
                    + "MEDCaseWorker = ?, "
                    + "ORGCaseWorker = ?, "
                    + "CSCCaseWorker = ?, "
                    + "CMDSCaseWorker = ?, "
                    + "HearingsCaseWorker = ?, "
                    + "ORGDocketing = ?, "
                    + "MEDDocketing = ?, "
                    + "CSCDocketing = ?, "
                    + "CMDSDocketing = ? "
                    + "where id = ?";
            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setString ( 1, item.firstName.equals("") ? null : StringUtils.left(item.firstName.trim(), 25));
            ps.setString ( 2, item.middleInitial.equals("") ? null : StringUtils.left(item.middleInitial.trim(), 1));
            ps.setString ( 3, item.lastName.equals("") ? null : StringUtils.left(item.lastName.trim(), 50));
            ps.setString ( 4, item.workPhone.equals("") ? null : StringUtils.left(item.workPhone.trim(), 10));
            ps.setString ( 5, item.emailAddress.equals("") ? null : StringUtils.left(item.emailAddress.trim(), 100));
            ps.setString ( 6, item.username.equals("") ? null : StringUtils.left(item.username.trim(), 100));
            ps.setBoolean( 7, item.activeLogIn);
            ps.setBoolean( 8, item.passwordReset);
            ps.setString ( 9, item.defaultSection.equals("") ? null : item.defaultSection.trim());
            ps.setBoolean(10, item.ULPCaseWorker);
            ps.setBoolean(11, item.REPCaseWorker);
            ps.setBoolean(12, item.ULPDocketing);
            ps.setBoolean(13, item.REPDocketing);
            ps.setString(14, item.initials.equals("") ? null : item.initials.trim());
            ps.setBoolean(15, item.investigator);
            ps.setString(16, item.jobTitle.equals("") ? null : item.jobTitle.trim());
            ps.setBoolean(17, item.MEDCaseWorker);
            ps.setBoolean(18, item.ORGCaseWorker);
            ps.setBoolean(19, item.CSCCaseWorker);
            ps.setBoolean(20, item.CMDSCaseWorker);
            ps.setBoolean(21, item.HearingsCaseWorker);
            ps.setBoolean(22, item.ORGDocketing);
            ps.setBoolean(23, item.MEDDocketing);
            ps.setBoolean(24, item.CSCDocketing);
            ps.setBoolean(25, item.CMDSDocketing);
            ps.setInt(26, item.id);

            ps.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateUser(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

}
