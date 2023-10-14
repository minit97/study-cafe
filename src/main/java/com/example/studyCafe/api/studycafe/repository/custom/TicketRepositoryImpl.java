package com.example.studyCafe.api.studycafe.repository.custom;

import com.example.studyCafe.api.studycafe.dto.request.TicketSearchRequest;
import com.example.studyCafe.api.studycafe.model.QTicket;
import com.example.studyCafe.api.studycafe.model.Ticket;
import com.querydsl.core.QueryResults;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

import static com.example.studyCafe.api.studycafe.model.QTicket.*;

public class TicketRepositoryImpl implements TicketRepositoryCustom{
    private final JPAQueryFactory queryFactory;

    public TicketRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public QueryResults<Ticket> searchTicketList(TicketSearchRequest condition, Pageable pageable) {
        QueryResults<Ticket> results = queryFactory
                .select(ticket)
                .from(ticket)
                .where(
                        priceGeo(condition.getTicketPriceGeo()),
                        priceLeo(condition.getTicketPriceLeo()),
                        timeGeo(condition.getTicketTimeGeo()),
                        timeLeo(condition.getTicketTimeLeo())
                )
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return results;
    }
    private BooleanExpression priceGeo(Integer priceGeo) {
        return priceGeo != null ?  ticket.price.goe(priceGeo) : null;
    }
    private BooleanExpression priceLeo(Integer priceLeo) {
        return priceLeo != null ?  ticket.price.loe(priceLeo) : null;
    }
    private BooleanExpression timeGeo(Integer timeGeo) {
        return timeGeo != null ?  ticket.time.goe(timeGeo) : null;
    }
    private BooleanExpression timeLeo(Integer timeLeo) {
        return timeLeo != null ?  ticket.time.loe(timeLeo) : null;
    }


}
