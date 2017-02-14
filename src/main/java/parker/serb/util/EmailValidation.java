/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import org.apache.commons.validator.routines.EmailValidator;

/**
 *
 * @author parkerjohnston
 */
public class EmailValidation {
    
    public static boolean validEmail(String email) {
        boolean valid = true;
        
        if(email.equals("")) {
            valid = true;
        } else {
            String[] emailSpilt = email.split(";");
            for(int i = 0; i < emailSpilt.length; i++) {
                valid = EmailValidator.getInstance().isValid(emailSpilt[i].trim());
                if(valid == false) {
                    return valid;
                }
            }
        }
        return valid;    
    }
    
}
