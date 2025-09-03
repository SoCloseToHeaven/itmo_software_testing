package page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class MainPage extends AbstractPage {

    public static final String PAGE_URL = "https://www.aviasales.ru/";


    // Cookies
    private final By acceptCookiesButton = By.xpath("//button[@data-test-id='accept-cookies-button']");
    // Search bar elements
    private final By fromCity = By.xpath("//input[@id='avia_form_origin-input']");
    private final By toCity = By.xpath("//input[@id='avia_form_destination-input']");

    private final By whenToGo = By.xpath("//button[@data-test-id='start-date-field']");
    private final By whenToComeBack = By.xpath("//button[@data-test-id='end-date-field']");

    private final By submitButton = By.xpath("//button[@data-test-id='form-submit']");

    private final By ostrovokCheckbox = By.xpath("//label[@data-test-id='checkbox']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public Page open() {
        driver.get(PAGE_URL);

        click(acceptCookiesButton);

        click(ostrovokCheckbox);

        return this;
    }

    @Override
    public boolean isLoaded() {
        return isElementDisplayed(fromCity) &&
                isElementDisplayed(toCity) &&
                isElementDisplayed(whenToGo) &&
                isElementDisplayed(whenToComeBack);
    }

    public SearchPage searchTickets(String fromCityText,
                                    String toCityText,
                                    String whenToGoText,
                                    String whenToComeBackText) {

        selectCity(fromCityText, fromCity);
        selectCity(toCityText, toCity);

        click(whenToGo);

        By toGoButton = By.xpath(String.format("//div[@data-test-id='date-%s']", whenToGoText));

        click(toGoButton);

        click(whenToComeBack);

        By toComeBackButton = By.xpath(String.format("//div[@data-test-id='date-%s']", whenToComeBackText));

        waitForElement(toComeBackButton).click();

        click(submitButton);

        return new SearchPage(driver);
    }

    private void selectCity(String city, By locator) {
        type(locator, city);
        try { // I see no other ways how to fill autocomplete input
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}
        waitForElement(locator).sendKeys(Keys.TAB);
    }
}
