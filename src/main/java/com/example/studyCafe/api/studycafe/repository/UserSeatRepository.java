package com.example.studyCafe.api.studycafe.repository;


import com.example.studyCafe.api.studycafe.model.UserSeat;
import com.example.studyCafe.api.studycafe.repository.custom.UserSeatRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserSeatRepository extends JpaRepository<UserSeat, Long>, UserSeatRepositoryCustom {
}
