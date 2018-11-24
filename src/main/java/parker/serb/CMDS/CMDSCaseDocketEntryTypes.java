/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.CMDS;

import java.sql.Timestamp;
import java.util.Date;
import parker.serb.Global;
import parker.serb.sql.Activity;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.EmailOutInvites;
import parker.serb.util.DateConversion;
import parker.serb.util.FileService;

/**
 *
 * @author parkerjohnston
 */
public class CMDSCaseDocketEntryTypes {

    public static void updateCaseHistory(CMDSCaseDocketEntryModel docket) {
        switch (docket.category) {
            case "A":
                addAEntryType(docket);
                break;
            case "C":
                addCEntryType(docket);
                break;
            case "D":
                addDEntryType(docket);
                break;
            case "E":
                addEEntryType(docket);
                break;
            case "F":
                addFEntryType(docket);
                break;
            case "G":
                addGEntryType(docket);
                break;
            case "H":
                addHEntryType(docket);
                break;
            case "I":
                addIEntryType(docket);
                break;
            case "J":
                addJEntryType(docket);
                break;
            case "K":
                addKEntryType(docket);
                break;
            case "L":
                addLEntryType(docket);
                break;
            case "M":
                addMEntryType(docket);
                break;
            case "N":
                addNEntryType(docket);
                break;
            case "O":
                addOEntryType(docket);
                break;
            case "P":
                addPEntryType(docket);
                break;
            case "Q":
                addQEntryType(docket);
                break;
            case "R":
                addREntryType(docket);
                break;
            case "S":
                addSEntryType(docket);
                break;
            case "U":
                addUEntryType(docket);
                break;
            case "V":
                addVEntryType(docket);
                break;
            case "W":
                addWEntryType(docket);
                break;
        }
    }

