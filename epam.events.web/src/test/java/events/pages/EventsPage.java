package events.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.List;

/**
 * EventsPage class for describing elements using only the page /events
 */
public class EventsPage extends AbstractPage {
    private Logger log = LogManager.getLogger(EventsPage.class);

    private By eventCardsLocator = By.cssSelector("div.evnt-event-card");
    private By upcomingEventsBntLocator = By.cssSelector("span.evnt-tab-text.desktop");
    private By upcomingEventCounterLocator = By.cssSelector("span.evnt-tab-counter");
    private By eventTabLinkLocator = By.cssSelector("a.evnt-tab-link");
    private By eventDateLocator = By.cssSelector("div.evnt-event-dates span.date");

    public EventsPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getAllEventCards() {
        waitElementToBeClickable(eventCardsLocator, 5);
        List<WebElement> eventCards =  getWebElements(eventCardsLocator);
        log.info("getAllEventCards, number of events = {}", eventCards.size());
        return eventCards;
    }

    public int getNumberUpcomingEvents() {
        if (!isUpcomingEvensActive()) waitElementToBeClickable(upcomingEventsBntLocator, 5).click();
        int expectedCount = Integer.parseInt(getWebElement(upcomingEventCounterLocator).getText()) ;
        log.info("getNumberUpcomingEvents = {}", expectedCount);
        return expectedCount;
    }

    public Boolean isUpcomingEvensActive() {
        Boolean isActive = waitVisibilityOfElementLocated(eventTabLinkLocator, 5).getAttribute("class").contains("active");
        //  log.debug("DEBUG atribute classname = {}", getWebElement(eventTabLinkLocator).getAttribute("class"));
        log.info("button 'Upcoming events' {}", isActive);
        return isActive;
    }

    public String getDatePeriodForEvent(List<WebElement> eventCards, int id) {
        //get date periods for the event
       // List<WebElement> eventCards = this.getAllEventCards();
        String datePeriodInText = eventCards.get(id).findElement(eventDateLocator).getText();
        log.debug("event [" + id + "] datePeriodInText = {}", datePeriodInText);
        return datePeriodInText;
    }

}
