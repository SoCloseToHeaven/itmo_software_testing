package driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.util.function.Supplier;

public enum BrowserDriver {

    FIREFOX(
            () -> {
                WebDriver driver = new FirefoxDriver(
                    new FirefoxOptions()
                );

                driver.manage().window().maximize();

                return driver;
            }
    ),
    CHROME(
            () -> new ChromeDriver(
                    new ChromeOptions()
                            .addArguments("--start-maximized")
            )
    );

    private final Supplier<WebDriver> webDriverSupplier;

    BrowserDriver(Supplier<WebDriver> webDriverSupplier) {
        this.webDriverSupplier = webDriverSupplier;
    }

    public WebDriver apply() {
        return webDriverSupplier.get();
    }
}
