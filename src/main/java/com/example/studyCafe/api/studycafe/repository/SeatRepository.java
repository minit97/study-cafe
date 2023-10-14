package com.example.studyCafe.api.studycafe.repository;

import com.example.studyCafe.api.studycafe.model.Seat;
import com.example.studyCafe.api.studycafe.repository.custom.SeatRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeatRepository extends JpaRepository<Seat, Long>, SeatRepositoryCustom {
}
