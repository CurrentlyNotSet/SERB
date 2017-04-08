/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.boardmeetings.AddREPBoardMeeting;
import parker.serb.boardmeetings.RemoveBoardMeetingDialog;
import parker.serb.boardmeetings.UpdateREPBoardMeeting;
import parker.serb.sql.BoardMeeting;
import parker.serb.sql.REPBoardActionType;
import parker.serb.sql.REPCase;
import parker.serb.sql.User;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class REPBoardStatusPanel extends javax.swing.JPanel {

    REPCase caseInformation;
    /**
     * Creates new form REPBoardStatusPanel
     */
    public REPBoardStatusPanel() {
        initComponents();
        addRenderer();
        setColumnWidths();

        addListeners();

        addBoardMeetingButton.setVisible(false);
    }

    private void addRenderer() {
        boardMeetingTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
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

    private void setColumnWidths() {
        boardMeetingTable.getColumnModel().getColumn(0).setPreferredWidth(125);
        boardMeetingTable.getColumnModel().getColumn(0).setMinWidth(125);
        boardMeetingTable.getColumnModel().getColumn(0).setMaxWidth(125);

        boardMeetingTable.getColumnModel().getColumn(1).setPreferredWidth(75);
        boardMeetingTable.getColumnModel().getColumn(1).setMinWidth(75);
        boardMeetingTable.getColumnModel().getColumn(1).setMaxWidth(75);

        boardMeetingTable.getColumnModel().getColumn(3).setPreferredWidth(125);
        boardMeetingTable.getColumnModel().getColumn(3).setMinWidth(125);
        boardMeetingTable.getColumnModel().getColumn(3).setMaxWidth(125);

        boardMeetingTable.getColumnModel().getColumn(4).setPreferredWidth(0);
        boardMeetingTable.getColumnModel().getColumn(4).setMinWidth(0);
        boardMeetingTable.getColumnModel().getColumn(4).setMaxWidth(0);
    }

    private void addListeners() {
        boardMeetingTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(boardMeetingTable.getSelectedRow() > -1) {
                    if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                        new UpdateREPBoardMeeting(
                                (JFrame) Global.root.getRootPane().getParent(),
                                true,
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 0).toString().trim(), //Date
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 1).toString().trim(), //agenda number
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 2).toString().trim(), //recommendation
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 4).toString().trim(), //ID
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 3).toString().trim()  //memoDate
                            );
                        loadBoardMeetings();
                    } else if(e.getButton() == MouseEvent.BUTTON3) {
                        new RemoveBoardMeetingDialog(
                                (JFrame) Global.root.getRootPane().getParent(),
                                true,
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 4).toString().trim()); //ID
                        loadBoardMeetings();
                    }
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {}

            @Override
            public void mouseReleased(MouseEvent e) {}

            @Override
            public void mouseEntered(MouseEvent e) {}

            @Override
            public void mouseExited(MouseEvent e) {}
        });
    }

    public void loadHearingPerson() {
        hearingPersonComboBox.removeAllItems();

        hearingPersonComboBox.addItem("");

        List hearingPeopleList = User.loadSectionDropDowns("ALJ");

        for (Object hearingPerson : hearingPeopleList) {
            hearingPersonComboBox.addItem(hearingPerson.toString());
        }
    }

    public void loadBoardActionType() {
        boardActionTypeComboBox.removeAllItems();

        boardActionTypeComboBox.addItem("");

        List boardActionTypeList = REPBoardActionType.loadAllREPBoardActionTypes();

        for (Object boardAction : boardActionTypeList) {
            boardActionTypeComboBox.addItem(boardAction.toString());
        }
    }

    public void loadInformation() {
        loadHearingPerson();
        loadBoardActionType();
        loadBoardMeetings();
        loadStatusInformation();
    }

    private void loadStatusInformation() {
        caseInformation = REPCase.loadBoardStatus();

        boardActionTypeComboBox.setSelectedItem(caseInformation.boardActionType == null ? "" : caseInformation.boardActionType);
        boardActionDateTextBox.setText(caseInformation.boardActionDate != null ? Global.mmddyyyy.format(new Date(caseInformation.boardActionDate.getTime())) : "");
        hearingPersonComboBox.setSelectedItem(caseInformation.hearingPersonID == 0 ? "" : User.getNameByID(caseInformation.hearingPersonID));
        BoardActionNote.setText(caseInformation.boardStatusNote);
        BoardMeetingBlurb.setText(caseInformation.boardStatusBlurb);
    }

    void enableUpdate() {
        Global.root.getjButton2().setText("Save");

        Global.root.getjButton9().setVisible(true);

        boardActionTypeComboBox.setEnabled(true);
        boardActionDateTextBox.setEnabled(true);
        boardActionDateTextBox.setBackground(Color.WHITE);
        hearingPersonComboBox.setEnabled(true);
        BoardActionNote.setEnabled(true);
        BoardActionNote.setBackground(Color.WHITE);
        BoardMeetingBlurb.setEnabled(true);
        BoardMeetingBlurb.setBackground(Color.WHITE);

        addBoardMeetingButton.setVisible(true);
    }

    void disableUpdate(boolean runSave) {
        Global.root.getjButton2().setText("Update");

        Global.root.getjButton9().setVisible(false);

        boardActionTypeComboBox.setEnabled(false);
        boardActionDateTextBox.setEnabled(false);
        boardActionDateTextBox.setBackground(new Color(238,238,238));
        hearingPersonComboBox.setEnabled(false);
        BoardActionNote.setEnabled(false);
        BoardActionNote.setBackground(new Color(238,238,238));
        BoardMeetingBlurb.setEnabled(false);
        BoardMeetingBlurb.setBackground(new Color(238,238,238));
        addBoardMeetingButton.setVisible(false);

        if(runSave) {
            saveInformation();
        } else {
            loadStatusInformation();
        }

    }

    private void loadBoardMeetings() {
        DefaultTableModel model = (DefaultTableModel) boardMeetingTable.getModel();

        model.setRowCount(0);

        List boardMeeting = BoardMeeting.loadREPBoardMeetings();

        for (Object meeting : boardMeeting) {
            BoardMeeting singleMeeting = (BoardMeeting) meeting;
            model.addRow(new Object[]{
                singleMeeting.boardMeetingDate,
                singleMeeting.agendaItemNumber,
                singleMeeting.recommendation,
                singleMeeting.memoDate,
                singleMeeting.id
            });
        }
        boardMeetingTable.clearSelection();
    }

    private void saveInformation() {
        REPCase newCaseInformation = new REPCase();

        newCaseInformation.boardActionType = boardActionTypeComboBox.getSelectedItem().toString().equals("") ? null : boardActionTypeComboBox.getSelectedItem().toString();
        newCaseInformation.boardActionDate = boardActionDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(boardActionDateTextBox.getText()));
        newCaseInformation.hearingPersonID = hearingPersonComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(hearingPersonComboBox.getSelectedItem().toString());
        newCaseInformation.boardStatusNote = BoardActionNote.getText().equals("") ? null : BoardActionNote.getText();
        newCaseInformation.boardStatusBlurb = BoardMeetingBlurb.getText().equals("") ? null : BoardMeetingBlurb.getText();

        REPCase.updateBoardStatus(newCaseInformation, caseInformation);
        caseInformation = REPCase.loadBoardStatus();
    }

    private void clearDate(WebDateField dateField, MouseEvent evt) {
        if(evt.getButton() == MouseEvent.BUTTON3 && dateField.isEnabled()) {
            ClearDateDialog dialog = new ClearDateDialog((JFrame) Global.root, true);
            if(dialog.isReset()) {
                dateField.setText("");
            }
            dialog.dispose();
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

        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        boardActionTypeComboBox = new javax.swing.JComboBox();
        jLabel2 = new javax.swing.JLabel();
        boardActionDateTextBox = new com.alee.extended.date.WebDateField();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        BoardActionNote = new javax.swing.JTextArea();
        hearingPersonComboBox = new javax.swing.JComboBox();
        jLabel6 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jScrollPane3 = new javax.swing.JScrollPane();
        BoardMeetingBlurb = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        addBoardMeetingButton = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        boardMeetingTable = new javax.swing.JTable();

        jLabel1.setText("Type:");

        boardActionTypeComboBox.setEnabled(false);

        jLabel2.setText("Board Action Date:");

        boardActionDateTextBox.setEditable(false);
        boardActionDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        boardActionDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        boardActionDateTextBox.setEnabled(false);
        boardActionDateTextBox.setDateFormat(Global.mmddyyyy);

        boardActionDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            boardActionDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    boardActionDateTextBoxMouseClicked(evt);
                }
            });

            jLabel3.setText("Hearing Person:");

            jLabel4.setText("Note:");

            BoardActionNote.setBackground(new java.awt.Color(238, 238, 238));
            BoardActionNote.setColumns(20);
            BoardActionNote.setLineWrap(true);
            BoardActionNote.setRows(5);
            BoardActionNote.setWrapStyleWord(true);
            BoardActionNote.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            BoardActionNote.setEnabled(false);
            jScrollPane1.setViewportView(BoardActionNote);

            hearingPersonComboBox.setEnabled(false);

            jLabel6.setText("Board Action:");

            javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
            jPanel1.setLayout(jPanel1Layout);
            jPanel1Layout.setHorizontalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jScrollPane1)
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addComponent(jLabel1)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(boardActionTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel2)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(boardActionDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(hearingPersonComboBox, 0, 108, Short.MAX_VALUE))
                        .addGroup(jPanel1Layout.createSequentialGroup()
                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(jLabel4)
                                .addComponent(jLabel6))
                            .addGap(0, 0, Short.MAX_VALUE)))
                    .addContainerGap())
            );
            jPanel1Layout.setVerticalGroup(
                jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel1Layout.createSequentialGroup()
                    .addContainerGap()
                    .addComponent(jLabel6)
                    .addGap(10, 10, 10)
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1)
                        .addComponent(boardActionTypeComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel2)
                        .addComponent(boardActionDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel3)
                        .addComponent(hearingPersonComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel4)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE))
            );

            jLabel5.setText("Blurb:");

            BoardMeetingBlurb.setBackground(new java.awt.Color(238, 238, 238));
            BoardMeetingBlurb.setColumns(20);
            BoardMeetingBlurb.setLineWrap(true);
            BoardMeetingBlurb.setRows(5);
            BoardMeetingBlurb.setWrapStyleWord(true);
            BoardMeetingBlurb.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            BoardMeetingBlurb.setEnabled(false);
            jScrollPane3.setViewportView(BoardMeetingBlurb);

            jPanel3.setMaximumSize(new java.awt.Dimension(300, 155));
            jPanel3.setMinimumSize(new java.awt.Dimension(300, 155));

            jLabel7.setText("Board Meeting Information:");

            addBoardMeetingButton.setText("+");
            addBoardMeetingButton.addActionListener(new java.awt.event.ActionListener() {
                public void actionPerformed(java.awt.event.ActionEvent evt) {
                    addBoardMeetingButtonActionPerformed(evt);
                }
            });

            jScrollPane2.setToolTipText("");

            boardMeetingTable.setAutoCreateRowSorter(true);
            boardMeetingTable.setModel(new javax.swing.table.DefaultTableModel(
                new Object [][] {

                },
                new String [] {
                    "Meeting Date", "Item", "Status/Recommendation", "Memo Date", "id"
                }
            ) {
                boolean[] canEdit = new boolean [] {
                    false, false, false, false, false
                };

                public boolean isCellEditable(int rowIndex, int columnIndex) {
                    return canEdit [columnIndex];
                }
            });
            boardMeetingTable.setMaximumSize(new java.awt.Dimension(2147483647, 155));
            boardMeetingTable.setMinimumSize(new java.awt.Dimension(0, 0));
            boardMeetingTable.setPreferredSize(new java.awt.Dimension(300, 155));
            boardMeetingTable.setShowGrid(true);
            boardMeetingTable.getTableHeader().setReorderingAllowed(false);
            jScrollPane2.setViewportView(boardMeetingTable);
            if (boardMeetingTable.getColumnModel().getColumnCount() > 0) {
                boardMeetingTable.getColumnModel().getColumn(4).setResizable(false);
            }

            javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
            jPanel4.setLayout(jPanel4Layout);
            jPanel4Layout.setHorizontalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addComponent(jScrollPane2)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(addBoardMeetingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGap(0, 0, 0))
            );
            jPanel4Layout.setVerticalGroup(
                jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel4Layout.createSequentialGroup()
                    .addGap(0, 0, 0)
                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(addBoardMeetingButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(0, 0, 0))
            );

            javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
            jPanel3.setLayout(jPanel3Layout);
            jPanel3Layout.setHorizontalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel3Layout.createSequentialGroup()
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel3Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jPanel4, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGap(0, 0, 0))
            );
            jPanel3Layout.setVerticalGroup(
                jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel3Layout.createSequentialGroup()
                    .addComponent(jLabel7)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
            jPanel2.setLayout(jPanel2Layout);
            jPanel2Layout.setHorizontalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(jPanel2Layout.createSequentialGroup()
                            .addComponent(jLabel5)
                            .addGap(0, 0, Short.MAX_VALUE))
                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane3))
                    .addContainerGap())
            );
            jPanel2Layout.setVerticalGroup(
                jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(jPanel2Layout.createSequentialGroup()
                    .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jLabel5)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap())
            );

            jPanel3.getAccessibleContext().setAccessibleName("");

            javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
            this.setLayout(layout);
            layout.setHorizontalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            );
            layout.setVerticalGroup(
                layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            );
        }// </editor-fold>//GEN-END:initComponents

    private void addBoardMeetingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addBoardMeetingButtonActionPerformed
        new AddREPBoardMeeting((JFrame) Global.root, true);
        loadBoardMeetings();
    }//GEN-LAST:event_addBoardMeetingButtonActionPerformed

    private void boardActionDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_boardActionDateTextBoxMouseClicked
        clearDate(boardActionDateTextBox, evt);
    }//GEN-LAST:event_boardActionDateTextBoxMouseClicked


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTextArea BoardActionNote;
    private javax.swing.JTextArea BoardMeetingBlurb;
    private javax.swing.JButton addBoardMeetingButton;
    private com.alee.extended.date.WebDateField boardActionDateTextBox;
    private javax.swing.JComboBox boardActionTypeComboBox;
    private javax.swing.JTable boardMeetingTable;
    private javax.swing.JComboBox hearingPersonComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    // End of variables declaration//GEN-END:variables
}
