package com.cacl.booking.rest.adapter;


import com.cacl.booking.api.TicketListResponse;
import com.cacl.booking.api.TicketRequest;
import com.cacl.booking.api.TicketResponse;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.app.exception.InvalidDataException;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class TicketAdapter {

    public TicketModel fromRequest(final TicketRequest ticketRequest) throws InvalidDataException {
        if(ticketRequest.getLocator() == null || ticketRequest.getLocator().isBlank()) {
            throw new InvalidDataException("1001", "Locator is required");
        }

        return TicketModel.builder()
                .locator(ticketRequest.getLocator())
                .firstName(ticketRequest.getFirstName())
                .lastName(ticketRequest.getLastName())
                .build();
    }

    public TicketListResponse toResponse(List<TicketModel> ticketModelList) {
        return TicketListResponse.builder()
                .tickets(ticketModelList.stream()
                    .map(ticketModel -> new TicketResponse(ticketModel.getId(), ticketModel.getLocator(), ticketModel.getFirstName(),
                                                ticketModel.getLastName()))
                    .collect(Collectors.toList()))
                .build();
    }
}