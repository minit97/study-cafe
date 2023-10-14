package com.example.studyCafe.api.studycafe.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class SeatRepositoryImpl implements SeatRepositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SeatRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
