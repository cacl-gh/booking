package com.cacl.booking.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TicketModel {
    private String locator;
    private String firstName;
    private String lastName;
}
