package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;



@SpringBootApplication
@EnableMongoRepositories(basePackages = "com.repo")
@ComponentScan ({"com", "com.services", "com.config"})
public class PlanetariumReservApplication {

	public static void main(String[] args) {
		SpringApplication.run(PlanetariumReservApplication.class, args);
	}


}
