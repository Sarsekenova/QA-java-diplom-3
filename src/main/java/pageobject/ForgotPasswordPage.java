package pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class ForgotPasswordPage {

    private final WebDriver driver;
    public ForgotPasswordPage(WebDriver driver) {
        this.driver = driver;
    }
    private final static String PasswordRecoveryPage = "https://stellarburgers.nomoreparties.site/login";

    private final By restorePasswordButton = By.xpath(".//a[text()='Восстановить пароль']"); //кнопка "Восстановить пароль"

    private final By loginButton = By.xpath(".//a[@class='Auth_link__1fOlj']"); //кнопка-ссылка "Войти" на странице "Восстановление пароля"
    @Step("Открыть страницу <Восстановление пароля>")
    public void openRestorePage (){  driver.get(PasswordRecoveryPage);  }

    @Step("Выполнить вход по кнопке <Войти> в форме восстановления пароля")
    public void clickForgotPassword (){
        new WebDriverWait(driver, Duration.ofSeconds(5))
                .until(ExpectedConditions.elementToBeClickable(restorePasswordButton));
        driver.findElement(restorePasswordButton).click();
        driver.findElement(loginButton).click();
    }
}