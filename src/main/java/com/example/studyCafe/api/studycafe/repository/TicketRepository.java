package com.example.studyCafe.api.studycafe.repository;

import com.example.studyCafe.api.studycafe.model.Ticket;
import com.example.studyCafe.api.studycafe.repository.custom.TicketRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TicketRepository extends JpaRepository<Ticket, Long>, TicketRepositoryCustom {
}
