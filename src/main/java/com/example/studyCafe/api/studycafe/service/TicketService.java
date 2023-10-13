package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.api.studycafe.dto.request.TicketAddRequest;
import com.example.studyCafe.api.studycafe.dto.request.TicketBuyRequest;
import com.example.studyCafe.api.studycafe.model.Ticket;
import com.example.studyCafe.api.studycafe.repository.TicketRepository;
import com.example.studyCafe.exception.customException.InvalidRequest;
import com.example.studyCafe.exception.securityException.NotFoundMemberException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import static com.example.studyCafe.util.SecurityUtil.getCurrentUsername;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    public void getAdminTicketAdd(TicketAddRequest request) {
        Ticket data = Ticket.builder()
                .name(request.getTicketName())
                .price(request.getTicketPrice())
                .ticketTime(request.getTicketTime())
                .build();
        ticketRepository.save(data);
    }

    public void getAdminTicketDel(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("없는 티켓입니다."));
        ticketRepository.delete(ticket);
    }


    public void getTicketBuy(TicketBuyRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundMemberException("request id member not found"));
        String securityUser = getCurrentUsername()
                .orElseThrow(() -> new NotFoundMemberException("security token error"));
        if (!user.getUsername().equals(securityUser)) {
            throw new InvalidRequest();
        }

        // 결제 로직
        // ...

    }
}
