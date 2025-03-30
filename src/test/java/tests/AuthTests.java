package tests;

import data.AuthData;
import models.auth.GenerateTokenLoginRequest;
import models.auth.TokenResponse;
import models.auth.UnsuccessfulTokenResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.GeneralSpec.requestSpecificationWithoutAuth;
import static specs.GeneralSpec.responseSpecification200;

@DisplayName("Создание нового токена аутентификации для использования при вызове запросов PUT, PATCH и DELETE в сервисе бронирования")
@Tag("auth_api")
public class AuthTests extends TestBase {
    AuthData authData = new AuthData();

    @Test
    @DisplayName("Удачное создание токена аутентификации")
    public void successfulCreateTokenTest() {
        GenerateTokenLoginRequest requestData = step("Подготовить данные для создания токена", () -> {
            GenerateTokenLoginRequest data = new GenerateTokenLoginRequest(authData.username, authData.password);

            return data;
        });

        TokenResponse responseData = step("Отправить запрос на создание токена аутентификации", () ->
                given(requestSpecificationWithoutAuth)
                        .body(requestData)
                        .when()
                        .post("/auth")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(TokenResponse.class));

        step("Проверить данных в ответе", () -> {
            assertThat(responseData.getToken()).hasSize(15);
        });
    }

    @Test
    @DisplayName("Неудачное создание токена аутентификации с пустыми логином и паролем")
    public void unsuccessfulCreateTokenBadCredentialsTest() {
        GenerateTokenLoginRequest requestData = step("Подготовить данные для создания токена", () -> {
            GenerateTokenLoginRequest data = new GenerateTokenLoginRequest("", "");

            return data;
        });
        UnsuccessfulTokenResponse responseData = step("Отправить запрос на создание токена аутентификации", () ->
                given(requestSpecificationWithoutAuth)
                        .body(requestData)
                        .when()
                        .post("/auth")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(UnsuccessfulTokenResponse.class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.getReason()).isEqualTo("Bad credentials");
        });
    }
}