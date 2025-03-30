package models.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullBookingRequest {
    String firstname, lastname, additionalneeds;
    int totalprice;
    boolean depositpaid;
    BookingDates bookingdates;
}