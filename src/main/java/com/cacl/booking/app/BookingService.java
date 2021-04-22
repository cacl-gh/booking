package com.cacl.booking.app;

import com.cacl.booking.domain.TicketRepository;
import ma.glasnost.orika.MapperFacade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

@Service
public class BookingService {

    private final MapperFacade mapperFacade;
    private final TicketRepository ticketRepository;

    @Autowired
    public BookingService(@Qualifier("appOrika") MapperFacade mapperFacade, final TicketRepository ticketRepository) {
        this.mapperFacade = mapperFacade;
        this.ticketRepository = ticketRepository;
    }


    public void bookTicket(TicketModel ticketModel) {

    }
}
