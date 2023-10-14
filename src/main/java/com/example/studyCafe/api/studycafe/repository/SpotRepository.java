package com.example.studyCafe.api.studycafe.repository;

import com.example.studyCafe.api.studycafe.model.Spot;
import com.example.studyCafe.api.studycafe.repository.custom.SpotRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SpotRepository extends JpaRepository<Spot, Long>, SpotRepositoryCustom {
}
