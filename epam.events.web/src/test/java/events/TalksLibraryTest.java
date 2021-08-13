package events;

import events.pages.MainPage;
import events.pages.TalksLibraryPage;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebElement;

public class TalksLibraryTest extends  BaseTest {

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

    @Test
    @DisplayName("Search talks events by key word")
    public void searchTalksByKeyword() {
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
