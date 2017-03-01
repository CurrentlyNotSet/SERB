/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import parker.serb.Global;
import parker.serb.sql.AdministrationInformation;
import parker.serb.sql.CaseParty;
import parker.serb.sql.Party;

/**
 *
 * @author User
 */
public class StringUtilities {

    public static String buildFullName(String first, String middle, String last) {
        String fullName = "";
        if (first != null) {
            fullName = fullName.trim() + first.trim();
        }
        if (middle != null) {
            fullName = fullName.trim() + " " + (middle.trim().length() == 1 ? middle.trim() + "." : middle.trim());
        }
        if (last != null) {
            fullName = fullName.trim() + " "  + last.trim();
        }
        return fullName.trim();
    }

    public static String buildCasePartyNameNoPreFix(CaseParty item) {
        String fullName = "";
        if (item.firstName != null) {
            fullName = fullName.trim() + " " + (item.firstName.equals("") ? "" : item.firstName.trim());
        }
        if (item.middleInitial != null) {
            fullName = fullName.trim() + " " + (item.middleInitial.equals("") ? "" : (item.middleInitial.trim().length() == 1 ? item.middleInitial.trim() + "." : item.middleInitial.trim()));
        }
        if (item.lastName != null) {
            fullName = fullName.trim() + " " + item.lastName.trim();
        }
        if (item.suffix != null) {
            fullName = fullName.trim() + ", " + item.suffix.trim();
        }
        if (item.nameTitle != null) {
            fullName = fullName.trim() + (item.nameTitle.equals("") ? "" : ", " + item.nameTitle.trim());
        }
        if (item.jobTitle != null) {
            fullName = fullName.trim() + (item.jobTitle.equals("") ? "" : ", " + item.jobTitle.trim());
        }
        if (!"".equals(fullName.trim()) && item.companyName != null){
            fullName = fullName.trim() + System.lineSeparator() +  item.companyName.trim();
        } else {
            fullName = fullName.trim() + " " +  item.companyName.trim();
        }
        
        return fullName.trim();
    }

    public static String buildCasePartyName(CaseParty item) {
        String fullName = "";
        if (item.prefix != null) {
            fullName = fullName.trim() + (item.prefix.equals("") ? "" : item.prefix.trim());
        }
        if (item.firstName != null) {
            fullName = fullName.trim() + " " + (item.firstName.equals("") ? "" : item.firstName.trim());
        }
        if (item.middleInitial != null) {
            fullName = fullName.trim() + " " + (item.middleInitial.equals("") ? "" : (item.middleInitial.trim().length() == 1 ? item.middleInitial.trim() + "." : item.middleInitial.trim()));
        }
        if (item.lastName != null) {
            fullName = fullName.trim() + " " + item.lastName.trim();
        }
        if (item.suffix != null) {
            fullName = fullName.trim() + " " + item.suffix.trim();
        }
        if ("".equals(fullName.trim()) && item.companyName != null){
            fullName = fullName.trim() + " " +  item.companyName.trim();
        }
        return fullName.trim();
    }

//    public static String buildAddressBlockWithPhoneAndEmail(CaseParty item) {
//        String addressBlock = "";
//
//        addressBlock += buildCasePartyNameNoPreFix(item);
//        if (item.address1 != null) {
//            addressBlock += item.address1.trim().equals("") ? "" : "\n" + item.address1;
//        }
//        if (item.address2 != null) {
//            addressBlock += item.address2.trim().equals("") ? "" : "\n" + item.address2;
//        }
//        if (item.address3 != null) {
//            addressBlock += item.address3.trim().equals("") ? "" : "\n" + item.address3;
//        }
//        addressBlock += "\n" + (item.city == null ? "" : item.city)
//                + ", " + (item.stateCode == null ? "" : item.stateCode)
//                + " " + (item.zipcode == null ? "" : item.zipcode);
//        if (item.phone1 != null){
//            addressBlock += item.phone1.trim().equals("") ? "" : "\n" + item.phone1;
//        }
//        if (item.phone2 != null){
//            addressBlock += item.phone2.trim().equals("") ? "" : "\n" + item.phone2;
//        }
//        if (item.emailAddress != null){
//            addressBlock += item.emailAddress.trim().equals("") ? "" : "\n" + item.emailAddress;
//        }
//        return addressBlock.trim();
//    }

