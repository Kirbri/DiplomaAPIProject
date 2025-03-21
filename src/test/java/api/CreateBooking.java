package api;

import io.qameta.allure.Step;
import models.booking.*;

import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.requestSpecificationWithBasicAuth;
import static specs.GeneralSpec.responseSpecification200;

public class CreateBooking {

    @Step("Создание бронирования со всеми заполненными полями")
    public BookingResponse successFullDataCreateBooking() {
        BookingDates bookingDates = new BookingDates("3025-12-31", "5000-01-01");

        return given(requestSpecificationWithBasicAuth)
                .body(FullBookingRequest.builder()
                        .firstname("Jimbo")
                        .lastname("Bird")
                        .totalprice(9_548_347)
                        .depositpaid(false)
                        .bookingdates(bookingDates)
                        .additionalneeds("Breakfast, diner").build())
                .when()
                .post("/booking")
                .then()
                .spec(responseSpecification200)
                .extract().as(BookingResponse.class);
    }

    @Step("Данные для создания бронирования со всеми заполненными полями")
    public FullBookingRequest getFullDataForCreateBooking() {
        BookingDates bookingDates = new BookingDates("1025-01-01", "1050-12-31");

        return FullBookingRequest.builder()
                .firstname("Update_Jimbo")
                .lastname("Update_Bird")
                .totalprice(8_457_923)
                .depositpaid(true)
                .bookingdates(bookingDates)
                .additionalneeds("No").build();
    }

    @Step("Данные для создания бронирования без полей дополнительных потребностей")
    public FullBookingWithoutAdditionalRequest successDataForCreateBookingWithoutAdditionalNeeds() {
        BookingDates bookingDates = new BookingDates("1025-01-01", "1050-12-31");

        return FullBookingWithoutAdditionalRequest.builder()
                .firstname("UpdatePath")
                .lastname("UpdatePart")
                .totalprice(5660)
                .depositpaid(false)
                .bookingdates(bookingDates)
                .build();
    }

    @Step("Данные для создания бронирования без имени и фамилии")
    public WithoutNameAndLastnameBookingRequest successDataForCreateBookingWithoutNameAndLastname() {
        BookingDates bookingDates = new BookingDates("2025-08-12", "2050-11-09");

        return WithoutNameAndLastnameBookingRequest.builder()
                .totalprice(5660)
                .depositpaid(false)
                .bookingdates(bookingDates)
                .additionalneeds("NO")
                .build();
    }
}