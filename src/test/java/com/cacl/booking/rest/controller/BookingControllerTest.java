package com.cacl.booking.rest.controller;

import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.app.BookingService;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.rest.adapter.TicketAdapter;
import com.cacl.booking.rest.exception.ApiException;
import com.cacl.booking.rest.exception.InvalidDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest
public class BookingControllerTest {

    private TicketRequest ticketRequest = mock(TicketRequest.class);

    @MockBean
    private TicketAdapter ticketAdapter;
    @MockBean
    private BookingService bookingService;

    @Autowired
    private BookingController bookingController;


    @Test
    public void shouldCallAdapterAndService() {
        TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromRequest(ticketRequest)).thenReturn(ticketModel);
        bookingController.bookTicket(ticketRequest);
        verify(ticketAdapter).fromRequest(ticketRequest);
        verify(bookingService).bookTicket(ticketModel);
    }

    @Test
    public void shouldThrowApiExceptionIfAdapterThrowsAnyException() {
        when(ticketAdapter.fromRequest(ticketRequest)).thenThrow(new InvalidDataException("1008", "Incorrect format"));
        Assertions.assertThrows(ApiException.class, () -> bookingController.bookTicket(ticketRequest));
    }

    @Test
    public void shouldThrowApiExceptionIfServiceThrowsAnyException() {
        TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromRequest(ticketRequest)).thenReturn(ticketModel);
        doThrow(new RuntimeException("Unexpected exception")).when(bookingService).bookTicket(ticketModel);
        Assertions.assertThrows(ApiException.class, () -> bookingController.bookTicket(ticketRequest));
    }
}