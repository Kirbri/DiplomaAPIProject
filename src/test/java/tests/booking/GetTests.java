package tests.booking;

import api.CreateBooking;
import models.booking.BookingResponse;
import models.booking.BookingidResponse;
import models.booking.FullBookingRequest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import tests.TestBase;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static specs.GeneralSpec.*;

@DisplayName("Проверка получения идентификаторов всех бронирований, которые существуют в API или по определённым критериям")
@Tag("booking_api")
public class GetTests extends TestBase {
    final CreateBooking createBooking = new CreateBooking();

    @Test
    @DisplayName("Получение всех действительных бронирований")
    public void successfulGetBookingIdsWithoutAuthTest() {
        BookingidResponse[] responseData = step("Отправить запрос на получение бронирований", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .get("/booking")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(BookingidResponse[].class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData[0].getBookingid()).isGreaterThan(0);
            assertThat(responseData.length).isGreaterThan(100);
        });
    }

    @Test
    @DisplayName("Получение всех действительных бронирований по фамилии и имени c 0 результатом")
    public void successfulGetBookingIdsWithFilterNameThenEmptyResultsWithoutAuthTest() {
        BookingidResponse[] responseData = step("Отправить запрос на получение бронирований по имени", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .queryParam("firstname", "sallyцуй23123ё!;%")
                        .queryParam("lastname", "b3472688%^#$!rown")
                        .get("/booking")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(BookingidResponse[].class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.length).isEqualTo(0);
        });
    }

    @Test
    @DisplayName("Получение всех действительных бронирований по фамилии и имени с результатом")
    public void successfulGetBookingIdsWithFilterNameThenNotEmptyResultsWithoutAuthTest() {
        BookingResponse testData = createBooking.successFullDataCreateBooking();
        BookingidResponse[] responseData = step("Отправить запрос на получение бронирований по имени", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .queryParam("firstname", testData.getBooking().getFirstname())
                        .queryParam("lastname", testData.getBooking().getLastname())
                        .get("/booking")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(BookingidResponse[].class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.length).isGreaterThan(0);
            List<Integer> bookingIds = Arrays.stream(responseData)
                    .map(BookingidResponse::getBookingid)
                    .collect(Collectors.toList());
            assertThat(bookingIds).containsAnyOf(testData.getBookingid());
        });
    }

    @Test
    @DisplayName("Получение всех действительных бронирований по дате заселения и выезда с результатом")
    public void successfulGetBookingIdsWithFilterCheckinCheckoutThenNotEmptyResultsWithoutAuthTest() {
        BookingidResponse[] responseData = step("Отправить запрос на получение бронирований по дате заезда и выезда", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .queryParam("checkin", "2014-01-13")
                        .queryParam("checkout", "3014-12-31")
                        .get("/booking")
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(BookingidResponse[].class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.length).isGreaterThan(100);
            assertThat(responseData[0].getBookingid()).isNotZero();
        });
    }

    @Test
    @DisplayName("Получение всех действительных бронирований по дате заселения и выезда с ошибочной датой")
    public void unsuccessfulGetBookingIdsWithFilterCheckinCheckoutError500WithoutAuthTest() {
        step("Отправить запрос на получение бронирований по дате заезда и выезда и проверка кода ответа", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .queryParam("checkin", "2014-00-13")
                        .queryParam("checkout", "3014-12-31")
                        .get("/booking")
                        .then()
                        .spec(responseSpecification500));
    }

    @Test
    @DisplayName("Получение всех действительных бронирований с недействительным ID")
    public void unsuccessfulGetBookingById404WithoutAuthTest() {
        step("Отправить запрос на получение конкретного бронирования по идентификатору бронирования и проверка кода ответа", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .get("/booking/" + 0)
                        .then()
                        .spec(responseSpecification404));
    }

    @Test
    @DisplayName("Получение информации о бронировании по действительному ID")
    public void successfulGetBookingByIdResult1WithoutAuthTest() {
        BookingResponse testData = createBooking.successFullDataCreateBooking();

        FullBookingRequest responseData = step("Отправить запрос на получение конкретного бронирования по идентификатору бронирования", () ->
                given(requestSpecificationWithoutAuth)
                        .when()
                        .get("/booking/" + testData.getBookingid())
                        .then()
                        .spec(responseSpecification200)
                        .extract().as(FullBookingRequest.class));

        step("Проверить данные в ответе", () -> {
            assertThat(responseData.getFirstname()).isEqualTo(testData.getBooking().getFirstname());
            assertThat(responseData.getLastname()).isEqualTo(testData.getBooking().getLastname());
            assertThat(responseData.getTotalprice()).isEqualTo(testData.getBooking().getTotalprice());
            assertThat(responseData.getBookingdates().getCheckin()).isEqualTo(testData.getBooking().getBookingdates().getCheckin());
            assertThat(responseData.getBookingdates().getCheckout()).isEqualTo(testData.getBooking().getBookingdates().getCheckout());
            assertThat(responseData.isDepositpaid()).isEqualTo(testData.getBooking().isDepositpaid());
            assertThat(responseData.getAdditionalneeds()).isEqualTo(testData.getBooking().getAdditionalneeds());
        });
    }
}