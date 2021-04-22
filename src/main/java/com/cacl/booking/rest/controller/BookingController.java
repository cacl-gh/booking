package com.cacl.booking.rest.controller;

import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.app.BookingService;
import com.cacl.booking.rest.adapter.TicketAdapter;
import com.cacl.booking.rest.exception.ApiException;
import com.cacl.booking.rest.exception.Error;
import com.cacl.booking.rest.exception.InvalidDataException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class BookingController {

    private final BookingService bookingService;
    private final TicketAdapter ticketAdapter;

    @Autowired
    public BookingController(final BookingService bookingService, final TicketAdapter ticketAdapter) {
        this.bookingService = bookingService;
        this.ticketAdapter = ticketAdapter;
    }

    @PostMapping(value = "/")
    public void bookTicket(final TicketRequest ticketRequest) {
        try {
            bookingService.bookTicket(ticketAdapter.fromRequest(ticketRequest));
        } catch (InvalidDataException e) {
            log.error(e.getMessage(), "Locator not received");
            throw new ApiException(e.getMessage(), Error.fromCode(e.getCode()));
        } catch (Exception e) {
            throw new ApiException("prueba", Error.INTERNAL_ERROR);
        }

    }
}
