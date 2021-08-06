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

    @Test
    public void firstTest() {
        driver.get("https://events.epam.com/");
        log.info("Open main page"); //cookie banner : id = onetrust-banner-sdk   & id = onetrust-accept-btn-handler

        driver.findElement(By.cssSelector("a[href='/events']")).click();
        WebDriverWait wait = new WebDriverWait(driver, 10);
        wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector("div.evnt-global-loader"))));
        log.info("Go to Events");

        WebElement upcomingEventsBtn = wait.until(ExpectedConditions.elementToBeClickable(By.cssSelector("span.evnt-tab-text.desktop")));
        upcomingEventsBtn.click(); //if ![a.evnt-tab-link].getAttribute("class").contains("active") else она уже нажата...
        int expectedCount = Integer.parseInt(driver.findElement(By.cssSelector("span.evnt-tab-counter")).getText()) ;
        List<WebElement> eventCard =  driver.findElements(By.cssSelector("div.evnt-event-card"));
        Assertions.assertEquals(eventCard.size(), expectedCount);

    }

}
