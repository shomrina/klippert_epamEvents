package events.pages;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * TalksLibraryPage class for describing elements on the Video page (talks library)
 */
public class TalksLibraryPage extends AbstractPage {
    private Logger log = LogManager.getLogger(TalksLibraryPage.class);

    private By moreFiltersBtnLocator = By.cssSelector("div[href='#collapseMoreFilters']");
    private By collapseMoreFiltersLocator = By.id("collapseMoreFilters");
    private By categoryFilterLocator = By.id("filter_category");
    private By locationFilterLocator = By.id("filter_location");
    private By filterScrollLocator = By.cssSelector("div.evnt-dropdown-filter.dropdown.show div.evnt-filter-menu");  //todo?
    private By languageFilterLocator = By.id("filter_language");



    public TalksLibraryPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getMoreFiltersElement() {
        return getWebElement(moreFiltersBtnLocator);
    }

    public void clickMoreFilters() {
        getMoreFiltersElement().click();
        log.info("Click More/Hide Filters button");
        waitVisibilityOfElementLocated(collapseMoreFiltersLocator, 5);
    }

    public WebElement getFilterCategoryElement() {
        return getWebElement(categoryFilterLocator);
    }

    public WebElement getFilterLocationElement() {
        return getWebElement(locationFilterLocator);
    }

    public WebElement getFilterLanguageElement() {
        return getWebElement(languageFilterLocator);
    }

    public void clickFilterValue(WebElement filterLocationElement) {
        filterLocationElement.click();
        waitVisibilityOfElementLocated(filterScrollLocator, 5);
        log.info("Click filter '{}'", filterLocationElement.getText());
    }

    public void filterByValue(String filterValue, WebElement filterElement) {
        if(filterElement.getAttribute("aria-expanded").contains("true")) {
            this.getWebElement(filterScrollLocator).findElement(By.cssSelector("label[data-value='" + filterValue + "']")).click();
            waitLoaderBecameInvisible();
            filterElement.click();  //close filter scroll
            log.info("Events was filtered by {} = {}", filterElement.getText(), filterValue);
        } else {
            log.info("Filter 'Location' wasn't expanded");
        }
    }


}
