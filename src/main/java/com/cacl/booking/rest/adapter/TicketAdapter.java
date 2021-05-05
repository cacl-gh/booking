package com.cacl.booking.rest.adapter;


import com.cacl.booking.api.*;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.app.exception.InvalidDataException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

    public TicketModel fromUpdateRequest(final TicketUpdateRequest ticketUpdateRequest) throws InvalidDataException {
        if(ticketUpdateRequest.getId() == null) {
            throw new InvalidDataException("1004", "Id is required");
        }

        TicketModel ticketModel = fromRequest(ticketUpdateRequest);
        ticketModel.setId(ticketUpdateRequest.getId());

        return ticketModel;
    }

    public TicketListResponse toResponse(List<TicketModel> ticketModelList) {
        return TicketListResponse.builder()
                .tickets(ticketModelList.stream()
                    .map(ticketModel -> new TicketResponse(ticketModel.getId(), ticketModel.getLocator(), ticketModel.getFirstName(),
                                                ticketModel.getLastName()))
                    .collect(Collectors.toList()))
                .build();
    }

    public TicketPaginatedResponse toPaginatedResponse(Page<TicketModel> ticketModelPage) {
        return TicketPaginatedResponse.builder()
                .totalItems(ticketModelPage.getTotalElements())
                .tickets(ticketModelPage.stream()
                    .map(ticketModel -> new TicketResponse(ticketModel.getId(), ticketModel.getLocator(), ticketModel.getFirstName(),
                            ticketModel.getLastName()))
                    .collect(Collectors.toList()))
                .totalPages(ticketModelPage.getTotalPages())
                .currentPage(ticketModelPage.getPageable().getPageNumber())
                .build();
    }
}
