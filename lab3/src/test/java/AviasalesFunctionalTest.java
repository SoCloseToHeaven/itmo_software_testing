import driver.BrowserDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;
import page.MainPage;
import page.SearchPage;

public class AviasalesFunctionalTest {

    public static final String[] DEFAULT_CITIES = {"Санкт-Петербург", "Москва", "Сочи", "Калининград"};
    public static final String TEST_BASE_DATE = "20.09.2025";

    // Test case 0: Open main page, check if it's being loaded
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

    // Test case 1: Open main page, search for avia ticket
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
                    TEST_BASE_DATE,
                    TEST_BASE_DATE
            );

            Assertions.assertTrue(searchPage.isLoaded());

            searchPage.waitForRandomTicket();
        } finally {
            driver.quit();
        }
    }

    // Test case 2: Open main page, multi search for avia tickets
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
                    TEST_BASE_DATE,
                    TEST_BASE_DATE
            );

            searchPage.waitForRandomTicket();
        } finally {
            driver.quit();
        }
    }
}
