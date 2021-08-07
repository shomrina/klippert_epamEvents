package events.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.WebDriver;

/**
 * MainPage class for describing elements using only the 'https://events.epam.com'
 */
public class MainPage extends AbstractPage {
    private Logger log = LogManager.getLogger(MainPage.class);

    public MainPage(WebDriver driver) {
        super(driver);
    }
}
