package com.example.studyCafe.api.studycafe.dto.request;

import lombok.Builder;
import lombok.Data;

@Data
public class TicketSearchRequest {
    private Integer ticketPriceGeo;
    private Integer ticketPriceLeo;
    private Integer ticketTimeGeo;
    private Integer ticketTimeLeo;

    @Builder
    public TicketSearchRequest(Integer ticketPriceGeo, Integer ticketPriceLeo, Integer ticketTimeGeo, Integer ticketTimeLeo) {
        this.ticketPriceGeo = ticketPriceGeo;
        this.ticketPriceLeo = ticketPriceLeo;
        this.ticketTimeGeo = ticketTimeGeo;
        this.ticketTimeLeo = ticketTimeLeo;
    }
}
