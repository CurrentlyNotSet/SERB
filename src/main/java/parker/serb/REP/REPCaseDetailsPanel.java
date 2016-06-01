/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.REP;

import java.awt.Color;
import parker.serb.Global;
import parker.serb.sql.REPCase;

/**
 *
 * @author parkerjohnston
 */
public class REPCaseDetailsPanel extends javax.swing.JPanel {

    REPCase repCase;
    /**
     * Creates new form REPCaseDetailsPanel
     */
    public REPCaseDetailsPanel() {
        initComponents();
        hideNotRequiredInformation();
    }
    
    private void hideNotRequiredInformation() {
        //hide opt in
        optInIncludedLabel.setVisible(false);
        optInIncludedScrollPane.setVisible(false);
        optInIncludedTextArea.setText("");
        
        //handle professional non professional checkbox
        displayProfessionalNonProfessionalInformation();
        
        //display certifications and unit information
        displayCertificationsUnitChangeInformation(false);
        clearCertificationsUnitChangeInformation();
    }
    
    public void loadInformation() {
        optInIncludedTextArea.setText("");
        professionalNonProfessionalCheckBox.setSelected(false);
        clearProfessionalNonProfessionalInformation();
        clearCertificationsUnitChangeInformation();
        
        repCase = REPCase.loadCaseDetails();
        
        filedByComboBox.setSelectedItem(repCase.fileBy);
        bargainingUnitIncludedTextArea.setText(repCase.bargainingUnitIncluded);
        bargainingUnitExcludedTextArea.setText(repCase.bargainingUnitExcluded);
        
        //hide/show opt in text area
        if(repCase.type.equalsIgnoreCase("Opt-In VR") ||
                repCase.type.equalsIgnoreCase("Opt-In RC")) {
            optInIncludedLabel.setVisible(true);
            optInIncludedScrollPane.setVisible(true);
            optInIncludedTextArea.setText(repCase.optInIncluded);
        } else {
            optInIncludedLabel.setVisible(false);
            optInIncludedScrollPane.setVisible(false);
            optInIncludedTextArea.setText("");
        }
        
        professionalNonProfessionalCheckBox.setSelected(repCase.professionalNonProfessional);
        
        if(professionalNonProfessionalCheckBox.isSelected()) {
            professionalIncludedTextArea.setText(repCase.professionalIncluded);
            professionalExcludedTextArea.setText(repCase.professionalExcluded);
            nonProfessionalIncludedTextArea.setText(repCase.nonProfessionalIncluded);
            nonProfessionalExcludedTextArea.setText(repCase.nonProfessionalExcluded);
        } else {
            professionalIncludedTextArea.setText("");
            professionalExcludedTextArea.setText("");
            nonProfessionalIncludedTextArea.setText("");
            nonProfessionalExcludedTextArea.setText("");
        }
        
        if(repCase.type.equalsIgnoreCase("AC") ||
            repCase.type.equalsIgnoreCase("UC") ||
            repCase.type.equalsIgnoreCase("JTAC") ||
            repCase.type.equalsIgnoreCase("JTUC")) {
            displayCertificationsUnitChangeInformation(true);
            toReflectTextBox.setText(repCase.toReflect);
            typeFiledByComboBox.setSelectedItem(repCase.typeFiledBy);
            typeFiledViaComboBox.setSelectedItem(repCase.typeFiledVia);
            PositionStatementFiledByTextBox.setText(repCase.positionStatementFiledBy);
            EEONameChangeFromTextBox.setText(repCase.EEONameChangeFrom);
            EEONameChangeToTextBox.setText(repCase.EEONameChangeTo);
            ERNameChangeFromTextBox.setText(repCase.ERNameChangeFrom);
            ERNameChangeToTextBox.setText(repCase.ERNameChangeTo);
        } else {
            displayCertificationsUnitChangeInformation(false);
        }
    }
    
