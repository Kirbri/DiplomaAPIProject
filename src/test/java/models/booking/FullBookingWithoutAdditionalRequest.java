package models.booking;

import lombok.Data;

@Data
public class FullBookingWithoutAdditionalRequest {
    String firstname, lastname;
    int totalprice;
    boolean depositpaid;
    BookingDates bookingdates;
}
