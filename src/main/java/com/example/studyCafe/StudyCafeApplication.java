package com.example.studyCafe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class StudyCafeApplication {

	public static void main(String[] args) {
		SpringApplication.run(StudyCafeApplication.class, args);
	}

}
