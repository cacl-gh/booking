package com.cacl.booking.app;

import com.cacl.booking.app.exception.InvalidDataException;
import com.cacl.booking.app.exception.TicketException;
import com.cacl.booking.domain.Ticket;
import com.cacl.booking.domain.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
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
        try {
            return this.ticketRepository.save(buildNewTicket(ticketModel)).getId();
        } catch (Exception e) {
            log.error(e.getMessage(), "Error saving ticket with locator "+ticketModel.getLocator());
            throw new TicketException("2001", "Error saving ticket");
        }
    }

    public List<TicketModel> listTickets(Integer page, Integer maxResults) {
        return listTicketsInPage(page, maxResults).getContent();
    }

    public Page<TicketModel> listTicketsInPage(Integer page, Integer maxResults) {
        try {
            Page<Ticket> ticketList = this.ticketRepository.findAll(PageRequest.of(page, maxResults));
            return new PageImpl<>(ticketList.getContent().stream()
                    .map(this::buildTicketModel)
                    .collect(Collectors.toList()), ticketList.getPageable(), ticketList.getTotalElements());
        } catch (Exception e) {
            log.error(e.getMessage(), "Error getting tickets");
            throw new TicketException("2001", "Error getting tickets");
        }
    }

    public TicketModel updateTicket(TicketModel ticketModel) {
        try {
            Optional<Ticket> ticket = this.ticketRepository.findById(ticketModel.getId());
            if (!ticket.isPresent()) {
                log.error("Ticket not found");
                throw new InvalidDataException("1003", "Ticket not found");
            }
            return buildTicketModel(this.ticketRepository.save(buildTicketToUpdate(ticketModel)));
        } catch (InvalidDataException ide) {
            throw ide;
        } catch (Exception e) {
            log.error(e.getMessage(), "Error getting or saving ticket");
            throw new TicketException("2001", "Error getting or saving ticket");
        }
    }

    private Ticket buildNewTicket(TicketModel ticketModel) {
        return Ticket.builder()
                .locator(ticketModel.getLocator())
                .firstName(ticketModel.getFirstName())
                .lastName(ticketModel.getLastName())
                .build();
    }

    private Ticket buildTicketToUpdate(TicketModel ticketModel) {
        Ticket ticket = buildNewTicket(ticketModel);
        ticket.setId(ticketModel.getId());
        ticket.setUpdatedOn(LocalDateTime.now());
        return ticket;
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
