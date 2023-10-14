package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.studycafe.dto.request.SpotAddRequest;
import com.example.studyCafe.api.studycafe.dto.response.SpotResponse;
import com.example.studyCafe.api.studycafe.model.Spot;
import com.example.studyCafe.api.studycafe.repository.SpotRepository;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SpotService {
    private final SpotRepository spotRepository;

    public void getAdminSpotAdd(SpotAddRequest request) {
        Spot data = Spot.builder()
                .name(request.getName())
                .phone(request.getPhone())
                .build();
        spotRepository.save(data);
    }

    public void getAdminSpotDel(Long spotId) {
        Spot spot = spotRepository.findById(spotId)
                .orElseThrow(() -> new IllegalArgumentException("존재하지 않는 지점입니다."));
        spotRepository.delete(spot);
    }

    public Page<SpotResponse> getSpotList(Pageable pageable) {
        QueryResults<Spot> queryResults = spotRepository.searchSpotList(pageable);
        long total = queryResults.getTotal();
        List<Spot> list = queryResults.getResults();
        List<SpotResponse> dtoList = list.stream().map(SpotResponse::from).collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, total);
    }
}
