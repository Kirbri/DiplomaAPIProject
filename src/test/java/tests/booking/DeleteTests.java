package tests.booking;

import api.CreateAuth;
import api.CreateBooking;
import models.auth.TokenResponse;
import models.booking.BookingResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.*;

@DisplayName("Проверка удаления бронирования по ID")
@Tag("booking_api")
public class DeleteTests extends TestBase {
    final CreateBooking createBooking = new CreateBooking();
    final CreateAuth createAuth = new CreateAuth();

    @Test
    @DisplayName("Удачное удаление бронирования при базовой авторизации и проверкой того, что удалённое бронирование не найдено в системе")
    public void successfulDeleteBookingByIdWithBasicAuthTest() {
        BookingResponse testData = createBooking.successFullDataCreateBooking();
        step("Отправить запрос на удаление конкретного бронирования по идентификатору бронирования", () ->
                given(requestSpecificationWithBasicAuth)
                        .when()
                        .delete("/booking/" + testData.getBookingid())
                        .then()
                        .spec(responseSpecification201));

        step("Отправить запрос на получение информации по конкретному бронированию", () ->
                given(requestSpecificationWithBasicAuth)
                        .when()
                        .get("/booking/" + testData.getBookingid())
                        .then()
                        .spec(responseSpecification404));
    }

    @Test
    @DisplayName("Удачное удаление бронирования при авторизации по токену и проверкой того, что удалённое бронирование не найдено в системе")
    public void successfulDeleteBookingByIdWithTokenTest() {
        BookingResponse testData = createBooking.successFullDataCreateBooking();
        TokenResponse testToken = createAuth.successfulCreateAuth();

        step("Отправить запрос на удаление конкретного бронирования по идентификатору бронирования", () ->
                given(requestSpecificationWithoutAuth)
                        .header("Cookie", "token=" + testToken.getToken())
                        .when()
                        .delete("/booking/" + testData.getBookingid())
                        .then()
                        .spec(responseSpecification201));

        step("Отправить запрос на получение информации по конкретному бронированию", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .get("/booking/" + testData.getBookingid())
                        .then()
                        .spec(responseSpecification404));
    }

    @Test
    @DisplayName("Неудачное удаление бронирования при отправке запроса без передачи данных по авторизации или токена")
    public void unsuccessfulDeleteBookingByIdWithoutAuth403Test() {
        BookingResponse testData = createBooking.successFullDataCreateBooking();
        step("Отправить запрос на удаление конкретного бронирования по идентификатору бронирования", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .delete("/booking/" + testData.getBookingid())
                        .then()
                        .spec(responseSpecification403));
    }

    @Test
    @DisplayName("Неудачное удаление бронирования при отправке запроса при базовой авторизации и недействительным ID")
    public void unsuccessfulDeleteBookingByIdWithBasicAuth405Test() {
        step("Отправить запрос на удаление конкретного бронирования по идентификатору бронирования", () ->
                given(requestSpecificationWithBasicAuth)
                        .when()
                        .delete("/booking/" + -1)
                        .then()
                        .spec(responseSpecification405));
    }

    @Test
    @DisplayName("Неудачное удаление бронирования при отправке запроса при авторизации по токену и недействительным ID")
    public void unsuccessfulDeleteBookingByIdWithToken405Test() {
        TokenResponse testToken = createAuth.successfulCreateAuth();

        step("Отправить запрос на удаление конкретного бронирования по идентификатору бронирования", () ->
                given(requestSpecificationWithoutAuth)
                        .header("Cookie", "token=" + testToken.getToken())
                        .when()
                        .delete("/booking/" + -1)
                        .then()
                        .spec(responseSpecification405));
    }
}