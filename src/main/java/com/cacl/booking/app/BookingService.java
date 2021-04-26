package com.cacl.booking.app;

import com.cacl.booking.app.exception.TicketException;
import com.cacl.booking.domain.Ticket;
import com.cacl.booking.domain.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class BookingService {

    private final TicketRepository ticketRepository;

    @Autowired
    public BookingService(final TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public Long bookTicket(TicketModel ticketModel) {
        List<Ticket> ticketList = this.ticketRepository.findAll();
        log.info("Number of tickets: " + ticketList.size());
        Ticket ticketSaved;

        try {
            ticketSaved = this.ticketRepository.save(buildTicket(ticketModel));
        } catch (Exception e) {
            log.error(e.getMessage(), "Error saving ticket");
            throw new TicketException("2001", "Error saving ticket");
        }

        ticketList = this.ticketRepository.findAll();
        ticketList.forEach(t -> log.info(t.toString()));
        log.info("New ticket: "+ticketSaved.getId()+". Number of tickets now: " + ticketList.size());
        return ticketSaved.getId();
    }

    public List<TicketModel> listTickets() {
        List<Ticket> ticketList = this.ticketRepository.findAll();
        return ticketList.stream()
                .map(this::buildTicketModel)
                .collect(Collectors.toList());
    }

    private Ticket buildTicket(TicketModel ticketModel) {
        return Ticket.builder()
                .locator(ticketModel.getLocator())
                .firstName(ticketModel.getFirstName())
                .lastName(ticketModel.getLastName())
                .build();
    }

    private TicketModel buildTicketModel(Ticket ticket) {
        return TicketModel.builder()
                .id(ticket.getId())
                .locator(ticket.getLocator())
                .firstName(ticket.getFirstName())
                .lastName(ticket.getLastName())
                .build();
    }
}
