/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import java.sql.Timestamp;
import java.util.Calendar;

/**
 *
 * @author parkerjohnston
 */
public class DateConversion {

    public static Timestamp generateReminderStartDate(Timestamp reminderDate) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(reminderDate);
        cal.set(Calendar.HOUR_OF_DAY,8);
        cal.set(Calendar.MINUTE,0);
        cal.set(Calendar.SECOND,0);
        return new Timestamp(cal.getTimeInMillis());
    }
}
