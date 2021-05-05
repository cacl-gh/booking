package com.cacl.booking.rest.controller;

import com.cacl.booking.api.*;
import com.cacl.booking.app.BookingService;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.app.exception.InvalidDataException;
import com.cacl.booking.app.exception.TicketException;
import com.cacl.booking.rest.adapter.TicketAdapter;
import com.cacl.booking.rest.exception.ApiException;
import com.cacl.booking.rest.exception.Error;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;

@RestController
@RequestMapping("api")
@CrossOrigin(origins = "http://localhost:8081")
@Slf4j
public class BookingController {

    final static Integer DEFAULT_PAGE = 0;
    final static Integer DEFAULT_MAX_RESULTS = 10;

    private final BookingService bookingService;
    private final TicketAdapter ticketAdapter;

    @Autowired
    public BookingController(final BookingService bookingService, final TicketAdapter ticketAdapter) {
        this.bookingService = bookingService;
        this.ticketAdapter = ticketAdapter;
    }

    @PostMapping(value = "bookings")
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

    @GetMapping(value = "bookings")
    public TicketPaginatedResponse listTickets(@RequestParam(name = "page", required = false) Integer page,
                                               @RequestParam(name = "maxResults", required = false) Integer maxResults) {
        try {
            return ticketAdapter.toPaginatedResponse(bookingService.listTicketsInPage(page != null ? page : DEFAULT_PAGE,
                    maxResults != null ? maxResults : DEFAULT_MAX_RESULTS));
        } catch (Exception e) {
            throw new ApiException("Internal error", Error.INTERNAL_ERROR);
        }
    }

    @PutMapping(value = "bookings")
    public TicketListResponse updateTicket(@RequestBody final TicketUpdateRequest ticketUpdateRequest) {
        try {
            TicketModel ticketModel = bookingService.updateTicket(ticketAdapter.fromUpdateRequest(ticketUpdateRequest));
            return ticketAdapter.toResponse(Collections.singletonList(ticketModel));
        } catch (InvalidDataException e) {
            log.error(e.getMessage(), "Ticket not found");
            throw new ApiException(e.getMessage(), Error.fromCode(e.getCode()));
        } catch (TicketException e) {
            log.error(e.getMessage(), "Error saving ticket");
            throw new ApiException(e.getMessage(), Error.fromCode(e.getCode()));
        } catch (Exception e) {
            throw new ApiException("Internal error", Error.INTERNAL_ERROR);
        }
    }
}
