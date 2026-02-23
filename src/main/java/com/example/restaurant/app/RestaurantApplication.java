package com.example.restaurant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Restaurant Manager Application

// Hosted on Spring Boot's embedded Apache Tomcat server.
// To run: mvn spring-boot:run
// The server starts on http://localhost:8080
// All REST API endpoints are available under /api/
//
// Platform: Spring Boot 3.5 with embedded Tomcat
// Database: MySQL 8 on localhost:3306

@SpringBootApplication
public class RestaurantApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestaurantApplication.class, args);
	}

}
