package page;

import org.openqa.selenium.WebDriver;

public class SearchPage extends AbstractPage {

    public static final String PAGE_URL = "https://www.aviasales.ru/search";

    public SearchPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public Page open() {
        driver.get(PAGE_URL);

        return this;
    }

    @Override
    public boolean isLoaded() {
        return true;
    }
}
