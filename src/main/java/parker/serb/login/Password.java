/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.login;

import parker.serb.sql.User;
import java.security.SecureRandom;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import org.apache.commons.lang3.RandomStringUtils;
import parker.serb.Global;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;
import static org.apache.commons.codec.digest.DigestUtils.sha256Hex;

//TODO: Test the validation of passwords, with a logged in user

/**
 *
 * @author parker
 */
public class Password {
    /**
     * Generate a random hash value (salt) to be used upon user creation
     * or password update.  
     * 
     * @return - A integer value of a secureRandom number
     */
    static public long generatePasswordSalt()
    {
        // Get the instance of SecureRandom class with specified PRNG algorithm
        SecureRandom secureRandom = new SecureRandom();
        
        //return a random secure number
        return (long) (secureRandom.nextInt());
    }
    
    /**
     * 
     * @param salt randomly generated SecureRandom number
     * @param password user provided value in a string format
     * @return a sha256 hashed password with salt in string
     */
    static public String hashPassword(long salt, String password)
    {
        return sha256Hex(salt + password + salt);
    }
    
    static public void validatePassword(String username, String password)
    {
        User user = User.findUserByUsername(username);
        if(user != null)
        {
            if(hashPassword(user.passwordSalt, password).equals(user.password) && user.active)
            {
                Global.activeUser = user;
            } 
        }
    }
    
    public static String generateTempPassword() {
        String temp = RandomStringUtils.randomAlphanumeric(8).toUpperCase();
        //Remove this line before shipping
        System.out.println("Password: " + temp);
        return temp;
    }
}
