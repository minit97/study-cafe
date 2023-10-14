package com.example.studyCafe.api.studycafe.repository.custom;

import com.example.studyCafe.api.studycafe.dto.request.TicketSearchRequest;
import com.example.studyCafe.api.studycafe.model.Ticket;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface TicketRepositoryCustom {
    QueryResults<Ticket> searchTicketList(TicketSearchRequest condition, Pageable pageable);
}
