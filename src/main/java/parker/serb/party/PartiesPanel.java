/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.party;

import java.awt.Font;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JTable;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.table.DefaultTableModel;
import parker.serb.Global;
import parker.serb.sql.CaseParty;
import parker.serb.sql.REPCaseSearchData;
import parker.serb.sql.ULPCaseSearchData;

//TODO: Implement duplicate party catch

/**
 *
 * @author parker
 */
public class PartiesPanel extends javax.swing.JPanel {

    List caseParties;
    String name = "";
    
    /**
     * Creates new form PartiesPanel
     */
    public PartiesPanel() {
        initComponents();
        addListeners();
        setTableColumnWidths();
    }
    
    private void setTableColumnWidths() {
        jTable1.getColumnModel().getColumn(0).setPreferredWidth(0);
        jTable1.getColumnModel().getColumn(0).setMinWidth(0);
        jTable1.getColumnModel().getColumn(0).setMaxWidth(0);
    }
    
    private void addListeners() {
        jTable1.getSelectionModel().addListSelectionListener((ListSelectionEvent e) -> {
            if(jTable1.getSelectionModel().isSelectionEmpty()) {
                Global.root.getjButton9().setEnabled(false);
            } else {
                Global.root.getjButton9().setEnabled(true);
            }
        });
        
        jTextField1.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                loadPartySearch(jTextField1.getText().trim());
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                loadPartySearch(jTextField1.getText().trim());
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                loadPartySearch(jTextField1.getText().trim());
            }
        });
        
        jTable1.addMouseListener(new MouseListener() {

            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2) {
                    if(!jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString().equals("")) {
                        new ViewUpdatePartyPanel((JFrame) Global.root.getParent(), true, jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString());
                        loadPartySearch(jTextField1.getText().trim());
                        updateHeader();
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
    
    private void updateHeader() {
        switch(Global.activeSection) {
            case "ULP":
                Global.root.getuLPHeaderPanel1().loadHeaderInformation();
                ULPCaseSearchData.updateCaseEntryFromParties(
                        Global.root.getuLPHeaderPanel1().getChargedPartyTextBox().getText().trim(),
                        Global.root.getuLPHeaderPanel1().getChargingPartyTextBox().getText().trim());
                break;
            case "REP":
                Global.root.getrEPHeaderPanel1().loadHeaderInformation();
                REPCaseSearchData.updateCaseEntryFromParties(
                        Global.root.getrEPHeaderPanel1().getEmployerTextBox().getText().trim(),
                        Global.root.getrEPHeaderPanel1().getEmployeeOrgTextBox().getText().trim(),
                        Global.root.getrEPHeaderPanel1().getIncumbentEEOTextBox().getText().trim());
                break;   
            case "MED":
                Global.root.getmEDHeaderPanel1().loadHeaderInformation();
//                REPCaseSearchData.updateCaseEntryFromParties(
//                        Global.root.getrEPHeaderPanel1().getEmployerTextBox().getText().trim(),
//                        Global.root.getrEPHeaderPanel1().getEmployeeOrgTextBox().getText().trim(),
//                        Global.root.getrEPHeaderPanel1().getIncumbentEEOTextBox().getText().trim());
                break; 
        }
    }
    
    private void loadPartySearch(String searchTerm) {
        List<CaseParty> chargingParties = new ArrayList<>();
        List<CaseParty> chargedParties = new ArrayList<>();
        List<CaseParty> employerParties = new ArrayList<>();
        List<CaseParty> employeeOrgParties = new ArrayList<>();
        List<CaseParty> rivalEmployeeOrgParties = new ArrayList<>();
        List<CaseParty> rivalEmployeeOrg2Parties = new ArrayList<>();
        List<CaseParty> rivalEmployeeOrg3Parties = new ArrayList<>();
        List<CaseParty> incumbentEmployeeParties = new ArrayList<>();
        List<CaseParty> intervenerParties = new ArrayList<>();
        List<CaseParty> petitionerParties = new ArrayList<>();
        List<CaseParty> conversionParties = new ArrayList<>();
        
        
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        for(Object caseParty: caseParties) {
            CaseParty partyInformation = (CaseParty) caseParty;
            
            if(partyInformation.firstName.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.middleInitial.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.lastName.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.companyName.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.caseRelation.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.address1.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.address2.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.address3.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.city.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.stateCode.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.zipcode.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.phone1.toLowerCase().contains(searchTerm.toLowerCase())
                    || partyInformation.emailAddress.toLowerCase().contains(searchTerm.toLowerCase())
                    ) 
            {
                
                if(partyInformation.caseRelation.contains("Charging")) {
                    chargingParties.add(partyInformation);
                }

                if(partyInformation.caseRelation.contains("Charged")) {
                    chargedParties.add(partyInformation);
                }  
                
                if(partyInformation.caseRelation.startsWith("Employer")) {
                    employerParties.add(partyInformation);
                } 
                
                if(partyInformation.caseRelation.startsWith("Employee Organization")) {
                    employeeOrgParties.add(partyInformation);
                } 
                
                if(partyInformation.caseRelation.startsWith("Rival Employee Organization")) {
                    rivalEmployeeOrgParties.add(partyInformation);
                } 
                
                if(partyInformation.caseRelation.startsWith("Incumbent Employee Organization")) {
                    incumbentEmployeeParties.add(partyInformation);
                } 
                
                if(partyInformation.caseRelation.startsWith("Intervener")) {
                    intervenerParties.add(partyInformation);
                } 
                
                if(partyInformation.caseRelation.contains("Petitioner")) {
                    petitionerParties.add(partyInformation);
                } 
                
                if(partyInformation.caseRelation.contains("Conversion School")) {
                    conversionParties.add(partyInformation);
                } 
                
                if(partyInformation.caseRelation.contains("Rival Employee Organization 2")) {
                    rivalEmployeeOrg2Parties.add(partyInformation);
                } 
                
                if(partyInformation.caseRelation.contains("Rival Employee Organization 3")) {
                    rivalEmployeeOrg3Parties.add(partyInformation);
                } 
            }  
        }
        
        if(chargingParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Charging Party</b></html>", "", "", "", ""});
            for(int i = 0; i < chargingParties.size(); i++) {
                CaseParty charginingPartyInformation = chargingParties.get(i);
                
                if(charginingPartyInformation.firstName.equals("") 
                        && charginingPartyInformation.lastName.equals("")) {
                    name = charginingPartyInformation.companyName;
                } else {
                    name = (charginingPartyInformation.prefix.equals("") ? "" : (charginingPartyInformation.prefix + " "))
                        + (charginingPartyInformation.firstName.equals("") ? "" : (charginingPartyInformation.firstName + " "))
                        + (charginingPartyInformation.middleInitial.equals("") ? "" : (charginingPartyInformation.middleInitial + ". "))
                        + (charginingPartyInformation.lastName.equals("") ? "" : (charginingPartyInformation.lastName))
                        + (charginingPartyInformation.suffix.equals("") ? "" : (" " + charginingPartyInformation.suffix))
                        + (charginingPartyInformation.nameTitle.equals("") ? "" : (", " + charginingPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {charginingPartyInformation.id,
                    name,
                    charginingPartyInformation.caseRelation, 
                    charginingPartyInformation.address1
                    + (charginingPartyInformation.address2.equals("") ? "" : (", " + charginingPartyInformation.address2))
                    + (charginingPartyInformation.address3.equals("") ? "" : (", " + charginingPartyInformation.address3))
                    + (charginingPartyInformation.city.equals("") ? "" : (", " + charginingPartyInformation.city))
                    + (charginingPartyInformation.stateCode.equals("") ? "" : (", " + charginingPartyInformation.stateCode))
                    + (charginingPartyInformation.zipcode.equals("") ? "" : (", " + charginingPartyInformation.zipcode)),
                    charginingPartyInformation.phone1, charginingPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }

        if(chargedParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Charged Party</b></html>", "", "", "", ""});
            for(int i = 0; i < chargedParties.size(); i++) {
                CaseParty chargedPartyInformation = chargedParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(employerParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Employer</b></html>", "", "", "", ""});
            for(int i = 0; i < employerParties.size(); i++) {
                CaseParty employerInformation = employerParties.get(i);
                
                if(employerInformation.firstName.equals("") 
                        && employerInformation.lastName.equals("")) {
                    name = employerInformation.companyName;
                } else {
                    name = (employerInformation.prefix.equals("") ? "" : (employerInformation.prefix + " "))
                        + (employerInformation.firstName.equals("") ? "" : (employerInformation.firstName + " "))
                        + (employerInformation.middleInitial.equals("") ? "" : (employerInformation.middleInitial + ". "))
                        + (employerInformation.lastName.equals("") ? "" : (employerInformation.lastName))
                        + (employerInformation.suffix.equals("") ? "" : (" " + employerInformation.suffix))
                        + (employerInformation.nameTitle.equals("") ? "" : (", " + employerInformation.nameTitle));
                }
                
                model.addRow(new Object[] {employerInformation.id,
                    name,
                    employerInformation.caseRelation, 
                    employerInformation.address1
                    + (employerInformation.address2.equals("") ? "" : (", " + employerInformation.address2))
                    + (employerInformation.address3.equals("") ? "" : (", " + employerInformation.address3))
                    + (employerInformation.city.equals("") ? "" : (", " + employerInformation.city))
                    + (employerInformation.stateCode.equals("") ? "" : (", " + employerInformation.stateCode))
                    + (employerInformation.zipcode.equals("") ? "" : (", " + employerInformation.zipcode)),
                    employerInformation.phone1, employerInformation.emailAddress});
                }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(employeeOrgParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Employee Organization</b></html>", "", "", "", ""});
            for(int i = 0; i < employeeOrgParties.size(); i++) {
                CaseParty chargedPartyInformation = employeeOrgParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(rivalEmployeeOrgParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Rival Employee Organization</b></html>", "", "", "", ""});
            for(int i = 0; i < rivalEmployeeOrgParties.size(); i++) {
                CaseParty employerInformation = rivalEmployeeOrgParties.get(i);
                
                if(employerInformation.firstName.equals("") 
                        && employerInformation.lastName.equals("")) {
                    name = employerInformation.companyName;
                } else {
                    name = (employerInformation.prefix.equals("") ? "" : (employerInformation.prefix + " "))
                        + (employerInformation.firstName.equals("") ? "" : (employerInformation.firstName + " "))
                        + (employerInformation.middleInitial.equals("") ? "" : (employerInformation.middleInitial + ". "))
                        + (employerInformation.lastName.equals("") ? "" : (employerInformation.lastName))
                        + (employerInformation.suffix.equals("") ? "" : (" " + employerInformation.suffix))
                        + (employerInformation.nameTitle.equals("") ? "" : (", " + employerInformation.nameTitle));
                }
                
                model.addRow(new Object[] {employerInformation.id,
                    name,
                    employerInformation.caseRelation, 
                    employerInformation.address1
                    + (employerInformation.address2.equals("") ? "" : (", " + employerInformation.address2))
                    + (employerInformation.address3.equals("") ? "" : (", " + employerInformation.address3))
                    + (employerInformation.city.equals("") ? "" : (", " + employerInformation.city))
                    + (employerInformation.stateCode.equals("") ? "" : (", " + employerInformation.stateCode))
                    + (employerInformation.zipcode.equals("") ? "" : (", " + employerInformation.zipcode)),
                    employerInformation.phone1, employerInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(rivalEmployeeOrg2Parties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Rival Employee Organization 2</b></html>", "", "", "", ""});
            for(int i = 0; i < rivalEmployeeOrg2Parties.size(); i++) {
                CaseParty chargedPartyInformation = rivalEmployeeOrg2Parties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(rivalEmployeeOrg3Parties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Rival Employee Organization 3</b></html>", "", "", "", ""});
            for(int i = 0; i < rivalEmployeeOrg3Parties.size(); i++) {
                CaseParty chargedPartyInformation = rivalEmployeeOrg3Parties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(incumbentEmployeeParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Incumbent Employee Organization</b></html>", "", "", "", ""});
            for(int i = 0; i < incumbentEmployeeParties.size(); i++) {
                CaseParty chargedPartyInformation = incumbentEmployeeParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(intervenerParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Intervener</b></html>", "", "", "", ""});
            for(int i = 0; i < intervenerParties.size(); i++) {
                CaseParty chargedPartyInformation = intervenerParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(petitionerParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Petitioner</b></html>", "", "", "", ""});
            for(int i = 0; i < petitionerParties.size(); i++) {
                CaseParty chargedPartyInformation = petitionerParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(conversionParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Conversion School</b></html>", "", "", "", ""});
            for(int i = 0; i < conversionParties.size(); i++) {
                CaseParty chargedPartyInformation = conversionParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        //remove blank row
        if(model.getRowCount() > 0) {
            if(model.getValueAt(model.getRowCount()-1, 0).toString().equals("")) {
                model.removeRow(model.getRowCount()-1);
            }
        } 
    }
    
    public void clearAll() {
        missingParties.setText("");
        jTextField1.setText("");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
    }
    
    public void loadParties() {
        List<CaseParty> chargingParties = new ArrayList<>();
        List<CaseParty> chargedParties = new ArrayList<>();
        List<CaseParty> employerParties = new ArrayList<>();
        List<CaseParty> employeeOrgParties = new ArrayList<>();
        List<CaseParty> rivalEmployeeOrgParties = new ArrayList<>();
        List<CaseParty> rivalEmployeeOrg2Parties = new ArrayList<>();
        List<CaseParty> rivalEmployeeOrg3Parties = new ArrayList<>();
        List<CaseParty> incumbentEmployeeParties = new ArrayList<>();
        List<CaseParty> intervenerParties = new ArrayList<>();
        List<CaseParty> petitionerParties = new ArrayList<>();
        List<CaseParty> conversionParties = new ArrayList<>();
        
        jTextField1.setText("");
        DefaultTableModel model = (DefaultTableModel) jTable1.getModel();
        model.setRowCount(0);
        
        caseParties = CaseParty.loadPartiesByCase();
        
        for(Object caseParty: caseParties) {
            CaseParty partyInformation = (CaseParty) caseParty;
            
            if(partyInformation.caseRelation.contains("Charging")) {
                chargingParties.add(partyInformation);
            }
            
            if(partyInformation.caseRelation.contains("Charged")) {
                chargedParties.add(partyInformation);
            }
            
            if(partyInformation.caseRelation.startsWith("Employer")) {
                employerParties.add(partyInformation);
            } 

            if(partyInformation.caseRelation.startsWith("Employee Organization")) {
                employeeOrgParties.add(partyInformation);
            } 

            if(partyInformation.caseRelation.startsWith("Rival Employee Organization")) {
                rivalEmployeeOrgParties.add(partyInformation);
            } 

            if(partyInformation.caseRelation.startsWith("Incumbent Employee Organization")) {
                incumbentEmployeeParties.add(partyInformation);
            } 

            if(partyInformation.caseRelation.contains("Intervener")) {
                intervenerParties.add(partyInformation);
            } 

            if(partyInformation.caseRelation.contains("Petitioner")) {
                petitionerParties.add(partyInformation);
            } 

            if(partyInformation.caseRelation.contains("Conversion School")) {
                conversionParties.add(partyInformation);
            } 

            if(partyInformation.caseRelation.contains("Rival Employee Organization 2")) {
                rivalEmployeeOrg2Parties.add(partyInformation);
            } 

            if(partyInformation.caseRelation.contains("Rival Employee Organization 3")) {
                rivalEmployeeOrg3Parties.add(partyInformation);
            }
        }
        
        if(chargingParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Charging Party</b></html>", "", "", "", ""});
            for(int i = 0; i < chargingParties.size(); i++) {
                CaseParty charginingPartyInformation = chargingParties.get(i);
                
                if(charginingPartyInformation.firstName.equals("") 
                        && charginingPartyInformation.lastName.equals("")) {
                    name = charginingPartyInformation.companyName;
                } else {
                    name = (charginingPartyInformation.prefix.equals("") ? "" : (charginingPartyInformation.prefix + " "))
                        + (charginingPartyInformation.firstName.equals("") ? "" : (charginingPartyInformation.firstName + " "))
                        + (charginingPartyInformation.middleInitial.equals("") ? "" : (charginingPartyInformation.middleInitial + ". "))
                        + (charginingPartyInformation.lastName.equals("") ? "" : (charginingPartyInformation.lastName))
                        + (charginingPartyInformation.suffix.equals("") ? "" : (" " + charginingPartyInformation.suffix))
                        + (charginingPartyInformation.nameTitle.equals("") ? "" : (", " + charginingPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {charginingPartyInformation.id,
                    name,
                    charginingPartyInformation.caseRelation, 
                    charginingPartyInformation.address1
                    + (charginingPartyInformation.address2.equals("") ? "" : (", " + charginingPartyInformation.address2))
                    + (charginingPartyInformation.address3.equals("") ? "" : (", " + charginingPartyInformation.address3))
                    + (charginingPartyInformation.city.equals("") ? "" : (", " + charginingPartyInformation.city))
                    + (charginingPartyInformation.stateCode.equals("") ? "" : (", " + charginingPartyInformation.stateCode))
                    + (charginingPartyInformation.zipcode.equals("") ? "" : (", " + charginingPartyInformation.zipcode)),
                    charginingPartyInformation.phone1, charginingPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }

        if(chargedParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Charged Party</b></html>", "", "", "", ""});
            for(int i = 0; i < chargedParties.size(); i++) {
                CaseParty chargedPartyInformation = chargedParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(employerParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Employer</b></html>", "", "", "", ""});
            for(int i = 0; i < employerParties.size(); i++) {
                CaseParty employerInformation = employerParties.get(i);
                
                if(employerInformation.firstName.equals("") 
                        && employerInformation.lastName.equals("")) {
                    name = employerInformation.companyName;
                } else {
                    name = (employerInformation.prefix.equals("") ? "" : (employerInformation.prefix + " "))
                        + (employerInformation.firstName.equals("") ? "" : (employerInformation.firstName + " "))
                        + (employerInformation.middleInitial.equals("") ? "" : (employerInformation.middleInitial + ". "))
                        + (employerInformation.lastName.equals("") ? "" : (employerInformation.lastName))
                        + (employerInformation.suffix.equals("") ? "" : (" " + employerInformation.suffix))
                        + (employerInformation.nameTitle.equals("") ? "" : (", " + employerInformation.nameTitle));
                }
                
                model.addRow(new Object[] {employerInformation.id,
                    name,
                    employerInformation.caseRelation, 
                    employerInformation.address1
                    + (employerInformation.address2.equals("") ? "" : (", " + employerInformation.address2))
                    + (employerInformation.address3.equals("") ? "" : (", " + employerInformation.address3))
                    + (employerInformation.city.equals("") ? "" : (", " + employerInformation.city))
                    + (employerInformation.stateCode.equals("") ? "" : (", " + employerInformation.stateCode))
                    + (employerInformation.zipcode.equals("") ? "" : (", " + employerInformation.zipcode)),
                    employerInformation.phone1, employerInformation.emailAddress});
                }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(employeeOrgParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Employee Organization</b></html>", "", "", "", ""});
            for(int i = 0; i < employeeOrgParties.size(); i++) {
                CaseParty chargedPartyInformation = employeeOrgParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(rivalEmployeeOrgParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Rival Employee Organization</b></html>", "", "", "", ""});
            for(int i = 0; i < rivalEmployeeOrgParties.size(); i++) {
                CaseParty employerInformation = rivalEmployeeOrgParties.get(i);
                
                if(employerInformation.firstName.equals("") 
                        && employerInformation.lastName.equals("")) {
                    name = employerInformation.companyName;
                } else {
                    name = (employerInformation.prefix.equals("") ? "" : (employerInformation.prefix + " "))
                        + (employerInformation.firstName.equals("") ? "" : (employerInformation.firstName + " "))
                        + (employerInformation.middleInitial.equals("") ? "" : (employerInformation.middleInitial + ". "))
                        + (employerInformation.lastName.equals("") ? "" : (employerInformation.lastName))
                        + (employerInformation.suffix.equals("") ? "" : (" " + employerInformation.suffix))
                        + (employerInformation.nameTitle.equals("") ? "" : (", " + employerInformation.nameTitle));
                }
                
                model.addRow(new Object[] {employerInformation.id,
                    name,
                    employerInformation.caseRelation, 
                    employerInformation.address1
                    + (employerInformation.address2.equals("") ? "" : (", " + employerInformation.address2))
                    + (employerInformation.address3.equals("") ? "" : (", " + employerInformation.address3))
                    + (employerInformation.city.equals("") ? "" : (", " + employerInformation.city))
                    + (employerInformation.stateCode.equals("") ? "" : (", " + employerInformation.stateCode))
                    + (employerInformation.zipcode.equals("") ? "" : (", " + employerInformation.zipcode)),
                    employerInformation.phone1, employerInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(rivalEmployeeOrg2Parties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Rival Employee Organization 2</b></html>", "", "", "", ""});
            for(int i = 0; i < rivalEmployeeOrg2Parties.size(); i++) {
                CaseParty chargedPartyInformation = rivalEmployeeOrg2Parties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(rivalEmployeeOrg3Parties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Rival Employee Organization 3</b></html>", "", "", "", ""});
            for(int i = 0; i < rivalEmployeeOrg3Parties.size(); i++) {
                CaseParty chargedPartyInformation = rivalEmployeeOrg3Parties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(incumbentEmployeeParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Incumbent Employee Organization</b></html>", "", "", "", ""});
            for(int i = 0; i < incumbentEmployeeParties.size(); i++) {
                CaseParty chargedPartyInformation = incumbentEmployeeParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(intervenerParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Intervener</b></html>", "", "", "", ""});
            for(int i = 0; i < intervenerParties.size(); i++) {
                CaseParty chargedPartyInformation = intervenerParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(petitionerParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Petitioner</b></html>", "", "", "", ""});
            for(int i = 0; i < petitionerParties.size(); i++) {
                CaseParty chargedPartyInformation = petitionerParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        if(conversionParties.size() > 0) {
            model.addRow(new Object[] {"", "<html><b>Conversion School</b></html>", "", "", "", ""});
            for(int i = 0; i < conversionParties.size(); i++) {
                CaseParty chargedPartyInformation = conversionParties.get(i);
                
                if(chargedPartyInformation.firstName.equals("") 
                        && chargedPartyInformation.lastName.equals("")) {
                    name = chargedPartyInformation.companyName;
                } else {
                    name = (chargedPartyInformation.prefix.equals("") ? "" : (chargedPartyInformation.prefix + " "))
                        + (chargedPartyInformation.firstName.equals("") ? "" : (chargedPartyInformation.firstName + " "))
                        + (chargedPartyInformation.middleInitial.equals("") ? "" : (chargedPartyInformation.middleInitial + ". "))
                        + (chargedPartyInformation.lastName.equals("") ? "" : (chargedPartyInformation.lastName))
                        + (chargedPartyInformation.suffix.equals("") ? "" : (" " + chargedPartyInformation.suffix))
                        + (chargedPartyInformation.nameTitle.equals("") ? "" : (", " + chargedPartyInformation.nameTitle));
                }
                
                model.addRow(new Object[] {chargedPartyInformation.id,
                    name,
                    chargedPartyInformation.caseRelation, 
                    chargedPartyInformation.address1
                    + (chargedPartyInformation.address2.equals("") ? "" : (", " + chargedPartyInformation.address2))
                    + (chargedPartyInformation.address3.equals("") ? "" : (", " + chargedPartyInformation.address3))
                    + (chargedPartyInformation.city.equals("") ? "" : (", " + chargedPartyInformation.city))
                    + (chargedPartyInformation.stateCode.equals("") ? "" : (", " + chargedPartyInformation.stateCode))
                    + (chargedPartyInformation.zipcode.equals("") ? "" : (", " + chargedPartyInformation.zipcode)),
                    chargedPartyInformation.phone1, chargedPartyInformation.emailAddress});
            }
            model.addRow(new Object[] {"", "", "", "", "", ""});
        }
        
        //remove blank row
        if(model.getRowCount() > 0) {
            if(model.getValueAt(model.getRowCount()-1, 0).toString().equals("")) {
                model.removeRow(model.getRowCount()-1);
            }
        }
        validateParties();
    }
    
    private void validateParties() {
        switch(Global.activeSection) {
            case "ULP":
                validateULPParties();
                break;
            case "REP":
                validateREPParties();
                break;
            case "MED":
                validateMEDParties();
                break;
        }
    }
    
    private  void validateULPParties() {
        boolean chargingParty = false;
        boolean chargingRepParty = false;
        boolean chargedParty = false;
        boolean chargedRepParty = false;
        
        //need value from type (1)
        for(int i = 0; i < jTable1.getRowCount(); i++) {
            if(jTable1.getValueAt(i, 2).toString().equals("Charging Party")) {
                chargingParty = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Charging Party REP")) {
                chargingRepParty = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Charged Party")) {
                chargedParty = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Charged Party REP")) {
                chargedRepParty = true;
            }
        }
        
        if(chargedParty && chargedRepParty && chargingParty && chargingRepParty) {
            missingParties.setText("");
        } else {
            String missingPartiesText = "Missing Required Parties:";
            missingPartiesText += (chargingParty ? "" : " Charging Party ");
            missingPartiesText += (chargingRepParty ? "" : " Charging Party REP ");
            missingPartiesText += (chargedParty ? "" : " Charged Party ");
            missingPartiesText += (chargedRepParty ? "" : " Charged Party REP ");
                    
            missingParties.setText(missingPartiesText.replace("  ", ", ").trim());
        }
    }
    
    private  void validateMEDParties() {
        boolean employerParty = false;
        boolean employerRepParty = false;
        boolean employeeOrgParty = false;
        boolean employeeOrgRepParty = false;
        
        //need value from type (1)
        for(int i = 0; i < jTable1.getRowCount(); i++) {
            if(jTable1.getValueAt(i, 2).toString().equals("Employer")) {
                employerParty = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Employer REP")) {
                employerRepParty = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Employee Organization")) {
                employeeOrgParty = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Employee Organization REP")) {
                employeeOrgRepParty = true;
            }
        }
        
        if(employerParty && employerRepParty && employeeOrgParty && employeeOrgRepParty) {
            missingParties.setText("");
        } else {
            String missingPartiesText = "Missing Required Parties:";
            missingPartiesText += (employerParty ? "" : " Employer ");
            missingPartiesText += (employerRepParty ? "" : " Employer REP ");
            missingPartiesText += (employeeOrgParty ? "" : " Employee Organization ");
            missingPartiesText += (employeeOrgRepParty ? "" : " Employee Organization REP ");
                    
            missingParties.setText(missingPartiesText.replace("  ", ", ").trim());
        }
    }
    
    private  void validateREPParties() {
        boolean employer = false;
        boolean emmployerREP = false;
        boolean employeeOrg = false;
        boolean employeeOrgREP = false;
        boolean rivalEmployeeOrg = false;
        boolean rivalEmployeeOrgREP = false;
        boolean incumbentEmployeeOrg = false;
        boolean incumbentEmployeeOrgREP = false;
        boolean intervener = false;
        boolean intervenerREP = false;
        boolean petitioner = false;
        boolean petitionerREP = false;
        boolean conversionSchool = false;
        boolean conversionSchoolREP = false;
        
        //need value from type (1)
        for(int i = 0; i < jTable1.getRowCount(); i++) {
            if(jTable1.getValueAt(i, 2).toString().equals("Employer")) {
                employer = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Employer REP")) {
                emmployerREP = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Employee Organization")) {
                employeeOrg = true;
            } 
            if(jTable1.getValueAt(i, 2).toString().equals("Employee Organization REP")) {
                employeeOrgREP = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Rival Employee Organization")) {
                rivalEmployeeOrg = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Rival Employee Organization REP")) {
                rivalEmployeeOrgREP = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Incumbent Employee Organization")) {
                incumbentEmployeeOrg = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Incumbent Employee Organization REP")) {
                incumbentEmployeeOrgREP = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Intervener")) {
                intervener = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Intervener REP")) {
                intervenerREP = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Petitioner")) {
                petitioner = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Petitioner REP")) {
                petitionerREP = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Conversion School")) {
                conversionSchool = true;
            }
            if(jTable1.getValueAt(i, 2).toString().equals("Conversion School REP")) {
                conversionSchoolREP = true;
            }
            
        }
        
        if(employer && emmployerREP
                && ((employeeOrg && employeeOrgREP)
                || (rivalEmployeeOrg && rivalEmployeeOrgREP)
                || (incumbentEmployeeOrg && incumbentEmployeeOrgREP)
                || (intervener && intervenerREP)
                || (petitioner && petitionerREP)
                || (conversionSchool && conversionSchoolREP))) {
            missingParties.setText("");
        } else {
            String missingPartiesText = "Missing Required Parties:";
            missingPartiesText += (employer ? "" : " Employer ");
            missingPartiesText += (emmployerREP ? "" : " Employer REP ");
            
            if(!((employeeOrg && employeeOrgREP)
                || (rivalEmployeeOrg && rivalEmployeeOrgREP)
                || (incumbentEmployeeOrg && incumbentEmployeeOrgREP)
                || (intervener && intervenerREP)
                || (petitioner && petitionerREP)
                || (conversionSchool && conversionSchoolREP)))
            {
                //Employee Org and Employee Org REP
                if(!employeeOrg && !employeeOrgREP) {
                    missingPartiesText += " Employee Org/REP ";
                } else if(employeeOrg && !employeeOrgREP) {
                    missingPartiesText += " Employee Org REP ";
                } else if(!employeeOrg && employeeOrgREP) {
                    missingPartiesText += " Employee Org ";
                }

                //Rival Employee Org and Rival Employee Org REP
                if(!rivalEmployeeOrg && !rivalEmployeeOrgREP) {
                    missingPartiesText += " Rival Employee Org/REP ";
                } else if(rivalEmployeeOrg && !rivalEmployeeOrgREP) {
                    missingPartiesText += " Rival Employee Org REP ";
                } else if(!rivalEmployeeOrg && rivalEmployeeOrgREP) {
                    missingPartiesText += " Rival Employee Org ";
                }

                //Incumbent Employee Org and Incumbent Employee Org REP
                if(!incumbentEmployeeOrg && !incumbentEmployeeOrgREP) {
                    missingPartiesText += " Incumbent Employee Org/REP ";
                } else if(incumbentEmployeeOrg && !incumbentEmployeeOrgREP) {
                    missingPartiesText += " Incumbent Employee Org REP ";
                } else if(!incumbentEmployeeOrg && incumbentEmployeeOrgREP) {
                    missingPartiesText += " Incumbent Employee Org ";
                }

                //Intervener and Intervener REP
                if(!intervener && !intervenerREP) {
                    missingPartiesText += " Intervener/REP ";
                } else if(intervener && !intervenerREP) {
                    missingPartiesText += " Intervener REP ";
                } else if(!intervener && intervenerREP) {
                    missingPartiesText += " Intervener ";
                }

                //Petitioner and Petitioner REP
                if(!petitioner && !petitionerREP) {
                    missingPartiesText += " Petitioner/REP ";
                } else if(petitioner && !petitionerREP) {
                    missingPartiesText += " Petitioner REP ";
                } else if(!petitioner && petitionerREP) {
                    missingPartiesText += " Petitioner ";
                }

                //Conversion School and Conversion School REP
                if(!conversionSchool && !conversionSchoolREP) {
                    missingPartiesText += " Conversion School/REP ";
                } else if(conversionSchool && !conversionSchoolREP) {
                    missingPartiesText += " Conversion School REP ";
                } else if(!conversionSchool && conversionSchoolREP) {
                    missingPartiesText += " Conversion School ";
                }
            }
           
            if(missingPartiesText.length() < 125) {
                missingParties.setFont(new Font("Lucida Grande", Font.PLAIN, 13));
            } else if(missingPartiesText.length() < 150) {
                missingParties.setFont(new Font("Lucida Grande", Font.PLAIN, 12));
            } else {
                missingParties.setFont(new Font("Lucida Grande", Font.PLAIN, 11));
            }
            
            missingParties.setText(missingPartiesText.replace("  ", ", ").trim());
        }
    }
    
    
    public void removeParty() {
        new DeletePartyDialog((JFrame) Global.root.getParent(),
                true,
                jTable1.getValueAt(jTable1.getSelectedRow(), 0).toString(),
                jTable1.getValueAt(jTable1.getSelectedRow(), 1).toString(),
                jTable1.getValueAt(jTable1.getSelectedRow(), 2).toString());
        
        loadParties();
    }

    public JTable getjTable1() {
        return jTable1;
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
        jTextField1 = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jButton1 = new javax.swing.JButton();
        missingParties = new javax.swing.JLabel();

        jLabel1.setText("Search:");

        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Name", "Type", "Address", "Phone Number", "Email"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jScrollPane1.setViewportView(jTable1);
        if (jTable1.getColumnModel().getColumnCount() > 0) {
            jTable1.getColumnModel().getColumn(0).setResizable(false);
        }

        jButton1.setText("Clear");
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });

        missingParties.setForeground(new java.awt.Color(255, 0, 0));
        missingParties.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        missingParties.setText("jLabel2");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 400, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(missingParties, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jTextField1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton1)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(missingParties)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 257, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        jTextField1.setText("");
    }//GEN-LAST:event_jButton1ActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jButton1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel missingParties;
    // End of variables declaration//GEN-END:variables
}
