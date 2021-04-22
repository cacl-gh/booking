package com.cacl.booking.rest.adapter;

import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.rest.exception.InvalidDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class TicketAdapterTest {

    @Autowired
    private TicketAdapter ticketAdapter;

    @Test
    public void shouldReturnCorrectTicketModel() {
        final TicketRequest ticketRequest = TicketRequest.builder()
                .locator("4CASAS")
                .firstName("Miguel")
                .lastName("Pérez")
                .build();
        TicketModel ticketModel = ticketAdapter.fromRequest(ticketRequest);
        assertEquals(ticketRequest.getLocator(), ticketModel.getLocator());
        assertEquals(ticketRequest.getFirstName(), ticketModel.getFirstName());
        assertEquals(ticketRequest.getLastName(), ticketModel.getLastName());
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