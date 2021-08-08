package events;

import events.pages.EventsPage;
import events.pages.MainPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * PastEventsTest class for testing features linked with past events
 */
public class PastEventsTest extends BaseTest {

    @Test
    @DisplayName("View past events")
    public void viewPastEvents() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        EventsPage eventsPage = mainPage.goToEvents();
        eventsPage.getEventTabsListElements().clickPastEventsBtn();
        eventsPage.getAllEventCards();


    }
}
