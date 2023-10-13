package com.example.studyCafe.api.studycafe.repository;

import com.example.studyCafe.api.studycafe.model.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long> {
}
