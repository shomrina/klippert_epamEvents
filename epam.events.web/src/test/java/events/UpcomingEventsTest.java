package events;

import events.pages.EventsPage;
import events.pages.MainPage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * UpcomingEventsTest class for testing features linked with upcoming events
 */
public class UpcomingEventsTest extends BaseTest {

    private Logger log = LogManager.getLogger(UpcomingEventsTest.class);

    @DisplayName("View upcoming events and verify count")
    @Test
    public void viewUpcomingEvents() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        EventsPage eventsPage = mainPage.goToEvents();
        int expectedCount = eventsPage.getNumberUpcomingEvents();
        List<WebElement> eventCard = eventsPage.getAllEventCards();

        Assertions.assertEquals(eventCard.size(), expectedCount);
    }












}
