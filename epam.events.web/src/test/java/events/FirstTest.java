package events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class FirstTest extends BaseTest {

    private Logger log = LogManager.getLogger(FirstTest.class);

    private String baseUrl = "https://events.epam.com/";
    private By eventsBtnLocator = By.cssSelector("a[href='/events']");
    private By loaderLocator = By.cssSelector("div.evnt-global-loader");
    private By upcomingEventsBntLocator = By.cssSelector("span.evnt-tab-text.desktop");
    private By upcomingEventCounterLocator = By.cssSelector("span.evnt-tab-counter");
    private By eventCardsLocator = By.cssSelector("div.evnt-event-card");
    private By eventTabLinkLocator = By.cssSelector("a.evnt-tab-link");

    @DisplayName("View upcoming events and verify count")
    @Test
    public void viewUpcomingEvents() {
        openMainPage();
        goToEvents();
        int expectedCount = getNumberUpcomingEvents();
        List<WebElement> eventCard = getAllEventCards();

        Assertions.assertEquals(eventCard.size(), expectedCount);
    }

    private List<WebElement> getAllEventCards() {
        List<WebElement> eventCard =  getWebElements(eventCardsLocator);
        return eventCard;
    }

    private int getNumberUpcomingEvents() {
        if (!isUpcomingEvensActive()) waitElementToBeClickable(upcomingEventsBntLocator, 5).click();
        int expectedCount = Integer.parseInt(getWebElement(upcomingEventCounterLocator).getText()) ;
        return expectedCount;
    }

    private void goToEvents() {
        getWebElement(eventsBtnLocator).click();
        waitInvisibilityOf(getWebElement(loaderLocator), 10);
        log.info("Go to Events");
    }

    private void openMainPage() {
        driver.get(baseUrl);
        log.info("Open main page"); //cookie banner : id = onetrust-banner-sdk   & id = onetrust-accept-btn-handler
    }

    private Boolean isUpcomingEvensActive() {
        Boolean isActive = waitVisibilityOfElementLocated(eventTabLinkLocator, 5).getAttribute("class").contains("active");
      //  log.debug("DEBUG atribute classname = {}", getWebElement(eventTabLinkLocator).getAttribute("class"));
        log.info("button 'Upcoming events' {}", isActive);
        return isActive;
    }

    private Boolean waitInvisibilityOf(WebElement webElement, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    private WebElement waitElementToBeClickable(By locator, int timeInSec){
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement waitVisibilityOfElementLocated(By locator, int timeInSec){
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    private WebElement getWebElement(By locator) {
        return driver.findElement(locator);
    }

    private List<WebElement> getWebElements(By locator) {
        return driver.findElements(locator);
    }

}