    private static void addAEntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - Notice of " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber,
                        d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
    }

    private static void addCEntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - R & R mailed " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                CMDSCase.updateCaseByTypeCEntry(d.entryDate, cmdsCaseNumber);

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
    }

    private static void addDEntryType(CMDSCaseDocketEntryModel d) {
        CMDSResultDialog result = new CMDSResultDialog(d.dialog, true);

        String activity = d.direction + " - Board Order mailed " + d.entryDescription;
        if (!result.getResult().equals("")) {
            activity += " Code " + result.getResult();
        }

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                String caseStatus = CMDSCase.getCaseStatus(cmdsCaseNumber);
                Timestamp MailedBO = new Timestamp(d.entryDate.getTime());

                if (d.entryDescription.toLowerCase().contains("stayed")
                        || d.entryDescription.toLowerCase().contains("fifting of stay")) {

                    if (caseStatus.equals("S")) {
                        CMDSRemoveStayDialog removeStay = new CMDSRemoveStayDialog(d.dialog, true);
                        if (removeStay.isRemoveStay()) {
                            caseStatus = "O";
                        } else {
                            caseStatus = "S";
                        }
                        removeStay.dispose();
                    } else if (caseStatus.equals("O")) {
                        CMDSPlaceStayDialog placeStay = new CMDSPlaceStayDialog(d.dialog, true);
                        if (placeStay.isPlaceStay()) {
                            caseStatus = "S";
                        } else {
                            caseStatus = "O";
                        }
                        placeStay.dispose();
                    }
                }

                if (!result.getResult().equals("")) {
                    Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                    CMDSCase.updateCaseByTypeDEntry(result.getResult(), MailedBO, caseStatus, cmdsCaseNumber);

                    if (d.updateStatusInventoryLine) {
                        CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                    }
                }
            }
        }
        result.dispose();
    }

    private static void addEEntryType(CMDSCaseDocketEntryModel d) {
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(d.dialog, true);
        CMDSCertifiedLetterDialog certified = new CMDSCertifiedLetterDialog(d.dialog, true);

        String activity = d.direction + " - " + d.entryDescription + (certified.isCertified() ? " - (Certified)" : "");

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {

                if (dueDate.getResponseDueDate() != null) {
                    EmailOutInvites.addNewHearing("CMDS",
                            CMDSCase.getALJemail(),
                            null,
                            "Response due for " + cmdsCaseNumber,
                            cmdsCaseNumber,
                            null,
                            null,
                            null,
                            DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                            "Response due for " + cmdsCaseNumber
                    );
                }

                String caseStatus = CMDSCase.getCaseStatus(cmdsCaseNumber);

                CMDSCase PODate = CMDSCase.getmailedPODates(cmdsCaseNumber);

                if (PODate.mailedPO1 == null) {
                    PODate.mailedPO1 = new Timestamp(d.entryDate.getTime());
                } else if (PODate.mailedPO2 == null) {
                    PODate.mailedPO2 = new Timestamp(d.entryDate.getTime());
                } else if (PODate.mailedPO3 == null) {
                    PODate.mailedPO3 = new Timestamp(d.entryDate.getTime());
                } else if (PODate.mailedPO4 == null) {
                    PODate.mailedPO4 = new Timestamp(d.entryDate.getTime());
                }

                if (caseStatus.equals("S")) {
                    CMDSRemoveStayDialog removeStay = new CMDSRemoveStayDialog(d.dialog, true);
                    if (removeStay.isRemoveStay()) {
                        caseStatus = "O";
                    } else {
                        caseStatus = "S";
                    }
                    removeStay.dispose();
                } else if (caseStatus.equals("O")) {
                    CMDSPlaceStayDialog placeStay = new CMDSPlaceStayDialog(d.dialog, true);
                    if (placeStay.isPlaceStay()) {
                        caseStatus = "S";
                    } else {
                        caseStatus = "O";
                    }
                    placeStay.dispose();
                }

                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                CMDSCase.updateCaseByTypeEEntry(PODate, caseStatus, cmdsCaseNumber);

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        dueDate.dispose();
        certified.dispose();
    }

    private static void addFEntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                CMDSCase rrpoPullDates = CMDSCase.getRRPOPullDates(cmdsCaseNumber);

                CMDSRRorPODialog rrpo = new CMDSRRorPODialog(d.dialog, true);

                if (rrpo.getSelection().equals("R&R")) {
                    CMDSPullDateDialog pullDate = new CMDSPullDateDialog(d.dialog, true);
                    rrpoPullDates.pullDateRR = pullDate.getResponseDueDate();
                    pullDate.dispose();
                } else {
                    CMDSWhichPOPullDateDialog whichPO = new CMDSWhichPOPullDateDialog(d.dialog, true);

                    CMDSPullDateDialog pullDate = new CMDSPullDateDialog(d.dialog, true);

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

                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                CMDSCase.updateCaseByTypeFEntry(rrpoPullDates, cmdsCaseNumber);

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
    }

    private static void addGEntryType(CMDSCaseDocketEntryModel d) {
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(d.dialog, true);

        String activity = d.direction + " - " + d.entryDescription;
        if (dueDate.getResponseDueDate() != null) {
            activity += (dueDate.getResponseDueDate() == null ? "" : " " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));
        }

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {

                if (dueDate.getResponseDueDate() != null) {
                    EmailOutInvites.addNewHearing("CMDS",
                            CMDSCase.getALJemail(),
                            null,
                            "Response due for " + cmdsCaseNumber,
                            cmdsCaseNumber,
                            null,
                            null,
                            null,
                            DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                            "Response due for " + cmdsCaseNumber
                    );
                }

                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        dueDate.dispose();
    }

    private static void addHEntryType(CMDSCaseDocketEntryModel d) {
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(d.dialog, true);

        String activity = d.direction + " - " + d.entryDescription;
        if (dueDate.getResponseDueDate() != null) {
            activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));
        }

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {

                if (dueDate.getResponseDueDate() != null) {
                    EmailOutInvites.addNewHearing("CMDS",
                            CMDSCase.getALJemail(),
                            null,
                            "Response due for " + cmdsCaseNumber,
                            cmdsCaseNumber,
                            null,
                            null,
                            null,
                            DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                            "Response due for " + cmdsCaseNumber
                    );
                }

                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        dueDate.dispose();
    }

    private static void addIEntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
    }

    private static void addJEntryType(CMDSCaseDocketEntryModel d) {
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(d.dialog, true);
        String activity = d.direction + " - " + d.entryDescription;

        if (dueDate.getResponseDueDate() != null) {
            activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));
        }

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                if (dueDate.getResponseDueDate() != null) {
                    EmailOutInvites.addNewHearing("CMDS",
                            CMDSCase.getALJemail(),
                            null,
                            "Response due for " + cmdsCaseNumber,
                            cmdsCaseNumber,
                            null,
                            null,
                            null,
                            DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                            "Response due for " + cmdsCaseNumber
                    );
                }

                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        dueDate.dispose();
    }

    private static void addKEntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
    }

    private static void addLEntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
    }

    private static void addMEntryType(CMDSCaseDocketEntryModel d) {
        CMDSAppealsCourtDialog court = new CMDSAppealsCourtDialog(d.dialog, true);

        String activity = d.direction + " - " + d.entryDescription;
        activity += (court.getSelection() == null ? "" : " - Appealed d.to " + court.getSelection());
        activity += (court.getCaseNumber().equals("") ? "" : " - Case Number " + court.getCaseNumber());

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                CMDSCase.updateCaseByTypeMEntry(cmdsCaseNumber);

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        court.dispose();
    }

    private static void addNEntryType(CMDSCaseDocketEntryModel d) {
        CMDSClearDateDialog clear = new CMDSClearDateDialog(d.dialog, true);
        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                CMDSCase.updateCaseByTypeNEntry(clearWhichDate(clear.getDateType(), clear.getWhichDate()), cmdsCaseNumber);

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        clear.dispose();
    }

    private static void addOEntryType(CMDSCaseDocketEntryModel d) {
        CMDSResponseDueDateDialog dueDate = new CMDSResponseDueDateDialog(d.dialog, true);
        String activity = d.direction + " - " + d.entryDescription;

        if (dueDate.getResponseDueDate() != null) {
            activity += (dueDate.getResponseDueDate() == null ? "" : " Response Due " + Global.mmddyyyy.format(new Date(dueDate.getResponseDueDate().getTime())));
        }

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                if (dueDate.getResponseDueDate() != null) {
                    EmailOutInvites.addNewHearing("CMDS",
                            CMDSCase.getALJemail(),
                            null,
                            "Response due for " + cmdsCaseNumber,
                            cmdsCaseNumber,
                            null,
                            null,
                            null,
                            DateConversion.generateReminderStartDate(dueDate.getResponseDueDate()),
                            "Response due for " + cmdsCaseNumber
                    );
                }
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        dueDate.dispose();
    }

    private static void addPEntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
    }

    private static void addQEntryType(CMDSCaseDocketEntryModel d) {
        CMDSWhichGreenCardDialog pullDate = new CMDSWhichGreenCardDialog(d.dialog, true);
        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                CMDSCase.updateCaseByTypeQEntry(greenCardWhichDate(pullDate.getWhichType()),
                        whichPullDate(pullDate.getWhichType()),
                        pullDate.getSignedDate(),
                        pullDate.getPullDate(),
                        cmdsCaseNumber);

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        pullDate.dispose();
    }

    private static void addREntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - " + d.entryDescription;
        String entryDate2 = Global.MMMMddyyyy.format(d.entryDate);

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                CMDSCase.updateCaseByTypeREntry(entryDate2, cmdsCaseNumber);

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
    }

    private static void addSEntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
    }

    private static void addUEntryType(CMDSCaseDocketEntryModel d) {
        CMDSPBRBoxDialog pbr = new CMDSPBRBoxDialog(d.dialog, true);

        String activity = d.direction + " - " + d.entryDescription;
        activity += (pbr.getPbrBox().equals("") ? "" : " " + pbr.getPbrBox());

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                CMDSCase.updateCaseByTypeUEntry(pbr.getPbrBox().equals("") ? null : pbr.getPbrBox(), cmdsCaseNumber);

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        pbr.dispose();
    }

    private static void addVEntryType(CMDSCaseDocketEntryModel d) {
        CMDSRemailedDialog remailed = new CMDSRemailedDialog(d.dialog, true);
        CMDSPullDateDialog pulldate = null;

        if (d.entryDescription.equals("R & R Remailed Certified Mail")
                || d.entryDescription.equals("R & R Remailed Regular Mail")) {
            pulldate = new CMDSPullDateDialog(d.dialog, true);
        }

        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));
                CMDSCase.updateCaseByTypeVEntry(whichRemailedDate(remailed.getWhichType()), remailed.getRemailedDate(), pulldate == null ? null : pulldate.getResponseDueDate(), cmdsCaseNumber);

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
                }
            }
        }
        remailed.dispose();
    }

    private static void addWEntryType(CMDSCaseDocketEntryModel d) {
        String activity = d.direction + " - " + d.entryDescription;

        for (String cmdsCaseNumber : d.caseNumbers) {
            if (FileService.CMDSDocketingFileOperation(d, cmdsCaseNumber)) {
                Activity.addCMDSActivty(activity, d.fileName, d.entryDate, cmdsCaseNumber, d.from, d.to, d.category, d.entryDescription, (d.comment.trim().equals("") ? null : d.comment.trim()));

                if (d.updateStatusInventoryLine) {
                    CMDSCase.updateCaseInventoryStatusLines(activity + (d.comment.equals("") ? "" : " " + d.comment), d.entryDate, cmdsCaseNumber);
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
