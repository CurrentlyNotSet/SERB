package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import static java.nio.file.StandardCopyOption.*;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.io.FilenameUtils;
import parker.serb.Global;
import parker.serb.recordRetention.CaseNumberModel;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class Activity {

    public int id;
    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    public String user;
    public String date;
    public String action;
    public String fileName;
    public String from;
    public String to;
    public String type;
    public String comment;
    public boolean redacted;
    public boolean awaitingScan;
    public boolean active;
    public String mailLog;

    /**
     * Add an activity to the activity table, pulls the case number from the
     * current selected case
     *
     * @param action the action that has been preformed
     * @param fileName the fileName of a document - null if no file
     */
    public static void addActivty(String action, String fileName) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName == null ? null : fileName);
            preparedStatement.setString(9, null); //from
            preparedStatement.setString(10, null); //to
            preparedStatement.setString(11, null); //type
            preparedStatement.setString(12, null); //comment
            preparedStatement.setBoolean(13, false); //redacted
            preparedStatement.setBoolean(14, false); //awaiting timestamp
            preparedStatement.setBoolean(15, true); //active
            preparedStatement.setTimestamp(16, null); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivty(action, fileName);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addActivtyNonGlobalCaseNumber(
            String action,
            String fileName,
            String caseNumber) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber.split("-")[0].trim());
            preparedStatement.setString(2, caseNumber.split("-")[1].trim());
            preparedStatement.setString(3, caseNumber.split("-")[2].trim());
            preparedStatement.setString(4, caseNumber.split("-")[3].trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName == null ? null : fileName);
            preparedStatement.setString(9, null); //from
            preparedStatement.setString(10, null); //to
            preparedStatement.setString(11, null); //type
            preparedStatement.setString(12, null); //comment
            preparedStatement.setBoolean(13, false); //redacted
            preparedStatement.setBoolean(14, false); //awaiting timestamp
            preparedStatement.setBoolean(15, true); //active
            preparedStatement.setTimestamp(16, null); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivtyNonGlobalCaseNumber(action, fileName, caseNumber);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addActivtyORGCase(String caseType, String orgNumber, String action, String fileName) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, caseType);
            preparedStatement.setString(3, null);
            preparedStatement.setString(4, orgNumber.trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName == null ? null : fileName);
            preparedStatement.setString(9, null);
            preparedStatement.setString(10, null);
            preparedStatement.setString(11, null);
            preparedStatement.setString(12, null);
            preparedStatement.setBoolean(13, false);
            preparedStatement.setBoolean(14, false);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, null); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivty(action, fileName);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addCMDSActivty(String action, String fileName, Date date,
            String caseNumber, String from, String to, String category, String description, String comment) {
        Statement stmt = null;
        String type = null;

        if (category != null && description != null) {
            type = category + " - " + description;
        }

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber.split("-")[0].trim()); //caseYear
            preparedStatement.setString(2, caseNumber.split("-")[1].trim()); //caseType
            preparedStatement.setString(3, caseNumber.split("-")[2].trim()); //caseMonth
            preparedStatement.setString(4, caseNumber.split("-")[3].trim()); //caseNumber
            preparedStatement.setInt(5, Global.activeUser.id); //user
            preparedStatement.setTimestamp(6, new Timestamp(date.getTime())); //date
            preparedStatement.setString(7, action.equals("") ? null : action); //action
            preparedStatement.setString(8, fileName == null || fileName.equals("")
                    ? null : FilenameUtils.getName(fileName)); //fileName
            preparedStatement.setString(9, from == null ? null
                    : from.trim().equals("") ? null : from.trim()); //from
            preparedStatement.setString(10, to == null ? null
                    : to.trim().equals("") ? null : to.trim()); //to
            preparedStatement.setString(11, type == null ? null
                    : type.trim().equals("") ? null : type.trim()); //type
            preparedStatement.setString(12, comment == null ? null
                    : comment.trim().equals("") ? null : comment.trim()); //comment
            preparedStatement.setBoolean(13, false);
            preparedStatement.setBoolean(14, false);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, new Timestamp(System.currentTimeMillis())); //mailLog

            preparedStatement.executeUpdate();

            if (fileName != null && !fileName.equals("")) {
                File src = new File(fileName);
                File dst = new File(Global.activityPath + File.separator + "CMDS" + File.separator + caseNumber.split("-")[0] + File.separator + caseNumber + fileName.substring(fileName.lastIndexOf(File.separator)));
                dst.mkdirs();
                Files.copy(src.toPath(), dst.toPath(), REPLACE_EXISTING);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addCMDSActivty(action, fileName, date, caseNumber, from, to, category, description, comment);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } catch (IOException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addHearingActivty(String action, String fileName, String caseNumber) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber.split("-")[0].trim());
            preparedStatement.setString(2, caseNumber.split("-")[1].trim());
            preparedStatement.setString(3, caseNumber.split("-")[2].trim());
            preparedStatement.setString(4, caseNumber.split("-")[3].trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName == null || fileName.equals("") ? null : FilenameUtils.getName(fileName));
            preparedStatement.setString(9, null);
            preparedStatement.setString(10, null);
            preparedStatement.setString(11, null);
            preparedStatement.setString(12, null);
            preparedStatement.setBoolean(13, false);
            preparedStatement.setBoolean(14, false);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, null); //mailLog

            preparedStatement.executeUpdate();

            if (fileName != null && !fileName.equals("")) {
                File src = new File(fileName);
                File dst = new File(Global.activityPath + File.separator + "CMDS" + File.separator + caseNumber.split("-")[0] + File.separator + caseNumber + fileName.substring(fileName.lastIndexOf(File.separator)));
                dst.mkdirs();
                Files.copy(src.toPath(), dst.toPath(), REPLACE_EXISTING);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addHearingActivty(action, fileName, caseNumber);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } catch (IOException ex) {
            SlackNotification.sendNotification(ex);
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void disableActivtyByID(String id) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Update Activity set active = 0 where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                disableActivtyByID(id);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addActivtyFromDocket(String action, String fileName,
            String[] caseNumber,
            String from,
            String to,
            String type,
            String comment,
            boolean redacted,
            boolean needsTimestamp) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber[0].trim());
            preparedStatement.setString(2, caseNumber[1].trim());
            preparedStatement.setString(3, caseNumber[2].trim());
            preparedStatement.setString(4, caseNumber[3].trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName.equals("") ? null : fileName);
            preparedStatement.setString(9, from.equals("") ? null : from);
            preparedStatement.setString(10, to.equals("") ? null : to);
            preparedStatement.setString(11, type.equals("") ? null : type);
            preparedStatement.setString(12, comment.equals("") ? null : comment);
            preparedStatement.setBoolean(13, redacted);
            preparedStatement.setBoolean(14, false);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, new Timestamp(System.currentTimeMillis())); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivtyFromDocket(action, fileName, caseNumber, from, to, type, comment, redacted, needsTimestamp);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addActivtySendPostal(String action, String fileName,
            String[] caseNumber,
            String from,
            String to,
            String type,
            String comment,
            boolean redacted,
            boolean needsTimestamp) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber[0].trim());
            preparedStatement.setString(2, caseNumber[1].trim());
            preparedStatement.setString(3, caseNumber[2].trim());
            preparedStatement.setString(4, caseNumber[3].trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName.equals("") ? null : fileName);
            preparedStatement.setString(9, from.equals("") ? null : from);
            preparedStatement.setString(10, to.equals("") ? null : to);
            preparedStatement.setString(11, type.equals("") ? null : type);
            preparedStatement.setString(12, comment.equals("") ? null : comment);
            preparedStatement.setBoolean(13, redacted);
            preparedStatement.setBoolean(14, false);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, null); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivtySendPostal(action, fileName, caseNumber, from, to, type, comment, redacted, needsTimestamp);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addActivtySendPostalORGCSC(String action, String fileName,
            String caseSection,
            String caseNumber,
            String from,
            String to,
            String type,
            String comment,
            boolean redacted,
            boolean needsTimestamp) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, caseSection.trim());
            preparedStatement.setString(3, null);
            preparedStatement.setString(4, caseNumber.trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName.equals("") ? null : fileName);
            preparedStatement.setString(9, from.equals("") ? null : from);
            preparedStatement.setString(10, to.equals("") ? null : to);
            preparedStatement.setString(11, type.equals("") ? null : type);
            preparedStatement.setString(12, comment.equals("") ? null : comment);
            preparedStatement.setBoolean(13, redacted);
            preparedStatement.setBoolean(14, false);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, null); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivtySendPostalORGCSC(action, fileName, caseSection, caseNumber, from, to, type, comment, redacted, needsTimestamp);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addScanActivtyFromDocket(String action, String fileName,
            String[] caseNumber,
            String from,
            String to,
            String type,
            String comment,
            boolean redacted,
            boolean needsTimestamp,
            Date activtyDate) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber[0].trim());
            preparedStatement.setString(2, caseNumber[1].trim());
            preparedStatement.setString(3, caseNumber[2].trim());
            preparedStatement.setString(4, caseNumber[3].trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(activtyDate.getTime()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName.equals("") ? null : fileName);
            preparedStatement.setString(9, from.equals("") ? null : from);
            preparedStatement.setString(10, to.equals("") ? null : to);
            preparedStatement.setString(11, type.equals("") ? null : type);
            preparedStatement.setString(12, comment.equals("") ? null : comment);
            preparedStatement.setBoolean(13, redacted);
            preparedStatement.setBoolean(14, needsTimestamp);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, new Timestamp(System.currentTimeMillis())); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivtyFromDocket(action, fileName, caseNumber, from, to, type, comment, redacted, needsTimestamp);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addActivtyFromDocketORGCSC(String action, String fileName,
            String caseNumber,
            String from,
            String to,
            String type,
            String comment,
            boolean redacted,
            boolean needsTimestamp,
            String section,
            Date fileDate) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, section.equalsIgnoreCase("Civil Service Commission") ? "CSC" : section);
            preparedStatement.setString(3, null);
            preparedStatement.setString(4, caseNumber.trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(fileDate.getTime()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName.equals("") ? null : fileName);
            preparedStatement.setString(9, from.equals("") ? null : from);
            preparedStatement.setString(10, to.equals("") ? null : to);
            preparedStatement.setString(11, type.equals("") ? null : type);
            preparedStatement.setString(12, comment.equals("") ? null : comment);
            preparedStatement.setBoolean(13, redacted);
            preparedStatement.setBoolean(14, needsTimestamp);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, new Timestamp(System.currentTimeMillis())); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivtyFromDocketORGCSC(action, fileName, caseNumber, from, to, type, comment, redacted, needsTimestamp, section, fileDate);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addScanActivtyFromDocketORGCSC(String action, String fileName,
            String caseNumber,
            String from,
            String to,
            String type,
            String comment,
            boolean redacted,
            boolean needsTimestamp,
            String section,
            Date activityDate) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, null);
            preparedStatement.setString(2, section);
            preparedStatement.setString(3, null);
            preparedStatement.setString(4, caseNumber.trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(activityDate.getTime()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName.equals("") ? null : fileName);
            preparedStatement.setString(9, from.equals("") ? null : from);
            preparedStatement.setString(10, to.equals("") ? null : to);
            preparedStatement.setString(11, type.equals("") ? null : type);
            preparedStatement.setString(12, comment.equals("") ? null : comment);
            preparedStatement.setBoolean(13, redacted);
            preparedStatement.setBoolean(14, needsTimestamp);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, new Timestamp(System.currentTimeMillis())); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivtyFromDocketORGCSC(action, fileName, caseNumber, from, to, type, comment, redacted, needsTimestamp, section, activityDate);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addActivtyFromDocket(String action, String fileName,
            String[] caseNumber,
            String from,
            String to,
            String type,
            String comment,
            boolean redacted,
            boolean needsTimestamp,
            Date activityDate) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNumber[0].trim());
            preparedStatement.setString(2, caseNumber[1].trim());
            preparedStatement.setString(3, caseNumber[2].trim());
            preparedStatement.setString(4, caseNumber[3].trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(activityDate.getTime()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, fileName.equals("") ? null : fileName);
            preparedStatement.setString(9, from.equals("") ? null : from);
            preparedStatement.setString(10, to.equals("") ? null : to);
            preparedStatement.setString(11, type.equals("") ? null : type);
            preparedStatement.setString(12, comment.equals("") ? null : comment);
            preparedStatement.setBoolean(13, redacted);
            preparedStatement.setBoolean(14, needsTimestamp);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, new Timestamp(System.currentTimeMillis())); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addActivtyFromDocket(action, fileName, caseNumber, from, to, type, comment, redacted, needsTimestamp, activityDate);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    /**
     * Creates activity entry when new cases are created
     *
     * @param caseNumber the new case number
     * @param message
     */
    public static void addNewCaseActivty(String caseNumber, String message) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            String[] parsedCaseNumber = caseNumber.split("-");
            preparedStatement.setString(1, parsedCaseNumber[0].trim());
            preparedStatement.setString(2, parsedCaseNumber[1].trim());
            preparedStatement.setString(3, parsedCaseNumber[2].trim());
            preparedStatement.setString(4, parsedCaseNumber[3].trim());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, message.equals("") ? null : message);
            preparedStatement.setString(8, null);
            preparedStatement.setString(9, null);
            preparedStatement.setString(10, null);
            preparedStatement.setString(11, null);
            preparedStatement.setString(12, null);
            preparedStatement.setBoolean(13, false);
            preparedStatement.setBoolean(14, false);
            preparedStatement.setBoolean(15, true);
            preparedStatement.setTimestamp(16, null); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addNewCaseActivty(caseNumber, message);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    /**
     * Loads all activity for a specified case number, pulls the case number
     * from global
     *
     * @param searchTerm term to limit the search results
     * @return List of Activities
     */
    public static List loadCaseNumberActivity(String searchTerm) {
        List<Activity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {
            PreparedStatement preparedStatement;

            stmt = Database.connectToDB().createStatement();

            if (Global.caseType.equals("ORG") || Global.caseType.equals("CSC")) {
                String sql = "select Activity.id,"
                        + " caseYear,"
                        + " caseType,"
                        + " caseMonth,"
                        + " caseNumber,"
                        + " date,"
                        + " action,"
                        + " comment,"
                        + " [from],"
                        + " firstName,"
                        + " lastName,"
                        + " fileName"
                        + " from Activity"
                        + " LEFT JOIN Users"
                        + " ON Activity.userID = Users.id"
                        + " where "
                        + " caseType = ? and"
                        + " caseNumber = ? and"
                        + " (firstName like ? or"
                        + " lastName like ? or"
                        + " action like ?)"
                        + " and Activity.active = 1"
                        + " ORDER BY date DESC, activity.id DESC ";

                preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setObject(1, Global.caseType);
                preparedStatement.setObject(2, Global.caseNumber);
                preparedStatement.setString(3, "%" + searchTerm + "%");
                preparedStatement.setString(4, "%" + searchTerm + "%");
                preparedStatement.setString(5, "%" + searchTerm + "%");
            } else {
                String sql = "select Activity.id,"
                        + " caseYear,"
                        + " caseType,"
                        + " caseMonth,"
                        + " caseNumber,"
                        + " date,"
                        + " action,"
                        + " comment,"
                        + " [from],"
                        + " firstName,"
                        + " lastName,"
                        + " fileName"
                        + " from Activity"
                        + " LEFT JOIN Users"
                        + " ON Activity.userID = Users.id"
                        + " where caseYear = ? and"
                        + " caseType = ? and"
                        + " caseMonth = ? and"
                        + " caseNumber = ? and"
                        + " (firstName like ? or"
                        + " lastName like ? or"
                        + " action like ?) "
                        + " and Activity.active = 1"
                        + "ORDER BY date DESC, activity.id DESC ";

                preparedStatement = stmt.getConnection().prepareStatement(sql);
                preparedStatement.setObject(1, Global.caseYear);
                preparedStatement.setObject(2, Global.caseType);
                preparedStatement.setObject(3, Global.caseMonth);
                preparedStatement.setObject(4, Global.caseNumber);
                preparedStatement.setString(5, "%" + searchTerm + "%");
                preparedStatement.setString(6, "%" + searchTerm + "%");
                preparedStatement.setString(7, "%" + searchTerm + "%");
            }

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                Activity act = new Activity();

                if (caseActivity.getString("firstName") == null && caseActivity.getString("lastName") == null) {
                    act.user = "SYSTEM";
                } else {
                    act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
                }

                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.from = caseActivity.getString("from") == null ? "" : caseActivity.getString("from");
                act.comment = caseActivity.getString("comment") == null ? "" : caseActivity.getString("comment");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadCaseNumberActivity(searchTerm);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }

    public static List loadAllActivity() {
        List<Activity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select TOP 50 Activity.id,"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " date,"
                    + " action,"
                    + " comment,"
                    + " [from],"
                    + " firstName,"
                    + " lastName,"
                    + " fileName"
                    + " from Activity"
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    + " WHERE Activity.active = 1"
                    + " ORDER BY date DESC, activity.id DESC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.comment = caseActivity.getString("comment");
                act.from = caseActivity.getString("from") == null ? "" : caseActivity.getString("from");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadAllActivity();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }

    public static List loadHearingActivity() {
        List<Activity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select Activity.id,"
                    + " caseYear,"
                    + " caseType,"
                    + " caseMonth,"
                    + " caseNumber,"
                    + " date,"
                    + " action,"
                    + " comment,"
                    + " [from],"
                    + " firstName,"
                    + " lastName,"
                    + " fileName"
                    + " from Activity"
                    + " INNER JOIN Users"
                    + " ON Activity.userID = Users.id"
                    + " WHERE date >= ?"
                    + " AND CaseYear = ?"
                    + " AND CaseType = ?"
                    + " AND CaseMonth = ?"
                    + " AND CaseNumber = ?"
                    + " and Activity.active = 1"
                    + " ORDER BY date DESC, activity.id DESC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setTimestamp(1, HearingCase.getBoardActionPCDate());
            preparedStatement.setString(2, Global.caseYear);
            preparedStatement.setString(3, Global.caseType);
            preparedStatement.setString(4, Global.caseMonth);
            preparedStatement.setString(5, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.comment = caseActivity.getString("comment") == null ? "" : caseActivity.getString("comment");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                act.from = caseActivity.getString("from") == null ? "" : caseActivity.getString("from");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadHearingActivity();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }

    public static Activity loadActivityByID(String id, String user) {
        Activity activity = new Activity();

        Statement stmt = null;

        String sql = "";

        try {

            stmt = Database.connectToDB().createStatement();

            if (user.equals("SYSTEM")) {
                sql = "select Activity.id,"
                        + " caseYear,"
                        + " caseType,"
                        + " caseMonth,"
                        + " caseNumber,"
                        + " date,"
                        + " [to],"
                        + " [from],"
                        + " type,"
                        + " comment,"
                        + " action,"
                        + " fileName"
                        + " from Activity"
                        + " Where Activity.id = ?"
                        + " and Activity.active = 1";
            } else {
                sql = "select Activity.id,"
                        + " caseYear,"
                        + " caseType,"
                        + " caseMonth,"
                        + " caseNumber,"
                        + " date,"
                        + " [to],"
                        + " [from],"
                        + " type,"
                        + " comment,"
                        + " action,"
                        + " firstName,"
                        + " lastName,"
                        + " fileName"
                        + " from Activity"
                        + " INNER JOIN Users"
                        + " ON Activity.userID = Users.id"
                        + " Where Activity.id = ?"
                        + " and Activity.active = 1";
            }

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, Integer.parseInt(id));

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                if (user.equals("SYSTEM")) {
                    activity.id = caseActivity.getInt("id");
                    activity.user = "SYSTEM";
                    activity.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                    activity.action = caseActivity.getString("action");
                    activity.caseYear = caseActivity.getString("caseYear");
                    activity.caseType = caseActivity.getString("caseType");
                    activity.caseMonth = caseActivity.getString("caseMonth");
                    activity.caseNumber = caseActivity.getString("caseNumber");
                    activity.fileName = caseActivity.getString("fileName");
                    activity.to = caseActivity.getString("to");
                    activity.type = caseActivity.getString("type");
                    activity.comment = caseActivity.getString("comment");
                    activity.from = caseActivity.getString("from");
                } else {
                    activity.id = caseActivity.getInt("id");
                    activity.user = caseActivity.getString("firstName") + " " + caseActivity.getString("lastName");
                    activity.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                    activity.action = caseActivity.getString("action");
                    activity.caseYear = caseActivity.getString("caseYear");
                    activity.caseType = caseActivity.getString("caseType");
                    activity.caseMonth = caseActivity.getString("caseMonth");
                    activity.caseNumber = caseActivity.getString("caseNumber");
                    activity.fileName = caseActivity.getString("fileName");
                    activity.to = caseActivity.getString("to");
                    activity.type = caseActivity.getString("type");
                    activity.comment = caseActivity.getString("comment");
                    activity.from = caseActivity.getString("from");
                }
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadActivityByID(id, user);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activity;
    }

    public static void updateActivtyEntry(Activity activty) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "update Activity SET"
                    + " [to] = ?,"
                    + " [from] = ?,"
                    + " type = ?,"
                    + " comment = ?,"
                    + " action = ?,"
                    + " fileName = ?"
                    + " Where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, activty.to);
            preparedStatement.setString(2, activty.from);
            preparedStatement.setString(3, activty.type);
            preparedStatement.setString(4, activty.comment);
            preparedStatement.setString(5, activty.action);
            preparedStatement.setString(6, activty.fileName);
            preparedStatement.setInt(7, activty.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                updateActivtyEntry(activty);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static List<Activity> loadActivityDocumentsByGlobalCase() {
        List<Activity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Activity WHERE "
                    + "awaitingTimestamp = 0 AND "
                    + "fileName IS NOT NULL AND "
                    + "caseYear = ? AND "
                    + "caseType = ? AND "
                    + "caseMonth = ? AND "
                    + "caseNumber = ? "
                    + " and active = 1"
                    + " ORDER BY date DESC, activity.id DESC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadActivityDocumentsByGlobalCase();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }

    public static List<Activity> loadActivityDocumentsByGlobalCaseORG() {
        List<Activity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Activity WHERE "
                    + "awaitingTimestamp = 0 AND "
                    + "fileName IS NOT NULL AND "
                    + "caseType = ? AND "
                    + "caseNumber = ? "
                    + " and active = 1"
                    + " ORDER BY date DESC, activity.id DESC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, Global.caseType.equals("Civil Service Commission") ? "CSC" : "ORG");
            preparedStatement.setString(2, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadActivityDocumentsByGlobalCaseORG();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }

    public static List<Activity> loadActivityDocumentsByGlobalCasePublicRecords() {
        List<Activity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Activity WHERE "
                    + "awaitingTimestamp = 0 AND "
                    + "fileName IS NOT NULL AND fileName <>'' AND "
                    + "caseYear = ? AND "
                    + "caseType = ? AND "
                    + "caseMonth = ? AND "
                    + "caseNumber = ? AND "
                    + "active = 1 and "
                    + "action NOT LIKE '%UNREDACTED%' "
                    + "ORDER BY date DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, Global.caseYear);
            preparedStatement.setString(2, Global.caseType);
            preparedStatement.setString(3, Global.caseMonth);
            preparedStatement.setString(4, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                act.redacted = caseActivity.getBoolean("redacted");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadActivityDocumentsByGlobalCasePublicRecords();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }

    public static List<Activity> loadActivityDocumentsByGlobalCaseORGCSCPublicRecords() {
        List<Activity> activityList = new ArrayList<>();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Activity WHERE "
                    + "awaitingTimestamp = 0 AND "
                    + "fileName IS NOT NULL AND fileName <>'' AND "
                    + "caseYear IS NULL AND "
                    + "caseType = ? AND "
                    + "caseMonth IS NULL AND "
                    + "caseNumber = ? AND "
                    + "active = 1 and "
                    + "action NOT LIKE '%UNREDACTED%' "
                    + "ORDER BY date DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, Global.caseType);
            preparedStatement.setString(2, Global.caseNumber);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                act.redacted = caseActivity.getBoolean("redacted");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadActivityDocumentsByGlobalCaseORGCSCPublicRecords();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }

    public static List<Activity> loadDocumentsBySectionAwaitingRedaction() {
        Statement stmt = null;

        List casetypes = CaseType.getCaseType();
        List<Activity> activityList = new ArrayList<>();

        try {
            int i = 0;
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Activity WHERE "
                    + "awaitingTimestamp = 0 AND "
                    + "fileName IS NOT NULL AND "
                    + "active = 1 AND "
                    + "redacted = 0 AND "
                    + "action LIKE 'REDACTED - %' ";

            if (!casetypes.isEmpty()) {
                sql += "AND (";

                while (i < casetypes.size()) {
                    sql += " Activity.caseType = ? OR";
                    i++;
                }

                sql = sql.substring(0, (sql.length() - 2)) + ")";
            }

            sql += " ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            int count = 0;
            for (Object casetype : casetypes) {
                count = count + 1;
                preparedStatement.setString(count, casetype.toString());
            }

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                Activity act = new Activity();
                act.id = caseActivity.getInt("id");
                act.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                act.action = caseActivity.getString("action");
                act.caseYear = caseActivity.getString("caseYear");
                act.caseType = caseActivity.getString("caseType");
                act.caseMonth = caseActivity.getString("caseMonth");
                act.caseNumber = caseActivity.getString("caseNumber");
                act.fileName = caseActivity.getString("fileName");
                activityList.add(act);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadDocumentsBySectionAwaitingRedaction();
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }

    public static List<Activity> loadMailLogBySectionActiveSection(String startDate, String endDate, String to, String section) {
        List<Activity> activityList = new ArrayList<>();

        String sql = "";
        Statement stmt = null;
        PreparedStatement preparedStatement = null;

        try {
            stmt = Database.connectToDB().createStatement();
            switch (section) {
                case "Hearings":
                    sql = "SELECT Activity.* FROM Activity "
                            + "INNER JOIN HearingCase ON HearingCase.caseYear = Activity.caseYear "
                            + "AND HearingCase.caseType = Activity.caseType "
                            + "AND HearingCase.caseMonth = Activity.caseMonth "
                            + "AND HearingCase.caseNumber = Activity.caseNumber "
                            + "WHERE Activity.mailLog >= ?  AND Activity.mailLog <= ? "
                            + "AND Activity.fileName IS NOT NULL AND Activity.fileName != '' "
                            + "AND Activity.active = 1 AND Activity.[to] LIKE ? "
                            + "ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC, activity.id DESC";
                    preparedStatement = stmt.getConnection().prepareStatement(sql);

                    preparedStatement.setString(1, startDate);
                    preparedStatement.setString(2, endDate);
                    preparedStatement.setString(3, "%" + to + "%");
                    break;
                case "CSC":
                case "ORG":
                    sql = "SELECT Activity.* FROM Activity "
                            + "WHERE Activity.mailLog >= ?  AND Activity.mailLog <= ? "
                            + "AND Activity.fileName IS NOT NULL AND Activity.fileName != '' "
                            + "AND Activity.active = 1 AND Activity.[to] LIKE ? "
                            + "AND activity.casetype = ? "
                            + "ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC, activity.id DESC";
                    preparedStatement = stmt.getConnection().prepareStatement(sql);

                    preparedStatement.setString(1, startDate);
                    preparedStatement.setString(2, endDate);
                    preparedStatement.setString(3, "%" + to + "%");
                    preparedStatement.setString(4, section);
                    break;
                default:
                    sql = "SELECT Activity.* FROM Activity LEFT JOIN caseType ON activity.casetype = casetype.casetype "
                            + "WHERE Activity.mailLog >= ?  AND Activity.mailLog <= ? "
                            + "AND Activity.fileName IS NOT NULL AND Activity.fileName != '' "
                            + "AND Activity.active = 1 AND Activity.[to] LIKE ? "
                            + "AND CaseType.section = ? "
                            + "ORDER BY Activity.CaseYear DESC, Activity.caseMonth DESC, Activity.caseNumber DESC, activity.id DESC";
                    preparedStatement = stmt.getConnection().prepareStatement(sql);

                    preparedStatement.setString(1, startDate);
                    preparedStatement.setString(2, endDate);
                    preparedStatement.setString(3, "%" + to + "%");
                    preparedStatement.setString(4, section);
                    break;
            }

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                Activity activity = new Activity();
                activity.id = caseActivity.getInt("id");
                activity.date = Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
                activity.action = caseActivity.getString("action");
                activity.caseYear = caseActivity.getString("caseYear");
                activity.caseType = caseActivity.getString("caseType");
                activity.caseMonth = caseActivity.getString("caseMonth");
                activity.caseNumber = caseActivity.getString("caseNumber");
                activity.fileName = caseActivity.getString("fileName");
                activity.to = caseActivity.getString("to");
                activity.type = caseActivity.getString("type");
                activity.comment = caseActivity.getString("comment");
                activity.from = caseActivity.getString("from");
                activity.mailLog = Global.mmddyyyy.format(new Date(caseActivity.getDate("mailLog").getTime()));
                activityList.add(activity);
            }
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                loadMailLogBySectionActiveSection(startDate, endDate, to, section);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activityList;
    }

    public static void updateRedactedStatus(boolean redacted, int id) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE  activity SET redacted = ? where id = ?";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setBoolean(1, redacted);
            ps.setInt(2, id);

            ps.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if (ex.getCause() instanceof SQLServerException) {
                updateRedactedStatus(redacted, id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateUnRedactedAction(String action, int id) {
        Statement stmt = null;

        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE activity SET action = ? where id = ?";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);
            ps.setString(1, action);
            ps.setInt(2, id);

            ps.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if (ex.getCause() instanceof SQLServerException) {
                updateUnRedactedAction(action, id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static Activity getFULLActivityByID(int id) {
        Activity activity = new Activity();

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM Activity WHERE Activity.id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet caseActivity = preparedStatement.executeQuery();

            while (caseActivity.next()) {
                activity.id = caseActivity.getInt("id");
                activity.caseYear = caseActivity.getString("caseYear");
                activity.caseType = caseActivity.getString("caseType");
                activity.caseMonth = caseActivity.getString("caseMonth");
                activity.caseNumber = caseActivity.getString("caseNumber");
                activity.user = caseActivity.getString("userID");
                activity.date = caseActivity.getTimestamp("date").toString();
                activity.action = caseActivity.getString("action");
                activity.fileName = caseActivity.getString("fileName");
                activity.from = caseActivity.getString("from");
                activity.to = caseActivity.getString("to");
                activity.type = caseActivity.getString("type");
                activity.comment = caseActivity.getString("comment");
                activity.redacted = caseActivity.getBoolean("redacted");
                activity.awaitingScan = caseActivity.getBoolean("awaitingTimestamp");
                activity.active = caseActivity.getBoolean("awaitingTimestamp");
                activity.mailLog = caseActivity.getTimestamp("mailLog") == null ? "" : Global.mmddyyyyhhmma.format(new Date(caseActivity.getTimestamp("date").getTime()));
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if (ex.getCause() instanceof SQLServerException) {
                getFULLActivityByID(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return activity;
    }

    public static void duplicatePublicRecordActivty(Activity item) {
        Timestamp time = null;

        try {
            time = new Timestamp(Global.SQLDateTimeFormat.parse(item.date).getTime());
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity ("
                    + "caseYear, " //01
                    + "caseType, " //02
                    + "caseMonth, " //03
                    + "caseNumber, " //04
                    + "userID, " //05
                    + "date, " //06
                    + "action, " //07
                    + "fileName, " //08
                    + "[from], " //09
                    + "[to], " //10
                    + "type, " //11
                    + "comment, " //12
                    + "redacted, " //13
                    + "awaitingTimeStamp, "//14
                    + "active, " //15
                    + "mailLog " //16
                    + ") VALUES (";
            for (int i = 0; i < 15; i++) {
                sql += "?, ";   //01-15
            }
            sql += "?)"; //16

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            preparedStatement.setString(1, item.caseYear);
            preparedStatement.setString(2, item.caseType);
            preparedStatement.setString(3, item.caseMonth);
            preparedStatement.setString(4, item.caseNumber);
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, time);
            preparedStatement.setString(7, item.action);
            preparedStatement.setString(8, item.fileName);
            preparedStatement.setString(9, item.from);
            preparedStatement.setString(10, item.to);
            preparedStatement.setString(11, item.type);
            preparedStatement.setString(12, item.comment);
            preparedStatement.setBoolean(13, item.redacted);
            preparedStatement.setBoolean(14, item.awaitingScan);
            preparedStatement.setBoolean(15, true); //active
            preparedStatement.setNull(16, java.sql.Types.DATE); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if (ex.getCause() instanceof SQLServerException) {
                duplicatePublicRecordActivty(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void deleteActivityByID(int id) {

        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "DELETE FROM Activity WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if (ex.getCause() instanceof SQLServerException) {
                deleteActivityByID(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void addPurgedActivityEntry(String action, CaseNumberModel caseNum) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO Activity VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, caseNum.getCaseYear());
            preparedStatement.setString(2, caseNum.getCaseType());
            preparedStatement.setString(3, caseNum.getCaseMonth());
            preparedStatement.setString(4, caseNum.getCaseNumber());
            preparedStatement.setInt(5, Global.activeUser.id);
            preparedStatement.setTimestamp(6, new Timestamp(System.currentTimeMillis()));
            preparedStatement.setString(7, action.equals("") ? null : action);
            preparedStatement.setString(8, null);
            preparedStatement.setString(9, null); //from
            preparedStatement.setString(10, null); //to
            preparedStatement.setString(11, null); //type
            preparedStatement.setString(12, null); //comment
            preparedStatement.setBoolean(13, false); //redacted
            preparedStatement.setBoolean(14, false); //awaiting timestamp
            preparedStatement.setBoolean(15, true); //active
            preparedStatement.setTimestamp(16, null); //mailLog

            preparedStatement.executeUpdate();

        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                addPurgedActivityEntry(action, caseNum);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void removeFileLinkFromActivtyEntry(int activtyID) {
        Statement stmt = null;

        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "update Activity SET fileName = NULL Where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, activtyID);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            if (ex.getCause() instanceof SQLServerException) {
                removeFileLinkFromActivtyEntry(activtyID);
            } else {
                SlackNotification.sendNotification(ex);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

}
