/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ORG;

import com.alee.laf.optionpane.WebOptionPane;
import java.awt.Color;
import java.awt.Component;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.generateDocument;
import parker.serb.sql.Activity;
import parker.serb.sql.CaseParty;
import parker.serb.sql.EmailOut;
import parker.serb.sql.EmailOutAttachment;
import parker.serb.sql.ORGCase;
import parker.serb.sql.PostalOut;
import parker.serb.sql.PostalOutAttachment;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.Item;
import parker.serb.util.SlackNotification;
import parker.serb.util.StringUtilities;

/**
 *
 * @author User
 */
public class ORGAllLettersPanel extends javax.swing.JDialog {

    List<ORGCase> orgCaseList;
    List<CaseParty> partyList;

    /**
     * Creates new form ORGAllLettersPanel
     *
     * @param parent
     * @param modal
     */
    public ORGAllLettersPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        addRenderer();
        setDefaults();
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void addRenderer() {
        jTable1.setDefaultRenderer(Object.class, new TableCellRenderer() {
            private DefaultTableCellRenderer DEFAULT_RENDERER = new DefaultTableCellRenderer();

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

    private void setDefaults() {
        setColumnSize();
        loadReports();
        enableGenerateButton();
    }

    //TODO: CLean this up so they all work for macOS
    private void setColumnSize() {
        //ID
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);

        //ORG #
        jTable1.getColumnModel().getColumn(1).setMinWidth(60);
        jTable1.getColumnModel().getColumn(1).setWidth(60);
        jTable1.getColumnModel().getColumn(1).setMaxWidth(60);

        //Org Name
        //NONE SET
        //ORG Via
        jTable1.getColumnModel().getColumn(3).setMinWidth(80);
        jTable1.getColumnModel().getColumn(3).setWidth(80);
        jTable1.getColumnModel().getColumn(3).setMaxWidth(80);

        //Rep Via
        jTable1.getColumnModel().getColumn(4).setMinWidth(80);
        jTable1.getColumnModel().getColumn(4).setWidth(80);
        jTable1.getColumnModel().getColumn(4).setMaxWidth(80);

        //AR Last
        jTable1.getColumnModel().getColumn(5).setMinWidth(80);
        jTable1.getColumnModel().getColumn(5).setWidth(80);
        jTable1.getColumnModel().getColumn(5).setMaxWidth(80);

        //FS Last
        jTable1.getColumnModel().getColumn(6).setMinWidth(80);
        jTable1.getColumnModel().getColumn(6).setWidth(80);
        jTable1.getColumnModel().getColumn(6).setMaxWidth(80);
    }

    private void loadReports() {
        List<SMDSDocuments> letterList = SMDSDocuments.loadDocumentNamesByTypeAndSection("ORG", "Letter");

        DefaultComboBoxModel dt = new DefaultComboBoxModel();
        letterComboBox.setModel(dt);
        letterComboBox.addItem(new Item<>("0", ""));

        for (SMDSDocuments letter : letterList) {
            letterComboBox.addItem(new Item<>(String.valueOf(letter.id), letter.description));
        }
        letterComboBox.setSelectedItem(new Item<>("0", ""));
    }

    private void processComboBoxSelection() {
        orgCaseList = null;
        partyList = null;
        Calendar cal = Calendar.getInstance();

        if (letterComboBox.getSelectedItem().toString().startsWith("Tickler 45")) {
            cal.set(Calendar.DAY_OF_MONTH, 15);
            cal.add(Calendar.MONTH, 1);
            cal.add(Calendar.MONTH, -5);
            processOverdueNumbers(cal);
        } else if (letterComboBox.getSelectedItem().toString().startsWith("Tickler 10")) {
            cal.set(Calendar.DAY_OF_MONTH, 15);
            cal.add(Calendar.MONTH, -5);
            processOverdueNumbers(cal);
        } else if (letterComboBox.getSelectedItem().toString().startsWith("Tickler 31")) {
            cal.set(Calendar.DAY_OF_MONTH, 15);
            cal.add(Calendar.MONTH, -5);
            cal.add(Calendar.MONTH, -1);
            processOverdueNumbers(cal);
        } else {
            orgCaseList = ORGCase.getOrgCasesAllLettersDefault();
        }

        FYEDuringTextField.setText(letterComboBox.getSelectedItem().toString().equals("")
                ? "" : cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH));
        loadTable();
    }

    private void processOverdueNumbers(Calendar cal) {
        String FYEMonthName = cal.getDisplayName(Calendar.MONTH, Calendar.LONG, Locale.ENGLISH);

        orgCaseList = ORGCase.getOrgCasesAllLetters(FYEMonthName, Global.SQLDateFormat.format(cal.getTime()));
    }

    private void loadTable() {
        int postalNumber = 0;
        int EmailNumber = 0;

        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);

        System.out.println(orgCaseList.size());

        for (ORGCase item : orgCaseList) {
            String orgVia = "";
            String repVia = "";
            String ARDate = "";
            String FSDate = "";

            partyList = CaseParty.loadORGPartiesByCase("ORG", item.orgNumber);
            for (CaseParty party : partyList) {
                if (party.caseRelation.equals("Representative")) {
                    if (party.emailAddress != null) {
                        EmailNumber++;
                        if (!repVia.trim().equals("")) {
                            repVia += ", ";
                        }
                        repVia += "Email";
                    } else if (item.orgAddress1 != null && item.orgCity != null && item.orgState != null && item.orgZip != null) {
                        if (!item.orgAddress1.equalsIgnoreCase(party.address1)
                                && !item.orgCity.equalsIgnoreCase(party.city)
                                && !item.orgState.equalsIgnoreCase(party.stateCode)
                                && !item.orgZip.equalsIgnoreCase(party.zipcode)) {
                            postalNumber++;
                            if (!repVia.trim().equals("")) {
                                repVia += ", ";
                            }
                            repVia += "Postal";
                        }
                    }
                }
            }

            if (item.orgEmail != null) {
                EmailNumber++;
                orgVia = "Email";
            } else if (item.orgAddress1 != null && item.orgCity != null && item.orgState != null && item.orgZip != null) {
                postalNumber++;
                orgVia = "Postal";
            }

            if (item.annualReport != null) {
                ARDate = Global.mmddyyyy.format(item.annualReport);
            }

            if (item.financialReport != null) {
                FSDate = Global.mmddyyyy.format(item.financialReport);
            }

            model.addRow(new Object[]{
                item.id, //id
                item.orgNumber, //org Number
                item.orgName, //org Name
                orgVia, //org via
                repVia, //rep via
                ARDate, //AR Last
                FSDate//FS Last
            });
        }

        NumberOfOrgsTextField.setText(letterComboBox.getSelectedItem().toString().equals("")
                ? "" : String.valueOf(orgCaseList.size()));

        PostalMailTextField.setText(letterComboBox.getSelectedItem().toString().equals("")
                ? "" : String.valueOf(postalNumber));

        EMailTextField.setText(letterComboBox.getSelectedItem().toString().equals("")
                ? "" : String.valueOf(EmailNumber));
    }

