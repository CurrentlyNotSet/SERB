/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import parker.serb.sql.CaseParty;

/**
 *
 * @author User
 */
public class StringUtilities {

    public static String buildFullName(String first, String middle, String last) {
        String fullName = "";
        if (!first.equals("")) {
            fullName += first.trim();
        }
        if (!middle.equals("")) {
            fullName += " " + (middle.trim().length() == 1 ? middle.trim() + "." : middle.trim());
        }
        if (!last.equals("")) {
            fullName += " " + last.trim();
        }
        return fullName.trim();
    }
    
    public static String buildCasePartyName(CaseParty item) {
        String fullName = "";
        if (item.prefix != null) {
            fullName += item.prefix.trim();
        }
        if (item.firstName != null) {
            fullName += item.firstName.trim();
        }
        if (item.middleInitial != null) {
            fullName += " " + (item.middleInitial.trim().length() == 1 ? item.middleInitial.trim() + "." : item.middleInitial.trim());
        }
        if (item.lastName != null) {
            fullName += " " + item.lastName.trim();
        }
        if (item.suffix != null) {
            fullName += " " + item.suffix.trim();
        }
        if ("".equals(fullName.trim())){
            fullName += item.companyName.trim();
        }
        
        return fullName.trim();
    }

    public static String buildAddressBlockWithPhoneAndEmail(CaseParty item) {
        String addressBlock = "";

        addressBlock += buildCasePartyName(item);
        if (item.address1 != null) {
            addressBlock += "\n" + item.address1;
        }
        if (item.address2 != null) {
            addressBlock += "\n" + item.address2;
        }
        if (item.address3 != null) {
            addressBlock += "\n" + item.address3;
        }
        addressBlock += "\n" + item.city + ", " + item.stateCode + " " + item.zipcode;
        if (item.phone1 != null){
            addressBlock += "\n" + NumberFormatService.convertStringToPhoneNumber(item.phone1);
        }
        if (item.phone2 != null){
            addressBlock += "\n" + NumberFormatService.convertStringToPhoneNumber(item.phone2);
        }
        if (item.emailAddress != null){
            addressBlock += "\n" + item.emailAddress;
        }
        return addressBlock.trim();
    }
}
