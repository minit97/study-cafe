package com.example.studyCafe.api.studycafe.repository.custom;

import com.example.studyCafe.api.studycafe.dto.request.SeatSearchRequest;
import com.example.studyCafe.api.studycafe.model.QUserSeat;
import com.example.studyCafe.api.studycafe.model.Seat;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

import static com.example.studyCafe.api.studycafe.model.QSeat.seat;
import static com.example.studyCafe.api.studycafe.model.QUserSeat.*;

public class SeatRepositoryImpl implements SeatRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SeatRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public QueryResults<Seat> searchSeatList(SeatSearchRequest condition, Pageable pageable) {
        QueryResults<Seat> results = queryFactory
                .select(seat)
                .from(seat)
//                .leftJoin(seat.userSeat, userSeat)
//                .fetchJoin()
                .where(
                        activeEq(condition.getSeatActive())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return results;
    }

    private BooleanExpression activeEq(Boolean active) {
        return active != null ?  seat.active.eq(active) : null;
    }
}
