/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import com.alee.extended.layout.VerticalFlowLayout;
import com.alee.extended.window.WebPopOver;
import com.alee.laf.label.WebLabel;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 *
 * @author parker
 */
public class PopoverService {
    
    public static void singleLinePopoverOnButton(JButton button, String text)
    {
        final WebPopOver popOver = new WebPopOver ( button );
        popOver.setCloseOnFocusLoss ( true );
        popOver.setMargin ( 10 );
        popOver.setLayout ( new VerticalFlowLayout () );
        popOver.add ( new WebLabel ( text ) );
        popOver.show ( button );
    }
    
    public static void singleLinePopoverOnTextField(JTextField textfield, String text)
    {
        final WebPopOver popOver = new WebPopOver ( textfield );
        popOver.setCloseOnFocusLoss ( true );
        popOver.setMargin ( 10 );
        popOver.setLayout ( new VerticalFlowLayout () );
        popOver.add ( new WebLabel ( text ) );
        popOver.show ( textfield );
    }
}
