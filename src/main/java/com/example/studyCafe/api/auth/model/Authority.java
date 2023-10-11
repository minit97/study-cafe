package com.example.studyCafe.api.auth.model;

import lombok.*;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import static lombok.AccessLevel.*;

@Entity
@Table(name = "authority")
@NoArgsConstructor(access = PROTECTED)
@Getter
@Setter
public class Authority {

    @Id
    @Column(name = "authority_name", length = 50)
    private String authorityName;

    @Builder
    public Authority(String authorityName) {
        this.authorityName = authorityName;
    }
}

