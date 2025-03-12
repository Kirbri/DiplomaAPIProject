package models.booking;

import lombok.Data;

@Data
public class GetBookingResponse {
    int bookingid;
    GetFullBookingRequest booking;
}
