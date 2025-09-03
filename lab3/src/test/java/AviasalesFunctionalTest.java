import driver.BrowserDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import page.*;

public class AviasalesFunctionalTest {

    public static final String[] DEFAULT_CITIES = {"Санкт-Петербург", "Москва", "Сочи", "Калининград"};
    public static final String[] TEST_BASE_DATES = {"20.09.2025", "26.09.2025"};

    // Test case 1: Open main page, check if it's being loaded
    @ParameterizedTest
    @EnumSource(BrowserDriver.class)
    public void mainPageOpen(BrowserDriver browserDriver) {
        WebDriver driver = browserDriver.apply();
        try {
            var result = new MainPage(driver)
                    .open()
                    .isLoaded();

            Assertions.assertTrue(result);
        } finally {
            driver.quit();
        }
    }

    // Test case 2: Open main page, search for avia ticket
    @ParameterizedTest
    @EnumSource(BrowserDriver.class)
    public void aviaTicketSearch(BrowserDriver browserDriver) {
        WebDriver driver = browserDriver.apply();
        try {
            MainPage mainPage = new MainPage(driver);

            mainPage.open();

            Assertions.assertTrue(mainPage.isLoaded());

            var searchPage = mainPage.searchTickets(
                    DEFAULT_CITIES[1],
                    DEFAULT_CITIES[2],
                    TEST_BASE_DATES[0],
                    TEST_BASE_DATES[0]
            );

            Assertions.assertTrue(searchPage.isLoaded());

            searchPage.waitForRandomTicket();
        } finally {
            driver.quit();
        }
    }

    // Test case 3: Open main page, multi search for avia tickets
    @ParameterizedTest
    @EnumSource(BrowserDriver.class)
    public void aviaTicketMultiSearch(BrowserDriver browserDriver) {
        WebDriver driver = browserDriver.apply();
        try {
            SearchPage searchPage = new SearchPage(driver);

            var result = searchPage.open();

            Assertions.assertTrue(result.isLoaded());

            searchPage = searchPage.multiSearchTickets(
                    DEFAULT_CITIES[0],
                    DEFAULT_CITIES[1],
                    DEFAULT_CITIES[2],
                    TEST_BASE_DATES[0],
                    TEST_BASE_DATES[0]
            );

            searchPage.waitForRandomTicket();
        } finally {
            driver.quit();
        }
    }

    // Test case 4: Open hotels booking page, look for a hotel
    @ParameterizedTest
    @EnumSource(BrowserDriver.class)
    public void bookingSearch(BrowserDriver browserDriver) {
        WebDriver driver = browserDriver.apply();
        try {
            BookingPage bookingPage = new BookingPage(driver);

            bookingPage.open();

            Assertions.assertTrue(bookingPage.isLoaded());

            bookingPage.searchHotels(DEFAULT_CITIES[0], TEST_BASE_DATES[0], TEST_BASE_DATES[1]);
        } finally {
            driver.quit();
        }
    }

    // Test case 5: Long story short search place
    @ParameterizedTest
    @EnumSource(BrowserDriver.class)
    public void longStoryShortSearch(BrowserDriver browserDriver) {
        WebDriver driver = browserDriver.apply();
        try {
            LongStoryShortPage lssPage = new LongStoryShortPage(driver);

            lssPage.open();

            Assertions.assertTrue(lssPage.isLoaded());

            lssPage.search(DEFAULT_CITIES[0]);

            lssPage.clickOnSearchResultCity("LED");
        } finally {
            driver.quit();
        }
    }

    // Test case 6: Long story short search place
    @ParameterizedTest()
    @EnumSource(BrowserDriver.class)
    public void longStoryShortSearchFail(BrowserDriver browserDriver) {
        WebDriver driver = browserDriver.apply();
        try {
            LongStoryShortPage lssPage = new LongStoryShortPage(driver);

            lssPage.open();

            Assertions.assertTrue(lssPage.isLoaded());

            lssPage.search(DEFAULT_CITIES[1]);

            Assertions.assertThrows(TimeoutException.class, () -> lssPage.clickOnSearchResultCity("LED"));
        } finally {
            driver.quit();
        }
    }

    // Test case 7: Load favourites page and test navigation by pop up button
    @ParameterizedTest()
    @EnumSource(BrowserDriver.class)
    public void favouritesLikeButtonTest(BrowserDriver browserDriver) {
        WebDriver driver = browserDriver.apply();
        try {
            FavouritesPage page = new FavouritesPage(driver);

            page.open();

            Assertions.assertTrue(page.isLoaded());

            page.like();

            MainPage mainPage = page.navigateToToMainPage();

            Assertions.assertTrue(mainPage.isLoaded());
        } finally {
            driver.quit();
        }
    }

    // Test case 8: Login via vk and check it in settings
    @ParameterizedTest()
    @EnumSource(BrowserDriver.class)
    public void loginViaVkAndPersonalize(BrowserDriver browserDriver) {
        WebDriver driver = browserDriver.apply();
        try {
            MainPage mainPage = new MainPage(driver);

            mainPage.open();

            Assertions.assertTrue(mainPage.isLoaded());

            mainPage.loginVkontakte();

            mainPage.toSettings();

            SettingsPage settingsPage = new SettingsPage(driver);

            Assertions.assertTrue(settingsPage.isLoaded());

            settingsPage.checkIfAuthorizedViaVK();
        } finally {
            driver.quit();
        }
    }

    // Test case 9: Open settings menu and personalize
    @ParameterizedTest()
    @EnumSource(BrowserDriver.class)
    public void settingsPersonalizationTest(BrowserDriver browserDriver) {
        WebDriver driver = browserDriver.apply();
        try {

            SettingsPage settingsPage = new SettingsPage(driver);

            settingsPage.open();

            Assertions.assertTrue(settingsPage.isLoaded());

            settingsPage.darkTheme();
        } finally {
            driver.quit();
        }
    }
}
