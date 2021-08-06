package events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.Assertions;
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

    @Test
    public void firstTest() {
        driver.get(baseUrl);
        log.info("Open main page"); //cookie banner : id = onetrust-banner-sdk   & id = onetrust-accept-btn-handler

        driver.findElement(eventsBtnLocator).click();
        waitInvisibilityOf(getWebElement(loaderLocator), 10);
        log.info("Go to Events");

        waitElementToBeClickable(upcomingEventsBntLocator, 5).click(); //if ![a.evnt-tab-link].getAttribute("class").contains("active") else она уже нажата...

        int expectedCount = Integer.parseInt(driver.findElement(upcomingEventCounterLocator).getText()) ;
        List<WebElement> eventCard =  getWebElements(eventCardsLocator);

        Assertions.assertEquals(eventCard.size(), expectedCount);
    }

    private Boolean waitInvisibilityOf(WebElement webElement, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    private WebElement waitElementToBeClickable(By locator, int timeInSec){
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    private WebElement getWebElement(By locator) {
        return driver.findElement(locator);
    }

    private List<WebElement> getWebElements(By locator) {
        return driver.findElements(locator);
    }

}
