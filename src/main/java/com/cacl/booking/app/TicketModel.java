package com.cacl.booking.app;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class TicketModel {
    private String locator;
    private String firstName;
    private String lastName;
}
