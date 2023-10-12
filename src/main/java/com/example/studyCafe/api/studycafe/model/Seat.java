package com.example.studyCafe.api.studycafe.model;

import com.example.studyCafe.api.auth.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.Nullable;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Seat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "seat_id")
    private Long id;

    @Nullable
    @Column(name = "seat_num")
    private Integer seatNum;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;

    @OneToOne(fetch = LAZY)
    private User user;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "spot_id")
    private Spot spot;


}
