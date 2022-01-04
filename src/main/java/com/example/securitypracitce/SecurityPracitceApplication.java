package com.example.securitypracitce;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class SecurityPracitceApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecurityPracitceApplication.class, args);
	}

}
