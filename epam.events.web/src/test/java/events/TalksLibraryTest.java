package events;

import events.pages.MainPage;
import events.pages.TalksLibraryPage;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class TalksLibraryTest extends  BaseTest {

    @Test
    @DisplayName("Filter talks by category")
    public void filterTalksByCategory() {
        String expectedCategory = "Testing";
        String expectedLocations = "Belarus";
        String expectedLanguage = "ENGLISH";

        MainPage mainPage = new MainPage(driver);
        mainPage.openMainPage();
        TalksLibraryPage talksLibraryPage = mainPage.goToVideo();
        talksLibraryPage.clickMoreFilters();
        talksLibraryPage.clickFilterValue(talksLibraryPage.getFilterCategoryElement());
        talksLibraryPage.filterByValue(expectedCategory, talksLibraryPage.getFilterCategoryElement());
        talksLibraryPage.clickFilterValue(talksLibraryPage.getFilterLocationElement());
        talksLibraryPage.filterByValue(expectedLocations, talksLibraryPage.getFilterLocationElement());
        talksLibraryPage.clickFilterValue(talksLibraryPage.getFilterLanguageElement());
        talksLibraryPage.filterByValue(expectedLanguage, talksLibraryPage.getFilterLanguageElement());
    }
}
