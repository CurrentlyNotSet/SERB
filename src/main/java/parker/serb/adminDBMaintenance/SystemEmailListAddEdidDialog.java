/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.adminDBMaintenance;

import com.alee.laf.optionpane.WebOptionPane;
import parker.serb.Global;
import parker.serb.sql.Audit;
import parker.serb.sql.SystemEmail;

/**
 *
 * @author Andrew
 */
public class SystemEmailListAddEdidDialog extends javax.swing.JDialog {

    private int ID;
    private SystemEmail item;
    
    /**
     * Creates new form SystemEmailListAddEdidDialog
     * @param parent
     * @param modal
     * @param itemIDpassed
     */
    public SystemEmailListAddEdidDialog(java.awt.Frame parent, boolean modal, int itemIDpassed) {
        super(parent, modal);
        initComponents();
        setDefaults(itemIDpassed);
    }
    
    private void setDefaults(int itemIDpassed) {
        ID = itemIDpassed;
        if (ID > 0) {
            titleLabel.setText("Edit System Email");
            editButton.setText("Save");
            loadInformation();
        } else {
            titleLabel.setText("Add System Email");
            editButton.setText("Add");
            editButton.setEnabled(false);
            item = new SystemEmail();
        }
        this.setLocationRelativeTo(Global.root);
        this.setVisible(true);
    }
        
    private void loadInformation() {
        item = SystemEmail.getSystemEmailByID(ID);
        
        //Header Info
        SectionTextField.setText(item.section);
        UsernameTextField.setText(item.username);
        EmailTextField.setText(item.emailAddress);
        PasswordField.setText(item.password);
        
        //Incoming Info
        IncomingFolderTextField.setText(item.incomingFolder);
        IncomingPortTextField.setText(String.valueOf(item.incomingPort));
        IncomingProtocolTextField.setText(item.incomingProtocol);
        IncomingURLTextField.setText(item.incomingURL);
        
        //Outgoing Info
        OutgoingFolderTextField.setText(item.outgoingFolder);
        OutgoingPortTextField.setText(String.valueOf(item.outgoingPort));
        OutgoingProtocolTextField.setText(item.outgoingProtocol);
        OutgoingURLTextField.setText(item.outgoingURL);
        
    }
   
    private void saveInformation() {
        item.id = ID;
        
        //Header Info
        item.section = SectionTextField.getText();
        item.username = UsernameTextField.getText();
        item.emailAddress = EmailTextField.getText();
        item.password = PasswordField.getText();
        
        //Incoming Info
        item.incomingFolder = IncomingFolderTextField.getText();
        item.incomingPort = Integer.valueOf(IncomingPortTextField.getText());
        item.incomingProtocol = IncomingProtocolTextField.getText();
        item.incomingURL = IncomingURLTextField.getText();
        
        //Outgoing Info
        item.outgoingFolder = OutgoingFolderTextField.getText();
        item.outgoingPort = Integer.valueOf(OutgoingPortTextField.getText());
        item.outgoingProtocol = OutgoingProtocolTextField.getText();
        item.outgoingURL = OutgoingURLTextField.getText();
        
        if (ID > 0) {
            SystemEmail.updateSystemEmail(item);
        }
    }

