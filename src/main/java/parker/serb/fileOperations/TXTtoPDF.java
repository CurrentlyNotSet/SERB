/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.fileOperations;

import parker.serb.util.PDFBoxTools;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.io.FilenameUtils;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.PDPageContentStream.AppendMode;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType1Font;

/**
 *
 * @author parker.johnston
 */
public class TXTtoPDF {
 
    public static String createPDF(String filePath, String fileName) {
        String txtFile = filePath + fileName;
        String pdfFile = filePath + FilenameUtils.removeExtension(fileName) + ".pdf";
        
        makePDF(pdfFile, getTextfromTXT(txtFile));
        
        new File(txtFile).delete();
        
        return FilenameUtils.getName(pdfFile);
    }
        
    private static String getTextfromTXT(String file){
        String textBody = "";
        BufferedReader br = null;
        try {
            String sCurrentLine;
            br = new BufferedReader(new FileReader(file));
            while ((sCurrentLine = br.readLine()) != null) {
                textBody += sCurrentLine + System.getProperty("line.separator");
            }
        } catch (IOException ex) {
            Logger.getLogger(TXTtoPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (br != null) {
                    br.close();
                }
            } catch (IOException ex) {
                Logger.getLogger(TXTtoPDF.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return textBody;
    }
    
    private static void makePDF(String pdfFile, String text){
        PDDocument doc = null;
        PDPageContentStream contentStream = null;
        
        //Fonts used
        PDFont bodyFont = PDType1Font.HELVETICA;
        
        //Font Sizes
        float bodyFontSize = 12;
        float leadingBody = 1.5f * bodyFontSize;

        try {            
            //Create Document, Page, Margins.
            doc = new PDDocument();
            PDPage page = new PDPage();
            doc.addPage(page);
            contentStream = new PDPageContentStream(doc, page, AppendMode.APPEND, true, false);
            PDRectangle mediabox = page.getMediaBox();
            float margin = 72;
            float width = mediabox.getWidth() - 2 * margin;
            float startX = mediabox.getLowerLeftX() + margin;
            float startY = mediabox.getUpperRightY() - margin;
            float textYlocation = margin;
            
            //Set Line Breaks
            List<String> textContent = PDFBoxTools.setLineBreaks(text, width, bodyFontSize, bodyFont);

            contentStream.beginText();
            contentStream.setFont(bodyFont, bodyFontSize);
            contentStream.setNonStrokingColor(Color.BLACK);
            contentStream.newLineAtOffset(startX, startY);
            
            if (!"".equals(text)) {    
            for (String line : textContent) {
                if (textYlocation > (mediabox.getHeight() - (margin * 2) - leadingBody)) {
                    contentStream.endText();
                    contentStream.close();
                    textYlocation = 0;

                    page = new PDPage();
                    doc.addPage(page);
                    contentStream = new PDPageContentStream(doc, page, true, true, false);

                    contentStream.beginText();
                    contentStream.setFont(bodyFont, bodyFontSize);
                    contentStream.setNonStrokingColor(Color.BLACK);
                    contentStream.newLineAtOffset(startX, startY);
                }

                textYlocation += leadingBody;

                contentStream.showText(line);
                contentStream.newLineAtOffset(0, -leadingBody);
            }
            contentStream.endText();
            
            }
            contentStream.close();
            doc.save(pdfFile);
        } catch (IOException ex) {
            Logger.getLogger(TXTtoPDF.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            if (doc != null) {
                try {
                    doc.close();
                } catch (IOException ex) {
                    Logger.getLogger(TXTtoPDF.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }
    
    
    
}
