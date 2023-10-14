package com.example.studyCafe.api.studycafe.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketBuyRequest {
    @NotNull
    private Long ticketId;
    @NotNull
    private Long userId;
}
