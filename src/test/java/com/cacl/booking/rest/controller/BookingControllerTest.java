package com.cacl.booking.rest.controller;

import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.app.BookingService;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.app.exception.InvalidDataException;
import com.cacl.booking.app.exception.TicketException;
import com.cacl.booking.rest.adapter.TicketAdapter;
import com.cacl.booking.rest.exception.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookingController.class})
class BookingControllerTest {

    private TicketRequest ticketRequest = mock(TicketRequest.class);

    @MockBean
    private TicketAdapter ticketAdapter;
    @MockBean
    private BookingService bookingService;

    @Autowired
    private BookingController bookingController;


    @Test
    public void shouldCallAdapterAndService() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromRequest(ticketRequest)).thenReturn(ticketModel);
        bookingController.bookTicket(ticketRequest);
        verify(ticketAdapter).fromRequest(ticketRequest);
        verify(bookingService).bookTicket(ticketModel);
    }

    @Test
    public void shouldThrowApiExceptionIfAdapterThrowsAnyException() {
        when(ticketAdapter.fromRequest(ticketRequest)).thenThrow(new InvalidDataException("1002", "Incorrect format"));
        Assertions.assertThrows(ApiException.class, () -> bookingController.bookTicket(ticketRequest));
    }

    @Test
    public void shouldThrowApiExceptionIfServiceThrowsAnyException() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromRequest(ticketRequest)).thenReturn(ticketModel);
        when(bookingService.bookTicket(ticketModel)).thenThrow(new TicketException("2001", "Error saving ticket"));
        Assertions.assertThrows(ApiException.class, () -> bookingController.bookTicket(ticketRequest));
    }

    @Test
    public void shouldReturnCorrectBookingResponse() {
        final Long bookingId = 989090L;
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromRequest(ticketRequest)).thenReturn(ticketModel);
        when(bookingService.bookTicket(ticketModel)).thenReturn(bookingId);
        Assertions.assertEquals(bookingId, bookingController.bookTicket(ticketRequest).getBookingId());
    }
}