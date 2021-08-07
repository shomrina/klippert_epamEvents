package events;

import events.pages.EventsPage;
import events.pages.MainPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.text.ParseException;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * UpcomingEventsTest class for testing features linked with upcoming events
 */
public class UpcomingEventsTest extends BaseTest {

    private Logger log = LogManager.getLogger(UpcomingEventsTest.class);


    @Test
    @DisplayName("View upcoming events and verify count")
    public void viewUpcomingEvents() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        EventsPage eventsPage = mainPage.goToEvents();
        int expectedCount = eventsPage.getNumberUpcomingEvents();
        List<WebElement> eventCards = eventsPage.getAllEventCards();

        Assertions.assertEquals(eventCards.size(), expectedCount);
    }

    @Test
    @DisplayName("Verify upcoming event's date and current date")
    public void verifyDateUpcomingEvents() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        EventsPage eventsPage = mainPage.goToEvents();
        List<WebElement> eventCards = eventsPage.getAllEventCards();

        if (eventCards.size() == 0) {
            log.info("Not found upcoming event. The test won't continue");
            return;
        }

        for (int i = 0; i < eventCards.size(); i++) {
            String datePeriodInText = eventsPage.getDatePeriodForEvent(eventCards, i);
            List<String> datesInText = splitPeriodOnDate(datePeriodInText);
            DateTime finishDate = convertStringToDateTime(datesInText.get(1));
            Assertions.assertTrue(finishDate.isAfterNow() || finishDate.isEqualNow(),
                    "The events " + i + "shouldn't be in upcoming events list. \nfinishDate = " + finishDate + "\ncurrentDate = " + DateTime.now());
        }

    }

    public List<String> splitPeriodOnDate(String period) {
        //divide string on the two strings with date
        List<String> datesInText = Arrays.asList(period.split(" - "));
        log.debug("beginDateInText, datesIntext 0: {}", datesInText.get(0));
        log.debug("finishDateInText, datesIntext 1: {}", datesInText.get(1));
        return datesInText;
    }

    public String addedYearToBeginningDate(String beginDateInText, String finishDateInText) {
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

    public DateTime convertStringToDateTime(String dateInText) {
        //convert to date format "dd MMM yyyy", for beginDateInText have to contains year! use addedYearToBeginningDate for convert it before.
        DateTimeFormatter dateFormat = DateTimeFormat.forPattern("dd MMM yyyy").withLocale(Locale.US);
        DateTime dateTime = dateFormat.parseDateTime(dateInText);
        log.debug("convertStringToDateTime, dateTime = {}", dateFormat.print(dateTime));
        return dateTime;
    }




}
