package com.cacl.booking.rest.controller;

import com.cacl.booking.api.TicketListResponse;
import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.api.TicketResponse;
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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

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
    public void bookTicketShouldCallAdapterAndService() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromRequest(ticketRequest)).thenReturn(ticketModel);
        bookingController.bookTicket(ticketRequest);
        verify(ticketAdapter).fromRequest(ticketRequest);
        verify(bookingService).bookTicket(ticketModel);
    }

    @Test
    public void bookTicketShouldThrowApiExceptionIfAdapterThrowsAnyException() {
        when(ticketAdapter.fromRequest(ticketRequest)).thenThrow(new InvalidDataException("1002", "Incorrect format"));
        Assertions.assertThrows(ApiException.class, () -> bookingController.bookTicket(ticketRequest));
    }

    @Test
    public void bookTicketShouldThrowApiExceptionIfServiceThrowsAnyException() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromRequest(ticketRequest)).thenReturn(ticketModel);
        when(bookingService.bookTicket(ticketModel)).thenThrow(new TicketException("2001", "Error saving ticket"));
        Assertions.assertThrows(ApiException.class, () -> bookingController.bookTicket(ticketRequest));
    }

    @Test
    public void bookTicketShouldReturnCorrectBookingResponse() {
        final Long bookingId = 989090L;
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromRequest(ticketRequest)).thenReturn(ticketModel);
        when(bookingService.bookTicket(ticketModel)).thenReturn(bookingId);
        Assertions.assertEquals(bookingId, bookingController.bookTicket(ticketRequest).getBookingId());
    }

    @Test
    public void listTicketsShouldReturnAListOfTicketsBooked() {
        final TicketModel ticketModel = mock(TicketModel.class);
        final List<TicketModel> ticketModelList = Collections.singletonList(ticketModel);
        final TicketResponse ticketResponse = mock(TicketResponse.class);
        final TicketListResponse ticketListResponse = mock(TicketListResponse.class);
        when(ticketListResponse.getTickets()).thenReturn(Collections.singletonList(ticketResponse));
        when(bookingService.listTickets()).thenReturn(ticketModelList);
        when(ticketAdapter.toResponse(ticketModelList)).thenReturn(ticketListResponse);

        Assertions.assertEquals(ticketListResponse, bookingController.listTickets());
    }

    @Test
    public void listTicketsShouldThrowApiExceptionIfServiceThrowsAnyException() {
        when(bookingService.listTickets()).thenThrow(new RuntimeException("Error getting tickets"));
        Assertions.assertThrows(ApiException.class, () -> bookingController.listTickets());
    }
}