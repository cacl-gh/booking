package com.cacl.booking.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class TicketResponse extends BookingResponse {
    private String locator;
    private String firstName;
    private String lastName;

    public TicketResponse(final Long bookingId, final String locator, final String firstName, final String lastName) {
        super(bookingId);
        this.locator = locator;
        this.firstName = firstName;
        this.lastName = lastName;
    }
}