    private void checkButton(){
        if (PasswordField.getText().trim().equals("")){
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

        jComboBox1 = new javax.swing.JComboBox<>();
        titleLabel = new javax.swing.JLabel();
        closeButton = new javax.swing.JButton();
        editButton = new javax.swing.JButton();
        UsernameTextField = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        SectionTextField = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        EmailTextField = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        IncomingFolderTextField = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        IncomingPortTextField = new javax.swing.JTextField();
        IncomingURLTextField = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        IncomingProtocolTextField = new javax.swing.JTextField();
        jLabel12 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        OutgoingProtocolTextField = new javax.swing.JTextField();
        jLabel14 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        OutgoingURLTextField = new javax.swing.JTextField();
        OutgoingPortTextField = new javax.swing.JTextField();
        jLabel15 = new javax.swing.JLabel();
        OutgoingFolderTextField = new javax.swing.JTextField();
        jLabel16 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        ShowPasswordCheckbox = new javax.swing.JCheckBox();
        PasswordField = new javax.swing.JPasswordField();

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

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

        UsernameTextField.setEditable(false);
        UsernameTextField.setBackground(new java.awt.Color(238, 238, 238));
        UsernameTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        UsernameTextField.setEnabled(false);
        UsernameTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                UsernameTextFieldKeyTyped(evt);
            }
        });

        jLabel7.setText("User Name:");

        jLabel2.setText("Section:");

        SectionTextField.setEditable(false);
        SectionTextField.setBackground(new java.awt.Color(238, 238, 238));
        SectionTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        SectionTextField.setEnabled(false);
        SectionTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                SectionTextFieldKeyTyped(evt);
            }
        });

        jLabel11.setText("Email:");

        EmailTextField.setEditable(false);
        EmailTextField.setBackground(new java.awt.Color(238, 238, 238));
        EmailTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        EmailTextField.setEnabled(false);
        EmailTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                EmailTextFieldKeyTyped(evt);
            }
        });

        jLabel3.setText("Password:");

        IncomingFolderTextField.setEditable(false);
        IncomingFolderTextField.setBackground(new java.awt.Color(238, 238, 238));
        IncomingFolderTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        IncomingFolderTextField.setEnabled(false);
        IncomingFolderTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                IncomingFolderTextFieldKeyTyped(evt);
            }
        });

        jLabel9.setText("Folder:");

        jLabel13.setText("Port:");

        IncomingPortTextField.setEditable(false);
        IncomingPortTextField.setBackground(new java.awt.Color(238, 238, 238));
        IncomingPortTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        IncomingPortTextField.setEnabled(false);
        IncomingPortTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                IncomingPortTextFieldKeyTyped(evt);
            }
        });

        IncomingURLTextField.setEditable(false);
        IncomingURLTextField.setBackground(new java.awt.Color(238, 238, 238));
        IncomingURLTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        IncomingURLTextField.setEnabled(false);
        IncomingURLTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                IncomingURLTextFieldKeyTyped(evt);
            }
        });

        jLabel8.setText("URL:");

        IncomingProtocolTextField.setEditable(false);
        IncomingProtocolTextField.setBackground(new java.awt.Color(238, 238, 238));
        IncomingProtocolTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        IncomingProtocolTextField.setEnabled(false);
        IncomingProtocolTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                IncomingProtocolTextFieldKeyTyped(evt);
            }
        });

        jLabel12.setText("Protocol:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Incoming:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("Outgoing:");

        OutgoingProtocolTextField.setEditable(false);
        OutgoingProtocolTextField.setBackground(new java.awt.Color(238, 238, 238));
        OutgoingProtocolTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        OutgoingProtocolTextField.setEnabled(false);
        OutgoingProtocolTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                OutgoingProtocolTextFieldKeyTyped(evt);
            }
        });

        jLabel14.setText("Protocol:");

        jLabel10.setText("URL:");

        OutgoingURLTextField.setEditable(false);
        OutgoingURLTextField.setBackground(new java.awt.Color(238, 238, 238));
        OutgoingURLTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        OutgoingURLTextField.setEnabled(false);
        OutgoingURLTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                OutgoingURLTextFieldKeyTyped(evt);
            }
        });

        OutgoingPortTextField.setEditable(false);
        OutgoingPortTextField.setBackground(new java.awt.Color(238, 238, 238));
        OutgoingPortTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        OutgoingPortTextField.setEnabled(false);
        OutgoingPortTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                OutgoingPortTextFieldKeyTyped(evt);
            }
        });

        jLabel15.setText("Port:");

        OutgoingFolderTextField.setEditable(false);
        OutgoingFolderTextField.setBackground(new java.awt.Color(238, 238, 238));
        OutgoingFolderTextField.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        OutgoingFolderTextField.setEnabled(false);
        OutgoingFolderTextField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                OutgoingFolderTextFieldKeyTyped(evt);
            }
        });

        jLabel16.setText("Folder:");

        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(IncomingFolderTextField))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(IncomingPortTextField))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(IncomingURLTextField))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(IncomingProtocolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(jLabel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 329, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 8, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OutgoingFolderTextField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OutgoingPortTextField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OutgoingURLTextField))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(OutgoingProtocolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel12, jLabel13, jLabel8, jLabel9});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel10, jLabel14, jLabel15, jLabel16});

        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(OutgoingProtocolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel14))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(OutgoingURLTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(OutgoingPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel15))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(OutgoingFolderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel16)))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IncomingProtocolTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel12))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IncomingURLTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel8))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IncomingPortTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel13))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(IncomingFolderTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel9)))
                    .addComponent(jSeparator1))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {IncomingFolderTextField, IncomingPortTextField, IncomingProtocolTextField, IncomingURLTextField, jLabel1, jLabel12, jLabel13, jLabel8, jLabel9});

        jPanel1Layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {OutgoingFolderTextField, OutgoingPortTextField, OutgoingProtocolTextField, OutgoingURLTextField, jLabel10, jLabel14, jLabel15, jLabel16, jLabel4});

        ShowPasswordCheckbox.setText("show");
        ShowPasswordCheckbox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ShowPasswordCheckboxActionPerformed(evt);
            }
        });

        PasswordField.setText("jPasswordField1");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(titleLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addComponent(closeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(editButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel11, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(EmailTextField))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(SectionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, 250, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(PasswordField)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(ShowPasswordCheckbox))))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {jLabel11, jLabel2, jLabel7});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(titleLabel)
                .addGap(30, 30, 30)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel2)
                            .addComponent(SectionTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(EmailTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel11)))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(UsernameTextField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(ShowPasswordCheckbox)
                            .addComponent(PasswordField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addGap(18, 18, 18)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 19, Short.MAX_VALUE)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(editButton)
                    .addComponent(closeButton))
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {EmailTextField, SectionTextField, UsernameTextField, jLabel11, jLabel2, jLabel7});

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

    private void SectionTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_SectionTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_SectionTextFieldKeyTyped

    private void EmailTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EmailTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_EmailTextFieldKeyTyped

    private void UsernameTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_UsernameTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_UsernameTextFieldKeyTyped

    private void IncomingURLTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IncomingURLTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_IncomingURLTextFieldKeyTyped

    private void IncomingProtocolTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IncomingProtocolTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_IncomingProtocolTextFieldKeyTyped

    private void IncomingFolderTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IncomingFolderTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_IncomingFolderTextFieldKeyTyped

    private void IncomingPortTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_IncomingPortTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_IncomingPortTextFieldKeyTyped

    private void OutgoingProtocolTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OutgoingProtocolTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_OutgoingProtocolTextFieldKeyTyped

    private void OutgoingURLTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OutgoingURLTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_OutgoingURLTextFieldKeyTyped

    private void OutgoingPortTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OutgoingPortTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_OutgoingPortTextFieldKeyTyped

    private void OutgoingFolderTextFieldKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_OutgoingFolderTextFieldKeyTyped
        checkButton();
    }//GEN-LAST:event_OutgoingFolderTextFieldKeyTyped

    private void ShowPasswordCheckboxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ShowPasswordCheckboxActionPerformed
        if (ShowPasswordCheckbox.isSelected()) {
            PasswordField.setEchoChar((char) 0);
            Audit.addAuditEntry("Admin Panel: Set Password Visible for " + EmailTextField.getText() + " for section " + SectionTextField.getText());
        } else {
            PasswordField.setEchoChar('*');
        }
    }//GEN-LAST:event_ShowPasswordCheckboxActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextField EmailTextField;
    private javax.swing.JTextField IncomingFolderTextField;
    private javax.swing.JTextField IncomingPortTextField;
    private javax.swing.JTextField IncomingProtocolTextField;
    private javax.swing.JTextField IncomingURLTextField;
    private javax.swing.JTextField OutgoingFolderTextField;
    private javax.swing.JTextField OutgoingPortTextField;
    private javax.swing.JTextField OutgoingProtocolTextField;
    private javax.swing.JTextField OutgoingURLTextField;
    private javax.swing.JPasswordField PasswordField;
    private javax.swing.JTextField SectionTextField;
    private javax.swing.JCheckBox ShowPasswordCheckbox;
    private javax.swing.JTextField UsernameTextField;
    private javax.swing.JButton closeButton;
    private javax.swing.JButton editButton;
    private javax.swing.JComboBox<String> jComboBox1;
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
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JLabel titleLabel;
    // End of variables declaration//GEN-END:variables
}
