package tests.booking;

import api.CreateAuth;
import api.CreateBooking;
import models.auth.TokenResponse;
import models.booking.BookingResponse;
import models.booking.FullBookingRequest;
import models.booking.FullBookingWithoutAdditionalRequest;
import models.booking.WithoutNameAndLastnameBookingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.GeneralSpec.*;

@DisplayName("Обновление текущего бронирования с частичной полезной нагрузкой")
@Tag("booking_api")
public class PatchTests extends TestBase {
    final CreateBooking createBooking = new CreateBooking();
    final CreateAuth createAuth = new CreateAuth();

    @Test
    @DisplayName("Обновление полных данных при базовой авторизации, по существующему ID")
    public void successfulUpdateBookingByIdWithBasicAuthTest() {
        BookingResponse testDataResponse = createBooking.successFullDataCreateBooking();
        FullBookingRequest testDataRequest = createBooking.getFullDataForCreateBooking();

        FullBookingRequest responseData = step("Отправить запрос на обновление данных по бронированию", () ->
                given(requestSpecificationWithBasicAuth)
                        .body(testDataRequest)
                        .when()
                        .patch("/booking/" + testDataResponse.getBookingid())
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
    @DisplayName("Обновление частичных данных при авторизации по токену, по существующему ID")
    public void successfulUpdateBookingByIdWithTokenTest() {
        BookingResponse testDataResponse = createBooking.successFullDataCreateBooking();
        WithoutNameAndLastnameBookingRequest testDataRequest = createBooking.successDataForCreateBookingWithoutNameAndLastname();
        TokenResponse testToken = createAuth.successfulCreateAuth();

        FullBookingRequest responseData = step("Отправить запрос на обновление данных по бронированию", () ->
                given(requestSpecificationWithoutAuth)
                        .header("Cookie", "token=" + testToken.getToken())
                        .body(testDataRequest)
                        .when()
                        .patch("/booking/" + testDataResponse.getBookingid())
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(FullBookingRequest.class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.getFirstname()).isEqualTo(testDataResponse.getBooking().getFirstname());
            assertThat(responseData.getLastname()).isEqualTo(testDataResponse.getBooking().getLastname());
            assertThat(responseData.getTotalprice()).isEqualTo(testDataRequest.getTotalprice());
            assertThat(responseData.getBookingdates().getCheckin()).isEqualTo(testDataRequest.getBookingdates().getCheckin());
            assertThat(responseData.getBookingdates().getCheckout()).isEqualTo(testDataRequest.getBookingdates().getCheckout());
            assertThat(responseData.isDepositpaid()).isEqualTo(testDataRequest.isDepositpaid());
            assertThat(responseData.getAdditionalneeds()).isEqualTo(testDataRequest.getAdditionalneeds());
        });
    }

    @Test
    @DisplayName("Обновление строковых данных со значением null при базовой авторизации по существующему ID")
    public void successfulUpdateEmptyDataBookingByIdWithAuthTest() {
        BookingResponse testDataResponse = createBooking.successFullDataCreateBooking();
        FullBookingRequest testDataRequest = new FullBookingRequest();

        step("Отправить запрос на обновление данных по бронированию", () ->
                given(requestSpecificationWithBasicAuth)
                        .body(testDataRequest)
                        .when()
                        .patch("/booking/" + testDataResponse.getBookingid())
                        .then()
                        .spec(responseSpecification200WithExpectBody));
    }

    @Test
    @DisplayName("Обновление частичных данных без авторизации, по существующему ID")
    public void successfulUpdateBookingByIdWithoutAuthTest() {
        BookingResponse testDataResponse = createBooking.successFullDataCreateBooking();
        FullBookingWithoutAdditionalRequest testDataRequest = createBooking.successDataForCreateBookingWithoutAdditionalNeeds();

        step("Отправить запрос на обновление данных по бронированию", () ->
                given(requestSpecificationWithoutAuth)
                        .body(testDataRequest)
                        .when()
                        .patch("/booking/" + testDataResponse.getBookingid())
                        .then()
                        .spec(responseSpecification403));
    }

    @Test
    @DisplayName("Неудачное обновление бронирования при базовой авторизации, с некорректным ID")
    public void unsuccessfulUpdateBookingById405Test() {
        FullBookingRequest testData = createBooking.getFullDataForCreateBooking();

        step("Отправить запрос на удаление конкретного бронирования по идентификатору бронирования", () ->
                given(requestSpecificationWithBasicAuth)
                        .body(testData)
                        .when()
                        .patch("/booking/" + 0)
                        .then()
                        .spec(responseSpecification405));
    }
}