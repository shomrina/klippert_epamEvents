package events.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * DateHelper class works with date using DateTime
 */

public class DateHelper {
    private static Logger log = LogManager.getLogger(DateHelper .class);

    /** Returns List of two strings with date from String period.*/
    public static List<String> splitPeriodOnDate(String period) {
        //divide string on the two strings with date
        List<String> datesInText = Arrays.asList(period.split(" - "));
        log.debug("beginDateInText, datesIntext 0: {}", datesInText.get(0));
        log.debug("finishDateInText, datesIntext 1: {}", datesInText.get(1));
        return datesInText;
    }

    /** Returns converted String date from 'dd MMM' to 'dd MMM yyyy' using value 'year' from finishDateInText */
    public static String addedYearToBeginningDate(String beginDateInText, String finishDateInText) {
        //convert beginning date to format 'dd mmm yyyy'
        List<String> beginDateInTextList = Arrays.asList(beginDateInText.split(" "));
        List<String> finishDateInTextList = Arrays.asList(finishDateInText.split(" "));
        String convertedBeginDateInText;
        if (beginDateInTextList.size() == 2) {
            convertedBeginDateInText = beginDateInText + " " + finishDateInTextList.get(2);
            log.debug("add year to value 'beginDateInText', new value = {}", convertedBeginDateInText);
        } else convertedBeginDateInText = beginDateInText;
        return convertedBeginDateInText;
    }

    /** Returns date in the format 'DateTime'. The dateInText converted from String to DateTime */
    public static DateTime convertStringToDateTime(String dateInText) {
        //convert to date format "dd MMM yyyy", for beginDateInText have to contains year! use addedYearToBeginningDate for convert it before.
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd MMM yyyy").withLocale(Locale.US);
        DateTime dateTime = dateFormat.parseDateTime(dateInText);
        log.debug("convertStringToDateTime, dateTime = {}", dateFormat.print(dateTime));
        return dateTime;
    }

}
