import driver.BrowserDriver;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.openqa.selenium.WebDriver;
import page.AbstractPage;
import page.MainPage;

public class XolodilnikFunctionalTest {

    private static final String BASE_URL = AbstractPage.BASE_URI;

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
}