    public void enableUpdate() {
        Global.root.getjButton2().setText("Save");
        
        Global.root.getjButton9().setVisible(true);
        
        filedByComboBox.setEnabled(true);
        bargainingUnitIncludedTextArea.setEnabled(true);
        bargainingUnitIncludedTextArea.setBackground(Color.WHITE);
        bargainingUnitExcludedTextArea.setEnabled(true);
        bargainingUnitExcludedTextArea.setBackground(Color.WHITE);
        optInIncludedTextArea.setEnabled(true);
        optInIncludedTextArea.setBackground(Color.WHITE);
        professionalNonProfessionalCheckBox.setEnabled(true);
        professionalIncludedTextArea.setEnabled(true);
        professionalIncludedTextArea.setBackground(Color.WHITE);
        professionalExcludedTextArea.setEnabled(true);
        professionalExcludedTextArea.setBackground(Color.WHITE);
        nonProfessionalIncludedTextArea.setEnabled(true);
        nonProfessionalIncludedTextArea.setBackground(Color.WHITE);
        nonProfessionalExcludedTextArea.setEnabled(true);
        nonProfessionalExcludedTextArea.setBackground(Color.WHITE);
        toReflectTextBox.setEnabled(true);
        toReflectTextBox.setBackground(Color.WHITE);
        typeFiledByComboBox.setEnabled(true);
        typeFiledViaComboBox.setEnabled(true);
        PositionStatementFiledByTextBox.setEnabled(true);
        PositionStatementFiledByTextBox.setBackground(Color.WHITE);
        EEONameChangeFromTextBox.setEnabled(true);
        EEONameChangeFromTextBox.setBackground(Color.WHITE);
        EEONameChangeToTextBox.setEnabled(true);
        EEONameChangeToTextBox.setBackground(Color.WHITE);
        ERNameChangeFromTextBox.setEnabled(true);
        ERNameChangeFromTextBox.setBackground(Color.WHITE);
        ERNameChangeToTextBox.setEnabled(true);
        ERNameChangeToTextBox.setBackground(Color.WHITE);
    }
    
    public void disableUpdate(boolean runSave) {
        Global.root.getjButton2().setText("Update");
        
        Global.root.getjButton9().setVisible(false);
        
        filedByComboBox.setEnabled(false);
        bargainingUnitIncludedTextArea.setEnabled(false);
        bargainingUnitIncludedTextArea.setBackground(new Color(238,238,238));
        bargainingUnitExcludedTextArea.setEnabled(false);
        bargainingUnitExcludedTextArea.setBackground(new Color(238,238,238));
        optInIncludedTextArea.setEnabled(false);
        optInIncludedTextArea.setBackground(new Color(238,238,238));
        professionalNonProfessionalCheckBox.setEnabled(false);
        professionalIncludedTextArea.setEnabled(false);
        professionalIncludedTextArea.setBackground(new Color(238,238,238));
        professionalExcludedTextArea.setEnabled(false);
        professionalExcludedTextArea.setBackground(new Color(238,238,238));
        nonProfessionalIncludedTextArea.setEnabled(false);
        nonProfessionalIncludedTextArea.setBackground(new Color(238,238,238));
        nonProfessionalExcludedTextArea.setEnabled(false);
        nonProfessionalExcludedTextArea.setBackground(new Color(238,238,238));
        toReflectTextBox.setEnabled(false);
        toReflectTextBox.setBackground(new Color(238,238,238));
        typeFiledByComboBox.setEnabled(false);
        typeFiledViaComboBox.setEnabled(false);
        PositionStatementFiledByTextBox.setEnabled(false);
        PositionStatementFiledByTextBox.setBackground(new Color(238,238,238));
        EEONameChangeFromTextBox.setEnabled(false);
        EEONameChangeFromTextBox.setBackground(new Color(238,238,238));
        EEONameChangeToTextBox.setEnabled(false);
        EEONameChangeToTextBox.setBackground(new Color(238,238,238));
        ERNameChangeFromTextBox.setEnabled(false);
        ERNameChangeFromTextBox.setBackground(new Color(238,238,238));
        ERNameChangeToTextBox.setEnabled(false);
        ERNameChangeToTextBox.setBackground(new Color(238,238,238));
        
        if(runSave) {
            saveInformation();
        } else {
            loadInformation();
        }
    }
    
