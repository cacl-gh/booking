package com.cacl.booking.rest.adapter;

import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.app.exception.InvalidDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

class TicketAdapterTest {

    private TicketAdapter ticketAdapter = new TicketAdapter();

    @Test
    public void shouldReturnCorrectTicketModel() {
        final TicketRequest ticketRequest = TicketRequest.builder()
                .locator("4CASAS")
                .firstName("Miguel")
                .lastName("Pérez")
                .build();
        TicketModel ticketModel = ticketAdapter.fromRequest(ticketRequest);
        Assertions.assertEquals(ticketRequest.getLocator(), ticketModel.getLocator());
        Assertions.assertEquals(ticketRequest.getFirstName(), ticketModel.getFirstName());
        Assertions.assertEquals(ticketRequest.getLastName(), ticketModel.getLastName());
    }

    @Test
    public void shouldThrowInvalidDataExceptionWhenLocatorIsNull() {
        final TicketRequest ticketRequest = TicketRequest.builder()
                .firstName("Miguel")
                .lastName("Pérez")
                .build();
        Assertions.assertThrows(InvalidDataException.class, () -> ticketAdapter.fromRequest(ticketRequest));
    }
}