package events.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

/**
 * EventsPage class for describing elements using only the page /events
 */
public class EventsPage extends AbstractPage {
    private Logger log = LogManager.getLogger(EventsPage.class);

    private By eventCardsLocator = By.cssSelector("div.evnt-event-card");
/*    private By upcomingEventsBntLocator = By.cssSelector("span.evnt-tab-text.desktop");
    private By upcomingEventCounterLocator = By.cssSelector("span.evnt-tab-counter");
    private By eventTabLinkLocator = By.cssSelector("a.evnt-tab-link");*/
 //   private By eventDateLocator = By.cssSelector("div.evnt-event-dates span.date");


    public EventsPage(WebDriver driver) {
        super(driver);
    }

    public List<WebElement> getAllEventCards() {
        waitElementToBeClickable(eventCardsLocator, 5);
        List<WebElement> eventCards =  getWebElements(eventCardsLocator);
        log.info("getAllEventCards, number of events = {}", eventCards.size());
        return eventCards;
    }

    public EventTabsListElements getEventTabsListElements() {
        return new EventTabsListElements();
    }

    public EventCardElement getEventCardElement(WebElement eventCard) {
        return new EventCardElement(eventCard);
    }

/*    public int getNumberUpcomingEvents() {
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
    }*/

/*    public String getDatePeriodForEvent(List<WebElement> eventCards, int id) {
        //get date periods for the event
       // List<WebElement> eventCards = this.getAllEventCards();
        String datePeriodInText = eventCards.get(id).findElement(eventDateLocator).getText();
        log.debug("event [" + id + "] datePeriodInText = {}", datePeriodInText);
        return datePeriodInText;
    }*/

    /**
     * EventTabsListElements class contains interaction, properties and describe buttons in the Event tab list^ Upcoming events and Past events
     */
    public class EventTabsListElements {
        private WebElement upcomingEventsLink;
        private WebElement pastEventsLink;

        @FindBy(css = "ul.evnt-tabs-list > li")
        private List<WebElement> evntTabsList;

        private By eventBtnLocator = By.cssSelector("span.evnt-tab-text.desktop");
        private By evntTabLinkLocator = By.cssSelector("a.evnt-tab-link");
        private By evntTabCounterLocator = By.cssSelector("span.evnt-tab-counter");

        public EventTabsListElements() {
            waitElementToBeClickable(eventBtnLocator, 5);
            PageFactory.initElements(driver, this);
            this.upcomingEventsLink = evntTabsList.get(0);
            this.pastEventsLink = evntTabsList.get(1);
        }

        /** Working with upcomingEvents button */
        public void clickUpcomingEventsBtn() {
            upcomingEventsLink.findElement(eventBtnLocator).click();
            waitLoaderBecameInvisible();
            log.info("Click Upcoming events button");
        }

        public Boolean isUpcomingActive() {
            Boolean isActive = upcomingEventsLink.findElement(evntTabLinkLocator).getAttribute("class").contains("active");
            log.info("button 'Upcoming events' {}", isActive);
            return isActive;
        }

        public int getNumberUpcomingEvents() {
            if (!isUpcomingActive()) clickUpcomingEventsBtn();
            int expectedCount = Integer.parseInt(upcomingEventsLink.findElement(evntTabCounterLocator).getText());
            log.info("getNumberUpcomingEvents = {}", expectedCount);
            return expectedCount;
        }

        /** Working with pastEvents button */
        public void clickPastEventsBtn() {
            pastEventsLink.findElement(eventBtnLocator).click();
            waitLoaderBecameInvisible();
            log.info("Click Past events button");
        }

        public Boolean isPastEventActive() {
            Boolean isActive = pastEventsLink.findElement(evntTabLinkLocator).getAttribute("class").contains("active");
            log.info("button 'Past events' {}", isActive);
            return isActive;
        }

      /*  public int getNumberPastEvents() {
            if (!isPastEventActive()) clickPastEventsBtn();
            int expectedCount = Integer.parseInt(pastEventsLink.findElement(evntTabCounterLocator).getText());
            log.info("getNumberPastEvents = {}", expectedCount);
            return expectedCount;
        }*/
    }

    public class EventCardElement {
        WebElement eventCard;

        private By languageLocator = By.cssSelector("p.language > span");
        private By eventsNameLocator = By.cssSelector("div.evnt-event-name span");
        private By eventDateLocator = By.cssSelector("div.evnt-event-dates span.date");
        private By eventRegStatusLocator = By.cssSelector("div.evnt-event-dates span.status");
        private By eventSpeakersLocator = By.cssSelector("div.evnt-people-cell.speakers");

        public EventCardElement(WebElement eventCard) {
            waitElementToBeClickable(eventCardsLocator, 5);
            PageFactory.initElements(driver, this);
            this.eventCard = eventCard;
        }

        public Boolean isElementDisplayed(By locator) {
            return eventCard.findElement(locator).isDisplayed();
        }

        public String getTextFromElement(By locator) {
            return eventCard.findElement(locator).getText();
        }

        public Boolean isDisplayedLanguage() {
            return isElementDisplayed(languageLocator);
        }

        public String getTextLanguage() {
            return getTextFromElement(languageLocator);
        }

        public Boolean isDisplayedEventName() {
            return isElementDisplayed(eventsNameLocator);
        }

        public String getTextEventName() {
            return getTextFromElement(eventsNameLocator);
        }

        public Boolean isDisplayedDate() {
            return isElementDisplayed(eventDateLocator);
        }

        public String getTextDatePeriod() {
            String datePeriodInText = getTextFromElement(eventDateLocator);
            log.debug("event datePeriodInText = {}", datePeriodInText);
            return datePeriodInText;
        }

        public Boolean isDisplayedRegStatus() {
            return isElementDisplayed(eventRegStatusLocator);
        }

        public String getTextRegStatus() {
            return getTextFromElement(eventRegStatusLocator);
        }

        public Boolean isDisplayedSpeakers() {
            return isElementDisplayed(eventSpeakersLocator);
        }

        public List<WebElement> getAllSpeakers() {
            return getWebElement(eventSpeakersLocator).findElements(By.cssSelector("div.speakers-wrapper > div.evnt-speaker"));
        }

    }
}
