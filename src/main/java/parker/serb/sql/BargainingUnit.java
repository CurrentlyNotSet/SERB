package parker.serb.sql;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.dbutils.DbUtils;
import parker.serb.Global;

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
    
    public static String getCertStatus(String number) {
        String[] buNumberParts = number.split("-");
        String certStatus = "";
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select cert from BarginingUnit where EmployerNumber = ? and UnitNumber = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, buNumberParts[0]);
            preparedStatement.setString(2, buNumberParts[1]);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                certStatus = caseActivity.getString("cert");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return certStatus;
    }

    public static List loadBUList() {
        List<BargainingUnit> employerList = new ArrayList<BargainingUnit>();
            
        try {

            Statement stmt = Database.connectToDB().createStatement();
            
            String sql = "select employerNumber, unitNumber, buEmployerName,"
                    + " lUnion, county, unitDescription, caseRefYear,"
                    + " caseRefSection, caseRefMonth, caseRefSequence, local, cert"
                    + " from BarginingUnit"
                    + " Where active = 1"
                    + " ORDER BY employerNumber ASC ";

            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            ResultSet employerListRS = preparedStatement.executeQuery();
            
            while(employerListRS.next()) {
                BargainingUnit emp = new BargainingUnit();
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
                employerList.add(emp);
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return employerList;
    }
    
    public static String getUnitDescription(String buNumber) {
        String[] buNumberParts = buNumber.split("-");
        String unitDesc = "";
            
        try {

            Statement stmt = Database.connectToDB().createStatement();

            String sql = "select unitDescription from BarginingUnit where EmployerNumber = ? and UnitNumber = ?";
                    
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(sql);
            preparedStatement.setString(1, buNumberParts[0]);
            preparedStatement.setString(2, buNumberParts[1]);

            ResultSet caseActivity = preparedStatement.executeQuery();
            
            while(caseActivity.next()) {
                unitDesc = caseActivity.getString("unitDescription");
            }
        } catch (SQLException ex) {
            Logger.getLogger(Audit.class.getName()).log(Level.SEVERE, null, ex);
        }
        return unitDesc;
    }
}