    private void enableGenerateButton() {
        if (letterComboBox.getSelectedItem().toString().equals("") || orgCaseList.isEmpty()) {
            GenerateButton.setEnabled(false);
        } else {
            GenerateButton.setEnabled(true);
        }
    }

    private void generateLetters() {
        int selection = 0;

        if (!letterComboBox.getSelectedItem().toString().trim().equals("")) {
            Item item = (Item) letterComboBox.getSelectedItem();
            selection = Integer.parseInt(item.getValue().toString());
        }

        if (selection > 0) {
            SMDSDocuments template = SMDSDocuments.findDocumentByID(selection);
            File templateFile = new File(Global.templatePath + Global.activeSection + File.separator + template.fileName);

            if (templateFile.exists()) {
                for (ORGCase item : orgCaseList) {
                    String attachDocName = "";
                    String orgdocName = generateDocument.generateSMDSdocument(template, 0, null, null, item, null, false);
                    String repdocName = generateDocument.generateSMDSdocument(template, 0, null, null, item, null, true);

                    if (template.questionsFileName != null) {
                        attachDocName = copyAttachmentToCaseFolder(item, template.questionsFileName);
                    }

                    if (template.description.startsWith("Tickler")) {
                        attachDocName = copyAttachmentToCaseFolder(item, "EOAR.pdf");
                    }

                    if (template.description.startsWith("New Registration")) {
                        attachDocName = copyAttachmentToCaseFolder(item, "RegistrationReportForm.pdf");
                    }

                    Activity.addActivtyORGCase("ORG", item.orgNumber,
                            "Created " + (template.historyDescription == null ? template.description : template.historyDescription) + " for Org",
                            orgdocName);

                    Activity.addActivtyORGCase("ORG", item.orgNumber,
                            "Created " + (template.historyDescription == null ? template.description : template.historyDescription) + " for Rep",
                            repdocName);

                    String lastNotify = "";

                    if (letterComboBox.getSelectedItem().toString().startsWith("Tickler 45")) {
                        lastNotify = "45";
                    } else if (letterComboBox.getSelectedItem().toString().startsWith("Tickler 10")) {
                        lastNotify = "10";
                    } else if (letterComboBox.getSelectedItem().toString().startsWith("Tickler 31")) {
                        lastNotify = "31";
                    } else if (letterComboBox.getSelectedItem().toString().startsWith("New")) {
                        lastNotify = "NR";
                    }

                    if (!lastNotify.equals("")){
                        ORGCase.updateORGLastNotification(lastNotify, item.orgNumber);
                    }

                    partyList = CaseParty.loadORGPartiesByCase("ORG", item.orgNumber);
                    for (CaseParty party : partyList) {
                        if (party.caseRelation.equals("Representative")) {

                            if (party.emailAddress != null) {
                                int emailID = insertEmail(template, item.orgNumber, party.emailAddress);
                                insertGeneratedAttachementEmail(emailID, repdocName, true);
                                if (!attachDocName.equals("")) {
                                    insertGeneratedAttachementEmail(emailID, attachDocName, false);
                                }

                            } else if (party.address1 != null & party.city != null & party.stateCode != null && party.zipcode != null) {
                                if (!item.orgAddress1.equalsIgnoreCase(party.address1)
                                        && !item.orgCity.equalsIgnoreCase(party.city)
                                        && !item.orgState.equalsIgnoreCase(party.stateCode)
                                        && !item.orgZip.equalsIgnoreCase(party.zipcode)) {
                                    int postalID = insertPostal(template, item.orgNumber, party);
                                    insertGeneratedAttachementPostal(postalID, repdocName, true);
                                    if (!attachDocName.equals("")) {
                                        insertGeneratedAttachementPostal(postalID, attachDocName, false);
                                    }
                                }
                            }
                        }
                    }

                    if (item.orgEmail != null) {
                        int emailID = insertEmail(template, item.orgNumber, item.orgEmail);
                        insertGeneratedAttachementEmail(emailID, orgdocName, true);
                        if (!attachDocName.equals("")) {
                            insertGeneratedAttachementEmail(emailID, attachDocName, false);
                        }
                    } else if (item.orgAddress1 != null & item.orgCity != null & item.orgState != null && item.orgZip != null) {
                        int postalID = insertPostalORG(template, item);
                        insertGeneratedAttachementPostal(postalID, orgdocName, true);
                        if (!attachDocName.equals("")) {
                            insertGeneratedAttachementPostal(postalID, attachDocName, false);
                        }
                    }
                }
            } else {
                WebOptionPane.showMessageDialog(Global.root, "<html><center> Sorry, unable to locate template. <br><br>" + template.fileName + "</center></html>", "Error", WebOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private String copyAttachmentToCaseFolder(ORGCase item, String fileName) {
        String docSourcePath = Global.templatePath + File.separator + Global.activeSection + File.separator + fileName;

        String docDestPath = Global.activityPath + Global.activeSection
                + File.separatorChar + item.orgNumber + File.separatorChar;

        String destFileName = String.valueOf(new java.util.Date().getTime()) + "_" + fileName;

        try {
            Files.copy(Paths.get(docSourcePath), Paths.get(docDestPath + destFileName), StandardCopyOption.REPLACE_EXISTING);
        } catch (IOException ex) {
            SlackNotification.sendNotification(ex);
            return "";
        }
        return destFileName;
    }

    private int insertEmail(SMDSDocuments SMDSdocToGenerate, String orgNumber, String toEmail) {
        String emailBody = SMDSdocToGenerate.emailBody == null ? "" : SMDSdocToGenerate.emailBody;

        emailBody += System.lineSeparator() + System.lineSeparator()
                + StringUtilities.buildFullName(Global.activeUser.firstName, Global.activeUser.middleInitial, Global.activeUser.lastName)
                + System.lineSeparator() + (Global.activeUser.jobTitle == null ? "" : Global.activeUser.jobTitle + System.lineSeparator())
                + StringUtilities.generateDepartmentAddressBlock() + System.lineSeparator()
                + (Global.activeUser.workPhone == null ? "" : "Telephone: " + Global.activeUser.workPhone);

        EmailOut eml = new EmailOut();

        eml.section = "ORG";
        eml.caseYear = null;
        eml.caseType = "ORG";
        eml.caseMonth = null;
        eml.caseNumber = orgNumber;
        eml.to = toEmail.trim().equals("") ? null : toEmail.trim();
        eml.from = Global.activeUser.emailAddress;
        eml.cc = null;
        eml.bcc = "serbeoarchive@serb.state.oh.us";
        eml.subject = SMDSdocToGenerate.emailSubject != null ? SMDSdocToGenerate.emailSubject
                : (SMDSdocToGenerate.description == null ? "" : SMDSdocToGenerate.description);
        eml.body = emailBody;
        eml.userID = Global.activeUser.id;
        eml.suggestedSendDate = null;
        eml.okToSend = false;

        return EmailOut.insertEmail(eml);
    }

    private int insertPostal(SMDSDocuments SMDSdocToGenerate, String orgNumber, CaseParty party) {
        PostalOut post = new PostalOut();

        post.section = "ORG";
        post.caseYear = null;
        post.caseType = "ORG";
        post.caseMonth = null;
        post.caseNumber = orgNumber;
        post.person = StringUtilities.buildCasePartyNameNoPreFix(party);
        post.addressBlock = StringUtilities.buildAddressBlockWithLineBreaks(party);
        post.userID = Global.activeUser.id;
        post.suggestedSendDate = null;
        post.historyDescription = SMDSdocToGenerate.historyDescription == null ? SMDSdocToGenerate.description : SMDSdocToGenerate.historyDescription;

        return PostalOut.insertPostalOut(post);
    }

    private int insertPostalORG(SMDSDocuments SMDSdocToGenerate, ORGCase item) {
        CaseParty orgAddress = new CaseParty();

        orgAddress.address1 = item.orgAddress1;
        orgAddress.address2 = item.orgAddress2;
        orgAddress.city = item.orgCity;
        orgAddress.stateCode = item.orgState;
        orgAddress.zipcode = item.orgZip;

        PostalOut post = new PostalOut();

        post.section = "ORG";
        post.caseYear = null;
        post.caseType = "ORG";
        post.caseMonth = null;
        post.caseNumber = item.orgNumber;
        post.person = item.orgName;
        post.addressBlock = StringUtilities.buildAddressBlockWithLineBreaks(orgAddress);
        post.userID = Global.activeUser.id;
        post.suggestedSendDate = null;
        post.historyDescription = SMDSdocToGenerate.historyDescription == null ? SMDSdocToGenerate.description : SMDSdocToGenerate.historyDescription;

        return PostalOut.insertPostalOut(post);
    }

    private void insertGeneratedAttachementEmail(int emailID, String docName, boolean primary) {
        if (emailID > 0) {
            EmailOutAttachment attach = new EmailOutAttachment();

            attach.emailOutID = emailID;
            attach.fileName = docName;
            attach.primaryAttachment = primary;
            EmailOutAttachment.insertAttachment(attach);
        }
    }

    private void insertGeneratedAttachementPostal(int postalID, String docName, boolean primary) {
        if (postalID > 0) {
            PostalOutAttachment attach = new PostalOutAttachment();

            attach.PostalOutID = postalID;
            attach.fileName = docName;
            attach.primaryAttachment = primary;
            PostalOutAttachment.insertAttachment(attach);
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

        jLabel1 = new javax.swing.JLabel();
        letterComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        FYEDuringTextField = new javax.swing.JTextField();
        PostalMailTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        EMailTextField = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        NumberOfOrgsTextField = new javax.swing.JTextField();
        jLabel6 = new javax.swing.JLabel();
        CloseButton = new javax.swing.JButton();
        GenerateButton = new javax.swing.JButton();
        jLayeredPane1 = new javax.swing.JLayeredPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Generate Letters For All Organizations");

        letterComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                letterComboBoxActionPerformed(evt);
            }
        });

        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel2.setText("Letter:");

        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel3.setText("Fiscal Year Ending During:");

        FYEDuringTextField.setBackground(new java.awt.Color(238, 238, 238));
        FYEDuringTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        FYEDuringTextField.setEnabled(false);

        PostalMailTextField.setBackground(new java.awt.Color(238, 238, 238));
        PostalMailTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        PostalMailTextField.setEnabled(false);

        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("Postal Mail:");

        EMailTextField.setBackground(new java.awt.Color(238, 238, 238));
        EMailTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        EMailTextField.setEnabled(false);

        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel5.setText("E-Mail:");

        NumberOfOrgsTextField.setBackground(new java.awt.Color(238, 238, 238));
        NumberOfOrgsTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        NumberOfOrgsTextField.setEnabled(false);

        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Number of Organizations:");

        CloseButton.setText("Close");
        CloseButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                CloseButtonActionPerformed(evt);
            }
        });

