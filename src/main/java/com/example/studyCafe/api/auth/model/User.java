package com.example.studyCafe.api.auth.model;


import com.example.studyCafe.api.board.model.Board;
import com.example.studyCafe.api.studycafe.model.UserSeat;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static jakarta.persistence.GenerationType.IDENTITY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@Table(name = "users")
@NoArgsConstructor(access = PROTECTED)
@Getter
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "user_id")
    private Long id;

    @Column(length = 50, unique = true)
    private String username;

    @Column(length = 100)
    private String password;

    @Column(length = 50)
    private String nickname;

    @Column(name = "remained_time")
    private Duration remainedTime;

    // cascade : 부모객체 수정시 자식객체도 적용 / orphanRemoval : 해당 컬렉션에서 삭제시 자식객체도 삭제
    @OneToMany(mappedBy = "user", cascade = ALL, orphanRemoval = true)
    private List<Authority> authorities = new ArrayList<>();

    @OneToMany(mappedBy = "user", cascade = ALL)
    private List<Board> boardList = new ArrayList<>();

    @OneToOne(mappedBy = "user", fetch = LAZY)
    private UserSeat userSeat;

    @Builder
    public User(String username, String password, String nickname, Role authorities, Integer remainedTime) {
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.remainedTime = Duration.ofHours(remainedTime);

        Authority role = Authority.builder()
                .user(this)
                .authorityName(authorities)
                .build();
        this.authorities.add(role);
    }

    public Duration buyTicketPlusTime(Integer addTime) {
        this.remainedTime = remainedTime.plusHours(addTime);
        return remainedTime;
    }
    public Duration minusRemainedTime(Duration minusTime) {
        this.remainedTime = remainedTime.minus(minusTime);
        return remainedTime;
    }

}
