package events;

import events.pages.EventsPage;
import events.pages.MainPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

import java.util.List;

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
        List<WebElement> eventCardsList = eventsPage.getAllEventCards();

        //todo потом подумать, возможно здесь заменить на софт-ассерт
        for (int i = 0; i < eventCardsList.size(); i++) {
            var eventCardElement = eventsPage.getEventCardElement(eventCardsList.get(i));
            Assertions.assertTrue(eventCardElement.isDisplayedLanguage());
            Assertions.assertNotNull(eventCardElement.getTextLanguage());
            Assertions.assertTrue(eventCardElement.isDisplayedEventName());
            Assertions.assertNotNull(eventCardElement.getTextEventName());
            Assertions.assertTrue(eventCardElement.isDisplayedDate());
            Assertions.assertNotNull(eventCardElement.getTextDatePeriod());
            Assertions.assertTrue(eventCardElement.isDisplayedRegStatus());
            Assertions.assertNotNull(eventCardElement.getTextRegStatus());
            Assertions.assertTrue(eventCardElement.isDisplayedSpeakers());
            Assertions.assertTrue(eventCardElement.getAllSpeakers().size() != 0);
        }
    }

    @Test
    @DisplayName("View past events in Canada")
    public void viewPastEventsInCanada() {
        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        EventsPage eventsPage = mainPage.goToEvents();
        eventsPage.getEventTabsListElements().clickPastEventsBtn();
        eventsPage.openLocationFilter();
        eventsPage.filterByLocation("Canada");
        int expectedCount = eventsPage.getEventTabsListElements().getNumberPastEvents();
        int eventCardsCount = eventsPage.getAllEventCards().size();

        Assertions.assertEquals(expectedCount, eventCardsCount);
    }
}
