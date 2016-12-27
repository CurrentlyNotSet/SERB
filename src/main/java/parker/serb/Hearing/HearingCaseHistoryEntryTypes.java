/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.Hearing;

import parker.serb.sql.Activity;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class HearingCaseHistoryEntryTypes {
    
    public static void updateCaseHistory(
            String category,
            String entryDescription,
            String extraText,
            String mailType,
            String filePath
        ) 
    {
        
        String activity = "";
        
        switch(category) {
            case "A": 
                activity = "Notice of " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "C": 
                activity = "R & R mailed " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "D": 
                activity = "Board Order mailed " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "E": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "F": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "G": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "H": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "I": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "J": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "K": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "L": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "M": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "N": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "O": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "P": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;  
            case "Q": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
            case "R": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());              
                break;  
            case "S": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());               
                break; 
            case "U": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());              
                break; 
            case "V": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());             
                break; 
            case "W": 
                activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += (mailType.equals("") ? "" : " " + mailType);
                Activity.addHearingActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                break;
        }
    }
}
