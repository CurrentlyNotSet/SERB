/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.notes;

import java.awt.Color;
import parker.serb.Global;
import parker.serb.sql.CMDSCase;
import parker.serb.sql.CSCCase;
import parker.serb.sql.MEDCase;
import parker.serb.sql.ORGCase;
import parker.serb.sql.REPCase;
import parker.serb.sql.ULPCase;

/**
 *
 * @author parker
 */
public class NotesPanel extends javax.swing.JPanel {

    String orginalNote = "";
    /**
     * Creates new form NotesPanel
     */
    public NotesPanel() {
        initComponents();
    }
    
    public void clearAll() {
        jTextArea2.setText("");
    }
    
    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        Global.root.getjButton9().setVisible(true);
        jTextArea2.setEnabled(true);
        jTextArea2.setBackground(Color.white);
    }
    
    public void disableUpdate(boolean save) {
        if(save)
            saveInformation();
        
        Global.root.getjButton2().setText("Update");
        Global.root.getjButton9().setVisible(false);
        jTextArea2.setBackground(new Color(238,238,238));
        jTextArea2.setEnabled(false);
        jTextArea2.setText(orginalNote);
    }
    
    public void loadInformation() {
        if(Global.activeSection.equals("REP")) {
            jTextArea2.setText(REPCase.loadNote());
        } else if(Global.activeSection.equals("ULP")) {
            jTextArea2.setText(ULPCase.loadNote());
        } else if(Global.activeSection.equals("MED")) {
            jTextArea2.setText(MEDCase.loadNote());
        } else if(Global.activeSection.equals("ORG")) {
            jTextArea2.setText(ORGCase.loadNote());
        } else if(Global.activeSection.equals("Civil Service Commission")) {
            jTextArea2.setText(CSCCase.loadNote());
        } else if(Global.activeSection.equals("CMDS")) {
            jTextArea2.setText(CMDSCase.loadNote());
        } else if(Global.activeSection.equals("Hearings")) {
            switch(Global.caseType) {
                case "REP":
                case "RBT":
                    jTextArea2.setText(REPCase.loadNote());
                    break;
                case "ULP":
                case "ERC":
                case "JWD":
                    jTextArea2.setText(ULPCase.loadNote());
                    break;
                case "MED":
                case "STK":
                case "NCN":
                case "CON":
                    jTextArea2.setText(MEDCase.loadNote());
                    break;
            }
        }
        orginalNote = jTextArea2.getText();
    }
    
    public void saveInformation() {
        if(Global.activeSection.equals("REP")) {
            REPCase.updateNote(jTextArea2.getText());
        } else if(Global.activeSection.equals("ULP")) {
            ULPCase.updateNote(jTextArea2.getText());
        } else if(Global.activeSection.equals("MED")) {
            MEDCase.updateNote(jTextArea2.getText());
        } else if(Global.activeSection.equals("ORG")) {
            ORGCase.updateNote(jTextArea2.getText());
        } else if(Global.activeSection.equals("Civil Service Commission")) {
            CSCCase.updateNote(jTextArea2.getText());
        } else if(Global.activeSection.equals("CMDS")) {
            CMDSCase.updateNote(jTextArea2.getText());
        } else if(Global.activeSection.equals("Hearings")) {
            switch(Global.caseType) {
                case "REP":
                case "RBT":
                    REPCase.updateNote(jTextArea2.getText());
                    break;
                case "ULP":
                case "ERC":
                case "JWD":
                    ULPCase.updateNote(jTextArea2.getText());
                    break;
                case "MED":
                case "STK":
                case "NCN":
                case "CON":
                    MEDCase.updateNote(jTextArea2.getText());
                    break;
            }
        }
        orginalNote = jTextArea2.getText();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane2 = new javax.swing.JScrollPane();
        jTextArea2 = new javax.swing.JTextArea();

        jTextArea2.setBackground(new java.awt.Color(238, 238, 238));
        jTextArea2.setColumns(20);
        jTextArea2.setFont(new java.awt.Font("Lucida Grande", 0, 15)); // NOI18N
        jTextArea2.setLineWrap(true);
        jTextArea2.setRows(5);
        jTextArea2.setWrapStyleWord(true);
        jTextArea2.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jTextArea2.setEnabled(false);
        jScrollPane2.setViewportView(jTextArea2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 461, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane2, javax.swing.GroupLayout.DEFAULT_SIZE, 342, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTextArea jTextArea2;
    // End of variables declaration//GEN-END:variables
}
