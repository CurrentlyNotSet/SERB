/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Date;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.EmailOutInvites;
import parker.serb.util.DateConversion;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parkerjohnston
 */
public class CMDSCaseDocketEntryTypes {
    
    public static void updateCaseHistory(String category, String entryDescription,
            String extraText, Date entryDate, 
            java.awt.Dialog dialog, String filePath, String direction) {
        switch(category) {
            case "A": 
                addAEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "C": 
                addCEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "D": 
                addDEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "E": 
                addEEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "F": 
                addFEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "G": 
                addGEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "H": 
                addHEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "I": 
                addIEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "J": 
                addJEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "K": 
                addKEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "L": 
                addLEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "M": 
                addMEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "N": 
                addNEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "O": 
                addOEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "P": 
                addPEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;  
            case "Q": 
                addQEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
            case "R": 
                addREntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);                
                break;  
            case "S": 
                addSEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);                
                break; 
            case "U": 
                addUEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);                
                break; 
            case "V": 
                addVEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);                
                break; 
            case "W": 
                addWEntryType(entryDescription,
                extraText, entryDate, 
                dialog, filePath, direction);
                break;
        }
    }
    
    private static void addAEntryType(String entryDescription,
            String extraText, Date entryDate, 
            java.awt.Dialog dialog, String filePath, String direction) {
            
        String activity = direction + " - Notice of " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());

                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addCEntryType(String entryDescription,
            String extraText, Date entryDate, 
            java.awt.Dialog dialog, String filePath, String direction) {

            
        String activity = direction + " - R & R mailed " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeCEntry(entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeCEntry(entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                CMDSCase.updateCaseByTypeCEntry(entryDate, NumberFormatService.generateFullCaseNumber());
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeCEntry(entryDate, groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addDEntryType(String entryDescription,
            String extraText, Date entryDate, 
            java.awt.Dialog dialog, String filePath, String direction) {

        String caseStatus = CMDSCase.getCaseStatus();
        Timestamp MailedBO = new Timestamp(entryDate.getTime());

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
            String activity = direction + " - Board Order mailed " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
            activity += " Code " + result.getResult();

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

            if(status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if(groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
                }

                if(groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for(int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                        if(updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if(groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, NumberFormatService.generateFullCaseNumber());
                } else if (groupList.size() > 0) {
                    for(int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                    }
                }
            }
        }
        result.dispose();
    }
    
    private static void addEEntryType(String entryDescription,
            String extraText, Date entryDate, 
            java.awt.Dialog dialog, String filePath, String direction) {
            
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate()!= null) {
            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + NumberFormatService.generateFullCaseNumber(),
                NumberFormatService.generateFullCaseNumber(),
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String caseStatus = CMDSCase.getCaseStatus();

        CMDSCase PODate = CMDSCase.getmailedPODates();

        if(PODate.mailedPO1 == null) {
            PODate.mailedPO1 = new Timestamp(entryDate.getTime());
        } else if(PODate.mailedPO2 == null) {
            PODate.mailedPO2 = new Timestamp(entryDate.getTime());
        } else if(PODate.mailedPO3 == null) {
            PODate.mailedPO3 = new Timestamp(entryDate.getTime());
        } else if(PODate.mailedPO4 == null) {
            PODate.mailedPO4 = new Timestamp(entryDate.getTime());
        }

        CMDSCertifiedLetterDialog certified = new CMDSCertifiedLetterDialog(dialog, true);

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

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
        activity += (certified.isCertified() ? " - (Certified)" : "");

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addFEntryType(String entryDescription,
            String extraText, Date entryDate, 
            java.awt.Dialog dialog, String filePath, String direction) {
            
        CMDSCase rrpoPullDates = CMDSCase.getRRPOPullDates();

        CMDSRRorPODialog rrpo = new CMDSRRorPODialog(dialog, true);

        if(rrpo.getSelection().equals("R&R")) {
            CMDSPullDateDialog pullDate = new CMDSPullDateDialog(dialog, true);
            rrpoPullDates.pullDateRR = pullDate.getResponseDueDate();
            pullDate.dispose();
        } else {
            CMDSWhichPOPullDateDialog whichPO = new CMDSWhichPOPullDateDialog(dialog, true);

            CMDSPullDateDialog pullDate = new CMDSPullDateDialog(dialog, true);

            switch (whichPO.getWhichPO()) {
                case "PO1":
                    rrpoPullDates.pullDatePO1 = pullDate.getResponseDueDate();
                    break;
                case "PO2":
                    rrpoPullDates.pullDatePO2 = pullDate.getResponseDueDate();
                    break;
                case "PO3":
                    rrpoPullDates.pullDatePO3 = pullDate.getResponseDueDate(); 
                    break;
                case "PO4":
                    rrpoPullDates.pullDatePO4 = pullDate.getResponseDueDate();
                    break;
                default:
                    break;
            }

            whichPO.dispose();
            pullDate.dispose();
        }

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addGEntryType(String entryDescription,
            String extraText, Date entryDate, 
            java.awt.Dialog dialog, String filePath, String direction) {
            
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate()!= null) {

            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + NumberFormatService.generateFullCaseNumber(),
                NumberFormatService.generateFullCaseNumber(),
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
        activity += (dueDate.getResponseDueDate() == null ? "" : " " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addHEntryType(String entryDescription,
            String extraText, Date entryDate, 
            java.awt.Dialog dialog, String filePath, String direction) {

            
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate()!= null) {

            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + NumberFormatService.generateFullCaseNumber(),
                NumberFormatService.generateFullCaseNumber(),
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
        activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addIEntryType(String entryDescription,
            String extraText, Date entryDate, 
            java.awt.Dialog dialog, String filePath, String direction) {
            
        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addJEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {
            
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate()!= null) {

            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + NumberFormatService.generateFullCaseNumber(),
                NumberFormatService.generateFullCaseNumber(),
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
        activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addKEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addLEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addMEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {
            
        CMDSAppealsCourtDialog court = new CMDSAppealsCourtDialog(dialog, true);

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
        activity += (court.getSelection() == null ? "" : " - Appealed to " + court.getSelection());
        activity += (court.getCaseNumber().equals("") ? "" : " - Case Number " + court.getCaseNumber());

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeMEntry(NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeMEntry(groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeMEntry(NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeMEntry(groupList.get(i).toString());
                }
            }
        }
        status.dispose();
    }
    
    private static void addNEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {

        CMDSClearDateDialog clear = new CMDSClearDateDialog(dialog, true);

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addOEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {
        
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate() != null) {

            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + NumberFormatService.generateFullCaseNumber(),
                NumberFormatService.generateFullCaseNumber(),
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
        activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addPEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {
        
        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addQEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {
            
        CMDSWhichGreenCardDialog pullDate = new CMDSWhichGreenCardDialog(dialog, true);

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                        whichPullDate(pullDate.getWhichType()),
                        pullDate.getSignedDate(),
                        pullDate.getPullDate(),
                        NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                            whichPullDate(pullDate.getWhichType()),
                            pullDate.getSignedDate(),
                            pullDate.getPullDate(),
                            groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                        whichPullDate(pullDate.getWhichType()),
                        pullDate.getSignedDate(),
                        pullDate.getPullDate(),
                        NumberFormatService.generateFullCaseNumber());
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                            whichPullDate(pullDate.getWhichType()),
                            pullDate.getSignedDate(),
                            pullDate.getPullDate(),
                            groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
        pullDate.dispose();
    }
    
    private static void addREntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        String entryDate2 = Global.MMMMddyyyy.format(entryDate);

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeREntry(entryDate2, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeREntry(entryDate2, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                CMDSCase.updateCaseByTypeREntry(entryDate2, NumberFormatService.generateFullCaseNumber());
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());

            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeREntry(entryDate2, groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addSEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    private static void addUEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {

        CMDSPBRBoxDialog pbr = new CMDSPBRBoxDialog(dialog, true);

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);
        activity += (pbr.getPbrBox().equals("") ? "" : " " + pbr.getPbrBox());

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                        NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                        groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                        NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                        groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
        pbr.dispose();
    }
    
    private static void addVEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {

        CMDSRemailedDialog remailed = new CMDSRemailedDialog(dialog, true);
        CMDSPullDateDialog pulldate = null;

        if(entryDescription.equals("R & R Remailed Certified Mail")
                || entryDescription.equals("R & R Remailed Regular Mail"))
        {
            pulldate = new CMDSPullDateDialog(dialog, true);
        }

        String activity = direction + " - " + entryDescription + (extraText.equals("") ? "" : " " + extraText);

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                        remailed.getRemailedDate(),
                        pulldate == null ? null : pulldate.getResponseDueDate(),
                        NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                            remailed.getRemailedDate(),
                            pulldate == null ? null : pulldate.getResponseDueDate(),
                            groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                        remailed.getRemailedDate(),
                        pulldate == null ? null : pulldate.getResponseDueDate(),
                        NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                            remailed.getRemailedDate(),
                            pulldate == null ? null : pulldate.getResponseDueDate(),
                            groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
        remailed.dispose();
    }
    
    private static void addWEntryType(String entryDescription,
        String extraText, Date entryDate, 
        java.awt.Dialog dialog, String filePath, String direction) {
        
        String activity = entryDescription + (extraText.equals("") ? "" : " " + extraText);

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
                CMDSCase.updateCaseInventoryStatusLines(activity, entryDate);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity, entryDate);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList();

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, NumberFormatService.generateFullCaseNumber());
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString());
                }
            }
        }
    }
    
    
    private static String clearWhichDate(String dateType, String whichDate) {
        String clearDateString = "";
        
        switch (dateType) {
            case "BO":
                switch (whichDate) {
                    case "Mailed":
                        clearDateString = "mailedBO";
                        break;
                    case "ReMailed":
                        clearDateString = "remailedBO";
                        break;
                    case "Green Card Signed":
                        clearDateString = "returnReceiptBO";
                        break;
                    case "Pull Date":
                        clearDateString = "pullDateBO";
                        break;
                    default:
                        break;
                }
                break;
            case "RR":
                switch (whichDate) {
                    case "Mailed":
                        clearDateString = "mailedRR";
                        break;
                    case "ReMailed":
                        clearDateString = "remailedRR";
                        break;
                    case "Green Card Signed":
                        clearDateString = "returnReceiptRR";
                        break;
                    case "Pull Date":
                        clearDateString = "pullDateRR";
                        break;
                    default:
                        break;
                }
                break;
            case "PO1":
                switch (whichDate) {
                    case "Mailed":
                        clearDateString = "mailedPO1";
                        break;
                    case "ReMailed":
                        clearDateString = "remailedPO1";
                        break;
                    case "Green Card Signed":
                        clearDateString = "returnReceiptPO1";
                        break;
                    case "Pull Date":
                        clearDateString = "pullDatePO1";
                        break;
                    default:
                        break;
                }
                break;
            case "PO2":
                switch (whichDate) {
                    case "Mailed":
                        clearDateString = "mailedPO2";
                        break;
                    case "ReMailed":
                        clearDateString = "remailedPO2";
                        break;
                    case "Green Card Signed":
                        clearDateString = "returnReceiptPO2";
                        break;
                    case "Pull Date":
                        clearDateString = "pullDatePO2";
                        break;
                    default:
                        break;
                }
                break;
            case "PO3":
                switch (whichDate) {
                    case "Mailed":
                        clearDateString = "mailedPO3";
                        break;
                    case "ReMailed":
                        clearDateString = "remailedPO3";
                        break;
                    case "Green Card Signed":
                        clearDateString = "returnReceiptPO3";
                        break;
                    case "Pull Date":
                        clearDateString = "pullDatePO3";
                        break;
                    default:
                        break;
                }
                break;
            case "PO4":
                switch (whichDate) {
                    case "Mailed":
                        clearDateString = "mailedPO4";
                        break;
                    case "ReMailed":
                        clearDateString = "remailedPO4";
                        break;
                    case "Green Card Signed":
                        clearDateString = "returnReceiptPO4";
                        break;
                    case "Pull Date":
                        clearDateString = "pullDatePO4";
                        break;
                    default:
                        break;
                }
                break;  
            default:
                break;
        }
        return clearDateString;
    }
    
    private static String greenCardWhichDate(String dateType) {
        String greenCardDateString = "";
        
        switch (dateType) {
            case "BO":
                greenCardDateString = "returnReceiptBO";
                break;
            case "RR":
                greenCardDateString = "returnReceiptRR";
                break;
            case "PO1":
                greenCardDateString = "returnReceiptPO1";
                break;
            case "PO2":
                greenCardDateString = "returnReceiptPO2";
                break;
            case "PO3":
                greenCardDateString = "returnReceiptPO3";
                break;
            case "PO4":
                greenCardDateString = "returnReceiptPO4";
                break;
            default:
                break;
        }
        return greenCardDateString;
    }
    
    private static String whichPullDate(String dateType) {
        String whichPullDate = "";
        
        switch (dateType) {
            case "BO":
                whichPullDate = "pullDateBO";
                break;
            case "RR":
                whichPullDate = "pullDateRR";
                break;
            case "PO1":
                whichPullDate = "pullDatePO1";
                break;
            case "PO2":
                whichPullDate = "pullDatePO2";
                break;
            case "PO3":
                whichPullDate = "pullDatePO3";
                break;
            case "PO4":
                whichPullDate = "pullDatePO4";
                break;
            default:
                break;
        }
        return whichPullDate;
    }
    
    private static String whichRemailedDate(String dateType) {
        String greenCardDateString = "";
        
        switch (dateType) {
            case "BO":
                greenCardDateString = "remailedBO";
                break;
            case "RR":
                greenCardDateString = "remailedRR";
                break;
            case "PO1":
                greenCardDateString = "remailedPO1";
                break;
            case "PO2":
                greenCardDateString = "remailedPO2";
                break;
            case "PO3":
                greenCardDateString = "remailedPO3";
                break;
            case "PO4":
                greenCardDateString = "remailedPO4";
                break;
        }
        return greenCardDateString;
    }
}
