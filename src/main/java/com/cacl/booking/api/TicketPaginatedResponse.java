package com.cacl.booking.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TicketPaginatedResponse {
    private Long totalItems;
    private List<TicketResponse> tickets;
    private Integer totalPages;
    private Integer currentPage;
}
