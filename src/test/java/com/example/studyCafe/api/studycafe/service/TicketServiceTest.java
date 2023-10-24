package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.api.studycafe.dto.request.TicketAddRequest;
import com.example.studyCafe.api.studycafe.dto.request.TicketBuyRequest;
import com.example.studyCafe.api.studycafe.dto.request.TicketSearchRequest;
import com.example.studyCafe.api.studycafe.dto.response.TicketResponse;
import com.example.studyCafe.api.studycafe.model.Ticket;
import com.example.studyCafe.api.studycafe.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static com.example.studyCafe.api.auth.model.Role.ROLE_USER;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class TicketServiceTest {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketRepository ticketRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void clean() {
        ticketRepository.deleteAll();
    }

    @Test
    @DisplayName("어드민 - 정기권 등록 성공")
    void ticketAddSuccess() {
        // given
        TicketAddRequest request = TicketAddRequest.builder()
                .ticketName("4시간")
                .ticketTime(4)
                .ticketPrice(4000)
                .build();
        // when
        ticketService.getAdminTicketAdd(request);

        // then
        Ticket ticket = ticketRepository.findAll().get(0);
        assertEquals("4시간", ticket.getName());
        assertEquals(4, ticket.getTime());
        assertEquals(4000, ticket.getPrice());
    }

    @Test
    @DisplayName("어드민 - 정기권 삭제 성공")
    void ticketDelSuccess() {
        // given
        Ticket ticket = Ticket.builder()
                .name("4시간")
                .time(4)
                .price(4000)
                .build();
        ticketRepository.save(ticket);

        // when
        ticketService.getAdminTicketDel(ticket.getId());

        // then
        assertEquals(0, ticketRepository.count());
    }

    @Test
    @DisplayName("정기권 리스트 전체 조회 성공")
    void ticketSearchSuccess() {
        // given
        List<Ticket> requestPosts = IntStream.range(1,31)
                .mapToObj(i -> Ticket.builder()
                        .name(i + "시간")
                        .time(i)
                        .price(i * 1000)
                        .build())
                .collect(Collectors.toList());
        ticketRepository.saveAll(requestPosts);

        TicketSearchRequest request = TicketSearchRequest.builder()
                .ticketPriceLeo(null)
                .ticketPriceGeo(null)
                .ticketTimeLeo(null)
                .ticketTimeGeo(null)
                .build();
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "id");

        // when
        Page<TicketResponse> ticketList = ticketService.getTicketList(request, pageable);
        // then
        assertEquals(30, ticketList.getTotalElements());
        assertEquals(5, ticketList.getSize());

        TicketResponse firstTicket = ticketList.getContent().get(0);
        assertEquals("1시간" ,firstTicket.getTicketName());
        assertEquals(1000 ,firstTicket.getTicketPrice());
        assertEquals(1 ,firstTicket.getTicketTime());
    }

    @Test
    @DisplayName("정기권 구매 성공")
    void ticketBuySuccess() {
        // given
        User user = User.builder()
                .username("phm")
                .password(passwordEncoder.encode("1234"))
                .nickname("박현민")
                .authorities(ROLE_USER)
                .remainedTime(0)
                .build();
        userRepository.save(user);
        Ticket ticket = Ticket.builder()
                .name("1시간권")
                .time(1)
                .price(1000)
                .build();
        ticketRepository.save(ticket);
        TicketBuyRequest request = TicketBuyRequest.builder()
                .ticketId(ticket.getId())
                .userId(user.getId())
                .build();

        // 토큰 세팅
        UsernamePasswordAuthenticationToken authenticationToken =
                new UsernamePasswordAuthenticationToken("phm", "1234");
        Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // when
        Duration remainedTime = ticketService.getTicketBuy(request);

        // then
        assertEquals(60 * 60, remainedTime.getSeconds());
    }
}