    private void saveInformation() {
       REPCase newRepCase = new REPCase();
       newRepCase.fileBy = filedByComboBox.getSelectedItem().toString().equals("") ? null : filedByComboBox.getSelectedItem().toString();
       newRepCase.bargainingUnitIncluded = bargainingUnitIncludedTextArea.getText().trim().equals("") ? null : bargainingUnitIncludedTextArea.getText().trim();
       newRepCase.bargainingUnitExcluded = bargainingUnitExcludedTextArea.getText().trim().equals("") ? null : bargainingUnitExcludedTextArea.getText().trim();
       
       if(optInIncludedScrollPane.isVisible()) {
           newRepCase.optInIncluded = optInIncludedTextArea.getText().trim().equals("") ? null : optInIncludedTextArea.getText().trim();
       } else {
           optInIncludedTextArea.setText("");
           newRepCase.optInIncluded = null;
       }
       
       newRepCase.professionalNonProfessional = professionalNonProfessionalCheckBox.isSelected();
       
       if(professionalNonProfessionalCheckBox.isSelected()) {
           newRepCase.professionalIncluded = professionalIncludedTextArea.getText().trim().equals("") ? null : professionalIncludedTextArea.getText().trim();
       } else {
           professionalIncludedTextArea.setText("");
           newRepCase.professionalIncluded = null;
       }
       
       if(professionalNonProfessionalCheckBox.isSelected()) {
           newRepCase.professionalExcluded = professionalExcludedTextArea.getText().trim().equals("") ? null : professionalExcludedTextArea.getText().trim();
       } else {
           professionalExcludedTextArea.setText("");
           newRepCase.professionalExcluded = null;
       }
       
       if(professionalNonProfessionalCheckBox.isSelected()) {
           newRepCase.nonProfessionalIncluded = nonProfessionalIncludedTextArea.getText().trim().equals("") ? null : nonProfessionalIncludedTextArea.getText().trim();
       } else {
           nonProfessionalIncludedTextArea.setText("");
           newRepCase.nonProfessionalIncluded = null;
       }
       
       if(professionalNonProfessionalCheckBox.isSelected()) {
           newRepCase.nonProfessionalExcluded = nonProfessionalExcludedTextArea.getText().trim().equals("") ? null : nonProfessionalExcludedTextArea.getText().trim();
       } else {
           nonProfessionalExcludedTextArea.setText("");
           newRepCase.nonProfessionalExcluded = null;
       }
       
       if(toReflectTextBox.isVisible()) {
           newRepCase.toReflect = toReflectTextBox.getText().trim().equals("") ? null : toReflectTextBox.getText().trim();
       } else {
           toReflectTextBox.setText("");
           newRepCase.toReflect = null;
       }
       
       if(typeFiledByComboBox.isVisible()) {
           newRepCase.typeFiledBy = typeFiledByComboBox.getSelectedItem() == null ? null : typeFiledByComboBox.getSelectedItem().toString();
       } else {
           typeFiledByComboBox.setSelectedItem("");
           newRepCase.typeFiledBy = null;
       }
       
       if(typeFiledViaComboBox.isVisible()) {
           newRepCase.typeFiledVia = typeFiledViaComboBox.getSelectedItem() == null ? null : typeFiledViaComboBox.getSelectedItem().toString().trim();
       } else {
           typeFiledViaComboBox.setSelectedItem("");
           newRepCase.typeFiledVia = null;
       }
       
       if(PositionStatementFiledByTextBox.isVisible()) {
           newRepCase.positionStatementFiledBy = PositionStatementFiledByTextBox.getText().trim().equals("") ? null : PositionStatementFiledByTextBox.getText().trim();
       } else {
           PositionStatementFiledByTextBox.setText("");
           newRepCase.positionStatementFiledBy = null;
       }
       
       if(EEONameChangeFromTextBox.isVisible()) {
           newRepCase.EEONameChangeFrom = EEONameChangeFromTextBox.getText().trim().equals("") ? null : EEONameChangeFromTextBox.getText().trim();
       } else {
           EEONameChangeFromTextBox.setText("");
           newRepCase.EEONameChangeFrom = null;
       }
       
       if(EEONameChangeToTextBox.isVisible()) {
           newRepCase.EEONameChangeTo = EEONameChangeToTextBox.getText().trim().equals("") ? null : EEONameChangeToTextBox.getText().trim();
       } else {
           EEONameChangeFromTextBox.setText("");
           newRepCase.EEONameChangeTo = null;
       }
       
       if(ERNameChangeFromTextBox.isVisible()) {
           newRepCase.ERNameChangeFrom = ERNameChangeFromTextBox.getText().trim().equals("") ? null : ERNameChangeFromTextBox.getText().trim();
       } else {
           ERNameChangeFromTextBox.setText("");
           newRepCase.ERNameChangeFrom = null;
       }
       
       if(ERNameChangeToTextBox.isVisible()) {
           newRepCase.ERNameChangeTo = ERNameChangeToTextBox.getText().trim().equals("") ? null : ERNameChangeToTextBox.getText().trim();
       } else {
           ERNameChangeToTextBox.setText("");
           newRepCase.ERNameChangeTo = null;
       }
       
       REPCase.updateCaseDetails(newRepCase, repCase);
       repCase = REPCase.loadCaseDetails();
    }
    
