package com.example.studyCafe.api.studycafe.service;


import com.example.studyCafe.api.studycafe.repository.SeatRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@RequiredArgsConstructor
@Service
public class SeatService {
    private final SeatRepository seatRepository;


}
