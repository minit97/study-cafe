package com.example.studyCafe.api.studycafe.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class TicketAddRequest {
    @NotBlank
    private String ticketName;
    @NotNull
    private Integer ticketPrice;
    @NotNull
    private Integer ticketTime;
}
