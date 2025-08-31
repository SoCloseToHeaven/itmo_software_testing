import driver.BrowserDriver;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;

public class XolodilnikFunctionalTest {

    @ParameterizedTest
    @EnumSource(BrowserDriver.class)
    public void searchBarTest() {

    }
}
