package com.example.studyCafe.api.studycafe.service;


import com.example.studyCafe.api.auth.model.User;
import com.example.studyCafe.api.auth.repository.UserRepository;
import com.example.studyCafe.api.studycafe.dto.request.SeatAddRequest;
import com.example.studyCafe.api.studycafe.dto.request.SeatExitRequest;
import com.example.studyCafe.api.studycafe.dto.request.SeatPickRequest;
import com.example.studyCafe.api.studycafe.dto.request.SeatSearchRequest;
import com.example.studyCafe.api.studycafe.dto.response.SeatResponse;
import com.example.studyCafe.api.studycafe.dto.response.UserSeatReponse;
import com.example.studyCafe.api.studycafe.model.Seat;
import com.example.studyCafe.api.studycafe.model.Spot;
import com.example.studyCafe.api.studycafe.model.UserSeat;
import com.example.studyCafe.api.studycafe.repository.SeatRepository;
import com.example.studyCafe.api.studycafe.repository.SpotRepository;
import com.example.studyCafe.api.studycafe.repository.UserSeatRepository;
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
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RequiredArgsConstructor
@Service
public class SeatService {
    private final SeatRepository seatRepository;
    private final SpotRepository spotRepository;
    private final UserRepository userRepository;
    private final UserSeatRepository userSeatRepository;

    @Transactional
    public void getSeatAdd(SeatAddRequest request) {
        Spot spot = spotRepository.findById(request.getSpotId())
                .orElseThrow(() -> new IllegalArgumentException("해당 지점이 없습니다."));

        Seat newSeat = Seat.builder()
                .seatNum(request.getSeatNum())
                .active(false)
                .spot(spot)
                .build();
        seatRepository.save(newSeat);
    }

    @Transactional
    public void getSeatDel(Long seatId) {
        Seat seat = seatRepository.findById(seatId)
                .orElseThrow(() -> new IllegalArgumentException("해당 자리는 없습니다."));
        seatRepository.delete(seat);
    }


    @Transactional(readOnly = true)
    public Page<SeatResponse> getSeatList(SeatSearchRequest request, Pageable pageable) {
        QueryResults<Seat> queryResults = seatRepository.searchSeatList(request, pageable);
        long total = queryResults.getTotal();
        List<SeatResponse> list = queryResults.getResults().stream()
                .map(SeatResponse::from)
                .collect(Collectors.toList());
        return new PageImpl<>(list, pageable, total);
    }

    @Transactional
    public SeatResponse getSeatPick(SeatPickRequest request) {
        User user = userRepository.findById(request.getUserId())
                .orElseThrow(NotFoundMemberException::new);

        Seat seat = seatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new IllegalArgumentException("해당 자리는 없습니다."));
        if (seat.getActive()) {
            throw new IllegalArgumentException("해당 자리는 이미 사용 중입니다.");
        }
        seat.seatIsActiveChange(true);

        UserSeat build = UserSeat.builder()
                .entryTime(LocalDateTime.now())
                .exitTime(LocalDateTime.now().plusHours(9))
                .user(user)
                .seat(seat)
                .build();
        return SeatResponse.from(userSeatRepository.save(build).getSeat());
    }

    @Transactional
    public UserSeatReponse getSeatExit(SeatExitRequest request) {
        UserSeat userSeat = userSeatRepository.findById(request.getSeatId())
                .orElseThrow(() -> new IllegalArgumentException("해당 자리는 오류가 있습니다."));
        userSeat.getSeat().seatIsActiveChange(false);
        Duration minusTime = Duration.between(userSeat.getEntryTime(), LocalDateTime.now());
        userSeat.getUser().minusRemainedTime(minusTime);

        return UserSeatReponse.from(userSeat);
    }
}
