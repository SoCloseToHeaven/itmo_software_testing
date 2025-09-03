package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class FavouritesPage extends AbstractPage {

    public static final String BASE_URL = "https://www.aviasales.ru/favorites";

    private final By likeButton = By.xpath("/html/body/div[7]/div/div[2]/div[4]/div[1]/div[1]/div[2]/div[2]");

    private final By toMainPageButton = By.xpath("//div[contains(text(), 'К поиску')]/parent::*");

    public FavouritesPage(WebDriver driver) {
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
        return isElementDisplayed(likeButton);
    }

    public void like() {
        click(likeButton);
    }

    public MainPage navigateToToMainPage() {
        click(toMainPageButton);

        return new MainPage(driver);
    }
}
