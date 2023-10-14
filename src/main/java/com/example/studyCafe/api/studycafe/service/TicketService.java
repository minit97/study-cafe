package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.api.studycafe.dto.request.TicketAddRequest;
import com.example.studyCafe.api.studycafe.dto.request.TicketBuyRequest;
import com.example.studyCafe.api.studycafe.dto.request.TicketSearchRequest;
import com.example.studyCafe.api.studycafe.dto.response.TicketResponse;
import com.example.studyCafe.api.studycafe.model.Ticket;
import com.example.studyCafe.api.studycafe.repository.TicketRepository;
import com.example.studyCafe.exception.customException.InvalidRequest;
import com.example.studyCafe.exception.securityException.NotFoundMemberException;
import com.querydsl.core.QueryResults;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.studyCafe.util.SecurityUtil.getCurrentUsername;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final TicketRepository ticketRepository;
    private final UserRepository userRepository;

    @Transactional
    public void getAdminTicketAdd(TicketAddRequest request) {
        Ticket data = Ticket.builder()
                .name(request.getTicketName())
                .price(request.getTicketPrice())
                .time(request.getTicketTime())
                .build();
        ticketRepository.save(data);
    }

    @Transactional
    public void getAdminTicketDel(Long ticketId) {
        Ticket ticket = ticketRepository.findById(ticketId)
                .orElseThrow(() -> new IllegalArgumentException("없는 티켓입니다."));
        ticketRepository.delete(ticket);
    }

    @Transactional(readOnly = true)
    public Page<TicketResponse> getTicketList(TicketSearchRequest request, Pageable pageable) {
        QueryResults<Ticket> queryResults = ticketRepository.searchTicketList(request, pageable);

        long totalCnt = queryResults.getTotal();
        List<Ticket> resultsList = queryResults.getResults();
        List<TicketResponse> dtoList = resultsList.stream()
                .map(TicketResponse::from)
                .collect(Collectors.toList());

        return new PageImpl<>(dtoList, pageable, totalCnt);
    }

    @Transactional
    public Duration getTicketBuy(TicketBuyRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new NotFoundMemberException("request id member not found"));
        String securityUser = getCurrentUsername()
                .orElseThrow(() -> new NotFoundMemberException("security token error"));
        if (!user.getUsername().equals(securityUser)) {
            throw new InvalidRequest();
        }

        // 결제 로직
        // ...

        Ticket ticket = ticketRepository.findById(request.getTicketId())
                .orElseThrow(() -> new IllegalArgumentException("해당 티켓은 없습니다."));
        Duration duration = user.addRemainedTime(ticket.getTime());
        return duration;
    }


}
