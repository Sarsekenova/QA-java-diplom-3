import driver.WebDriverCreator;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.ForgotPasswordPage;
import pageobject.LoginPage;
import pageobject.RegisterPage;
import pageobject.StartPage;
import static org.junit.Assert.assertEquals;

public class LoginPageTest {
    private RegisterPage objRegisterPage;
    private LoginPage objLoginPage;
    private WebDriver driver;
    private String email;
    private String password;
    String accessToken;

    @Before
    public void before() {
        driver = WebDriverCreator.createWebDriver();

        UserData userData = new UserData();
        String name = userData.getRandomName();
        email = userData.getRandomEmail();
        password = userData.getRandomPassword();
        objRegisterPage = new RegisterPage(driver);
        objRegisterPage.openRegisterPage();
        objRegisterPage.createUser(name,email,password);
    }

    @Test
    @DisplayName("Login to personal account on the start page")
    public void startPageTest() {
        StartPage objStartPage = new StartPage(driver);
        objStartPage.openStartPage();
        objStartPage.checkAuthorization();
        objLoginPage = new LoginPage(driver);
        objLoginPage.login(email, password);
        assertEquals("Ошибка", "Войти", objStartPage.checkOrderButton());
    }
    @Test
    @DisplayName("Personal account on the start page")
    public void personalAccountTest() {
        StartPage objStartPage = new StartPage(driver);
        objStartPage.openStartPage();
        objStartPage.checkPersonalArea();
        objLoginPage = new LoginPage(driver);
        objLoginPage.login(email, password);
        assertEquals("Ошибка", "Войти", objStartPage.checkOrderButton());
    }

    @Test
    @DisplayName("User login via the link on the registration page")
    public void registrationLinkTest() {
        objRegisterPage.openRegisterPage();
        objRegisterPage.clickAuthLinkLogin();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.login(email, password);
        StartPage objStartPage = new StartPage(driver);
        assertEquals("Ошибка", "Войти", objStartPage.checkOrderButton());
    }

    @Test
    @DisplayName("Login on the password reset page")
    public void restorePasswordLinkTest() {
        ForgotPasswordPage objForgotPasswordPage = new ForgotPasswordPage(driver);
        objForgotPasswordPage.openRestorePage();
        objForgotPasswordPage.clickForgotPassword();
        LoginPage objLoginPage = new LoginPage(driver);
        objLoginPage.login(email,password);
        StartPage objStartPage = new StartPage(driver);
        assertEquals("Ошибка", "Войти", objStartPage.checkOrderButton());
    }
    @After
    public void teardown() {
        if (accessToken != null) {
            UserData.deleteUser(accessToken);
        }
        driver.quit();
    }
}