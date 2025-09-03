package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LongStoryShortPage extends AbstractPage {

    public static final String BASE_URL = "https://www.aviasales.ru/guides";

    private final By longStoryInput = By.xpath("//input[@id='travel_map_cities_search']");

    public LongStoryShortPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public Page open() {
        driver.get(BASE_URL);

        click(acceptCookiesButton);

        return this;
    }

    @Override
    public boolean isLoaded() {
        return isElementDisplayed(longStoryInput);
    }

    public void search(String text) {
        stealthType(longStoryInput, text);
    }

    public void clickOnSearchResultCity(String cityCode) {
        By button = By.xpath(String.format("//li[@data-test-id='place-card-%s']", cityCode));

        click(button);
    }


}
