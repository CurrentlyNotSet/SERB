/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.fileOperations;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import parker.serb.util.JacobCOMBridge;
import org.apache.commons.io.FilenameUtils;
import parker.serb.util.SlackNotification;

/**
 *
 * @author Andrew
 */
public class WordToPDF {

    public static String createPDF(String filePath, String fileName) {
        ActiveXComponent eolWord = null;
        String docxFile = filePath + fileName;
        String pdfFile = filePath + FilenameUtils.removeExtension(fileName) + ".pdf";

        eolWord = JacobCOMBridge.setWordActive(true, false, eolWord);
        if (eolWord != null) {
            try {
                Dispatch document = eolWord.getProperty("Documents").toDispatch();
                Dispatch.call(document, "Open", docxFile).toDispatch();
                Dispatch WordBasic = Dispatch.call(eolWord, "WordBasic").getDispatch();
                Dispatch.call(WordBasic, "FileSaveAs", pdfFile, new Variant(17));
                Thread.sleep(250);
                JacobCOMBridge.setWordActive(false, false, eolWord);
                return FilenameUtils.getName(pdfFile);
            } catch (InterruptedException ex) {
                SlackNotification.sendNotification(ex.toString());
                return "";
            }
        }
        return "";
    }

}
