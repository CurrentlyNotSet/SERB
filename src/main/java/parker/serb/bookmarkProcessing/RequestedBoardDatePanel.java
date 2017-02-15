/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.bookmarkProcessing;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.utils.swing.Customizer;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import parker.serb.Global;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.ClearDateDialog;

/**
 *
 * @author parker
 */
public class RequestedBoardDatePanel extends javax.swing.JDialog {

    SMDSDocuments template;

    public RequestedBoardDatePanel(java.awt.Frame parent, boolean modal, SMDSDocuments templatePassed) {
        super(parent, modal);
        initComponents();
        setLocationRelativeTo(parent);
        template = templatePassed;
        loadingPanel.setVisible(false);
        jLayeredPane.moveToFront(InfoPanel);
        setVisible(true);
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

    private void generateLetterButton() {
        processThread();
        loadingPanel.setVisible(true);
        jLayeredPane.moveToFront(loadingPanel);
    }

    private void processThread() {
        Thread temp = new Thread(() -> {
            generateDocument.generateSMDSAgenda(template, boardDateDatePicker.getDate());
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
        boardDateDatePicker = new com.alee.extended.date.WebDateField();
        jLabel4 = new javax.swing.JLabel();
        cancelButton = new javax.swing.JButton();
        generateButton = new javax.swing.JButton();
        loadingPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jLayeredPane.setLayout(new javax.swing.OverlayLayout(jLayeredPane));

        jLabel1.setFont(new java.awt.Font("Lucida Grande", 0, 18)); // NOI18N
        jLabel1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel1.setText("Required Agenda Information");

        boardDateDatePicker.setEditable(false);
        boardDateDatePicker.setCaretColor(new java.awt.Color(0, 0, 0));
        boardDateDatePicker.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        boardDateDatePicker.setDateFormat(Global.mmddyyyy);

        boardDateDatePicker.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            boardDateDatePicker.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    boardDateDatePickerMouseClicked(evt);
                }
            });

            jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
            jLabel4.setText("Board Meeting Date:");

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

            javax.swing.GroupLayout InfoPanelLayout = new javax.swing.GroupLayout(InfoPanel);
            InfoPanel.setLayout(InfoPanelLayout);
            InfoPanelLayout.setHorizontalGroup(
                InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(InfoPanelLayout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(InfoPanelLayout.createSequentialGroup()
                            .addComponent(cancelButton)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 180, Short.MAX_VALUE)
                            .addComponent(generateButton, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGroup(InfoPanelLayout.createSequentialGroup()
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                            .addComponent(boardDateDatePicker, javax.swing.GroupLayout.DEFAULT_SIZE, 236, Short.MAX_VALUE)))
                    .addContainerGap())
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 380, Short.MAX_VALUE)
                        .addContainerGap()))
            );

            InfoPanelLayout.linkSize(javax.swing.SwingConstants.HORIZONTAL, new java.awt.Component[] {cancelButton, generateButton});

            InfoPanelLayout.setVerticalGroup(
                InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, InfoPanelLayout.createSequentialGroup()
                    .addGap(80, 80, 80)
                    .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(boardDateDatePicker, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel4))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                    .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(cancelButton)
                        .addComponent(generateButton))
                    .addContainerGap())
                .addGroup(InfoPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(InfoPanelLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel1)
                        .addContainerGap(181, Short.MAX_VALUE)))
            );

            InfoPanelLayout.linkSize(javax.swing.SwingConstants.VERTICAL, new java.awt.Component[] {boardDateDatePicker, jLabel4});

            jLayeredPane.add(InfoPanel);

            jLabel8.setBackground(new java.awt.Color(255, 255, 255));
            jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
            jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

            javax.swing.GroupLayout loadingPanelLayout = new javax.swing.GroupLayout(loadingPanel);
            loadingPanel.setLayout(loadingPanelLayout);
            loadingPanelLayout.setHorizontalGroup(
                loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            );
            loadingPanelLayout.setVerticalGroup(
                loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 216, Short.MAX_VALUE)
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

    private void boardDateDatePickerMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boardDateDatePickerMouseClicked
        clearDate(boardDateDatePicker, evt);
    }//GEN-LAST:event_boardDateDatePickerMouseClicked

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel InfoPanel;
    private com.alee.extended.date.WebDateField boardDateDatePicker;
    private javax.swing.JButton cancelButton;
    private javax.swing.JButton generateButton;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JPanel loadingPanel;
    // End of variables declaration//GEN-END:variables
}
