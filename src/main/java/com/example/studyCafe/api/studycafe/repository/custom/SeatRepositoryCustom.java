package com.example.studyCafe.api.studycafe.repository.custom;

import com.example.studyCafe.api.studycafe.dto.request.SeatSearchRequest;
import com.example.studyCafe.api.studycafe.model.Seat;
import com.example.studyCafe.api.studycafe.model.Spot;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

public interface SeatRepositoryCustom {

    QueryResults<Seat> searchSeatList(SeatSearchRequest condition, Pageable pageable);
}
