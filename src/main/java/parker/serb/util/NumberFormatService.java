/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import java.text.ParseException;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;

/**
 *
 * @author parker
 */
public class NumberFormatService {
    
    public static String convertStringToPhoneNumber(String number) {
        String formattedNumber = "";
        formattedNumber += "(" + number.substring(0, 3) + ") ";
        formattedNumber += number.substring(3, 6) + "-";
        formattedNumber += number.substring(6);
        return formattedNumber;
    }
    
    public static String convertPhoneNumberToString(String number) {
        return number.replaceAll("[^0-9]", "");
    }
    
    public static long convertMMDDYYYY(String mmddyyyyDate) {
         
        if(mmddyyyyDate.equals("")) {
            return 0;
        } else {
            Date date = null;
            try {
                date = Global.mmddyyyy.parse(mmddyyyyDate);
            } catch (ParseException ex) {
                Logger.getLogger(NumberFormatService.class.getName()).log(Level.SEVERE, null, ex);
            }
            return date.getTime();
        }
    }
    
    public static String generateFullCaseNumber() {
        return Global.caseYear + "-" + Global.caseType
                + "-" + Global.caseMonth + "-" + Global.caseNumber;
    }
    
    public static void parseFullCaseNumber(String fullCaseNumber) {
        String[] parsedCaseNumber = fullCaseNumber.split("-");
        Global.caseYear = parsedCaseNumber[0];
        Global.caseType = parsedCaseNumber[1];
        Global.caseMonth = parsedCaseNumber[2];
        Global.caseNumber = parsedCaseNumber[3];
    }
    
    public static void clearCaseNumberInformation() {
        Global.caseYear = "";
        Global.caseType = "";
        Global.caseMonth = "";
        Global.caseNumber = "";
    }
}
