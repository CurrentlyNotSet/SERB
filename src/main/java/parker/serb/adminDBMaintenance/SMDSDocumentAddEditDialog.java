/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import com.alee.laf.optionpane.WebOptionPane;
import parker.serb.Global;
import parker.serb.sql.SMDSDocuments;

/**
 *
 * @author Andrew
 */
public class SMDSDocumentAddEditDialog extends javax.swing.JDialog {

    private int ID;
    private SMDSDocuments item;

    /**
     * Creates new form CMDSStatusTypeAddEditDialog
     * @param parent
     * @param modal
     * @param itemIDpassed
     */
    public SMDSDocumentAddEditDialog(java.awt.Frame parent, boolean modal, int itemIDpassed) {
        super(parent, modal);
        initComponents();
        setDefaults(itemIDpassed);
    }

    private void setDefaults(int itemIDpassed) {
        ID = itemIDpassed;
        loadSectionComboBox();
        loadParameterComboBox();
        if (ID > 0) {
            titleLabel.setText("Edit Document");
            editButton.setText("Save");
            loadInformation();
        } else {
            titleLabel.setText("Add Document");
            editButton.setText("Add");
            editButton.setEnabled(false);
            item = new SMDSDocuments();
        }
        this.setLocationRelativeTo(Global.root);
        this.setVisible(true);
    }

    private void loadSectionComboBox() {
        sectionComboBox.removeAllItems();
        sectionComboBox.addItem("");
        sectionComboBox.addItem("ALL");
        sectionComboBox.addItem("CSC");
        sectionComboBox.addItem("HRG");
        sectionComboBox.addItem("MED");
        sectionComboBox.addItem("ORG");
        sectionComboBox.addItem("REP");
        sectionComboBox.addItem("ULP");
    }

    private void loadParameterComboBox() {
        parametersComboBox.removeAllItems();
        parametersComboBox.addItem("");
        parametersComboBox.addItem("ActivityType");
        parametersComboBox.addItem("ActivityType, Year");
        parametersComboBox.addItem("AddressSearch");
        parametersComboBox.addItem("begin date, end date");
        parametersComboBox.addItem("begin date, end date, Conciliator");
        parametersComboBox.addItem("begin date, end date, EmployerType");
        parametersComboBox.addItem("begin date, end date, Fact Finder");
        parametersComboBox.addItem("begin date, end date, InvestigatorID");
        parametersComboBox.addItem("begin date, end date, LikeString");
        parametersComboBox.addItem("begin date, end date, Mediator");
        parametersComboBox.addItem("begin date, end date, SectionUserID");
        parametersComboBox.addItem("begin date, end date, String");
        parametersComboBox.addItem("begin date, end date, UserID");
        parametersComboBox.addItem("caseNumber");
        parametersComboBox.addItem("Charging Party, Charged Party");
        parametersComboBox.addItem("date");
        parametersComboBox.addItem("EmployerID");
        parametersComboBox.addItem("groupNumber");
        parametersComboBox.addItem("InvestigatorID");
        parametersComboBox.addItem("month");
        parametersComboBox.addItem("Month, Year");
        parametersComboBox.addItem("NameSearch");
        parametersComboBox.addItem("OrgNumber");
        parametersComboBox.addItem("UserID");
        parametersComboBox.addItem("SectionUserID");
        parametersComboBox.addItem("Year");
        parametersComboBox.addItem("Year, InvestigatorID");
    }

    private void loadInformation() {
        item = SMDSDocuments.getDocumentByID(ID);

        sectionComboBox.setSelectedItem(item.section);
        typeTextField.setText(item.type);
        descriptionTextField.setText(item.description);
        fileNameTextField.setText(item.fileName);
        dueDateTextField.setText(String.valueOf(item.dueDate));
        groupTextField.setText(item.group);
        historyFileNameTextField.setText(item.historyFileName);
        historyDescriptionTextField.setText(item.historyDescription);
        emailSubjectTextField.setText(item.emailSubject);
        parametersComboBox.setSelectedItem(item.parameters);
        emailBodyTextArea.setText(item.emailBody);
        sortOrderTextField.setText(String.valueOf(item.sortOrder));
    }

