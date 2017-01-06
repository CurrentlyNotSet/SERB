/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.jacob.com.Dispatch;

/**
 *
 * @author User
 */
public class processBookmark {
    
//      Microsoft Word Save Formats    
//      Variant(0)  //  Microsoft Office Word Format
//      Variant(1)  //  Word Template
//      Variant(2)  //  Microsoft Windows Text Format
//      Variant(3)  //  Windows Text Format with line breaks preserved
//      Variant(4)  //  Microsoft Dos format
//      Variant(5)  //  Microsoft Dos format with line breaks preserved
//      Variant(6)  //  Rich Rext Format (rtf)
//      Variant(7)  //  Encoded text format
//      Variant(8)  //  Standard HTML format
//      Variant(9)  //  Web Archive Format
//      Variant(10) //  Filtered HTML Format
//      Variant(11) //  Extensible Markup Language Format (xml)
//      Variant(12) //  XML Document Format
//      Variant(13) //  XML Document Format with Macros enabled
//      Variant(14) //  XML Template Format
//      Variant(15) //  XML Template Format with Macros enabled
//      Variant(16) //  Word Default document file format. (docx)
//      Variant(17) //  Adobe PDF
//      Variant(18) //  XPS Format
    
    public static void process(String BookmarkName, String ReplaceText, Dispatch Document) {
        Dispatch obj;
        try {
            obj = Dispatch.call(Document, "Bookmarks", BookmarkName).getDispatch();
            Dispatch d = Dispatch.call(obj, "Range").getDispatch();
            Dispatch.put(d, "Text", ReplaceText);
        } catch (RuntimeException e) {
            obj = null;
        } finally {
            obj = null;
        }
    }    
    
    public static void processSignature(String bookmark, Dispatch Document) {
        Dispatch image;
        Dispatch find;
        
        try {
            image = Dispatch.get(Document, "InLineShapes").toDispatch();
            find = Dispatch.call(Document, "Find").toDispatch();
            
            // This Works!  Adds signature After "Thank you, "
            Dispatch.put(find, "Text", "Thank you, ");
            Dispatch.call(find, "Execute");
            Dispatch.call(image, "AddPicture", bookmark);
        } catch (RuntimeException e) {
            image = null;
            find = null;
        } finally {
            image = null;
            find = null;
        }



        
    }
}
