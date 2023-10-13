package com.example.studyCafe.api.studycafe.controller;

import com.example.studyCafe.api.studycafe.dto.request.TicketAddRequest;
import com.example.studyCafe.api.studycafe.dto.request.TicketBuyRequest;
import com.example.studyCafe.api.studycafe.service.TicketService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping("/api")
public class TicketController {
    private final TicketService ticketService;

    // 어드민 - 정기권 추가
    @PostMapping("/admin/ticket")
    public void adminTicketAdd(@RequestBody TicketAddRequest request) {
        ticketService.getAdminTicketAdd(request);
    }

    // 어드민 - 정기권 삭제
    @DeleteMapping("/admin/ticket/{ticketId}")
    public void adminTicketDel(@PathVariable Long ticketId) {
        ticketService.getAdminTicketDel(ticketId);
    }

    // 티켓 리스트
    @GetMapping("/tickets")
    public void ticketList() {

    }

    // 정기권 사기
    @PostMapping("/ticket-buy")
    public void ticketBuy(@RequestBody TicketBuyRequest request) {
        ticketService.getTicketBuy(request);
    }

}
