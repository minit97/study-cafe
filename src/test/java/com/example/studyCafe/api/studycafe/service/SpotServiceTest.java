package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.studycafe.repository.SpotRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class SpotServiceTest {
    @Autowired
    private SpotService spotService;
    @Autowired
    private SpotRepository spotRepository;

    @BeforeEach
    void clean() {
        spotRepository.deleteAll();
    }

}