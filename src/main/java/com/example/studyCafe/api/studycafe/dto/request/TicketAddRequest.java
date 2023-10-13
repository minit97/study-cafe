package com.example.studyCafe.api.studycafe.dto.request;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TicketAddRequest {
    private String ticketName;
    private Integer ticketPrice;
    private LocalDateTime ticketTime;
}
