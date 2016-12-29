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
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;
import parker.serb.login.Password;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class User {
    public int      id;
    public boolean  active;
    public String   firstName;
    public String   middleInitial;
    public String   lastName;
    public String   workPhone;
    public String   emailAddress;
    public String   username;
    public long     passwordSalt;
    public String   password;
    public Date     lastLogInDateTime;
    public String   lastLogInPCName;
    public boolean  activeLogIn;
    public boolean  passwordReset;
    public String   applicationVersion;
    public String   defaultSection;
    public boolean  ULPCaseWorker;
    public boolean  REPCaseWorker;
    public boolean  ULPDocketing;
    public boolean  REPDocketing;
    public String   initials;
    public boolean  investigator;
    public String   jobTitle;
    public String   lastTab;
    public String   lastCaseYear;
    public String   lastCaseType;
    public String   lastCaseMonth;
    public String   lastCaseNumber;
    
    public static void createUser(User user) {
        Statement stmt = null;
        
        try {
            long passwordSalt = Password.generatePasswordSalt();
            String tempPassword = Password.generateTempPassword();
            
            stmt = Database.connectToDB().createStatement();
            
            String sql = "INSERT INTO Users VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, user.firstName);
            preparedStatement.setString(3, user.middleInitial);
            preparedStatement.setString(4, user.lastName);
            preparedStatement.setString(5, user.workPhone);
            preparedStatement.setString(6, user.emailAddress);
            preparedStatement.setString(7, user.username);
            preparedStatement.setLong(8, passwordSalt);
            preparedStatement.setString(9, Password.hashPassword(passwordSalt, tempPassword));
            preparedStatement.setTimestamp(10, null);
            preparedStatement.setString(11, null);
            preparedStatement.setBoolean(12, false);
            preparedStatement.setBoolean(13, true);
            preparedStatement.setString(14, null);
            preparedStatement.setString(15, null);
            preparedStatement.setString(16, null);
            preparedStatement.setString(17, null);
            preparedStatement.setString(18, null);
            preparedStatement.setString(19, null);
            preparedStatement.setString(20, null);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                createUser(user);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } 
    }
    
    /**
     * Locate and load a single user instance by searching based on username
     * @param username the username to be searched
     * @return a user instance of the found user
     */
    public static User findUserByUsername(String username) {
        User user = new User();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from Users where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, username);
            
            ResultSet foundUser = preparedStatement.executeQuery();
            
            if(foundUser.next()) {
            
                user.id = foundUser.getInt("id");
                user.active = foundUser.getBoolean("active");
                user.activeLogIn = foundUser.getBoolean("activeLogIn");
                user.emailAddress = foundUser.getString("emailAddress");
                user.firstName = foundUser.getString("firstName");
                user.lastName = foundUser.getString("lastName");
                user.id = foundUser.getInt("id");
                user.password = foundUser.getString("password");
                user.passwordSalt = foundUser.getLong("passwordSalt");
                user.passwordReset = foundUser.getBoolean("passwordReset");
                user.username = foundUser.getString("username");
                user.lastLogInPCName = foundUser.getString("lastLogInPCName");
                user.workPhone = foundUser.getString("workPhone");
                user.middleInitial = foundUser.getString("middleInitial");
                user.applicationVersion = foundUser.getString("applicationVersion");
                user.defaultSection = foundUser.getString("defaultSection");
                user.jobTitle = foundUser.getString("jobTitle");
                user.lastTab = foundUser.getString("lastTab") == null ? "" : foundUser.getString("lastTab");
                user.lastCaseYear = foundUser.getString("lastCaseYear");
                user.lastCaseType = foundUser.getString("lastCaseType");
                user.lastCaseMonth = foundUser.getString("lastCaseMonth");
                user.lastCaseNumber = foundUser.getString("lastCaseNumber");
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                findUserByUsername(username);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } 
        return user;
    }
    
    /**
     * Load a list of all users currently logged into the application
     * @return a list of user instance
     */
    public static List<User> findActiveUsers() {
        
        List<User> activeUsers = new ArrayList<User>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                findActiveUsers();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return activeUsers;
    }
    
    public static List getEnabledUsers() {
        
        List activeUsers = new ArrayList<User>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                getEnabledUsers();
            } else {
                SlackNotification.sendNotification(ex);
            }
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
        }
        
        List activeUsers = new ArrayList<>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from Users where " + sectionColumnName + " = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            
            ResultSet users = preparedStatement.executeQuery();
            
            while(users.next()) {
                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                loadSectionDropDowns(section);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return activeUsers;
    }
    
    /**
    * @deprecated replaced by {@link #loadSectionDropDowns(String section)}
    */
    @Deprecated
    public static List loadAllREPCurrentOwners() {
        
        List activeUsers = new ArrayList<>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from Users where repcurrentowner = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            
            ResultSet users = preparedStatement.executeQuery();
            
            while(users.next()) {
                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                System.out.println("TESTING");
                SlackNotification.sendNotification(ex);
            } else {
                SlackNotification.sendNotification(ex);
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return activeUsers;
    }
    
    /**
    * @deprecated replaced by {@link #loadSectionDropDowns(String section)}
    */
    @Deprecated
    public static List loadULPComboBox() {
        
        List activeUsers = new ArrayList<>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from Users where ULPCaseWorker = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            
            ResultSet users = preparedStatement.executeQuery();
            
            while(users.next()) {
                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                System.out.println("TESTING");
                SlackNotification.sendNotification(ex);
            } else {
                SlackNotification.sendNotification(ex);
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return activeUsers;
    }
    
    
    /**
     * Update the time/date stamp that the user last successfully logged in
     */
    @Deprecated
    public static void updateLastLogInTime() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET lastLogInDateTime = ? where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
            preparedStatement.setString(2, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                updateLastLogInTime();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
    /**
     * Update the name of the PC that the user was last using upon their last
     * successful log in
     */
    @Deprecated
    public static void updateLastPCName() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET lastLogInPCName = ? where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, InetAddress.getLocalHost().getHostName());
            preparedStatement.setString(2, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException | UnknownHostException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                updateLastPCName();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
    /**
     * Update the application version number of the build the user is running
     */
    @Deprecated
    public static void updateApplicationVersion() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET applicationVersion = ? where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.applicationVersion);
            preparedStatement.setString(2, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                updateApplicationVersion();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
    public static void updateLogInInformation() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users"
                    + " SET"
                    + " applicationVersion = ?,"
                    + " lastLogInPCName = ?,"
                    + " lastLogInDateTime = ?,"
                    + " activeLogIn = ?"
                    + " where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.applicationVersion);
            preparedStatement.setString(2, InetAddress.getLocalHost().getHostName());
            preparedStatement.setTimestamp(3, new java.sql.Timestamp(new Date().getTime()));
            preparedStatement.setBoolean(4, !Global.activeUser.activeLogIn);
            preparedStatement.setString(5, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                updateLogInInformation();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } catch (UnknownHostException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Updates the boolean value of the current active log in
     */
    public static void updateActiveLogIn() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET activeLogIn = ? where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, !Global.activeUser.activeLogIn);
            preparedStatement.setString(2, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                updateActiveLogIn();
                SlackNotification.sendNotification(ex);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
    public static void updateLastTab(String tabName) {
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
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET lastTab = ? where username = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, tab);
            preparedStatement.setString(2, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                updateLastTab(tabName);
                SlackNotification.sendNotification(ex);
            } else {
                SlackNotification.sendNotification(ex);
            }
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
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET passwordSalt = ?, password = ? where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setLong(1, salt);
            preparedStatement.setString(2, Password.hashPassword(salt, password));
            preparedStatement.setString(3, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                updatePassword(salt, password);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
    /**
     * Update the boolean value of the users password reset value
     */
    public static void updatePasswordReset() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET passwordReset = ? where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, !Global.activeUser.passwordReset);
            preparedStatement.setString(2, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                updatePasswordReset();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
    /**
     * Find all roles that the user is able to view
     */
    public static void findAppliedRoles() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                findAppliedRoles();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    }
    
    /**
     * Update the user prefs.  Allowing for the user to update their own 
     * information.  This is not meant for an admin update as the information
     * is scaled down to basic information
     * @param user 
     */
    public static void updateUserPrefs(User user) {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
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
            preparedStatement.setString(1, user.firstName);
            preparedStatement.setString(2, user.middleInitial);
            preparedStatement.setString(3, user.lastName);
            preparedStatement.setString(4, user.username);
            preparedStatement.setString(5, user.emailAddress);
            preparedStatement.setString(6, user.workPhone);
            preparedStatement.setString(7, user.defaultSection);
            preparedStatement.setInt(8, Global.activeUser.id);
            
            int success = preparedStatement.executeUpdate();
            
            if(success == 1) {
                Global.activeUser = findUserByUsername(user.username);
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                updateUserPrefs(user);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
    } 
    
    public static String getNameByID(int userID) {
        String userName = "";
        
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select firstName, lastName from Users where ID = ?";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            
            ResultSet success = preparedStatement.executeQuery();
            
            while (success.next()) {
                userName = success.getString("FirstName") + " " + success.getString("LastName");
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                getNameByID(userID);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return userName;    
    }
    
    public static int getUserID(String userName) {
        int userID = 0;
        
        String[] parsedUserName = userName.split(" ");
        
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select id from Users where firstName = ? and lastName = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, parsedUserName[0]);
            preparedStatement.setString(2, parsedUserName[1]);
            
            ResultSet success = preparedStatement.executeQuery();
            
            while (success.next()) {
                userID = success.getInt("id");
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                getUserID(userName);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return userID;    
    }
    
    public static String getEmailByID(int id) {
        String userEmail = "";
        
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select emailAddress from Users where id = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            
            ResultSet success = preparedStatement.executeQuery();
            
            while (success.next()) {
                userEmail = success.getString("emailAddress");
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                getEmailByID(id);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return userEmail;    
    }
    
    public static List getDocketSections() {
        List docketSections = new ArrayList<>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                getDocketSections();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return docketSections;
    }
    
    /**
    * @deprecated replaced by {@link #loadSectionDropDowns(String section)}
    */
    @Deprecated
    public static List loadREPComboBox() {
        
        List activeUsers = new ArrayList<>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from Users where REPCaseWorker = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            
            ResultSet users = preparedStatement.executeQuery();
            
            while(users.next()) {
                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return activeUsers;
    }
    
    /**
    * @deprecated replaced by {@link #loadSectionDropDowns(String section)}
    */
    @Deprecated
    public static List loadORGComboBox() {
        
        List activeUsers = new ArrayList<>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from Users where REPCaseWorker = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            
            ResultSet users = preparedStatement.executeQuery();
            
            while(users.next()) {
                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
            }
        } catch (SQLException ex) {
            if(ex.getCause() instanceof SQLServerException) {
                System.out.println("TESTING");
                SlackNotification.sendNotification(ex);
            } else {
                SlackNotification.sendNotification(ex);
                Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return activeUsers;
    }
        
    public static List loadAllUsers() {
        
        List allUsers = new ArrayList<>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                loadAllUsers();
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return allUsers;
    }
    
    public static List searchAllUsers(String[] param) {
        List<User> list = new ArrayList<>();

        try {

            Statement stmt = Database.connectToDB().createStatement();

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
            if(ex.getCause() instanceof SQLServerException) {
                SlackNotification.sendNotification(ex);
                searchAllUsers(param);
            } else {
                SlackNotification.sendNotification(ex);
            }
        }
        return list;
    }
    
}
