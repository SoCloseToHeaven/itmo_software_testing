package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class SettingsPage extends AbstractPage {

    public static final String BASE_URL = "https://www.aviasales.ru/my/settings";

    private final By darkThemeButton = By.xpath("//span[contains(text(), 'Тёмная')]");
    private final By loginViaVk = By.xpath("//div[contains(text(), 'Вы вошли через VK ID')]");

    public SettingsPage(WebDriver webDriver) {
        super(webDriver);
    }

    @Override
    public Page open() {
        driver.get(BASE_URL);

        click(acceptCookiesButton);

        return this;
    }

    @Override
    public boolean isLoaded() {
        return isElementDisplayed(darkThemeButton);
    }

    public void darkTheme() {
        click(darkThemeButton);
    }

    public void checkIfAuthorizedViaVK() {
        waitForElement(loginViaVk);
    }
}
