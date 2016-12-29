/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import com.alee.laf.optionpane.WebOptionPane;
import parker.serb.Global;
import parker.serb.sql.CMDSDocuments;

/**
 *
 * @author Andrew
 */
public class CMDSTemplateAddEditDialog extends javax.swing.JDialog {

    private int ID;
    private CMDSDocuments item;
    
    /**
     * Creates new form CMDSStatusTypeAddEditDialog
     * @param parent
     * @param modal
     * @param itemIDpassed
     */
    public CMDSTemplateAddEditDialog(java.awt.Frame parent, boolean modal, int itemIDpassed) {
        super(parent, modal);
        initComponents();
        setDefaults(itemIDpassed);
    }

    private void setDefaults(int itemIDpassed) {
        ID = itemIDpassed;
        if (ID > 0) {
            titleLabel.setText("Edit Template");
            editButton.setText("Save");
            loadInformation();
        } else {
            titleLabel.setText("Add Template");
            editButton.setText("Add");
            editButton.setEnabled(false);
            item = new CMDSDocuments();
        }
        this.setLocationRelativeTo(Global.root);
        this.setVisible(true);
    }
        
    private void loadInformation() {
        item = CMDSDocuments.findDocumentByID(ID);
        mainCategoryTextField.setText(item.MainCategory);
        subCategoryTextField.setText(item.SubCategory);
        letterNameTextField.setText(item.LetterName);
        locationTextField.setText(item.Location);
        emailSubjectTextField.setText(item.emailSubject);
        emailBodyTextArea.setText(item.emailBody);
        multiplePrintCheckBox.setSelected(item.MultiplePrint);
        responseDueCheckBox.setSelected(item.ResponseDue);
        actionAppealedCheckBox.setSelected(item.ActionAppealed);
        classificationTitleCheckBox.setSelected(item.ClassificationTitle);
        classificationNumberCheckBox.setSelected(item.ClassificationNumber);
        barginingUnitCheckBox.setSelected(item.BarginingUnit);
        appellantAppointedCheckBox.setSelected(item.AppelantAppointed);
        probationaryPeriodCheckBox.setSelected(item.ProbitionaryPeriod);
        hearingDateCheckBox.setSelected(item.HearingDate);
        hearingTimeCheckBox.setSelected(item.HearingTime);
        hearingServedCheckBox.setSelected(item.HearingServed);
        memorandumContraCheckBox.setSelected(item.MemorandumContra);
        genderCheckBox.setSelected(item.Gender);
        addressBlockCheckBox.setSelected(item.AddressBlock);
        firstLetterSentCheckBox.setSelected(item.FirstLetterSent);
        codeSectionCheckBox.setSelected(item.CodeSection);
        countyNameCheckBox.setSelected(item.CountyName);
        stayDateCheckBox.setSelected(item.StayDate);
        casePendingResolutionCheckBox.setSelected(item.CasePendingResolution);
        lastUpdateCheckBox.setSelected(item.LastUpdate);
        dateGrantedCheckBox.setSelected(item.DateGranted);
        matterContinuedCheckBox.setSelected(item.MatterContinued);
        settlementDueCheckBox.setSelected(item.SettlementDue);
        filingPartyCheckBox.setSelected(item.FilingParty);
        respondingPartyCheckBox.setSelected(item.RespondingParty);
        requestingPartyCheckBox.setSelected(item.RequestingParty);
        depositionCheckBox.setSelected(item.Deposition);
        repHimOrHerCheckBox.setSelected(item.RepHimOrHer);
        typeOfActionCheckBox.setSelected(item.TypeOfAction);
        codeSectionFillInCheckBox.setSelected(item.CodeSectionFillIn);
        documentNameCheckBox.setSelected(item.DocumentName);
        dateFiledCheckBox.setSelected(item.DateFiled);
        infoRedactedCheckBox.setSelected(item.InfoRedacted);
        redactorNameCheckBox.setSelected(item.RedactorName);
        redactorTitleCheckBox.setSelected(item.RedactorTitle);
        datePOSentCheckBox.setSelected(item.DatePOSent);
        appealTypeCheckBox.setSelected(item.AppealType);
        appealType2CheckBox.setSelected(item.AppealType2);
        appealTypeUFCheckBox.setSelected(item.AppealTypeUF);
        appealTypeLSCheckBox.setSelected(item.AppealTypeLS);
        requestingPartyCCheckBox.setSelected(item.RequestingPartyC);
        dateRequestedCheckBox.setSelected(item.DateRequested);
        purposeOfExtensionCheckBox.setSelected(item.PurposeOfExtension);
    }
    
