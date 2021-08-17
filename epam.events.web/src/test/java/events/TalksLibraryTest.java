package events;

import events.pages.MainPage;
import events.pages.TalksLibraryPage;
import io.qameta.allure.Description;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Story;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

/**
 * TalksLibraryTest class for testing features linked with talks library
 */
@Epic("Epam Events")
@Feature("Talks library")
public class TalksLibraryTest extends  BaseTest {

    @Story("Filter talks by category, locations and language")
    @Description("Filter talks by category, locations and language and verify that the result list of talks card matches the filter value")
    @Test
    @DisplayName("Filter talks by category, locations and language")
    public void filterTalksByCategories() {
        String expectedCategory = "Testing";
        String expectedLocations = "Belarus";
        String expectedLanguage = "ENGLISH";

        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        TalksLibraryPage talksLibraryPage = mainPage.goToVideo();
        //set filters
        talksLibraryPage.clickMoreFilters();
        talksLibraryPage.clickFilterValue(talksLibraryPage.getFilterCategoryElement());
        talksLibraryPage.filterByValue(expectedCategory, talksLibraryPage.getFilterCategoryElement());
        talksLibraryPage.clickFilterValue(talksLibraryPage.getFilterLocationElement());
        talksLibraryPage.filterByValue(expectedLocations, talksLibraryPage.getFilterLocationElement());
        talksLibraryPage.clickFilterValue(talksLibraryPage.getFilterLanguageElement());
        talksLibraryPage.filterByValue(expectedLanguage, talksLibraryPage.getFilterLanguageElement());
        int numbersOfEventTalks = talksLibraryPage.getEventTalks().size();

        for (int i = 0; i < numbersOfEventTalks; i++) {
            WebElement eventTalk = talksLibraryPage.getEventTalks().get(i);  //get all events for these filters, take one
            var eventTalksElement = talksLibraryPage.clickEventTalk(eventTalk);
            Assertions.assertTrue(eventTalksElement.isContainsCategory(expectedCategory));
            Assertions.assertTrue(eventTalksElement.isContainsLocation(expectedLocations));
            Assertions.assertTrue(eventTalksElement.isMatchLanguage(expectedLanguage));
            talksLibraryPage = eventTalksElement.goBack();
        }
    }

    @Story("Search talks events by key word")
    @Description("Search talks events by key word and verify that the talk's name contains the key word")
    @Test
    @DisplayName("Search talks events by key word")
    public void searchTalksByKeyword() throws InterruptedException {
        String searchingValue = "QA";

        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        TalksLibraryPage talksLibraryPage = mainPage.goToVideo();
        talksLibraryPage.searchByKeyword(searchingValue);
        for (WebElement eventTalksCard : talksLibraryPage.getEventTalks()) {
            String talkName = talksLibraryPage.getEventTalkCardName(eventTalksCard);
            Assertions.assertTrue(talkName.contains(searchingValue),
                    "talks card summery = " + talkName + "\nsearchingValue = " + searchingValue);
        }
    }
}
