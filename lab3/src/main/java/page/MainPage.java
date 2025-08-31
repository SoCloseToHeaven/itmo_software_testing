package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends AbstractPage {

    private final By searchContainer = By.xpath(
            "//div[contains(@class, 'site-header__search-form-control')]"
    );

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public Page open() {
        driver.get(BASE_URI);

        return this;
    }

    @Override
    public boolean isLoaded() {
        return isElementDisplayed(searchContainer);
    }
}
