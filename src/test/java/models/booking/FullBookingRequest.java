package models.booking;

import lombok.Data;

@Data
public class FullBookingRequest {
    String firstname, lastname, additionalneeds;
    int totalprice;
    boolean depositpaid;
    BookingDates bookingdates;
}
