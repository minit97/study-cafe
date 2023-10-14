package com.example.studyCafe.api.studycafe.repository.custom;

import com.example.studyCafe.api.studycafe.model.Spot;
import com.querydsl.core.QueryResults;
import org.springframework.data.domain.Pageable;

public interface SpotRepositoryCustom {
    QueryResults<Spot> searchSpotList(Pageable pageable);
}
