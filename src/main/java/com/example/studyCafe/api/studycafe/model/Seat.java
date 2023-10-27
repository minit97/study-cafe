package com.example.studyCafe.api.studycafe.model;

import jakarta.persistence.*;
import lombok.Builder;
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
    private Boolean active;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;

    @OneToOne(fetch = LAZY, mappedBy = "seat", cascade = CascadeType.ALL)
    private UserSeat userSeat;

    @Builder
    public Seat(Integer seatNum, boolean active, Spot spot) {
        this.seatNum = seatNum;
        this.active = active;
        this.spot = spot;
    }

    public void seatIsActiveChange(boolean active) {
        this.active = active;
    }
}
