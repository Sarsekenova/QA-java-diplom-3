import driver.WebDriverCreator;
import io.qameta.allure.junit4.DisplayName;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.RegisterPage;
import pageobject.StartPage;

import java.time.Duration;

import static org.junit.Assert.assertEquals;

public class RegisterTest {
    private RegisterPage objRegisterPage;
    private WebDriver driver;
    private String name;
    private String email;
    private String password;
    String accessToken;


    @Before
    public void before() {
        driver = WebDriverCreator.createWebDriver();

        UserData userData = new UserData();
        name = userData.getRandomName();
        email = userData.getRandomEmail();
        password = userData.getRandomPassword();
        objRegisterPage = new RegisterPage(driver);
    }

    @Test
    @DisplayName("Registration")
    public void checkRegistrationTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        objRegisterPage.openRegisterPage();
        objRegisterPage.createUser(name,email,password);
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.login(email, password);
        StartPage objStartPage = new StartPage(driver);
        assertEquals("Error", "Войти", objStartPage.checkOrderButton());
    }

    @Test
    @DisplayName("Registration - error")
    public void checkErrorMassageTest() {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(15));
        objRegisterPage.openRegisterPage();
        objRegisterPage.createUser(name,email,"12345");
        objRegisterPage.getError();
        assertEquals("Error", "Некорректный пароль", objRegisterPage.getTextErrorMessage());
    }

    @After
    public void teardown() {
        if (accessToken != null) {
            UserData.deleteUser(accessToken);
        }
        driver.quit();
    }
}