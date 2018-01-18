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
import parker.serb.sql.PostalOut;
import parker.serb.sql.PostalOutAttachment;
import parker.serb.util.FileService;

/**
 *
 * @author User
 */
public class ProcessSendingPostal {

    private static boolean fileInUse = false;

    public static String sendPostal(int letterID) {
        PostalOut post = PostalOut.getPostalOutByID(letterID);
        if (verifyFilesExist(post)) {
            if (!fileInUse) {
                return postalSend.sendPostal(letterID);
            }
        }
        return "";
    }

    private static boolean verifyFilesExist(PostalOut post) {
        boolean allExist = true;
        fileInUse = false;
        String path = "";
        List<PostalOutAttachment> attachList = PostalOutAttachment.getPostalOutAttachments(post.id);
        if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                || Global.activeSection.equalsIgnoreCase("CSC")
                || Global.activeSection.equalsIgnoreCase("ORG")) {
            path = Global.activityPath
                    + (Global.activeSection.equals("Civil Service Commission")
                    ? post.caseType : Global.activeSection) + File.separator + post.caseNumber + File.separator;
        } else if (Global.activeSection.equals("Hearings")){
            path = Global.activityPath
                    + FileService.getCaseSectionFolderByCaseType(post.caseType) + File.separatorChar
                    + post.caseYear + File.separatorChar
                    + (post.caseYear + "-" + post.caseType + "-" + post.caseMonth + "-" + post.caseNumber)
                    + File.separatorChar;
        } else {
            path = Global.activityPath + File.separatorChar
                    + Global.activeSection + File.separatorChar
                    + post.caseYear + File.separatorChar
                    + (post.caseYear + "-" + post.caseType + "-" + post.caseMonth + "-" + post.caseNumber)
                    + File.separatorChar;
        }

        for (PostalOutAttachment attach : attachList) {
            File attachment = new File(path + attach.fileName);
            if (!attachment.exists()) {
                System.out.println("Attachment for PostalID: " + post.id + " can't be found"  
                        + System.lineSeparator() + "    (" + path + attach.fileName + ")");
                allExist = false;
            } else {
                if ("docx".equalsIgnoreCase(FilenameUtils.getExtension(attach.fileName))
                        || "doc".equalsIgnoreCase(FilenameUtils.getExtension(attach.fileName))) {
                    if (!attachment.renameTo(attachment)) {
                        System.out.println("Attachment for PostalID: " + post.id + " is in use"  
                        + System.lineSeparator() + "    (" + path + attach.fileName + ")");
                        fileInUse = true;
                    }
                }
            }
        }
        return allExist;
    }

}
