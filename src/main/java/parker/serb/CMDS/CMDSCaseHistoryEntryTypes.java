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
public class CMDSCaseHistoryEntryTypes {

    public static String entryCategory = "";
    public static String entryDirection = "";

    public static void updateCaseHistory(String category, String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath, String direction) {
        entryCategory = category;
        entryDirection = direction;

        switch (category) {
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
            case "F":
                addFEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "G":
                addGEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "H":
                addHEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "I":
                addIEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "J":
                addJEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "K":
                addKEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "L":
                addLEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "M":
                addMEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "N":
                addNEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "O":
                addOEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "P":
                addPEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "Q":
                addQEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "R":
                addREntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "S":
                addSEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "U":
                addUEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "V":
                addVEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
            case "W":
                addWEntryType(entryDescription,
                        extraText, partyType, mailType, entryDate,
                        dialog, filePath);
                break;
        }
    }

    private static void addAEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            String activity = entryDirection + "Notice of " + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));

                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addCEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            String activity = entryDirection + "R & R mailed " + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeCEntry(date, NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeCEntry(date, groupList.get(i).toString());
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    CMDSCase.updateCaseByTypeCEntry(date, NumberFormatService.generateFullCaseNumber());
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        CMDSCase.updateCaseByTypeCEntry(date, groupList.get(i).toString());
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addDEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {
            String caseStatus = CMDSCase.getCaseStatus();
            Timestamp MailedBO = new Timestamp(Global.mmddyyyy.parse(entryDate).getTime());

            CMDSResultDialog result = new CMDSResultDialog(dialog, true);

            if (entryDescription.toLowerCase().contains("stayed")
                    || entryDescription.toLowerCase().contains("fifting of stay")) {

                if (caseStatus.equals("S")) {
                    CMDSRemoveStayDialog removeStay = new CMDSRemoveStayDialog(dialog, true, NumberFormatService.generateFullCaseNumber());
                    if (removeStay.isRemoveStay()) {
                        caseStatus = "O";
                    } else {
                        caseStatus = "S";
                    }
                    removeStay.dispose();
                } else if (caseStatus.equals("O")) {
                    CMDSPlaceStayDialog placeStay = new CMDSPlaceStayDialog(dialog, true, NumberFormatService.generateFullCaseNumber());
                    if (placeStay.isPlaceStay()) {
                        caseStatus = "S";
                    } else {
                        caseStatus = "O";
                    }
                    placeStay.dispose();
                }
            }

            if (!result.getResult().equals("")) {
                String activity = entryDirection + "Board Order mailed " + entryDescription;
                activity += " Code " + result.getResult();
                activity += (partyType.equals("") ? "" : " " + partyType);
                activity += (mailType.equals("") ? "" : " " + mailType);

                Date date = Global.mmddyyyy.parse(entryDate);

                CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

                if (status.isUpdateStatus()) {
                    List groupList = CMDSCase.getGroupNumberList();

                    if (groupList.isEmpty()) {
                        Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, NumberFormatService.generateFullCaseNumber());
                        CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                    }

                    if (groupList.size() > 0) {
                        CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                        boolean updateAllCases = update.isUpdateStatus();

                        for (int i = 0; i < groupList.size(); i++) {
                            Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                            CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                            if (updateAllCases) {
                                CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                            }
                        }
                    }
                } else {
                    List groupList = CMDSCase.getGroupNumberList();

                    if (groupList.isEmpty()) {
                        Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, NumberFormatService.generateFullCaseNumber());
                    } else if (groupList.size() > 0) {
                        for (int i = 0; i < groupList.size(); i++) {
                            Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                            CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, groupList.get(i).toString());
                        }
                    }
                }
            }
            result.dispose();
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addEEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

            if (dueDate.getResponseDueDate() != null) {
                EmailOutInvites.addNewHearing("CMDS",
                        CMDSCase.getALJemail(),
                        null,
                        "Response due for " + NumberFormatService.generateFullCaseNumber(),
                        NumberFormatService.generateFullCaseNumber(),
                        null,
                        null,
                        null,
                        DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                        "Response due for " + NumberFormatService.generateFullCaseNumber()
                );
            }

            String caseStatus = CMDSCase.getCaseStatus();

            CMDSCase PODate = CMDSCase.getmailedPODates();

            if (PODate.mailedPO1 == null) {
                PODate.mailedPO1 = new Timestamp(Global.mmddyyyy.parse(entryDate).getTime());
            } else if (PODate.mailedPO2 == null) {
                PODate.mailedPO2 = new Timestamp(Global.mmddyyyy.parse(entryDate).getTime());
            } else if (PODate.mailedPO3 == null) {
                PODate.mailedPO3 = new Timestamp(Global.mmddyyyy.parse(entryDate).getTime());
            } else if (PODate.mailedPO4 == null) {
                PODate.mailedPO4 = new Timestamp(Global.mmddyyyy.parse(entryDate).getTime());
            }

            CMDSCertifiedLetterDialog certified = new CMDSCertifiedLetterDialog(dialog, true);

            if (caseStatus.equals("S")) {
                CMDSRemoveStayDialog removeStay = new CMDSRemoveStayDialog(dialog, true, NumberFormatService.generateFullCaseNumber());
                if (removeStay.isRemoveStay()) {
                    caseStatus = "O";
                } else {
                    caseStatus = "S";
                }
                removeStay.dispose();
            } else if (caseStatus.equals("O")) {
                CMDSPlaceStayDialog placeStay = new CMDSPlaceStayDialog(dialog, true, NumberFormatService.generateFullCaseNumber());
                if (placeStay.isPlaceStay()) {
                    caseStatus = "S";
                } else {
                    caseStatus = "O";
                }
            }

            String activity = entryDirection + entryDescription;
            activity += (certified.isCertified() ? " - (Certified)" : "");
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, groupList.get(i).toString());
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, NumberFormatService.generateFullCaseNumber());
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, groupList.get(i).toString());
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addFEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSCase rrpoPullDates = CMDSCase.getRRPOPullDates();

            CMDSRRorPODialog rrpo = new CMDSRRorPODialog(dialog, true);

            if (rrpo.getSelection().equals("R&R")) {
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

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, groupList.get(i).toString());
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, NumberFormatService.generateFullCaseNumber());
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, groupList.get(i).toString());
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addGEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

            if (dueDate.getResponseDueDate() != null) {

                EmailOutInvites.addNewHearing("CMDS",
                        CMDSCase.getALJemail(),
                        null,
                        "Response due for " + NumberFormatService.generateFullCaseNumber(),
                        NumberFormatService.generateFullCaseNumber(),
                        null,
                        null,
                        null,
                        DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                        "Response due for " + NumberFormatService.generateFullCaseNumber()
                );
            }

            String activity = entryDirection + entryDescription;
            activity += (dueDate.getResponseDueDate() == null ? "" : " " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addHEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

            if (dueDate.getResponseDueDate() != null) {

                EmailOutInvites.addNewHearing("CMDS",
                        CMDSCase.getALJemail(),
                        null,
                        "Response due for " + NumberFormatService.generateFullCaseNumber(),
                        NumberFormatService.generateFullCaseNumber(),
                        null,
                        null,
                        null,
                        DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                        "Response due for " + NumberFormatService.generateFullCaseNumber()
                );
            }

            String activity = entryDirection + entryDescription;
            activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addIEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addJEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

            if (dueDate.getResponseDueDate() != null) {

                EmailOutInvites.addNewHearing("CMDS",
                        CMDSCase.getALJemail(),
                        null,
                        "Response due for " + NumberFormatService.generateFullCaseNumber(),
                        NumberFormatService.generateFullCaseNumber(),
                        null,
                        null,
                        null,
                        DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                        "Response due for " + NumberFormatService.generateFullCaseNumber()
                );
            }

            String activity = entryDirection + entryDescription;
            activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addKEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    boolean updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addLEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addMEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSAppealsCourtDialog court = new CMDSAppealsCourtDialog(dialog, true);

            String activity = entryDirection + entryDescription;
            activity += (court.getSelection() == null ? "" : " - Appealed to " + court.getSelection());
            activity += (court.getCaseNumber().equals("") ? "" : " - Case Number " + court.getCaseNumber());
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeMEntry(NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeMEntry(groupList.get(i).toString());
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeMEntry(NumberFormatService.generateFullCaseNumber());
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeMEntry(groupList.get(i).toString());
                    }
                }
            }
            status.dispose();
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addNEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSClearDateDialog clear = new CMDSClearDateDialog(dialog, true);

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), groupList.get(i).toString());
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), NumberFormatService.generateFullCaseNumber());
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), groupList.get(i).toString());
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addOEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(dialog, true);

            if (dueDate.getResponseDueDate() != null) {

                EmailOutInvites.addNewHearing("CMDS",
                        CMDSCase.getALJemail(),
                        null,
                        "Response due for " + NumberFormatService.generateFullCaseNumber(),
                        NumberFormatService.generateFullCaseNumber(),
                        null,
                        null,
                        null,
                        DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                        "Response due for " + NumberFormatService.generateFullCaseNumber()
                );
            }

            String activity = entryDirection + entryDescription;
            activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }

        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addPEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addQEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSWhichGreenCardDialog pullDate = new CMDSWhichGreenCardDialog(dialog, true);

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                            whichPullDate(pullDate.getWhichType()),
                            pullDate.getSignedDate(),
                            pullDate.getPullDate(),
                            NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                                whichPullDate(pullDate.getWhichType()),
                                pullDate.getSignedDate(),
                                pullDate.getPullDate(),
                                groupList.get(i).toString());
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                            whichPullDate(pullDate.getWhichType()),
                            pullDate.getSignedDate(),
                            pullDate.getPullDate(),
                            NumberFormatService.generateFullCaseNumber());
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                                whichPullDate(pullDate.getWhichType()),
                                pullDate.getSignedDate(),
                                pullDate.getPullDate(),
                                groupList.get(i).toString());
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
            pullDate.dispose();
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addREntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeREntry(entryDate, NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeREntry(entryDate, groupList.get(i).toString());
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    CMDSCase.updateCaseByTypeREntry(entryDate, NumberFormatService.generateFullCaseNumber());
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));

                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        CMDSCase.updateCaseByTypeREntry(entryDate, groupList.get(i).toString());
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addSEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addUEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            CMDSPBRBoxDialog pbr = new CMDSPBRBoxDialog(dialog, true);

            String activity = entryDirection + entryDescription;
            activity += (pbr.getPbrBox().equals("") ? "" : " " + pbr.getPbrBox());
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                            NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                                groupList.get(i).toString());
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                            NumberFormatService.generateFullCaseNumber());
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(),
                                groupList.get(i).toString());
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
            pbr.dispose();
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addVEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {
            CMDSRemailedDialog remailed = new CMDSRemailedDialog(dialog, true);
            CMDSPullDateDialog pulldate = null;

            if (entryDescription.equals("R & R Remailed Certified Mail")
                    || entryDescription.equals("R & R Remailed Regular Mail")) {
                pulldate = new CMDSPullDateDialog(dialog, true);
            }

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                            remailed.getRemailedDate(),
                            pulldate == null ? null : pulldate.getResponseDueDate(),
                            NumberFormatService.generateFullCaseNumber());
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                                remailed.getRemailedDate(),
                                pulldate == null ? null : pulldate.getResponseDueDate(),
                                groupList.get(i).toString());
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                            remailed.getRemailedDate(),
                            pulldate == null ? null : pulldate.getResponseDueDate(),
                            NumberFormatService.generateFullCaseNumber());
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()),
                                remailed.getRemailedDate(),
                                pulldate == null ? null : pulldate.getResponseDueDate(),
                                groupList.get(i).toString());
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
            remailed.dispose();
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private static void addWEntryType(String entryDescription,
            String extraText, String partyType, String mailType, String entryDate,
            java.awt.Dialog dialog, String filePath) {

        try {

            String activity = entryDirection + entryDescription;
            activity += (partyType.equals("") ? "" : " " + partyType);
            activity += (mailType.equals("") ? "" : " " + mailType);

            Date date = Global.mmddyyyy.parse(entryDate);

            boolean updateAllCases = false;

            CMDSUpdateInventoryStatusLineDialog status = new CMDSUpdateInventoryStatusLineDialog(dialog, true, activity);

            if (status.isUpdateStatus()) {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    CMDSCase.updateCaseInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                }

                if (groupList.size() > 0) {
                    CMDSUpdateAllGroupCasesDialog update = new CMDSUpdateAllGroupCasesDialog(dialog, true);

                    updateAllCases = update.isUpdateStatus();

                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                        if (updateAllCases) {
                            CMDSCase.updateAllGroupInventoryStatusLines(activity + (extraText.equals("") ? "" : " " + extraText), date);
                        }
                    }
                }
            } else {
                List groupList = CMDSCase.getGroupNumberList();

                if (groupList.isEmpty()) {
                    Activity.addCMDSActivty(activity, filePath, date, NumberFormatService.generateFullCaseNumber(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                } else if (groupList.size() > 0) {
                    for (int i = 0; i < groupList.size(); i++) {
                        Activity.addCMDSActivty(activity, filePath, date, groupList.get(i).toString(), null, null, entryCategory, entryDescription, (extraText.trim().equals("") ? null : extraText.trim()));
                    }
                }
            }
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
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
