package models.booking;

import lombok.Data;

@Data
public class BookingResponse {
    int bookingid;
    FullBookingRequest booking;
}
