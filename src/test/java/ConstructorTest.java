import driver.WebDriverCreator;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.StartPage;

import static org.junit.Assert.assertTrue;
public class ConstructorTest {
    private StartPage objStartPage;
    private WebDriver driver;
    String accessToken;

    @Before
    public void before() {
        driver = WebDriverCreator.createWebDriver();
        objStartPage = new StartPage(driver);
    }

    @Test
    @DisplayName("Open tab Sauce")
    public void checkSauce() {
        objStartPage.openStartPage();
        assertTrue("Ошибка", objStartPage.checkSauce());
    }

    @Test
    @DisplayName("Open tab Buns")
    public void checkBuns() {
        objStartPage.openStartPage();
        assertTrue("Ошибка", objStartPage.checkBuns());
    }

    @Test
    @DisplayName("Open tab Fillings")
    public void checkFillings() {
        objStartPage.openStartPage();
        assertTrue("Ошибка", objStartPage.checkFillings());
    }

    @After
    public void teardown() {
        if (accessToken != null) {
            UserData.deleteUser(accessToken);
        }
        driver.quit();
    }
}