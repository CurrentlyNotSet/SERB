package parker.serb.adminDBMaintenance;

import parker.serb.Global;

/**
 * @author andrew.schmidt
 */
public class AdminMainMenuPanel extends javax.swing.JDialog {

    public AdminMainMenuPanel(java.awt.Frame parent, boolean modal) {
        super(parent, modal);
        initComponents();
        this.setLocationRelativeTo(parent);
        this.setVisible(true);
    }

    /** This method is called from within the constructor to
     * initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is
     * always regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        FFConciliatorsButton = new javax.swing.JButton();
        MediatorsButton = new javax.swing.JButton();
        HistoryTypesButton = new javax.swing.JButton();
        PartyButton = new javax.swing.JButton();
        SystemSERBButton = new javax.swing.JButton();
        UsersButton = new javax.swing.JButton();
        SERBExecButton = new javax.swing.JButton();
        SystemPBRButton = new javax.swing.JButton();
        PBRExecsButton = new javax.swing.JButton();
        REPRecsButton = new javax.swing.JButton();
        REPStatusOptionsButton = new javax.swing.JButton();
        ULPRecsButton = new javax.swing.JButton();
        BoardActionTypeButton = new javax.swing.JButton();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jLabel8 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        PreFixesButton = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setResizable(false);

        jLabel1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Case Management and Docketing System");

        jLabel2.setFont(new java.awt.Font("Tahoma", 0, 13)); // NOI18N
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Reference Table Maintenance");

        FFConciliatorsButton.setText("<html><center>Fact Finders &<br>Conciliators</center></html>");
        FFConciliatorsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                FFConciliatorsButtonActionPerformed(evt);
            }
        });

        MediatorsButton.setText("Mediators");
        MediatorsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                MediatorsButtonActionPerformed(evt);
            }
        });

        HistoryTypesButton.setText("Doc History Types");
        HistoryTypesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                HistoryTypesButtonActionPerformed(evt);
            }
        });

        PartyButton.setText("Party Info");
        PartyButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PartyButtonActionPerformed(evt);
            }
        });

        SystemSERBButton.setText("SERB System Info");
        SystemSERBButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SystemSERBButtonActionPerformed(evt);
            }
        });

        UsersButton.setText("Users");
        UsersButton.setEnabled(false);
        UsersButton.setMargin(new java.awt.Insets(2, 2, 2, 2));
        UsersButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                UsersButtonActionPerformed(evt);
            }
        });

        SERBExecButton.setText("SERB Board");
        SERBExecButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SERBExecButtonActionPerformed(evt);
            }
        });

        SystemPBRButton.setText("PBR System Info");
        SystemPBRButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                SystemPBRButtonActionPerformed(evt);
            }
        });

        PBRExecsButton.setText("PBR Board");
        PBRExecsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PBRExecsButtonActionPerformed(evt);
            }
        });

        REPRecsButton.setText("<html><center>REP<br>Recommendations</center></html>");
        REPRecsButton.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        REPRecsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                REPRecsButtonActionPerformed(evt);
            }
        });

        REPStatusOptionsButton.setText("REP Status Options");
        REPStatusOptionsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                REPStatusOptionsButtonActionPerformed(evt);
            }
        });

        ULPRecsButton.setText("<html><center>ULP<br>Recommendations</center></html>");
        ULPRecsButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ULPRecsButtonActionPerformed(evt);
            }
        });

        BoardActionTypeButton.setText("Board Action Type");
        BoardActionTypeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BoardActionTypeButtonActionPerformed(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel3.setText("REP");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("MED");

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel5.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel5.setText("ULP");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel7.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel7.setText("General");

        jButton1.setText("Close");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setText("CMDS");

        PreFixesButton.setText("Name Prefixes");
        PreFixesButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PreFixesButtonActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addGroup(layout.createSequentialGroup()
                .addGap(10, 10, 10)
                .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(10, 10, 10))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(411, 411, 411)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(UsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(PreFixesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(SERBExecButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(SystemSERBButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel8, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(SystemPBRButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(PBRExecsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(FFConciliatorsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(MediatorsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(PartyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(HistoryTypesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(BoardActionTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(REPStatusOptionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(REPRecsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(ULPRecsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 130, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 867, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(24, Short.MAX_VALUE))
        );

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {HistoryTypesButton, PartyButton, REPRecsButton, SERBExecButton, SystemSERBButton, ULPRecsButton, UsersButton});

        layout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {PBRExecsButton, SystemPBRButton});

        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addGap(40, 40, 40)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(jLabel4)
                        .addComponent(jLabel7)
                        .addComponent(jLabel8))
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(UsersButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SystemPBRButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(SystemSERBButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(MediatorsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(REPStatusOptionsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(PBRExecsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(SERBExecButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(PreFixesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(REPRecsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(ULPRecsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(FFConciliatorsButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BoardActionTypeButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(PartyButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(HistoryTypesButton, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 55, Short.MAX_VALUE)
                .addComponent(jButton1)
                .addContainerGap())
        );

        layout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {BoardActionTypeButton, FFConciliatorsButton, HistoryTypesButton, MediatorsButton, PBRExecsButton, PartyButton, REPRecsButton, REPStatusOptionsButton, SERBExecButton, SystemPBRButton, SystemSERBButton, ULPRecsButton, UsersButton});

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void HistoryTypesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_HistoryTypesButtonActionPerformed
        new ActivityTypeSearchDialog(Global.root, true);
    }//GEN-LAST:event_HistoryTypesButtonActionPerformed

    private void FFConciliatorsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_FFConciliatorsButtonActionPerformed
        new FactFinderConciliatorSearchDialog(Global.root, true);
    }//GEN-LAST:event_FFConciliatorsButtonActionPerformed

    private void MediatorsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_MediatorsButtonActionPerformed
        new MediatorSearchDialog(Global.root, true);
    }//GEN-LAST:event_MediatorsButtonActionPerformed

    private void PartyButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PartyButtonActionPerformed
        new PartySearchDialog(Global.root, true);
    }//GEN-LAST:event_PartyButtonActionPerformed

    private void SystemSERBButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SystemSERBButtonActionPerformed
        new AdministrationInfoAddEdidDialog(Global.root, true, "SERB");
    }//GEN-LAST:event_SystemSERBButtonActionPerformed

    private void SERBExecButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SERBExecButtonActionPerformed
        new BoardExecSearchDialog(Global.root, true, "SERB");
    }//GEN-LAST:event_SERBExecButtonActionPerformed

    private void UsersButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_UsersButtonActionPerformed
        new UserSearchDialog(Global.root, true);
    }//GEN-LAST:event_UsersButtonActionPerformed

    private void SystemPBRButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_SystemPBRButtonActionPerformed
        new AdministrationInfoAddEdidDialog(Global.root, true, "SPBR");
    }//GEN-LAST:event_SystemPBRButtonActionPerformed

    private void PBRExecsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PBRExecsButtonActionPerformed
        new BoardExecSearchDialog(Global.root, true, "SPBR");
    }//GEN-LAST:event_PBRExecsButtonActionPerformed

    private void REPRecsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_REPRecsButtonActionPerformed
        new REPRecsSearchDialog(Global.root, true);
    }//GEN-LAST:event_REPRecsButtonActionPerformed

    private void REPStatusOptionsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_REPStatusOptionsButtonActionPerformed
        new REPStatusOptionsSearchDialog(Global.root, true);
    }//GEN-LAST:event_REPStatusOptionsButtonActionPerformed

    private void ULPRecsButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ULPRecsButtonActionPerformed
        new ULPRecsSearchDialog(Global.root, true);
    }//GEN-LAST:event_ULPRecsButtonActionPerformed

    private void BoardActionTypeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BoardActionTypeButtonActionPerformed
        new REPBoardActionTypeSearchDialog(Global.root, true);
    }//GEN-LAST:event_BoardActionTypeButtonActionPerformed

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        this.dispose();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void PreFixesButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PreFixesButtonActionPerformed
        new PreFixSearchDialog(Global.root, true);
    }//GEN-LAST:event_PreFixesButtonActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BoardActionTypeButton;
    private javax.swing.JButton FFConciliatorsButton;
    private javax.swing.JButton HistoryTypesButton;
    private javax.swing.JButton MediatorsButton;
    private javax.swing.JButton PBRExecsButton;
    private javax.swing.JButton PartyButton;
    private javax.swing.JButton PreFixesButton;
    private javax.swing.JButton REPRecsButton;
    private javax.swing.JButton REPStatusOptionsButton;
    private javax.swing.JButton SERBExecButton;
    private javax.swing.JButton SystemPBRButton;
    private javax.swing.JButton SystemSERBButton;
    private javax.swing.JButton ULPRecsButton;
    private javax.swing.JButton UsersButton;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JSeparator jSeparator1;
    // End of variables declaration//GEN-END:variables

}
