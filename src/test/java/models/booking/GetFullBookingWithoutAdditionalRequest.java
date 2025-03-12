package models.booking;

import lombok.Data;

@Data
public class GetFullBookingWithoutAdditionalRequest {
    String firstname, lastname;
    int totalprice;
    boolean depositpaid;
    BookingDates bookingdates;
}
