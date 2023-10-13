package com.example.studyCafe.api.studycafe.repository.custom;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;

public class SpotRepositoryImpl implements SpotRespositoryCustom {
    private final JPAQueryFactory queryFactory;

    public SpotRepositoryImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }
}
