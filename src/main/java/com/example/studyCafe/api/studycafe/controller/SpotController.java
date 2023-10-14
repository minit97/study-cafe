package com.example.studyCafe.api.studycafe.controller;

import com.example.studyCafe.api.studycafe.dto.request.SpotAddRequest;
import com.example.studyCafe.api.studycafe.dto.response.SpotResponse;
import com.example.studyCafe.api.studycafe.service.SpotService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SpotController {
    private final SpotService spotService;

    // 어드민 - 지점 등록
    @PostMapping("/admin/spot")
    public void adminSpotAdd(@RequestBody SpotAddRequest request) {
        spotService.getAdminSpotAdd(request);
    }

    // 어드민 - 지점 삭제
    @DeleteMapping("/admin/spot/{spotId}")
    public void adminSpotDel(@PathVariable Long spotId) {
        spotService.getAdminSpotDel(spotId);
    }

    // 지점 리스트
    @GetMapping("/spots")
    public ResponseEntity<Page<SpotResponse>> spotList(Pageable pageable) {
        Page<SpotResponse> spotList = spotService.getSpotList(pageable);
        return ResponseEntity.ok(spotList);
    }
}
