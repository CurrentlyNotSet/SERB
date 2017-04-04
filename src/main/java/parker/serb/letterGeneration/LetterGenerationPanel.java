/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.letterGeneration;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.laf.optionpane.WebOptionPane;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Date;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.generateDocument;
import parker.serb.bookmarkProcessing.questionsCMDSModel;
import parker.serb.bookmarkProcessing.questionsCMDSPanel;
import parker.serb.sql.Activity;
import parker.serb.sql.Audit;
import parker.serb.sql.CMDSDocuments;
import parker.serb.sql.CSCCase;
import parker.serb.sql.CaseParty;
import parker.serb.sql.EmailOut;
import parker.serb.sql.EmailOutAttachment;
import parker.serb.sql.FactFinder;
import parker.serb.sql.MEDCase;
import parker.serb.sql.ORGCase;
import parker.serb.sql.PostalOut;
import parker.serb.sql.PostalOutAttachment;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;
import parker.serb.util.SlackNotification;
import parker.serb.util.StringUtilities;

/**
 *
 * @author parker
 */
public class LetterGenerationPanel extends javax.swing.JDialog {

    SMDSDocuments SMDSdocToGenerate;
    CMDSDocuments CMDSdocToGenerate;
    MEDCase medCaseData;
    ORGCase orgCase;
    CSCCase cscCase;
    List<Integer> toParties = new ArrayList<>();
    List<Integer> ccParties = new ArrayList<>();
    boolean sendToEmail = false;
    boolean sendToPostal = false;
    String toEmail = "";
    String ccEmail = "";
    double attachmentSize = 0.0;

