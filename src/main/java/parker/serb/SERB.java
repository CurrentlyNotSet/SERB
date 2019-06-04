/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb;

import parker.serb.login.LogInDialog;
import com.alee.laf.WebLookAndFeel;

/**
 *
 * @author parker
 */
public class SERB {
    
    public static void main(String[] args) {
        //install weblaf look and feel
        WebLookAndFeel.install();
        
        //display the log in dialog
        new LogInDialog(null);
    }
}
