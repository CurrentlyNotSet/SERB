/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.ULP;
import com.alee.extended.date.WebCalendar;
import com.alee.extended.date.WebDateField;
import com.alee.utils.swing.Customizer;
import java.awt.Color;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.boardmeetings.AddULPBoardMeeting;
import parker.serb.boardmeetings.RemoveBoardMeetingDialog;
import parker.serb.boardmeetings.UpdateULPBoardMeeting;
import parker.serb.bunumber.buNumberSearch;
import parker.serb.employer.employerDetail;
import parker.serb.employer.employerSearch;
import parker.serb.relatedcase.AddNewRelatedCase;
import parker.serb.relatedcase.RemoveRelatedCaseDialog;
import parker.serb.sql.Audit;
import parker.serb.sql.BoardMeeting;
import parker.serb.sql.RelatedCase;
import parker.serb.sql.ULPCase;
import parker.serb.sql.ULPCaseSearchData;
import parker.serb.sql.ULPRecommendation;
import parker.serb.sql.User;
import parker.serb.util.ClearDataDialog;
import parker.serb.util.ClearDateDialog;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parkerjohnston
 */
public class ULPStatusPanel extends javax.swing.JPanel {

    ULPCase currentStatusInformation;

    /**
     * Creates new form ULPStatusPanel
     */
    public ULPStatusPanel() {
        initComponents();
        addListeners();
        setHearingsTableColumns();
        addCaseHearingButton.setVisible(false);
        addRelatedCaseButton.setVisible(false);
    }

