package com.cacl.booking.rest.adapter;

import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.rest.exception.InvalidDataException;
import org.springframework.stereotype.Component;

@Component
public class TicketAdapter {

    public TicketModel fromRequest(TicketRequest ticketRequest) throws InvalidDataException {
            if(ticketRequest.getLocator() == null) {
                throw new InvalidDataException("1001", "Locator is required");
            }

            return TicketModel.builder()
                    .locator(ticketRequest.getLocator())
                    .firstName(ticketRequest.getFirstName())
                    .lastName(ticketRequest.getLastName())
                    .build();
    }
}
