package com.example.studyCafe.api.studycafe.dto.request;

import lombok.Data;

@Data
public class TicketBuyRequest {
    private Long ticketId;
    private Long userId;
}
