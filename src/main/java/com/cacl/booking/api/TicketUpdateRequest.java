package com.cacl.booking.api;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(callSuper = true)
@ToString
public class TicketUpdateRequest extends TicketRequest {
    private Long id;

    public TicketUpdateRequest(final Long id, final String locator, final String firstName, final String lastName) {
        super(locator, firstName, lastName);
        this.id = id;
    }
}
