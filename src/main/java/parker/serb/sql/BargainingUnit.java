package parker.serb.sql;

import com.microsoft.sqlserver.jdbc.SQLServerException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class BargainingUnit {
    
    public int id;
    public boolean active;
    public String employerNumber;
    public String unitNumber;
    public String cert;
    public String buEmployerName;
    public String jurisdiction;
    public String county;
    public String lUnion;
    public String local;
    public boolean strike;
    public String lGroup;
    public Timestamp certDate;
    public boolean enabled;
    public String caseRefYear;
    public String caseRefSection;
    public String caseRefMonth;
    public String caseRefSequence;
    public String unitDescription;
    public String notes;
    
    public static String getCertStatus(String number) {
        
        String[] buNumberParts = number.split("-");
        String certStatus = "";
        
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();

            String sql = "select cert from BarginingUnit where EmployerNumber = ? and UnitNumber = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, buNumberParts[0]);
            preparedStatement.setString(2, buNumberParts[1]);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                certStatus = caseActivity.getString("cert");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getCertStatus(number);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return certStatus;
    }

    public static List loadBUList() {
        List<BargainingUnit> employerList = new ArrayList<>();
            
        Statement stmt = null;
        
        try {

            stmt = Database.connectToDB().createStatement();
            
            String sql = "select id, employerNumber, unitNumber, buEmployerName,"
                    + " lUnion, county, unitDescription, caseRefYear,"
                    + " caseRefSection, caseRefMonth, caseRefSequence, local, cert, notes"
                    + " from BarginingUnit"
                    + " Where active = 1"
                    + " ORDER BY buEmployerName ASC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            ResultSet employerListRS = preparedStatement.executeQuery();
            
            while(employerListRS.next()) {
                BargainingUnit emp = new BargainingUnit();
                emp.id = employerListRS.getInt("id");
                emp.employerNumber = employerListRS.getString("employerNumber") == null ? "" : employerListRS.getString("employerNumber");
                emp.unitNumber = employerListRS.getString("unitNumber") == null ? "" : employerListRS.getString("unitNumber");
                emp.buEmployerName = employerListRS.getString("buEmployerName") == null ? "" : employerListRS.getString("buEmployerName");
                emp.lUnion = employerListRS.getString("lUnion") == null ? "" : employerListRS.getString("lUnion");
                emp.county = employerListRS.getString("County") == null ? "" : employerListRS.getString("County");
                emp.unitDescription = employerListRS.getString("unitDescription") == null ? "" : employerListRS.getString("unitDescription");
                emp.caseRefYear = employerListRS.getString("caseRefYear") == null ? "" : employerListRS.getString("caseRefYear");
                emp.caseRefSection = employerListRS.getString("caseRefSection") == null ? "" : employerListRS.getString("caseRefSection");
                emp.caseRefMonth = employerListRS.getString("caseRefMonth") == null ? "" : employerListRS.getString("caseRefMonth");
                emp.caseRefSequence = employerListRS.getString("caseRefSequence") == null ? "" : employerListRS.getString("caseRefSequence");
                emp.local = employerListRS.getString("local") == null ? "" : employerListRS.getString("local");
                emp.cert = employerListRS.getString("cert") == null ? "" : employerListRS.getString("cert");
                emp.notes = employerListRS.getString("notes") == null ? "" : employerListRS.getString("notes");

                employerList.add(emp);
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                loadBUList();
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return employerList;
    }
    
    public static String getUnitDescription(String buNumber) {
        String[] buNumberParts = buNumber.split("-");
        String unitDesc = "";
            
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select unitDescription from BarginingUnit where EmployerNumber = ? and UnitNumber = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, buNumberParts[0]);
            preparedStatement.setString(2, buNumberParts[1]);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                unitDesc = caseActivity.getString("unitDescription");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getUnitDescription(buNumber);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return unitDesc;
    }
    
    public static BargainingUnit getBUbyID(String id) {
        BargainingUnit bu = new BargainingUnit();
            
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "select * from BarginingUnit where id = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, id);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                bu.id = caseActivity.getInt("id");
                bu.active = caseActivity.getBoolean("active");
                bu.employerNumber = caseActivity.getString("employerNumber");
                bu.unitNumber = caseActivity.getString("unitNumber");
                bu.cert = caseActivity.getString("cert");
                bu.buEmployerName = caseActivity.getString("buEmployerName");
                bu.jurisdiction = caseActivity.getString("jurisdiction");
                bu.county = caseActivity.getString("county");
                bu.lUnion = caseActivity.getString("lUnion");
                bu.local = caseActivity.getString("local");
                bu.strike = caseActivity.getBoolean("strike");
                bu.lGroup = caseActivity.getString("lGroup");
                bu.certDate = caseActivity.getTimestamp("certDate");
                bu.enabled = caseActivity.getBoolean("enabled");
                bu.caseRefYear = caseActivity.getString("caseRefYear");
                bu.caseRefSection = caseActivity.getString("caseRefSection");
                bu.caseRefMonth = caseActivity.getString("caseRefMonth");
                bu.caseRefSequence = caseActivity.getString("caseRefSequence");
                bu.unitDescription = caseActivity.getString("unitDescription");
                bu.notes = caseActivity.getString("notes");
            }
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                getBUbyID(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
        return bu;
    }
    
    public static void updateBUByID(BargainingUnit bu) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "update BarginingUnit set "
                    + "employerNumber = ?, "
                    + "unitNumber = ?, "
                    + "cert = ?, "
                    + "buEmployerName = ?, "
                    + "jurisdiction = ?, "
                    + "county = ?, "
                    + "lUnion = ?, "
                    + "local = ?, "
                    + "strike = ?, "
                    + "lGroup = ?, "
                    + "certDate = ?, "
                    + "enabled = ?, "
                    + "caseRefYear = ?, "
                    + "caseRefSection = ?, "
                    + "caseRefMonth = ?, "
                    + "caseRefSequence = ?, "
                    + "unitDescription = ?, "
                    + "notes = ? "
                    + "where id = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, bu.employerNumber.equals("") ? null : bu.employerNumber);
            preparedStatement.setString(2, bu.unitNumber.equals("") ? null : bu.unitNumber);
            preparedStatement.setString(3, bu.cert.equals("") ? null : bu.cert);
            preparedStatement.setString(4, bu.buEmployerName.equals("") ? null : bu.buEmployerName);
            preparedStatement.setString(5, bu.jurisdiction.equals("") ? null : bu.jurisdiction);
            preparedStatement.setString(6, bu.county.equals("") ? null : bu.county);
            preparedStatement.setString(7, bu.lUnion.equals("") ? null : bu.lUnion);
            preparedStatement.setString(8, bu.local.equals("") ? null : bu.local);
            preparedStatement.setBoolean(9, bu.strike);
            preparedStatement.setString(10, bu.lGroup.equals("") ? null : bu.lGroup);
            preparedStatement.setTimestamp(11, bu.certDate);
            preparedStatement.setBoolean(12, bu.enabled);
            preparedStatement.setString(13, bu.caseRefYear);
            preparedStatement.setString(14, bu.caseRefSection);
            preparedStatement.setString(15, bu.caseRefMonth);
            preparedStatement.setString(16, bu.caseRefSequence);
            preparedStatement.setString(17, bu.unitDescription.equals("") ? null : bu.unitDescription);
            preparedStatement.setString(18, bu.notes.equals("") ? null : bu.notes);
            preparedStatement.setInt(19, bu.id);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateBUByID(bu);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void disableBU(String id) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "update BarginingUnit set active = 0 where id = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setInt(1, Integer.valueOf(id));
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                disableBU(id);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
    
    public static void createBU(BargainingUnit bu) {
        Statement stmt = null;
        
        try {
            stmt = Database.connectToDB().createStatement();

            String sql = "insert into BarginingUnit values ("
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?, "
                    + "?)";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setBoolean(1, true);
            preparedStatement.setString(2, bu.employerNumber.equals("") ? null : bu.employerNumber);
            preparedStatement.setString(3, bu.unitNumber.equals("") ? null : bu.unitNumber);
            preparedStatement.setString(4, bu.cert.equals("") ? null : bu.cert);
            preparedStatement.setString(5, bu.buEmployerName.equals("") ? null : bu.buEmployerName);
            preparedStatement.setString(6, bu.jurisdiction.equals("") ? null : bu.jurisdiction);
            preparedStatement.setString(7, bu.county.equals("") ? null : bu.county);
            preparedStatement.setString(8, bu.lUnion.equals("") ? null : bu.lUnion);
            preparedStatement.setString(9, bu.local.equals("") ? null : bu.local);
            preparedStatement.setBoolean(10, bu.strike);
            preparedStatement.setString(11, bu.lGroup.equals("") ? null : bu.lGroup);
            preparedStatement.setTimestamp(12, bu.certDate);
            preparedStatement.setBoolean(13, bu.enabled);
            preparedStatement.setString(14, bu.caseRefYear);
            preparedStatement.setString(15, bu.caseRefSection);
            preparedStatement.setString(16, bu.caseRefMonth);
            preparedStatement.setString(17, bu.caseRefSequence);
            preparedStatement.setString(18, bu.unitDescription.equals("") ? null : bu.unitDescription);
            preparedStatement.setString(19, bu.notes.equals("") ? null : bu.notes);
            
            preparedStatement.executeUpdate();
            
        } catch (SQLException ex) {
            SlackNotification.sendNotification(ex);
            if(ex.getCause() instanceof SQLServerException) {
                updateBUByID(bu);
            } 
        } finally {
            DbUtils.closeQuietly(stmt);
        }
    }
}
