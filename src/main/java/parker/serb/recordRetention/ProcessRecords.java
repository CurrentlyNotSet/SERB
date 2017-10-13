/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.recordRetention;


import com.alee.laf.optionpane.WebOptionPane;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JTable;
import javax.swing.RowSorter;
import javax.swing.SortOrder;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import parker.serb.Global;
import parker.serb.bookmarkProcessing.generateDocument;
import parker.serb.bookmarkProcessing.questionsCMDSModel;
import parker.serb.bookmarkProcessing.questionsCMDSPanel;
import parker.serb.sql.Activity;
import parker.serb.sql.Audit;
import parker.serb.sql.CMDSDocuments;
import parker.serb.sql.PurgedActivity;
import parker.serb.sql.SMDSDocuments;
import parker.serb.util.FileService;
import parker.serb.util.NumberFormatService;

/**
 *
 * @author parker
 */
public class ProcessRecords extends javax.swing.JDialog {



    public ProcessRecords(java.awt.Frame parent, boolean modal, JTable tablePassed) {
        super(parent, modal);
        initComponents();
        processThread(tablePassed);
        setLocationRelativeTo(parent);
        setVisible(true);
    }

    private void processThread(JTable table) {
        Thread temp = new Thread(() -> {
            PurgeRecords(table);
            dispose();
        });
        temp.start();
    }

    private void PurgeRecords(JTable table) {
        long lStartTime = System.currentTimeMillis();
        
        List<PurgedActivity> selectedActivityList = new ArrayList<>();
        
        
        //Sort on CaseNumber
        TableRowSorter<TableModel> sorter = new TableRowSorter<>(table.getModel());
        List<RowSorter.SortKey> sortKeys = new ArrayList<>(1);
        sortKeys.add(new RowSorter.SortKey(3, SortOrder.ASCENDING)); //Sort On Case Number
        sorter.setSortKeys(sortKeys);
        table.setRowSorter(sorter);
        
        //Gather Selected Records
        for (int i = 0; i < table.getRowCount(); i++) {
            if (table.getValueAt(i, 1).equals(true)) {
                selectedActivityList.add((PurgedActivity) table.getValueAt(i, 0));
            }
        }
        
        //Process List
        for (PurgedActivity item : selectedActivityList){
            handleRecord(item);
        }
        
        //Update Cases with Purged Entry
        
        long lEndTime = System.currentTimeMillis();
        System.out.println("Purge Process Time: " + NumberFormatService.convertLongToTime(lEndTime - lStartTime));
    }

    
    private void handleRecord(PurgedActivity item) {
        //insert record
        if (PurgedActivity.insertPurgedRecord(item)) {
            //DeleteFile
//            if (deleteFile(item)) {
//                //Delete Activity Record
//                PurgedActivity.deleteActivityByID(item.activityID);
//            }
        }
    }


    private boolean deleteFile(PurgedActivity item) {
        String filePath = "";
        
        if (Global.activeSection.equalsIgnoreCase("Civil Service Commission")
                    || Global.activeSection.equalsIgnoreCase("CSC")
                    || Global.activeSection.equalsIgnoreCase("ORG")) {
                filePath = Global.activityPath
                        + (Global.activeSection.equals("Civil Service Commission")
                        ? Global.caseType : Global.activeSection) + File.separator 
                        + item.caseNumber + File.separatorChar;
            } else {
                filePath = Global.activityPath + File.separatorChar
                        + Global.activeSection + File.separatorChar
                        + item.caseYear + File.separatorChar
                        + (item.caseYear + "-" + item.caseType + "-" + item.caseMonth + "-" + item.caseNumber)
                        + File.separatorChar;
            }
        
        
        File purgeFile = new File(filePath + item.fileName);

        if (purgeFile.exists()) {
            if (purgeFile.renameTo(purgeFile)) {
                return true;
            }
        }
        
        
        
        
        
        return false;
    }
    

    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLayeredPane = new javax.swing.JLayeredPane();
        loadingPanel = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        HeaderLabel = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DO_NOTHING_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(340, 300));
        setResizable(false);

        jLayeredPane.setLayout(new javax.swing.OverlayLayout(jLayeredPane));

        loadingPanel.setMaximumSize(new java.awt.Dimension(340, 300));
        loadingPanel.setMinimumSize(new java.awt.Dimension(340, 300));
        loadingPanel.setPreferredSize(new java.awt.Dimension(340, 300));

        jLabel8.setBackground(new java.awt.Color(255, 255, 255));
        jLabel8.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel8.setIcon(new javax.swing.ImageIcon(getClass().getResource("/loading_spinner.gif"))); // NOI18N

        HeaderLabel.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        HeaderLabel.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        HeaderLabel.setText("Processing Records");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel9.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel9.setText("Please Wait...");

        javax.swing.GroupLayout loadingPanelLayout = new javax.swing.GroupLayout(loadingPanel);
        loadingPanel.setLayout(loadingPanelLayout);
        loadingPanelLayout.setHorizontalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, loadingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 352, Short.MAX_VALUE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(HeaderLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        loadingPanelLayout.setVerticalGroup(
            loadingPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(loadingPanelLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(HeaderLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel8, javax.swing.GroupLayout.DEFAULT_SIZE, 227, Short.MAX_VALUE)
                .addContainerGap())
        );

        jLayeredPane.add(loadingPanel);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane)
                .addGap(0, 0, 0))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(jLayeredPane)
                .addGap(0, 0, 0))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel HeaderLabel;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLayeredPane jLayeredPane;
    private javax.swing.JPanel loadingPanel;
    // End of variables declaration//GEN-END:variables
}
