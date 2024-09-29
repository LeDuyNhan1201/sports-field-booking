package org.jakartaee5g23.sportsfieldbooking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing(auditorAwareRef = "auditorProvider")
public class SportsFieldBookingApplication {

	public static void main(String[] args) {
		SpringApplication.run(SportsFieldBookingApplication.class, args);
	}

}
