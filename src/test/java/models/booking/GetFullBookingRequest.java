package models.booking;

import lombok.Data;

@Data
public class GetFullBookingRequest {
    String firstname, lastname, additionalneeds;
    int totalprice;
    boolean depositpaid;
    BookingDates bookingdates;
}
