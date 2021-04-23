package com.cacl.booking.app;


import com.cacl.booking.app.exception.TicketException;
import com.cacl.booking.domain.Ticket;
import com.cacl.booking.domain.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.QueryTimeoutException;

import static org.mockito.Mockito.*;

@SpringBootTest
class BookingServiceTest {

    @MockBean
    TicketRepository ticketRepository;
    @Autowired
    BookingService bookingService;

    @Test
    public void shouldCallRepository() {
        final TicketModel ticketModel = mock(TicketModel.class);
        final Ticket ticket = mock(Ticket.class);
        when(ticket.getId()).thenReturn(9809080L);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        bookingService.bookTicket(ticketModel);
        verify(ticketRepository, times(2)).findAll();
        verify(ticketRepository).save(any(Ticket.class));
    }

    @Test
    public void shouldThrowTicketExceptionWhenRepositoryThrowsException() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketRepository.save(any(Ticket.class))).thenThrow(new QueryTimeoutException());

        Assertions.assertThrows(TicketException.class, () -> bookingService.bookTicket(ticketModel));
    }

    @Test
    public void shouldReturnTicketId() {
        final TicketModel ticketModel = mock(TicketModel.class);
        final Ticket ticket = mock(Ticket.class);
        when(ticket.getId()).thenReturn(9809080L);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Assertions.assertEquals(9809080L, bookingService.bookTicket(ticketModel));
    }
}