package com.example.restaurant.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

// Restaurant Manager Application

// Hosted on Spring Boot's embedded Apache Tomcat server.
// To run: mvn spring-boot:run
// The server starts on http://localhost:8080
// All REST API endpoints are available under /api/
//
// Platform: Spring Boot 3.5 with embedded Tomcat
// Database: MySQL 8 on localhost:3306

@SpringBootApplication(scanBasePackages = "com.example.restaurant")
@EnableJpaRepositories(basePackages = "com.example.restaurant.repository")
@EntityScan(basePackages = "com.example.restaurant.model")
public class RestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}

}
