/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.docket;

import java.awt.Color;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import parker.serb.Global;
import parker.serb.sql.Audit;
import parker.serb.sql.DocketLock;
import parker.serb.sql.Email;
import parker.serb.sql.EmailAttachment;
import parker.serb.sql.User;
import parker.serb.util.SlackNotification;

/**
 *
 * @author parker
 */
public class DocketRootPanel extends javax.swing.JPanel {

    List docs = new ArrayList<>();
    ActionListener actionListener;
    Thread docketThread = null;
    /**
     * Creates new form DocketRootPanel
     */
    public DocketRootPanel() {
        initComponents();
        setColumnSizes();
        addRenderer();
        addListeners();
    }

    private void addRenderer() {
        docketTable.setDefaultRenderer(Object.class, new TableCellRenderer(){
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

    public void loadDocketList() {
        List docketAccess = User.getDocketSections();

        SectionComboBox.removeActionListener(actionListener);

        SectionComboBox.removeAllItems();

        for(int i = 0; i < docketAccess.size(); i++) {
            SectionComboBox.addItem((String) docketAccess.get(i));
        }

        SectionComboBox.addActionListener(actionListener);

        if (Global.docketSection.equals("")) {
            SectionComboBox.setSelectedIndex(0);
        } else {
            SectionComboBox.setSelectedItem(Global.docketSection);
        }

        loadDocketListThread();
    }

    /**
     * Thread for letter queue count
     */
    public void loadDocketListThread(){
        if(docketThread == null) {
            docketThread = new Thread() {
                @Override
                public void run() {
                    threadDocketList();
                }
            };
            docketThread.start();
        }
    }

    private void threadDocketList() {
        while (true) {
            try {
                if(Global.activeSection.equals("Docketing")) {
                    docs.clear();
                    loadScanData(SectionComboBox.getSelectedItem().toString());
                    loadEmailData(SectionComboBox.getSelectedItem().toString());
                    loadMediaData(SectionComboBox.getSelectedItem().toString());
                    Collections.sort(docs, new CustomComparator());
                    loadTable();
                    Thread.sleep(30000); //milliseconds  (30 sec)
                } else {
                    docs.clear();
                }
            } catch (InterruptedException ex) {
                System.err.println("Thread Interrupted");
            }
        }
    }


    private void loadScanData(String section) {

        try {
            Files.walk(Paths.get(Global.scanPath + section)).forEach(filePath -> {
                try {
                    Path file = filePath;
                    if(file.getFileName().toString().endsWith(".pdf")) {
                        BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                        Email docket = new Email();
                        docket.id = 0;
                        docket.attachmentCount = "";
                        docket.receivedDate = new Date(attr.creationTime().toMillis());
                        docket.emailFrom = "";
                        docket.emailSubject = file.getFileName().toString();
                        docket.type = "Scan";
                        docs.add(docket);
                    }
                } catch (IOException ex) {
                    SlackNotification.sendNotification(ex);
                }
            });
        } catch (IOException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private void loadMediaData(String section) {
        try {
            if(Files.exists(Paths.get(Global.mediaPath + section)) == true) {
                Files.walk(Paths.get(Global.mediaPath + section)).forEach(filePath -> {
                    try {
                        if (Files.isRegularFile(filePath) && !Files.isHidden(filePath) && !filePath.startsWith(".DS_")) {
                            try {
                                Path file = filePath;
                                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                                Email docket = new Email();
                                docket.id = 0;
                                docket.attachmentCount = "";
                                docket.receivedDate = new Date(attr.creationTime().toMillis());
                                docket.emailFrom = "";
                                docket.emailSubject = file.getFileName().toString();
                                docket.type = "Media";
                                docs.add(docket);
                            } catch (IOException ex) {
                                SlackNotification.sendNotification(ex);
                            }
                        }
                    } catch (IOException ex) {
                        SlackNotification.sendNotification(ex);
                    }
                });
            } else {
                new File(Global.mediaPath + section).mkdirs();
            }
        } catch (IOException ex) {
            SlackNotification.sendNotification(ex);
        }
    }

    private void loadEmailData(String section) {

        List emailList = Email.getAllEmails(section);

        for (Object emailListItem : emailList) {
            docs.add((Email)emailListItem);
        }
    }

    public class CustomComparator implements Comparator<Email> {
    @Override
        public int compare(Email o1, Email o2) {
            return o1.receivedDate.compareTo(o2.receivedDate);
        }
    }

    private void setColumnSizes() {
        docketTable.getColumnModel().getColumn(0).setPreferredWidth(0);
        docketTable.getColumnModel().getColumn(0).setMinWidth(0);
        docketTable.getColumnModel().getColumn(0).setMaxWidth(0);
        docketTable.getColumnModel().getColumn(1).setPreferredWidth(150);
        docketTable.getColumnModel().getColumn(1).setMinWidth(150);
        docketTable.getColumnModel().getColumn(1).setMaxWidth(150);
        docketTable.getColumnModel().getColumn(2).setPreferredWidth(75);
        docketTable.getColumnModel().getColumn(2).setMinWidth(75);
        docketTable.getColumnModel().getColumn(2).setMaxWidth(75);
        docketTable.getColumnModel().getColumn(5).setPreferredWidth(125);
        docketTable.getColumnModel().getColumn(5).setMinWidth(125);
        docketTable.getColumnModel().getColumn(5).setMaxWidth(125);
    }


    private void addListeners() {
        docketTable.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                Global.root.getjButton9().setEnabled(docketTable.getSelectedRow() > -1);
            }
        });

        //build action listen that will be added and removed from the combobox
        actionListener = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchTextBox.setText("");
                Global.docketSection = SectionComboBox.getSelectedItem().toString();
                Global.root.getDocketingSectionLabel().setText(SectionComboBox.getSelectedItem().toString() + " Docketing");
                docs.clear();
                loadScanData(SectionComboBox.getSelectedItem().toString());
                loadEmailData(SectionComboBox.getSelectedItem().toString());
                loadMediaData(SectionComboBox.getSelectedItem().toString());
                Collections.sort(docs, new CustomComparator());
                loadTable();
            }
        };

        docketTable.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(docketTable.getSelectionModel().isSelectionEmpty()) {
                Global.root.getjButton1().setEnabled(false);
            } else {
                Global.root.getjButton1().setEnabled(true);
            }
        });

        docketTable.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() >= 2) {
                    displayFileDialog();
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

        //add listener for searching to be enabled
        searchTextBox.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                searchDocketList();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                searchDocketList();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                searchDocketList();
            }
        });
    }

    public void displayFileDialog() {
        String section = SectionComboBox.getSelectedItem().toString();

        if(docketTable.getValueAt(docketTable.getSelectedRow(), 2).equals("Scan")) {
            DocketLock docketLock = DocketLock.checkLock(section, docketTable.getValueAt(docketTable.getSelectedRow(), 4).toString());
            if(docketLock == null) {
                String value = docketTable.getValueAt(docketTable.getSelectedRow(), 4).toString();
                DocketLock.addLock(section, value);
                new scanFileDialog((JFrame) Global.root.getRootPane().getParent(),
                        true,
                        docketTable.getValueAt(docketTable.getSelectedRow(), 4).toString(),section,
                        docketTable.getValueAt(docketTable.getSelectedRow(), 1).toString());
                DocketLock.removeLock(section, value);
                reloadTableAfterFiling();
            } else {
                new DocketLockDialog((JFrame) Global.root.getRootPane().getParent(), true, docketLock);
            }
        } else if(docketTable.getValueAt(docketTable.getSelectedRow(), 2).equals("Email")){
            DocketLock docketLock = DocketLock.checkLock(section, docketTable.getValueAt(docketTable.getSelectedRow(), 0).toString());
            if(docketLock == null) {
                String value = docketTable.getValueAt(docketTable.getSelectedRow(), 0).toString();

                DocketLock.addLock(section, value);
                new fileEmailDialog((JFrame) Global.root.getRootPane().getParent(),
                        true,
                        docketTable.getValueAt(docketTable.getSelectedRow(), 0).toString(),
                        section,
                        docketTable.getValueAt(docketTable.getSelectedRow(), 1).toString());
                DocketLock.removeLock(section, value);
                reloadTableAfterFiling();
            } else {
                new DocketLockDialog((JFrame) Global.root.getRootPane().getParent(), true, docketLock);
            }
        } else if(docketTable.getValueAt(docketTable.getSelectedRow(), 2).equals("Media")){
            DocketLock docketLock = DocketLock.checkLock(section, docketTable.getValueAt(docketTable.getSelectedRow(), 4).toString());
            if(docketLock == null) {
                String value = docketTable.getValueAt(docketTable.getSelectedRow(), 4).toString();
                DocketLock.addLock(section, value);
                new mediaFileDialog((JFrame) Global.root.getRootPane().getParent(),
                    true,
                    docketTable.getValueAt(docketTable.getSelectedRow(), 4).toString(),section);
                DocketLock.removeLock(section, value);
                reloadTableAfterFiling();
            } else {
                new DocketLockDialog((JFrame) Global.root.getRootPane().getParent(), true, docketLock);
            }
        }
    }

    private void reloadTableAfterFiling() {
        docs.clear();
        loadScanData(SectionComboBox.getSelectedItem().toString());
        loadEmailData(SectionComboBox.getSelectedItem().toString());
        loadMediaData(SectionComboBox.getSelectedItem().toString());
        Collections.sort(docs, new CustomComparator());
        loadTable();
    }

    private void searchDocketList() {
        DefaultTableModel model = (DefaultTableModel) docketTable.getModel();
        model.setRowCount(0);

        for (Object docketItem : docs) {
            Email docket = (Email) docketItem;
            if(docket.type.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || docket.emailFrom.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || docket.emailSubject.toLowerCase().contains(searchTextBox.getText().toLowerCase())
                    || docket.attachmentCount.toLowerCase().contains(searchTextBox.getText().toLowerCase())) {
                model.addRow(new Object[] {docket.id
                        , Global.mmddyyyyhhmma.format(docket.receivedDate)
                        , docket.type, docket.emailFrom, docket.emailSubject
                        , docket.attachmentCount});
            }
        }
    }

    private void loadTable() {
        DefaultTableModel model = (DefaultTableModel) docketTable.getModel();

        model.setRowCount(0);

        for (Object docketItem : docs) {
            Email docket = (Email) docketItem;
            model.addRow(new Object[] {docket.id, Global.mmddyyyyhhmma.format(docket.receivedDate), docket.type, docket.emailFrom, docket.emailSubject, docket.attachmentCount});
        }
    }

    public void delete() {
        if(docketTable.getValueAt(docketTable.getSelectedRow(), 2).equals("Scan")) {
            deleteScan();
        } else if(docketTable.getValueAt(docketTable.getSelectedRow(), 2).equals("Email")) {
            deleteEmail();
        } else if(docketTable.getValueAt(docketTable.getSelectedRow(), 2).equals("Media")) {
            deleteMedia();
        }
    }

    private void deleteScan() {
        ConfirmDocketDelete delete = new ConfirmDocketDelete((JFrame) Global.root, true);

        if(delete.isDelete()) {
            File scanFile = new File(Global.scanPath + SectionComboBox.getSelectedItem().toString() + File.separatorChar + docketTable.getValueAt(docketTable.getSelectedRow(), 4));
            scanFile.delete();
            for(int i = 0; i < docs.size(); i++) {
                Email doc = (Email) docs.get(i);
                if(doc.emailSubject.equals(docketTable.getValueAt(docketTable.getSelectedRow(), 4))) {
                    docs.remove(doc);
                }
            }

            Audit.addAuditEntry("Removed " + docketTable.getValueAt(docketTable.getSelectedRow(), 4).toString().trim() + " from Docket");

            loadTable();
        }
    }

    private void deleteMedia() {
        ConfirmDocketDelete delete = new ConfirmDocketDelete((JFrame) Global.root, true);

        if(delete.isDelete()) {
            File scanFile = new File(Global.mediaPath + SectionComboBox.getSelectedItem().toString() + File.separatorChar + docketTable.getValueAt(docketTable.getSelectedRow(), 4));
            scanFile.delete();
            for(int i = 0; i < docs.size(); i++) {
                Email doc = (Email) docs.get(i);
                if(doc.emailSubject.equals(docketTable.getValueAt(docketTable.getSelectedRow(), 4))) {
                    docs.remove(doc);
                }
            }

            Audit.addAuditEntry("Removed " + docketTable.getValueAt(docketTable.getSelectedRow(), 4).toString().trim() + " from Docket");

            loadTable();
        }
    }

    private void deleteEmail() {
        ConfirmDocketDelete delete = new ConfirmDocketDelete((JFrame) Global.root, true);

        if(delete.isDelete()) {

            for(int i = 0; i < docs.size(); i++) {
                Email doc = (Email) docs.get(i);
                if(doc.id == (int) docketTable.getValueAt(docketTable.getSelectedRow(), 0)) {

                    File bodyFile = new File(Global.emailPath + SectionComboBox.getSelectedItem().toString() + File.separatorChar + doc.emailBodyFileName);

                    if(bodyFile.exists()) {
                        bodyFile.delete();
                    }

                    Email.deleteEmailEntry(doc.id);

                    List emailAttachmentList = EmailAttachment.getAttachmentList(Integer.toString(doc.id));

                    for(int j = 0; j < emailAttachmentList.size(); j++) {
                        EmailAttachment attach = (EmailAttachment) emailAttachmentList.get(j);

                        File attachFile = new File(Global.emailPath + SectionComboBox.getSelectedItem().toString() + File.separatorChar + attach.fileName);

                        if(attachFile.exists()) {
                            attachFile.delete();
                        }

                        EmailAttachment.deleteEmailAttachmentEntry(attach.id);
                    }

                    Audit.addAuditEntry("Removed " + docketTable.getValueAt(docketTable.getSelectedRow(), 4).toString().trim() + " from Docket");

                    docs.remove(doc);
                }
            }
            loadTable();
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        docketTable = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        searchTextBox = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jLabel4 = new javax.swing.JLabel();
        SectionComboBox = new javax.swing.JComboBox<>();

        jLabel2.setText("jLabel2");

        jLabel3.setText("jLabel3");

        docketTable.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Date", "Type", "From", "Title", "Attachments"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        docketTable.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(docketTable);

        jLabel1.setText("Search:");

        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        jLabel4.setText("Section:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 609, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(SectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(searchTextBox)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jButton1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(searchTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(SectionComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 258, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        searchTextBox.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JComboBox<String> SectionComboBox;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JTable docketTable;
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextField searchTextBox;
    // End of variables declaration//GEN-END:variables
}
