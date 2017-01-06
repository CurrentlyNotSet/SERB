/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import com.alee.laf.optionpane.WebOptionPane;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import org.apache.commons.validator.routines.EmailValidator;
import parker.serb.Global;
import parker.serb.sql.User;
import parker.serb.sql.UserRole;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author Andrew
 */
public class UserAddEditDialog extends javax.swing.JDialog {

    private int ID;
    private User item;
    
    /**
     * Creates new form MediatorAddEdidDialog
     * @param parent
     * @param modal
     * @param itemIDpassed
     */
    public UserAddEditDialog(java.awt.Frame parent, boolean modal, int itemIDpassed) {
        super(parent, modal);
        initComponents();
        addListeners();
        setDefaults(itemIDpassed);
    }
    
    private void addListeners() {
        EmailTextField.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                editButton.setEnabled(
                    EmailValidator.getInstance().isValid(EmailTextField.getText()) ||
                    EmailTextField.getText().equals("")
                );
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                editButton.setEnabled(
                    EmailValidator.getInstance().isValid(EmailTextField.getText()) ||
                    EmailTextField.getText().equals("")
                );
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                editButton.setEnabled(
                    EmailValidator.getInstance().isValid(EmailTextField.getText()) ||
                    EmailTextField.getText().equals("")
                );
            }
        });
    }

    private void setDefaults(int itemIDpassed) {
        ID = itemIDpassed;
        setTableDefault();
        loadDefaultSectionComboBox();
        if (ID > 0) {
            titleLabel.setText("Edit User");
            editButton.setText("Save");
            AddAccessButton.setEnabled(true);
            loadInformation();
        } else {
            titleLabel.setText("Add User");
            editButton.setText("Add");
            PasswordResetCheckBox.setEnabled(false);
            PasswordResetCheckBox.setSelected(true);
            editButton.setEnabled(false);
            item = new User();
        }
        this.setLocationRelativeTo(Global.root);
        this.setVisible(true);
    }
    
    private void setTableDefault() {
        //ID
        UserRoleTable.getColumnModel().getColumn(0).setMinWidth(0);
        UserRoleTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        UserRoleTable.getColumnModel().getColumn(0).setMaxWidth(0);
    }
    
    private void loadDefaultSectionComboBox() {
        defaultSectionComboBox.removeAllItems();
        defaultSectionComboBox.addItem("");
        defaultSectionComboBox.addItem("Civil Service Commission");
        defaultSectionComboBox.addItem("CMDS");
        defaultSectionComboBox.addItem("Hearings");
        defaultSectionComboBox.addItem("MED");
        defaultSectionComboBox.addItem("ORG");
        defaultSectionComboBox.addItem("REP");
        defaultSectionComboBox.addItem("ULP");
    }
        
    private void loadInformation() {
        item = User.findUserByID(ID);
        
        //left side load
        UserNameTextField.setText(item.username);
        FirstNameTextField.setText(item.firstName);
        MiddleInitialTextField.setText(item.middleInitial);
        LastNameTextField.setText(item.lastName);
        InitialsTextField.setText(item.initials);
        JobTitleTextField.setText(item.jobTitle);
        EmailTextField.setText(item.emailAddress);
        PhoneTextField.setText(NumberFormatService.convertStringToPhoneNumber(item.workPhone));
        defaultSectionComboBox.setSelectedItem(item.defaultSection);
        
        //right side load
        ActiveLoginCheckBox.setSelected(item.activeLogIn);
        PasswordResetCheckBox.setSelected(item.passwordReset);
        InvestigatorCheckBox.setSelected(item.investigator);
        
        //Docketing Permissions
        CMDSDocketingCheckBox.setSelected(item.CMDSDocketing);
        CSCDocketingCheckBox.setSelected(item.CSCDocketing);
        MEDDocketingCheckBox.setSelected(item.MEDDocketing);
        ORGDocketingCheckBox.setSelected(item.ORGDocketing);
        REPDocketingCheckBox.setSelected(item.REPDocketing);
        ULPDocketingCheckBox.setSelected(item.ULPDocketing);
        
        //Case Worker permissions
        CMDSWorkerCheckBox.setSelected(item.CMDSCaseWorker);
        CSCWorkerCheckBox.setSelected(item.CSCCaseWorker);
        HearingsWorkerCheckBox.setSelected(item.HearingsCaseWorker);
        MEDWorkerCheckBox.setSelected(item.MEDCaseWorker);
        ORGWorkerCheckBox.setSelected(item.ORGCaseWorker);
        REPWorkerCheckBox.setSelected(item.REPCaseWorker);
        ULPWorkerCheckBox.setSelected(item.ULPCaseWorker);
        loadRolesTable();
    }
   
    private void loadRolesTable() {
        List<UserRole> roleList = UserRole.loadRolesByUser(ID);
        
        DefaultTableModel model = (DefaultTableModel) UserRoleTable.getModel();
        model.setRowCount(0);
        
        for (UserRole role : roleList) {

            model.addRow(new Object[]{
                role.roleID,
                role.roleName              
            });
        }
    }
    
    private void saveInformation() {
        
        item.id = ID;
        item.firstName = FirstNameTextField.getText().trim();
        item.middleInitial = MiddleInitialTextField.getText().trim();
        item.lastName = LastNameTextField.getText().trim();
        item.workPhone = NumberFormatService.convertPhoneNumberToString(PhoneTextField.getText().trim());
        item.emailAddress = EmailTextField.getText().trim();
        item.username = UserNameTextField.getText().trim();
        item.activeLogIn = ActiveLoginCheckBox.isSelected();
        item.passwordReset = PasswordResetCheckBox.isSelected();
        item.defaultSection = defaultSectionComboBox.getSelectedItem() == null ? "" : defaultSectionComboBox.getSelectedItem().toString();
        item.ULPCaseWorker = ULPWorkerCheckBox.isSelected();
        item.REPCaseWorker = REPWorkerCheckBox.isSelected();
        item.ULPDocketing = ULPDocketingCheckBox.isSelected();
        item.REPDocketing = REPDocketingCheckBox.isSelected();
        item.initials = InitialsTextField.getText().trim();
        item.investigator = InvestigatorCheckBox.isSelected();
        item.jobTitle = JobTitleTextField.getText().trim();
        item.MEDCaseWorker = MEDWorkerCheckBox.isSelected();
        item.ORGCaseWorker = ORGWorkerCheckBox.isSelected();
        item.CSCCaseWorker = CSCWorkerCheckBox.isSelected();
        item.CMDSCaseWorker = CMDSWorkerCheckBox.isSelected();
        item.HearingsCaseWorker = HearingsWorkerCheckBox.isSelected();
        item.ORGDocketing = ORGDocketingCheckBox.isSelected();
        item.MEDDocketing = MEDDocketingCheckBox.isSelected();
        item.CSCDocketing = CSCDocketingCheckBox.isSelected();
        item.CMDSDocketing = CMDSDocketingCheckBox.isSelected();
                
        if (ID > 0) {
            User.updateUser(item);
            this.dispose();
        } else {
            String[] returnedItems = User.createUser(item);
            ID = Integer.valueOf(returnedItems[0]);
            setDefaults(ID);
            if (ID > 0) {
                WebOptionPane.showMessageDialog(this,
                        "<html><center>Temporary Password Created:<br><br>" + returnedItems[1] + "</center></html>",
                        "Temporary Password", WebOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    private void checkButton(){
        if (FirstNameTextField.getText().trim().equals("") ||
                LastNameTextField.getText().trim().equals("")){
            editButton.setEnabled(false);
        } else {
            editButton.setEnabled(true);
        }
    }
    
    private void removeRoleButtonAction(){
        if (UserRoleTable.getSelectedRow() > -1) {
            int roleID = (int) UserRoleTable.getValueAt(UserRoleTable.getSelectedRow(), 0);
            
            UserRole.removeRole(ID, roleID);
        }
        loadRolesTable();
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
        editButton = new javax.swing.JButton();
        PhoneTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        EmailTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        FirstNameTextField = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        LastNameTextField = new javax.swing.JTextField();
        MiddleInitialTextField = new javax.swing.JTextField();
        UserNameTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        ActiveLoginCheckBox = new javax.swing.JCheckBox();
        PasswordResetCheckBox = new javax.swing.JCheckBox();
        ULPDocketingCheckBox = new javax.swing.JCheckBox();
        REPDocketingCheckBox = new javax.swing.JCheckBox();
        MEDDocketingCheckBox = new javax.swing.JCheckBox();
        ORGDocketingCheckBox = new javax.swing.JCheckBox();
        CSCDocketingCheckBox = new javax.swing.JCheckBox();
        CMDSDocketingCheckBox = new javax.swing.JCheckBox();
        ULPWorkerCheckBox = new javax.swing.JCheckBox();
        CMDSWorkerCheckBox = new javax.swing.JCheckBox();
        CSCWorkerCheckBox = new javax.swing.JCheckBox();
        ORGWorkerCheckBox = new javax.swing.JCheckBox();
        MEDWorkerCheckBox = new javax.swing.JCheckBox();
        REPWorkerCheckBox = new javax.swing.JCheckBox();
        HearingsWorkerCheckBox = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        defaultSectionComboBox = new javax.swing.JComboBox<>();
        jLabel10 = new javax.swing.JLabel();
        InitialsTextField = new javax.swing.JTextField();
        InvestigatorCheckBox = new javax.swing.JCheckBox();
        jLabel12 = new javax.swing.JLabel();
        JobTitleTextField = new javax.swing.JTextField();
        jSeparator2 = new javax.swing.JSeparator();
        jScrollPane1 = new javax.swing.JScrollPane();
        UserRoleTable = new javax.swing.JTable();
        AddAccessButton = new javax.swing.JButton();
        RemoveAccessButton = new javax.swing.JButton();
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

        editButton.setText("<<EDIT>>");
        editButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                editButtonActionPerformed(evt);
            }
        });

        PhoneTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel8.setText("Phone:");

        EmailTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jLabel7.setText("Email:");

        jLabel2.setText("First Name:");

        FirstNameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        FirstNameTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                FirstNameTextFieldCaretUpdate(evt);
            }
        });

        jLabel4.setText("Middle Initial:");

        jLabel11.setText("Last Name:");

        LastNameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        LastNameTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                LastNameTextFieldCaretUpdate(evt);
            }
        });

        MiddleInitialTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        UserNameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        UserNameTextField.addCaretListener(new javax.swing.event.CaretListener() {
            public void caretUpdate(javax.swing.event.CaretEvent evt) {
                UserNameTextFieldCaretUpdate(evt);
            }
        });

        jLabel3.setText("User Name:");

        ActiveLoginCheckBox.setText("Active Login");

        PasswordResetCheckBox.setText("Password Reset");

        ULPDocketingCheckBox.setText("ULP Docketing");

        REPDocketingCheckBox.setText("REP Docketing");

        MEDDocketingCheckBox.setText("MED Docketing");

        ORGDocketingCheckBox.setText("ORG Docketing");

        CSCDocketingCheckBox.setText("CSC Docketing");

        CMDSDocketingCheckBox.setText("CMDS Docketing");

        ULPWorkerCheckBox.setText("ULP Worker");

        CMDSWorkerCheckBox.setText("CMDS Worker");

        CSCWorkerCheckBox.setText("CSC Worker");

        ORGWorkerCheckBox.setText("ORG Worker");

        MEDWorkerCheckBox.setText("MED Worker");

        REPWorkerCheckBox.setText("REP Worker");

        HearingsWorkerCheckBox.setText("Hearings Worker");

        jLabel9.setText("Default Section:");

        jLabel10.setText("Initials:");

        InitialsTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        InvestigatorCheckBox.setText("Investigator");

        jLabel12.setText("Job Title:");

        JobTitleTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));

        jSeparator2.setOrientation(javax.swing.SwingConstants.VERTICAL);

        UserRoleTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "roleID", "Tab Access"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        UserRoleTable.getTableHeader().setReorderingAllowed(false);
        UserRoleTable.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                UserRoleTableMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(UserRoleTable);
        if (UserRoleTable.getColumnModel().getColumnCount() > 0) {
            UserRoleTable.getColumnModel().getColumn(0).setResizable(false);
            UserRoleTable.getColumnModel().getColumn(1).setResizable(false);
        }

        AddAccessButton.setText("Add Access");
        AddAccessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddAccessButtonActionPerformed(evt);
            }
        });

        RemoveAccessButton.setText("Remove Access");
        RemoveAccessButton.setEnabled(false);
        RemoveAccessButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RemoveAccessButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jSeparator1)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(JobTitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(InitialsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(defaultSectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(FirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(LastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(PhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(UserNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 325, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MiddleInitialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(ActiveLoginCheckBox)
                                .addGap(18, 18, 18)
                                .addComponent(PasswordResetCheckBox)
                                .addGap(18, 18, 18)
                                .addComponent(InvestigatorCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 103, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(RemoveAccessButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(AddAccessButton, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(CMDSWorkerCheckBox)
                            .addComponent(CMDSDocketingCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(CSCDocketingCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(CSCWorkerCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(HearingsWorkerCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(MEDWorkerCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 107, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(MEDDocketingCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(ORGDocketingCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ORGWorkerCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(REPWorkerCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(REPDocketingCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 104, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(ULPWorkerCheckBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(ULPDocketingCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 105, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel11, jLabel12, jLabel2, jLabel3, jLabel4, jLabel7, jLabel8, jLabel9});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {CMDSDocketingCheckBox, CMDSWorkerCheckBox, CSCDocketingCheckBox, CSCWorkerCheckBox, HearingsWorkerCheckBox, ULPDocketingCheckBox, ULPWorkerCheckBox});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {REPDocketingCheckBox, REPWorkerCheckBox});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {ORGDocketingCheckBox, ORGWorkerCheckBox});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {MEDDocketingCheckBox, MEDWorkerCheckBox});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(titleLabel)
                        .addGap(30, 30, 30)
                        .addComponent(jLabel3)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel4)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel10)
                        .addGap(42, 42, 42)
                        .addComponent(jLabel7)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel8)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jLabel9))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(ActiveLoginCheckBox)
                                .addComponent(PasswordResetCheckBox)
                                .addComponent(InvestigatorCheckBox))
                            .addGap(18, 18, 18)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(AddAccessButton, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(RemoveAccessButton, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 203, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(UserNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(FirstNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(MiddleInitialTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(LastNameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(InitialsTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(JobTitleTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel12))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(PhoneTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(defaultSectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jSeparator2, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 268, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(CMDSDocketingCheckBox)
                    .addComponent(CSCDocketingCheckBox)
                    .addComponent(ORGDocketingCheckBox)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(REPDocketingCheckBox)
                        .addComponent(ULPDocketingCheckBox))
                    .addComponent(MEDDocketingCheckBox))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(HearingsWorkerCheckBox)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(CMDSWorkerCheckBox)
                        .addComponent(CSCWorkerCheckBox))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(MEDWorkerCheckBox)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(ORGWorkerCheckBox)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(REPWorkerCheckBox)
                                .addComponent(ULPWorkerCheckBox)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 30, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {EmailTextField, FirstNameTextField, LastNameTextField, MiddleInitialTextField, PhoneTextField, jLabel10, jLabel11, jLabel12, jLabel2, jLabel3, jLabel4, jLabel7, jLabel8, jLabel9});

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {CMDSDocketingCheckBox, CMDSWorkerCheckBox, CSCDocketingCheckBox, CSCWorkerCheckBox, HearingsWorkerCheckBox, MEDDocketingCheckBox, MEDWorkerCheckBox, ORGDocketingCheckBox, ORGWorkerCheckBox, REPDocketingCheckBox, REPWorkerCheckBox, ULPDocketingCheckBox, ULPWorkerCheckBox});

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
    }//GEN-LAST:event_editButtonActionPerformed

    private void FirstNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_FirstNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_FirstNameTextFieldCaretUpdate

    private void LastNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_LastNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_LastNameTextFieldCaretUpdate

    private void UserNameTextFieldCaretUpdate(javax.swing.event.CaretEvent evt) {//GEN-FIRST:event_UserNameTextFieldCaretUpdate
        checkButton();
    }//GEN-LAST:event_UserNameTextFieldCaretUpdate

    private void AddAccessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddAccessButtonActionPerformed
        if (ID > 0){
            new UserAddEditUserRoleDialog(Global.root, true, ID);
            loadRolesTable();
        } else {
            WebOptionPane.showMessageDialog(this, "<html><center> Sorry, unable to add role until user has been created.</center></html>", "Warning", WebOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_AddAccessButtonActionPerformed

    private void UserRoleTableMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_UserRoleTableMouseClicked
        if (UserRoleTable.getSelectedRow() > -1) {
            RemoveAccessButton.setEnabled(true);
        } else {
            RemoveAccessButton.setEnabled(false);
        }
    }//GEN-LAST:event_UserRoleTableMouseClicked

    private void RemoveAccessButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RemoveAccessButtonActionPerformed
        removeRoleButtonAction();
    }//GEN-LAST:event_RemoveAccessButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox ActiveLoginCheckBox;
    private javax.swing.JButton AddAccessButton;
    private javax.swing.JCheckBox CMDSDocketingCheckBox;
    private javax.swing.JCheckBox CMDSWorkerCheckBox;
    private javax.swing.JCheckBox CSCDocketingCheckBox;
    private javax.swing.JCheckBox CSCWorkerCheckBox;
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JTextField FirstNameTextField;
    private javax.swing.JCheckBox HearingsWorkerCheckBox;
    private javax.swing.JTextField InitialsTextField;
    private javax.swing.JCheckBox InvestigatorCheckBox;
    private javax.swing.JTextField JobTitleTextField;
    private javax.swing.JTextField LastNameTextField;
    private javax.swing.JCheckBox MEDDocketingCheckBox;
    private javax.swing.JCheckBox MEDWorkerCheckBox;
    private javax.swing.JTextField MiddleInitialTextField;
    private javax.swing.JCheckBox ORGDocketingCheckBox;
    private javax.swing.JCheckBox ORGWorkerCheckBox;
    private javax.swing.JCheckBox PasswordResetCheckBox;
    private javax.swing.JTextField PhoneTextField;
    private javax.swing.JCheckBox REPDocketingCheckBox;
    private javax.swing.JCheckBox REPWorkerCheckBox;
    private javax.swing.JButton RemoveAccessButton;
    private javax.swing.JCheckBox ULPDocketingCheckBox;
    private javax.swing.JCheckBox ULPWorkerCheckBox;
    private javax.swing.JTextField UserNameTextField;
    private javax.swing.JTable UserRoleTable;
    private javax.swing.JButton closeButton;
    private javax.swing.JComboBox<String> defaultSectionComboBox;
    private javax.swing.JButton editButton;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