    private void saveInformation() {
        item.ID = ID;
        item.MainCategory = mainCategoryTextField.getText().trim();
        item.SubCategory = subCategoryTextField.getText().trim();
        item.LetterName = letterNameTextField.getText().trim();
        item.Location = locationTextField.getText().trim();
        item.emailSubject = emailSubjectTextField.getText().trim();
        item.emailBody = emailBodyTextArea.getText().trim();
        item.MultiplePrint = multiplePrintCheckBox.isSelected();
        item.ResponseDue = responseDueCheckBox.isSelected();
        item.ActionAppealed = actionAppealedCheckBox.isSelected();
        item.ClassificationTitle = classificationTitleCheckBox.isSelected();
        item.ClassificationNumber = classificationNumberCheckBox.isSelected();
        item.BarginingUnit = barginingUnitCheckBox.isSelected();
        item.AppelantAppointed = appellantAppointedCheckBox.isSelected();
        item.ProbitionaryPeriod = probationaryPeriodCheckBox.isSelected();
        item.HearingDate = hearingDateCheckBox.isSelected();
        item.HearingTime = hearingTimeCheckBox.isSelected();
        item.HearingServed = hearingServedCheckBox.isSelected();
        item.MemorandumContra = memorandumContraCheckBox.isSelected();
        item.Gender = genderCheckBox.isSelected();
        item.AddressBlock = addressBlockCheckBox.isSelected();
        
        
        item.FirstLetterSent = firstLetterSentCheckBox.isSelected();
        item.CodeSection = codeSectionCheckBox.isSelected();
        item.CountyName = countyNameCheckBox.isSelected();
        item.StayDate = stayDateCheckBox.isSelected();
        item.CasePendingResolution = casePendingResolutionCheckBox.isSelected();
        item.LastUpdate = lastUpdateCheckBox.isSelected();
        item.DateGranted = dateGrantedCheckBox.isSelected();
        item.MatterContinued = matterContinuedCheckBox.isSelected();
        item.SettlementDue = settlementDueCheckBox.isSelected();
        item.FilingParty = filingPartyCheckBox.isSelected();
        item.RespondingParty = respondingPartyCheckBox.isSelected();
        item.RequestingParty = requestingPartyCheckBox.isSelected();
        item.Deposition = depositionCheckBox.isSelected();
        item.RepHimOrHer = repHimOrHerCheckBox.isSelected();
        item.TypeOfAction = typeOfActionCheckBox.isSelected();
        item.CodeSectionFillIn = codeSectionFillInCheckBox.isSelected();
        item.DocumentName = documentNameCheckBox.isSelected();
        item.DateFiled = dateFiledCheckBox.isSelected();
        item.InfoRedacted = infoRedactedCheckBox.isSelected();
        item.RedactorName = redactorNameCheckBox.isSelected();
        item.RedactorTitle = redactorTitleCheckBox.isSelected();
        item.DatePOSent = datePOSentCheckBox.isSelected();
        item.AppealType = appealTypeCheckBox.isSelected();
        item.AppealType2 = appealType2CheckBox.isSelected();
        item.AppealTypeUF = appealTypeUFCheckBox.isSelected();
        item.AppealTypeLS = appealTypeLSCheckBox.isSelected();
        item.RequestingPartyC = requestingPartyCCheckBox.isSelected();
        item.DateRequested = dateRequestedCheckBox.isSelected();
        item.PurposeOfExtension = purposeOfExtensionCheckBox.isSelected();
        
                       
        if (ID > 0){
                CMDSDocuments.updateCMDSDocument(item);
        } else {
                CMDSDocuments.createCMDSDocument(item);
        }
    }