    public void clearAll() {
        filedByComboBox.setSelectedItem("");
        bargainingUnitIncludedTextArea.setText("");
        bargainingUnitExcludedTextArea.setText("");
        
        optInIncludedLabel.setVisible(false);
        optInIncludedScrollPane.setVisible(false);
        optInIncludedTextArea.setText("");
        
        professionalNonProfessionalCheckBox.setSelected(false);
        displayProfessionalNonProfessionalInformation();
        
        clearCertificationsUnitChangeInformation();
        displayCertificationsUnitChangeInformation(false);
    }
    
    private void displayProfessionalNonProfessionalInformation() {
        if(professionalNonProfessionalCheckBox.isSelected()) {
            professionalIncludedPanel.setVisible(true);
            professionalExcludedPanel.setVisible(true);
            nonProfessionalExcludedPanel.setVisible(true);
            nonProfessionalIncludedPanel.setVisible(true);
        } else {
            professionalIncludedPanel.setVisible(false);
            professionalExcludedPanel.setVisible(false);
            nonProfessionalExcludedPanel.setVisible(false);
            nonProfessionalIncludedPanel.setVisible(false);
        }
    }
    
    private void clearProfessionalNonProfessionalInformation() {
        professionalIncludedTextArea.setText("");
        professionalExcludedTextArea.setText("");
        nonProfessionalIncludedTextArea.setText("");
        nonProfessionalExcludedTextArea.setText("");
    }
    
    private void displayCertificationsUnitChangeInformation(boolean show) {
        certificationUnitChangesLabel.setVisible(show);
        toReflectLabel.setVisible(show);
        toReflectTextBox.setVisible(show);
        typeFiledByLabel.setVisible(show);
        typeFiledByComboBox.setVisible(show);
        typeFiledViaLabel.setVisible(show);
        typeFiledViaComboBox.setVisible(show);
        positionStatementFiledByLabel.setVisible(show);
        PositionStatementFiledByTextBox.setVisible(show);
        EEONameChangeFromLabel.setVisible(show);
        EEONameChangeFromTextBox.setVisible(show);
        EEONameChangeToLabel.setVisible(show);
        EEONameChangeToTextBox.setVisible(show);
        ERNameChangeFromLabel.setVisible(show);
        ERNameChangeFromTextBox.setVisible(show);
        ERNameChangeToLabel.setVisible(show);
        ERNameChangeToTextBox.setVisible(show);
    }
    
