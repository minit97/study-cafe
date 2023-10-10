package com.example.studyCafe.api.studycafe.repository;

import com.example.studyCafe.api.studycafe.model.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long> {
}