    private void checkButton(){
        if (mainCategoryTextField.getText().trim().equals("") || 
                subCategoryTextField.getText().trim().equals("") || 
                letterNameTextField.getText().trim().equals("") || 
                locationTextField.getText().trim().equals("")){
            editButton.setEnabled(false);
        } else {
            editButton.setEnabled(true);
        }
    }    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        titleLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        mainCategoryTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        subCategoryTextField = new javax.swing.JTextField();
        locationTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        letterNameTextField = new javax.swing.JTextField();
        multiplePrintCheckBox = new javax.swing.JCheckBox();
        responseDueCheckBox = new javax.swing.JCheckBox();
        actionAppealedCheckBox = new javax.swing.JCheckBox();
        classificationTitleCheckBox = new javax.swing.JCheckBox();
        classificationNumberCheckBox = new javax.swing.JCheckBox();
        barginingUnitCheckBox = new javax.swing.JCheckBox();
        appellantAppointedCheckBox = new javax.swing.JCheckBox();
        addressBlockCheckBox = new javax.swing.JCheckBox();
        firstLetterSentCheckBox = new javax.swing.JCheckBox();
        probationaryPeriodCheckBox = new javax.swing.JCheckBox();
        hearingDateCheckBox = new javax.swing.JCheckBox();
        hearingTimeCheckBox = new javax.swing.JCheckBox();
        hearingServedCheckBox = new javax.swing.JCheckBox();
        memorandumContraCheckBox = new javax.swing.JCheckBox();
        dateGrantedCheckBox = new javax.swing.JCheckBox();
        matterContinuedCheckBox = new javax.swing.JCheckBox();
        codeSectionCheckBox = new javax.swing.JCheckBox();
        countyNameCheckBox = new javax.swing.JCheckBox();
        stayDateCheckBox = new javax.swing.JCheckBox();
        casePendingResolutionCheckBox = new javax.swing.JCheckBox();
        lastUpdateCheckBox = new javax.swing.JCheckBox();
        repHimOrHerCheckBox = new javax.swing.JCheckBox();
        typeOfActionCheckBox = new javax.swing.JCheckBox();
        settlementDueCheckBox = new javax.swing.JCheckBox();
        filingPartyCheckBox = new javax.swing.JCheckBox();
        respondingPartyCheckBox = new javax.swing.JCheckBox();
        requestingPartyCheckBox = new javax.swing.JCheckBox();
        depositionCheckBox = new javax.swing.JCheckBox();
        dateFiledCheckBox = new javax.swing.JCheckBox();
        infoRedactedCheckBox = new javax.swing.JCheckBox();
        redactorNameCheckBox = new javax.swing.JCheckBox();
        redactorTitleCheckBox = new javax.swing.JCheckBox();
        datePOSentCheckBox = new javax.swing.JCheckBox();
        codeSectionFillInCheckBox = new javax.swing.JCheckBox();
        documentNameCheckBox = new javax.swing.JCheckBox();
        appealTypeUFCheckBox = new javax.swing.JCheckBox();
        appealTypeLSCheckBox = new javax.swing.JCheckBox();
        requestingPartyCCheckBox = new javax.swing.JCheckBox();
        dateRequestedCheckBox = new javax.swing.JCheckBox();
        purposeOfExtensionCheckBox = new javax.swing.JCheckBox();
        appealTypeCheckBox = new javax.swing.JCheckBox();
        appealType2CheckBox = new javax.swing.JCheckBox();
        jLabel8 = new javax.swing.JLabel();
        emailSubjectTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        emailBodyTextArea = new javax.swing.JTextArea();
        genderCheckBox = new javax.swing.JCheckBox();
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        titleLabel.setFont(new java.awt.Font("Arial", 1, 14)); // NOI18N
        titleLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        titleLabel.setText("<<TITLE>>");

