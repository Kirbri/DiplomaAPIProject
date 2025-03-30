package tests.booking;

import api.CreateAuth;
import api.CreateBooking;
import models.auth.TokenResponse;
import models.booking.BookingResponse;
import models.booking.FullBookingRequest;
import models.booking.FullBookingWithoutAdditionalRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.GeneralSpec.*;

@DisplayName("Обновление текущего бронирования по ID")
@Tag("booking_api")
public class PutTests extends TestBase {
    final CreateBooking createBooking = new CreateBooking();
    final CreateAuth createAuth = new CreateAuth();

    @Test
    @DisplayName("Удачное обновление всех данных бронирования при базовой авторизации, с полными данными")
    public void successfulUpdateBookingByIdWithBasicAuthTest() {
        BookingResponse testDataResponse = createBooking.successFullDataCreateBooking();
        FullBookingRequest testDataRequest = createBooking.getFullDataForCreateBooking();

        FullBookingRequest responseData = step("Отправить запрос на обновление данных по бронированию", () ->
                given(requestSpecificationWithBasicAuth)
                        .body(testDataRequest)
                        .when()
                        .put("/booking/" + testDataResponse.getBookingid())
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(FullBookingRequest.class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.getFirstname()).isEqualTo(testDataRequest.getFirstname());
            assertThat(responseData.getLastname()).isEqualTo(testDataRequest.getLastname());
            assertThat(responseData.getTotalprice()).isEqualTo(testDataRequest.getTotalprice());
            assertThat(responseData.getBookingdates().getCheckin()).isEqualTo(testDataRequest.getBookingdates().getCheckin());
            assertThat(responseData.getBookingdates().getCheckout()).isEqualTo(testDataRequest.getBookingdates().getCheckout());
            assertThat(responseData.isDepositpaid()).isEqualTo(testDataRequest.isDepositpaid());
            assertThat(responseData.getAdditionalneeds()).isEqualTo(testDataRequest.getAdditionalneeds());
        });
    }

    @Test
    @DisplayName("Удачное обновление части данных бронирования при авторизации по токену")
    public void successfulUpdateBookingByIdWithTokenTest() {
        BookingResponse testDataResponse = createBooking.successFullDataCreateBooking();
        FullBookingWithoutAdditionalRequest testDataRequest = createBooking.successDataForCreateBookingWithoutAdditionalNeeds();
        TokenResponse testToken = createAuth.successfulCreateAuth();

        FullBookingRequest responseData = step("Отправить запрос на обновление данных по бронированию", () ->
                given(requestSpecificationWithoutAuth)
                        .header("Cookie", "token=" + testToken.getToken())
                        .body(testDataRequest)
                        .when()
                        .put("/booking/" + testDataResponse.getBookingid())
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(FullBookingRequest.class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.getFirstname()).isEqualTo(testDataRequest.getFirstname());
            assertThat(responseData.getLastname()).isEqualTo(testDataRequest.getLastname());
            assertThat(responseData.getTotalprice()).isEqualTo(testDataRequest.getTotalprice());
            assertThat(responseData.getBookingdates().getCheckin()).isEqualTo(testDataRequest.getBookingdates().getCheckin());
            assertThat(responseData.getBookingdates().getCheckout()).isEqualTo(testDataRequest.getBookingdates().getCheckout());
            assertThat(responseData.isDepositpaid()).isEqualTo(testDataRequest.isDepositpaid());
            assertThat(responseData.getAdditionalneeds()).isEqualTo(testDataResponse.getBooking().getAdditionalneeds());
        });
    }

    @Test
    @DisplayName("Неудачное обновление бронирования при базовой авторизации, с некорректным ID")
    public void unsuccessfulUpdateBookingById405Test() {
        FullBookingRequest testData = createBooking.getFullDataForCreateBooking();

        step("Отправить запрос на удаление конкретного бронирования по идентификатору бронирования", () ->
                given(requestSpecificationWithBasicAuth)
                        .body(testData)
                        .when()
                        .put("/booking/" + -1)
                        .then()
                        .spec(responseSpecification405));
    }

    @Test
    @DisplayName("Неудачное обновление бронирования без авторизации, с актуальным ID")
    public void unsuccessfulUpdateBookingById403Test() {
        FullBookingRequest testData = createBooking.getFullDataForCreateBooking();
        BookingResponse testDataResponse = createBooking.successFullDataCreateBooking();

        step("Отправить запрос на удаление конкретного бронирования по идентификатору бронирования", () ->
                given(requestSpecificationWithoutAuth)
                        .body(testData)
                        .when()
                        .put("/booking/" + testDataResponse.getBookingid())
                        .then()
                        .spec(responseSpecification403));
    }
}