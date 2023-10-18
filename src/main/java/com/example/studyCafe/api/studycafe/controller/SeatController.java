package com.example.studyCafe.api.studycafe.controller;

import com.example.studyCafe.api.studycafe.dto.request.SeatAddRequest;
import com.example.studyCafe.api.studycafe.dto.request.SeatExitRequest;
import com.example.studyCafe.api.studycafe.dto.request.SeatPickRequest;
import com.example.studyCafe.api.studycafe.dto.request.SeatSearchRequest;
import com.example.studyCafe.api.studycafe.dto.response.SeatResponse;
import com.example.studyCafe.api.studycafe.dto.response.UserSeatReponse;
import com.example.studyCafe.api.studycafe.service.SeatService;
import jakarta.validation.Valid;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SeatController {
    private final SeatService seatService;

    // 어드민 - 자리 등록
    @PostMapping("/admin/seat")
    public void seatAdd(@RequestBody @Valid SeatAddRequest request) {
        seatService.getSeatAdd(request);
    }

    // 어드민 - 자리 삭제
    @DeleteMapping("/admin/seat/{seatId}")
    public void seatDel(@PathVariable Long seatId) {
        seatService.getSeatDel(seatId);
    }

    // 자리 리스트
    @GetMapping("/seats")
    public ResponseEntity<Page<SeatResponse>> seatList(@RequestBody SeatSearchRequest request, Pageable pageable) {
        Page<SeatResponse> seatList = seatService.getSeatList(request, pageable);
        return ResponseEntity.ok(seatList);
    }

    // 자리 선택
    @PostMapping("/seat-pick")
    public ResponseEntity<SeatResponse> seatPick(@RequestBody @Valid SeatPickRequest request) {
        SeatResponse seatPick = seatService.getSeatPick(request);
        return ResponseEntity.ok(seatPick);
    }

    // 퇴장하기
    @PostMapping("/seat-exit")
    public ResponseEntity<UserSeatReponse> seatExit(@RequestBody SeatExitRequest request) {
        UserSeatReponse seatExit = seatService.getSeatExit(request);
        return ResponseEntity.ok(seatExit);
    }

}