    private void clearCertificationsUnitChangeInformation() {
        toReflectTextBox.setText("");
        typeFiledByComboBox.setSelectedItem("");
        typeFiledViaComboBox.setSelectedItem("");
        PositionStatementFiledByTextBox.setText("");
        EEONameChangeFromTextBox.setText("");
        EEONameChangeToTextBox.setText("");
        ERNameChangeFromTextBox.setText("");
        ERNameChangeToTextBox.setText("");
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        filedByComboBox = new javax.swing.JComboBox<>();
        professionalNonProfessionalCheckBox = new javax.swing.JCheckBox();
        optInIncludedLabel = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane10 = new javax.swing.JScrollPane();
        bargainingUnitIncludedTextArea = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane6 = new javax.swing.JScrollPane();
        bargainingUnitExcludedTextArea = new javax.swing.JTextArea();
        professionalExcludedPanel = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jScrollPane12 = new javax.swing.JScrollPane();
        professionalExcludedTextArea = new javax.swing.JTextArea();
        professionalIncludedPanel = new javax.swing.JPanel();
        professionalIncludedLabel = new javax.swing.JLabel();
        jScrollPane11 = new javax.swing.JScrollPane();
        professionalIncludedTextArea = new javax.swing.JTextArea();
        certificationUnitChangesLabel = new javax.swing.JLabel();
        toReflectLabel = new javax.swing.JLabel();
        typeFiledByLabel = new javax.swing.JLabel();
        positionStatementFiledByLabel = new javax.swing.JLabel();
        EEONameChangeFromLabel = new javax.swing.JLabel();
        ERNameChangeFromLabel = new javax.swing.JLabel();
        toReflectTextBox = new javax.swing.JTextField();
        typeFiledByComboBox = new javax.swing.JComboBox<>();
        typeFiledViaLabel = new javax.swing.JLabel();
        typeFiledViaComboBox = new javax.swing.JComboBox<>();
        PositionStatementFiledByTextBox = new javax.swing.JTextField();
        EEONameChangeToLabel = new javax.swing.JLabel();
        EEONameChangeToTextBox = new javax.swing.JTextField();
        EEONameChangeFromTextBox = new javax.swing.JTextField();
        ERNameChangeFromTextBox = new javax.swing.JTextField();
        ERNameChangeToLabel = new javax.swing.JLabel();
        ERNameChangeToTextBox = new javax.swing.JTextField();
        optInIncludedScrollPane = new javax.swing.JScrollPane();
        optInIncludedTextArea = new javax.swing.JTextArea();
        nonProfessionalExcludedPanel = new javax.swing.JPanel();
        jLabel7 = new javax.swing.JLabel();
        jScrollPane13 = new javax.swing.JScrollPane();
        nonProfessionalExcludedTextArea = new javax.swing.JTextArea();
        nonProfessionalIncludedPanel = new javax.swing.JPanel();
        professionalIncludedLabel1 = new javax.swing.JLabel();
        jScrollPane14 = new javax.swing.JScrollPane();
        nonProfessionalIncludedTextArea = new javax.swing.JTextArea();

        jLabel1.setText("Filed By:");

        filedByComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Employer", "Employee", "Union", "Joint", "" }));
        filedByComboBox.setSelectedIndex(4);
        filedByComboBox.setEnabled(false);

        professionalNonProfessionalCheckBox.setText("Professional/Non-Professional");
        professionalNonProfessionalCheckBox.setEnabled(false);
        professionalNonProfessionalCheckBox.addChangeListener(new javax.swing.event.ChangeListener() {
            public void stateChanged(javax.swing.event.ChangeEvent evt) {
                professionalNonProfessionalCheckBoxStateChanged(evt);
            }
        });
        professionalNonProfessionalCheckBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                professionalNonProfessionalCheckBoxActionPerformed(evt);
            }
        });

        optInIncludedLabel.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        optInIncludedLabel.setText("Opt-In INCLUDED:");

        jLabel2.setText("Bargaining Unit INCLUDED:");

        bargainingUnitIncludedTextArea.setBackground(new java.awt.Color(238, 238, 238));
        bargainingUnitIncludedTextArea.setColumns(20);
        bargainingUnitIncludedTextArea.setLineWrap(true);
        bargainingUnitIncludedTextArea.setRows(3);
        bargainingUnitIncludedTextArea.setWrapStyleWord(true);
        bargainingUnitIncludedTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        bargainingUnitIncludedTextArea.setEnabled(false);
        jScrollPane10.setViewportView(bargainingUnitIncludedTextArea);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane10)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane10))
        );

        jLabel3.setText("Bargaining Unit EXCLUDED:");

        bargainingUnitExcludedTextArea.setBackground(new java.awt.Color(238, 238, 238));
        bargainingUnitExcludedTextArea.setColumns(20);
        bargainingUnitExcludedTextArea.setLineWrap(true);
        bargainingUnitExcludedTextArea.setRows(3);
        bargainingUnitExcludedTextArea.setWrapStyleWord(true);
        bargainingUnitExcludedTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        bargainingUnitExcludedTextArea.setEnabled(false);
        jScrollPane6.setViewportView(bargainingUnitExcludedTextArea);

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane6)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane6, javax.swing.GroupLayout.DEFAULT_SIZE, 56, Short.MAX_VALUE))
        );

        jLabel6.setText("Professional EXCLUDED:");

        professionalExcludedTextArea.setBackground(new java.awt.Color(238, 238, 238));
        professionalExcludedTextArea.setColumns(20);
        professionalExcludedTextArea.setLineWrap(true);
        professionalExcludedTextArea.setRows(2);
        professionalExcludedTextArea.setWrapStyleWord(true);
        professionalExcludedTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        professionalExcludedTextArea.setEnabled(false);
        jScrollPane12.setViewportView(professionalExcludedTextArea);

        javax.swing.GroupLayout professionalExcludedPanelLayout = new javax.swing.GroupLayout(professionalExcludedPanel);
        professionalExcludedPanel.setLayout(professionalExcludedPanelLayout);
        professionalExcludedPanelLayout.setHorizontalGroup(
            professionalExcludedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(professionalExcludedPanelLayout.createSequentialGroup()
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane12)
        );
        professionalExcludedPanelLayout.setVerticalGroup(
            professionalExcludedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(professionalExcludedPanelLayout.createSequentialGroup()
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane12, javax.swing.GroupLayout.DEFAULT_SIZE, 49, Short.MAX_VALUE))
        );

        professionalIncludedLabel.setText("Professional INCLUDED:");

        professionalIncludedTextArea.setBackground(new java.awt.Color(238, 238, 238));
        professionalIncludedTextArea.setColumns(20);
        professionalIncludedTextArea.setLineWrap(true);
        professionalIncludedTextArea.setRows(2);
        professionalIncludedTextArea.setWrapStyleWord(true);
        professionalIncludedTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        professionalIncludedTextArea.setEnabled(false);
        jScrollPane11.setViewportView(professionalIncludedTextArea);

        javax.swing.GroupLayout professionalIncludedPanelLayout = new javax.swing.GroupLayout(professionalIncludedPanel);
        professionalIncludedPanel.setLayout(professionalIncludedPanelLayout);
        professionalIncludedPanelLayout.setHorizontalGroup(
            professionalIncludedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane11)
            .addGroup(professionalIncludedPanelLayout.createSequentialGroup()
                .addComponent(professionalIncludedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        professionalIncludedPanelLayout.setVerticalGroup(
            professionalIncludedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(professionalIncludedPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(professionalIncludedLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane11))
        );

        certificationUnitChangesLabel.setText("Certifications/Unit Changes:");

        toReflectLabel.setText("To Reflect:");

        typeFiledByLabel.setText("Type Filed By:");

        positionStatementFiledByLabel.setText("Position Statement Filed By:");

        EEONameChangeFromLabel.setText("EEO Name Change From:");

        ERNameChangeFromLabel.setText("ER Name Change From:");

        toReflectTextBox.setBackground(new java.awt.Color(238, 238, 238));
        toReflectTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        toReflectTextBox.setEnabled(false);

        typeFiledByComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "JTAC", "AC", "UC", "JTUC", "" }));
        typeFiledByComboBox.setSelectedIndex(4);
        typeFiledByComboBox.setEnabled(false);

        typeFiledViaLabel.setText("By:");

        typeFiledViaComboBox.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Letter", "Motion", "" }));
        typeFiledViaComboBox.setSelectedIndex(2);
        typeFiledViaComboBox.setEnabled(false);

        PositionStatementFiledByTextBox.setBackground(new java.awt.Color(238, 238, 238));
        PositionStatementFiledByTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        PositionStatementFiledByTextBox.setEnabled(false);
        PositionStatementFiledByTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                PositionStatementFiledByTextBoxActionPerformed(evt);
            }
        });

        EEONameChangeToLabel.setText("To:");

        EEONameChangeToTextBox.setBackground(new java.awt.Color(238, 238, 238));
        EEONameChangeToTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        EEONameChangeToTextBox.setEnabled(false);

        EEONameChangeFromTextBox.setBackground(new java.awt.Color(238, 238, 238));
        EEONameChangeFromTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        EEONameChangeFromTextBox.setEnabled(false);

        ERNameChangeFromTextBox.setBackground(new java.awt.Color(238, 238, 238));
        ERNameChangeFromTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ERNameChangeFromTextBox.setEnabled(false);

        ERNameChangeToLabel.setText("To:");

        ERNameChangeToTextBox.setBackground(new java.awt.Color(238, 238, 238));
        ERNameChangeToTextBox.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        ERNameChangeToTextBox.setEnabled(false);
        ERNameChangeToTextBox.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ERNameChangeToTextBoxActionPerformed(evt);
            }
        });

        optInIncludedTextArea.setBackground(new java.awt.Color(238, 238, 238));
        optInIncludedTextArea.setColumns(20);
        optInIncludedTextArea.setLineWrap(true);
        optInIncludedTextArea.setRows(1);
        optInIncludedTextArea.setWrapStyleWord(true);
        optInIncludedTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        optInIncludedTextArea.setEnabled(false);
        optInIncludedScrollPane.setViewportView(optInIncludedTextArea);

        jLabel7.setText("Non-Professional EXCLUDED:");

        nonProfessionalExcludedTextArea.setBackground(new java.awt.Color(238, 238, 238));
        nonProfessionalExcludedTextArea.setColumns(20);
        nonProfessionalExcludedTextArea.setLineWrap(true);
        nonProfessionalExcludedTextArea.setRows(2);
        nonProfessionalExcludedTextArea.setWrapStyleWord(true);
        nonProfessionalExcludedTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nonProfessionalExcludedTextArea.setEnabled(false);
        jScrollPane13.setViewportView(nonProfessionalExcludedTextArea);

        javax.swing.GroupLayout nonProfessionalExcludedPanelLayout = new javax.swing.GroupLayout(nonProfessionalExcludedPanel);
        nonProfessionalExcludedPanel.setLayout(nonProfessionalExcludedPanelLayout);
        nonProfessionalExcludedPanelLayout.setHorizontalGroup(
            nonProfessionalExcludedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane13, javax.swing.GroupLayout.Alignment.TRAILING)
            .addGroup(nonProfessionalExcludedPanelLayout.createSequentialGroup()
                .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 232, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        nonProfessionalExcludedPanelLayout.setVerticalGroup(
            nonProfessionalExcludedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nonProfessionalExcludedPanelLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane13, javax.swing.GroupLayout.DEFAULT_SIZE, 44, Short.MAX_VALUE))
        );

        professionalIncludedLabel1.setText("Non-Professional INCLUDED:");

        nonProfessionalIncludedTextArea.setBackground(new java.awt.Color(238, 238, 238));
        nonProfessionalIncludedTextArea.setColumns(20);
        nonProfessionalIncludedTextArea.setLineWrap(true);
        nonProfessionalIncludedTextArea.setRows(2);
        nonProfessionalIncludedTextArea.setWrapStyleWord(true);
        nonProfessionalIncludedTextArea.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        nonProfessionalIncludedTextArea.setEnabled(false);
        jScrollPane14.setViewportView(nonProfessionalIncludedTextArea);

        javax.swing.GroupLayout nonProfessionalIncludedPanelLayout = new javax.swing.GroupLayout(nonProfessionalIncludedPanel);
        nonProfessionalIncludedPanel.setLayout(nonProfessionalIncludedPanelLayout);
        nonProfessionalIncludedPanelLayout.setHorizontalGroup(
            nonProfessionalIncludedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nonProfessionalIncludedPanelLayout.createSequentialGroup()
                .addComponent(professionalIncludedLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jScrollPane14)
        );
        nonProfessionalIncludedPanelLayout.setVerticalGroup(
            nonProfessionalIncludedPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(nonProfessionalIncludedPanelLayout.createSequentialGroup()
                .addGap(0, 0, 0)
                .addComponent(professionalIncludedLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane14))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(professionalNonProfessionalCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 227, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(certificationUnitChangesLabel))
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(filedByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 128, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(optInIncludedScrollPane)
                            .addComponent(optInIncludedLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(typeFiledByLabel)
                                    .addComponent(toReflectLabel)
                                    .addComponent(positionStatementFiledByLabel)
                                    .addComponent(EEONameChangeFromLabel)
                                    .addComponent(ERNameChangeFromLabel))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(PositionStatementFiledByTextBox)
                                            .addGroup(layout.createSequentialGroup()
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                                    .addComponent(EEONameChangeFromTextBox, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
                                                    .addComponent(ERNameChangeFromTextBox))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(EEONameChangeToLabel)
                                                    .addComponent(ERNameChangeToLabel))
                                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(ERNameChangeToTextBox)
                                                    .addComponent(EEONameChangeToTextBox)))
                                            .addComponent(toReflectTextBox))
                                        .addGap(1, 1, 1))
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(typeFiledByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(typeFiledViaLabel)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(typeFiledViaComboBox, 0, 189, Short.MAX_VALUE))))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(professionalIncludedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(professionalExcludedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(nonProfessionalIncludedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(nonProfessionalExcludedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(10, 10, 10))))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(filedByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(1, 1, 1)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addComponent(optInIncludedLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 16, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(optInIncludedScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 39, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(professionalNonProfessionalCheckBox, javax.swing.GroupLayout.PREFERRED_SIZE, 17, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(professionalExcludedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(professionalIncludedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(nonProfessionalExcludedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(nonProfessionalIncludedPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(5, 5, 5)
                .addComponent(certificationUnitChangesLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 13, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(toReflectLabel)
                    .addComponent(toReflectTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(typeFiledByLabel, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(typeFiledByComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(typeFiledViaComboBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(typeFiledViaLabel, javax.swing.GroupLayout.DEFAULT_SIZE, 27, Short.MAX_VALUE)))
                .addGap(6, 6, 6)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(positionStatementFiledByLabel)
                    .addComponent(PositionStatementFiledByTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(EEONameChangeFromLabel)
                    .addComponent(EEONameChangeFromTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(EEONameChangeToLabel)
                    .addComponent(EEONameChangeToTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ERNameChangeFromLabel)
                    .addComponent(ERNameChangeFromTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(ERNameChangeToLabel)
                    .addComponent(ERNameChangeToTextBox, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(2, 2, 2))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void professionalNonProfessionalCheckBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_professionalNonProfessionalCheckBoxActionPerformed
//        displayProfessionalNonProfessionalInformation();
    }//GEN-LAST:event_professionalNonProfessionalCheckBoxActionPerformed

    private void ERNameChangeToTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ERNameChangeToTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ERNameChangeToTextBoxActionPerformed

    private void PositionStatementFiledByTextBoxActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_PositionStatementFiledByTextBoxActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_PositionStatementFiledByTextBoxActionPerformed

    private void professionalNonProfessionalCheckBoxStateChanged(javax.swing.event.ChangeEvent evt) {//GEN-FIRST:event_professionalNonProfessionalCheckBoxStateChanged
        displayProfessionalNonProfessionalInformation();
    }//GEN-LAST:event_professionalNonProfessionalCheckBoxStateChanged


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel EEONameChangeFromLabel;
    private javax.swing.JTextField EEONameChangeFromTextBox;
    private javax.swing.JLabel EEONameChangeToLabel;
    private javax.swing.JTextField EEONameChangeToTextBox;
    private javax.swing.JLabel ERNameChangeFromLabel;
    private javax.swing.JTextField ERNameChangeFromTextBox;
    private javax.swing.JLabel ERNameChangeToLabel;
    private javax.swing.JTextField ERNameChangeToTextBox;
    private javax.swing.JTextField PositionStatementFiledByTextBox;
    private javax.swing.JTextArea bargainingUnitExcludedTextArea;
    private javax.swing.JTextArea bargainingUnitIncludedTextArea;
    private javax.swing.JLabel certificationUnitChangesLabel;
    private javax.swing.JComboBox<String> filedByComboBox;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane10;
    private javax.swing.JScrollPane jScrollPane11;
    private javax.swing.JScrollPane jScrollPane12;
    private javax.swing.JScrollPane jScrollPane13;
    private javax.swing.JScrollPane jScrollPane14;
    private javax.swing.JScrollPane jScrollPane6;
    private javax.swing.JPanel nonProfessionalExcludedPanel;
    private javax.swing.JTextArea nonProfessionalExcludedTextArea;
    private javax.swing.JPanel nonProfessionalIncludedPanel;
    private javax.swing.JTextArea nonProfessionalIncludedTextArea;
    private javax.swing.JLabel optInIncludedLabel;
    private javax.swing.JScrollPane optInIncludedScrollPane;
    private javax.swing.JTextArea optInIncludedTextArea;
    private javax.swing.JLabel positionStatementFiledByLabel;
    private javax.swing.JPanel professionalExcludedPanel;
    private javax.swing.JTextArea professionalExcludedTextArea;
    private javax.swing.JLabel professionalIncludedLabel;
    private javax.swing.JLabel professionalIncludedLabel1;
    private javax.swing.JPanel professionalIncludedPanel;
    private javax.swing.JTextArea professionalIncludedTextArea;
    private javax.swing.JCheckBox professionalNonProfessionalCheckBox;
    private javax.swing.JLabel toReflectLabel;
    private javax.swing.JTextField toReflectTextBox;
    private javax.swing.JComboBox<String> typeFiledByComboBox;
    private javax.swing.JLabel typeFiledByLabel;
    private javax.swing.JComboBox<String> typeFiledViaComboBox;
    private javax.swing.JLabel typeFiledViaLabel;
    // End of variables declaration//GEN-END:variables
}
