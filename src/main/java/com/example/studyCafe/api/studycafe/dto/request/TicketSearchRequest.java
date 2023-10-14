package com.example.studyCafe.api.studycafe.dto.request;

import lombok.Data;

@Data
public class TicketSearchRequest {
    private Integer ticketPriceGeo;
    private Integer ticketPriceLeo;

    private Integer ticketTimeGeo;
    private Integer ticketTimeLeo;


}
