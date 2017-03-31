/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import parker.serb.Global;
import parker.serb.sql.CMDSDocuments;
import parker.serb.sql.CaseParty;
import parker.serb.sql.County;

/**
 *
 * @author User
 */
public class questionsCMDSPanel extends JDialog {

    private JTextField hearingLengthTextField, actionAppealedOtherTextField, classificationTitleTextField,
            barganingUnitTextField, classificationNumberTextField, probationaryPeriodTextField,
            hearingTimeTextField, casePendingResolutionTextField, codeSectionFillInTextField,
            documentNameTextField, infoRedactedTextField, whoRedactedTextField, redactedTitleTextField,
            purposeOfExtensionTextField, filingPartyTextField, addressBlock1TextField,
            addressBlock2TextField, addressBlock3TextField, addressBlock4TextField;

    private WebDateField responseDueWebDateField, appellantAppointedWebDateField, hearingDateWebDateField,
            hearingDateServedWebDateField, firstLetterSentWebDateField, stayDateWebDateField,
            lastUpdateWebDateField, matterContinuedWebDateField, settleMentDueWebDateField, dateFiledWebDateField,
            datePOSentWebDateField, dateRequestedWebDateField, dateRequestedExtensionWebDateField;

    private JComboBox genderAppellantComboBox, memorandumContraComboBox, codeSelectionComboBox, countyComboBox,
            respondingPartyComboBox, requestingPartyComboBox, depositionComboBox, genderRepresentativeComboBox,
            appealTypeComboBox, appealType2ComboBox, appealTypeUFComboBox, appealTypeLSComboBox,
            RequestingPartyContinuanceComboBox, RequestingPartyTimeExtensionComboBox, actionAppealedComboBox,
            RequestingPartyConsolidationComboBox, purposeOfExtensionComboBox, filingPartyComboBox, addressBlockComboBox;

    public questionsCMDSModel answers = new questionsCMDSModel();

    int panelWidth = 325;
    int panelHeight = 100;

