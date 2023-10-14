package com.example.studyCafe.api.studycafe.model;

import com.example.studyCafe.api.auth.model.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static jakarta.persistence.GenerationType.*;
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

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    @OneToOne
    @JoinColumn(name = "seat_id")
    private Seat seat;

    @Column(name = "entry_time")
    private LocalDateTime entryTime;

    @Column(name = "exit_time")
    private LocalDateTime exitTime;
}
