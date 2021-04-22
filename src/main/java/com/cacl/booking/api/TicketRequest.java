package com.cacl.booking.api;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketRequest {
    private String locator;
    private String firstName;
    private String lastName;
}
