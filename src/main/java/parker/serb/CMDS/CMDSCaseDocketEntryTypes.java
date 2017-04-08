/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.EmailOutInvites;
import parker.serb.util.DateConversion;

/**
 *
 * @author parkerjohnston
 */
public class CMDSCaseDocketEntryTypes {

    public static void updateCaseHistory(
            String category,
            String entryDescription,
            String comment,
            Date entryDate,
            java.awt.Dialog dialog,
            String filePath,
            String direction,
            String caseNumber,
            String from,
            String to) {

        switch(category) {
            case "A":
                addAEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "C":
                addCEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "D":
                addDEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "E":
                addEEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "F":
                addFEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "G":
                addGEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "H":
                addHEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "I":
                addIEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "J":
                addJEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "K":
                addKEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "L":
                addLEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "M":
                addMEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "N":
                addNEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "O":
                addOEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "P":
                addPEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "Q":
                addQEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "R":
                addREntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "S":
                addSEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "U":
                addUEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "V":
                addVEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
            case "W":
                addWEntryType(entryDescription, comment, entryDate,
                dialog, filePath, direction, caseNumber, from, to, category);
                break;
        }
    }

    private static void addAEntryType(String entryDescription,
            String extraText, Date entryDate,
            java.awt.Dialog dialog, String filePath, String direction,
            String caseNumber, String from, String to, String category) {

        String activity = direction + " - Notice of " + entryDescription;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));

                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addCEntryType(String entryDescription,
            String extraText, Date entryDate,
            java.awt.Dialog dialog, String filePath, String direction,
            String caseNumber, String from, String to, String category) {


        String activity = direction + " - R & R mailed " + entryDescription;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeCEntry(entryDate, caseNumber);
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeCEntry(entryDate, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                CMDSCase.updateCaseByTypeCEntry(entryDate, caseNumber);
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeCEntry(entryDate, groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addDEntryType(String entryDescription,
            String extraText, Date entryDate,
            java.awt.Dialog dialog, String filePath, String direction,
            String caseNumber, String from, String to, String category) {

        String caseStatus = CMDSCase.getCaseStatus();
        Timestamp MailedBO = new Timestamp(entryDate.getTime());

        CMDSResultDialog result = new CMDSResultDialog(dialog, true);

        if(entryDescription.toLowerCase().contains("stayed") ||
                entryDescription.toLowerCase().contains("fifting of stay")) {

            if (caseStatus.equals("S")) {
                CMDSRemoveStayDialog removeStay = new CMDSRemoveStayDialog(dialog, true);
                if (removeStay.isRemoveStay()) {
                    caseStatus = "O";
                } else {
                    caseStatus = "S";
                }
                removeStay.dispose();
            } else if (caseStatus.equals("O")) {
                CMDSPlaceStayDialog placeStay = new CMDSPlaceStayDialog(dialog, true);
                if (placeStay.isPlaceStay()) {
                    caseStatus = "S";
                } else {
                    caseStatus = "O";
                }
                placeStay.dispose();
            }
        }

        if(!result.getResult().equals("")) {
            String activity = direction + " - Board Order mailed " + entryDescription;
            activity += " Code " + result.getResult();

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

            if(status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList(caseNumber);

                if(groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, caseNumber);
                    CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                }

                if(groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for(int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                        if(updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList(caseNumber);

                if(groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, caseNumber);
                } else if (groupList.size() > 0) {
                    for(int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                    }
                }
            }
        }
        result.dispose();
    }

    private static void addEEntryType(String entryDescription,
            String extraText, Date entryDate,
            java.awt.Dialog dialog, String filePath, String direction,
            String caseNumber, String from, String to, String category) {

        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate()!= null) {
            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + caseNumber,
                caseNumber,
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String caseStatus = CMDSCase.getCaseStatus(caseNumber);

        CMDSCase PODate = CMDSCase.getmailedPODates(caseNumber);

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


        String activity = direction + " - " + entryDescription;
        activity += (certified.isCertified() ? " - (Certified)" : "");

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber.split("-")[0]);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, caseNumber);
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, caseNumber);
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, groupList.get(i).toString());
                }
            }
        }
    }

    private static void addFEntryType(String entryDescription,
            String extraText, Date entryDate,
            java.awt.Dialog dialog, String filePath, String direction,
            String caseNumber, String from, String to, String category) {

        CMDSCase rrpoPullDates = CMDSCase.getRRPOPullDates(caseNumber);

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

        String activity = direction + " - " + entryDescription;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, caseNumber);
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, caseNumber);
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, groupList.get(i).toString());
                }
            }
        }
    }

    private static void addGEntryType(String entryDescription,
            String extraText, Date entryDate,
            java.awt.Dialog dialog, String filePath, String direction,
            String caseNumber, String from, String to, String category) {

        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate()!= null) {

            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + caseNumber,
                caseNumber,
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String activity = direction + " - " + entryDescription;
        activity += (dueDate.getResponseDueDate() == null ? "" : " " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addHEntryType(String entryDescription,
            String extraText, Date entryDate,
            java.awt.Dialog dialog, String filePath, String direction,
            String caseNumber, String from, String to, String category) {


        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate()!= null) {

            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + caseNumber,
                caseNumber,
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String activity = direction + " - " + entryDescription;
        activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addIEntryType(String entryDescription,
            String extraText, Date entryDate,
            java.awt.Dialog dialog, String filePath, String direction,
            String caseNumber, String from, String to, String category) {

        String activity = direction + " - " + entryDescription;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addJEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate()!= null) {

            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + caseNumber,
                caseNumber,
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String activity = direction + " - " + entryDescription;
        activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addKEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        String activity = direction + " - " + entryDescription;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                boolean updateAllCases = update.isUpdateStatus();

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addLEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        String activity = direction + " - " + entryDescription;

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addMEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        CMDSAppealsCourtDialog court = new CMDSAppealsCourtDialog(dialog, true);

        String activity = direction + " - " + entryDescription;
        activity += (court.getSelection() == null ? "" : " - Appealed to " + court.getSelection());
        activity += (court.getCaseNumber().equals("") ? "" : " - Case Number " + court.getCaseNumber());

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeMEntry(caseNumber);
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeMEntry(groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeMEntry(caseNumber);
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeMEntry(groupList.get(i).toString());
                }
            }
        }
        status.dispose();
    }

    private static void addNEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        CMDSClearDateDialog clear = new CMDSClearDateDialog(dialog, true);

        String activity = direction + " - " + entryDescription;

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), caseNumber);
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), caseNumber);
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), groupList.get(i).toString());
                }
            }
        }
    }

    private static void addOEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

        if(dueDate.getResponseDueDate() != null) {

            EmailOutInvites.addNewHearing("CMDS",
                CMDSCase.getALJemail(),
                null,
                "Response due for " + caseNumber,
                caseNumber,
                null,
                null,
                null,
                DateConversion.generateReminderStartDate(dueDate.getResponseDueDate())
            );
        }

        String activity = direction + " - " + entryDescription;
        activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addPEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        String activity = direction + " - " + entryDescription;

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addQEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        CMDSWhichGreenCardDialog pullDate = new CMDSWhichGreenCardDialog(dialog, true);

        String activity = direction + " - " + entryDescription;

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                        whichPullDate(pullDate.getWhichType()),
                        pullDate.getSignedDate(),
                        pullDate.getPullDate(),
                        caseNumber);
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                            whichPullDate(pullDate.getWhichType()),
                            pullDate.getSignedDate(),
                            pullDate.getPullDate(),
                            groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                        whichPullDate(pullDate.getWhichType()),
                        pullDate.getSignedDate(),
                        pullDate.getPullDate(),
                        caseNumber);
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                            whichPullDate(pullDate.getWhichType()),
                            pullDate.getSignedDate(),
                            pullDate.getPullDate(),
                            groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
        pullDate.dispose();
    }

    private static void addREntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        String activity = direction + " - " + entryDescription;

        String entryDate2 = Global.MMMMddyyyy.format(entryDate);

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeREntry(entryDate2, caseNumber);
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeREntry(entryDate2, groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                CMDSCase.updateCaseByTypeREntry(entryDate2, caseNumber);
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));

            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeREntry(entryDate2, groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addSEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        String activity = direction + " - " + entryDescription;

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
    }

    private static void addUEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        CMDSPBRBoxDialog pbr = new CMDSPBRBoxDialog(dialog, true);

        String activity = direction + " - " + entryDescription;
        activity += (pbr.getPbrBox().equals("") ? "" : " " + pbr.getPbrBox());

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                        caseNumber);
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                        groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                        caseNumber);
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                        groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
        pbr.dispose();
    }

    private static void addVEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        CMDSRemailedDialog remailed = new CMDSRemailedDialog(dialog, true);
        CMDSPullDateDialog pulldate = null;

        if(entryDescription.equals("R & R Remailed Certified Mail")
                || entryDescription.equals("R & R Remailed Regular Mail"))
        {
            pulldate = new CMDSPullDateDialog(dialog, true);
        }

        String activity = direction + " - " + entryDescription;

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                        remailed.getRemailedDate(),
                        pulldate == null ? null : pulldate.getResponseDueDate(),
                        caseNumber);
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                            remailed.getRemailedDate(),
                            pulldate == null ? null : pulldate.getResponseDueDate(),
                            groupList.get(i).toString());
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                        remailed.getRemailedDate(),
                        pulldate == null ? null : pulldate.getResponseDueDate(),
                        caseNumber);
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                            remailed.getRemailedDate(),
                            pulldate == null ? null : pulldate.getResponseDueDate(),
                            groupList.get(i).toString());
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                }
            }
        }
        remailed.dispose();
    }

    private static void addWEntryType(String entryDescription,
        String extraText, Date entryDate,
        java.awt.Dialog dialog, String filePath, String direction,
        String caseNumber, String from, String to, String category) {

        String activity = direction + " - " + entryDescription;

        boolean updateAllCases = false;

        CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true);

        if(status.isUpdateStatus()) {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                CMDSCase.updateCaseInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
            }

            if(groupList.size() > 0) {
                CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                if(update.isUpdateStatus()) {
                    updateAllCases = true;
                } else {
                    updateAllCases = false;
                }

                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    if(updateAllCases) {
                        CMDSCase.updateAllGroupInventoryStatusLines(activity  + (extraText.equals("") ? "" : " " + extraText), entryDate, caseNumber);
                    }
                }
            }
        } else {
            List groupList = CMDSCase.getGroupNumberList(caseNumber);

            if(groupList.isEmpty()) {
                Activity.addCMDSActivty(activity, filePath, entryDate, caseNumber, from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
            } else if (groupList.size() > 0) {
                for(int i = 0; i < groupList.size(); i++) {
                    Activity.addCMDSActivty(activity, filePath, entryDate, groupList.get(i).toString(), from, to, category, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
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