    public questionsCMDSPanel(java.awt.Frame parent, boolean modal, CMDSDocuments template, int count) {
        super(parent, modal);

        //Set Padding for Button/Labels
        Border border = null;
        Border margin = null;
        if (template.AddressBlock){
            panelHeight = 175;
        }

        //Set Top Panel
        final JPanel topPanel = new JPanel();
        JLabel titleLabel = new JLabel("Questions");
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 14));
        border = titleLabel.getBorder();
        margin = new EmptyBorder(10,10,10,10);
        titleLabel.setBorder(new CompoundBorder(border, margin));
        topPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel buttonPanel = new JPanel(); //Creating the orderList JPanel
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        final JButton addButton = new JButton("Submit");
        addButton.setPreferredSize(new Dimension(100, 25));
        addButton.setMargin(new Insets(1, 1, 1, 1));
        buttonPanel.add(addButton);

        //Set QuestionPanel
        final JPanel questionsPanel = new JPanel(); //Creating the orderList JPanel
        questionsPanel.setLayout(new GridLayout((int) Math.ceil((Math.sqrt(count))), (int) Math.ceil((Math.sqrt(count)))));
        if (template.LetterName != null) {
            if (template.LetterName.equals("Appeal Notice")) {
                questionsPanel.add(HearingLengthPanel());    //01
            }
        }
        if (template.ResponseDue) {
            questionsPanel.add(ResponseDuePanel());          //02
        }
        if (template.Gender) {
            questionsPanel.add(GenderAppellantPanel());      //03
        }
        if (template.ActionAppealed) {
            questionsPanel.add(ActionAppealedPanelOther());  //04
        }
        if (template.MemorandumContra) {
            questionsPanel.add(MemorandumContraPanel());     //05
        }
        if (template.ClassificationTitle) {
            questionsPanel.add(ClassificationTitlePanel());  //06
        }
        if (template.BarginingUnit) {
            questionsPanel.add(BargainingUnitPanel());       //07
        }
        if (template.ClassificationNumber) {
            questionsPanel.add(ClassificationNumberPanel()); //08
        }
        if (template.AppelantAppointed) {
            questionsPanel.add(AppellantAppointedPanel());   //09
        }
        if (template.ProbitionaryPeriod) {
            questionsPanel.add(ProbationaryPeriodPanel());   //10
        }
        if (template.HearingDate) {
            questionsPanel.add(HearingDatePanel());          //11
        }
        if (template.HearingTime) {
            questionsPanel.add(HearingTimePanel());          //12
        }
        if (template.HearingServed) {
            questionsPanel.add(HearingServedPanel());        //13
        }
        if (template.AddressBlock) {
            questionsPanel.add(AddressBlockPanel());         //14
        }
        if (template.FirstLetterSent) {
            questionsPanel.add(FirstLetterSentPanel());      //15
        }
        if (template.CodeSection) {
            questionsPanel.add(CodeSelectionPanel());        //16
        }
        if (template.CountyName) {
            questionsPanel.add(CountyNamePanel());           //17
        }
        if (template.StayDate) {
            questionsPanel.add(StayDatePanel());             //18
        }
        if (template.CasePendingResolution) {
            questionsPanel.add(CasePendingResolutionPanel());//19
        }
        if (template.LastUpdate) {
            questionsPanel.add(LastUpdatePanel());           //20
        }
        if (template.MatterContinued) {
            questionsPanel.add(MatterContinuedPanel());      //21
        }
        if (template.SettlementDue) {
            questionsPanel.add(SettlementDuePanel());        //22
        }
        if (template.FilingParty) {
            questionsPanel.add(FilingPartyPanel());          //23
        }
        if (template.RespondingParty) {
            questionsPanel.add(RespondingPartyPanel());      //24
        }
        if (template.RequestingParty) {
            questionsPanel.add(RequestingPartyPanel());      //25
        }
        if (template.Deposition) {
            questionsPanel.add(DepositionPanel());           //26
        }
        if (template.RepHimOrHer) {
            questionsPanel.add(GenderRepresentativePanel()); //27
        }
        if (template.CodeSectionFillIn) {
            questionsPanel.add(CodeSectionFillinPanel());    //28
        }
        if (template.DocumentName) {
            questionsPanel.add(DocumentNamePanel());         //29
        }
        if (template.DateFiled) {
            questionsPanel.add(DateFiledPanel());            //30
        }
        if (template.InfoRedacted) {
            questionsPanel.add(InfoRedactedPanel());         //31
        }
        if (template.RedactorName) {
            questionsPanel.add(RedactorNamePanel());         //32
        }
        if (template.RedactorTitle) {
            questionsPanel.add(RedactorTitlePanel());        //33
        }
        if (template.DatePOSent) {
            questionsPanel.add(DatePOSentPanel());           //34
        }
        if (template.AppealType) {
            questionsPanel.add(AppealTypePanel());           //35
        }
        if (template.AppealType2) {
            questionsPanel.add(AppealType2Panel());          //36
        }
        if (template.AppealTypeUF) {
            questionsPanel.add(AppealTypeUFPanel());         //37
        }
        if (template.AppealTypeLS) {
            questionsPanel.add(AppealTypeLSPanel());         //38
        }
        if (template.RequestingPartyC && template.HearingTime) {
            questionsPanel.add(RequestingPartyContinuancePanel());   //39
        }
        if (template.DateRequested && template.HearingTime) {
            questionsPanel.add(DateRequestedPanel());                //40
        }
        if (template.RequestingPartyC && template.PurposeOfExtension) {
            questionsPanel.add(RequestingPartyCTimeExtensionPanel());//41
        }
        if (template.DateRequested && template.PurposeOfExtension) {
            questionsPanel.add(DateRequestedTimeExtensionPanel());   //42
        }
        if (template.PurposeOfExtension) {
            questionsPanel.add(PurposeOfExtension());                //43
        }
        if (template.RequestingPartyC && !template.PurposeOfExtension && !template.HearingTime) {
            questionsPanel.add(RequestingPartyCConsolidationPanel());//44
        }

        this.setLayout(new BorderLayout());
        this.add(topPanel, BorderLayout.NORTH);
        this.add(questionsPanel, BorderLayout.CENTER);
        this.add(buttonPanel, BorderLayout.SOUTH);

        addButton.addActionListener((java.awt.event.ActionEvent evt) -> {
            SubmitButton();
        });

        this.setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        this.pack();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    private void SubmitButton() {
        this.setVisible(false);

        answers.setHearingLength(hearingLengthTextField == null ? "" : hearingLengthTextField.getText());
        answers.setResponseDueDate(responseDueWebDateField == null ? "" : responseDueWebDateField.getText());
        answers.setGenderAppellant(genderAppellantComboBox == null ? "" : genderAppellantComboBox.getSelectedItem().toString().toLowerCase());
        answers.setActionAppealed(actionAppealedComboBox == null ? "" : actionAppealedComboBox.getSelectedItem().toString());

        if (!answers.getActionAppealed().equals("Other")){
            answers.setActionAppealed(actionAppealedOtherTextField == null ? "" : actionAppealedOtherTextField.getText());
        }

        if (actionAppealedComboBox != null){
                answers.setMemorandumContra(
                    memorandumContraComboBox == null ? "" : (memorandumContraComboBox.getSelectedItem().toString().equals("Did Not file")
                        ? "reduction" : memorandumContraComboBox.getSelectedItem().toString().toLowerCase()));
        } else {
            answers.setMemorandumContra("");
        }
        answers.setClassificationTitle(classificationTitleTextField == null ? "" : classificationTitleTextField.getText());
        answers.setBargainingUnit(barganingUnitTextField == null ? "" : barganingUnitTextField.getText());
        answers.setClassificationNumber(classificationNumberTextField == null ? "" : classificationNumberTextField.getText());
        answers.setAppellantAppointed(appellantAppointedWebDateField == null ? ""
                : (appellantAppointedWebDateField.getText().equals("") ? "" : Global.MMMMdyyyy.format(appellantAppointedWebDateField.getDate())));
        answers.setProbationaryPeriod(probationaryPeriodTextField == null ? "" : probationaryPeriodTextField.getText());
        answers.setHearingDate(hearingDateWebDateField == null ? "" : hearingDateWebDateField.getText());
        answers.setHearingTime(hearingTimeTextField == null ? "" : hearingTimeTextField.getText());
        answers.setHearingServed(hearingDateServedWebDateField == null ? "" : hearingDateServedWebDateField.getText());
        answers.setFirstLetterSent(firstLetterSentWebDateField == null ? "" : firstLetterSentWebDateField.getText());
        answers.setCodeSelection(codeSelectionComboBox == null ? "" : codeSelectionComboBox.getSelectedItem().toString().toLowerCase());
        answers.setCountyName(countyComboBox == null ? "" : countyComboBox.getSelectedItem().toString());
        answers.setStayDate(stayDateWebDateField == null ? "" : stayDateWebDateField.getText());
        answers.setCasePendingResolution(casePendingResolutionTextField == null ? "" : casePendingResolutionTextField.getText());
        answers.setLastUpdate(lastUpdateWebDateField == null ? "" : lastUpdateWebDateField.getText());
        answers.setMatterContinued(matterContinuedWebDateField == null ? "" : matterContinuedWebDateField.getText());
        answers.setSettlementDue(settleMentDueWebDateField == null ? "" : settleMentDueWebDateField.getText());
        answers.setCodeSectionFillIn(codeSectionFillInTextField == null ? "" : codeSectionFillInTextField.getText());
        answers.setDocumentName(documentNameTextField == null ? "" : documentNameTextField.getText());
        answers.setDateFiled(dateFiledWebDateField == null ? "" : dateFiledWebDateField.getText());
        answers.setInfoRedacted(infoRedactedTextField == null ? "" : infoRedactedTextField.getText());
        answers.setRedactorName(whoRedactedTextField == null ? "" : whoRedactedTextField.getText());
        answers.setRedactorTitle(redactedTitleTextField == null ? "" : redactedTitleTextField.getText());
        answers.setDatePOSent(datePOSentWebDateField == null ? ""
                : (datePOSentWebDateField.getText().equals("") ? "" : Global.MMMMdyyyy.format(datePOSentWebDateField.getDate())));
        answers.setRespondingParty(respondingPartyComboBox == null ? "" : respondingPartyComboBox.getSelectedItem().toString());
        answers.setRequestingParty(requestingPartyComboBox == null ? "" : requestingPartyComboBox.getSelectedItem().toString());
        answers.setDeposition(depositionComboBox == null ? "" : depositionComboBox.getSelectedItem().toString());
        answers.setGenderRep(genderRepresentativeComboBox == null ? "" : genderRepresentativeComboBox.getSelectedItem().toString().toLowerCase());
        answers.setAppealTypeLS(appealTypeLSComboBox == null ? "" : appealTypeLSComboBox.getSelectedItem().toString().toLowerCase());
        answers.setRequestingPartyContinuance(RequestingPartyContinuanceComboBox == null ? "" : RequestingPartyContinuanceComboBox.getSelectedItem().toString().toLowerCase());
        answers.setDateRequestedContinuance(dateRequestedWebDateField == null ? "" : dateRequestedWebDateField.getText());
        answers.setRequestingPartyExtension(RequestingPartyTimeExtensionComboBox == null ? "" : RequestingPartyTimeExtensionComboBox.getSelectedItem().toString().toLowerCase());
        answers.setDateRequestedExtension(dateRequestedExtensionWebDateField == null ? "" : dateRequestedExtensionWebDateField.getText());
        answers.setRequestingPartyConsolidation(RequestingPartyConsolidationComboBox == null ? "" : RequestingPartyConsolidationComboBox.getSelectedItem().toString().toLowerCase());

        if (addressBlockComboBox != null) {
            answers.setAddressBlockName(addressBlockComboBox.getSelectedItem().toString().equals("Other") ? addressBlock1TextField.getText() : filingPartyComboBox.getSelectedItem().toString());
        } else {
            answers.setAddressBlockName("");
        }

        if (addressBlockComboBox != null) {
            answers.setAddressBlockBlock(addressBlockComboBox.getSelectedItem().toString().equals("Other")
                    ? (addressBlock2TextField.getText().equals("") ? "" : addressBlock2TextField.getText() + System.lineSeparator())
                    + (addressBlock3TextField.getText().equals("") ? "" : addressBlock3TextField.getText() + System.lineSeparator())
                    + addressBlock4TextField.getText() : "");
        } else {
            answers.setAddressBlockBlock("");
        }

        if (filingPartyComboBox != null) {
            answers.setFilingParty(filingPartyComboBox.getSelectedItem().toString().equals("Other")
                    ? filingPartyTextField.getText() : filingPartyComboBox.getSelectedItem().toString().toLowerCase());
        } else {
            answers.setFilingParty("");
        }

        if (appealTypeComboBox != null) {
            switch (appealTypeComboBox.getSelectedItem().toString().trim().toLowerCase()) {
                case "no order reduction":
                    answers.setAppealType(readFile("PO-MIS-DisparateTreatmentDenied-P1"));
                    break;
                default:
                    answers.setAppealType(readFile("PO-MIS-DisparateTreatmentDenied-P2"));
                    break;
            }
        } else {
            answers.setAppealType("");
        }

        if (purposeOfExtensionComboBox != null) {
            switch (purposeOfExtensionComboBox.getSelectedItem().toString().trim()) {
                case "Other":
                    answers.setPurposeofExtension(purposeOfExtensionTextField.getText().trim());
                    break;
                case "File Objections to an R&R":
                    answers.setPurposeofExtension("file objections to a Report and Recommendation issued");
                    break;
                case "File a Response to Objections to the R&R":
                    answers.setPurposeofExtension("file a response to objections to a Report and Recommendation issued");
                    break;
                case "Respond to a Procedural Order":
                    answers.setPurposeofExtension("respond to a Procedural Order issued");
                    break;
                case "Respond to a Letter":
                    answers.setPurposeofExtension("respond to a letter issued");
                    break;
                case "File a Memorandum Contra":
                    answers.setPurposeofExtension("file a memorandum contra");
                    break;
                default:
                    break;
            }
        } else {
            answers.setPurposeofExtension("");
        }

        String[] AppealType2 = new String[2];
        if (appealType2ComboBox != null) {
            switch (appealType2ComboBox.getSelectedItem().toString().trim().toLowerCase()) {
                case "suspension of three days or less":
                    AppealType2[0] = "suspension of three days or less";
                    AppealType2[1] = readFile("RR-LOJ-NonappealableAction-P1");
                    break;
                case "fine of three days or less":
                    AppealType2[0] = "fine of three days or less";
                    AppealType2[1] = readFile("RR-LOJ-NonappealableAction-P2");
                    break;
                case "written reprimand":
                    AppealType2[0] = "written reprimand";
                    AppealType2[1] = readFile("RR-LOJ-NonappealableAction-P3");
                    break;
                case "annual performance evaluation":
                    AppealType2[0] = "annual performance evaluation";
                    AppealType2[1] = readFile("RR-LOJ-NonappealableAction-P4");
                    break;
                default:
                    break;
            }
        } else {
            AppealType2[0] = "";
            AppealType2[1] = "";
        }
        answers.setAppealType2(AppealType2);

        String AppealTypeUF[] = new String[2];
        if (appealTypeUFComboBox != null) {
            switch (appealTypeUFComboBox.getSelectedItem().toString().trim().toLowerCase()) {
                case "abolishment":
                    AppealTypeUF[0] = "abolishment";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P1");
                    break;
                case "alleged reduction":
                    AppealTypeUF[0] = "alleged reduction";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P2");
                    break;
                case "denial of reinstatement":
                    AppealTypeUF[0] = "denial of reinstatement";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P3");
                    break;
                case "disciplinary reduction":
                    AppealTypeUF[0] = "disciplinary reduction";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P4");
                    break;
                case "displacement":
                    AppealTypeUF[0] = "displacement";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P5");
                    break;
                case "ids":
                    AppealTypeUF[0] = "IDS";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P6");
                    break;
                case "investigation":
                    AppealTypeUF[0] = "investigation";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P7");
                    break;
                case "layoff":
                    AppealTypeUF[0] = "layoff";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P8");
                    break;
                case "non-disciplinary reduction":
                    AppealTypeUF[0] = "non-disciplinary reduction";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P9");
                    break;
                case "osha":
                    AppealTypeUF[0] = "OSHA";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P10");
                    break;
                case "reclassification":
                    AppealTypeUF[0] = "reclassification";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P11");
                    break;
                case "removal":
                    AppealTypeUF[0] = "removal";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P12");
                    break;
                case "suspension":
                    AppealTypeUF[0] = "suspension";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P13");
                    break;
                case "transfer":
                    AppealTypeUF[0] = "transfer";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P14");
                    break;
                case "whistleblower":
                    AppealTypeUF[0] = "whistleblower";
                    AppealTypeUF[1] = readFile("RR-LOJ-UntimelyFiling-P15");
                    break;
                default:
                    break;
            }
        } else {
            AppealTypeUF[0] = "";
            AppealTypeUF[1] = "";
        }
        answers.setAppealTypeUF(AppealTypeUF);
    }

    private JPanel HearingLengthPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        hearingLengthTextField = new JTextField();
        hearingLengthTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Please Enter the Length of the Hearing");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Length (minutes): ");
        mainPanel.add(actionLabel);
        mainPanel.add(hearingLengthTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel ResponseDuePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        responseDueWebDateField = new WebDateField();
        responseDueWebDateField.setPreferredSize(new Dimension(150, 25));
        responseDueWebDateField.setDateFormat(Global.mmddyyyy);
        responseDueWebDateField.setEditable(false);
        responseDueWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });

        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Please Enter the Response Due Date");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Response Due: ");
        mainPanel.add(actionLabel);
        mainPanel.add(responseDueWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel GenderAppellantPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        genderAppellantComboBox = new JComboBox();
        genderAppellantComboBox.setPreferredSize(new Dimension(100, 25));
        genderAppellantComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Male", "Female" }));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Please Select Appellent's Gender");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Gender: ");
        mainPanel.add(actionLabel);
        mainPanel.add(genderAppellantComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

//    private JPanel ActionAppealedPanel() {
//        JPanel panel = new JPanel();
//        JLabel titleLabel = new JLabel();
//        JLabel actionLabel = new JLabel();
//        actionAppealedComboBox = new JComboBox();
//        actionAppealedComboBox.setPreferredSize(new Dimension(100, 25));
//        actionAppealedComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Removal", "Reduction", "Suspension", "Other" }));
//
//        panel.setBorder(BorderFactory.createEtchedBorder());
//        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));
//
//        //Set Top Panel
//        final JPanel headerPanel = new JPanel();
//        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
//        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
//        titleLabel.setText("What Type of Action Was Appealed?");
//        headerPanel.add(titleLabel);
//
//        //Set Bottom Panel (Button Bar)
//        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
//        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
//        actionLabel.setText("Type: ");
//        mainPanel.add(actionLabel);
//        mainPanel.add(actionAppealedComboBox);
//
//        panel.setLayout(new BorderLayout());
//        panel.add(headerPanel, BorderLayout.NORTH);
//        panel.add(mainPanel, BorderLayout.CENTER);
//
//        return panel;
//    }

    private JPanel ActionAppealedPanelOther() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel(), actionLabel = new JLabel(), action2Label = new JLabel();
        actionAppealedOtherTextField = new JTextField();
        actionAppealedOtherTextField.setPreferredSize(new Dimension(150, 25));
        actionAppealedComboBox = new JComboBox();
        actionAppealedComboBox.setPreferredSize(new Dimension(150, 25));
        actionAppealedComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Removal", "Reduction", "Suspension", "Other" }));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What is the purpose of the Extension?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        final JPanel mainSub1Panel = new JPanel(); //Creating the orderList JPanel
        final JPanel mainSub2Panel = new JPanel(); //Creating the orderList JPanel

        mainSub1Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainSub1Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        actionLabel.setText("Type: ");
        mainSub1Panel.add(actionLabel);
        mainSub1Panel.add(actionAppealedComboBox);

        mainSub2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainSub2Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        action2Label.setText("Other: ");
        mainSub2Panel.add(action2Label);
        mainSub2Panel.add(actionAppealedOtherTextField);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mainSub1Panel);
        mainPanel.add(mainSub2Panel);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel MemorandumContraPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        memorandumContraComboBox = new JComboBox();
        memorandumContraComboBox.setPreferredSize(new Dimension(100, 25));
        memorandumContraComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Filed", "Did Not File" }));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Did Appellant File a Memorandum Contra?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Memorandum: ");
        mainPanel.add(actionLabel);
        mainPanel.add(memorandumContraComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel ClassificationTitlePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        classificationTitleTextField = new JTextField();
        classificationTitleTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What Was Appellant's Classification Title?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Title: ");
        mainPanel.add(actionLabel);
        mainPanel.add(classificationTitleTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel BargainingUnitPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        barganingUnitTextField = new JTextField();
        barganingUnitTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What Bargaining Unit Represented Appellant?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Bargaining Unit: ");
        mainPanel.add(actionLabel);
        mainPanel.add(barganingUnitTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel ClassificationNumberPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        classificationNumberTextField = new JTextField();
        classificationNumberTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What is Appellant's Classification Number?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Classification Number: ");
        mainPanel.add(actionLabel);
        mainPanel.add(classificationNumberTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel AppellantAppointedPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel title2Label = new JLabel();
        JLabel actionLabel = new JLabel();
        appellantAppointedWebDateField = new WebDateField();
        appellantAppointedWebDateField.setPreferredSize(new Dimension(150, 25));
        appellantAppointedWebDateField.setDateFormat(Global.mmddyyyy);
        appellantAppointedWebDateField.setEditable(false);
        appellantAppointedWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("What Date was Appellant Appointed");
        title2Label.setFont(new java.awt.Font("Tahoma", 1, 12));
        title2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2Label.setText("to Their Classification?");
        headerPanel.add(titleLabel);
        headerPanel.add(title2Label);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Classification Date: ");
        mainPanel.add(actionLabel);
        mainPanel.add(appellantAppointedWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel ProbationaryPeriodPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel title2Label = new JLabel();
        JLabel actionLabel = new JLabel();
        probationaryPeriodTextField = new JTextField();
        probationaryPeriodTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("What Was The Length of");
        title2Label.setFont(new java.awt.Font("Tahoma", 1, 12));
        title2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2Label.setText("Appellant's Probationary Period?");
        headerPanel.add(titleLabel);
        headerPanel.add(title2Label);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Probationary Period: ");
        mainPanel.add(actionLabel);
        mainPanel.add(probationaryPeriodTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel HearingDatePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        hearingDateWebDateField = new WebDateField();
        hearingDateWebDateField.setPreferredSize(new Dimension(150, 25));
        hearingDateWebDateField.setDateFormat(Global.mmddyyyy);
        hearingDateWebDateField.setEditable(false);
        hearingDateWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What Date was the Hearing Scheduled?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Hearing Date: ");
        mainPanel.add(actionLabel);
        mainPanel.add(hearingDateWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel HearingTimePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        hearingTimeTextField = new JTextField();
        hearingTimeTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("When Does the Hearing Start?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Hearing Time: ");
        mainPanel.add(actionLabel);
        mainPanel.add(hearingTimeTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel HearingServedPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        hearingDateServedWebDateField = new WebDateField();
        hearingDateServedWebDateField.setPreferredSize(new Dimension(150, 25));
        hearingDateServedWebDateField.setDateFormat(Global.mmddyyyy);
        hearingDateServedWebDateField.setEditable(false);
        hearingDateServedWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 10));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What Date was the Notice of Hearing Served on Appellant?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Hearing Date: ");
        mainPanel.add(actionLabel);
        mainPanel.add(hearingDateServedWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel AddressBlockPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel(), actionLabel = new JLabel(), action2Label = new JLabel(),
                action3Label = new JLabel(), action4Label = new JLabel(), action5Label = new JLabel();
        actionLabel.setPreferredSize(new Dimension(90, 25));
        actionLabel.setHorizontalAlignment(SwingConstants.TRAILING);

        action2Label.setPreferredSize(new Dimension(90, 25));
        action2Label.setHorizontalAlignment(SwingConstants.TRAILING);
        addressBlock1TextField = new JTextField();
        addressBlock1TextField.setPreferredSize(new Dimension(175, 25));

        action3Label.setPreferredSize(new Dimension(90, 25));
        action3Label.setHorizontalAlignment(SwingConstants.TRAILING);
        addressBlock2TextField = new JTextField();
        addressBlock2TextField.setPreferredSize(new Dimension(175, 25));

        action4Label.setPreferredSize(new Dimension(90, 25));
        action4Label.setHorizontalAlignment(SwingConstants.TRAILING);
        addressBlock3TextField = new JTextField();
        addressBlock3TextField.setPreferredSize(new Dimension(175, 25));

        action5Label.setPreferredSize(new Dimension(90, 25));
        action5Label.setHorizontalAlignment(SwingConstants.TRAILING);
        addressBlock4TextField = new JTextField();
        addressBlock4TextField.setPreferredSize(new Dimension(175, 25));
        addressBlockComboBox = new JComboBox();
        addressBlockComboBox.setPreferredSize(new Dimension(175, 25));
        List partyType = CaseParty.loadPartiesByCase();
        addressBlockComboBox.addItem("");
        for (int i = 0; i < partyType.size(); i++) {
            CaseParty party = (CaseParty) partyType.get(i);
            addressBlockComboBox.addItem(party.caseRelation);
        }
        addressBlockComboBox.addItem("Other");

        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("To Whom is the Letter Being Sent?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel();
        final JPanel mainSub1Panel = new JPanel();
        final JPanel mainSub2Panel = new JPanel();
        final JPanel mainSub3Panel = new JPanel();
        final JPanel mainSub4Panel = new JPanel();
        final JPanel mainSub5Panel = new JPanel();

        FlowLayout flow = new FlowLayout(FlowLayout.CENTER);
        flow.setVgap(0);

        mainSub1Panel.setLayout(flow);
        mainSub1Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        actionLabel.setText("Send To: ");
        mainSub1Panel.add(actionLabel);
        mainSub1Panel.add(addressBlockComboBox);

        mainSub2Panel.setLayout(flow);
        mainSub2Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        action2Label.setText("Name: ");
        mainSub2Panel.add(action2Label);
        mainSub2Panel.add(addressBlock1TextField);

        mainSub3Panel.setLayout(flow);
        mainSub3Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        action3Label.setText("Address 1: ");
        mainSub3Panel.add(action3Label);
        mainSub3Panel.add(addressBlock2TextField);

        mainSub4Panel.setLayout(flow);
        mainSub4Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        action4Label.setText("Address 2: ");
        mainSub4Panel.add(action4Label);
        mainSub4Panel.add(addressBlock3TextField);

        mainSub5Panel.setLayout(flow);
        mainSub5Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        action5Label.setText("City/State/Zip: ");
        mainSub5Panel.add(action5Label);
        mainSub5Panel.add(addressBlock4TextField);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mainSub1Panel);
        mainPanel.add(mainSub2Panel);
        mainPanel.add(mainSub3Panel);
        mainPanel.add(mainSub4Panel);
        mainPanel.add(mainSub5Panel);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel FirstLetterSentPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel title2Label = new JLabel();
        JLabel actionLabel = new JLabel();
        firstLetterSentWebDateField = new WebDateField();
        firstLetterSentWebDateField.setPreferredSize(new Dimension(150, 25));
        firstLetterSentWebDateField.setDateFormat(Global.mmddyyyy);
        firstLetterSentWebDateField.setEditable(false);
        firstLetterSentWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("What Date was the First Letter");
        title2Label.setFont(new java.awt.Font("Tahoma", 1, 12));
        title2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2Label.setText("to the Appelle Sent?");
        headerPanel.add(titleLabel);
        headerPanel.add(title2Label);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Date Sent: ");
        mainPanel.add(actionLabel);
        mainPanel.add(firstLetterSentWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel CodeSelectionPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel title2Label = new JLabel();
        JLabel actionLabel = new JLabel();
        codeSelectionComboBox = new JComboBox();
        codeSelectionComboBox.setPreferredSize(new Dimension(100, 25));
        codeSelectionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "124.40", "124.56" }));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("Which Code Section Provides Jurisdiction");
        title2Label.setFont(new java.awt.Font("Tahoma", 1, 12));
        title2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2Label.setText("Over This Request for Investigation?");
        headerPanel.add(titleLabel);
        headerPanel.add(title2Label);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Section: ");
        mainPanel.add(actionLabel);
        mainPanel.add(codeSelectionComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel CountyNamePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        countyComboBox = new JComboBox();
        countyComboBox.setPreferredSize(new Dimension(150, 25));
        countyComboBox.setModel(new javax.swing.DefaultComboBoxModel());

        List counties = County.loadCountyListByState("OH");
        countyComboBox.addItem("");
        for (int i = 0; i < counties.size(); i++) {
            County county = (County) counties.get(i);
            countyComboBox.addItem(county.countyName);
        }

        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What County is the Appelle Located In?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("County: ");
        mainPanel.add(actionLabel);
        mainPanel.add(countyComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel StayDatePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        stayDateWebDateField = new WebDateField();
        stayDateWebDateField.setPreferredSize(new Dimension(150, 25));
        stayDateWebDateField.setDateFormat(Global.mmddyyyy);
        stayDateWebDateField.setEditable(false);
        stayDateWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Enter Date of Stay");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Date of Stay: ");
        mainPanel.add(actionLabel);
        mainPanel.add(stayDateWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel CasePendingResolutionPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel title2Label = new JLabel();
        JLabel actionLabel = new JLabel();
        casePendingResolutionTextField = new JTextField();
        casePendingResolutionTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("Description of Reason for Stay");
        title2Label.setFont(new java.awt.Font("Tahoma", 0, 11));
        title2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2Label.setText("i.e. Pending resolution of WHAT");
        headerPanel.add(titleLabel);
        headerPanel.add(title2Label);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Description: ");
        mainPanel.add(actionLabel);
        mainPanel.add(casePendingResolutionTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel LastUpdatePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        lastUpdateWebDateField = new WebDateField();
        lastUpdateWebDateField.setPreferredSize(new Dimension(150, 25));
        lastUpdateWebDateField.setDateFormat(Global.mmddyyyy);
        lastUpdateWebDateField.setEditable(false);
        lastUpdateWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Please Enter the Date of the Last Update");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Date of Last Update: ");
        mainPanel.add(actionLabel);
        mainPanel.add(lastUpdateWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel MatterContinuedPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        matterContinuedWebDateField = new WebDateField();
        matterContinuedWebDateField.setPreferredSize(new Dimension(150, 25));
        matterContinuedWebDateField.setDateFormat(Global.mmddyyyy);
        matterContinuedWebDateField.setEditable(false);
        matterContinuedWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("On What Date was the Matter Continued?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Date: ");
        mainPanel.add(actionLabel);
        mainPanel.add(matterContinuedWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel SettlementDuePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        settleMentDueWebDateField = new WebDateField();
        settleMentDueWebDateField.setPreferredSize(new Dimension(150, 25));
        settleMentDueWebDateField.setDateFormat(Global.mmddyyyy);
        settleMentDueWebDateField.setEditable(false);
        settleMentDueWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Enter the Date of the Last Update");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Date: ");
        mainPanel.add(actionLabel);
        mainPanel.add(settleMentDueWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel FilingPartyPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel(), actionLabel = new JLabel(), action2Label = new JLabel();
        filingPartyTextField = new JTextField();
        filingPartyTextField.setPreferredSize(new Dimension(225, 25));
        filingPartyComboBox = new JComboBox();
        filingPartyComboBox.setPreferredSize(new Dimension(225, 25));
        List partyType = CaseParty.loadPartiesByCase();
        filingPartyComboBox.addItem("");
        for (int i = 0; i < partyType.size(); i++) {
            CaseParty party = (CaseParty) partyType.get(i);
            filingPartyComboBox.addItem(party.caseRelation);
        }
        filingPartyComboBox.addItem("Other");

        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Which Party Filed The Document?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel();
        final JPanel mainSub1Panel = new JPanel();
        final JPanel mainSub2Panel = new JPanel();

        mainSub1Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainSub1Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        actionLabel.setText("Party: ");
        mainSub1Panel.add(actionLabel);
        mainSub1Panel.add(filingPartyComboBox);

        mainSub2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainSub2Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        action2Label.setText("Name: ");
        mainSub2Panel.add(action2Label);
        mainSub2Panel.add(filingPartyTextField);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mainSub1Panel);
        mainPanel.add(mainSub2Panel);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel RespondingPartyPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        respondingPartyComboBox = new JComboBox();
        respondingPartyComboBox.setPreferredSize(new Dimension(100, 25));
        respondingPartyComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Appellant", "Appellee" }));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Which Party is Ordered to Respond?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Party: ");
        mainPanel.add(actionLabel);
        mainPanel.add(respondingPartyComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel RequestingPartyPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        requestingPartyComboBox = new JComboBox();
        requestingPartyComboBox.setPreferredSize(new Dimension(100, 25));
        requestingPartyComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Appellant", "Appellee"}));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Which Party is Requesting the Extension?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Party: ");
        mainPanel.add(actionLabel);
        mainPanel.add(requestingPartyComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel DepositionPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        depositionComboBox = new JComboBox();
        depositionComboBox.setPreferredSize(new Dimension(100, 25));
        depositionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Deposition", "Depositions"}));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Is a Single Deposition Requested, or Multiple?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Request: ");
        mainPanel.add(actionLabel);
        mainPanel.add(depositionComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel GenderRepresentativePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        genderRepresentativeComboBox = new JComboBox();
        genderRepresentativeComboBox.setPreferredSize(new Dimension(100, 25));
        genderRepresentativeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "Male", "Female" }));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Please Select Representative's Gender");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Gender: ");
        mainPanel.add(actionLabel);
        mainPanel.add(genderRepresentativeComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel CodeSectionFillinPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel title2Label = new JLabel();
        JLabel actionLabel = new JLabel();
        codeSectionFillInTextField = new JTextField();
        codeSectionFillInTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("Pursuant to what section does Appellee assert");
        title2Label.setFont(new java.awt.Font("Tahoma", 1, 12));
        title2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2Label.setText("Appellant is exempled from the classified service?");
        headerPanel.add(titleLabel);
        headerPanel.add(title2Label);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Title: ");
        mainPanel.add(actionLabel);
        mainPanel.add(codeSectionFillInTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel DocumentNamePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel title2Label = new JLabel();
        JLabel actionLabel = new JLabel();
        documentNameTextField = new JTextField();
        documentNameTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("Please Enter the Title or Description");
        title2Label.setFont(new java.awt.Font("Tahoma", 1, 12));
        title2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2Label.setText("of the Document Being Redacted");
        headerPanel.add(titleLabel);
        headerPanel.add(title2Label);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Title: ");
        mainPanel.add(actionLabel);
        mainPanel.add(documentNameTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel DateFiledPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        dateFiledWebDateField = new WebDateField();
        dateFiledWebDateField.setPreferredSize(new Dimension(150, 25));
        dateFiledWebDateField.setDateFormat(Global.mmddyyyy);
        dateFiledWebDateField.setEditable(false);
        dateFiledWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("On What Date was the Document Filed?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Date: ");
        mainPanel.add(actionLabel);
        mainPanel.add(dateFiledWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel InfoRedactedPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        infoRedactedTextField = new JTextField();
        infoRedactedTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What Information was Redacted?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Info: ");
        mainPanel.add(actionLabel);
        mainPanel.add(infoRedactedTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel RedactorNamePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        whoRedactedTextField = new JTextField();
        whoRedactedTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Who Redacted the Document?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Name: ");
        mainPanel.add(actionLabel);
        mainPanel.add(whoRedactedTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel RedactorTitlePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        redactedTitleTextField = new JTextField();
        redactedTitleTextField.setPreferredSize(new Dimension(150, 25));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What is the Redactor's Job title?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Title: ");
        mainPanel.add(actionLabel);
        mainPanel.add(redactedTitleTextField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel DatePOSentPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel title2Label = new JLabel();
        JLabel actionLabel = new JLabel();
        datePOSentWebDateField = new WebDateField();
        datePOSentWebDateField.setPreferredSize(new Dimension(150, 25));
        datePOSentWebDateField.setDateFormat(Global.mmddyyyy);
        datePOSentWebDateField.setEditable(false);
        datePOSentWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("On What Date Was the First Procedural Order");
        title2Label.setFont(new java.awt.Font("Tahoma", 1, 12));
        title2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2Label.setText("and Questionnaire Sent");
        headerPanel.add(titleLabel);
        headerPanel.add(title2Label);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Date: ");
        mainPanel.add(actionLabel);
        mainPanel.add(datePOSentWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel AppealTypePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        appealTypeComboBox = new JComboBox();
        appealTypeComboBox.setPreferredSize(new Dimension(225, 25));
        appealTypeComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "", "No Order Reduction", "Job Abolishment/Displacement/Layoff" }));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What Type of Action was Appealed?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Type: ");
        mainPanel.add(actionLabel);
        mainPanel.add(appealTypeComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel AppealType2Panel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        appealType2ComboBox = new JComboBox();
        appealType2ComboBox.setPreferredSize(new Dimension(225, 25));
        appealType2ComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
            "", "Suspension of Three Days or Less", "Fine of Three Days or Less",
            "Written Reprimand","Annual Performance Evaluation"}));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What Type of Action was Appealed?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Type: ");
        mainPanel.add(actionLabel);
        mainPanel.add(appealType2ComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel AppealTypeUFPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        appealTypeUFComboBox = new JComboBox();
        appealTypeUFComboBox.setPreferredSize(new Dimension(225, 25));
        appealTypeUFComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
            "", "Abolishment", "Alleged Reduction", "Denial of Reinstatement", "Disciplinary Reduction",
            "Displacement", "IDS", "Investigation", "Layoff", "Non-Disciplinary Reduction",
            "OSHA", "Reclassification", "Removal", "Suspension", "Transfer", "Whistleblower"}));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What Type of Action was Appealed?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Type: ");
        mainPanel.add(actionLabel);
        mainPanel.add(appealTypeUFComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel AppealTypeLSPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        appealTypeLSComboBox = new JComboBox();
        appealTypeLSComboBox.setPreferredSize(new Dimension(225, 25));
        appealTypeLSComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
            "", "Involuntary Disability Separation", "Fine", "Reduction", "Removal", "Suspension"}));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What Type of Action was Appealed?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Type: ");
        mainPanel.add(actionLabel);
        mainPanel.add(appealTypeLSComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel RequestingPartyContinuancePanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        RequestingPartyContinuanceComboBox = new JComboBox();
        RequestingPartyContinuanceComboBox.setPreferredSize(new Dimension(150, 25));
        RequestingPartyContinuanceComboBox.setModel(new javax.swing.DefaultComboBoxModel());

        List partyType = CaseParty.loadPartiesByCase();
        RequestingPartyContinuanceComboBox.addItem("");
        RequestingPartyContinuanceComboBox.addItem("Joint");
        for (int i = 0; i < partyType.size(); i++) {
            CaseParty party = (CaseParty) partyType.get(i);
            RequestingPartyContinuanceComboBox.addItem(party.caseRelation + "'s");
        }

        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Which Party Requested a Continuance?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Party: ");
        mainPanel.add(actionLabel);
        mainPanel.add(RequestingPartyContinuanceComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel DateRequestedPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        dateRequestedWebDateField = new WebDateField();
        dateRequestedWebDateField.setDateFormat(Global.mmddyyyy);
        dateRequestedWebDateField.setEditable(false);
        dateRequestedWebDateField.setPreferredSize(new Dimension(150, 25));
        dateRequestedWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("On What Date was the Continuance Requested?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Date: ");
        mainPanel.add(actionLabel);
        mainPanel.add(dateRequestedWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel RequestingPartyCTimeExtensionPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        RequestingPartyTimeExtensionComboBox = new JComboBox();
        RequestingPartyTimeExtensionComboBox.setPreferredSize(new Dimension(150, 25));
        RequestingPartyTimeExtensionComboBox.setModel(new javax.swing.DefaultComboBoxModel());

        List partyType = CaseParty.loadPartiesByCase();
        RequestingPartyTimeExtensionComboBox.addItem("");
        for (int i = 0; i < partyType.size(); i++) {
            CaseParty party = (CaseParty) partyType.get(i);
            RequestingPartyTimeExtensionComboBox.addItem(party.caseRelation);
        }

        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Which Party Requested the Extension of Time?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Party: ");
        mainPanel.add(actionLabel);
        mainPanel.add(RequestingPartyTimeExtensionComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel DateRequestedTimeExtensionPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel title2Label = new JLabel();
        JLabel actionLabel = new JLabel();
        dateRequestedExtensionWebDateField = new WebDateField();
        dateRequestedExtensionWebDateField.setPreferredSize(new Dimension(150, 25));
        dateRequestedExtensionWebDateField.setDateFormat(Global.mmddyyyy);
        dateRequestedExtensionWebDateField.setEditable(false);
        dateRequestedExtensionWebDateField.setCalendarCustomizer((final WebCalendar calendar) -> {
            calendar.setStartWeekFromSunday(true);
        });
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        headerPanel.setLayout(new BoxLayout(headerPanel, BoxLayout.Y_AXIS));
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        titleLabel.setText("On What Date was the Extension");
        title2Label.setFont(new java.awt.Font("Tahoma", 1, 12));
        title2Label.setAlignmentX(Component.CENTER_ALIGNMENT);
        title2Label.setText("of Time Requested?");
        headerPanel.add(titleLabel);
        headerPanel.add(title2Label);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Date: ");
        mainPanel.add(actionLabel);
        mainPanel.add(dateRequestedExtensionWebDateField);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel PurposeOfExtension() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel(), actionLabel = new JLabel(), action2Label = new JLabel();
        purposeOfExtensionTextField = new JTextField();
        purposeOfExtensionTextField.setPreferredSize(new Dimension(225, 25));
        purposeOfExtensionComboBox = new JComboBox();
        purposeOfExtensionComboBox.setPreferredSize(new Dimension(225, 25));
        purposeOfExtensionComboBox.setModel(new javax.swing.DefaultComboBoxModel(new String[] {
            "", "File Objections to an R&R", "File a Response to Objections to the R&R",
            "Respond to a Procedural Order", "Respond to a Letter", "File a Memorandum Contra", "Other"}));
        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("What is the purpose of the Extension?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        final JPanel mainSub1Panel = new JPanel(); //Creating the orderList JPanel
        final JPanel mainSub2Panel = new JPanel(); //Creating the orderList JPanel

        mainSub1Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainSub1Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        actionLabel.setText("Type: ");
        mainSub1Panel.add(actionLabel);
        mainSub1Panel.add(purposeOfExtensionComboBox);

        mainSub2Panel.setLayout(new FlowLayout(FlowLayout.CENTER));
        mainSub2Panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 30));
        action2Label.setText("Other: ");
        mainSub2Panel.add(action2Label);
        mainSub2Panel.add(purposeOfExtensionTextField);

        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
        mainPanel.add(mainSub1Panel);
        mainPanel.add(mainSub2Panel);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private JPanel RequestingPartyCConsolidationPanel() {
        JPanel panel = new JPanel();
        JLabel titleLabel = new JLabel();
        JLabel actionLabel = new JLabel();
        RequestingPartyConsolidationComboBox = new JComboBox();
        RequestingPartyConsolidationComboBox.setPreferredSize(new Dimension(150, 25));
        RequestingPartyConsolidationComboBox.setModel(new javax.swing.DefaultComboBoxModel());

        List partyType = CaseParty.loadPartiesByCase();
        RequestingPartyConsolidationComboBox.addItem("");
        for (int i = 0; i < partyType.size(); i++) {
            CaseParty party = (CaseParty) partyType.get(i);
            RequestingPartyConsolidationComboBox.addItem(party.caseRelation);
        }

        panel.setBorder(BorderFactory.createEtchedBorder());
        panel.setPreferredSize(new Dimension(panelWidth, panelHeight));

        //Set Top Panel
        final JPanel headerPanel = new JPanel();
        titleLabel.setFont(new java.awt.Font("Tahoma", 1, 12));
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setText("Which Party Requested Consolidation?");
        headerPanel.add(titleLabel);

        //Set Bottom Panel (Button Bar)
        final JPanel mainPanel = new JPanel(); //Creating the orderList JPanel
        mainPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        actionLabel.setText("Party: ");
        mainPanel.add(actionLabel);
        mainPanel.add(RequestingPartyConsolidationComboBox);

        panel.setLayout(new BorderLayout());
        panel.add(headerPanel, BorderLayout.NORTH);
        panel.add(mainPanel, BorderLayout.CENTER);

        return panel;
    }

    private String readFile(String name) {
        String sourcePath = Global.templatePath + "CMDS" + File.separator + "Forms" + File.separator;
        String printout = "";
        try {
            FileReader f = new FileReader(sourcePath + name + ".txt");
            try (
                    BufferedReader br = new BufferedReader(f)) {
                    StringBuilder sb = new StringBuilder();
                    String line = br.readLine();

                    while (line != null) {
                        sb.append(line);
                        line = br.readLine();
                    }
                printout = sb.toString();
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
        return printout;
    }

}
