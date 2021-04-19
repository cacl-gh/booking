package com.cacl.booking;

import com.cacl.booking.domain.Ticket;
import com.cacl.booking.domain.TicketRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;

@SpringBootApplication
@Slf4j
public class BookingApplication {
    @Autowired
    private TicketRepository ticketRepository;

    public static void main(String[] args) {
        SpringApplication.run(BookingApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void runAfterStartup() {
        List ticketList = this.ticketRepository.findAll();
        log.info("Number of tickets: " + ticketList.size());

        Ticket ticket = Ticket.builder()
                .firstName("John")
                .lastName("Smith")
                .locator("CJ89UN")
                .build();
        this.ticketRepository.save(ticket);

        ticketList = this.ticketRepository.findAll();
        log.info("Number of tickets: " + ticketList.size());
    }
}
