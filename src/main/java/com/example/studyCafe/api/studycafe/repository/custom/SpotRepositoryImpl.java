package com.example.studyCafe.api.studycafe.repository.custom;

import com.example.studyCafe.api.studycafe.model.Spot;
import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import org.springframework.data.domain.Pageable;

import static com.example.studyCafe.api.studycafe.model.QSpot.*;

public class SpotRepositoryImpl implements SpotRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SpotRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public QueryResults<Spot> searchSpotList(Pageable pageable) {
        QueryResults<Spot> results = queryFactory
                .select(spot)
                .from(spot)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetchResults();
        return results;
    }
}
