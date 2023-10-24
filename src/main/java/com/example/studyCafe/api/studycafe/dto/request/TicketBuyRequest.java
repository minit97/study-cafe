package com.example.studyCafe.api.studycafe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
public class TicketBuyRequest {
    @NotNull
    private Long ticketId;
    @NotNull
    private Long userId;

    @Builder
    public TicketBuyRequest(Long ticketId, Long userId) {
        this.ticketId = ticketId;
        this.userId = userId;
    }
}
