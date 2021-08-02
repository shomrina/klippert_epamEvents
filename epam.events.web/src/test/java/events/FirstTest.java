package events;


import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Base class for TestNG-based test classes
 */
public class FirstTest {

    protected static WebDriver driver;
    private Logger log = LogManager.getLogger(FirstTest.class);

    @BeforeTest
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        log.info("Driver is started");

    }

    @AfterTest
    public void shutDown() {
        if (driver != null)
            driver.quit();
        log.info("Driver is stopped");
    }

    @Test(description = "Просмотр предстоящих событий")
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
        Assert.assertEquals(eventCard.size(), expectedCount);

    }

}
