package com.example.studyCafe.api.studycafe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id")
    private Long id;

    @Column(name = "spot_name")
    private String name;

    @Column(name = "spot_phone")
    private String phone;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    private List<Seat> seat;

}
