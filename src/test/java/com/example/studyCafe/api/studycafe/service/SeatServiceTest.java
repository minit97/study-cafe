package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.studycafe.repository.SeatRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SeatServiceTest {
    @Autowired
    private SeatService seatService;
    @Autowired
    private SeatRepository seatRepository;

    @BeforeEach
    void clean() {
        seatRepository.deleteAll();
    }

}