package com.example.studyCafe.api.studycafe.model;

import com.example.studyCafe.entity.BaseTimeEntity;
import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.GenerationType.*;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@Getter
public class Ticket extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "ticket_name")
    private String name;

    @Column(name = "ticket_price")
    private Integer price;

    @Column(name = "ticket_time")
    private Integer time;     //시간권

    @Builder
    public Ticket(String name, Integer price, Integer time) {
        this.name = name;
        this.price = price;
        this.time = time;
    }
}
