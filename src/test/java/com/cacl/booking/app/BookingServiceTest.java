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
import java.util.Arrays;
import java.util.List;

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

    @Test
    public void shouldReturnTicketModelList() {
        final Ticket ticket = new Ticket(997989789L, "3CANTO", "Paco", "Porras");
        final Ticket ticket2 = new Ticket(997986689L, "4COSAS", "Pepe", "Trola");
        final List<Ticket> ticketList = Arrays.asList(ticket, ticket2);
        when(ticketRepository.findAll()).thenReturn(ticketList);

        List<TicketModel> ticketModelList = bookingService.listTickets();
        for (TicketModel ticketModel: ticketModelList) {
            Assertions.assertTrue(isTicketModelEqualToTicket(ticketModel, ticketList.get(0)) ||
                    isTicketModelEqualToTicket(ticketModel, ticketList.get(1)));
        }
    }

    private boolean isTicketModelEqualToTicket(TicketModel ticketModel, Ticket ticket) {
        return (ticketModel.getId().equals(ticket.getId())) &&
                (ticketModel.getLocator().equals(ticket.getLocator())) &&
                (ticketModel.getFirstName().equals(ticket.getFirstName())) &&
                (ticketModel.getLastName().equals(ticket.getLastName()));
    }
}