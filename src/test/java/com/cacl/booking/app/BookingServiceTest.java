package com.cacl.booking.app;


import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.domain.Ticket;
import com.cacl.booking.domain.TicketRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.verify;

@SpringBootTest
public class BookingServiceTest {

    @MockBean
    TicketRepository ticketRepository;
    @Autowired
    BookingService bookingService;

    @Test
    public void shouldCallRepository() {
        TicketModel ticketModel = mock(TicketModel.class);
        Ticket ticket = mock(Ticket.class);

        bookingService.bookTicket(ticketModel);
        verify(ticketRepository.save(ticket));

        //when(ticketAdapter.fromRequest(ticketRequest)).thenReturn(ticketModel);
    }
}