        GenerateButton.setText("Generate Letters");
        GenerateButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                GenerateButtonActionPerformed(evt);
            }
        });

        jLayeredPane1.setLayout(new javax.swing.OverlayLayout(jLayeredPane1));

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "id", "Org #", "Organization Name", "Org Via", "Rep Via", "AR Last", "FS Last"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jLayeredPane1.add(jScrollPane1);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));
        jPanel1.setOpaque(false);

        jLabel7.setBackground(new java.awt.Color(255, 255, 255));
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 724, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 724, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 334, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 334, Short.MAX_VALUE))
        );

        jLayeredPane1.add(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(letterComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EMailTextField))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PostalMailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 259, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel6, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(NumberOfOrgsTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 234, Short.MAX_VALUE)
                            .addComponent(FYEDuringTextField)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(CloseButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(GenerateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLayeredPane1)
                    .addContainerGap()))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel4, jLabel5});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(letterComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(PostalMailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel5)
                            .addComponent(EMailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(FYEDuringTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(NumberOfOrgsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel6)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 384, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(CloseButton)
                    .addComponent(GenerateButton))
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addGap(171, 171, 171)
                    .addComponent(jLayeredPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 334, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap(55, Short.MAX_VALUE)))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {EMailTextField, FYEDuringTextField, NumberOfOrgsTextField, PostalMailTextField, jLabel2, jLabel3, jLabel4, jLabel5, jLabel6, letterComboBox});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void CloseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CloseButtonActionPerformed
        this.dispose();
    }//GEN-LAST:event_CloseButtonActionPerformed

    private void GenerateButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_GenerateButtonActionPerformed
        jLayeredPane1.moveToFront(jPanel1);
        GenerateButton.setEnabled(false);
        CloseButton.setEnabled(false);
        letterComboBox.setEnabled(false);
        jScrollPane1.setVisible(false);

        Thread temp = new Thread(() -> {
            System.out.println(new Date());
            generateLetters();
            System.out.println(new Date());

            jLayeredPane1.moveToBack(jPanel1);
            GenerateButton.setEnabled(true);
            CloseButton.setEnabled(true);
            letterComboBox.setEnabled(true);
            jScrollPane1.setVisible(true);
            WebOptionPane.showMessageDialog(Global.root, "<html><center>Finished Generation of all Documents</center></html>", "Done", WebOptionPane.INFORMATION_MESSAGE);
        });
        temp.start();
    }//GEN-LAST:event_GenerateButtonActionPerformed

    private void letterComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_letterComboBoxActionPerformed
        jLayeredPane1.moveToFront(jPanel1);
        GenerateButton.setEnabled(false);
        CloseButton.setEnabled(false);
        letterComboBox.setEnabled(false);
        jTable1.setVisible(false);
        jTable1.setEnabled(false);

        Thread temp = new Thread(() -> {
        processComboBoxSelection();
        enableGenerateButton();

        jLayeredPane1.moveToBack(jPanel1);
        GenerateButton.setEnabled(true);
        CloseButton.setEnabled(true);
        letterComboBox.setEnabled(true);
        jTable1.setVisible(true);
        jTable1.setEnabled(true);
        });
        temp.start();
    }//GEN-LAST:event_letterComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton CloseButton;
    private javax.swing.JTextField EMailTextField;
    private javax.swing.JTextField FYEDuringTextField;
    private javax.swing.JButton GenerateButton;
    private javax.swing.JTextField NumberOfOrgsTextField;
    private javax.swing.JTextField PostalMailTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLayeredPane jLayeredPane1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JComboBox letterComboBox;
    // End of variables declaration//GEN-END:variables
}
