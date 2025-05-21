import driver.WebDriverCreator;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.WebDriver;
import pageobject.LoginPage;
import pageobject.ProfilePage;
import pageobject.RegisterPage;
import pageobject.StartPage;

import static org.junit.Assert.assertEquals;

public class UserTest {
    private LoginPage objLoginPage;
    private StartPage objStartPage;
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
        RegisterPage objRegisterPage = new RegisterPage(driver);
        objRegisterPage.openRegisterPage();
        objRegisterPage.createUser(name,email,password);
        objLoginPage = new LoginPage(driver);
        objStartPage = new StartPage(driver);
    }

    @Test
    @DisplayName("Login to personal account")
    public void personalAccountTest() {
        objLoginPage.login(email, password);
        objStartPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        assertEquals("Entering was  Failed", "Выход", objProfilePage.checkLogInPersonalAccount());
    }

    @Test
    @DisplayName("Exit personal account")
    public void checkExitTest() {
        objLoginPage.login(email, password);
        objStartPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        objProfilePage.clickExitButton();
        assertEquals("ExitFailed", "Войти", objLoginPage.checkLoginButton());
    }

    @Test
    @DisplayName("Exit to start page from personal account")
    public void checkLogoTest() {
        objLoginPage.login(email, password);
        objStartPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        objProfilePage.clickLogoButton();
        assertEquals("LogoButtonFailed", "Оформить заказ", objStartPage.checkOrderButton());
    }

    @Test
    @DisplayName("Exit from your personal account to constructor")
    public void checkConstructorTest() {
        objLoginPage.login(email, password);
        objStartPage.checkPersonalArea();
        ProfilePage objProfilePage = new ProfilePage(driver);
        objProfilePage.clickConstructorButton();
        assertEquals("ConstructorButtonFailed", "Оформить заказ", objStartPage.checkOrderButton());
    }
    @After
    public void teardown() {
        if (accessToken != null) {
            UserData.deleteUser(accessToken);
        }
        driver.quit();
    }
}