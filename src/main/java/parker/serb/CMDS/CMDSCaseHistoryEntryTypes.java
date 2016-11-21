/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import java.sql.Timestamp;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.CMDSCase;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class CMDSCaseHistoryEntryTypes {
    
    public static void updateCaseHistory(String category, String entryDescription,
            String extraText, String partyType, String mailType, String entryDate, 
            java.awt.Dialog dialog, String filePath) {
        switch(category) {
            case "A": 
                addAEntryType(entryDescription,
                extraText, partyType, mailType, entryDate, 
                dialog, filePath);
            break;
            case "C": 
                addCEntryType(entryDescription,
                extraText, partyType, mailType, entryDate, 
                dialog, filePath);
            break;
            case "D": 
                addDEntryType(entryDescription,
                extraText, partyType, mailType, entryDate, 
                dialog, filePath);
            break;
            case "E": 
                addEEntryType(entryDescription,
                extraText, partyType, mailType, entryDate, 
                dialog, filePath);
            break;
        }
    }
    
    private static void addAEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate, 
            java.awt.Dialog dialog, String filePath) {
        
        try {
            
            String activity = "Notice of " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);
            
            Date date = Global.mmddyyyy.parse(entryDate);
            
            boolean updateAllCases = false;
            
            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);
            
            if(status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();
                
                if(groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity, date);
                }
                
                if(groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);
                    
                    if(update.isUpdateStatus()) {
                        updateAllCases = true;
                    } else {
                        updateAllCases = false;
                    }
                    
                    for(int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, groupList.get(i).toString());

                        if(updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity, date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();
                
                if(groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, null, NumberFormatService.generateFullCaseNumber());
                } else if (groupList.size() > 0) {
                    for(int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, null, groupList.get(i).toString());
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(CMDSCaseHistoryEntryTypes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void addCEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate, 
            java.awt.Dialog dialog, String filePath) {
            
        try {
            
            String activity = "R & R mailed " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);
            
            Date date = Global.mmddyyyy.parse(entryDate);
            
            boolean updateAllCases = false;
            
            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);
            
            if(status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();
                
                if(groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity, date);
                }
                
                if(groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);
                    
                    if(update.isUpdateStatus()) {
                        updateAllCases = true;
                    } else {
                        updateAllCases = false;
                    }
                    
                    for(int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, groupList.get(i).toString());

                        if(updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity, date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();
                
                if(groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, null, NumberFormatService.generateFullCaseNumber());
                } else if (groupList.size() > 0) {
                    for(int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, null, groupList.get(i).toString());
                    }
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(CMDSCaseHistoryEntryTypes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void addDEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate, 
            java.awt.Dialog dialog, String filePath) {
            
        try {
            String caseStatus = CMDSCase.getCaseStatus();
            Timestamp MailedBO = new Timestamp(Global.mmddyyyy.parse(entryDate).getTime());
            
            CMDSResultDialog result = new CMDSResultDialog(dialog, true);
            
            if(entryDescription.toLowerCase().contains("stayed") ||
                    entryDescription.toLowerCase().contains("fifting of stay")) {
                if(caseStatus.equals("S")) {
                    CMDSRemoveStayDialog removeStay = new CMDSRemoveStayDialog(dialog, true);
                    if(removeStay.isRemoveStay()) {
                        caseStatus = "O";
                    } else {
                        caseStatus = "S";
                    }
                    removeStay.dispose();
                } else if(caseStatus.equals("O")) {
                    CMDSPlaceStayDialog placeStay = new CMDSPlaceStayDialog(dialog, true);
                    if(placeStay.isPlaceStay()) {
                        caseStatus = "S";
                    } else {
                        caseStatus = "O";
                    }
                    placeStay.dispose();
                }
            }
            
            if(!result.getResult().equals("")) {
                String activity = "Board Order mailed " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += " Code " + result.getResult();
                activity += (partyType.equals("") ? "" : " " + partyType);
                activity += (mailType.equals("") ? "" : " " + mailType);

                Date date = Global.mmddyyyy.parse(entryDate);

                boolean updateAllCases = false;

                CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

                if(status.isUpdateStatus()) {
                    List groupList = CMDSCase.getGroupNumberList();

                    if(groupList.isEmpty()) {
                        Activity.addCMDSActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, NumberFormatService.generateFullCaseNumber());
                        CMDSCase.updateCaseInventoryStatusLines(activity, date);
                    }

                    if(groupList.size() > 0) {
                        CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                        if(update.isUpdateStatus()) {
                            updateAllCases = true;
                        } else {
                            updateAllCases = false;
                        }

                        for(int i = 0; i < groupList.size(); i++) {
                            Activity.addCMDSActivty(activity, filePath, groupList.get(i).toString());
                            CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                            if(updateAllCases) {
                                CMDSCase.updateAllGroupInventoryStatusLines(activity, date);
                            }
                        }
                    }
                } else {
                    List groupList = CMDSCase.getGroupNumberList();

                    if(groupList.isEmpty()) {
                        Activity.addCMDSActivty(activity, null, NumberFormatService.generateFullCaseNumber());
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, NumberFormatService.generateFullCaseNumber());
                    } else if (groupList.size() > 0) {
                        for(int i = 0; i < groupList.size(); i++) {
                            Activity.addCMDSActivty(activity, null, groupList.get(i).toString());
                            CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                        }
                    }
                }
            }
            result.dispose();
        } catch (ParseException ex) {
            Logger.getLogger(CMDSCaseHistoryEntryTypes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private static void addEEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate, 
            java.awt.Dialog dialog, String filePath) {
            
        try {
            String caseStatus = CMDSCase.getCaseStatus();
            Timestamp MailedBO = new Timestamp(Global.mmddyyyy.parse(entryDate).getTime());
            
            CMDSResultDialog result = new CMDSResultDialog(dialog, true);
            
            if(entryDescription.toLowerCase().contains("stayed") ||
                    entryDescription.toLowerCase().contains("fifting of stay")) {
                if(caseStatus.equals("S")) {
                    CMDSRemoveStayDialog removeStay = new CMDSRemoveStayDialog(dialog, true);
                    if(removeStay.isRemoveStay()) {
                        caseStatus = "O";
                    } else {
                        caseStatus = "S";
                    }
                    removeStay.dispose();
                } else if(caseStatus.equals("O")) {
                    CMDSPlaceStayDialog placeStay = new CMDSPlaceStayDialog(dialog, true);
                    if(placeStay.isPlaceStay()) {
                        caseStatus = "S";
                    } else {
                        caseStatus = "O";
                    }
                    placeStay.dispose();
                }
            }
            
            if(!result.getResult().equals("")) {
                String activity = "Board Order mailed " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
                activity += " Code " + result.getResult();
                activity += (partyType.equals("") ? "" : " " + partyType);
                activity += (mailType.equals("") ? "" : " " + mailType);

                Date date = Global.mmddyyyy.parse(entryDate);

                boolean updateAllCases = false;

                CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

                if(status.isUpdateStatus()) {
                    List groupList = CMDSCase.getGroupNumberList();

                    if(groupList.isEmpty()) {
                        Activity.addCMDSActivty(activity, filePath, NumberFormatService.generateFullCaseNumber());
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, NumberFormatService.generateFullCaseNumber());
                        CMDSCase.updateCaseInventoryStatusLines(activity, date);
                    }

                    if(groupList.size() > 0) {
                        CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                        if(update.isUpdateStatus()) {
                            updateAllCases = true;
                        } else {
                            updateAllCases = false;
                        }

                        for(int i = 0; i < groupList.size(); i++) {
                            Activity.addCMDSActivty(activity, filePath, groupList.get(i).toString());
                            CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                            if(updateAllCases) {
                                CMDSCase.updateAllGroupInventoryStatusLines(activity, date);
                            }
                        }
                    }
                } else {
                    List groupList = CMDSCase.getGroupNumberList();

                    if(groupList.isEmpty()) {
                        Activity.addCMDSActivty(activity, null, NumberFormatService.generateFullCaseNumber());
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, NumberFormatService.generateFullCaseNumber());
                    } else if (groupList.size() > 0) {
                        for(int i = 0; i < groupList.size(); i++) {
                            Activity.addCMDSActivty(activity, null, groupList.get(i).toString());
                            CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                        }
                    }
                }
            }
            result.dispose();
        } catch (ParseException ex) {
            Logger.getLogger(CMDSCaseHistoryEntryTypes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
