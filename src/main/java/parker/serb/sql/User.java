package parker.serb.sql;

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
    
    /**
     * Create an empty User table
     */
    public static void createTable() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "CREATE TABLE Users" +
                    "(id int IDENTITY (1,1) NOT NULL, " +
                    " active bit NOT NULL, " +
                    " firstName varchar(25) NOT NULL, " + 
                    " middleInitial varchar(1) NULL, " +
                    " lastName varchar(50) NOT NULL, " +
                    " workPhone varchar(10) NOT NULL, " +
                    " emailAddress varchar(100) NOT NULL, " +
                    " username varchar(100) NOT NULL, " +
                    " passwordSalt bigint NOT NULL, " +
                    " password varchar(100) NOT NULL, " +
                    " lastLogInDateTime datetime NULL, " +
                    " lastLogInPCName varchar(100) NULL, " +
                    " activeLogIn bit NOT NULL, " +
                    " passwordReset bit NOT NULL, " +
                    " applicationVersion varchar(5) NULL, " +
                    " PRIMARY KEY (id, username))"; 
            
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    public static void createUser(User user) {
        try {
            long passwordSalt = Password.generatePasswordSalt();
            String tempPassword = Password.generateTempPassword();
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "INSERT INTO Users VALUES"
                    + "(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            
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
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
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
            
//            foundUser.next();
            
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
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return user;
    }
    
    /**
     * Load a list of all users currently logged into the application
     * @return a list of user instance
     */
    public static List findActiveUsers() {
        
        List activeUsers = new ArrayList<User>();
        
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
                activeUsers.add(user);
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activeUsers;
    }
    
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activeUsers;
    }
    
    public static List loadAllHearingPeople() {
        
        List activeUsers = new ArrayList<>();
        
        try {
            
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select * from Users where hearingPerson = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            
            ResultSet users = preparedStatement.executeQuery();
            
            while(users.next()) {
                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activeUsers;
    }
    
//    public static List loadAllALJs() {
//        
//        List activeUsers = new ArrayList<>();
//        
//        try {
//            
//            Statement stmt = Database.connectToDB().createStatement();
//            
//            String sql = "Select * from Users where alj = ?";
//            
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setBoolean(1, true);
//            
//            ResultSet users = preparedStatement.executeQuery();
//            
//            while(users.next()) {
//                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return activeUsers;
//    }
    
//    public static List loadAllInvestigators() {
//        
//        List activeUsers = new ArrayList<>();
//        
//        try {
//            
//            Statement stmt = Database.connectToDB().createStatement();
//            
//            String sql = "Select * from Users where investigator = ?";
//            
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setBoolean(1, true);
//            
//            ResultSet users = preparedStatement.executeQuery();
//            
//            while(users.next()) {
//                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return activeUsers;
//    }
    
//    public static List loadAllMediators() {
//        
//        List activeUsers = new ArrayList<>();
//        
//        try {
//            
//            Statement stmt = Database.connectToDB().createStatement();
//            
//            String sql = "Select * from Users where mediator = ?";
//            
//            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
//            preparedStatement.setBoolean(1, true);
//            
//            ResultSet users = preparedStatement.executeQuery();
//            
//            while(users.next()) {
//                activeUsers.add(users.getString("firstName") + " " + users.getString("lastName"));
//            }
//        } catch (SQLException ex) {
//            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        return activeUsers;
//    }
    
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return activeUsers;
    }
    
    
    /**
     * Update the time/date stamp that the user last successfully logged in
     */
    public static void updateLastLogInTime() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET lastLogInDateTime = ? where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, new java.sql.Timestamp(new Date().getTime()));
            preparedStatement.setString(2, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Update the name of the PC that the user was last using upon their last
     * successful log in
     */
    public static void updateLastPCName() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET lastLogInPCName = ? where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, InetAddress.getLocalHost().getHostName());
            preparedStatement.setString(2, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException | UnknownHostException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Update the application version number of the build the user is running
     */
    public static void updateApplicationVersion() {
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Update Users SET applicationVersion = ? where username = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.applicationVersion);
            preparedStatement.setString(2, Global.activeUser.username);
            
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
    } 
    
    public static String getNameByID(int userID) {
        String userName = "";
        
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select *  from Users where ID = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, userID);
            
            ResultSet success = preparedStatement.executeQuery();
            
            while (success.next()) {
                userName = success.getString("FirstName") + " " + success.getString("LastName");
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userName;    
    }
    
    public static int getCurrentOwnerID(String userName) {
        int userID = 0;
        
        String[] parsedUserName = userName.split(" ");
        
        try {
            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "Select *  from Users where firstName = ? and lastName = ? and active = 1";
            
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, parsedUserName[0]);
            preparedStatement.setString(2, parsedUserName[1]);
            
            ResultSet success = preparedStatement.executeQuery();
            
            while (success.next()) {
                userID = success.getInt("id");
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
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
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return userEmail;    
    }
    
    public static List getDocketSections() {
        List docketSections = new ArrayList<String>();
        
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
            }
        } catch (SQLException ex) {
            Logger.getLogger(User.class.getName()).log(Level.SEVERE, null, ex);
        }
        return docketSections;
    }
        
}
