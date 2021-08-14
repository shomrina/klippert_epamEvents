package events;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.WebDriver;

/**
 * Base class for JUnit5-based test classes
 */
public class BaseTest {

    protected WebDriver driver;
    private Logger log = LogManager.getLogger(BaseTest.class);

    @BeforeEach
    public void setUp() {
        driver = WebDriverFactory.create(System.getProperty("browser"), System.getProperty("options"));
        log.info("Driver is started");
    }

    @AfterEach
    public void shutDown() {
        if (driver != null)
            driver.quit();
        log.info("Driver is stopped");
    }
}
