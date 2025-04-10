package models.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FullBookingWithoutAdditionalRequest {
    String firstname, lastname;
    int totalprice;
    boolean depositpaid;
    BookingDates bookingdates;
}