    private void addListeners() {

        filedDateTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(filedDateTextBox.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.MONTH, 5);
                    reportDueDateTextBox.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    reportDueDateTextBox.setText("");
                }
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(filedDateTextBox.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.MONTH, 5);
                    reportDueDateTextBox.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    reportDueDateTextBox.setText("");
                }
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                try {
                    Date startDate = Global.mmddyyyy.parse(filedDateTextBox.getText());
                    Calendar cal = Calendar.getInstance();
                    cal.setTime(startDate);
                    cal.add(Calendar.MONTH, 5);
                    reportDueDateTextBox.setText(Global.mmddyyyy.format(cal.getTime()));
                } catch (ParseException ex) {
                    reportDueDateTextBox.setText("");
                }
            }
        });

        boardMeetingTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(boardMeetingTable.getSelectedRow() > -1) {
                    if(e.getClickCount() == 2 && e.getButton() == MouseEvent.BUTTON1) {
                        new UpdateULPBoardMeeting(
                                (JFrame) Global.root.getRootPane().getParent(),
                                true,
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 0).toString().trim(),
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 1).toString().trim(),
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 2).toString().trim(),
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 3).toString().trim()
                            );
                        loadBoardMeetingTable();
                    } else if(e.getButton() == MouseEvent.BUTTON3) {
                        new RemoveBoardMeetingDialog(
                                (JFrame) Global.root.getRootPane().getParent(),
                                true,
                                boardMeetingTable.getValueAt(boardMeetingTable.getSelectedRow(), 3).toString().trim());
                        loadBoardMeetingTable();
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

        relatedCaseTable.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(relatedCaseTable.getSelectedRow() > -1) {
                    if(e.getButton() == MouseEvent.BUTTON3) {
                        new RemoveRelatedCaseDialog(
                            (JFrame) Global.root.getRootPane().getParent(),
                            true,
                            relatedCaseTable.getValueAt(relatedCaseTable.getSelectedRow(), 0).toString().trim()
                        );
                        loadRelatedCasesTable();
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

        employerNumberTextBox.addMouseListener(new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && e.getButton() != MouseEvent.BUTTON3) {
                    if(employerNumberTextBox.isEnabled()) {
                        employerSearch search = new employerSearch((JFrame) Global.root.getRootPane().getParent(), true, employerNumberTextBox.getText().trim());
                        employerNumberTextBox.setText(search.getEmployerNumber());
                        search.dispose();
                    } else {
                        new employerDetail((JFrame) Global.root.getRootPane().getParent(), true, employerNumberTextBox.getText().trim());
                    }
                } else {
                    clearData(employerNumberTextBox, e);
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

    private void setHearingsTableColumns() {
        boardMeetingTable.getColumnModel().getColumn(0).setPreferredWidth(125);
        boardMeetingTable.getColumnModel().getColumn(0).setMinWidth(125);
        boardMeetingTable.getColumnModel().getColumn(0).setMaxWidth(125);

        boardMeetingTable.getColumnModel().getColumn(1).setPreferredWidth(75);
        boardMeetingTable.getColumnModel().getColumn(1).setMinWidth(75);
        boardMeetingTable.getColumnModel().getColumn(1).setMaxWidth(75);

        boardMeetingTable.getColumnModel().getColumn(3).setPreferredWidth(0);
        boardMeetingTable.getColumnModel().getColumn(3).setMinWidth(0);
        boardMeetingTable.getColumnModel().getColumn(3).setMaxWidth(0);
    }

    public void enableUpdate() {
        //Set buttons
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);

        //set text boxes and combo boxes
        allegationTextBox.setEnabled(true);
        allegationTextBox.setBackground(Color.white);
        statusComboBox.setEnabled(true);
        priorityComboBox.setEnabled(true);
        assignedDateTextBox.setEnabled(true);
        assignedDateTextBox.setBackground(Color.white);
        turnInDateTextBox.setEnabled(true);
        turnInDateTextBox.setBackground(Color.white);
        reportDueDateTextBox.setEnabled(true);
        reportDueDateTextBox.setBackground(Color.white);
        dismissalDateTextBox.setEnabled(true);
        dismissalDateTextBox.setBackground(Color.white);
        deferredDateTextBox.setEnabled(true);
        deferredDateTextBox.setBackground(Color.white);
        appealReceivedTextBox.setEnabled(true);
        appealReceivedTextBox.setBackground(Color.white);
        appealSentTextBox.setEnabled(true);
        appealSentTextBox.setBackground(Color.white);
        courtTextBox.setEnabled(true);
        courtTextBox.setBackground(Color.white);
        courtCaseNumberTextBox.setEnabled(true);
        courtCaseNumberTextBox.setBackground(Color.white);
        serbCaseNumberTextBox.setEnabled(true);
        serbCaseNumberTextBox.setBackground(Color.white);
        employerNumberTextBox.setEnabled(true);
        employerNumberTextBox.setBackground(Color.white);
        barginingUnitNoTextBox.setEnabled(true);
        barginingUnitNoTextBox.setBackground(Color.white);
        finalDispositionComboBox.setEnabled(true);
        investigatorComboBox.setEnabled(true);
        mediatorAssignedComboBox.setEnabled(true);
        aljComboBox.setEnabled(true);
        filedDateTextBox.setEnabled(true);
        filedDateTextBox.setBackground(Color.white);
        probableCauseCheckBox.setEnabled(true);
        addCaseHearingButton.setVisible(true);
        addRelatedCaseButton.setVisible(true);
    }

    public void disableUpdate(boolean runSave) {

        //Set Buttons
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);

        //Set text boxes and combo boxes
        allegationTextBox.setEnabled(false);
        allegationTextBox.setBackground(new Color(238,238,238));
        statusComboBox.setEnabled(false);
        priorityComboBox.setEnabled(false);
        assignedDateTextBox.setEnabled(false);
        assignedDateTextBox.setBackground(new Color(238,238,238));
        turnInDateTextBox.setEnabled(false);
        turnInDateTextBox.setBackground(new Color(238,238,238));
        reportDueDateTextBox.setEnabled(false);
        reportDueDateTextBox.setBackground(new Color(238,238,238));
        dismissalDateTextBox.setEnabled(false);
        dismissalDateTextBox.setBackground(new Color(238,238,238));
        deferredDateTextBox.setEnabled(false);
        deferredDateTextBox.setBackground(new Color(238,238,238));
        appealReceivedTextBox.setEnabled(false);
        appealReceivedTextBox.setBackground(new Color(238,238,238));
        appealSentTextBox.setEnabled(false);
        appealSentTextBox.setBackground(new Color(238,238,238));
        courtTextBox.setEnabled(false);
        courtTextBox.setBackground(new Color(238,238,238));
        courtCaseNumberTextBox.setEnabled(false);
        courtCaseNumberTextBox.setBackground(new Color(238,238,238));
        serbCaseNumberTextBox.setEnabled(false);
        serbCaseNumberTextBox.setBackground(new Color(238,238,238));
        finalDispositionComboBox.setEnabled(false);
        investigatorComboBox.setEnabled(false);
        mediatorAssignedComboBox.setEnabled(false);
        aljComboBox.setEnabled(false);
        filedDateTextBox.setEnabled(false);
        filedDateTextBox.setBackground(new Color(238,238,238));
        employerNumberTextBox.setEnabled(false);
        employerNumberTextBox.setBackground(new Color(238,238,238));
        barginingUnitNoTextBox.setEnabled(false);
        barginingUnitNoTextBox.setBackground(new Color(238,238,238));
        probableCauseCheckBox.setEnabled(false);

        addCaseHearingButton.setVisible(false);
        addRelatedCaseButton.setVisible(false);

        if(runSave) {
            saveInformation();
        } else {
            loadPanelInformation();
        }
    }

    public void loadInformation() {

        loadStatusComboBox();
        loadInvestigatorComobBox();
        loadALJComboBox();
        loadMediatorComboBox();
        loadBoardMeetingTable();
        loadRelatedCasesTable();
        loadPanelInformation();
    }

    public void loadStatusComboBox() {
        statusComboBox.removeAllItems();
        finalDispositionComboBox.removeAllItems();

        statusComboBox.addItem("");
        finalDispositionComboBox.addItem("");

        List recommendationList = ULPRecommendation.loadAllULPRecommendations();

        for (Object recommendation : recommendationList) {
            ULPRecommendation rec = (ULPRecommendation) recommendation;
            statusComboBox.addItem(rec.code);
            finalDispositionComboBox.addItem(rec.code);
        }

        statusComboBox.setSelectedItem("");
        finalDispositionComboBox.setSelectedItem("");
    }

    public void loadInvestigatorComobBox() {
        investigatorComboBox.removeAllItems();

        investigatorComboBox.addItem("");

        List userList = User.loadSectionDropDowns("ULP");

        for (Object user : userList) {
            investigatorComboBox.addItem((String) user);
        }

        investigatorComboBox.setSelectedItem("");
    }

    public void loadALJComboBox() {
        aljComboBox.removeAllItems();

        aljComboBox.addItem("");

        List userList = User.loadSectionDropDowns("ALJ");

        for (Object user : userList) {
            aljComboBox.addItem((String) user);
        }

        aljComboBox.setSelectedItem("");
    }

    public void loadMediatorComboBox() {
        mediatorAssignedComboBox.removeAllItems();

        mediatorAssignedComboBox.addItem("");

        List userList = User.loadSectionDropDowns("ULP");

        for (Object user : userList) {
            mediatorAssignedComboBox.addItem((String) user);
        }

        mediatorAssignedComboBox.setSelectedItem("");
    }

    public void loadBoardMeetingTable() {
        DefaultTableModel model = (DefaultTableModel) boardMeetingTable.getModel();

        model.setRowCount(0);

        List boardMeeting = BoardMeeting.loadULPBoardMeetings();

        for (Object meeting : boardMeeting) {
            BoardMeeting singleMeeting = (BoardMeeting) meeting;
            model.addRow(new Object[] {singleMeeting.boardMeetingDate, singleMeeting.agendaItemNumber, singleMeeting.recommendation, singleMeeting.id});
        }
        boardMeetingTable.clearSelection();
    }

    public void loadRelatedCasesTable() {

        DefaultTableModel model = (DefaultTableModel) relatedCaseTable.getModel();

        model.setRowCount(0);

        List relatedCases = RelatedCase.loadRelatedCases();

        for (Object relatedCase : relatedCases) {
            model.addRow(new Object[] {relatedCase});
        }
        relatedCaseTable.clearSelection();
    }

    public void loadPanelInformation() {
        currentStatusInformation = ULPCase.loadStatus();

        employerNumberTextBox.setText(currentStatusInformation.employerIDNumber);
        barginingUnitNoTextBox.setText(currentStatusInformation.barginingUnitNo);
        allegationTextBox.setText(currentStatusInformation.allegation);
        statusComboBox.setSelectedItem(currentStatusInformation.currentStatus);
        priorityComboBox.setSelectedItem(currentStatusInformation.priority == true ? "Yes" : "No");
        assignedDateTextBox.setText(currentStatusInformation.assignedDate != null ? Global.mmddyyyy.format(new Date(currentStatusInformation.assignedDate.getTime())) : "");
        turnInDateTextBox.setText(currentStatusInformation.turnInDate != null ? Global.mmddyyyy.format(new Date(currentStatusInformation.turnInDate.getTime())) : "");
        reportDueDateTextBox.setText(currentStatusInformation.reportDueDate != null ? Global.mmddyyyy.format(new Date(currentStatusInformation.reportDueDate.getTime())) : "");
        dismissalDateTextBox.setText(currentStatusInformation.dismissalDate != null ? Global.mmddyyyy.format(new Date(currentStatusInformation.dismissalDate.getTime())) : "");
        deferredDateTextBox.setText(currentStatusInformation.deferredDate != null ? Global.mmddyyyy.format(new Date(currentStatusInformation.deferredDate.getTime())) : "");
        appealReceivedTextBox.setText(currentStatusInformation.appealDateReceived != null ? Global.mmddyyyy.format(new Date(currentStatusInformation.appealDateReceived.getTime())) : "");
        appealSentTextBox.setText(currentStatusInformation.appealDateSent != null ? Global.mmddyyyy.format(new Date(currentStatusInformation.appealDateSent.getTime())) : "");
        courtTextBox.setText(currentStatusInformation.courtName);
        courtCaseNumberTextBox.setText(currentStatusInformation.courtCaseNumber);
        serbCaseNumberTextBox.setText(currentStatusInformation.SERBCaseNumber);
        finalDispositionComboBox.setSelectedItem(currentStatusInformation.finalDispositionStatus);
        investigatorComboBox.setSelectedItem(currentStatusInformation.investigatorID != 0 ? User.getNameByID(currentStatusInformation.investigatorID) : "");
        mediatorAssignedComboBox.setSelectedItem(currentStatusInformation.mediatorAssignedID != 0 ? User.getNameByID(currentStatusInformation.mediatorAssignedID) : "");
        aljComboBox.setSelectedItem(currentStatusInformation.aljID != 0 ? User.getNameByID(currentStatusInformation.aljID) : "");
        filedDateTextBox.setText(currentStatusInformation.fileDate != null ? Global.mmddyyyy.format(new Date(currentStatusInformation.fileDate.getTime())) : "");
        probableCauseCheckBox.setSelected(currentStatusInformation.probableCause);
    }

    public void saveInformation() {
        Audit.addAuditEntry("Updated ULP Status Panel ");
        ULPCase newStatusInformation = new ULPCase();

        newStatusInformation.employerIDNumber = employerNumberTextBox.getText().trim().equals("") ? null : employerNumberTextBox.getText().trim();
        newStatusInformation.barginingUnitNo = barginingUnitNoTextBox.getText().trim().equals("") ? null : barginingUnitNoTextBox.getText().trim();
        newStatusInformation.allegation = allegationTextBox.getText().trim().equals("") ? null : allegationTextBox.getText().trim();
        newStatusInformation.currentStatus = (statusComboBox.getSelectedItem() == null || statusComboBox.getSelectedItem().equals("")) ? null : statusComboBox.getSelectedItem().toString().trim();
        newStatusInformation.priority = priorityComboBox.getSelectedItem().toString().equals("Yes");
        newStatusInformation.assignedDate = assignedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(assignedDateTextBox.getText()));
        newStatusInformation.turnInDate = turnInDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(turnInDateTextBox.getText()));
        newStatusInformation.reportDueDate = reportDueDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(reportDueDateTextBox.getText()));
        newStatusInformation.dismissalDate = dismissalDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(dismissalDateTextBox.getText()));
        newStatusInformation.deferredDate = deferredDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(deferredDateTextBox.getText()));
        newStatusInformation.appealDateReceived = appealReceivedTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(appealReceivedTextBox.getText()));
        newStatusInformation.appealDateSent = appealSentTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(appealSentTextBox.getText()));
        newStatusInformation.courtName = courtTextBox.getText().trim().equals("") ? null : courtTextBox.getText().trim();
        newStatusInformation.courtCaseNumber = courtCaseNumberTextBox.getText().trim().equals("") ? null : courtCaseNumberTextBox.getText().trim();
        newStatusInformation.SERBCaseNumber = serbCaseNumberTextBox.getText().trim().equals("") ? null : serbCaseNumberTextBox.getText().trim();
        newStatusInformation.finalDispositionStatus = (finalDispositionComboBox.getSelectedItem() == null || finalDispositionComboBox.getSelectedItem().equals("")) ? null : finalDispositionComboBox.getSelectedItem().toString();
        newStatusInformation.investigatorID = (investigatorComboBox.getSelectedItem() == null || investigatorComboBox.getSelectedItem().equals("")) ? 0 : (investigatorComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(investigatorComboBox.getSelectedItem().toString().trim()));
        newStatusInformation.mediatorAssignedID = (mediatorAssignedComboBox.getSelectedItem() == null || mediatorAssignedComboBox.getSelectedItem().equals("")) ? 0 : (mediatorAssignedComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(mediatorAssignedComboBox.getSelectedItem().toString().trim()));
        newStatusInformation.aljID = (aljComboBox.getSelectedItem() == null || aljComboBox.getSelectedItem().equals("")) ? 0 : (aljComboBox.getSelectedItem().toString().equals("") ? 0 : User.getUserID(aljComboBox.getSelectedItem().toString().trim()));
        newStatusInformation.fileDate = filedDateTextBox.getText().equals("") ? null : new Timestamp(NumberFormatService.convertMMDDYYYY(filedDateTextBox.getText()));
        newStatusInformation.probableCause = probableCauseCheckBox.isSelected();

        ULPCase.updateCaseStatusInformation(newStatusInformation, currentStatusInformation);
        ULPCaseSearchData.updateCaseEntryFromStatus(newStatusInformation.employerIDNumber, newStatusInformation.barginingUnitNo);
        currentStatusInformation = ULPCase.loadStatus();
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

    private void clearData(JTextField dateField, MouseEvent evt) {
        if(evt.getButton() == MouseEvent.BUTTON3 && dateField.isEnabled()) {
            ClearDataDialog dialog = new ClearDataDialog((JFrame) Global.root, true);
            if(dialog.isReset()) {
                dateField.setText("");
            }
            dialog.dispose();
        }
    }

    public void clearAll() {
        DefaultTableModel relatedCase = (DefaultTableModel) relatedCaseTable.getModel();

        relatedCase.setRowCount(0);

        DefaultTableModel boardMeeting = (DefaultTableModel) boardMeetingTable.getModel();

        boardMeeting.setRowCount(0);

        employerNumberTextBox.setText("");
        barginingUnitNoTextBox.setText("");
        allegationTextBox.setText("");
        statusComboBox.setSelectedItem("");
        priorityComboBox.setSelectedItem(false);
        assignedDateTextBox.setText("");
        turnInDateTextBox.setText("");
        reportDueDateTextBox.setText("");
        dismissalDateTextBox.setText("");
        deferredDateTextBox.setText("");
        appealReceivedTextBox.setText("");
        appealSentTextBox.setText("");
        courtTextBox.setText("");
        courtCaseNumberTextBox.setText("");
        serbCaseNumberTextBox.setText("");
        finalDispositionComboBox.setSelectedItem("");
        investigatorComboBox.setSelectedItem("");
        mediatorAssignedComboBox.setSelectedItem("");
        aljComboBox.setSelectedItem("");
        filedDateTextBox.setText("");
        probableCauseCheckBox.setSelected(false);
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
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        allegationTextBox = new javax.swing.JTextField();
        statusComboBox = new javax.swing.JComboBox<>();
        priorityComboBox = new javax.swing.JComboBox<>();
        assignedDateTextBox = new com.alee.extended.date.WebDateField();
        reportDueDateTextBox = new com.alee.extended.date.WebDateField();
        dismissalDateTextBox = new com.alee.extended.date.WebDateField();
        deferredDateTextBox = new com.alee.extended.date.WebDateField();
        appealReceivedTextBox = new com.alee.extended.date.WebDateField();
        appealSentTextBox = new com.alee.extended.date.WebDateField();
        courtTextBox = new javax.swing.JTextField();
        courtCaseNumberTextBox = new javax.swing.JTextField();
        serbCaseNumberTextBox = new javax.swing.JTextField();
        finalDispositionComboBox = new javax.swing.JComboBox<>();
        investigatorComboBox = new javax.swing.JComboBox<>();
        aljComboBox = new javax.swing.JComboBox<>();
        mediatorAssignedComboBox = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        turnInDateTextBox = new com.alee.extended.date.WebDateField();
        jPanel2 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        relatedCaseTable = new javax.swing.JTable();
        addRelatedCaseButton = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        boardMeetingTable = new javax.swing.JTable();
        addCaseHearingButton = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        filedDateTextBox = new com.alee.extended.date.WebDateField();
        probableCauseCheckBox = new javax.swing.JCheckBox();
        jLabel9 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        employerNumberTextBox = new javax.swing.JTextField();
        barginingUnitNoTextBox = new javax.swing.JTextField();

        jLabel3.setText("Allegation:");

        jLabel4.setText("Status:");

        jLabel5.setText("Priority:");

        jLabel6.setText("Assigned Date:");

        jLabel7.setText("Report Due Date:");

        jLabel10.setText("Dismissal Date:");

        jLabel11.setText("Deferred Date:");

        jLabel12.setText("Appeal Received:");

        jLabel13.setText("Appeal Sent:");

        jLabel14.setText("Court:");

        jLabel15.setText("Court Case #:");

        jLabel16.setText("SERB Case #:");

        jLabel17.setText("Final Disposition:");

        jLabel18.setText("Investigator:");

        jLabel19.setText("Mediator Assigned:");

        jLabel20.setText("ALJ:");

        allegationTextBox.setBackground(new java.awt.Color(238, 238, 238));
        allegationTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        allegationTextBox.setEnabled(false);
        allegationTextBox.setMaximumSize(new java.awt.Dimension(267, 2147483647));
        allegationTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                allegationTextBoxActionPerformed(evt);
            }
        });

        statusComboBox.setEnabled(false);

        priorityComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Yes", "No" }));
        priorityComboBox.setSelectedIndex(1);
        priorityComboBox.setEnabled(false);

        assignedDateTextBox.setEditable(false);
        assignedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
        assignedDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
        assignedDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        assignedDateTextBox.setEnabled(false);
        assignedDateTextBox.setDateFormat(Global.mmddyyyy);

        assignedDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
            {
                @Override
                public void customize ( final WebCalendar calendar )
                {
                    calendar.setStartWeekFromSunday ( true );
                }
            } );
            assignedDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    assignedDateTextBoxMouseClicked(evt);
                }
            });

            reportDueDateTextBox.setEditable(false);
            reportDueDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
            reportDueDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
            reportDueDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
            reportDueDateTextBox.setEnabled(false);
            reportDueDateTextBox.setDateFormat(Global.mmddyyyy);

            reportDueDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                {
                    @Override
                    public void customize ( final WebCalendar calendar )
                    {
                        calendar.setStartWeekFromSunday ( true );
                    }
                } );
                reportDueDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        reportDueDateTextBoxMouseClicked(evt);
                    }
                });

                dismissalDateTextBox.setEditable(false);
                dismissalDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                dismissalDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                dismissalDateTextBox.setEnabled(false);
                dismissalDateTextBox.setDateFormat(Global.mmddyyyy);

                dismissalDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                    {
                        @Override
                        public void customize ( final WebCalendar calendar )
                        {
                            calendar.setStartWeekFromSunday ( true );
                        }
                    } );
                    dismissalDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                            dismissalDateTextBoxMouseClicked(evt);
                        }
                    });

                    deferredDateTextBox.setEditable(false);
                    deferredDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                    deferredDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                    deferredDateTextBox.setEnabled(false);
                    deferredDateTextBox.setDateFormat(Global.mmddyyyy);

                    deferredDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                        {
                            @Override
                            public void customize ( final WebCalendar calendar )
                            {
                                calendar.setStartWeekFromSunday ( true );
                            }
                        } );
                        deferredDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                deferredDateTextBoxMouseClicked(evt);
                            }
                        });

                        appealReceivedTextBox.setEditable(false);
                        appealReceivedTextBox.setBackground(new java.awt.Color(238, 238, 238));
                        appealReceivedTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                        appealReceivedTextBox.setEnabled(false);
                        appealReceivedTextBox.setDateFormat(Global.mmddyyyy);

                        appealReceivedTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                            {
                                @Override
                                public void customize ( final WebCalendar calendar )
                                {
                                    calendar.setStartWeekFromSunday ( true );
                                }
                            } );
                            appealReceivedTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                public void mouseClicked(java.awt.event.MouseEvent evt) {
                                    appealReceivedTextBoxMouseClicked(evt);
                                }
                            });

                            appealSentTextBox.setEditable(false);
                            appealSentTextBox.setBackground(new java.awt.Color(238, 238, 238));
                            appealSentTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                            appealSentTextBox.setEnabled(false);
                            appealSentTextBox.setDateFormat(Global.mmddyyyy);

                            appealSentTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                {
                                    @Override
                                    public void customize ( final WebCalendar calendar )
                                    {
                                        calendar.setStartWeekFromSunday ( true );
                                    }
                                } );
                                appealSentTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                                        appealSentTextBoxMouseClicked(evt);
                                    }
                                });

                                courtTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                courtTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                courtTextBox.setEnabled(false);

                                courtCaseNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                courtCaseNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                courtCaseNumberTextBox.setEnabled(false);
                                courtCaseNumberTextBox.setMaximumSize(new java.awt.Dimension(267, 2147483647));

                                serbCaseNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                serbCaseNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                serbCaseNumberTextBox.setEnabled(false);
                                serbCaseNumberTextBox.setMaximumSize(new java.awt.Dimension(267, 2147483647));

                                finalDispositionComboBox.setEnabled(false);

                                investigatorComboBox.setEnabled(false);

                                aljComboBox.setEnabled(false);

                                mediatorAssignedComboBox.setEnabled(false);

                                jLabel22.setText("Turn In Date:");

                                turnInDateTextBox.setEditable(false);
                                turnInDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                turnInDateTextBox.setCaretColor(new java.awt.Color(0, 0, 0));
                                turnInDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                turnInDateTextBox.setEnabled(false);
                                turnInDateTextBox.setDateFormat(Global.mmddyyyy);

                                turnInDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                    {
                                        @Override
                                        public void customize ( final WebCalendar calendar )
                                        {
                                            calendar.setStartWeekFromSunday ( true );
                                        }
                                    } );
                                    turnInDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                        public void mouseClicked(java.awt.event.MouseEvent evt) {
                                            turnInDateTextBoxMouseClicked(evt);
                                        }
                                    });

                                    javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
                                    jPanel1.setLayout(jPanel1Layout);
                                    jPanel1Layout.setHorizontalGroup(
                                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addContainerGap()
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                .addComponent(jLabel22)
                                                .addComponent(jLabel20)
                                                .addComponent(jLabel19)
                                                .addComponent(jLabel18)
                                                .addComponent(jLabel17)
                                                .addComponent(jLabel16)
                                                .addComponent(jLabel15)
                                                .addComponent(jLabel14)
                                                .addComponent(jLabel13)
                                                .addComponent(jLabel12)
                                                .addComponent(jLabel11)
                                                .addComponent(jLabel10)
                                                .addComponent(jLabel7)
                                                .addComponent(jLabel6)
                                                .addComponent(jLabel5)
                                                .addComponent(jLabel4)
                                                .addComponent(jLabel3))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(statusComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(priorityComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(assignedDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(reportDueDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(dismissalDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(deferredDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(appealReceivedTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(appealSentTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(courtTextBox)
                                                .addComponent(finalDispositionComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(investigatorComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(aljComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(mediatorAssignedComboBox, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(courtCaseNumberTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                                                .addComponent(serbCaseNumberTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 267, Short.MAX_VALUE)
                                                .addComponent(allegationTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addComponent(turnInDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                    );
                                    jPanel1Layout.setVerticalGroup(
                                        jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel1Layout.createSequentialGroup()
                                            .addContainerGap(12, Short.MAX_VALUE)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel3)
                                                .addComponent(allegationTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(statusComboBox)
                                                .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                .addComponent(priorityComboBox)
                                                .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel6)
                                                .addComponent(assignedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel22)
                                                .addComponent(turnInDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel7)
                                                .addComponent(reportDueDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(investigatorComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel10)
                                                .addComponent(dismissalDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel11)
                                                .addComponent(deferredDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel12)
                                                .addComponent(appealReceivedTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel13)
                                                .addComponent(appealSentTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel14)
                                                .addComponent(courtTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel15)
                                                .addComponent(courtCaseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel16)
                                                .addComponent(serbCaseNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel17, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(finalDispositionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(mediatorAssignedComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                .addComponent(aljComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(jLabel20, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    );

                                    jPanel4.setPreferredSize(new java.awt.Dimension(409, 107));

                                    jLabel2.setText("Related Cases:");

                                    relatedCaseTable.setModel(new javax.swing.table.DefaultTableModel(
                                        new Object [][] {

                                        },
                                        new String [] {
                                            "Case Number"
                                        }
                                    ) {
                                        boolean[] canEdit = new boolean [] {
                                            false
                                        };

                                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                            return canEdit [columnIndex];
                                        }
                                    });
                                    relatedCaseTable.setRequestFocusEnabled(false);
                                    jScrollPane2.setViewportView(relatedCaseTable);
                                    if (relatedCaseTable.getColumnModel().getColumnCount() > 0) {
                                        relatedCaseTable.getColumnModel().getColumn(0).setResizable(false);
                                    }

                                    addRelatedCaseButton.setText("+");
                                    addRelatedCaseButton.setMaximumSize(new java.awt.Dimension(29, 91));
                                    addRelatedCaseButton.setMinimumSize(new java.awt.Dimension(29, 91));
                                    addRelatedCaseButton.setPreferredSize(new java.awt.Dimension(29, 91));
                                    addRelatedCaseButton.addActionListener(new java.awt.event.ActionListener() {
                                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                            addRelatedCaseButtonActionPerformed(evt);
                                        }
                                    });

                                    javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
                                    jPanel4.setLayout(jPanel4Layout);
                                    jPanel4Layout.setHorizontalGroup(
                                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(addRelatedCaseButton, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addContainerGap())
                                    );
                                    jPanel4Layout.setVerticalGroup(
                                        jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel4Layout.createSequentialGroup()
                                            .addComponent(jLabel2)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(addRelatedCaseButton, javax.swing.GroupLayout.DEFAULT_SIZE, 173, Short.MAX_VALUE)
                                                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
                                            .addGap(0, 0, 0))
                                    );

                                    boardMeetingTable.setModel(new javax.swing.table.DefaultTableModel(
                                        new Object [][] {

                                        },
                                        new String [] {
                                            "Meeting Date", "Item", "Recommendation", "id"
                                        }
                                    ) {
                                        boolean[] canEdit = new boolean [] {
                                            false, false, false, false
                                        };

                                        public boolean isCellEditable(int rowIndex, int columnIndex) {
                                            return canEdit [columnIndex];
                                        }
                                    });
                                    jScrollPane1.setViewportView(boardMeetingTable);
                                    if (boardMeetingTable.getColumnModel().getColumnCount() > 0) {
                                        boardMeetingTable.getColumnModel().getColumn(0).setResizable(false);
                                        boardMeetingTable.getColumnModel().getColumn(1).setResizable(false);
                                        boardMeetingTable.getColumnModel().getColumn(2).setResizable(false);
                                        boardMeetingTable.getColumnModel().getColumn(3).setResizable(false);
                                    }

                                    addCaseHearingButton.setText("+");
                                    addCaseHearingButton.setMaximumSize(new java.awt.Dimension(29, 206));
                                    addCaseHearingButton.setMinimumSize(new java.awt.Dimension(29, 206));
                                    addCaseHearingButton.setPreferredSize(new java.awt.Dimension(29, 206));
                                    addCaseHearingButton.addActionListener(new java.awt.event.ActionListener() {
                                        public void actionPerformed(java.awt.event.ActionEvent evt) {
                                            addCaseHearingButtonActionPerformed(evt);
                                        }
                                    });

                                    jLabel1.setText("Board Meeting Information:");

                                    javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
                                    jPanel5.setLayout(jPanel5Layout);
                                    jPanel5Layout.setHorizontalGroup(
                                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(addCaseHearingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addGap(0, 0, Short.MAX_VALUE))
                                    );
                                    jPanel5Layout.setVerticalGroup(
                                        jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel5Layout.createSequentialGroup()
                                            .addComponent(jLabel1)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addComponent(addCaseHearingButton, javax.swing.GroupLayout.PREFERRED_SIZE, 172, javax.swing.GroupLayout.PREFERRED_SIZE))
                                            .addGap(0, 6, Short.MAX_VALUE))
                                    );

                                    javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
                                    jPanel3.setLayout(jPanel3Layout);
                                    jPanel3Layout.setHorizontalGroup(
                                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 359, Short.MAX_VALUE)
                                        .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    );
                                    jPanel3Layout.setVerticalGroup(
                                        jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                        .addGroup(jPanel3Layout.createSequentialGroup()
                                            .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, 193, Short.MAX_VALUE)
                                            .addContainerGap())
                                    );

                                    jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.TRAILING);
                                    jLabel8.setText("Filed Date:");

                                    filedDateTextBox.setEditable(false);
                                    filedDateTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                    filedDateTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                    filedDateTextBox.setDrawShade(false);
                                    filedDateTextBox.setEnabled(false);
                                    filedDateTextBox.setDateFormat(Global.mmddyyyy);

                                    filedDateTextBox.setCalendarCustomizer(new Customizer<WebCalendar> ()
                                        {
                                            @Override
                                            public void customize ( final WebCalendar calendar )
                                            {
                                                calendar.setStartWeekFromSunday ( true );
                                            }
                                        } );
                                        filedDateTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                filedDateTextBoxMouseClicked(evt);
                                            }
                                        });

                                        probableCauseCheckBox.setText("Probable Cause");
                                        probableCauseCheckBox.setEnabled(false);

                                        jLabel9.setText("Employer Number:");

                                        jLabel21.setText("BUN Number:");

                                        employerNumberTextBox.setEditable(false);
                                        employerNumberTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                        employerNumberTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                        employerNumberTextBox.setEnabled(false);

                                        barginingUnitNoTextBox.setEditable(false);
                                        barginingUnitNoTextBox.setBackground(new java.awt.Color(238, 238, 238));
                                        barginingUnitNoTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
                                        barginingUnitNoTextBox.setEnabled(false);
                                        barginingUnitNoTextBox.addMouseListener(new java.awt.event.MouseAdapter() {
                                            public void mouseClicked(java.awt.event.MouseEvent evt) {
                                                barginingUnitNoTextBoxMouseClicked(evt);
                                            }
                                        });

                                        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
                                        jPanel2.setLayout(jPanel2Layout);
                                        jPanel2Layout.setHorizontalGroup(
                                            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                                                .addComponent(jLabel9)
                                                                .addComponent(jLabel21))
                                                            .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 73, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                            .addComponent(filedDateTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 122, Short.MAX_VALUE)
                                                            .addComponent(employerNumberTextBox)
                                                            .addComponent(barginingUnitNoTextBox))
                                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                        .addComponent(probableCauseCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                    .addGroup(jPanel2Layout.createSequentialGroup()
                                                        .addContainerGap()
                                                        .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                                                .addContainerGap())
                                        );
                                        jPanel2Layout.setVerticalGroup(
                                            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(jPanel2Layout.createSequentialGroup()
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel8)
                                                    .addComponent(filedDateTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(probableCauseCheckBox))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel9)
                                                    .addComponent(employerNumberTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                                    .addComponent(jLabel21)
                                                    .addComponent(barginingUnitNoTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addGap(18, 18, 18)
                                                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        );

                                        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
                                        this.setLayout(layout);
                                        layout.setHorizontalGroup(
                                            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        );
                                        layout.setVerticalGroup(
                                            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                                        );
                                    }// </editor-fold>//GEN-END:initComponents

    private void addCaseHearingButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addCaseHearingButtonActionPerformed
        new AddULPBoardMeeting((JFrame) Global.root, true);
        loadBoardMeetingTable();
    }//GEN-LAST:event_addCaseHearingButtonActionPerformed

    private void addRelatedCaseButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_addRelatedCaseButtonActionPerformed
        new AddNewRelatedCase((JFrame) Global.root, true);
        loadRelatedCasesTable();
    }//GEN-LAST:event_addRelatedCaseButtonActionPerformed

    private void assignedDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_assignedDateTextBoxMouseClicked
        clearDate(assignedDateTextBox, evt);
    }//GEN-LAST:event_assignedDateTextBoxMouseClicked

    private void reportDueDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_reportDueDateTextBoxMouseClicked
        clearDate(reportDueDateTextBox, evt);
    }//GEN-LAST:event_reportDueDateTextBoxMouseClicked

    private void dismissalDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_dismissalDateTextBoxMouseClicked
        clearDate(dismissalDateTextBox, evt);
    }//GEN-LAST:event_dismissalDateTextBoxMouseClicked

    private void deferredDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_deferredDateTextBoxMouseClicked
        clearDate(deferredDateTextBox, evt);
    }//GEN-LAST:event_deferredDateTextBoxMouseClicked

    private void appealReceivedTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appealReceivedTextBoxMouseClicked
        clearDate(appealReceivedTextBox, evt);
    }//GEN-LAST:event_appealReceivedTextBoxMouseClicked

    private void appealSentTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_appealSentTextBoxMouseClicked
        clearDate(appealSentTextBox, evt);
    }//GEN-LAST:event_appealSentTextBoxMouseClicked

    private void filedDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_filedDateTextBoxMouseClicked
        clearDate(filedDateTextBox, evt);
    }//GEN-LAST:event_filedDateTextBoxMouseClicked

    private void barginingUnitNoTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_barginingUnitNoTextBoxMouseClicked
        if(evt.getClickCount() == 2 && evt.getButton() != MouseEvent.BUTTON3) {
            if(barginingUnitNoTextBox.isEnabled()) {
                buNumberSearch search = new buNumberSearch((JFrame) Global.root.getRootPane().getParent(), true, employerNumberTextBox.getText().trim(), barginingUnitNoTextBox.getText().trim(), "");
                barginingUnitNoTextBox.setText(search.getBuNumber().contains("-") ? search.getBuNumber() : "");
                barginingUnitNoTextBox.setCaretPosition(0);
                if(employerNumberTextBox.getText().equals("")) {
                    employerNumberTextBox.setText(search.getBuNumber().split("-")[0]);
                }
                search.dispose();
            }
        }
    }//GEN-LAST:event_barginingUnitNoTextBoxMouseClicked

    private void turnInDateTextBoxMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_turnInDateTextBoxMouseClicked
        clearDate(turnInDateTextBox, evt);
    }//GEN-LAST:event_turnInDateTextBoxMouseClicked

    private void allegationTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_allegationTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_allegationTextBoxActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton addCaseHearingButton;
    private javax.swing.JButton addRelatedCaseButton;
    private javax.swing.JComboBox<String> aljComboBox;
    private javax.swing.JTextField allegationTextBox;
    private com.alee.extended.date.WebDateField appealReceivedTextBox;
    private com.alee.extended.date.WebDateField appealSentTextBox;
    private com.alee.extended.date.WebDateField assignedDateTextBox;
    private javax.swing.JTextField barginingUnitNoTextBox;
    private javax.swing.JTable boardMeetingTable;
    private javax.swing.JTextField courtCaseNumberTextBox;
    private javax.swing.JTextField courtTextBox;
    private com.alee.extended.date.WebDateField deferredDateTextBox;
    private com.alee.extended.date.WebDateField dismissalDateTextBox;
    private javax.swing.JTextField employerNumberTextBox;
    private com.alee.extended.date.WebDateField filedDateTextBox;
    private javax.swing.JComboBox<String> finalDispositionComboBox;
    private javax.swing.JComboBox<String> investigatorComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JComboBox<String> mediatorAssignedComboBox;
    private javax.swing.JComboBox<String> priorityComboBox;
    private javax.swing.JCheckBox probableCauseCheckBox;
    private javax.swing.JTable relatedCaseTable;
    private com.alee.extended.date.WebDateField reportDueDateTextBox;
    private javax.swing.JTextField serbCaseNumberTextBox;
    private javax.swing.JComboBox<String> statusComboBox;
    private com.alee.extended.date.WebDateField turnInDateTextBox;
    // End of variables declaration//GEN-END:variables
}
