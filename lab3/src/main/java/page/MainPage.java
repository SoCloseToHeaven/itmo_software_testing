package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class MainPage extends AbstractPage {

    public static final String PAGE_URL = "https://www.aviasales.ru/";


    // Cookies
    private final By acceptCookiesButton = By.xpath("//button[@data-test-id='accept-cookies-button']");

    // Search bar elements


    // Personal info elements


    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Override
    public Page open() {
        driver.get(PAGE_URL);

        click(acceptCookiesButton);

        return this;
    }

    @Override
    public boolean isLoaded() {
        return true;
    }
}