    private void saveInformation() {
        item.id = ID;
        item.section = sectionComboBox.getSelectedItem().toString();
        item.type = typeTextField.getText().trim();
        item.description = descriptionTextField.getText().trim();
        item.fileName = fileNameTextField.getText().trim();

        if (!dueDateTextField.getText().replaceAll("[^0-9]", "").equals("")){
            item.dueDate = Integer.parseInt(dueDateTextField.getText().trim());
        } else {
            item.dueDate = -1;
        }
        item.group = groupTextField.getText().trim();
        item.historyFileName = historyFileNameTextField.getText().trim();
        item.historyDescription = historyDescriptionTextField.getText().trim();
        item.emailSubject = emailSubjectTextField.getText().trim();
        item.parameters = parametersComboBox.getSelectedItem().toString();
        item.emailBody = emailBodyTextArea.getText().trim();
        if (!sortOrderTextField.getText().replaceAll("[^0-9]", "").equals("")){
            item.sortOrder = Double.parseDouble(sortOrderTextField.getText().trim());
        } else {
            item.dueDate = -1;
        }

        item.CHDCHG = "";
        item.questionsFileName = "";

        if (ID > 0){
            SMDSDocuments.updateDocument(item);
        } else {
            SMDSDocuments.createDocument(item);
        }
    }

