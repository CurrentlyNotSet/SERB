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
import org.apache.commons.dbutils.DbUtils;
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
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createUser(user);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
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
            
            String sql = "Select * from Users where " + sectionColumnName + " = ? and active = 1";
            
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
    
}
