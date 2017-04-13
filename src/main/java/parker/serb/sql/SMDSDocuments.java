/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.SlackNotification;

/**
 *
 * @author User
 */
public class SMDSDocuments {
    public int id;
    public boolean active;
    public String section;
    public String type;
    public String description;
    public String fileName;
    public int dueDate;
    public String group;
    public String historyFileName;
    public String historyDescription;
    public String CHDCHG;
    public String questionsFileName;
    public String emailSubject;
    public String parameters;
    public String emailBody;
    public double sortOrder;

    public static List loadDocumentNamesByTypeAndSection(String section, String type) {
        List<SMDSDocuments> documentList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from SMDSDocuments where"
                    + " section = ? AND"
                    + " type = ? AND"
                    + " active = 1"
                    + " order by sortOrder, cast(description as nvarchar(max))";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, type);

            ResultSet docList = preparedStatement.executeQuery();

            while(docList.next()) {
                SMDSDocuments doc = new SMDSDocuments();
                doc.id = docList.getInt("id");
                doc.active = docList.getBoolean("active");
                doc.section = docList.getString("section");
                doc.type = docList.getString("type");
                doc.description = docList.getString("description");
                doc.fileName = docList.getString("fileName");
                doc.dueDate = docList.getInt("dueDate");
                doc.group = docList.getString("group");
                doc.historyFileName = docList.getString("historyFileName");
                doc.historyDescription = docList.getString("historyDescription");
                doc.CHDCHG = docList.getString("CHDCHG");
                doc.questionsFileName = docList.getString("questionsFileName");
                doc.emailSubject = docList.getString("emailSubject");
                doc.parameters = docList.getString("parameters");
                doc.emailBody = docList.getString("emailBody");
                doc.sortOrder = docList.getDouble("sortOrder");
                documentList.add(doc);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadDocumentNamesByTypeAndSection(section, type);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return documentList;
    }

    public static List loadDocumentNamesByTypeAndSectionCEAChoice(String section, String type, boolean CEA) {
        List<SMDSDocuments> documentList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from SMDSDocuments where"
                    + " section = ? AND "
                    + " type = ? AND ";
                    if (CEA){
                        sql += " description LIKE '%CEA%' AND ";
                    } else {
                        sql += " description NOT LIKE '%CEA%' AND ";
                    }
                    sql += " active = 1 "
                    + " ORDER BY sortOrder, cast(description as nvarchar(max))";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, type);

            ResultSet docList = preparedStatement.executeQuery();

            while(docList.next()) {
                SMDSDocuments doc = new SMDSDocuments();
                doc.id = docList.getInt("id");
                doc.active = docList.getBoolean("active");
                doc.section = docList.getString("section");
                doc.type = docList.getString("type");
                doc.description = docList.getString("description");
                doc.fileName = docList.getString("fileName");
                doc.dueDate = docList.getInt("dueDate");
                doc.group = docList.getString("group");
                doc.historyFileName = docList.getString("historyFileName");
                doc.historyDescription = docList.getString("historyDescription");
                doc.CHDCHG = docList.getString("CHDCHG");
                doc.questionsFileName = docList.getString("questionsFileName");
                doc.emailSubject = docList.getString("emailSubject");
                doc.parameters = docList.getString("parameters");
                doc.emailBody = docList.getString("emailBody");
                doc.sortOrder = docList.getDouble("sortOrder");
                documentList.add(doc);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadDocumentNamesByTypeAndSection(section, type);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return documentList;
    }

    public static List loadDocumentNamesByTypeAndSectionWithAll(String section, String type) {
        List<SMDSDocuments> documentList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SMDSDocuments WHERE"
                    + " (section = 'ALL' OR"
                    + " section = ?) AND"
                    + " type = ? AND"
                    + " fileName != 'MailLog.jasper' AND"
                    + " active = 1"
                    + " ORDER BY sortOrder, cast(description AS nvarchar(max))";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, type);

            ResultSet docList = preparedStatement.executeQuery();

