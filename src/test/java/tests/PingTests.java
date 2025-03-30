package tests;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.requestSpecificationWithoutAuth;
import static specs.GeneralSpec.responseSpecification201;

@DisplayName("Проверка работоспособности API")
@Tag("ping_api")
public class PingTests extends TestBase {

    @Test
    @DisplayName("Удачный запрос на проверку работоспособности API")
    public void successfulPingTest() {
        step("Отправить запрос на проверку работоспособности API и проверить возвращаемый код ответа", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .get("/ping")
                        .then()
                        .spec(responseSpecification201));
    }
}
