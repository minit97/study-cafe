package com.example.studyCafe.api.studycafe.service;

import com.example.studyCafe.api.studycafe.repository.TicketRepository;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TicketServiceTest {
    @Autowired
    private TicketService ticketService;
    @Autowired
    private TicketRepository ticketRepository;

    @BeforeEach
    void clean() {
        ticketRepository.deleteAll();
    }


}