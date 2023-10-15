package com.example.studyCafe.api.studycafe.controller;

import com.example.studyCafe.api.studycafe.dto.request.TicketAddRequest;
import com.example.studyCafe.api.studycafe.dto.request.TicketBuyRequest;
import com.example.studyCafe.api.studycafe.dto.request.TicketSearchRequest;
import com.example.studyCafe.api.studycafe.dto.response.TicketResponse;
import com.example.studyCafe.api.studycafe.service.TicketService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Duration;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TicketController {
    private final TicketService ticketService;

    // 어드민 - 정기권 추가
    @PostMapping("/admin/ticket")
    public void adminTicketAdd(@RequestBody @Valid TicketAddRequest request) {
        ticketService.getAdminTicketAdd(request);
    }

    // 어드민 - 정기권 삭제
    @DeleteMapping("/admin/ticket/{ticketId}")
    public void adminTicketDel(@PathVariable Long ticketId) {
        ticketService.getAdminTicketDel(ticketId);
    }

    // 티켓 리스트
    @GetMapping("/tickets")
    public ResponseEntity<Page<TicketResponse>> ticketList(TicketSearchRequest request, Pageable pageable) {
        Page<TicketResponse> ticketList = ticketService.getTicketList(request, pageable);
        return ResponseEntity.ok(ticketList);
    }

    // 정기권 사기
    @PostMapping("/ticket-buy")
    public ResponseEntity<?> ticketBuy(@RequestBody @Valid TicketBuyRequest request) {
        Duration remainedTime = ticketService.getTicketBuy(request);
        return ResponseEntity.ok(remainedTime);
    }
}
