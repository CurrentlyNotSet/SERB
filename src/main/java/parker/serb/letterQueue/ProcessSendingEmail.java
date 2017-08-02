/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterQueue;

import java.io.File;
import java.util.List;
import org.apache.commons.io.FilenameUtils;
import parker.serb.Global;
import parker.serb.sql.EmailOut;
import parker.serb.sql.EmailOutAttachment;
import parker.serb.sql.HearingCase;
import static parker.serb.util.FileService.getCaseSectionFolderByCaseType;

/**
 *
 * @author User
 */
public class ProcessSendingEmail {

    static boolean fileInUse = false;
    static double attachmentSize = 0.0;

    public static boolean sendEmail(int letterID) {
        EmailOut eml = EmailOut.getEmailByID(letterID);
        if (verifyFilesExist(eml)) {
            if (!fileInUse) {
                if (Global.EMAIL_SIZE_LIMIT >= attachmentSize) {
                    EmailOut.markEmailReadyToSend(eml.id);
                    return true;
                }
            }
        } else {
            
        }
        return false;
    }

    private static boolean verifyFilesExist(EmailOut eml) {
        boolean allExist = true;
        fileInUse = false;
        String path = "";
        List<EmailOutAttachment> attachList = EmailOutAttachment.getEmailAttachments(eml.id);
        if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                || Global.activeSection.equalsIgnoreCase("CSC")
                || Global.activeSection.equalsIgnoreCase("ORG")) {
            path = Global.activityPath
                    + (Global.activeSection.equals("Civil Service Commission")
                    ? eml.caseType : Global.activeSection) + File.separator + eml.caseNumber + File.separator;
        } else if(Global.activeSection.equalsIgnoreCase("hearings")) {
            path = Global.activityPath + File.separatorChar
                    + getCaseSectionFolderByCaseType(eml.caseType) + File.separatorChar
                    + eml.caseYear + File.separatorChar
                    + (eml.caseYear + "-" + getCaseSectionFolderByCaseType(eml.caseType) + "-" + eml.caseMonth + "-" + eml.caseNumber)
                    + File.separatorChar;
        } else {
            path = Global.activityPath + File.separatorChar
                    + Global.activeSection + File.separatorChar
                    + eml.caseYear + File.separatorChar
                    + (eml.caseYear + "-" + eml.caseType + "-" + eml.caseMonth + "-" + eml.caseNumber)
                    + File.separatorChar;
        }

        for (EmailOutAttachment attach : attachList) {
            File attachment = new File(path + attach.fileName);
            if (!attachment.exists()) {
                allExist = false;
            } else {
                if ("docx".equalsIgnoreCase(FilenameUtils.getExtension(attach.fileName))
                        || "doc".equalsIgnoreCase(FilenameUtils.getExtension(attach.fileName))) {
                    if (!attachment.renameTo(attachment)) {
                        fileInUse = true;
                    }
                    attachmentSize += attachment.length();
                }
            }
        }
        return allExist;
    }

}
