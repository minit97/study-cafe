package com.example.studyCafe.api.studycafe.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Seat {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Column(name = "seat_num")
    private Integer seatNum;

    @Column(name = "seat_active" )
    private boolean active;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @OneToOne(mappedBy = "seat")
    private UserSeat userSeat;


}
