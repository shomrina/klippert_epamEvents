package events.pages;

import io.qameta.allure.Step;
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

    @Step("Open main page")
    public void openMainPage() {
        driver.get(baseUrl);
        log.info("Open main page");
        closeCookieBanner();
    }

    @Step("Close cookie banner")
    public void closeCookieBanner() {
        if(getWebElement(cookieBannerLocator).isDisplayed()) {
            getWebElement(cookieBannerLocator).findElement(acceptCookieBtn).click();
            log.info("close cookie banner");
        }
    }

}
