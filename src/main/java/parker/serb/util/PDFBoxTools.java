/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import java.awt.Dimension;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.pdfbox.pdmodel.font.PDFont;

/**
 *
 * @author Andrew
 */
public class PDFBoxTools {
    
    public static List<String> setLineBreaks(String origText, float width, float fontSize, PDFont pdfFont) {
        List<String> lines = new ArrayList<>();

        origText = origText.replaceAll(System.getProperty("line.separator"), System.getProperty("line.separator") + " ");
        String[] splitText = origText.split(System.getProperty("line.separator"));

        for (String text : splitText) {
            int lastSpace = -1;

            while (text.length() > 0) {
                try {
                    int spaceIndex = text.indexOf(' ', lastSpace + 1);
                    if (spaceIndex < 0) {
                        spaceIndex = text.length();
                    }
                    String subString = text.substring(0, spaceIndex);
                    float size = fontSize * pdfFont.getStringWidth(subString) / 1000;
//                System.out.printf("'%s' - %f of %f\n", subString, size, width);
                    if (size > width) {
                        if (lastSpace < 0) {
                            lastSpace = spaceIndex;
                        }
                        subString = text.substring(0, lastSpace);
                        lines.add(subString);
                        text = text.substring(lastSpace).trim();
//                    System.out.printf("'%s' is line\n", subString);
                        lastSpace = -1;
                    } else if (spaceIndex == text.length()) {
                        lines.add(text);
//                    System.out.printf("'%s' is line\n", text);
                        text = "";
                    } else {
                        lastSpace = spaceIndex;
                    }
                } catch (IOException ex) {
                    Logger.getLogger(PDFBoxTools.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        return lines;
    }

    public static Dimension getScaledDimension(Dimension imgSize, Dimension boundary) {
        int original_width = imgSize.width;
        int original_height = imgSize.height;
        int bound_width = boundary.width;
        int bound_height = boundary.height;
        int new_width = original_width;
        int new_height = original_height;

        // first check if we need to scale width
        if (original_width > bound_width) {
            //scale width to fit
            new_width = bound_width;
            //scale height to maintain aspect ratio
            new_height = (new_width * original_height) / original_width;
        }

        // then check if we need to scale even with the new height
        if (new_height > bound_height) {
            //scale height to fit instead
            new_height = bound_height;
            //scale width to maintain aspect ratio
            new_width = (new_height * original_width) / original_height;
        }

        return new Dimension(new_width, new_height);
    }

}