            while(docList.next()) {
                SMDSDocuments doc = new SMDSDocuments();
                doc.id = docList.getInt("id");
                doc.active = docList.getBoolean("active");
                doc.section = docList.getString("section");
                doc.type = docList.getString("type");
                doc.description = docList.getString("description");
                doc.fileName = docList.getString("fileName");
                doc.dueDate = docList.getInt("dueDate");
                doc.group = docList.getString("group");
                doc.historyFileName = docList.getString("historyFileName");
                doc.historyDescription = docList.getString("historyDescription");
                doc.CHDCHG = docList.getString("CHDCHG");
                doc.questionsFileName = docList.getString("questionsFileName");
                doc.emailSubject = docList.getString("emailSubject");
                doc.parameters = docList.getString("parameters");
                doc.emailBody = docList.getString("emailBody");
                doc.sortOrder = docList.getDouble("sortOrder");
                documentList.add(doc);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadDocumentNamesByTypeAndSection(section, type);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return documentList;
    }

    public static List loadDocumentGroupByTypeAndSection(String section, String type) {
        List<String> documentList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select DISTINCT [group] from SMDSDocuments where"
                    + " section = ? AND"
                    + " type = ? AND"
                    + " active = 1";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, type);

            ResultSet docList = preparedStatement.executeQuery();

            while(docList.next()) {
                if (docList.getString("group") != null) {
                    documentList.add(docList.getString("group"));
                }
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadDocumentGroupByTypeAndSection(section, type);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return documentList;
    }

    public static List loadDocumentNamesByTypeSectionGroup(String section, String type, String group) {
        List<SMDSDocuments> documentList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from SMDSDocuments where"
                    + " section = ? AND "
                    + " type = ? AND "
                    + " [group] = ? AND "
                    + " active = 1"
                    + " order by sortOrder, cast(description as nvarchar(max))";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, section);
            preparedStatement.setString(2, type);
            preparedStatement.setString(3, group);

            ResultSet docList = preparedStatement.executeQuery();

            while(docList.next()) {
                SMDSDocuments doc = new SMDSDocuments();
                doc.id = docList.getInt("id");
                doc.active = docList.getBoolean("active");
                doc.section = docList.getString("section");
                doc.type = docList.getString("type");
                doc.description = docList.getString("description");
                doc.fileName = docList.getString("fileName");
                doc.dueDate = docList.getInt("dueDate");
                doc.group = docList.getString("group");
                doc.historyFileName = docList.getString("historyFileName");
                doc.historyDescription = docList.getString("historyDescription");
                doc.CHDCHG = docList.getString("CHDCHG");
                doc.questionsFileName = docList.getString("questionsFileName");
                doc.emailSubject = docList.getString("emailSubject");
                doc.parameters = docList.getString("parameters");
                doc.emailBody = docList.getString("emailBody");
                doc.sortOrder = docList.getDouble("sortOrder");
                documentList.add(doc);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadDocumentNamesByTypeSectionGroup(section, type, group);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return documentList;
    }

    public static List loadRepMemos(String type) {
        String switchString = "";
        switch (type) {
            case "JTAC":
                switchString = " AND (fileName LIKE 'AC%' OR fileName LIKE 'All%') ";
                break;
            case "All Types":
                switchString = "";
                break;
            default:
                switchString = "AND  (fileName LIKE 'All%' OR fileName LIKE '" + type + "%')";
                break;
        }



        List<SMDSDocuments> documentList = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from SMDSDocuments where"
                    + " section = 'REP' AND "
                    + " type = 'Memo' AND "
                    + " active = 1 "
                    + switchString
                    + " order by sortOrder, cast(description as nvarchar(max))";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);

            ResultSet docList = preparedStatement.executeQuery();

            while(docList.next()) {
                SMDSDocuments doc = new SMDSDocuments();
                doc.id = docList.getInt("id");
                doc.active = docList.getBoolean("active");
                doc.section = docList.getString("section");
                doc.type = docList.getString("type");
                doc.description = docList.getString("description");
                doc.fileName = docList.getString("fileName");
                doc.dueDate = docList.getInt("dueDate");
                doc.group = docList.getString("group");
                doc.historyFileName = docList.getString("historyFileName");
                doc.historyDescription = docList.getString("historyDescription");
                doc.CHDCHG = docList.getString("CHDCHG");
                doc.questionsFileName = docList.getString("questionsFileName");
                doc.emailSubject = docList.getString("emailSubject");
                doc.parameters = docList.getString("parameters");
                doc.emailBody = docList.getString("emailBody");
                doc.sortOrder = docList.getDouble("sortOrder");
                documentList.add(doc);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadRepMemos(type);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return documentList;
    }

