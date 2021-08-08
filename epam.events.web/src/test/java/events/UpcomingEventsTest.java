package events;

import events.pages.EventsPage;
import events.pages.MainPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;
import java.util.List;

import static events.util.DateHelper.convertStringToDateTime;
import static events.util.DateHelper.splitPeriodOnDate;

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
        int expectedCount = eventsPage.getEventTabsListElements().getNumberUpcomingEvents();
       // int expectedCount = eventsPage.getNumberUpcomingEvents();
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

}