    private void checkButton(){
        if (sectionComboBox.getSelectedItem().toString().trim().equals("") ||
                typeTextField.getText().trim().equals("") ||
                descriptionTextField.getText().trim().equals("") ||
                fileNameTextField.getText().trim().equals("")){
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
        fileNameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        editButton = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();
        typeTextField = new javax.swing.JTextField();
        sectionComboBox = new javax.swing.JComboBox<>();
        jLabel7 = new javax.swing.JLabel();
        descriptionTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        dueDateTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        groupTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        historyFileNameTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        historyDescriptionTextField = new javax.swing.JTextField();
        emailSubjectTextField = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        emailBodyTextArea = new javax.swing.JTextArea();
        jLabel15 = new javax.swing.JLabel();
        parametersComboBox = new javax.swing.JComboBox<>();
        sortOrderTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();

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

        fileNameTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        fileNameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        fileNameTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                fileNameTextFieldCaretUpdate(evt);
            }
        });

        jLabel4.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel4.setText("File Name:");

        editButton.setText("<<EDIT>>");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        jLabel6.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel6.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel6.setText("Type:");

        typeTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        typeTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        typeTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                typeTextFieldCaretUpdate(evt);
            }
        });

        sectionComboBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                sectionComboBoxActionPerformed(evt);
            }
        });

        jLabel7.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel7.setText("Section:");

        descriptionTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        descriptionTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        descriptionTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                descriptionTextFieldCaretUpdate(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel8.setText("Description:");

        dueDateTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        dueDateTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        dueDateTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                dueDateTextFieldCaretUpdate(evt);
            }
        });

        jLabel9.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel9.setText("Due Date:");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel10.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel10.setText("Group:");

        groupTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        groupTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        groupTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                groupTextFieldCaretUpdate(evt);
            }
        });

        jLabel11.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel11.setText("History File Name:");

        historyFileNameTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        historyFileNameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        historyFileNameTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                historyFileNameTextFieldCaretUpdate(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel12.setText("History Description:");

        historyDescriptionTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        historyDescriptionTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        historyDescriptionTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                historyDescriptionTextFieldCaretUpdate(evt);
            }
        });

        emailSubjectTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        emailSubjectTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        emailSubjectTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                emailSubjectTextFieldCaretUpdate(evt);
            }
        });

        jLabel13.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel13.setText("Email Subject:");

        jLabel14.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel14.setText("Email Body:");

        emailBodyTextArea.setColumns(20);
        emailBodyTextArea.setRows(5);
        jScrollPane1.setViewportView(emailBodyTextArea);

        jLabel15.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel15.setText("Parameters:");

        sortOrderTextField.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        sortOrderTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        sortOrderTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                sortOrderTextFieldCaretUpdate(evt);
            }
        });

        jLabel16.setFont(new java.awt.Font("Arial", 0, 11)); // NOI18N
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
        jLabel16.setText("Sort Order:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText(".DOCX Templates");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText(".JASPER Reports");

        jLabel3.setText("(Number of Days)");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, 110, Short.MAX_VALUE)
                                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(jLabel10, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(jLabel7, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(jLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                                    .addComponent(jLabel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(historyDescriptionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(historyFileNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(fileNameTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(descriptionTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(sectionComboBox, 0, 300, Short.MAX_VALUE)
                                    .addComponent(typeTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(sortOrderTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE)
                                    .addComponent(groupTextField, javax.swing.GroupLayout.DEFAULT_SIZE, 300, Short.MAX_VALUE))))
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addComponent(parametersComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jLabel14, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE))
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                                                .addComponent(dueDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 183, Short.MAX_VALUE))
                                            .addComponent(emailSubjectTextField, javax.swing.GroupLayout.Alignment.LEADING))))
                                .addGap(0, 20, Short.MAX_VALUE))
                            .addComponent(jSeparator2)
                            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel15, jLabel16, jLabel4, jLabel6, jLabel7, jLabel8, jLabel9});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {descriptionTextField, fileNameTextField, groupTextField, historyDescriptionTextField, historyFileNameTextField, sectionComboBox, sortOrderTextField, typeTextField});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(sectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel6)
                                    .addComponent(typeTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel8)
                                    .addComponent(descriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel10)
                                    .addComponent(groupTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel16)
                                    .addComponent(sortOrderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(fileNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel11)
                                    .addComponent(historyFileNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel12)
                                    .addComponent(historyDescriptionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 34, Short.MAX_VALUE)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(editButton)
                                    .addComponent(closeButton))
                                .addContainerGap())
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel9)
                                    .addComponent(dueDateTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel13)
                                    .addComponent(emailSubjectTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel14)
                                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(10, 10, 10)
                                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel2)
                                .addGap(15, 15, 15)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel15)
                                    .addComponent(parametersComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 252, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))))
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {descriptionTextField, dueDateTextField, fileNameTextField, groupTextField, historyDescriptionTextField, historyFileNameTextField, jLabel10, jLabel11, jLabel12, jLabel13, jLabel14, jLabel16, jLabel3, jLabel4, jLabel6, jLabel7, jLabel8, jLabel9, sectionComboBox, sortOrderTextField, typeTextField});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {emailSubjectTextField, jLabel15, parametersComboBox});

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

    private void fileNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_fileNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_fileNameTextFieldCaretUpdate

    private void typeTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_typeTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_typeTextFieldCaretUpdate

    private void descriptionTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_descriptionTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_descriptionTextFieldCaretUpdate

    private void dueDateTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_dueDateTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_dueDateTextFieldCaretUpdate

    private void groupTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_groupTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_groupTextFieldCaretUpdate

    private void historyFileNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_historyFileNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_historyFileNameTextFieldCaretUpdate

    private void historyDescriptionTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_historyDescriptionTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_historyDescriptionTextFieldCaretUpdate

    private void emailSubjectTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_emailSubjectTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_emailSubjectTextFieldCaretUpdate

    private void sortOrderTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_sortOrderTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_sortOrderTextFieldCaretUpdate

    private void sectionComboBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_sectionComboBoxActionPerformed
        checkButton();
    }//GEN-LAST:event_sectionComboBoxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton closeButton;
    private javax.swing.JTextField descriptionTextField;
    private javax.swing.JTextField dueDateTextField;
    private javax.swing.JButton editButton;
    private javax.swing.JTextArea emailBodyTextArea;
    private javax.swing.JTextField emailSubjectTextField;
    private javax.swing.JTextField fileNameTextField;
    private javax.swing.JTextField groupTextField;
    private javax.swing.JTextField historyDescriptionTextField;
    private javax.swing.JTextField historyFileNameTextField;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JComboBox<String> parametersComboBox;
    private javax.swing.JComboBox<String> sectionComboBox;
    private javax.swing.JTextField sortOrderTextField;
    private javax.swing.JLabel titleLabel;
    private javax.swing.JTextField typeTextField;
    // End of variables declaration//GEN-END:variables
}
