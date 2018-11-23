/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import java.util.ArrayList;
import java.util.List;
import parker.serb.sql.CMDSCase;

/**
 *
 * @author Andrew
 */
public class CaseNumberTools {

    public static String[] removeDupliateCasesByGroupNumber(String[] caseNumbers) {
        List<String> strippedArray = new ArrayList<>();

        List<CMDSCase> caseList = CMDSCase.CMDSCaseNumbersWithGroupNumber(caseNumbers);

        if (caseList.size() > 0) {
            String groupNumber = "";

            for (CMDSCase c : caseList) {
                String caseNumber = NumberFormatService.generateFullCaseNumberNonGlobal(c.caseYear, c.caseType, c.caseMonth, c.caseNumber);

                if (c.groupNumber.equals("") || !c.groupNumber.equals(groupNumber)) {
                    groupNumber = c.groupNumber;
                    strippedArray.add(caseNumber);
                }
            }
        }
        String[] returnList = strippedArray.toArray(new String[0]);
        return returnList;
    }
    
    
    public static List<CMDSCase> DocketingCases(String[] caseNumbers) {
        
        List<String> groupNumbers = CMDSCase.DistinctGroupNumberFromCMDSCaseNumbers(caseNumbers);

        List<CMDSCase> caseList = CMDSCase.CMDSDocketingCaseList(caseNumbers, groupNumbers.toArray(new String[0]));
        
        return caseList;
    }
    
}
