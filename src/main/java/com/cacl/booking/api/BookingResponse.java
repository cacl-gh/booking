package com.cacl.booking.api;

import lombok.Builder;
import lombok.Data;
import lombok.ToString;

@Data
@Builder
@ToString
public class BookingResponse {
    private Long bookingId;
}
