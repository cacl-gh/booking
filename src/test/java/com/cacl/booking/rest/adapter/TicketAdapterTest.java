package com.cacl.booking.rest.adapter;

import com.cacl.booking.api.*;
import com.cacl.booking.app.TicketModel;
import com.cacl.booking.app.exception.InvalidDataException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.Arrays;
import java.util.List;

class TicketAdapterTest {

    private TicketAdapter ticketAdapter = new TicketAdapter();

    @Test
    public void shouldReturnCorrectTicketModel() {
        final TicketRequest ticketRequest = TicketRequest.builder()
                .locator("4CASAS")
                .firstName("Miguel")
                .lastName("Pérez")
                .build();
        TicketModel ticketModel = ticketAdapter.fromRequest(ticketRequest);
        Assertions.assertEquals(ticketRequest.getLocator(), ticketModel.getLocator());
        Assertions.assertEquals(ticketRequest.getFirstName(), ticketModel.getFirstName());
        Assertions.assertEquals(ticketRequest.getLastName(), ticketModel.getLastName());
    }

    @Test
    public void shouldThrowInvalidDataExceptionWhenLocatorIsNull() {
        final TicketRequest ticketRequest = TicketRequest.builder()
                .firstName("Miguel")
                .lastName("Pérez")
                .build();
        Assertions.assertThrows(InvalidDataException.class, () -> ticketAdapter.fromRequest(ticketRequest));
    }

    @Test
    public void shouldThrowInvalidDataExceptionWhenLocatorIsBlank() {
        final TicketRequest ticketRequest = TicketRequest.builder()
                .locator("")
                .firstName("Miguel")
                .lastName("Pérez")
                .build();
        Assertions.assertThrows(InvalidDataException.class, () -> ticketAdapter.fromRequest(ticketRequest));
    }

    @Test
    public void shouldThrowInvalidDataExceptionWhenIdIsNull() {
        final TicketUpdateRequest ticketUpdateRequest = new TicketUpdateRequest(null, "0890808", "Cristina", "Vázquez");

        Assertions.assertThrows(InvalidDataException.class, () -> ticketAdapter.fromUpdateRequest(ticketUpdateRequest));
    }

    @Test
    public void shouldReturnCorrectTicketModelWithId() {
        final TicketUpdateRequest ticketUpdateRequest = new TicketUpdateRequest(95555L, "0890808", "Cristina", "Vázquez");

        TicketModel ticketModel = ticketAdapter.fromUpdateRequest(ticketUpdateRequest);
        Assertions.assertEquals(ticketUpdateRequest.getId(), ticketModel.getId());
        Assertions.assertEquals(ticketUpdateRequest.getLocator(), ticketModel.getLocator());
        Assertions.assertEquals(ticketUpdateRequest.getFirstName(), ticketModel.getFirstName());
        Assertions.assertEquals(ticketUpdateRequest.getLastName(), ticketModel.getLastName());
    }

    @Test
    public void shouldReturnCorrectTicketListResponse() {
        final TicketModel ticketModel = TicketModel.builder()
                .id(96009640L)
                .locator("8WUHI7")
                .firstName("Enrique")
                .lastName("Eldelavaca")
                .build();
        final TicketModel ticketModel2 = TicketModel.builder()
                .id(96779640L)
                .locator("9WUHI7")
                .firstName("Juan")
                .lastName("Sintierra")
                .build();
        final List<TicketModel> ticketModelList = Arrays.asList(ticketModel, ticketModel2);

        TicketListResponse ticketListResponse = ticketAdapter.toResponse(ticketModelList);
        Assertions.assertEquals(2, ticketListResponse.getTickets().size());
        for (TicketResponse ticketResponse: ticketListResponse.getTickets()) {
            Assertions.assertTrue(isResponseEqualToModel(ticketResponse, ticketModelList.get(0)) ||
                    isResponseEqualToModel(ticketResponse, ticketModelList.get(1)));
        }
    }

    @Test
    public void shouldReturnCorrectTicketPaginatedResponse() {
        final TicketModel ticketModel = TicketModel.builder()
                .id(96009640L)
                .locator("8WUHI7")
                .firstName("Enrique")
                .lastName("Eldelavaca")
                .build();
        final TicketModel ticketModel2 = TicketModel.builder()
                .id(96779640L)
                .locator("9WUHI7")
                .firstName("Juan")
                .lastName("Sintierra")
                .build();
        final List<TicketModel> ticketModelList = Arrays.asList(ticketModel, ticketModel2);
        Pageable pageable = PageRequest.of(0, 2);
        Page<TicketModel> page = new PageImpl<TicketModel>(ticketModelList, pageable, 2);

        TicketPaginatedResponse pageTicketResponse = ticketAdapter.toPaginatedResponse(page);
        Assertions.assertEquals(2, pageTicketResponse.getTotalItems());
        for (TicketResponse ticketResponse: pageTicketResponse.getTickets()) {
            Assertions.assertTrue(isResponseEqualToModel(ticketResponse, ticketModelList.get(0)) ||
                    isResponseEqualToModel(ticketResponse, ticketModelList.get(1)));
        }
    }

    private boolean isResponseEqualToModel(TicketResponse ticketResponse, TicketModel ticketModel) {
        return (ticketResponse.getBookingId().equals(ticketModel.getId())) &&
                (ticketResponse.getLocator().equals(ticketModel.getLocator())) &&
                (ticketResponse.getFirstName().equals(ticketModel.getFirstName())) &&
                (ticketResponse.getLastName().equals(ticketModel.getLastName()));
    }
}
