package com.example.studyCafe.api.studycafe.model;

import com.example.studyCafe.api.auth.model.User;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "user_seat")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class UserSeat {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_seat_id")
    private Long id;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;


    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne(fetch = LAZY)
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Builder
    public UserSeat(User user, Seat seat, LocalDateTime entryTime, LocalDateTime exitTime) {
        this.user = user;
        this.seat = seat;
        this.entryTime = entryTime;
        this.exitTime = exitTime;
    }
}
