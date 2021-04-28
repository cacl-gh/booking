package com.cacl.booking.rest.controller;

import com.cacl.booking.api.BookingResponse;
import com.cacl.booking.api.TicketListResponse;
import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.app.BookingService;
import com.cacl.booking.app.exception.InvalidDataException;
import com.cacl.booking.app.exception.TicketException;
import com.cacl.booking.rest.adapter.TicketAdapter;
import com.cacl.booking.rest.exception.ApiException;
import com.cacl.booking.rest.exception.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin(origins = "http://localhost:80")
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    private final TicketAdapter ticketAdapter;

    @Autowired
    public BookingController(final BookingService bookingService, final TicketAdapter ticketAdapter) {
        this.bookingService = bookingService;
        this.ticketAdapter = ticketAdapter;
    }

    @PostMapping(value = "/book")
    public BookingResponse bookTicket(@RequestBody final TicketRequest ticketRequest) {
        try {
            Long bookingId = bookingService.bookTicket(ticketAdapter.fromRequest(ticketRequest));
            return BookingResponse.builder().bookingId(bookingId).build();
        } catch (InvalidDataException e) {
            log.error(e.getMessage(), "Locator not received");
            throw new ApiException(e.getMessage(), Error.fromCode(e.getCode()));
        } catch (TicketException e) {
            log.error(e.getMessage(), "Error saving ticket");
            throw new ApiException(e.getMessage(), Error.fromCode(e.getCode()));
        } catch (Exception e) {
            throw new ApiException("Internal error", Error.INTERNAL_ERROR);
        }
    }

    @GetMapping(value = "/list")
    public TicketListResponse listTickets() {
        try {
            return ticketAdapter.toResponse(bookingService.listTickets());
        } catch (Exception e) {
            throw new ApiException("Internal error", Error.INTERNAL_ERROR);
        }
    }
}
