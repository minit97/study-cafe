package com.example.studyCafe.api.studycafe.dto.response;

import com.example.studyCafe.api.studycafe.model.Ticket;
import lombok.Builder;
import lombok.Data;

@Data
public class TicketResponse {
    private Long ticketId;
    private String ticketName;
    private Integer ticketPrice;
    private Integer ticketTime;

    @Builder
    public TicketResponse(Long ticketId, String ticketName, Integer ticketPrice, Integer ticketTime) {
        this.ticketId = ticketId;
        this.ticketName = ticketName;
        this.ticketPrice = ticketPrice;
        this.ticketTime = ticketTime;
    }

    public static TicketResponse from(Ticket ticket) {
        if (ticket == null) {
            return null;
        }

        return TicketResponse.builder()
                .ticketId(ticket.getId())
                .ticketName(ticket.getName())
                .ticketPrice(ticket.getPrice())
                .ticketTime(builder().ticketTime)
                .build();
    }
}
