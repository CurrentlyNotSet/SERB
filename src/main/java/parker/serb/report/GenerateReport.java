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

/**
 *
 * @author User
 */
public class GenerateReport {
    
    private static void generateReport(int reportID, HashMap hash) {
        Connection conn = null;
        SMDSDocuments report = SMDSDocuments.findDocumentByID(reportID);
        hash.put("current user", Global.activeUser.id);
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
                }
            } catch (JRException ex) {
                ex.toString();
            } finally {
                DbUtils.closeQuietly(conn);
            }
        }
    }   
    
    public static void generateSingleDatesReport(Date date, int reportID) {
        HashMap hash = new HashMap();
        hash.put("date", Global.SQLDateFormat.format(date) + " 00:00:00");
        generateReport(reportID, hash);
    }
    
    public static void generateTwoDatesReport(Date Start, Date End, int reportID) {
        HashMap hash = new HashMap();
        hash.put("BeginDate", Global.SQLDateFormat.format(Start) + " 00:00:00");
        hash.put("EndDate", Global.SQLDateFormat.format(End) + " 23:59:59");
        generateReport(reportID, hash);
    }
    
}
