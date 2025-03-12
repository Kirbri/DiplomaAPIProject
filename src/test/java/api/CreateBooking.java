package api;

import io.qameta.allure.Step;
import models.booking.*;

import static io.restassured.RestAssured.given;
import static specs.GeneralSpec.requestSpecificationWithBasicAuth;
import static specs.GeneralSpec.responseSpecification200;

public class CreateBooking {

    @Step("Создание бронирования со всеми заполненными полями")
    public GetBookingResponse successFullDataCreateBooking() {
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("3025-12-31");
        bookingDates.setCheckout("5000-01-01");

        GetFullBookingRequest data = new GetFullBookingRequest();
        data.setFirstname("Jimbo");
        data.setLastname("Bird");
        data.setTotalprice(9_548_347);
        data.setDepositpaid(false);
        data.setBookingdates(bookingDates);
        data.setAdditionalneeds("Breakfast, diner");

        return given(requestSpecificationWithBasicAuth)
                .body(data)
                .when()
                .post("/booking")
                .then()
                .spec(responseSpecification200)
                .extract().as(GetBookingResponse.class);
    }

    @Step("Данные для создания бронирования со всеми заполненными полями")
    public GetFullBookingRequest getFullDataForCreateBooking() {
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("1025-01-01");
        bookingDates.setCheckout("1050-12-31");

        GetFullBookingRequest data = new GetFullBookingRequest();
        data.setFirstname("Update_Jimbo");
        data.setLastname("Update_Bird");
        data.setTotalprice(8_457_923);
        data.setDepositpaid(true);
        data.setBookingdates(bookingDates);
        data.setAdditionalneeds("No");

        return data;
    }

    @Step("Данные для создания бронирования без полей дополнительных потребностей")
    public GetFullBookingWithoutAdditionalRequest successDataForCreateBookingWithoutAdditionalNeeds() {
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("1025-01-01");
        bookingDates.setCheckout("1050-12-31");

        GetFullBookingWithoutAdditionalRequest data = new GetFullBookingWithoutAdditionalRequest();
        data.setFirstname("UpdatePath");
        data.setLastname("UpdatePart");
        data.setTotalprice(5660);
        data.setDepositpaid(false);
        data.setBookingdates(bookingDates);

        return data;
    }

    @Step("Данные для создания бронирования без имени и фамилии")
    public GetWithoutNameAndLastnameBookingRequest successDataForCreateBookingWithoutNameAndLastname() {
        BookingDates bookingDates = new BookingDates();
        bookingDates.setCheckin("2025-08-12");
        bookingDates.setCheckout("2050-11-09");

        GetWithoutNameAndLastnameBookingRequest data = new GetWithoutNameAndLastnameBookingRequest();
        data.setTotalprice(5660);
        data.setDepositpaid(false);
        data.setBookingdates(bookingDates);
        data.setAdditionalneeds("NO");

        return data;
    }
}