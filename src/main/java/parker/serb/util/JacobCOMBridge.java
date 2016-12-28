/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.LibraryLoader;
import com.jacob.com.Variant;
import java.io.File;
import parker.serb.Global;

/**
 *
 * @author Andrew
 */
public class JacobCOMBridge {

    public static ActiveXComponent setWordActive(boolean active, boolean visible, ActiveXComponent eolWord) {
        final String libFile = "amd64".equals(System.getProperty("os.arch")) ? "jacob-1.18-x64.dll" : "jacob-1.18-x86.dll";    
        String dllPath = ""; 
        
        switch(Global.templatePath) {
                case "/Users/parkerjohnston/Desktop/SERB/Template/":
                case "C:\\SERB\\Template\\":
                case "C:\\Users\\johnp10\\Desktop\\SERB\\Template\\":
                    File dll = new File(libFile);
                    if (dll.exists()){
                        dllPath = dll.getAbsolutePath();
                        break;    
                    }
                default:
                    //SERB LOCATION
                    dllPath = System.getenv("WINDIR") + "\\system32\\" + libFile;
            }
               
        if (loadLibrary(dllPath)) {
            if (active) {
                if (eolWord == null) {
                    eolWord = new ActiveXComponent("Word.Application");
                }
                eolWord.setProperty("Visible", new Variant(visible));
            } else {
                if (eolWord != null) {
                    eolWord.invoke("Quit", new Variant[0]);
                    eolWord.safeRelease();
                }
                eolWord = null;
            } 
        }
        return eolWord;
    }

    private static boolean loadLibrary(final String dllPath) {
        try {
            System.setProperty(LibraryLoader.JACOB_DLL_PATH, dllPath);
            LibraryLoader.loadJacobLibrary();
            return true;
        } catch (UnsatisfiedLinkError e) {
            System.err.println("Missing DLL: " + dllPath);
//            SlackNotification.sendNotification(ex);
            return false;
        }
    }

}
