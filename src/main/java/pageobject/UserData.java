import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserData {
    private final String email = "email" + new Random().nextInt(10000) + "@yandex.ru";
    private final String password = "123456" + new Random().nextInt(10000);
    private final String name = "user" + new Random().nextInt(10000);

    public String getRandomName() {
        return this.name;
    }

    public  String getRandomEmail() {
        return this.email;
    }

    public  String getRandomPassword() {
        return this.password;
    }

    public static RequestSpecification baseSpecification(){

        return new RequestSpecBuilder()
                .setContentType(ContentType.JSON)
                .setBaseUri("https://stellarburgers.nomoreparties.site/api")
                .addFilter(new AllureRestAssured())
                .build();
    }

    public static final String USER = "/api/auth/user";
    @Step("Удаление пользователя.")
    public static void deleteUser(String accessToken) {
        if (accessToken == null) {
            return;
        }
        given()
                .header("Authorization", accessToken)
                .spec(baseSpecification())
                .when()
                .delete(USER)
                .then();

    }
}