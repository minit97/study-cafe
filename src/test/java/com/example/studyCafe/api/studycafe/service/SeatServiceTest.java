package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.api.studycafe.dto.request.SeatAddRequest;
import com.example.studyCafe.api.studycafe.dto.request.SeatPickRequest;
import com.example.studyCafe.api.studycafe.dto.request.SeatSearchRequest;
import com.example.studyCafe.api.studycafe.dto.request.SpotAddRequest;
import com.example.studyCafe.api.studycafe.dto.response.SeatResponse;
import com.example.studyCafe.api.studycafe.dto.response.SpotResponse;
import com.example.studyCafe.api.studycafe.model.Seat;
import com.example.studyCafe.api.studycafe.model.Spot;
import com.example.studyCafe.api.studycafe.model.UserSeat;
import com.example.studyCafe.api.studycafe.repository.SeatRepository;
import com.example.studyCafe.api.studycafe.repository.SpotRepository;
import com.example.studyCafe.api.studycafe.repository.UserSeatRepository;
import com.example.studyCafe.testUtil.SecurityTestUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeatServiceTest {
    @Autowired
    private SeatService seatService;

    @Autowired
    private SeatRepository seatRepository;
    @Autowired
    private SpotRepository spotRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserSeatRepository userSeatRepository;

    @Autowired
    private SecurityTestUtil securityTestUtil;

    @BeforeEach
    void clean() {
        seatRepository.deleteAll();
        spotRepository.deleteAll();
    }

    @Transactional
    @Test
    @DisplayName("어드민 - 자리 등록 성공")
    void seatAddSuccess() {
        // given
        Spot spot = Spot.builder()
                .name("창원점")
                .phone("055")
                .build();
        spotRepository.save(spot);
        SeatAddRequest request = SeatAddRequest.builder()
                .spotId(spot.getId())
                .seatNum(1)
                .build();
        // when
        seatService.getSeatAdd(request);

        // then
        Seat seat = seatRepository.findAll().get(0);
        assertEquals(1, seat.getSeatNum());
        assertFalse(seat.getActive());
        Spot spot1 = seat.getSpot();
        assertEquals("창원점", spot1.getName());
        assertEquals("055", spot1.getPhone());
    }

    @Test
    @DisplayName("어드민 - 자리 삭제 성공")
    void seatDelSuccess() {
        // given
        Spot spot = Spot.builder()
                .name("창원점")
                .phone("055")
                .build();
        spotRepository.save(spot);
        Seat seat = Seat.builder()
                .seatNum(1)
                .active(false)
                .spot(spot)
                .build();
        seatRepository.save(seat);
        // when
        seatService.getSeatDel(seat.getId());

        // then
        assertEquals(0, seatRepository.count());
    }

    @Test
    @DisplayName("자리 조회 성공 - active true 경")
    void seatListSearchSuccess() {
        // given
        Spot spot = Spot.builder()
                .name("창원점")
                .phone("055")
                .build();
        spotRepository.save(spot);

        List<Seat> seats = IntStream.range(1,31)
                .mapToObj(i -> Seat.builder()
                        .seatNum(i)
                        .active(false)
                        .spot(spot)
                        .build())
                .collect(Collectors.toList());
        seatRepository.saveAll(seats);

        SeatSearchRequest request = SeatSearchRequest.builder()
                .seatActive(false)
                .build();
        Pageable pageable = PageRequest.of(0, 5, Sort.Direction.DESC, "seat_num");
        // when
        Page<SeatResponse> seatList = seatService.getSeatList(request, pageable);

        // then
        assertEquals(30, seatList.getTotalElements());
        assertEquals(5, seatList.getSize());

        SeatResponse firstSeat = seatList.getContent().get(0);
        assertFalse(firstSeat.getSeatActive());
        assertEquals(1 , firstSeat.getSeatNum());
    }

    @Transactional
    @Test
    @DisplayName("자리 선택 성공")
    void seatPickSuccess() {
        // given
        User user = User.builder()
                .username("phm")
                .password(securityTestUtil.passwordEncoder("1234"))
                .nickname("박현민")
                .remainedTime(1)
                .build();
        userRepository.save(user);

        Spot spot = Spot.builder()
                .name("창원점")
                .phone("055")
                .build();
        spotRepository.save(spot);

        Seat seat = Seat.builder()
                .seatNum(1)
                .active(false)
                .spot(spot)
                .build();
        seatRepository.save(seat);

        SeatPickRequest request = SeatPickRequest.builder()
                .userId(user.getId())
                .seatId(seat.getId())
                .build();

        // when
        SeatResponse seatPick = seatService.getSeatPick(request);

        // then
        assertTrue(seatPick.getSeatActive());
        assertEquals(1, seatPick.getSeatId());

        UserSeat userSeat = userSeatRepository.findAll().get(0);
        assertNotNull(userSeat.getEntryTime());
        assertNotNull(userSeat.getExitTime());
        User seatedUser = userSeat.getUser();
        assertEquals("박현민", seatedUser.getNickname());
        Seat seated = userSeat.getSeat();
        assertEquals(1, seated.getSeatNum());
    }
}