    public LetterGenerationPanel(java.awt.Frame parent, boolean modal, SMDSDocuments SMDSdocumentToGeneratePassed, CMDSDocuments CMDSdocumentToGeneratePassed) {
        super(parent, modal);
        initComponents();
        addRenderer();
        loadPanel(SMDSdocumentToGeneratePassed, CMDSdocumentToGeneratePassed);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addRenderer() {
        personTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Global.ALTERNATE_ROW_COLOR);
                }
                return c;
            }
        });

        activityTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Global.ALTERNATE_ROW_COLOR);
                }
                return c;
            }
        });

        additionalDocsTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
            private DefaultTableCellRenderer DEFAULT_RENDERER =  new DefaultTableCellRenderer();
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = DEFAULT_RENDERER.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (!isSelected) {
                    c.setBackground(row % 2 == 0 ? Color.WHITE : Global.ALTERNATE_ROW_COLOR);
                }
                return c;
            }
        });
    }

    private void loadPanel(SMDSDocuments SMDSdocumentToGeneratePassed, CMDSDocuments CMDSdocumentToGeneratePassed) {
        SMDSdocToGenerate = SMDSdocumentToGeneratePassed;
        CMDSdocToGenerate = CMDSdocumentToGeneratePassed;
        if (SMDSdocToGenerate != null) {
            documentLabel.setText("Document: " + SMDSdocToGenerate.description);
        } else if (CMDSdocToGenerate != null) {
            documentLabel.setText("Document: " + CMDSdocToGenerate.LetterName);
        }

        if (Global.activeSection.equals("ORG")) {
            orgCase = ORGCase.loadORGInformation();
        } else if (Global.activeSection.equals("Civil Service Commission")) {
            cscCase = CSCCase.loadCSCInformation();
        }

        setColumnWidth();
        loadPartyTable();
        loadActivityDocumentsTable();
        loadExtraAttachmentTable();
        loadingPanel.setVisible(false);
    }

    private void setColumnWidth() {
        personTable.getColumnModel().getColumn(0).setMinWidth(0);
        personTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        personTable.getColumnModel().getColumn(0).setMaxWidth(0);
        personTable.getColumnModel().getColumn(1).setMinWidth(80);
        personTable.getColumnModel().getColumn(1).setPreferredWidth(80);
        personTable.getColumnModel().getColumn(1).setMaxWidth(80);
        personTable.getColumnModel().getColumn(2).setMinWidth(90);
        personTable.getColumnModel().getColumn(2).setPreferredWidth(90);
        personTable.getColumnModel().getColumn(2).setMaxWidth(90);

        activityTable.getColumnModel().getColumn(0).setMinWidth(0);
        activityTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        activityTable.getColumnModel().getColumn(0).setMaxWidth(0);
        activityTable.getColumnModel().getColumn(1).setMinWidth(60);
        activityTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        activityTable.getColumnModel().getColumn(1).setMaxWidth(60);
        activityTable.getColumnModel().getColumn(3).setMinWidth(0);
        activityTable.getColumnModel().getColumn(3).setPreferredWidth(0);
        activityTable.getColumnModel().getColumn(3).setMaxWidth(0);

        additionalDocsTable.getColumnModel().getColumn(0).setMinWidth(0);
        additionalDocsTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        additionalDocsTable.getColumnModel().getColumn(0).setMaxWidth(0);
        additionalDocsTable.getColumnModel().getColumn(1).setMinWidth(60);
        additionalDocsTable.getColumnModel().getColumn(1).setPreferredWidth(60);
        additionalDocsTable.getColumnModel().getColumn(1).setMaxWidth(60);
        additionalDocsTable.getColumnModel().getColumn(3).setMinWidth(0);
        additionalDocsTable.getColumnModel().getColumn(3).setPreferredWidth(0);
        additionalDocsTable.getColumnModel().getColumn(3).setMaxWidth(0);
    }

    private JComboBox loadLocationComboBox() {
        JComboBox locationCombo = new JComboBox();
        locationCombo.removeAllItems();
        locationCombo.addItem("");
        locationCombo.addItem("Email");
        locationCombo.addItem("Postal");
        locationCombo.addItem("Both");
        return locationCombo;
    }

    private JComboBox loadToCCComboBox() {
        JComboBox ToCCCombo = new JComboBox();
        ToCCCombo.removeAllItems();
        ToCCCombo.addItem("");
        ToCCCombo.addItem("TO:");
        ToCCCombo.addItem("CC:");
        return ToCCCombo;
    }

    private void loadPartyTable() {
        DefaultTableModel model = (DefaultTableModel) personTable.getModel();
        model.setRowCount(0);

        JComboBox toCCComboBox = loadToCCComboBox();
        TableColumn rowOneCombo = personTable.getColumnModel().getColumn(1);
        rowOneCombo.setCellEditor(new DefaultCellEditor(toCCComboBox));

        toCCComboBox.addActionListener((ActionEvent e) -> {
            ExecutorService executor = Executors.newSingleThreadExecutor();
            Runnable task = () -> {
                if (personTable.getSelectedRow() >= 0 && personTable.getSelectedColumn() == 1) {
                    if (!personTable.getValueAt(personTable.getSelectedRow(), 1).toString().equals("")
                            && !personTable.getValueAt(personTable.getSelectedRow(), 5).toString().equals("")
                            && personTable.getValueAt(personTable.getSelectedRow(), 2).toString().equals("")) {
                        personTable.setValueAt("Email", personTable.getSelectedRow(), 2);
                    } else if (!personTable.getValueAt(personTable.getSelectedRow(), 1).toString().equals("")
                            && personTable.getValueAt(personTable.getSelectedRow(), 5).toString().equals("")
                            && personTable.getValueAt(personTable.getSelectedRow(), 2).toString().equals("")) {
                        personTable.setValueAt("Postal", personTable.getSelectedRow(), 2);
                    } else if (personTable.getValueAt(personTable.getSelectedRow(), 1).toString().equals("")) {
                        personTable.setValueAt("", personTable.getSelectedRow(), 2);
                    }
                }
            };
            executor.submit(task);
        });

        JComboBox destinationComboBox = loadLocationComboBox();
        TableColumn rowTwoCombo = personTable.getColumnModel().getColumn(2);
        rowTwoCombo.setCellEditor(new DefaultCellEditor(destinationComboBox));

        // SET TO/CC Listener
        destinationComboBox.addItemListener((ItemEvent e) -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                if (personTable.getSelectedRow() > -1){
                    if (e.getItem().equals("") && personTable.getSelectedColumn() == 2) {
                        personTable.setValueAt("", personTable.getSelectedRow(), 1);
                    }
                    if (personTable.getValueAt(personTable.getSelectedRow(), 1).equals("")) {
                        personTable.setValueAt("", personTable.getSelectedRow(), 2);
                    }
                }
            }
        });

        List<CaseParty> partyList = null;

        if (orgCase != null){
            partyList = CaseParty.loadORGPartiesByCase();

            String toCC = "";
            String emailPostal = "";

            model.addRow(new Object[]{
                orgCase.orgNumber, // ID
                toCC, // TO/CC
                emailPostal, // Email/Postal
                "ORG",
                orgCase.orgName, // NAME
                orgCase.orgEmail
            });
        } else if (cscCase != null){
            partyList = CaseParty.loadORGPartiesByCase();

            String toCC = "";
            String emailPostal = "";

            model.addRow(new Object[]{
                cscCase.cscNumber, // ID
                toCC, // TO/CC
                emailPostal, // Email/Postal
                "CSC",
                cscCase.name, // NAME
                cscCase.email
            });
        } else {
            partyList = CaseParty.loadPartiesByCaseForLetterGeneration();
        }

        for (CaseParty party : partyList) {
            String toCC = "";
            String emailPostal = "";
            String caseRelation = party.caseRelation;

            if (Global.activeSection.equals("MED")
                    && (party.caseRelation.equals("Employer REP") || party.caseRelation.equals("Employee Organization REP"))
                    && SMDSdocToGenerate.id != 5475) {
                if (!party.emailAddress.trim().equals("")) {
                    emailPostal = "Email";
                } else {
                    emailPostal = "Postal";
                }
                toCC = "TO:";
            }

            if (Global.activeSection.equals("Hearings")){
                caseRelation = party.caseRelation.replace("Charged", "Respondent").replace("Charging", "Intervenor");
            }


            model.addRow(new Object[]{
                party.id, // ID
                toCC, // TO/CC
                emailPostal, // Email/Postal
                caseRelation,
                StringUtilities.buildCasePartyNameNoPreFix(party), // NAME
                party.emailAddress
            });
        }
    }

    private void loadActivityDocumentsTable() {
        DefaultTableModel model = (DefaultTableModel) activityTable.getModel();
        model.setRowCount(0);

        List<Activity> activtyList = null;

        switch (Global.activeSection) {
            case "ORG":
            case "Civil Service Commission":
                activtyList = Activity.loadActivityDocumentsByGlobalCaseORG();
                break;
            case "Hearings":
                activtyList = Activity.loadHearingActivity();
                break;
            default:
                activtyList = Activity.loadActivityDocumentsByGlobalCase();
                break;
        }

        for (Activity doc : activtyList) {
            model.addRow(new Object[]{
                doc.id,
                false,
                doc.action,
                doc.fileName
            });
        }
    }

    private void loadExtraAttachmentTable() {
        DefaultTableModel model = (DefaultTableModel) additionalDocsTable.getModel();
        model.setRowCount(0);
        List<SMDSDocuments> documentList = null;
        List<CMDSDocuments> CMDSdocumentList = null;
        List<FactFinder> ffList = null;

        switch (Global.activeSection) {
            case "REP":
                documentList = SMDSDocuments.loadDocumentNamesByTypeAndSection(Global.activeSection, "Quest");
                break;
            case "ULP":
                documentList = SMDSDocuments.loadDocumentNamesByTypeAndSection(Global.activeSection, "Quest");
                break;
            case "ORG":
                documentList = SMDSDocuments.loadDocumentNamesByTypeAndSection(Global.activeSection, "Quest");
                break;
            case "MED":
                additionalDocumentsLabel.setText("Fact Finder / Conciliator Bios:");
                additionalDocsTable.getColumnModel().getColumn(2).setHeaderValue("Person");
                ffList = FactFinder.loadActiveFF();
                break;
            case "Hearings":
                break;
            case "Civil Service Commission":
                documentList = SMDSDocuments.loadDocumentNamesByTypeAndSection("CSC", "Quest");
                break;
            case "CMDS":
                break;
        }

        if (!Global.activeSection.equals("CMDS")) {
            if (documentList != null && !Global.activeSection.equals("MED")) {
                for (SMDSDocuments doc : documentList) {
                    boolean selected = false;

                    if (Global.activeSection.equals("ORG")
                            //&& SMDSdocToGenerate.fileName.startsWith("Tickler")
                            && doc.fileName.equals("EOAR.pdf")) {
                        selected = true;
                    }

                    if (Global.activeSection.equals("ORG")
                            && SMDSdocToGenerate.fileName.startsWith("New Registration")
                            && doc.fileName.equals("RegistrationReportForm.pdf")) {
                        selected = true;
                    }

                    model.addRow(new Object[]{
                        doc.id,
                        selected,
                        doc.description,
                        doc.fileName
                    });
                }
            }

            if (ffList != null && Global.activeSection.equals("MED")) {
                medCaseData = MEDCase.loadEntireCaseInformation();

                for (FactFinder ff : ffList) {
                    //Format Name
                    String person = StringUtilities.buildFullName(ff.firstName, ff.middleName, ff.lastName);

                    //check for checkmark
                    boolean selected = false;
                    if (SMDSdocToGenerate.description.contains("Panel") && medCaseData != null) {
                        selected = setMEDFFConcCheckMark(person.replaceAll("[^a-zA-Z]", "").toLowerCase());
                    }

                    //Spoof Filename

                    String bioFileName = ff.bioFileName;
                    if (bioFileName == null){
                        bioFileName = StringUtilities.buildFullName(ff.firstName, ff.middleName, ff.lastName) + ".doc";
                    }

                    //load table
                    model.addRow(new Object[]{
                        ff.id,
                        selected,
                        person,
                        ff.bioFileName
                    });
                }
            }
        } else {
            //For loading CMDS documents
        }
    }

    private void clearDate(WebDateField dateField, MouseEvent evt) {
        if (evt.getButton() == MouseEvent.BUTTON3 && dateField.isEnabled()) {
            ClearDateDialog dialog = new ClearDateDialog((JFrame) Global.root, true);
            if (dialog.isReset()) {
                dateField.setText("");
            }
            dialog.dispose();
        }
    }

    private boolean setMEDFFConcCheckMark(String Name) {
        if (SMDSdocToGenerate.description.contains("Fact Finder") && medCaseData != null) {
            if (!medCaseData.FFList2Name1.equals("")) {
                if (medCaseData.FFList2Name1.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.FFList2Name2.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.FFList2Name3.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.FFList2Name4.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.FFList2Name5.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)) {
                    return true;
                }
            } else {
                if (medCaseData.FFList1Name1.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.FFList1Name2.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.FFList1Name3.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.FFList1Name4.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.FFList1Name5.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)) {
                    return true;
                }
            }

        } else if ((SMDSdocToGenerate.description.contains("Conciliation") || SMDSdocToGenerate.description.contains("Conciliator")) && medCaseData != null) {
            if (!medCaseData.concilList2Name1.equals("")) {
                if (medCaseData.concilList2Name1.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.concilList2Name2.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.concilList2Name3.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.concilList2Name4.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.concilList2Name5.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)) {
                    return true;
                }
            } else {
                if (medCaseData.concilList1Name1.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.concilList1Name2.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.concilList1Name3.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.concilList1Name4.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)
                        || medCaseData.concilList1Name5.replaceAll("[^a-zA-Z]", "").toLowerCase().equals(Name)) {
                    return true;
                }
            }
        }
        return false;
    }

    private void generateLetter() {
        List<Integer> postalIDList = new ArrayList<>();
        getPartyList();

        String docName;
        questionsCMDSModel answers = null;

        switch (Global.activeSection) {
            case "CMDS":
                int count = CMDSDocuments.CMDSQuestionCount(CMDSdocToGenerate);
                if (count > 0) {
                    questionsCMDSPanel returnInfo = new questionsCMDSPanel(Global.root, true, CMDSdocToGenerate, count);
                    answers = returnInfo.answers;
                    returnInfo.dispose();
                } else {
                    answers = new questionsCMDSModel();
                }
                docName = generateDocument.generateCMDSdocument(CMDSdocToGenerate, answers, 0, toParties, ccParties);
                break;
            case "ORG":
                docName = generateDocument.generateSMDSdocument(SMDSdocToGenerate, 0, toParties, ccParties, orgCase, null, true);
                break;
            case "Civil Service Commission":
                docName = generateDocument.generateSMDSdocument(SMDSdocToGenerate, 0, toParties, ccParties, null, cscCase, true);
                break;
            default:
                docName = generateDocument.generateSMDSdocument(SMDSdocToGenerate, 0, toParties, ccParties, null, null, true);
                break;
        }

        if (docName != null) {

            switch (Global.activeSection) {
                case "CMDS":
                    Activity.addActivty("Generated " + CMDSdocToGenerate.LetterName, docName);
                    Audit.addAuditEntry("Generated " + CMDSdocToGenerate.LetterName);
                    break;
                case "ORG":
                    Activity.addActivtyORGCase("ORG", orgCase.orgNumber ,
                            "Generated " + (SMDSdocToGenerate.historyDescription == null ? SMDSdocToGenerate.description : SMDSdocToGenerate.historyDescription)
                            , docName);
                    String lastNotify = "";

                    if (SMDSdocToGenerate.description.startsWith("Tickler 45")) {
                        lastNotify = "45";
                    } else if (SMDSdocToGenerate.description.startsWith("Tickler 10")) {
                        lastNotify = "10";
                    } else if (SMDSdocToGenerate.description.startsWith("Tickler 31")) {
                        lastNotify = "31";
                    } else if (SMDSdocToGenerate.description.startsWith("New")) {
                        lastNotify = "NR";
                    }

                    if (!lastNotify.equals("")){
                        ORGCase.updateORGLastNotification(lastNotify, orgCase.orgNumber);
                    }

                    Audit.addAuditEntry("Generated " + SMDSdocToGenerate.historyDescription == null ? SMDSdocToGenerate.description : SMDSdocToGenerate.historyDescription);
                    break;
                case "Civil Service Commission":
                    Activity.addActivtyORGCase("CSC", cscCase.cscNumber ,
                            "Generated " + (SMDSdocToGenerate.historyDescription == null ? SMDSdocToGenerate.description : SMDSdocToGenerate.historyDescription)
                            , docName);
                    Audit.addAuditEntry("Generated " + SMDSdocToGenerate.historyDescription == null ? SMDSdocToGenerate.description : SMDSdocToGenerate.historyDescription);
                    break;
                default:
                    Activity.addActivty("Generated " + (SMDSdocToGenerate.historyDescription == null ? SMDSdocToGenerate.description : SMDSdocToGenerate.historyDescription), docName);
                    Audit.addAuditEntry("Generated " + SMDSdocToGenerate.historyDescription == null ? SMDSdocToGenerate.description : SMDSdocToGenerate.historyDescription);
                    break;
            }

            int emailID = 0;
            int postalID = 0;

            if (sendToEmail) {
                emailID = insertEmail();
                insertGeneratedAttachementEmail(emailID, docName);
            }

            if (sendToPostal) {
                for (int i = 0; i < personTable.getRowCount(); i++) {
                    if (!personTable.getValueAt(i, 1).equals("")
                            && (personTable.getValueAt(i, 2).equals("Postal") || personTable.getValueAt(i, 2).equals("Both"))) {

                        boolean org = false;
                        boolean csc = false;
                        if (personTable.getValueAt(i, 3).equals("ORG")) {
                            org = true;
                        } else if (personTable.getValueAt(i, 3).equals("CSC")) {
                            csc = true;
                        }

                        postalID = insertPostal(personTable.getValueAt(i, 0).toString(), org, csc);
                        insertGeneratedAttachementPostal(postalID, docName);
                        postalIDList.add(postalID);
                    }
                }
            }

            insertExtraAttachmentsEmail(emailID, postalIDList);

            switch (Global.activeSection) {
                case "ORG":
                    FileService.openFileWithORGNumber("ORG", orgCase.orgNumber, docName);
                    break;
                case "Civil Service Commission":
                    FileService.openFileWithORGNumber("CSC", cscCase.cscNumber, docName);
                    break;
                case "Hearings":
                    FileService.openHearingCaseFile(docName);
                    break;
                default:
                    FileService.openFile(docName);
                    break;
            }

            reloadActivity();
        } else {
            WebOptionPane.showMessageDialog(Global.root,
                    "<html><div style='text-align: center;'>Files required to generate documents are missing."
                    + "<br><br>Unable to generate " + (Global.activeSection.equals("CMDS") ? CMDSdocToGenerate.LetterName
                    : SMDSdocToGenerate.historyDescription) + "</html>", "Required File Missing", WebOptionPane.ERROR_MESSAGE);
        }
    }

    private void getPartyList() {
        for (int i = 0; i < personTable.getRowCount(); i++) {

            //get Party List
            if (personTable.getValueAt(i, 1).equals("TO:")) {
                toParties.add(Integer.valueOf(personTable.getValueAt(i, 0).toString()));

                //Add TO: Email Addresses
                if ((personTable.getValueAt(i, 2).equals("Email") || personTable.getValueAt(i, 2).equals("Both"))
                        && !personTable.getValueAt(i, 5).equals("")) {
                    if (!toEmail.trim().equals("")) {
                        toEmail += "; ";
                    }
                    toEmail += personTable.getValueAt(i, 5);
                }
            } else if (personTable.getValueAt(i, 1).equals("CC:")) {
                ccParties.add(Integer.valueOf(personTable.getValueAt(i, 0).toString()));

                //Add CC: Email Addresses
                if ((personTable.getValueAt(i, 2).equals("Email") || personTable.getValueAt(i, 2).equals("Both"))
                        && !personTable.getValueAt(i, 5).equals("")) {
                    if (!ccEmail.trim().equals("")) {
                        ccEmail += "; ";
                    }
                    ccEmail += personTable.getValueAt(i, 5);
                }
            }

            //Get Destinations
            if (personTable.getValueAt(i, 2).equals("Email")) {
                sendToEmail = true;
            } else if (personTable.getValueAt(i, 2).equals("Postal")) {
                sendToPostal = true;
            } else if (personTable.getValueAt(i, 2).equals("Both")) {
                sendToEmail = true;
                sendToPostal = true;
            }
        }
    }

    private void reloadActivity() {
        switch (Global.activeSection) {
            case "REP":
                Global.root.getrEPRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "ULP":
                Global.root.getuLPRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "ORG":
                Global.root.getoRGRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "MED":
                Global.root.getmEDRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "Hearings":
                Global.root.getHearingRootPanel1().getActivityPanel1().loadAllHearingActivity();
                break;
            case "Civil Service Commission":
                Global.root.getcSCRootPanel1().getActivityPanel1().loadAllActivity();
                break;
            case "CMDS":
                Global.root.getcMDSRootPanel1().getActivityPanel1().loadAllActivity();
                break;
        }
    }

    private int insertEmail() {
        String emailBody = "";
        if (Global.activeSection.equals("CMDS")){
            emailBody = CMDSdocToGenerate.emailBody == null ? "" : CMDSdocToGenerate.emailBody;
        } else {
            emailBody = SMDSdocToGenerate.emailBody == null ? "" : SMDSdocToGenerate.emailBody;
        }

        emailBody += System.lineSeparator() + System.lineSeparator()
                + StringUtilities.buildFullName(Global.activeUser.firstName, Global.activeUser.middleInitial, Global.activeUser.lastName)
                + System.lineSeparator() + (Global.activeUser.jobTitle == null ? "" : Global.activeUser.jobTitle + System.lineSeparator())
                + StringUtilities.generateDepartmentAddressBlock() + System.lineSeparator()
                + (Global.activeUser.workPhone == null ? "" : "Telephone: " + Global.activeUser.workPhone);

        EmailOut eml = new EmailOut();

        eml.section = Global.activeSection.equals("Civil Service Commission") ? Global.caseType : Global.activeSection;
        eml.caseYear = Global.caseYear;
        eml.caseType = Global.caseType;
        eml.caseMonth = Global.caseMonth;
        eml.caseNumber = Global.caseNumber;
        eml.to = toEmail.trim().equals("") ? null : toEmail.trim();
        eml.from = Global.activeUser.emailAddress;
        eml.cc = ccEmail.trim().equals("") ? null : ccEmail.trim();
        eml.bcc = "serbeoarchive@serb.state.oh.us";
        if (Global.activeSection.equals("CMDS")){
            eml.subject = NumberFormatService.generateFullCaseNumber() +
                    (CMDSdocToGenerate.emailSubject == null
                    ? (CMDSdocToGenerate.LetterName == null ? "" : " " + CMDSdocToGenerate.LetterName)
                    : " " + CMDSdocToGenerate.emailSubject);
        } else {
            eml.subject = NumberFormatService.generateFullCaseNumber() +
                    (SMDSdocToGenerate.emailSubject == null
                    ? (SMDSdocToGenerate.description == null ? "" : " " + SMDSdocToGenerate.description)
                    : " " + SMDSdocToGenerate.emailSubject);
        }
        eml.body = emailBody;
        eml.userID = Global.activeUser.id;
        eml.suggestedSendDate = suggestedSendDatePicker.getText().equals("") ? null : new Date(NumberFormatService.convertMMDDYYYY(suggestedSendDatePicker.getText()));
        eml.okToSend = false;

        return EmailOut.insertEmail(eml);
    }

    private int insertPostal(String partyID, boolean Org, boolean Csc) {
        String name = "";
        String address = "";

        if (Org){
            CaseParty orgAddress = new CaseParty();

            orgAddress.address1 = orgCase.orgAddress1;
            orgAddress.address2 = orgCase.orgAddress2;
            orgAddress.city = orgCase.orgCity;
            orgAddress.stateCode = orgCase.orgState;
            orgAddress.zipcode = orgCase.orgZip;

            name = orgCase.orgName;
            address = StringUtilities.buildAddressBlockWithLineBreaks(orgAddress);

        } else if (Csc){
            CaseParty orgAddress = new CaseParty();

            orgAddress.address1 = cscCase.address1;
            orgAddress.address2 = cscCase.address2;
            orgAddress.city = cscCase.city;
            orgAddress.stateCode = cscCase.state;
            orgAddress.zipcode = cscCase.zipCode;

            name = orgCase.orgName;
            address = StringUtilities.buildAddressBlockWithLineBreaks(orgAddress);

        } else {
            CaseParty party = CaseParty.getCasePartyByID(partyID);
            name = StringUtilities.buildCasePartyNameNoPreFix(party);
            address = StringUtilities.buildAddressBlockWithLineBreaks(party);
        }

        PostalOut post = new PostalOut();

        post.section = Global.activeSection.equals("Civil Service Commission") ? Global.caseType : Global.activeSection;
        post.caseYear = Global.caseYear;
        post.caseType = Global.caseType;
        post.caseMonth = Global.caseMonth;
        post.caseNumber = Global.caseNumber;
        post.person = name;
        post.addressBlock = address;
        post.userID = Global.activeUser.id;
        post.suggestedSendDate = suggestedSendDatePicker.getText().equals("") ? null : new Date(NumberFormatService.convertMMDDYYYY(suggestedSendDatePicker.getText()));

        if (Global.activeSection.equals("CMDS")){
            post.historyDescription = CMDSdocToGenerate.LetterName == null ? "" : CMDSdocToGenerate.LetterName;
        } else {
            post.historyDescription = SMDSdocToGenerate.historyDescription == null ? SMDSdocToGenerate.description : SMDSdocToGenerate.historyDescription;
        }
        return PostalOut.insertPostalOut(post);
    }

    private void insertGeneratedAttachementEmail(int emailID, String docName) {
        if (emailID > 0) {
            EmailOutAttachment attach = new EmailOutAttachment();

            attach.emailOutID = emailID;
            attach.fileName = docName;
            attach.primaryAttachment = true;
            EmailOutAttachment.insertAttachment(attach);
        }
    }

    private void insertGeneratedAttachementPostal(int postalID, String docName) {
        if (postalID > 0) {
            PostalOutAttachment attach = new PostalOutAttachment();

            attach.PostalOutID = postalID;
            attach.fileName = docName;
            attach.primaryAttachment = true;
            PostalOutAttachment.insertAttachment(attach);
        }
    }

    private void insertExtraAttachmentsEmail(int emailID, List<Integer> postalIDList) {
        activityTableAttachmentProcess(emailID, postalIDList);

        switch (Global.activeSection) {
            case "REP":
            case "ULP":
            case "ORG":
            case "Hearings":
            case "Civil Service Commission":
            case "CMDS":
                additionalDocsTableAttachmentProcess(emailID, postalIDList);
                break;
            case "MED":
                additionalDocsTableMEDAttachmentProcess(emailID, postalIDList);
                break;
        }
    }

    private void activityTableAttachmentProcess(int emailID, List<Integer> postalIDList) {
        for (int i = 0; i < activityTable.getRowCount(); i++) {
            if (activityTable.getValueAt(i, 1).equals(true) && !activityTable.getValueAt(i, 3).toString().equals("")) {

                if (emailID > 0) {
                    EmailOutAttachment attach = new EmailOutAttachment();
                    attach.emailOutID = emailID;
                    attach.fileName = activityTable.getValueAt(i, 3).toString();
                    attach.primaryAttachment = false;
                    EmailOutAttachment.insertAttachment(attach);
                }

                for (int postalID : postalIDList) {
                    PostalOutAttachment attach = new PostalOutAttachment();
                    attach.PostalOutID = postalID;
                    attach.fileName = activityTable.getValueAt(i, 3).toString();
                    attach.primaryAttachment = false;
                    PostalOutAttachment.insertAttachment(attach);
                }
            }
        }
    }

    private void additionalDocsTableAttachmentProcess(int emailID, List<Integer> postalIDList) {
        for (int i = 0; i < additionalDocsTable.getRowCount(); i++) {
            if (additionalDocsTable.getValueAt(i, 1).equals(true) && !additionalDocsTable.getValueAt(i, 3).toString().equals("")) {
                String docName = "";

                if (additionalDocsTable.getValueAt(i, 3).toString().toLowerCase().endsWith(".docx")) {
                    SMDSDocuments additionalDoc = SMDSDocuments.findDocumentByID(Integer.valueOf(additionalDocsTable.getValueAt(i, 0).toString()));
                    docName = generateDocument.generateSMDSdocument(additionalDoc, 0, toParties, ccParties, null, null, true);
                } else {
                    docName = copyAttachmentToCaseFolder(
                            "Copy of " + additionalDocsTable.getValueAt(i, 2).toString() + " For Generation of " + SMDSdocToGenerate.description,
                            additionalDocsTable.getValueAt(i, 3).toString());
                }

                if (emailID > 0) {
                    EmailOutAttachment attach = new EmailOutAttachment();
                    attach.emailOutID = emailID;
                    attach.fileName = docName;
                    attach.primaryAttachment = false;
                    EmailOutAttachment.insertAttachment(attach);
                }

                for (int postalID : postalIDList) {
                    PostalOutAttachment attach = new PostalOutAttachment();
                    attach.PostalOutID = postalID;
                    attach.fileName = docName;
                    attach.primaryAttachment = false;
                    PostalOutAttachment.insertAttachment(attach);
                }
            }
        }
    }

    private void additionalDocsTableMEDAttachmentProcess(int emailID, List<Integer> postalIDList) {
        for (int i = 0; i < additionalDocsTable.getRowCount(); i++) {
            if (additionalDocsTable.getValueAt(i, 1).equals(true) && !additionalDocsTable.getValueAt(i, 3).toString().equals("")) {

                String destFileName = copyAttachmentToCaseFolder(
                        "Copy of " + additionalDocsTable.getValueAt(i, 2).toString() + " Bio For Generation of " + SMDSdocToGenerate.description,
                        additionalDocsTable.getValueAt(i, 3).toString());

                if (emailID > 0) {
                    EmailOutAttachment attach = new EmailOutAttachment();
                    attach.emailOutID = emailID;
                    attach.fileName = destFileName;
                    attach.primaryAttachment = false;
                    EmailOutAttachment.insertAttachment(attach);
                }

                for (int postalID : postalIDList) {
                    PostalOutAttachment attach = new PostalOutAttachment();
                    attach.PostalOutID = postalID;
                    attach.fileName = destFileName;
                    attach.primaryAttachment = false;
                    PostalOutAttachment.insertAttachment(attach);
                }
            }
        }
    }

    private String copyAttachmentToCaseFolder(String descriptionName, String fileName) {
        String docSourcePath = Global.templatePath
                + (Global.activeSection.equals("Civil Service Commission") ? Global.caseType : Global.activeSection) + File.separator + fileName;

        String docDestPath = "";

        if (Global.activeSection.equals("Civil Service Commission") || Global.activeSection.equals("ORG")){
            docDestPath = Global.activityPath + Global.caseType + File.separatorChar + Global.caseNumber + File.separatorChar;
        } else {
            docDestPath = Global.activityPath + Global.activeSection
                + File.separatorChar + Global.caseYear + File.separatorChar
                + NumberFormatService.generateFullCaseNumber() + File.separatorChar;
        }

        String destFileName = String.valueOf(new java.util.Date().getTime()) + "_" + fileName;

        try {
            Files.copy(Paths.get(docSourcePath), Paths.get(docDestPath + destFileName), StandardCopyOption.REPLACE_EXISTING);
            Activity.addActivty(descriptionName, destFileName);

        } catch (IOException ex) {
            SlackNotification.sendNotification(ex);
            return "";
        }
        return destFileName;
    }

    private void generateLetterButton() {
        if (checkActivityTable() && checkAdditionalDocsTable()) {
            if (Global.EmailSizeLimit >= attachmentSize) {
                processThread();
                loadingPanel.setVisible(true);
                InfoPanel.setVisible(false);
                jLayeredPane.moveToFront(loadingPanel);
                generateButton.setEnabled(false);
                cancelButton.setEnabled(false);
            } else {
                WebOptionPane.showMessageDialog(
                        Global.root,
                        "<html><center> Sorry, email size exceeds server limit unable to send.</center></html>",
                        "Error",
                        WebOptionPane.ERROR_MESSAGE
                );
            }
        } else {
            WebOptionPane.showMessageDialog(
                    Global.root,
                    "<html><center> Sorry, unable to locate files selected. <br><br>Unable to generate letter.</center></html>",
                    "Error",
                    WebOptionPane.ERROR_MESSAGE
            );
        }
    }

    private boolean checkActivityTable() {
        String path = Global.activityPath + File.separatorChar
                    + Global.activeSection + File.separatorChar
                    + Global.caseYear + File.separatorChar
                    + (Global.caseYear + "-" + Global.caseType + "-" + Global.caseMonth + "-" + Global.caseNumber)
                    + File.separatorChar;


        for (int i = 0; i < activityTable.getRowCount(); i++) {
            if (activityTable.getValueAt(i, 1).equals(true)) {
                File templateFile = new File(path + activityTable.getValueAt(i, 3));
                if (!templateFile.exists()){
                    return false;
                } else {
                    attachmentSize += templateFile.length();
                }
            }
        }
        return true;
    }

    private boolean checkAdditionalDocsTable() {
        String path = Global.templatePath
                + (Global.activeSection.equals("Civil Service Commission")
                ? Global.caseType : Global.activeSection) + File.separator;

        for (int i = 0; i < additionalDocsTable.getRowCount(); i++) {
            if (additionalDocsTable.getValueAt(i, 1).equals(true)) {
                if (!additionalDocsTable.getValueAt(i, 3).toString().equals("")){
                    File templateFile = new File(path + additionalDocsTable.getValueAt(i, 3));
                    if (!templateFile.exists()){
                        return false;
                    } else {
                        attachmentSize += templateFile.length();
                    }
                } else {
                    return false;
                }
            }
        }
        return true;
    }

    private void processThread() {
        Thread temp = new Thread(() -> {
            generateLetter();
            dispose();
        });
        temp.start();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane = new javax.swing.JLayeredPane();
        InfoPanel = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        documentLabel = new javax.swing.JLabel();
        suggestedSendDatePicker = new com.alee.extended.date.WebDateField();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        personTable = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        activityTable = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        additionalDocumentsLabel = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        additionalDocsTable = new javax.swing.JTable();
        cancelButton = new javax.swing.JButton();
        generateButton = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        loadingPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLayeredPane.setLayout(new javax.swing.OverlayLayout(jLayeredPane));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Letter");

        documentLabel.setText("Document: <<DOCUMENT NAME>>");

        suggestedSendDatePicker.setEditable(false);
        suggestedSendDatePicker.setCaretColor(new java.awt.Color(0, 0, 0));
        suggestedSendDatePicker.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        suggestedSendDatePicker.setDateFormat(Global.mmddyyyy);

        suggestedSendDatePicker.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            suggestedSendDatePicker.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    suggestedSendDatePickerMouseClicked(evt);
                }
            });

            jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            jLabel4.setText("Suggested Send Date:");

            personTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "ID", "To/CC", "Destination", "Party Type", "Name", "Email"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    true, true, true, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            personTable.setCellSelectionEnabled(true);
            personTable.getTableHeader().setReorderingAllowed(false);
            jScrollPane1.setViewportView(personTable);
            personTable.getColumnModel().getSelectionModel().setSelectionMode(javax.swing.ListSelectionModel.SINGLE_SELECTION);

            activityTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "ID", "Attach", "Document", "fileName"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
                };
                boolean[] canEdit = new boolean [] {
                    false, true, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jScrollPane2.setViewportView(activityTable);
            if (activityTable.getColumnModel().getColumnCount() > 0) {
                activityTable.getColumnModel().getColumn(0).setResizable(false);
                activityTable.getColumnModel().getColumn(1).setResizable(false);
                activityTable.getColumnModel().getColumn(2).setResizable(false);
                activityTable.getColumnModel().getColumn(3).setResizable(false);
            }

            jLabel5.setText("Case Documents:");

            additionalDocumentsLabel.setText("Additional Documents:");

            additionalDocsTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "ID", "Attach", "Document", "fileName"
                }
            ) {
                Class[] types = new Class [] {
                    java.lang.Object.class, java.lang.Boolean.class, java.lang.Object.class, java.lang.Object.class
                };
                boolean[] canEdit = new boolean [] {
                    false, true, false, false
                };

                public Class getColumnClass(int columnIndex) {
                    return types [columnIndex];
                }

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            jScrollPane3.setViewportView(additionalDocsTable);
            if (additionalDocsTable.getColumnModel().getColumnCount() > 0) {
                additionalDocsTable.getColumnModel().getColumn(0).setResizable(false);
                additionalDocsTable.getColumnModel().getColumn(1).setResizable(false);
                additionalDocsTable.getColumnModel().getColumn(2).setResizable(false);
                additionalDocsTable.getColumnModel().getColumn(3).setResizable(false);
            }

            cancelButton.setText("Cancel");
            cancelButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    cancelButtonActionPerformed(evt);
                }
            });

            generateButton.setText("Generate");
            generateButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    generateButtonActionPerformed(evt);
                }
            });

            jLabel2.setText("Send To:");

            javax.swing.GroupLayout InfoPanelLayout = new javax.swing.GroupLayout(InfoPanel);
            InfoPanel.setLayout(InfoPanelLayout);
            InfoPanelLayout.setHorizontalGroup(
                InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(InfoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(cancelButton)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 692, Short.MAX_VALUE)
                    .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, InfoPanelLayout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(77, 77, 77)
                                .addComponent(additionalDocumentsLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 415, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(2, 2, 2))
                            .addGroup(InfoPanelLayout.createSequentialGroup()
                                .addComponent(documentLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 544, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(45, 45, 45)
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(suggestedSendDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 159, Short.MAX_VALUE))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, InfoPanelLayout.createSequentialGroup()
                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 398, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 417, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addContainerGap()))
            );

            InfoPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, generateButton});

            InfoPanelLayout.setVerticalGroup(
                InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InfoPanelLayout.createSequentialGroup()
                    .addContainerGap(603, Short.MAX_VALUE)
                    .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(generateButton))
                    .addContainerGap())
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(documentLabel)
                            .addComponent(suggestedSendDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 189, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20)
                        .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(additionalDocumentsLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel5))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
            );

            InfoPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {documentLabel, jLabel4, suggestedSendDatePicker});

            jLayeredPane.add(InfoPanel);

            jLabel8.setBackground(new java.awt.Color(255, 255, 255));
            jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

            jLabel3.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
            jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel3.setText("Generating Document");

            jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
            jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel9.setText("Please Wait...");

            javax.swing.GroupLayout loadingPanelLayout = new javax.swing.GroupLayout(loadingPanel);
            loadingPanel.setLayout(loadingPanelLayout);
            loadingPanelLayout.setHorizontalGroup(
                loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE)
                .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 912, Short.MAX_VALUE))
            );
            loadingPanelLayout.setVerticalGroup(
                loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(loadingPanelLayout.createSequentialGroup()
                    .addGap(125, 125, 125)
                    .addComponent(jLabel3)
                    .addGap(18, 18, 18)
                    .addComponent(jLabel9)
                    .addContainerGap(448, Short.MAX_VALUE))
                .addGroup(loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 637, Short.MAX_VALUE))
            );

            jLayeredPane.add(loadingPanel);

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
            getContentPane().setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPane)
                    .addContainerGap())
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPane)
                    .addContainerGap())
            );

            pack();
        }// </editor-fold>//GEN-END:initComponents

    private void generateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_generateButtonActionPerformed
        generateLetterButton();
    }//GEN-LAST:event_generateButtonActionPerformed

    private void cancelButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_cancelButtonActionPerformed
        dispose();
    }//GEN-LAST:event_cancelButtonActionPerformed

    private void suggestedSendDatePickerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_suggestedSendDatePickerMouseClicked
        clearDate(suggestedSendDatePicker, evt);
    }//GEN-LAST:event_suggestedSendDatePickerMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel InfoPanel;
    private javax.swing.JTable activityTable;
    private javax.swing.JTable additionalDocsTable;
    private javax.swing.JLabel additionalDocumentsLabel;
    private javax.swing.JButton cancelButton;
    private javax.swing.JLabel documentLabel;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JPanel loadingPanel;
    private javax.swing.JTable personTable;
    private com.alee.extended.date.WebDateField suggestedSendDatePicker;
    // End of variables declaration//GEN-END:variables
}
