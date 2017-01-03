/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.sql;

import com.alee.laf.optionpane.WebOptionPane;
import parker.serb.Global;

/**
 *
 * @author User
 */
public class DatabaseExceptionCatch {

    public static int DatabaseConnectionError(int nbAttempts) {
        nbAttempts++;
        System.out.println("Silent Failure: Connection to Database Failed. Waiting and trying again.");
        if (nbAttempts == 2) {
            WebOptionPane.showMessageDialog(Global.root, "<html><center>Unable to connect to the database.<br>"
                    + "Please ensure you are connected to the network and press OK to try again.</center></html>",
                    "Connection Error", WebOptionPane.WARNING_MESSAGE);
        }
        try {
            Thread.sleep(3000);
        } catch (InterruptedException exi) {
            System.err.println(exi.getMessage());
        }
        if (nbAttempts == 3) {
            WebOptionPane.showMessageDialog(Global.root, "<html><center>"
                    + "Still unable to connect to database.<br><br>"
                    + "The application must now close. The information will not be saved.<br> "
                    + "Make note of that information and press OK to exit the application.</center></html>",
                    "Connection Error", WebOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return nbAttempts;
    }
}
