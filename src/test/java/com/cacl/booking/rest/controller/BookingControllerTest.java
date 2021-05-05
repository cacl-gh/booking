package com.cacl.booking.rest.controller;

import com.cacl.booking.api.TicketPaginatedResponse;
import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.api.TicketResponse;
import com.cacl.booking.api.TicketUpdateRequest;
import com.cacl.booking.app.BookingService;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.app.exception.InvalidDataException;
import com.cacl.booking.app.exception.TicketException;
import com.cacl.booking.rest.adapter.TicketAdapter;
import com.cacl.booking.rest.exception.ApiException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.Collections;

import static org.mockito.Mockito.*;

@SpringBootTest(classes = {BookingController.class})
class BookingControllerTest {

    @Mock
    private TicketRequest ticketRequest;
    @Mock
    private TicketUpdateRequest ticketUpdateRequest;

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
    public void listTicketsShouldReturnAPageOfTicketsBooked() {
        final TicketModel ticketModel = mock(TicketModel.class);
        final Page<TicketModel> ticketModelPage = new PageImpl<>(Collections.singletonList(ticketModel));
        final TicketResponse ticketResponse = mock(TicketResponse.class);
        final TicketPaginatedResponse ticketPaginatedResponse = TicketPaginatedResponse.builder().tickets(Collections.singletonList(ticketResponse)).build();
        when(bookingService.listTicketsInPage(2, 10)).thenReturn((ticketModelPage));
        when(ticketAdapter.toPaginatedResponse(ticketModelPage)).thenReturn(ticketPaginatedResponse);

        Assertions.assertEquals(ticketPaginatedResponse, bookingController.listTickets(2, 10));
    }

    @Test
    public void listTicketsShouldReturnAListOfTicketsBookedWithDefaultPagination() {
        final TicketModel ticketModel = mock(TicketModel.class);
        final Page<TicketModel> ticketModelPage = new PageImpl<>(Collections.singletonList(ticketModel));
        final TicketResponse ticketResponse = mock(TicketResponse.class);
        final TicketPaginatedResponse ticketPaginatedResponse = TicketPaginatedResponse.builder().tickets(Collections.singletonList(ticketResponse)).build();
        when(bookingService.listTicketsInPage(BookingController.DEFAULT_PAGE, BookingController.DEFAULT_MAX_RESULTS)).thenReturn(ticketModelPage);
        when(ticketAdapter.toPaginatedResponse(ticketModelPage)).thenReturn(ticketPaginatedResponse);

        Assertions.assertEquals(ticketPaginatedResponse, bookingController.listTickets(null, null));
    }

    @Test
    public void listTicketsShouldThrowApiExceptionIfServiceThrowsAnyException() {
        when(bookingService.listTicketsInPage(2, 10)).thenThrow(new TicketException("2001", "Error getting tickets"));

        Assertions.assertThrows(ApiException.class, () -> bookingController.listTickets(2,10));
    }

    @Test
    public void updateTicketShouldThrowApiExceptionIfAdapterThrowsInvalidDataException() {
        when(ticketAdapter.fromUpdateRequest(ticketUpdateRequest)).thenThrow(new InvalidDataException("1002", "Incorrect format"));

        Assertions.assertThrows(ApiException.class, () -> bookingController.updateTicket(ticketUpdateRequest));
    }

    @Test
    public void updateTicketShouldThrowApiExceptionIfServiceThrowsTicketException() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromUpdateRequest(ticketUpdateRequest)).thenReturn(ticketModel);
        when(bookingService.updateTicket(ticketModel)).thenThrow(new TicketException("2001", "Error getting or saving ticket"));

        Assertions.assertThrows(ApiException.class, () -> bookingController.updateTicket(ticketUpdateRequest));
    }

    @Test
    public void updateTicketShouldThrowApiExceptionIfServiceThrowsInvalidDataException() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketAdapter.fromUpdateRequest(ticketUpdateRequest)).thenReturn(ticketModel);
        when(bookingService.updateTicket(ticketModel)).thenThrow(new InvalidDataException("1003", "Ticket not found"));

        Assertions.assertThrows(ApiException.class, () -> bookingController.updateTicket(ticketUpdateRequest));
    }
}
