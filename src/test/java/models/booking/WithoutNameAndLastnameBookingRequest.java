package models.booking;

import lombok.Data;

@Data
public class WithoutNameAndLastnameBookingRequest {
    int totalprice;
    boolean depositpaid;
    BookingDates bookingdates;
    String additionalneeds;
}