    public static String buildAddressBlockWithLineBreaks(CaseParty item) {
        String addressBlock = "";

        addressBlock += buildCasePartyNameNoPreFix(item);
        if (item.address1 != null) {
            if (!item.address1.equals("")) {
                addressBlock += System.lineSeparator() + item.address1;
            }
        }
        if (item.address2 != null) {
            if (!item.address2.equals("")) {
                addressBlock += System.lineSeparator() + item.address2;
            }
        }
        if (item.address3 != null) {
            if (!item.address3.equals("")) {
                addressBlock += System.lineSeparator() + item.address3;
            }
        }
        addressBlock += System.lineSeparator() + (item.city == null ? "" : item.city)
                + ", " + (item.stateCode == null ? "" : item.stateCode)
                + " " + (item.zipcode == null ? "" : item.zipcode);

        return addressBlock.trim();
    }

    public static String buildCasePartyAddressBlock(CaseParty item){
        return (item.address1.equals("") ? "" : (item.address1))
                + (item.address2.equals("") ? "" : (", " + item.address2))
                + (item.address3.equals("") ? "" : (", " + item.address3))
                + (item.city.equals("") ? "" : (", " + item.city))
                + (item.stateCode.equals("") ? "" : (", " + item.stateCode))
                + (item.zipcode.equals("") ? "" : (" " + item.zipcode));
    }

    public static String buildAddressBlock(Party item){
        return (item.address1.equals("") ? "" : (item.address1))
                + (item.address2.equals("") ? "" : (", " + item.address2))
                + (item.address3.equals("") ? "" : (", " + item.address3))
                + (item.city.equals("") ? "" : (", " + item.city))
                + (item.stateCode.equals("") ? "" : (", " + item.stateCode))
                + (item.zipCode.equals("") ? "" : (" " + item.zipCode));
    }

    public static String buildFullNameWithTitles(Party item) {
        return (item.prefix.equals("") ? "" : (item.prefix + " "))
                + (item.firstName.equals("") ? "" : (item.firstName + " "))
                + (item.middleInitial.equals("") ? "" : (item.middleInitial + ". "))
                + (item.lastName.equals("") ? "" : (item.lastName))
                + (item.suffix.equals("") ? "" : (" " + item.suffix))
                + (item.nameTitle.equals("") ? "" : (", " + item.nameTitle)
                + (item.jobTitle.equals("") ? "" : (", " + item.jobTitle)));
    }

    public static String getDepartment(){
        switch (Global.activeSection) {
            case "REP":
            case "ULP":
            case "ORG":
            case "MED":
            case "Hearings":
                return "SERB";
            case "Civil Service Commission":
            case "CMDS":
                return "SPBR";
        }
        return "";
    }

    public static String generateDepartmentAddressBlock(){
        String address = "";
        String dept = StringUtilities.getDepartment();

        AdministrationInformation sysAdminInfo = AdministrationInformation.loadAdminInfo(dept);

        if (!sysAdminInfo.Address1.equals("")) {
            address += sysAdminInfo.Address1.trim();
        }
        if (!sysAdminInfo.Address2.equals("")) {
            address += System.lineSeparator() + sysAdminInfo.Address2.trim();
        }
        address += System.lineSeparator();
        if (!sysAdminInfo.City.equals("")) {
            address += sysAdminInfo.City.trim();
        }
        if (!sysAdminInfo.State.equals("")) {
            address += ", " + sysAdminInfo.State.trim();
        }
        if (!sysAdminInfo.Zip.equals("")) {
            address += " " + sysAdminInfo.Zip.trim();
        }
        return address;
    }
}
