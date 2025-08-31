package page;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

public abstract class AbstractPage implements Page {

    public static final String BASE_URI = "https://www.holodilnik.ru";

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    public AbstractPage(WebDriver webDriver) {
        this.driver = webDriver;
        wait = new WebDriverWait(webDriver, Duration.ofSeconds(10));
    }

    protected void click(By locator) {
        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    protected WebElement waitForElement(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected List<WebElement> waitForElements(By locator) {
        return wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(locator));
    }

    protected void type(By locator, String text) {
        var inputElement = waitForElement(locator);
        inputElement.sendKeys(text);
    }

    protected void hoverOverElement(By locator) {
        var element = wait.until((driver) ->
            driver.findElement(locator)
        );

        new Actions(driver).moveToElement(element).perform();
    }

    public void hoverOverWebElement(WebElement element) {
        new Actions(driver).moveToElement(element).perform();
    }

    public boolean isElementDisplayed(By locator) {
        try {
            return wait.until((driver) ->
                    driver.findElement(locator).isDisplayed()
            );
        } catch (RuntimeException e) {
            return false;
        }
    }
}
