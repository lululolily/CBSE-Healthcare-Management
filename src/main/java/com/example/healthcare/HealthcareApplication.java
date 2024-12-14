package com.example.healthcare;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class HealthcareApplication {

	public static void main(String[] args) {
		SpringApplication.run(HealthcareApplication.class, args);
	}

}
