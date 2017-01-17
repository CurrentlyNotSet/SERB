/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import parker.serb.Global;

/**
 *
 * @author parker
 */
public class NumberFormatService {
    
    public String caseYear;
    public String caseType; 
    public String caseMonth;
    public String caseNumber;
    
    public static String convertStringToPhoneNumber(String number) {
        String formattedNumber = "";
        
        if(number == null) {
            formattedNumber = "";
        } else if(number.length() >= 10) {
            formattedNumber += "(" + number.substring(0, 3) + ") ";
            formattedNumber += number.substring(3, 6) + "-";
            formattedNumber += number.substring(6, 10);
            
            if(number.length() > 10) {
                formattedNumber += " x" + number.substring(10);
            }
        } else {
            formattedNumber = number;
        }        
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
                SlackNotification.sendNotification(ex);
            }
            return date.getTime();
        }
    }
    
    public static long converthmma(String hmma) {
         
        if(hmma.equals("")) {
            return 0;
        } else {
            Date date = null;
            try {
                date = Global.hmma.parse(hmma);
            } catch (ParseException ex) {
                SlackNotification.sendNotification(ex);
            }
            return date.getTime();
        }
    }
    
    public static String generateFullCaseNumber() {
        return Global.caseYear + "-" + Global.caseType
                + "-" + Global.caseMonth + "-" + Global.caseNumber;
    }
    
    public static String generateFullCaseNumberNonGlobal(String caseYear, String caseType, String caseMonth, String caseNumber) {
        return caseYear + "-" + caseType
                + "-" + caseMonth + "-" + caseNumber;
    }
    
    public static void parseFullCaseNumber(String fullCaseNumber) {
        String[] parsedCaseNumber = fullCaseNumber.split("-");
        Global.caseYear = parsedCaseNumber[0];
        Global.caseType = parsedCaseNumber[1];
        Global.caseMonth = parsedCaseNumber[2];
        Global.caseNumber = parsedCaseNumber[3];
    }
    
    public static NumberFormatService parseFullCaseNumberNoNGlobal(String fullCaseNumber) {
        NumberFormatService num = new NumberFormatService();
        
        String[] parsedCaseNumber = fullCaseNumber.split("-");
        num.caseYear = parsedCaseNumber[0];
        num.caseType = parsedCaseNumber[1];
        num.caseMonth = parsedCaseNumber[2];
        num.caseNumber = parsedCaseNumber[3];
        return num;
    }
    
    public static void clearCaseNumberInformation() {
        Global.caseYear = "";
        Global.caseType = "";
        Global.caseMonth = "";
        Global.caseNumber = "";
    }
    
    public static String convertLongToTime(long millis) {
        String duration = String.format("%02dhr %02dmin %02dsec",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        if (TimeUnit.MILLISECONDS.toHours(millis) == 0) {
            String[] split = duration.split("hr");
            duration = split[1].trim();
        }
        return duration.trim();
    }
    
    public static String monthNumber(String month){
        if (month != null){
            if (       month.startsWith("Jan")){
                return "01";
            } else if (month.startsWith("Feb")){
                return "02";
            } else if (month.startsWith("Mar")){
                return "03";
            } else if (month.startsWith("Apr")){
                return "04";
            } else if (month.startsWith("May")){
                return "05";
            } else if (month.startsWith("Jun")){
                return "06";
            } else if (month.startsWith("Jul")){
                return "07";
            } else if (month.startsWith("Aug")){
                return "08";
            } else if (month.startsWith("Sep")){
                return "09";
            } else if (month.startsWith("Oct")){
                return "10";
            } else if (month.startsWith("Nov")){
                return "11";
            } else if (month.startsWith("Dec")){
                return "12";
            }
        }
        return null;
    }
}
