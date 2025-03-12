package models.booking;

import lombok.Data;

@Data
public class GetWithoutNameAndLastnameBookingRequest {
    int totalprice;
    boolean depositpaid;
    BookingDates bookingdates;
    String additionalneeds;
}
