/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.report;

import com.alee.laf.optionpane.WebOptionPane;
import java.io.File;
import java.sql.Connection;
import java.util.HashMap;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import org.apache.commons.dbutils.DbUtils;
import org.apache.commons.lang3.StringUtils;
import parker.serb.Global;
import parker.serb.sql.Database;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;
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
                case "date":
                    new RequestedInfoOneDatePanel(Global.root, true, report);
                    break;
                case "begin date, end date":
                    new RequestedInfoTwoDatePanel(Global.root, true, report);
                    break;
                case "begin date, end date, investigatorID":
                    new RequestedInfoTwoDateIDPanel(Global.root, true, report, "InvestigatorID");
                    break;
                case "begin date, end date, UserID":
                    new RequestedInfoTwoDateIDPanel(Global.root, true, report, "UserID");
                    break;
                case "begin date, end date, LikeString":
                    new RequestedInfoTwoDateLikeStringPanel(Global.root, true, report);
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
    
    public static void generateTwoDatesLikeStringReport(String Start, String End, String Like, SMDSDocuments report) {
        HashMap hash = new HashMap();
        hash.put("begin date", Start);
        hash.put("end date", End);
        hash.put("LikeString", Like);
        generateReport(report, hash);
    }
    
    public static void generateCasenumberReport(String CaseNumber, SMDSDocuments report) {
        NumberFormatService num = NumberFormatService.parseFullCaseNumberNoNGlobal(CaseNumber);
        
        HashMap hash = new HashMap();
        hash.put("caseYear", num.caseYear);
        hash.put("caseType", num.caseType);
        hash.put("caseMonth", num.caseMonth);
        hash.put("CaseNumber", num.caseNumber);
        generateReport(report, hash);
    }
    
    private static void generateReport(SMDSDocuments report, HashMap hash) {
        long lStartTime = System.currentTimeMillis();
        Connection conn = null;
        hash.put("current user", StringUtilities.buildFullName(
                Global.activeUser.firstName, 
                Global.activeUser.middleInitial, 
                Global.activeUser.lastName)
        );
        if (report.fileName == null) {
            WebOptionPane.showMessageDialog(Global.root, "<html><center> Sorry, unable to locate report. <br><br>" + report.fileName + "</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
        } else {
            try {
                String fileName = report.fileName.substring(0, report.fileName.lastIndexOf("."));
                if (fileName.length() > 50) {
                    fileName = System.currentTimeMillis() + "_" + StringUtils.left(fileName.trim(), 50);
                } else {
                    fileName = fileName + "_" + System.currentTimeMillis();
                }

                String jasperFileName = Global.reportingPath  + report.section + File.separator + report.fileName;
                String pdfFileName = System.getProperty("java.io.tmpdir") + fileName + ".pdf";

                conn = Database.connectToDB();
                JasperPrint jprint = (JasperPrint) JasperFillManager.fillReport(jasperFileName, hash, conn);
                try {
                    JasperExportManager.exportReportToPdfFile(jprint, pdfFileName);
                } catch(Exception e){
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
                ex.printStackTrace();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
    }   
    
}
