package com.example.studyCafe.api.studycafe.model;

import com.example.studyCafe.api.auth.model.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor(access = PROTECTED)
public class Spot {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "spot_id")
    private Long id;

    private String name;

    @OneToMany(mappedBy = "spot", cascade = CascadeType.ALL)
    private List<Seat> seat;


}