    public static SMDSDocuments findDocumentByID(int id) {
        SMDSDocuments doc = new SMDSDocuments();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from SMDSDocuments where id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet foundDoc = preparedStatement.executeQuery();

            if(foundDoc.next()) {
                doc.id = foundDoc.getInt("id");
                doc.active = foundDoc.getBoolean("active");
                doc.section = foundDoc.getString("section");
                doc.type = foundDoc.getString("type");
                doc.description = foundDoc.getString("description");
                doc.fileName = foundDoc.getString("fileName");
                doc.dueDate = foundDoc.getInt("dueDate");
                doc.group = foundDoc.getString("group");
                doc.historyFileName = foundDoc.getString("historyFileName");
                doc.historyDescription = foundDoc.getString("historyDescription");
                doc.CHDCHG = foundDoc.getString("CHDCHG");
                doc.questionsFileName = foundDoc.getString("questionsFileName");
                doc.emailSubject = foundDoc.getString("emailSubject");
                doc.parameters = foundDoc.getString("parameters");
                doc.emailBody = foundDoc.getString("emailBody");
                doc.sortOrder = foundDoc.getDouble("sortOrder");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findDocumentByID(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return doc;
    }

    public static SMDSDocuments findDocumentByIDAndDescription(int id, String description) {
        SMDSDocuments doc = new SMDSDocuments();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from SMDSDocuments where id = ? AND description = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);
            preparedStatement.setString(1, description);

            ResultSet foundDoc = preparedStatement.executeQuery();

            if(foundDoc.next()) {
                doc.id = foundDoc.getInt("id");
                doc.active = foundDoc.getBoolean("active");
                doc.section = foundDoc.getString("section");
                doc.type = foundDoc.getString("type");
                doc.description = foundDoc.getString("description");
                doc.fileName = foundDoc.getString("fileName");
                doc.dueDate = foundDoc.getInt("dueDate");
                doc.group = foundDoc.getString("group");
                doc.historyFileName = foundDoc.getString("historyFileName");
                doc.historyDescription = foundDoc.getString("historyDescription");
                doc.CHDCHG = foundDoc.getString("CHDCHG");
                doc.questionsFileName = foundDoc.getString("questionsFileName");
                doc.emailSubject = foundDoc.getString("emailSubject");
                doc.parameters = foundDoc.getString("parameters");
                doc.emailBody = foundDoc.getString("emailBody");
                doc.sortOrder = foundDoc.getDouble("sortOrder");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findDocumentByID(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return doc;
    }

    public static SMDSDocuments findDocumentByFileName(String fileName) {
        SMDSDocuments doc = new SMDSDocuments();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from SMDSDocuments where fileName = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, fileName);

            ResultSet foundDoc = preparedStatement.executeQuery();

            if(foundDoc.next()) {
                doc.id = foundDoc.getInt("id");
                doc.active = foundDoc.getBoolean("active");
                doc.section = foundDoc.getString("section");
                doc.type = foundDoc.getString("type");
                doc.description = foundDoc.getString("description");
                doc.fileName = foundDoc.getString("fileName");
                doc.dueDate = foundDoc.getInt("dueDate");
                doc.group = foundDoc.getString("group");
                doc.historyFileName = foundDoc.getString("historyFileName");
                doc.historyDescription = foundDoc.getString("historyDescription");
                doc.CHDCHG = foundDoc.getString("CHDCHG");
                doc.questionsFileName = foundDoc.getString("questionsFileName");
                doc.emailSubject = foundDoc.getString("emailSubject");
                doc.parameters = foundDoc.getString("parameters");
                doc.emailBody = foundDoc.getString("emailBody");
                doc.sortOrder = foundDoc.getDouble("sortOrder");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findDocumentByFileName(fileName);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return doc;
    }

    public static SMDSDocuments findDocumentByDescription(String description) {
        SMDSDocuments doc = new SMDSDocuments();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Select * from SMDSDocuments where description = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, description);

            ResultSet foundDoc = preparedStatement.executeQuery();

            if(foundDoc.next()) {
                doc.id = foundDoc.getInt("id");
                doc.active = foundDoc.getBoolean("active");
                doc.section = foundDoc.getString("section");
                doc.type = foundDoc.getString("type");
                doc.description = foundDoc.getString("description");
                doc.fileName = foundDoc.getString("fileName");
                doc.dueDate = foundDoc.getInt("dueDate");
                doc.group = foundDoc.getString("group");
                doc.historyFileName = foundDoc.getString("historyFileName");
                doc.historyDescription = foundDoc.getString("historyDescription");
                doc.CHDCHG = foundDoc.getString("CHDCHG");
                doc.questionsFileName = foundDoc.getString("questionsFileName");
                doc.emailSubject = foundDoc.getString("emailSubject");
                doc.parameters = foundDoc.getString("parameters");
                doc.emailBody = foundDoc.getString("emailBody");
                doc.sortOrder = foundDoc.getDouble("sortOrder");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                findDocumentByDescription(description);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return doc;
    }

    public static List<SMDSDocuments> loadAllDocuments(String[] param) {
        List<SMDSDocuments> list = new ArrayList<>();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SMDSDocuments";
            if (param.length > 0) {
                sql += " WHERE";
                for (int i = 0; i < param.length; i++) {
                    if (i > 0) {
                        sql += " AND";
                    }
                    sql += " CONCAT(section, fileName, description, type) "
                            + "LIKE ?";
                }
            }
            sql += " ORDER BY section, type, description";

            PreparedStatement ps = stmt.getConnection().prepareStatement(sql);

            for (int i = 0; i < param.length; i++) {
                ps.setString((i + 1), "%" + param[i].trim() + "%");
            }

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                SMDSDocuments item = new SMDSDocuments();
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.type = rs.getString("type") == null ? "" : rs.getString("type");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                item.fileName = rs.getString("fileName") == null ? "" : rs.getString("fileName");
                item.dueDate = rs.getInt("dueDate");
                item.group = rs.getString("group") == null ? "" : rs.getString("group");
                item.historyFileName = rs.getString("historyFileName") == null ? "" : rs.getString("historyFileName");
                item.historyDescription = rs.getString("historyDescription") == null ? "" : rs.getString("historyDescription");
                item.CHDCHG = rs.getString("CHDCHG") == null ? "" : rs.getString("CHDCHG");
                item.questionsFileName = rs.getString("questionsFileName") == null ? "" : rs.getString("questionsFileName");
                item.emailSubject = rs.getString("emailSubject") == null ? "" : rs.getString("emailSubject");
                item.parameters = rs.getString("parameters") == null ? "" : rs.getString("parameters");
                item.emailBody = rs.getString("emailBody") == null ? "" : rs.getString("emailBody");
                item.sortOrder = rs.getDouble("sortOrder");
                list.add(item);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadAllDocuments(param);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return list;
    }

    public static SMDSDocuments getDocumentByID(int id) {
        SMDSDocuments item = new SMDSDocuments();

        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "SELECT * FROM SMDSDocuments WHERE id = ?";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, id);

            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                item.id = rs.getInt("id");
                item.active = rs.getBoolean("active");
                item.section = rs.getString("section") == null ? "" : rs.getString("section");
                item.type = rs.getString("type") == null ? "" : rs.getString("type");
                item.description = rs.getString("description") == null ? "" : rs.getString("description");
                item.fileName = rs.getString("fileName") == null ? "" : rs.getString("fileName");
                item.dueDate = rs.getInt("dueDate");
                item.group = rs.getString("group") == null ? "" : rs.getString("group");
                item.historyFileName = rs.getString("historyFileName") == null ? "" : rs.getString("historyFileName");
                item.historyDescription = rs.getString("historyDescription") == null ? "" : rs.getString("historyDescription");
                item.CHDCHG = rs.getString("CHDCHG") == null ? "" : rs.getString("CHDCHG");
                item.questionsFileName = rs.getString("questionsFileName") == null ? "" : rs.getString("questionsFileName");
                item.emailSubject = rs.getString("emailSubject") == null ? "" : rs.getString("emailSubject");
                item.parameters = rs.getString("parameters") == null ? "" : rs.getString("parameters");
                item.emailBody = rs.getString("emailBody") == null ? "" : rs.getString("emailBody");
                item.sortOrder = rs.getDouble("sortOrder");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getDocumentByID(id);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return item;
    }

    public static void createDocument(SMDSDocuments item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "Insert INTO SMDSDocuments ("
                    + "active, "
                    + "section, "
                    + "type, "
                    + "description, "
                    + "fileName, "
                    + "dueDate, "
                    + "[group], "
                    + "historyFileName, "
                    + "historyDescription, "
                    + "CHDCHG, "
                    + "questionsFileName, "
                    + "emailSubject, "
                    + "parameters, "
                    + "emailBody, "
                    + "sortOrder "
                    + ") VALUES ("
                    + "1, " // active
                    + "?, " //1 section
                    + "?, " //2 type
                    + "?, " //3 description
                    + "?, " //4 fileName
                    + "?, " //5 dueDate
                    + "?, " //6 group
                    + "?, " //7 historyFileName
                    + "?, " //8 historyDescription
                    + "?, " //9 CHDCHG
                    + "?, " //10 questionsFileName
                    + "?, " //11 emailSubject
                    + "?, " //12 parameters
                    + "?, " //13 emailBody
                    + "? "  //14 sortOrder
                    + ")";
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString( 1, item.section.equals("") ? null : item.section.trim());
            preparedStatement.setString( 2, item.type.equals("") ? null : item.type.trim());
            preparedStatement.setString( 3, item.description.equals("") ? null : item.description.trim());
            preparedStatement.setString( 4, item.fileName.equals("") ? null : item.fileName.trim());
            if (item.dueDate != -1) {
                preparedStatement.setInt (5, item.dueDate);
            } else {
                preparedStatement.setNull(5, java.sql.Types.INTEGER);
            }
            preparedStatement.setString( 6, item.group.equals("") ? null : item.group.trim());
            preparedStatement.setString( 7, item.historyFileName.equals("") ? null : item.historyFileName.trim());
            preparedStatement.setString( 8, item.historyDescription.equals("") ? null : item.historyDescription.trim());
            preparedStatement.setString( 9, item.CHDCHG.equals("") ? null : item.CHDCHG.trim());
            preparedStatement.setString(10, item.questionsFileName.equals("") ? null : item.questionsFileName.trim());
            preparedStatement.setString(11, item.emailSubject.equals("") ? null : item.emailSubject.trim());
            preparedStatement.setString(12, item.parameters.equals("") ? null : item.parameters.trim());
            preparedStatement.setString(13, item.emailBody.equals("") ? null : item.emailBody.trim());
            if (item.sortOrder != -1) {
                preparedStatement.setDouble(14, item.sortOrder);
            } else {
                preparedStatement.setNull  (14, java.sql.Types.DOUBLE);
            }
            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                createDocument(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }

    public static void updateDocument(SMDSDocuments item) {
        Statement stmt = null;
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "UPDATE SMDSDocuments SET "
                    + "active = ?, "        //01
                    + "section = ?, "       //02
                    + "type = ?, "          //03
                    + "description = ?, "   //04
                    + "fileName = ?, "      //05
                    + "dueDate = ?, "       //06
                    + "[group] = ?, "       //07
                    + "historyFileName = ?, "   //08
                    + "historyDescription = ?, "//09
                    + "CHDCHG = ?, "            //10
                    + "questionsFileName = ?, " //11
                    + "emailSubject = ?, "      //12
                    + "parameters = ?, "        //13
                    + "emailBody = ?, "         //14
                    + "sortOrder = ? "          //15
                    + "where id = ?";           //16

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, item.active);
            preparedStatement.setString( 2, item.section.equals("") ? null : item.section.trim());
            preparedStatement.setString( 3, item.type.equals("") ? null : item.type.trim());
            preparedStatement.setString( 4, item.description.equals("") ? null : item.description.trim());
            preparedStatement.setString( 5, item.fileName.equals("") ? null : item.fileName.trim());
            if (item.dueDate != -1) {
                preparedStatement.setInt (6, item.dueDate);
            } else {
                preparedStatement.setNull(6, java.sql.Types.INTEGER);
            }
            preparedStatement.setString( 7, item.group.equals("") ? null : item.group.trim());
            preparedStatement.setString( 8, item.historyFileName.equals("") ? null : item.historyFileName.trim());
            preparedStatement.setString( 9, item.historyDescription.equals("") ? null : item.historyDescription.trim());
            preparedStatement.setString(10, item.CHDCHG.equals("") ? null : item.CHDCHG.trim());
            preparedStatement.setString(11, item.questionsFileName.equals("") ? null : item.questionsFileName.trim());
            preparedStatement.setString(12, item.emailSubject.equals("") ? null : item.emailSubject.trim());
            preparedStatement.setString(13, item.parameters.equals("") ? null : item.parameters.trim());
            preparedStatement.setString(14, item.emailBody.equals("") ? null : item.emailBody.trim());
            if (item.sortOrder != -1) {
                preparedStatement.setDouble(15, item.sortOrder);
            } else {
                preparedStatement.setNull  (15, java.sql.Types.DOUBLE);
            }
            preparedStatement.setInt    (16, item.id);

            preparedStatement.executeUpdate();
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateDocument(item);
            }
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
