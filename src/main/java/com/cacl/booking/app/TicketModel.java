package com.cacl.booking.app;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
public class TicketModel {
    private Long id;
    private String locator;
    private String firstName;
    private String lastName;
}
