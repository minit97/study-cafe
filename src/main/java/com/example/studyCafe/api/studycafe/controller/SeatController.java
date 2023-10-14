package com.example.studyCafe.api.studycafe.controller;

import com.example.studyCafe.api.studycafe.service.SeatService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class SeatController {
    private final SeatService seatService;

    // 어드민 - 자리 등록
    @PostMapping("/admin/seat")
    public void seatAdd() {


    }

    // 어드민 - 자리 삭제
    @DeleteMapping("/admin/seat/{seatId}")
    public void seatDel() {

    }

    // 자리 리스트
    @GetMapping("/seats")
    public void seatList() {

    }

    // 자리 선택
    @PostMapping("/seat-pick")
    public void seatPick() {

    }

    // 퇴장하기
    @PostMapping("/seat-exit")
    public void seatExit() {

    }

}
