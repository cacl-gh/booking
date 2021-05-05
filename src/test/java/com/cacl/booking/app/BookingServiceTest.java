package com.cacl.booking.app;


import com.cacl.booking.app.exception.InvalidDataException;
import com.cacl.booking.app.exception.TicketException;
import com.cacl.booking.domain.Ticket;
import com.cacl.booking.domain.TicketRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import javax.persistence.QueryTimeoutException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class BookingServiceTest {

    @MockBean
    TicketRepository ticketRepository;
    @Autowired
    BookingService bookingService;


    @Test
    public void bookTicketShouldThrowTicketExceptionWhenRepositoryThrowsException() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketRepository.save(any(Ticket.class))).thenThrow(new QueryTimeoutException());

        Assertions.assertThrows(TicketException.class, () -> bookingService.bookTicket(ticketModel));
    }

    @Test
    public void bookTicketShouldReturnTicketId() {
        final TicketModel ticketModel = mock(TicketModel.class);
        final Ticket ticket = mock(Ticket.class);
        when(ticket.getId()).thenReturn(9809080L);
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticket);

        Assertions.assertEquals(9809080L, bookingService.bookTicket(ticketModel));
    }

    @Test
    public void listTicketsShouldThrowTicketExceptionWhenRepositoryThrowsException() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketRepository.findAll(any(PageRequest.class))).thenThrow(new QueryTimeoutException());

        Assertions.assertThrows(TicketException.class, () -> bookingService.listTickets(2, 10));
    }

    @Test
    public void listTicketsShouldReturnTicketModelList() {
        final Ticket ticket = Ticket.builder().id(997989789L).locator("3CANTO").firstName("Paco").lastName("Porras").deletedOn(null).updatedOn(null).build();
        final Ticket ticket2 = Ticket.builder().id(997986689L).locator("4COSAS").firstName("Pepe").lastName("Trola").deletedOn(null).updatedOn(null).build();
        final List<Ticket> ticketList = Arrays.asList(ticket, ticket2);
        when(ticketRepository.findAll(PageRequest.of(2, 10))).thenReturn(new PageImpl<>(ticketList));

        List<TicketModel> ticketModelList = bookingService.listTickets(2, 10);
        for (TicketModel ticketModel: ticketModelList) {
            Assertions.assertTrue(isTicketModelEqualToTicket(ticketModel, ticketList.get(0)) ||
                    isTicketModelEqualToTicket(ticketModel, ticketList.get(1)));
        }
    }

    @Test
    public void listTicketsShouldReturnTicketModelPage() {
        final Ticket ticket = Ticket.builder().id(997989789L).locator("3CANTO").firstName("Paco").lastName("Porras").deletedOn(null).updatedOn(null).build();
        final Ticket ticket2 = Ticket.builder().id(997986689L).locator("4COSAS").firstName("Pepe").lastName("Trola").deletedOn(null).updatedOn(null).build();
        final List<Ticket> ticketList = Arrays.asList(ticket, ticket2);
        when(ticketRepository.findAll(PageRequest.of(2, 10))).thenReturn(new PageImpl<>(ticketList));

        Page<TicketModel> ticketModelPage = bookingService.listTicketsInPage(2, 10);
        for (TicketModel ticketModel: ticketModelPage) {
            Assertions.assertTrue(isTicketModelEqualToTicket(ticketModel, ticketList.get(0)) ||
                    isTicketModelEqualToTicket(ticketModel, ticketList.get(1)));
        }
    }

    @Test
    public void updateTicketShouldThrowInvalidDataExceptionWhenTicketDoesntExists() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketModel.getId()).thenReturn(9809080L);
        when(ticketRepository.findById(9809080L)).thenReturn(Optional.empty());

        Assertions.assertThrows(InvalidDataException.class, () -> bookingService.updateTicket(ticketModel));
    }

    @Test
    public void updateTicketShouldThrowTicketExceptionWhenRepositoryThrowsException() {
        final TicketModel ticketModel = mock(TicketModel.class);
        when(ticketModel.getId()).thenReturn(9809080L);
        when(ticketRepository.findById(9809080L)).thenThrow(new QueryTimeoutException());

        Assertions.assertThrows(TicketException.class, () -> bookingService.updateTicket(ticketModel));
    }

    @Test
    public void updateTicketShouldReturnTicketModelWithSameValuesWhenSaveIsCorrect() {
        final TicketModel ticketModel = TicketModel.builder().id(997989789L).locator("8LOROS").firstName("Juan").lastName("SinTierra").build();
        final Ticket ticketBeforeUpdate = Ticket.builder().id(997989789L).locator("44UU3M").firstName("Juan").lastName("SinTierra").deletedOn(null).updatedOn(null).build();
        final Ticket ticketAfterUpdate = Ticket.builder().id(997989789L).locator("8LOROS").firstName("Juan").lastName("SinTierra").deletedOn(null).updatedOn(LocalDateTime.now()).build();
        when(ticketRepository.findById(997989789L)).thenReturn(Optional.of(ticketBeforeUpdate));
        when(ticketRepository.save(any(Ticket.class))).thenReturn(ticketAfterUpdate);

        Assertions.assertEquals(ticketModel, bookingService.updateTicket(ticketModel));
    }

    private boolean isTicketModelEqualToTicket(TicketModel ticketModel, Ticket ticket) {
        return (ticketModel.getId().equals(ticket.getId())) &&
                (ticketModel.getLocator().equals(ticket.getLocator())) &&
                (ticketModel.getFirstName().equals(ticket.getFirstName())) &&
                (ticketModel.getLastName().equals(ticket.getLastName()));
    }
}
