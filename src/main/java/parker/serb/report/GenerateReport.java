/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.report;

import com.alee.laf.optionpane.WebOptionPane;
import java.io.File;
import java.sql.Connection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import parker.serb.Global;
import parker.serb.sql.Audit;
import parker.serb.sql.CaseType;
import parker.serb.sql.Database;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class GenerateReport {

    public static void runReport(SMDSDocuments report) {
        if (report.parameters != null) {
            switch (report.parameters) {
                case "EmployerID":
                    new RequestedInfoEmployerIDPanel(Global.root, true, report);
                    break;
                case "Board Meeting Date":
                    new RequestedInfoOneDatePanel(Global.root, true, report);
                    break;
                case "date":
                    new RequestedInfoOneDatePanel(Global.root, true, report);
                    break;
                case "caseNumber":
                    new RequestedInfoCaseNumberPanel(Global.root, true, report);
                    break;
                case "month":
                    new RequestedInfoDropdownPanel(Global.root, true, report, "Month");
                    break;
                case "UserID":
                    new RequestedInfoDropdownPanel(Global.root, true, report, "UserID");
                    break;
                case "SectionUserID":
                    new RequestedInfoDropdownPanel(Global.root, true, report, "SectionUserID");
                    break;
                case "begin date, end date":
                    new RequestedInfoTwoDatePanel(Global.root, true, report);
                    break;
                case "begin date, end date, InvestigatorID":
                    new RequestedInfoTwoDateIDPanel(Global.root, true, report, "InvestigatorID");
                    break;
                case "begin date, end date, SectionUserID":
                    new RequestedInfoTwoDateIDPanel(Global.root, true, report, "SectionUserID");
                    break;
                case "begin date, end date, UserID":
                    new RequestedInfoTwoDateIDPanel(Global.root, true, report, "UserID");
                    break;
                case "begin date, end date, LikeString":
                    new RequestedInfoTwoDateStringPanel(Global.root, true, report, "like");
                    break;
                case "begin date, end date, String":
                    new RequestedInfoTwoDateStringPanel(Global.root, true, report, "exact");
                    break;
                case "ActivityType, Year":
                    new RequestedInfoComboBoxStringPanel(Global.root, true, report, "ActivityType, Year");
                    break;
                case "CMDSActivityType, Year":
                    new RequestedInfoComboBoxStringPanel(Global.root, true, report, "CMDSActivityType, Year");
                    break;
                case "Year, InvestigatorID":
                    new RequestedInfoComboBoxStringPanel(Global.root, true, report, "InvestigatorID, Year");
                    break;
                case "ActivityType":
                    new RequestedInfoDropdownPanel(Global.root, true, report, "ActivityType");
                    break;
                case "InvestigatorID":
                    new RequestedInfoDropdownPanel(Global.root, true, report, "InvestigatorID");
                    break;
                case "groupNumber":
                    new RequestedInfoStringPanel(Global.root, true, report, "groupNumber");
                    break;
                case "PBR Box Number":
                    new RequestedInfoStringPanel(Global.root, true, report, "PBR Box Number");
                    break;
                case "Month, Year":
                    new RequestedInfoMonthYearPanel(Global.root, true, report);
                    break;
                case "Year":
                    new RequestedInfoStringPanel(Global.root, true, report, "Year");
                    break;
                case "NameSearch":
                    new RequestedInfoStringPanel(Global.root, true, report, "Name");
                    break;
                case "AddressSearch":
                    new RequestedInfoStringPanel(Global.root, true, report, "Address");
                    break;
                case "OrgNumber":
                    new RequestedInfoStringPanel(Global.root, true, report, "OrgNumber");
                    break;
                case "Charging Party, Charged Party":
                    new RequestedInfoTwoStringPanel(Global.root, true, report, "Charging Party, Charged Party");
                    break;
                case "Multi Case Party":
                    new RequestedInfoThreeStringPanel(Global.root, true, report, "Multi Case Party");
                    break;
                case "begin date, end date, EmployerType":
                    new RequestedInfoTwoDateComboBoxPanel(Global.root, true, report, "EmployerType");
                    break;
                case "begin date, end date, Fact Finder":
                    new RequestedInfoTwoDateComboBoxPanel(Global.root, true, report, "Fact Finder");
                    break;
                case "begin date, end date, Conciliator":
                    new RequestedInfoTwoDateComboBoxPanel(Global.root, true, report, "Conciliator");
                    break;
                case "begin date, end date, Mediator":
                    new RequestedInfoTwoDateComboBoxPanel(Global.root, true, report, "Mediator");
                    break;                
                default:
                    HashMap hash = new HashMap();
                    generateReport(report, hash);
                    break;
            }
        } else {
            HashMap hash = new HashMap();
            generateReport(report, hash);
        }
    }

    public static void generateSingleDatesReport(String date, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("date", date);
        generateReport(report, hash);
    }

    public static void generateIDReport(String ID, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("ID", ID);
        generateReport(report, hash);
    }

    public static void generateTwoDatesReport(String Start, String End, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("begin date", Start);
        hash.put("end date", End);
        generateReport(report, hash);
    }

    public static void generateTwoDatesIDReport(String Start, String End, String ID, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("begin date", Start);
        hash.put("end date", End);
        hash.put("ID", ID);
        generateReport(report, hash);
    }

    public static void generateExactStringReport(String exact, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("string", exact);
        generateReport(report, hash);
    }

    public static void generateTwoDatesExactStringReport(String Start, String End, String exact, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("begin date", Start);
        hash.put("end date", End);
        hash.put("string", exact);
        generateReport(report, hash);
    }

    public static void generateMailLogReport(String Start, String End, String to, String section, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("begin date", Start);
        hash.put("end date", End);
        hash.put("assignedTo", to);
        hash.put("section", section);
        generateReport(report, hash);
    }

    public static void generateMailLogFROMReport(String String, String Start, String End, String to, String section, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("string", String);
        hash.put("begin date", Start);
        hash.put("end date", End);
        hash.put("assignedTo", to);
        hash.put("section", section);
        generateReport(report, hash);
    }

    public static void generateIDStringReport(String ID, String exact, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("ID", ID);
        hash.put("string", exact);
        generateReport(report, hash);
    }

    public static void generateTwoStringsReport(String string1, String string2, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("string1", string1);
        hash.put("string2", string2);
        generateReport(report, hash);
    }

    public static void generateThreeStringsReport(String string1, String string2, String string3, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("string1", string1);
        hash.put("string2", string2);
        hash.put("string3", string3);
        generateReport(report, hash);
    }

    public static void generateMonthYearReport(String month, String year, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("month", month);
        hash.put("year", year);
        generateReport(report, hash);
    }

    public static void generateCasenumberReport(String CaseNumber, SMDSDocuments report) {
        NumberFormatService num = NumberFormatService.parseFullCaseNumberNoNGlobal(CaseNumber);

        HashMap hash = new HashMap();
        hash.put("caseYear", num.caseYear);
        hash.put("caseType", num.caseType);
        hash.put("caseMonth", num.caseMonth);
        hash.put("caseNumber", num.caseNumber);
        generateReport(report, hash);
    }

    private static String generateCaseTypeList(){
        String param = "";
        List casetypes = null;

        if (Global.activeSection.equals("Hearings")){
            casetypes = CaseType.getCaseTypeHearings();
        } else {
            casetypes = CaseType.getCaseType();
        }

        if (!casetypes.isEmpty()) {
                param += "AND (";

                for (Object casetype : casetypes) {
                    param += " " + Global.activeSection.replaceAll(" ", "") + ".caseType = '" + casetype.toString() + "' OR";
                }
                param = param.substring(0, (param.length() - 2)) + ")";
            }

        return param;
    }

    private static String generateCaseTypeListBySection(String section){
        String param = "";
        List casetypes = null;

        casetypes = CaseType.getCaseTypeBySection(section);

        if (!casetypes.isEmpty()) {
                param += "AND (";

                for (Object casetype : casetypes) {
                    param += " " + section.replaceAll(" ", "") + ".caseType = '" + casetype.toString() + "' OR";
                }
                param = param.substring(0, (param.length() - 2)) + ")";
            }

        return param;
    }

    private static void generateReport(SMDSDocuments report, HashMap hash) {
        long lStartTime = System.currentTimeMillis();
        Connection conn = null;

        hash.put("current user", StringUtilities.buildFullName(
                Global.activeUser.firstName,
                Global.activeUser.middleInitial,
                Global.activeUser.lastName)
        );
        hash.put("caseTypeBySection", generateCaseTypeList());
        hash.put("caseTypeMED", generateCaseTypeListBySection("MED"));
        hash.put("caseTypeREP", generateCaseTypeListBySection("REP"));
        hash.put("caseTypeULP", generateCaseTypeListBySection("ULP"));
        hash.put("caseTypeCMDS", generateCaseTypeListBySection("CMDS"));
        hash.put("activeSection", Global.activeSection);
        hash.put("CurrentCaseYear", Global.caseYear);
        hash.put("CurrentCaseType", Global.caseType);
        hash.put("CurrentCaseMonth", Global.caseMonth);
        hash.put("CurrentCaseNumber", Global.caseNumber);
        hash.put("CurrentYear", Global.yyyy.format(new Date()));


        String jasperFileName = Global.reportingPath  + report.section + File.separator + report.fileName;

        if (report.fileName == null || !new File(jasperFileName).exists()) {
            WebOptionPane.showMessageDialog(Global.root, "<html><center> Sorry, unable to locate report. <br><br>" + report.fileName + "</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String fileName = report.fileName.substring(0, report.fileName.lastIndexOf("."));
                if (fileName.length() > 50) {
                    fileName = StringUtils.left(fileName.trim(), 50).trim()  + "_" + System.currentTimeMillis();
                } else {
                    fileName = fileName + "_" + System.currentTimeMillis();
                }

                String pdfFileName = System.getProperty("java.io.tmpdir") + fileName + ".pdf";

                conn = Database.connectToDB();
                JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, hash, conn);
                try {
                    JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);
                    Audit.addAuditEntry("Generetated Report: " + report.fileName);
                } catch(JRException e){
                    WebOptionPane.showMessageDialog(Global.root, "<html><center>An old version of this report is currently open elsewhere. "
                            + "<br>Please ensure it is closed before continuing.</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
                }
                File recentReport = new File(pdfFileName);
                if (recentReport.exists()) {
                    FileService.openFileFullPath(recentReport);
                    long lEndTime = System.currentTimeMillis();
                    System.out.println("Report Generation Time: " + NumberFormatService.convertLongToTime(lEndTime - lStartTime));
                }
            } catch (JRException ex) {
                SlackNotification.sendNotification(ex);
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
    }

}
