package com.webtech.schoolfeedingsystemtracker;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing // This enables JPA auditing
public class SchoolfeedingsystemtrackerApplication {

	public static void main(String[] args) {
		SpringApplication.run(SchoolfeedingsystemtrackerApplication.class, args);
	}
}