        closeButton.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        closeButton.setText("Close");
        closeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                closeButtonActionPerformed(evt);
            }
        });

        mainCategoryTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        mainCategoryTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        mainCategoryTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                mainCategoryTextFieldCaretUpdate(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Main Category:");

        editButton.setText("<<EDIT>>");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Sub Category:");

        subCategoryTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        subCategoryTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        subCategoryTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                subCategoryTextFieldCaretUpdate(evt);
            }
        });

        locationTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        locationTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        locationTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                locationTextFieldCaretUpdate(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Location:");

        jLabel5.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("Letter Name:");

        letterNameTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        letterNameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        letterNameTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                letterNameTextFieldCaretUpdate(evt);
            }
        });

        multiplePrintCheckBox.setText("Multiple Print");

        responseDueCheckBox.setText("Response Due");

        actionAppealedCheckBox.setText("Action Appealed");

        classificationTitleCheckBox.setText("Classification Title");

        classificationNumberCheckBox.setText("Classification Number");

        barginingUnitCheckBox.setText("Bargining Unit");

        appellantAppointedCheckBox.setText("Appellant Appointed");

        addressBlockCheckBox.setText("Address Block");

        firstLetterSentCheckBox.setText("First Letter Sent");

        probationaryPeriodCheckBox.setText("Probationary Period");

        hearingDateCheckBox.setText("Hearing Date");

        hearingTimeCheckBox.setText("Hearing Time");

        hearingServedCheckBox.setText("Hearing Served");

        memorandumContraCheckBox.setText("Memorandum Contra");

        dateGrantedCheckBox.setText("Date Granted");

        matterContinuedCheckBox.setText("Matter Continued");

        codeSectionCheckBox.setText("Code Section");

        countyNameCheckBox.setText("County Name");

        stayDateCheckBox.setText("Stay Date");

        casePendingResolutionCheckBox.setText("Case Pending Resolution");

        lastUpdateCheckBox.setText("Last Update");

        repHimOrHerCheckBox.setText("Rep Him or Her");

        typeOfActionCheckBox.setText("Type of Action");

        settlementDueCheckBox.setText("Settlement Due");

        filingPartyCheckBox.setText("Filing Party");

        respondingPartyCheckBox.setText("Responding Party");

        requestingPartyCheckBox.setText("Requesting Party");

        depositionCheckBox.setText("Deposition");

        dateFiledCheckBox.setText("Date Filed");

        infoRedactedCheckBox.setText("Info Redacted");

        redactorNameCheckBox.setText("Redactor Name");

        redactorTitleCheckBox.setText("Redactor Title");

        datePOSentCheckBox.setText("Date PO Sent");

        codeSectionFillInCheckBox.setText("Code Section Fill In");

        documentNameCheckBox.setText("Document Name");

        appealTypeUFCheckBox.setText("Appeal Type UF");

        appealTypeLSCheckBox.setText("Appeal Type LS");

        requestingPartyCCheckBox.setText("Requesting Party C");

        dateRequestedCheckBox.setText("Date Requested");

        purposeOfExtensionCheckBox.setText("Purpose of Extension");

        appealTypeCheckBox.setText("Appeal Type");

        appealType2CheckBox.setText("Appeal Type 2");

        jLabel8.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("Email Subject:");

        emailSubjectTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        emailSubjectTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel9.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("Email Body:");

        emailBodyTextArea.setColumns(20);
        emailBodyTextArea.setRows(5);
        jScrollPane1.setViewportView(emailBodyTextArea);

        genderCheckBox.setText("Gender");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(352, 352, 352))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 85, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(mainCategoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(subCategoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(letterNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(locationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(emailSubjectTextField, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 349, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(appellantAppointedCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(appealTypeUFCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(appealTypeLSCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(appealType2CheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(appealTypeCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(barginingUnitCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addComponent(codeSectionFillInCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(codeSectionCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(classificationTitleCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(classificationNumberCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addComponent(casePendingResolutionCheckBox, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 160, Short.MAX_VALUE)
                                        .addComponent(countyNameCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addComponent(actionAppealedCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(addressBlockCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(filingPartyCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(documentNameCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(depositionCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateRequestedCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, 117, Short.MAX_VALUE)
                                    .addComponent(datePOSentCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(firstLetterSentCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(genderCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(hearingDateCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(hearingServedCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(hearingTimeCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(infoRedactedCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateGrantedCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(dateFiledCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lastUpdateCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(redactorTitleCheckBox)
                                    .addComponent(redactorNameCheckBox)
                                    .addComponent(purposeOfExtensionCheckBox)
                                    .addComponent(probationaryPeriodCheckBox)
                                    .addComponent(multiplePrintCheckBox)
                                    .addComponent(repHimOrHerCheckBox)
                                    .addComponent(requestingPartyCheckBox)
                                    .addComponent(requestingPartyCCheckBox)
                                    .addComponent(respondingPartyCheckBox)
                                    .addComponent(responseDueCheckBox)
                                    .addComponent(stayDateCheckBox)
                                    .addComponent(memorandumContraCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(matterContinuedCheckBox)
                                    .addComponent(typeOfActionCheckBox)
                                    .addComponent(settlementDueCheckBox))
                                .addGap(0, 19, Short.MAX_VALUE)))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {dateFiledCheckBox, dateGrantedCheckBox, datePOSentCheckBox, dateRequestedCheckBox, depositionCheckBox, documentNameCheckBox, filingPartyCheckBox, firstLetterSentCheckBox, genderCheckBox, hearingDateCheckBox, hearingServedCheckBox, hearingTimeCheckBox, infoRedactedCheckBox, lastUpdateCheckBox});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {matterContinuedCheckBox, memorandumContraCheckBox, multiplePrintCheckBox, probationaryPeriodCheckBox, purposeOfExtensionCheckBox, redactorNameCheckBox, redactorTitleCheckBox, repHimOrHerCheckBox, requestingPartyCCheckBox, requestingPartyCheckBox, respondingPartyCheckBox, responseDueCheckBox, settlementDueCheckBox, stayDateCheckBox, typeOfActionCheckBox});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {actionAppealedCheckBox, addressBlockCheckBox, appealType2CheckBox, appealTypeCheckBox, appealTypeLSCheckBox, appealTypeUFCheckBox, appellantAppointedCheckBox, barginingUnitCheckBox, casePendingResolutionCheckBox, classificationNumberCheckBox, classificationTitleCheckBox, codeSectionCheckBox, codeSectionFillInCheckBox, countyNameCheckBox});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(mainCategoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(subCategoryTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(letterNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(locationTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(emailSubjectTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 232, Short.MAX_VALUE))
                        .addComponent(jSeparator1, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel4)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel6)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel5)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel7)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel8)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(jLabel9))
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(actionAppealedCheckBox)
                                .addComponent(dateFiledCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(addressBlockCheckBox)
                                .addComponent(dateGrantedCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(appealTypeCheckBox)
                                .addComponent(datePOSentCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(appealType2CheckBox)
                                .addComponent(dateRequestedCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(appealTypeLSCheckBox)
                                .addComponent(depositionCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(appealTypeUFCheckBox)
                                .addComponent(documentNameCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(appellantAppointedCheckBox)
                                .addComponent(filingPartyCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(barginingUnitCheckBox)
                                .addComponent(firstLetterSentCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(casePendingResolutionCheckBox)
                                .addComponent(genderCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(classificationNumberCheckBox)
                                .addComponent(hearingDateCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(classificationTitleCheckBox)
                                .addComponent(hearingServedCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(codeSectionCheckBox)
                                .addComponent(hearingTimeCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(codeSectionFillInCheckBox)
                                .addComponent(infoRedactedCheckBox))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(countyNameCheckBox)
                                .addComponent(lastUpdateCheckBox))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(matterContinuedCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(memorandumContraCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(multiplePrintCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(probationaryPeriodCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(purposeOfExtensionCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(redactorNameCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(redactorTitleCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(repHimOrHerCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(requestingPartyCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(requestingPartyCCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(respondingPartyCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(responseDueCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(stayDateCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(settlementDueCheckBox)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(typeOfActionCheckBox)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 52, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {emailSubjectTextField, jLabel4, jLabel5, jLabel6, jLabel7, jLabel8, jLabel9, letterNameTextField, locationTextField, mainCategoryTextField, subCategoryTextField});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {actionAppealedCheckBox, addressBlockCheckBox, appealType2CheckBox, appealTypeCheckBox, appealTypeLSCheckBox, appealTypeUFCheckBox, appellantAppointedCheckBox, barginingUnitCheckBox, casePendingResolutionCheckBox, classificationNumberCheckBox, classificationTitleCheckBox, codeSectionCheckBox, codeSectionFillInCheckBox, countyNameCheckBox, dateFiledCheckBox, dateGrantedCheckBox, datePOSentCheckBox, dateRequestedCheckBox, depositionCheckBox, documentNameCheckBox, filingPartyCheckBox, firstLetterSentCheckBox, genderCheckBox, hearingDateCheckBox, hearingServedCheckBox, hearingTimeCheckBox, infoRedactedCheckBox, lastUpdateCheckBox, matterContinuedCheckBox, memorandumContraCheckBox, multiplePrintCheckBox, probationaryPeriodCheckBox, purposeOfExtensionCheckBox, redactorNameCheckBox, redactorTitleCheckBox, repHimOrHerCheckBox, requestingPartyCCheckBox, requestingPartyCheckBox, respondingPartyCheckBox, responseDueCheckBox, settlementDueCheckBox, stayDateCheckBox, typeOfActionCheckBox});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void closeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_closeButtonActionPerformed
        int answer = WebOptionPane.showConfirmDialog(this, "Are you sure you wish to close this window. Any unsaved information will be lost.", "Cancel", WebOptionPane.YES_NO_OPTION);
        if (answer == WebOptionPane.YES_OPTION) {
            this.dispose();
        }
    }//GEN-LAST:event_closeButtonActionPerformed

    private void editButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_editButtonActionPerformed
        saveInformation();
        this.dispose();
    }//GEN-LAST:event_editButtonActionPerformed

    private void mainCategoryTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_mainCategoryTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_mainCategoryTextFieldCaretUpdate

    private void subCategoryTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_subCategoryTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_subCategoryTextFieldCaretUpdate

    private void locationTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_locationTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_locationTextFieldCaretUpdate

    private void letterNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_letterNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_letterNameTextFieldCaretUpdate

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox actionAppealedCheckBox;
    private javax.swing.JCheckBox addressBlockCheckBox;
    private javax.swing.JCheckBox appealType2CheckBox;
    private javax.swing.JCheckBox appealTypeCheckBox;
    private javax.swing.JCheckBox appealTypeLSCheckBox;
    private javax.swing.JCheckBox appealTypeUFCheckBox;
    private javax.swing.JCheckBox appellantAppointedCheckBox;
    private javax.swing.JCheckBox barginingUnitCheckBox;
    private javax.swing.JCheckBox casePendingResolutionCheckBox;
    private javax.swing.JCheckBox classificationNumberCheckBox;
    private javax.swing.JCheckBox classificationTitleCheckBox;
    private javax.swing.JButton closeButton;
    private javax.swing.JCheckBox codeSectionCheckBox;
    private javax.swing.JCheckBox codeSectionFillInCheckBox;
    private javax.swing.JCheckBox countyNameCheckBox;
    private javax.swing.JCheckBox dateFiledCheckBox;
    private javax.swing.JCheckBox dateGrantedCheckBox;
    private javax.swing.JCheckBox datePOSentCheckBox;
    private javax.swing.JCheckBox dateRequestedCheckBox;
    private javax.swing.JCheckBox depositionCheckBox;
    private javax.swing.JCheckBox documentNameCheckBox;
    private javax.swing.JButton editButton;
    private javax.swing.JTextArea emailBodyTextArea;
    private javax.swing.JTextField emailSubjectTextField;
    private javax.swing.JCheckBox filingPartyCheckBox;
    private javax.swing.JCheckBox firstLetterSentCheckBox;
    private javax.swing.JCheckBox genderCheckBox;
    private javax.swing.JCheckBox hearingDateCheckBox;
    private javax.swing.JCheckBox hearingServedCheckBox;
    private javax.swing.JCheckBox hearingTimeCheckBox;
    private javax.swing.JCheckBox infoRedactedCheckBox;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JCheckBox lastUpdateCheckBox;
    private javax.swing.JTextField letterNameTextField;
    private javax.swing.JTextField locationTextField;
    private javax.swing.JTextField mainCategoryTextField;
    private javax.swing.JCheckBox matterContinuedCheckBox;
    private javax.swing.JCheckBox memorandumContraCheckBox;
    private javax.swing.JCheckBox multiplePrintCheckBox;
    private javax.swing.JCheckBox probationaryPeriodCheckBox;
    private javax.swing.JCheckBox purposeOfExtensionCheckBox;
    private javax.swing.JCheckBox redactorNameCheckBox;
    private javax.swing.JCheckBox redactorTitleCheckBox;
    private javax.swing.JCheckBox repHimOrHerCheckBox;
    private javax.swing.JCheckBox requestingPartyCCheckBox;
    private javax.swing.JCheckBox requestingPartyCheckBox;
    private javax.swing.JCheckBox respondingPartyCheckBox;
    private javax.swing.JCheckBox responseDueCheckBox;
    private javax.swing.JCheckBox settlementDueCheckBox;
    private javax.swing.JCheckBox stayDateCheckBox;
    private javax.swing.JTextField subCategoryTextField;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JCheckBox typeOfActionCheckBox;
    // End of variables declaration//GEN-END:variables
}
