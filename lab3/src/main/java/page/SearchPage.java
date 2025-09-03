package page;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class SearchPage extends AbstractPage {

    public static final String PAGE_URL = "https://www.aviasales.ru/search";

    private final By randomTicket = By.xpath("//div[@data-test-id='ticket-preview']");

    private final By multiWayButton = By.xpath("//button[@data-test-id='switch-to-multiwayform']");

    private final By multiCityFrom1 = By.xpath("//input[@id='multiway_form_origin-input' and @tabindex='1']");
    private final By multiCityFrom2 = By.xpath("//input[@id='multiway_form_origin-input' and @tabindex='2']");
    private final By multiCityTo1 = By.xpath("//input[@id='multiway_form_destination-input' and @tabindex='1']");
    private final By multiCityTo2 = By.xpath("//input[@id='multiway_form_destination-input' and @tabindex='2']");

    private final WebDriverWait ticketWait = new WebDriverWait(driver, Duration.ofSeconds(30));

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
        return isElementDisplayed(multiWayButton);
    }

    public void waitForRandomTicket() {
        Actions actions = new Actions(driver);

        actions.scrollToElement(waitForElement(randomTicket));

        click(randomTicket);
    }

    public SearchPage multiSearchTickets(
            String city1,
            String city2,
            String city3,
            String date1,
            String date2
    ) {
        click(multiWayButton);

        type(multiCityFrom1, city1);
        type(multiCityTo1, city2);

        By dateButton1 = By.xpath("//button[@tabindex='1' and @data-test-id='multiway-date']");
        selectDate(dateButton1, date1);

        type(multiCityFrom2, city2);
        type(multiCityTo2, city3);

        By dateButton2 = By.xpath("//button[@tabindex='2' and @data-test-id='multiway-date']");
        selectDate(dateButton2, date2);

        return this;
    }

    private void selectDate(By locator, String date) {
        click(locator);

        By toGoButton = By.xpath(String.format("//div[@data-test-id='date-%s']", date));

        click(toGoButton);
    }

    private void selectCity(String city, By locator) {
        type(locator, city);
        try { // I see no other ways how to fill autocomplete input
            Thread.sleep(1000);
        } catch (InterruptedException ignore) {}
        waitForElement(locator).sendKeys(Keys.TAB);
    }
}
