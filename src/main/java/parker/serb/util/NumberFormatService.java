/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package parker.serb.util;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import parker.serb.Global;

/**
 *
 * @author parker
 */
public class NumberFormatService {

    public String caseYear;
    public String caseType;
    public String caseMonth;
    public String caseNumber;
    private static final String[] tensNames = {
        "",
        " ten",
        " twenty",
        " thirty",
        " forty",
        " fifty",
        " sixty",
        " seventy",
        " eighty",
        " ninety"
    };

    private static final String[] numNames = {
        "",
        " one",
        " two",
        " three",
        " four",
        " five",
        " six",
        " seven",
        " eight",
        " nine",
        " ten",
        " eleven",
        " twelve",
        " thirteen",
        " fourteen",
        " fifteen",
        " sixteen",
        " seventeen",
        " eighteen",
        " nineteen"
    };

    public static String convertStringToPhoneNumber(String number) {
        String formattedNumber = "";

        if (number == null) {
            formattedNumber = "";
        } else if (number.length() >= 10) {
            formattedNumber += "(" + number.substring(0, 3) + ") ";
            formattedNumber += number.substring(3, 6) + "-";
            formattedNumber += number.substring(6, 10);

            if (number.length() > 10) {
                formattedNumber += " x" + number.substring(10);
            }
        } else {
            formattedNumber = number;
        }
        return formattedNumber;
    }

    public static String convertPhoneNumberToString(String number) {
        return number.replaceAll("[^0-9]", "");
    }

    public static long convertMMDDYYYY(String mmddyyyyDate) {

        if (mmddyyyyDate.equals("")) {
            return 0;
        } else {
            Date date = null;
            try {
                date = Global.mmddyyyy.parse(mmddyyyyDate);
            } catch (ParseException ex) {
                SlackNotification.sendNotification(ex);
            }
            return date.getTime();
        }
    }

    public static long converthmma(String hmma) {

        if (hmma.equals("")) {
            return 0;
        } else {
            Date date = null;
            try {
                date = Global.hmma.parse(hmma);
            } catch (ParseException ex) {
                SlackNotification.sendNotification(ex);
            }
            return date.getTime();
        }
    }

    public static String generateFullCaseNumber() {
        return Global.caseYear + "-" + Global.caseType
                + "-" + Global.caseMonth + "-" + Global.caseNumber;
    }

    public static String generateFullCaseNumberNonGlobal(String caseYear, String caseType, String caseMonth, String caseNumber) {
        return caseYear + "-" + caseType
                + "-" + caseMonth + "-" + caseNumber;
    }

    public static void parseFullCaseNumber(String fullCaseNumber) {
        String[] parsedCaseNumber = fullCaseNumber.split("-");
        Global.caseYear = parsedCaseNumber[0];
        Global.caseType = parsedCaseNumber[1];
        Global.caseMonth = parsedCaseNumber[2];
        Global.caseNumber = parsedCaseNumber[3];
    }

    public static NumberFormatService parseFullCaseNumberNoNGlobal(String fullCaseNumber) {
        NumberFormatService num = new NumberFormatService();

        String[] parsedCaseNumber = fullCaseNumber.split("-");
        num.caseYear = parsedCaseNumber[0];
        num.caseType = parsedCaseNumber[1];
        num.caseMonth = parsedCaseNumber[2];
        num.caseNumber = parsedCaseNumber[3];
        return num;
    }

    public static void clearCaseNumberInformation() {
        Global.caseYear = "";
        Global.caseType = "";
        Global.caseMonth = "";
        Global.caseNumber = "";
    }

    public static String convertLongToTime(long millis) {
        String duration = String.format("%02dhr %02dmin %02dsec",
                TimeUnit.MILLISECONDS.toHours(millis),
                TimeUnit.MILLISECONDS.toMinutes(millis)
                - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)),
                TimeUnit.MILLISECONDS.toSeconds(millis)
                - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
        if (TimeUnit.MILLISECONDS.toHours(millis) == 0) {
            String[] split = duration.split("hr");
            duration = split[1].trim();
        }
        return duration.trim();
    }

    public static String monthNumber(String month) {
        if (month != null) {
            if (month.startsWith("Jan")) {
                return "01";
            } else if (month.startsWith("Feb")) {
                return "02";
            } else if (month.startsWith("Mar")) {
                return "03";
            } else if (month.startsWith("Apr")) {
                return "04";
            } else if (month.startsWith("May")) {
                return "05";
            } else if (month.startsWith("Jun")) {
                return "06";
            } else if (month.startsWith("Jul")) {
                return "07";
            } else if (month.startsWith("Aug")) {
                return "08";
            } else if (month.startsWith("Sep")) {
                return "09";
            } else if (month.startsWith("Oct")) {
                return "10";
            } else if (month.startsWith("Nov")) {
                return "11";
            } else if (month.startsWith("Dec")) {
                return "12";
            }
        }
        return null;
    }

    public static String convert(long number) {
        // 0 to 999 999 999 999
        if (number == 0) {
            return "zero";
        }

        String snumber = Long.toString(number);

        // pad with "0"
        String mask = "000000000000";
        DecimalFormat df = new DecimalFormat(mask);
        snumber = df.format(number);

        // XXXnnnnnnnnn
        int billions = Integer.parseInt(snumber.substring(0, 3));
        // nnnXXXnnnnnn
        int millions = Integer.parseInt(snumber.substring(3, 6));
        // nnnnnnXXXnnn
        int hundredThousands = Integer.parseInt(snumber.substring(6, 9));
        // nnnnnnnnnXXX
        int thousands = Integer.parseInt(snumber.substring(9, 12));

        String tradBillions;
        switch (billions) {
            case 0:
                tradBillions = "";
                break;
            case 1:
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
                break;
            default:
                tradBillions = convertLessThanOneThousand(billions)
                        + " billion ";
        }
        String result = tradBillions;

        String tradMillions;
        switch (millions) {
            case 0:
                tradMillions = "";
                break;
            case 1:
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
                break;
            default:
                tradMillions = convertLessThanOneThousand(millions)
                        + " million ";
        }
        result = result + tradMillions;

        String tradHundredThousands;
        switch (hundredThousands) {
            case 0:
                tradHundredThousands = "";
                break;
            case 1:
                tradHundredThousands = "one thousand ";
                break;
            default:
                tradHundredThousands = convertLessThanOneThousand(hundredThousands)
                        + " thousand ";
        }
        result = result + tradHundredThousands;

        String tradThousand;
        tradThousand = convertLessThanOneThousand(thousands);
        result = result + tradThousand;

        // remove extra spaces!
        return result.replaceAll("^\\s+", "").replaceAll("\\b\\s{2,}\\b", " ");
    }

    private static String convertLessThanOneThousand(int number) {
        String soFar;

        if (number % 100 < 20) {
            soFar = numNames[number % 100];
            number /= 100;
        } else {
            soFar = numNames[number % 10];
            number /= 10;

            soFar = tensNames[number % 10] + soFar;
            number /= 10;
        }
        if (number == 0) {
            return soFar;
        }
        return numNames[number] + " hundred" + soFar;
    }
}
