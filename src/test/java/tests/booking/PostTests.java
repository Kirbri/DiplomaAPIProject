package tests.booking;

import models.booking.BookingDates;
import models.booking.BookingResponse;
import models.booking.FullBookingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.GeneralSpec.*;

@DisplayName("Создание нового бронирование в API")
@Tag("booking_api")
public class PostTests extends TestBase {

    @Test
    @DisplayName("Создание успешного бронирования с полными данными")
    public void successfulPostCreateBookingTest() {
        FullBookingRequest requestData = step("Подготовить данные для создания бронирования", () -> {
            BookingDates bookingDates = new BookingDates("2025-01-01", "2026-01-01");

            return FullBookingRequest.builder()
                    .firstname("Jim")
                    .lastname("Brown")
                    .totalprice(111)
                    .depositpaid(true)
                    .bookingdates(bookingDates)
                    .additionalneeds("Breakfast").build();
        });

        BookingResponse responseData = step("Отправить запрос на создание нового бронирования", () ->
                given(requestSpecificationWithoutAuth)
                        .body(requestData)
                        .when()
                        .post("/booking")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(BookingResponse.class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.getBookingid()).isNotEqualTo(0);
            assertThat(responseData.getBooking().getFirstname()).isEqualTo(requestData.getFirstname());
            assertThat(responseData.getBooking().getLastname()).isEqualTo(requestData.getLastname());
            assertThat(responseData.getBooking().getTotalprice()).isEqualTo(requestData.getTotalprice());
            assertThat(responseData.getBooking().isDepositpaid()).isEqualTo(requestData.isDepositpaid());
            assertThat(responseData.getBooking().getBookingdates().getCheckin()).isEqualTo(requestData.getBookingdates().getCheckin());
            assertThat(responseData.getBooking().getBookingdates().getCheckout()).isEqualTo(requestData.getBookingdates().getCheckout());
            assertThat(responseData.getBooking().getAdditionalneeds()).isEqualTo(requestData.getAdditionalneeds());
        });
    }

    @Test
    @DisplayName("Создание успешного бронирования с пустыми строковыми данными")
    public void successfulPostCreateBookingWithEmptyFieldsTest() {
        FullBookingRequest requestData = step("Подготовить данные для создания бронирования", () -> {
            BookingDates bookingDates = new BookingDates("", "");

            return FullBookingRequest.builder()
                    .firstname("")
                    .lastname("")
                    .bookingdates(bookingDates)
                    .additionalneeds("").build();
        });

        BookingResponse responseData = step("Отправить запрос на создание нового бронирования", () ->
                given(requestSpecificationWithoutAuth)
                        .body(requestData)
                        .when()
                        .post("/booking")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(BookingResponse.class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.getBookingid()).isNotEqualTo(0);
            assertThat(responseData.getBooking().getFirstname()).isEmpty();
            assertThat(responseData.getBooking().getLastname()).isEmpty();
            assertThat(responseData.getBooking().getTotalprice()).isEqualTo(requestData.getTotalprice());
            assertThat(responseData.getBooking().isDepositpaid()).isEqualTo(requestData.isDepositpaid());
            assertThat(responseData.getBooking().getBookingdates().getCheckin()).isEqualTo("0NaN-aN-aN");
            assertThat(responseData.getBooking().getBookingdates().getCheckout()).isEqualTo("0NaN-aN-aN");
            assertThat(responseData.getBooking().getAdditionalneeds()).isEmpty();
        });
    }

    @Test
    @DisplayName("Создание неуспешного бронирования со строковыми данными равными null")
    public void unsuccessfulPostCreateBooking500Test() {
        FullBookingRequest requestData = step("Подготовить данные для создания бронирования", () -> {
            BookingDates bookingDates = new BookingDates();

            return FullBookingRequest.builder()
                    .bookingdates(bookingDates).build();
        });

        step("Отправить запрос на создание нового бронирования и проверить код ответа", () ->
                given(requestSpecificationWithoutAuth)
                        .body(requestData)
                        .when()
                        .post("/booking")
                        .then()
                        .spec(responseSpecification500));
    }
}