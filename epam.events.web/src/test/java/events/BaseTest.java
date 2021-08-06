package events;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * Base class for TestNG-based test classes
 */

public class BaseTest {

    protected static WebDriver driver;
    private Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeEach
    public void setUp() {
        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        log.info("Driver is started");

    }

    @AfterEach
    public void shutDown() {
        if (driver != null)
            driver.quit();
        log.info("Driver is stopped");
    }
}
