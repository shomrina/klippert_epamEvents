package events.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

/**
 * AbstractPage class for describing general and common things, using everywhere
 */
public abstract class AbstractPage {
    protected WebDriver driver;
    private Logger log = LogManager.getLogger(AbstractPage.class);

    /** Common locators of web elements using on the each page */

    private By eventsBtnLocator = By.cssSelector("a[href='/events']");
    private By loaderLocator = By.cssSelector("div.evnt-global-loader");
    private By videoBtnLocator = By.cssSelector("a[href*='/video']");

    public AbstractPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }
    public WebElement getWebElement(By locator) {
        return driver.findElement(locator);
    }

    public List<WebElement> getWebElements(By locator) {
        return driver.findElements(locator);
    }

    /** Methods for explicit expectations  */
    public WebElement waitElementToBeClickable(By locator, int timeInSec){
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitVisibilityOfElementLocated(By locator, int timeInSec){
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public WebElement waitVisibilityOf(WebElement webElement, int timeInSec){
        WebDriverWait wait = new WebDriverWait(driver, timeInSec,20);
        return wait.until(ExpectedConditions.visibilityOf(webElement));
    }

    public Boolean waitInvisibilityOf(WebElement webElement, int timeInSec) {
        WebDriverWait wait = new WebDriverWait(driver, timeInSec);
        return wait.until(ExpectedConditions.invisibilityOf(webElement));
    }

    public void  waitLoaderBecameInvisible() {
        if (getLoader().isDisplayed()) {
            log.debug("loader detected");
            waitInvisibilityOf(getLoader(), 10);
        }
    }

    public WebElement getLoader() {
        return getWebElement(loaderLocator);
    }

    /** Methods for common activities or interaction with web elements using on the each page  */
    @Step("Go to Events page")
    public EventsPage goToEvents() {
        getWebElement(eventsBtnLocator).click();
        waitLoaderBecameInvisible();
        log.info("Go to Events");
        return new EventsPage(driver);
    }

    @Step("Go to Video page (talks library)")
    public TalksLibraryPage goToVideo() {
        getWebElement(videoBtnLocator).click();
        waitLoaderBecameInvisible();
        log.info("Go to Video page (talks library)");
        return new TalksLibraryPage(driver);
    }
}
