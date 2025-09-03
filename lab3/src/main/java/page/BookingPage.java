package page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;

public class BookingPage extends AbstractPage {

    public static final String BASE_URL = "https://www.aviasales.ru/hotels";

    private final By bookingCityInput = By.xpath("//input[@data-test-id='hotel-autocomplete-input']");

    private final By whenToGo = By.xpath("//button[@data-test-id='start-date-field']");
    private final By whenToComeBack = By.xpath("//button[@data-test-id='end-date-field']");

    private final By submitButton = By.xpath("//button[@data-test-id='form-submit']");

    public BookingPage(WebDriver driver) {
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
        return isElementDisplayed(bookingCityInput) && isElementDisplayed(whenToGo) && isElementDisplayed(whenToComeBack);
    }

    public void searchHotels(String city, String date1, String date2) {
        selectCity(city, bookingCityInput);

        click(whenToGo);

        By toGoButton = By.xpath(String.format("//div[@data-test-id='date-%s']", date1));

        click(toGoButton);

        click(whenToComeBack);

        By toComeBackButton = By.xpath(String.format("//div[@data-test-id='date-%s']", date2));

        waitForElement(toComeBackButton).click();

        click(submitButton);
    }

    private void selectDate(By locator, String date) {
        click(locator);

        By toGoButton = By.xpath(String.format("//div[@data-test-id='date-%s']", date));

        click(toGoButton);
    }

    private void selectCity(String city, By locator) {
        stealthType(locator, city);
    }
}
