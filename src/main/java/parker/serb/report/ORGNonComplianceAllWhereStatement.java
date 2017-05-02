/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.report;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import parker.serb.util.SlackNotification;

/**
 *
 * @author User
 */
public class ORGNonComplianceAllWhereStatement {

    public static String oneYearPriorData(String month, String year){
        String sqlWHERE = "";

        SimpleDateFormat monthDate = new SimpleDateFormat("MMMM-yyyy");
        Calendar cal = Calendar.getInstance();
        try {
            cal.setTime(monthDate.parse(month + "-" + year));
        } catch (ParseException ex) {
            SlackNotification.sendNotification(ex);
        }

        for (int i = 1; i <= 12; i++){
            String[] month_Year = monthDate.format(cal.getTime()).split("-");

            sqlWHERE += (sqlWHERE.trim().equals("") ? "" : " OR ");

            sqlWHERE += "((ORGCase.fiscalYearEnding = '" + month_Year[0] + "' "
                    + " AND "
                    + " ((ORGCase.annualReport IS NULL OR ORGCase.annualReport NOT LIKE '" + month_Year[1] + "%' ) "
                    + "OR (ORGCase.financialReport NOT LIKE  '" + month_Year[1] + "%'  "
                    + "OR ORGCase.financialReport IS NULL) "
                    + "OR (ORGCase.registrationReport IS NULL) "
                    + "OR (ORGCase.constructionAndByLaws IS NULL)) "
                    + " AND "
                    + " (ORGCase.valid = 1 AND ORGCase.filedByParent = 0) "
                    + " AND "
                    + " (ISNUMERIC(ORGCase.orgNumber) = 1))) ";

            cal.add(Calendar.MONTH, -1);
        }
        return sqlWHERE;
    }

}
