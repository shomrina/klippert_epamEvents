package events.pages;

import io.qameta.allure.Step;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import java.util.List;

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
    private By eventTalkCardLocator = By.cssSelector("div.evnt-talks-row div.evnt-talk-card");
    private By searchFilterLocator = By.cssSelector("div.evnt-search-filter > input");
    private By eventTalkCardNameLocator = By.cssSelector("div.evnt-talk-name");

    public TalksLibraryPage(WebDriver driver) {
        super(driver);
    }

    public WebElement getMoreFiltersElement() {
        return getWebElement(moreFiltersBtnLocator);
    }

    @Step("Click 'More filters' or 'Hide filters'")
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

    @Step("Click filter value {filterLocationElement}")
    public void clickFilterValue(WebElement filterLocationElement) {
        filterLocationElement.click();
        waitVisibilityOfElementLocated(filterScrollLocator, 5);
        log.info("Click filter '{}'", filterLocationElement.getText());
    }

    @Step("Filter by value {filterElement}")
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

    @Step("Get event talks cards")
    public List<WebElement> getEventTalks() {
        List<WebElement> list = getWebElements(eventTalkCardLocator);
        log.debug("get events task, count = {}", list.size());
        return list;
    }

    @Step("Click on the event talks card and go to event talks page")
    public EventTalksPage clickEventTalk(WebElement eventTalksItem) {
        eventTalksItem.click();
        return new EventTalksPage();
    }

    @Step("search by key word {keyword}")
    public void searchByKeyword(String keyword) throws InterruptedException {
        waitElementToBeClickable(searchFilterLocator, 5);
        getWebElement(searchFilterLocator).sendKeys(keyword);
        waitVisibilityOf(getLoader(), 2);
        waitLoaderBecameInvisible();
        Thread.sleep(2000);
        log.info("Search by value = {}", keyword);
    }

    @Step("Get event talk card's name")
    public String getEventTalkCardName(WebElement talkCard) {
        return talkCard.findElement(eventTalkCardNameLocator).getText();
    }

    /**
     * EventTalksPage class for describing elements and interactions on the event talks opened as different page
     */
    public class EventTalksPage {
        private By eventCardTableLocator = By.cssSelector("div.evnt-card-table");

        @FindBy(css = "div.evnt-talk-details.topics div.evnt-topic.evnt-talk-topic")
        private List<WebElement> categories;

        @FindBy(css = "div.evnt-talk-details.location.evnt-now-past-talk")
        private WebElement location;

        @FindBy(css = "div.evnt-talk-details.language.evnt-now-past-talk")
        private WebElement language;

        @FindBy(css = "div.evnt-nav-cell.link > a")
        private WebElement backLink;

        public EventTalksPage() {
            waitLoaderBecameInvisible();
            waitVisibilityOfElementLocated(eventCardTableLocator, 5);
            PageFactory.initElements(driver, this);
        }

        @Step("Verify that talks contains category")
        public Boolean isContainsCategory(String expectedCategory) {
            for (WebElement category : categories) {
                log.debug("category.getText() = {}", category.getText());
                if (category.getText().contains(expectedCategory)) return true;
            }
            return false;
        }

        @Step("Verify that talks contains location")
        public Boolean isContainsLocation(String expectedLocation) {
            String locText = location.getText();
            log.debug("location text = {}", locText);
            return locText.contains(expectedLocation);
        }

        @Step("Verify that talks language is matched")
        public Boolean isMatchLanguage(String expectedLanguage) {
            String languageText = language.getText();
            log.debug("language text = {}", languageText);
            return languageText.equals(expectedLanguage);
        }

        @Step("Go back to the talks library page")
        public TalksLibraryPage goBack(){
            backLink.click();
            waitLoaderBecameInvisible();
            waitElementToBeClickable(eventCardTableLocator, 5);
            log.info("go back to the talks library page");
            return new TalksLibraryPage(driver);
        }

    }



}
