package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.studycafe.dto.request.SpotAddRequest;
import com.example.studyCafe.api.studycafe.model.Spot;
import com.example.studyCafe.api.studycafe.repository.SpotRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

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
}
