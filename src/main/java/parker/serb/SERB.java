/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb;

import parker.serb.login.LogInDialog;
import com.alee.laf.WebLookAndFeel;
import com.apple.eawt.AppEvent;
import com.apple.eawt.Application;
import com.apple.eawt.QuitResponse;
import java.awt.Color;
import javax.swing.UIManager;
import parker.serb.sql.Audit;
import parker.serb.sql.DocketLock;
import parker.serb.sql.NewCaseLock;
import parker.serb.sql.User;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parker
 */
public class SERB {
    
    public static void main(String[] args) {
        
        //UIManager.put("InternalFrame.borderShadow", Color.gray);
        
        if(System.getProperty("os.name").equalsIgnoreCase("Mac OS X")) {
            //Used to watch for Apple-Q keys and handle the applicaiton being closed
            try {
                Application macOSXApplication=Application.getApplication();

                macOSXApplication.setQuitHandler((AppEvent.QuitEvent qe, QuitResponse qr) -> {
                    if(Global.activeUser != null) {
                        DocketLock.removeUserLocks();
                        NewCaseLock.removeUserLocks();
                        User.updateActiveLogIn();
                        Audit.addAuditEntry("Logged Off");
                    }
                    System.exit(0);
                });
            }
            catch (Throwable ex) {
                SlackNotification.sendNotification(ex.getMessage());
            }
        }
        
        //install weblaf look and feel
        WebLookAndFeel.install();
        
        new LogInDialog(null);
    }
}
