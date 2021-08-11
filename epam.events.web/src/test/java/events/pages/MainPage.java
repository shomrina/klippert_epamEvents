package events.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

/**
 * MainPage class for describing elements on the page 'https://events.epam.com'
 */
public class MainPage extends AbstractPage {
    private Logger log = LogManager.getLogger(MainPage.class);

    private String baseUrl = "https://events.epam.com/";

    private By cookieBannerLocator = By.id("onetrust-banner-sdk");
    private By acceptCookieBtn = By.id("onetrust-accept-btn-handler");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    public MainPage openMainPage() {
        driver.get(baseUrl);
        log.info("Open main page"); //cookie banner : id = onetrust-banner-sdk   & id = onetrust-accept-btn-handler
        closeCookieBanner();
        return new MainPage(driver);
    }

    public void closeCookieBanner() {
        if(getWebElement(cookieBannerLocator).isDisplayed()) {
            getWebElement(cookieBannerLocator).findElement(acceptCookieBtn).click();
            log.info("close cookie banner");
        }
    }

}
